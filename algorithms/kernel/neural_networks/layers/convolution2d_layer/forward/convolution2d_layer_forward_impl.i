/* file: convolution2d_layer_forward_impl.i */
/*******************************************************************************
* Copyright 2014-2017 Intel Corporation
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

/*
//++
//  Implementation of convolution2d algorithm
//--
*/

#include "service_tensor.h"
#include "service_numeric_table.h"
#include "mkl_tensor.h"

using namespace daal::internal;
using namespace daal::services;

namespace daal
{
namespace algorithms
{
namespace neural_networks
{
namespace layers
{
namespace convolution2d
{
namespace forward
{
namespace internal
{

template<typename algorithmFPType, Method method, CpuType cpu>
void Convolution2dKernel<algorithmFPType, method, cpu>::initialize(const services::Collection<size_t>& inDimsFull, const services::Collection<size_t>& wDims,
                                                                const convolution2d::Parameter *parameter, const services::Collection<size_t>& outDimsFull)
{
    dnnError_t err;

    const size_t nGroups = parameter->nGroups;

    services::Collection<size_t> inDims (dimension);
    services::Collection<size_t> outDims(dimension);

    inDims  = inDimsFull ;
    outDims = outDimsFull;

    biasSize   [0] = parameter->nKernels;
    biasStrides[0] = 1;

    inputSize    [0] = inDims [dimension-1];
    inputStrides [0] = 1;
    outputSize   [0] = outDims[dimension-1];
    outputStrides[0] = 1;

    for(size_t i=1; i<dimension; i++)
    {
        inputSize    [i] = inDims [dimension-1-i];
        inputStrides [i] = inputStrides [i-1]*inputSize[i-1];
        outputSize   [i] = outDims[dimension-1-i];
        outputStrides[i] = outputStrides[i-1]*outputSize[i-1];
    }

    size_t filterDimension = dimension + (nGroups != 1);

    filterSize   [0] = wDims[filterDimension - 1];
    filterStrides[0] = 1;
    for(size_t i=1; i<filterDimension; i++)
    {
        filterSize   [i] = wDims  [filterDimension-1-i];
        filterStrides[i] = filterStrides[i-1]*filterSize[i-1];
    }

    convolutionStride[0] =   parameter->strides.size[1]  ;
    convolutionStride[1] =   parameter->strides.size[0]  ;
    inputOffset      [0] = -(parameter->paddings.size[1]);
    inputOffset      [1] = -(parameter->paddings.size[0]);

    ltUserInput  = xDnnLayout(dimension,       inputSize,  inputStrides ); ON_ERR(ltUserInput .err);
    ltUserFilt   = xDnnLayout(filterDimension, filterSize, filterStrides); ON_ERR(ltUserFilt  .err);
    ltUserBias   = xDnnLayout(1,               biasSize,   biasStrides  ); ON_ERR(ltUserBias  .err);
    ltUserOutput = xDnnLayout(dimension,       outputSize, outputStrides); ON_ERR(ltUserOutput.err);

    err = dnn::xConvolutionCreateForwardBias( &convPrim, dnnAlgorithmConvolutionDirect, nGroups, dimension, inputSize, outputSize,
        filterSize, convolutionStride, inputOffset, dnnBorderZeros);  ON_ERR(err);
}

template<typename algorithmFPType, Method method, CpuType cpu>
void Convolution2dKernel<algorithmFPType, method, cpu>::compute(Tensor *inputTensor, Tensor *wTensor, Tensor *bTensor,
                                                                const convolution2d::Parameter *parameter, Tensor *resultTensor)
{
    MklTensor<algorithmFPType> *inputMklTensor = dynamic_cast<MklTensor<algorithmFPType>*>(inputTensor);
    MklTensor<algorithmFPType> *wMklTensor = dynamic_cast<MklTensor<algorithmFPType>*>(wTensor);
    MklTensor<algorithmFPType> *bMklTensor = dynamic_cast<MklTensor<algorithmFPType>*>(bTensor);
    MklTensor<algorithmFPType> *resultMklTensor = dynamic_cast<MklTensor<algorithmFPType>*>(resultTensor);

    dnnError_t err;

    const services::Collection<size_t>& inDimsFull  = inputTensor->getDimensions();
    const services::Collection<size_t>& wDims       = wTensor->getDimensions();
    const services::Collection<size_t>& bDims       = bTensor->getDimensions();
    const services::Collection<size_t>& outDimsFull = resultTensor->getDimensions();

    algorithmFPType* convRes[dnnResourceNumber] = {0};

    dnnLayout_t inputLayout;
    err = dnn::xLayoutCreateFromPrimitive(&inputLayout, convPrim, dnnResourceSrc); ON_ERR(err);

    ReadSubtensor<algorithmFPType, cpu> inputBlock;
    LayoutConvertor<algorithmFPType, cpu> cvToInnerInput;

    if (inputMklTensor != 0)
    {
        inputMklTensor->setLayout((void*)inputLayout);
        convRes[dnnResourceSrc] = inputMklTensor->getArray();
    }
    else
    {
        const size_t dimsArray[dimension] = { 0, parameter->groupDimension, parameter->indices.dims[0], parameter->indices.dims[1] };
        TensorOffsetLayout targetInLayout = inputTensor->createDefaultSubtensorLayout();
        targetInLayout.shuffleDimensions( services::Collection<size_t>( dimension, dimsArray ) );

        inputBlock.set(inputTensor, 0, 0, 0, inDimsFull[0], targetInLayout);
        algorithmFPType *inputArray = const_cast<algorithmFPType*>(inputBlock.get());

        cvToInnerInput.set(&inputArray, ltUserInput.get(), true, &convRes[dnnResourceSrc], inputLayout, false); ON_ERR(cvToInnerInput.err);
        cvToInnerInput.convert(); ON_ERR(cvToInnerInput.err);

        dnn::xLayoutDelete(inputLayout);
    }

    dnnLayout_t filtLayout;
    err = dnn::xLayoutCreateFromPrimitive(&filtLayout, convPrim, dnnResourceFilter); ON_ERR(err);

    ReadSubtensor<algorithmFPType, cpu> wBlock;
    LayoutConvertor<algorithmFPType, cpu> cvToInnerFilt;

    if (wMklTensor != 0)
    {
        wMklTensor->setLayout((void*)filtLayout);
        convRes[dnnResourceFilter] = wMklTensor->getArray();
    }
    else
    {
        wBlock.set(wTensor, 0, 0, 0, wDims[0]);
        algorithmFPType *wArray = const_cast<algorithmFPType*>(wBlock.get());

        cvToInnerFilt.set(&wArray, ltUserFilt.get(), true, &convRes[dnnResourceFilter], filtLayout, false); ON_ERR(cvToInnerFilt.err);
        cvToInnerFilt.convert(); ON_ERR(cvToInnerFilt.err);

        dnn::xLayoutDelete(filtLayout);
    }

    dnnLayout_t biasLayout;
    err = dnn::xLayoutCreateFromPrimitive(&biasLayout, convPrim, dnnResourceBias); ON_ERR(err);

    ReadSubtensor<algorithmFPType, cpu> bBlock;
    LayoutConvertor<algorithmFPType, cpu> cvToInnerBias;

    if (bMklTensor != 0)
    {
        bMklTensor->setLayout((void*)biasLayout);
        convRes[dnnResourceBias] = bMklTensor->getArray();
    }
    else
    {
        bBlock.set(bTensor, 0, 0, 0, bDims[0]);
        algorithmFPType *bArray = const_cast<algorithmFPType*>(bBlock.get());

        cvToInnerBias.set(&bArray, ltUserBias.get(), true, &convRes[dnnResourceBias], biasLayout, false); ON_ERR(cvToInnerBias.err);
        cvToInnerBias.convert(); ON_ERR(cvToInnerBias.err);

        dnn::xLayoutDelete(biasLayout);
    }

    dnnLayout_t resultLayout;
    err = dnn::xLayoutCreateFromPrimitive(&resultLayout, convPrim, dnnResourceDst); ON_ERR(err);

    if (resultMklTensor != NULL)
    {
        resultMklTensor->setLayout((void*)resultLayout);
        convRes[dnnResourceDst] = resultMklTensor->getArray();

        err = dnn::xExecute(convPrim, (void**)convRes); ON_ERR(err);
    }
    else
    {
        WriteOnlySubtensor<algorithmFPType, cpu> resultBlock(resultTensor, 0, 0, 0, outDimsFull[0]);
        algorithmFPType *resultArray = resultBlock.get();

        LayoutConvertor<algorithmFPType, cpu> cvFromInnerOutput(&convRes[dnnResourceDst], resultLayout, false, &resultArray, ltUserOutput.get(), true); ON_ERR(cvFromInnerOutput.err);

        err = dnn::xExecute(convPrim, (void**)convRes); ON_ERR(err);

        cvFromInnerOutput.convert(); ON_ERR(cvFromInnerOutput.err);

        dnn::xLayoutDelete(resultLayout);
    }
}

template<typename algorithmFPType, Method method, CpuType cpu>
void Convolution2dKernel<algorithmFPType, method, cpu>::reset()
{
    if(convPrim != NULL)
    {
        dnn::xDelete(convPrim);
    }
}

} // internal
} // forward
} // namespace convolution2d
} // namespace layers
} // namespace neural_networks
} // namespace algorithms
} // namespace daal

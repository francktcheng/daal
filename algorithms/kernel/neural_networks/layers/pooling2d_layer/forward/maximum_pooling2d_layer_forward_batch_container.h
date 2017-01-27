/* file: maximum_pooling2d_layer_forward_batch_container.h */
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
//  Implementation of forward pooling layer container.
//--
*/

#ifndef __MAXIMUM_POOLING2D_LAYER_FORWARD_BATCH_CONTAINER_H__
#define __MAXIMUM_POOLING2D_LAYER_FORWARD_BATCH_CONTAINER_H__

#include "neural_networks/layers/pooling2d/maximum_pooling2d_layer_forward.h"
#include "maximum_pooling2d_layer_forward_kernel.h"

namespace daal
{
namespace algorithms
{
namespace neural_networks
{
namespace layers
{
namespace maximum_pooling2d
{
namespace forward
{
namespace interface1
{
template<typename algorithmFPType, Method method, CpuType cpu>
BatchContainer<algorithmFPType, method, cpu>::BatchContainer(daal::services::Environment::env *daalEnv)
{
    __DAAL_INITIALIZE_KERNELS(internal::PoolingKernel, algorithmFPType, method);
}

template<typename algorithmFPType, Method method, CpuType cpu>
BatchContainer<algorithmFPType, method, cpu>::~BatchContainer()
{
    __DAAL_DEINITIALIZE_KERNELS();
}

template<typename algorithmFPType, Method method, CpuType cpu>
void BatchContainer<algorithmFPType, method, cpu>::setupCompute()
{
    maximum_pooling2d::forward::Input *input = static_cast<maximum_pooling2d::forward::Input *>(_in);
    maximum_pooling2d::forward::Result *result = static_cast<maximum_pooling2d::forward::Result *>(_res);

    maximum_pooling2d::Parameter *parameter = static_cast<maximum_pooling2d::Parameter *>(_par);
    daal::services::Environment::env &env = *_env;

    const services::Collection<size_t>& inDimsFull  = input->get(layers::forward::data)->getDimensions();
    const services::Collection<size_t>& outDimsFull = result->get(layers::forward::value)->getDimensions();

    __DAAL_CALL_KERNEL(env, internal::PoolingKernel, __DAAL_KERNEL_ARGUMENTS(algorithmFPType, method), initialize, inDimsFull, parameter, outDimsFull);
}

template<typename algorithmFPType, Method method, CpuType cpu>
void BatchContainer<algorithmFPType, method, cpu>::compute()
{
    maximum_pooling2d::forward::Input *input = static_cast<maximum_pooling2d::forward::Input *>(_in);
    maximum_pooling2d::forward::Result *result = static_cast<maximum_pooling2d::forward::Result *>(_res);
    maximum_pooling2d::Parameter *parameter = static_cast<maximum_pooling2d::Parameter *>(_par);

    data_management::Tensor *dataTensor = input->get(layers::forward::data).get();
    data_management::Tensor *valueTensor = result->get(layers::forward::value).get();
    data_management::Tensor *selectedPosTensor = nullptr;
    if (result->get(layers::forward::resultForBackward))
    {
        selectedPosTensor = result->get(auxSelectedIndices).get();
    }

    daal::services::Environment::env &env = *_env;

    __DAAL_CALL_KERNEL(env, internal::PoolingKernel, __DAAL_KERNEL_ARGUMENTS(algorithmFPType, method), compute, \
        dataTensor, valueTensor, selectedPosTensor, parameter);
}
} // namespace interface1
} // namespace forward
} // namespace maximum_pooling2d
} // namespace layers
} // namespace neural_networks
} // namespace algorithms
} // namespace daal

#endif

/* file: StochPool2DLayerDenseBatch.java */
/*******************************************************************************
* Copyright 2014-2017 Intel Corporation
* All Rights Reserved.
*
* If this  software was obtained  under the  Intel Simplified  Software License,
* the following terms apply:
*
* The source code,  information  and material  ("Material") contained  herein is
* owned by Intel Corporation or its  suppliers or licensors,  and  title to such
* Material remains with Intel  Corporation or its  suppliers or  licensors.  The
* Material  contains  proprietary  information  of  Intel or  its suppliers  and
* licensors.  The Material is protected by  worldwide copyright  laws and treaty
* provisions.  No part  of  the  Material   may  be  used,  copied,  reproduced,
* modified, published,  uploaded, posted, transmitted,  distributed or disclosed
* in any way without Intel's prior express written permission.  No license under
* any patent,  copyright or other  intellectual property rights  in the Material
* is granted to  or  conferred  upon  you,  either   expressly,  by implication,
* inducement,  estoppel  or  otherwise.  Any  license   under such  intellectual
* property rights must be express and approved by Intel in writing.
*
* Unless otherwise agreed by Intel in writing,  you may not remove or alter this
* notice or  any  other  notice   embedded  in  Materials  by  Intel  or Intel's
* suppliers or licensors in any way.
*
*
* If this  software  was obtained  under the  Apache License,  Version  2.0 (the
* "License"), the following terms apply:
*
* You may  not use this  file except  in compliance  with  the License.  You may
* obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
*
*
* Unless  required  by   applicable  law  or  agreed  to  in  writing,  software
* distributed under the License  is distributed  on an  "AS IS"  BASIS,  WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*
* See the   License  for the   specific  language   governing   permissions  and
* limitations under the License.
*******************************************************************************/

/*
 //  Content:
 //   Java example of neural network forward and backward two-dimensional stochastic pooling layers usage
 ////////////////////////////////////////////////////////////////////////////////
 */

package com.intel.daal.examples.neural_networks;

import com.intel.daal.algorithms.neural_networks.layers.stochastic_pooling2d.*;
import com.intel.daal.algorithms.neural_networks.layers.ForwardResultId;
import com.intel.daal.algorithms.neural_networks.layers.ForwardResultLayerDataId;
import com.intel.daal.algorithms.neural_networks.layers.ForwardInputId;
import com.intel.daal.algorithms.neural_networks.layers.BackwardResultId;
import com.intel.daal.algorithms.neural_networks.layers.BackwardInputId;
import com.intel.daal.algorithms.neural_networks.layers.BackwardInputLayerDataId;
import com.intel.daal.data_management.data.Tensor;
import com.intel.daal.data_management.data.HomogenTensor;
import com.intel.daal.examples.utils.Service;
import com.intel.daal.services.DaalContext;

import com.intel.daal.data_management.data.NumericTable;
/**
 * <a name="DAAL-EXAMPLE-JAVA-STOCHASTICPOOLING2DLAYERBATCH">
 * @example StochPool2DLayerDenseBatch.java
 */
class StochPool2DLayerDenseBatch {
    /* Input non-negative data set */
    private static final String datasetFileName = "../data/batch/layer_non_negative.csv";

    private static DaalContext context = new DaalContext();

    public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
        /* Read datasetFileName from a file and create a tensor to store forward input data */
        Tensor data = Service.readTensorFromCSV(context, datasetFileName);

        /* Get number of dimensions in input tensor */
        long nDim = data.getDimensions().length;

        /* Print the input of the forward two-dimensional pooling */
        Service.printTensor("Forward two-dimensional stochastic pooling input (first 10 rows):", data, 10, 0);

        /* Create an algorithm to compute forward two-dimensional pooling results using default method */
        StochasticPooling2dForwardBatch stochasticPooling2DLayerForward = new StochasticPooling2dForwardBatch(context, Float.class, StochasticPooling2dMethod.defaultDense, nDim);

        /* Set input objects for the forward two-dimensional pooling */
        stochasticPooling2DLayerForward.input.set(ForwardInputId.data, data);

        /* Compute forward two-dimensional pooling results */
        StochasticPooling2dForwardResult forwardResult = stochasticPooling2DLayerForward.compute();

        /* Print the results of the forward two-dimensional pooling */
        Service.printTensor("Forward two-dimensional stochastic pooling result (first 5 rows):", forwardResult.get(ForwardResultId.value), 5, 0);
        Service.printTensor("Forward two-dimensional stochastic pooling layer selected indices (first 5 rows):",
                            forwardResult.get(StochasticPooling2dLayerDataId.auxSelectedIndices), 5, 0);

        /* Create an algorithm to compute backward two-dimensional pooling results using default method */
        StochasticPooling2dBackwardBatch stochasticPooling2DLayerBackward = new StochasticPooling2dBackwardBatch(context, Float.class, StochasticPooling2dMethod.defaultDense, nDim);

        /* Set input objects for the backward two-dimensional pooling */
        stochasticPooling2DLayerBackward.input.set(BackwardInputId.inputGradient, forwardResult.get(ForwardResultId.value));
        stochasticPooling2DLayerBackward.input.set(BackwardInputLayerDataId.inputFromForward,
                forwardResult.get(ForwardResultLayerDataId.resultForBackward));

        /* Compute backward two-dimensional pooling results */
        StochasticPooling2dBackwardResult backwardResult = stochasticPooling2DLayerBackward.compute();

        /* Print the results of the backward two-dimensional pooling */
        Service.printTensor("Backward two-dimensional stochastic pooling result (first 10 rows):", backwardResult.get(BackwardResultId.gradient), 10, 0);

        context.dispose();
    }
}

/* file: PredictionInput.java */
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

/**
 * @defgroup neural_networks_prediction Prediction
 * @brief Contains classes for making prediction based on the trained model
 * @ingroup neural_networks
 * @{
 */
package com.intel.daal.algorithms.neural_networks.prediction;

import com.intel.daal.data_management.data.Factory;
import com.intel.daal.data_management.data.Tensor;
import com.intel.daal.services.DaalContext;

/**
 * <a name="DAAL-CLASS-ALGORITHMS__NEURAL_NETWORKS__PREDICTION__PREDICTIONINPUT"></a>
 * @brief Input objects of the neural networks prediction algorithm
 */
public class PredictionInput extends com.intel.daal.algorithms.Input {
    /** @private */
    static {
        System.loadLibrary("JavaAPI");
    }

    public PredictionInput(DaalContext context, long cObject) {
        super(context, cObject);
    }

    /**
     * Sets the input object for the neural networks prediction algorithm
     * @param id    Identifier of the input object
     * @param val   Value of the input object
     */
    public void set(PredictionTensorInputId id, Tensor val) {
        if (id == PredictionTensorInputId.data) {
            cSetInput(cObject, id.getValue(), val.getCObject());
        }
        else {
            throw new IllegalArgumentException("Incorrect PredictionTensorInputId");
        }
    }

    /**
     * Sets the input object for the neural networks prediction algorithm
     * @param id    Identifier of the input object
     * @param val   Value of the input object
     */
    public void set(PredictionModelInputId id, PredictionModel val) {
        if (id == PredictionModelInputId.model) {
            cSetInput(cObject, id.getValue(), val.getCObject());
        }
        else {
            throw new IllegalArgumentException("Incorrect PredictionModelInputId");
        }
    }

    /**
     * Returns input object for the neural networks prediction algorithm
     * @param id Identifier of the input object
     * @return   Input object that corresponds to the given identifier
     */
    public Tensor get(PredictionTensorInputId id) {
        if (id == PredictionTensorInputId.data) {
            return (Tensor)Factory.instance().createObject(getContext(), cGetInput(cObject, id.getValue()));
        }
        else {
            throw new IllegalArgumentException("id unsupported");
        }
    }

    /**
     * Returns input object for the neural networks prediction algorithm
     * @param id Identifier of the input object
     * @return   Input object that corresponds to the given identifier
     */
    public PredictionModel get(PredictionModelInputId id) {
        if (id == PredictionModelInputId.model) {
            return new PredictionModel(getContext(), cGetInput(cObject, id.getValue()));
        }
        else {
            throw new IllegalArgumentException("id unsupported");
        }
    }

    private native void cSetInput(long cObject, int id, long ntAddr);
    private native long cGetInput(long cObject, int id);
}
/** @} */

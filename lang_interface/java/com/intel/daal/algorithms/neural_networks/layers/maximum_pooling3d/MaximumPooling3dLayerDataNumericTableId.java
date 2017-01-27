/* file: MaximumPooling3dLayerDataNumericTableId.java */
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
 * @ingroup maximum_pooling3d
 * @{
 */
package com.intel.daal.algorithms.neural_networks.layers.maximum_pooling3d;

/**
 * <a name="DAAL-CLASS-ALGORITHMS__NEURAL_NETWORKS__LAYERS__MAXIMUM_POOLING3D__MAXIMUMPOOLING3DLAYERDATANUMERICTABLEID"></a>
 * \brief Identifiers of input objects for the backward three-dimensional maximum pooling layer and
 *        results for the forward three-dimensional maximum pooling layer
 */
public final class MaximumPooling3dLayerDataNumericTableId {
    /** @private */
    static {
        System.loadLibrary("JavaAPI");
    }

    private int _value;

    /**
     * Constructs the result object identifier using the provided value
     * @param value     Value of the input object identifier
     */
    public MaximumPooling3dLayerDataNumericTableId(int value) {
        _value = value;
    }

    /**
     * Returns the value corresponding to the result identifier
     * @return Value corresponding to the result identifier
     */
    public int getValue() {
        return _value;
    }

    private static final int auxInputDimensionsId = 1;

    public static final MaximumPooling3dLayerDataNumericTableId auxInputDimensions = new MaximumPooling3dLayerDataNumericTableId(
        auxInputDimensionsId);    /*!< Numeric table of size 1 x p that contains the sizes
                                       of the dimensions of the input data tensor */
}
/** @} */

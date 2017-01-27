/* file: InputId.java */
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
 * @ingroup low_order_moments
 * @{
 */
package com.intel.daal.algorithms.low_order_moments;

/**
 * <a name="DAAL-CLASS-ALGORITHMS__LOW_ORDER_MOMENTS__INPUTID"></a>
 * @brief Available identifiers of input objects for the low order %moments
 */
public final class InputId {
    private int _value;

    public InputId(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }

    private static final int DefaultInputId = 0;

    /** %Input data table */
    public static final InputId data = new InputId(DefaultInputId);
}
/** @} */

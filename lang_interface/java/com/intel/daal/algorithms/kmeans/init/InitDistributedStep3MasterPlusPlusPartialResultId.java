/* file: InitDistributedStep3MasterPlusPlusPartialResultId.java */
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
 * @ingroup kmeans_init_distributed
 * @{
 */
package com.intel.daal.algorithms.kmeans.init;

/**
 * <a name="DAAL-CLASS-ALGORITHMS__KMEANS__INIT__INITDISTRIBUTEDSTEP3MASTERPLUSPLUSPARTIALRESULTID"></a>
 * @brief Available identifiers of partial results of computing initial clusters for the K-Means algorithm in the distributed processing mode
 *        used with plusPlus and parallelPlus methods only on the 3rd step on a master node.
 */
public final class InitDistributedStep3MasterPlusPlusPartialResultId {
    private int _value;

    public InitDistributedStep3MasterPlusPlusPartialResultId(int value) {
        _value = value;
    }

    /**
     * Returns a value corresponding to the identifier of the input object
     * \return Value corresponding to the identifier
     */
    public int getValue() {
        return _value;
    }

    private static final int outputOfStep3ForStep4Value = 0;

    /** KeyValueDataCollection with the input for local nodes on step 4 */
    public static final InitDistributedStep3MasterPlusPlusPartialResultId outputOfStep3ForStep4 =
        new InitDistributedStep3MasterPlusPlusPartialResultId(outputOfStep3ForStep4Value);
}
/** @} */

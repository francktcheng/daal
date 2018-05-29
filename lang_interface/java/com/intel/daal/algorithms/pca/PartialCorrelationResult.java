/* file: PartialCorrelationResult.java */
/*******************************************************************************
* Copyright 2014-2018 Intel Corporation
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

/**
 * @ingroup pca
 * @{
 */
package com.intel.daal.algorithms.pca;

import com.intel.daal.algorithms.ComputeMode;
import com.intel.daal.algorithms.ComputeStep;
import com.intel.daal.algorithms.Precision;
import com.intel.daal.data_management.data.Factory;
import com.intel.daal.data_management.data.NumericTable;
import com.intel.daal.services.DaalContext;

/**
 * <a name="DAAL-CLASS-ALGORITHMS__PCA__PARTIALCORRELATIONRESULT"></a>
 * @brief Provides methods to access partial results obtained with compute() of the %correlation method of PCA algorithm
 *        in the online or distributed processing mode
 */
public final class PartialCorrelationResult extends com.intel.daal.algorithms.PartialResult {
    /** @private */
    static {
        System.loadLibrary("JavaAPI");
    }

    /**
     * Constructs the partial result of the algorithm
     * @param context       Context to manage the partial result of the algorithm
     */
    public PartialCorrelationResult(DaalContext context) {
        super(context);
        this.cObject = cNewPartialCorrelationResult();
    }

    public PartialCorrelationResult(DaalContext context, long cPartialResult) {
        super(context, cPartialResult);
    }

    /**
     * Returns partial result of the PCA algorithm
     * @param  id   Identifier of the PartialCorrelationResult, PartialCorrelationResultID
     * @return Partial result that corresponds to the given identifier
     */
    public NumericTable get(PartialCorrelationResultID id) {
        int idValue = id.getValue();
        if (id != PartialCorrelationResultID.nObservations && id != PartialCorrelationResultID.crossProductCorrelation
                && id != PartialCorrelationResultID.sumCorrelation) {
            throw new IllegalArgumentException("id unsupported");
        }
        return (NumericTable)Factory.instance().createObject(getContext(), cGetPartialCorrelationResultTable(getCObject(), idValue));
    }

    /**
     * Sets partial result of the PCA algorithm
     * @param id                 Identifier of the partial result
     * @param value              Value of the partial result
     */
    public void set(PartialCorrelationResultID id, NumericTable value) {
        int idValue = id.getValue();
        if (id != PartialCorrelationResultID.nObservations && id != PartialCorrelationResultID.crossProductCorrelation
                && id != PartialCorrelationResultID.sumCorrelation) {
            throw new IllegalArgumentException("id unsupported");
        }
        cSetPartialCorrelationResultTable(getCObject(), idValue, value.getCObject());
    }

    private native long cNewPartialCorrelationResult();

    private native long cGetPartialCorrelationResultTable(long cPartialCorrelationResult, int id);

    private native void cSetPartialCorrelationResultTable(long cPartialCorrelationResult, int id, long cNumericTable);
}
/** @} */

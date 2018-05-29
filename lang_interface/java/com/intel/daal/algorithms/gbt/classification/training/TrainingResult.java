/* file: TrainingResult.java */
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
 * @ingroup gbt_classification_training
 * @{
 */
package com.intel.daal.algorithms.gbt.classification.training;

import com.intel.daal.algorithms.ComputeMode;
import com.intel.daal.algorithms.classifier.training.TrainingResultId;
import com.intel.daal.algorithms.gbt.classification.Model;
import com.intel.daal.data_management.data.Factory;
import com.intel.daal.data_management.data.NumericTable;
import com.intel.daal.services.DaalContext;

/**
 * <a name="DAAL-CLASS-ALGORITHMS__GBT__CLASSIFICATION__TRAINING__TRAININGRESULT"></a>
 * @brief Provides methods to access final results obtained with the compute() method
 *        of the gradient boosted trees classification training algorithm in the batch processing mode.
 */
public final class TrainingResult extends com.intel.daal.algorithms.classifier.training.TrainingResult {
    /** @private */
    static {
        System.loadLibrary("JavaAPI");
    }

    public TrainingResult(DaalContext context, long cAlgorithm, ComputeMode cmode) {
        super(context);
        cObject = cGetResult(cAlgorithm, cmode.getValue());
    }

    /**
     * Returns the final result of the gradient boosted trees classification training algorithms
     * @param id   Identifier of the result
     * @return         %Result that corresponds to the given identifier
     */
    public Model get(TrainingResultId id) {
        if (id != TrainingResultId.model) {
            throw new IllegalArgumentException("id unsupported");
        }
        return new Model(getContext(), cGetModel(cObject, TrainingResultId.model.getValue()));
    }

    private native long cGetResult(long algAddress, int cmode);
    private native long cGetModel(long resAddr, int id);
}
/** @} */

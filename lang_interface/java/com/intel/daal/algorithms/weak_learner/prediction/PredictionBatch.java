/* file: PredictionBatch.java */
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
 * @defgroup weak_learner_prediction Prediction
 * @brief Contains classes to make predictions based on the weak learner model
 * @ingroup weak_learner
 * @{
 */
/**
 * @defgroup weak_learner_prediction_batch Batch
 * @ingroup weak_learner_prediction
 * @{
 */
/**
 * @brief Contains classes for making predictions based on the weak learner model
 */
package com.intel.daal.algorithms.weak_learner.prediction;

import com.intel.daal.services.DaalContext;

/**
 * <a name="DAAL-CLASS-ALGORITHM__WEAK_LEARNER__PREDICTION__PREDICTIONBATCH"></a>
 *  @brief Base class for making predictions based on the weak learner model
 *
 *  @par References
 *      - com.intel.daal.algorithms.classifier.prediction.NumericTableInputId class
 *      - com.intel.daal.algorithms.classifier.prediction.ModelInputId class
 *      - com.intel.daal.algorithms.classifier.prediction.PredictionResultId class
 *      - com.intel.daal.algorithms.weak_learner.Model class
 *      - com.intel.daal.algorithms.classifier.prediction.PredictionInput class
 *      - com.intel.daal.algorithms.classifier.prediction.PredictionResult class
 *
 */
public class PredictionBatch extends com.intel.daal.algorithms.classifier.prediction.PredictionBatch {
    /** @private */
    static {
        System.loadLibrary("JavaAPI");
    }

    /**
     * Constructs algorithm for making predictions based on the weak learner model
     * by copying input objects and parameters of another algorithm
     * @param context   Context to manage the weak learner algorithm
     * @param other     An algorithm to be used as the source to initialize the input objects
     *                  and parameters of the algorithm
     */
    public PredictionBatch(DaalContext context, PredictionBatch other) {
        super(context, other);
    }

    /**
     * Constructs algorithm for making predictions based on the weak learner model
     * @param context   Context to manage the weak learner algorithm
     */
    public PredictionBatch(DaalContext context) {
        super(context);
    }

    /**
     * Returns the newly allocated algorithm for making predictions based on the weak learner model
     * with a copy of input objects and parameters of this algorithm
     * @param context   Context to manage the weak learner algorithm
     *
     * @return The newly allocated algorithm
     */
    @Override
    public PredictionBatch clone(DaalContext context) {
        return new PredictionBatch(context, this);
    }
}
/** @} */
/** @} */

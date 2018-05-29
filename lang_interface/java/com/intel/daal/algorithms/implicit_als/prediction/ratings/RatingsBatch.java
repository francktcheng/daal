/* file: RatingsBatch.java */
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
 * @defgroup implicit_als_prediction_batch Batch
 * @ingroup implicit_als_prediction
 * @{
 */
/**
 * @brief Contains classes for computing ratings based on the implicit ALS model
 */
package com.intel.daal.algorithms.implicit_als.prediction.ratings;

import com.intel.daal.algorithms.Precision;
import com.intel.daal.algorithms.implicit_als.Parameter;
import com.intel.daal.services.DaalContext;

/**
 * <a name="DAAL-CLASS-ALGORITHMS__IMPLICIT_ALS__PREDICTION__RATINGS__RATINGSBATCH"></a>
 * @brief Predicts the results of implicit ALS model-based ratings prediction in the batch processing mode
 */
public class RatingsBatch extends com.intel.daal.algorithms.Prediction {
    public RatingsInput    input;        /*!< %Input data */
    public Parameter  parameter;     /*!< Parameters of the algorithm */
    private RatingsMethod  method; /*!< %Prediction method for the algorithm */
    private Precision                 prec; /*!< Precision of intermediate computations */

    /** @private */
    static {
        System.loadLibrary("JavaAPI");
    }

    /**
     * Constructs the implicit ALS ratings prediction algorithm in the batch processing mode
     * by copying input objects and parameters of another implicit ALS ratings prediction algorithm
     * @param context   Context to manage the implicit ALS ratings prediction algorithm
     * @param other     An algorithm to be used as the source to initialize the input objects
     *                  and parameters of the algorithm
     */
    public RatingsBatch(DaalContext context, RatingsBatch other) {
        super(context);
        this.method = other.method;
        prec = other.prec;

        this.cObject = cClone(other.cObject, prec.getValue(), method.getValue());
        input = new RatingsInput(getContext(), cObject, prec, method);
        parameter = new Parameter(getContext(), cInitParameter(this.cObject, prec.getValue(), method.getValue()));
    }

    /**
     * Constructs the implicit ALS ratings prediction algorithm in the batch processing mode
     * @param context   Context to manage the implicit ALS ratings prediction algorithm
     * @param cls       Data type to use in intermediate computations for the implicit ALS algorithm,
     *                  Double.class or Float.class
     * @param method    Implicit ALS computation method, @ref RatingsMethod
     */
    public RatingsBatch(DaalContext context, Class<? extends Number> cls, RatingsMethod method) {
        super(context);

        this.method = method;
        if (method != RatingsMethod.defaultDense && method != RatingsMethod.allUsersAllItems) {
            throw new IllegalArgumentException("method unsupported");
        }

        if (cls == Double.class) {
            prec = Precision.doublePrecision;
        } else if (cls == Float.class) {
            prec = Precision.singlePrecision;
        } else {
            throw new IllegalArgumentException("type unsupported");
        }

        this.cObject = cInit(prec.getValue(), method.getValue());
        input = new RatingsInput(getContext(), cObject, prec, method);
        parameter = new Parameter(getContext(), cInitParameter(this.cObject, prec.getValue(), method.getValue()));
    }

    /**
     * Computes the results of implicit ALS model-based ratings prediction in the batch processing mode
     * @return Results of implicit ALS model-based ratings prediction in the batch processing mode
     */
    @Override
    public RatingsResult compute() {
        super.compute();
        RatingsResult result = new RatingsResult(getContext(), cGetResult(cObject, prec.getValue(), method.getValue()));
        return result;
    }

    /**
    * Registers user-allocated memory for storing the results of implicit ALS
    * model-based ratings prediction in the batch processing mode
    * @param result Structure for storing the results of implicit ALS model-based
    * ratings prediction in the batch processing mode
    */
    public void setResult(RatingsResult result) {
        cSetResult(cObject, prec.getValue(), method.getValue(), result.getCObject());
    }

    /**
     * Returns the newly allocated ALS ratings prediction algorithm in the batch processing mode
     * with a copy of input objects of this ALS ratings prediction algorithm
     * @param context   Context to manage the implicit ALS ratings prediction algorithm
     *
     * @return The newly allocated algorithm
     */
    @Override
    public RatingsBatch clone(DaalContext context) {
        return new RatingsBatch(context, this);
    }

    private native long cInit(int prec, int method);

    private native long cInitParameter(long algAddr, int prec, int method);

    private native long cGetResult(long cObject, int prec, int method);

    private native void cSetResult(long cObject, int prec, int method, long cResult);

    private native long cClone(long algAddr, int prec, int method);
}
/** @} */

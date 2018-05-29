/* file: quality_metric_single_beta_types.cpp */
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

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include "daal.h"
#include "linear_regression/quality_metric/JSingleBetaParameter.h"
#include "linear_regression/quality_metric/JSingleBetaInput.h"
#include "linear_regression/quality_metric/JSingleBetaDataInputId.h"
#include "linear_regression/quality_metric/JSingleBetaModelInputId.h"
#include "linear_regression/quality_metric/JSingleBetaResult.h"
#include "linear_regression/quality_metric/JSingleBetaResultId.h"
#include "linear_regression/quality_metric/JSingleBetaResultDataCollectionId.h"
#include "JComputeMode.h"
#include "common_helpers.h"

USING_COMMON_NAMESPACES();
using namespace daal::algorithms::linear_regression::quality_metric;
using namespace daal::algorithms::linear_regression::quality_metric::single_beta;

#define Rms com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResultId_Rms
#define Variance com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResultId_Variance
#define ZScore com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResultId_ZScore
#define ConfidenceIntervals com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResultId_ConfidenceIntervals
#define InverseOfXtX com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResultId_InverseOfXtX
#define BetaCovariances com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResultDataCollectionId_BetaCovariances
#define ExpectedResponses com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaDataInputId_ExpectedResponses
#define PredictedResponses com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaDataInputId_PredictedResponses
#define ConstModel com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaModelInputId_Model

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaParameter
* Method:    cSetAlpha
* Signature: (JD)V
*/
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaParameter_cSetAlpha
(JNIEnv *, jobject, jlong parAddr, jdouble alpha)
{
    (*(single_beta::Parameter *)parAddr).alpha = alpha;
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaParameter
* Method:    cGetAlpha
* Signature: (J)D
*/
JNIEXPORT jdouble JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaParameter_cGetAlpha
(JNIEnv *, jobject, jlong parAddr)
{
    return(jdouble)(*(single_beta::Parameter *)parAddr).alpha;
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaParameter
* Method:    cSetAccuracyThreshold
* Signature: (JD)V
*/
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaParameter_cSetAccuracyThreshold
(JNIEnv *, jobject, jlong parAddr, jdouble accuracyThreshold)
{
    (*(single_beta::Parameter *)parAddr).accuracyThreshold = accuracyThreshold;
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaParameter
* Method:    cGetAccuracyThreshold
* Signature: (J)D
*/
JNIEXPORT jdouble JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaParameter_cGetAccuracyThreshold
(JNIEnv *, jobject, jlong parAddr)
{
    return(jdouble)(*(single_beta::Parameter *)parAddr).accuracyThreshold;
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResult
* Method:    cSetResultTable
* Signature: (JIJ)V
*/
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaResult_cSetResultTable
(JNIEnv *, jobject, jlong resAddr, jint id, jlong ntAddr)
{
    jniArgument<single_beta::Result>::set<single_beta::ResultId, NumericTable>(resAddr, id, ntAddr);
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResult
* Method:    cGetResultTable
* Signature: (JI)J
*/
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaResult_cGetResultTable
(JNIEnv *, jobject, jlong resAddr, jint id)
{
    if(id == Rms)
        return jniArgument<single_beta::Result>::get<single_beta::ResultId, NumericTable>(resAddr, rms);
    if(id == Variance)
        return jniArgument<single_beta::Result>::get<single_beta::ResultId, NumericTable>(resAddr, variance);
    if(id == ZScore)
        return jniArgument<single_beta::Result>::get<single_beta::ResultId, NumericTable>(resAddr, zScore);
    if(id == ConfidenceIntervals)
        return jniArgument<single_beta::Result>::get<single_beta::ResultId, NumericTable>(resAddr, confidenceIntervals);
    if(id == InverseOfXtX)
        return jniArgument<single_beta::Result>::get<single_beta::ResultId, NumericTable>(resAddr, inverseOfXtX);
    return (jlong)0;
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResult
* Method:    cNewResult
* Signature: ()J
*/
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaResult_cNewResult
(JNIEnv *, jobject)
{
    return jniArgument<single_beta::Result>::newObj();
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResult
* Method:    cGetBetaCovariancesDataCollection
* Signature: (JI)J
*/
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaResult_cGetBetaCovariancesDataCollection
(JNIEnv *, jobject, jlong resAddr, jint id)
{
    return jniArgument<single_beta::Result>::get<single_beta::ResultDataCollectionId, DataCollection>(resAddr, id);
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaResult
* Method:    cSetBetaCovariancesDataCollection
* Signature: (JIJ)V
*/
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaResult_cSetBetaCovariancesDataCollection
(JNIEnv *, jobject, jlong resAddr, jint id, jlong dcAddr)
{
    if(id == BetaCovariances)
        jniArgument<single_beta::Result>::set<single_beta::ResultDataCollectionId, DataCollection>(resAddr, betaCovariances, dcAddr);
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaInput
* Method:    cSetInputTable
* Signature: (JIJ)V
*/
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaInput_cSetInputTable
(JNIEnv *, jobject, jlong resAddr, jint id, jlong ntAddr)
{
    if(id == ExpectedResponses)
        jniInput<single_beta::Input>::set<single_beta::DataInputId, NumericTable>(resAddr, single_beta::expectedResponses, ntAddr);
    else if(id == PredictedResponses)
        jniInput<single_beta::Input>::set<single_beta::DataInputId, NumericTable>(resAddr, single_beta::predictedResponses, ntAddr);
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaInput
* Method:    cGetInputTable
* Signature: (JI)J
*/
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaInput_cGetInputTable
(JNIEnv *, jobject, jlong inputAddr, jint id)
{
    if(id != PredictedResponses && id != ExpectedResponses) return (jlong)0;

    return jniInput<single_beta::Input>::get<single_beta::DataInputId, NumericTable>(inputAddr, id);
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaInput
* Method:    cSetInputModel
* Signature: (JIJ)V
*/
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaInput_cSetInputModel
(JNIEnv *, jobject, jlong inputAddr, jint, jlong ntAddr)
{
    jniInput<single_beta::Input>::
        set<single_beta::ModelInputId, linear_regression::Model>(inputAddr, single_beta::model, ntAddr);
}

/*
* Class:     com_intel_daal_algorithms_linear_regression_quality_metric_SingleBetaInput
* Method:    cGetInputModel
* Signature: (JI)J
*/
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_linear_1regression_quality_1metric_SingleBetaInput_cGetInputModel
(JNIEnv *, jobject, jlong inputAddr, jint id)
{
    if(id == ConstModel)
    {
        return jniInput<single_beta::Input>::get<single_beta::ModelInputId, linear_regression::Model>(inputAddr, single_beta::model);
    }
    return (jlong)0;
}

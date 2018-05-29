/* file: initbatch.cpp */
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

#include <jni.h>
#include "daal.h"
#include "kmeans/init/JInitBatch.h"

#include "common_helpers.h"

USING_COMMON_NAMESPACES()
using namespace daal::algorithms::kmeans::init;

#define MethodsList\
    deterministicDense, randomDense, plusPlusDense, parallelPlusDense,\
    deterministicCSR, randomCSR, plusPlusCSR, parallelPlusCSR
/*
 * Class:     com_intel_daal_algorithms_kmeans_Batch
 * Method:    cInit
 * Signature:(IIJ)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_kmeans_init_InitBatch_cInit
(JNIEnv *, jobject, jint precision, jint method, jlong nClusters)
{
    return jniBatch<kmeans::init::Method, Batch, MethodsList>::newObj(precision, method, nClusters);
}

/*
 * Class:     com_intel_daal_algorithms_kmeans_Batch
 * Method:    cSetResult
 * Signature:(JIIJ)V
 */
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_kmeans_init_InitBatch_cSetResult
(JNIEnv *, jobject, jlong algAddr, jint precision, jint method, jlong resultAddr)
{
    jniBatch<kmeans::init::Method, Batch, MethodsList>::
        setResult<kmeans::init::Result>(precision,method,algAddr,resultAddr);
}

/*
 * Class:     com_intel_daal_algorithms_kmeans_Batch
 * Method:    cGetResult
 * Signature:(JII)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_kmeans_init_InitBatch_cGetResult
(JNIEnv *, jobject, jlong algAddr, jint precision, jint method)
{
    return jniBatch<kmeans::init::Method, Batch, MethodsList>::
        getResult(precision,method,algAddr);
}

/*
 * Class:     com_intel_daal_algorithms_kmeans_Batch
 * Method:    cInitParameter
 * Signature:(JII)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_kmeans_init_InitBatch_cInitParameter
(JNIEnv *, jobject, jlong algAddr, jint prec, jint method)
{
    return jniBatch<kmeans::init::Method, Batch, MethodsList>::getParameter(prec, method, algAddr);
}

/*
 * Class:     com_intel_daal_algorithms_kmeans_Batch
 * Method:    cGetInput
 * Signature:(JII)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_kmeans_init_InitBatch_cGetInput
(JNIEnv *, jobject, jlong algAddr, jint prec, jint method)
{
    return jniBatch<kmeans::init::Method, Batch, MethodsList>::getInput(prec, method, algAddr);
}

/*
 * Class:     com_intel_daal_algorithms_kmeans_Batch
 * Method:    cClone
 * Signature:(JII)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_kmeans_init_InitBatch_cClone
(JNIEnv *, jobject, jlong algAddr, jint prec, jint method)
{
    return jniBatch<kmeans::init::Method, Batch, MethodsList>::getClone(prec, method, algAddr);
}

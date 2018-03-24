/* file: distri.cpp */
/*******************************************************************************
* Copyright 2014-2016 Intel Corporation
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

#include <jni.h>
#include "subgraph_types.i"

#include "subgraph/JDistri.h"
#include "subgraph/JMethod.h"

#include "common_helpers.h"

USING_COMMON_NAMESPACES()
using namespace daal::algorithms::subgraph;

/*
 * Class:     com_intel_daal_algorithms_subgraph_Distri
 * Method:    cInit
 * Signature:(II)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_subgraph_Distri_cInit
(JNIEnv *env, jobject thisObj, jint prec, jint method)
{
    return jniDistributed<step1Local, subgraph::Method, Distri, defaultSC>::newObj(prec, method);
}

// JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_subgraph_Distri_cComputeBottom
// (JNIEnv *env, jobject thisObj, jlong algAddr)
// {
//     // ((subgraph::Distri<step1Local, double, computeBottom>*)algAddr)->computeBottom();
//     ((subgraph::Distri<step1Local, double, computeBottom>*)algAddr)->compute();
// }

/*
 * Class:     Java_com_intel_daal_algorithms_subgraph_Distri
 * Method:    cGetInput
 * Signature: (JII)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_subgraph_Distri_cGetInput
(JNIEnv *env, jobject thisObj, jlong algAddr, jint prec, jint method)
{
    return jniDistributed<step1Local, subgraph::Method, Distri, defaultSC>::getInput(prec, method, algAddr);
}

/*
 * Class:     Java_com_intel_daal_algorithms_subgraph_Distri
 * Method:    cGetInput
 * Signature: (JII)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_subgraph_Distri_cGetParameter
(JNIEnv *env, jobject thisObj, jlong algAddr, jint prec, jint method)
{
    return jniDistributed<step1Local, subgraph::Method, Distri, defaultSC>::getParameter(prec, method, algAddr);
}


/*
 * Class:     Java_com_intel_daal_algorithms_subgraph_Distri
 * Method:    cSetResult
 * Signature: (JIIJ)V
 */
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_subgraph_Distri_cSetResult
(JNIEnv *env, jobject thisObj, jlong algAddr, jint prec, jint method, jlong resultAddr)
{
    jniDistributed<step1Local, subgraph::Method, Distri, defaultSC>::setResult<subgraph::Result>(prec, method, algAddr, resultAddr);
}

/*
 * Class:     Java_com_intel_daal_algorithms_subgraph_Distri
 * Method:    cSetPartialResult
 * Signature: (JIIJ)V
 */
JNIEXPORT void JNICALL Java_com_intel_daal_algorithms_subgraph_Distri_cSetPartialResult
(JNIEnv *env, jobject thisObj, jlong algAddr, jint prec, jint method, jlong presultAddr)
{
    jniDistributed<step1Local, subgraph::Method, Distri, defaultSC>::setPartialResult<subgraph::DistributedPartialResult>(prec, method, algAddr, presultAddr);
}

/*
 * Class:     Java_com_intel_daal_algorithms_subgraph_Distri
 * Method:    cClone
 * Signature: (JII)V
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_algorithms_subgraph_Distri_cClone
(JNIEnv *env, jobject thisObj, jlong algAddr, jint prec, jint method)
{
    return jniDistributed<step1Local, subgraph::Method, Distri, defaultSC>::getClone(prec, method, algAddr);
}
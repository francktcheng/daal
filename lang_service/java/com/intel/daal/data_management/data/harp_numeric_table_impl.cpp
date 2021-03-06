/* file: harp_numeric_table_impl.cpp */
/*******************************************************************************
* Copyright 2014-2017 Intel Corporation
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

#include "JHarpNumericTableImpl.h"
#include "numeric_table.h"
#include "harp_numeric_table.h"
#include "homogen_numeric_table.h"

#include "daal.h"

#include "java_harp_numeric_table.h"
#include "common_defines.i"
#include "common_helpers_functions.h"

using namespace daal::services;
using namespace daal::data_management;

JavaVM* daal::JavaHarpNumericTableBase::globalJavaVM = NULL;
tbb::enumerable_thread_specific<jobject> daal::JavaHarpNumericTableBase::globalDaalContext;

inline static NumericTablePtr *getNIONumericTableObject(JNIEnv *env, jobject thisObj)
{
    // Get a class reference for Java NumericTableFeature
    jclass cls = env->FindClass("com/intel/daal/data_management/data/NumericTable");
    jfieldID objFieldID = env->GetFieldID(cls, "cObject", "J");
    return (NumericTablePtr *)(env->GetLongField(thisObj, objFieldID));
}


/**
 * @brief create a JavaHarpNumeric Table
 *
 * @param env
 * @param thisObj
 * @param p
 * @param n
 * @param layout
 * @param featuresEqual
 * @param tag
 *
 * @return 
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_data_1management_data_HarpNumericTableImpl_cNewJavaHarpNumericTable
(JNIEnv *env, jobject thisObj, jlong p, jlong n, jint layout, jint featuresEqual, jint tag)
{
    using namespace daal;

    JavaVM *jvm;
    // Get pointer to the Java VM interface function table
    jint status = env->GetJavaVM(&jvm);
    if(status != 0)
    {
        /* printf("Error: Couldn't get Java VM, code = %d\n",(int)status);
        fflush(0); */
        return 0;
    }

    // Create C++ object of the class NumericTable
    NumericTable *tbl = 0;
    switch(tag){
        case SERIALIZATION_JAVANIO_HOMOGEN_NT_ID:
            tbl = new daal::JavaHarpNumericTable<SERIALIZATION_JAVANIO_HOMOGEN_NT_ID>((size_t)p,
                                                                                  (size_t)n,
                                                                                  jvm,
                                                                                  thisObj,
                                                                                  (NumericTableIface::StorageLayout)layout,
                                                                                  (DictionaryIface::FeaturesEqual)featuresEqual);
            break;
        case SERIALIZATION_JAVANIO_AOS_NT_ID:
            tbl = new daal::JavaHarpNumericTable<SERIALIZATION_JAVANIO_AOS_NT_ID>((size_t)p,
                                                                              (size_t)n,
                                                                              jvm,
                                                                              thisObj,
                                                                              (NumericTableIface::StorageLayout)layout,
                                                                              (DictionaryIface::FeaturesEqual)featuresEqual);
            break;
        case SERIALIZATION_JAVANIO_SOA_NT_ID:
            tbl = new daal::JavaHarpNumericTable<SERIALIZATION_JAVANIO_SOA_NT_ID>((size_t)p,
                                                                              (size_t)n,
                                                                              jvm,
                                                                              thisObj,
                                                                              (NumericTableIface::StorageLayout)layout,
                                                                              (DictionaryIface::FeaturesEqual)featuresEqual);
            break;
        case SERIALIZATION_JAVANIO_HARP_NT_ID:
            tbl = new daal::JavaHarpNumericTable<SERIALIZATION_JAVANIO_HARP_NT_ID>((size_t)p,
                                                                              (size_t)n,
                                                                              jvm,
                                                                              thisObj,
                                                                              (NumericTableIface::StorageLayout)layout,
                                                                              (DictionaryIface::FeaturesEqual)featuresEqual);
            break;
        case SERIALIZATION_JAVANIO_PACKEDSYMMETRIC_NT_ID:
            tbl = new daal::JavaHarpNumericTable<SERIALIZATION_JAVANIO_PACKEDSYMMETRIC_NT_ID>((size_t)p,
                                                                                          (size_t)n,
                                                                                          jvm,
                                                                                          thisObj,
                                                                                          (NumericTableIface::StorageLayout)layout,
                                                                                          (DictionaryIface::FeaturesEqual)featuresEqual);
            break;
        case SERIALIZATION_JAVANIO_PACKEDTRIANGULAR_NT_ID:
            tbl = new daal::JavaHarpNumericTable<SERIALIZATION_JAVANIO_PACKEDTRIANGULAR_NT_ID>((size_t)p,
                                                                                           (size_t)n,
                                                                                           jvm,
                                                                                           thisObj,
                                                                                           (NumericTableIface::StorageLayout)layout,
                                                                                           (DictionaryIface::FeaturesEqual)featuresEqual);
            break;
        default:
            break;
    }

    if(tbl->getErrors()->size() > 0)
    {
        env->ThrowNew(env->FindClass("java/lang/Exception"), tbl->getErrors()->getDescription());
    }

    return(jlong)(new SerializationIfacePtr(tbl));
}


/**
 * @brief set up a key-idx pair
 *
 * @param env
 * @param thisObj
 * @param key
 * @param idx
 *
 * @return 
 */
JNIEXPORT void JNICALL Java_com_intel_daal_data_1management_data_HarpNumericTableImpl_cSetKeyMap
(JNIEnv *env, jobject thisObj, jlong numTableAddr, jlong key, jlong idx)
{
    HarpNumericTable *nt = static_cast<HarpNumericTable *>(((SerializationIfacePtr *)numTableAddr)->get());
    nt->setKeyIdx(key, idx);

}

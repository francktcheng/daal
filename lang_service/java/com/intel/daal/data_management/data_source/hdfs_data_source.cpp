/* file: hdfs_data_source.cpp */
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

#include "JHDFSDataSource.h"
#include <jni.h>

#include "hdfs_data_source.h"
#include "csv_feature_manager.h"
#include "common_helpers_functions.h"

using namespace daal;
using namespace daal::data_management;

/*
 * Class:     com_intel_daal_data_management_data_source_HDFSDataSource
 * Method:    cInit
 * Signature:(Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_data_1management_data_1source_HDFSDataSource_cInit
(JNIEnv *env, jobject obj, jstring jFileName)
{
    const char *fileName = env->GetStringUTFChars(jFileName, NULL);

    const CsvDataSourceOptions csvOptions = CsvDataSourceOptions::allocateNumericTable |
                                            CsvDataSourceOptions::createDictionaryFromContext;

    DataSource *ds = new HDFSDataSource<CSVFeatureManager>(fileName, csvOptions);
    env->ReleaseStringUTFChars(jFileName, fileName);
    if(!ds->status())
    {
        const services::Status s = ds->status();
        delete ds;
        DAAL_CHECK_THROW(s);
        return (jlong)0;
    }

    return(jlong)(ds);
}

JNIEXPORT void JNICALL Java_com_intel_daal_data_1management_data_1source_HDFSDataSource_cDetachSourceThd
(JNIEnv *env, jobject obj)
{
    // detach jni thread from JVM
    JavaVM *vm;
    jint ret;
    ret = env->GetJavaVM(&vm);
    if (ret) {
        fprintf(stderr, "hdfsThreadDestructor: GetJavaVM failed with "
                "error %d\n", ret);
        env->ExceptionDescribe();
    } else {
        vm->DetachCurrentThread();
    }

}

/*
 * Class:     com_intel_daal_data_management_data_source_HDFSDataSource
 * Method:    cGetFeatureManager
 * Signature:(J)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_daal_data_1management_data_1source_HDFSDataSource_cGetFeatureManager
(JNIEnv *env, jobject obj, jlong ptr)
{
    HDFSDataSource<CSVFeatureManager> *ds = (HDFSDataSource<CSVFeatureManager> *)ptr;
    services::SharedPtr<CSVFeatureManager>* featureManager =
        new services::SharedPtr<CSVFeatureManager>(&(ds->getFeatureManager()), services::EmptyDeleter());

    return (jlong)featureManager;
}

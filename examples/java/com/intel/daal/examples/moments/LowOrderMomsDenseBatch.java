/* file: LowOrderMomsDenseBatch.java */
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

/*
 //  Content:
 //     Java example of computing low order moments in the batch processing mode
 ////////////////////////////////////////////////////////////////////////////////
 */

/**
 * <a name="DAAL-EXAMPLE-JAVA-LOWORDERMOMENTSDENSEBATCH">
 * @example LowOrderMomsDenseBatch.java
 */

package com.intel.daal.examples.moments;

import com.intel.daal.algorithms.low_order_moments.*;
import com.intel.daal.data_management.data.NumericTable;
import com.intel.daal.data_management.data_source.DataSource;
import com.intel.daal.data_management.data_source.FileDataSource;
import com.intel.daal.examples.utils.Service;
import com.intel.daal.services.DaalContext;


class LowOrderMomsDenseBatch {

    /* Input data set parameters */
    private static final String datasetFileName = "../data/batch/covcormoments_dense.csv";

    private static DaalContext context = new DaalContext();

    private static Result result;

    public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
        /* Retrieve the input data from a .csv file */
        FileDataSource dataSource = new FileDataSource(context, datasetFileName,
                DataSource.DictionaryCreationFlag.DoDictionaryFromContext,
                DataSource.NumericTableAllocationFlag.DoAllocateNumericTable);
        dataSource.loadDataBlock();

        /* Create algorithm objects to compute low order moments using the default method */
        Batch algorithm = new Batch(context, Float.class, Method.defaultDense);

        /* Set input objects for the algorithm */
        NumericTable input = dataSource.getNumericTable();
        algorithm.input.set(InputId.data, input);

        /* Compute low order moments */
        result = algorithm.compute();

        printResults();

        context.dispose();
    }

    private static void printResults() {
        Service.printNumericTable("Minimum:", result.get(ResultId.minimum));
        Service.printNumericTable("Maximum:", result.get(ResultId.maximum));
        Service.printNumericTable("Sum:", result.get(ResultId.sum));
        Service.printNumericTable("Sum of squares:", result.get(ResultId.sumSquares));
        Service.printNumericTable("Sum of squared difference from the means:", result.get(ResultId.sumSquaresCentered));
        Service.printNumericTable("Mean:", result.get(ResultId.mean));
        Service.printNumericTable("Second order raw moment:", result.get(ResultId.secondOrderRawMoment));
        Service.printNumericTable("Variance:", result.get(ResultId.variance));
        Service.printNumericTable("Standart deviation:", result.get(ResultId.standardDeviation));
        Service.printNumericTable("Variation:", result.get(ResultId.variation));
    }
}

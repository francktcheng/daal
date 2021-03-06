/* file: HarpNumericTableImpl.java */
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

/**
 * @ingroup numeric_tables
 * @{
 */
package com.intel.daal.data_management.data;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

import com.intel.daal.services.DaalContext;
import com.intel.daal.SerializationTag;

/**
 * <a name="DAAL-CLASS-DATA_MANAGEMENT__DATA__HARPNUMERICTABLEIMPL"></a>
 * @brief Class that provides methods to access data that is stored as a Structure
 *        Of Arrays(Harp), where each contiguous array represents values
 *        corresponding to a specific feature
 */
public class HarpNumericTableImpl extends NumericTableImpl {
    /** @private */
    static {
        System.loadLibrary("JavaAPI");
    }

    /**
     * Constructs a Structure Of Arrays(Harp) numeric table
     *
     * @param context   Context to manage created numeric table
     * @param nFeatures Number of features in numeric table
     * @param nVectors  Number of feature vectors in numeric table
     */
    public HarpNumericTableImpl(DaalContext context, long nFeatures, long nVectors) {
        super(context);

        dataAllocatedInJava = true;

        this.cObject = newJavaHarpNumericTable(nFeatures, nVectors, NumericTable.StorageLayout.soa, DataDictionary.FeaturesEqual.notEqual,
                                           SerializationTag.SERIALIZATION_JAVANIO_HARP_NT_ID);

        arrays = new Vector<Object>();
        arrays.setSize((int) nFeatures);
        dict = new DataDictionary(getContext(), nFeatures, cGetCDataDictionary(cObject));
    }

    private long newJavaHarpNumericTable(long nColumns, long nRows, NumericTable.StorageLayout layout, DataDictionary.FeaturesEqual featuresEqual, SerializationTag tag) {
        nJavaFeatures = nColumns;
        nJavaVectors = nRows;
        return cNewJavaHarpNumericTable(nColumns, nRows, layout.ordinal(), featuresEqual.ordinal(), tag.getValue());
    }


    /**
     * Sets array of doubles of the feature to the table
     *
     * @param arr Array of values of the feature
     * @param idx Index of the feature
     */
    public void setArray(double[] arr, long idx, long key) {
        dict.setFeature(Double.class, (int) idx);
        arrays.set((int) idx, arr);
        cSetKeyMap(this.cObject, key, idx);
    }

    /**
     * updates array of doubles of the feature to the table
     *
     * @param arr Array of values of the feature
     * @param idx Index of the feature
     */
    public void updateArray(double[] arr, long idx, long key) {
        arrays.set((int) idx, arr);
        cSetKeyMap(this.cObject, key, idx);
    }

    /**
     * Sets array of floats of the feature to the table
     *
     * @param arr Array of values of the feature
     * @param idx Index of the feature
     */
    public void setArray(float[] arr, long idx, long key) {
        dict.setFeature(Float.class, (int) idx);
        arrays.set((int) idx, arr);
        cSetKeyMap(this.cObject, key, idx);
    }

    /**
     * updates array of floats of the feature to the table
     *
     * @param arr Array of values of the feature
     * @param idx Index of the feature
     */
    public void updateArray(float[] arr, long idx, long key) {
        arrays.set((int) idx, arr);
        cSetKeyMap(this.cObject, key, idx);
    }

    /**
     * Sets array of longs of the feature to the table
     *
     * @param arr Array of values of the feature
     * @param idx Index of the feature
     */
    public void setArray(long[] arr, long idx, long key) {
        dict.setFeature(Long.class, (int) idx);
        arrays.set((int) idx, arr);
        cSetKeyMap(this.cObject, key, idx);
    }

    /**
     * updates array of longs of the feature to the table
     *
     * @param arr Array of values of the feature
     * @param idx Index of the feature
     */
    public void updateArray(long[] arr, long idx, long key) {
        arrays.set((int) idx, arr);
        cSetKeyMap(this.cObject, key, idx);
    }

    /**
     * Sets array of integers of the feature to the table
     *
     * @param arr Array of values of the feature
     * @param idx Index of the feature
     */
    public void setArray(int[] arr, long idx, long key) {
        dict.setFeature(Integer.class, (int) idx);
        arrays.set((int) idx, arr);
        cSetKeyMap(this.cObject, key, idx);
    }

    /**
     * updates array of ints of the feature to the table
     *
     * @param arr Array of values of the feature
     * @param idx Index of the feature
     */
    public void updateArray(int[] arr, long idx, long key) {
        arrays.set((int) idx, arr);
        cSetKeyMap(this.cObject, key, idx);
    }

    /**
     * Returns the data dictionary
     *
     * @return Data dictionary
     */
    public DataDictionary getDictionary() {
        return dict;
    }

    /**
     * Sets number of columns in the table
     *
     * @param nCol Number of columns
     * @return Execution status
     */
    @Override
    public void setNumberOfColumns(long nCol) {
        dict.setNumberOfFeatures(nCol);
        super.setNumberOfColumns(nCol);
    }

    protected Buffer getTBlock(DataFeatureUtils.InternalNumType numType, long vectorIndex, long vectorNum, Buffer buf) {
        int nColumns = (int) (getNumberOfColumns());

        for (int i = 0; i < nColumns; i++) {
            DataFeature df = dict.getFeature(i);
            DataFeatureUtils.VectorUpCastIface vectorUpCast = DataFeatureUtils.VectorUpCast.getCast(df.type, DataFeatureUtils.getClassByType(numType));
            vectorUpCast.upCastWithBufferStride((int) vectorNum, (int) vectorIndex, i, nColumns, arrays.get(i), buf);
        }

        return buf;
    }

    /** @copydoc NumericTable::getBlockOfRows(long,long,DoubleBuffer) */
    @Override
    public DoubleBuffer getBlockOfRows(long vectorIndex, long vectorNum, DoubleBuffer buf) {
        return (DoubleBuffer) getTBlock(DataFeatureUtils.InternalNumType.DAAL_DOUBLE, vectorIndex, vectorNum, buf);
    }

    /** @copydoc NumericTable::getBlockOfRows(long,long,FloatBuffer) */
    @Override
    public FloatBuffer getBlockOfRows(long vectorIndex, long vectorNum, FloatBuffer buf) {
        return (FloatBuffer) getTBlock(DataFeatureUtils.InternalNumType.DAAL_SINGLE, vectorIndex, vectorNum, buf);
    }

    /** @copydoc NumericTable::getBlockOfRows(long,long,IntBuffer) */
    @Override
    public IntBuffer getBlockOfRows(long vectorIndex, long vectorNum, IntBuffer buf) {
        return (IntBuffer) getTBlock(DataFeatureUtils.InternalNumType.DAAL_INT32, vectorIndex, vectorNum, buf);
    }

    /** @copydoc NumericTable::getBlockOfColumnValues(long,long,long,DoubleBuffer) */
    @Override
    public DoubleBuffer getBlockOfColumnValues(long featureIndex, long vectorIndex, long vectorNum, DoubleBuffer buf) {
        DataFeature df = dict.getFeature((int) featureIndex);

        // Copies data to the NIO buffer
        DataFeatureUtils.VectorUpCastIface vectorUpCast = DataFeatureUtils.VectorUpCast.getCast(df.type, double.class);
        vectorUpCast.upCast((int) vectorNum, (int) vectorIndex, arrays.get((int) featureIndex), buf);
        return buf;
    }

    /** @copydoc NumericTable::getBlockOfColumnValues(long,long,long,FloatBuffer) */
    @Override
    public FloatBuffer getBlockOfColumnValues(long featureIndex, long vectorIndex, long vectorNum, FloatBuffer buf) {
        DataFeature df = dict.getFeature((int) featureIndex);

        // Copies data to the NIO buffer
        DataFeatureUtils.VectorUpCastIface vectorUpCast = DataFeatureUtils.VectorUpCast.getCast(df.type, float.class);
        vectorUpCast.upCast((int) vectorNum, (int) vectorIndex, arrays.get((int) featureIndex), buf);
        return buf;
    }

    /** @copydoc NumericTable::getBlockOfColumnValues(long,long,long,IntBuffer) */
    @Override
    public IntBuffer getBlockOfColumnValues(long featureIndex, long vectorIndex, long vectorNum, IntBuffer buf) {
        DataFeature df = dict.getFeature((int) featureIndex);

        // Copies data to the NIO buffer
        DataFeatureUtils.VectorUpCastIface vectorUpCast = DataFeatureUtils.VectorUpCast.getCast(df.type, int.class);
        vectorUpCast.upCast((int) vectorNum, (int) vectorIndex, arrays.get((int) featureIndex), buf);
        return buf;
    }

    protected void releaseTBlock(DataFeatureUtils.InternalNumType numType, long vectorIndex, long vectorNum,
            Buffer buf) {
        int nColumns = (int) (getNumberOfColumns());

        for (int i = 0; i < nColumns; i++) {
            DataFeature df = dict.getFeature(i);
            DataFeatureUtils.VectorDownCastIface vectorDownCast = DataFeatureUtils.VectorDownCast.getCast(DataFeatureUtils.getClassByType(numType), df.type);
            vectorDownCast.downCastWithBufferStride((int) vectorNum, (int) vectorIndex, i, nColumns, buf, arrays.get(i));
        }
    }

    /** @copydoc NumericTable::releaseBlockOfRows(long,long,DoubleBuffer) */
    @Override
    public void releaseBlockOfRows(long vectorIndex, long vectorNum, DoubleBuffer buf) {
        releaseTBlock(DataFeatureUtils.InternalNumType.DAAL_DOUBLE, vectorIndex, vectorNum, buf);
    }

    /** @copydoc NumericTable::releaseBlockOfRows(long,long,FloatBuffer) */
    @Override
    public void releaseBlockOfRows(long vectorIndex, long vectorNum, FloatBuffer buf) {
        releaseTBlock(DataFeatureUtils.InternalNumType.DAAL_SINGLE, vectorIndex, vectorNum, buf);
    }

    /** @copydoc NumericTable::releaseBlockOfRows(long,long,IntBuffer) */
    @Override
    public void releaseBlockOfRows(long vectorIndex, long vectorNum, IntBuffer buf) {
        releaseTBlock(DataFeatureUtils.InternalNumType.DAAL_INT32, vectorIndex, vectorNum, buf);
    }

    /** @copydoc NumericTable::releaseBlockOfColumnValues(long,long,long,DoubleBuffer) */
    @Override
    public void releaseBlockOfColumnValues(long featureIndex, long vectorIndex, long vectorNum, DoubleBuffer buf) {
        DataFeature df = dict.getFeature((int) featureIndex);

        // Copies results from the NIO buffer
        DataFeatureUtils.VectorDownCastIface vectorDownCast = DataFeatureUtils.VectorDownCast.getCast(double.class, df.type);
        vectorDownCast.downCast((int) vectorNum, (int) vectorIndex, buf, arrays.get((int) featureIndex));
    }

    /** @copydoc NumericTable::releaseBlockOfColumnValues(long,long,long,FloatBuffer) */
    @Override
    public void releaseBlockOfColumnValues(long featureIndex, long vectorIndex, long vectorNum, FloatBuffer buf) {
        DataFeature df = dict.getFeature((int) featureIndex);

        // Copies results from the NIO buffer
        DataFeatureUtils.VectorDownCastIface vectorDownCast = DataFeatureUtils.VectorDownCast.getCast(float.class, df.type);
        vectorDownCast.downCast((int) vectorNum, (int) vectorIndex, buf, arrays.get((int) featureIndex));
    }

    /** @copydoc NumericTable::releaseBlockOfColumnValues(long,long,long,IntBuffer) */
    @Override
    public void releaseBlockOfColumnValues(long featureIndex, long vectorIndex, long vectorNum, IntBuffer buf) {
        DataFeature df = dict.getFeature((int) featureIndex);

        // Copies results from the NIO buffer
        DataFeatureUtils.VectorDownCastIface vectorDownCast = DataFeatureUtils.VectorDownCast.getCast(int.class, df.type);
        vectorDownCast.downCast((int) vectorNum, (int) vectorIndex, buf, arrays.get((int) featureIndex));
    }

    @Override
    protected void onUnpack(DaalContext context) {
        if (this.cObject == 0) {
            this.cObject = newJavaNumericTable(nJavaFeatures, nJavaVectors, NumericTable.StorageLayout.soa, DataDictionary.FeaturesEqual.notEqual,
                                               SerializationTag.SERIALIZATION_JAVANIO_HARP_NT_ID);
        }
    }

    @Override
    public void freeDataMemory() {
        arrays = null;
    }

    // member field
    protected Vector<Object> arrays;
    // create a Java_harp_numeric_table at backend
    private native long cNewJavaHarpNumericTable(long nColumns, long nRows, int layout, int featuresEqual, int tag);
    // add a new entry to key hashmap
    private native void cSetKeyMap(long cObject, long key, long idx);
}
/** @} */

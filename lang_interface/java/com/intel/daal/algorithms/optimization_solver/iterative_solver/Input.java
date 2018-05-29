/* file: Input.java */
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
 * @ingroup iterative_solver
 * @{
 */
package com.intel.daal.algorithms.optimization_solver.iterative_solver;

import com.intel.daal.algorithms.ComputeMode;
import com.intel.daal.algorithms.ComputeStep;
import com.intel.daal.algorithms.Precision;
import com.intel.daal.algorithms.OptionalArgument;
import com.intel.daal.data_management.data.Factory;
import com.intel.daal.data_management.data.NumericTable;
import com.intel.daal.services.DaalContext;

/**
 * <a name="DAAL-CLASS-ALGORITHMS__OPTIMIZATION_SOLVER__ITERATIVE_SOLVER__INPUT"></a>
 * @brief %Input objects for the iterative algorithm
 */
public class Input extends com.intel.daal.algorithms.Input {
    /** @private */
    static {
        System.loadLibrary("JavaAPI");
    }

    /**
     * Constructs the input for the iterative algorithm
     * @param context       Context to manage the input for the iterative algorithm
     */
    public Input(DaalContext context) {
        super(context);
    }

    /**
     * Constructs the input for the iterative algorithm
     * @param context       Context to manage the iterative algorithm
     * @param cInput        Pointer to C++ implementation of the input
     */
    public Input(DaalContext context, long cInput) {
        super(context, cInput);
    }

    /**
     * Sets an input object for the iterative algorithm
     * @param id    Identifier of the input object
     * @param val   The input object
     */
    public void set(InputId id, NumericTable val) {
        if (id != InputId.inputArgument) {
            throw new IllegalArgumentException("id unsupported");
        }
        cSetInput(cObject, id.getValue(), val.getCObject());
    }

    /**
     * Returns an input object for the iterative algorithm
     * @param id Identifier of the input object
     * @return   %Input object that corresponds to the given identifier
     */
    public NumericTable get(InputId id) {
        if (id != InputId.inputArgument) {
            throw new IllegalArgumentException("id unsupported");
        }
        return (NumericTable)Factory.instance().createObject(getContext(), cGetInput(cObject, id.getValue()));
    }

    /**
     * Sets an optional input object for the iterative algorithm
     * @param id    Identifier of the optional input object
     * @param val   The optional input object
     */
    public void set(OptionalInputId id, OptionalArgument val) {
        if (id != OptionalInputId.optionalArgument) {
            throw new IllegalArgumentException("id unsupported");
        }
        cSetOptionalInput(cObject, id.getValue(), val.getCObject());
    }

    /**
     * Returns an optional input object for the iterative algorithm
     * @param id Identifier of the optional input object
     * @return   %Input object that corresponds to the given identifier
     */
    public OptionalArgument get(OptionalInputId id) {
        if (id != OptionalInputId.optionalArgument) {
            throw new IllegalArgumentException("id unsupported");
        }
        return (OptionalArgument)Factory.instance().createObject(getContext(), cGetOptionalInput(cObject, id.getValue()));
    }

    protected native void cSetInput(long cInput, int id, long ntAddr);
    protected native long cGetInput(long cInput, int id);
    protected native void cSetOptionalInput(long cInput, int id, long ntAddr);
    protected native long cGetOptionalInput(long cInput, int id);
}
/** @} */

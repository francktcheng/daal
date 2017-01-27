/* file: minmax_types.h */
/*******************************************************************************
* Copyright 2014-2017 Intel Corporation
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

/*
//++
//  Definition of common types of min-max normalization.
//--
*/

#ifndef __MINMAX_TYPES_H__
#define __MINMAX_TYPES_H__

#include "algorithms/algorithm.h"
#include "data_management/data/numeric_table.h"
#include "data_management/data/homogen_numeric_table.h"
#include "algorithms/moments/low_order_moments_batch.h"
#include "services/daal_defines.h"

namespace daal
{
namespace algorithms
{
/**
 * @defgroup normalization Normalization
 * \copydoc daal::algorithms::normalization
 * @ingroup analysis
 * @{
 */
/**
 * \brief Contains classes to run the min-max normalization algorithms
 */
namespace normalization
{
/**
 * @defgroup minmax Min-max
 * \copydoc daal::algorithms::normalization::minmax
 * @ingroup normalization
 * @{
 */
/**
* \brief Contains classes for computing the min-max normalization
*/
namespace minmax
{
/**
 * <a name="DAAL-ENUM-ALGORITHMS__NORMALIZATION__MINMAX__METHOD"></a>
 * Available methods for min-max normalization computation
 * @ingroup minmax
 */
enum Method
{
    defaultDense = 0,      /*!< Default: performance-oriented method. Works with all types of numeric tables */
};

/**
 * <a name="DAAL-ENUM-ALGORITHMS__NORMALIZATION__MINMAX__INPUTID"></a>
 * Available identifiers of input objects for the min-max normalization algorithm
 * @ingroup minmax
 */
enum InputId
{
    data = 0            /*!< %Input data table */
};

/**
 * <a name="DAAL-ENUM-ALGORITHMS__NORMALIZATION__MINMAX__RESULTID"></a>
 * Available identifiers of results of the min-max normalization algorithm
 * @ingroup minmax
 */
enum ResultId
{
    normalizedData = 0       /*!< min-max normalization results */
};

/**
 * \brief Contains version 1.0 of Intel(R) Data Analytics Acceleration Library (Intel(R) DAAL) interface.
 */
namespace interface1
{

/**
* <a name="DAAL-CLASS-ALGORITHMS__NORMALIZATION__MINMAX__PARAMETERBASE"></a>
* \brief Base class that specifies the parameters of the algorithm in the batch computing mode
*/
struct DAAL_EXPORT ParameterBase : public daal::algorithms::Parameter
{
public:
    /** Constructs min-max normalization parameters */
    ParameterBase(double lowerBound, double upperBound, const services::SharedPtr<low_order_moments::BatchIface> &momentsForParameterBase);

    double lowerBound;  /*!< The lower bound of the features value will be obtained during normalization. */
    double upperBound;  /*!< The upper bound of the features value will be obtained during normalization. */

    services::SharedPtr<low_order_moments::BatchIface> moments; /*!< Pointer to the algorithm that computes the low order moments */

    /**
     * Check the correctness of the %ParameterBase object
     */
    virtual void check() const DAAL_C11_OVERRIDE;
};

/**
* <a name="DAAL-CLASS-ALGORITHMS__NORMALIZATION__MINMAX__PARAMETER"></a>
* \brief Class that specifies the parameters of the algorithm in the batch computing mode
*/
template<typename algorithmFPType>
struct DAAL_EXPORT Parameter : public ParameterBase
{
public:
    /** Constructs min-max normalization parameters with default low order algorithm */
    Parameter(double lowerBound = 0.0, double upperBound = 1.0);

    /** Constructs min-max normalization parameters */
    Parameter(double lowerBound, double upperBound, const services::SharedPtr<low_order_moments::BatchIface> &momentsForParameter);
};

/**
 * <a name="DAAL-CLASS-ALGORITHMS__NORMALIZATION__MINMAX__INPUT"></a>
 * \brief %Input objects for the min-max normalization algorithm
 */
class DAAL_EXPORT Input : public daal::algorithms::Input
{
public:
    /** Default constructor */
    Input();

    virtual ~Input() {}

    /**
     * Returns an input object for the min-max normalization algorithm
     * \param[in] id    Identifier of the %input object
     * \return          %Input object that corresponds to the given identifier
     */
    data_management::NumericTablePtr get(InputId id) const;

    /**
     * Sets the input object of the min-max normalization algorithm
     * \param[in] id    Identifier of the %input object
     * \param[in] ptr   Pointer to the input object
     */
    void set(InputId id, const data_management::NumericTablePtr &ptr);

    /**
     * Check the correctness of the %Input object
     * \param[in] par       Algorithm parameter
     * \param[in] method    Algorithm computation method
     */
    void check(const daal::algorithms::Parameter *par, int method) const DAAL_C11_OVERRIDE;
};

/**
 * <a name="DAAL-CLASS-ALGORITHMS__NORMALIZATION__MINMAX__RESULT"></a>
 * \brief Provides methods to access final results obtained with the compute() method of the
 *        min-max normalization algorithm in the batch processing mode
 */
class DAAL_EXPORT Result : public daal::algorithms::Result
{
public:
    DECLARE_SERIALIZABLE();
    Result();

    virtual ~Result() {};

    /**
     * Allocates memory to store final results of the min-max normalization algorithms
     * \param[in] input     Input objects for the min-max normalization algorithm
     * \param[in] method    Algorithm computation method
     */
    template <typename algorithmFPType>
    DAAL_EXPORT void allocate(const daal::algorithms::Input *input, int method);

    /**
     * Returns the final result of the min-max normalization algorithm
     * \param[in] id   Identifier of the final result, daal::algorithms::normalization::minmax::ResultId
     * \return         Final result that corresponds to the given identifier
     */
    data_management::NumericTablePtr get(ResultId id) const;

    /**
     * Sets the Result object of the min-max normalization algorithm
     * \param[in] id        Identifier of the Result object
     * \param[in] value     Pointer to the Result object
     */
    void set(ResultId id, const data_management::NumericTablePtr &value);

    /**
     * Checks the correctness of the Result object
     * \param[in] in     Pointer to the input object
     * \param[in] par    Pointer to the parameter object
     * \param[in] method Algorithm computation method
     */
    void check(const daal::algorithms::Input *in, const daal::algorithms::Parameter *par, int method) const DAAL_C11_OVERRIDE;

protected:
    /** \private */
    template<typename Archive, bool onDeserialize>
    void serialImpl(Archive *arch)
    {
        daal::algorithms::Result::serialImpl<Archive, onDeserialize>(arch);
    }

    void serializeImpl(data_management::InputDataArchive  *arch) DAAL_C11_OVERRIDE
    {serialImpl<data_management::InputDataArchive, false>(arch);}

    void deserializeImpl(data_management::OutputDataArchive *arch) DAAL_C11_OVERRIDE
    {serialImpl<data_management::OutputDataArchive, true>(arch);}
};
/** @} */
/** @} */
} // namespace interface1
using interface1::ParameterBase;
using interface1::Parameter;
using interface1::Input;
using interface1::Result;

} // namespace minmax
} // namespace normalization
} // namespace algorithms
} // namespace daal
#endif

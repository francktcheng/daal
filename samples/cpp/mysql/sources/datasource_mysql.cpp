/* file: datasource_mysql.cpp */
/*******************************************************************************
* Copyright 2017-2018 Intel Corporation
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
! Content:
!
! C++ sample of a MySQL data source.
!
! 1) Connect to the database and create a table there.
!
! 2) Create a dictionary from the data table and read the data from it using
!    the ODBCDataSource functionality. Print the data from the table.
!
! 3) Delete the table from the database and disconnect.
!
!******************************************************************************/

#include <ctime>
#include <cstdlib>
#include <sstream>
#include <iostream>
#include <stdexcept>

#include "utils.h"
#include "odbc_wrapper.h"

#include "daal.h"
#include "data_management/data_source/odbc_data_source.h"

using namespace daal::data_management;

int main(int argc, char const *argv[])
{
    /*
     * This sample demonstrates how to connect to MySQL server using connection string and
     * perform basic data selection and loading with ODBCDataSource component.
     */

    std::string connectionString;

    if (argc > 1)
    { connectionString = argv[1]; }

    if (utils::trim(connectionString).empty())
    { utils::printHelp(); return 0; }

    /* Example of user's connection string: */
    // connectionString = "DRIVER=MySQL;"
    //                    "SERVER=<host_name>;"
    //                    "USER=<user_name>;"
    //                    "PASSWORD=<password>;"
    //                    "DATABASE=<data_base_name>";

    /* Establish connection to MySQL server */
    odbc_wrapper::Connection connection(connectionString);

    /* Create a table and insert a few rows into it */
    const std::string tableName = utils::generateTableName(connection.id());
    connection.execute("CREATE TABLE ? (DoubleColumn1 double, DoubleColumn2 double)", tableName);
    connection.execute("INSERT INTO ? VALUES (1.23, 4.56), (7.89, 1.56), (2.62, 9.35)", tableName);

    /* Crate ODBC Data Source via connection string */
    const ODBCDataSourceOptions options = ODBCDataSourceOptions::allocateNumericTable |
                                          ODBCDataSourceOptions::createDictionaryFromContext;
    ODBCDataSource<SQLFeatureManager> ds(connectionString, options);

    /* Execute SQL query, you can execute arbitrary query supported by your DB */
    ds.executeQuery("SELECT * FROM " + tableName);

    /* Cause loading data from the table */
    ds.loadDataBlock();

    /* Print loaded numeric table */
    utils::printNumericTable(ds.getNumericTable(), "The loaded table:");

    /* Remove created table */
    connection.execute("DROP TABLE ?", tableName);

    return 0;
}

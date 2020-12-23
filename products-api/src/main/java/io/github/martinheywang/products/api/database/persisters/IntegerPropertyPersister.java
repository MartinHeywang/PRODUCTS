/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.database.persisters;

import java.sql.SQLException;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntegerPropertyPersister extends BaseDataType {

    private static final IntegerPropertyPersister instance = new IntegerPropertyPersister();

    private IntegerPropertyPersister() {
        super(SqlType.INTEGER, new Class<?>[] { IntegerProperty.class });
    }

    @Override
    public IntegerProperty parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        // Default value = zero
        return new SimpleIntegerProperty(0);
    }

    @Override
    public Integer resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getInt(columnPos);
    }

    @Override
    public IntegerProperty sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return new SimpleIntegerProperty((int) sqlArg);
    }

    @Override
    public Integer javaToSqlArg(FieldType type, Object obj){
        return ((IntegerProperty) obj).get();
    }

    /**
     * Returns the singleton.
     * 
     * @return the singleton.
     */
    public static IntegerPropertyPersister getInstance() {
        return instance;
    }

}
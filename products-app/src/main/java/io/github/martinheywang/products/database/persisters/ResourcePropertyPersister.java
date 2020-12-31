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

import io.github.martinheywang.products.api.model.properties.SimpleResourceProperty;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.ResourceManager;

/**
 * Singleton class that allows ORMLite to persist resources.
 */
public final class ResourcePropertyPersister extends BaseDataType {

    private static final ResourcePropertyPersister instance = new ResourcePropertyPersister();

    private ResourcePropertyPersister() {
        super(SqlType.STRING, new Class<?>[] { SimpleResourceProperty.class });
    }

    @Override
    public SimpleResourceProperty parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        return new SimpleResourceProperty(ResourceManager.valueOf(defaultStr));
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    @Override
    public SimpleResourceProperty sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return new SimpleResourceProperty(ResourceManager.valueOf((String) sqlArg));

    }

    @Override
    public String javaToSqlArg(FieldType type, Object obj){
        Resource res = (Resource) obj;

        // Results in something like 'com.example.MyEnum.FIELD_NAME'
        return res.getClass().getCanonicalName() + "." + res.toString();
    }

    /**
     * Returns the singleton.
     * 
     * @return the singleton.
     */
    public static ResourcePropertyPersister getInstance() {
        return instance;
    }

}

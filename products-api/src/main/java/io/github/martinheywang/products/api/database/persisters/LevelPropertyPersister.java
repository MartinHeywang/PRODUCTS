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

import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.model.properties.SimpleLevelProperty;

/**
 * Singleton class that allows ORMLite to persist simple big integer properties.
 */
public class LevelPropertyPersister extends BaseDataType {

    private static final LevelPropertyPersister instance = new LevelPropertyPersister();

    private LevelPropertyPersister() {
        super(SqlType.STRING, new Class<?>[] { SimpleLevelProperty.class });
    }

    @Override
    public SimpleLevelProperty parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        return new SimpleLevelProperty(Level.LEVEL_1);
    }

    @Override
    public String resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    @Override
    public SimpleLevelProperty sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return new SimpleLevelProperty(Level.valueOf((String) sqlArg));
    }

    @Override
	public String javaToSqlArg(FieldType fieldType, Object obj) {
		SimpleLevelProperty prop = (SimpleLevelProperty) obj;
		return prop.get().toString();
	}

    /**
     * Returns the singleton.
     * 
     * @return the singleton.
     */
    public static LevelPropertyPersister getInstance() {
        return instance;
    }

}

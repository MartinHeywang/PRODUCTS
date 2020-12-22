package io.github.martinheywang.products.model.database;

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
        // BigInteger.toString() returns the number as a text
        // this text can be parsed when fetching back the value
        return ((IntegerProperty) results.getObject(columnPos)).get();
    }

    @Override
    public IntegerProperty sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        // We can affirm that the sql arg in the database an integer
        // This is the defined type in the constructor
        return new SimpleIntegerProperty((int) sqlArg);
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
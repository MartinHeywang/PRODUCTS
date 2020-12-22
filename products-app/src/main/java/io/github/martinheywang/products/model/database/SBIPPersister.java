package io.github.martinheywang.products.model.database;

import java.math.BigInteger;
import java.sql.SQLException;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

import io.github.martinheywang.products.api.model.properties.SimpleBigIntegerProperty;

/**
 * Singleton class that allows ORMLite to persist simple big integer properties.
 */
public class SBIPPersister extends BaseDataType {

    private static final SBIPPersister instance = new SBIPPersister();

    private SBIPPersister() {
        super(SqlType.STRING, new Class<?>[] { SimpleBigIntegerProperty.class });
    }

    @Override
    public SimpleBigIntegerProperty parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        // Default value = zero
        return new SimpleBigIntegerProperty(BigInteger.ZERO);
    }

    @Override
    public String resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        // BigInteger.toString() returns the number as a text
        // this text can be parsed when fetching back the value
        return results.getObject(columnPos).toString();
    }

    @Override
    public SimpleBigIntegerProperty sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        // We can affirm that the sql arg in the database is string
        // This is the defined type in the constructor
        return new SimpleBigIntegerProperty(new BigInteger((String) sqlArg));
    }

    /**
     * Returns the singleton.
     * 
     * @return the singleton.
     */
    public static SBIPPersister getInstance() {
        return instance;
    }

}

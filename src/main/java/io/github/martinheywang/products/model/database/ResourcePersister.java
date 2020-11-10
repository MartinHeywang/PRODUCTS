package io.github.martinheywang.products.model.database;

import java.sql.SQLException;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.controller.ResourceManager;
import io.github.martinheywang.products.model.resource.DefaultResource;

/**
 * Singleton class that allows ORMLite to persist resources.
 */
public class ResourcePersister extends BaseDataType {

    private static final ResourcePersister instance = new ResourcePersister();

    private ResourcePersister() {
	super(SqlType.STRING, new Class<?>[] { Resource.class });
    }

    @Override
    public Resource parseDefaultString(FieldType fieldType,
	    String defaultStr)
		    throws SQLException {
	return DefaultResource.NONE;
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results,
	    int columnPos) throws SQLException {
	return results.getObject(columnPos);
    }

    @Override
    public Resource sqlArgToJava(FieldType fieldType,
	    Object sqlArg,
	    int columnPos) {
	return ResourceManager.valueOf((String) sqlArg);

    }

    /**
     * Returns the singleton.
     * 
     * @return the singleton.
     */
    public static ResourcePersister getInstance() {
	return instance;
    }

}

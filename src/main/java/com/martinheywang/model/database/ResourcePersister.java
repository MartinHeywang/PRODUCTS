package com.martinheywang.model.database;

import java.sql.SQLException;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import com.martinheywang.model.resources.DefaultResource;
import com.martinheywang.model.resources.Resource;

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
		System.out.println(fieldType.getField().toString());
		return results.getObject(columnPos);
	}

	@Override
	public Resource sqlArgToJava(FieldType fieldType,
			Object sqlArg,
			int columnPos) {
		return Resource.valueOf((String) sqlArg);

	}

	public static ResourcePersister getInstance() {
		return instance;
	}

}

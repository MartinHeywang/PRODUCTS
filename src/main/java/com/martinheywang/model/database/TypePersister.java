package com.martinheywang.model.database;

import java.sql.SQLException;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.model.types.Type;

public class TypePersister extends BaseDataType {

	private static final ResourcePersister instance = new ResourcePersister();

	public TypePersister() {
		super(SqlType.STRING, new Class<?>[] { Type.class });
	}

	@Override
	public Object parseDefaultString(FieldType fieldType, String defaultStr)
			throws SQLException {
		return Resource.valueOf(defaultStr);
	}

	@Override
	public Object resultToSqlArg(FieldType fieldType, DatabaseResults results,
			int columnPos) throws SQLException {
		return results.getObject(columnPos);
	}

	@Override
	public Resource sqlArgToJava(FieldType fieldType, Object sqlArg,
			int columnPos) {
		return Resource.valueOf((String) sqlArg);

	}

	public static ResourcePersister getInstance() {
		return instance;
	}

}

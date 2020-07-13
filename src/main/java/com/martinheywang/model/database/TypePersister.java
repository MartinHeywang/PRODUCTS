package com.martinheywang.model.database;

import java.sql.SQLException;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import com.martinheywang.model.types.Type;

public class TypePersister extends BaseDataType {

	private static final TypePersister instance = new TypePersister();

	public TypePersister() {
		super(SqlType.STRING, new Class<?>[] { Type.class });
	}

	@Override
	public Type parseDefaultString(FieldType fieldType, String defaultStr)
			throws SQLException {
		return Type.valueOf(defaultStr);
	}

	@Override
	public Object resultToSqlArg(FieldType fieldType, DatabaseResults results,
			int columnPos) throws SQLException {
		return results.getObject(columnPos);
	}

	@Override
	public Type sqlArgToJava(FieldType fieldType, Object sqlArg,
			int columnPos) {
		return Type.valueOf((String) sqlArg);

	}

	public static TypePersister getInstance() {
		return instance;
	}

}

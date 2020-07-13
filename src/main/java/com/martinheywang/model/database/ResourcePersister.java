package com.martinheywang.model.database;

import java.sql.SQLException;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import com.martinheywang.model.resources.DefaultResources;
import com.martinheywang.model.resources.Resource;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ResourcePersister extends BaseDataType {

	private static final ResourcePersister instance = new ResourcePersister();

	private ResourcePersister() {
		super(SqlType.STRING, new Class<?>[] { ObjectProperty.class });
	}

	@Override
	public ObjectProperty<Resource> parseDefaultString(FieldType fieldType,
			String defaultStr)
			throws SQLException {
		return new SimpleObjectProperty<Resource>(DefaultResources.NONE);
	}

	@Override
	public Object resultToSqlArg(FieldType fieldType, DatabaseResults results,
			int columnPos) throws SQLException {
		return results.getObject(columnPos);
	}

	@Override
	public ObjectProperty<Resource> sqlArgToJava(FieldType fieldType,
			Object sqlArg,
			int columnPos) {
		return new SimpleObjectProperty<Resource>(
				Resource.valueOf((String) sqlArg));

	}

	public static ResourcePersister getInstance() {
		return instance;
	}

}

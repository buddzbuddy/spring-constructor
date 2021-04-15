package com.webdatabase.dgz.query.utils;

public class InsertEntityModel {
	private String entityName;
	private InsertEntityFieldModel[] fields;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public InsertEntityFieldModel[] getFields() {
		return fields;
	}

	public void setFields(InsertEntityFieldModel[] fields) {
		this.fields = fields;
	}
}

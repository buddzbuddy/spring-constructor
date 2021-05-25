package com.webdatabase.dgz.util;

public class JsonSqlJoin {
	private String entityTo;
	private String pkField;
	private String fkField;
	public String getPkField() {
		return pkField;
	}
	public void setPkField(String pkField) {
		this.pkField = pkField;
	}
	public String getFkField() {
		return fkField;
	}
	public void setFkField(String fkField) {
		this.fkField = fkField;
	}
	public String getEntityTo() {
		return entityTo;
	}
	public void setEntityTo(String entityTo) {
		this.entityTo = entityTo;
	}
}

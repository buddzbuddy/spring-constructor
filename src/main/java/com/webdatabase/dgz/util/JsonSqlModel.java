package com.webdatabase.dgz.util;

public class JsonSqlModel {

	private String rootEntity;
	private JsonSqlJoin[] joins;
	private JsonSqlSelect[] selects;
	private JsonSqlCondition[] conditions;
	public String getRootEntity() {
		return rootEntity;
	}
	public void setRootEntity(String rootEntity) {
		this.rootEntity = rootEntity;
	}
	public JsonSqlJoin[] getJoins() {
		return joins;
	}
	public void setJoins(JsonSqlJoin[] joins) {
		this.joins = joins;
	}
	public JsonSqlSelect[] getSelects() {
		return selects;
	}
	public void setSelects(JsonSqlSelect[] selects) {
		this.selects = selects;
	}
	public JsonSqlCondition[] getConditions() {
		return conditions;
	}
	public void setConditions(JsonSqlCondition[] conditions) {
		this.conditions = conditions;
	}
}

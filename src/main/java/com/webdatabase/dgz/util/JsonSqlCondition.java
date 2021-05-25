package com.webdatabase.dgz.util;

public class JsonSqlCondition {
	private String expressionOperation;
	private String conditionOperation;
	private String fieldAlias;
	private String paramName;
	private Object paramValue;
	public String getExpressionOperation() {
		return expressionOperation;
	}
	public void setExpressionOperation(String expressionOperation) {
		this.expressionOperation = expressionOperation;
	}
	public String getConditionOperation() {
		return conditionOperation;
	}
	public void setConditionOperation(String conditionOperation) {
		this.conditionOperation = conditionOperation;
	}
	public String getFieldAlias() {
		return fieldAlias;
	}
	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public Object getParamValue() {
		return paramValue;
	}
	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}
}

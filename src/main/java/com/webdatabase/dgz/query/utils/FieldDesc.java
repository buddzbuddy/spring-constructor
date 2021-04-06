package com.webdatabase.dgz.query.utils;

public class FieldDesc {
	private String name;
	private String label;
	private String dataType;
	private String dictionaryClassName;
	private String dictionaryFieldName;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDictionaryClassName() {
		return dictionaryClassName;
	}
	public void setDictionaryClassName(String dictionaryClassName) {
		this.dictionaryClassName = dictionaryClassName;
	}
	public String getDictionaryFieldName() {
		return dictionaryFieldName;
	}
	public void setDictionaryFieldName(String dictionaryFieldName) {
		this.dictionaryFieldName = dictionaryFieldName;
	}
}

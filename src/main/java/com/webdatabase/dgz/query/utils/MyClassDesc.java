package com.webdatabase.dgz.query.utils;

import java.util.List;

public class MyClassDesc {
	private String className;
	private String classLabel;
	private List <FieldDesc> propList;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List <FieldDesc> getPropList() {
		return propList;
	}
	public void setPropList(List <FieldDesc> propList) {
		this.propList = propList;
	}
	public String getClassLabel() {
		return classLabel;
	}
	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}
}

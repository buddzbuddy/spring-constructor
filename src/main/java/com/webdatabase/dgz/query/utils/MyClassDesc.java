package com.webdatabase.dgz.query.utils;

import java.util.List;

public class MyClassDesc {
	private String className;
	private String classLabel;
	private Integer orderNo;
	private List <FieldDesc> propList;
	private String dbName;
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
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}

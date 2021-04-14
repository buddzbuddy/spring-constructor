package com.webdatabase.dgz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table (name = "datasource_fields")
@IsMetaClass(label = "Поле источника данных")
public class DatasourceField extends AuditModel {

	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@MetaFieldName(label = "Источник данных", selectClassName = "Datasource")
	@Column(name = "datasource_id", nullable = true)
	private long datasource;
	
	@MetaFieldName(label = "Наименование")
	@Column(name = "name")
	private String name;
	
	@MetaFieldName(label = "Метка")
	@Column(name = "label")
	private String label;
	
	@MetaFieldName(label = "Тип Данных")
	@Column(name = "data_type")
	private String dataType;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getDatasource() {
		return datasource;
	}
	
	public void setDatasource(long datasource) {
		this.datasource = datasource;
	}
	
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
}
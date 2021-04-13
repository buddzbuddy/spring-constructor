package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table (name = "taxe_codes")
@IsMetaClass(label = "Налоговый кодекс")
public class Taxe_code extends AuditModel{
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@MetaFieldName(label = "Код")
	private String code;
	
	@MetaFieldName(label = "Наименование налогового кодекса")
	private String name;
	
	@MetaFieldName(label = "Название детализации")
	private String detailName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	
	
}
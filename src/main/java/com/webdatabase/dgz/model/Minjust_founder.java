package com.webdatabase.dgz.model;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "minjust_founders")
@IsMetaClass(label = "Учередители")

public class Minjust_founder extends AuditModel{
	@MetaFieldName(label = "Id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "ФИО или наименование учредителя")
	@Column(name = "full_name")
	private List<String> fullName = new ArrayList<>();
	
	@MetaFieldName(label = "Гражданство (страна)")
	@Column(name = "citizenship")
	private List<String> citizenship = new ArrayList<>();
	
	@MetaFieldName(label = "ИНН учредителя")
	@Column(name = "tin")
	private List<String> tin = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getFullName() {
		return fullName;
	}

	public void setFullName(List<String> fullName) {
		this.fullName = fullName;
	}

	public List<String> getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(List<String> citizenship) {
		this.citizenship = citizenship;
	}

	public List<String> getTin() {
		return tin;
	}

	public void setTin(List<String> tin) {
		this.tin = tin;
	}
}

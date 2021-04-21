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
@Table (name = "tp_data_by_inn_for_business_activity_responses")
@IsMetaClass(label = "Данные инн об активности ИП")
public class Tp_data_by_inn_for_business_activity_response extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "ИНН")
	@Column(name = "inn")
	private String inn;
	
	@MetaFieldName(label = "Код района")
	@Column(name = "rayon_code")
	private String rayonCode;
	
	@MetaFieldName(label = "Полное название")
	@Column(name = "full_name")
	private String fullName;
	
	@MetaFieldName(label = "Полный адрес")
	@Column(name = "full_address")
	private String fullAddress;
	
	@MetaFieldName(label = "ZIP")
	@Column(name = "zip")
	private String zip;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getRayonCode() {
		return rayonCode;
	}

	public void setRayonCode(String rayonCode) {
		this.rayonCode = rayonCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
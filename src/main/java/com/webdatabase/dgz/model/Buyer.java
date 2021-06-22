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
@Table(name = "buyers")
@IsMetaClass(label = "Закупающая организация", orderNo = 5)
public class Buyer extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private long id;
	
	@Column(name = "keycloak_user_id")
	private String keycloakUserId;
	

	@MetaFieldName(label = "Наименование поставщика")
	@Column(name = "name")
	private String name;
	
	@MetaFieldName(label = "ИНН")
	@Column(name = "inn")
	private String inn;

	@MetaFieldName(label = "Юр. адрес")
	@Column(name = "legal_address")
	private String legalAddress;

	@MetaFieldName(label = "Факт. адрес")
	@Column(name = "fact_address")
	private String factAddress;

	@MetaFieldName(label = "Телефон")
	@Column(name = "telephone")
	private String telephone;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKeycloakUserId() {
		return keycloakUserId;
	}

	public void setKeycloakUserId(String keycloakUserId) {
		this.keycloakUserId = keycloakUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}

	public String getFactAddress() {
		return factAddress;
	}

	public void setFactAddress(String factAddress) {
		this.factAddress = factAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}

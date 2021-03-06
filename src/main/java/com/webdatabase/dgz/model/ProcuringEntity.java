package com.webdatabase.dgz.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "procuring_entities")
@IsMetaClass(label = "Закупающая организация")
public class ProcuringEntity extends AuditModel {
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private long id;
	
	@MetaFieldName(label = "ИНН")
	@Column(name = "inn")
	private String inn;
	
	@MetaFieldName(label = "Наименование закупающей организации")
	@Column(name = "name")
	private String name;
	
	@MetaFieldName(label = "Адрес")
	@Column(name = "address")
	private String address;
	
	@MetaFieldName(label = "Контактные данные")
	@Column(name = "contact_data")
	private String contactData;
	/*
	
	@OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_type_id", nullable = true)
	private Set<LicenseType> licenseTypes;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "procuring_entity_id", nullable = true)
	private Set<Appeal> appeals;
	
	*/
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactData() {
		return contactData;
	}

	public void setContactData(String contactData) {
		this.contactData = contactData;
	}
	
	/*
	public Set<LicenseType> getLicenseTypes() {
		return licenseTypes;
	}

	public void setLicenseTypes(Set<LicenseType> licenseTypes) {
		this.licenseTypes = licenseTypes;
	}

	public Set<Appeal> getAppeals() {
		return appeals;
	}

	public void setAppeals(Set<Appeal> appeals) {
		this.appeals = appeals;
	}
	*/
}

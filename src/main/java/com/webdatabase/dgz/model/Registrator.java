package com.webdatabase.dgz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "registrators")
@IsMetaClass(label = "Регистратор")
public class Registrator extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private long id;
	
	@MetaFieldName(label = "Наименование регистратора")
	@Column(name = "name")
	private String name;
	
	@MetaFieldName(label = "Партнер", selectClassName = "Counterpart")
	@Column(name = "counterpart_id", nullable = true)
	private long counterpartId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="counterpart_id", referencedColumnName="id", insertable=false, updatable=false)
	private Counterpart counterpart;
	
	@MetaFieldName(label = "Контактные данные")
	@Column(name = "contact_data")
	private String contactData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactData() {
		return contactData;
	}

	public void setContactData(String contactData) {
		this.contactData = contactData;
	}

	public long getCounterpartId() {
		return counterpartId;
	}

	public void setCounterpartId(long counterpartId) {
		this.counterpartId = counterpartId;
	}

	public void setCounterpart(Counterpart counterpart) {
		this.counterpart = counterpart;
	}
}

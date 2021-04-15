package com.webdatabase.dgz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@MetaFieldName(label = "Партнер")
	@Nullable
	@Column(name = "counterpart")
	private int counterpart;
	
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

	public int getCounterpart() {
		return counterpart;
	}

	public void setCounterpart(int counterpart) {
		this.counterpart = counterpart;
	}

	public String getContactData() {
		return contactData;
	}

	public void setContactData(String contactData) {
		this.contactData = contactData;
	}
}

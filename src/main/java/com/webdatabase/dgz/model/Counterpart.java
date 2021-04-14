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
@Table(name = "counterparts")
@IsMetaClass(label = "Партнер")
public class Counterpart extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
	
	@MetaFieldName(label = "Наименование партнера")
    private String name;
    
	@MetaFieldName(label = "Тип партнера")
    @Nullable
    private int counterpart_type;
    
	@MetaFieldName(label = "Контактные данные")
    @Column(columnDefinition = "text")
    private String contactData;
    
	@MetaFieldName(label = "Адрес")
    private String address;
    
	@MetaFieldName(label = "Номер банковского счета")
    private String bankAccountNo;
    
	@MetaFieldName(label = "Комментарии")
    private String comments;

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

	public int getCounterpart_type() {
		return counterpart_type;
	}

	public void setCounterpart_type(int counterpart_type) {
		this.counterpart_type = counterpart_type;
	}

	public String getContactData() {
		return contactData;
	}

	public void setContactData(String contactData) {
		this.contactData = contactData;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
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
@Table(name = "counterparts")
@IsMetaClass(label = "Партнер")
public class Counterpart extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private long id;
	
	@MetaFieldName(label = "Наименование партнера")
	@Column(name = "name")
    private String name;
    
	@MetaFieldName(label = "Тип партнера", selectClassName = "CounterpartType")
    @Column(name = "counterpart_type_id", nullable = true)
    private long counterpartTypeId;
    
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="counterpart_type_id", referencedColumnName="id", insertable=false, updatable=false)
	private CounterpartType counterpartType;
	
	
	@MetaFieldName(label = "Контактные данные")
    @Column(columnDefinition = "text", name = "contact_data")
    private String contactData;
    
	@MetaFieldName(label = "Адрес")
	@Column(name = "address")
    private String address;
    
	@MetaFieldName(label = "Номер банковского счета")
	@Column(name = "bank_account_no")
    private String bankAccountNo;
    
	@MetaFieldName(label = "Комментарии")
	@Column(name = "comments")
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

	public long getCounterpartTypeId() {
		return counterpartTypeId;
	}

	public void setCounterpartTypeId(long counterpartTypeId) {
		this.counterpartTypeId = counterpartTypeId;
	}

	public CounterpartType getCounterpartType() {
		return counterpartType;
	}

	public void setCounterpartType(CounterpartType counterpartType) {
		this.counterpartType = counterpartType;
	}
}
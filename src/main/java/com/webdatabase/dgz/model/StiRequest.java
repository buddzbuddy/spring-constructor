package com.webdatabase.dgz.model;

import java.util.Date;

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
@Table(name = "sti_requests")
@IsMetaClass(label = "Задолженности с ГНС")
public class StiRequest extends AuditModel {
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "ИНН организации")
	@Column(name = "inn")
	private String inn;
	
	@MetaFieldName(label = "Наименование организации")
	@Column(name = "org_name")
	private String orgName;
	
	@MetaFieldName(label = "Выдавший орган")
	@Column(name = "issuer")
	private String issuer;
	
	@MetaFieldName(label = "Дата")
	@Column(name = "app_date")
	private Date appDate;
	
	@MetaFieldName(label = "Имеется задолженность?")
	@Column(name = "has")
	private boolean has;

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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public boolean isHas() {
		return has;
	}

	public void setHas(boolean has) {
		this.has = has;
	}
}

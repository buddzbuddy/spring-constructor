package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "certificates")
@IsMetaClass(label = "Задолженность по налогам")
public class Certificate extends AuditModel {
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private long supplierId;
	
	@MetaFieldName(label = "Сотрудник", selectClassName = "SupplierMember")
	@Column(name = "supplier_member_id", nullable = true)
	private long supplierMemberId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="supplier_member_id", referencedColumnName="id", insertable=false, updatable=false)
	private SupplierMember supplierMember;
	
	@MetaFieldName(label = "Специальность")
	@Column(name = "name")
	private String name;
	
	@MetaFieldName(label = "Дата выдачи")
	@Column(name = "issue_date", nullable = true)
	private Date issueDate;
	
	@MetaFieldName(label = "Выдан кем")
	@Column(name = "issuer", nullable = true)
	private String issuer;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public long getSupplierMemberId() {
		return supplierMemberId;
	}

	public void setSupplierMemberId(long supplierMemberId) {
		this.supplierMemberId = supplierMemberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public SupplierMember getSupplierMember() {
		return supplierMember;
	}

	public void setSupplierMember(SupplierMember supplierMember) {
		this.supplierMember = supplierMember;
	}
}

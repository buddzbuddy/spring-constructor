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

import org.springframework.lang.Nullable;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "license")
@IsMetaClass(label = "Лицензия", orderNo = 4)
public class License extends AuditModel{
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "Выдавший орган")
	@Column(name = "issuer")
	private String issuer;
	
	@MetaFieldName(label = "Номер")
	@Column(name = "no")
	private String no;
	
	@MetaFieldName(label = "Дата выпуска")
	@Nullable
	@Column(name = "issue_date")
	private Date issueDate;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private long supplierId;
	
	@MetaFieldName(label = "Тип лицензии", selectClassName = "LicenseType")
	@Column(name = "license_type_id", nullable = true)
	private long licenseTypeId;
	

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="license_type_id", referencedColumnName="id", insertable=false, updatable=false)
	private LicenseType licenseType;
	
	@MetaFieldName(label = "Срок действия")
	@Nullable
	@Column(name = "expiry_date")
	private Date expiryDate;
	
	@MetaFieldName(label = "Статус")
	@Column(name = "status")
	private String status;
	
	@MetaFieldName(label = "Дополнительная информация")
	@Column(name = "additional_info")
	private String additionalInfo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	public long getLicenseTypeId() {
		return licenseTypeId;
	}

	public void setLicenseTypeId(long licenseTypeId) {
		this.licenseTypeId = licenseTypeId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public LicenseType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(LicenseType licenseType) {
		this.licenseType = licenseType;
	}
	
}

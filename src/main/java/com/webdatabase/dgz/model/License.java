package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "license")
@IsMetaClass(label = "Лицензия")
public class License extends AuditModel{
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@MetaFieldName(label = "Эмитент")
	private String issuer;
	
	@MetaFieldName(label = "Номер")
	private String no;
	
	@MetaFieldName(label = "Дата выпуска")
	@Nullable
	private Date issueDate;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private long supplierId;
	
	@MetaFieldName(label = "Тип лицензии", selectClassName = "LicenseType")
	@Column(name = "license_type_id", nullable = true)
	private long license_typeId;
	
	@MetaFieldName(label = "Срок действия")
	@Nullable
	private Date expiryDate;
	
	@MetaFieldName(label = "Статус")
	private String status;
	
	@MetaFieldName(label = "Дополнительная информация")
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
	
	public long getLicense_typeId() {
		return license_typeId;
	}

	public void setLicense_typeId(long license_type_id) {
		this.license_typeId = license_type_id;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	
}

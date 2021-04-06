package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "license")
@IsMetaClass(label = "Лицензия")
public class License extends AuditModel{

	@Id
    @GeneratedValue(generator = "license_generator")
    @SequenceGenerator(
            name = "license_generator",
            sequenceName = "license_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
	
	private Long id;
	
	private String issuer;
	
	private String no;
	
	@Nullable
	private Date issueDate;
	
	@MetaFieldName(label = "Тип лицензии", selectClassName = "LicenseType")
	private Long license_type_id;
	
	@Nullable
	private Date expiryDate;
	
	private String status;
	
	private String additionalInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getLicense_type_id() {
		return license_type_id;
	}

	public void setLicense_type_id(Long license_type_id) {
		this.license_type_id = license_type_id;
	}
}

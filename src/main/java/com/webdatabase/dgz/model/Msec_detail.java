package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "msec_details")
@IsMetaClass(label = "Детализация МСЭК")
public class Msec_detail extends AuditModel {

	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private long id;
	
	@MetaFieldName(label = "Название организации")
    @NotBlank
    @Size(min = 3, max = 100)
	@Column(name = "organization_name")
    private String organizationName;
    
	@MetaFieldName(label = "Дата обследования")
    @Nullable
    @Column(name = "examination_date")
    private Date examinationDate;
    
	@MetaFieldName(label = "Тип обследования")
	@Column(name = "examination_type")
    private String examinationType;
    
	@MetaFieldName(label = "Группа инвалидности")
	@Column(name = "disability_group")
    private String disabilityGroup;
    
	@MetaFieldName(label = "От даты")
    @Nullable
    @Column(name = "from_date")
    private Date fromDate;
    
	@MetaFieldName(label = "До даты")
    @Nullable
    @Column(name = "to_date")
    private Date toDate;
    
	@MetaFieldName(label = "Повторное обследование")
	@Column(name = "re_examination")
    private String reExamination;
    
	@MetaFieldName(label = "Члены поставщика")
    @Nullable
    @Column(name = "supplier_member")
    private int supplier_member;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Date getExaminationDate() {
		return examinationDate;
	}

	public void setExaminationDate(Date examinationDate) {
		this.examinationDate = examinationDate;
	}

	public String getDisabilityGroup() {
		return disabilityGroup;
	}

	public void setDisabilityGroup(String disabilityGroup) {
		this.disabilityGroup = disabilityGroup;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date to) {
		this.toDate = to;
	}

	public String getReExamination() {
		return reExamination;
	}

	public void setReExamination(String reExamination) {
		this.reExamination = reExamination;
	}

	public int getSupplier_member() {
		return supplier_member;
	}

	public void setSupplier_member(int supplier_member) {
		this.supplier_member = supplier_member;
	}

	public String getExaminationType() {
		return examinationType;
	}

	public void setExaminationType(String examinationType) {
		this.examinationType = examinationType;
	}
}

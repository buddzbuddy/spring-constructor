package com.webdatabase.dgz.model;

import java.util.Date;

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
    private long id;
	
	@MetaFieldName(label = "Название организации")
    @NotBlank
    @Size(min = 3, max = 100)
    private String organizationName;
    
	@MetaFieldName(label = "Дата обследования")
    @Nullable
    private Date examinationDate;
    
	@MetaFieldName(label = "Тип обследования")
    private String examinationType;
    
	@MetaFieldName(label = "Группа инвалидности")
    private String disabilityGroup;
    
	@MetaFieldName(label = "От даты")
    @Nullable
    private Date fromDate;
    
	@MetaFieldName(label = "До даты")
    @Nullable
    private Date toDate;
    
	@MetaFieldName(label = "Повторное обследование")
    private String reExamination;
    
	@MetaFieldName(label = "Члены поставщика")
    @Nullable
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

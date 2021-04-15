package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table (name = "tpb_usiness_activity_date_by_inn_responses")
@IsMetaClass(label = "Ввод даты для ответа деловой активности по инн")
public class Tpb_usiness_activity_date_by_inn_response extends AuditModel{
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "Юр. адрес")
	@Column(name = "legal_address")
	private String legalAddress;
	
	@MetaFieldName(label = "Наименование")
	@Column(name = "name")
	private String name;
	
	@MetaFieldName(label = "Код района")
	@Column(name = "rayon_code")
	private String rayonCode;
	
	@MetaFieldName(label = "Наименование района")
	@Column(name = "rayon_name")
	private String rayonName;
	
	@MetaFieldName(label = "Дата начала действия налога")
	@Column(name = "tax_active_date")
	private Date taxActiveDate;
	
	@MetaFieldName(label = "Код типа налога")
	@Column(name = "tax_type_code")
	private String taxTypeCode;
	
	@MetaFieldName(label = "Название типа налога")
	@Column(name = "tax_type_name")
	private String taxTypeName;
	
	@MetaFieldName(label = "Банка")
	@Column(name = "tin")
	private String tin;
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public String getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRayonCode() {
		return rayonCode;
	}

	public void setRayonCode(String rayonCode) {
		this.rayonCode = rayonCode;
	}

	public String getRayonName() {
		return rayonName;
	}

	public void setRayonName(String rayonName) {
		this.rayonName = rayonName;
	}

	public Date getTaxActiveDate() {
		return taxActiveDate;
	}

	public void setTaxActiveDate(Date taxActiveDate) {
		this.taxActiveDate = taxActiveDate;
	}

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public String getTaxTypeName() {
		return taxTypeName;
	}

	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}
	
}

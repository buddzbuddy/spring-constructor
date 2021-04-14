package com.webdatabase.dgz.model;

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
@Table(name = "pension_infos")
@IsMetaClass(label = "Информация о пенсии")
public class Pension_info extends AuditModel{

	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @MetaFieldName(label = "Члены поставщика", selectClassName = "SupplierMember")
	@Column(name = "supplier_member_id", nullable = true)
	private Long supplier_member;
	
    @MetaFieldName(label = "Rusf")
	private String rusf;
	
    @MetaFieldName(label = "Номер досье")
	private String numDossier;
	
    @MetaFieldName(label = "ПИН пенсионера")
	private String pinPensioner;
	
    @MetaFieldName(label = "ПИН получателя")
	private String pinRecipient;
	
    @MetaFieldName(label = "Дата начало")
	private String dateFromInitial;
	
    @MetaFieldName(label = "Сумма")
	private String summa;
	
    @MetaFieldName(label = "Вид пенсии")
	private String kindOfPension;
	
    @MetaFieldName(label = "Категория пенсия")
	private String categoryPension;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSupplier_member() {
		return supplier_member;
	}

	public void setSupplier_member(Long supplier_member) {
		this.supplier_member = supplier_member;
	}

	public String getRusf() {
		return rusf;
	}

	public void setRusf(String rusf) {
		this.rusf = rusf;
	}

	public String getNumDossier() {
		return numDossier;
	}

	public void setNumDossier(String numDossier) {
		this.numDossier = numDossier;
	}

	public String getPinPensioner() {
		return pinPensioner;
	}

	public void setPinPensioner(String pinPensioner) {
		this.pinPensioner = pinPensioner;
	}

	public String getPinRecipient() {
		return pinRecipient;
	}

	public void setPinRecipient(String pinRecipient) {
		this.pinRecipient = pinRecipient;
	}

	public String getDateFromInitial() {
		return dateFromInitial;
	}

	public void setDateFromInitial(String dateFromInitial) {
		this.dateFromInitial = dateFromInitial;
	}

	public String getSumma() {
		return summa;
	}

	public void setSumma(String sum) {
		this.summa = sum;
	}

	public String getKindOfPension() {
		return kindOfPension;
	}

	public void setKindOfPension(String kindOfPension) {
		this.kindOfPension = kindOfPension;
	}

	public String getCategoryPension() {
		return categoryPension;
	}

	public void setCategoryPension(String categoryPension) {
		this.categoryPension = categoryPension;
	}
}
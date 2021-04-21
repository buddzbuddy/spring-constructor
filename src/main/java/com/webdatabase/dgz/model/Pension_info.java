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
	@Column(name = "id")
	private long id;
	
    @MetaFieldName(label = "Участники поставщика", selectClassName = "SupplierMember")
	@Column(name = "supplier_member_id", nullable = true)
	private long supplier_member;
	
    @MetaFieldName(label = "РУСФ")
    @Column(name = "rusf")
	private String rusf;
	
    @MetaFieldName(label = "Номер досье")
    @Column(name = "num_dossier")
	private String numDossier;
	
    @MetaFieldName(label = "ПИН пенсионера")
    @Column(name = "pin_pensioner")
	private String pinPensioner;
	
    @MetaFieldName(label = "ПИН получателя")
    @Column(name = "pin_recipient")
	private String pinRecipient;
	
    @MetaFieldName(label = "Дата начало")
    @Column(name = "date_from_initial")
	private String dateFromInitial;
	
    @MetaFieldName(label = "Сумма")
    @Column(name = "summa")
	private String summa;
	
    @MetaFieldName(label = "Вид пенсии")
    @Column(name = "kind_of_pension")
	private String kindOfPension;
	
    @MetaFieldName(label = "Категории пенсии")
    @Column(name = "category_pension")
	private String categoryPension;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSupplier_member() {
		return supplier_member;
	}

	public void setSupplier_member(long supplier_member) {
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
package com.webdatabase.dgz.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "suppliers")
@IsMetaClass(label = "Поставщик")
public class Supplier extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

	@MetaFieldName(label = "Наименование поставщика")
	private String name;
	
	
	@MetaFieldName(label = "Форма собственности", selectClassName = "OwnershipType")
	@Column(name="ownership_type_id", nullable=true)
	private Long ownershipTypeId;
	
	
	
	@MetaFieldName(label = "Отрасль", selectClassName = "Industry")
	@Column(name="industry_id", nullable=true)
	private Long industryId;
	

	@MetaFieldName(label = "ИНН")
	private String inn;

	@MetaFieldName(label = "Юр. адрес")
	private String legalAddress;

	@MetaFieldName(label = "Факт. адрес")
	private String factAddress;

	@MetaFieldName(label = "Телефон")
	private String telephone;

	@MetaFieldName(label = "Банк")
	private String bankName;

	@MetaFieldName(label = "Счет")
	private String bankAccount;

	@MetaFieldName(label = "БИК")
	private String bic;

	@MetaFieldName(label = "Индекс")
	private String zip;

	@MetaFieldName(label = "Код района")
	private String rayonCode;

	@MetaFieldName(label = "Резидент (да/нет)")
	@Nullable
	private Boolean isResident;
	
	@Nullable
	@MetaFieldName(label = "В черном списке (да/нет)")
	private Boolean isBlack;
	
	/*
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", nullable = true)
	private Set<License> licenses;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", nullable = true)
	private Set<SupplierMember> supplierMembers;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", nullable = true)
	private Set<Appeal> appeals;
	*/
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}

	public String getFactAddress() {
		return factAddress;
	}

	public void setFactAddress(String factAddress) {
		this.factAddress = factAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getRayonCode() {
		return rayonCode;
	}

	public void setRayonCode(String rayonCode) {
		this.rayonCode = rayonCode;
	}

	public Boolean getIsResident() {
		return isResident;
	}

	public void setIsResident(Boolean isResident) {
		this.isResident = isResident;
	}

	public Boolean getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Boolean isBlack) {
		this.isBlack = isBlack;
	}
	
	
	public Long getOwnershipTypeId() {
		return ownershipTypeId;
	}

	public void setOwnershipTypeId(Long ownershipTypeId) {
		this.ownershipTypeId = ownershipTypeId;
	}

	public Long getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Long industryId) {
		this.industryId = industryId;
	}
	
	/*
	public Set<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(Set<License> licenses) {
		this.licenses = licenses;
	}
	
	public Set<Appeal> getAppeals() {
		return appeals;
	}

	public void setAppeals(Set<Appeal> appeals) {
		this.appeals = appeals;
	}

	public Set<SupplierMember> getSupplierMembers() {
		return supplierMembers;
	}

	public void setSupplierMembers(Set<SupplierMember> supplierMembers) {
		this.supplierMembers = supplierMembers;
	}
	*/
}

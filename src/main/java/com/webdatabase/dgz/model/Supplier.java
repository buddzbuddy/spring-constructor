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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "suppliers")
@IsMetaClass(label = "Поставщик", orderNo = 1)
public class Supplier extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private long id;

	@MetaFieldName(label = "Наименование поставщика")
	@Column(name = "name")
	private String name;
	
	
	@MetaFieldName(label = "Форма собственности", selectClassName = "OwnershipType")
	@Column(name="ownership_type_id", nullable=true)
	private long ownershipTypeId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="ownership_type_id", referencedColumnName="id", insertable=false, updatable=false)
	private OwnershipType ownershipType;
	
	
	@MetaFieldName(label = "Отрасль", selectClassName = "Industry")
	@Column(name="industry_id", nullable=true)
	private long industryId;
	

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="industry_id", referencedColumnName="id", insertable=false, updatable=false)
	private Industry industry;
	

	@MetaFieldName(label = "ИНН")
	@Column(name = "inn")
	private String inn;

	@MetaFieldName(label = "Юр. адрес")
	@Column(name = "legal_address")
	private String legalAddress;

	@MetaFieldName(label = "Факт. адрес")
	@Column(name = "fact_address")
	private String factAddress;

	@MetaFieldName(label = "Телефон")
	@Column(name = "telephone")
	private String telephone;

	@MetaFieldName(label = "Банк")
	@Column(name = "bank_name")
	private String bankName;

	@MetaFieldName(label = "Счет")
	@Column(name = "bank_account")
	private String bankAccount;

	@MetaFieldName(label = "БИК")
	@Column(name = "bic")
	private String bic;

	@MetaFieldName(label = "Индекс")
	@Column(name = "zip")
	private String zip;

	@MetaFieldName(label = "Код района")
	@Column(name = "rayon_code")
	private String rayonCode;

	@MetaFieldName(label = "Резидент (да/нет)")
	@Nullable
	@Column(name = "is_resident")
	private boolean isResident;
	
	@Nullable
	@MetaFieldName(label = "В черном списке (да/нет)")
	@Column(name = "is_black")
	private boolean isBlack;
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id")
	private Set<License> licenses;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", nullable = true)
	private Set<SupplierMember> supplierMembers;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", nullable = true)
	private Set<Appeal> appeals;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public boolean getIsResident() {
		return isResident;
	}

	public void setIsResident(boolean isResident) {
		this.isResident = isResident;
	}

	public boolean getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}
	
	
	public long getOwnershipTypeId() {
		return ownershipTypeId;
	}

	public void setOwnershipTypeId(long ownershipTypeId) {
		this.ownershipTypeId = ownershipTypeId;
	}

	public long getIndustryId() {
		return industryId;
	}

	public void setIndustryId(long industryId) {
		this.industryId = industryId;
	}

	public OwnershipType getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(OwnershipType ownershipType) {
		this.ownershipType = ownershipType;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}
	
	
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
	
}

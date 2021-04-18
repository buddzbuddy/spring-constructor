package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;


@Entity
@Table (name = "supplier_members")
@IsMetaClass(label = "Участники поставщика", orderNo = 2)
public class SupplierMember extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	
	@MetaFieldName(label = "Тип участника", selectClassName = "MemberType")
	@Column(name = "member_type_id", nullable = true)
	private long memberTypeId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="member_type_id", referencedColumnName="id", insertable=false, updatable=false)
	private MemberType memberType;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private long supplierId;
	
	@MetaFieldName(label = "ПИН")
	@Column(name = "pin")
	private String pin;
	
	@MetaFieldName(label = "ИНН")
	@Column(name = "inn")
	private String inn;
	
	@MetaFieldName(label = "Фамилия")
	@Column(name = "surname")
	private String surname;
	
	@MetaFieldName(label = "Имя")
	@Column(name = "name")
	private String name;
	
	@MetaFieldName(label = "Отчество")
	@Column(name = "patronymic")
	private String patronymic;
	
	@MetaFieldName(label = "Национальность")
	@Column(name = "nationality")
	private String nationality;
	
	@MetaFieldName(label = "Дата рождения")
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@MetaFieldName(label = "Серия паспорта")
	@Column(name = "passport_series")
	private String passportSeries;
	
	@MetaFieldName(label = "Номер паспорта")
	@Column(name = "passport_number")
	private String passportNumber;
	
	@MetaFieldName(label = "Паспортный орган")
	@Column(name = "passport_authority")
	private String passportAuthority;
	
	@MetaFieldName(label = "Дата выпуска")
	@Column(name = "issued_date")
	private Date issuedDate;
	
	@MetaFieldName(label = "Дата истечения срока")
	@Column(name = "expired_date")
	private Date expiredDate;
	
	@MetaFieldName(label = "Статус недействительности" )
	@Column(name = "void_status")
	private int voidStatus;
	
	@MetaFieldName(label = "Семейное положение")
	@Column(name = "family_status")
	private int familyStatus;
	
	@MetaFieldName(label = "Пол")
	@Column(name = "gender")
	private String gender;
	
	@MetaFieldName(label = "Регион")
	@Column(name = "address_region")
	private String addressRegion;
	
	@MetaFieldName(label = "Населенный пункт")
	@Column(name = "address_locality")
	private String addressLocality;
	
	@MetaFieldName(label = "Улица")
	@Column(name = "address_street")
	private String addressStreet;
	
	@MetaFieldName(label = "Дом")
	@Column(name = "address_house")
	private String addressHouse;
	
	@MetaFieldName(label = "ID региона")
	@Nullable
	@Column(name = "region_id")
	private int regionId;
	
	@MetaFieldName(label = "ID района")
	@Nullable
	@Column(name = "district_id")
	private int districtId;
	
	@MetaFieldName(label = "ID области")
	@Nullable
	@Column(name = "area_id")
	private int areaId;
	
	@MetaFieldName(label = "ID подрайона")
	@Nullable
	@Column(name = "subarea_id")
	private int subareaId;
	
	@MetaFieldName(label = "ID улицы")
	@Nullable
	@Column(name = "street_id")
	private int streetId;
	
	@MetaFieldName(label = "ID дома")
	@Nullable
	@Column(name = "house_id")
	private int houseId;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPassportSeries() {
		return passportSeries;
	}

	public void setPassportSeries(String passportSeries) {
		this.passportSeries = passportSeries;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPassportAuthority() {
		return passportAuthority;
	}

	public void setPassportAuthority(String passportAuthority) {
		this.passportAuthority = passportAuthority;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public int getVoidStatus() {
		return voidStatus;
	}

	public void setVoidStatus(int voidStatus) {
		this.voidStatus = voidStatus;
	}

	public int getFamilyStatus() {
		return familyStatus;
	}

	public void setFamilyStatus(int familyStatus) {
		this.familyStatus = familyStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddressRegion() {
		return addressRegion;
	}

	public void setAddressRegion(String addressRegion) {
		this.addressRegion = addressRegion;
	}

	public String getAddressLocality() {
		return addressLocality;
	}

	public void setAddressLocality(String addressLocality) {
		this.addressLocality = addressLocality;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressHouse() {
		return addressHouse;
	}

	public void setAddressHouse(String addressHouse) {
		this.addressHouse = addressHouse;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getSubareaId() {
		return subareaId;
	}

	public void setSubareaId(int subareaId) {
		this.subareaId = subareaId;
	}

	public int getStreetId() {
		return streetId;
	}

	public void setStreetId(int streetId) {
		this.streetId = streetId;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public long getMemberTypeId() {
		return memberTypeId;
	}

	public void setMemberTypeId(long memberTypeId) {
		this.memberTypeId = memberTypeId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}
	
}

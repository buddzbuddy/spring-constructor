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
@IsMetaClass(label = "Члены поставщика")
public class SupplierMember extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
    @GeneratedValue(generator = "supplier_member_generator",
    		strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "supplier_member_generator",
            sequenceName = "supplier_member_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
	
	private Long id;
	
	
	@MetaFieldName(label = "Тип члена", selectClassName = "MemberType")
	@Column(name = "member_type_id", nullable = true)
	private Long member_typeId;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private Long supplierId;
	
	@MetaFieldName(label = "ПИН")
	private String pin;
	
	@MetaFieldName(label = "ИНН")
	private String inn;
	
	@MetaFieldName(label = "Фамилия")
	private String surname;
	
	@MetaFieldName(label = "Имя")
	private String name;
	
	@MetaFieldName(label = "Отчество")
	private String patronymic;
	
	@MetaFieldName(label = "Национальность")
	private String nationality;
	
	@MetaFieldName(label = "Дата рождения")
	private Date dateOfBirth;
	
	@MetaFieldName(label = "Серия паспорта")
	private String passportSeries;
	
	@MetaFieldName(label = "Номер паспорта")
	private String passportNumber;
	
	@MetaFieldName(label = "Паспортный орган")
	private String passportAuthority;
	
	@MetaFieldName(label = "Дата выпуска")
	private Date issuedDate;
	
	@MetaFieldName(label = "Дата истечения срока")
	private Date expiredDate;
	
	@MetaFieldName(label = "Недействительный статус" )
	private int voidStatus;
	
	@MetaFieldName(label = "Семейное положение")
	private int familyStatus;
	
	@MetaFieldName(label = "Пол")
	private String gender;
	
	@MetaFieldName(label = "Регион")
	private String addressRegion;
	
	@MetaFieldName(label = "Населенный пункт")
	private String addressLocality;
	
	@MetaFieldName(label = "Улица")
	private String addressStreet;
	
	@MetaFieldName(label = "Дом")
	private String addressHouse;
	
	@MetaFieldName(label = "ID региона")
	@Nullable
	private int regionId;
	
	@MetaFieldName(label = "ID района")
	@Nullable
	private int districtId;
	
	@MetaFieldName(label = "ID области")
	@Nullable
	private int areaId;
	
	@MetaFieldName(label = "ID подрайона")
	@Nullable
	private int subareaId;
	
	@MetaFieldName(label = "ID улицы")
	@Nullable
	private int streetId;
	
	@MetaFieldName(label = "ID дома")
	@Nullable
	private int houseId;
	
	@MetaFieldName(label = "ID типа поставщика")
	@Nullable
	private int _memberTypeId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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

	public int get_MemberTypeId() {
		return _memberTypeId;
	}

	public void set_MemberTypeId(int memberTypeId) {
		this._memberTypeId = memberTypeId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	
	public Long getMember_type() {
		return member_typeId;
	}

	public void setMember_type(Long member_typeId) {
		this.member_typeId = member_typeId;
	}
	
	
}

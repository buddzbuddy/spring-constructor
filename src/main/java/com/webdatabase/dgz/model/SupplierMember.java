package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.CascadeType;
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


@Entity
@Table (name = "supplier_members")
public class SupplierMember extends AuditModel {

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
	
	private MemberType memberType;
	
	private Supplier Supplier;
	
	private String pin;
	
	private String inn;
	
	private String surname;
	
	private String name;
	
	private String patronymic;
	
	private String nationality;
	
	private Date dateOfBirth;
	
	private String passportSeries;
	
	private String passportNumber;
	
	private String passportAuthority;
	
	private Date issuedDate;
	
	private Date expiredDate;
	
	private int voidStatus;
	
	private int familyStatus;
	
	private String gender;
	
	private String addressRegion;
	
	private String addressLocality;
	
	private String addressStreet;
	
	private String addressHouse;
	
	@Nullable
	private int regionId;
	
	@Nullable
	private int districtId;
	
	@Nullable
	private int areaId;
	
	@Nullable
	private int subareaId;
	
	@Nullable
	private int streetId;
	
	@Nullable
	private int houseId;
	
	@Nullable
	private int memberTypeId;
	
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

	public int getMemberTypeId() {
		return memberTypeId;
	}

	public void setMemberTypeId(int memberTypeId) {
		this.memberTypeId = memberTypeId;
	}

	public MemberType getMember_type() {
		return memberType;
	}

	public void setMember_type(MemberType memberType) {
		this.memberType = memberType;
	}

	public Supplier getSupplier() {
		return Supplier;
	}

	public void setSupplier(Supplier supplier) {
		Supplier = supplier;
	}
	
	
	
}

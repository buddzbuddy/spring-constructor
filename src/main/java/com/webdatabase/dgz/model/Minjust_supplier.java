package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "minjust_suppliers")
@IsMetaClass(label = "Юридические лица")
public class Minjust_supplier extends AuditModel{
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "Полное наименование на государственном языке")
	@Column(name = "full_name_Gl")
	private String fullNameGl;
	
	@MetaFieldName(label = "Краткое наименование на государственном языке")
	@Column(name = "short_name_Gl")
	private String shortNameGl;
	
	@MetaFieldName(label = "Полное наименование на официальном языке")
	@Column(name = "full_name_Ol")
	private String fullNameOl;
	
	@MetaFieldName(label = "Краткое наименование на официальном языке")
	@Column(name = "short_name_Ol")
	private String shortNameOl;
	
	@MetaFieldName(label = "Признак иностранного участия  в учредителях")
	@Column(name = "foreign_part")
	private String foreignPart;
	
	@MetaFieldName(label = "Регистрационный код минюста")
	@Column(name = "registr_code")
	private String registrCode;
	
	@MetaFieldName(label = "ОКПО")
	@Column(name = "stat_sub_code")
	private String statSubCode;
	
	@MetaFieldName(label = "ИНН")
	@Column(name = "tin")
	private String tin;
	
	@MetaFieldName(label = "Регион (область)")
	@Column(name = "region")
	private String region;
	
	@MetaFieldName(label = "Район")
	@Column(name = "district")
	private String district;
	
	@MetaFieldName(label = "Город")
	@Column(name = "city")
	private String city;
	
	@MetaFieldName(label = "Село")
	@Column(name = "village")
	private String village;
	
	@MetaFieldName(label = "Микрорайон")
	@Column(name = "microdistrict")
	private String microdistrict;
	
	@MetaFieldName(label = "Улица")
	@Column(name = "street")
	private String street;
	
	@MetaFieldName(label = "Дом")
	@Column(name = "house")
	private String house;
	
	@MetaFieldName(label = "Кабинет (офис)")
	@Column(name = "room")
	private String room;
	
	@MetaFieldName(label = "Телефоны")
	@Column(name = "phones")
	private String phones;
	
	@MetaFieldName(label = "Адрес электронной почты 1")
	@Column(name = "email_1")
	private String email1;
	
	@MetaFieldName(label = "Адрес электронной почты 2")
	@Column(name = "email_2")
	private String email2;
	
	@MetaFieldName(label = "Дата приказа о последней операции о регистрации, перерегистрации и ликвидации")
	@Column(name = "order_date")
	private Date orderDate;
	
	@MetaFieldName(label = "Дата первичной регистрации")
	@Column(name = "first_order_date")
	private Date firstOrderDate;
	
	@MetaFieldName(label = "Категория", selectClassName = "Minjust_category")
	@Column(name = "category_id", nullable = true)
	private long categoryId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="category_id", referencedColumnName="id", insertable=false, updatable=false)
	private Minjust_category minjust_category;
	
	
	@MetaFieldName(label = "Форма собственности", selectClassName = "Minjust_ownership")
	@Column(name = "ownership_id", nullable = true)
	private long ownershipId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="ownership_id", referencedColumnName="id", insertable=false, updatable=false)
	private Minjust_ownership minjust_ownership;
	
	@MetaFieldName(label = "Руководитель")
	@Column(name = "chief")
	private String chief;
	
	@MetaFieldName(label = "ИНН руководителя")
	@Column(name = "chief_tin")
	private String chief_tin;
	
	@MetaFieldName(label = "Наименование кода экономической деятельности ")
	@Column(name = "base_bus")
	private String baseBus;
	
	@MetaFieldName(label = "Код экономической деятельности ")
	@Column(name = "base_bus_code")
	private String baseBusCode;
	
	@MetaFieldName(label = "Количество учредитлей (физические лица)")
	@Column(name = "ind_founders")
	private long indFounders;
	
	@MetaFieldName(label = "Колчество учредитлей (юридические лица)")
	@Column(name = "jur_founders")
	private long jurFounders;
	
	@MetaFieldName(label = "Общее количество учредителей")
	@Column(name = "total_founders")
	private long totalFounders;
	
	@MetaFieldName(label = "Дополнительное текстовое описание (примечание)")
	@Column(name = "description")
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullNameGl() {
		return fullNameGl;
	}

	public void setFullNameGl(String fullNameGl) {
		this.fullNameGl = fullNameGl;
	}

	public String getShortNameGl() {
		return shortNameGl;
	}

	public void setShortNameGl(String shortNameGl) {
		this.shortNameGl = shortNameGl;
	}

	public String getFullNameOl() {
		return fullNameOl;
	}

	public void setFullNameOl(String fullNameOl) {
		this.fullNameOl = fullNameOl;
	}

	public String getShortNameOl() {
		return shortNameOl;
	}

	public void setShortNameOl(String shortNameOl) {
		this.shortNameOl = shortNameOl;
	}

	public String getForeignPart() {
		return foreignPart;
	}

	public void setForeignPart(String foreignPart) {
		this.foreignPart = foreignPart;
	}

	public String getRegistrCode() {
		return registrCode;
	}

	public void setRegistrCode(String registrCode) {
		this.registrCode = registrCode;
	}

	public String getStatSubCode() {
		return statSubCode;
	}

	public void setStatSubCode(String statSubCode) {
		this.statSubCode = statSubCode;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getMicrodistrict() {
		return microdistrict;
	}

	public void setMicrodistrict(String microdistrict) {
		this.microdistrict = microdistrict;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getFirstOrderDate() {
		return firstOrderDate;
	}

	public void setFirstOrderDate(Date firstOrderDate) {
		this.firstOrderDate = firstOrderDate;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getOwnershipId() {
		return ownershipId;
	}

	public void setOwnershipId(long ownershipId) {
		this.ownershipId = ownershipId;
	}

	public String getChief() {
		return chief;
	}

	public void setChief(String chief) {
		this.chief = chief;
	}

	public String getChief_tin() {
		return chief_tin;
	}

	public void setChief_tin(String chief_tin) {
		this.chief_tin = chief_tin;
	}

	public String getBaseBus() {
		return baseBus;
	}

	public void setBaseBus(String baseBus) {
		this.baseBus = baseBus;
	}

	public String getBaseBusCode() {
		return baseBusCode;
	}

	public void setBaseBusCode(String baseBusCode) {
		this.baseBusCode = baseBusCode;
	}

	public long getIndFounders() {
		return indFounders;
	}

	public void setIndFounders(long indFounders) {
		this.indFounders = indFounders;
	}

	public long getJurFounders() {
		return jurFounders;
	}

	public void setJurFounders(long jurFounders) {
		this.jurFounders = jurFounders;
	}

	public long getTotalFounders() {
		return totalFounders;
	}

	public void setTotalFounders(long totalFounders) {
		this.totalFounders = totalFounders;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Minjust_category getMinjust_category() {
		return minjust_category;
	}

	public void setMinjust_category(Minjust_category minjust_category) {
		this.minjust_category = minjust_category;
	}

	public Minjust_ownership getMinjust_ownership() {
		return minjust_ownership;
	}

	public void setMinjust_ownership(Minjust_ownership minjust_ownership) {
		this.minjust_ownership = minjust_ownership;
	}
	
}


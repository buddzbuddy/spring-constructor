package com.webdatabase.dgz.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@MetaFieldName(label = "Фамилия")
	@Column(name = "surname")
	private String surname;
	
	@MetaFieldName(label = "Имя")
	@Column(name = "name")
	private String name;

	@Nullable
	@MetaFieldName(label = "Отчество")
	@Column(name = "patronymic")
	private String patronymic;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_member_id", nullable = true)
	private Set<MsecDetail> msecDetails;
	
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

	public Set<MsecDetail> getMsecDetails() {
		return msecDetails;
	}

	public void setMsecDetails(Set<MsecDetail> msecDetails) {
		this.msecDetails = msecDetails;
	}
	
}

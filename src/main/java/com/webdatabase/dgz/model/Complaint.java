package com.webdatabase.dgz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "complaints")
@IsMetaClass(label = "Жалобы закупающих орг.")
public class Complaint {
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "Тип жалобы", selectClassName = "ComplaintType")
	@Column(name = "complaint_type_id", nullable = true)
	private long complaintTypeId;
	
	@MetaFieldName(label = "Закупающая организация", selectClassName = "Buyer")
	@Column(name = "buyer_id", nullable = true)
	private long buyerId;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private long supplierId;
	
	@MetaFieldName(label = "Содержание жалобы")
	@Column(name = "description")
	private String description;
	
	
}

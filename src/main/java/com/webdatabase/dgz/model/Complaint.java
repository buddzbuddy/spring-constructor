package com.webdatabase.dgz.model;

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
@Table(name = "complaints")
@IsMetaClass(label = "Жалобы закупающих орг.")
public class Complaint  extends AuditModel {
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "Тип жалобы", selectClassName = "ComplaintType")
	@Column(name = "complaint_type_id", nullable = true)
	private long complaintTypeId;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="complaint_type_id", referencedColumnName="id", insertable=false, updatable=false)
	private ComplaintType complaintType;
	
	@MetaFieldName(label = "Закупающая организация", selectClassName = "Buyer")
	@Column(name = "buyer_id", nullable = true)
	private long buyerId;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="buyer_id", referencedColumnName="id", insertable=false, updatable=false)
	private Buyer buyer;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private long supplierId;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="supplier_id", referencedColumnName="id", insertable=false, updatable=false)
	private Supplier supplier;
	
	@MetaFieldName(label = "Содержание жалобы")
	@Column(name = "description")
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(long complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public ComplaintType getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(ComplaintType complaintType) {
		this.complaintType = complaintType;
	}
	
	
}

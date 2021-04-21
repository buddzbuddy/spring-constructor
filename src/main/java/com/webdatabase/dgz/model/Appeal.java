package com.webdatabase.dgz.model;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "appeals")
@IsMetaClass(label = "Жалобы")
public class Appeal extends AuditModel{
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "Описание")
	@Column(name = "description")
	private String description;
	
	
	@MetaFieldName(label = "Закупающая организация", selectClassName = "ProcuringEntity", selectClassFieldName="name")
	@Column(name="procuring_entity_id", nullable=true)
	private long procuringEntityId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="procuring_entity_id", referencedColumnName="id", insertable=false, updatable=false)
	private ProcuringEntity procuringEntity;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private long supplierId;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getProcuringEntityId() {
		return procuringEntityId;
	}

	public void setProcuringEntityId(long procuringEntity) {
		this.procuringEntityId = procuringEntity;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public ProcuringEntity getProcuringEntity() {
		return procuringEntity;
	}

	public void setProcuringEntity(ProcuringEntity procuringEntity) {
		this.procuringEntity = procuringEntity;
	}
	
	
}

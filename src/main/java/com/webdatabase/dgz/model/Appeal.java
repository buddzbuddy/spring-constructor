package com.webdatabase.dgz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
@IsMetaClass(label = "Обращение")
public class Appeal extends AuditModel{
	@MetaFieldName(label = "ID")
	@Id
    @GeneratedValue(generator = "appeal_generator")
    @SequenceGenerator(
            name = "appeal_generator",
            sequenceName = "appeal_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
	private Long id;
	
	@MetaFieldName(label = "Описание")
	private String description;
	
	
	@MetaFieldName(label = "Закупающая организация", selectClassName = "ProcuringEntity")
	@Column(name="procuring_entity_id", nullable=true)
	private Long procuringEntityId;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private Long supplierId;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getProcuring_entityId() {
		return procuringEntityId;
	}

	public void setProcuring_entityId(Long procuringEntity) {
		this.procuringEntityId = procuringEntity;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	
}

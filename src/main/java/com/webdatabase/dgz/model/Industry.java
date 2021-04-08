package com.webdatabase.dgz.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "industries")
@IsMetaClass(label = "Отрасль")
public class Industry extends AuditModel{
	@MetaFieldName(label = "ID")
	@Id
    @GeneratedValue(generator = "industry_generator")
    @SequenceGenerator(
            name = "industry_generator",
            sequenceName = "industry_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
	private Long id;
	
	@MetaFieldName(label = "Наименование отрасли")
	private String name;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="industry_id", nullable=true)
	private Set<Supplier> suppliers;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Set<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
}

package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "currencies")
@IsMetaClass(label = "Валюта")
public class Currency extends AuditModel {
	@MetaFieldName(label = "ID")
	@Id
    @GeneratedValue(generator = "currency_generator")
    @SequenceGenerator(
            name = "currency_generator",
            sequenceName = "currency_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    private Long id;
	
	@MetaFieldName(label = "Наименование валюты")
    private String name;

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
}

package com.webdatabase.dgz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "datasources")
@IsMetaClass(label = "Источник данных")
public class Datasource extends AuditModel {
	
	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@MetaFieldName(label = "Наименование")
	@NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "name")
    private String name;
	
	@MetaFieldName(label = "Описание")
	@Column(columnDefinition = "text")
    private String description;
	
    public Long getId(){
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getName(){
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public String getDescription(){
    	return description;
    }
    
    public void setDescription(String description){
    	this.description = description;
    }
}
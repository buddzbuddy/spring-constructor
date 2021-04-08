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
@Table(name = "license_types")
@IsMetaClass(label = "Тип лицензии")
public class LicenseType extends AuditModel{
	@MetaFieldName(label = "ID")
	@Id
    @GeneratedValue(generator = "license_type_generator")
    @SequenceGenerator(
            name = "license_type_generator",
            sequenceName = "license_type_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
	private Long id;
	
	@MetaFieldName(label = "Наименование типа лицензии")
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_type_id", nullable = true)
	private Set<License> licenses;
	
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

	public Set<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(Set<License> licenses) {
		this.licenses = licenses;
	}
	
}

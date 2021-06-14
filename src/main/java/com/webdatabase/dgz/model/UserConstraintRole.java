package com.webdatabase.dgz.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.webdatabase.dgz.model.base.AuditModel;

@Entity
@Table(name = "user_constraint_role")
public class UserConstraintRole extends AuditModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Set<UserConstraint> userConstraints;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserConstraint> getUserConstraints() {
		return userConstraints;
	}

	public void setUserConstraints(Set<UserConstraint> userConstraints) {
		this.userConstraints = userConstraints;
	}
}

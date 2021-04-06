package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.OwnershipType;

@Repository
public interface OwnershipTypeRepository extends JpaRepository<OwnershipType, Long> {	
	
}

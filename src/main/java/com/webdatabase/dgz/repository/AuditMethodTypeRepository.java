package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.AuditMethodType;

@Repository
public interface AuditMethodTypeRepository extends JpaRepository<AuditMethodType, Long>{

}

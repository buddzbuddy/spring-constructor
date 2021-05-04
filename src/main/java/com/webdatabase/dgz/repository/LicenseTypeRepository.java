package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.LicenseType;

@Repository
public interface LicenseTypeRepository  extends JpaRepository<LicenseType, Long>{

}

package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.MemberType;

@Repository
public interface MemberTypeRepository extends JpaRepository<MemberType, Long>{

}

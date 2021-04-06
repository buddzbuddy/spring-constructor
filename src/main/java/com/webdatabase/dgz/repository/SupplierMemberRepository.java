package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.SupplierMember;

@Repository
public interface SupplierMemberRepository extends JpaRepository<SupplierMember, Long> {

}

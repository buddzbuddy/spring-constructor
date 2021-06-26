package com.webdatabase.dgz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Complaint;
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
	List<Complaint> findBySupplierId(long supplierId);
	List<Complaint> findByBuyerId(long buyerId);
}

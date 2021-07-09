package com.webdatabase.dgz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webdatabase.dgz.model.StiRequest;

public interface StiRequestRepository extends JpaRepository<StiRequest, Long> {
	List<StiRequest> findByInn(String inn);
}

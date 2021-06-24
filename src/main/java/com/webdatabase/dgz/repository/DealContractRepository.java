package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.DealContract;

@Repository
public interface DealContractRepository extends JpaRepository<DealContract, Long> {

}

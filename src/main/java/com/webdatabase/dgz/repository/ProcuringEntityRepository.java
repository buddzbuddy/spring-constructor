package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.ProcuringEntity;

@Repository
public interface ProcuringEntityRepository extends JpaRepository<ProcuringEntity, Long> {

}

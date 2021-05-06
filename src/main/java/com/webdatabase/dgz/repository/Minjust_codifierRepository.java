package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Minjust_category;
@Repository
public interface Minjust_codifierRepository extends JpaRepository<Minjust_category, Long>{

}

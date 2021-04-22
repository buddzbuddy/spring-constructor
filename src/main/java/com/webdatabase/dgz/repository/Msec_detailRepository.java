package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.MsecDetail;

@Repository
public interface Msec_detailRepository extends JpaRepository<MsecDetail, Long> {

}

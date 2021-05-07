package com.webdatabase.dgz.repository;

import com.webdatabase.dgz.model.LocalGrantedSource;
import com.webdatabase.dgz.model.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalGrantedSourceRepository extends JpaRepository<LocalGrantedSource, Long> {
    List<LocalGrantedSource> findBySupplierId(long supplierId);
    List<LocalGrantedSource> findBySupplierIdAndSourceType(long supplierId, SourceType sourceType);
}

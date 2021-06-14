package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.UserConstraint;
@Repository
public interface UserConstraintRepository extends JpaRepository<UserConstraint, Long>{

}

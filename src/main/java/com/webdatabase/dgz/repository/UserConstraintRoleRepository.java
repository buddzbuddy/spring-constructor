package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.UserConstraintRole;
@Repository
public interface UserConstraintRoleRepository extends JpaRepository<UserConstraintRole, Long>{

}

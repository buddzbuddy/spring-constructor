package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}

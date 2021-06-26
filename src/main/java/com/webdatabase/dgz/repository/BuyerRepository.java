package com.webdatabase.dgz.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Buyer;
@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findByInn(String inn);
    Optional<Buyer> findByKeycloakUserId(String keycloakUserId);
}

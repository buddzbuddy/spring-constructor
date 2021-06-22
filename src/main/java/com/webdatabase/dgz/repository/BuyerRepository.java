package com.webdatabase.dgz.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.webdatabase.dgz.model.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findByInn(String inn);
    Optional<Buyer> findByKeycloakUserId(String keycloakUserId);
}

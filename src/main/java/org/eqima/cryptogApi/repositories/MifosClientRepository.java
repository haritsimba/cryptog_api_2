package org.eqima.cryptogApi.repositories;

import org.eqima.cryptogApi.entities.MifosClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MifosClientRepository extends JpaRepository<MifosClient, Long> {
}

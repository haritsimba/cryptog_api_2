package org.eqima.cryptogApi.repositories;

import org.eqima.cryptogApi.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
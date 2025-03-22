package org.eqima.cryptogApi.repositories;

import org.eqima.cryptogApi.entities.Transaction;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

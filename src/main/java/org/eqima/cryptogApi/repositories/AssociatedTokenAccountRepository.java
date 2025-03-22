package org.eqima.cryptogApi.repositories;

import org.eqima.cryptogApi.entities.AssociatedTokenAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociatedTokenAccountRepository extends JpaRepository<AssociatedTokenAccount,Long> {
}

package org.eqima.cryptogApi.services;

import org.eqima.cryptogApi.entities.AssociatedTokenAccount;
import org.eqima.cryptogApi.repositories.AssociatedTokenAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociatedTokenAccountService {
    @Autowired
    AssociatedTokenAccountRepository associatedTokenAccountRepository;

    public AssociatedTokenAccount saveAssociatedTokenAccount(AssociatedTokenAccount associatedTokenAccount){
        return associatedTokenAccountRepository.save(associatedTokenAccount);
    }
}

package org.eqima.cryptogApi.services;

import jakarta.transaction.Transactional;
import org.eqima.cryptogApi.dto.AssociatedTokenAccountDto;
import org.eqima.cryptogApi.dto.ServiceResponse;
import org.eqima.cryptogApi.dto.response.VkaWalletCreationResponseDto;
import org.eqima.cryptogApi.entities.AssociatedTokenAccount;
import org.eqima.cryptogApi.entities.Wallet;
import org.eqima.cryptogApi.enums.OrchestratorDataTypes.WalletForUserCreation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletOrchestratorService {
    @Autowired
    WalletService walletService;
    @Autowired
    VkaApiService vkaApiService;
    @Autowired
    UserService userService;
    @Autowired
    AssociatedTokenAccountService associatedTokenAccountService;

    @Transactional
    public ServiceResponse<WalletForUserCreation,Wallet> createWalletForUserProcess(String username, String phoneNumber, String walletPIN){
        // Recuperation de l'utilisateur
        UserRepresentation user = userService.getUserByUsername(username);
        if(username==null){
            return new ServiceResponse<>(WalletForUserCreation.USER_DOES_NOT_EXIST,null);
        }
        // Creation du wallet dans l'api de vka
        VkaWalletCreationResponseDto vkaWallet = vkaApiService.createWallet();
        // Enregistrement du wallet créé
        Wallet wallet = walletService.saveWallet(user.getUsername(),walletPIN,phoneNumber,vkaWallet.getWalletOwnerPrivateKey());

        // Enregistrement des ATAs
        for (AssociatedTokenAccountDto ata : vkaWallet.getTokenAccountInfo()){
            AssociatedTokenAccount tokenAccount = new AssociatedTokenAccount();
            tokenAccount.setMintAddress(ata.getMint());
            tokenAccount.setTokenAccountAddress(ata.getTokenAccount());
            tokenAccount.setMintName(ata.getMint());
            tokenAccount.setWallet(wallet);
            associatedTokenAccountService.saveAssociatedTokenAccount(tokenAccount);
        }

        // Mise à jour de l'utilisateur
        userService.addUserATAs(user,vkaWallet.getTokenAccountInfo());

        return new ServiceResponse<>(WalletForUserCreation.OK,walletService.getWalletById(wallet.getId()));
    }
}

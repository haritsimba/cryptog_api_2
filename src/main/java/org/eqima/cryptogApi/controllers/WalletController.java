package org.eqima.cryptogApi.controllers;

import org.eqima.cryptogApi.dto.ApiResponseError;
import org.eqima.cryptogApi.dto.ServiceResponse;
import org.eqima.cryptogApi.dto.request.CreateWalletForUserRequest;
import org.eqima.cryptogApi.entities.Wallet;
import org.eqima.cryptogApi.enums.OrchestratorDataTypes;
import org.eqima.cryptogApi.services.WalletOrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "wallet")
public class WalletController {
    @Autowired
    WalletOrchestratorService walletOrchestratorService;
    @PostMapping(path = "")
    public ResponseEntity<?> createWalletForUser(@RequestBody CreateWalletForUserRequest user){
        ServiceResponse<OrchestratorDataTypes.WalletForUserCreation, Wallet> serviceResponse = walletOrchestratorService.createWalletForUserProcess(user.getUsername(), user.getPhoneNumber(), user.getPIN());
        switch (serviceResponse.getStatus()){
            case USER_DOES_NOT_EXIST -> {
                return ResponseEntity.badRequest().body(new ApiResponseError("L'utilisateur n'existe pas"));
            }
            case OK -> {
                return ResponseEntity.ok().body(serviceResponse.getData());
            }
            default -> {
                return ResponseEntity.internalServerError().body(new ApiResponseError("Erreur lors de création du wallet"));
            }
        }
    }
}

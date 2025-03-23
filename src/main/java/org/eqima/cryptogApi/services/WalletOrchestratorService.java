package org.eqima.cryptogApi.services;

import org.springframework.beans.factory.annotation.Autowired;

public class WalletOrchestratorService {
    @Autowired
    WalletService walletService;
    @Autowired
    VkaApiService vkaApiService;
    @Autowired
    UserService userService;


}

package org.eqima.cryptogApi.services;

import org.eqima.cryptogApi.dto.response.VkaWalletCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class VkaApiService {
    @Autowired
    WebClient vkaWebclient;

    public VkaWalletCreationDto createWallet(){
        ResponseEntity<VkaWalletCreationDto> walletResponse =  vkaWebclient.post()
                .uri("/wallet")
                .retrieve()
                .toEntity(VkaWalletCreationDto.class)
                .block();


        if(walletResponse.getStatusCode().is2xxSuccessful()){
            return walletResponse.getBody();
        } else if (walletResponse.getStatusCode().is4xxClientError()) {
            return null;
        }else {
            return null;
        }
    }
}

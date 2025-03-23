package org.eqima.cryptogApi.services;

import org.eqima.cryptogApi.dto.response.VkaWalletCreationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VkaApiService {
    @Autowired
    WebClient vkaWebclientApi;

    public VkaWalletCreationResponseDto createWallet() {
        ResponseEntity<VkaWalletCreationResponseDto> walletResponse = vkaWebclientApi.post()
                .uri("/wallet")
                .retrieve()
                .toEntity(VkaWalletCreationResponseDto.class)
                .block();
        if(walletResponse == null){
            return null;
        }
        if (walletResponse.getStatusCode().is2xxSuccessful()) {
            return walletResponse.getBody();
        } else {
            System.out.println("error status : " + walletResponse.getStatusCode() + "error message : " + walletResponse.getBody());
            return null;
        }
    }
}

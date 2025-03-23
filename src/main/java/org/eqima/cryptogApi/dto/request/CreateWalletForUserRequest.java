package org.eqima.cryptogApi.dto.request;

import lombok.Data;

@Data
public class CreateWalletForUserRequest {
    String username;
    String PIN;
    String phoneNumber;
}

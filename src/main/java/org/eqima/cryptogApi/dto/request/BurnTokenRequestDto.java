package org.eqima.cryptogApi.dto.request;

import lombok.Data;

@Data
public class BurnTokenRequestDto {
    String walletAddress;
    Long amount;
    Integer[] walletKey;
}

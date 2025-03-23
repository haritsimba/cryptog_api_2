package org.eqima.cryptogApi.dto.response;

import lombok.Data;
import org.eqima.cryptogApi.dto.AssociatedTokenAccountDto;

import java.util.List;

@Data
public class VkaWalletCreationResponseDto {
    Long timestamp;
    String status;
    String signature;
    String walletOwner;
    List<AssociatedTokenAccountDto> tokenAccountInfo;
    Integer[] walletOwnerPrivateKey;
}

package org.eqima.cryptogApi.dto.request;

import lombok.Data;

@Data
public class TransferTokenRequestDto {
    String senderAddress;
    String receiverAddress;
    Long amount;
    Integer[] senderKey;
}

package org.eqima.cryptogApi.dto;

import lombok.Data;

@Data
public class AssociatedTokenAccountDto {
    String mintName;
    String mint;
    String tokenAccount;
}
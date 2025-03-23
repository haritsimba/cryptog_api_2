package org.eqima.cryptogApi.dto.request;

import lombok.Data;

@Data
public class UserCreationInfoDto {
    private String username;
    private String phone;
    private String name;
    private String surname;
    private String email;
}

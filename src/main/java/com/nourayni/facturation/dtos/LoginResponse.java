package com.nourayni.facturation.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class LoginResponse {
    private String token;
    private String refreshToken;
}

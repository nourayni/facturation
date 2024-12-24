package com.nourayni.facturation.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter @Builder
public class UserRequest {
    private String username;
    private String password;
}

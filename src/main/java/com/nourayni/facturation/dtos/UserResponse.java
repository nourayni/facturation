package com.nourayni.facturation.dtos;

import java.util.List;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter @Builder
public class UserResponse {
    private String id;
    private String username;
    private String password;
    private List<RoleDTO> rolesDTO;
}

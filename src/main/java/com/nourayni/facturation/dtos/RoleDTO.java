package com.nourayni.facturation.dtos;

import com.nourayni.facturation.RoleName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class RoleDTO {
    private String id;
    private RoleName roleName;

}

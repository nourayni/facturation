package com.nourayni.facturation.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nourayni.facturation.dtos.RoleDTO;
import com.nourayni.facturation.dtos.UserResponse;
import com.nourayni.facturation.entity.Role;
import com.nourayni.facturation.entity.User;
@Service
public class UserRoleMapper {
    public RoleDTO roleToRoleTDO(Role role){
        return RoleDTO.builder()
            .id(role.getId())
            .roleName(role.getRoleName())
        .build();
    }

    public UserResponse userToUserResponse(User user){
        List<RoleDTO> rolesDTO = user.getRoles().stream().map(role ->
                                roleToRoleTDO(role)).collect(Collectors.toList());

        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .rolesDTO(rolesDTO)
        .build();
    }

}

package com.nourayni.facturation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nourayni.facturation.entity.Role;

public interface RoleRepository extends JpaRepository<Role,String> {
    Optional<Role> findByRoleName(String roleName);

}

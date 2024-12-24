package com.nourayni.facturation.userservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nourayni.facturation.dtos.UserRequest;
import com.nourayni.facturation.dtos.UserResponse;
import com.nourayni.facturation.entity.Role;
import com.nourayni.facturation.entity.User;
import com.nourayni.facturation.mapper.UserRoleMapper;
import com.nourayni.facturation.repository.RoleRepository;
import com.nourayni.facturation.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleMapper mapper;

    @Override
    public UserResponse saveUser(UserRequest request) {

        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow();
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .roles(roles)
        .build();
        userRepository.save(user);
        return mapper.userToUserResponse(user);
    }

    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }

}

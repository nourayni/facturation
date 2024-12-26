package com.nourayni.facturation.userservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nourayni.facturation.RoleName;
import com.nourayni.facturation.dtos.LoginRequest;
import com.nourayni.facturation.dtos.LoginResponse;
import com.nourayni.facturation.dtos.RoleDTO;
import com.nourayni.facturation.dtos.UserRequest;
import com.nourayni.facturation.dtos.UserResponse;
import com.nourayni.facturation.entity.Role;
import com.nourayni.facturation.entity.User;
import com.nourayni.facturation.jwt.JwtUtils;
import com.nourayni.facturation.mapper.UserRoleMapper;
import com.nourayni.facturation.repository.RoleRepository;
import com.nourayni.facturation.repository.UserRepository;
import com.nourayni.facturation.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor @Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    public UserResponse saveUser(UserRequest request) {

        Role role = roleRepository.findByRole(RoleName.ROLE_USER.name()).orElseThrow();
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
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // si l'authentification est réussie, on retourne un token et un refresh token
        if(authentication.isAuthenticated()){
            return LoginResponse.builder()
                .token(jwtUtils.generateToken(authentication))
                .refreshToken(jwtUtils.refreshToken(authentication))
                .build();
        }
        // sinon on retourne une exception
    return null;
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        if (jwtUtils.validateToken(refreshToken)) {
            // on récupère le username à partir du refresh token
            String username = jwtUtils.getUsernameFromToken(refreshToken);
            // on charge les détails de l'utilisateur
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // on crée une authentification
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());
                // on met l'authentification dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.isAuthenticated()) {
                // si l'authentification est réussie, on retourne un token et un refresh token
                return LoginResponse.builder()
                    .token(jwtUtils.generateToken(authentication))
                    .refreshToken(jwtUtils.refreshToken(authentication))
                    .build();
            }else {
                // sinon on retourne une exception
                return null;
            }
        } else {
            // si le refresh token n'est pas valide, on retourne une exception
            return null;
        }
    }

    @Override
    public RoleDTO saveRole(String roleName) {
        Role role = Role.builder()
            .role(roleName)
            .build();
        roleRepository.save(role);
        return mapper.roleToRoleTDO(role);
    }

}

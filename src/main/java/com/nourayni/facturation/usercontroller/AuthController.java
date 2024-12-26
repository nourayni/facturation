package com.nourayni.facturation.usercontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.nourayni.facturation.RoleName;
import com.nourayni.facturation.dtos.LoginRequest;
import com.nourayni.facturation.dtos.LoginResponse;
import com.nourayni.facturation.dtos.RefreshToken;
import com.nourayni.facturation.dtos.RoleDTO;
import com.nourayni.facturation.dtos.RoleRequest;
import com.nourayni.facturation.dtos.UserRequest;
import com.nourayni.facturation.dtos.UserResponse;
import com.nourayni.facturation.entity.Role;
import com.nourayni.facturation.jwt.JwtUtils;
import com.nourayni.facturation.userservice.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    // @GetMapping("/username/{token}")
    // public ResponseEntity<String> getUsernameFromToken(@PathVariable String token){
    //     return ResponseEntity.ok(jwtUtils.getUsernameFromToken(token));
    // }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> saveUser( @RequestBody UserRequest user){
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/role")
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleRequest roleName){
        return ResponseEntity.ok(userService.saveRole(roleName.getRoleName()));
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshToken refreshToken){
        return ResponseEntity.ok(userService.refreshToken(refreshToken.getRefreshToken()));
    }
}

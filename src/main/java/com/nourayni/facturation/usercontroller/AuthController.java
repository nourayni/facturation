package com.nourayni.facturation.usercontroller;

import org.springframework.http.HttpStatus;
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
        try {
            UserResponse userResponse = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/role")
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleRequest roleName){
        try {
            RoleDTO role = userService.saveRole(roleName.getRoleName());
            return ResponseEntity.status(HttpStatus.CREATED).body(role);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        try {
            LoginResponse loginResponse = userService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshToken refreshToken){
        try {
            LoginResponse loginResponse = userService.refreshToken(refreshToken.getRefreshToken());
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}

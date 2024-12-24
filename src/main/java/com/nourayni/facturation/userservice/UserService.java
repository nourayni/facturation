package com.nourayni.facturation.userservice;

import java.util.List;

import com.nourayni.facturation.dtos.LoginRequest;
import com.nourayni.facturation.dtos.LoginResponse;
import com.nourayni.facturation.dtos.UserRequest;
import com.nourayni.facturation.dtos.UserResponse;
import com.nourayni.facturation.entity.User;

public interface UserService {

    UserResponse saveUser(UserRequest request);
    List<User> getAllUsers();

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(String refreshToken);

}

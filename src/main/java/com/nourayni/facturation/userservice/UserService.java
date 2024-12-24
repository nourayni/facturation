package com.nourayni.facturation.userservice;

import java.util.List;

import com.nourayni.facturation.dtos.UserRequest;
import com.nourayni.facturation.dtos.UserResponse;
import com.nourayni.facturation.entity.User;

public interface UserService {

    UserResponse saveUser(UserRequest request);
    List<User> getAllUsers();

}

package com.siduska.ehealthwallet.service;

import com.siduska.ehealthwallet.dto.RegisterUserRequest;
import com.siduska.ehealthwallet.dto.UpdateUserRequest;
import com.siduska.ehealthwallet.dto.UserDto;

public interface UserService {

    String getUserName();
    UserDto getUserById(Long id);
    Iterable<UserDto> getAllUsers();
    UserDto createUser(RegisterUserRequest request);
    UserDto updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    Boolean existsByEmail(String email);
}
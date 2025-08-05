package com.siduska.ehealthwallet.mapper;

import com.siduska.ehealthwallet.dto.RegisterUserRequest;
import com.siduska.ehealthwallet.dto.UpdateUserRequest;
import com.siduska.ehealthwallet.dto.UserDto;
import com.siduska.ehealthwallet.entitiy.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
    User toEntity(RegisterUserRequest userRequest);
    void updateUser(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}

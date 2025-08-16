package com.siduska.ehealthwallet.service.impl;

import com.siduska.ehealthwallet.dto.RegisterUserRequest;
import com.siduska.ehealthwallet.dto.UpdateUserRequest;
import com.siduska.ehealthwallet.dto.UserDto;
import com.siduska.ehealthwallet.mapper.UserMapper;
import com.siduska.ehealthwallet.repository.UserRepository;
import com.siduska.ehealthwallet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public String getUserName()  {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetails ? ((UserDetails) principal).getUsername()
                                                : principal.toString();
    }


    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElse(null);
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return userRepository.findAll()
               .stream().map(userMapper::toUserDto).toList();
    }

    @Override
    public UserDto createUser(RegisterUserRequest request) {
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        userMapper.updateUser(request, user);
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}

package com.siduska.ehealthwallet.service.impl;

import com.siduska.ehealthwallet.entitiy.User;
import com.siduska.ehealthwallet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    public String getUserName()  {
        return "userName";
    }

    @Override
    public User build() {
        User user = new User();
        user.setName("Ferko");
        user.setEmail("ferko@gmail.com");
        user.setPassword("ferko");
        return user;
    }
}

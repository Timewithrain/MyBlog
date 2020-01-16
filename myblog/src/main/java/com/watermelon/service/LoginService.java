package com.watermelon.service;

import com.watermelon.DAO.UserRepository;
import com.watermelon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public User checkUser(String username, String password){
        User user = userRepository.findUserByUsernameAndPassword(username,password);
        return user;
    }

}

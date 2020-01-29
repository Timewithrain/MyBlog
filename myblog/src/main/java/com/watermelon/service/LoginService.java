package com.watermelon.service;

import com.watermelon.DAO.UserRepositoryInterface;
import com.watermelon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired(required = true)
    public UserRepositoryInterface userRepository;

    public User checkUser(String username, String password){
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user;
    }

}

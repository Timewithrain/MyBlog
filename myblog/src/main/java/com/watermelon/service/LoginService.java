package com.watermelon.service;

import com.watermelon.DAO.UserRepositoryInterface;
import com.watermelon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    public UserRepositoryInterface userRepository;

    public User checkUser(String username, String password){
        int a = 1 + 3;
        int b = a + 1;
        System.out.println(b);
        User user = userRepository.findByUsernameAndPassword(username,password);
        System.out.println(b+2);
        return user;
    }

}

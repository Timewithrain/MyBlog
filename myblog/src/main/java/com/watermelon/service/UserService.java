package com.watermelon.service;

import com.watermelon.DAO.UserRepository;
import com.watermelon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired(required = true)
    public UserRepository userRepository;

    //此方法不开启事务，并且以只读方式获取数据，修改对象属性后数据库不做修改
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public User getUser(Long id){
        User user = userRepository.getOne(id);
        user.setPassword(encodePassword(user.getPassword()));
        return user;
    }

    public User checkUser(String username, String password){
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user;
    }

    private String encodePassword(String password){
        int length = password.length();
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<length;i++){
            sb.append("*");
        }
        return sb.toString();
    }

}

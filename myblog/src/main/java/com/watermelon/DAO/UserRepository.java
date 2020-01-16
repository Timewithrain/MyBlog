package com.watermelon.DAO;

import com.watermelon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//定义UserRepository接口继承自JpaRepository，用于获取User实现数据库操作
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsernameAndPassword(String username,String password);

}

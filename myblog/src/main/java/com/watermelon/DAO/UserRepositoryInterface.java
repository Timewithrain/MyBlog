package com.watermelon.DAO;

import com.watermelon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//定义UserRepository接口继承自JpaRepository，用于获取User实现数据库操作
public interface UserRepositoryInterface extends JpaRepository<User, Long> {

    //通过特定的方法接口，调用jpa根据username和password查询User
    User findByUsernameAndPassword(String username,String password);

}

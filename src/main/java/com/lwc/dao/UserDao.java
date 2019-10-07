package com.lwc.dao;

import com.lwc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2019/9/5.
 */
public interface UserDao extends JpaRepository<User,Long>,JpaSpecificationExecutor<User>{
    User findByLoginName(String loginName);
}

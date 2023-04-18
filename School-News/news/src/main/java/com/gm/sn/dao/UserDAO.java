package com.gm.sn.dao;

import com.gm.sn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDAO extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    User getByUsernameAndPassword(String username,String password);

    @Query(value = "update user set username=NULL,password=NULL,salt=NULL," +
            "phone=NULL,email=NULL,enabled=NULL " +
            "where id=?1 ", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteByIdunlessNameZH(int id);

    @Query(value = "select * from `user` where username is not null", nativeQuery = true)
    @Transactional
    @Modifying
    List<User> findAllnotNull();
}

package com.gm.sn.dao;

import com.gm.sn.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;

public interface AdminRoleDAO extends JpaRepository<AdminRole, Integer> {
    AdminRole findById(int id);

    @Query(value = "DELETE FROM `admin_user_role` where rid = ?1 ", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteRole_Userbyid(int rid);

    @Query(value = "DELETE FROM `admin_role_menu` where rid = ?1 ", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteRole_Menubyid(int rid);
}

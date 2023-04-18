package com.gm.sn.dao;

import com.gm.sn.entity.Audit_status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuditStatusDAO extends JpaRepository<Audit_status,Integer> {

    @Query(value = "SELECT `status` FROM `audit_status`", nativeQuery = true)
    @Transactional
    @Modifying
    List<String> AuditList();

//    @Query(value = "SELECT `status` FROM `audit_status` where id=?1", nativeQuery = true)
//    @Transactional
//    @Modifying
//    String findStatusById(int aid);

    @Query(value = "SELECT * FROM `audit_status` where id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    Audit_status findOneById(int aid);

    @Query(value = "SELECT `status` FROM `audit_status` where id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    String findstatusByid(int aid);
//    List<Book> findAllByCategory(Category category);
//    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}

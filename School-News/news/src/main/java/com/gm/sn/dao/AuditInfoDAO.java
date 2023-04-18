package com.gm.sn.dao;

import com.gm.sn.entity.AuditInfo;
import com.gm.sn.entity.Audit_status;
import com.gm.sn.entity.JotterArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface AuditInfoDAO extends JpaRepository<AuditInfo,Integer> {

//    @Query(value = "insert into article_audit_info(nid,news_title,uid,user_name_zh,operate_audit) values (?1,?2,?3,?4,'添加新文章') ", nativeQuery = true)
//    @Transactional
//    @Modifying
//    void insertNewAudit(int nid, String news_title,int uid,String user_name_zh);

    @Query(value = "insert into article_audit_info(nid,uid,operate_audit,operate_date) values (?1,?2,'添加新文章',now()) ", nativeQuery = true)
    @Transactional
    @Modifying
    void insertNewAudit(int nid,int uid);

    @Query(value = "insert into article_audit_info(nid,uid,operate_audit,audit_mind,operate_date) values (?2,?3,?1,?4,now()) ", nativeQuery = true)
    @Transactional
    @Modifying
    void insertOperate(String audit_status, int nid, int uid, String auditMind);

    @Query(value = "insert into article_audit_info(nid,uid,operate_audit,operate_date) values (?2,?3,?1,now()) ", nativeQuery = true)
    @Transactional
    @Modifying
    void insertOperate_PublishorRevoke(String status, int nid, int uid);

    @Query(value = "insert into article_audit_info(nid,uid,operate_audit,operate_date) values (?1,?2,'文章修改',now()) ", nativeQuery = true)
    @Transactional
    @Modifying
    void insertoldAudit(int nid,int uid);

    @Query(value = "SELECT * FROM `article_audit_info` limit ?1,?2", nativeQuery = true)
    @Transactional
    @Modifying
    List<AuditInfo> findAllnoTime(int page, int size);

    @Query(value = "SELECT count(*) FROM `article_audit_info`", nativeQuery = true)
    Integer articlesTotalNum();

    @Query(value = "SELECT * FROM `article_audit_info` where operate_date>=?3 AND operate_date<=?4 limit ?1,?2", nativeQuery = true)
    @Transactional
    @Modifying
    List<AuditInfo> findAllinTime(int page, int size, Date sdate_start, Date edate_end);

    @Query(value = "SELECT count(*) FROM `article_audit_info` where operate_date>=?1 AND operate_date<=?2", nativeQuery = true)
    Integer articlesTotalNumIntime(Date sdate_start, Date edate_end);

    @Query(value = "insert into article_audit_info(nid,uid,operate_audit,operate_date) values (?1,?2,'文章删除',now()) ", nativeQuery = true)
    @Transactional
    @Modifying
    void insertdeleteOperate(int nid, int uid);

    @Query(value = "SELECT * FROM `article_audit_info` where uid=?1", nativeQuery = true)
    @Transactional
    @Modifying
    List<AuditInfo> findbyUid(int uid);


    @Query(value = "SELECT id FROM `news_article` where " +
            "(article_title like ?1)AND cid is null " +
            "limit ?2,?3",
            nativeQuery = true)
    @Transactional
    @Modifying
    List<Integer> findvalueLikenoTimeOperate(String value, int page, int size);


    @Query(value = "SELECT count(*) FROM `news_article` where " +
            "article_title like ?1 " +
            "AND cid is null",
            nativeQuery = true)
    Integer LikearticlesTotalNumOperate(String value);


    @Query(value = "SELECT id FROM `news_article` where article_date>=?3 AND article_date<=?4 " +
            "AND (article_title like ?5) AND cid is null " +
            "limit ?1,?2 ",
            nativeQuery = true)
    List<Integer> findvalueLikeinTimeOperate(int page, int size, Date sdate_start, Date edate_end, String value);


    @Query(value = "SELECT count(*) FROM `news_article` where article_date>=?1 AND article_date<=?2 " +
            "AND (article_title like ?3) AND cid is null",
            nativeQuery = true)
    Integer LikearticlesTotalNumIntimeOperate(Date sdate_start, Date edate_end, String value);

//    @Query(value = "insert into article_audit_info(nid,news_title,uid,user_name_zh,operate_audit) values (?1,?2,?3,?4,'文章修改') ", nativeQuery = true)
//    @Transactional
//    @Modifying
//    void insertoldAudit(int nid, String news_title,int uid,String user_name_zh);
}

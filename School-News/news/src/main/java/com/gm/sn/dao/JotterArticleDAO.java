package com.gm.sn.dao;

import cn.hutool.core.date.DateTime;
import com.gm.sn.entity.Category;
import com.gm.sn.entity.JotterArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface JotterArticleDAO  extends JpaRepository<JotterArticle,Integer> {

    JotterArticle findById(int id);

    @Query(value = "update news_article set articleis_check=?1,article_audit_mind=?3 where id=?2 ", nativeQuery = true)
    @Transactional
    @Modifying
    void updateAuditStatus(int aid,int id,String auditMind);

    List<JotterArticle> findAllByCategory(Category category);

    @Query(value = "update news_article set article_views=?1 where id=?2 ", nativeQuery = true)
    @Transactional
    @Modifying
    void updateAuditViews(int article_views, int id);

    @Query(value = "SELECT * FROM `news_article` where articleis_check = 5 And id=?1 ", nativeQuery = true)
    @Transactional
    @Modifying
    JotterArticle getOneArticlebypublish(int id);

//    @Query(value = "DELETE FROM `news_article` where cid = ?1 ", nativeQuery = true)
//    @Transactional
//    @Modifying
//    void deleteJotterArticleByCategoryId(int id);

    @Query(value = "update news_article set article_views=-1,article_content_html=NULL," +
            "article_content_md=NULL,article_abstract=NULL,article_cover=NULL,article_date=NULL," +
            "article_Rate=NULL,articleis_check=NULL,article_Author=NULL,cid=NULL,article_video=NULL," +
            "article_audit_mind=NULL,articleis_top=0 where cid=?1 ", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteJotterArticleByCategoryId(int cid);

    @Query(value = "update news_article set cid=9 where id=?1 ", nativeQuery = true)
    @Transactional
    @Modifying
    void changecateAllByCategoryId(int id);

    @Query(value = "SELECT * FROM `news_article` where articleis_check = 5 AND cid is not null", nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findAllbypublish();

    @Query(value = "SELECT * FROM `news_article` where articleis_check = 5 AND cid=?1 AND cid is not null", nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findAllbypublisCategory(int cid);

    @Query(value = "SELECT * FROM `news_article` where " +
            "article_title like ?1 or article_content_md like ?1 " +
            "or article_abstract like ?1 or article_Author like ?1",
            nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findvalueLike(String value);

    @Query(value = "update news_article set articleis_check=?1 where id=?2 ", nativeQuery = true)
    @Transactional
    @Modifying
    void publish_or_Revoke(int aid, int id);

    @Query(value = "update news_article set articleis_top=?1 where id=?2 ", nativeQuery = true)
    @Transactional
    @Modifying
    void edit_article_isTop(boolean top, int id);

    @Query(value = "SELECT * FROM `news_article` where articleis_check = 5 AND articleis_top = 1 AND cid is not null", nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findAllbypublish_Top();

    @Query(value = "SELECT `article_date` FROM `news_article` where cid is not null", nativeQuery = true)
    @Transactional
    @Modifying
    List<String> DateList();

    @Query(value = "SELECT * FROM `news_article` where `article_date`>=?1 and `article_date`<=?2 AND cid is not null", nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findAriclebyDate(String startTime, String endTime);

    @Query(value = "SELECT * FROM `news_article` where cid is not null limit ?1,?2", nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findAllnoTime(int page, int size);

    @Query(value = "SELECT count(*) FROM `news_article` where cid is not null", nativeQuery = true)
//    @Transactional
//    @Modifying
    Integer articlesTotalNum();

    @Query(value = "SELECT * FROM `news_article` where article_date>=?3 AND article_date<=?4 AND cid is not null limit ?1,?2", nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findAllinTime(int page, int size, Date startTime, Date endTime);

    @Query(value = "SELECT count(*) FROM `news_article` where article_date>=?1 AND article_date<=?2 AND cid is not null", nativeQuery = true)
    Integer articlesTotalNumIntime(Date startTime, Date endTime);

    @Query(value = "SELECT * FROM `news_article` where cid =?3 AND cid is not null limit ?1,?2", nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findAllnoTimebyCid(int page, int size, int cid);

    @Query(value = "SELECT count(*) FROM `news_article` where cid=?1 AND article_title is not null", nativeQuery = true)
    Integer articlesTotalNumbyCid(int cid);

    @Query(value = "SELECT * FROM `news_article` where cid=?5 AND article_date>=?3 AND article_date<=?4 limit ?1,?2", nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findAllinTimebyCid(int page, int size, Date sdate_start, Date edate_end,int cid);

    @Query(value = "SELECT count(*) FROM `news_article` where cid=?3 AND article_date>=?1 AND article_date<=?2", nativeQuery = true)
    Integer articlesTotalNumIntimebyCid(Date sdate_start, Date edate_end, int cid);

    @Query(value = "SELECT * FROM `news_article` where " +
            "(article_title like ?1 or article_content_md like ?1 " +
            "or article_abstract like ?1 or article_Author like ?1 )AND cid is not null " +
            "limit ?2,?3",
            nativeQuery = true)
    @Transactional
    @Modifying
    List<JotterArticle> findvalueLikenoTime(String value, int page, int size);

    @Query(value = "SELECT count(*) FROM `news_article` where " +
            "(article_title like ?1 or article_content_md like ?1 " +
            "or article_abstract like ?1 or article_Author like ?1 ) AND cid is not null",
            nativeQuery = true)
    Integer LikearticlesTotalNum(String value);

    @Query(value = "SELECT * FROM `news_article` where article_date>=?3 AND article_date<=?4" +
            "AND (article_title like ?5 or article_content_md like ?5 " +
            "or article_abstract like ?5 or article_Author like ?5 ) AND cid is not null " +
            "limit ?1,?2 ",
            nativeQuery = true)
    List<JotterArticle> findvalueLikeinTime(int page, int size, Date sdate_start, Date edate_end, String value);

    @Query(value = "SELECT count(*) FROM `news_article` where article_date>=?1 AND article_date<=?2" +
            "AND (article_title like ?3 or article_content_md like ?3 " +
            "or article_abstract like ?3 or article_Author like ?3) AND cid is not null",
            nativeQuery = true)
    Integer LikearticlesTotalNumIntime(Date sdate_start, Date edate_end, String value);

    @Query(value = "update news_article set article_views=-1,article_content_html=NULL," +
            "article_content_md=NULL,article_abstract=NULL,article_cover=NULL,article_date=NULL," +
            "article_Rate=NULL,articleis_check=NULL,article_Author=NULL,cid=NULL,article_video=NULL," +
            "article_audit_mind=NULL,articleis_top=0 where id=?1 ", nativeQuery = true)
    @Transactional
    @Modifying
    void deletearticle_ById(int id);
}

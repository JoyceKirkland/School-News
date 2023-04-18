package com.gm.sn.service;

import cn.hutool.core.date.DateTime;
import com.gm.sn.dao.AuditInfoDAO;
import com.gm.sn.dao.AuditStatusDAO;
import com.gm.sn.dao.JotterArticleDAO;
import com.gm.sn.dao.UserDAO;
import com.gm.sn.entity.Audit_status;
import com.gm.sn.entity.Category;
import com.gm.sn.entity.JotterArticle;
import com.gm.sn.entity.User;
import com.gm.sn.redis.RedisService;
import com.gm.sn.util.CastUtils;
import com.gm.sn.util.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Evan
 * @date 2020/1/14 21:00
 */
@Service
public class JotterArticleService {
    @Autowired
    JotterArticleDAO jotterArticleDAO;
    @Autowired
    RedisService redisService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AuditStatusDAO auditStatusDAO;
    @Autowired
    AuditInfoDAO auditInfoDAO;
    @Autowired
    UserDAO userDAO;

//    public MyPage list(int page, int size,String startTime,String endTime) {
//        MyPage<JotterArticle> articles;
//        String key = "articlepage:" + page;
//        Object articlePageCache = redisService.get(key);
//
//        if (articlePageCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
//            Page<JotterArticle> articlesInDb = jotterArticleDAO.findAll(PageRequest.of(page, size, sort));
//            articles = new MyPage<>(articlesInDb);
//            redisService.set(key, articles);
//            System.out.println("article-no");
//        } else {
//            articles = (MyPage<JotterArticle>) articlePageCache;
//
//        }
//        System.out.println("article-test");
//        redisService.delete(key);
//        return articles;
//    }

    public Map<String, Object> list(int page, int size, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        List<JotterArticle> articles = null;
        Integer TotalNum=0;
        String key = "articlepage:" + page;
        Object articlePageCache = redisService.get(key);

        if (articlePageCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
            System.out.println(startTime);
            System.out.println(endTime);
            if(startTime=="" && endTime=="")
            {
                System.out.println("article-no");
                articles = jotterArticleDAO.findAllnoTime(page, size);
                TotalNum = jotterArticleDAO.articlesTotalNum();
            }
            else {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.sql.Date sdate_start = null; //初始化
                java.sql.Date edate_end = null; //初始化
                java.util.Date s_date = null;
                java.util.Date e_date = null;
                try{
                    s_date = sdf.parse(startTime);
                    e_date = sdf.parse(endTime);
                }catch (ParseException e) {
                    e.printStackTrace();
                }

                sdate_start = new Date(s_date.getTime());
                edate_end = new Date(e_date.getTime());

                articles = jotterArticleDAO.findAllinTime(page, size,sdate_start,edate_end);
                TotalNum = jotterArticleDAO.articlesTotalNumIntime(sdate_start,edate_end);
            }

//            articles = new MyPage<>(articlesInDb);
            System.out.println("articles:"+articles);
            map.put("articlesList", articles);
            map.put("TotalNum", TotalNum);
            redisService.set(key, articles);

        }
        System.out.println("article-test");
        redisService.delete(key);
        return map;
    }

//    用于复现异常
//    @Cacheable(value = RedisConfig.REDIS_KEY_DATABASE)
//    public Page list(int page, int size) {
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        return jotterArticleDAO.findAll(PageRequest.of(page, size, sort));
//    }


    public JotterArticle getOneArticlebypublish(int id) {
        JotterArticle article;
        String key = "article:" + id;
        Object articleCache = redisService.get(key);

        if (articleCache == null) {
            article = jotterArticleDAO.getOneArticlebypublish(id);
            redisService.set(key, article);
        } else {
            article = (JotterArticle) articleCache;
        }
        redisService.delete(key);
        return article;
    }

    public JotterArticle findById(int id) {
        JotterArticle article;
        String key = "article:" + id;
        Object articleCache = redisService.get(key);

        if (articleCache == null) {
            article = jotterArticleDAO.findById(id);
            redisService.set(key, article);
        } else {
            article = (JotterArticle) articleCache;
        }
        if(id==35)
        {
            System.out.println("??????:"+article.getArticleTitle());
        }

        redisService.delete(key);
        return article;
    }

    public void addOrUpdate(JotterArticle article,String username) {

        int article_id=article.getId();
        User user=userDAO.findByUsername(username);
        jotterArticleDAO.save(article);
//        String news_title=article.getArticleTitle();
        System.out.println("article.id:"+article.getId());
        System.out.println("user.id:"+user.getId());
        if(article_id==0)
        {
//                        auditInfoDAO.insertNewAudit(article.getId(),article.getArticleTitle(),user.getId(),user.getName());
            auditInfoDAO.insertNewAudit(article.getId(),user.getId());
        }else {
//            auditInfoDAO.insertoldAudit(article.getId(),article.getArticleTitle(),user.getId(),user.getName());
            auditInfoDAO.insertoldAudit(article.getId(),user.getId());
        }

        redisService.delete("article" + article.getId());

        Set<String> keys = redisService.getKeysByPattern("articlepage*");

        redisService.delete(keys);
    }

    public void deleteArticle(int id,String username) {
        User user_temp=userDAO.findByUsername(username);
//        JotterArticle article=jotterArticleDAO.getOne(id);
        Integer user_id=user_temp.getId();
        System.out.println("user_id:"+user_id);
        System.out.println("news_id:"+id);
        auditInfoDAO.insertdeleteOperate(id,user_id);
        //纯删除改成清空记录只剩id和标题
//        jotterArticleDAO.deleteById(id);
        jotterArticleDAO.deletearticle_ById(id);
        redisService.delete("article:" + id);
        Set<String> keys = redisService.getKeysByPattern("articlepage*");
        redisService.delete(keys);
    }
    public void updateAuditStatus(int aid,int nid,String auditMind,String username)
    {
        jotterArticleDAO.updateAuditStatus(aid,nid,auditMind);
        Audit_status audit_temp=auditStatusDAO.getOne(aid);
        User user_temp=userDAO.findByUsername(username);
        //操作名，nid，uid，审核意见
        System.out.println("audit_temp:"+audit_temp.getStatus());
        System.out.println("user_temp:"+user_temp.getId());
        auditInfoDAO.insertOperate(audit_temp.getStatus(),nid,user_temp.getId(),auditMind);
        redisService.delete("article_update_status");
    }
    public void publish_or_Revoke(int aid,int nid,String username)
    {
        jotterArticleDAO.publish_or_Revoke(aid,nid);
        Audit_status audit_temp=auditStatusDAO.getOne(aid);
        User user_temp=userDAO.findByUsername(username);
        auditInfoDAO.insertOperate_PublishorRevoke(audit_temp.getStatus(),nid,user_temp.getId());
        redisService.delete("article_update_status");
    }

    public List<JotterArticle> list() {

        List<JotterArticle> articles;
        String key = "articleslist";
        Object articlesCache = redisService.get(key);

        if (articlesCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
            articles = jotterArticleDAO.findAllbypublish();
            redisService.set(key, articles);
            System.out.println("getarticles");

        } else {
            articles = CastUtils.objectConvertToList(articlesCache, JotterArticle.class);
            System.out.println("??????");
        }
        redisService.delete(key);
        return articles;
    }


        public List<JotterArticle> listByCategory(int cid) {
//            Category category = categoryService.get(cid);
            return jotterArticleDAO.findAllbypublisCategory(cid);
        }

    public void updateAuditViews(int article_views, int id) {
        jotterArticleDAO.updateAuditViews(article_views,id);
        redisService.delete("article_update_views");
    }

    //模糊查询
    public Map<String, Object> findvalueLike(String value,int page, int size, String startTime, String endTime) {
//        redisService.delete("article_like_");
        Map<String, Object> map = new HashMap<>();
        List<JotterArticle> articles = null;
        Integer TotalNum=0;
        String key = "articlepage:" + page;
        Object articlePageCache = redisService.get(key);

        if (articlePageCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
            System.out.println(startTime);
            System.out.println(endTime);
            if(startTime=="" && endTime=="")
            {
                System.out.println("article-no");
                articles = jotterArticleDAO.findvalueLikenoTime(value,page, size);
                TotalNum = jotterArticleDAO.LikearticlesTotalNum(value);
            }
            else {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.sql.Date sdate_start = null; //初始化
                java.sql.Date edate_end = null; //初始化
                java.util.Date s_date = null;
                java.util.Date e_date = null;
                try{
                    s_date = sdf.parse(startTime);
                    e_date = sdf.parse(endTime);
                }catch (ParseException e) {
                    e.printStackTrace();
                }

                sdate_start = new Date(s_date.getTime());
                edate_end = new Date(e_date.getTime());

                articles = jotterArticleDAO.findvalueLikeinTime(page, size,sdate_start,edate_end,value);
                TotalNum = jotterArticleDAO.LikearticlesTotalNumIntime(sdate_start,edate_end,value);
            }

//            articles = new MyPage<>(articlesInDb);
            System.out.println(TotalNum);
            map.put("articlesList", articles);
            map.put("TotalNum", TotalNum);
            redisService.set(key, articles);

        }
        System.out.println("article-test");
        redisService.delete(key);
        return map;
//        return jotterArticleDAO.findvalueLike(value);
    }

    public List<String> AuditList() {
//        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return auditStatusDAO.AuditList();
    }

    public void edit_article_isTop(boolean top,int id) {
        jotterArticleDAO.edit_article_isTop(top,id);
    }

    public List<JotterArticle> toplist() {
        List<JotterArticle> articles;
        String key = "articleslist";
        Object articlesCache = redisService.get(key);

        if (articlesCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
            articles = jotterArticleDAO.findAllbypublish_Top();
            redisService.set(key, articles);
            System.out.println("getarticles");

        } else {
            articles = CastUtils.objectConvertToList(articlesCache, JotterArticle.class);
            System.out.println("??????");
        }
        redisService.delete(key);
        return articles;
    }

    public List<String> DateList() {
        return jotterArticleDAO.DateList();
    }

    public List<JotterArticle> findAriclebyDate(String startTime, String endTime) {
        return jotterArticleDAO.findAriclebyDate(startTime,endTime);
    }


    public Map<String, Object> listByCategory_page(int cid, int page, int size, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        List<JotterArticle> articles = null;
        Integer TotalNum=0;
        String key = "articlepage:" + page;
        Object articlePageCache = redisService.get(key);

        if (articlePageCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
            System.out.println(startTime);
            System.out.println(endTime);
            if(startTime=="" && endTime=="")
            {
                System.out.println("article-no");
                articles = jotterArticleDAO.findAllnoTimebyCid(page, size,cid);
                TotalNum = jotterArticleDAO.articlesTotalNumbyCid(cid);
            }
            else {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.sql.Date sdate_start = null; //初始化
                java.sql.Date edate_end = null; //初始化
                java.util.Date s_date = null;
                java.util.Date e_date = null;
                try{
                    s_date = sdf.parse(startTime);
                    e_date = sdf.parse(endTime);
                }catch (ParseException e) {
                    e.printStackTrace();
                }

                sdate_start = new Date(s_date.getTime());
                edate_end = new Date(e_date.getTime());

                articles = jotterArticleDAO.findAllinTimebyCid(page, size,sdate_start,edate_end,cid);
                TotalNum = jotterArticleDAO.articlesTotalNumIntimebyCid(sdate_start,edate_end,cid);
            }

//            articles = new MyPage<>(articlesInDb);
            System.out.println(TotalNum);
            map.put("articlesList", articles);
            map.put("TotalNum", TotalNum);
            redisService.set(key, articles);

        }
        System.out.println("article-test");
        redisService.delete(key);
        return map;
    }
}

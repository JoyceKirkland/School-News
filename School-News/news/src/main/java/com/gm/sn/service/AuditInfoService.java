package com.gm.sn.service;

import com.gm.sn.dao.AuditInfoDAO;
import com.gm.sn.entity.AuditInfo;
import com.gm.sn.entity.JotterArticle;
import com.gm.sn.redis.RedisService;
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

@Service
public class AuditInfoService {

    @Autowired
    AuditInfoDAO auditInfoDAO;
    @Autowired
    RedisService redisService;

    public Map<String, Object> list(int page, int size, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        List<AuditInfo> Operate = null;
        Integer TotalNum=0;
        String key = "articlepage:" + page;
        Object articlePageCache = redisService.get(key);

        if (articlePageCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
            System.out.println(startTime);
            System.out.println(endTime);
            if(startTime=="" && endTime=="")
            {
//                System.out.println("audit-no");
                Operate = auditInfoDAO.findAllnoTime(page, size);
                TotalNum = auditInfoDAO.articlesTotalNum();
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

                Operate = auditInfoDAO.findAllinTime(page, size,sdate_start,edate_end);
                TotalNum = auditInfoDAO.articlesTotalNumIntime(sdate_start,edate_end);
            }

//            articles = new MyPage<>(articlesInDb);
            System.out.println(TotalNum);
            map.put("OperateList", Operate);
            map.put("TotalNum", TotalNum);
            redisService.set(key, Operate);

        }
        System.out.println("article-test");
        redisService.delete(key);
        return map;
    }

    public Map<String, Object> findvalueLikeOperate(String value,int page, int size, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> articles_ids = null;
        List<AuditInfo> auditInfos=null;
        Integer TotalNum=0;
        String key = "articlepage:" + page;
        Object articlePageCache = redisService.get(key);

        if (articlePageCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
            System.out.println(startTime);
            System.out.println(endTime);
            if(startTime=="" && endTime=="")
            {

                articles_ids = auditInfoDAO.findvalueLikenoTimeOperate(value,page, size);

                auditInfos= auditInfoDAO.findAllById(articles_ids);
                System.out.println("auditInfos"+auditInfos);
                TotalNum = auditInfoDAO.LikearticlesTotalNumOperate(value);
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

                articles_ids = auditInfoDAO.findvalueLikeinTimeOperate(page, size,sdate_start,edate_end,value);
                auditInfos= auditInfoDAO.findAllById(articles_ids);
                TotalNum = auditInfoDAO.LikearticlesTotalNumIntimeOperate(sdate_start,edate_end,value);
            }

//            articles = new MyPage<>(articlesInDb);
            System.out.println(auditInfos);
            map.put("OperateList", auditInfos);
            map.put("TotalNum", TotalNum);
            redisService.set(key, auditInfos);

        }
        System.out.println("article-test");
        redisService.delete(key);
        return map;

    }

//    public MyPage list(int page, int size) {
//        MyPage<AuditInfo> auditInfos;
//        String key = "auditpage:" + page;
//        Object auditOperatePageCache = redisService.get(key);
//
//        if (auditOperatePageCache == null) {
//            Sort sort = new Sort(Sort.Direction.DESC, "id");
//            Page<AuditInfo> auditInfosInDb = auditInfoDAO.findAll(PageRequest.of(page, size, sort));
//            auditInfos = new MyPage<>(auditInfosInDb);
//            redisService.set(key, auditInfos);
//            System.out.println("article-no");
//        } else {
//            auditInfos = (MyPage<AuditInfo>) auditOperatePageCache;
//
//        }
////        System.out.println("article-test");
//        redisService.delete(key);
//        return auditInfos;
//    }
}

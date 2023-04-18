package com.gm.sn.service;

import com.gm.sn.dao.AuditInfoDAO;
import com.gm.sn.dao.CategoryDAO;
import com.gm.sn.dao.JotterArticleDAO;
import com.gm.sn.entity.Category;
import com.gm.sn.entity.JotterArticle;
import com.gm.sn.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @date 2019/4
 */
@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    JotterArticleDAO jotterArticleDAO;
    @Autowired
    private RedisService redisService;

    @Autowired
    AuditInfoDAO auditInfoDAO;


    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return categoryDAO.findAll(sort);
    }

    public Category get(int id) {
        Category c= categoryDAO.findById(id).orElse(null);
        return c;
    }

    public void addOrUpdate(Category category) {
        redisService.delete("categorylist");
        categoryDAO.save(category);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisService.delete("categorylist");
    }

    //删除栏目
    public void deleteById(int id) {
        redisService.delete("categorylist");

//        List<Integer> deleteIds=jotterArticleDAO.findIds_delete();
//        auditInfoDAO.insertdeleteOperate(id,user_temp.getId(),article.getArticleTitle(),user_temp.getName());

        jotterArticleDAO.deleteJotterArticleByCategoryId(id);
        categoryDAO.deleteById(id);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisService.delete("categorylist");
    }

    public List<String> CategroyList() {
        return categoryDAO.CategroyList();
    }

    public List<Map<String, Object>> getallCategorys() {
//        List<String> list=categoryDAO.getallCategorys();
//        List<String> list_cid = null;
//        for (int i=0;i<list.size();i++)
//        {
//
//            String[] cids = list.toArray(new String[list.size()]);
//            System.out.println("cids:"+cids[i]);
//        }

        return categoryDAO.getallCategorys();
    }
}

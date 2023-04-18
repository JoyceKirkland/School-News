package com.gm.sn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gm.sn.entity.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT `name` FROM `category`", nativeQuery = true)
    @Transactional
    @Modifying
    List<String> CategroyList();

    @Query(value = "SELECT " +
            "cid," +
            "sum( article_views ) allviews," +
            "c.`name` " +
            "FROM" +
            "`news_article` t " +
            "LEFT JOIN category c ON c.id = t.cid " +
            "where t.cid is not null " +
            "GROUP BY " +
            "cid", nativeQuery = true)
    @Transactional
    @Modifying
    List<Map<String, Object>> getallCategorys();
}

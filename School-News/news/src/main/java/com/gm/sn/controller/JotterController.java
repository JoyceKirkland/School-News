package com.gm.sn.controller;

import com.gm.sn.entity.AdminRole;
import com.gm.sn.entity.JotterArticle;
import com.gm.sn.result.Result;
import com.gm.sn.result.ResultFactory;
import com.gm.sn.service.JotterArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

/**
 * Jotter controller.
 *
 * @author Evan
 * @date 2020/1/14 20:33
 */
@RestController
public class JotterController {
    @Autowired
    JotterArticleService jotterArticleService;

    @PostMapping("api/admin/content/article/{username}")
    public Result saveArticle(@RequestBody @Valid JotterArticle article,@PathVariable("username") String username) {
        jotterArticleService.addOrUpdate(article,username);
        return ResultFactory.buildSuccessResult("保存成功");
    }
    //带审核意见
    @PostMapping("api/admin/content/auditarticle/{aid}/{nid}/{auditMind}/{username}")
    public Result updateAuditStatus(@PathVariable("aid") int aid,@PathVariable("nid") int nid,
                                    @PathVariable("auditMind") String auditMind,
                                    @PathVariable("username") String username){
//        JotterArticle target = jotterArticleService.findById(id);  //数据库查出待更新对象
//        BeanUtils.copyProperties(article,target); //使用更新对象的非空值去覆盖待更新对象
//        jotterArticleService.addOrUpdate(target ); //执行更新操作
        jotterArticleService.updateAuditStatus(aid,nid,auditMind,username);
        return ResultFactory.buildSuccessResult("审核状态更新成功");
    }
    //发布/撤销
    @PostMapping("api/admin/content/publish_or_Revoke/{aid}/{nid}/{username}")
    public Result publish_or_Revoke(@PathVariable("aid") int aid,@PathVariable("nid") int nid,
                                    @PathVariable("username") String username){
//        JotterArticle target = jotterArticleService.findById(id);  //数据库查出待更新对象
//        BeanUtils.copyProperties(article,target); //使用更新对象的非空值去覆盖待更新对象
//        jotterArticleService.addOrUpdate(target ); //执行更新操作
        jotterArticleService.publish_or_Revoke(aid,nid,username);
        return ResultFactory.buildSuccessResult("审核状态更新成功");
    }

    //审核列表
    @GetMapping("api/admin/AuditList")
    public Result AuditList(){
        return ResultFactory.buildSuccessResult(jotterArticleService.AuditList());
    }

    //日期
    @GetMapping("api/admin/DateList")
    public Result DateList(){
        return ResultFactory.buildSuccessResult(jotterArticleService.DateList());
    }

    @PostMapping("api/admin/article_Top/{top}/{id}")
    public Result edit_article_isTop(@PathVariable("top") boolean top,@PathVariable("id") int id){
        jotterArticleService.edit_article_isTop(top,id);
        return ResultFactory.buildSuccessResult("置顶成功");
    }

    @PostMapping("api/article/content/countarticle/{views}/{id}")
    public Result updateAuditViews(@PathVariable("views") int article_views,@PathVariable("id") int id){
//        JotterArticle target = jotterArticleService.findById(id);  //数据库查出待更新对象
//        BeanUtils.copyProperties(article,target); //使用更新对象的非空值去覆盖待更新对象
//        jotterArticleService.addOrUpdate(target ); //执行更新操作
        jotterArticleService.updateAuditViews(article_views,id);
        return ResultFactory.buildSuccessResult("浏览量成功");
    }

    @GetMapping("/api/article")
    public Result getlistArticles() {
//        System.out.println("??????");
        return ResultFactory.buildSuccessResult(jotterArticleService.list());

    }

    @GetMapping("/api/Toparticle")
    public Result Toparticle() {
//        System.out.println("??????");
        return ResultFactory.buildSuccessResult(jotterArticleService.toplist());

    }

    // xxx/1/10?startTiem=2020-3-1&endTime=2023-1-1
    @GetMapping("/api/article/{size}/{page}")
    public Result listArticles(@PathVariable("size") int size, @PathVariable("page") int page, @RequestParam String startTime,@RequestParam String endTime) {
        return ResultFactory.buildSuccessResult(jotterArticleService.list((page - 1)*size, size,startTime,endTime));
    }
    //    模糊查询
    @GetMapping("/api/findvalueLike/{value}/{size}/{page}")
    public Result findvalueLike(@PathVariable("value") String value,@PathVariable("size") int size, @PathVariable("page") int page, @RequestParam String startTime,@RequestParam String endTime) {
        return ResultFactory.buildSuccessResult(jotterArticleService.findvalueLike("%"+value+"%",(page - 1)*size, size,startTime,endTime));
    }

//
//    @GetMapping("/api/findvalueLike/{value}")
//    public Result findvalueLike(@PathVariable("value") String value) {
//        return ResultFactory.buildSuccessResult(jotterArticleService.findvalueLike("%"+value+"%"));
//    }

    @GetMapping("/api/article/{id}")
    public Result getOneArticle(@PathVariable("id") int id) {
        return ResultFactory.buildSuccessResult(jotterArticleService.findById(id));
//        return ResultFactory.buildSuccessResult(jotterArticleService.getOneArticlebypublish(id));
    }


    @GetMapping("/api/allarticle/{id}")
    public Result noadmin_getOneArticle(@PathVariable("id") int id) {
        return ResultFactory.buildSuccessResult(jotterArticleService.findById(id));
    }

    @GetMapping("/api/admin/findAriclebyDate/{startTime}/{endTime}")
    public Result findAriclebyDate(@RequestBody @Valid @PathVariable("startTime") String startTime,@RequestBody @Valid @PathVariable("endTime") String endTime) {
        return ResultFactory.buildSuccessResult(jotterArticleService.findAriclebyDate(startTime,endTime));
    }


    @DeleteMapping("/api/admin/content/deleteArticle/{id}/{username}")
    public Result deleteArticle(@PathVariable("id") int id,@PathVariable("username") String username) {
        jotterArticleService.deleteArticle(id,username);
        return ResultFactory.buildSuccessResult("删除成功");
    }

}

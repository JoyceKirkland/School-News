package com.gm.sn.controller;

import com.gm.sn.entity.JotterArticle;
import com.gm.sn.result.Result;
import com.gm.sn.result.ResultFactory;
import com.gm.sn.service.AuditInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuditInfoController {

    @Autowired
    AuditInfoService auditInfoService;

//    @GetMapping("/api/admin/auditInfo/{size}/{page}")
//    public Result listAuditInfos(@PathVariable("size") int size, @PathVariable("page") int page) {
//        return ResultFactory.buildSuccessResult(auditInfoService.list(page - 1, size));
//    }

    //    模糊查询
    @GetMapping("/api/findvalueLikeOperate/{value}/{size}/{page}")
    public Result findvalueLikeOperate(@PathVariable("value") String value,@PathVariable("size") int size, @PathVariable("page") int page, @RequestParam String startTime,@RequestParam String endTime) {
        return ResultFactory.buildSuccessResult(auditInfoService.findvalueLikeOperate("%"+value+"%",(page - 1)*size, size,startTime,endTime));
    }

    @GetMapping("/api/Operate/{size}/{page}")
    public Result listOperates(@PathVariable("size") int size, @PathVariable("page") int page, @RequestParam String startTime,@RequestParam String endTime) {
        return ResultFactory.buildSuccessResult(auditInfoService.list((page - 1)*size, size,startTime,endTime));
    }

//    @PostMapping("api/admin/content/article")
//    public Result saveAuditInfo(@RequestBody @Valid JotterArticle article) {
//        auditInfoService.addOrUpdate(article);
//        return ResultFactory.buildSuccessResult("保存成功");
//    }

}

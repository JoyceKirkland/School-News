//package com.gm.sn.controller;
//
//import com.gm.sn.entity.Audit_status;
//import com.gm.sn.entity.Category;
//import com.gm.sn.result.Result;
//import com.gm.sn.result.ResultFactory;
//import com.gm.sn.service.AuditStatusService;
//import com.gm.sn.service.CategoryService;
//import com.gm.sn.util.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import java.io.File;
//import java.io.IOException;
//
///**
// * Library controller.
// *
// * @author Evan
// * @date 2019/4
// */
//@RestController
//public class LibraryController {
//    @Autowired
//    AuditStatusService bookService;
//    @Autowired
//    CategoryService categoryService;
//
//    @GetMapping("/api/books")
//    public Result listBooks() {
//        return ResultFactory.buildSuccessResult(bookService.list());
//    }
//
//    @GetMapping("/api/categorys")
//    public Result listCategorys() {
//        return ResultFactory.buildSuccessResult(categoryService.list());
//    }
//
//    @PostMapping("/api/admin/content/books")
//    public Result addOrUpdateBooks(@RequestBody @Valid Audit_status book) {
//        bookService.addOrUpdate(book);
//        return ResultFactory.buildSuccessResult("修改成功");
//    }
//
//    @PostMapping("/api/admin/content/categorys")
//    public Result addOrUpdateCategoryss(@RequestBody @Valid Category category) {
//        categoryService.addOrUpdate(category);
//        return ResultFactory.buildSuccessResult("修改成功");
//    }
//
//    @PostMapping("/api/admin/content/books/delete")
//    public Result deleteBook(@RequestBody @Valid Audit_status book) {
//        bookService.deleteById(book.getId());
//        return ResultFactory.buildSuccessResult("删除成功");
//    }
//
//    @PostMapping("/api/admin/content/categorys/delete")
//    public Result deleteCategorys(@RequestBody @Valid Category category) {
//        categoryService.deleteById(category.getId());
//        return ResultFactory.buildSuccessResult("栏目删除成功");
//    }
//
//
//    @GetMapping("/api/search")
//    public Result searchResult(@RequestParam("keywords") String keywords) {
//        if ("".equals(keywords)) {
//            return ResultFactory.buildSuccessResult(bookService.list());
//        } else {
//            return ResultFactory.buildSuccessResult(bookService.Search(keywords));
//        }
//    }
//
//    @GetMapping("/api/categories/{cid}/books")
//    public Result listByCategory(@PathVariable("cid") int cid) {
//        if (0 != cid) {
//            return ResultFactory.buildSuccessResult(bookService.listByCategory(cid));
//        } else {
//            return ResultFactory.buildSuccessResult(bookService.list());
//        }
//    }
//
//    @PostMapping("/api/admin/content/books/covers")
//    public String coversUpload(MultipartFile file) {
//        String folder = "D:/workspace/img";
//        File imageFolder = new File(folder);
//        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
//                .substring(file.getOriginalFilename().length() - 4));
//        if (!f.getParentFile().exists())
//            f.getParentFile().mkdirs();
//        try {
//            file.transferTo(f);
//            String imgURL = "http://localhost:8443/api/file/" + f.getName();
//            return imgURL;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//}

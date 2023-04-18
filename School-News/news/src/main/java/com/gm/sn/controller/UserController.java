package com.gm.sn.controller;

import com.gm.sn.entity.*;
import com.gm.sn.result.Result;
import com.gm.sn.result.ResultFactory;
import com.gm.sn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User controller.
 *
 * @author Evan
 * @date 2019/11
 */

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    @GetMapping("/api/admin/user")
    public Result listUsers() {
        return ResultFactory.buildSuccessResult(userService.list());
    }

    @GetMapping("/api/admin/{username}")
    public Result findbyUserName(@PathVariable("username") String username) {
        return ResultFactory.buildSuccessResult(userService.findByUsername(username));
    }

    @GetMapping("/api/admin/findbyUserId/{uid}")
    public Result findbyUserId(@PathVariable("uid") int uid) {
        return ResultFactory.buildSuccessResult(userService.findbyUserId(uid));
    }

    @PutMapping("/api/admin/user/status")
    public Result updateUserStatus(@RequestBody @Valid User requestUser) {
        userService.updateUserStatus(requestUser);
        return ResultFactory.buildSuccessResult("用户状态更新成功");
    }

    @PutMapping("/api/user/password")
    public Result resetPassword(@RequestBody @Valid User requestUser) {
        userService.resetPassword1(requestUser.getUsername(),requestUser.getPassword());
        return ResultFactory.buildSuccessResult("重置密码成功");
    }

//    @PutMapping("/api/admin/user/password1")
//    public Result resetPassword1(@RequestBody @Valid User requestUser,String newpassword) {
//        userService.resetPassword1(requestUser,newpassword);
//        return ResultFactory.buildSuccessResult("重置密码成功");
//    }

    @PutMapping("/api/admin/user")
    public Result admin_editUser(@RequestBody @Valid User requestUser) {
        userService.admin_editUser(requestUser);
        return ResultFactory.buildSuccessResult("修改用户信息成功");
    }

    @PutMapping("/api/admin/all_user")
    public Result editUser(@RequestBody @Valid User requestUser) {
        userService.editUser(requestUser);
        return ResultFactory.buildSuccessResult("修改个人信息成功");
    }

    @PostMapping("/api/admin/deleteUsers/{uid}")
    public Result deleteUsers(@PathVariable("uid") int uid) {
        userService.deleteById(uid);
        return ResultFactory.buildSuccessResult("栏目删除成功");
    }


}

package com.gm.sn.controller;

import com.gm.sn.result.Result;
import com.gm.sn.result.ResultFactory;
import com.gm.sn.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Menu controller.
 *
 * @author Evan
 * @date 2019/11
 */
@RestController
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;

    @GetMapping("/api/menu")
    public Result menu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByCurrentUser());
    }

    //去重
    @GetMapping("/api/admin/role/menu")
    public Result listAllMenus() {
//        旧：getMenusByRoleId
        return ResultFactory.buildSuccessResult(adminMenuService.getAllMenusByadminRoleId(1));
    }
}

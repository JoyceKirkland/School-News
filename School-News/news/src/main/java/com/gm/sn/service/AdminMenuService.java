package com.gm.sn.service;

import com.gm.sn.dao.AdminMenuDAO;
import com.gm.sn.entity.AdminMenu;
import com.gm.sn.entity.AdminRoleMenu;
import com.gm.sn.entity.AdminUserRole;
import com.gm.sn.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2019/10
 */
@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminMenu> getAllByParentId(int parentId) {
        return adminMenuDAO.findAllByParentId(parentId);
    }

    public List<AdminMenu> getMenusByCurrentUser() {
        // Get current user in DB.
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);

        // Get roles' ids of current user.
        List<Integer> rids = adminUserRoleService.listAllByUid(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        // Get menu items of these roles.
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rids)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        // Adjust the structure of the menu.
        handleMenus(menus);
        return menus;
    }

    //不去重
    public List<AdminMenu> getMenusByRoleId(int rid) {
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rid)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds);

        handleMenus(menus);
        System.out.println("menuIds:"+rid+menuIds);
        System.out.println("menus:"+rid+menus);
        return menus;
    }

    //去重
    public List<AdminMenu> getAllMenusByadminRoleId(int rid) {
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rid)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds);
        //去重
        handleAllMenus(menus);
        System.out.println("menus_role:"+menus);
        return menus;
    }

    /**
     * Adjust the Structure of the menu.
     *
     * @param menus Menu items list without structure
     */
    //不去重-bug
    public void handleMenus(List<AdminMenu> menus) {
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            children.removeIf(child -> !menus.contains(child));
            m.setChildren(children);
        });
        menus.removeIf(m -> m.getParentId() != 0);
    }
    //去重
    public void handleAllMenus(List<AdminMenu> menus) {
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            children.removeIf(child -> !menus.contains(child));
            m.setChildren(children);
        });
        menus.removeIf(m -> m.getParentId() != 0);
    }

}

package com.minnow.controller;

import com.minnow.domain.PermMenu;
import com.minnow.model.Result;
import com.minnow.service.PermMenuService;
import com.minnow.util.AuthUtils;
import com.minnow.vo.MenuAndPermsVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

@RequestMapping("sys/menu")
@RestController
public class SysMenuController {

    @Resource
    private PermMenuService permMenuService;

    /**
     * 返回树形的菜单和和使用权限，前端根据菜单信息显示对应菜单
     * @return
     */
    @ApiOperation("查询登录用户的菜单和权限")
    @GetMapping("nav")
    public Result<MenuAndPermsVo> loadUserMenuAndPerms() {
        // 获取当前登录用户ID
        Long userId = AuthUtils.getLoginUserId();
        // 查询当前登录用户的权限集合
        Set<String> perms = AuthUtils.getPerms();
        // 根据用户ID查询菜单集合
        Set<PermMenu> menus = permMenuService.queryUserMenus(userId);
        // 创建菜单和权限集合对象
        MenuAndPermsVo menuAndPermsVo = new MenuAndPermsVo(menus,perms);
        return Result.success(menuAndPermsVo);
    }

}

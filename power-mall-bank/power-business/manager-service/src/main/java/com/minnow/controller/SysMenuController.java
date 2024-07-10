package com.minnow.controller;

import com.minnow.domain.PermMenu;
import com.minnow.model.Result;
import com.minnow.service.PermMenuService;
import com.minnow.util.AuthUtils;
import com.minnow.vo.MenuAndPermsVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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

    @ApiOperation("查询系统所有权限集合")
    @GetMapping("table")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<PermMenu>> loadAllSysMenuList() {
        List<PermMenu> menuList = permMenuService.queryAllSysMenuList();
        return Result.success(menuList);
    }

    /**
     * 新增权限
     * @param permMenu
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<String> saveSysMenu(@RequestBody PermMenu permMenu) {
        return Result.handle(permMenuService.save(permMenu));
    }

    /**
     * 查询权限详情
     * @param menuId
     * @return
     */
    @GetMapping("info/{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<PermMenu> loadSysMenuInfo(@PathVariable Long menuId) {
        PermMenu menu = permMenuService.getById(menuId);
        return Result.success(menu);
    }

    /**
     * 修改权限
     * @param permMenu
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<String> modifySysMenu(@RequestBody PermMenu permMenu) {
        return Result.handle(permMenuService.updateById(permMenu));
    }

    /**
     * 删除权限
     * @param menuId
     * @return
     */
    @DeleteMapping("{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result<String> removeSysMenu(@PathVariable Long menuId) {
        return Result.handle(permMenuService.removeById(menuId));
    }
}

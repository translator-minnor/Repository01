package com.minnow.service;

import com.minnow.domain.PermMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author 小池鱼
 * @description 针对表【perm_menu(权限管理)】的数据库操作Service
 * @createDate 2024-07-05 16:41:03
 */
public interface PermMenuService extends IService<PermMenu> {

    /**
     * 根据用户ID查询菜单集合
     * @param userId 用户ID
     * @return 菜单集合
     */
    Set<PermMenu> queryUserMenus(Long userId);

    /**
     * 查询系统所有权限集合
     * @return 系统所有权限集合
     */
    List<PermMenu> queryAllSysMenuList();

}

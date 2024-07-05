package com.minnow.mapper;

import com.minnow.domain.PermMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
* @author 小池鱼
* @description 针对表【perm_menu(权限管理)】的数据库操作Mapper
* @createDate 2024-07-05 16:41:03
* @Entity com.minnow.domain.PermMenu
*/
public interface PermMenuMapper extends BaseMapper<PermMenu> {

    Set<PermMenu> selectUserMenusByUserId(Long userId);

}





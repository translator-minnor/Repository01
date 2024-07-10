package com.minnow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnow.domain.PermMenu;
import com.minnow.ex.BusinessException;
import com.minnow.service.PermMenuService;
import com.minnow.mapper.PermMenuMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 小池鱼
* @description 针对表【perm_menu(权限管理)】的数据库操作Service实现
* @createDate 2024-07-05 16:41:03
*/
@Service
@CacheConfig(cacheNames = "com.minnow.service.impl.SysMenuServiceImpl")
public class PermMenuServiceImpl extends ServiceImpl<PermMenuMapper, PermMenu>
    implements PermMenuService{

    @Resource
    private PermMenuMapper permMenuMapper;

    @Override
    @Cacheable(key = "#userId")
    public Set<PermMenu> queryUserMenus(Long userId) {
        Set<PermMenu> permMenus = permMenuMapper.selectUserMenusByUserId(userId);
        // 判断菜单集合是否有值
        if (!CollectionUtils.isEmpty(permMenus) && permMenus.size() != 0) {
            // 将菜单集合转换为树结构
            permMenus = translateMenusToTree(permMenus,0L);
        }
        return permMenus;
    }

    @Override
    public List<PermMenu> queryAllSysMenuList() {
        return permMenuMapper.selectList(null);
    }

    private Set<PermMenu> translateMenusToTree(Set<PermMenu> permMenus, long l) {
        Set<PermMenu> roots = permMenus.stream()
                .filter(menu -> menu.getParentId().equals(l))
                .collect(Collectors.toSet());
        // 循环遍历父节点集合，根据指定父节点id查询子节点集合
        roots.forEach(r -> r.setList(translateMenusToTree(permMenus,r.getMenuId())));
        return roots;
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean save(PermMenu permMenu) {
        return super.save(permMenu);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean updateById(PermMenu permMenu) {
        return super.updateById(permMenu);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean removeById(Serializable id) {
        // 查询当前权限节点的子节点
        List<PermMenu> childMenuList = permMenuMapper.selectList(new LambdaQueryWrapper<PermMenu>()
                .eq(PermMenu::getParentId, id)
        );
        // 判断是否包含子节点
        if (!CollectionUtils.isEmpty(childMenuList) && childMenuList.size() != 0) {
            throw new BusinessException("当前节点包含子节点，不可删除");
        }
        return super.removeById(id);
    }
}





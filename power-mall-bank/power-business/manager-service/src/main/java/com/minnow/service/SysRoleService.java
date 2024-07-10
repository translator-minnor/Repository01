package com.minnow.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.minnow.domain.SysRole;
import com.minnow.dto.SysRoleAddOrUpdateParam;
import com.minnow.vo.SysRoleDetailVo;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 新增角色
     * @param addOrUpdateParam
     * @return
     */
    Integer addSysRole(SysRoleAddOrUpdateParam addOrUpdateParam);

    /**
     * 查询角色详情(包含权限ID集合)
     * @param roleId 角色标识
     * @return
     */
    SysRoleDetailVo querySysRoleDetailById(Long roleId);

    /**
     * 修改角色信息
     * @param addOrUpdateParam
     * @return
     */
    Integer modifySysRole(SysRoleAddOrUpdateParam addOrUpdateParam);

    /**
     * 批量删除角色
     * @param roleIds 角色ID集合
     * @return
     */
    Integer removeSysRolesByIds(List<Long> roleIds);

}

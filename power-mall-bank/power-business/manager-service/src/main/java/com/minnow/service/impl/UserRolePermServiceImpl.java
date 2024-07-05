package com.minnow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnow.domain.UserRolePerm;
import com.minnow.service.UserRolePermService;
import com.minnow.mapper.UserRolePermMapper;
import org.springframework.stereotype.Service;

/**
* @author 小池鱼
* @description 针对表【user_role_perm(角色与权限对应关系)】的数据库操作Service实现
* @createDate 2024-07-05 16:42:18
*/
@Service
public class UserRolePermServiceImpl extends ServiceImpl<UserRolePermMapper, UserRolePerm>
    implements UserRolePermService{

}





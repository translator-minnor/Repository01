package com.minnow.service;

import com.minnow.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 小池鱼
* @description 针对表【user】的数据库操作Service
* @createDate 2024-07-05 16:42:18
*/
public interface UserService extends IService<User> {

    Integer addSysUser(User user);

    Integer modifySysUser(User User);

    /**
     * 批量删除管理员
     * @param userIds 管理员ID集合
     * @return 删除数量
     */
    Integer removeSysUsersByUserIdList(List<Long> userIds);
}

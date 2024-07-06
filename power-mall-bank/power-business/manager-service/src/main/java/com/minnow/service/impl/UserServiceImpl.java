package com.minnow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnow.domain.User;
import com.minnow.service.UserService;
import com.minnow.mapper.UserMapper;
import com.minnow.util.AuthUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 小池鱼
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-05 16:42:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer addSysUser(User user) {
        // 获取登录用户的ID
        Long createUserId = AuthUtils.getLoginUserId();
        // 补充管理员对象属性的值
        user.setCreateUserId(createUserId);
        user.setCreateTime(new Date());
        // 获取管理员密码
        String password = user.getPassword();
        // 判断是否有值
        if (StringUtils.hasText(password)) {
            // 管理员密码使用 加密
            user.setPassword(passwordEncoder.encode(password));
        }

        int count = userMapper.insert(user);

        return count;
    }

    @Override
    public Integer modifySysUser(User user) {
        String newPassword = user.getPassword();
        // 判断新密码是否有值，如果有值：设置新密码，相反：密码不变
        if (StringUtils.hasText(newPassword)) {
            // 新密码加密
            String encodeNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodeNewPassword);
        }
        return userMapper.updateById(user);
    }

    @Override
    public Integer removeSysUsersByUserIdList(List<Long> userIds) {
        // 批量删除指定管理员
        return userMapper.deleteBatchIds(userIds);
    }
}





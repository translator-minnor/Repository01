package com.minnow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnow.domain.User;
import com.minnow.service.UserService;
import com.minnow.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 小池鱼
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-05 16:42:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





package com.minnow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnow.domain.Card;
import com.minnow.service.CardService;
import com.minnow.mapper.CardMapper;
import org.springframework.stereotype.Service;

/**
* @author 小池鱼
* @description 针对表【card】的数据库操作Service实现
* @createDate 2024-07-05 16:42:18
*/
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card>
    implements CardService{

}





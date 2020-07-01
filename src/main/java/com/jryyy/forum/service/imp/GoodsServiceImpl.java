package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.MyGoodsMapper;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author OU
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static final Integer MINIMUM_CREDIT = 10;
    private final UserInfoMapper userInfoMapper;
    private final MyGoodsMapper myGoodsMapper;

    public GoodsServiceImpl(UserInfoMapper userInfoMapper, MyGoodsMapper myGoodsMapper) {
        this.userInfoMapper = userInfoMapper;
        this.myGoodsMapper = myGoodsMapper;
    }

    @Override
    public Response purchaseGoods(Integer userId, Integer goods) throws Exception {
        int credit = userInfoMapper.findCreditByUserId(userId);
        if (credit < MINIMUM_CREDIT) {
            throw new GlobalException(GlobalStatus.insufficientCredit);
        }
        userInfoMapper.updateCredit(userId, credit - MINIMUM_CREDIT);
        myGoodsMapper.insertMyGoodes(userId, goods);
        return new Response<>("购买商品成功");
    }

    @Override
    public Response findGoodsByUserId(Integer userId) throws Exception {
        List<Integer> goods = myGoodsMapper.findGoodsByUserId(userId);
        goods.add(1);
        goods.add(2);
        goods.add(0);
        return new Response<>(goods);
    }
}

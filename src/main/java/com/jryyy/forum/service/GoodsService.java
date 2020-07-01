package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;

/**
 * 商品服务
 *
 * @author OU
 */
public interface GoodsService {

    /**
     * 购买商品
     *
     * @param userId 用户id
     * @param goods  商品
     * @return {@link Response}
     * @throws Exception
     */
    Response purchaseGoods(Integer userId, Integer goods) throws Exception;

    /**
     * 我的商品
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response findGoodsByUserId(Integer userId) throws Exception;
}

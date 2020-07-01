package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.GoodsService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;

/**
 * @author OU
 */
@UserLoginToken
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * 购买商品
     *
     * @param userId 用户id
     * @param goods  商品
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/{userId}")
    public Response purchaseGoods(@PathVariable Integer userId,
                                  @RequestParam Integer goods) throws Exception {
        return goodsService.purchaseGoods(userId, goods);
    }

    /**
     * 我的商品
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @GetMapping("/{userId}")
    public Response findGoodsByUserId(@PathVariable Integer userId) throws Exception {
        return goodsService.findGoodsByUserId(userId);
    }
}

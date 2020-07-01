package com.jryyy.forum.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 我的商品dao
 *
 * @author OU
 */
@Mapper
public interface MyGoodsMapper {

    /**
     * 购买商品
     *
     * @param userId 用户id
     * @param goods  商品
     * @throws Exception
     */
    @Insert("insert my_goods(userId,goods)values(#{userId},#{goods})")
    void insertMyGoodes(Integer userId, Integer goods) throws Exception;

    /**
     * 我的商品
     *
     * @param userId 用户
     * @return 商品列表
     * @throws Exception
     */
    @Select("select goods from my_goods where userId = #{userId}")
    List<Integer> findGoodsByUserId(Integer userId) throws Exception;

}

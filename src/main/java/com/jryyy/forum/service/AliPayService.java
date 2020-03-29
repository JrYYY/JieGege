package com.jryyy.forum.service;

import com.alipay.api.AlipayApiException;
import com.jryyy.forum.model.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author OU
 */
public interface AliPayService {
    /**
     * @param orderNo: 订单编号
     * @param amount:  实际支付金额
     * @param body:    订单描述
     * @return 订单
     * @throws AlipayApiException {@link AlipayApiException}
     * @Description: 创建支付宝订单
     */
    String createOrder(String orderNo, double amount, String body) throws AlipayApiException;

    /**
     * @param tradeStatus: 支付宝交易状态
     * @param orderNo:     订单编号
     * @param tradeNo:     支付宝订单号
     * @Description:
     */
    boolean notify(String tradeStatus, String orderNo, String tradeNo);

    /**
     * @param request {@link HttpServletResponse}
     * @Description: 校验签名
     */
    boolean rsaCheckV1(HttpServletRequest request);

    /**
     * @param orderNo:      订单编号
     * @param amount:       实际支付金额
     * @param refundReason: 退款原因
     * @Description: 退款
     */
    Response refund(String orderNo, double amount, String refundReason);
}

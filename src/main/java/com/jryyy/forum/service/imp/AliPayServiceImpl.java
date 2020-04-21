package com.jryyy.forum.service.imp;

import com.alipay.api.AlipayApiException;
import com.jryyy.forum.config.AliPayConfiguration;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author OU
 */
@Slf4j
@Service
public class AliPayServiceImpl implements AliPayService {

    private final AliPayConfiguration aliPayConfig;

    public AliPayServiceImpl(AliPayConfiguration aliPayConfig) {
        this.aliPayConfig = aliPayConfig;
    }

    @Override
    public String createOrder(String orderNo, double amount, String body) throws AlipayApiException {
//        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setSubject(body);
//        model.setOutTradeNo(orderNo);
//        model.setTotalAmount(String.valueOf(amount));
//        model.setProductCode("QUICK_MSECURITY_PAY");
//        model.setPassbackParams("公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数");
//
//        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//        AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
//        ali_request.setBizModel(model);
//        ali_request.setNotifyUrl(aliPayConfig.getNotifyUrl());// 回调地址
//        AlipayTradeAppPayResponse ali_response = aliPayConfig.sdkExecute(ali_request);
//        //就是orderString 可以直接给客户端请求，无需再做处理。
//        return ali_response.getBody();
        return "";
    }

    @Override
    public boolean notify(String tradeStatus, String orderNo, String tradeNo) {
        if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
//            // 支付成功，根据业务逻辑修改相应数据的状态
//            boolean state = orderPaymentService.updatePaymentState(orderNo, tradeNo);
//            if (state) {
//                return true;
//            }
        }
        return false;
    }

    @Override
    public boolean rsaCheckV1(HttpServletRequest request) {
//        try {
//            Map<String, String> params = new HashMap<>();
//            Map<String, String[]> requestParams = request.getParameterMap();
//            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
//                String name = (String) iter.next();
//                String[] values = requestParams.get(name);
//                String valueStr = "";
//                for (int i = 0; i < values.length; i++) {
//                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//                }
//                params.put(name, valueStr);
//            }
//
//            boolean verifyResult = AlipaySignature.rsaCheckV1(params, aliPayConfig.getAlipayPublicKey(), aliPayConfig.getCharset(), alipayConfig.getSignType());
//            return verifyResult;
//        } catch (AlipayApiException e) {
//            return false;
//        }
        return true;
    }

    @Override
    public Response refund(String orderNo, double amount, String refundReason) {
        return null;
    }
}

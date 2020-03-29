package com.jryyy.forum.config;
 
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
 
/**
 * @author OU
 */

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfiguration {
    /**
     * 日志地址,这里在d盘下要创建这个文件,不然会报错
     */
    private static String log_path = "C:/logs/";
    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
        private String appID;
    /**例：2016082600317257
     *商户私钥，您的PKCS8格式RSA2私钥
     */
    private String merchantPrivateKey;
    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
     * 对应APPID下的支付宝公钥。
     */
    private String alipayPublicKey ;
        /**
         * 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
         * 返回的时候此页面不会返回到用户页面，只会执行你写到控制器里的地址
         */
        private String notifyUrl;
    /**
     * 签名方式
     */
    private String signType = "RSA2";
    /**
     * 字符编码格式
     */
    private String charset = "utf-8";
    /**
     * 支付宝网关
     */
    private String gatewayUrl;
    private String sysServiceProviderId = "2088102180418544";

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord
     * 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_"
                    + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                }
        }

}

package com.hh.wx.v3.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huanghan
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx3")
public class Wx3Constant {

    /**
     * 应用的APP_ID
     */
    private String appid;

    /**
     * 应用绑定的商户号ID
     */
    private String mchId;

//    /**
//     * 商户号KEY
//     */
//    private String mchKey;

    /**
     * 全局交易类型
     * JSAPI: 小程序支付，
     * NATIVE: 二维码，
     * APP: app支付，
     * MWEB: H5支付
     * MICROPAY：付款码支付
     */
    private String tradeType;

    /**
     * 回调地址
     */
    private String notifyUrl;
//
//    /**
//     * 商户证书路径
//     */
//    private String certUrl;

    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 商户API私钥路径
     */
    private String privateKeyPath;

    /**
     * 商户证书序列号
     */
    private String merchantSerialNumber;

    /**
     * 商户APIV3密钥
     */
    private String apiV3Key;
}

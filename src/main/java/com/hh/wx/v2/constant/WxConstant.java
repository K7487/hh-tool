package com.hh.wx.v2.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huanghan
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx2")
public class WxConstant {

    /**
     * 应用的APP_ID
     */
    private String appid;

    /**
     * 应用绑定的商户号ID
     */
    private String mchId;

    /**
     * 商户号KEY
     */
    private String mchKey;

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

    /**
     * 商户证书路径
     */
    private String certUrl;

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     */
    private Integer connectTimeout = 60000;

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     */
    private Integer readTimeout = 60000;

}

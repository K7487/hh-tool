package com.hh.wx.v3.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huanghan
 */
public interface Wx3ConfigConstant {

    /**
     * 应用的APP_ID
     */
    String getAppid();

    /**
     * 应用绑定的商户号ID
     */
    String getMchId();

    /**
     * 商户号
     */
    String getMerchantId();

    /**
     * 商户API私钥路径
     */
    String getPrivateKeyPath();

    /**
     * 商户证书序列号
     */
    String getMerchantSerialNumber();

    /**
     * 商户APIV3密钥
     */
    String getApiV3Key();
}

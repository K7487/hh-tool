package com.hh.ali.conig;

import com.alipay.api.AlipayConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huanghan
 */
@Data
@Component
@ConfigurationProperties(prefix = "ali")
public class AliConfig extends AlipayConfig {

    /**
     * 异步回调
     */
    private String notifyUrl;

    /**
     * 小程序支付中，商户实际经营主体的小程序应用的appid
     */
    private String opAppId;

    /**
     * 交易类型
     * JSAPI: 小程序支付，
     * NATIVE: 二维码，
     * APP: app支付，
     * MWEB: H5支付
     * MICROPAY：付款码支付
     */
    private String tradeType;
}

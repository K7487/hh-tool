package com.hh.factory.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 支付公共请求方法
 * @author huanghan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayReqVO {

    /**
     * 下单金额，单位元（保留2位小数）
     */
    private BigDecimal amounts;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 交易类型
     * JSAPI: 小程序支付，
     * NATIVE: 二维码，
     * APP: app支付，
     * MWEB: H5支付
     * MICROPAY：付款码支付
     */
    private String tradeType;

    /**
     * openid
     * 当tradeType=JSAPI时，必传
     */
    private String openid;

    /**
     * 支付授权码
     * 当tradeType=MICROPAY时，必传
     */
    private String authCode;
}

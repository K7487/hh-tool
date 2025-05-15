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

    /**
     * 子商户号
     * 微信使用
     */
    private String subMchId;

    /**
     * 子商户授权token
     * 支付宝
     */
    private String subAuthToken;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 小程序支付中，商户实际经营主体的小程序应用的appid
     */
    private String opAppId;

    /**
     * 子商户公众账号ID
     * 微信分配的子商户公众账号ID，如需在支付完成后获取sub_openid则此参数必传
     */
    private String subAppid;

    /**
     * 用户子标识
     * trade_type=JSAPI，此参数必传，用户在子商户appid下的唯一标识。openid和sub_openid可以选传其中之一，如果选择传sub_openid,则必须传sub_appid
     */
    private String subOpenid;

}

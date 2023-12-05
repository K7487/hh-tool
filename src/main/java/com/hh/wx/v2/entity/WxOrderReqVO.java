package com.hh.wx.v2.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 微信支付下单对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxOrderReqVO {

	/**
	 * 商品描述
	 */
	private String body;

	/**
	 * 商品详情
	 */
	private String detail;

	/**
	 * 附加数据
	 */
	private String attach;

	/**
	 * 商户订单号
	 */
	private String outTradeNo;

	/**
	 * 货币类型
	 */
	private String feeType;

	/**
	 * 总金额(单位：元) 保留两位小数
	 */
	private BigDecimal totalFee;

	/**
	 * 终端IP
	 */
	private String spbillCreateIp;

	/**
	 * 交易起始时间 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	 */
	private String timeStart;

	/**
	 * 交易结束时间 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010
	 */
	private String timeExpire;

	/**
	 * 商品标记
	 */
	private String goodsTag;

	/**
	 * 通知地址
	 */
	private String notifyUrl;

	/**
	 * 交易类型 JSAPI:小程序支付，NATIVE:二维码，APP，MWEB:H5支付
	 */
	private String tradeType;

	/**
	 * 商品ID
	 */
	private String productId;

	/**
	 * 指定支付方式
	 */
	private String limitPay;

	/**
	 * 用户标识
	 */
	private String openid;

	/**
	 * 电子发票入口开放标识
	 */
	private String receipt;

	/**
	 * 场景信息
	 */
	private String sceneInfo;

}

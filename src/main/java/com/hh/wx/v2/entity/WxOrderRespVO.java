package com.hh.wx.v2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/*
 * 微信支付下单对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxOrderRespVO {


	/**
	 * 商户订单号
	 */
	private String outTradeNo;

	/**
	 * 微信返回信息
	 */
	private Map<String, Object> wxMap;

	/**
	 * 微信返回状态，
	 * 	成功返回 true
	 * 	失败返回 false
	 */
	private boolean r;

	/**
	 * 类型
	 * 1是支付回调
	 * 2是退款回调
	 */
	private Integer type;
}

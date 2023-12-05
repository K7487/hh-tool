package com.hh.wx.v2.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 微信退款订单对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxRefundReqVO {

	/**
	 * 商户订单号
	 */
	private String outTradeNo;

	/**
	 * 商户退款单号
	 */
	private String outRefundNo;
	
	/**
	 * 订单金额(单位：元)
	 * 保留两位小数
	 */
	private BigDecimal totalFee;

	/**
	 * 退款金额(单位：元)
	 * 保留两位小数
	 */
	private BigDecimal refundFee;

	/**
	 * 退款货币种类
	 */
	private String refundFeeType;

	/**
	 * 退款原因
	 */
	private String refundDesc;

	/**
	 * 退款资金来源
	 */
	private String refundAccount;

	/**
	 * 退款结果通知url
	 */
	private String notifyUrl;

}

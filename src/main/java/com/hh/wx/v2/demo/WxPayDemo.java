package com.hh.wx.v2.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.github.wxpay.sdk.WXPayConfig;
import com.hh.wx.v2.entity.WxOrderReqVO;
import com.hh.wx.v2.entity.WxRefundReqVO;
import com.hh.wx.v2.enums.WxPayEnum;
import com.hh.wx.v2.enums.WxRefundEnum;
import com.hh.wx.v2.util.WxPaymentUtil;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * 支付示例
 */
public class WxPayDemo {
	
	private static final Log log = LogFactory.get();
	
	public static void main(String[] args) {
//		unifiedorder();
//		orderquery();
//		closeorder();
//		refund();
//		refundquery();
	}

	/**
	 * 下单
	 */
	public static void unifiedorder() {
		WxConfig config = new WxConfig();
		WxOrderReqVO reqVO = new WxOrderReqVO();
		Map<String, String> map = new HashMap<>();
		reqVO.setBody("测试商品");
		reqVO.setOutTradeNo("20231205123450");
		reqVO.setTotalFee(new BigDecimal("0.01"));
		reqVO.setSpbillCreateIp("127.0.0.1");
		reqVO.setNotifyUrl("https://www.baidu.com/");
		reqVO.setTradeType("MWEB");
		try {
			map = WxPaymentUtil.unifiedorder(reqVO, config);
		} catch (Exception e) {
			log.info("下单失败:{}", e.getMessage());
		}
		log.info("下单成功:{}", JSON.toJSONString(map));
	}

	/**
	 * 查询订单
	 */
	public static void orderquery() {
		WxConfig config = new WxConfig();
		WxPayEnum wxPayEnum = null;
		try {
			wxPayEnum = WxPaymentUtil.orderquery("20231205123450", config);
			log.info("查询订单成功:{}", wxPayEnum);
		} catch (Exception e) {
			log.info("查询订单失败:{}", e.getMessage());
		}
	}
	
	/**
	 * 关闭订单
	 */
	public static void closeorder() {
		WxConfig config = new WxConfig();
		Boolean close = null;
		try {
			close = WxPaymentUtil.closeorder("20231205123450", config);
			log.info("关闭订单成功:{}", close);
		} catch (Exception e) {
			log.info("关闭订单失败:{}", e.getMessage());
		}
	}

	/**
	 * 申请退款
	 */
	public static void refund() {
		WxConfig config = new WxConfig();
		WxRefundReqVO reqVO = new WxRefundReqVO();
		reqVO.setOutTradeNo("20231205123450");
		reqVO.setOutRefundNo("20231205123450");
		reqVO.setTotalFee(new BigDecimal("0.01"));
		reqVO.setRefundFee(new BigDecimal("0.01"));
		Boolean close = null;
		try {
			close = WxPaymentUtil.refund(reqVO, config);
			log.info("申请退款成功:{}", close);
		} catch (Exception e) {
			log.info("申请退款失败:{}", e.getMessage());
		}
	}
	
	/**
	 * 退款查询
	 */
	public static void refundquery() {
		WxConfig config = new WxConfig();
		WxRefundEnum wxRefundEnum  = null;
		try {
			wxRefundEnum = WxPaymentUtil.refundquery("20231205123450", config);
			log.info("退款查询成功:{}", wxRefundEnum);
		} catch (Exception e) {
			log.info("退款查询失败:{}", e.getMessage());
		}
	}
}

/**
 * 如下参数需要替换成自己的
 * getAppID 公众账号ID
 * getMchID 商户号
 * getKey	商户key
 * getCertStream 证书
 */
class WxConfig implements WXPayConfig {

	@Override
	public String getAppID() {
		return "";
	}

	@Override
	public String getMchID() {
		return "";
	}

	@Override
	public String getKey() {
		return "";
	}

	@Override
	public InputStream getCertStream() {
		try {
			return new FileInputStream("");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("微信证书获取失败");
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 60000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 60000;
	}
}
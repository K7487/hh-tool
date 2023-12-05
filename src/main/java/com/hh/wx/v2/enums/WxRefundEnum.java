package com.hh.wx.v2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 微信退款返回状态枚举
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum WxRefundEnum {

	SUCCESS("SUCCESS", "退款成功"), REFUNDCLOSE("REFUNDCLOSE", "退款关闭"), PROCESSING("PROCESSING", "退款处理中"),
	CHANGE("CHANGE", "退款异常");

	private String code;
	private String msg;

	/**
	 * 根据code，返回对应枚举
	 * 
	 * @param code
	 * @return
	 */
	public static WxRefundEnum codeToEnum(String code) {
		for (WxRefundEnum values : WxRefundEnum.values()) {
			if (values.getCode().equals(code)) {
				return values;
			}
		}
		throw new RuntimeException("未找到对应枚举");
	}

	/**
	 * 根据枚举说明，返回对应枚举
	 * 
	 * @param msg
	 * @return
	 */
	public static WxRefundEnum msgToEnum(String msg) {
		for (WxRefundEnum values : WxRefundEnum.values()) {
			if (values.getMsg().equals(msg)) {
				return values;
			}
		}
		throw new RuntimeException("未找到对应枚举");
	}
}

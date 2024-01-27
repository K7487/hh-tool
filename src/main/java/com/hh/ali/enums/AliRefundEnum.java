package com.hh.ali.enums;

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
public enum AliRefundEnum {

	SUCCESS("REFUND_SUCCESS", "退款成功"),
	UNKNOWN("UNKNOWN", "退款请求未收到或者退款失败");

	private String code;
	private String msg;

	/**
	 * 根据code，返回对应枚举
	 * 
	 * @param code
	 * @return
	 */
	public static AliRefundEnum codeToEnum(String code) {
		for (AliRefundEnum values : AliRefundEnum.values()) {
			if (values.getCode().equals(code)) {
				return values;
			}
		}
		return AliRefundEnum.UNKNOWN;
	}

	/**
	 * 根据枚举说明，返回对应枚举
	 * 
	 * @param msg
	 * @return
	 */
	public static AliRefundEnum msgToEnum(String msg) {
		for (AliRefundEnum values : AliRefundEnum.values()) {
			if (values.getMsg().equals(msg)) {
				return values;
			}
		}
		throw new RuntimeException("未找到对应枚举");
	}
}

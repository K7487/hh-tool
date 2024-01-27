package com.hh.ali.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 支付宝支付返回状态枚举
 * @author huanghan
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum AliPayEnum {

	WAIT_BUYER_PAY("WAIT_BUYER_PAY", "待付款"),
	TRADE_CLOSED("TRADE_CLOSED", "交易关闭"),
	TRADE_SUCCESS("TRADE_SUCCESS", "支付成功"),
	TRADE_FINISHED("TRADE_FINISHED", "交易结束");

	
	private String code;
	private String msg;
	
	/**
	 * 根据code，返回对应枚举
	 * @param code
	 * @return
	 */
	public static AliPayEnum codeToEnum(String code) {
		for (AliPayEnum values : AliPayEnum.values()) {
			if (values.getCode().equals(code)){
				return values;
			}
		}
		throw new RuntimeException("未找到对应枚举");
	}
	
	/**
	 * 根据枚举说明，返回对应枚举
	 * @param msg
	 * @return
	 */
	public static AliPayEnum msgToEnum(String msg) {
		for (AliPayEnum values : AliPayEnum.values()) {
			if (values.getMsg().equals(msg)){
				return values;
			}
		}
		throw new RuntimeException("未找到对应枚举");
	}
}

package com.hh.wx.v2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 微信支付返回状态枚举
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum WxPayEnum {
	
	SUCCESS("SUCCESS", "支付成功"),
	REFUND("REFUND", "转入退款"),
	NOTPAY("NOTPAY", "未支付"),
	CLOSED("CLOSED", "已关闭"),
	REVOKED("REVOKED", "已撤销(刷卡支付)"),
	USERPAYING("USERPAYING", "用户支付中"),
	PAYERROR("PAYERROR", "支付失败"),
	ACCEPT("ACCEPT", "已接收，等待扣款");
	
	
	private String code;
	private String msg;
	
	/**
	 * 根据code，返回对应枚举
	 * @param code
	 * @return
	 */
	public static WxPayEnum codeToEnum(String code) {
		for (WxPayEnum values : WxPayEnum.values()) {
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
	public static WxPayEnum msgToEnum(String msg) {
		for (WxPayEnum values : WxPayEnum.values()) {
			if (values.getMsg().equals(msg)){
				return values;
			}
		}
		throw new RuntimeException("未找到对应枚举");
	}
}

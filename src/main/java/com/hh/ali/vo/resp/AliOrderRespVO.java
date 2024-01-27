package com.hh.ali.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 *
 * 支付宝回调对象
 * @author huanghan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AliOrderRespVO {


	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 返回信息
	 */
	private Map<String, Object> dataMap;

	/**
	 * 支付宝返回状态，
	 * 	成功返回 true
	 * 	失败返回 false
	 */
	private Boolean r;

}

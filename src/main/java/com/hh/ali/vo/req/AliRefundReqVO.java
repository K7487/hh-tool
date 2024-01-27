package com.hh.ali.vo.req;

import com.hh.ali.vo.entity.OpenApiRoyaltyDetailInfoPojo;
import com.hh.ali.vo.entity.RefundGoodsDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 支付宝退款订单对象
 * @author huanghan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AliRefundReqVO {

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 退款金额(单位：元)
	 * 保留两位小数
	 */
	private BigDecimal refundAmount;

	/**
	 * 退款原因说明
	 */
	private String refundReason;


	/**
	 * 退款请求号
	 * 部分退款时必选
	 * 标识一次退款请求，需要保证在交易号下唯一，如需部分退款，则此参数必传。 注：针对同一次退款请求，如果调用接口失败或异常了，重试时需要保证退款请求号不能变更，防止该笔交易重复退款。支付宝会保证同样的退款请求号多次请求只会退一次。
	 */
	private String outRequestNo;

	/**
	 * 退款包含的商品列表信息
	 */
	private RefundGoodsDetail refundGoodsDetail;

	/**
	 * 退分账明细信息。
	 * 注： 1.当面付且非直付通模式无需传入退分账明细，系统自动按退款金额与订单金额的比率，从收款方和分账收入方退款，不支持指定退款金额与退款方。 2.直付通模式，电脑网站支付，手机 APP 支付，手机网站支付产品，须在退款请求中明确是否退分账，从哪个分账收入方退，退多少分账金额；如不明确，默认从收款方退款，收款方余额不足退款失败。不支持系统按比率退款。
	 */
	private OpenApiRoyaltyDetailInfoPojo openApiRoyaltyDetailInfoPojo;

	/**
	 * 查询选项。
	 * 【枚举值】
	 * 本次退款使用的资金渠道: refund_detail_item_list
	 * 银行卡冲退信息: deposit_back_info
	 *
	 * 商户通过上送该参数来定制同步需要额外返回的信息字段，数组格式。支持：refund_detail_item_list：退款使用的资金渠道；deposit_back_info：触发银行卡冲退信息通知；
	 */
	private String queryOptions;
}


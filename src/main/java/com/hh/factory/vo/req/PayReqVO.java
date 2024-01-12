package com.hh.factory.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 支付公共请求方法
 * @author huanghan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayReqVO {

    /**
     * 下单金额，单位元（保留2位小数）
     */
    private BigDecimal amounts;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 支付者
     */
    private String openid;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;
}

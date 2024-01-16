package com.hh.factory.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 退款公共请求方法
 * @author huanghan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundReqVO {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 退款金额,单位元（保留2位小数）
     */
    private BigDecimal refundFee;
}

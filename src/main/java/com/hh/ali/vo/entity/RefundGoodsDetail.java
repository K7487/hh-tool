package com.hh.ali.vo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 退款包含的商品列表信息
 * @author huanghan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundGoodsDetail {
    /**
     * 商品编号
     */
	private String goodsId;

    /**
     * 该商品的退款总金额
     * 单位为元
     */
    private BigDecimal refundAmount;

    /**
     * 商家侧小程序商品ID
     */
    private String outItemId;

    /**
     * 商家侧小程序商品sku ID
     */
    private String outSkuId;
}

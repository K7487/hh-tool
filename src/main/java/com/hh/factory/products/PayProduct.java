package com.hh.factory.products;

import com.hh.factory.vo.req.PayReqVO;
import com.hh.factory.vo.req.RefundReqVO;
import com.hh.factory.vo.resp.WxOrderRespVO;
import com.hh.wx.v2.enums.WxPayEnum;
import com.hh.wx.v2.enums.WxRefundEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PayProduct{
    /**
     * 下单
     * @param reqVO
     * @return
     */
    Map<String, String> placeOrder(PayReqVO reqVO);

    /**
     * 查询订单
     * @param orderNo 订单号
     * @return
     */
    WxPayEnum orderquery(Long orderNo);

    /**
     * 关闭订单
     * @param orderNo 订单号
     * @return
     */
    Boolean closeorder(Long orderNo);

    /**
     * 退款申请
     * @param reqVO
     * @return
     */
    Boolean refund(RefundReqVO reqVO);

    /**
     * 查询退款订单
     * @param orderNo 订单号
     * @return
     */
    WxRefundEnum refundquery(Long orderNo);

    /**
     * 支付回调
     * @param request
     * @return
     */
    WxOrderRespVO callback(HttpServletRequest request);
}

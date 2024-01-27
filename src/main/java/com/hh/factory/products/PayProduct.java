package com.hh.factory.products;


import com.hh.factory.vo.req.PayReqVO;
import com.hh.factory.vo.req.RefundReqVO;

import javax.servlet.http.HttpServletRequest;

public interface PayProduct{
    /**
     * 下单
     * @param reqVO
     * @return 返回收银台信息
     */
    <E> E placeOrder(PayReqVO reqVO);

    /**
     * 查询订单
     * @param orderNo 订单号
     * @return
     */
    <T> T orderquery(String orderNo);

    /**
     * 关闭订单
     * @param orderNo 订单号
     * @return
     */
    Boolean closeorder(String orderNo);

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
    <T> T refundquery(String orderNo);

    /**
     * 支付回调
     * @param request
     * @return
     */
    <T> T callback(HttpServletRequest request);
}

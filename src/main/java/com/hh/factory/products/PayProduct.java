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
     * @param reqVO
     * @return
     */
    <T> T orderquery(PayReqVO reqVO);

    /**
     * 关闭订单
     * @param reqVO
     * @return
     */
    Boolean closeorder(PayReqVO reqVO);

    /**
     * 撤销订单
     * @param reqVO
     * @return
     */
    Boolean reverse(PayReqVO reqVO);

    /**
     * 退款申请
     * @param reqVO
     * @return
     */
    Boolean refund(RefundReqVO reqVO);

    /**
     * 查询退款订单
     * @param reqVO
     * @return
     */
    <T> T refundquery(RefundReqVO reqVO);

    /**
     * 支付回调
     * @param request
     * @return
     */
    <T> T callback(HttpServletRequest request);
}

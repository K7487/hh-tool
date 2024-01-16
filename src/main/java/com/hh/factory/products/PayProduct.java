package com.hh.factory.products;

import com.hh.factory.vo.req.PayReqVO;
import com.hh.factory.vo.req.RefundReqVO;
import com.hh.factory.vo.resp.WxOrderRespVO;
import com.hh.wx.v2.enums.WxPayEnum;
import com.hh.wx.v2.enums.WxRefundEnum;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Documented;
import java.util.Map;

public interface PayProduct{
    /**
     * 下单
     * @param reqVO
     * @return 返回收银台信息
     */
    Map<String, String> placeOrder(PayReqVO reqVO);

    /**
     * 查询订单
     * @param orderNo 订单号
     * @return
     */
    WxPayEnum orderquery(String orderNo);

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
    WxRefundEnum refundquery(String orderNo);

    /**
     * 支付回调
     * @param request
     * @return
     */
    WxOrderRespVO callback(HttpServletRequest request);
}

package com.hh.factory.products;


import com.hh.factory.vo.req.PayReqVO;
import com.hh.wx.v3.constant.Wx3ConfigConstant;

public interface PayV3Product {
    /**
     * 下单
     * @param reqVO
     * @return 返回收银台信息
     */
    <E> E placeOrder(PayReqVO reqVO, Wx3ConfigConstant cfg);

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

}

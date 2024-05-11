package com.hh.factory.products;


import com.hh.factory.vo.req.PayReqVO;

public interface PayV3Product {
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

}

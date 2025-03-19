package com.hh.factory.products;


import com.hh.factory.vo.req.PayReqVO;
import com.hh.factory.vo.req.RefundReqVO;

import javax.servlet.http.HttpServletRequest;

public interface PayProduct {

    /**
     * 统一下单
     *
     * @param reqVO 支付公共请求方法
     * @param <E>   支付宝：String，微信：Map
     * @return 统一下单返回结果
     */
    <E> E placeOrder(PayReqVO reqVO);

    /**
     * 统一下单
     *
     * @param reqVO  支付公共请求方法
     * @param cfg 支付配置
     * @param <E>    支付宝：String，微信：Map
     * @return 统一下单返回结果
     */
    <E> E placeOrder(PayReqVO reqVO, Object cfg);

    /**
     * 查询订单
     *
     * @param reqVO 支付公共请求方法
     * @param <T>   支付宝：AliPayEnum，微信：WxPayEnum
     * @return 查询订单返回结果
     */
    <T> T orderquery(PayReqVO reqVO);

    /**
     * 查询订单
     *
     * @param reqVO  支付公共请求方法
     * @param cfg 支付配置
     * @param <T>    支付宝：AliPayEnum，微信：WxPayEnum
     * @return 查询订单返回结果
     */
    <T> T orderquery(PayReqVO reqVO, Object cfg);

    /**
     * 关闭订单
     *
     * @param reqVO 支付公共请求方法
     * @return 成功：true，失败：false
     */
    Boolean closeorder(PayReqVO reqVO);

    /**
     * 关闭订单
     *
     * @param reqVO  支付公共请求方法
     * @param cfg 支付配置
     * @return 成功：true，失败：false
     */
    Boolean closeorder(PayReqVO reqVO, Object cfg);

    /**
     * 撤销订单
     *
     * @param reqVO 支付公共请求方法
     * @return 成功：true，失败：false
     */
    Boolean reverse(PayReqVO reqVO);

    /**
     * 撤销订单
     *
     * @param reqVO  支付公共请求方法
     * @param cfg 支付配置
     * @return 成功：true，失败：false
     */
    Boolean reverse(PayReqVO reqVO, Object cfg);

    /**
     * 退款申请
     *
     * @param reqVO 退款公共请求方法
     * @return 成功：true，失败：false
     */
    Boolean refund(RefundReqVO reqVO);

    /**
     * 退款申请
     *
     * @param reqVO  退款公共请求方法
     * @param cfg 支付配置
     * @return 成功：true，失败：false
     */
    Boolean refund(RefundReqVO reqVO, Object cfg);

    /**
     * 查询退款订单
     *
     * @param reqVO 退款公共请求方法
     * @param <T>   支付宝：AliRefundEnum，微信：WxRefundEnum
     * @return 查询退款订单返回结果
     */
    <T> T refundquery(RefundReqVO reqVO);

    /**
     * 查询退款订单
     *
     * @param reqVO  支付公共请求方法
     * @param cfg 支付配置
     * @param <T>    支付宝：AliRefundEnum，微信：WxRefundEnum
     * @return 查询退款订单返回结果
     */
    <T> T refundquery(RefundReqVO reqVO, Object cfg);

    /**
     * 支付回调
     *
     * @param request 请求
     * @param <T>     支付宝：AliOrderRespVO，微信：WxOrderRespVO
     * @return 支付回调返回结果
     */
    <T> T callback(HttpServletRequest request);

    /**
     * 支付回调
     *
     * @param request 请求
     * @param cfg  支付配置
     * @param <T>     支付宝：AliOrderRespVO，微信：WxOrderRespVO
     * @return 支付回调返回结果
     */
    <T> T callback(HttpServletRequest request, Object cfg);
}

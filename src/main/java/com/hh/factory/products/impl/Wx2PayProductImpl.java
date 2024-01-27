package com.hh.factory.products.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.hh.enums.PayType;
import com.hh.factory.products.PayProduct;
import com.hh.factory.util.OrderCheck;
import com.hh.factory.vo.req.PayReqVO;
import com.hh.factory.vo.req.RefundReqVO;
import com.hh.factory.vo.resp.WxOrderRespVO;
import com.hh.wx.v2.config.WxConfig;
import com.hh.wx.v2.constant.WxConstant;
import com.hh.wx.v2.enums.WxPayEnum;
import com.hh.wx.v2.enums.WxRefundEnum;
import com.hh.wx.v2.util.WxPaymentUtil;
import com.hh.wx.v2.vo.WxOrderReqVO;
import com.hh.wx.v2.vo.WxRefundReqVO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/**
 * 微信V2版本支付
 *
 * @author huanghan
 */
@Service
@AllArgsConstructor
@Log4j2
public class Wx2PayProductImpl implements PayProduct {

    private WxConfig wxConfig;
    private WxConstant wxConstant;
    private OrderCheck orderCheck;

    private static final String HEAD = "[微信支付]";

    @Override
    public Map<String, String> placeOrder(PayReqVO reqVO) {
        WxOrderReqVO wxOrderReqVO = new WxOrderReqVO();
        Map<String, String> map;
        String tradeType = orderCheck.placeOrderIsNull(reqVO, PayType.WX_V2);
        wxOrderReqVO.setSpbillCreateIp(new ArrayList<>(NetUtil.localIpv4s()).get(0));
        wxOrderReqVO.setNotifyUrl(wxConstant.getNotifyUrl());
        wxOrderReqVO.setTradeType(tradeType);
        wxOrderReqVO.setBody(reqVO.getDescription());
        wxOrderReqVO.setOutTradeNo(reqVO.getOrderNo());
        wxOrderReqVO.setTotalFee(reqVO.getAmounts());
        wxOrderReqVO.setOpenid(reqVO.getOpenid());
        try {
            map = WxPaymentUtil.unifiedorder(wxOrderReqVO, wxConfig);
        } catch (Exception e) {
            log.error(HEAD + "下单失败：", e);
            throw new RuntimeException(HEAD + "下单失败：" + e.getMessage());
        }
        log.info(HEAD + "下单成功:{}", JSON.toJSONString(map));
        return map;
    }

    @Override
    public WxPayEnum orderquery(String orderNo) {
        WxPayEnum wxPayEnum = null;
        if (ObjectUtil.isEmpty(orderNo)) {
            throw new RuntimeException(HEAD + "订单号不能为空");
        }
        try {
            wxPayEnum = WxPaymentUtil.orderquery(orderNo, wxConfig);
            log.info(HEAD + "查询订单成功:{}", wxPayEnum);
        } catch (Exception e) {
            log.error(HEAD + "查询订单失败:", e);
            throw new RuntimeException(HEAD + "查询订单失败:" + e.getMessage());
        }
        return wxPayEnum;
    }

    @Override
    public Boolean closeorder(String orderNo) {
        Boolean close = null;
        if (ObjectUtil.isEmpty(orderNo)) {
            throw new RuntimeException(HEAD + "订单号不能为空");
        }
        try {
            close = WxPaymentUtil.closeorder(orderNo.toString(), wxConfig);
            log.info(HEAD + "关闭订单:{}", close);
        } catch (Exception e) {
            log.error(HEAD + "关闭订单失败:", e);
            throw new RuntimeException(HEAD + "关闭订单失败:" + e.getMessage());
        }
        return close;
    }

    @Override
    public Boolean refund(RefundReqVO reqVO) {
        if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
            throw new RuntimeException("订单号不能为空");
        }
        if (ObjectUtil.isEmpty(reqVO.getRefundFee())) {
            throw new RuntimeException("退款金额不能为空");
        }
        WxRefundReqVO refundReqVO = new WxRefundReqVO();
        refundReqVO.setOutTradeNo(reqVO.getOrderNo());
        refundReqVO.setOutRefundNo(reqVO.getOrderNo());
        refundReqVO.setTotalFee(reqVO.getRefundFee());
        refundReqVO.setRefundFee(reqVO.getRefundFee());
        refundReqVO.setNotifyUrl(wxConstant.getNotifyUrl());
        Boolean close = null;
        try {
            close = WxPaymentUtil.refund(refundReqVO, wxConfig);
            log.info(HEAD + "申请退款:{}", close);
        } catch (Exception e) {
            log.error(HEAD + "申请退款失败:", e);
            throw new RuntimeException(HEAD + "申请退款失败:" + e.getMessage());
        }
        return close;
    }

    @Override
    public WxRefundEnum refundquery(String orderNo) {
        WxRefundEnum wxRefundEnum = null;
        try {
            wxRefundEnum = WxPaymentUtil.refundquery(orderNo, wxConfig);
            log.info(HEAD + "退款查询成功:{}", wxRefundEnum);
        } catch (Exception e) {
            log.error(HEAD + "退款查询失败:", e);
            throw new RuntimeException(HEAD + "退款查询失败:" + e.getMessage());
        }
        return wxRefundEnum;
    }

//    @Override
//    public <T> T callback(HttpServletRequest request) {
//        try {
//            return WxOrderRespVO WxPaymentUtil.callback(request, wxConfig);
//        } catch (Exception e) {
//            log.error("微信支付回调失败:", e);
//            throw new RuntimeException(e.getMessage());
//        }
//    }

    @Override
    public WxOrderRespVO callback(HttpServletRequest request) {
        WxOrderRespVO wxOrderRespVO = null;
        try {
            wxOrderRespVO = WxPaymentUtil.callback(request, wxConfig);
            log.info(HEAD + "回调:{}", JSON.toJSONString(wxOrderRespVO));
        } catch (Exception e) {
            log.error(HEAD + "回调失败:", e);
            throw new RuntimeException(HEAD + "回调失败:" + e.getMessage());
        }
        return wxOrderRespVO;
    }


//    @Override
//    public Map<String, String> placeOrder(PayReqVO reqVO) {
//        WxOrderReqVO wxOrderReqVO = new WxOrderReqVO();
//        Map<String, String> map;
//        if (ObjectUtil.isEmpty(reqVO.getDescription())) {
//            throw new RuntimeException("商品描述不能为空");
//        }
//        if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
//            throw new RuntimeException("订单号不能为空");
//        }
//        if (ObjectUtil.isEmpty(reqVO.getAmounts())) {
//            throw new RuntimeException("下单金额不能为空");
//        }
//        String tradeType = null;
//        if (ObjectUtil.isNotEmpty(reqVO.getTradeType())) {
//            tradeType = reqVO.getTradeType();
//        } else {
//            tradeType = wxConstant.getTradeType();
//        }
//        if (ObjectUtil.isEmpty(tradeType)) {
//            throw new RuntimeException("交易类型不能为空");
//        }
//        if ("JSAPI".equals(tradeType) && ObjectUtil.isEmpty(reqVO.getOpenid())) {
//            throw new RuntimeException("交易类型为：JSAPI，openid不能为空");
//        }
//        wxOrderReqVO.setSpbillCreateIp(new ArrayList<>(NetUtil.localIpv4s()).get(0));
//        wxOrderReqVO.setNotifyUrl(wxConstant.getNotifyUrl());
//        wxOrderReqVO.setTradeType(tradeType);
//        wxOrderReqVO.setBody(reqVO.getDescription());
//        wxOrderReqVO.setOutTradeNo(reqVO.getOrderNo());
//        wxOrderReqVO.setTotalFee(reqVO.getAmounts());
//        wxOrderReqVO.setOpenid(reqVO.getOpenid());
//        try {
//            map = WxPaymentUtil.unifiedorder(wxOrderReqVO, wxConfig);
//        } catch (Exception e) {
//            log.error("下单失败：",e);
//            throw new RuntimeException("下单失败：" + e.getMessage());
//        }
//        log.info("下单成功:{}", JSON.toJSONString(map));
//        return map;
//    }
//
//    @Override
//    public WxPayEnum orderquery(String orderNo) {
//        WxPayEnum wxPayEnum = null;
//        if (ObjectUtil.isEmpty(orderNo)) {
//            throw new RuntimeException("订单号不能为空");
//        }
//        try {
//            wxPayEnum = WxPaymentUtil.orderquery(orderNo, wxConfig);
//            log.info("查询订单成功:{}", wxPayEnum);
//        } catch (Exception e) {
//            log.error("查询订单失败:", e);
//        }
//        return wxPayEnum;
//    }
//
//    @Override
//    public Boolean closeorder(String orderNo) {
//        Boolean close = null;
//        if (ObjectUtil.isEmpty(orderNo)) {
//            throw new RuntimeException("订单号不能为空");
//        }
//        try {
//            close = WxPaymentUtil.closeorder(orderNo.toString(), wxConfig);
//            log.info("关闭订单成功:{}", close);
//        } catch (Exception e) {
//            log.error("关闭订单失败:", e);
//        }
//        return close;
//    }
//
//    @Override
//    public Boolean refund(RefundReqVO reqVO) {
//        if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
//            throw new RuntimeException("订单号不能为空");
//        }
//        if (ObjectUtil.isEmpty(reqVO.getRefundFee())) {
//            throw new RuntimeException("退款金额不能为空");
//        }
//        WxRefundReqVO refundReqVO = new WxRefundReqVO();
//        refundReqVO.setOutTradeNo(reqVO.getOrderNo());
//        refundReqVO.setOutRefundNo(reqVO.getOrderNo());
//        refundReqVO.setTotalFee(reqVO.getRefundFee());
//        refundReqVO.setRefundFee(reqVO.getRefundFee());
//        refundReqVO.setNotifyUrl(wxConstant.getNotifyUrl());
//        Boolean close = null;
//        try {
//            close = WxPaymentUtil.refund(refundReqVO, wxConfig);
//            log.info("申请退款成功:{}", close);
//        } catch (Exception e) {
//            log.error("申请退款失败:", e);
//            throw new RuntimeException("申请退款失败:" + e.getMessage());
//        }
//        return close;
//    }
//
//    @Override
//    public WxRefundEnum refundquery(String orderNo) {
//        WxRefundEnum wxRefundEnum  = null;
//        try {
//            wxRefundEnum = WxPaymentUtil.refundquery(orderNo, wxConfig);
//            log.info("退款查询成功:{}", wxRefundEnum);
//        } catch (Exception e) {
//            log.error("退款查询失败:", e);
//        }
//        return wxRefundEnum;
//    }
//
//    @Override
//    public WxOrderRespVO callback(HttpServletRequest request) {
//        try {
//            return WxPaymentUtil.callback(request, wxConfig);
//        } catch (Exception e) {
//            log.error("微信支付回调失败:", e);
//            return null;
//        }
//    }
}

package com.hh.factory.products.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayConfig;
import com.github.wxpay.sdk.WXPayConfig;
import com.hh.constants.Pay;
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
        WXPayConfig wxPayConfig = wxConfig;
        return getPlaceOrder(reqVO, wxPayConfig);
    }

    @Override
    public Map<String, String> placeOrder(PayReqVO reqVO, Object cfg) {
        WXPayConfig wxPayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            wxPayConfig = (WXPayConfig) cfg;
        } else {
            wxPayConfig = wxConfig;
        }
        return getPlaceOrder(reqVO, wxPayConfig);
    }

    private Map<String, String> getPlaceOrder(PayReqVO reqVO, WXPayConfig wxPayConfig) {
        String notifyUrl;
        if (ObjectUtil.isNotEmpty(reqVO.getNotifyUrl())) {
            notifyUrl = reqVO.getNotifyUrl();
        } else {
            notifyUrl = wxConstant.getNotifyUrl();
        }
        WxOrderReqVO wxOrderReqVO = new WxOrderReqVO();
        Map<String, String> map;
        String tradeType = orderCheck.placeOrderIsNull(reqVO, PayType.WX_V2);
        wxOrderReqVO.setSpbillCreateIp(new ArrayList<>(NetUtil.localIpv4s()).get(0));
        wxOrderReqVO.setNotifyUrl(notifyUrl);
        wxOrderReqVO.setTradeType(tradeType);
        wxOrderReqVO.setBody(reqVO.getDescription());
        wxOrderReqVO.setOutTradeNo(reqVO.getOrderNo());
        wxOrderReqVO.setTotalFee(reqVO.getAmounts());
        wxOrderReqVO.setOpenid(reqVO.getOpenid());
        wxOrderReqVO.setAuthCode(reqVO.getAuthCode());
        wxOrderReqVO.setSubMchId(reqVO.getSubMchId());
        wxOrderReqVO.setSubAppid(reqVO.getSubAppid());
        wxOrderReqVO.setSubOpenid(reqVO.getSubOpenid());
        try {
            if (Pay.TradeType.MICROPAY.equals(reqVO.getTradeType())) {
                map = WxPaymentUtil.microPay(wxOrderReqVO, wxPayConfig);
            } else {
                map = WxPaymentUtil.unifiedorder(wxOrderReqVO, wxPayConfig);
            }
        } catch (Exception e) {
            log.error(HEAD + "下单失败：", e);
            throw new RuntimeException(HEAD + "下单失败：" + e.getMessage());
        }
        log.info(HEAD + "下单成功:{}", JSON.toJSONString(map));
        return map;
    }

    @Override
    public WxPayEnum orderquery(PayReqVO reqVO) {
        return getOrderquery(reqVO, wxConfig);
    }

    @Override
    public WxPayEnum orderquery(PayReqVO reqVO, Object cfg) {
        WXPayConfig wxPayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            wxPayConfig = (WXPayConfig) cfg;
        } else {
            wxPayConfig = wxConfig;
        }
        return getOrderquery(reqVO, wxPayConfig);
    }

    private WxPayEnum getOrderquery(PayReqVO reqVO, WXPayConfig wxPayConfig) {
        WxPayEnum wxPayEnum = null;
        if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
            throw new RuntimeException(HEAD + "订单号不能为空");
        }
        try {
            wxPayEnum = WxPaymentUtil.orderquery(reqVO.getOrderNo(), reqVO.getSubMchId(), wxPayConfig);
            log.info(HEAD + "查询订单成功:{}", wxPayEnum);
        } catch (Exception e) {
            log.error(HEAD + "查询订单失败:", e);
            throw new RuntimeException(HEAD + "查询订单失败:" + e.getMessage());
        }
        return wxPayEnum;
    }

    @Override
    public Boolean closeorder(PayReqVO reqVO) {
        return getCloseorder(reqVO, wxConfig);
    }


    @Override
    public Boolean closeorder(PayReqVO reqVO, Object cfg) {
        WXPayConfig wxPayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            wxPayConfig = (WXPayConfig) cfg;
        } else {
            wxPayConfig = wxConfig;
        }
        return getCloseorder(reqVO, wxPayConfig);
    }

    private Boolean getCloseorder(PayReqVO reqVO, WXPayConfig wxConfig) {
        Boolean close = null;
        if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
            throw new RuntimeException(HEAD + "订单号不能为空");
        }
        try {
            close = WxPaymentUtil.closeorder(reqVO.getOrderNo(), reqVO.getSubMchId(), wxConfig);
            log.info(HEAD + "关闭订单:{}", close);
        } catch (Exception e) {
            log.error(HEAD + "关闭订单失败:", e);
            throw new RuntimeException(HEAD + "关闭订单失败:" + e.getMessage());
        }
        return close;
    }

    @Override
    public Boolean reverse(PayReqVO reqVO) {
        return getReverse(reqVO, wxConfig);
    }

    @Override
    public Boolean reverse(PayReqVO reqVO, Object cfg) {
        WXPayConfig wxPayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            wxPayConfig = (WXPayConfig) cfg;
        } else {
            wxPayConfig = wxConfig;
        }
        return getReverse(reqVO, wxPayConfig);
    }

    private Boolean getReverse(PayReqVO reqVO, WXPayConfig wxPayConfig) {
        Boolean close = null;
        if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
            throw new RuntimeException(HEAD + "订单号不能为空");
        }
        if (ObjectUtil.isEmpty(wxConfig.getCertStream())) {
            throw new RuntimeException(HEAD + "证书不能为空");
        }
        try {
            close = WxPaymentUtil.reverse(reqVO.getOrderNo(), reqVO.getSubMchId(), wxPayConfig);
            log.info(HEAD + "撤销订单:{}", close);
        } catch (Exception e) {
            log.error(HEAD + "撤销订单失败:", e);
            throw new RuntimeException(HEAD + "撤销订单失败:" + e.getMessage());
        }
        return close;
    }

    @Override
    public Boolean refund(RefundReqVO reqVO) {
        return getRefund(reqVO, wxConfig);
    }

    @Override
    public Boolean refund(RefundReqVO reqVO, Object cfg) {
        WXPayConfig wxPayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            wxPayConfig = (WXPayConfig) cfg;
        } else {
            wxPayConfig = wxConfig;
        }
        return getRefund(reqVO, wxPayConfig);
    }

    private Boolean getRefund(RefundReqVO reqVO, WXPayConfig wxPayConfig) {
        String notifyUrl;
        if (ObjectUtil.isNotEmpty(reqVO.getNotifyUrl())) {
            notifyUrl = reqVO.getNotifyUrl();
        } else {
            notifyUrl = wxConstant.getNotifyUrl();
        }
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
        refundReqVO.setNotifyUrl(notifyUrl);
        refundReqVO.setSubMchId(reqVO.getSubMchId());
        Boolean close = null;
        try {
            close = WxPaymentUtil.refund(refundReqVO, wxPayConfig);
            log.info(HEAD + "申请退款:{}", close);
        } catch (Exception e) {
            log.error(HEAD + "申请退款失败:", e);
            throw new RuntimeException(HEAD + "申请退款失败:" + e.getMessage());
        }
        return close;
    }

    @Override
    public WxRefundEnum refundquery(RefundReqVO reqVO) {
        return getRefundquery(reqVO, wxConfig);
    }

    @Override
    public WxRefundEnum refundquery(RefundReqVO reqVO, Object cfg) {
        WXPayConfig wxPayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            wxPayConfig = (WXPayConfig) cfg;
        } else {
            wxPayConfig = wxConfig;
        }
        return getRefundquery(reqVO, wxPayConfig);
    }

    private WxRefundEnum getRefundquery(RefundReqVO reqVO, WXPayConfig wxPayConfig) {
        WxRefundEnum wxRefundEnum = null;
        try {
            if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
                throw new RuntimeException(HEAD + "订单号不能为空");
            }
            wxRefundEnum = WxPaymentUtil.refundquery(reqVO.getOrderNo(), reqVO.getSubMchId(), wxPayConfig);
            log.info(HEAD + "退款查询成功:{}", wxRefundEnum);
        } catch (Exception e) {
            log.error(HEAD + "退款查询失败:", e);
            throw new RuntimeException(HEAD + "退款查询失败:" + e.getMessage());
        }
        return wxRefundEnum;
    }

    @Override
    public WxOrderRespVO callback(HttpServletRequest request) {
        return getCallback(request, wxConfig);
    }

    @Override
    public WxOrderRespVO callback(HttpServletRequest request, Object cfg) {
        WXPayConfig wxPayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            wxPayConfig = (WXPayConfig) cfg;
        } else {
            wxPayConfig = wxConfig;
        }
        return getCallback(request, wxPayConfig);
    }

    private WxOrderRespVO getCallback(HttpServletRequest request, WXPayConfig wxPayConfig) {
        WxOrderRespVO wxOrderRespVO = null;
        try {
            wxOrderRespVO = WxPaymentUtil.callback(request, wxPayConfig);
            log.info(HEAD + "回调:{}", JSON.toJSONString(wxOrderRespVO));
        } catch (Exception e) {
            log.error(HEAD + "回调失败:", e);
            throw new RuntimeException(HEAD + "回调失败:" + e.getMessage());
        }
        return wxOrderRespVO;
    }
}

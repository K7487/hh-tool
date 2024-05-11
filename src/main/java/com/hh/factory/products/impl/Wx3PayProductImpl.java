package com.hh.factory.products.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.hh.constants.Pay;
import com.hh.factory.products.PayProduct;
import com.hh.factory.vo.req.PayReqVO;
import com.hh.factory.vo.req.RefundReqVO;
import com.hh.factory.vo.resp.WxOrderRespVO;
import com.hh.wx.v2.config.WxConfig;
import com.hh.wx.v2.enums.WxPayEnum;
import com.hh.wx.v2.enums.WxRefundEnum;
import com.hh.wx.v2.util.WxPaymentUtil;
import com.hh.wx.v3.constant.Wx3Constant;
import com.hh.wx.v3.products.Wx3AppService;
import com.hh.wx.v3.products.Wx3H5Service;
import com.hh.wx.v3.products.Wx3JsapiService;
import com.hh.wx.v3.products.Wx3NativePayService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 微信V3版本支付
 *
 * @author huanghan
 */
@Service
@AllArgsConstructor
@Log4j2
public class Wx3PayProductImpl implements PayProduct {

    private WxConfig wxConfig;
    private Wx3Constant wx3Constant;
    private Wx3NativePayService nativePayService;
    private Wx3JsapiService wx3JsapiService;
    private Wx3AppService wx3AppService;
    private Wx3H5Service wx3H5Service;

    private static final String HEAD = "[微信支付]";

    @Override
    public Map<String, String> placeOrder(PayReqVO reqVO) {
        switch (reqVO.getTradeType()) {
            case Pay.TradeType.JSAPI:
                return wx3JsapiService.placeOrder(reqVO);
            case Pay.TradeType.NATIVE:
                return nativePayService.placeOrder(reqVO);
            case Pay.TradeType.APP:
                return wx3AppService.placeOrder(reqVO);
            case Pay.TradeType.MWEB:
                return wx3H5Service.placeOrder(reqVO);
            default:
                throw new RuntimeException("支付类型有误");
        }


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
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(wx3Constant.getMerchantId())
                .privateKeyFromPath(wx3Constant.getPrivateKeyPath())
                .merchantSerialNumber(wx3Constant.getMerchantSerialNumber())
                .apiV3Key(wx3Constant.getApiV3Key())
                .build();
        RefundService service = new RefundService.Builder().config(config).build();
        CreateRequest request = new CreateRequest();
        request.setOutTradeNo(reqVO.getOrderNo());
        request.setOutRefundNo(reqVO.getOrderNo());
        AmountReq amountReq = new AmountReq();
        Long refundFee = reqVO.getRefundFee().multiply(new BigDecimal(100)).longValue();
        amountReq.setTotal(refundFee);
        amountReq.setRefund(refundFee);
        request.setAmount(amountReq);
        request.setNotifyUrl(wx3Constant.getNotifyUrl());
        log.info(HEAD + "申请退款,入参:{}", JSON.toJSONString(request));
        Refund refund = service.create(request);
        log.info(HEAD + "申请退款,出参:{}", JSON.toJSONString(refund));
        switch (refund.getStatus()) {
            case SUCCESS:
            case PROCESSING:
                return true;
        }
        return false;
    }

    @Override
    public WxRefundEnum refundquery(String orderNo) {
        if (ObjectUtil.isEmpty(orderNo)) {
            throw new RuntimeException("订单号不能为空");
        }
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(wx3Constant.getMerchantId())
                .privateKeyFromPath(wx3Constant.getPrivateKeyPath())
                .merchantSerialNumber(wx3Constant.getMerchantSerialNumber())
                .apiV3Key(wx3Constant.getApiV3Key())
                .build();
        RefundService service = new RefundService.Builder().config(config).build();
        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
        request.setOutRefundNo(orderNo);
        log.info(HEAD + "退款查询,入参:{}", JSON.toJSONString(request));
        Refund refund = service.queryByOutRefundNo(request);
        log.info(HEAD + "退款查询,出参:{}", JSON.toJSONString(refund));
        return WxRefundEnum.codeToEnum(refund.getStatus().name());
    }

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


}

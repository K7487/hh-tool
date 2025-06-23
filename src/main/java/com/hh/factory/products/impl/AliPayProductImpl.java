package com.hh.factory.products.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayConfig;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.hh.ali.conig.AliConfig;
import com.hh.ali.enums.AliPayEnum;
import com.hh.ali.enums.AliRefundEnum;
import com.hh.ali.util.AliPayUtil;
import com.hh.ali.vo.req.AliRefundReqVO;
import com.hh.ali.vo.resp.AliOrderRespVO;
import com.hh.enums.PayType;
import com.hh.factory.products.PayProduct;
import com.hh.factory.util.OrderCheck;
import com.hh.factory.vo.req.PayReqVO;
import com.hh.factory.vo.req.RefundReqVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


/**
 * 支付宝支付实现类
 *
 * @author huanghan
 */
@Service
@Log4j2
@AllArgsConstructor
public class AliPayProductImpl implements PayProduct {

    private AliConfig config;
    private OrderCheck orderCheck;
    private static final String HEAD = "[支付宝支付]";

    @Override
    public String placeOrder(PayReqVO reqVO) {
        AlipayConfig alipayConfig = config;
        return getPlaceOrder(reqVO, alipayConfig);
    }

    @Override
    public String placeOrder(PayReqVO reqVO, Object cfg) {
        AlipayConfig alipayConfig;
        String notifyUrl;
        if (ObjectUtil.isNotEmpty(cfg)) {
            alipayConfig = (AlipayConfig) cfg;
        } else {
            alipayConfig = config;
        }
        return getPlaceOrder(reqVO, alipayConfig);
    }

    private String getPlaceOrder(PayReqVO reqVO, AlipayConfig alipayConfig) {
        String notifyUrl;
        if (ObjectUtil.isNotEmpty(reqVO.getNotifyUrl())) {
            notifyUrl = reqVO.getNotifyUrl();
        } else {
            notifyUrl = config.getNotifyUrl();
        }
        String tradeType = orderCheck.placeOrderIsNull(reqVO, PayType.ZFB);
        String tradeNo = null;
        switch (tradeType) {
            case "JSAPI_PAY" -> {
                AlipayTradeCreateModel model = new AlipayTradeCreateModel();
                AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
                model.setOutTradeNo(reqVO.getOrderNo());
                model.setTotalAmount(reqVO.getAmounts().toString());
                model.setSubject(reqVO.getDescription());
                model.setProductCode(tradeType);
                model.setOpAppId(reqVO.getOpAppId());
                model.setBuyerId(reqVO.getOpenid());
                request.setNotifyUrl(notifyUrl);
                if (ObjectUtil.isNotEmpty(reqVO.getSubAuthToken())) {
                    request.putOtherTextParam("app_auth_token", reqVO.getSubAuthToken());
                }
                try {
                    tradeNo = AliPayUtil.unifiedorder(model, alipayConfig, request);
                    log.info(HEAD + "下单成功:{}", tradeNo);
                } catch (Exception e) {
                    log.error(HEAD + "下单失败：", e);
                    throw new RuntimeException(HEAD + "下单失败:{}" + e.getMessage());
                }
                return tradeNo;
            }
            case "QUICK_WAP_WAY" -> {
                AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
                AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
                model.setOutTradeNo(reqVO.getOrderNo());
                model.setTotalAmount(reqVO.getAmounts().toString());
                model.setSubject(reqVO.getDescription());
                model.setProductCode(tradeType);
                request.setNotifyUrl(notifyUrl);
                if (ObjectUtil.isNotEmpty(reqVO.getSubAuthToken())) {
                    request.putOtherTextParam("app_auth_token", reqVO.getSubAuthToken());
                }
                try {
                    tradeNo = AliPayUtil.unifiedorder(model, alipayConfig, request);
                    log.info(HEAD + "下单成功:{}", tradeNo);
                } catch (Exception e) {
                    log.error(HEAD + "下单失败：", e);
                    throw new RuntimeException(HEAD + "下单失败:{}" + e.getMessage());
                }
                return tradeNo;
            }
            case "QUICK_MSECURITY_PAY" -> {
                AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
                model.setOutTradeNo(reqVO.getOrderNo());
                model.setTotalAmount(reqVO.getAmounts().toString());
                model.setSubject(reqVO.getDescription());
                model.setProductCode(tradeType);
                request.setNotifyUrl(notifyUrl);
                request.setBizModel(model);
                if (ObjectUtil.isNotEmpty(reqVO.getSubAuthToken())) {
                    request.putOtherTextParam("app_auth_token", reqVO.getSubAuthToken());
                }
                try {
                    tradeNo = AliPayUtil.unifiedorder(model, alipayConfig, request);
                    log.info(HEAD + "下单成功:{}", tradeNo);
                } catch (Exception e) {
                    log.error(HEAD + "下单失败：", e);
                    throw new RuntimeException(HEAD + "下单失败:{}" + e.getMessage());
                }
                return tradeNo;
            }
            case "FAST_INSTANT_TRADE_PAY" -> {
                AlipayTradePagePayModel model = new AlipayTradePagePayModel();
                AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
                model.setOutTradeNo(reqVO.getOrderNo());
                model.setTotalAmount(reqVO.getAmounts().toString());
                model.setSubject(reqVO.getDescription());
                model.setProductCode(tradeType);
                request.setNotifyUrl(notifyUrl);
                if (ObjectUtil.isNotEmpty(reqVO.getSubAuthToken())) {
                    request.putOtherTextParam("app_auth_token", reqVO.getSubAuthToken());
                }
                try {
                    tradeNo = AliPayUtil.unifiedorder(model, alipayConfig, request);
                    log.info(HEAD + "下单成功:{}", tradeNo);
                } catch (Exception e) {
                    log.error(HEAD + "下单失败：", e);
                    throw new RuntimeException(HEAD + "下单失败:{}" + e.getMessage());
                }
                return tradeNo;
            }
            case "MICROPAY" -> {
                AlipayTradePayModel model = new AlipayTradePayModel();
                AlipayTradePayRequest request = new AlipayTradePayRequest();
                model.setOutTradeNo(reqVO.getOrderNo());
                model.setTotalAmount(reqVO.getAmounts().toString());
                model.setSubject(reqVO.getDescription());
                model.setAuthCode(reqVO.getAuthCode());
                model.setScene("bar_code");
                request.setNotifyUrl(notifyUrl);
                if (ObjectUtil.isNotEmpty(reqVO.getSubAuthToken())) {
                    request.putOtherTextParam("app_auth_token", reqVO.getSubAuthToken());
                }
                try {
                    tradeNo = AliPayUtil.unifiedorder(model, alipayConfig, request);
                    log.info(HEAD + "下单成功:{}", tradeNo);
                } catch (Exception e) {
                    log.error(HEAD + "下单失败：", e);
                    throw new RuntimeException(HEAD + "下单失败:{}" + e.getMessage());
                }
                return tradeNo;
            }
            default -> throw new RuntimeException("支付类型有误");
        }
    }

    @Override
    public AliPayEnum orderquery(PayReqVO reqVO) {
        return getOrderquery(reqVO, config);
    }

    @Override
    public AliPayEnum orderquery(PayReqVO reqVO, Object cfg) {
        AlipayConfig alipayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            alipayConfig = (AlipayConfig) cfg;
        } else {
            alipayConfig = config;
        }
        return getOrderquery(reqVO, alipayConfig);
    }

    private AliPayEnum getOrderquery(PayReqVO reqVO, AlipayConfig alipayConfig) {
        AliPayEnum aliPayEnum = null;
        try {
            aliPayEnum = AliPayUtil.orderquery(reqVO, alipayConfig);
            log.info(HEAD + "查询订单成功:{}", JSON.toJSONString(aliPayEnum));
        } catch (Exception e) {
            log.error(HEAD + "查询订单失败：", e);
            throw new RuntimeException(HEAD + "查询订单失败:{}" + e.getMessage());
        }
        return aliPayEnum;
    }

    @Override
    public Boolean closeorder(PayReqVO reqVO) {
        return getCloseorder(reqVO, config);
    }

    @Override
    public Boolean closeorder(PayReqVO reqVO, Object cfg) {
        AlipayConfig alipayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            alipayConfig = (AlipayConfig) cfg;
        } else {
            alipayConfig = config;
        }
        return getCloseorder(reqVO, alipayConfig);
    }

    private Boolean getCloseorder(PayReqVO reqVO, AlipayConfig config) {
        Boolean b = null;
        try {
            b = AliPayUtil.closeorder(reqVO, config);
            log.info(HEAD + "关闭订单:{}", b);
        } catch (Exception e) {
            log.error(HEAD + "关闭订单失败：", e);
            throw new RuntimeException(HEAD + "关闭订单失败:{}" + e.getMessage());
        }
        return b;
    }

    @Override
    public Boolean reverse(PayReqVO reqVO) {
        throw new RuntimeException("支付宝不支持");
    }

    @Override
    public Boolean reverse(PayReqVO reqVO, Object cfg) {
        throw new RuntimeException("支付宝不支持");
    }

    @Override
    public Boolean refund(RefundReqVO reqVO) {
        return getRefund(reqVO, config);
    }

    @Override
    public Boolean refund(RefundReqVO reqVO, Object cfg) {
        AlipayConfig alipayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            alipayConfig = (AlipayConfig) cfg;
        } else {
            alipayConfig = config;
        }
        return getRefund(reqVO, alipayConfig);
    }

    private Boolean getRefund(RefundReqVO reqVO, AlipayConfig alipayConfig) {
        AliRefundReqVO aliRefundReqVO = new AliRefundReqVO();
        aliRefundReqVO.setOrderNo(reqVO.getOrderNo());
        aliRefundReqVO.setRefundAmount(reqVO.getRefundFee());
        if (ObjectUtil.isNotEmpty(reqVO.getOutRequestNo())) {
            aliRefundReqVO.setOutRequestNo(reqVO.getOutRequestNo());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getSubAuthToken())) {
            aliRefundReqVO.setSubAuthToken(reqVO.getSubAuthToken());
        }
        Boolean b = null;
        try {
            b = AliPayUtil.refund(aliRefundReqVO, alipayConfig);
            log.info(HEAD + "退款申请:{}", b);
        } catch (Exception e) {
            log.error(HEAD + "退款申请失败：", e);
            throw new RuntimeException(HEAD + "退款申请失败:" + e);
        }
        return b;
    }

    @Override
    public AliRefundEnum refundquery(RefundReqVO reqVO) {
        return getRefundquery(reqVO, config);
    }

    @Override
    public AliRefundEnum refundquery(RefundReqVO reqVO, Object cfg) {
        AlipayConfig alipayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            alipayConfig = (AlipayConfig) cfg;
        } else {
            alipayConfig = config;
        }
        return getRefundquery(reqVO, alipayConfig);
    }

    private AliRefundEnum getRefundquery(RefundReqVO reqVO, AlipayConfig alipayConfig) {
        AliRefundEnum aliRefundEnum = null;
        try {
            aliRefundEnum = AliPayUtil.refundquery(reqVO, alipayConfig);
            log.info(HEAD + "查询退款订单:{}", JSON.toJSONString(aliRefundEnum));
        } catch (Exception e) {
            log.error(HEAD + "查询退款订单失败：", e);
            throw new RuntimeException(HEAD + "查询退款订单:" + e.getMessage());
        }
        return aliRefundEnum;
    }

    @Override
    public AliOrderRespVO callback(HttpServletRequest request) {
        return getCallback(request, config);
    }

    @Override
    public AliOrderRespVO callback(HttpServletRequest request, Object cfg) {
        AlipayConfig alipayConfig;
        if (ObjectUtil.isNotEmpty(cfg)) {
            alipayConfig = (AlipayConfig) cfg;
        } else {
            alipayConfig = config;
        }
        return getCallback(request, alipayConfig);
    }

    private AliOrderRespVO getCallback(HttpServletRequest request, AlipayConfig alipayConfig) {
        AliOrderRespVO aliOrderRespVO = null;
        try {
            aliOrderRespVO = AliPayUtil.callback(request, alipayConfig);
            log.info(HEAD + "支付回调:{}", JSON.toJSONString(aliOrderRespVO));
        } catch (Exception e) {
            log.error(HEAD + "支付回调失败：", e);
            throw new RuntimeException(HEAD + "支付回调失败：" + e.getMessage());
        }
        return aliOrderRespVO;
    }
}

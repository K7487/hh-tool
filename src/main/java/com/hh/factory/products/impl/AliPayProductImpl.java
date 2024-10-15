package com.hh.factory.products.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.ExtUserInfo;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
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
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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
        String tradeType = orderCheck.placeOrderIsNull(reqVO, PayType.ZFB);
        AlipayTradeCreateModel model1 = new AlipayTradeCreateModel();
        AlipayTradePagePayModel model2 = new AlipayTradePagePayModel();
        AlipayTradeCreateRequest request1 = new AlipayTradeCreateRequest();
        AlipayTradePagePayRequest request2 = new AlipayTradePagePayRequest();
        switch (tradeType) {
            case "JSAPI_PAY":
                model1.setOutTradeNo(reqVO.getOrderNo());
                model1.setTotalAmount(reqVO.getAmounts().toString());
                model1.setSubject(reqVO.getDescription());
                model1.setProductCode(tradeType);
                model1.setOpAppId(config.getOpAppId());
                model1.setBuyerId(reqVO.getOpenid());
                request1.setNotifyUrl(config.getNotifyUrl());
                break;
            case "QUICK_WAP_WAY":
                break;
            case "QUICK_MSECURITY_PAY":
                break;
            case "FAST_INSTANT_TRADE_PAY":
                model2.setOutTradeNo(reqVO.getOrderNo());
                model2.setTotalAmount(reqVO.getAmounts().toString());
                model2.setSubject(reqVO.getDescription());
                model2.setProductCode(tradeType);
                request2.setNotifyUrl(config.getNotifyUrl());
                break;
            default:
                throw new RuntimeException("支付类型有误");
        }
        String tradeNo = null;
        try {
            switch (tradeType) {
                case "JSAPI_PAY":
                    tradeNo = AliPayUtil.unifiedorder(model1, config, request1);
                    break;
                case "QUICK_WAP_WAY":
                    break;
                case "QUICK_MSECURITY_PAY":
                    break;
                case "FAST_INSTANT_TRADE_PAY":
                    tradeNo = AliPayUtil.unifiedorder(model2, config, request2);
                    break;
                default:
                    throw new RuntimeException("支付类型有误");
            }
            log.info(HEAD + "下单成功:{}", tradeNo);
        } catch (Exception e) {
            log.error(HEAD + "下单失败：", e);
            throw new RuntimeException(HEAD + "下单失败:{}" + e.getMessage());
        }
        return tradeNo;
    }

    @Override
    public AliPayEnum orderquery(String orderNo) {
        AliPayEnum aliPayEnum = null;
        try {
            aliPayEnum = AliPayUtil.orderquery(orderNo, config);
            log.info(HEAD + "查询订单成功:{}", JSON.toJSONString(aliPayEnum));
        } catch (Exception e) {
            log.error(HEAD + "查询订单失败：", e);
            throw new RuntimeException(HEAD + "查询订单失败:{}" + e.getMessage());
        }
        return aliPayEnum;
    }

    @Override
    public Boolean closeorder(String orderNo) {
        Boolean b = null;
        try {
            b = AliPayUtil.closeorder(orderNo, config);
            log.info(HEAD + "关闭订单:{}", b);
        } catch (Exception e) {
            log.error(HEAD + "关闭订单失败：", e);
            throw new RuntimeException(HEAD + "关闭订单失败:{}" + e.getMessage());
        }
        return b;
    }

    @Override
    public Boolean refund(RefundReqVO reqVO) {
        AliRefundReqVO aliRefundReqVO = new AliRefundReqVO();
        aliRefundReqVO.setOrderNo(reqVO.getOrderNo());
        aliRefundReqVO.setRefundAmount(reqVO.getRefundFee());
        if (ObjectUtil.isNotEmpty(reqVO.getOutRequestNo())) {
            aliRefundReqVO.setOutRequestNo(reqVO.getOutRequestNo());
        }
        Boolean b = null;
        try {
            b = AliPayUtil.refund(aliRefundReqVO, config);
            log.info(HEAD + "退款申请:{}", b);
        } catch (Exception e) {
            log.error(HEAD + "退款申请失败：", e);
            throw new RuntimeException(HEAD + "退款申请失败:" + e);
        }
        return b;
    }

    @Override
    public AliRefundEnum refundquery(String orderNo) {
        AliRefundEnum aliRefundEnum = null;
        try {
            aliRefundEnum = AliPayUtil.refundquery(orderNo, config);
            log.info(HEAD + "查询退款订单:{}", JSON.toJSONString(aliRefundEnum));
        } catch (Exception e) {
            log.error(HEAD + "查询退款订单失败：", e);
            throw new RuntimeException(HEAD + "查询退款订单:" + e.getMessage());
        }
        return aliRefundEnum;
    }

    @Override
    public AliOrderRespVO callback(HttpServletRequest request) {
        AliOrderRespVO aliOrderRespVO = null;
        try {
            aliOrderRespVO = AliPayUtil.callback(request, config);
            log.info(HEAD + "支付回调:{}", JSON.toJSONString(aliOrderRespVO));
        } catch (Exception e) {
            log.error(HEAD + "支付回调失败：", e);
            throw new RuntimeException(HEAD + "支付回调失败：" + e.getMessage());
        }
        return aliOrderRespVO;
    }
}

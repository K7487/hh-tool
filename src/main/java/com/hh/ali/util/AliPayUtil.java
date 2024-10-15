package com.hh.ali.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.hh.ali.enums.AliPayEnum;
import com.hh.ali.enums.AliRefundEnum;
import com.hh.ali.vo.req.AliRefundReqVO;
import com.hh.ali.vo.resp.AliOrderRespVO;
import com.hh.constants.Pay;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AliPayUtil {

    private static final Log log = LogFactory.get();
    private static final String HEAD = "[支付宝支付]";

    /**
     * 支付宝统一下单
     *
     * @param model
     * @param config
     * @return 成功:返回参数支付宝交易号 失败:抛出异常
     */
    public static String unifiedorder(AlipayTradeCreateModel model, AlipayConfig config, AlipayTradeCreateRequest request) {
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(config);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        request.setBizModel(model);
        log.info(HEAD + "统一下单,入参:{}", JSON.toJSONString(request));
        AlipayTradeCreateResponse response = null;
        try {
        switch (model.getProductCode()) {
            case "JSAPI_PAY":
                response = alipayClient.execute(request);
                break;
            case "QUICK_WAP_WAY":
                response = alipayClient.pageExecute(request, "POST");
                break;
            case "QUICK_MSECURITY_PAY":
                response = alipayClient.sdkExecute(request);
                break;
            case "FAST_INSTANT_TRADE_PAY":
                response = alipayClient.pageExecute(request);
                break;
            default:
                throw new RuntimeException("支付类型有误");
        }

        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        log.info(HEAD + "统一下单,出参:{}", JSON.toJSONString(response));
        if (response != null && !response.isSuccess()) {
            throw new RuntimeException(response.getSubMsg());
        }
        switch (model.getProductCode()) {
            case "JSAPI_PAY":
                log.info(HEAD + "统一下单,返回的结果:{}", response.getTradeNo());
                return response.getTradeNo();
            case "QUICK_WAP_WAY":
            case "QUICK_MSECURITY_PAY":
            case "FAST_INSTANT_TRADE_PAY":
                log.info(HEAD + "统一下单,返回的结果:{}", response.getBody());
                return response.getBody();
            default:
                throw new RuntimeException("支付类型有误");
        }
    }

    /**
     * 支付宝统一下单
     *
     * @param model
     * @param config
     * @return 成功:返回参数支付宝交易号 失败:抛出异常
     */
    public static String unifiedorder(AlipayTradePagePayModel model, AlipayConfig config, AlipayTradePagePayRequest request) {
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(config);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        request.setBizModel(model);
        log.info(HEAD + "统一下单,入参:{}", JSON.toJSONString(request));
        AlipayTradePagePayResponse response = null;
        try {
            switch (model.getProductCode()) {
                case "JSAPI_PAY":
                    response = alipayClient.execute(request);
                    break;
                case "QUICK_WAP_WAY":
                    response = alipayClient.pageExecute(request, "POST");
                    break;
                case "QUICK_MSECURITY_PAY":
                    response = alipayClient.sdkExecute(request);
                    break;
                case "FAST_INSTANT_TRADE_PAY":
                    response = alipayClient.pageExecute(request);
                    break;
                default:
                    throw new RuntimeException("支付类型有误");
            }

        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        log.info(HEAD + "统一下单,出参:{}", JSON.toJSONString(response));
        if (response != null && !response.isSuccess()) {
            throw new RuntimeException(response.getSubMsg());
        }
        switch (model.getProductCode()) {
            case "JSAPI_PAY":
                log.info(HEAD + "统一下单,返回的结果:{}", response.getTradeNo());
                return response.getTradeNo();
            case "QUICK_WAP_WAY":
            case "QUICK_MSECURITY_PAY":
            case "FAST_INSTANT_TRADE_PAY":
                log.info(HEAD + "统一下单,返回的结果:{}", response.getBody());
                return response.getBody();
            default:
                throw new RuntimeException("支付类型有误");
        }
    }

    /**
     * 查询订单状态
     *
     * @param orderNo 订单号
     * @param config  支付宝支付配置参数
     * @return 成功:返回状态枚举 失败:抛出异常
     */
    public static AliPayEnum orderquery(String orderNo, AlipayConfig config) {
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(config);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        try {
            log.info(HEAD + "查询订单状态,入参:{}", JSON.toJSONString(request));
            response = alipayClient.execute(request);
            log.info(HEAD + "查询订单状态,出参:{}", JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccess()) {
            throw new RuntimeException(response.getSubMsg());
        }
        AliPayEnum aliPayEnum = AliPayEnum.codeToEnum(response.getTradeStatus());
        log.info(HEAD + "查询订单状态,返回的结果:{}", aliPayEnum);
        return aliPayEnum;
    }

    /**
     * 关闭订单
     *
     * @param orderNo 订单号
     * @param config  支付宝支付配置参数
     * @return 成功:返回true 失败:抛出异常
     */
    public static Boolean closeorder(String orderNo, AlipayConfig config) {
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(config);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        request.setBizContent(bizContent.toString());
        AlipayTradeCloseResponse response = null;
        try {
            log.info(HEAD + "关闭订单,入参:{}", JSON.toJSONString(request));
            response = alipayClient.execute(request);
            log.info(HEAD + "关闭订单,出参:{}", JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccess()) {
            throw new RuntimeException(response.getSubMsg());
        }
        log.info(HEAD + "关闭订单,返回的结果:{}", true);
        return true;
    }

    /**
     * 申请退款
     *
     * @param reqVO  退款业务参数
     * @param config 微信支付配置参数
     * @return 成功:返回true 失败:抛出异常
     */
    public static Boolean refund(AliRefundReqVO reqVO, AlipayConfig config) {
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(config);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", reqVO.getOrderNo());
        bizContent.put("refund_amount", reqVO.getRefundAmount());
        if (ObjectUtil.isNotEmpty(reqVO.getOutRequestNo())) {
            bizContent.put("out_request_no", reqVO.getOutRequestNo());
        }
        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response = null;
        try {
            log.info(HEAD + "申请退款,入参:{}", JSON.toJSONString(request));
            response = alipayClient.execute(request);
            log.info(HEAD + "申请退款,出参:{}", JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccess()) {
            throw new RuntimeException(response.getSubMsg());
        }
        return true;
    }

    /**
     * 退款查询
     *
     * @param orderNo 订单号
     * @param config  微信支付配置参数
     * @return 成功:返回状态枚举 失败:抛出异常
     */
    public static AliRefundEnum refundquery(String orderNo, AlipayConfig config) {
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(config);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_request_no", orderNo);
        bizContent.put("out_trade_no", orderNo);
        request.setBizContent(bizContent.toString());
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            log.info(HEAD + "退款查询,入参:{}", JSON.toJSONString(request));
            response = alipayClient.execute(request);
            log.info(HEAD + "退款查询,出参:{}", JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccess()) {
            throw new RuntimeException(response.getSubMsg());
        }
        AliRefundEnum enum1 = AliRefundEnum.codeToEnum(response.getRefundStatus());
        log.info(HEAD + "退款查询,返回的结果:{}", enum1);
        return enum1;
    }

    /**
     * 支付回调
     *
     * @param request
     * @param config
     * @return
     */
    public static AliOrderRespVO callback(HttpServletRequest request, AlipayConfig config) {
        AliOrderRespVO aliOrderRespVO = new AliOrderRespVO();
        //获取支付宝POST过来反馈信息，将异步通知中收到的待验证所有参数都存放到map中
        Map params = new HashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
//            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }
        //调用SDK验证签名
        //公钥验签示例代码
        boolean signVerified;
        try {
            log.info(HEAD + "支付回调,验签入参:{}", JSON.toJSONString(params));
            signVerified = AlipaySignature.rsaCheckV1(params, config.getAlipayPublicKey(), config.getCharset(), config.getSignType());
            log.info(HEAD + "支付回调,验签返回:{}", signVerified);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        //公钥证书验签示例代码
        //boolean flag = AlipaySignature.rsaCertCheckV1(params,alipayPublicCertPath,"UTF-8","RSA2");
        if (!signVerified) {
            throw new RuntimeException(HEAD + "支付回调,验签失败");
        }
        // TODO 验签成功后
        //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success
        if (AliPayEnum.TRADE_SUCCESS.getCode().equals(params.get("trade_status"))) {
            aliOrderRespVO.setR(true);
        } else {
            aliOrderRespVO.setR(false);
        }
        aliOrderRespVO.setDataMap(params);
        aliOrderRespVO.setOrderNo(params.get("out_trade_no").toString());
        return aliOrderRespVO;
    }
}
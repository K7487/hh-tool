package com.hh.wx.v2.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.hh.wx.v2.entity.WxOrderReqVO;
import com.hh.wx.v2.entity.WxOrderRespVO;
import com.hh.wx.v2.entity.WxRefundReqVO;
import com.hh.wx.v2.enums.WxPayEnum;
import com.hh.wx.v2.enums.WxRefundEnum;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信V2版本支付工具类
 */
public class WxPaymentUtil {

    private static final Log log = LogFactory.get();

    private static final String HEAD = "[微信支付]";

    /**
     * 统一下单
     *
     * @param reqVO  下单业务参数
     * @param config 微信支付配置参数
     * @return 成功:返回收银台信息 失败:抛出异常
     */
    public static Map<String, String> unifiedorder(WxOrderReqVO reqVO, WXPayConfig config) {
        Map<String, String> reqMap = new TreeMap<>();
        WXPay wxPay = new WXPay(config);
        reqMap.put("nonce_str", WXPayUtil.generateNonceStr());
        // 必填项
        reqMap.put("body", reqVO.getBody());
        reqMap.put("out_trade_no", reqVO.getOutTradeNo());
        Integer total_fee = reqVO.getTotalFee().multiply(new BigDecimal(100)).intValue();
        reqMap.put("total_fee", total_fee.toString());
        reqMap.put("spbill_create_ip", reqVO.getSpbillCreateIp());
        reqMap.put("notify_url", reqVO.getNotifyUrl());
        reqMap.put("trade_type", reqVO.getTradeType());
        // 选填项
        if (ObjectUtil.isNotEmpty(reqVO.getOpenid())) {
            reqMap.put("openid", reqVO.getOpenid());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getAttach())) {
            reqMap.put("attach", reqVO.getAttach());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getFeeType())) {
            reqMap.put("fee_type", reqVO.getFeeType());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getTimeStart())) {
            reqMap.put("time_start", reqVO.getTimeStart());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getTimeExpire())) {
            reqMap.put("time_expire", reqVO.getTimeExpire());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getGoodsTag())) {
            reqMap.put("goods_tag", reqVO.getGoodsTag());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getProductId())) {
            reqMap.put("product_id", reqVO.getProductId());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getLimitPay())) {
            reqMap.put("limit_pay", reqVO.getLimitPay());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getReceipt())) {
            reqMap.put("receipt", reqVO.getReceipt());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getSceneInfo())) {
            reqMap.put("scene_info", reqVO.getSceneInfo());
        }
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(reqMap, config.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqMap.put("sign", sign);
        log.info(HEAD + "统一下单,入参:{}", JSON.toJSONString(reqMap));
        Map<String, String> resqMap = new HashMap<>();
        try {
            resqMap = wxPay.unifiedOrder(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(HEAD + "统一下单,出参:{}", JSON.toJSONString(resqMap));
        String return_code = resqMap.get("return_code");
        if ("SUCCESS".equals(return_code)) {
            String result_code = resqMap.get("result_code");
            if (!"SUCCESS".equals(result_code)) {
                String err_code_des = resqMap.get("err_code_des");
                throw new RuntimeException(err_code_des);
            }
        } else {
            String msg = resqMap.get("return_msg").toString();
            throw new RuntimeException(msg);
        }

        String prepayId = resqMap.get("prepay_id");
        Map<String, String> data = new TreeMap<>();
        data.put("appId", config.getAppID());
        data.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        data.put("nonceStr", WXPayUtil.generateNonceStr());
        data.put("package", "prepay_id=" + prepayId);
        data.put("signType", "MD5");
        try {
            data.put("sign", WXPayUtil.generateSignature(data, config.getKey()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(HEAD + "统一下单,返回的结果:{}", JSON.toJSONString(data));
        return data;
    }

    /**
     * 查询订单状态
     *
     * @param outTradeNo 订单号
     * @param config     微信支付配置参数
     * @return 成功:返回状态枚举 失败:抛出异常
     */
    public static WxPayEnum orderquery(String outTradeNo, WXPayConfig config) {
        Map<String, String> reqMap = new TreeMap<>();
        WXPay wxPay = new WXPay(config);
        reqMap.put("nonce_str", WXPayUtil.generateNonceStr());
        // 必填项
        reqMap.put("out_trade_no", outTradeNo);
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(reqMap, config.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqMap.put("sign", sign);
        log.info(HEAD + "查询订单状态,入参:{}", JSON.toJSONString(reqMap));
        Map<String, String> resqMap = new HashMap<>();
        try {
            resqMap = wxPay.orderQuery(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(HEAD + "查询订单状态,出参:{}", JSON.toJSONString(resqMap));
        String return_code = resqMap.get("return_code");
        if ("SUCCESS".equals(return_code)) {
            String result_code = resqMap.get("result_code");
            if (!"SUCCESS".equals(result_code)) {
                String err_code_des = resqMap.get("err_code_des");
                throw new RuntimeException(err_code_des);
            }
        } else {
            String msg = resqMap.get("return_msg").toString();
            throw new RuntimeException(msg);
        }
        WxPayEnum enum1 = WxPayEnum.codeToEnum(resqMap.get("trade_state"));
        log.info(HEAD + "查询订单状态,返回的结果:{}", JSON.toJSONString(enum1));
        return enum1;
    }

    /**
     * 关闭订单
     *
     * @param outTradeNo 订单号
     * @param config     微信支付配置参数
     * @return 成功:返回true 失败:抛出异常
     */
    public static Boolean closeorder(String outTradeNo, WXPayConfig config) {
        Map<String, String> reqMap = new TreeMap<>();
        WXPay wxPay = new WXPay(config);
        reqMap.put("nonce_str", WXPayUtil.generateNonceStr());
        // 必填项
        reqMap.put("out_trade_no", outTradeNo);
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(reqMap, config.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqMap.put("sign", sign);
        log.info(HEAD + "关闭订单,入参:{}", JSON.toJSONString(reqMap));
        Map<String, String> resqMap = new HashMap<>();
        try {
            resqMap = wxPay.closeOrder(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(HEAD + "关闭订单,出参:{}", JSON.toJSONString(resqMap));
        if ("SUCCESS".equals(resqMap.get("return_code"))) {
            if (!"SUCCESS".equals(resqMap.get("result_code"))) {
                throw new RuntimeException(resqMap.get("err_code_des"));
            }
        } else {
            throw new RuntimeException(resqMap.get("return_msg"));
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
    public static Boolean refund(WxRefundReqVO reqVO, WXPayConfig config) {
        Map<String, String> reqMap = new TreeMap<>();
        WXPay wxPay = new WXPay(config);
        reqMap.put("nonce_str", WXPayUtil.generateNonceStr());
        // 必填项
        reqMap.put("out_trade_no", reqVO.getOutTradeNo());
        reqMap.put("out_refund_no", reqVO.getOutRefundNo());
        Integer totalFee = reqVO.getTotalFee().multiply(new BigDecimal(100)).intValue();
        reqMap.put("total_fee", totalFee.toString());
        Integer refundFee = reqVO.getRefundFee().multiply(new BigDecimal(100)).intValue();
        reqMap.put("refund_fee", refundFee.toString());

        // 选填项
        if (ObjectUtil.isNotEmpty(reqVO.getRefundFeeType())) {
            reqMap.put("refund_fee_type", reqVO.getRefundFeeType());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getRefundDesc())) {
            reqMap.put("refund_desc", reqVO.getRefundDesc());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getRefundAccount())) {
            reqMap.put("refund_account", reqVO.getRefundAccount());
        }
        if (ObjectUtil.isNotEmpty(reqVO.getNotifyUrl())) {
            reqMap.put("notify_url", reqVO.getNotifyUrl());
        }
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(reqMap, config.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqMap.put("sign", sign);
        log.info(HEAD + "申请退款,入参:{}", JSON.toJSONString(reqMap));
        Map<String, String> resqMap = new HashMap<>();
        try {
            resqMap = wxPay.refund(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(HEAD + "申请退款,出参:{}", JSON.toJSONString(resqMap));
        String return_code = resqMap.get("return_code");
        if ("SUCCESS".equals(return_code)) {
            String result_code = resqMap.get("result_code");
            if (!"SUCCESS".equals(result_code)) {
                String err_code_des = resqMap.get("err_code_des");
                throw new RuntimeException(err_code_des);
            }
        } else {
            String msg = resqMap.get("return_msg").toString();
            throw new RuntimeException(msg);
        }
        log.info(HEAD + "申请退款,返回的结果:{}", true);
        return true;
    }

    /**
     * 退款查询
     *
     * @param outTradeNo 订单号
     * @param config     微信支付配置参数
     * @return 成功:返回状态枚举 失败:抛出异常
     */
    public static WxRefundEnum refundquery(String outTradeNo, WXPayConfig config) {
        Map<String, String> reqMap = new TreeMap<>();
        WXPay wxPay = new WXPay(config);
        reqMap.put("nonce_str", WXPayUtil.generateNonceStr());
        // 必填项
        reqMap.put("out_trade_no", outTradeNo);
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(reqMap, config.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqMap.put("sign", sign);
        log.info(HEAD + "退款查询,入参:{}", JSON.toJSONString(reqMap));
        Map<String, String> resqMap = new HashMap<>();
        try {
            resqMap = wxPay.refundQuery(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(HEAD + "退款查询,出参:{}", JSON.toJSONString(resqMap));
        String return_code = resqMap.get("return_code");
        if ("SUCCESS".equals(return_code)) {
            String result_code = resqMap.get("result_code");
            if (!"SUCCESS".equals(result_code)) {
                String err_code_des = resqMap.get("err_code_des");
                throw new RuntimeException(err_code_des);
            }
        } else {
            String msg = resqMap.get("return_msg").toString();
            throw new RuntimeException(msg);
        }
        Pattern pattern = Pattern.compile("refund_status_.*");
        String refundStatusKey = null;
        for (String key : resqMap.keySet()) {
            if (pattern.matcher(key).matches()) {
                refundStatusKey = key;
            }
        }
        WxRefundEnum enum1 = WxRefundEnum.codeToEnum(resqMap.get(refundStatusKey));
        log.info(HEAD + "退款查询,返回的结果:{},退款申请次数:{}", enum1, refundStatusKey.split("_")[2]);
        return enum1;
    }

    /**
     * 微信支付回调
     *
     * @param request
     * @param config
     * @return
     */
    public static WxOrderRespVO callback(HttpServletRequest request, WXPayConfig config) {
        WxOrderRespVO wxOrderRespVO = new WxOrderRespVO();
        String result = null;
        try {
            result = XMLUtil.getRequestResult(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(HEAD + "微信支付回调:" + result);
        Map<String, Object> map = XMLUtil.decodeXml(result);
        log.info(HEAD + "回调map:{}", JSON.toJSONString(map));
        wxOrderRespVO.setWxMap(map);

        if ("SUCCESS".equals(map.get("result_code")) && "SUCCESS".equals(map.get("return_code"))) {
            wxOrderRespVO.setR(true);
            if (ObjectUtil.isNotEmpty(map.get("req_info"))) {
                String req_infoString = map.get("req_info").toString();
                String req_info_decrypt = null;
                try {
                    req_info_decrypt = AESUtil.decryptData(req_infoString, config.getKey());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                log.info(HEAD + "退款字符串:{}", req_info_decrypt);
                Map<String, Object> reqInfoMap = XMLUtil.decodeXml(req_info_decrypt);
                log.info(HEAD + "退款map:{}", JSON.toJSONString(reqInfoMap));
                wxOrderRespVO.setOutTradeNo(reqInfoMap.get("out_trade_no").toString());
                wxOrderRespVO.setWxMap(reqInfoMap);
                wxOrderRespVO.setType(2);
            } else {
                wxOrderRespVO.setType(1);
                wxOrderRespVO.setOutTradeNo(map.get("out_trade_no").toString());
            }
        } else {
            wxOrderRespVO.setR(false);
        }
        log.info(HEAD + "微信支付回调,返回:{}", JSON.toJSONString(wxOrderRespVO));
        return wxOrderRespVO;
    }


}

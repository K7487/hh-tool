package com.hh.wx.v3.products;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.hh.factory.products.PayV3Product;
import com.hh.factory.util.OrderCheck;
import com.hh.factory.vo.req.PayReqVO;
import com.hh.wx.v2.enums.WxPayEnum;
import com.hh.wx.v3.constant.Wx3Constant;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信V3版本支付
 * 二维码支付
 *
 * @author huanghan
 */
@Service
@AllArgsConstructor
@Log4j2
public class Wx3NativePayService implements PayV3Product {

    private Wx3Constant wx3Constant;
    private OrderCheck orderCheck;

    private static final String HEAD = "[微信支付V3]-[二维码支付]";

    @Override
    public Map<String, String> placeOrder(PayReqVO reqVO) {
        // 下单参数为空判断
        orderCheck.placeOrderIsNull2(reqVO);
        // 使用自动更新平台证书的RSA配置,一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config = new RSAAutoCertificateConfig.Builder()
                        .merchantId(wx3Constant.getMerchantId())
                        .privateKeyFromPath(wx3Constant.getPrivateKeyPath())
                        .merchantSerialNumber(wx3Constant.getMerchantSerialNumber())
                        .apiV3Key(wx3Constant.getApiV3Key())
                        .build();
        NativePayService service = new NativePayService.Builder().config(config).build();
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(reqVO.getAmounts().multiply(new BigDecimal(100)).intValue());
        request.setAmount(amount);
        request.setAppid(wx3Constant.getAppid());
        request.setMchid(wx3Constant.getMchId());
        request.setDescription(reqVO.getDescription());
        request.setNotifyUrl(wx3Constant.getNotifyUrl());
        request.setOutTradeNo(reqVO.getOrderNo());
        log.info(HEAD + "统一下单,入参:{}", JSON.toJSONString(request));
        // 调用下单方法，得到应答
        PrepayResponse response = service.prepay(request);
        log.info(HEAD + "统一下单,出参:{}", JSON.toJSONString(response));
        Map<String, String> map = new HashMap<>();
        map.put("code_url", response.getCodeUrl());
        return map;
    }

    @Override
    public WxPayEnum orderquery(String orderNo) {
        if (ObjectUtil.isEmpty(orderNo)) {
            throw new RuntimeException(HEAD + "订单号不能为空");
        }
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(wx3Constant.getMerchantId())
                .privateKeyFromPath(wx3Constant.getPrivateKeyPath())
                .merchantSerialNumber(wx3Constant.getMerchantSerialNumber())
                .apiV3Key(wx3Constant.getApiV3Key())
                .build();
        NativePayService service = new NativePayService.Builder().config(config).build();
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(wx3Constant.getMchId());
        request.setOutTradeNo(orderNo);
        log.info(HEAD + "查询订单状态,入参:{}", JSON.toJSONString(request));
        // 调用下单方法，得到应答
        Transaction transaction = service.queryOrderByOutTradeNo(request);
        log.info(HEAD + "查询订单状态,出参:{}", JSON.toJSONString(transaction));
        return WxPayEnum.codeToEnum(transaction.getTradeState().name());
    }

    @Override
    public Boolean closeorder(String orderNo) {
        if (ObjectUtil.isEmpty(orderNo)) {
            throw new RuntimeException(HEAD + "订单号不能为空");
        }
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(wx3Constant.getMerchantId())
                .privateKeyFromPath(wx3Constant.getPrivateKeyPath())
                .merchantSerialNumber(wx3Constant.getMerchantSerialNumber())
                .apiV3Key(wx3Constant.getApiV3Key())
                .build();
        NativePayService service = new NativePayService.Builder().config(config).build();
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(wx3Constant.getMchId());
        request.setOutTradeNo(orderNo);
        log.info(HEAD + "关闭订单,入参:{}", JSON.toJSONString(request));
        service.closeOrder(request);
        return true;
    }

}

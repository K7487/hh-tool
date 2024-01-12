package com.hh.factory.products.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.hh.factory.products.PayProduct;
import com.hh.factory.vo.req.PayReqVO;
import com.hh.factory.vo.req.RefundReqVO;
import com.hh.factory.vo.resp.WxOrderRespVO;
import com.hh.wx.v2.config.WxConfig;
import com.hh.wx.v2.constant.WxConstant;
import com.hh.wx.v2.enums.WxPayEnum;
import com.hh.wx.v2.enums.WxRefundEnum;
import com.hh.wx.v2.util.WxPaymentUtil;
import com.hh.wx.v2.vo.WxOrderReqVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信V2版本支付
 * @author huanghan
 */
@Component
@AllArgsConstructor
public class Wx2PayProductImpl implements PayProduct {

    private WxConfig wxConfig;
    private WxConstant wxConstant;

    @Override
    public Map<String, String> placeOrder(PayReqVO reqVO) {
        WxOrderReqVO wxOrderReqVO = new WxOrderReqVO();
        Map<String, String> map = new HashMap<>();
        if (ObjectUtil.isEmpty(reqVO.getDescription())) {
            throw new RuntimeException("商品描述不能为空");
        }
        if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
            throw new RuntimeException("订单号不能为空");
        }
        if (ObjectUtil.isEmpty(reqVO.getAmounts())) {
            throw new RuntimeException("下单金额不能为空");
        }
        wxOrderReqVO.setSpbillCreateIp(new ArrayList<>(NetUtil.localIpv4s()).get(0));
        wxOrderReqVO.setNotifyUrl(wxConstant.getNotifyUrl());
        wxOrderReqVO.setTradeType(wxConstant.getTradeType());
        wxOrderReqVO.setBody(reqVO.getDescription());
        wxOrderReqVO.setOutTradeNo(reqVO.getOrderNo().toString());
        wxOrderReqVO.setTotalFee(reqVO.getAmounts());
        wxOrderReqVO.setOpenid(reqVO.getOpenid());
        try {
            map = WxPaymentUtil.unifiedorder(wxOrderReqVO, wxConfig);
        } catch (Exception e) {
            log.error("下单失败:{}", e.getMessage());
            throw new RuntimeException("下单失败");
        }
        log.info("下单成功:{}", JSON.toJSONString(map));
        return map;
    }

    @Override
    public WxPayEnum orderquery(Long orderNo) {
        return null;
    }

    @Override
    public Boolean closeorder(Long orderNo) {
        return null;
    }

    @Override
    public Boolean refund(RefundReqVO reqVO) {
        return null;
    }

    @Override
    public WxRefundEnum refundquery(Long orderNo) {
        return null;
    }

    @Override
    public WxOrderRespVO callback(HttpServletRequest request) {
        return null;
    }
}

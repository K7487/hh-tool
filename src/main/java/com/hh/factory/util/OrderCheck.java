package com.hh.factory.util;

import cn.hutool.core.util.ObjectUtil;
import com.hh.ali.conig.AliConfig;
import com.hh.constants.Pay;
import com.hh.enums.PayType;
import com.hh.factory.vo.req.PayReqVO;
import com.hh.wx.v2.constant.WxConstant;
import com.hh.wx.v2.vo.WxOrderReqVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class OrderCheck {

    private WxConstant wxConstant;
    private AliConfig config;

    /**
     * 下单参数为空判断
     * @param reqVO
     * @param payType
     * @return
     */
    public String placeOrderIsNull(PayReqVO reqVO, PayType payType) {
        parameterNull(reqVO);
        String tradeType = null;
        if (ObjectUtil.isNotEmpty(reqVO.getTradeType())) {
            tradeType = reqVO.getTradeType();
        } else {
            switch (payType) {
                case WX_V2:
                case WX_V3:
                    tradeType = wxConstant.getTradeType();
                    break;
                case ZFB:
                    tradeType = config.getTradeType();
                    break;
            }
        }
        if (ObjectUtil.isEmpty(tradeType)) {
            throw new RuntimeException("交易类型不能为空");
        }
        if (Pay.TradeType.JSAPI.equals(tradeType) && ObjectUtil.isEmpty(reqVO.getOpenid())) {
            throw new RuntimeException("交易类型为：JSAPI，openid不能为空");
        }
        // 支付宝类型转化
        if (PayType.ZFB.getCode().equals(payType.getCode())) {
            switch (tradeType) {
                case Pay.TradeType.JSAPI:
                    tradeType = "JSAPI_PAY";
                    break;
                case Pay.TradeType.NATIVE:
                    tradeType = "FACE_TO_FACE_PAYMENT";
                    break;
                case Pay.TradeType.APP:
                case Pay.TradeType.MWEB:
                    tradeType = "FAST_INSTANT_TRADE_PAY";
                    break;
                default: throw  new RuntimeException("交易类型类型有误");
            }
        }
        return tradeType;
    }

    /**
     * 下单参数为空判断
     * @param reqVO
     * @return
     */
    public void placeOrderIsNull2(PayReqVO reqVO) {
        parameterNull(reqVO);
    }

    private void parameterNull(PayReqVO reqVO) {
        if (ObjectUtil.isEmpty(reqVO.getDescription())) {
            throw new RuntimeException("商品描述不能为空");
        }
        if (ObjectUtil.isEmpty(reqVO.getOrderNo())) {
            throw new RuntimeException("订单号不能为空");
        }
        if (ObjectUtil.isEmpty(reqVO.getAmounts())) {
            throw new RuntimeException("下单金额不能为空");
        }
    }
}

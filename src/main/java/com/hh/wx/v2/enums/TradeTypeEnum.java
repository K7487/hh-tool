package com.hh.wx.v2.enums;

import com.hh.constants.Pay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 交易类型
 * @author huanghan
 */

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum TradeTypeEnum {

    JSAPI(Pay.TradeType.JSAPI, "小程序支付"),
    NATIVE(Pay.TradeType.NATIVE, "二维码支付"),
    APP(Pay.TradeType.APP, "app支付"),
    MWEB(Pay.TradeType.MWEB, "H5支付"),



    ;

    private String code;
    private String msg;

    /**
     * 根据code，返回对应枚举
     * @param code
     * @return
     */
    public static WxPayEnum codeToEnum(String code) {
        for (WxPayEnum values : WxPayEnum.values()) {
            if (values.getCode().equals(code)){
                return values;
            }
        }
        throw new RuntimeException("未找到对应枚举");
    }

    /**
     * 根据枚举说明，返回对应枚举
     * @param msg
     * @return
     */
    public static WxPayEnum msgToEnum(String msg) {
        for (WxPayEnum values : WxPayEnum.values()) {
            if (values.getMsg().equals(msg)){
                return values;
            }
        }
        throw new RuntimeException("未找到对应枚举");
    }
}

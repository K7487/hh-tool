package com.hh.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author huanghan
 */

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum PayType {

    WX_V2("wx_v2", "微信支付,v2版本"),
    WX_V3("wx_v3", "微信支付,v3版本"),
    ZFB_V2("zfb_v2", "支付宝支付,v2版本"),
    ZFB_V3("zfb_v3", "支付宝支付,v3版本"),



    ;




    private String code;
    private String notes;

}

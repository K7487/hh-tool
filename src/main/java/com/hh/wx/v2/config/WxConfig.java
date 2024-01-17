package com.hh.wx.v2.config;

import com.github.wxpay.sdk.WXPayConfig;
import com.hh.wx.v2.constant.WxConstant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author huanghan
 */
@Component
public class WxConfig implements WXPayConfig {

    @Resource
    private WxConstant wxConstant;

    @Override
    public String getAppID() {
        return wxConstant.getAppid();
    }

    @Override
    public String getMchID() {
        return wxConstant.getMchId();
    }

    @Override
    public String getKey() {
        return wxConstant.getMchKey();
    }

    @Override
    public InputStream getCertStream() {
        try {
            return new FileInputStream(wxConstant.getCertUrl());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("微信证书获取失败");
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return wxConstant.getConnectTimeout();
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return wxConstant.getReadTimeout();
    }
}

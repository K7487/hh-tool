package com.hh.wx.v2.config;

import cn.hutool.core.util.ObjectUtil;
import com.github.wxpay.sdk.WXPayConfig;
import com.hh.wx.v2.constant.WxConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author huanghan
 */
@Component
public class WxConfig implements WXPayConfig {

    @Autowired
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
            if (ObjectUtil.isEmpty(wxConstant.getCertUrl())) {
                throw new RuntimeException("微信证书不能为空");
            }
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

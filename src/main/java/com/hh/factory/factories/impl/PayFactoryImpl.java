package com.hh.factory.factories.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hh.enums.PayType;
import com.hh.factory.factories.PayFactory;
import com.hh.factory.products.PayProduct;
import com.hh.factory.products.impl.AliPayProductImpl;
import com.hh.factory.products.impl.Wx2PayProductImpl;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * @author huanghan
 */
@Log4j2
@Service
@AllArgsConstructor
public class PayFactoryImpl implements PayFactory {

    private Wx2PayProductImpl wx2PayProduct;
    private AliPayProductImpl aliPayProduct;

    @Override
    public PayProduct init(PayType payType) {
        if (ObjectUtil.isEmpty(payType)) {
            throw new RuntimeException("支付类型不能为空");
        }
        switch (payType) {
            case WX_V2:
                return wx2PayProduct;
            case WX_V3:
                return null;
            case ZFB:
                return aliPayProduct;
            default:
                throw new RuntimeException("支付类型有误");
        }
    }
}

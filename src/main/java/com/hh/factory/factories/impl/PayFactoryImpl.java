package com.hh.factory.factories.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hh.enums.PayType;
import com.hh.factory.factories.PayFactory;
import com.hh.factory.products.PayProduct;
import com.hh.factory.products.impl.Wx2PayProductImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author huanghan
 */
@Component
@AllArgsConstructor
public class PayFactoryImpl implements PayFactory {

    private Wx2PayProductImpl wx2PayProduct;

    @Override
    public PayProduct pay(PayType payType) {
        if (ObjectUtil.isEmpty(payType)) {
            throw new RuntimeException("支付类型不能为空");
        }
        switch (payType) {
            case WX_V2:
                return wx2PayProduct;
            case WX_V3:
            case ZFB_V2:
            case ZFB_V3:
                return null;
            default:
                throw new RuntimeException("支付类型有误");
        }
    }
}

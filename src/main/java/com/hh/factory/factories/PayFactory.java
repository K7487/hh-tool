package com.hh.factory.factories;

import com.hh.enums.PayType;
import com.hh.factory.products.PayProduct;

public interface PayFactory {

    PayProduct init(PayType payType);

}

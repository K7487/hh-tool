package com.hh.constants;

/**
 * @author huanghan
 */
public interface Pay {

    interface TradeType {
        /**
         * 小程序支付
         */
        String JSAPI = "JSAPI";

        /**
         * 二维码支付
         */
        String NATIVE = "NATIVE";

        /**
         * app支付
         */
        String APP = "APP";

        /**
         * H5支付
         */
        String MWEB = "MWEB";

        /**
         * 付款码支付
         */
        String MICROPAY = "MICROPAY";
    }

}

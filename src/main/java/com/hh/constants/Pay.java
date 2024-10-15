package com.hh.constants;

/**
 * @author huanghan
 */
public interface Pay {

    interface TradeType {
        /**
         * 小程序支付
         */
        public static final String JSAPI = "JSAPI";

        /**
         * 二维码支付
         */
        public static final String NATIVE = "NATIVE";

        /**
         * app支付
         */
        public static final String APP = "APP";

        /**
         * H5支付
         */
        public static final String MWEB = "MWEB";
    }

}

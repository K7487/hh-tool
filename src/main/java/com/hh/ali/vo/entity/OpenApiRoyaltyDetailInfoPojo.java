package com.hh.ali.vo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 退分账明细信息。 注： 1.当面付且非直付通模式无需传入退分账明细，系统自动按退款金额与订单金额的比率，从收款方和分账收入方退款，不支持指定退款金额与退款方。 2.直付通模式，电脑网站支付，手机 APP 支付，手机网站支付产品，须在退款请求中明确是否退分账，从哪个分账收入方退，退多少分账金额；如不明确，默认从收款方退款，收款方余额不足退款失败。不支持系统按比率退款。
 * @author huanghan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenApiRoyaltyDetailInfoPojo {

    /**
     * 收入方账户
     * 如果收入方账户类型为userId，本参数为收入方的支付宝账号对应的支付宝唯一用户号，以2088开头的纯16位数字；如果收入方类型为cardAliasNo，本参数为收入方在支付宝绑定的卡编号；如果收入方类型为loginName，本参数为收入方的支付宝登录号；
     */
    private String transIn;

    /**
     * 分账类型
     * 【枚举值】
     * 分账: transfer
     * 营销补差: replenish
     */
    private String royaltyType;

    /**
     * 支出方账户
     * 如果支出方账户类型为userId，本参数为支出方的支付宝账号对应的支付宝唯一用户号，以2088开头的纯16位数字；如果支出方类型为loginName，本参数为支出方的支付宝登录号。 泛金融类商户分账时，该字段不要上送。
     */
    private String transOut;

    /**
     * 支出方账户类型
     * 【枚举值】
     * 支付宝账号对应的支付宝唯一用户号: userId
     * 支付宝登录号: loginName
     *
     * 泛金融类商户分账时，该字段不要上送
     */
    private String transOutType;

    /**
     * 收入方账户类型
     * 【枚举值】
     * 支付宝账号对应的支付宝唯一用户号: userId
     * 支付宝登录号: loginName
     * 卡编号: cardAliasNo
     */
    private String transInType;

    /**
     * 分账的金额，单位为元
     */
    private BigDecimal amount;

    /**
     * 分账描述
     */
    private String desc;

    /**
     * 可选值：达人佣金、平台服务费、技术服务费、其他
     */
    private String royaltyScene;

    /**
     * 分账收款方姓名，上送则进行姓名与支付宝账号的一致性校验，校验不一致则分账失败。不上送则不进行姓名校验
     */
    private String transInName;
}

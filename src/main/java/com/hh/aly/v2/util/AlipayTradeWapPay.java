package com.hh.aly.v2.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.AlipayConfig;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.FileItem;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

public class AlipayTradeWapPay {

    public static void main(String[] args) throws AlipayApiException {
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcWfvga2HfMFgPcgOeufJz847Ilzx9TAkgf8VkNce8cP/Gd4R23z09kA1zIjlOGzOu6lpffI/lE1a6Nf3TAH/aqqUrZRrq3GkAt/E82xvT9wQZPKKBdOQ5YjWNfMKoDHBZsro1do5oVKAXI4gRgqlRpwJLLxQxcGebMfMf5ddQ5/gHqyRjhpHYybGezD4MdfhXpMni3tSAhB2A5wmcikr7Ofu6u5WiH8YYnog/Nd/qM799DINgJIsNBJP03UgkluwI4BbhM2fpd5Ri4g6uc/mMT4oufRVC3L3oC4GhB8zJFV/VwgS0doUDPYIbay15Czb6MIY6MC0kZhhvNa3wXfbTAgMBAAECggEAd8uiZ5MfTEjYUA1FpMpIcHs+/YaTPFWT8Ki1b1Cdl07lSwWsIwBtBcxn4b1pNuU0tmKtl9fy0Mv+eXKA5rjNPZfeMd+nvife9EP7vGsNaV0scMG6qYIMk8Mh0SS0aNoPA0sjzW7nMh0VBGWfDfuVDBDweva5fB/p4kllRR1v2naNfpdtydUMNgYlMg26jkqIzsgb3H3qK8vXY4WCMvulmb7HfXTvRRtwDjwMKn/nNkYLoYesuksLrpfidc5OhZLIJqPhI7vX5x0xeH0y8o4NT+310Wlae5AqKSjr4gGPisScg7i7L0wHgu65wWqzsI/OzF4PnBd4YMd8Cxc5a9e1QQKBgQDe5UQUgS4cHIsq9coyEW+BYFQYJURjNrvKuTiYL+QQqK0u0lq/mUZcGkMh899azw3P+FTEIu1XaxXOKOJ6f5PcFT3/I4l4gbe0Qgd/UqL3FNRJYk1HKcaB/si8Oiri0+Wth+ioM+GDYPmXpI7sv0RtzBtyahJ4vWV9QZo2lEVLcQKBgQCzkqOwCspNUlptX0iaPXWMZv3Dl2YPedu4ktGkby3Gdt4UWjfssM9vokNS7bmPBBkWtIgzOQ6CFc5sps9gFdaWUn/8roRne6B3vVgUfmhYPRSWVoudFvE7m9WX5qLou4ZuLjavGlOnXurJNpci4nuhurn+UXjEF+Ea/JEIYygcgwKBgQCMJqyAGBneIzR+dr4EbWInf2JhLE2r9KZv+bbUOoi00NrULgBRqRbMotbDaoxulPKiagsiqLOD3BmB38NIMx/Nqq2o17eAxk+0UDBWpqdBpR82ZVEiBeqDoqilcg21QneQfXyVBsotybQm3Zng0wM75rL7ZibnNG+0KqPE3cYUgQKBgBLz5izdZq60g3DJvrrww6hW8m+jWhVGEkevz0qgpV8uCpBxWTe1DZ/Fq39kMdKDZzZ7RWjKm9pk/M2A6aBFjcl92Z5MuRLG/I/GvzYihVbS04puhfvFgR5+exO2NvmDWVPlssXWbH/cx8EG7vqylsPiFmFeLfXcY1ja0V/mP0SLAoGAfQpnQeSSDxlKrMxCv9/Nai+iH+gKGnqpxyQ80sRP2si8eVxZXmtna8CZDpBNHkzmE6whobLK361Wj8sLQmNBSXM2vdeUkC7d4PJoh5YDp0/7fWMrmyGuoOfOJuvSHNeoN5caiIR/n9JBZeu0ol8dnzmq9984R4ufMg5KDVxxHRE=";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgzRlYejjRMyU6d6Dfr9WSibVIICJnCIKhQXGTGz4acy1jPoZM5P/d99+iiwQh5+iNihkHCKZacxMjZ2Zp8C65Ml1AApImQ7mElultEOTIKWFm/qK4+n+9dO/7Gr2inBQQev1EayEJCTCiN1BhfzFsuKxR4NR8cmcGxC4BhxCmEZhNylBCPcB8D9E8XVWjSG78DnnUeg8LZhVNIntKXfoL7DaNNXP+L0c3sOFc3QKnYas9eyJvyTnvwEEKiFJTFG190+KcnA4Sdcu8BZtUIa67kfweBhTSS5NDdJyQ2g4qaiSs/vkzkPKTBxkhxt0X5ZGnIBIWlVAwWteekPEJTA6fQIDAQAB";
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId("9021000132668508");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo("70501111111S001111119");
        model.setTotalAmount("9.00");
        model.setSubject("大乐透");
        model.setProductCode("QUICK_WAP_WAY");
        model.setSellerId("2088102147948060");
        request.setBizModel(model);
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
    }
}
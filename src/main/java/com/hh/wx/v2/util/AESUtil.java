package com.hh.wx.v2.util;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * @author wujianjun
 * @date 2020/4/20 6:33 PM
 */
public class AESUtil {

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";

    static {
//        Security.addProvider(new BouncyCastleProvider());
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data, String key) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(Md5Utils.Md5Base32Encode(key).getBytes(), ALGORITHM);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return new String(Base64Utils.encode(cipher.doFinal(data.getBytes())));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(Md5Utils.Md5Base32Encode(key).getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(Base64Utils.decode(base64Data.getBytes())));
    }

    public static void main(String[] args) throws Exception {
        String A = "ufDW2yUXSafVUChYFvGXlY1JFB0csSWwJQV+1fMCHVRjZjI+qEsC+lksix9AcPrirOU2jSE4SEH+OgN08loXsNP/HB7TfvqXVXPEm3JM3TF6qAvP3SPDoM6KnVtUTdxJMmBcysQd2G4qX/D9C3oBEmF/QClKXtOOBDDeWxkRpcnbys0uBDd1CEMx3/Zwl0LGb8m7t614e7P2l/ygqTvAp4iKIekCj9iNj64qNJZsTxWweKqHghymtEe7kSRzhS/yr7mYh1loTjB2sxuT1uB1J0fS7i9vSqW3K3cqGePuySvCMnuInbgWO/NS3vJKVoiHkzAQXR6ISJyzaIxtYq/lBj+1SVPoH4L/z+JhDEx035eYo27WSknhkwwMGdW2EjZB6Ub8AfvgIRtBNtxl87uWkmaDfE9WlOqVEEz6Cbk4tc4CWqLZdOJ7xi3suivKOycolRfsCunVHaqfpXnIo8iiFzunO5A7k9VG3p9m7yDUNOo8xXtJWTvscfVsrt5XPe7YhYharI+QvDmLb8osCzXKlFOMu+iFrCzajL3dhygRudl2i/7ou8IZyRCZnry+TwhsTxRt/h14oeo7qLmk/BSQRFKaHAXHj2uw8EO8vjhzGmvfQCwseusR14T4/k40Djhc/e7k4T9U0ZklAsWbFsD1wTVjo0/JYZi1HnAy4efhDY86RCA/dCtwOkidsWFHgfwZNhDaDADLOJ6gGE5nMAuLgWrbJAALXtjqYXksQf9QVv0P53/5XLawN15frLOeSHOoZ4Dt9Poz5Sg+HHWJQK0qbdV9DUTbRJdi7FrC6HI2surTuwL8LMoskj1MNXGwW5ZfOM2QqyNPRYOrMMRTu8OmloHX3Jx4Myy90iLr3qdupoFampvVLcDpqWie60T+MXv9qIynYcGiBadNX5bqwUgZPBheQzXg3QlnNfePi6OdUTw0h4wsuUVvFYkr+wq6c8TEeU5joGQtBh2s0/H0EN5PXW1A54t4IskpsEXjX3yHLvjZAVSlk9aL4F2jvGrrKio8mKd1SRFijRHRBCDYoKlli9herpND3I0gdW57pJyc9+h50dO0n8vvRaaGGxKTQiV6";
        A = AESUtil.decryptData(A, "BD9A94F372903FC663085456D6EFFEA1");
        System.out.println(A);
    }
}

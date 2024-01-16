package com.hh.wx.v2.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * @author huanghan
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
        Security.addProvider(new BouncyCastleProvider());
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

}

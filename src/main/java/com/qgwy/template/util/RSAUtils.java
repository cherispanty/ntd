package com.qgwy.template.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/** */

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 * @author IceWee
 * @version 1.0
 * @date 2012-4-26
 */
public class RSAUtils {

    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /** */
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** */
    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /** */
    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /** */
    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * java端公钥加密
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            data = Base64Utils.encode(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    /**
     * java端私钥解密
     */
    public static String decryptDataOnJava(String data, String PRIVATEKEY) {
        String temp = "";
        try {
            byte[] rs = Base64Utils.decode(data);
            temp = new String(RSAUtils.decryptByPrivateKey(rs, PRIVATEKEY), "UTF-8"); //以utf-8的方式生成字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }


    public static void main(String[] args) {
        System.err.println("私钥加密——公钥解密");
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsi3EqwscnNQRBBAHByBhZqFUnSO8dCB5OjOKEivncOLhPxZXiczU420Fwd6p9FHTe2pgcJ6s6syLS6MdGq6JPBNNqYZGAytQO2GDU0RyhGyGru8HieyvCZLXN/s/oYEvzqRsW3JnCq+yFY8g7ItdFyeChrqD6GDHveAMETgoOiQIDAQAB";
            //String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKyLcSrCxyc1BEEEAcHIGFmoVSdI7x0IHk6M4oSK+dw4uE/FleJzNTjbQXB3qn0UdN7amBwnqzqzItLox0arok8E02phkYDK1A7YYNTRHKEbIau7weJ7K8Jktc3+z+hgS/OpGxbcmcKr7IVjyDsi10XJ4KGuoPoYMe94AwROCg6JAgMBAAECgYB+Jljv5YZLkQRyMGbEcfHzoxHW7gj8VdIqpfB67UzuiH2gvRnvfUG9NvhqLON2HX2w7Wrmss4K1auxPcVsUtIP9gasr7jlxVuFRcOxsS6g9akmeTftboLg98a8PwspMqDMtihV1Ea6MLjxOihM5fm4UjliRw/yaVM8VJySrCrdgQJBAN0BIEAfJFDrmhcrvS7jTWe7ktyWVtHCgvlE8yagPWqalMob6KeGWr8lFrKMWHjuZ64nDO7rXC3sK106ataaODECQQDH3eaoCLBV2RN+Mr/usDQQhexoNI4tBhwQG3tzN0TyD0zqB4gG8XHVWaTBahhNy+s0K3ci/60XsYmR3UOOSP3ZAkBsBnrfHNnBfAgLv2NvocdaJwN4Mzwot60RbrRzcuvSSdDkoZOtHy0INXbMu3EbW1zucUQPHJgoEkJ4BW/nieZhAkBNV8iXg+1A+2T4tAjkBBnRvxGR7ayLLc8USJ5IIr4ycctQ0SNJlIt5hVwlYLoQ+PWG1b8fsxM3lQNoh9hJqmNZAkEAiSW6nt1+Ve5zKfI00IJNX9dkOns5oYUy93BFv0MBC+CDtJ/KGg5I5LLmT3Q2klNp4Kr75lUh73nKGcxC/zxMSw==";
            String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIGEgGxi0UHyKZePbpfELoGG5/M9lTjD43KkrSDuyX3SJY4EndQwK3pzMjpU39c+sLkBQXOIOWaVC4XFeLeuN8FY7+sMUopEJuJjaoUI7C8pb0nQzaSgijTOdsPf6P4es6SOsiRkpx80mmTlqxHqnvgeBGM35Cl0TeADlZvW+q4dAgMBAAECgYBAa3xI7LmyeFWRqejTGeQbztMN3EKdcmlt+1SVfc/AgkblpYwvHypxBF0mQFL5x5e03VgFs+sBB/8sMTVC/yeBkYCAb8M5qn5VjAtANZAi+3EqOqGDqQ7J9P+Os2TDpUglJ8IhXgdNps8cORMvxAc70Ou5+WvGr2mEab43UOzXDQJBAL4vjvLRYnhz6ablL33m/dBkPjdfOnIXbUKZHkThugclHcT1f86DYeqTPW0W25U3/g488kkomKMZI6seC/W9PGcCQQCuVmKluSfAM5yCjk0EAB98BXrSbRHNPAJY2HVGj34A1JWBl7D4XZ9BrnrxuTwcsTeNnnhobKMpVhE5VrfiZq7bAkAJ2ibal1Q1jzOkbKz4JbYyTG8YqTs629HSKGJ0CQl9A0Q2hs03IoE5CKWe+Bcp9LuZezjF5294y32YiLbv5PyzAkAxEPUQqWzrplr9T6mTX6l80VoBEVt6RNJwSPOw2T/gYfR2FchkDo19AetuTvEBLktxW1GOKk09nBCa77IvUTETAkBT0iJye0ORefXRjXweHr5Z06NJrI7l76E7Q7mc3uAAIR6FFMtgWPwOdaye0oru+YekU4URzziJPWy2gKV2s+Qu";

            String source = "agentNum=A151201806291654416990&startTime=2018-07-09 09:00:00&endTime=2018-07-09 10:00:00&pageSize=100&pageNum=1";
            System.out.println("原文字:" + source);
            byte[] data = source.getBytes();
            String sign = RSAUtils.sign(data, privateKey);
            System.out.println("生产签名数据:" + sign);
            System.err.println("签名:" + RSAUtils.verify(source.getBytes(), publicKey, sign));

            String source1 = "agentNum=A011201806071120134030&mercNo=M01201806141631425890&feerateCode=99999999&productType=QPOS&payType=0";
            System.out.println("2:" + RSAUtils.sign(source1.getBytes(), privateKey));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
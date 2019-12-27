package com.laonsys.springmvc.extensions.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;

public class EncryptionUtils {

    /**
     * 해당 알고리즘에 사용할 비밀키(SecretKey)를 생성한다.
     * 
     * @param algorithm
     *        DES/DESede/TripleDES/AES
     * @return 비밀키(SecretKey)
     * @throws NoSuchAlgorithmException
     */
    public static Key generatekey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    /**
     * 주어진 데이터로, 해당 알고리즘에 사용할 비밀키(SecretKey)를 생성한다.
     * 
     * @param algorithm
     *        DES/DESede/TripleDES/AES
     * @param keyData
     *        비밀키를 생성하기 위한 data
     * @return 비밀키(SecretKey)
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    public static Key generateKey(String algorithm, byte[] keyData) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        String upper = StringUtils.upperCase(algorithm);
        if ("DES".equals(upper)) {
            KeySpec keySpec = new DESKeySpec(keyData);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            return secretKey;
        }
        else if ("DESede".equals(upper) || "TripleDES".equals(upper)) {
            KeySpec keySpec = new DESedeKeySpec(keyData);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            return secretKey;
        }
        else {
            SecretKeySpec keySpec = new SecretKeySpec(keyData, algorithm);
            return keySpec;
        }
    }

    /**
     * 주어진 문자열을 이용하여 암호화 키를 생성한다. (AES 알고리즘사용)
     * 
     * 암호화 키는 128 bit (16 byte)를 넘을수 없기 때문에 키 스트링의 길이가 16 byte보다 크면 16 byte만 사용하고, 작은 경우엔, 키 스트링을 반복해서 뒤에 붙여서 사용한다.
     * 
     * @param keyString
     *        암호화 키를 만들기 위해 사용할 문자열
     * @return 비밀키(SecretKey)
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    public static Key getSecretKey(String keyString) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        byte[] strByte = keyString.getBytes();
        int len = strByte.length;
        byte[] keyData = new byte[16];

        if (len >= 16) {
            keyData = Arrays.copyOf(strByte, 16);
        }
        else {
            keyData = Arrays.copyOf(strByte, 16);
            for (int i = 0; i < (16 - len); i++) {
                keyData[len + 1] = strByte[i % len];
            }
        }

        Key key = generateKey("AES", keyData);
        return key;
    }

    /**
     * 비밀키로 암호화 (AES 알고리즘사용)
     * 
     * @param key
     *        비밀키(secret key)
     * @param target
     *        암호화할 원문
     * @return 암호화된 데이터
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encrypt(Key key, String target) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        String transformation = "AES/ECB/PKCS5Padding";
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] plain = target.getBytes();
        return cipher.doFinal(plain);
    }

    /**
     * 비밀키(SecretKey)로 복호화 (AES 알고리즘사용)
     * 
     * @param key
     *        비밀키(SecretKey)
     * @param encrypt
     *        암호화된 데이터
     * @return 복호화된 데이터
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decrypt(Key key, byte[] encrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        String transformation = "AES/ECB/PKCS5Padding";
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(encrypt);
    }

    public static String encryptToUrl(String keyString, String source) throws Exception {
        Key key = getSecretKey(keyString);
        byte[] encrypt = encrypt(key, source);
        return new String(URLCodec.encodeUrl(null, encrypt));
    }
    
    public static String decryptFromUrl(String keyString, String url) throws Exception {
        Key key = getSecretKey(keyString);
        byte[] decrypt = decrypt(key, URLCodec.decodeUrl(url.getBytes()));
        return new String(decrypt);
    }
    
    public static void main(String[] args) throws Exception {
        Key key = getSecretKey("smartchurch");

        System.out.println("원본 : kaldosa@gmail.com");

        byte[] encrypt = encrypt(key, "kaldosa@gmail.com");

        System.out.println("암호 : " + encrypt);

        String urlString = new String(URLCodec.encodeUrl(null, encrypt));
        System.out.println("URL encode : " + urlString);

        System.out.println("URL decode : " + URLCodec.decodeUrl(urlString.getBytes()));

        byte[] decrypt = decrypt(key, URLCodec.decodeUrl(urlString.getBytes()));

        System.out.println("복호 : " + new String(decrypt));
    }
}

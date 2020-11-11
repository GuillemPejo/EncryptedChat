package com.example.criptografia_xat_guillem;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class XifratSimetric {
    private static Cipher cipher;
    private KeyGenerator keygen;
    private SecretKey secretKey;

    public XifratSimetric() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.keygen = KeyGenerator.getInstance("AES");
        this.keygen.init(256);

        this.secretKey = keygen.generateKey();
        cipher = Cipher.getInstance("AES");
    }


    public static String encrypt(String msg, SecretKey secretKey) throws Exception {
        byte[] plainTextByte = msg.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String text_xifrat = encoder.encodeToString(encryptedByte);
        System.out.println("MISSATGE XIFRAT AMB SIMETRIC CORRECTAMENT: "+text_xifrat);
        return text_xifrat;
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}

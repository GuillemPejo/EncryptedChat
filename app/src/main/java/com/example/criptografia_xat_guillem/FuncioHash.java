package com.example.criptografia_xat_guillem;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class FuncioHash {

    public static String getHash(String msg) {
        try {
            // CREACIÓ DEL HASH MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(msg.getBytes());
            byte messageDigest[] = digest.digest();

            // CREACIÓ HEX
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            System.out.println("FUNCIO HASH FETA CORRECTAMENT: "+hexString);

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

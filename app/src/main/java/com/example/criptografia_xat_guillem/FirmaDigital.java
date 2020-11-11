package com.example.criptografia_xat_guillem;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;

public class FirmaDigital {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public FirmaDigital() throws NoSuchAlgorithmException {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        this.keyPair = keygen.generateKeyPair();
        this.privateKey = this.keyPair.getPrivate();
        this.publicKey = this.keyPair.getPublic();
    }

    public static byte[] generarSignatura(String msg, Signature sign) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException {
        byte[] data = msg.getBytes(StandardCharsets.UTF_8);
        sign.update(data);

        return sign.sign();
    }

    public static byte[] firmarDades(String msg, PrivateKey priv) throws Exception {
        byte[] firma = null;
        byte[] data = msg.getBytes(StandardCharsets.UTF_8);

        Signature signer = Signature.getInstance("SHA1withRSA/PSS");
        signer.initSign(priv);
        signer.update(data);
        firma = signer.sign();
        System.out.println("MISSATGE SIGNAT CORRECTAMENT: "+firma);

        return firma;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}

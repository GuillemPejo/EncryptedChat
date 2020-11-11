package com.example.criptografia_xat_guillem;


import java.security.PrivateKey;

import javax.crypto.SecretKey;

public class Missatge {
    private String username;
    private String missatge;
    private String funciohash;
    private String xifrat_simetric;
    private String xifrat_asimetric;
    private byte[] firma_digital;
    private boolean isLongPressed = false;

    public Missatge(String username, String msg, SecretKey symmetricSecretKey, PrivateKey asymmetricPrivateKey,
                    PrivateKey signaturePrivateKey) throws Exception {
        this.username = username;
        this.missatge = msg;


        // FIRMA DIGITAL
        this.firma_digital = FirmaDigital.firmarDades(msg, signaturePrivateKey);

        // FUNCIÓ HASH
        this.funciohash = FuncioHash.getHash(msg);

        // XIFRAT SIMÈTRIC
        this.xifrat_simetric = XifratSimetric.encrypt(msg, (SecretKey) symmetricSecretKey);

        // XIFRAT ASIMÈTRIC
        this.xifrat_asimetric = XifratAsimetric.encryptMessage(msg, asymmetricPrivateKey);

    }

    public String getEncriptarText() {
        return "\n\nFirma Digital\n" + getFirmaDigital() + "Funcio Hash\n" + getFuncioHash() + "\n\nEncriptació Simètrica\n" + getXifratSimetric() +
                "\n\nEncriptació Asimètrica\n" + getXifratAsimetric();
    }

    public String getUsername() {
        return username;
    }

    public String getMissatge() {
        return missatge;
    }

    public String getXifratSimetric() {

        return xifrat_simetric;
    }

    public String getXifratAsimetric() {

        return xifrat_asimetric;
    }

    public String getFuncioHash() {

        return funciohash;
    }

    public byte[] getFirmaDigital() {

        return firma_digital;
    }

}


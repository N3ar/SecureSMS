package com.research.near.encryptedsms;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RsaKeyGen {
    public static KeyPair newKeyPair() throws NullPointerException, NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    public static String RSAEnc(String text, PublicKey key) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OASP");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(text.getBytes());
        return Base64.encodeToString(cipherText, Base64.DEFAULT);
    }

    public static String RSADec(String toDecrypt, PrivateKey key) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OASP");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] text = cipher.doFinal(Base64.decode(toDecrypt, Base64.DEFAULT));
        return new String(text);
    }
}

package com.research.near.encryptedsms;

import android.util.Base64; // https://developer.android.com/reference/android/util/Base64.html

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptSMS {

    private static final String comparison = "placeholder#D%kd";
    private String privateKey = "placeholder#D%kd";

    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    public EncryptSMS(String otherNumber) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        privateKey = FindKey.findKeyAES(otherNumber);
//         CURRENT STATE ASSUMING THAT SECRETS ARE SECURELY DISTRIBUTED
        if (privateKey.equals(comparison)) {
            SendKey.sendKey(otherNumber);
        }
        ivParameterSpec = new IvParameterSpec(comparison.getBytes("UTF-8"));
        secretKeySpec = new SecretKeySpec(privateKey.getBytes("UTF-8"), "AES");
        cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

    public String AESenc(String toEncrypt) throws InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    public String AESdec(String toDecrypt) throws InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(Base64.decode(toDecrypt, Base64.DEFAULT));
        return new String(decrypted);
    }
}

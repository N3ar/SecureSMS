package com.example;

import android.os.Environment;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SaveKey {
    static private File privateKeyFile = new File(Environment.getExternalStoragePublicDirectory(
                                         Environment.DIRECTORY_DOCUMENTS), "RSAPrivateKey.pem");

    static public void saveKey(String number, String content) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, InvalidKeyException, BadPaddingException {
        RsaKeyGen.RSADec(content, getPrivate());
        File folder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "SymmetricKey_" + number + ".key");
        FileWriter fileWriter = new FileWriter(folder);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

    static public void saveKey(String number, String content, Boolean local) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, InvalidKeyException, BadPaddingException {
        //RsaKeyGen.RSADec(content, get());
        if (local) {
            File folder = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "SymmetricKey_" + number + ".key");
            FileWriter fileWriter = new FileWriter(folder);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } else {
            saveKey(number, content);
        }
    }

    static private PrivateKey getPrivate() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        FileInputStream fis = new FileInputStream(privateKeyFile);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int)privateKeyFile.length()];
        dis.readFully(keyBytes);
        dis.close();

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }
}

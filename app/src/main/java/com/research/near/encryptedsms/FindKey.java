package com.research.near.encryptedsms;

import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class FindKey {
    // FileReader behavior read fromhttp://www.tutorialspoint.com/java/java_filereader_class.htm
    static public String findKeyAES(String number) throws IOException {
        File folder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "SymmetricKey_" + number + ".key");
        FileReader fileReader = new FileReader(folder);
        char [] a = new char[16];
        fileReader.read(a);
        fileReader.close();
        return new String(a);
    }

    static public String findKeyRSA(String number) throws IOException {
        File folder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "RSAPublicKey_" + number + ".key");
        FileReader fileReader = new FileReader(folder);
        char [] a = new char[128];
        fileReader.read(a);
        fileReader.close();
        return new String(a);
    }

    static public PublicKey getPublicKeyBytes(String string) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        InputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
        DataInputStream dis = new DataInputStream(stream);
        byte[] keyBytes = new byte[string.length()];
        dis.readFully(keyBytes);
        dis.close();

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
}

package com.research.near.encryptedsms;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SendKey {
    static public void sendKey(String recipientNumber) throws IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
            BadPaddingException, IllegalBlockSizeException {
        String sharedSecret = AESSharedSecretGen.newAESKey();

        SaveKey.saveKey(recipientNumber, sharedSecret, true);

        String rsaPub = FindKey.findKeyRSA(recipientNumber);
        RsaKeyGen.RSAEnc(sharedSecret, FindKey.getPublicKeyBytes(rsaPub));

        // TODO: Send special message
    }
}

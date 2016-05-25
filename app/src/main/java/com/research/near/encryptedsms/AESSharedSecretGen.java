package com.research.near.encryptedsms;

import java.security.SecureRandom;

public final class AESSharedSecretGen {
    private static SecureRandom random = new SecureRandom();
    private static final String characters =
            "abcdefghijkklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int len = 16;

    // Adapted from http://stackoverflow.com/questions/2863852/
    public static String newAESKey() {
        char[] text = new char[len];
        for (int i = 0; i < len; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}

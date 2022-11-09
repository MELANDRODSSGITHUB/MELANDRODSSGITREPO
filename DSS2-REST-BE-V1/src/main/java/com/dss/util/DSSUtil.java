package com.dss.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class DSSUtil {

    private DSSUtil() {
    }

    public static String passwordEncryption(String password) {
        MessageDigest messageDigest;
        String hashedPassword;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] passwordInBytes = password.getBytes();
            messageDigest.update(passwordInBytes);
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte byteVar : resultByteArray) {
                String formattedString = String.format("%02x", byteVar);
                stringBuilder.append(formattedString);
            }
            hashedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No Such Algorithm....", e);
        }

        return hashedPassword;
    }
}

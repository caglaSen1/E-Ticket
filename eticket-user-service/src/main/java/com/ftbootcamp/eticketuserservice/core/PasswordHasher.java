package com.ftbootcamp.eticketuserservice.core;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    public static String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // Digest the password bytes
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array into a hexadecimal string
            BigInteger number = new BigInteger(1, hashedBytes);
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 128) {
                hexString.insert(0, '0');
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

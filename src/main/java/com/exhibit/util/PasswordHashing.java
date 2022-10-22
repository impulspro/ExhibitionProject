package com.exhibit.util;

import com.exhibit.exeptions.DaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//MD5 Hashing Technique
public class PasswordHashing {
    public static String toMD5(String crypt) {
        /* Plain-text password initialization. */
        String encryptedpassword;
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(crypt.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new DaoException("Cannot convert password", e);
        }
        return encryptedpassword;
    }
}

package com.auto_ds.app;

import org.jasypt.util.text.StrongTextEncryptor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ArgumentUtils {

    public static final String PASSWORD = "PASSWORD";
    public static final String EMAIL = "EMAIL";
    public static final String ACT_TIME = "ACT_TIME";
    public static final String ACT_DATE = "ACT_DATE";
    public static final String FIRE_TIME = "FIRE_TIME";
    public static final String FOOTBALL_SIDE = "FOOTBALL_SIDE";

    private static StrongTextEncryptor encryptor = new StrongTextEncryptor();

    static {
        encryptor.setPassword("ABC");
    }

    public static String encrypt(String password) {
        return encryptor.encrypt(password);
    }

    public static String decrypt(String encryptedPass) {
        return encryptor.decrypt(encryptedPass);
    }
}

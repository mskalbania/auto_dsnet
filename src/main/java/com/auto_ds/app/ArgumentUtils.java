package com.auto_ds.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArgumentUtils {

    public static final String PASSWORD = "PASSWORD";
    public static final String EMAIL = "EMAIL";
    public static final String TYPE = "TYPE";
    public static final String ACT_TIME = "ACT_TIME";
    public static final String ACT_DATE = "ACT_DATE";
    public static final String FIRE_TIME = "FIRE_TIME";

    private Map<String, String> argumentsMap;

    ArgumentUtils(String[] args) {
        argumentsMap = parseArgumentList(args);
    }

    public static boolean isHelpFlagPresent(String[] args) {
        return Arrays.stream(args)
                .anyMatch(s -> s.contains("-h"));
    }

    public boolean areCredentialsPresent() {
        return argumentsMap.containsKey(PASSWORD) && argumentsMap.containsKey(EMAIL);
    }

    public String getPassword() {
        return argumentsMap.get(PASSWORD);
    }

    public String getEmail() {
        return argumentsMap.get(EMAIL);
    }

    public Map<String, String> getArgumentsMap() {
        return this.argumentsMap;
    }

    private Map<String, String> parseArgumentList(String[] args) {
        Map<String, String> argsMap = new HashMap<>();
        for (int i = 0; i < args.length - 1; i += 2) {
            String key = args[i];
            String value = args[i + 1];
            switch (key) {
                case "-p":
                    argsMap.put(PASSWORD, value);
                    break;
                case "-e":
                    argsMap.put(EMAIL, value);
                    break;
                case "-type":
                    argsMap.put(TYPE, value);
                    break;
                case "-t":
                    argsMap.put(ACT_TIME, value);
                    break;
                case "-d":
                    argsMap.put(ACT_DATE, value);
            }
        }
        return argsMap;
    }
}

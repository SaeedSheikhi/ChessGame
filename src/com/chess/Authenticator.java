package com.chess;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 2017-07-26.
 */
public class Authenticator {
    public static final Map<String, String> MEMBER_MAP_VALIDATOR = new HashMap<String, String>();
    static {
        MEMBER_MAP_VALIDATOR.put("demo", "demo");
    }
    public static boolean validate(String user, String password){
        String validUserPassword = MEMBER_MAP_VALIDATOR.get(user);
        return validUserPassword != null && validUserPassword.equals(password);
    }
}
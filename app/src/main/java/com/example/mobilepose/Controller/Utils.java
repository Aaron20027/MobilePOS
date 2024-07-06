package com.example.mobilepose.Controller;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Utils {
    public static String ToJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <JsonType> JsonType FromJson(String json, Class<JsonType> jType) {
        return new Gson().fromJson(json, jType);
    }

    public static String MD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return "";
    }

}

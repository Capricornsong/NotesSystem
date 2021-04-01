package cn.edu.bnuz.notes.pojo;

import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class Token {
    public static String token = "";
    public static JsonObject UserInf = new JsonObject();

}

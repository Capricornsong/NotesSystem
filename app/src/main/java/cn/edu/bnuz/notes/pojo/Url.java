package cn.edu.bnuz.notes.pojo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;
import rxhttp.wrapper.annotation.DefaultDomain;
import rxhttp.wrapper.annotation.Domain;
import rxhttp.wrapper.annotation.Param;
import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.parse.AbstractParser;

public class Url {
    @DefaultDomain() //设置为默认域名
    public static String baseUrl = "http://120.76.128.222:8004/internal/";
    @Domain(name = "file") //非默认域名
    public static String internal = "http://120.76.128.222:8004/file/";
    @Domain(name = "auth") //非默认域名
    public static String auth = "http://120.76.128.222:8004/auth/";
    //websocket测试地址
    public static final String wsTest = "ws://echo.websocket.org";
    //websocket测试地址
    public static final String ws = "ws://39.108.195.47:8001/endpoint-websocket";

}

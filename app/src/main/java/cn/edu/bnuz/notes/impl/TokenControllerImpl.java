package cn.edu.bnuz.notes.impl;

import android.annotation.SuppressLint;
import android.os.Binder;
import android.util.Log;

import com.google.gson.JsonObject;

import cn.edu.bnuz.notes.interfaces.ITokenController;
import cn.edu.bnuz.notes.ntwpojo.BaseRD;
import cn.edu.bnuz.notes.ntwpojo.DeleteNoteTagShareEmailCheckUsernameCheckRD;
import cn.edu.bnuz.notes.ntwpojo.NotesbyPageorTagIdRD;
import cn.edu.bnuz.notes.ntwpojo.RegisterRD;
import cn.edu.bnuz.notes.ntwpojo.TokenRD;
import cn.edu.bnuz.notes.ntwpojo.UserInfo;
import cn.edu.bnuz.notes.pojo.Token;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import rxhttp.RxHttp;
import rxhttp.wrapper.cahce.CacheMode;

import static rxhttp.RxHttp.*;

public class TokenControllerImpl extends Binder implements ITokenController{

    private static final String TAG = "TokenControllerImpl";

    @Override
    public int GetToken(String username, String password) {
        AtomicInteger result = new AtomicInteger();
        Log.d(TAG, "GetToken: username" + username + " pass:" + password);
        postForm("http://120.76.128.222:8004/auth/oauth/token")
                .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)  //设置单独的缓存策略为先请求网络，请求成功，写入缓存并返回；否则读取缓存.
                .setCacheValidTime(6000 * 1000) //当前请求缓存有效期为6000秒
                .setSync()
                .add("client_id","android")
                .add("client_secret","android")
                .add("redirect_uri","www.baidu.com")
                .add("grant_type","password")
                .add("username",username)
                .add("password",password)
                .asClass(TokenRD.class)
                .subscribe(t -> {
                    result.set(1);
                    Log.d(TAG, "result1: " + result.get());
                    Token.token = "Bearer " + t.getAccess_token();
                    Log.d(TAG, "Token.token：:" + Token.token);
                },throwable -> {
                    Log.d(TAG, "fail case" + throwable);
                    Log.d(TAG, "result2: " + result.get());
                    result.set(0);
                    Log.d(TAG, "result3: " + result.get());
                });
        Log.d(TAG, "result4: " + result.get());
        return result.get();
    }

    @SuppressLint("CheckResult")
    @Override
    public int EmailCheck(String email) {
        AtomicInteger result = new AtomicInteger();
        get("/user/email/" + email)
                .setDomainToauthIfAbsent()  //指定使用auth url
                .setSync()
                .asClass(DeleteNoteTagShareEmailCheckUsernameCheckRD.class)
                .subscribe(c -> {
                    Log.d("GetNoteByNoteID", "code: " + c.getMsg());
                    result.set(c.getCode());
                    if (c.getCode() == 200){
                        Log.d(TAG, "GetNoteByNoteID: 成功检测邮箱！");
                    }
                    else {
                        Log.d(TAG, "CheckUsername: 出现问题");
                        Log.d(TAG, "GetNoteByNoteID: code --> " + c.getCode());
                    }
                });
        return result.get();
    }

    /**
     * 检查用户名是否已存在
     * @param username
     * @return
     */
    @Override
    public int UsernameCheck(String username) {
        AtomicInteger result = new AtomicInteger();
        get("/user/username/" + username)
                .setDomainToauthIfAbsent()  //指定使用auth url
                .setSync()
                .asClass(DeleteNoteTagShareEmailCheckUsernameCheckRD.class)
                .subscribe(c -> {
                    Log.d("GetNoteByNoteID", "code: " + c.getMsg());
                    result.set(c.getCode());
                    if (c.getCode() == 200){
                        Log.d(TAG, "GetNoteByNoteID: 成功检测用户名！");
                    }
                    else {
                        Log.d(TAG, "CheckUsername: 出现问题");
                        Log.d(TAG, "GetNoteByNoteID: code --> " + c.getCode());
                    }
                });
        return result.get();
    }

    /**
     * 发送验证码
     * @param email
     * @return
     */
    @Override
    public int SendEmailCode(String email) {
        AtomicInteger result = new AtomicInteger();
        get("/email/" + email)
                .setDomainToauthIfAbsent()  //指定使用auth url
                .setSync()
                .asClass(DeleteNoteTagShareEmailCheckUsernameCheckRD.class)
                .subscribe(c -> {
                    Log.d("GetNoteByNoteID", "code: " + c.getMsg());
                    result.set(c.getCode());
                    if (c.getCode() == 200){
                        Log.d(TAG, "GetNoteByNoteID: 成功发送邮箱验证码！");
                    }
                    else {
                        Log.d(TAG, "EmailCodeCheck: 出现问题");
                        Log.d(TAG, "GetNoteByNoteID: code --> " + c.getCode());
                    }
                });
        return result.get();
    }

    /**
     * 注册新用户
     * @param username
     * @param email
     * @param checkcode
     * @param password
     * @return
     *      200    成功
     *      100085 验证码错误
     */
    @Override
    public int Register(String username, String email, String checkcode, String password) {
        AtomicInteger result = new AtomicInteger();
        //先构建一个json对象
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("checkCode",checkcode);
        jsonObject.addProperty("email",email);
        jsonObject.addProperty("password",password);
        jsonObject.addProperty("username", username);
        Log.d(TAG, "Register: json:" + jsonObject.toString());
        postJson("/user")
                .setSync()
                .setDomainToauthIfAbsent()  //指定使用auth url
//                .as(RxLife.as((LifecycleOwner) this)) //页面销毁、自动关闭请求
                .addAll(jsonObject)
                .asClass(RegisterRD.class)
                .subscribe(c -> {
                    Log.d(TAG, "code: " + c.getMsg());
                    result.set(c.getCode());
                    if (c.getCode() == 200){
                        Log.d(TAG, "Register: 成功注册！");
                    }
                    else {
                        Log.d(TAG, "EmailCodeCheck: 出现问题");
                        Log.d(TAG, "GetNoteByNoteID: code --> " + c.getCode());
                    }
                });

        return result.get();
    }

    /**
     * @return UserInfo.class
     * {
     *     "code": 200,
     *     "msg": "SUCCESS",
     *     "data": {
     *         "userId": 888888888,
     *         "username": "test",
     *         "imgPath": "aaa",
     *         "email": null,
     *         "gmtCreate": "2020-05-08 21:57:23",
     *         "gmtModified": "2020-09-24 20:41:14",
     *         "version": 0,
     *         "roles": [
     *             {
     *                 "roleId": 1,
     *                 "roleName": "USER",
     *                 "description": "普通用户",
     *                 "gmtCreate": "2020-05-08 22:02:02"
     *             }
     *         ]
     *     }
     * }
     */
    @Override
    public UserInfo GetUserInfo() {
        UserInfo userbean = new UserInfo();
        RxHttp.get("/user/userInfo")
                .setSync()
                .setDomainToauthIfAbsent()
                .asClass(UserInfo.class)
                .subscribe(userinfo -> {
                    Log.d(TAG, "code: " + userinfo.getMsg());
                    userbean.setCode(userinfo.getCode());
                    userbean.setMsg(userinfo.getMsg());
                    Log.d(TAG, "GetUserInfo: time" + userinfo.getData().getGmtCreate()) ;
                    Log.d(TAG, "GetUserInfo: user" + userinfo.getData().getUsername()) ;
                    Log.d(TAG, "GetUserInfo: time" + userinfo.getData().getUserId()) ;
                    if (userinfo.getCode() == 200){
                        userbean.setData(userinfo.data);
                        Log.d(TAG, "GetUserInfo: 成功查询！");
                    }
                    else {
                        Log.d(TAG, "查询userinfo 出现问题");
                        Log.d(TAG, "查询userinfo: code --> " + userinfo.getCode());
                    }
                },throwable -> {
                    Log.d("GetUserInfo", "获取用户信息失败" + throwable);
                });


        return userbean;
    }

    /**
     * 更改用户密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public Boolean UpdatePassword(String oldPassword, String newPassword) {
        AtomicBoolean result = new AtomicBoolean();
        RxHttp.putForm("/user/password")
                .setDomainToauthIfAbsent()
                .addHeader("Authorization",Token.token)
                .add("oldPassword",oldPassword)
                .add("newPassword",newPassword)
                .setSync()
                .asClass(BaseRD.class)
                .subscribe(baseRD -> {
                    Log.d(TAG, "code: " + baseRD.getMsg());
                    if (baseRD.getCode() == 200){
                        result.set(true);
                        Log.d(TAG, "UpdatePassword: 成功查询！");
                    }
                    else {
                        result.set(false);
                        Log.d(TAG, "UpdatePassword 出现问题");
                        Log.d(TAG, "UpdatePassword: code --> " + baseRD.getCode());
                    }
                },throwable -> {
                    Log.d("UpdatePassword", "修改密码失败" + throwable);
                });
        return result.get();
    }

    @Override
    public Boolean UpdateUsername(String newName) {
        AtomicBoolean result = new AtomicBoolean();
        RxHttp.putForm("/user/username/" + newName)
                .setDomainToauthIfAbsent()
                .addHeader("Authorization",Token.token)
                .setSync()
                .asClass(BaseRD.class)
                .subscribe(baseRD -> {
                    Log.d(TAG, "code: " + baseRD.getMsg());
                    if (baseRD.getCode() == 200){
                        result.set(true);
                        Log.d(TAG, "UpdatePassword: 成功查询！");
                    }
                    else {
                        result.set(false);
                        Log.d(TAG, "UpdatePassword 出现问题");
                        Log.d(TAG, "UpdatePassword: code --> " + baseRD.getCode());
                    }
                },throwable -> {
                    Log.d(TAG, "修改用户名失败" + throwable);
                });
        return result.get();
    }


}

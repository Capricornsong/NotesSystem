package cn.edu.bnuz.notes.pojo;

import android.util.Log;
import cn.edu.bnuz.notes.ntwpojo.TokenRD;

import java.util.concurrent.TimeUnit;

import rxhttp.RxHttp;


public class User {
    private static String TAG = "User";
    String token;
    String userId;
    String username;
    String password;

    //获取token
    public String getToken() {
        Log.d(TAG, "getToken: " + token);
        return token;
    }

    public void setToken() {
//        RxHttp.postForm("http://120.76.128.222:8004/auth/oauth/token")
////                .subscribeOnCurrent() //指定在当前线程执行请求，即同步执行，
//                .add("client_id","android")
//                .add("client_secret","android")
//                .add("redirect_uri","www.baidu.com")
//                .add("grant_type","password")
//                .add("username",username)
//                .add("password",password)
//                .asClass(TokenRD.class)
//                .subscribe(t -> {
//                    this.token = "Bearer " + t.getAccess_token();
//                    Log.d(TAG, "token获取成功：:" + token);
//                },throwable -> {
//                    Log.d("User", "fail case" + throwable);
//                });
//        try {
//            //等待上方异步加载完
//            TimeUnit.MILLISECONDS.sleep(700);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

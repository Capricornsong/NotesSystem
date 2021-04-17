package cn.edu.bnuz.notes.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static cn.edu.bnuz.notes.constants.destPath;
import cn.edu.bnuz.notes.pojo.Token;
import static cn.edu.bnuz.notes.constants.destPath;
import java.io.File;
import java.io.UnsupportedEncodingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

import static cn.edu.bnuz.notes.MyReceiver.mNetworkInfo;
import static cn.edu.bnuz.notes.pojo.Token.UserInf;
import static cn.edu.bnuz.notes.pojo.Token.token;
import static rxhttp.RxHttp.setOnParamAssembly;

public class util {


    private static String TAG  = "util";

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    //检查网络状态
    public static boolean NetCheck(){
        if (mNetworkInfo != null && mNetworkInfo.isAvailable()){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * 判断文件是否存在（本地）
     * @param name
     * @return
     */
    public static boolean fileIsExists(String name) {
        try {
            File f = new File(destPath + name);
            if (f.exists()) {
                Log.d(TAG, "fileIsExists: 文件已存在");
                return true;
            } else {
                Log.d(TAG, "fileIsExists: 文件不存在");
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "fileIsExists: " + e);
        }
        return true;
    }

    /**
     * 通过Token获取用户信息
     */
    public static void gainUserId() {
        Log.d(TAG, "gainUserId: token");
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(("uaaNotes").getBytes("UTF-8"))
                    .parseClaimsJws(token.substring(7))
                    .getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //获取用户名
        String userinf = (String) claims.get("user_name");
        Gson gson = new Gson();
        UserInf = gson.fromJson(userinf, JsonObject.class);
        Log.d(TAG, "gainUserId: username" + UserInf.get("userId").toString());
    }
}

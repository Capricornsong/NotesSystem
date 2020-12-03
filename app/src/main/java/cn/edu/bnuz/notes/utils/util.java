package cn.edu.bnuz.notes.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import static cn.edu.bnuz.notes.constants.destPath;
import cn.edu.bnuz.notes.pojo.Token;
import static cn.edu.bnuz.notes.constants.destPath;
import java.io.File;

import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

import static cn.edu.bnuz.notes.MyReceiver.mNetworkInfo;
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
//            Toast.makeText(context,"网络已连接",Toast.LENGTH_LONG).show();
            return true;
        }
        else{
//            Toast.makeText(context,"网络已断开",Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public static void ShareControllerImpl(){
        setOnParamAssembly(new Function<Param<?>, Param<?>>() {
            @Override
            public Param apply(Param param) throws Exception {
                Method method = param.getMethod();
                return param.addHeader("Authorization", Token.token);
            }
        });
        Log.d(TAG, "NoteControllerImpl: 初始化。。");
    }

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
}

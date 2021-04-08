package cn.edu.bnuz.notes;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import cn.edu.bnuz.notes.cache.NoteCache;

import org.litepal.LitePal;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.edu.bnuz.notes.interfaces.IFileController;
import cn.edu.bnuz.notes.interfaces.IFileTrans;
import cn.edu.bnuz.notes.interfaces.INoteController;
import cn.edu.bnuz.notes.interfaces.IShareController;
import cn.edu.bnuz.notes.interfaces.ITagController;
import cn.edu.bnuz.notes.interfaces.ITokenController;
import java.net.URI;
//
import cn.edu.bnuz.notes.websocket.MyWebSocketClient;
import cn.edu.bnuz.notes.websocket.MyWebSocketClientService;
import cn.edu.bnuz.notes.websocket.SocketMessage;
import rxhttp.RxHttp;
import rxhttp.RxHttpPlugins;

public class MyApplication extends Application {
    private static final String TAG = "MainActivity";
    private Boolean mIsbind;
    private IntentFilter mIntentFilter;
    public static MyReceiver mMyReceiver;
    private Context mContext;
    //database
    private SQLiteDatabase mNoteDB;
    private NoteSocketReceiver mNoteSocketReceiver ;

    //初始化线程池
    /**
     * corePoolSize: 指定了线程池中的线程数量，它的数量决定了添加的任务是开辟新的线程去执行，还是放到workQueue任务队列中去；
     * maximumPoolSize：指定了线程池中的最大线程数量，这个参数会根据你使用的workQueue任务队列的类型，决定线程池会开辟的最大线程数量；
     * keepAliveTime：当线程池中空闲线程数量超过corePoolSize时，多余的线程会在多长时间内被销毁；
     * TimeUnit：时间单位
     *
     */
    public static final ThreadPoolExecutor threadExecutor = new ThreadPoolExecutor( 3, 4,  30,
            TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(10)
    );

    public static MyWebSocketClient myWebSocketClient;

    @Override
    public void onCreate() {
        super.onCreate();
        initRxHttpCache(this);
        NoteCache.initDiskCache(this);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();

        //Litepal相关
        LitePal.initialize(this);
        //初始化接收器（网络监测）
        initReceiver();
        //启动websocket服务
        startJWebSClientService();
        //绑定服务所有
        doBindService();
        //检测通知是否开启
        Log.d(TAG, "onCreate: not");
        checkNotification(this);
        //初始化数据库
        Log.d(TAG, "onCreate: '22222");
        initDatabese();
        //初始化WebSocket服务
//        initWebSocketService();
    }

    //初始化websocket连接
    public void initWebSocketService() {
        URI uri = URI.create("http://39.108.195.47:8001/endpoint-websocket");
        myWebSocketClient = new MyWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                //message就是接收到的消息
                Log.e("JWebSClientService", message);
            }
        };
        try {
            //连接
            Log.d(TAG, "initWebSocketService: " + myWebSocketClient.connectBlocking());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //TokenController
//    private TokenControllerImpl mTokenController;
    public static MyApplication.TokenConnection mTokenConnection;
    public static ITokenController mTokenController;
    private class TokenConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected:.." + componentName);
            mTokenController = (ITokenController) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected:.." + componentName);
        }
    }

    //NoteController
    public static MyApplication.NoteConnection mNoteConnection;
    public static INoteController mNoteController;
    private class NoteConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected:.." + componentName);
            mNoteController = (INoteController) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected:.." + componentName);
        }
    }

    //FileController
    public static MyApplication.FileConnection mFileConnection;
    public static IFileController mFileController;
    private class FileConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected:.." + componentName);
            mFileController = (IFileController) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected:.." + componentName);
        }
    }

    //TagController
    public static MyApplication.TagConnection mTagConnection;
    public static ITagController mTagController;
    private class TagConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected:.." + componentName);
            mTagController = (ITagController) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected:.." + componentName);
        }
    }

    //ShareController
    public static MyApplication.ShareConnection mShareConnection;
    public static IShareController mShareController;
    private class ShareConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected:.." + componentName);
            mShareController = (IShareController) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected:.." + componentName);
        }
    }

    //FileTranController
    public static MyApplication.FileTransConnection mFileTransConnection;
    public static IFileTrans mFileTransController;
    private class FileTransConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected:.." + componentName);
            mFileTransController = (IFileTrans) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected:.." + componentName);
        }
    }

    /**
     * 启动服务（websocket客户端服务）
     */
    private void startJWebSClientService() {
        Intent intent = new Intent(this, MyWebSocketClientService.class);
        startService(intent);
    }

    //websocket
    public MyWebSocketClient client;
    public MyWebSocketClientService.MyWebSocketClientBinder binder;
    public static MyWebSocketClientService myWebSClientService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务与活动成功绑定
            Log.e("MainActivity", "wesocket服务与活动成功绑定");
            binder = (MyWebSocketClientService.MyWebSocketClientBinder) iBinder;
            myWebSClientService = binder.getService();
            client = myWebSClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //服务与活动断开
            Log.e("MainActivity", "wesocket服务与活动成功断开");
        }
    };


    //LitePal初始化
    private void initDatabese() {
        mNoteDB = LitePal.getDatabase();
    }

    private void doBindService() {
        //TokenControl
        Log.d(TAG, "doBindService: 绑定TokenController服务。。");
        Intent TokenControlIntent = new Intent();
        TokenControlIntent.setAction("com.bnuz.noteservice.ACTION_TOKEN_CONTROLLER");
        TokenControlIntent.setPackage(this.getPackageName());
        TokenControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mTokenConnection = new MyApplication.TokenConnection();
        mIsbind = bindService(TokenControlIntent, mTokenConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "doBindService:*/*******************TokenController ");
        //NoteControl
        Log.d(TAG, "doBindService: 绑定NoteController服务。。");
        Intent NoteControlIntent = new Intent();
        NoteControlIntent.setAction("com.bnuz.noteservice.ACTION_NOTE_CONTROLLER");
        NoteControlIntent.setPackage(this.getPackageName());
        NoteControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mNoteConnection = new MyApplication.NoteConnection();
        mIsbind = bindService(NoteControlIntent, mNoteConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "doBindService:*/*******************NoteController ");
        //TagControl
        Log.d(TAG, "doBindService: 绑定TagController服务。。");
        Intent TagControlIntent = new Intent();
        TagControlIntent.setAction("com.bnuz.noteservice.ACTION_TAG_CONTROLLER");
        TagControlIntent.setPackage(this.getPackageName());
        TagControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mTagConnection = new MyApplication.TagConnection();
        mIsbind = bindService(TagControlIntent, mTagConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "doBindService:*/*******************TagController ");
        //ShareControl
        Log.d(TAG, "doBindService: 绑定ShareController服务。。");
        Intent ShareControlIntent = new Intent();
        ShareControlIntent.setAction("com.bnuz.noteservice.ACTION_SHARE_CONTROLLER");
        ShareControlIntent.setPackage(this.getPackageName());
        ShareControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mShareConnection = new MyApplication.ShareConnection();
        mIsbind = bindService(ShareControlIntent, mShareConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "doBindService:*/*******************ShareController ");
        //FileControl
        Log.d(TAG, "doBindService: 绑定FileController服务。。");
        Intent FileControlIntent = new Intent();
        FileControlIntent.setAction("com.bnuz.noteservice.ACTION_FILE_CONTROLLER");
        FileControlIntent.setPackage(this.getPackageName());
        FileControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mFileConnection = new MyApplication.FileConnection();
        mIsbind = bindService(FileControlIntent, mFileConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "doBindService:*/*******************FileController ");
        //FileTranControl
        Log.d(TAG, "doBindService: 绑定FileTransController服务。。");
        Intent FileTranControlIntent = new Intent();
        FileTranControlIntent.setAction("com.bnuz.noteservice.ACTION_FILETRANS_CONTROLLER");
        FileTranControlIntent.setPackage(this.getPackageName());
        FileTranControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mFileTransConnection = new MyApplication.FileTransConnection();
        mIsbind = bindService(FileTranControlIntent, mFileTransConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "doBindService:*/*******************FileTransController ");

        //WebSocket
        Log.d(TAG, "doBindService: 绑定WebSocket服务。。");
        Intent bindIntent = new Intent(this, MyWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }


    /**
     * 设置全局缓存策略
     * @param context
     */
    public void initRxHttpCache(Context context) {
        RxHttp.init(null);
        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
        File cacheDir = new File(context.getExternalCacheDir(), "NoteHttpCache");
        Log.d(TAG, "initRxHttpCache: " + cacheDir.getPath());
        //设置缓存存放地址（/sdcard/Android/data/com.bnuz.noteservice/cache/NoteHttpCache），最大缓存容量。
        RxHttpPlugins.setCache(cacheDir,10 * 1024 * 1024);
//        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE, 60 * 1000);
    }
    //初始化广播
    public void initReceiver(){
        mIntentFilter  = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mMyReceiver = new MyReceiver();
        registerReceiver(mMyReceiver,mIntentFilter);
    }

    private class NoteSocketReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message=intent.getStringExtra("message");
            SocketMessage chatMessage=new SocketMessage();
            chatMessage.setContent(message);
            chatMessage.setIsMeSend(0);
//            chatMessage.setIsRead(1);
            chatMessage.setTime(System.currentTimeMillis()+"");
//            chatMessageList.add(chatMessage);
//            initChatMsgListView();
        }
    }
    /**
     * 动态注册广播
     */
    private void doRegisterReceiver() {
        mNoteSocketReceiver = new NoteSocketReceiver();
        IntentFilter filter = new IntentFilter("ccn.edu.bnuz.notes.websocket");
        registerReceiver(mNoteSocketReceiver, filter);
    }

    /*------------------------------------------------------------------------------通知相关------------------------------------------------------------------------*/
    /**
     * 检测是否开启通知
     *
     * @param context
     */
    private void checkNotification(final Context context) {
        if (!isNotificationEnabled(context)) {
            new AlertDialog.Builder(context).setTitle("温馨提示")
                    .setMessage("你还未开启系统通知，将影响消息的接收，要去开启吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setNotification(context);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
    }
    /**
     * 如果没有开启通知，跳转至设置界面
     *
     * @param context
     */
    private void setNotification(Context context) {
        Intent localIntent = new Intent();
        //直接跳转到应用通知设置的代码：
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(localIntent);
    }

    /**
     * 获取通知权限,监测是否开启了系统通知
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public void initRxHttpCache(Context context) {
//        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
//        File cacheDir = new File(context.getExternalCacheDir(), "NoteHttpCache");
////        File cacheDir = new File("sdcard/download/", "NoteHttpCache");
//
//        Log.d(TAG, "initRxHttpCache: " + cacheDir.getPath());
//        //设置最大缓存为10M，缓存有效时长为60秒
////        RxHttpPlugins.setCache(cacheDir,10 * 1024 * 1024);
//        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE, 60 * 1000);
//    }





}

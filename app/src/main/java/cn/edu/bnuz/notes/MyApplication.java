package cn.edu.bnuz.notes;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import cn.edu.bnuz.notes.cache.NoteCache;

import org.litepal.LitePal;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.edu.bnuz.notes.impl.TokenControllerImpl;
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
import rxhttp.RxHttp;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;

public class MyApplication extends Application {
    private static final String TAG = "MainActivity";
    private Boolean mIsbind;
    private IntentFilter mIntentFilter;
    public static MyReceiver mMyReceiver;

    //绑定相关(test，进入app绑定所有操作)
    //NoteController

    private SQLiteDatabase mNoteDB;

    //初始化线程池
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
//        Log.d(TAG, "onCreate: litepal初始化-------------------------------");
        //初始化接收器（网络监测）
        initReceiver();
        //绑定服务
        doBindService();
        //初始化数据库
        initDatabese();
        //初始化WebSocket服务
//        initWebSocketService();
    }

    //初始化websocket连接
    public void initWebSocketService() {
        URI uri = URI.create("ws://*******");
        myWebSocketClient = new MyWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                //message就是接收到的消息
                Log.e("JWebSClientService", message);
            }
        };
        try {
            //连接
            myWebSocketClient.connectBlocking();
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
    private static ITagController mTagController;
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

    //websocket
    public MyWebSocketClient client;
    public MyWebSocketClientService.MyWebSocketClientBinder binder;
    public MyWebSocketClientService myWebSClientService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务与活动成功绑定
            Log.e("MainActivity", "服务与活动成功绑定");
            binder = (MyWebSocketClientService.MyWebSocketClientBinder) iBinder;
            myWebSClientService = binder.getService();
            client = myWebSClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //服务与活动断开
            Log.e("MainActivity", "服务与活动成功断开");
        }
    };


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

    public void initReceiver(){
        mIntentFilter  = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mMyReceiver = new MyReceiver();
        registerReceiver(mMyReceiver,mIntentFilter);
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

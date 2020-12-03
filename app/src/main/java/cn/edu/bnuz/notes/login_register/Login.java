package cn.edu.bnuz.notes.login_register;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.MainActivity;
import cn.edu.bnuz.notes.MyReceiver;
import cn.edu.bnuz.notes.R;
import cn.edu.bnuz.notes.utils.ParseHtml1;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;


import static cn.edu.bnuz.notes.MyApplication.mTokenController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;

public class Login extends Activity {
    @BindView(R.id.top_login)
    QMUITopBar mTopBar;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_register)
    TextView register;
    @BindView(R.id.tv_find_psw)
    TextView find_psw;
    @BindView(R.id.et_user_name)
    EditText user_name;
    @BindView(R.id.et_psw)
    EditText user_psw;
    private String TAG = "Login";

    private Boolean mIsbind;
    private IntentFilter mIntentFilter;
    public static MyReceiver mMyReceiver;
    private SQLiteDatabase mNoteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.login_ui, null);
        ButterKnife.bind(this, root);
        initView();
        setContentView(root);
    }

    private void initView() {
        mTopBar.setTitle("登陆");
        mTopBar.setTitleGravity(0);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Log.d(TAG, "run: user" + user_name.getText().toString() + " pass" + user_psw.getText().toString());
                        int result =  mTokenController.GetToken(user_name.getText().toString(),user_psw.getText().toString());
                        Log.d(TAG, "run: result" + result);
                        //1为用户名和密码正确
                        if (result == 1) {
                            Toast.makeText(Login.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(Login.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    }
                });
//                int result =  mTokenController.GetToken(user_name.getText().toString(),user_psw.getText().toString());
//                Log.d(TAG, "run: result" + result);
//                if (result == 1) {
//                    Intent i=new Intent(Login.this,MainActivity.class);
//                    startActivity(i);
//                }
//                else{
//                    Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
//                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,Register.class);
                startActivity(i);

            }
        });
        find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this, ParseHtml1.class);
//                startActivity(i);
            }
        });
    }

//    //TokenController
//    public static TokenConnection mTokenConnection;
//    public static ITokenController mTokenController;
//
//    //NoteController
//    public static NoteConnection mNoteConnection;
//    public static INoteController mNoteController;
//    private class NoteConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            Log.d(TAG, "onServiceConnected:.." + componentName);
//            mNoteController = (INoteController) iBinder;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            Log.d(TAG, "onServiceDisconnected:.." + componentName);
//        }
//    }
//
//    //FileController
//    public static FileConnection mFileConnection;
//    private static IFileController mFileController;
//    private class FileConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            Log.d(TAG, "onServiceConnected:.." + componentName);
//            mFileController = (IFileController) iBinder;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            Log.d(TAG, "onServiceDisconnected:.." + componentName);
//        }
//    }
//
//    //TagController
//    public static TagConnection mTagConnection;
//    private static ITagController mTagController;
//    private class TagConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            Log.d(TAG, "onServiceConnected:.." + componentName);
//            mTagController = (ITagController) iBinder;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            Log.d(TAG, "onServiceDisconnected:.." + componentName);
//        }
//    }
//
//    //ShareController
//    public static ShareConnection mShareConnection;
//    private static IShareController mShareController;
//    private class ShareConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            Log.d(TAG, "onServiceConnected:.." + componentName);
//            mShareController = (IShareController) iBinder;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            Log.d(TAG, "onServiceDisconnected:.." + componentName);
//        }
//    }
//
//    //FileTranController
//    public static FileTransConnection mFileTransConnection;
//    private static IFileTrans mFileTransController;
//    private class FileTransConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            Log.d(TAG, "onServiceConnected:.." + componentName);
//            mFileTransController = (IFileTrans) iBinder;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            Log.d(TAG, "onServiceDisconnected:.." + componentName);
//        }
//    }
//
//
//
//
//    private void initDatabese() {
//        mNoteDB = LitePal.getDatabase();
//    }
//
//    private void doBindService() {
//        //TokenControl
//        Log.d(TAG, "doBindService: 绑定TokenController服务。。");
//        Intent TokenControlIntent = new Intent();
//        TokenControlIntent.setAction("com.bnuz.noteservice.ACTION_TOKEN_CONTROLLER");
//        TokenControlIntent.setPackage(this.getPackageName());
//        TokenControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        mTokenConnection = new TokenConnection();
//        mIsbind = bindService(TokenControlIntent, mTokenConnection, BIND_AUTO_CREATE);
//        Log.d(TAG, "doBindService:*/*******************NoteController ");
//        //NoteControl
//        Log.d(TAG, "doBindService: 绑定NoteController服务。。");
//        Intent NoteControlIntent = new Intent();
//        NoteControlIntent.setAction("com.bnuz.noteservice.ACTION_NOTE_CONTROLLER");
//        NoteControlIntent.setPackage(this.getPackageName());
//        NoteControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        mNoteConnection = new NoteConnection();
//        mIsbind = bindService(NoteControlIntent, mNoteConnection, BIND_AUTO_CREATE);
//        Log.d(TAG, "doBindService:*/*******************NoteController ");
//        //TagControl
//        Log.d(TAG, "doBindService: 绑定TagController服务。。");
//        Intent TagControlIntent = new Intent();
//        TagControlIntent.setAction("com.bnuz.noteservice.ACTION_TAG_CONTROLLER");
//        TagControlIntent.setPackage(this.getPackageName());
//        TagControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        mTagConnection = new TagConnection();
//        mIsbind = bindService(TagControlIntent, mTagConnection, BIND_AUTO_CREATE);
//        Log.d(TAG, "doBindService:*/*******************TagController ");
//        //ShareControl
//        Log.d(TAG, "doBindService: 绑定ShareController服务。。");
//        Intent ShareControlIntent = new Intent();
//        ShareControlIntent.setAction("com.bnuz.noteservice.ACTION_SHARE_CONTROLLER");
//        ShareControlIntent.setPackage(this.getPackageName());
//        ShareControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        mShareConnection = new ShareConnection();
//        mIsbind = bindService(ShareControlIntent, mShareConnection, BIND_AUTO_CREATE);
//        Log.d(TAG, "doBindService:*/*******************ShareController ");
//        //FileControl
//        Log.d(TAG, "doBindService: 绑定FileController服务。。");
//        Intent FileControlIntent = new Intent();
//        FileControlIntent.setAction("com.bnuz.noteservice.ACTION_FILE_CONTROLLER");
//        FileControlIntent.setPackage(this.getPackageName());
//        FileControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        mFileConnection = new FileConnection();
//        mIsbind = bindService(FileControlIntent, mFileConnection, BIND_AUTO_CREATE);
//        Log.d(TAG, "doBindService:*/*******************FileController ");
//        //FileTranControl
//        Log.d(TAG, "doBindService: 绑定FileTransController服务。。");
//        Intent FileTranControlIntent = new Intent();
//        FileTranControlIntent.setAction("com.bnuz.noteservice.ACTION_FILETRANS_CONTROLLER");
//        FileTranControlIntent.setPackage(this.getPackageName());
//        FileTranControlIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        mFileTransConnection = new FileTransConnection();
//        mIsbind = bindService(FileTranControlIntent, mFileTransConnection, BIND_AUTO_CREATE);
//        Log.d(TAG, "doBindService:*/*******************FileTransController ");
//
//    }
//
//
//    /**
//     * 设置全局缓存策略
//     * @param context
//     */
//    public void initRxHttpCache(Context context) {
//        RxHttp.init(null);
//        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
//        File cacheDir = new File(context.getExternalCacheDir(), "NoteHttpCache");
//        Log.d(TAG, "initRxHttpCache: " + cacheDir.getPath());
//        //设置缓存存放地址（/sdcard/Android/data/com.bnuz.noteservice/cache/NoteHttpCache），最大缓存容量。
//        RxHttpPlugins.setCache(cacheDir,10 * 1024 * 1024);
////        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE, 60 * 1000);
//    }
//
//    public void initReceiver(){
//        mIntentFilter  = new IntentFilter();
//        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        mMyReceiver = new MyReceiver();
//        registerReceiver(mMyReceiver,mIntentFilter);
//    }
//
////    public void initRxHttpCache(Context context) {
////        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
////        File cacheDir = new File(context.getExternalCacheDir(), "NoteHttpCache");
//////        File cacheDir = new File("sdcard/download/", "NoteHttpCache");
////
////        Log.d(TAG, "initRxHttpCache: " + cacheDir.getPath());
////        //设置最大缓存为10M，缓存有效时长为60秒
//////        RxHttpPlugins.setCache(cacheDir,10 * 1024 * 1024);
////        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE, 60 * 1000);
////    }
//
//
//    //    private TokenControllerImpl mTokenController;
//    private class TokenConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            Log.d(TAG, "onServiceConnected:.." + componentName);
//            mTokenController = (ITokenController) iBinder;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            Log.d(TAG, "onServiceDisconnected:.." + componentName);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();

//    }
}

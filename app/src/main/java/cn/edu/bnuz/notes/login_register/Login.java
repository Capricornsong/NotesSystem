package cn.edu.bnuz.notes.login_register;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
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
import cn.edu.bnuz.notes.ntwpojo.TreeRelationRD;
import cn.edu.bnuz.notes.user_center.UserCenter;
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
    private Handler mHandler = new Handler();
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
//                        Looper.prepare();
                        Log.d(TAG, "run: user" + user_name.getText().toString() + " pass" + user_psw.getText().toString());
                        int result =  mTokenController.GetToken(user_name.getText().toString(),user_psw.getText().toString());
                        Log.d(TAG, "run: result" + result);
                        //1为用户名和密码正确
                        if (result == 1) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                }
                            });

                            Intent i=new Intent(Login.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);

            }
        });
        find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(Login.this, ParseHtml1.class);
//                startActivity(i);
            }
        });
    }
}

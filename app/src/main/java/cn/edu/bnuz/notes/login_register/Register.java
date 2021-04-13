package cn.edu.bnuz.notes.login_register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.MainActivity;
import cn.edu.bnuz.notes.R;
import com.afollestad.materialdialogs.MaterialDialog;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import cn.edu.bnuz.notes.note_edit.Notes_Edit;
import jp.wasabeef.richeditor.RichEditor;

import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.mTokenController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;

public class Register extends Activity {
    int result;
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.edittext_name)
    EditText user_name;
    @BindView(R.id.edittext_email)
    EditText user_email;
    @BindView(R.id.edittext_password)
    EditText user_password;
    @BindView(R.id.edittext_verifycode)
    EditText email_verifycode;
    @BindView(R.id.button_getverifycode)
    Button btn_verifycode;
    @BindView(R.id.button_register)
    Button btn_register;
    private Handler mHandler = new Handler();

    final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.register_ui, null);
        ButterKnife.bind(this, root);
        //初始化状态栏
        initTopBar();
        setContentView(root);
    }


    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("注册账号");
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 注册时，需先对username进行判断，即先执行UsernameCheck()方法，若其返回值为200，
                 * 则调用Register()方法进行注册
                 */
                threadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
//                        Looper.prepare();

                        if (mTokenController.UsernameCheck(user_name.getText().toString()) != 200){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register.this,"用户已被使用",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else {
                            int  result = mTokenController.Register(user_name.getText().toString(),user_email.getText().toString(),email_verifycode.getText().toString(),user_password.getText().toString());
                            Log.d(TAG, "result: " + result);
                            if (result==200) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Register.this,"注册成功",Toast.LENGTH_LONG).show();
                                    }
                                });

                                Intent i=new Intent(Register.this,Login.class);
                                startActivity(i);
                            }
                            else if (result == 10085) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Register.this,"验证码错误",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                            else{
                                //其他情况
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Register.this,"注册失败",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }
//                        Looper.loop();
                    }
                });
            }
        });
        //发送验证码
        btn_verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Register.this,user_email.getText().toString(),Toast.LENGTH_LONG).show();
                threadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        int code = mTokenController.EmailCheck(user_email.getText().toString());
                        if (code == 200) {
                            if (mTokenController.SendEmailCode(user_email.getText().toString()) == 200) {
                                Toast.makeText(Register.this,"已发送验证码",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Register.this,"发送验证码失败",Toast.LENGTH_LONG).show();
                            }
                        }
                        //邮箱已被注册
                        else if(code == 10002){
                            Toast.makeText(Register.this,"该邮箱已被注册",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(Register.this,"发送验证码失败",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                });
            }
        });
    }

}

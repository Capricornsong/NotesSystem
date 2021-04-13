package cn.edu.bnuz.notes.user_center;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.R;

import static cn.edu.bnuz.notes.MyApplication.mTokenController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;

public class edit_password extends Activity {
    @BindView(R.id.top_bar_password)
    QMUITopBar NotesTopBar;
    @BindView(R.id.user_oldpassword)
    EditText old_passwword;
    @BindView(R.id.user_newpassword)
    EditText new_passwword;
    @BindView(R.id.change_password)
    QMUIRoundButton change_password;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = LayoutInflater.from(this).inflate(R.layout.edit_password_ui, null);
        ButterKnife.bind(this, root);
        setContentView(root);
        QMUIStatusBarHelper.translucent(this);
        initView();
    }

    private void initView() {
        //返回按钮
        NotesTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
            }
        });

        //修改密码
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  newpassword=new_passwword.getText().toString();
                String  oldpassword=old_passwword.getText().toString();

                threadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Boolean result = mTokenController.UpdatePassword(oldpassword,newpassword);
                        if (result == true) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(edit_password.this,"修改成功",Toast.LENGTH_LONG).show();

                                }
                            });
                            finish();
                        }
                        else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(edit_password.this,"修改失败，请重试",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                });
            }
        });
    }
}
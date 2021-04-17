package cn.edu.bnuz.notes.user_center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.concurrent.CountDownLatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.R;
import cn.edu.bnuz.notes.note_edit.Notes_Edit;
import cn.edu.bnuz.notes.ntwpojo.UserInfo;

import static cn.edu.bnuz.notes.MyApplication.mTokenController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static org.litepal.LitePalApplication.getContext;

public class UserCenter extends Activity {
    @BindView(R.id.user_center_name)
    TextView userCenterName;
    @BindView(R.id.user_center_id)
    TextView userCenterId;
    @BindView(R.id.user_center_gmtCreate)
    TextView userCenterGmtCreate;
    @BindView(R.id.top_bar_Center)
    QMUITopBar NotesTopBar;
    @BindView(R.id.button_editid)
    QMUIRoundButton edit_id;
    @BindView(R.id.button_editpassword)
    QMUIRoundButton edit_password;
    private String TAG = "UserCenter";
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = LayoutInflater.from(this).inflate(R.layout.user_center_ui, null);
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
        edit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(UserCenter.this);
                builder.setTitle("用户名修改")
                        .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                        .setPlaceholder("在此输入您要修改的用户名")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }

                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                CharSequence userid=builder.getEditText().getText();
                                threadExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Boolean result = mTokenController.UpdateUsername(userid.toString());
                                        if (result == true) {
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(UserCenter.this,"修改成功",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                        else {
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(UserCenter.this,"修改失败，请重试",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .create(R.style.QMUI_Dialog).show();
            }
        });
        edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCenter.this,edit_password.class);
                startActivity(intent);
            }
        });
        UserInfo mUserInfo = new UserInfo();
        //创建一个CountDownLatch类，构造入参线程数
        CountDownLatch countDownLatch = new CountDownLatch(1);
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 3......");
                mUserInfo.setData(mTokenController.GetUserInfo().getData());
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userCenterGmtCreate.setText(mUserInfo.getData().getGmtCreate());
        userCenterId.setText(mUserInfo.getData().getUserId().toString());
        userCenterName.setText(mUserInfo.getData().getUsername());
    }
}
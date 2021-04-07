package cn.edu.bnuz.notes.websocket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import java.util.concurrent.CountDownLatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.R;
import cn.edu.bnuz.notes.impl.TokenControllerImpl;
import cn.edu.bnuz.notes.ntwpojo.UserInfo;

import static cn.edu.bnuz.notes.MyApplication.mTokenController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;

public class UserCenter extends Activity {
    @BindView(R.id.user_center_name)
    TextView userCenterName;
    @BindView(R.id.user_center_id)
    TextView userCenterId;
    @BindView(R.id.user_center_gmtCreate)
    TextView userCenterGmtCreate;
    @BindView(R.id.top_bar_Center)
    QMUITopBar NotesTopBar;
    private String TAG = "UserCenter";
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
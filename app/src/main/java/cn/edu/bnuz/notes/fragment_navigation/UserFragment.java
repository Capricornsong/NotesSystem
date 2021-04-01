package cn.edu.bnuz.notes.fragment_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.bnuz.notes.R;
import cn.edu.bnuz.notes.login_register.Login;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

public class UserFragment  extends Fragment{
    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private Unbinder unbinder;
    @BindView(R.id.user_notes)
    QMUICommonListItemView userNotesCommonListView;
    @BindView(R.id.user_come)
    ImageButton userCome;
    @BindView(R.id.btn_quit)
    Button userQuit;
    public UserFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }
    private void initView() {
        userNotesCommonListView.setText("我的笔记");
        userNotesCommonListView.showNewTip(true);
        userCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在屏幕上显示提示 Toast
                new QMUIDialog.MessageDialogBuilder(getActivity())
                        .setTitle("个人中心测试")
                        .setMessage("这是个人中心")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        userQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在屏幕上显示提示 Toast
                new QMUIDialog.MessageDialogBuilder(getActivity())
                        .setTitle("退出系统")
                        .setMessage("您确定退出吗？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                Intent intent = new Intent(getContext(), Login.class);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public static UserFragment newInstance(String param1) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }
}

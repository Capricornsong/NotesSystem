package cn.edu.bnuz.notes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import cn.edu.bnuz.notes.login_register.Login;
import cn.edu.bnuz.notes.websocket.WebSocketClientService;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.fragment_navigation.NotesFragment;
import cn.edu.bnuz.notes.fragment_navigation.TreeFragment;
import cn.edu.bnuz.notes.fragment_navigation.UserFragment;
import cn.edu.bnuz.notes.interfaces.IFileController;
import cn.edu.bnuz.notes.interfaces.IFileTrans;
import cn.edu.bnuz.notes.interfaces.INoteController;
import cn.edu.bnuz.notes.interfaces.IShareController;
import cn.edu.bnuz.notes.interfaces.ITagController;
import cn.edu.bnuz.notes.interfaces.ITokenController;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import cn.edu.bnuz.notes.login_register.Login;
import cn.edu.bnuz.notes.websocket.WebSocketClientService;
import static cn.edu.bnuz.notes.MyApplication.myWebSocketClient;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static cn.edu.bnuz.notes.constants.destPath;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.client.WebSocketClient;
import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static cn.edu.bnuz.notes.MyApplication.mFileConnection;
import static cn.edu.bnuz.notes.MyApplication.mFileTransConnection;
import static cn.edu.bnuz.notes.MyApplication.mMyReceiver;
import static cn.edu.bnuz.notes.MyApplication.mNoteConnection;
import static cn.edu.bnuz.notes.MyApplication.mShareConnection;
import static cn.edu.bnuz.notes.MyApplication.mTagConnection;
import static cn.edu.bnuz.notes.MyApplication.mTokenConnection;

public class MainActivity extends FragmentActivity {
    @BindView(R.id.fragment_vp) ViewPager mViewPager;
    @BindView(R.id.tabs_rg) RadioGroup mTabRadioGroup;
    @BindView(R.id.top_bar) QMUITopBar mTopBar;
    @BindView(R.id.note_tab) RadioButton rbnote;
    @BindView(R.id.tree_tab) RadioButton rbtree;
    @BindView(R.id.user_tab) RadioButton rbuser;
    private static final String TAG = "MainActivity";
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private WebSocketClient client;
    private WebSocketClientService.WebSocketClientBinder binder;
    private WebSocketClientService WebSClientService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        ButterKnife.bind(this, root);
        //配置本地文件缓存路径
        destPath = this.getExternalCacheDir().getPath() + "/";
        initView();
        setContentView(root);
        //绑定webSocker服务
//        bindService();

    }
    //底部导航栏和相关函数
    private void initView() {
        mTopBar.setTitle("笔记");
        mTopBar.setTitleGravity(0);
        //底部导航栏菜单样式实现
        //设置底部导航栏图片大小和文字位置
        Drawable drawableNote = ContextCompat.getDrawable(this, R.drawable.tab_note_selector);
        drawableNote.setBounds(0, 0, 69, 69);
        rbnote.setCompoundDrawables(null, drawableNote, null, null);

        Drawable drawableTree = ContextCompat.getDrawable(this, R.drawable.tab_tree_selector);
        drawableTree.setBounds(0, 0, 69, 69);
        rbtree.setCompoundDrawables(null, drawableTree, null, null);

        Drawable drawableUser = ContextCompat.getDrawable(this, R.drawable.tab_user_selector);
        drawableUser.setBounds(0, 0, 69, 69);
        rbuser.setCompoundDrawables(null, drawableUser, null, null);

        //初始化底部标签
        mTabRadioGroup.check(R.id.tabs_rg);
        mFragments = new ArrayList<>(3);
        mFragments.add(NotesFragment.newInstance("111"));
        mFragments.add(TreeFragment.newInstance("222"));
        mFragments.add(UserFragment.newInstance("333"));
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent bindIntent = new Intent(MainActivity.this, WebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务与活动成功绑定
            Log.e("MainActivity", "服务与活动成功绑定");
            binder = (WebSocketClientService.WebSocketClientBinder) iBinder;
            WebSClientService = binder.getService();
            client = WebSClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //服务与活动断开
            Log.e("MainActivity", "服务与活动成功断开");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mFileTransConnection != null || mTokenConnection != null || mTokenConnection != null || mShareConnection != null || mTagConnection != null || mNoteConnection != null || mFileConnection != null) {
////            Log.d(TAG, "onDestroy: unbind service...");
//            unbindService(mFileTransConnection);
//            unbindService(mTokenConnection);
//            unbindService(mShareConnection);
//            unbindService(mTagConnection);
//            unbindService(mNoteConnection);
//            unbindService(mFileConnection);
//            mFileTransConnection = null;
//            mTokenConnection = null;
//            mShareConnection = null;
//            mTagConnection = null;
//            mNoteConnection = null;
//            mFileConnection = null;
////            mIsbind = false;
//        }
        //解绑接收者
        unregisterReceiver(mMyReceiver);
        threadExecutor.shutdown();
        //关闭Socket连接
        closeSocketConnect();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);

        //关闭线程池
        threadExecutor.shutdown();
    }

    private void closeSocketConnect() {
        try {
            if (null != myWebSocketClient) {
                myWebSocketClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myWebSocketClient = null;
        }
    }
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);
                    if(i==0){
                        mTopBar.removeAllRightViews();
                        mTopBar.removeAllLeftViews();
                        mTopBar.setTitle("笔记");
                        mTopBar.setTitleGravity(0);
                    }
                    if(i==1) {
                        mTopBar.setTitle("知识树");
                        mTopBar.setTitleGravity(0);
                        mTopBar.addRightImageButton(R.mipmap.tree_tag,R.layout.activity_main).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                                        .setTitle("Tag选择")
                                        .setMessage("这是Tag选择按钮")
                                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();

                                            }
                                        })
                                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .show();
                            }
                        });
                        mTopBar.addLeftImageButton(R.mipmap.tree_seek,R.layout.activity_main).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                                        .setTitle("知识树搜索")
                                        .setMessage("这是知识树搜索按钮")
                                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();

                                            }
                                        })
                                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .show();
                            }
                        });
                    }
                    if(i==2) {
                        mTopBar.removeAllRightViews();
                        mTopBar.removeAllLeftViews();
                        mTopBar.setTitle("用户中心");
                        mTopBar.setTitleGravity(0);
                    }
                    return;
                }
            }
        }
    };

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }
    //底部导航栏和相关函数

}

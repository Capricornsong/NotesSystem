package cn.edu.bnuz.notes;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import cn.edu.bnuz.notes.ntwpojo.NotesbyPageorTagIdRD;
import cn.edu.bnuz.notes.ntwpojo.TagListRD;
import cn.edu.bnuz.notes.ntwpojo.TagsFilter;
import cn.edu.bnuz.notes.pojo.Note;
import cn.edu.bnuz.notes.websocket.MyWebSocketClient;
import cn.edu.bnuz.notes.websocket.MyWebSocketClientService;
import cn.edu.bnuz.notes.websocket.WebSocketClientService;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.fragment_navigation.NotesFragment;
import cn.edu.bnuz.notes.fragment_navigation.TreeFragment;
import cn.edu.bnuz.notes.fragment_navigation.UserFragment;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import static cn.edu.bnuz.notes.MyApplication.mFileTransConnection;
import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.mShareController;
import static cn.edu.bnuz.notes.MyApplication.mTagController;
import static cn.edu.bnuz.notes.MyApplication.myWebSocketClient;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static cn.edu.bnuz.notes.constants.destPath;

import org.java_websocket.client.WebSocketClient;

import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static cn.edu.bnuz.notes.MyApplication.mMyReceiver;
import static cn.edu.bnuz.notes.pojo.Token.token;
import static cn.edu.bnuz.notes.pojo.Token.UserInf;
import static cn.edu.bnuz.notes.utils.util.NetCheck;
import static cn.edu.bnuz.notes.utils.util.gainUserId;
import static org.litepal.LitePalApplication.getContext;

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
//    private WebSocketClient client;
//    private WebSocketClientService.WebSocketClientBinder binder;
    private WebSocketClientService WebSClientService;
    private Note note;
    public static Intent WebSocketServiceintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        ButterKnife.bind(this, root);
        //配置本地文件缓存路径
        destPath = this.getExternalCacheDir().getPath() + "/";
        initView();
        gainUserId();
        if (NetCheck()) {
            Log.d(TAG, "开启websocket...........................");
            //启动websocket服务
            startMyWebSClientService();
        }
        setContentView(root);
        //绑定webSocker服务
    }

    /**
     * 启动服务（websocket客户端服务）
     */
    public void startMyWebSClientService() {
        WebSocketServiceintent = new Intent(this, MyWebSocketClientService.class);
        startService(WebSocketServiceintent);
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

        if (NetCheck()) {
            mTopBar.removeAllRightViews();
        }

        //获取分享笔记按钮
        mTopBar.addLeftImageButton(R.mipmap.share,R.layout.notes_edit_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个CountDownLatch类，构造入参线程数
                CountDownLatch countDownLatch = new CountDownLatch(1);
                note = new Note();
                if (NetCheck()) {
                    final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(MainActivity.this);
                    builder.setTitle("打开分享笔记")
                            .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                            .setPlaceholder("在此输入笔记的分享id")
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
                                    CharSequence text = builder.getEditText().getText();
                                    if (text.length() == 0) {
                                        Toast.makeText(MainActivity.this, "请输入分享ID", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        threadExecutor.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                note = mShareController.GetShare(Long.parseLong(text.toString()));
                                                countDownLatch.countDown();
                                            }
                                        });
                                        //等待上方线程执行完
                                        try {
                                            countDownLatch.await();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        if (note == null) {
                                            Toast.makeText(MainActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else {
                                            Intent intent = new Intent(getContext(), notes_show.class);
                                            Bundle bundle = new Bundle();
                                            Log.d(TAG, "onClick: htmlcontent" + note.getHtmlContent());
                                            //把笔记数据传给下个activity
                                            bundle.putString("title",note.getTitle());
                                            bundle.putString("htmlcontent",note.getHtmlContent());
                                            bundle.putLong("noteid",note.getNoteId());
                                            intent.putExtras(bundle);
                                            //显示笔记详情
                                            startActivity(intent);
                                        }
                                    }
                                }
                            })
                            .create(R.style.QMUI_Dialog).show();
                }
                else{

                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mFileTransConnection != null || mTokenConnection != null || mTokenConnection != null || mShareConnection != null || mTagConnection != null || mNoteConnection != null || mFileConnection != null) {
////            Log.d(TAG, "onDestroy: unbind service...");
            unbindService(mFileTransConnection);
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
                        mTopBar.addLeftImageButton(R.mipmap.share,R.layout.notes_edit_ui).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //创建一个CountDownLatch类，构造入参线程数
                                CountDownLatch countDownLatch = new CountDownLatch(1);
                                note = new Note();
                                if (NetCheck()) {
                                    final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(MainActivity.this);
                                    builder.setTitle("打开分享笔记")
                                            .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                                            .setPlaceholder("在此输入笔记的分享id")
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
                                                    CharSequence text = builder.getEditText().getText();
                                                    if (text.length() == 0) {
                                                        Toast.makeText(MainActivity.this, "请输入分享ID", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        threadExecutor.execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                note = mShareController.GetShare(Long.parseLong(text.toString()));
                                                                countDownLatch.countDown();
                                                            }
                                                        });
                                                        //等待上方线程执行完
                                                        try {
                                                            countDownLatch.await();
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (note == null) {
                                                            Toast.makeText(MainActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                        }
                                                        else {
                                                            Intent intent = new Intent(getContext(), notes_show.class);
                                                            Bundle bundle = new Bundle();
                                                            //把笔记数据传给下个activity
                                                            bundle.putString("title",note.getTitle());
                                                            bundle.putString("htmlcontent",note.getHtmlContent());
                                                            bundle.putLong("noteid",note.getNoteId());
                                                            intent.putExtras(bundle);
                                                            //显示笔记详情
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }
                                            })
                                            .create(R.style.QMUI_Dialog).show();
                                }
                                else{

                                }
                            }
                        });
                        if (!NetCheck()) {
                            mTopBar.removeAllRightViews();
                        }
                    }
                    if(i==1) {
                        mTopBar.removeAllRightViews();
                        mTopBar.removeAllLeftViews();
                        mTopBar.setTitle("知识树");
                        mTopBar.setTitleGravity(0);
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

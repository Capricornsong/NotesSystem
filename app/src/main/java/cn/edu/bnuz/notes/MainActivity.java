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

import cn.edu.bnuz.notes.ntwpojo.TagListRD;
import cn.edu.bnuz.notes.ntwpojo.TagsFilter;
import cn.edu.bnuz.notes.pojo.Note;
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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.mShareController;
import static cn.edu.bnuz.notes.MyApplication.mTagController;
import static cn.edu.bnuz.notes.MyApplication.myWebSocketClient;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static cn.edu.bnuz.notes.constants.destPath;

import org.java_websocket.client.WebSocketClient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static cn.edu.bnuz.notes.MyApplication.mMyReceiver;
import static cn.edu.bnuz.notes.pojo.Token.token;
import static cn.edu.bnuz.notes.pojo.Token.UserInf;
import static cn.edu.bnuz.notes.utils.util.NetCheck;
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
    private WebSocketClient client;
    private WebSocketClientService.WebSocketClientBinder binder;
    private WebSocketClientService WebSClientService;
    private Note note;

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
        setContentView(root);
        //绑定webSocker服务
//        bindService();

    }

    private void gainUserId() {
//        Log.d(TAG, "gainUserId: token");
        Claims claims = null;
        try {
            claims = Jwts.parser()
                        .setSigningKey(("uaaNotes").getBytes("UTF-8"))
                    .parseClaimsJws(token.substring(7))
                    .getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //获取用户名
        String userinf = (String) claims.get("user_name");
        Gson gson = new Gson();
        UserInf = gson.fromJson(userinf,JsonObject.class);
        Log.d(TAG, "gainUserId: username" + UserInf.get("userId").toString());
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

        if (NetCheck()) {
            mTopBar.removeAllRightViews();
        }
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
        mTopBar.addRightImageButton(R.mipmap.tree_tag,R.layout.activity_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个CountDownLatch类，构造入参线程数
                CountDownLatch countDownLatch1 = new CountDownLatch(1);
                List<TagListRD.DataBean> tags = new ArrayList<TagListRD.DataBean>();
                threadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        tags.addAll(mTagController.GetTagsByUser());
                        countDownLatch1.countDown();
                    }
                });
                //等待上方线程执行完
                try {
                    countDownLatch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String[] items = new String[tags.size()];
                for(int i = 0; i < tags.size(); i++){
                    items[i] = tags.get(i).getTagName();
                }
                tags.addAll(mTagController.GetTagsByUser());

                final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(MainActivity.this)
                        .addItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                });
                builder.addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CountDownLatch countDownLatch2 = new CountDownLatch(1);
                        List<TagsFilter.DataBean.NotesBean> notesBeanList = new ArrayList<>();
                        if (builder.getCheckedItemIndexes().length != 0) {
                            ArrayList<String> selectedTags = new ArrayList<>();
                            for(int i = 0;i < builder.getCheckedItemIndexes().length;i++){
                                selectedTags.add(tags.get(builder.getCheckedItemIndexes()[i]).getTagName());
                            }
                            Log.d(TAG, "onClick: tags" + selectedTags.toString());
                            threadExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    TagsFilter.DataBean dataBean = mNoteController.GetNotesbyTags(selectedTags,1,50);
                                    Log.d(TAG, "run: datesize" + dataBean.getNotes().size());
                                    notesBeanList.addAll(dataBean.getNotes());
                                    countDownLatch2.countDown();
                                }
                            });
                            //等待上方线程执行完
                            try {
                                countDownLatch2.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "onClick: noteBeanList" + notesBeanList.toString());
                            NotesFragment notesFragment=new NotesFragment();
                            Bundle bundle=new Bundle();
                            bundle.putParcelableArrayList("notelist", (ArrayList<? extends Parcelable>) notesBeanList);
//                            bundle.putString("noteList",);
                            notesFragment.setArguments(bundle);
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "请选择", Toast.LENGTH_SHORT).show();
                        }

//                        String result = "你选择了 ";
//                        for (int i = 0; i < builder.getCheckedItemIndexes().length; i++) {
//                            result += "" + ++builder.getCheckedItemIndexes()[i] + "; ";
//                        }
//                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });
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
                        mTopBar.addRightImageButton(R.mipmap.tree_tag,R.layout.activity_main).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //创建一个CountDownLatch类，构造入参线程数
                                CountDownLatch countDownLatch1 = new CountDownLatch(1);
                                List<TagListRD.DataBean> tags = new ArrayList<TagListRD.DataBean>();
                                threadExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        tags.addAll(mTagController.GetTagsByUser());
                                        countDownLatch1.countDown();
                                    }
                                });
                                //等待上方线程执行完
                                try {
                                    countDownLatch1.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                String[] items = new String[tags.size()];
                                for(int i = 0; i < tags.size(); i++){
                                    items[i] = tags.get(i).getTagName();
                                }
                                tags.addAll(mTagController.GetTagsByUser());

                                final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(MainActivity.this)
                                        .addItems(items, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                builder.addAction("取消", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.addAction("确定", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        CountDownLatch countDownLatch2 = new CountDownLatch(1);
                                        List<TagsFilter.DataBean.NotesBean> notesBeanList = new ArrayList<>();
                                        if (builder.getCheckedItemIndexes().length != 0) {
                                            ArrayList<String> selectedTags = new ArrayList<>();
                                            for(int i = 0;i < builder.getCheckedItemIndexes().length;i++){
                                                selectedTags.add(tags.get(builder.getCheckedItemIndexes()[i]).getTagName());
                                            }
                                            Log.d(TAG, "onClick: tags" + selectedTags.toString());
                                            threadExecutor.execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    TagsFilter.DataBean dataBean = mNoteController.GetNotesbyTags(selectedTags,1,50);
                                                    Log.d(TAG, "run: datesize" + dataBean.getNotes().size());
                                                    notesBeanList.addAll(dataBean.getNotes());
                                                    countDownLatch2.countDown();
                                                }
                                            });
                                            //等待上方线程执行完
                                            try {
                                                countDownLatch2.await();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Log.d(TAG, "onClick: noteBeanList" + notesBeanList.toString());
                                            dialog.dismiss();
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "请选择", Toast.LENGTH_SHORT).show();
                                        }

//                        String result = "你选择了 ";
//                        for (int i = 0; i < builder.getCheckedItemIndexes().length; i++) {
//                            result += "" + ++builder.getCheckedItemIndexes()[i] + "; ";
//                        }
//                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

                                    }
                                });
                                builder.show();
                            }
                        });
                        if (NetCheck()) {
                            mTopBar.removeAllRightViews();
                        }
                    }
                    if(i==1) {
                        mTopBar.removeAllRightViews();
                        mTopBar.removeAllLeftViews();
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

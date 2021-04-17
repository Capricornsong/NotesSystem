package cn.edu.bnuz.notes.fragment_navigation;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.LitePal;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import butterknife.*;
import cn.edu.bnuz.notes.MainActivity;
import cn.edu.bnuz.notes.note_edit.Notes_Edit;
import cn.edu.bnuz.notes.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.notes_show;
import cn.edu.bnuz.notes.ntwpojo.NotesbyPageorTagIdRD;
import cn.edu.bnuz.notes.ntwpojo.TagListRD;
import cn.edu.bnuz.notes.pojo.Note;
import cn.edu.bnuz.notes.ntwpojo.GetFilesbyNoteId;

import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.mTagController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static cn.edu.bnuz.notes.pojo.Token.UserInf;
import static cn.edu.bnuz.notes.utils.util.NetCheck;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {
    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private Unbinder unbinder;
    @BindView(R.id.add_notes)
    ImageButton add_notes;
    @BindView(R.id.listview_contact)
    ListView mListView_contact;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.btn_tag)
    ImageButton btn_tag;
    private NotesAdapter mNotesAdapter;
    private String TAG ="NoteFragment";
    private int index = 0;      //用于记录点击item的序号，用于destory方法。

    private List<String> mFilespPath = new ArrayList<>();
    private Note mNote;
    private List<GetFilesbyNoteId.DataBean> mFileList = new ArrayList<>();
    private List<NotesbyPageorTagIdRD.NotesPkg.Notes> filterList = new ArrayList<>();
    private List<Note> mNotelist = new ArrayList<>();       //用于存储从本地获取的笔记
    List<NotesbyPageorTagIdRD.NotesPkg.Notes> notesBeanList = new ArrayList<>();        //用与存储根据标签筛选出来的笔记
    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment NotesFragment.
     */
    public static NotesFragment newInstance(String param1) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_notes,null);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(true));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
//        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initView();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        return rootView;
    }

    private void initView() {
        List<NotesbyPageorTagIdRD.NotesPkg.Notes> Lnotes=new ArrayList<>();
        mFilespPath.clear();
        mNotelist.clear();
        mNotelist.addAll(LitePal.where("isDelete == ? and userId = ?","0",UserInf.get("userId").toString()).find(Note.class));
        mNote = new Note();
//        if(yon==0)
//        {
//            Lnotes.clear();
//            notesBeanList.clear();
//        }
        if(!notesBeanList.isEmpty()) {
            Lnotes.addAll(notesBeanList);
            mNotesAdapter = new NotesAdapter(getContext(),R.layout.simple_list_item,Lnotes);
            mListView_contact.setAdapter(mNotesAdapter);
            notesBeanList.clear();
        }
        else
            {
            //判断是否有网络
            if (NetCheck()) {
                Toast.makeText(getContext(), "正在从云端获取笔记", Toast.LENGTH_SHORT).show();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //过去笔记列表
                        NotesbyPageorTagIdRD.NotesPkg notesPkg = mNoteController.GetNotesbyPage();
                        Lnotes.addAll(notesPkg.getNotes());
                    }
                });
                thread.start();
                try {
                    thread.join();      //待线程运行完再往下执行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mNotesAdapter = new NotesAdapter(getContext(),R.layout.simple_list_item,Lnotes);
                mListView_contact.setAdapter(mNotesAdapter);

            }
            else{
                Toast.makeText(getContext(), "无网络，已显示本地笔记", Toast.LENGTH_SHORT).show();
                for (Note note : mNotelist){
                    NotesbyPageorTagIdRD.NotesPkg.Notes newnote = new NotesbyPageorTagIdRD.NotesPkg.Notes(note.getTitle(),note.getContent(),note.getGmtModified(),note.getNoteId());
                    Lnotes.add(newnote);
                }
                mNotesAdapter = new NotesAdapter(getContext(),R.layout.simple_list_item,Lnotes);
                mListView_contact.setAdapter(mNotesAdapter);
            }
        }
        notesBeanList.clear();
        Log.d(TAG, "initView: -*----------------------");
        mListView_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Item 点击方法
             * @param adapterView
             * @param view
             * @param i    点击的item的序号
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: i:" + i);
                mFilespPath.clear();
                mFileList.clear();
                index = i;
                Intent intent = new Intent(getContext(), notes_show.class);
                //创建一个CountDownLatch类，构造入参线程数
                CountDownLatch countDownLatch1 = new CountDownLatch(1);

                Bundle bundle = new Bundle();
                Log.d(TAG, "run: Lnotes.size:" + Lnotes.size());

                /*
                 *有网络时，点击item显示的从云端的笔记，点击返回按钮时才会将查看过的那篇笔记缓存到本地。
                 * */
                if (NetCheck()){
                    threadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            mNote = mNoteController.GetNoteByNoteID(Lnotes.get(i).getNoteId());
                            countDownLatch1.countDown();
                        }
                    });
                    //等待上方线程执行完
                    try {
                        countDownLatch1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //把笔记数据传给下个activity
                    bundle.putString("title",mNote.getTitle());
                    bundle.putString("htmlcontent",mNote.getHtmlContent());
                    bundle.putString("content",mNote.getContent());
                    bundle.putString("gmt_modified",mNote.getGmtModified());
                    bundle.putLong("noteid",mNote.getNoteId());
                    bundle.putLong("userid",mNote.getUserId());
                    bundle.putInt("version",mNote.getVersion());
                    intent.putExtras(bundle);
                    //显示笔记详情
                    startActivity(intent);

                }
                //无网络时
                else{
                    bundle.putString("title",mNotelist.get(i).getTitle());
                    bundle.putString("htmlcontent",mNotelist.get(i).getHtmlContent());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
        mListView_contact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new QMUIDialog.MessageDialogBuilder(getContext())
                        .setTitle("删除笔记")
                        .setMessage("确认删除？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                threadExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mNoteController.DeleteNote(Lnotes.get(i).getNoteId());
                                        LitePal.deleteAll(Note.class, "noteId = ?" , String.valueOf(Lnotes.get(i).getNoteId()));

                                    }
                                });
                                dialog.dismiss();
                                Toast.makeText(getContext(), "已删除", Toast.LENGTH_SHORT).show();
                                refreshLayout.autoRefresh();//自动刷新
                            }
                        })
                        .show();
                return true;
            }
        });

        //新建笔记按钮
        add_notes.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent intent = new Intent(getContext(),Notes_Edit.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",mNote.getTitle());
                bundle.putString("htmlcontent",mNote.getHtmlContent());
                bundle.putLong("noteid",mNote.getNoteId());
                bundle.putLong("userid",mNote.getUserId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //TAG按钮
        if (NetCheck()) {
            btn_tag.setVisibility(View.VISIBLE);
        }
        else{
            btn_tag.setVisibility(View.INVISIBLE);
        }
        btn_tag.setOnClickListener(new View.OnClickListener() {
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

                final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(getContext())
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
                        if (builder.getCheckedItemIndexes().length != 0) {
                            ArrayList<String> selectedTags = new ArrayList<>();
                            for(int i = 0;i < builder.getCheckedItemIndexes().length;i++){
                                selectedTags.add(tags.get(builder.getCheckedItemIndexes()[i]).getTagName());
                            }
                            Log.d(TAG, "onClick: tags" + selectedTags.toString());
                            threadExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    NotesbyPageorTagIdRD.NotesPkg notesPkg = mNoteController.GetNotesbyTags(selectedTags,1,50);
                                    Log.d(TAG, "run: datesize" + notesPkg.getNotes().size());
                                    notesBeanList.clear();

                                    notesBeanList.addAll(notesPkg.getNotes());
                                    countDownLatch2.countDown();
                                }
                            });
                            //等待上方线程执行完
                            try {
                                countDownLatch2.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            initView();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getContext(), "请选择", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ..");
        super.onDestroyView();
        unbinder.unbind();
    }
}

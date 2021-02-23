package cn.edu.bnuz.notes.fragment_navigation;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.LitePal;

import java.io.File;
import androidx.fragment.app.Fragment;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

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
import cn.edu.bnuz.notes.pojo.Note;
import cn.edu.bnuz.notes.notes_show;
import cn.edu.bnuz.notes.ntwpojo.GetFilesbyNoteId;
import cn.edu.bnuz.notes.ntwpojo.NotesbyPageorTagIdRD;
import cn.edu.bnuz.notes.pojo.Note;
import cn.edu.bnuz.notes.utils.util;

import static cn.edu.bnuz.notes.MyApplication.mFileController;
import static cn.edu.bnuz.notes.MyApplication.mFileTransController;
import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static cn.edu.bnuz.notes.constants.destPath;
import static cn.edu.bnuz.notes.utils.util.NetCheck;
import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;

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
    private NotesAdapter mNotesAdapter;
    private List<NotesbyPageorTagIdRD.NotesPkg.Notes> Lnotes=new ArrayList<>();
    private String TAG ="NF";

    private final List<Note> mNotelist = LitePal.findAll(Note.class);       //用于存储从本地获取的笔记
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
        return rootView;
    }

    private void initView() {

//        threadExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                NotesbyPageorTagIdRD.NotesPkg notesPkg = mNoteController.GetNotesbyPage();
//                Log.d(TAG, "run: ///////////////////");
//                Lnotes.addAll(notesPkg.getNotes());
//            }
//        });

        //判断是否有网络
        if (NetCheck()) {
            Toast.makeText(getContext(), "正在从云端获取笔记", Toast.LENGTH_SHORT).show();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //过去笔记列表
                    NotesbyPageorTagIdRD.NotesPkg notesPkg = mNoteController.GetNotesbyPage();
                    Log.d(TAG, "run: ///////////////////");
                    Lnotes.addAll(notesPkg.getNotes());
                }
            });

            thread.start();
            try {
                thread.join();      //待线程运行完再往下执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getContext(), "无网络，已显示本地笔记", Toast.LENGTH_SHORT).show();
            for (Note note : mNotelist){
                NotesbyPageorTagIdRD.NotesPkg.Notes newnote = new NotesbyPageorTagIdRD.NotesPkg.Notes(note.getTitle(),note.getContent(),note.getGmt_modified());
                Lnotes.add(newnote);
            }
        }

        Log.d(TAG, "initView: -*----------------------");
        mNotesAdapter = new NotesAdapter(getContext(),R.layout.simple_list_item,Lnotes);
        mListView_contact.setAdapter(mNotesAdapter);
        mListView_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private List<String> mFilespPath = new ArrayList<>();
            private List<GetFilesbyNoteId.DataBean> mFileList = new ArrayList<>();
            private Note mNote;

            /**+
             * Item 点击方法
             * @param adapterView
             * @param view
             * @param i    点击的item的序号
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mFilespPath.clear();
                mFileList.clear();
                Intent intent = new Intent(getContext(), notes_show.class);
                //创建一个CountDownLatch类，构造入参线程数
                CountDownLatch countDownLatch = new CountDownLatch(1);
                // intent.putExtra("name", 123);
                //
                Bundle bundle = new Bundle();
                //有网络时
                if (NetCheck()){
                    threadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {

                            mNote = mNoteController.GetNoteByNoteID(Lnotes.get(i).getNoteId());     //通过noteid获取云端笔记

                            mFileList.addAll(mFileController.GetFilesbyNoteId(Lnotes.get(i).getNoteId()));

                            //循环下载文件，并将返回的每个文件的本地路径存入mFilespPath
                            for (GetFilesbyNoteId.DataBean file : mFileList){
                                //若本地已存在
                                if (util.fileIsExists(file.getFileName())) {
                                    //路径替换为本地路径
                                    mFilespPath.add(destPath + file.getFileName());
                                }
                                else{
                                    //下载文件，并替换为本地路径
                                    mFilespPath.add(mFileTransController.FileDownload(file.getFileId(),file.getFileName()));
                                }
                            }
                            countDownLatch.countDown();
                        }
                    });
                    //等待上方线程执行完
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //将最新获取到的数据更新/存入本地
                    Note note = new Note();
                    note.setTitle(mNote.getTitle());
                    note.setContent(mNote.getContent());
                    note.setNoteId(mNote.getNoteId());
                    note.setVersion(mNote.getVersion());
                    note.setUserId(mNote.getUserId());
                    note.setIsSyn(1);
                    for (String path : mFilespPath){
                        Log.d(TAG, "onItemClick: path:" + path);
                    }
                    String html = mNote.getHtmlContent();
                    Document doc = Jsoup.parseBodyFragment(html);
                    Element body = doc.body();
                    Elements img = doc.select("img");

                    //替换img表点中的src
                    for (int j = 0;j < mFilespPath.size();j++){
                        doc.select("img").get(j).attr("src",mFilespPath.get(j)).attr("alt",mFilespPath.get(j));
                    }
                    note.setHtmlContent(doc.body().toString());
                    List<Note> noteListInside = new ArrayList<>();
                    noteListInside.addAll(LitePal.select("noteId").where("noteId = ?",String.valueOf(mNote.getNoteId())).find(Note.class));
                    Log.d(TAG, "onItemClick: noteListInside.size()" + noteListInside.size());
                    if (noteListInside.size() == 0) {
                        note.save();
                    }
                    else{
                        note.updateAll("noteId = ?",String.valueOf(mNote.getNoteId()));
                    }
//                    Log.d(TAG, "onItemClick: String.valueOf(mNote.getNoteId()):" + String.valueOf(mNote.getNoteId()));
                    bundle.putString("title",note.getTitle());
                    bundle.putString("htmlcontent",note.getHtmlContent());
                    intent.putExtras(bundle);
                    //
                    startActivity(intent);
                }
                //无网络时
                else{

                    bundle.putString("title",mNotelist.get(i).getTitle());
                    bundle.putString("htmlcontent",mNotelist.get(i).getHtmlContent());
                    intent.putExtras(bundle);
                    //
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
                                dialog.dismiss();
                                Toast.makeText(getContext(), "确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                return true;
            }
        });

//        for (NotesbyPageorTagIdRD.NotesPkg.Notes note: Lnotes){
////            NotesbyPageorTagIdRD.NotesPkg.Notes notes=new NotesbyPageorTagIdRD.NotesPkg.Notes(note.getTitle(),note.getContent(),note.getGmtModified());
//            Log.d("shit", "initView: content" + note.getContent());
//        }
//        for (NotesbyPageorTagIdRD.NotesPkg.Notes note: Lnotes){
////            NotesbyPageorTagIdRD.NotesPkg.Notes notes=new NotesbyPageorTagIdRD.NotesPkg.Notes(note.getTitle(),note.getContent(),note.getGmtModified());
//            Log.d("fuck", "initView: content" + note.getContent());
//            Lnotes.add(note);
//        }
//        Lnotes.addAll()
        add_notes.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                startActivity(new Intent(getContext(), Notes_Edit.class));
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

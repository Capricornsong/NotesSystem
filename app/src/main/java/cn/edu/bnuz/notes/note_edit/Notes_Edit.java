package cn.edu.bnuz.notes.note_edit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.R;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.rxjava.rxlife.RxLife;
import android.net.Uri;
import android.database.Cursor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.bnuz.notes.login_register.Login;

import cn.edu.bnuz.notes.ntwpojo.NetNoteRD;
import cn.edu.bnuz.notes.pojo.Note;
import io.reactivex.Observable;
import jp.wasabeef.richeditor.RichEditor;
import rxhttp.RxHttp;
import rxhttp.wrapper.utils.LogUtil;


import static cn.edu.bnuz.notes.MyApplication.mFileTransController;
import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static org.litepal.LitePalApplication.getContext;

public class Notes_Edit extends Activity {
    private static final int IMAGE = 1;

    @BindView(R.id.richeditor)
    RichEditor mEditor;
    @BindView(R.id.preview)
    TextView mPreview;
    @BindView(R.id.action_undo)
    ImageButton undo;
    @BindView(R.id.editTitle)
    EditText editTitle;
    @BindView(R.id.action_redo)
    ImageButton redo;
    @BindView(R.id.action_bold)
    ImageButton bold;
    @BindView(R.id.action_italic)
    ImageButton italic;
    @BindView(R.id.action_subscript)
    ImageButton subscript;
    @BindView(R.id.action_superscript)
    ImageButton superscript;
    @BindView(R.id.action_strikethrough)
    ImageButton strikethrough;
    @BindView(R.id.action_underline)
    ImageButton underline;
    @BindView(R.id.action_heading1)
    ImageButton heading1;
    @BindView(R.id.action_heading2)
    ImageButton heading2;
    @BindView(R.id.action_heading3)
    ImageButton heading3;
    @BindView(R.id.action_heading4)
    ImageButton heading4;
    @BindView(R.id.action_heading5)
    ImageButton heading5;
    @BindView(R.id.action_heading6)
    ImageButton heading6;
    @BindView(R.id.action_txt_color)
    ImageButton txt_color;
    @BindView(R.id.action_bg_color)
    ImageButton bg_color;
    @BindView(R.id.action_indent)
    ImageButton indent;
    @BindView(R.id.action_outdent)
    ImageButton outdent;
    @BindView(R.id.action_align_left)
    ImageButton align_left;
    @BindView(R.id.action_align_center)
    ImageButton align_center;
    @BindView(R.id.action_align_right)
    ImageButton align_right;
    @BindView(R.id.action_insert_bullets)
    ImageButton insert_bullets;
    @BindView(R.id.action_insert_numbers)
    ImageButton insert_numbers;
    @BindView(R.id.action_blockquote)
    ImageButton blockquote;
    @BindView(R.id.action_insert_image)
    ImageButton insert_image;
    @BindView(R.id.action_insert_link)
    ImageButton insert_link;
    @BindView(R.id.action_insert_checkbox)
    ImageButton insert_checkbox;
    @BindView(R.id.action_insert_video)
    ImageButton insert_video;
    @BindView(R.id.action_insert_audio)
    ImageButton insert_audio;
    @BindView(R.id.top_bar_notes)
    QMUITopBar NotesTopBar;
    @BindView(R.id.create_notes)
    FloatingActionButton create_note;
    private String TAG = "Notes_Edit";
    private Handler mHandler = new Handler();
    int button_type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.notes_edit_ui, null);
        ButterKnife.bind(this, root);

        initView();
        setContentView(root);
    }
    private void initView() {
        Intent intent = getIntent();
        String htmlcontent = intent.getStringExtra("htmlcontent");
        String title = intent.getStringExtra("title");
        Long noteid = intent.getLongExtra("noteid",0L);
        Long userid = intent.getLongExtra("userid",0L);

        //返回按钮
        NotesTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
            }
        });
        //完成按钮（创建新笔记）
        create_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(Notes_Edit.this);
                builder.setTitle("TAG")
                        .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                        .setPlaceholder("在此输入您的TAG")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("暂不添加TAG", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
//                              String html1 = "111<img src=\"/storage/emulated/0/Download/create.png\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\"><img src=\"/storage/emulated/0/Download/create.png\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\">";
                                Toast.makeText(Notes_Edit.this,"正在上传至云端",Toast.LENGTH_SHORT).show();
                                //获取htmlcontent
                                String html = mPreview.getText().toString();
                                Document doc = Jsoup.parseBodyFragment(html);
                                Element body = doc.body();

                                Note note = new Note();
                                note.setTitle(editTitle.getText().toString());
                                note.setContent(body.text());
                                note.setHtmlContent(html);
                                note.setUserId(userid);
                                note.setVersion(1);
                                //上传新建文件
                                uploadNewNote(note,doc);
                                finish();
                            }

                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                Toast.makeText(Notes_Edit.this,"正在上传至云端",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                CharSequence text = builder.getEditText().getText();
                                //获取htmlcontent
                                String html = mPreview.getText().toString();
                                Document doc = Jsoup.parseBodyFragment(html);
                                Element body = doc.body();
                                Note note = new Note();
                                note.setTitle(editTitle.getText().toString());
                                note.setContent(body.text());
                                note.setHtmlContent(html);
                                note.setUserId(userid);
                                note.setVersion(1);
                                //上传新建文件
//                                uploadNewNote(note,doc);
                                if (text != null && text.length() > 0) {
                                    Toast.makeText(Notes_Edit.this, "您的TAG为: " + text, Toast.LENGTH_SHORT).show();
                                    uploadNewNotewithTag(note,doc,text.toString());
                                    dialog.dismiss();
                                    finish();
                                } else {
                                    Toast.makeText(Notes_Edit.this, "请填入TAG,如没有TAG请取消", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(R.style.QMUI_Dialog).show();

//                mEditor.setHtml("11111114<img src=\"/storage/emulated/0/Download/create.png\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\">");
            }
        });
        NotesTopBar.setTitle("编辑笔记");
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("请输入内容...");
        mEditor.setInputEnabled(true);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                mPreview.setText(text);
                Log.d(TAG, "onTextChange: " + mEditor.getHtml());
            }
        });
        //撤销
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.undo();
            }
        });
        //恢复撤销
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo();
            }
        });
        //加粗
        bold.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });
        //斜体
        italic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });
        //下角标
        subscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditor.getHtml() == null) {
                    return;
                }
                mEditor.setSubscript();
            }
        });
        //上角标
        superscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditor.getHtml() == null) {
                    return;
                }
                mEditor.setSuperscript();
            }
        });
        //删除线
        strikethrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });
        //下划线
        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });
        //设置1到6标题
        heading1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        heading2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        heading3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        heading4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        heading5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        heading6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });
        //设置字体颜色
        txt_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(Notes_Edit.this)
                        .title("选择字体颜色")
                        .items(R.array.color_items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                dialog.dismiss();
                                switch (which) {
                                    case 0://红
                                        mEditor.setTextColor(Color.RED);
                                        break;
                                    case 1://黄
                                        mEditor.setTextColor(Color.YELLOW);
                                        break;
                                    case 2://蓝
                                        mEditor.setTextColor(Color.GREEN);
                                        break;
                                    case 3://绿
                                        mEditor.setTextColor(Color.BLUE);
                                        break;
                                    case 4://黑
                                        mEditor.setTextColor(Color.BLACK);
                                        break;
                                }
                                return false;
                            }
                        }).show();
            }
        });
        //设置字体背景颜色
        bg_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(Notes_Edit.this)
                        .title("选择字体背景颜色")
                        .items(R.array.text_back_color_items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                dialog.dismiss();
                                switch (which) {
                                    case 0://红
                                        mEditor.setTextBackgroundColor(Color.RED);
                                        break;
                                    case 1://黄
                                        mEditor.setTextBackgroundColor(Color.YELLOW);
                                        break;
                                    case 2://蓝
                                        mEditor.setTextBackgroundColor(Color.GREEN);
                                        break;
                                    case 3://绿
                                        mEditor.setTextBackgroundColor(Color.BLUE);
                                        break;
                                    case 4://黑
                                        mEditor.setTextBackgroundColor(Color.BLACK);
                                        break;
                                    case 5://透明
                                        mEditor.setTextBackgroundColor(R.color.transparent);
                                        break;
                                }
                                return false;
                            }
                        }).show();

            }
        });
        //向右缩进
        indent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setIndent();
            }
        });
        //向左缩进
        outdent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setOutdent();
            }
        });
        //文章左对齐
        align_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });
        //文章居中
        align_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });
        //文章右对齐
        align_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });
        //无序排列
        insert_bullets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });
        //有序排列
        insert_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setNumbers();
            }
        });
        //引用
        blockquote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });
        //插入图片
        insert_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Notes_Edit.this, mPermissionList, 100);
                button_type=1;
            }
        });

        //插入音乐
        insert_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertAudio("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3");
            }
        });
        //插入视频
        insert_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                button_type=2;
            }
        });
        //插入链接
        insert_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(Notes_Edit.this)
                        .title("将输入连接地址")
                        .items("http://blog.csdn.net/huangxiaoguo1")
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                dialog.dismiss();
                                mEditor.insertLink("http://blog.csdn.net/huangxiaoguo1",
                                        "http://blog.csdn.net/huangxiaoguo1");
                                return false;
                            }
                        }).show();
            }
        });
        //选择框
        insert_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertTodo();
            }
        });

    }

    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 创建笔记时未添加标签
     * @param note
     * @param doc
     */
    public void uploadNewNote(Note note,Document doc){

        //准备过滤文件
        List<String> imagesUrl = new ArrayList<>();
        List<String> videosUrl = new ArrayList<>();
        Elements img = doc.select("img");
        Elements video = doc.select("video");
        int imgsize = img.size();
        int videosize = video.size();
        Log.d(TAG, "uploadNewNote: imgsize" + imgsize);
        Log.d(TAG, "uploadNewNote: videosize" + videosize);


        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
//                Looper.prepare();

                long result = mNoteController.CreateNote(note);
                Log.d(TAG, "run: 新建笔记结果码：:" + result);
                if (result > 9999999) {
                    note.setNoteId(result);

                    //准备待上传的图片list
                    if (imgsize != 0) {
                        for (Element s : img) {
                            Log.d(TAG, "src:" + s.attr("src"));
                            File file = new File(s.attr("src"));
                            String url = mFileTransController.FileUpload(file,result);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "run: filesuploadresult:" + url);
                            imagesUrl.add(url);
                        }
                        Log.d(TAG, "run: imagesUrl.size" + imagesUrl.size());
                        //得到urllist：imagesUrl，开始上传
                        for (int i = 0;i < imagesUrl.size();i++){
                            doc.select("img").get(i).attr("src",imagesUrl.get(i)).attr("alt",imagesUrl.get(i));
                        }
                    }
                    //准备待上传的视频list
                    if (videosize != 0) {
                        for (Element s : video) {
                            Log.d(TAG, "src:" + s.attr("src"));
                            File file = new File(s.attr("src"));
                            String url = mFileTransController.FileUpload(file,result);
                            Log.d(TAG, "run: filesuploadresult:" + url);
                            videosUrl.add(url);
                        }
                        //得到urllist：videosUrl，开始上传
                        for (int i = 0;i < videosUrl.size();i++){
                            doc.select("video").get(i).attr("src",videosUrl.get(i)).attr("alt",videosUrl.get(i));
                        }
                    }

                    if (!(videosize == 0 && imgsize == 0)) {
                        //得到替换完成的doc
                        Log.d(TAG, "run: 替换完成的doc" + doc);
                        note.setHtmlContent(doc.body().toString());
                        //修改云端htmlcontent
                        mNoteController.UpdateNoteHtmlContent(note);
                    }
//                                          mNoteController.UpdateNote(note);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Notes_Edit.this,"已上传至云端",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else if (result == 201){
                    //无网络时。具体笔记存储逻辑在CreateNote()方法中
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Notes_Edit.this,"上传失败，已保存在本地",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Notes_Edit.this,"保存失败",Toast.LENGTH_LONG).show();
                        }
                    });

                }



//                Looper.loop();
            }
        });
    }


    /**
     * 创建笔记时添加标签
     * @param note
     * @param doc
     * @param tagName
     */
    public void uploadNewNotewithTag(Note note,Document doc,String tagName){

        //准备过滤文件
        List<String> imagesUrl = new ArrayList<>();
        List<String> videosUrl = new ArrayList<>();
        Elements img = doc.select("img");
        Elements video = doc.select("video");
        int imgsize = img.size();
        int videosize = video.size();
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                long result = mNoteController.CreateNote(note);
                Log.d(TAG, "run: 新建笔记结果码：:" + result);
                if (result > 9999999) {
                    note.setNoteId(result);
                    //准备待上传的图片list
                    if (imgsize != 0) {
                        for (Element s : img) {
                            Log.d(TAG, "src:" + s.attr("src"));
                            File file = new File(s.attr("src"));
                            String url = mFileTransController.FileUpload(file,result);
                            Log.d(TAG, "run: filesuploadresult:" + url);
                            imagesUrl.add(url);
                        }
                        //得到urllist：imagesUrl，开始上传
                        for (int i = 0;i < imgsize;i++){
                            doc.select("img").get(i).attr("src",imagesUrl.get(i)).attr("alt",imagesUrl.get(i));
                        }
                    }
                    //准备待上传的视频list
                    if (videosize != 0) {
                        for (Element s : video) {
                            Log.d(TAG, "src:" + s.attr("src"));
                            File file = new File(s.attr("src"));
                            String url = mFileTransController.FileUpload(file,result);
                            Log.d(TAG, "run: filesuploadresult:" + url);
                            videosUrl.add(url);
                        }
                        //得到urllist：videosUrl，开始上传
                        for (int i = 0;i < imgsize;i++){
                            doc.select("video").get(i).attr("src",videosUrl.get(i)).attr("alt",videosUrl.get(i));
                        }
                    }

                    if (!(videosize == 0 && imgsize == 0)) {
                        //得到替换完成的doc
                        Log.d(TAG, "run: 替换完成的doc" + doc);
                        note.setHtmlContent(doc.body().toString());
                        //修改云端htmlcontent
                        mNoteController.UpdateNoteHtmlContent(note);
                    }
//                                          mNoteController.UpdateNote(note);
                    Toast.makeText(Notes_Edit.this,"已上传至云端",Toast.LENGTH_LONG).show();
                }
                else if (result == 201){
                    //无网络时。具体笔记存储逻辑在CreateNote()方法中
                    Toast.makeText(Notes_Edit.this,"上传失败，已保存在本地",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Notes_Edit.this,"保存失败",Toast.LENGTH_LONG).show();
                }

                //更新Tag
                Log.d(TAG, "run: mNoteController.AddTagToNote：" + mNoteController.AddTagToNote(tagName,result));

                Looper.loop();
            }
        });
    }
    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    1);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(button_type==1) {
                if (data != null) {
                    String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                    mEditor.insertImage(realPathFromUri, realPathFromUri + "\" style=\"max-width:75%");
                } else {
                    Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                }
            }
            if(button_type==2) {
                if (data != null) {
                    Uri uri = data.getData();
//                    String realVideoPathFromUri1=uri.toString();
                    String realVideoPathFromUri=RealPathFromUriUtils.getRealVideoPathFromUri(this,data.getData());
                    mEditor.insertVideo(realVideoPathFromUri+"\" style=\"max-width:75%");
                } else {
                    Toast.makeText(this, "视频损坏，请重新选择", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}

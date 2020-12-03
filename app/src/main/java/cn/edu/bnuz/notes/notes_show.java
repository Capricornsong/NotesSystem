package cn.edu.bnuz.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.qmuiteam.qmui.QMUILog;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bnuz.notes.note_edit.Notes_Edit;
import cn.edu.bnuz.notes.note_edit.RealPathFromUriUtils;
import cn.edu.bnuz.notes.pojo.Note;
import jp.wasabeef.richeditor.RichEditor;

import static cn.edu.bnuz.notes.MyApplication.mFileTransController;
import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static org.litepal.LitePalApplication.getContext;

public class  notes_show extends Activity {
    private static final int IMAGE = 1;

    @BindView(R.id.richeditor1)
    RichEditor mEditor;
    @BindView(R.id.preview1)
    TextView mPreview;
    @BindView(R.id.action_undo1)
    ImageButton undo;
    @BindView(R.id.action_redo1)
    ImageButton redo;
    @BindView(R.id.action_bold1)
    ImageButton bold;
    @BindView(R.id.action_italic1)
    ImageButton italic;
    @BindView(R.id.action_subscript1)
    ImageButton subscript;
    @BindView(R.id.action_superscript1)
    ImageButton superscript;
    @BindView(R.id.action_strikethrough1)
    ImageButton strikethrough;
    @BindView(R.id.action_underline1)
    ImageButton underline;
    @BindView(R.id.action_heading11)
    ImageButton heading1;
    @BindView(R.id.action_heading21)
    ImageButton heading2;
    @BindView(R.id.action_heading31)
    ImageButton heading3;
    @BindView(R.id.action_heading41)
    ImageButton heading4;
    @BindView(R.id.action_heading51)
    ImageButton heading5;
    @BindView(R.id.action_heading61)
    ImageButton heading6;
    @BindView(R.id.action_txt_color1)
    ImageButton txt_color;
    @BindView(R.id.action_bg_color1)
    ImageButton bg_color;
    @BindView(R.id.action_indent1)
    ImageButton indent;
    @BindView(R.id.action_outdent1)
    ImageButton outdent;
    @BindView(R.id.action_align_left1)
    ImageButton align_left;
    @BindView(R.id.action_align_center1)
    ImageButton align_center;
    @BindView(R.id.action_align_right1)
    ImageButton align_right;
    @BindView(R.id.action_insert_bullets1)
    ImageButton insert_bullets;
    @BindView(R.id.action_insert_numbers1)
    ImageButton insert_numbers;
    @BindView(R.id.action_blockquote1)
    ImageButton blockquote;
    @BindView(R.id.action_insert_image1)
    ImageButton insert_image;
    @BindView(R.id.action_insert_link1)
    ImageButton insert_link;
    @BindView(R.id.action_insert_checkbox1)
    ImageButton insert_checkbox;
    @BindView(R.id.action_insert_video1)
    ImageButton insert_video;
    @BindView(R.id.action_insert_audio1)
    ImageButton insert_audio;
    @BindView(R.id.top_bar_notes1)
    QMUITopBar NotesTopBar;
    @BindView(R.id.title1)
    EditText title_show;
    private String TAG = "Notes_Show";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_notes_show, null);
        ButterKnife.bind(this, root);
        initView();
        setContentView(root);
    }

    private void initView() {
        Intent intent = getIntent();
        String htmlcontent = intent.getStringExtra("htmlcontent");
        String title = intent.getStringExtra("title");
        mEditor.setHtml(htmlcontent);
        Log.d(TAG,"htmlcontent:"+  htmlcontent);
        title_show.setText(title);
        //返回按钮
        NotesTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
            }
        });
        //完成按钮
        NotesTopBar.addRightImageButton(R.drawable.create,R.layout.notes_edit_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(notes_show.this);
                builder.setTitle("TAG")
                        .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                        .setPlaceholder("在此输入您的TAG")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("暂不添加TAG", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
//                              String html1 = "111<img src=\"/storage/emulated/0/Download/create.png\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\"><img src=\"/storage/emulated/0/Download/create.png\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\">";
                                Toast.makeText(notes_show.this,"正在上传至云端",Toast.LENGTH_SHORT).show();
                                String html = mPreview.getText().toString();
                                Document doc = Jsoup.parseBodyFragment(html);
                                Element body = doc.body();
                                Note note = new Note();
                                note.setTitle("this is title");
//                              note.setHtmlContent(mPreview.getText().toString());
                                note.setContent(body.text());
                                note.setHtmlContent(html);
//                              note.setUserId(888888888);
//                              List<File> images = new ArrayList<>();
//                              String html = mPreview.getText().toString();
//                              Document doc = Jsoup.parseBodyFragment(html);
//                              Element body = doc.body();
                                List<String> imagesUrl = new ArrayList<>();
                                Elements img = doc.select("img");
                                int imgsize = img.size();
                                Log.d(TAG, "onClick: imgsize:" + imgsize);
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Looper.prepare();

                                        long result = mNoteController.CreateNote(note);
                                        Log.d(TAG, "run: 新建笔记结果码：:" + result);
                                        if (result > 9999999) {
                                            note.setNoteId(result);
                                            //准备待上传的文件list
                                            for (Element s : img) {
                                                Log.d(TAG, "src:" + s.attr("src"));
                                                File file = new File(s.attr("src"));
                                                String url = mFileTransController.FileUpload(file,result);
                                                Log.d(TAG, "run: filesuploadresult:" + url);
                                                imagesUrl.add(url);
                                            }
                                            //得到urllist：imagesUrl
                                            for (int i = 0;i < imgsize;i++){
                                                doc.select("img").get(i).attr("src",imagesUrl.get(i)).attr("alt",imagesUrl.get(i));
                                            }
                                            //得到替换完成的doc
                                            Log.d(TAG, "run: 替换完成的doc" + doc);
                                            note.setHtmlContent(doc.body().toString());
                                            //修改云端htmlcontent
                                            mNoteController.UpdateNoteHtmlContent(note);
//                                          mNoteController.UpdateNote(note);
                                            Toast.makeText(notes_show.this,"已上传至云端",Toast.LENGTH_LONG).show();
                                        }
                                        else if (result == 201){
                                            Toast.makeText(notes_show.this,"上传失败，已保存在本地",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(notes_show.this,"保存失败",Toast.LENGTH_LONG).show();
                                        }
                                        Looper.loop();
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();      //等待线程执行完
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                threadExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Looper.prepare();
                                        long result = mNoteController.CreateNote(note);

                                        //即上传成功，result为noteid
                                        if (result > 9999999) {
                                            //准备待上传的文件list
                                            String html = mPreview.getText().toString();
                                            Document doc = Jsoup.parseBodyFragment(html);
//                            Element body = doc.body();
                                            Elements img = doc.select("img");
                                            List<File> images = new ArrayList<>();
                                            for(Element s : img){
                                                File file = new File(s.attr("src"));
                                                images.add(file);
                                            }

                                            int size = doc.select("img").size();
                                            int i;
                                            if (size != 0) {
                                                for (i = 0; i < size; i++){
                                                    File file = new File(doc.select("img").get(i).attr("src"));
                                                    threadExecutor.execute(new Runnable() {
                                                        @Override
                                                        public void run() {
//                                            s.attr("src",mFileTransController.FileUpload(file,result));
//                                            Log.d(TAG, "run: filurl:" + mFileTransController.FileUpload(file,result));
//                                            doc.select("img").get(i).attr("src",mFileTransController.FileUpload(file,result));
                                                        }
                                                    });
//                                    Log.d(TAG, "onCreate: img:" + s.attr("src"));
//                                    doc.select("img").get(i).attr("src",doc2.select("img").get(i).attr("src"));
                                                }


                                                Log.d(TAG, "onCreate:" + doc);
//                                List<Element> img1=doc1.select("img");

//                                for(Element s : img){
//                                    File file = new File(s.attr("src"));
//                                    threadExecutor.execute(new Runnable() {
//                                        @Override
//                                        public void run() {
////                                            s.attr("src",mFileTransController.FileUpload(file,result));
//                                            Log.d(TAG, "run: filurl:" + mFileTransController.FileUpload(file,result));
//                                        }
//                                    });
//                                    Log.d(TAG, "onCreate: img:" + s.attr("src"));
//                                }
                                            }

                                            Toast.makeText(notes_show.this,"已上传至云端",Toast.LENGTH_LONG).show();
                                        }
                                        else if (result == 201){
                                            Toast.makeText(notes_show.this,"上传失败，已保存在本地",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(notes_show.this,"保存失败",Toast.LENGTH_LONG).show();
                                        }
                                        Looper.loop();
                                    }
                                });
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                CharSequence text = builder.getEditText().getText();
                                if (text != null && text.length() > 0) {
                                    Toast.makeText(notes_show.this, "您的TAG为: " + text, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(notes_show.this, "请填入TAG,如没有TAG请取消", Toast.LENGTH_SHORT).show();
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
                mEditor.focusEditor();
                mEditor.setBold();
            }
        });
        //斜体
        italic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setItalic();
            }
        });
        //下角标
        subscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
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
                mEditor.focusEditor();
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
                mEditor.focusEditor();
                mEditor.setStrikeThrough();
            }
        });
        //下划线
        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
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
                mEditor.focusEditor();
                new MaterialDialog.Builder(notes_show.this)
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
                mEditor.focusEditor();
                new MaterialDialog.Builder(notes_show.this)
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
                mEditor.focusEditor();
                mEditor.setIndent();
            }
        });
        //向左缩进
        outdent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setOutdent();
            }
        });
        //文章左对齐
        align_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
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
                mEditor.focusEditor();
                ActivityCompat.requestPermissions(notes_show.this, mPermissionList, 100);
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
                mEditor.insertVideo("https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_10MB.mp4", 360);
            }
        });
        //插入链接
        insert_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(notes_show.this)
                        .title("将输入连接地址")
                        .items("http://blog.csdn.net/huangxiaoguo1")
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                dialog.dismiss();
                                mEditor.focusEditor();
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
                mEditor.focusEditor();
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
            switch (requestCode) {
                case 1:
                    if (data != null) {
                        String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                        mEditor.insertImage(realPathFromUri, realPathFromUri + "\" style=\"max-width:75%");
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }
}
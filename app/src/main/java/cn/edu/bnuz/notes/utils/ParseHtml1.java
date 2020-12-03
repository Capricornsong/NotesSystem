package cn.edu.bnuz.notes.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.ripple.RippleUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.edu.bnuz.notes.R;
import cn.edu.bnuz.notes.pojo.Note;
import jp.wasabeef.richeditor.RichEditor;
import static cn.edu.bnuz.notes.MyApplication.mFileTransController;
import static cn.edu.bnuz.notes.MyApplication.mNoteController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;

public class ParseHtml1 extends AppCompatActivity {

    private String TAG = "ParseHtml";
    @BindView(R.id.richeditor)
    RichEditor mEditor;
    @BindView(R.id.preview)
    TextView mPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_edit_ui);

        mNoteController.GetNotesbyPage();

        String html1 = "111<img src=\"/storage/emulated/0/Download/create.png\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\"><img src=\"/storage/emulated/0/Download/create.png\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\">";
        String html2 = "111<img src=\"123\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\"><img src=\"465\" alt=\"/storage/emulated/0/Download/create.png\" style=\"max-width:75%\">";

        Document doc1 = Jsoup.parseBodyFragment(html1);
        Document doc2 = Jsoup.parseBodyFragment(html2);
        Element body1 = doc1.body();
        Element body2 = doc2.body();
//        doc1.select("img").get(1).attr("src","123").attr("alt","123");
//        Log.d(TAG, "onCreate: " + doc1.body());
        LitePal.deleteAll("note");
//        Log.d(TAG, "onCreate: 删除所有note中数据");
//        Note note = new Note();
//        note.setTitle("test");
//        note.setContent("test");
//        note.save();
//        doc1.charset();
//        Log.d(TAG, "onCreate: body:" + body1.data());
//        Elements img = doc1.select("img");
//        List<File> images = new ArrayList<>();
//        for(Element s : img){
//            File file = new File(s.attr("src"));
//            images.add(file);
//        }
//
//        for(File s : images){
//            Log.d(TAG, "onCreate: imgpath:" + s.getPath());
//        }

//        int size =  doc1.select("img").size();
//        for (int i = 0;i < size;i++){
//            doc1.select("img").get(i).attr("src",doc2.select("img").get(i).attr("src"));
//        }

//        Log.d(TAG, "onCreate:" + doc1);
//        List<Element> img1=doc1.select("img");
//        Log.d(TAG, "onCreate: body:" + body.toString());
//        Log.d(TAG, "onCreate: text:" + body.text());
//        Log.d(TAG, "onCreate: size" + img1.size());
//        int n = 1;
//        for(Element s : img1){
//            if (n == 1){
//                s.attr("src","123");
//                n = 2;
//            }
//            else{
//                s.attr("src","465");
//            }
//        }
//        threadExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "onCreate: path:" + mFileTransController.FileDownload(1316302282611687425L));
//            }
//        });


    }
}
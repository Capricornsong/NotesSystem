package cn.edu.bnuz.notes.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static cn.edu.bnuz.notes.MyApplication.mFileTransController;

public class ParseHtml {
    private static final String TAG = "ParseHtml";

    List<String> ImgUrlList(String html, long noteid){
        Document doc = Jsoup.parseBodyFragment(html);
        List<String> imagesUrl = new ArrayList<>();
        Elements img = doc.select("img");
        int imgsize = img.size();
        //准备待上传的文件list
        for (Element s : img) {
            Log.d(TAG, "src:" + s.attr("src"));
            File file = new File(s.attr("src"));
            String url = mFileTransController.FileUpload(file,noteid);
            Log.d(TAG, "run: filesuploadresult:" + url);
            imagesUrl.add(url);
        }
        //得到urllist：imagesUrl
        for (int i = 0;i < imgsize;i++){
            doc.select("img").get(i).attr("src",imagesUrl.get(i));
        }
        return imagesUrl;
    }
}

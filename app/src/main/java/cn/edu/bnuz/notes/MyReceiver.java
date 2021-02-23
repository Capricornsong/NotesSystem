package cn.edu.bnuz.notes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


import cn.edu.bnuz.notes.ntwpojo.NetNoteRD;
import cn.edu.bnuz.notes.pojo.Note;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;

import java.util.List;
import cn.edu.bnuz.notes.pojo.Token;
import rxhttp.RxHttp;

import static cn.edu.bnuz.notes.MyApplication.threadExecutor;

public class MyReceiver extends BroadcastReceiver {

    public static NetworkInfo mNetworkInfo;
    private String TAG = "MyReceiver";
    //    threadExecutor
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isAvailable() && Token.token.equals("")){
            Log.d(TAG, "onReceive: token:" + Token.token);
            Toast.makeText(context,"网络已连接",Toast.LENGTH_LONG).show();
        }
        else if (mNetworkInfo != null && mNetworkInfo.isAvailable() && !Token.token.equals("")){
            Toast.makeText(context,"网络已连接,正在尝试同步笔记",Toast.LENGTH_LONG).show();
            //上传本地数据库中未上传的笔记
            SynNote();
        }
        else{
            Toast.makeText(context,"网络已断开",Toast.LENGTH_LONG).show();
            //上传本地数据库中未上传的笔记

        }
    }

    public void SynNote(){
        //新创建在本地的笔记
        List<Note> notes = LitePal.where("isSyn = ?","0").find(Note.class);
        //批量上传为同步笔记
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                uploadNotes(notes);
            }
        });

//        for(Note note : notes){
//            Log.d(TAG, "IsSyn:" + note.getIsSyn() + " noteid:" + note.getNoteId());
//        }
    }

    public void uploadNotes(List<Note> notes){
        for(Note note : notes){
            //获取本地创建的未上传的新笔记
            Log.d(TAG, "IsSyn:" + note.getIsSyn() + " notetitle:" + note.getTitle());
            RxHttp.postJson("/note")
                    .setSync()
                    .add("content",note.getContent())
                    .add("htmlContent",note.getHtmlContent())
                    .add("title",note.getTitle())
                    .add("userId",note.getUserId())
                    .asClass(NetNoteRD.class)
                    .subscribe(c -> {
                        Log.d("NoteController", "code: " + c.getCode());
                        if (c.getCode() == 200){
                            Log.d(TAG, "CreateNote: 成功创建文本笔记！");
                            Log.d(TAG, "CreateNote: content --> " + c.getObject().getGmtModified().toString());

                            Note updateNews = new Note();
                            //表示已经同步
                            updateNews.setIsSyn(1);
                            updateNews.setUserId(c.getObject().getUserId());
                            updateNews.setNoteId(c.getObject().getNoteId());
                            updateNews.setGmt_modified(c.getObject().getGmtModified());

                            updateNews.setVersion(1);
                            updateNews.setGmt_create(c.getObject().getGmtModified());
                            //用上传成功的返回值更新本地的笔记
                            updateNews.updateAll("IsSyn = ? and content = ?","0",c.getObject().getHtmlContent());
                        }
                    },throwable -> {
                        Log.d(TAG + "uploadNotes", "创建文本笔记失败" + throwable);
                    });
        }
//        Note updateNews = new Note();
//        updateNews.setIsSyn(1);
//        updateNews.updateAll("IsSyn = ?","0");


    }


}

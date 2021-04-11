package cn.edu.bnuz.notes.impl;

import android.os.Binder;
import android.util.Log;

import cn.edu.bnuz.notes.interfaces.IFileController;
import cn.edu.bnuz.notes.ntwpojo.GetFilesbyNoteId;
import cn.edu.bnuz.notes.pojo.File;
import cn.edu.bnuz.notes.pojo.Token;

import java.util.ArrayList;
import java.util.List;

import rxhttp.RxHttp;
import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

import static rxhttp.RxHttp.setOnParamAssembly;

public class FileControllerImpl extends Binder implements IFileController {

    private static final String TAG = "FileControllerImpl";


    public FileControllerImpl(){
        //设置全局请求头
        setOnParamAssembly(new Function<Param<?>, Param<?>>() {
            @Override
            public Param apply(Param param) throws Exception {
                Method method = param.getMethod();
                return param.addHeader("Authorization", Token.token);
            }
        });
        Log.d(TAG, "NoteControllerImpl: 初始化。。");

    }
    @Override
    public List<GetFilesbyNoteId.DataBean> GetFilesbyNoteId(long noteid) {
        List<GetFilesbyNoteId.DataBean> filelist = new ArrayList<>();
        RxHttp.get("/file/" + noteid)
                .setSync()
                .asClass(GetFilesbyNoteId.class)
                .subscribe(fl -> {
                    Log.d(TAG, "GetFilesbyNoteId: " + fl.getCode());
                    if (fl.getCode() == 200){
                        Log.d(TAG, "GetFilesbyNoteId: 获取文件列表成功");
                        filelist.addAll(fl.getData());
                    }
                },throwable -> {
                    Log.d("GetFilesbyNoteId", "取文件列表失败" + throwable);
                });
        return filelist;
    }

    @Override
    public List<GetFilesbyNoteId.DataBean> GetFilesinPagebyNoteId(long noteid, int pageno, int pagesize) {
        List<GetFilesbyNoteId.DataBean> filelist = new ArrayList<>();
        RxHttp.get("/file/" + noteid + "/" + pageno + "/" + pagesize)
                .setSync()
                .asClass(GetFilesbyNoteId.class)
                .subscribe(fl -> {
                    Log.d(TAG, "GetFilesbyNoteId: " + fl.getCode());
                    if (fl.getCode() == 200){
                        Log.d(TAG, "GetFilesbyNoteId: 获取文件列表成功");
                        filelist.addAll(fl.getData());
                    }
                },throwable -> {
                    Log.d("GetFilesbyNoteId", "取文件列表失败" + throwable);
                });
        return filelist;
    }
}

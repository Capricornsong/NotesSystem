package cn.edu.bnuz.notes.impl;

import android.util.Log;
import android.os.Binder;
import cn.edu.bnuz.notes.interfaces.IFileTrans;
import cn.edu.bnuz.notes.ntwpojo.DeleteNoteTagShareEmailCheckUsernameCheckRD;
import cn.edu.bnuz.notes.ntwpojo.PostFile;
import cn.edu.bnuz.notes.pojo.Token;
import android.content.Context;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import static cn.edu.bnuz.notes.constants.destPath;
import rxhttp.RxHttp;
import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

import static rxhttp.RxHttp.setOnParamAssembly;

public class FileTransImpl extends Binder implements IFileTrans {
    AtomicInteger responsecode = new AtomicInteger();
    private static final String TAG = "FileTransImpl";
//    AtomicReference<String> path = null;
    //设置全局请求头
    public FileTransImpl(){
        setOnParamAssembly(new Function<Param<?>, Param<?>>() {
            @Override
            public Param apply(Param param) throws Exception {
                Method method = param.getMethod();
                return param.addHeader("Authorization", Token.token);
            }
        });
    }

    /**
     * 文件下载
     * @param fileid
     * @return
     */
    @Override
    public String FileDownload(long fileid,String FileName) {
        AtomicReference<String> path = new AtomicReference<String>();
        AtomicReference<String> path1 ;
        Log.d(TAG, "FileDownload: " + Thread.currentThread().toString());
        RxHttp.get("http://120.76.128.222:8004/file/" + fileid)
                .setSync()
                .asDownload(destPath + FileName,progress -> {
                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已下载的字节大小
                    long totalSize = progress.getTotalSize();     //要下载的总字节大小
                    Log.d(TAG, "FileDownload: curr --> " + progress.getProgress());
                    Log.d(TAG, "FileDownload: size --> " + currentSize);
                    Log.d(TAG, "FileDownload: totle --> " + totalSize);
                })
                .subscribe(s -> {                  //s为String类型，这里为文件存储路径
                    path.set(s);
                    Log.d(TAG, "FileDownload: 文件存储路径 --> " + s);
                }, throwable -> {
                    //下载失败
                    Log.d(TAG, "FileDownload: fail " + throwable);
                });
        return path.get();
    }

    @Override
    public int FileDelete(long fileid) {
        RxHttp.deleteForm("http://120.76.128.222:8004/file/" + fileid)
                .setSync()
                .asClass(DeleteNoteTagShareEmailCheckUsernameCheckRD.class)
                .subscribe(s -> {                  //s为String类型，这里为文件存储路径
                    Log.d(TAG, "FileDownload: 文件存储路径 --> " + s.getMsg());
                    responsecode.set(s.getCode());


                }, throwable -> {
                    //下载失败
                    Log.d(TAG, "FileDownload: fail " + throwable);
                });
        return responsecode.get();
    }


    /**
     * 单文件上传
     * @param file
     * @param noteid
     * @return
     */
    @Override
    public String FileUpload(File file, long noteid) {
//        AtomicLong fileid = new AtomicLong();
        AtomicReference<String> result = new AtomicReference<>();
        RxHttp.postForm("http://120.76.128.222:8004/file/" + noteid)
                .addFile("file", file)
                .setSync()
                .upload(progress -> {
                    //上传进度回调,0-100，仅在进度有更新时才会回调,最多回调101次，最后一次回调Http执行结果
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                    long totalSize = progress.getTotalSize();     //要上传的总字节大小
                    Log.d(TAG, "FileUpload: currentProgress"  +currentProgress);
                    Log.d(TAG, "FileUpload: currentSize"  +currentSize);
                    Log.d(TAG, "FileUpload: totalSize"  +totalSize);
                })
                .asClass(PostFile.class)
                .subscribe(s -> {
                    if (s.getCode() == 200){
                        Log.d(TAG, "FileUpload: uploaddone..");
                        result.set(s.getData().getFileUrl());
//                        fileid.set(s.getData().getFileId());
                    }
                    else
                        result.set(String.valueOf(s.getCode()));
//                        fileid.set((s.getCode()));

                    //上传成功
                }, throwable -> {
                    //上传失败
                });
        return result.get();

    }

    /**
     * 多文件上传
     * @param files
     * @param noteid
     * @return
     */
    @Override
    public int FilesUpload(List<File> files, long noteid) {
        AtomicInteger result = new AtomicInteger();
        RxHttp.postForm("http://120.76.128.222:8004/file/" + noteid)
                .addFile("file", files)
                .setSync()
//                .asClass(PostFile.class)
                .asString()
                .subscribe(s -> {
                    Log.d(TAG, "FilesUpload: 文件上传返回的string" + s);
//                    Log.d(TAG, "FilesUpload: return:" + s.getMsg());
//                    result.set(s.getCode());
                        //成功回调
                },throwable ->  {
                        //异常回调
                    Log.d(TAG, "FilesUpload: " + throwable);
                });
        return result.get();
    }

}

package cn.edu.bnuz.notes.impl;

import android.os.Binder;
import android.util.Log;

import cn.edu.bnuz.notes.interfaces.IShareController;
import cn.edu.bnuz.notes.ntwpojo.CreateShareRD;
import cn.edu.bnuz.notes.ntwpojo.DeleteNoteTagShareEmailCheckUsernameCheckRD;
import cn.edu.bnuz.notes.ntwpojo.GetSharebyIdRD;
import cn.edu.bnuz.notes.ntwpojo.GetSharesRD;
import cn.edu.bnuz.notes.pojo.Note;
import cn.edu.bnuz.notes.pojo.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import rxhttp.RxHttp;
import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

import static rxhttp.RxHttp.setOnParamAssembly;

public class ShareControllerImpl extends Binder implements IShareController {
    AtomicLong shareid = new AtomicLong();
    private static final String TAG = "ShareControllerImpl";
    AtomicInteger responseCode = new AtomicInteger();
    //初始化线程池
    private static final ThreadPoolExecutor threadExecutor = new ThreadPoolExecutor( 3, 4,  30,
            TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(10)
    );
    //设置全局请求头
    public ShareControllerImpl(){
        setOnParamAssembly(new Function<Param<?>, Param<?>>() {
            @Override
            public Param apply(Param param) throws Exception {
                Method method = param.getMethod();
                return param.addHeader("Authorization",Token.token);
            }
        });
        Log.d(TAG, "NoteControllerImpl: 初始化。。");
    }


    /**
     * 获取用户本人分享的全部笔记
     * @return List
     * {
     *    "share_id": 1294937509981065217,
     *     gmt_create": "2020-08-16T10:02:16.000+00:00",
     *    "note_id": 1294876087679000577,
     *    "expired_time": null,
     *    "title": "java学习",
     *    "content": null
     *      * },
     */
    @Override
    public List<GetSharesRD.DataBean> GetAllShares() {
        List<GetSharesRD.DataBean> notelist = new ArrayList<>();
        RxHttp.get("/share/")
                .setSync()
                .asClass(GetSharesRD.class)
                .subscribe(s -> {
                    Log.d("GetAllShare", "code: " + s.getCode());
                    if (s.getCode() == 200){
                        Log.d(TAG, "GetAllShare: 成功获取用户分享的所有笔记！");
                        notelist.addAll(s.getData());
                        Log.d(TAG, "run: size --> " + s.getData().size());
                    }
                },throwable -> {
                    Log.d("GetAllShare", "获取用户分享的所有笔记失败" + throwable);
                });
//        Log.d(TAG, "run: size --> " + notelist.size());
//        for (GetSharesRD.DataBean s : notelist)
//            Log.d(TAG, "GetShareNotesClick:  noteid -- > + " + s.getNote_id() + "shareid --> " + s.getShare_id() +  "title --> + " + s.getTitle() + "content --> " +s.getContent());
        return notelist;
    }

    /**
     * 创建分享
     *
     * 返回的shareid通过对象获取不到...
     * @param noteid
     * @return
     */
    @Override
    public long CreateShare(long noteid) {

        RxHttp.postJson("/share")
                .add("noteId",noteid)
                .setSync()
//                .asString()
                .asClass(CreateShareRD.class)
                .subscribe(s -> {
                    Log.d(TAG, "CreateShare: message --> " + s.getMsg());
                    Log.d(TAG, "CreateShare:shareid --> " + s.getDate());
                    shareid.set(s.getDate());
                    if (s.getCode() == 200){
                        Log.d(TAG, "CreateShare: 成功创建笔记分享！");
                    }
                },throwable -> {
                    Log.d("CreateShare", "创建文本笔记分享失败" + throwable);
                });
        Log.d(TAG, "CreateShare: shareid -->" + shareid.get());
        return shareid.get();
    }


    /**
     * 获取分享的笔记信息
     *
     * @param shareid
     * @return
     * 只包含以下三项
     *      "note_id": 1304708347300917250,
     *      "html_content": "this is html_content2",
     *      "title": "this  is title2"
     */
    @Override
    public Note GetShare(long shareid) {
        Note note = new Note();
        RxHttp.get("/share/" + shareid)
                .setSync()
                .asClass(GetSharebyIdRD.class)
                .subscribe(s -> {
                    Log.d("CreateShare", "code: " + s.getCode());
//                    responseCode.set(s.getCode());

                    if (s.getCode() == 200){
                        Log.d(TAG, "CreateShare: 成功创建笔记分享！");
                        note.setNoteId(s.getData().getNote_id());
                        note.setTitle(s.getData().getTitle());
                        note.setHtmlContent(s.getData().getHtml_content());
                    }

                },throwable -> {
                    Log.d("CreateShare", "创建文本笔记分享失败" + throwable);
                });

        return note;
    }

    //取消分享
    @Override
    public boolean DeleteShare(long shareid) {
        RxHttp.deleteForm("/share/" + shareid)
                .asClass(DeleteNoteTagShareEmailCheckUsernameCheckRD.class)
                .subscribe(s -> {
                    Log.d("DeleteShare", "code: " + s.getCode());
                    responseCode.set(s.getCode());
                    if (s.getCode() == 200){
                        Log.d(TAG, "DeleteShare: 成功取消笔记分享！");
                    }
                },throwable -> {
                    Log.d("DeleteShare", "取消笔记分享失败" + throwable);
                });
        if (responseCode.get() == 200)
            return true;
        else
            return false;
    }
}

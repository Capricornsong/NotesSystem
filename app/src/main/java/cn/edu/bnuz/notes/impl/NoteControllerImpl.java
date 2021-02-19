package cn.edu.bnuz.notes.impl;

import android.os.Binder;
import android.util.Log;

import cn.edu.bnuz.notes.interfaces.INoteController;
import cn.edu.bnuz.notes.ntwpojo.AddTagtoNoteRD;
import cn.edu.bnuz.notes.ntwpojo.DeleteNoteTagShareEmailCheckUsernameCheckRD;
import cn.edu.bnuz.notes.ntwpojo.NetNoteRD;
import cn.edu.bnuz.notes.ntwpojo.NoteSearchRD;
import cn.edu.bnuz.notes.ntwpojo.NotesbyPageorTagIdRD;
import cn.edu.bnuz.notes.pojo.Note;
import cn.edu.bnuz.notes.pojo.Token;
import static cn.edu.bnuz.notes.MyApplication.myWebSocketClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import rxhttp.RxHttp;
import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

import static cn.edu.bnuz.notes.utils.util.NetCheck;
import static rxhttp.RxHttp.setOnParamAssembly;

public class NoteControllerImpl extends Binder implements INoteController{
    private static final String TAG = "NoteControllerImpl";
    AtomicLong responseCode = new AtomicLong();
    //设置全局请求头
    public NoteControllerImpl(){
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
     * 创建笔记(有缓存)
     * @param note
     * @return Noteid即为成功创建,201未无网络时保存在本地。
     * {
     *     "code": 200,
     *     "msg": "SUCCESS",
     *     "data": {
     *         "noteId": 1308975053561036802,
     *         "userId": 888888888,
     *         "title": "this is title1",
     *         "htmlContent": "this is htmlContent1",
     *         "content": "this is content1",
     *         "version": null,
     *         "gmtModified": "2020-09-24T11:42:27.012"
     *     }
     * }
     */
    @Override
    public long CreateNote(Note note){
        Log.d(TAG, "CreateNote:...");
        Log.d(TAG, "CreateNote:  token --> " + Token.token);
        //先存入本地数据库
        Note newnote = new Note();
        newnote.setTitle(note.getTitle());
        newnote.setUserId(note.getUserId());
        newnote.setHtmlContent(note.getHtmlContent());
        newnote.setContent(note.getContent());

//        LitePal.deleteAll(Note.class);
        //判断网络状态
        if(NetCheck()){
            newnote.setVersion(1);
            RxHttp.postJson("/note")
                    .setSync()
                    .add("content",note.getContent())
                    .add("htmlContent",note.getHtmlContent())
                    .add("title",note.getTitle())
                    .asClass(NetNoteRD.class)
                    .subscribe(c -> {
                        Log.d("NoteController", "code: " + c.getCode());
                        if (c.getCode() == 200){
                            responseCode.set(c.getObject().getNoteId());
                            newnote.setGmt_create(c.getObject().getGmtModified());
                            newnote.setGmt_modified(c.getObject().getGmtModified());
                            newnote.setNoteId(c.getObject().getNoteId());
                            //设置标识符为1，即已同步到网络
                            newnote.setIsSyn(1);
                            newnote.save();
                            //websocket发送noteid到另一端
                            if (myWebSocketClient != null && myWebSocketClient.isOpen()) {
                                myWebSocketClient.send(String.valueOf(c.getObject().getNoteId()));
                            }
                            Log.d(TAG, "CreateNote: 成功创建文本笔记！");
                            Log.d(TAG, "CreateNote: content --> " + c.getObject().getGmtModified().toString());
                        }
                        else
                            responseCode.set(c.getCode());

                    },throwable -> {
                        Log.d(TAG + "CreateNote", "创建文本笔记失败" + throwable);
                    });

            return responseCode.get();
        }
        else{
            //先缓存基本信息到本地
            Log.d(TAG, "无网络，先存储在本地数据库中");

            newnote.setIsSyn(0);        //设置标识符为0，即未同步到网络
            newnote.setVersion(0);      //设置版本为0，即为新建的笔记且未上传，用于receiver中的同步逻辑
            newnote.save();
            return 201;
        }
        //发送请求
    }


    /**
     * 更新笔记(有缓存)
     * @param note
     * @return  200即为成功
     *          201即为等待网络重连时自动同步
     * {
     *     "code": 200,
     *     "msg": "SUCCESS",
     *     "data": {
     *         "noteId": 1308975053561036802,
     *         "userId": 888888888,
     *         "title": "this is updated title1",
     *         "htmlContent": "this is updated htmlContent1",
     *         "content": "this is updated content1",
     *         "version": 2,
     *         "gmtModified": "2020-09-24T11:44:28.753"
     *     }
     * }
     */
    @Override
    public long UpdateNote(Note note) {
        Log.d(TAG, "UpdateNote。。。");
        Note updateNews = new Note();
        updateNews.setNoteId(note.getNoteId());
        updateNews.setTitle(note.getTitle());
        updateNews.setContent(note.getContent());
        updateNews.setHtmlContent(note.getHtmlContent());
        updateNews.setUserId(note.getUserId());
        //用上传成功的返回值更新本地的笔记
        updateNews.updateAll("noteId = ?", String.valueOf(note.getNoteId()));
        if (NetCheck()){
            RxHttp.putJson("/note")
                    .setSync()
                    .add("content",note.getContent())
                    .add("htmlContent",note.getHtmlContent())
                    .add("title",note.getTitle())
                    .add("userId",note.getUserId())
                    .add("noteId",note.getNoteId())
                    .asClass(NetNoteRD.class)
                    .subscribe(c -> {
                        Log.d("NoteController", "code: " + c.getCode());
                        responseCode.set(c.getCode());
                        if (c.getCode() == 200){
                            updateNews.setIsSyn(1);
                            updateNews.setVersion(c.getObject().getVersion());
                            updateNews.setGmt_modified(c.getObject().getGmtModified());
                            updateNews.save();
                            //websocket发送noteid到另一端
                            if (myWebSocketClient != null && myWebSocketClient.isOpen()) {
                                myWebSocketClient.send(String.valueOf(c.getObject().getNoteId()));
                            }
                            Log.d(TAG, "UpdateNote: 成功修改文本笔记");
                            Log.d(TAG, "CreateNote: content --> " + c.getObject().getNoteId());
                        }
                        else if (c.getCode() == 3005){
                            System.out.println("修改异常，笔记不存在或已被删除！");
                        }
                        else{
                            Log.d(TAG, "UpdateNote: 失败");
                            updateNews.setIsSyn(0);
                            updateNews.save();
                        }
                    },throwable -> {
                        Log.d("UpdateNote", "修改笔记失败!" + throwable);

                    });
            return responseCode.get();
        }
        else{
            updateNews.setIsSyn(0);
            updateNews.save();
            return 201;
        }

    }

    /**
     * 修改云端笔记htmlcontent（用于HTML内容替换完成后）
     * @param note
     * @return
     */
    @Override
    public int UpdateNoteHtmlContent(Note note) {
        AtomicInteger ResultCode = new AtomicInteger();
        RxHttp.putJson("/note")
                .setSync()
                .add("content",note.getContent())
                .add("htmlContent",note.getHtmlContent())
                .add("title",note.getTitle())
                .add("userId",note.getUserId())
                .add("noteId",note.getNoteId())
                .asClass(NetNoteRD.class)
                .subscribe(c -> {
                    ResultCode.set(c.getCode());
                    Log.d("UpdateNoteHtmlContent", "code: " + c.getCode());
                    if (c.getCode() == 200){
                        Log.d(TAG, "UpdateNoteHtmlContent：成功修改文本笔记");
                        Log.d(TAG, "UpdateNoteHtmlContent：修改后的htmlcontent --> " + c.getObject().getHtmlContent());
                    }
                    else if (c.getCode() == 3005){
                        System.out.println("UpdateNoteHtmlContent：修改异常，笔记不存在或已被删除！");
                    }
                    else{
                        Log.d(TAG, "UpdateNoteHtmlContent：: 失败");
                    }
                },throwable -> {
                    Log.d("UpdateNote", "UpdateNoteHtmlContent：修改笔记失败!" + throwable);

                });
        return ResultCode.get();
    }

    /**
     * 删除笔记
     * @param noteid
     * @return 200即为成功删除
     *         201即为等待网络重连时自动同步
     */
    @Override
    public long DeleteNote(long noteid) {
        Note note = new Note();
        note.setDelete(1);
        note.updateAll("noteId = ?",Long.toString(noteid));
        if (NetCheck()){
            //准备需要删除的笔记(只需要note_id)
            RxHttp.deleteJson("/note/" + noteid)
//                .addHeader("Authorization",token)
                    .asClass(DeleteNoteTagShareEmailCheckUsernameCheckRD.class)
                    .subscribe(c -> {
                        Log.d("NoteController", "code: " + c.getCode());
                        responseCode.set(c.getCode());
                        if (c.getCode() == 200){
                            Log.d(TAG, "DeleteNote: 成功删除文本笔记！");
                        }
                        else {
                            Log.d(TAG, "DeleteNote: code --> " + c.getCode());
                            Log.d(TAG, "DeleteNote: message --> " + c.getMsg());
                        }
                    });
            return responseCode.get();
        }
        else{
            note.setIsSyn(0);
            note.updateAll("noteId = ?",Long.toString(noteid));
            return 201;
        }
    }

    /**
     * 根据笔记id查询用户的笔记详细信息
     * 点击笔记列表中的笔记会调用此方法
     * @param noteid
     * @return Note
     */
    @Override
    public Note GetNoteByNoteID(Long noteid) {
        Note note = new Note();
        RxHttp.get("/note/" + noteid)
                .setSync()
                .asClass(NetNoteRD.class)
                .subscribe(c -> {
                    Log.d("GetNoteByNoteID", "code: " + c.getCode());
                    responseCode.set(c.getCode());
                    if (c.getCode() == 200){
                        Log.d(TAG, "GetNoteByNoteID: 成功获得文本笔记！");
                        note.setContent(c.getObject().getContent());
                        note.setNoteId(c.getObject().getNoteId());
                        note.setHtmlContent(c.getObject().getHtmlContent());
                        note.setTitle(c.getObject().getTitle());
                        note.setUserId(c.getObject().getUserId());
                        note.setVersion(c.getObject().getVersion());
                    }
                    else {
                        Log.d(TAG, "GetNoteByNoteID: code --> " + c.getCode());
                        Log.d(TAG, "GetNoteByNoteID: message --> " + c.getMsg());
                    }
                });

        //        note.updateAll("noteId = ?",String.valueOf(note.getNoteId()));
        return note;
    }

    /**
     * 分页查询用户的笔记列表
     * pageNo    默认为1
     * pageSize  默认为200
     * @return  NotesbyPageorTagIdRD.DataBean:
     *      "data": {
     *         "notes": [
     *             {
     *                 "gmtModified": "2020-08-16 16:29:00",
     *                 "noteId": 1294914038374985729,
     *                 "title": "666",
     *                 "gmtCreate": "2020-08-16 16:29:00",
     *                 "content": "123456"
     *             },
     *             {
     *                 "gmtModified": "2020-08-16 17:06:18",
     *                 "noteId": 1294923428070989825,
     *                 "title": "666",
     *                 "gmtCreate": "2020-08-16 17:06:18",
     *                 "content": "123456"
     *             }
     *         ],
     *         "count": 2
     *     }
     */
    @Override
    public NotesbyPageorTagIdRD.NotesPkg GetNotesbyPage() {
        NotesbyPageorTagIdRD.NotesPkg notelist = new NotesbyPageorTagIdRD.NotesPkg();
        notelist.notes = new ArrayList<>();

        RxHttp.get("/note/page/1/200")
                .setSync() //指定在当前线程执行请求，即同步执行，
                .asClass(NotesbyPageorTagIdRD.class)
//                .asString()
                .subscribe(nl -> {
                    Log.d(TAG, "GetNotelistbyPage: " + nl.getCode());
                    if (nl.getCode() == 200){
                        notelist.notes.addAll(nl.getData().getNotes());
                    }
                    Log.d(TAG, "GetNotesbyPage: s:" + nl);
                },throwable -> {
                    Log.d("GetNotelistbyPage", "查询笔记列表失败" + throwable);
                });
        return notelist;
    }



    /**
     * 为笔记添加标签
     * @param TagName
     * @param noteid
     * @return
     *      200即为成功
     */
    @Override
    public long AddTagToNote(String TagName, long noteid) {
        RxHttp.postForm("/note/tag")
//                .addHeader("Authorization",token)
                .add("noteId",noteid)
                .add("tagName",TagName)
                .asClass(AddTagtoNoteRD.class)
                .subscribe(t -> {
                    responseCode.set(t.getCode());
                    Log.d(TAG, "code: " + t.getCode());
                    if (t.getCode() == 200){
                        Log.d(TAG, "AddTagToNote: 成功添加 Tag note_id:" + t.getData().getNoteId() + "  Tag_id:" + t.getData().getTagId());
                    }
                    else if (t.getCode() == 3005){
                        Log.d(TAG, "AddTagToNote: 添加Tag异常");
                    }
                },throwable -> {
                    Log.d(TAG, "AddTagToNote:添加Tag失败" + throwable);
                });

        return responseCode.get();
    }

    /**
     * 删除笔记中的某个标签
     * @param tagid
     * @param noteid
     * @return
     *      200即为成功
     */
    @Override
    public long DeleteTaginNote(long tagid, long noteid) {
        RxHttp.deleteForm("/note/" + tagid +  "/" + noteid)
                .asClass(DeleteNoteTagShareEmailCheckUsernameCheckRD.class)
                .subscribe(td -> {
                    Log.d("DeleteTaginNote", "code: " + td.getCode());
                    responseCode.set(td.getCode());
                    if (td.getCode() == 200){
                        Log.d(TAG, "DeleteTaginNote: state -->" + td.getData());
                    }
                    else if (td.getCode() == 3005){
                        Log.d(TAG, "DeleteTaginNote: 删除笔记的tag异常");
                    }
                },throwable -> {
                    Log.d("DeleteTaginNote", "删除笔记的tag失败" + throwable);
                });
        return responseCode.get();
    }

    /**
     * 通过标签id获取用户的分页笔记列表
     * @param tagid
     * @param pageNo
     * @param pageSize
     * @return List
     *      gmtModified : 2020-08-16T05:58:12.000+00:00
     *      note_id : 1294876087679000577
     *      title : java学习
     *      content : null
     */
    @Override
    public NotesbyPageorTagIdRD.NotesPkg GetNotesByTag(long tagid, int pageNo, int pageSize) {
        NotesbyPageorTagIdRD.NotesPkg notelist = new NotesbyPageorTagIdRD.NotesPkg();

        RxHttp.get("/note/tag/" + tagid + "/" + pageNo + "/" + pageSize )
                .setSync() //指定在当前线程执行请求，即同步执行，
                .asClass(NotesbyPageorTagIdRD.class)
                .subscribe(nl -> {
                    Log.d(TAG, "GetNotelistByTag: code --> " + nl.getCode());
                    if (nl.getCode() == 200){
                        notelist.getNotes().addAll(nl.getData().getNotes());
                    }
                },throwable -> {
                    Log.d("GetNotelistByTag", "通过tagid查询笔记列表失败" + throwable);
                });
        return notelist;
    }




    /**
     *通过多个标签名称获取用户的分页笔记列表
     * @param tags  标签list
     * @param pageNo    页数
     * @param pageSize  一页的大小
     * @return  查询到的notes，返回一个list
     *      "gmtModified": "2020-08-16T09:06:18.000+00:00",
     *      "noteId": 1294923428070989825,
     *      "title": "666",
     *      "content": "123456"
     */
    @Override
    public List<NotesbyPageorTagIdRD.NotesPkg> GetNotesbyTags(List<String> tags, int pageNo, int pageSize) {
//        RxHttp.postJson("/note/tags/" + pageNo + "/" + pageSize)
//                .setSync()
//                .add
        return null;

    }

    /**
     * 通过关键字查询笔记
     * @param key
     * @param pageNo
     * @param pageSize
     * @return List<NoteSearchRD.DataBean>
     *      deleted : 0
     *      noteId : 1294923553371627522
     *      title : 666
     *      userId : 888888888
     *      content : 123456
     *      htmlContent : 123456
     */
    @Override
    public List<NoteSearchRD.DataBean> GetNotesByKey(String key, int pageNo, int pageSize) {

        List<NoteSearchRD.DataBean> notelist = new ArrayList<>();
        RxHttp.postForm("/note/" + pageNo + "/" + pageSize)
                .add("keyword",key)
                .setSync()
                .asClass(NoteSearchRD.class)
                .subscribe(nl -> {
                    Log.d(TAG, "GetNotesByKey: " + nl.getCode());
                    if (nl.getCode() == 200){
                        notelist.addAll(nl.getData());
                    }
                },throwable -> {
                    Log.d("GetNotesByKey", "通过tagid查询笔记列表第一页失败" + throwable);
                });

        return notelist;
    }

    /**
     * 已删除
     * 通过tagid查询用户笔记列表第一页
     * @param tagid
     * @param pageSize
     * @return       List
     *      {"gmt_create":"2020-08-16T05:58:12.000+00:00",
     *      "note_id":1294876087679000577,
     *      "title":"java学习",
     *      "content":null}
     */
//    @Override
//    public List<NotesorTagsFirstPageRD.DataBean.NotesBean> GetNotesFirstPageByTag(long tagid ,int pageSize) {
//
//        List<NotesorTagsFirstPageRD.DataBean.NotesBean> notelist = new ArrayList<>();
//
//        RxHttp.get("/note/tag/" + tagid + "/" + pageSize )
//                .setSync() //指定在当前线程执行请求，即同步执行，
//                .asClass(NotesorTagsFirstPageRD.class)
//                .subscribe(nl -> {
//                    Log.d(TAG, "GetNotesFirstPageByTag: " + nl.getCode());
//                    if (nl.getCode() == 200){
//                        notelist.addAll(nl.getData().getNotes());
//                        Log.d(TAG, "GetNotesFirstPageByTag: count --> " + nl.getData().getCount());
//                    }
//                },throwable -> {
//                    Log.d("GetNotesFirstPageByTag", "通过tagid查询笔记列表第一页失败" + throwable);
//                });
//        return notelist;
//    }

    /**
     * *已删除*
     * 查询用户的笔记列表的第一页
     * @param pageSize
     * @return List
     *      {"gmt_create":"2020-08-16T05:58:12.000+00:00",
     *       "note_id":1294876087679000577,
     *       "title":"java学习",
     *       "content":null}
     */
//    @Override
//    public List<NotesorTagsFirstPageRD.DataBean.NotesBean> GetNotesFirstPage(int pageSize) {
//
//        List<NotesorTagsFirstPageRD.DataBean.NotesBean> notelist = new ArrayList<>();
//
//        RxHttp.get("/note/page/" + pageSize)
//                .setSync() //指定在当前线程执行请求，即同步执行，
//                .asClass(NotesorTagsFirstPageRD.class)
//                .as(RxLife.as((LifecycleOwner) this))
////                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
//                .subscribe(nl -> {
//                    Log.d(TAG, "GetNotelistFirstPage: " + nl.getCode());
//                    if (nl.getCode() == 200){
//                        notelist.addAll(nl.getData().getNotes());
//                        Log.d(TAG, "GetNotelistFirstPage: count --> " + nl.getData().getCount());
//                    }
//                },throwable -> {
//                    Log.d("GetNotelistFirstPage", "查询笔记列表第一页失败" + throwable);
//                });
//        return notelist;
//    }
}

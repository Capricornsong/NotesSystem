package cn.edu.bnuz.notes.impl;

import android.os.Binder;
import android.util.Log;

import cn.edu.bnuz.notes.interfaces.ITagController;
import cn.edu.bnuz.notes.ntwpojo.AddTagRD;
import cn.edu.bnuz.notes.ntwpojo.DeleteNoteTagShareEmailCheckUsernameCheckRD;
import cn.edu.bnuz.notes.ntwpojo.TagListRD;
import cn.edu.bnuz.notes.pojo.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import rxhttp.RxHttp;
import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

import static rxhttp.RxHttp.setOnParamAssembly;

public class TagControllerImpl extends Binder implements ITagController {
    AtomicReference<Boolean> result = new AtomicReference<>(false);
    private static final String TAG = "TagControllerImpl";
    AtomicInteger responseCode = new AtomicInteger();
    //设置全局请求头
    public TagControllerImpl(){
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
     * 查找用户的所有标签不用传参
     * @return
     *      tagName : 学习
     *      userId : null
     *      gmtCreate : 2020-08-13T15:31:47
     *      notes : null
     */
    @Override
    public List<TagListRD.DataBean> GetTagsByUser() {
        List<TagListRD.DataBean> notelist = new ArrayList<>();
        RxHttp.get("/tag/")
                .setSync() //指定在当前线程执行请求，即同步执行，
                .asClass(TagListRD.class)
                .subscribe(Tl -> {
                    Log.d(TAG, "GetTagsByUser: " + Tl.getCode());
                    if (Tl.getCode() == 200){
                        notelist.addAll(Tl.getData());
                    }
                },throwable -> {
                    Log.d("GetTagsByUser", "查询笔记列表第一页失败" + throwable);
                });
        return notelist;
    }

    @Override
    public int AddTag(String tagname) {

        RxHttp.postForm("/tag")
                .add("tagName",tagname)
                .asClass(AddTagRD.class)
                .subscribe(td -> {
                    Log.d(TAG, "AddTag: code -->" + td.getCode());
                    responseCode.set(td.getCode());
                    if (td.getCode() == 200){
                        result.set(true);
                        Log.d(TAG, "AddTag: 添加标签成功,tagid = "+ td.getData().getTagId());
                    }
                    else if (td.getCode() == 3005){
                        Log.d(TAG, "AddTag: 添加标签异常");
                    }
                },throwable -> {
                    Log.d("AddTag", "添加标签失败" + throwable);
                });
        return responseCode.get();
    }

    @Override
    public int DeleteTag(long tagid) {
        RxHttp.deleteForm("/tag/" + tagid)
                .asClass(DeleteNoteTagShareEmailCheckUsernameCheckRD.class)
                .subscribe(td -> {
                    Log.d(TAG, "DeleteTag: code -->" + td.getCode());
                    Log.d(TAG, "DeleteTag: state -->  "+ td.getMsg());
                    responseCode.set(td.getCode());
                    if (td.getCode() == 200){
                        result.set(true);
                        Log.d(TAG, "DeleteTag: 删除标签成功");
                    }
                },throwable -> {
                    Log.d("DeleteTag", "删除标签失败" + throwable);
                });
        return responseCode.get();
    }

    @Override
    public List<TagListRD.DataBean> GetTagByUserId(long userid) {
        List<TagListRD.DataBean> notelist = new ArrayList<>();
        RxHttp.get("/tag/" + userid)
                .setSync()  //指定在当前线程执行请求，即同步执行，
                .asClass(TagListRD.class)
                .subscribe(Tl -> {
                    Log.d(TAG, "GetTagsByUser: " + Tl.getCode());
                    if (Tl.getCode() == 200){
                        notelist.addAll(Tl.getData());
                    }
                },throwable -> {
                    Log.d("GetTagsByUser", "查询笔记列表第一页失败" + throwable);
                });
        return notelist;
    }
}

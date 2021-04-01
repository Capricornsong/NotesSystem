package cn.edu.bnuz.notes.interfaces;

import cn.edu.bnuz.notes.ntwpojo.NoteSearchRD;
import cn.edu.bnuz.notes.ntwpojo.NotesbyPageorTagIdRD;
import cn.edu.bnuz.notes.ntwpojo.TagsFilter;
import cn.edu.bnuz.notes.pojo.Note;

import java.util.List;

public interface INoteController {
    /**
     * 创建笔记
     * @param note
     *        content
     *        htmlContent
     *        title
     *        userId
     *
     * @return 200即为成功创建,201未无网络时保存在本地。
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
    long CreateNote(Note note);


    /**
     * 更新笔记(有缓存)
     * @param note
     * @return
     *      200即为成功
     *      201即为等待网络重连时自动同步
     */

    long UpdateNote(Note note);

    /**
     * 修改云端笔记htmlcontent（用于HTML内容替换完成后）
     * @param note
     * @return
     */
    int UpdateNoteHtmlContent(Note note);

    /**
     * 删除笔记
     * @param noteid
     * @return 200即为成功删除
     *         201即为等待网络重连时自动同步
     */
    long DeleteNote(long noteid);


    /**
     * 根据笔记id查询用户的笔记详细信息
     * @param noteid
     * @return
     */
    Note GetNoteByNoteID(Long noteid);

    //通过关键字分页查询用户的笔记
//    void GetNoteByKey();

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
    NotesbyPageorTagIdRD.NotesPkg GetNotesbyPage();


    /**
     * *已删除*
     * 查询用户的笔记列表的第一页
     * @param pageSize
     * @return
     */
//    List<NotesorTagsFirstPageRD.DataBean.NotesBean> GetNotesFirstPage(int pageSize);


    /**
     * 为笔记添加标签
     * @param TagName
     * @param noteid
     * @return
     */
    long AddTagToNote(String TagName, long noteid);


    /**
     * 删除笔记中的某个标签
     * @param noteid
     * @param tagid
     * @return
     */
    long DeleteTaginNote(long noteid, long tagid);


    /**
     * 通过标签id获取用户的分页笔记列表
     * @param tagid
     * @param pageNo
     * @param pageSize
     * @return
     */
    NotesbyPageorTagIdRD.NotesPkg GetNotesByTag(long tagid, int pageNo, int pageSize);


    /**
     * *已删除*
     * 通过标签id获取用户的笔记列表的第一页
     * @param tagid
     * @param pageSize
     * @return
     */
//    List<NotesorTagsFirstPageRD.DataBean.NotesBean> GetNotesFirstPageByTag(long tagid,int pageSize);


    /**
     * 通过多个标签名称获取用户的分页笔记列表
     * @param tags
     * @param pageNo
     * @param pageSize
     * @return
     *
     */
    TagsFilter.DataBean GetNotesbyTags(List<String> tags, int pageNo, int pageSize);


    /**
     * 通过关键子查询笔记
     * @param key
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<NoteSearchRD.DataBean> GetNotesByKey(String key, int pageNo, int pageSize);
}

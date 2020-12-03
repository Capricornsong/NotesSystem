package cn.edu.bnuz.notes.interfaces;

import cn.edu.bnuz.notes.ntwpojo.GetFilesbyNoteId;
import cn.edu.bnuz.notes.pojo.File;

import java.util.List;

public interface IFileController {
    //通过笔记id获取笔记包含的所有文件
    /**
     * 通过笔记id获取笔记包含的所有文件,返回list，其中的对象只包含如下项
     * @param noteid
     * @return
     *      "data": [
     *         {
     *             "fileName": "huhui.PNG",
     *             "fileUrl": null,
     *             "type": "image/png",
     *             "gmtCreate": "2020-11-11 22:36:42",
     *             "fileId": 1326534319033511938
     *         },
     *         {
     *             "fileName": "testpic.jpg",
     *             "fileUrl": null,
     *             "type": "image/jpeg",
     *             "gmtCreate": "2020-11-11 22:36:43",
     *             "fileId": 1326534321118081025
     *         }
     *     ]
     */
    List<GetFilesbyNoteId.DataBean> GetFilesbyNoteId(long noteid);


    List<GetFilesbyNoteId.DataBean> GetFilesinPagebyNoteId(long noteid, int pageno, int pagesize);
}

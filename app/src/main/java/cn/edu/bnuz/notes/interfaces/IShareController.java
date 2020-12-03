package cn.edu.bnuz.notes.interfaces;

import cn.edu.bnuz.notes.ntwpojo.GetSharesRD;
import cn.edu.bnuz.notes.pojo.Note;

import java.util.List;

public interface IShareController {
    /**
     * 获取用户本人分享的全部笔记
     * @return
     * {
     * "share_id": 1294937509981065217,
     *  gmt_create": "2020-08-16T10:02:16.000+00:00",
     * "note_id": 1294876087679000577,
     * "expired_time": null,
     * "title": "java学习",
     * "content": null
     * },
     */
    List<GetSharesRD.DataBean> GetAllShares();

    //创建分享,返回分享id
    long CreateShare(long noteid);

    /**
     * 获取分享的笔记信息
     * @return  只包含以下三项
     * "note_id": 1304708347300917250,
     * "html_content": "this is html_content2",
     * "title": "this  is title2"
     */
    Note GetShare(long shareid);

    //取消分享
    boolean DeleteShare(long shareid);

    //将其他人分享的笔记添加到我的笔记中
//    void AddShareToMy();

}

package cn.edu.bnuz.notes.interfaces;

import cn.edu.bnuz.notes.ntwpojo.TagListRD;

import java.util.List;

public interface ITagController {
    /**
     * 查找用户的所有标签不用传参
     * @return
     *      tagId : 1
     *      tagName : 学习
     *      userId : null
     *      gmtCreate : 2020-08-13T15:31:47
     *      notes : null
     */
    List<TagListRD.DataBean> GetTagsByUser();

    /**
     * 添加标签
     * @param tagname
     * @return
     */
    int AddTag(String tagname);


    /**
     * 删除用户的标签
     * @param tagid
     * @return
     */
    int DeleteTag(long tagid);


    /**
     * 通过用户id查找标签
     * @param userid
     * @return
     *      tagId : 1
     *      tagName : 学习
     *      userId : null
     *      gmtCreate : 2020-08-13T15:31:47
     *      notes : null
     */
    List<TagListRD.DataBean> GetTagByUserId(long userid);
}

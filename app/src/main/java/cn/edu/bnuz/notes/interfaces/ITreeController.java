package cn.edu.bnuz.notes.interfaces;

import java.util.List;

import cn.edu.bnuz.notes.ntwpojo.TreeNode;
import cn.edu.bnuz.notes.ntwpojo.TreeRelationRD;

public interface ITreeController {

    //提取关键词

    /**
     * 获取所有关系
     * @return jsonArray
     * {
     *    "startNodeName": "火烧云",
     *    "startNode_id": 1,
     *    "sim": 0.8,
     *    "endNodeName": "彗星",
     *    "endNode_id": 1294876087679000577
     *},
     */
    List<TreeRelationRD> getRelation();

    /**
     * 获取所有节点
     * @return
     * [
     *     {
     *         "wid": 1,
     *         "name": "火烧云"
     *     },
     *     {
     *         "wid": 1294876087679000577,
     *         "name": "彗星"
     *     }
     * ]
     */
    List<TreeNode> GetNodes();
    //获取单一节点的关系
    //添加一个节点及对应的关系
    //删除一个节点及对应的关系
    //通过百度搜索爬取链接
}

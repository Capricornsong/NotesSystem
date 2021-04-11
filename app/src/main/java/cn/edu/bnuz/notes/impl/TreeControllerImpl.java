package cn.edu.bnuz.notes.impl;

import android.os.Binder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import cn.edu.bnuz.notes.interfaces.ITreeController;
import cn.edu.bnuz.notes.ntwpojo.TreeNode;
import cn.edu.bnuz.notes.ntwpojo.TreeRelationRD;
import rxhttp.RxHttp;

import static cn.edu.bnuz.notes.utils.util.NetCheck;

public class TreeControllerImpl extends Binder implements ITreeController {

    private String TAG = "TreeControllerImpl";


    public TreeControllerImpl(){
        //设置全局请求头
//        setOnParamAssembly(new Function<Param<?>, Param<?>>() {
//            @Override
//            public Param apply(Param param) throws Exception {
//                Method method = param.getMethod();
//                return param.addHeader("Authorization", Token.token);
//            }
//        });
        Log.d(TAG, "NoteControllerImpl: 初始化。。");

    }

    @Override
    public List<TreeRelationRD> getRelation() {
        List<TreeRelationRD> TreeNodeList = new ArrayList<>();
        if (NetCheck()) {
            RxHttp.get("http://118.178.58.95:8080/getRelation")
                    .setSync()
                    .asString()
                    .subscribe(l -> {
                        Log.d(TAG, "getRelation: 收到的结果" + l);
                        JSONArray jsonArray = null;
                        jsonArray = new JSONArray(l);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            TreeRelationRD TreeNode = new TreeRelationRD();
                            TreeNode.setEndNode_id(jsonObject.getLong("endNode_id"));
                            TreeNode.setStartNodeName(jsonObject.getString("startNodeName"));
                            TreeNode.setStartNode_id(jsonObject.getLong("startNode_id"));
                            TreeNode.setEndNodeName(jsonObject.getString("endNodeName"));
                            TreeNode.setSim(jsonObject.getDouble("sim"));
                            TreeNodeList.add(TreeNode);
                        }
                    });
        }
        return TreeNodeList;
//        return String.valueOf(result);
    }


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
    @Override
    public List<TreeNode> GetNodes() {
        List<TreeNode> TreeNodes = new ArrayList<>();
        if (NetCheck()) {
            RxHttp.get("http://118.178.58.95:8080/getNode")
                    .setSync()
                    .asString()
                    .subscribe(l -> {
                        Log.d(TAG, "GetNodes: 收到的结果" + l);
                        JSONArray jsonArray = null;
                        jsonArray = new JSONArray(l);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            TreeNode TreeNode = new TreeNode();
                            TreeNode.setName(jsonObject.getString("name"));
                            TreeNode.setNodeId(jsonObject.getLong("wid"));
                            TreeNodes.add(TreeNode);
                        }
                    });
        }
        return TreeNodes;
    }
}

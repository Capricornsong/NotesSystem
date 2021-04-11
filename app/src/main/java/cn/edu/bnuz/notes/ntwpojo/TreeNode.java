package cn.edu.bnuz.notes.ntwpojo;

public class TreeNode {

    private Long nodeId;
    private String name;

    public TreeNode(){
        super();
    }
    public TreeNode(Long nodeId, String name){
        this.nodeId = nodeId;
        this.name = name;
    }
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

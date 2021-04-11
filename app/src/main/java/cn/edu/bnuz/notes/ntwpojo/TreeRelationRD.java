package cn.edu.bnuz.notes.ntwpojo;

public class TreeRelationRD {

    private String startNodeName;
    private Long startNode_id;
    private double sim;
    private String endNodeName;
    private Long endNode_id;

    public TreeRelationRD(){
        super();
    }
    public TreeRelationRD(String startNodeName, Long startNode_id, double sim, String endNodeName, Long endNode_id) {
        super();
        this.startNodeName = startNodeName;
        this.startNode_id = startNode_id;
        this.sim = sim;
        this.endNodeName = endNodeName;
        this.endNode_id = endNode_id;
    }
    public String getStartNodeName() {
        return startNodeName;
    }

    public void setStartNodeName(String startNodeName) {
        this.startNodeName = startNodeName;
    }

    public Long getStartNode_id() {
        return startNode_id;
    }

    public void setStartNode_id(Long startNode_id) {
        this.startNode_id = startNode_id;
    }

    public double getSim() {
        return sim;
    }

    public void setSim(double sim) {
        this.sim = sim;
    }

    public String getEndNodeName() {
        return endNodeName;
    }

    public void setEndNodeName(String endNodeName) {
        this.endNodeName = endNodeName;
    }

    public long getEndNode_id() {
        return endNode_id;
    }

    public void setEndNode_id(long endNode_id) {
        this.endNode_id = endNode_id;
    }
}

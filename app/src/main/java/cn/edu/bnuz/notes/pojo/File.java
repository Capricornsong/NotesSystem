package cn.edu.bnuz.notes.pojo;

public class File {

    private long fileId;
    private long noteId;
    private Object fileUrl;
    private int size;
    private String type;
    private String fileName;
    private String gmtCreate;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public Object getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(Object fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}

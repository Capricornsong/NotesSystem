package cn.edu.bnuz.notes.pojo;

import org.litepal.crud.LitePalSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note extends LitePalSupport {
    long noteId;
    long userId;
    String title;
    String content;
    String htmlcontent;
    int version;
    int delete;
    String gmt_modified;
    //1表示内容已同步，0表示未同步。
    int isSyn ;
    //
    int isDelete;

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsSyn() {
        return isSyn;
    }

    public void setIsSyn(int isSyn) {
        this.isSyn = isSyn;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long note_id) {
        this.noteId = note_id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long user_id) {
        this.userId = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtmlContent() {
        return htmlcontent;
    }

    public void setHtmlContent(String html_content) {
        this.htmlcontent = html_content;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }


//    public void setGmt_create(String  gmt_create) throws ParseException {
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = sf.parse(gmt_create);
//
//        this.gmt_create = date;
//    }


    public void setGmt_modified(String gmt_modified) {
        this.gmt_modified = gmt_modified;
    }
    public String getGmt_modified() {
        return gmt_modified;
    }

//    public void setGmt_modified(String gmt_modified) throws ParseException {
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = sf.parse(gmt_modified);
//        this.gmt_modified = date;
//    }
}

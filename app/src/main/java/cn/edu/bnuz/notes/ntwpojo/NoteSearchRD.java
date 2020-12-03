package cn.edu.bnuz.notes.ntwpojo;

import java.util.List;


/**
 * 通过关键字查询笔记RD
 */
public class NoteSearchRD extends BaseRD {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * deleted : 0
         * noteId : 1294923553371627522
         * title : 666
         * userId : 888888888
         * content : 123456
         * htmlContent : 123456
         */

        private int deleted;
        private long noteId;
        private String title;
        private long userId;
        private String content;
        private String htmlContent;

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public long getNoteId() {
            return noteId;
        }

        public void setNoteId(long noteId) {
            this.noteId = noteId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }
    }
}

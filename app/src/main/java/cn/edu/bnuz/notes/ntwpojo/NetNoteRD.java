package cn.edu.bnuz.notes.ntwpojo;

import cn.edu.bnuz.notes.utils.UDate;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class NetNoteRD extends BaseRD {
    /**
     * code : 200
     * msg : SUCCESS
     * data : {"noteId":1302071401701376001,"userId":888888888,"title":"this is title","htmlContent":"this is htmlContent","content":"this is content","version":null,"gmtModified":"2020-09-05T10:29:48.145"}
     */

    private Object data;

    public Object getObject() {
        return data;
    }

    public void setObject(Object data) {
        this.data = data;
    }

    public class Object{
        /**
         * noteId : 1302071401701376001
         * userId : 888888888
         * title : this is title
         * htmlContent : this is htmlContent
         * content : this is content
         * version : null
         * gmtModified : 2020-09-05T10:29:48.145
         */

        private long noteId;
        private long userId;
        private String title;
        private String htmlContent;
        private String content;
        private int version;
        private String gmtModified;

        public long getNoteId() {
            return noteId;
        }

        public void setNoteId(long noteId) {
            this.noteId = noteId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {

//            this.gmtModified = UDate.TfStringtoDate(gmtModified);
            this.gmtModified = gmtModified;

        }
    }
}

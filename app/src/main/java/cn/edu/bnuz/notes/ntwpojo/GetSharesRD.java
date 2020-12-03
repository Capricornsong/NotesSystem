package cn.edu.bnuz.notes.ntwpojo;

import java.util.List;

public class GetSharesRD extends BaseRD {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * share_id : 1294937509981065217
         * gmt_create : 2020-08-16T10:02:16.000+00:00
         * note_id : 1294876087679000577
         * expired_time : null
         * title : java学习
         * content : null
         */

        private long shareId;
        private String gmtCreate;
        private long noteId;
        private Object expiredTime;
        private String title;
        private Object content;

        public long getShareId() {
            return shareId;
        }

        public void setShareId(long shareId) {
            this.shareId = shareId;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public long getNoteId() {
            return noteId;
        }

        public void setNoteId(long noteId) {
            this.noteId = noteId;
        }

        public Object getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(Object expiredTime) {
            this.expiredTime = expiredTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }
    }
}

package cn.edu.bnuz.notes.ntwpojo;

import cn.edu.bnuz.notes.ntwpojo.BaseRD;

import java.util.List;

public class TagListRD extends BaseRD {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tagId : 1
         * tagName : 学习
         * userId : null
         * gmtCreate : 2020-08-13T15:31:47
         * notes : null
         */

        private long tagId;
        private String tagName;
        private long userId;
        private String gmtCreate;
        private Object notes;

        public long getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public Object getNotes() {
            return notes;
        }

        public void setNotes(Object notes) {
            this.notes = notes;
        }
    }
}

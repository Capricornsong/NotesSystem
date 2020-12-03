package cn.edu.bnuz.notes.ntwpojo;

public class AddTagRD extends BaseRD {

    /**
     * data : {"tagId":1305396799721943041,"tagName":"gshock","userId":888888888,"gmtCreate":"2020-09-14T14:43:44.812","notes":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tagId : 1305396799721943041
         * tagName : gshock
         * userId : 888888888
         * gmtCreate : 2020-09-14T14:43:44.812
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

        public void setTagId(long tagId) {
            this.tagId = tagId;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
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

package cn.edu.bnuz.notes.ntwpojo;

public class AddTagtoNoteRD extends BaseRD {

    DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tagId : 1302469367863902210
         * noteId : 1302177615412133889
         */

        private long tagId;
        private long noteId;

        public long getTagId() {
            return tagId;
        }

        public void setTagId(long tagId) {
            this.tagId = tagId;
        }

        public long getNoteId() {
            return noteId;
        }

        public void setNoteId(long noteId) {
            this.noteId = noteId;
        }
    }
}

package cn.edu.bnuz.notes.ntwpojo;

public class PostFile extends BaseRD {

    /**
     * data : {"fileId":1306478345597943809,"noteId":1302071401701376001,"fileUrl":null,"size":2295255,"type":"application/x-msdos-program","fileName":"AutoDarkMode_v3_0_1_Setup.exe","gmtCreate":"2020-09-17T14:21:25.443"}
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
         * fileId : 1306478345597943809
         * noteId : 1302071401701376001
         * fileUrl : null
         * size : 2295255
         * type : application/x-msdos-program
         * fileName : AutoDarkMode_v3_0_1_Setup.exe
         * gmtCreate : 2020-09-17T14:21:25.443
         */

        private long fileId;
        private long noteId;
        private String fileUrl;
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

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
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
}

package cn.edu.bnuz.notes.ntwpojo;

import java.util.List;

public class GetFilesbyNoteId extends BaseRD {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * gmtCreate : "2020-11-11 22:36:42"
         * fileUrl : null
         * fileName : 微信图片_20191231102908.jpg
         * fileId : 1302797077429551106
         * type : image/jpeg
         */

        private String gmtCreate;
        private Object fileUrl;
        private String fileName;
        private long fileId;
        private String type;

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public Object getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(Object fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public long getFileId() {
            return fileId;
        }

        public void setFileId(long fileId) {
            this.fileId = fileId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

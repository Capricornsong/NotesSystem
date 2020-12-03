package cn.edu.bnuz.notes.ntwpojo;

import java.util.List;

public class GetSharebyIdRD extends BaseRD {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * note_id : 1304708347300917250
         * html_content : this is html_content2
         * title : this  is title2
         */

        private long note_id;
        private String html_content;
        private String title;

        public long getNote_id() {
            return note_id;
        }

        public void setNote_id(long note_id) {
            this.note_id = note_id;
        }

        public String getHtml_content() {
            return html_content;
        }

        public void setHtml_content(String html_content) {
            this.html_content = html_content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

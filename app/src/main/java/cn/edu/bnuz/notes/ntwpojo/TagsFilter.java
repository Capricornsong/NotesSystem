package cn.edu.bnuz.notes.ntwpojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TagsFilter extends BaseRD{

    /**
     * data : {"notes":[{"gmtModified":"2020-09-28 14:28:25","noteId":1310466372095655937,"title":"this  is title2","content":"this is content2"},{"gmtModified":"2020-10-07 16:40:55","noteId":1313761208601104386,"title":"this is title1","content":"this is content1"}],"count":2}
     */

    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * notes : [{"gmtModified":"2020-09-28 14:28:25","noteId":1310466372095655937,"title":"this  is title2","content":"this is content2"},{"gmtModified":"2020-10-07 16:40:55","noteId":1313761208601104386,"title":"this is title1","content":"this is content1"}]
         * count : 2
         */

        public List<NotesBean> notes;
        public int count;

        public List<NotesBean> getNotes() {
            return notes;
        }

        public void setNotes(List<NotesBean> notes) {
            this.notes = notes;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public static class NotesBean implements Parcelable {
            /**
             * gmtModified : 2020-09-28 14:28:25
             * noteId : 1310466372095655937
             * title : this  is title2
             * content : this is content2
             */

            private String gmtModified;
            private long noteId;
            private String title;
            private String content;

            public String getGmtModified() {
                return gmtModified;
            }

            public void setGmtModified(String gmtModified) {
                this.gmtModified = gmtModified;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.gmtModified);
                dest.writeLong(this.noteId);
                dest.writeString(this.title);
                dest.writeString(this.content);
            }

            public void readFromParcel(Parcel source) {
                this.gmtModified = source.readString();
                this.noteId = source.readLong();
                this.title = source.readString();
                this.content = source.readString();
            }

            public NotesBean() {
            }

            protected NotesBean(Parcel in) {
                this.gmtModified = in.readString();
                this.noteId = in.readLong();
                this.title = in.readString();
                this.content = in.readString();
            }

            public static final Creator<NotesBean> CREATOR = new Creator<NotesBean>() {
                @Override
                public NotesBean createFromParcel(Parcel source) {
                    return new NotesBean(source);
                }

                @Override
                public NotesBean[] newArray(int size) {
                    return new NotesBean[size];
                }
            };
        }
    }
}

package cn.edu.bnuz.notes.ntwpojo;

import java.util.List;


/**
 * 查询用户笔记所返回的json
 */
public class NotesorTagsFirstPageRD extends BaseRD{

    /**
     * data : {"notes":[{"gmt_create":"2020-08-16T05:58:12.000+00:00","note_id":1294876087679000577,"title":"java学习","content":null},{"gmt_create":"2020-08-16T08:29:00.000+00:00","note_id":1294914038374985729,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:18.000+00:00","note_id":1294923428070989825,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:19.000+00:00","note_id":1294923432210767873,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:21.000+00:00","note_id":1294923437772414978,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:21.000+00:00","note_id":1294923439315918850,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:21.000+00:00","note_id":1294923440762953730,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:22.000+00:00","note_id":1294923442528755714,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:22.000+00:00","note_id":1294923443925458946,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:24.000+00:00","note_id":1294923451940773890,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:25.000+00:00","note_id":1294923453777879042,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:25.000+00:00","note_id":1294923455166193666,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:26.000+00:00","note_id":1294923460891418626,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:27.000+00:00","note_id":1294923463064068097,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:27.000+00:00","note_id":1294923464448188417,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:29.000+00:00","note_id":1294923472589332481,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:30.000+00:00","note_id":1294923476527783937,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:31.000+00:00","note_id":1294923479342161921,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:31.000+00:00","note_id":1294923481758081025,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:32.000+00:00","note_id":1294923483981062145,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:32.000+00:00","note_id":1294923486132740097,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:33.000+00:00","note_id":1294923487957262338,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:33.000+00:00","note_id":1294923490046025730,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:34.000+00:00","note_id":1294923492424196097,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:34.000+00:00","note_id":1294923495074996225,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:35.000+00:00","note_id":1294923497335726082,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:36.000+00:00","note_id":1294923500338847745,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:36.000+00:00","note_id":1294923503316803586,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:37.000+00:00","note_id":1294923506022129666,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:38.000+00:00","note_id":1294923508781981698,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:38.000+00:00","note_id":1294923510682001409,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:39.000+00:00","note_id":1294923512879816705,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:39.000+00:00","note_id":1294923515790663681,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:40.000+00:00","note_id":1294923518428880897,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:41.000+00:00","note_id":1294923521192927234,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:41.000+00:00","note_id":1294923523600457729,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:42.000+00:00","note_id":1294923525911519234,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:42.000+00:00","note_id":1294923528696537089,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:43.000+00:00","note_id":1294923530978238465,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:44.000+00:00","note_id":1294923533499015170,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:44.000+00:00","note_id":1294923535734579202,"title":"666","content":"java"},{"gmt_create":"2020-08-16T09:06:45.000+00:00","note_id":1294923538297298946,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:45.000+00:00","note_id":1294923541006819330,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:46.000+00:00","note_id":1294923543762477057,"title":"666","content":"1294943754792333314，"},{"gmt_create":"2020-08-16T09:06:47.000+00:00","note_id":1294923546350362625,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:47.000+00:00","note_id":1294923548674007042,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:48.000+00:00","note_id":1294923550892793858,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:48.000+00:00","note_id":1294923553371627522,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:50.000+00:00","note_id":1294923560065736705,"title":"666","content":"123456"},{"gmt_create":"2020-09-05T02:29:48.000+00:00","note_id":1302071401701376001,"title":"this is updated title","content":"this is updated content"},{"gmt_create":"2020-09-05T12:27:35.000+00:00","note_id":1302221839964766210,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-07T12:03:53.000+00:00","note_id":1302940647591444482,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-07T12:05:58.000+00:00","note_id":1302941171900416002,"title":"this is updated title","content":"this is updated content"},{"gmt_create":"2020-09-07T12:08:08.000+00:00","note_id":1302941720905449473,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-07T12:43:35.000+00:00","note_id":1302950640193568769,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-10T05:13:10.000+00:00","note_id":1303924454733799425,"title":"this is updated title1","content":"this is updated content1"},{"gmt_create":"2020-09-10T05:30:28.000+00:00","note_id":1303928806487777282,"title":"this is title1","content":"this is content1"},{"gmt_create":"2020-09-10T05:45:38.000+00:00","note_id":1303932624826949634,"title":"this is title1","content":"this is content1"},{"gmt_create":"2020-09-10T09:24:23.000+00:00","note_id":1303987674194620417,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-11T05:15:37.000+00:00","note_id":1304287458440753154,"title":"this is title1","content":"this is content1"},{"gmt_create":"2020-09-11T07:21:08.000+00:00","note_id":1304319046289436673,"title":"this is update title2","content":"this is update content2"},{"gmt_create":"2020-09-11T07:24:47.000+00:00","note_id":1304319962031828994,"title":"this is update title2","content":"this is update content2"},{"gmt_create":"2020-09-11T08:37:17.000+00:00","note_id":1304338209095528449,"title":"this is update title2","content":"this is update content2"},{"gmt_create":"2020-09-11T08:38:01.000+00:00","note_id":1304338394043363329,"title":"this is update title2","content":"this is update content2"},{"gmt_create":"2020-09-11T09:13:22.000+00:00","note_id":1304347289189068802,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-11T10:08:51.000+00:00","note_id":1304361250949148673,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:28:03.000+00:00","note_id":1304668073635852290,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:29:02.000+00:00","note_id":1304668319652753409,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:30:12.000+00:00","note_id":1304668612947849218,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:31:28.000+00:00","note_id":1304668934697103362,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:35:07.000+00:00","note_id":1304669850057814018,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:36:34.000+00:00","note_id":1304670217155883010,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:41:49.000+00:00","note_id":1304671537325649921,"title":"this  is title2","content":"this is content2"}],"count":73}
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
         * notes : [{"gmt_create":"2020-08-16T05:58:12.000+00:00","note_id":1294876087679000577,"title":"java学习","content":null},{"gmt_create":"2020-08-16T08:29:00.000+00:00","note_id":1294914038374985729,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:18.000+00:00","note_id":1294923428070989825,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:19.000+00:00","note_id":1294923432210767873,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:21.000+00:00","note_id":1294923437772414978,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:21.000+00:00","note_id":1294923439315918850,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:21.000+00:00","note_id":1294923440762953730,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:22.000+00:00","note_id":1294923442528755714,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:22.000+00:00","note_id":1294923443925458946,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:24.000+00:00","note_id":1294923451940773890,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:25.000+00:00","note_id":1294923453777879042,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:25.000+00:00","note_id":1294923455166193666,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:26.000+00:00","note_id":1294923460891418626,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:27.000+00:00","note_id":1294923463064068097,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:27.000+00:00","note_id":1294923464448188417,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:29.000+00:00","note_id":1294923472589332481,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:30.000+00:00","note_id":1294923476527783937,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:31.000+00:00","note_id":1294923479342161921,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:31.000+00:00","note_id":1294923481758081025,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:32.000+00:00","note_id":1294923483981062145,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:32.000+00:00","note_id":1294923486132740097,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:33.000+00:00","note_id":1294923487957262338,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:33.000+00:00","note_id":1294923490046025730,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:34.000+00:00","note_id":1294923492424196097,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:34.000+00:00","note_id":1294923495074996225,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:35.000+00:00","note_id":1294923497335726082,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:36.000+00:00","note_id":1294923500338847745,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:36.000+00:00","note_id":1294923503316803586,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:37.000+00:00","note_id":1294923506022129666,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:38.000+00:00","note_id":1294923508781981698,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:38.000+00:00","note_id":1294923510682001409,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:39.000+00:00","note_id":1294923512879816705,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:39.000+00:00","note_id":1294923515790663681,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:40.000+00:00","note_id":1294923518428880897,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:41.000+00:00","note_id":1294923521192927234,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:41.000+00:00","note_id":1294923523600457729,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:42.000+00:00","note_id":1294923525911519234,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:42.000+00:00","note_id":1294923528696537089,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:43.000+00:00","note_id":1294923530978238465,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:44.000+00:00","note_id":1294923533499015170,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:44.000+00:00","note_id":1294923535734579202,"title":"666","content":"java"},{"gmt_create":"2020-08-16T09:06:45.000+00:00","note_id":1294923538297298946,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:45.000+00:00","note_id":1294923541006819330,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:46.000+00:00","note_id":1294923543762477057,"title":"666","content":"1294943754792333314，"},{"gmt_create":"2020-08-16T09:06:47.000+00:00","note_id":1294923546350362625,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:47.000+00:00","note_id":1294923548674007042,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:48.000+00:00","note_id":1294923550892793858,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:48.000+00:00","note_id":1294923553371627522,"title":"666","content":"123456"},{"gmt_create":"2020-08-16T09:06:50.000+00:00","note_id":1294923560065736705,"title":"666","content":"123456"},{"gmt_create":"2020-09-05T02:29:48.000+00:00","note_id":1302071401701376001,"title":"this is updated title","content":"this is updated content"},{"gmt_create":"2020-09-05T12:27:35.000+00:00","note_id":1302221839964766210,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-07T12:03:53.000+00:00","note_id":1302940647591444482,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-07T12:05:58.000+00:00","note_id":1302941171900416002,"title":"this is updated title","content":"this is updated content"},{"gmt_create":"2020-09-07T12:08:08.000+00:00","note_id":1302941720905449473,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-07T12:43:35.000+00:00","note_id":1302950640193568769,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-10T05:13:10.000+00:00","note_id":1303924454733799425,"title":"this is updated title1","content":"this is updated content1"},{"gmt_create":"2020-09-10T05:30:28.000+00:00","note_id":1303928806487777282,"title":"this is title1","content":"this is content1"},{"gmt_create":"2020-09-10T05:45:38.000+00:00","note_id":1303932624826949634,"title":"this is title1","content":"this is content1"},{"gmt_create":"2020-09-10T09:24:23.000+00:00","note_id":1303987674194620417,"title":"this is title","content":"this is content"},{"gmt_create":"2020-09-11T05:15:37.000+00:00","note_id":1304287458440753154,"title":"this is title1","content":"this is content1"},{"gmt_create":"2020-09-11T07:21:08.000+00:00","note_id":1304319046289436673,"title":"this is update title2","content":"this is update content2"},{"gmt_create":"2020-09-11T07:24:47.000+00:00","note_id":1304319962031828994,"title":"this is update title2","content":"this is update content2"},{"gmt_create":"2020-09-11T08:37:17.000+00:00","note_id":1304338209095528449,"title":"this is update title2","content":"this is update content2"},{"gmt_create":"2020-09-11T08:38:01.000+00:00","note_id":1304338394043363329,"title":"this is update title2","content":"this is update content2"},{"gmt_create":"2020-09-11T09:13:22.000+00:00","note_id":1304347289189068802,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-11T10:08:51.000+00:00","note_id":1304361250949148673,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:28:03.000+00:00","note_id":1304668073635852290,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:29:02.000+00:00","note_id":1304668319652753409,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:30:12.000+00:00","note_id":1304668612947849218,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:31:28.000+00:00","note_id":1304668934697103362,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:35:07.000+00:00","note_id":1304669850057814018,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:36:34.000+00:00","note_id":1304670217155883010,"title":"this  is title2","content":"this is content2"},{"gmt_create":"2020-09-12T06:41:49.000+00:00","note_id":1304671537325649921,"title":"this  is title2","content":"this is content2"}]
         * count : 73
         */

        private int count;
        private List<NotesBean> notes;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<NotesBean> getNotes() {
            return notes;
        }

        public void setNotes(List<NotesBean> notes) {
            this.notes = notes;
        }

        public static class NotesBean {
            /**
             * gmtModified : 2020-08-16T05:58:12.000+00:00
             * note_id : 1294876087679000577
             * title : java学习
             * content : null
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
        }
    }
}
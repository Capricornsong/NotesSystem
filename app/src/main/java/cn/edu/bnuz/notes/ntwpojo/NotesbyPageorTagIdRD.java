package cn.edu.bnuz.notes.ntwpojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NotesbyPageorTagIdRD extends BaseRD {
    /**
     * data : {"notes":[{"gmtModified":"2020-08-16 16:29:00","noteId":1294914038374985729,"title":"666","gmtCreate":"2020-08-16 16:29:00","content":"123456"},{"gmtModified":"2020-08-16 17:06:18","noteId":1294923428070989825,"title":"666","gmtCreate":"2020-08-16 17:06:18","content":"123456"},{"gmtModified":"2020-08-16 17:06:19","noteId":1294923432210767873,"title":"666","gmtCreate":"2020-08-16 17:06:19","content":"123456"},{"gmtModified":"2020-08-16 17:06:21","noteId":1294923437772414978,"title":"666","gmtCreate":"2020-08-16 17:06:21","content":"123456"},{"gmtModified":"2020-08-16 17:06:21","noteId":1294923439315918850,"title":"666","gmtCreate":"2020-08-16 17:06:21","content":"123456"},{"gmtModified":"2020-08-16 17:06:21","noteId":1294923440762953730,"title":"666","gmtCreate":"2020-08-16 17:06:21","content":"123456"},{"gmtModified":"2020-08-16 17:06:22","noteId":1294923442528755714,"title":"666","gmtCreate":"2020-08-16 17:06:22","content":"123456"},{"gmtModified":"2020-08-16 17:06:22","noteId":1294923443925458946,"title":"666","gmtCreate":"2020-08-16 17:06:22","content":"123456"},{"gmtModified":"2020-08-16 17:06:24","noteId":1294923451940773890,"title":"666","gmtCreate":"2020-08-16 17:06:24","content":"123456"},{"gmtModified":"2020-08-16 17:06:25","noteId":1294923453777879042,"title":"666","gmtCreate":"2020-08-16 17:06:25","content":"123456"},{"gmtModified":"2020-08-16 17:06:25","noteId":1294923455166193666,"title":"666","gmtCreate":"2020-08-16 17:06:25","content":"123456"},{"gmtModified":"2020-08-16 17:06:26","noteId":1294923460891418626,"title":"666","gmtCreate":"2020-08-16 17:06:26","content":"123456"},{"gmtModified":"2020-08-16 17:06:27","noteId":1294923463064068097,"title":"666","gmtCreate":"2020-08-16 17:06:27","content":"123456"},{"gmtModified":"2020-08-16 17:06:27","noteId":1294923464448188417,"title":"666","gmtCreate":"2020-08-16 17:06:27","content":"123456"},{"gmtModified":"2020-08-16 17:06:29","noteId":1294923472589332481,"title":"666","gmtCreate":"2020-08-16 17:06:29","content":"123456"},{"gmtModified":"2020-08-16 17:06:30","noteId":1294923476527783937,"title":"666","gmtCreate":"2020-08-16 17:06:30","content":"123456"},{"gmtModified":"2020-08-16 17:06:31","noteId":1294923479342161921,"title":"666","gmtCreate":"2020-08-16 17:06:31","content":"123456"},{"gmtModified":"2020-08-16 17:06:31","noteId":1294923481758081025,"title":"666","gmtCreate":"2020-08-16 17:06:31","content":"123456"},{"gmtModified":"2020-08-16 17:06:32","noteId":1294923483981062145,"title":"666","gmtCreate":"2020-08-16 17:06:32","content":"123456"},{"gmtModified":"2020-08-16 17:06:32","noteId":1294923486132740097,"title":"666","gmtCreate":"2020-08-16 17:06:32","content":"123456"},{"gmtModified":"2020-08-16 17:06:33","noteId":1294923487957262338,"title":"666","gmtCreate":"2020-08-16 17:06:33","content":"123456"},{"gmtModified":"2020-08-16 17:06:33","noteId":1294923490046025730,"title":"666","gmtCreate":"2020-08-16 17:06:33","content":"123456"},{"gmtModified":"2020-08-16 17:06:34","noteId":1294923492424196097,"title":"666","gmtCreate":"2020-08-16 17:06:34","content":"123456"},{"gmtModified":"2020-08-16 17:06:34","noteId":1294923495074996225,"title":"666","gmtCreate":"2020-08-16 17:06:34","content":"123456"},{"gmtModified":"2020-08-16 17:06:35","noteId":1294923497335726082,"title":"666","gmtCreate":"2020-08-16 17:06:35","content":"123456"},{"gmtModified":"2020-08-16 17:06:36","noteId":1294923500338847745,"title":"666","gmtCreate":"2020-08-16 17:06:36","content":"123456"},{"gmtModified":"2020-08-16 17:06:36","noteId":1294923503316803586,"title":"666","gmtCreate":"2020-08-16 17:06:36","content":"123456"},{"gmtModified":"2020-08-16 17:06:37","noteId":1294923506022129666,"title":"666","gmtCreate":"2020-08-16 17:06:37","content":"123456"},{"gmtModified":"2020-08-16 17:06:38","noteId":1294923508781981698,"title":"666","gmtCreate":"2020-08-16 17:06:38","content":"123456"},{"gmtModified":"2020-08-16 17:06:38","noteId":1294923510682001409,"title":"666","gmtCreate":"2020-08-16 17:06:38","content":"123456"},{"gmtModified":"2020-08-16 17:06:39","noteId":1294923512879816705,"title":"666","gmtCreate":"2020-08-16 17:06:39","content":"123456"},{"gmtModified":"2020-08-16 17:06:39","noteId":1294923515790663681,"title":"666","gmtCreate":"2020-08-16 17:06:39","content":"123456"},{"gmtModified":"2020-08-16 17:06:40","noteId":1294923518428880897,"title":"666","gmtCreate":"2020-08-16 17:06:40","content":"123456"},{"gmtModified":"2020-08-16 17:06:41","noteId":1294923521192927234,"title":"666","gmtCreate":"2020-08-16 17:06:41","content":"123456"},{"gmtModified":"2020-08-16 17:06:41","noteId":1294923523600457729,"title":"666","gmtCreate":"2020-08-16 17:06:41","content":"123456"},{"gmtModified":"2020-08-16 17:06:42","noteId":1294923525911519234,"title":"666","gmtCreate":"2020-08-16 17:06:42","content":"123456"},{"gmtModified":"2020-08-16 17:06:42","noteId":1294923528696537089,"title":"666","gmtCreate":"2020-08-16 17:06:42","content":"123456"},{"gmtModified":"2020-08-16 17:06:43","noteId":1294923530978238465,"title":"666","gmtCreate":"2020-08-16 17:06:43","content":"123456"},{"gmtModified":"2020-08-16 17:06:44","noteId":1294923533499015170,"title":"666","gmtCreate":"2020-08-16 17:06:44","content":"123456"},{"gmtModified":"2020-08-16 17:06:44","noteId":1294923535734579202,"title":"666","gmtCreate":"2020-08-16 17:06:44","content":"java"},{"gmtModified":"2020-08-16 17:06:45","noteId":1294923538297298946,"title":"666","gmtCreate":"2020-08-16 17:06:45","content":"123456"},{"gmtModified":"2020-08-16 17:06:45","noteId":1294923541006819330,"title":"666","gmtCreate":"2020-08-16 17:06:45","content":"123456"},{"gmtModified":"2020-08-16 17:06:46","noteId":1294923543762477057,"title":"666","gmtCreate":"2020-08-16 17:06:46","content":"1294943754792333314，"},{"gmtModified":"2020-08-16 17:06:47","noteId":1294923546350362625,"title":"666","gmtCreate":"2020-08-16 17:06:47","content":"123456"},{"gmtModified":"2020-08-16 17:06:47","noteId":1294923548674007042,"title":"666","gmtCreate":"2020-08-16 17:06:47","content":"123456"},{"gmtModified":"2020-08-16 17:06:48","noteId":1294923550892793858,"title":"666","gmtCreate":"2020-08-16 17:06:48","content":"123456"},{"gmtModified":"2020-08-16 17:06:48","noteId":1294923553371627522,"title":"666","gmtCreate":"2020-08-16 17:06:48","content":"123456"},{"gmtModified":"2020-08-16 17:06:50","noteId":1294923560065736705,"title":"666","gmtCreate":"2020-08-16 17:06:50","content":"123456"},{"gmtModified":"2020-09-20 10:32:10","noteId":1301890108103524356,"title":"aaa","gmtCreate":"2020-09-20 10:25:07","content":"aaa"},{"gmtModified":"2020-09-21 15:04:14","noteId":1307938671153545218,"title":"this  is title2","gmtCreate":"2020-09-21 15:04:14","content":"this is content2"},{"gmtModified":"2020-09-22 11:45:02","noteId":1308250925916913666,"title":"this  is title2","gmtCreate":"2020-09-22 11:45:02","content":"this is content2"},{"gmtModified":"2020-09-22 11:46:38","noteId":1308251331342532610,"title":"this  is title2","gmtCreate":"2020-09-22 11:46:38","content":"this is content2"},{"gmtModified":"2020-09-24 11:44:29","noteId":1308975053561036802,"title":"this is updated title1","gmtCreate":"2020-09-24 11:42:27","content":"this is updated content1"},{"gmtModified":"2020-09-24 14:48:59","noteId":1309021995632590849,"title":"this  is title2","gmtCreate":"2020-09-24 14:48:59","content":"this is content2"},{"gmtModified":"2020-09-24 14:55:53","noteId":1309023734528114689,"title":"this  is title2","gmtCreate":"2020-09-24 14:55:53","content":"this is content2"},{"gmtModified":"2020-09-28 14:18:51","noteId":1310463966096080898,"title":"this is title1","gmtCreate":"2020-09-28 14:18:51","content":"this is content1"},{"gmtModified":"2020-09-28 14:23:30","noteId":1310465134209744897,"title":"this  is title2","gmtCreate":"2020-09-28 14:23:30","content":"this is content2"},{"gmtModified":"2020-09-28 14:28:25","noteId":1310466372095655937,"title":"this  is title2","gmtCreate":"2020-09-28 14:28:25","content":"this is content2"},{"gmtModified":"2020-09-28 14:29:32","noteId":1310466653009166337,"title":"this  is title2","gmtCreate":"2020-09-28 14:29:32","content":"this is content2"},{"gmtModified":"2020-09-28 15:12:24","noteId":1310477440763248642,"title":"this  is title2","gmtCreate":"2020-09-28 15:12:24","content":"this is content2"},{"gmtModified":"2020-10-07 16:33:30","noteId":1313759342492340226,"title":"this  is title2","gmtCreate":"2020-10-07 16:33:30","content":"this is content2"},{"gmtModified":"2020-10-07 16:33:41","noteId":1313759388688404481,"title":"this  is title2","gmtCreate":"2020-10-07 16:33:41","content":"this is content2"},{"gmtModified":"2020-10-07 16:40:55","noteId":1313761208601104386,"title":"this is title1","gmtCreate":"2020-10-07 16:40:55","content":"this is content1"},{"gmtModified":"2020-10-07 16:43:42","noteId":1313761907388928002,"title":"this  is title2","gmtCreate":"2020-10-07 16:43:42","content":"this is content2"},{"gmtModified":"2020-10-07 16:45:06","noteId":1313762261992165378,"title":"this  is title2","gmtCreate":"2020-10-07 16:45:06","content":"this is content2"},{"gmtModified":"2020-10-07 17:27:57","noteId":1313773042804674561,"title":"this is title1","gmtCreate":"2020-10-07 17:27:57","content":"this is content1"},{"gmtModified":"2020-10-08 13:53:11","noteId":1314081385595228161,"title":"this is title1","gmtCreate":"2020-10-08 13:53:11","content":"this is content1"},{"gmtModified":"2020-10-08 14:41:46","noteId":1314093608443006978,"title":"this  is title2","gmtCreate":"2020-10-08 14:41:46","content":"this is content2"},{"gmtModified":"2020-10-14 20:18:04","noteId":1314098406051500034,"title":"this is update title2","gmtCreate":"2020-10-08 15:00:49","content":"this is update content2"},{"gmtModified":"2020-10-08 15:14:27","noteId":1314101833481539585,"title":"this  is title2","gmtCreate":"2020-10-08 15:14:27","content":"this is content2"},{"gmtModified":"2020-10-09 09:39:00","noteId":1314379804851261442,"title":"this  is title2","gmtCreate":"2020-10-09 09:39:00","content":"this is content2"},{"gmtModified":"2020-10-13 16:26:22","noteId":1315931872170823681,"title":"this  is title2","gmtCreate":"2020-10-13 16:26:22","content":"this is content2"},{"gmtModified":"2020-10-13 22:32:48","noteId":1316024090621595650,"title":"string","gmtCreate":"2020-10-13 22:32:48","content":"string"},{"gmtModified":"2020-10-14 10:32:57","noteId":1316205319581028354,"title":"string","gmtCreate":"2020-10-14 10:32:57","content":"string"},{"gmtModified":"2020-10-14 10:48:41","noteId":1316209279998054401,"title":"string","gmtCreate":"2020-10-14 10:48:41","content":"string"},{"gmtModified":"2020-10-14 11:02:48","noteId":1316212834666500098,"title":"","gmtCreate":"2020-10-14 11:02:48","content":"string"},{"gmtModified":"2020-10-14 11:06:26","noteId":1316213746185228290,"title":"string","gmtCreate":"2020-10-14 11:06:26","content":"string"},{"gmtModified":"2020-10-14 11:07:11","noteId":1316213935100874754,"title":"","gmtCreate":"2020-10-14 11:07:11","content":"string"},{"gmtModified":"2020-10-14 11:11:08","noteId":1316214930883170305,"title":"","gmtCreate":"2020-10-14 11:11:08","content":"string"},{"gmtModified":"2020-10-14 11:11:55","noteId":1316215126010580993,"title":"","gmtCreate":"2020-10-14 11:11:55","content":"string"},{"gmtModified":"2020-10-14 11:12:32","noteId":1316215283326341122,"title":" this.inputtitle","gmtCreate":"2020-10-14 11:12:32","content":"string"},{"gmtModified":"2020-10-14 11:13:47","noteId":1316215597253218305,"title":"","gmtCreate":"2020-10-14 11:13:47","content":"string"},{"gmtModified":"2020-10-14 11:20:31","noteId":1316217291034808322,"title":"123","gmtCreate":"2020-10-14 11:20:31","content":"string"},{"gmtModified":"2020-10-14 15:49:37","noteId":1316285012422381569,"title":"","gmtCreate":"2020-10-14 15:49:37","content":"string"},{"gmtModified":"2020-10-14 18:01:49","noteId":1316318283046801410,"title":"this  is title2","gmtCreate":"2020-10-14 18:01:49","content":"this is content2"},{"gmtModified":"2020-10-14 19:35:24","noteId":1316341831324880897,"title":"this  is title2","gmtCreate":"2020-10-14 19:35:24","content":"this is content2"},{"gmtModified":"2020-10-16 10:50:25","noteId":1316934492205305857,"title":"","gmtCreate":"2020-10-16 10:50:25","content":"string"},{"gmtModified":"2020-10-23 15:09:22","noteId":1319536374497103873,"title":"","gmtCreate":"2020-10-23 15:09:22","content":"string"},{"gmtModified":"2020-10-25 20:20:00","noteId":1320339321254531073,"title":"hello","gmtCreate":"2020-10-25 20:20:00","content":"content"},{"gmtModified":"2020-11-05 16:33:43","noteId":1324268643484913665,"title":"this is title1","gmtCreate":"2020-11-05 16:33:43","content":"this is content1"},{"gmtModified":"2020-11-11 20:21:50","noteId":1326500378759061506,"title":"this is title1","gmtCreate":"2020-11-11 20:21:50","content":"this is content1"}],"count":91}
     */

    public NotesPkg data;

    public NotesPkg getData() {
        return data;
    }

    public void setData(NotesPkg data) {
        this.data = data;
    }

    public static class NotesPkg {
        /**
         * notes : [{"gmtModified":"2020-08-16 16:29:00","noteId":1294914038374985729,"title":"666","gmtCreate":"2020-08-16 16:29:00","content":"123456"},{"gmtModified":"2020-08-16 17:06:18","noteId":1294923428070989825,"title":"666","gmtCreate":"2020-08-16 17:06:18","content":"123456"},{"gmtModified":"2020-08-16 17:06:19","noteId":1294923432210767873,"title":"666","gmtCreate":"2020-08-16 17:06:19","content":"123456"},{"gmtModified":"2020-08-16 17:06:21","noteId":1294923437772414978,"title":"666","gmtCreate":"2020-08-16 17:06:21","content":"123456"},{"gmtModified":"2020-08-16 17:06:21","noteId":1294923439315918850,"title":"666","gmtCreate":"2020-08-16 17:06:21","content":"123456"},{"gmtModified":"2020-08-16 17:06:21","noteId":1294923440762953730,"title":"666","gmtCreate":"2020-08-16 17:06:21","content":"123456"},{"gmtModified":"2020-08-16 17:06:22","noteId":1294923442528755714,"title":"666","gmtCreate":"2020-08-16 17:06:22","content":"123456"},{"gmtModified":"2020-08-16 17:06:22","noteId":1294923443925458946,"title":"666","gmtCreate":"2020-08-16 17:06:22","content":"123456"},{"gmtModified":"2020-08-16 17:06:24","noteId":1294923451940773890,"title":"666","gmtCreate":"2020-08-16 17:06:24","content":"123456"},{"gmtModified":"2020-08-16 17:06:25","noteId":1294923453777879042,"title":"666","gmtCreate":"2020-08-16 17:06:25","content":"123456"},{"gmtModified":"2020-08-16 17:06:25","noteId":1294923455166193666,"title":"666","gmtCreate":"2020-08-16 17:06:25","content":"123456"},{"gmtModified":"2020-08-16 17:06:26","noteId":1294923460891418626,"title":"666","gmtCreate":"2020-08-16 17:06:26","content":"123456"},{"gmtModified":"2020-08-16 17:06:27","noteId":1294923463064068097,"title":"666","gmtCreate":"2020-08-16 17:06:27","content":"123456"},{"gmtModified":"2020-08-16 17:06:27","noteId":1294923464448188417,"title":"666","gmtCreate":"2020-08-16 17:06:27","content":"123456"},{"gmtModified":"2020-08-16 17:06:29","noteId":1294923472589332481,"title":"666","gmtCreate":"2020-08-16 17:06:29","content":"123456"},{"gmtModified":"2020-08-16 17:06:30","noteId":1294923476527783937,"title":"666","gmtCreate":"2020-08-16 17:06:30","content":"123456"},{"gmtModified":"2020-08-16 17:06:31","noteId":1294923479342161921,"title":"666","gmtCreate":"2020-08-16 17:06:31","content":"123456"},{"gmtModified":"2020-08-16 17:06:31","noteId":1294923481758081025,"title":"666","gmtCreate":"2020-08-16 17:06:31","content":"123456"},{"gmtModified":"2020-08-16 17:06:32","noteId":1294923483981062145,"title":"666","gmtCreate":"2020-08-16 17:06:32","content":"123456"},{"gmtModified":"2020-08-16 17:06:32","noteId":1294923486132740097,"title":"666","gmtCreate":"2020-08-16 17:06:32","content":"123456"},{"gmtModified":"2020-08-16 17:06:33","noteId":1294923487957262338,"title":"666","gmtCreate":"2020-08-16 17:06:33","content":"123456"},{"gmtModified":"2020-08-16 17:06:33","noteId":1294923490046025730,"title":"666","gmtCreate":"2020-08-16 17:06:33","content":"123456"},{"gmtModified":"2020-08-16 17:06:34","noteId":1294923492424196097,"title":"666","gmtCreate":"2020-08-16 17:06:34","content":"123456"},{"gmtModified":"2020-08-16 17:06:34","noteId":1294923495074996225,"title":"666","gmtCreate":"2020-08-16 17:06:34","content":"123456"},{"gmtModified":"2020-08-16 17:06:35","noteId":1294923497335726082,"title":"666","gmtCreate":"2020-08-16 17:06:35","content":"123456"},{"gmtModified":"2020-08-16 17:06:36","noteId":1294923500338847745,"title":"666","gmtCreate":"2020-08-16 17:06:36","content":"123456"},{"gmtModified":"2020-08-16 17:06:36","noteId":1294923503316803586,"title":"666","gmtCreate":"2020-08-16 17:06:36","content":"123456"},{"gmtModified":"2020-08-16 17:06:37","noteId":1294923506022129666,"title":"666","gmtCreate":"2020-08-16 17:06:37","content":"123456"},{"gmtModified":"2020-08-16 17:06:38","noteId":1294923508781981698,"title":"666","gmtCreate":"2020-08-16 17:06:38","content":"123456"},{"gmtModified":"2020-08-16 17:06:38","noteId":1294923510682001409,"title":"666","gmtCreate":"2020-08-16 17:06:38","content":"123456"},{"gmtModified":"2020-08-16 17:06:39","noteId":1294923512879816705,"title":"666","gmtCreate":"2020-08-16 17:06:39","content":"123456"},{"gmtModified":"2020-08-16 17:06:39","noteId":1294923515790663681,"title":"666","gmtCreate":"2020-08-16 17:06:39","content":"123456"},{"gmtModified":"2020-08-16 17:06:40","noteId":1294923518428880897,"title":"666","gmtCreate":"2020-08-16 17:06:40","content":"123456"},{"gmtModified":"2020-08-16 17:06:41","noteId":1294923521192927234,"title":"666","gmtCreate":"2020-08-16 17:06:41","content":"123456"},{"gmtModified":"2020-08-16 17:06:41","noteId":1294923523600457729,"title":"666","gmtCreate":"2020-08-16 17:06:41","content":"123456"},{"gmtModified":"2020-08-16 17:06:42","noteId":1294923525911519234,"title":"666","gmtCreate":"2020-08-16 17:06:42","content":"123456"},{"gmtModified":"2020-08-16 17:06:42","noteId":1294923528696537089,"title":"666","gmtCreate":"2020-08-16 17:06:42","content":"123456"},{"gmtModified":"2020-08-16 17:06:43","noteId":1294923530978238465,"title":"666","gmtCreate":"2020-08-16 17:06:43","content":"123456"},{"gmtModified":"2020-08-16 17:06:44","noteId":1294923533499015170,"title":"666","gmtCreate":"2020-08-16 17:06:44","content":"123456"},{"gmtModified":"2020-08-16 17:06:44","noteId":1294923535734579202,"title":"666","gmtCreate":"2020-08-16 17:06:44","content":"java"},{"gmtModified":"2020-08-16 17:06:45","noteId":1294923538297298946,"title":"666","gmtCreate":"2020-08-16 17:06:45","content":"123456"},{"gmtModified":"2020-08-16 17:06:45","noteId":1294923541006819330,"title":"666","gmtCreate":"2020-08-16 17:06:45","content":"123456"},{"gmtModified":"2020-08-16 17:06:46","noteId":1294923543762477057,"title":"666","gmtCreate":"2020-08-16 17:06:46","content":"1294943754792333314，"},{"gmtModified":"2020-08-16 17:06:47","noteId":1294923546350362625,"title":"666","gmtCreate":"2020-08-16 17:06:47","content":"123456"},{"gmtModified":"2020-08-16 17:06:47","noteId":1294923548674007042,"title":"666","gmtCreate":"2020-08-16 17:06:47","content":"123456"},{"gmtModified":"2020-08-16 17:06:48","noteId":1294923550892793858,"title":"666","gmtCreate":"2020-08-16 17:06:48","content":"123456"},{"gmtModified":"2020-08-16 17:06:48","noteId":1294923553371627522,"title":"666","gmtCreate":"2020-08-16 17:06:48","content":"123456"},{"gmtModified":"2020-08-16 17:06:50","noteId":1294923560065736705,"title":"666","gmtCreate":"2020-08-16 17:06:50","content":"123456"},{"gmtModified":"2020-09-20 10:32:10","noteId":1301890108103524356,"title":"aaa","gmtCreate":"2020-09-20 10:25:07","content":"aaa"},{"gmtModified":"2020-09-21 15:04:14","noteId":1307938671153545218,"title":"this  is title2","gmtCreate":"2020-09-21 15:04:14","content":"this is content2"},{"gmtModified":"2020-09-22 11:45:02","noteId":1308250925916913666,"title":"this  is title2","gmtCreate":"2020-09-22 11:45:02","content":"this is content2"},{"gmtModified":"2020-09-22 11:46:38","noteId":1308251331342532610,"title":"this  is title2","gmtCreate":"2020-09-22 11:46:38","content":"this is content2"},{"gmtModified":"2020-09-24 11:44:29","noteId":1308975053561036802,"title":"this is updated title1","gmtCreate":"2020-09-24 11:42:27","content":"this is updated content1"},{"gmtModified":"2020-09-24 14:48:59","noteId":1309021995632590849,"title":"this  is title2","gmtCreate":"2020-09-24 14:48:59","content":"this is content2"},{"gmtModified":"2020-09-24 14:55:53","noteId":1309023734528114689,"title":"this  is title2","gmtCreate":"2020-09-24 14:55:53","content":"this is content2"},{"gmtModified":"2020-09-28 14:18:51","noteId":1310463966096080898,"title":"this is title1","gmtCreate":"2020-09-28 14:18:51","content":"this is content1"},{"gmtModified":"2020-09-28 14:23:30","noteId":1310465134209744897,"title":"this  is title2","gmtCreate":"2020-09-28 14:23:30","content":"this is content2"},{"gmtModified":"2020-09-28 14:28:25","noteId":1310466372095655937,"title":"this  is title2","gmtCreate":"2020-09-28 14:28:25","content":"this is content2"},{"gmtModified":"2020-09-28 14:29:32","noteId":1310466653009166337,"title":"this  is title2","gmtCreate":"2020-09-28 14:29:32","content":"this is content2"},{"gmtModified":"2020-09-28 15:12:24","noteId":1310477440763248642,"title":"this  is title2","gmtCreate":"2020-09-28 15:12:24","content":"this is content2"},{"gmtModified":"2020-10-07 16:33:30","noteId":1313759342492340226,"title":"this  is title2","gmtCreate":"2020-10-07 16:33:30","content":"this is content2"},{"gmtModified":"2020-10-07 16:33:41","noteId":1313759388688404481,"title":"this  is title2","gmtCreate":"2020-10-07 16:33:41","content":"this is content2"},{"gmtModified":"2020-10-07 16:40:55","noteId":1313761208601104386,"title":"this is title1","gmtCreate":"2020-10-07 16:40:55","content":"this is content1"},{"gmtModified":"2020-10-07 16:43:42","noteId":1313761907388928002,"title":"this  is title2","gmtCreate":"2020-10-07 16:43:42","content":"this is content2"},{"gmtModified":"2020-10-07 16:45:06","noteId":1313762261992165378,"title":"this  is title2","gmtCreate":"2020-10-07 16:45:06","content":"this is content2"},{"gmtModified":"2020-10-07 17:27:57","noteId":1313773042804674561,"title":"this is title1","gmtCreate":"2020-10-07 17:27:57","content":"this is content1"},{"gmtModified":"2020-10-08 13:53:11","noteId":1314081385595228161,"title":"this is title1","gmtCreate":"2020-10-08 13:53:11","content":"this is content1"},{"gmtModified":"2020-10-08 14:41:46","noteId":1314093608443006978,"title":"this  is title2","gmtCreate":"2020-10-08 14:41:46","content":"this is content2"},{"gmtModified":"2020-10-14 20:18:04","noteId":1314098406051500034,"title":"this is update title2","gmtCreate":"2020-10-08 15:00:49","content":"this is update content2"},{"gmtModified":"2020-10-08 15:14:27","noteId":1314101833481539585,"title":"this  is title2","gmtCreate":"2020-10-08 15:14:27","content":"this is content2"},{"gmtModified":"2020-10-09 09:39:00","noteId":1314379804851261442,"title":"this  is title2","gmtCreate":"2020-10-09 09:39:00","content":"this is content2"},{"gmtModified":"2020-10-13 16:26:22","noteId":1315931872170823681,"title":"this  is title2","gmtCreate":"2020-10-13 16:26:22","content":"this is content2"},{"gmtModified":"2020-10-13 22:32:48","noteId":1316024090621595650,"title":"string","gmtCreate":"2020-10-13 22:32:48","content":"string"},{"gmtModified":"2020-10-14 10:32:57","noteId":1316205319581028354,"title":"string","gmtCreate":"2020-10-14 10:32:57","content":"string"},{"gmtModified":"2020-10-14 10:48:41","noteId":1316209279998054401,"title":"string","gmtCreate":"2020-10-14 10:48:41","content":"string"},{"gmtModified":"2020-10-14 11:02:48","noteId":1316212834666500098,"title":"","gmtCreate":"2020-10-14 11:02:48","content":"string"},{"gmtModified":"2020-10-14 11:06:26","noteId":1316213746185228290,"title":"string","gmtCreate":"2020-10-14 11:06:26","content":"string"},{"gmtModified":"2020-10-14 11:07:11","noteId":1316213935100874754,"title":"","gmtCreate":"2020-10-14 11:07:11","content":"string"},{"gmtModified":"2020-10-14 11:11:08","noteId":1316214930883170305,"title":"","gmtCreate":"2020-10-14 11:11:08","content":"string"},{"gmtModified":"2020-10-14 11:11:55","noteId":1316215126010580993,"title":"","gmtCreate":"2020-10-14 11:11:55","content":"string"},{"gmtModified":"2020-10-14 11:12:32","noteId":1316215283326341122,"title":" this.inputtitle","gmtCreate":"2020-10-14 11:12:32","content":"string"},{"gmtModified":"2020-10-14 11:13:47","noteId":1316215597253218305,"title":"","gmtCreate":"2020-10-14 11:13:47","content":"string"},{"gmtModified":"2020-10-14 11:20:31","noteId":1316217291034808322,"title":"123","gmtCreate":"2020-10-14 11:20:31","content":"string"},{"gmtModified":"2020-10-14 15:49:37","noteId":1316285012422381569,"title":"","gmtCreate":"2020-10-14 15:49:37","content":"string"},{"gmtModified":"2020-10-14 18:01:49","noteId":1316318283046801410,"title":"this  is title2","gmtCreate":"2020-10-14 18:01:49","content":"this is content2"},{"gmtModified":"2020-10-14 19:35:24","noteId":1316341831324880897,"title":"this  is title2","gmtCreate":"2020-10-14 19:35:24","content":"this is content2"},{"gmtModified":"2020-10-16 10:50:25","noteId":1316934492205305857,"title":"","gmtCreate":"2020-10-16 10:50:25","content":"string"},{"gmtModified":"2020-10-23 15:09:22","noteId":1319536374497103873,"title":"","gmtCreate":"2020-10-23 15:09:22","content":"string"},{"gmtModified":"2020-10-25 20:20:00","noteId":1320339321254531073,"title":"hello","gmtCreate":"2020-10-25 20:20:00","content":"content"},{"gmtModified":"2020-11-05 16:33:43","noteId":1324268643484913665,"title":"this is title1","gmtCreate":"2020-11-05 16:33:43","content":"this is content1"},{"gmtModified":"2020-11-11 20:21:50","noteId":1326500378759061506,"title":"this is title1","gmtCreate":"2020-11-11 20:21:50","content":"this is content1"}]
         * count : 91
         */

        public int count;
        public List<Notes> notes;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<Notes> getNotes() {
            return notes;
        }

        public void setNotes(List<Notes> notes) {
            this.notes = notes;
        }

        public static class Notes implements Parcelable {
            /**
             * gmtModified : 2020-08-16 16:29:00
             * noteId : 1294914038374985729
             * title : 666
             * gmtCreate : 2020-08-16 16:29:00
             * content : 123456
             */

            private String gmtModified;
            private long noteId;
            private String title;
            private String gmtCreate;
            private String content;

            public Notes(String title,String content,String gmtModified,Long noteId){
                this.content = content;
                this.title = title;
                this.gmtModified = getGmtModified();
                this.noteId = noteId;
            }

            protected Notes(Parcel in) {
                gmtModified = in.readString();
                noteId = in.readLong();
                title = in.readString();
                gmtCreate = in.readString();
                content = in.readString();
            }

            public static final Creator<Notes> CREATOR = new Creator<Notes>() {
                @Override
                public Notes createFromParcel(Parcel in) {
                    return new Notes(in);
                }

                @Override
                public Notes[] newArray(int size) {
                    return new Notes[size];
                }
            };

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

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
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
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(gmtModified);
                parcel.writeLong(noteId);
                parcel.writeString(title);
                parcel.writeString(gmtCreate);
                parcel.writeString(content);
            }
        }
    }

//    private List<DataBean> data;
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * gmtModified : "2020-10-14 19:35:24"
//         * note_id : 1294876087679000577
//         * title : java学习
//         * "gmtCreate": "2020-08-16 16:29:00"
//         * content : null
//         */
//
//        private String gmtModified;
//        private long noteId;
//        private String title;
//        private String content;
//        private String gmtCreate;
//
//        public String getGmtCreate() {
//            return gmtCreate;
//        }
//
//        public void setGmtCreate(String gmtCreate) {
//            this.gmtCreate = gmtCreate;
//        }
//
//        public String getGmtModified() {
//            return gmtModified;
//        }
//
//        public void setGmtModified(String gmtModified) {
//            this.gmtModified = gmtModified;
//        }
//
//        public long getNoteId() {
//            return noteId;
//        }
//
//        public void setNoteId(long noteId) {
//            this.noteId = noteId;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//    }

}

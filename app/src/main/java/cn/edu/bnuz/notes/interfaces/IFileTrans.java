package cn.edu.bnuz.notes.interfaces;

import java.io.File;
import java.util.List;

public interface IFileTrans {
    //获取文件
    String FileDownload(long fileid,String FileName);

    int FileDelete(long fileid);

    //单上传文件
    String FileUpload(File file, long noteid);

    //多文件上传
    int FilesUpload(List<File> files,long noteid);
}

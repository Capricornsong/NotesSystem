package cn.edu.bnuz.notes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import cn.edu.bnuz.notes.impl.FileControllerImpl;
import cn.edu.bnuz.notes.impl.FileTransImpl;
import cn.edu.bnuz.notes.impl.NoteControllerImpl;
import cn.edu.bnuz.notes.impl.ShareControllerImpl;
import cn.edu.bnuz.notes.impl.TagControllerImpl;
import cn.edu.bnuz.notes.impl.TokenControllerImpl;
import cn.edu.bnuz.notes.impl.TreeControllerImpl;
import cn.edu.bnuz.notes.interfaces.IFileController;
import cn.edu.bnuz.notes.interfaces.ITreeController;

public class  NoteService extends Service {
    public NoteService() {
    }

    /**
     * 绑定服务
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        String action  = intent.getAction();
        //判断Action
        if (!TextUtils.isEmpty(action)) {
            if ("com.bnuz.noteservice.ACTION_TOKEN_CONTROLLER".equals(action)) {
                return new TokenControllerImpl();
            } else if ("com.bnuz.noteservice.ACTION_FILE_CONTROLLER".equals(action)) {
                return new FileControllerImpl();
            } else if ("com.bnuz.noteservice.ACTION_NOTE_CONTROLLER".equals(action)) {
                return new NoteControllerImpl();
            } else if ("com.bnuz.noteservice.ACTION_SHARE_CONTROLLER".equals(action)) {
                return new ShareControllerImpl();
            } else if ("com.bnuz.noteservice.ACTION_TAG_CONTROLLER".equals(action)) {
                return new TagControllerImpl();
            }else if ("com.bnuz.noteservice.ACTION_FILETRANS_CONTROLLER".equals(action)){
                return new FileTransImpl();
            } else if ("com.bnuz.noteservice.ACTION_TREE_CONTROLLER".equals(action)){
                return new TreeControllerImpl();
            }
        }
        return null;
    }

}

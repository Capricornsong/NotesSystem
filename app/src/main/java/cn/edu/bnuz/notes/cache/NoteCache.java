package cn.edu.bnuz.notes.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import cn.edu.bnuz.notes.pojo.Note;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static cn.edu.bnuz.notes.MyApplication.threadExecutor;
import static cn.edu.bnuz.notes.utils.util.getAppVersion;


/**
 *
 */
public class NoteCache {
    private static final String TAG = "NoteCache";
    private static DiskLruCache mDiskLruCache;
    private static LruCache mLruCache;
    /**
     * 获取缓存地址
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 初始化DiskLruCache
     * @param context
     */
    public static void initDiskCache(Context context){
        try {
            File cacheDir = getDiskCacheDir(context, "notetest");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存要缓存的数据
     * @param key   唯一标识,此处传入的是NoteId
     */
    public static void saveCacheData(long key) {
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(String.valueOf(key));
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        editor.commit();
//                        mDiskLruCache
//                        if (downloadUrlToStream(key, outputStream)) {
//                            editor.commit();
//                        } else {
//                            editor.abort();
//                        }
                    }
                    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(String.valueOf(key));
                    if(snapShot!=null){
                        InputStream is = snapShot.getInputStream(0);
                        Log.d(TAG, "InputStream: " + is.read());
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * 初始化LruCache
     * 用于缓存缓存
     */
    public static void initCache(){
        int maxMemoryKB = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheMaxSizeKB = maxMemoryKB / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMaxSizeKB) {

            @Override

            protected int sizeOf(String key, Bitmap bitmap) {

                // 必须重写此方法来衡量每张图片的大小，默认返回图片数量。

                return bitmap.getByteCount() / 1024;

            }

        };

    }

}

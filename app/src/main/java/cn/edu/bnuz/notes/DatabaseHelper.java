package cn.edu.bnuz.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    /**
     * @param context   上下文
     * @param name      数据库名称
     * @param factory   游标工厂
     * @param version   版本号
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, constants.DATABASE_NAME, null, constants.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建的时候回调
        Log.d(TAG, "创建数据库");
        //创建表
        //
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //升级数据库的时候回调
        Log.d(TAG, "升级数据库");
    }
}

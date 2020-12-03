package cn.edu.bnuz.notes;

import android.content.Context;

public class Dao {

    private final DatabaseHelper mDatabaseHelper;

    public Dao(Context context){
        mDatabaseHelper = new DatabaseHelper(context);
        mDatabaseHelper.getWritableDatabase();
    }
    public void insert(){

    }
    public void delete(){}
    public void update(){}
    public void query(){}

}

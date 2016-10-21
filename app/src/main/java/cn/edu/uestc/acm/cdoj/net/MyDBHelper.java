package cn.edu.uestc.acm.cdoj.net;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/9/23.
 * 负责查询存储多用户的数据库
 */

public class MyDBHelper extends SQLiteOpenHelper {
    private String TAG = "SQLiteOpenHelperTag";
    private String tableName = "user";
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName  + "( userName VARCHAR(50) NOT NULL, sha1password VARCHAR(50) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<Map<String, Object>> getList(String userName) {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName + (userName==null?"":(" where userName=" + "'" +userName + "'")), null);
        while (cursor.moveToNext()) {
            HashMap map = new HashMap();
            int count = cursor.getColumnCount();
            for (int i = 0; i < count; i++) {
                map.put(cursor.getColumnName(i), cursor.getString(i));
            }
            arrayList.add(map);
        }
        return arrayList;
    }
    public void insert(String userName, String sha1password){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "";
        if (getList(userName).size() == 0){
            sql = "insert into " + tableName + " (userName, sha1password) values " +
                    "(" +  ("'" + userName + "'") + "," + ("'" + sha1password + "'")+ ")";
        }
        else {
            sql = "update " + tableName + " set sha1password=" + ("'" + sha1password + "'") + " where userName=" + "'" +userName + "'";
        }
        Log.d(TAG, "sql:  " +  sql);
        db.execSQL(sql);
    }
    public void delete(String userName){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "delete from " + tableName + (" where userName=" + "'" +userName + "'");
        Log.d(TAG, "sql:  " +  sql);
        db.execSQL(sql);
    }
}
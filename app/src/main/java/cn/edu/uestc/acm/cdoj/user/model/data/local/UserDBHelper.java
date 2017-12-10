package cn.edu.uestc.acm.cdoj.user.model.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lagranmoon on 2017/8/22.
 */

public class UserDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String COMMA_SEP = ",";

    private static final String TEXT_TYPE = " TEXT";

    private static final String DATABASE_NAME = "CDOJ.db";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE"+UserPersistenceContract.UserEntry.TABLENAME+"("+
                    UserPersistenceContract.UserEntry.USERNAME+TEXT_TYPE+" PRIMARY KEY,"+
                    UserPersistenceContract.UserEntry.PASSWORD+TEXT_TYPE+COMMA_SEP+
                    UserPersistenceContract.UserEntry.USERNAME+TEXT_TYPE+COMMA_SEP+
                    UserPersistenceContract.UserEntry.ISLOGIN+TEXT_TYPE+COMMA_SEP
            +")";

    public UserDBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

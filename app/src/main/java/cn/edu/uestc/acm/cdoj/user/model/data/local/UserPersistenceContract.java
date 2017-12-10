package cn.edu.uestc.acm.cdoj.user.model.data.local;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * The contract used for the db to save the userInfo locally.
 * Created by lagranmoon on 2017/8/22.
 */

public final class UserPersistenceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.

    private UserPersistenceContract() {
    }
    /* Inner class that defines the table contents */
    public static abstract class UserEntry  {
        public static final String TABLENAME="user";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String ISLOGIN = "isLogin";
        public static final String EMAIL = "email";
    }
}

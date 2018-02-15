package cn.edu.uestc.acm.cdoj.data.source.repo;

import cn.edu.uestc.acm.cdoj.data.bean.User;
import cn.edu.uestc.acm.cdoj.data.source.local.UserLocalDataSource;
import cn.edu.uestc.acm.cdoj.data.source.remote.UserRemoteDataSource;
import cn.edu.uestc.acm.cdoj.util.NetworkUtil;

/**
 * Created by lagranmoon on 2018/2/14.
 * 存储用户信息的仓库
 */

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE = null;

    private boolean mCacheIsDirty = false;

    private final UserDataSource mUserLocalDataSource;

    private final UserDataSource mUserRemoteDataSource;

    private UserRepository(UserDataSource userLocalDataSource,
                           UserDataSource userRemoteDataSource) {
        mUserLocalDataSource = userLocalDataSource;
        mUserRemoteDataSource = userRemoteDataSource;
    }

    public static UserRepository getInstance(UserLocalDataSource userLocalDataSource,
                                             UserRemoteDataSource userRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(userLocalDataSource, userRemoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public boolean login(String userName, String password) {
        return mUserRemoteDataSource.login(userName, password);
    }

    @Override
    public User getUserInfo(String userName) {
        if (mCacheIsDirty){
           return mUserRemoteDataSource.getUserInfo(userName);
        }else {
           return mUserLocalDataSource.getUserInfo(userName);
        }
    }
}

package cn.edu.uestc.acm.cdoj.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
     * Created by lagranmoon on 2017/8/21.
     * This provides methods to help Activities load their UI.
 * */

public class ActivityUtil {

        /**
         * The {@code fragment} is added to the container view with id {@code containerId}. The operation is
         * performed by the {@code fragmentManager}.
         */

    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
    }

        /**
         * The {@code fragment} will replace the fragment in the container  with id {@code containerId} .
         * The operation is performed by the {@code fragmentManager}.
         */

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

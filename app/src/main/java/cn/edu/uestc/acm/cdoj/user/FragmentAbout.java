package cn.edu.uestc.acm.cdoj.user;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by lagranmoon on 2017/8/10.
 */

public class FragmentAbout extends Fragment {
    private TextView text_version;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        text_version = view.findViewById(R.id.app_version);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_version.setText(getVersionName());
    }

    private String getVersionName() {
        String version_name = "V1.0";
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(getActivity().getPackageName(), 0);
            version_name = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version_name;
    }

}

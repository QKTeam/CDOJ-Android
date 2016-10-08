package cn.edu.uestc.acm.cdoj.ui.statusBar;

import java.io.IOException;

/**
 * Created by fitzz on 16-8-23.
 */
public final class MIUIUtils {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI(){
        try {
            final BuildProperties properties = BuildProperties.newInstance();
            return properties.getProperty(KEY_MIUI_VERSION_NAME,null) != null
                    || properties.getProperty(KEY_MIUI_VERSION_CODE,null) != null
                    || properties.getProperty(KEY_MIUI_INTERNAL_STORAGE,null) != null;
        } catch (final IOException e) {
            return false;
        }
    }
}

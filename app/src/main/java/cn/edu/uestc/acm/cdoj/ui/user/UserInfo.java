package cn.edu.uestc.acm.cdoj.ui.user;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.UserManager;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Grea on 2016/10/16.
 */

public class UserInfo implements ViewHandler{

    private int departmentId;
    private String email;
    private int grade;
    private String motto;
    private String name;
    private String nickName;
    private String phone;
    private String school;
    private int sex;
    private int size;
    private String studentId;
    private int type;
    private String userName;
    private Bitmap header;
    private Map<String, Object> userInfoMap;
    private SynListener listener;
    private boolean hasSetListener;

    public interface SynListener{
        public void synInfo(UserInfo user);

        public void synHeaderImage(Bitmap header);

    }

    public UserInfo() {
    }

    public UserInfo(Map<String, Object> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

    public int getDepartmentId() {
        return (int) userInfoMap.get("departmentId");
    }

    public String getEmail() {
        return (String) userInfoMap.get("email");
    }

    public int getGrade() {
        return (int) userInfoMap.get("grade");
    }

    public String getMotto() {
        return (String) userInfoMap.get("motto");
    }

    public String getName() {
        return (String) userInfoMap.get("name");
    }

    public String getNickName() {
        return (String) userInfoMap.get("nickName");
    }

    public String getPhone() {
        return (String) userInfoMap.get("phone");
    }

    public String getSchool() {
        return (String) userInfoMap.get("school");
    }

    public int getSex() {
        return (int) userInfoMap.get("sex");
    }

    public int getSize() {
        return (int) userInfoMap.get("size");
    }

    public String getStudentId() {
        return (String) userInfoMap.get("studentId");
    }

    public int getType() {
        return (int) userInfoMap.get("type");
    }

    public String getUserName() {
        return (String) userInfoMap.get("userName");
    }

    public Bitmap getHeader() {
        return header;
    }

    public Map<String, Object> getUserInfoMap() {
        return userInfoMap;
    }

    public void setSynListener(SynListener synListener) {
        listener = synListener;
        hasSetListener = true;
        if (userInfoMap != null) {
            listener.synInfo(this);
        }
        if (header != null) {
            listener.synHeaderImage(header);
        }
    }

    public void removeSynListener() {
        listener = null;
        hasSetListener = false;
    }

    @Override
    public void show(int which, Result result, long time) {
        switch (which) {
            case ViewHandler.USER_PROFILE:
                if (result.result) {
                    userInfoMap = (Map<String, Object>) ((Map<String, Object>) result.getContent()).get("user");
                    if (listener != null) {
                        listener.synInfo(this);
                    }
                    writeInfoToLocalFile();
                    Global.userManager.getAvatar(String.valueOf(userInfoMap.get("email")), null, this);
                }
                break;
            case ViewHandler.AVATAR:
                header = (Bitmap) result.getContent();
                if (listener != null) {
                    listener.synHeaderImage(header);
                }
                userInfoMap.put("header", header);
                break;
        }
    }

    private void writeInfoToLocalFile() {
        try {
            File file = new File(Global.filesDirPath + "/userInfo");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(String.valueOf(getDepartmentId()));
            writer.write("\n");
            writer.write(getEmail());
            writer.write("\n");
            writer.write(String.valueOf(getGrade()));
            writer.write("\n");
            writer.write(getMotto());
            writer.write("\n");
            writer.write(getName());
            writer.write("\n");
            writer.write(getNickName());
            writer.write("\n");
            writer.write(getPhone());
            writer.write("\n");
            writer.write(getSchool());
            writer.write("\n");
            writer.write(String.valueOf(getSex()));
            writer.write("\n");
            writer.write(String.valueOf(getSize()));
            writer.write("\n");
            writer.write(getStudentId());
            writer.write("\n");
            writer.write(String.valueOf(getType()));
            writer.write("\n");
            writer.write(getUserName());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

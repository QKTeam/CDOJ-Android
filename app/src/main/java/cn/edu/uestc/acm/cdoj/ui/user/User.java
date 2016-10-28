package cn.edu.uestc.acm.cdoj.ui.user;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Grea on 2016/10/25.
 */

public class User {
    private String email;
    private String result;
    private String userName;
    private String passwordSHA1;
    private int type;
    private int size;
    private int sex;
    private int grade;
    private int departmentId;
    private String studentId;
    private String school;
    private String phone;
    private String nickName;
    private String name;
    private String motto;
    private BitmapDrawable avatar;

    public User() {}

    public User(String userName, String passwordSHA1) {
        this.userName = userName;
        this.passwordSHA1 = passwordSHA1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPasswordSHA1() {
        return passwordSHA1;
    }

    public void setPasswordSHA1(String passwordSHA1) {
        this.passwordSHA1 = passwordSHA1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public BitmapDrawable getAvatar() {
        return avatar;
    }

    public void setAvatar(BitmapDrawable avatar) {
        this.avatar = avatar;
    }
}

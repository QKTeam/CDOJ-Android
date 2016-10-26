package cn.edu.uestc.acm.cdoj.ui.user;

/**
 * Created by Grea on 2016/10/25.
 */

public class User {
    private String email;
    private String result;
    private String userName;
    private String passwordSHA1;
    private Integer type;
    private Integer size;
    private Integer sex;
    private Integer grade;
    private Integer departmentId;
    private String studentId;
    private String school;
    private String phone;
    private String nickName;
    private String name;
    private String motto;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPasswordSHA1() {
        return passwordSHA1;
    }

    public void setPasswordSHA1(String passwordSHA1) {
        this.passwordSHA1 = passwordSHA1;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
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
}

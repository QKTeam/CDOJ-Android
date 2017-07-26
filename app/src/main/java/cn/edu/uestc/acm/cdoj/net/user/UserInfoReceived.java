package cn.edu.uestc.acm.cdoj.net.user;

/**
 * Created by lagranmoon on 2017/7/25.
 */

public class UserInfoReceived {

    /**
     * result : success
     * user : {"departmentId":3,"email":"tanliqun1999@outlook.com","grade":3,"motto":"","name":"檀利群","nickName":"檀利群","phone":"18156907253","school":"UESTC","sex":0,"size":2,"studentId":"2016100104001","type":0,"userName":"2016100104001"}
     */

    private String result;
    private UserBean user;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * departmentId : 3
         * email : tanliqun1999@outlook.com
         * grade : 3
         * motto :
         * name : 檀利群
         * nickName : 檀利群
         * phone : 18156907253
         * school : UESTC
         * sex : 0
         * size : 2
         * studentId : 2016100104001
         * type : 0
         * userName : 2016100104001
         */

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

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}

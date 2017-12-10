package cn.edu.uestc.acm.cdoj.user.model.bean;

import java.util.List;

/**
 * Created by lagranmoon on 2017/7/23.
 * 登录之后返回的状态信息
 */

public class LoginResponse {

    /**
     * 登录成功返回的Json
     * email : tanliqun1999@outlook.com
     * result : success
     * type : 0
     * userName : 2016100104001
     */

    private String email;
    private String result;
    private int type;
    private String userName;

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

    /**
     * 登录失败返回的Json
     * field : [{"bindingFailure":false,"defaultMessage":"User or password is wrong, please try again","field":"password","objectName":"password"}]
     * result : field_error
     */

//    private String result;
    private List<LoginResponse.FieldBean> field;

//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }

    public List<LoginResponse.FieldBean> getField() {
        return field;
    }

    public void setField(List<LoginResponse.FieldBean> field) {
        this.field = field;
    }

    public static class FieldBean {
        /**
         * bindingFailure : false
         * defaultMessage : User or password is wrong, please try again
         * field : password
         * objectName : password
         */

        private boolean bindingFailure;
        private String defaultMessage;
        private String field;
        private String objectName;

        public boolean isBindingFailure() {
            return bindingFailure;
        }

        public void setBindingFailure(boolean bindingFailure) {
            this.bindingFailure = bindingFailure;
        }

        public String getDefaultMessage() {
            return defaultMessage;
        }

        public void setDefaultMessage(String defaultMessage) {
            this.defaultMessage = defaultMessage;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }
    }
}

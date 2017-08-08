package cn.edu.uestc.acm.cdoj.net.user;

import java.util.List;

/**
 * Created by lagranmoon on 2017/8/8.
 */

public class UserNameError {

    /**
     * field : [{"bindingFailure":false,"defaultMessage":"User name has been used!","field":"userName","objectName":"userName"}]
     * result : field_error
     */

    private String result;
    private List<FieldBean> field;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<FieldBean> getField() {
        return field;
    }

    public void setField(List<FieldBean> field) {
        this.field = field;
    }

    public static class FieldBean {
        /**
         * bindingFailure : false
         * defaultMessage : User name has been used!
         * field : userName
         * objectName : userName
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

package cn.edu.uestc.acm.cdoj.user.model.bean;

import java.util.List;

/**
 * Created by lagranmoon on 2017/8/9.
 */

public class EditResponse {
    /**
     * field : [{"bindingFailure":false,"defaultMessage":"Your password is wrong, please try again.","field":"oldPassword","objectName":"oldPassword"}]
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
         * defaultMessage : Your password is wrong, please try again.
         * field : oldPassword
         * objectName : oldPassword
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

package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by qwe on 16-9-12.
 */
public class Result <T> {
    public final static int OK = 0, NETWORK_ERROR = 1, CONTENT_ERROR = 2;
    public int resultType = OK;
    public boolean result = false;
    public String resultString, errorMsg = "if you see this, it represents this response doesn't contain errorMsg!";
    T content;
    Object extra = null;
    public Result(String json, Class cls, Class inner) {
        if (json == null) {
            resultType = NETWORK_ERROR;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            resultString = jsonObject.getString("result");
            result = resultString.equals("success");
            errorMsg = jsonObject.optString("error_msg", errorMsg);
            if (cls != null){
                if (inner != null){
                    Constructor c1 = cls.getDeclaredConstructor(new Class[]{JSONObject.class, Class.class});
                    content = (T)c1.newInstance(jsonObject, inner);
                }
                else {
                    Constructor c1 = cls.getDeclaredConstructor(new Class[]{JSONObject.class});
                    content = (T)c1.newInstance(jsonObject);
                }
            }
        } catch (JSONException e) {
            resultType = CONTENT_ERROR;
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public Result(String json, Class cls){
        this(json, cls, null);
    }

    public T getContent(){
        return content;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}

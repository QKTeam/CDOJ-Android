package cn.edu.uestc.acm.cdoj.net.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.uestc.acm.cdoj.net.JsonUtils;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;


/**
 * Created by qwe on 16-9-12.
 */
public class Result {
    private final static String TAG = "ResultTag";
    public final static int STATE_OK = 0, STATE_NETWORK_ERROR = 1, STATE_CONTENT_ERROR = 2;
    public boolean result = false;
    public int resultType = STATE_OK;
    public String resultString, errorMsg = "if you see this, it represents this response doesn't contain errorMsg!";

    Object content;
    Object extra = null;
    public Result( int which, String json) {
        Log.d(TAG, "which:" + which + "  Result: " + json);
        if (json == null) {
            resultType = STATE_NETWORK_ERROR;
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            resultString = jsonObject.getString("result");
            if (!resultString.equals("success")){
                resultType = STATE_CONTENT_ERROR;
                errorMsg = jsonObject.optString("error_msg", errorMsg);
            }
            else{
                result = true;
                switch (which){
                    case ViewHandler.PROBLEM_LIST:
                    case ViewHandler.ARTICLE_LIST:
                    case ViewHandler.CONTEST_LIST:
                    case ViewHandler.STATUS_LIST:
                    case ViewHandler.CONTEST_COMMENT:
                        content = new InfoList(jsonObject);
                        break;
                    case ViewHandler.PROBLEM_DETAIL:
                        content = new Problem(jsonObject);
                        break;
                    case ViewHandler.CONTEST_DETAIL:
                        content = new Contest(jsonObject);
                        break;
                    case ViewHandler.ARTICLE_DETAIL:
                        content = new Article(jsonObject);
                        break;
                    case ViewHandler.CONTEST_RANK:
                    case ViewHandler.STATUS_INFO:
                    case ViewHandler.USER_PROFILE:
                    case ViewHandler.USER_TYPE_AHEAD_ITEM:
                    case ViewHandler.USER_CENTER_DATA:
                        content = JsonUtils.getMapFromJson(jsonObject);
                        break;
                    default:;
                }
            }


        } catch (JSONException e) {
            resultType = STATE_CONTENT_ERROR;
            e.printStackTrace();
        }
    }
    public Result(Object content, Object extra){
        if (content == null){
            resultType = STATE_NETWORK_ERROR;
        }
        this.content = content;
        this.extra = extra;
    }
    public Result(Object content){
        this(content, null);
    }
    /*
    public Result(String json, Class cls){
        this(json, cls, null);
    }*/

    public Object getContent(){
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}

package cn.edu.uestc.acm.cdoj.net.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.uestc.acm.cdoj.net.ViewHandler;


/**
 * Created by qwe on 16-9-12.
 */
public class Result {
    private final static String TAG = "ResultTag";
    public final static int STATE_OK = 0, STATE_NETWORK_ERROR = 1, STATE_CONTENT_ERROR = 2;
    public int resultType = STATE_OK;
    public String resultString, errorMsg = "if you see this, it represents this response doesn't contain errorMsg!";

    Object content;
    Object extra = null;
    public Result( int which, String json) {
        if (json == null) {
            resultType = STATE_NETWORK_ERROR;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            resultString = jsonObject.getString("result");
            if (!resultString.equals("success")){
                resultType = STATE_CONTENT_ERROR;
                errorMsg = jsonObject.optString("error_msg", errorMsg);
            }
            else{
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
                        content = new Rank(jsonObject);
                        break;
                    case ViewHandler.STATUS_INFO:
                        content =  Status.getCode(jsonObject);
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
        this.content = content;
        this.extra = extra;
    }
    public Result(Object content){
        this.content = content;
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

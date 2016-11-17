package cn.edu.uestc.acm.cdoj.ui.user;

/**
 * Created by 13662 on 2016/11/17.
 */

public class UserProblemsStatus {

    private String userName;
    private String passwordSHA1;
    private int[] problemsStatus;

    public UserProblemsStatus(){
        //暂时还没有数据，以下为测试临时使用
        problemsStatus = new int[1493];
        for(int i = 0;i < problemsStatus.length;i++){
            problemsStatus[i] = 0;
        }
        problemsStatus[9] = 1;
        problemsStatus[16] = 2;
    }

    public UserProblemsStatus(String userName, String passwordSHA1){
        this.userName = userName;
        this.passwordSHA1 = passwordSHA1;
    }

    public int getProblemsStatus(int position){
        return problemsStatus[position];
    }
    public void setProblemsStatus(int position,int status){problemsStatus[position] = status;}
    public void setUserName(String userName){this.userName = userName;}
    public void setPasswordSHA1(String passwordSHA1){this.passwordSHA1 = passwordSHA1;}
}

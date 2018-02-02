package cn.edu.uestc.acm.cdoj.data.bean;

import java.util.List;

/**
 * Created by lagranmoon on 2018/2/1.
 * 按照给定条件返回的符合条件项组成的列表
 */

public class ListResponse<T> extends ResposeSuccess {

    public List<T> list;

}

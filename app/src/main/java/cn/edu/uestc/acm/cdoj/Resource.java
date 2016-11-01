package cn.edu.uestc.acm.cdoj;

import android.graphics.drawable.BitmapDrawable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.List;

import cn.edu.uestc.acm.cdoj.ui.user.User;


/**
 * Created by great on 2016/8/22.
 */
public class Resource {

    public static Object tem;

    private static boolean isTwoPane;
    private static User user;
    /**
     * HTML数据
     * */
    private static String htmlData_article;
    private static String htmlData_problem;
    private static String htmlData_contest;
    /**
     * 默认颜色矩阵和默认Logo图标
     * */
    private static float[] mainColorMatrix;
    private static BitmapDrawable defaultLogo;
    /**
     * 参赛者题目完成状态图标
     */
    private static BitmapDrawable rankIcon_didNothing;
    private static BitmapDrawable rankIcon_tried;
    private static BitmapDrawable rankIcon_solved;
    private static BitmapDrawable rankIcon_theFirstSolved;
    /**
     * 可上拉、下滑加载内容的ListVi的底部状态图标
     */
    private static BitmapDrawable listIcon_up;
    private static BitmapDrawable listFooterIcon_done;
    private static BitmapDrawable listFooterIcon_noData;
    private static BitmapDrawable listFooterIcon_problem;
    private static BitmapDrawable listFooterIcon_netProblem;
    /**
     * 主列表的Tab图标
     */
    private static BitmapDrawable noticeListIcon_selected;
    private static BitmapDrawable noticeListIcon_unselect;
    private static BitmapDrawable problemListIcon_selected;
    private static BitmapDrawable problemListIcon_unselect;
    private static BitmapDrawable contestListIcon_selected;
    private static BitmapDrawable contestListIcon_unselect;
    /**
     * 问题详情页按钮图标
     * */
    private static BitmapDrawable problemIcon_addCode;
    private static BitmapDrawable problemIcon_checkResult;
    /**
     * 搜索记录
     * */
    private static List<SearchSuggestion> problemSearchHistory;
    private static List<SearchSuggestion> contestSearchHistory;
    /**
     * 文件和缓存文件夹地址
     * */
    private static String filesDirPath;
    private static String cacheDirPath;

    public static boolean isTwoPane() {
        return isTwoPane;
    }

    public static void setIsTwoPane(boolean isTwoPane) {
        Resource.isTwoPane = isTwoPane;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Resource.user = user;
    }

    public static String getHtmlData_article() {
        return htmlData_article;
    }

    static void setHtmlData_article(String htmlData_article) {
        Resource.htmlData_article = htmlData_article;
    }

    public static String getHtmlData_problem() {
        return htmlData_problem;
    }

    static void setHtmlData_problem(String htmlData_problem) {
        Resource.htmlData_problem = htmlData_problem;
    }

    public static String getHtmlData_contest() {
        return htmlData_contest;
    }

    static void setHtmlData_contest(String htmlData_contest) {
        Resource.htmlData_contest = htmlData_contest;
    }

    public static float[] getMainColorMatrix() {
        return mainColorMatrix;
    }

    static void setMainColorMatrix(float[] mainColorMatrix) {
        Resource.mainColorMatrix = mainColorMatrix;
    }

    public static BitmapDrawable getDefaultLogo() {
        return defaultLogo;
    }

    static void setDefaultLogo(BitmapDrawable defaultLogo) {
        Resource.defaultLogo = defaultLogo;
    }

    public static BitmapDrawable getRankIcon_didNothing() {
        return rankIcon_didNothing;
    }

    static void setRankIcon_didNothing(BitmapDrawable rankIcon_didNothing) {
        Resource.rankIcon_didNothing = rankIcon_didNothing;
    }

    public static BitmapDrawable getRankIcon_tried() {
        return rankIcon_tried;
    }

    static void setRankIcon_tried(BitmapDrawable rankIcon_tried) {
        Resource.rankIcon_tried = rankIcon_tried;
    }

    public static BitmapDrawable getRankIcon_solved() {
        return rankIcon_solved;
    }

    static void setRankIcon_solved(BitmapDrawable rankIcon_solved) {
        Resource.rankIcon_solved = rankIcon_solved;
    }

    public static BitmapDrawable getRankIcon_theFirstSolved() {
        return rankIcon_theFirstSolved;
    }

    static void setRankIcon_theFirstSolved(BitmapDrawable rankIcon_theFirstSolved) {
        Resource.rankIcon_theFirstSolved = rankIcon_theFirstSolved;
    }

    public static BitmapDrawable getListFooterIcon_done() {
        return listFooterIcon_done;
    }

    static void setListFooterIcon_done(BitmapDrawable listFooterIcon_done) {
        Resource.listFooterIcon_done = listFooterIcon_done;
    }

    public static BitmapDrawable getListFooterIcon_noData() {
        return listFooterIcon_noData;
    }

    static void setListFooterIcon_noData(BitmapDrawable listFooterIcon_noData) {
        Resource.listFooterIcon_noData = listFooterIcon_noData;
    }

    public static BitmapDrawable getListFooterIcon_problem() {
        return listFooterIcon_problem;
    }

    static void setListFooterIcon_problem(BitmapDrawable listFooterIcon_problem) {
        Resource.listFooterIcon_problem = listFooterIcon_problem;
    }

    public static BitmapDrawable getListFooterIcon_netProblem() {
        return listFooterIcon_netProblem;
    }

    static void setListFooterIcon_netProblem(BitmapDrawable listFooterIcon_netProblem) {
        Resource.listFooterIcon_netProblem = listFooterIcon_netProblem;
    }

    public static List<SearchSuggestion> getProblemSearchHistory() {
        return problemSearchHistory;
    }

    static void setProblemSearchHistory(List<SearchSuggestion> problemSearchHistory) {
        Resource.problemSearchHistory = problemSearchHistory;
    }

    public static List<SearchSuggestion> getContestSearchHistory() {
        return contestSearchHistory;
    }

    static void setContestSearchHistory(List<SearchSuggestion> contestSearchHistory) {
        Resource.contestSearchHistory = contestSearchHistory;
    }

    public static String getFilesDirPath() {
        return filesDirPath;
    }

    static void setFilesDirPath(String filesDirPath) {
        Resource.filesDirPath = filesDirPath;
    }

    public static String getCacheDirPath() {
        return cacheDirPath;
    }

    static void setCacheDirPath(String cacheDirPath) {
        Resource.cacheDirPath = cacheDirPath;
    }

    public static BitmapDrawable getListIcon_up() {
        return listIcon_up;
    }

    static void setListIcon_up(BitmapDrawable listIcon_up) {
        Resource.listIcon_up = listIcon_up;
    }

    public static BitmapDrawable getNoticeListIcon_selected() {
        return noticeListIcon_selected;
    }

    public static void setNoticeListIcon_selected(BitmapDrawable noticeListIcon_selected) {
        Resource.noticeListIcon_selected = noticeListIcon_selected;
    }

    public static BitmapDrawable getNoticeListIcon_unselect() {
        return noticeListIcon_unselect;
    }

    public static void setNoticeListIcon_unselect(BitmapDrawable noticeListIcon_unselect) {
        Resource.noticeListIcon_unselect = noticeListIcon_unselect;
    }

    public static BitmapDrawable getProblemListIcon_selected() {
        return problemListIcon_selected;
    }

    public static void setProblemListIcon_selected(BitmapDrawable problemListIcon_selected) {
        Resource.problemListIcon_selected = problemListIcon_selected;
    }

    public static BitmapDrawable getProblemListIcon_unselect() {
        return problemListIcon_unselect;
    }

    public static void setProblemListIcon_unselect(BitmapDrawable problemListIcon_unselect) {
        Resource.problemListIcon_unselect = problemListIcon_unselect;
    }

    public static BitmapDrawable getContestListIcon_selected() {
        return contestListIcon_selected;
    }

    public static void setContestListIcon_selected(BitmapDrawable contestListIcon_selected) {
        Resource.contestListIcon_selected = contestListIcon_selected;
    }

    public static BitmapDrawable getContestListIcon_unselect() {
        return contestListIcon_unselect;
    }

    public static void setContestListIcon_unselect(BitmapDrawable contestListIcon_unselect) {
        Resource.contestListIcon_unselect = contestListIcon_unselect;
    }

    public static BitmapDrawable getProblemIcon_addCode() {
        return problemIcon_addCode;
    }

    public static void setProblemIcon_addCode(BitmapDrawable problemIcon_addCode) {
        Resource.problemIcon_addCode = problemIcon_addCode;
    }

    public static BitmapDrawable getProblemIcon_checkResult() {
        return problemIcon_checkResult;
    }

    public static void setProblemIcon_checkResult(BitmapDrawable problemIcon_checkResult) {
        Resource.problemIcon_checkResult = problemIcon_checkResult;
    }
}

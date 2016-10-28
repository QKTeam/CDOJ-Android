package cn.edu.uestc.acm.cdoj.ui.modules;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistory;
import cn.edu.uestc.acm.cdoj.ui.user.User;


/**
 * Created by great on 2016/8/22.
 */
public class Global {

    public static Object tem;

    private static Activity currentMainUIActivity;
    private static boolean isTwoPane;
    private static User user;

    private static String HTMLDATA_ARTICLE;
    private static String HTMLDATA_PROBLEM;
    private static String HTMLDATA_CONTEST;

    private static float[] mainColorMatrix;
    private static BitmapDrawable defaultLogo;

    private static BitmapDrawable rankIcon_didNothing;
    private static BitmapDrawable rankIcon_tried;
    private static BitmapDrawable rankIcon_solved;
    private static BitmapDrawable rankIcon_theFirstSolved;

    private static BitmapDrawable listIcon_up;
    private static BitmapDrawable listFooterIcon_done;
    private static BitmapDrawable listFooterIcon_noData;
    private static BitmapDrawable listFooterIcon_problem;

    private static BitmapDrawable listFooterIcon_netProblem;
    private static ArrayList<SearchHistory> problemSearchHistory;

    private static ArrayList<SearchHistory> contestSearchHistory;

    private static String filesDirPath;
    private static String cacheDirPath;

    public static Activity getCurrentMainUIActivity() {
        return currentMainUIActivity;
    }

    public static void setCurrentMainUIActivity(Activity currentMainUIActivity) {
        Global.currentMainUIActivity = currentMainUIActivity;
    }

    public static boolean isTwoPane() {
        return isTwoPane;
    }

    public static void setIsTwoPane(boolean isTwoPane) {
        Global.isTwoPane = isTwoPane;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Global.user = user;
    }

    public static String getHtmldataArticle() {
        return HTMLDATA_ARTICLE;
    }

    public static void setHtmldataArticle(String htmldataArticle) {
        HTMLDATA_ARTICLE = htmldataArticle;
    }

    public static String getHtmldataProblem() {
        return HTMLDATA_PROBLEM;
    }

    public static void setHtmldataProblem(String htmldataProblem) {
        HTMLDATA_PROBLEM = htmldataProblem;
    }

    public static String getHtmldataContest() {
        return HTMLDATA_CONTEST;
    }

    public static void setHtmldataContest(String htmldataContest) {
        HTMLDATA_CONTEST = htmldataContest;
    }

    public static float[] getMainColorMatrix() {
        return mainColorMatrix;
    }

    public static void setMainColorMatrix(float[] mainColorMatrix) {
        Global.mainColorMatrix = mainColorMatrix;
    }

    public static BitmapDrawable getDefaultLogo() {
        return defaultLogo;
    }

    public static void setDefaultLogo(BitmapDrawable defaultLogo) {
        Global.defaultLogo = defaultLogo;
    }

    public static BitmapDrawable getRankIcon_didNothing() {
        return rankIcon_didNothing;
    }

    public static void setRankIcon_didNothing(BitmapDrawable rankIcon_didNothing) {
        Global.rankIcon_didNothing = rankIcon_didNothing;
    }

    public static BitmapDrawable getRankIcon_tried() {
        return rankIcon_tried;
    }

    public static void setRankIcon_tried(BitmapDrawable rankIcon_tried) {
        Global.rankIcon_tried = rankIcon_tried;
    }

    public static BitmapDrawable getRankIcon_solved() {
        return rankIcon_solved;
    }

    public static void setRankIcon_solved(BitmapDrawable rankIcon_solved) {
        Global.rankIcon_solved = rankIcon_solved;
    }

    public static BitmapDrawable getRankIcon_theFirstSolved() {
        return rankIcon_theFirstSolved;
    }

    public static void setRankIcon_theFirstSolved(BitmapDrawable rankIcon_theFirstSolved) {
        Global.rankIcon_theFirstSolved = rankIcon_theFirstSolved;
    }

    public static BitmapDrawable getListFooterIcon_done() {
        return listFooterIcon_done;
    }

    public static void setListFooterIcon_done(BitmapDrawable listFooterIcon_done) {
        Global.listFooterIcon_done = listFooterIcon_done;
    }

    public static BitmapDrawable getListFooterIcon_noData() {
        return listFooterIcon_noData;
    }

    public static void setListFooterIcon_noData(BitmapDrawable listFooterIcon_noData) {
        Global.listFooterIcon_noData = listFooterIcon_noData;
    }

    public static BitmapDrawable getListFooterIcon_problem() {
        return listFooterIcon_problem;
    }

    public static void setListFooterIcon_problem(BitmapDrawable listFooterIcon_problem) {
        Global.listFooterIcon_problem = listFooterIcon_problem;
    }

    public static BitmapDrawable getListFooterIcon_netProblem() {
        return listFooterIcon_netProblem;
    }

    public static void setListFooterIcon_netProblem(BitmapDrawable listFooterIcon_netProblem) {
        Global.listFooterIcon_netProblem = listFooterIcon_netProblem;
    }

    public static ArrayList<SearchHistory> getProblemSearchHistory() {
        return problemSearchHistory;
    }

    public static void setProblemSearchHistory(ArrayList<SearchHistory> problemSearchHistory) {
        Global.problemSearchHistory = problemSearchHistory;
    }

    public static ArrayList<SearchHistory> getContestSearchHistory() {
        return contestSearchHistory;
    }

    public static void setContestSearchHistory(ArrayList<SearchHistory> contestSearchHistory) {
        Global.contestSearchHistory = contestSearchHistory;
    }

    public static String getFilesDirPath() {
        return filesDirPath;
    }

    public static void setFilesDirPath(String filesDirPath) {
        Global.filesDirPath = filesDirPath;
    }

    public static String getCacheDirPath() {
        return cacheDirPath;
    }

    public static void setCacheDirPath(String cacheDirPath) {
        Global.cacheDirPath = cacheDirPath;
    }

    public static BitmapDrawable getListIcon_up() {
        return listIcon_up;
    }

    public static void setListIcon_up(BitmapDrawable listIcon_up) {
        Global.listIcon_up = listIcon_up;
    }
}

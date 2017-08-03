package cn.edu.uestc.acm.cdoj.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by lagranmoon on 2017/8/1.
 */

public class FileUtil {
    public static void saveFile(Context context,String path,String content,String fileName){
        File file_dir = new File(context.getFilesDir()+"/"+path);
        File file = new File(file_dir,fileName);
        if (!file_dir.exists()){
            file_dir.mkdirs();
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(file));
            printWriter.write(content);
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void saveUserInfo(Context context,String content,String fileName){
         saveFile(context,"UserInfo",content,fileName);
    }
    public static void saveArticle(Context context,String content,String fileName){
        saveFile(context,"Article",content,fileName);
    }
    public static void saveProblem(Context context,String content,String fileName){
        saveFile(context,"Problem",content,fileName);
    }
    public static void saveContest(Context context,String content,String fileName){
        saveFile(context,"Contest",content,fileName);
    }
    public static String readFile(Context context,String path,String fileName){
        File file = new File(context.getFilesDir()+"/"+path+"/"+fileName);
        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()){
                stringBuilder.append(scanner.nextLine());
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}

package cn.edu.uestc.acm.cdoj.net;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * Created by qwe on 16-10-11.
 */

public class ThreadTools {
    private static Handler handler;
    static {
        handler = new Handler(Looper.getMainLooper());
    }
    private static ArrayBlockingQueue<TaskStruct> taskQueue = new ArrayBlockingQueue<>(100);
    private static Thread queueThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true){
                try {
                    TaskStruct taskStruct = taskQueue.take();
                    if (taskStruct.inQueue){
                        taskStruct.runnable.run();
                    }
                    else {
                        executeOnThreadPool(taskStruct.runnable);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    static {
        queueThread.start();
    }

    private static ArrayList<Thread> threadPool = new ArrayList<>();
    static ArrayBlockingQueue<Runnable> taskPool = new ArrayBlockingQueue<Runnable>(100);
    static Integer busyThreadCount = 0, maxThreadCount = 20;

    public static void runOnMainThread(Runnable runnable){
        handler.post(runnable);
    }
    public static class Task<Request, Result>{
        Result doInBackGround(Request request){
            return null;
        }
        void onPostExecute(Result result) {
        }
        void execute(boolean inQueue){
            try {
                taskQueue.put(new TaskStruct(inQueue, new Runnable() {
                    @Override
                    public void run() {
                        final Result result = doInBackGround(null);
                        runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                onPostExecute(result);
                            }
                        });
                    }
                }));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    static void executeOnThreadPool(Runnable runnable){
        synchronized (busyThreadCount){
            if (busyThreadCount >= threadPool.size() && threadPool.size() <= maxThreadCount){
                Thread thread;
                threadPool.add(thread = new Thread(){
                    @Override
                    public void run() {
                        while (true){
                            try {
                                Runnable r = taskPool.take();
                                busyThreadCount ++;
                                r.run();
                                busyThreadCount --;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                thread.start();
            }
        }
        try {
            taskPool.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class TaskStruct{
        boolean inQueue;
        Runnable runnable;
        public TaskStruct(boolean inQueue, Runnable runnable) {
            this.inQueue = inQueue;
            this.runnable = runnable;
        }
    }
}

package com.thinking.keep_alive;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Yu Yong on 2018/5/8.
 */

public class MyApp extends Application {

    public static String ProcessName = "";

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        ProcessName = getAppName(this, pid);
    }

    private static String getAppName(Context context, int pid) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    return info.processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

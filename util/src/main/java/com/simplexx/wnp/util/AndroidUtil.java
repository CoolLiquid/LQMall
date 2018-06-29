package com.simplexx.wnp.util;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class AndroidUtil {
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    //获取包的名称
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // Do Nothing
        }
        return info;
    }

    //获取 app 的版本号
    public static int getVersionCode(Context context) {
        PackageInfo info = getPackageInfo(context);
        int version = 0;
        if (info != null)
            version = info.versionCode;
        return version;
    }

    //获取 app 的版本名
    public static String getVersionName(Context context) {
        PackageInfo info = getPackageInfo(context);
        String version = null;
        if (info != null)
            version = info.versionName;
        return version;
    }

    public static boolean isOpenNotification(Context context) {
        try {
            String CHECK_OP_NO_THROW = "checkOpNoThrow";
            String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            Class appOpsClass = null;
            /* Context.APP_OPS_MANAGER */
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (NoClassDefFoundError e){
            e.printStackTrace();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return "";
    }


}


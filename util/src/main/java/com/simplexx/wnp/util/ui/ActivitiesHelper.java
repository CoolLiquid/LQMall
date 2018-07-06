package com.simplexx.wnp.util.ui;

import android.app.Activity;

import com.simplexx.wnp.baselib.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wnp on 2018/7/6.
 */

public class ActivitiesHelper {
    private static Activity lastResumeActivity;
    private static final LinkedList<Activity> activities = new LinkedList<>();

    private static void finish(Activity activity) {
        if (activity != null) {
            activity.finish();
        }
    }

    public static void setLastResumeActivity(Activity activity) {
        lastResumeActivity = activity;
    }

    public static <T> boolean existActivity(Class<T> classOfActivity) {
        synchronized (activities) {
            for (Activity activity : activities) {
                if (activity.getClass().getName().equals(classOfActivity.getName())) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void addActivity(Activity activity) {
        removeActivity(activity);
        synchronized (activities) {
            activities.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        synchronized (activities) {
            activities.remove(activity);
        }
    }

    public static void finishAllButThis(Activity activity) {
        synchronized (activities) {
            Activity item = null;
            do {
                item = activities.poll();
                if (item != activity) {
                    finish(item);
                }
            } while (item != null);
        }
    }

    public static <T> void finishAllButThis(Class<T> activityClass) {
        synchronized (activities) {
            Activity item = null;
            do {
                item = activities.poll();
                if (item != null
                        && !StringUtil.equal(item.getClass().getName(), activityClass.getName())) {
                    finish(item);
                }
            } while (item != null);
        }
    }

    public static int size() {
        synchronized (activities) {
            return activities.size();
        }
    }

    public static <T> void finishActivites(Class<T> activityClass) {
        List<Activity> finishes = new ArrayList<>();
        synchronized (activities) {
            for (Activity activity : activities) {
                if (activity != null && StringUtil.equals(activity.getClass().getName(), activityClass.getName())) {
                    finishes.add(activity);
                }
            }
            for (Activity finish : finishes) {
                finish.finish();
            }
        }
    }


}

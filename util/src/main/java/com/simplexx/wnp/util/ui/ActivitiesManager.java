package com.simplexx.wnp.util.ui;

import com.simplexx.wnp.util.base.BaseActivity;

/**
 * Created by wnp on 2018/7/6.
 */

public class ActivitiesManager {
    private final BaseActivity currentActivity;

    public ActivitiesManager(BaseActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public void finishOtherActivies() {
        ActivitiesHelper.finishAllButThis(currentActivity);
    }

    public <T> void finishActivities(Class<T> classOfActivities) {
        ActivitiesHelper.finishActivites(classOfActivities);
    }

    public <T> void finishAllButThis(Class<T> activityClass) {
        ActivitiesHelper.finishAllButThis(activityClass);
    }

    /**
     * 当前打开的activity数量
     * @return
     */
    public int size() {
        return ActivitiesHelper.size();
    }


}

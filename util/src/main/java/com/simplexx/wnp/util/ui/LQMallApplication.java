package com.simplexx.wnp.util.ui;

import android.app.Application;

import com.simplexx.wnp.baselib.repositories.ClientRepositories;
import com.simplexx.wnp.baselib.ui.ClientSettings;
import com.simplexx.wnp.baselib.util.StringUtil;
import com.simplexx.wnp.util.AndroidUtil;
import com.simplexx.wnp.util.DeviceUtil;
import com.simplexx.wnp.util.repositories.SharedPreferencesRepositoryProvider;

/**
 * Created by wnp on 2018/6/26.
 */
// TODO: 2018/6/26 未完
public abstract class LQMallApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        onRealCreate();
    }

    private void onRealCreate() {

        InitRepositories();
        ClientSettings clientSettings = new ClientSettings();
        if (StringUtil.isNullOrWhiteSpace(clientSettings.getUuid())) {
            clientSettings.setUuid(AndroidUtil.getAndroidId(this));
        }

        ClientSettings.DeviceInfo deviceInfo = new ClientSettings.DeviceInfo(
                DeviceUtil.getDeviceDisplay(this),
                DeviceUtil.getOSVersion(),
                DeviceUtil.getDeviceModel(),
                AndroidUtil.getVersionName(this),
                AndroidUtil.getVersionCode(this));
        clientSettings.setDeviceInfo(deviceInfo);
    }

    private void InitRepositories() {
        ClientRepositories
                .getInstance()
                .setSharedPreferencesRepositoryProvider(new SharedPreferencesRepositoryProvider(this));
    }
}

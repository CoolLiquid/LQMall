package com.wnp.lqmall.application;

import com.simplexx.wnp.util.ui.LQMallApplication;
import com.wnp.lqmall.loader.ImageLoaderUtil;

/**
 * Created by wnp on 2018/7/6.
 */

public class LQFrameApplication extends LQMallApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtil.INSTANT.init(this);
    }
}

package com.wnp.lqmall.application;

import com.simplexx.wnp.util.ui.LQMallApplication;
import com.wnp.lqmall.dao.DaoMaster;
import com.wnp.lqmall.dao.DaoSession;
import com.wnp.lqmall.dao.OpenHelper;
import com.wnp.lqmall.loader.ImageLoaderUtil;

import org.greenrobot.greendao.identityscope.IdentityScopeType;

/**
 * Created by wnp on 2018/7/6.
 */

public class LQFrameApplication extends LQMallApplication {
    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
        ImageLoaderUtil.INSTANT.init(this);
    }

    public void initDB() {
        OpenHelper openHelper = new OpenHelper(this);
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

}

package com.simplexx.wnp.baselib;

import com.google.gson.annotations.Expose;
import com.simplexx.wnp.baselib.util.DateUtil;
import com.simplexx.wnp.baselib.util.StringUtil;

import java.util.Date;

/**
 * Created by wnp on 2018/7/2.
 */

public class Sessions {
    private final static int DEFAULT_MONTH_EXPIRED = 28;//Sessions过期时间间隔--30天
    private final static int DEFAULT_SECOND_EXPIRED = 300;//Sessions需要刷新的时间间隔--5分钟

    @Expose
    private String accessSessions;

    @Expose
    private Date expired;

    public boolean isExpired() {
        return expired == null || DateUtil.now().after(expired);
    }

    public boolean isRefreshExpired() {
        return expired == null
                || StringUtil.isNullOrWhiteSpace(accessSessions)
                || DateUtil.now().after(DateUtil.addDays(expired, DEFAULT_MONTH_EXPIRED));
    }

    public String getAccessSessions() {
        return accessSessions;
    }

}

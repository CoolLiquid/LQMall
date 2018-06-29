package com.simplexx.wnp.baselib;

/**
 * Created by wnp on 2018/6/26.
 */

public class Config {

    public final static String SCHEMA = "http://";
    public final static String COMMON_HOST = "api.tgnet.com";
    public final static String COMMON_DATA_BASE_URL = SCHEMA + COMMON_HOST;

    public final static String HOST_URL_P = "api.p.tgnet.com";
    public final static String URL_P = SCHEMA + HOST_URL_P;

    public final static String getBaseUrl(String host) {
        if (host == null)
            return null;
        switch (host) {
            case COMMON_HOST:
                return COMMON_DATA_BASE_URL;
            default:
                return String.format("%1s%2s", SCHEMA, host);
        }
    }


}

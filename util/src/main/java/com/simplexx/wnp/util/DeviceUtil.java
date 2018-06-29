package com.simplexx.wnp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


import com.simplexx.wnp.baselib.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class DeviceUtil {

    public static String getDeviceDisplay(Context context) {
        WindowManager wMng = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wMng.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        String dd = metrics.widthPixels + "X" + metrics.heightPixels + "/"
                + metrics.scaledDensity;

        try {
            dd = URLEncoder.encode(dd, "utf8");
        } catch (UnsupportedEncodingException e) {
            // nothing
        }
        return dd;
    }

    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getDeviceModel() {
        String model = android.os.Build.MODEL;
        try {
            model = URLEncoder.encode(model, "utf8");
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }
        return model;
    }

    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getDeviceName() {
        String manufacturer = getDeviceManufacturer();
        String model = getDeviceModel();
        if ((model != null) && model.startsWith(manufacturer)) {
            return StringUtil.capitalize(model);
        } else if (manufacturer != null) {
            return StringUtil.capitalize(manufacturer) + " " + model;
        } else {
            return "Unknown";
        }
    }

    public static String networkType(Context context) {
        if (context == null) {
            return "";
        }
        ConnectivityManager connMng = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMng.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            switch (activeNetworkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return "wifi";
                case ConnectivityManager.TYPE_MOBILE:
                    return getNetworkTypeName(activeNetworkInfo.getSubtype());
            }
        }
        return getNetworkTypeName(TelephonyManager.NETWORK_TYPE_UNKNOWN);
    }

    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }

    public static String getNetworkTypeName(int type) {
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "CDMA - EvDo rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "CDMA - EvDo rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "CDMA - EvDo rev. B";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "CDMA - 1xRTT";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "CDMA - eHRPD";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDEN";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            default:
                return "UNKNOWN";
        }
    }
}


package com.simplexx.wnp.util;

import android.content.Context;
import android.util.TypedValue;

import java.lang.reflect.Field;

/**
 * Created by fan-gk on 2017/4/19.
 */


public class ScreenUtils {

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getWindowsWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getWindowsHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels - getStausBarHeight();
    }

    public static int getStausBarHeight() {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int height = Integer.parseInt(field.get(obj).toString());
            if (height < 90) {
                return TypedValueUtil.fromDip(height);
            }
        } catch (Exception e) {
        }

        return TypedValueUtil.fromDip(25);
    }
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

    public static int px2dp(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                pxValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

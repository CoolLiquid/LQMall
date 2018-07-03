package com.simplexx.wnp.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class TypedValueUtil {
    public static int fromDip(float dip) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }

}

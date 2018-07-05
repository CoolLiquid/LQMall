package com.simplexx.wnp.util.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by fan-gk on 2017/4/20.
 */


public final class DialogFragmentUtil {
    /**
     * @param context
     * @param fragmentManager
     * @param classOfFragment
     * @param tag
     * @param args            只有在未弹出时，此参数才有效
     * @param <T>
     * @return
     */
    public static <T extends DialogFragment> T singleShow(Context context, FragmentManager fragmentManager, Class<T> classOfFragment, String tag, Bundle args) {
        final Fragment fragment = fragmentManager.findFragmentByTag(tag);
        T result;
        if (fragment == null || !fragment.getClass().getName().equals(classOfFragment.getName())) {
            result = (T) DialogFragment.instantiate(context, classOfFragment.getName(), args);
            fragmentManager.beginTransaction()
                    .add(result, tag)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } else {
            result = (T) fragment;
        }
        return result;
    }

    public static <T extends DialogFragment> void dismiss(FragmentManager fragmentManager, Class<T> classOfFragment, String tag) {
        DialogFragment df = getDialogFragment(fragmentManager, classOfFragment, tag);
        if (df == null)
            return;
        df.dismiss();
    }

    public static <T extends DialogFragment> void dismissAllowingStateLoss(FragmentManager fragmentManager, Class<T> classOfFragment, String tag) {
        DialogFragment df = getDialogFragment(fragmentManager, classOfFragment, tag);
        if (df == null)
            return;
        df.dismissAllowingStateLoss();
    }

    private static <T extends DialogFragment> T getDialogFragment(FragmentManager fragmentManager, Class<T> classOfFragment, String tag) {
        final Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null || !(fragment instanceof DialogFragment) || !fragment.getClass().getName().equals(classOfFragment.getName())) {
            return null;
        }
        return (T) fragment;
    }
}

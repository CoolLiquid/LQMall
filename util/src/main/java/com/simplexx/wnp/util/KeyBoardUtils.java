package com.simplexx.wnp.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class KeyBoardUtils {
    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideKeyBoard(Activity context) {
        if (context != null) {
            View focusView = context.getCurrentFocus();
            hideKeyBoard(focusView, context);
        }
    }

    public static void hideKeyBoard(DialogFragment context) {
        Dialog dialog = context.getDialog();
        if (dialog != null) {
            View focusView = dialog.getCurrentFocus();
            hideKeyBoard(focusView, context.getActivity());
        } else {
            hideKeyBoard(context.getActivity());
        }
    }


    private static void hideKeyBoard(View focusView, Activity context) {
        if (focusView != null && context != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    focusView.getWindowToken(), 0);
        }
    }

/*    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mServedInputConnectionWrapper"};
        Field f = null;
        Object obj_get;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }*/

    /**
     * 显示软键盘
     *
     * @param context
     * @param editText 获取焦点
     */
    public static void showKeyBord(Activity context, EditText editText) {
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

    }


    /**
     * 获取虚拟键盘的高度
     *
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totlaHeight = ScreenUtils.getScreenHeight(context);
        int contentHeight = ScreenUtils.getScreenHeight(context);
        return totlaHeight - contentHeight;
    }

    /**
     * 点击事件的位置在指定View的外面则隐藏键盘
     *
     * @param view
     * @param ev
     * @return
     */
    public static void isShouldHideInput(MotionEvent ev, Activity context, View... view) {
        if (ev != null && ev.getAction() == MotionEvent.ACTION_DOWN) {
            int[] l = {0, 0};
            if (view != null) {
                View[] views = view;
                boolean hadClick = false;
                for (int i = 0; i < views.length; i++) {
                    if (isClickOnView(views[i], ev, l)) {
                        hadClick = true;
                    }
                }
                if (hadClick == false) {
                    hideKeyBoard(context);
                }
            }
        }
    }


    private static boolean isClickOnView(View view, MotionEvent ev, int[] location) {
        view.getLocationInWindow(location);
        int left = location[0], top = location[1], bottom = top + view.getHeight(), right = left + view.getWidth();
        if (ev.getX() < left || ev.getX() > right || ev.getY() < top || ev.getY() > bottom) {
            return false;
        } else {
            return true;
        }
    }

}

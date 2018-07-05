package com.simplexx.wnp.util.base;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplexx.wnp.util.BuildConfig;

/**
 * 申请权限
 * 加载弹窗
 * Created by wnp on 2018/7/3.
 * DialogFragment---使用的是兼容库中的类
 */

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return onBeforeBackPressed();
                }
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 权限申请的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected boolean onBeforeBackPressed() {
        return false;
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void singleShow(FragmentManager manager) {
        if (this.isAdded() || isVisible() || isRemoving() || manager == null)
            return;
        if (BuildConfig.DEBUG) {
            show(manager, getClass().getName());
        } else {
            try {
                show(manager, getClass().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

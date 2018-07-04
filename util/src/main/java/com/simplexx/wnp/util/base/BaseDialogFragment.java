package com.simplexx.wnp.util.base;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.baselib.exception.NetWorkException;
import com.simplexx.wnp.baselib.executor.ActionRequest;
import com.simplexx.wnp.util.BuildConfig;

/**
 * 申请权限
 * 加载弹窗
 * Created by wnp on 2018/7/3.
 * DialogFragment---使用的是兼容库中的类
 */

public abstract class BaseDialogFragment extends DialogFragment implements IView {

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
    public void onNeedLogin(boolean otherDevice) {
        getBaseActivity().onNeedLogin(otherDevice);
    }

    @Override
    public void onException(Exception e) {
        getBaseActivity().onException(e);
    }

    @Override
    public void onException(Exception e, boolean finish) {
        getBaseActivity().onException(e, finish);
    }

    @Override
    public void onException(ActionRequest request, NetWorkException e) {
        if (this.isAdded()) {
            //todo Show ActionReloadDialog
        }
    }

    @Override
    public void onWarn(String message) {
        getBaseActivity().onWarn(message);
    }

    @Override
    public void hideKeyBoard() {
        getBaseActivity().hideKeyBoard();
    }

    @Override
    public void runAction(ActionRequest request) {
        getBaseActivity().runAction(request);
    }

    @Override
    public boolean viewDestroyed() {
        return this.isDetached();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideKeyBoard();
    }

    public void singleShow(FragmentManager manager) {
        if (this.isAdded() || isVisible() || isRemoving() || manager == null)
            return;
        if (BuildConfig.DEBUG){
            show(manager, getClass().getName());
        }else {
            try {
                show(manager, getClass().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

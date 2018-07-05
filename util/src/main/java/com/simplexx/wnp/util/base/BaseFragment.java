package com.simplexx.wnp.util.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.baselib.exception.NetWorkException;
import com.simplexx.wnp.baselib.executor.ActionRequest;
import com.simplexx.wnp.util.executor.ThreadExecutor;
import com.simplexx.wnp.util.ui.dialog.ActionLoadingDialogFragment;

/**
 * 权限申请机制
 * Created by wnp on 2018/7/3.
 */

public abstract class BaseFragment extends Fragment implements IView {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideKeyBoard();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onNeedLogin(boolean otherDevice) {
        if (getBaseActivity() != null)
            getBaseActivity().onNeedLogin(otherDevice);
    }

    @Override
    public void onException(Exception e) {
        try {
            getBaseActivity().onException(e);
        } catch (Exception exception) {
            e.printStackTrace();
        }
    }

    @Override
    public void onException(Exception e, boolean finish) {
        getBaseActivity().onException(e, finish);
    }

    @Override
    public void onException(ActionRequest request, NetWorkException e) {
        getBaseActivity().onException(request, e);
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
    public void showLoadingView(final ActionRequest request) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                if (BaseFragment.this.isAdded()) {
                    ActionLoadingDialogFragment.singleShow(getBaseActivity(), request);
                }
            }
        });
    }

    @Override
    public void dismissLoadingView() {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                if (BaseFragment.this.isAdded()) {
                    ActionLoadingDialogFragment.dismiss(getBaseActivity());
                }
            }
        });
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}

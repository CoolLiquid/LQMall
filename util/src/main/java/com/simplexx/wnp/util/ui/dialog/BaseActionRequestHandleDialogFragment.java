package com.simplexx.wnp.util.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.simplexx.wnp.baselib.executor.ActionRequest;
import com.simplexx.wnp.baselib.ui.IActionRequestHandleView;
import com.simplexx.wnp.baselib.ui.ICloseable;
import com.simplexx.wnp.util.ui.widget.DialogFragmentUtil;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by wnp on 2018/7/4.
 */

public class BaseActionRequestHandleDialogFragment extends SimpleDialogFragment
        implements IActionRequestHandleView, ICloseable {
    protected final Queue<ActionRequest> actions = new ArrayDeque<>();
    private int handleBackType;
    private Fragment parentFragment;

    @Override
    public void close() {
        dismiss();
    }

    @Override
    public void addActionRequest(ActionRequest request) {
        if (request != null) {
            if (request.getBackOnExceptionType() > this.handleBackType) {
                this.handleBackType = request.getBackOnExceptionType();
            }
            actions.add(request);
        }
    }

    @Override
    protected boolean onBeforeBackPressed() {
        for (ActionRequest request : actions) {
            request.setResultFailed();
        }
        switch (handleBackType) {
            case ActionRequest.BACK_ON_EXCEPTION_TYPE_DISALLOWED://不允许退出
                return true;
            case ActionRequest.BACK_ON_EXCEPTION_TYPE_FINISH_PARENT://连带父activity一起退出
                closeSelfAndParent();
                return true;
            default:
                return false;
        }
    }

    private void closeSelfAndParent() {
        if (getActivity() != null) {
            if (parentFragment != null) {
                if (parentFragment instanceof DialogFragment)
                    ((DialogFragment) parentFragment).dismiss();
                else
                    getActivity().getSupportFragmentManager().beginTransaction().remove(parentFragment).commit();
            } else {
                getActivity().finish();
            }
        }
        dismiss();
    }

    public void setParentFragment(Fragment fragment) {
        this.parentFragment = fragment;
    }

    protected static <T extends BaseActionRequestHandleDialogFragment> T singleShow(Fragment parent, Class<T> classOfT, String tag,
                                                                                    ActionRequest request, Bundle args) {
        T result = DialogFragmentUtil.singleShow(parent.getContext(), parent.getChildFragmentManager(), classOfT, tag, args);
        result.setParentFragment(parent);
        result.addActionRequest(request);
        return result;
    }

    protected static <T extends BaseActionRequestHandleDialogFragment> T singleShow(FragmentActivity parent, Class<T> classOfT, String
            tag, ActionRequest request, Bundle args) {
        T result = DialogFragmentUtil.singleShow(parent, parent.getSupportFragmentManager(), classOfT, tag, args);
        result.addActionRequest(request);
        return result;
    }
}

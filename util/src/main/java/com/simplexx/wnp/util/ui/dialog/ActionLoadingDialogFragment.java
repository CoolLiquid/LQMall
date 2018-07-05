package com.simplexx.wnp.util.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplexx.wnp.baselib.executor.ActionRequest;
import com.simplexx.wnp.baselib.util.StringUtil;
import com.simplexx.wnp.util.R;
import com.simplexx.wnp.util.executor.ThreadExecutor;
import com.simplexx.wnp.util.ui.widget.DialogFragmentUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wnp on 2018/7/4.
 */

public class ActionLoadingDialogFragment extends BaseActionRequestHandleDialogFragment {

    private static class Builder extends SimpleDialogFragment.Builder<ActionLoadingDialogFragment, ActionLoadingDialogFragment.Builder> {
        private String content;

        public String getContent() {
            return content;
        }

        public ActionLoadingDialogFragment.Builder setContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        public ActionLoadingDialogFragment build() {
            return createInstance(this);
        }
    }

    private static ActionLoadingDialogFragment createInstance(ActionLoadingDialogFragment.Builder builder) {
        ActionLoadingDialogFragment fragment = new ActionLoadingDialogFragment();
        fragment.setBuilder(builder);
        return fragment;
    }

    private final static String FRAGMENT_TAG = "single_progress_dialog";
    private final AtomicInteger refCount = new AtomicInteger(0);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_progress, null);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        Builder builder = getBuilder();
        if (builder != null) {
            if (!StringUtil.isNullOrEmpty(builder.getContent())) {
                tvContent.setText(builder.getContent());
            }
        }
        if (!tvContent.getText().toString().equals("请稍后"))
            tvContent.setVisibility(View.VISIBLE);
        else
            tvContent.setVisibility(View.GONE);
        return view;
    }

    public static ActionLoadingDialogFragment singleShow(FragmentActivity parent, ActionRequest request) {
        Builder builder = new Builder().setCancelable(true).setContent(request.getLoadingMessage());
        ActionLoadingDialogFragment fragment = singleShow(parent, ActionLoadingDialogFragment.class, FRAGMENT_TAG, request, builder
                .buildArgs(null));
        fragment.refCount.incrementAndGet();
        return fragment;
    }

    public static ActionLoadingDialogFragment singleShow(Fragment parent, ActionRequest request) {
        Builder builder = new Builder().setCancelable(true).setContent(request.getLoadingMessage());
        ActionLoadingDialogFragment fragment = singleShow(parent, ActionLoadingDialogFragment.class, FRAGMENT_TAG, request, builder
                .buildArgs(null));
        fragment.refCount.incrementAndGet();
        return fragment;
    }

    public static void dismiss(FragmentActivity parent) {
        DialogFragmentUtil.dismissAllowingStateLoss(parent.getSupportFragmentManager(), ActionLoadingDialogFragment.class, FRAGMENT_TAG);
    }

    public static void dismiss(Fragment parent) {
        DialogFragmentUtil.dismissAllowingStateLoss(parent.getChildFragmentManager(), ActionLoadingDialogFragment.class, FRAGMENT_TAG);
    }

    @Override
    public void dismiss() {
        final int count = refCount.decrementAndGet();
        if (count <= 0) {
            refCount.set(0);
            ThreadExecutor.runInMain(new Runnable() {
                @Override
                public void run() {
                    try {
                        ActionLoadingDialogFragment.super.dismiss();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

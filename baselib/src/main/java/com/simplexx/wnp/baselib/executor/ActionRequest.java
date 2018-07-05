package com.simplexx.wnp.baselib.executor;

import com.simplexx.wnp.baselib.basemvp.IView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wnp on 2018/7/2.
 */

public class ActionRequest implements Runnable {


    // 显示加载中样式，异步
    public final static int RUN_TYPE_LOADING = 0;
    //后台运行，异步
    public final static int RUN_TYPE_BACKGROUND = 1;
    //在当前线程运行，同步
    public final static int RUN_TYPE_LOCK = 2;
    //调用失败后不做任何重试处理
    public final static int HANDLE_ON_EXCEPTION_NONE = 0;
    //在用户点击重试的时候进行重新调用
    public final static int HANDLE_ON_EXCEPTION_USER_RELOAD = 1;


    //按返回键时关闭重新加载的提示，默认
    public final static int BACK_ON_EXCEPTION_TYPE_CLOSE = 0;
    //按返回键时关闭activity或fragment，在哪里弹出关那张,略危险、需确定你要关闭的是逻辑上能关闭的界面
    public final static int BACK_ON_EXCEPTION_TYPE_FINISH_PARENT = 1;
    //不允许返回，只能重新加载
    public final static int BACK_ON_EXCEPTION_TYPE_DISALLOWED = 2;


    public static class Builder {
        private final static String DEFAULT_LOADING_MESSAGE = "请稍等";

        private ActionRunnable runnable = null;
        private int handleOnExceptionType = HANDLE_ON_EXCEPTION_NONE;//加载失败之后，做出相应的处理
        private String loadingMessage = DEFAULT_LOADING_MESSAGE;
        private int runType = RUN_TYPE_LOADING;//前台显示模式还是后台模式
        private int backOnExceptionType = BACK_ON_EXCEPTION_TYPE_CLOSE;//点击返回键，显示的效果
        private List<Runnable> successRunnableList = new ArrayList<>();
        private List<Runnable> failRunnableList = new ArrayList<>();
        private List<Runnable> completedRunnableList = new ArrayList<>();
        private IView view = null;

        public Builder addOnSuccessListener(Runnable runnable, Runnable... runnableArray) {
            if (runnable != null)
                this.successRunnableList.add(runnable);
            if (runnableArray != null)
                this.successRunnableList.addAll(Arrays.asList(runnableArray));
            return this;
        }

        public Builder addOnFailListener(Runnable runnable, Runnable... runnableArray) {
            if (runnable != null)
                this.failRunnableList.add(runnable);
            if (runnableArray != null)
                this.failRunnableList.addAll(Arrays.asList(runnableArray));
            return this;
        }

        public Builder addOnCompletedListener(Runnable runnable, Runnable... runnableArray) {
            if (runnable != null)
                this.completedRunnableList.add(runnable);
            if (runnableArray != null)
                this.completedRunnableList.addAll(Arrays.asList(runnableArray));
            return this;
        }

        public Builder setRunnable(ActionRunnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public Builder setHandleOnExceptionType(int handleOnExceptionType) {
            this.handleOnExceptionType = handleOnExceptionType;
            return this;
        }

        public Builder setRunType(int runType) {
            this.runType = runType;
            return this;
        }

        public Builder setRunBackground() {
            return setRunType(RUN_TYPE_BACKGROUND);
        }

        public Builder setRunLoading() {
            return setRunType(RUN_TYPE_LOADING);
        }

        public Builder setRunLock() {
            return setRunType(RUN_TYPE_LOCK);
        }

        public Builder setLoadingMessage(String loadingMessage) {
            this.loadingMessage = loadingMessage;
            return this;
        }

        public Builder setAllowUserReload() {
            return setHandleOnExceptionType(HANDLE_ON_EXCEPTION_USER_RELOAD);
        }

        public Builder setBackOnExceptionType(int backOnExceptionType) {
            this.backOnExceptionType = backOnExceptionType;
            return this;
        }

        public Builder setBackOnExceptionClose() {
            setBackOnExceptionType(BACK_ON_EXCEPTION_TYPE_CLOSE);
            return this;
        }

        public Builder setBackOnExceptionFinishParent() {
            setBackOnExceptionType(BACK_ON_EXCEPTION_TYPE_FINISH_PARENT);
            return this;
        }

        public Builder setBackOnExceptionDisallowed() {
            setBackOnExceptionType(BACK_ON_EXCEPTION_TYPE_DISALLOWED);
            return this;
        }

        public Builder setView(IView view) {
            this.view = view;
            return this;
        }

        public ActionRequest build() {
            return new ActionRequest(this);
        }

        public void run() {
            build().run();
        }
    }

    private final ActionRunnable runnable;
    private final List<Runnable> successRunnableList;
    private final List<Runnable> failRunnableList;
    private final List<Runnable> completedRunnableList;
    private final int handleOnExceptionType;
    private final String loadingMessage;
    private final int runType;
    private final int backOnExceptionType;
    private final IView view;
    private Boolean result = null;

    private ActionRequest(Builder builder) {
        this.runnable = builder.runnable;
        this.successRunnableList = builder.successRunnableList;
        this.failRunnableList = builder.failRunnableList;
        this.completedRunnableList = builder.completedRunnableList;
        this.handleOnExceptionType = builder.handleOnExceptionType;
        this.loadingMessage = builder.loadingMessage;
        this.runType = builder.runType;
        this.backOnExceptionType = builder.backOnExceptionType;
        this.view = builder.view;
    }

    public int getRunType() {
        return this.runType;
    }

    public ActionRunnable getRunnable() {
        return runnable;
    }

    public int getHandleOnExceptionType() {
        return handleOnExceptionType;
    }

    public String getLoadingMessage() {
        return loadingMessage;
    }

    public int getBackOnExceptionType() {
        return backOnExceptionType;
    }

    public synchronized void setResultSuccessful() {
        if (result == null) {
            result = true;
            try {
                runRunableList(successRunnableList);
            } finally {
                runRunableList(completedRunnableList);
            }
        }
    }

    public synchronized void setResultFailed() {
        if (result == null) {
            result = false;
            try {
                runRunableList(failRunnableList);
            } finally {
                runRunableList(completedRunnableList);
            }
        }
    }

    private void runRunableList(List<Runnable> runnableList) {
        if (runnableList != null)
            for (Runnable runnable : runnableList) {
                if (runnable != null)
                    runnable.run();
            }
    }

    public IView getView() {
        return view;
    }


    @Override
    public void run() {
        view.runAction(this);
    }
}

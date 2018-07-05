package com.simplexx.wnp.baselib.executor;

import com.simplexx.wnp.baselib.basemvp.IView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wnp on 2018/7/2.
 */

public class ActionRequest implements Runnable {


    // ��ʾ��������ʽ���첽
    public final static int RUN_TYPE_LOADING = 0;
    //��̨���У��첽
    public final static int RUN_TYPE_BACKGROUND = 1;
    //�ڵ�ǰ�߳����У�ͬ��
    public final static int RUN_TYPE_LOCK = 2;
    //����ʧ�ܺ����κ����Դ���
    public final static int HANDLE_ON_EXCEPTION_NONE = 0;
    //���û�������Ե�ʱ��������µ���
    public final static int HANDLE_ON_EXCEPTION_USER_RELOAD = 1;


    //�����ؼ�ʱ�ر����¼��ص���ʾ��Ĭ��
    public final static int BACK_ON_EXCEPTION_TYPE_CLOSE = 0;
    //�����ؼ�ʱ�ر�activity��fragment�������ﵯ��������,��Σ�ա���ȷ����Ҫ�رյ����߼����ܹرյĽ���
    public final static int BACK_ON_EXCEPTION_TYPE_FINISH_PARENT = 1;
    //�������أ�ֻ�����¼���
    public final static int BACK_ON_EXCEPTION_TYPE_DISALLOWED = 2;


    public static class Builder {
        private final static String DEFAULT_LOADING_MESSAGE = "���Ե�";

        private ActionRunnable runnable = null;
        private int handleOnExceptionType = HANDLE_ON_EXCEPTION_NONE;//����ʧ��֮��������Ӧ�Ĵ���
        private String loadingMessage = DEFAULT_LOADING_MESSAGE;
        private int runType = RUN_TYPE_LOADING;//ǰ̨��ʾģʽ���Ǻ�̨ģʽ
        private int backOnExceptionType = BACK_ON_EXCEPTION_TYPE_CLOSE;//������ؼ�����ʾ��Ч��
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

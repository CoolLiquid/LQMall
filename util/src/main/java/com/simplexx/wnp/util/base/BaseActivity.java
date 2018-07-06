package com.simplexx.wnp.util.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.baselib.exception.LQException;
import com.simplexx.wnp.baselib.exception.NetWorkException;
import com.simplexx.wnp.baselib.exception.UnLoginException;
import com.simplexx.wnp.baselib.executor.ActionRequest;
import com.simplexx.wnp.baselib.executor.ActionRunnable;
import com.simplexx.wnp.baselib.util.StringUtil;
import com.simplexx.wnp.util.KeyBoardUtils;
import com.simplexx.wnp.util.ToastUtils;
import com.simplexx.wnp.util.executor.ThreadExecutor;
import com.simplexx.wnp.util.ui.ActivitiesManager;
import com.simplexx.wnp.util.ui.dialog.ActionLoadingDialogFragment;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 2.需要接入ActivityHelper,同时完善onNeedLogin方法
 * Created by wnp on 2018/6/25.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {
    private final Queue<Runnable> saveInstanceStateRunnables = new ArrayDeque<>();
    private boolean isSavedInstanceState;
    private ActivitiesManager activitiesManager = new ActivitiesManager(this);


    public ActivitiesManager getActivitiesManager() {
        return activitiesManager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSavedInstanceState = false;
        runSaveInstanceStateRunnables();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        isSavedInstanceState = true;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isSavedInstanceState = false;
        runSaveInstanceStateRunnables();
    }

    protected synchronized void runSaveInstanceStateRunnables() {
        Runnable run = null;
        while ((run = saveInstanceStateRunnables.poll()) != null) {
            try {
                run.run();
            } catch (Throwable e) {
            }
        }
    }

    @Override
    public void runAction(final ActionRequest request) {
        if (request != null) {
            final ActionRunnable runnable = request.getRunnable();
            if (runnable != null) {
                if (request.getRunType() == ActionRequest.RUN_TYPE_LOCK) {
                    try {
                        runnable.run();
                        request.setResultSuccessful();
                    } catch (Exception ex) {
                        onException(request, ex);
                    }
                } else {
                    final boolean isShowProgress = request.getRunType() == ActionRequest.RUN_TYPE_LOADING;
                    if (isShowProgress) {
                        request.getView().showLoadingView(request);
                    }
                    //异步执行runabel
                    ThreadExecutor.runInAsync(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                runnable.run();
                            } catch (final Exception e) {
                                if (request.getRunType() != ActionRequest.RUN_TYPE_BACKGROUND) {
                                    onException(request, e);
                                } else {
                                    ThreadExecutor.runInMain(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!StringUtil.isNullOrEmpty(e.getMessage())) {
                                                if (e instanceof NetWorkException) {
                                                    ToastUtils.showShort(BaseActivity.this, e.getMessage());
                                                }
                                            }
                                        }
                                    });
                                }
                            } finally {
                                if (isShowProgress)
                                    request.getView().dismissLoadingView();
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (onBeforeBackPressed())
                return true;
            else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);

    }

    protected boolean onBeforeBackPressed() {
        if (activitiesManager.size() == 1) {
            moveTaskToBack(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void hideKeyBoard() {
        KeyBoardUtils.hideKeyBoard(this);
    }

    protected void safeRunAfterSaveInstanceState(Runnable runnable) {
        if (runnable != null) {
            if (isSavedInstanceState) {
                saveInstanceStateRunnables.offer(runnable);
            } else {
                runnable.run();
            }
        }
    }

    @Override
    public void onNeedLogin(boolean otherDevice) {
        if (otherDevice) {
            //被动退出登录
            onForceLogout();
        } else {
            //强制退出登录
            onTokenOutDate();
//            getTgnetApplication().launchLoginActivity(this);
        }
    }

    protected abstract void onForceLogout();

    protected abstract void onTokenOutDate();

    @Override
    public void onWarn(final String message) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort(BaseActivity.this, message);
            }
        });
    }


    protected void onException(ActionRequest request, Exception ex) {
        if (ex instanceof NetWorkException) {
            if (request != null
                    && request.getHandleOnExceptionType() == ActionRequest.HANDLE_ON_EXCEPTION_USER_RELOAD) {
                request.getView().onException(request, (NetWorkException) ex);
            } else {
                onException(ex);
            }
        } else {
            onException(ex);
        }
    }

    @Override
    public void onException(ActionRequest request, NetWorkException e) {
        onException(e, request != null && request.getBackOnExceptionType() == ActionRequest.BACK_ON_EXCEPTION_TYPE_FINISH_PARENT);
    }

    @Override
    public void onException(Exception e, boolean finish) {
        e.printStackTrace();
        if (e instanceof UnLoginException) {
            onNeedLogin(((UnLoginException) e).getType() == UnLoginException.TYPE_OTHER_DEVICE);
        } else if (e instanceof LQException) {
            onException((LQException) e, finish);
        } else if (e instanceof NetWorkException) {
            onException((NetWorkException) e, finish);
        } else {
            showExceptionDialog(e.toString(), finish);
        }
    }

    protected void onException(LQException e, boolean finish) {
        switch (e.getErrorCode()) {
            //TODO 根据具体错误码进行处理
            default:
                if (!StringUtil.isNullOrEmpty(e.getMessage()))
                    showExceptionDialog(e.getMessage(), finish);
                break;
        }
    }

    protected void onException(final NetWorkException e, boolean finish) {
        if (finish) {
            showExceptionDialog(e.getMessage(), true);
        } else {
            ThreadExecutor.runInMain(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort(BaseActivity.this, e.getMessage());
                }
            });
        }
    }

    @Override
    public void onException(Exception e) {
        onException(e, false);
    }

    @Override
    public void showLoadingView(final ActionRequest request) {
        safeRunAfterSaveInstanceState(new Runnable() {
            @Override
            public void run() {

                ThreadExecutor.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        ActionLoadingDialogFragment.singleShow(BaseActivity.this, request);
                    }
                });
            }
        });
    }

    @Override
    public void dismissLoadingView() {
        safeRunAfterSaveInstanceState(new Runnable() {
            @Override
            public void run() {
                ActionLoadingDialogFragment.dismiss(BaseActivity.this);
            }
        });
    }

    private void showExceptionDialog(String messsage, boolean finish) {

    }


    @Override
    public boolean viewDestroyed() {
        return this.isDestroyed();
    }
}

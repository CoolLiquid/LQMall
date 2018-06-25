package com.simplexx.wnp.util.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class MainThreadExecutor implements Executor {

    private Handler handler;
    private Thread mMainThread;

    public MainThreadExecutor() {
        handler = new Handler(Looper.getMainLooper());
        mMainThread = Looper.getMainLooper().getThread();
    }

    @Override
    public void execute(Runnable command) {
        if (mMainThread == Thread.currentThread())
            command.run();
        else
            handler.post(command);
    }
}

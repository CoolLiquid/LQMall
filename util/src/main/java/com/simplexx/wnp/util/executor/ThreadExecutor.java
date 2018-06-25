package com.simplexx.wnp.util.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class ThreadExecutor {
    private static Executor mainExecutor;
    private static Executor asyncExecutor;

    static {
        mainExecutor = new MainThreadExecutor();
        asyncExecutor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "AsyncExecutor Runnable");
            }
        });
    }

    public static void runInMain(@NonNull Runnable runnable) {
        mainExecutor.execute(runnable);
    }

    public static void runInAsync(@NonNull Runnable runnable) {
        asyncExecutor.execute(runnable);
    }
}

package com.simplexx.wnp.util;

import android.support.annotation.NonNull;

import com.simplexx.wnp.baselib.basemvp.BasePresenter;
import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.util.base.PresenterActivity;
import com.simplexx.wnp.util.base.PresenterDialogFragment;
import com.simplexx.wnp.util.base.PresenterFragment;
import com.simplexx.wnp.util.executor.ThreadExecutor;
import com.simplexx.wnp.util.reflect.TypeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class PresenterUtil {
    private static class ViewInvocationHandler<E extends IView> implements InvocationHandler {
        private static final String RUN_ON_MAIN_METHOD_NAME_PREFIX = "on";

        private E view;

        public ViewInvocationHandler(@NonNull E view) {
            this.view = view;
        }

        @Override
        public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
            boolean returnVoid = method.getReturnType().isAssignableFrom(void.class);
            if (returnVoid && method.getName().startsWith(RUN_ON_MAIN_METHOD_NAME_PREFIX)) {
                ThreadExecutor.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!view.viewDestroyed())
                                method.invoke(view, args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return null;
            } else {
                return method.invoke(view, args);
            }
        }
    }


    private static <E extends IView> E createProxyView(E view) {
        return (E) Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new ViewInvocationHandler(view));
    }

    private static <T extends BasePresenter<E>, E extends IView> T createPresenter(Class classOfView, E view) {
        Type[] genericTypes = ((ParameterizedType) classOfView.getGenericSuperclass()).getActualTypeArguments();
        Class<?> presenterClass = null;
        Class<?> viewClass = null;
        Constructor constructor = null;
        try {
            presenterClass = TypeUtil.getClass(genericTypes[0]);
            viewClass = TypeUtil.getClass(genericTypes[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            constructor = presenterClass.getConstructor(viewClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        T presenter = null;
        if (constructor != null) {
            try {
                presenter = (T) constructor.newInstance(createProxyView(view));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return presenter;
    }

    public static <T extends BasePresenter<E>, E extends IView> T createPresenter(PresenterActivity<T, E> activity) {
        return createPresenter(activity.getClass(), (E) activity);
    }

    public static <T extends BasePresenter<E>, E extends IView> T createPresenter(PresenterFragment<T, E> fragment) {
        return createPresenter(fragment.getClass(), (E) fragment);
    }

    public static <T extends BasePresenter<E>, E extends IView> T createPresenter(PresenterDialogFragment<T, E> fragment) {
        return createPresenter(fragment.getClass(), (E) fragment);
    }
}

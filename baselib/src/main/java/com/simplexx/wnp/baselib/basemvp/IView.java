package com.simplexx.wnp.baselib.basemvp;

import com.simplexx.wnp.baselib.exception.IExceptionHandler;
import com.simplexx.wnp.baselib.executor.ActionRequest;

/**
 * Created by fan-gk on 2017/4/19.
 */

public interface IView extends IExceptionHandler {

    void hideKeyBoard();

    void runAction(ActionRequest request);

    boolean viewDestroyed();

    void showLoadingView(ActionRequest request);

    void dismissLoadingView();

}

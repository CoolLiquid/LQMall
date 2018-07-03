package com.simplexx.wnp.baselib.exception;


import com.simplexx.wnp.baselib.executor.ActionRequest;

/**
 * Created by fan-gk on 2017/4/20.
 */

public interface IExceptionHandler {
    void onNeedLogin(boolean otherDevice);
    void onException(Exception e);
    void onException(Exception e, boolean finish);
    void onException(ActionRequest request, NetWorkException e);
    void onWarn(String message);
}
package com.simplexx.wnp.baselib.exception;

/**
 * Created by wnp on 2018/6/29.
 */

public class NetWorkException extends Exception {

    public NetWorkException(String message) {
        super(message, new LQException(ErrorCode.NETWORK, message));
    }

    public NetWorkException(String message, Throwable throwable) {
        super(message, new LQException(ErrorCode.NETWORK, message, throwable));
    }
}

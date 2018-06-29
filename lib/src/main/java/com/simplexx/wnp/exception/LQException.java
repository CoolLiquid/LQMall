package com.simplexx.wnp.exception;

/**
 * Created by wnp on 2018/6/29.
 */

public class LQException extends Exception {

    ErrorCode errorCode;

    public LQException(ErrorCode code, String message) {
        super(message);
        this.errorCode = code;
    }

    public LQException(ErrorCode code, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = code;
    }

}

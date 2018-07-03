package com.simplexx.wnp.baselib.exception;

/**
 * Created by wnp on 2018/6/29.
 */

public class LQException extends Exception {

    ErrorCode errorCode;

    public ErrorCode getErrorCode(){
        return  errorCode;
    }

    public LQException(ErrorCode code, String message) {
        super(message);
        this.errorCode = code;
    }

    public LQException(ErrorCode code, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = code;
    }

}

package com.simplexx.wnp.baselib.exception;

/**
 * Created by wnp on 2018/6/29.
 */

public enum ErrorCode {
    UNLOGIN(10),
    NONE(0),
    NETWORK(50),
    SERVER(200);

    int code;

    private ErrorCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    public static ErrorCode parse(String code) {
        switch (code) {
            case "0":
                return NONE;
            case "10":
                return UNLOGIN;
            default:
                return SERVER;
        }
    }
}

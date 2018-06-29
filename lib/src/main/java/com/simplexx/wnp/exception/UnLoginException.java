package com.simplexx.wnp.exception;

/**
 * Created by wnp on 2018/6/29.
 */

public class UnLoginException extends Exception {
    public static final int TYPE_UNLOGIN = 1;
    public static final int TYPE_TOO_LONG = 2;
    public static final int TYPE_OTHER_DEVICE = 3;
    private final int type;

    public int getType() {
        return type;
    }

    public UnLoginException(int type) {
        super("未登录", new LQException(ErrorCode.UNLOGIN, "未登录"));
        this.type = type;
    }
}

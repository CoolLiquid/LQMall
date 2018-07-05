package com.simplexx.wnp.baselib.exception;

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
        super("Î´µÇÂ¼", new LQException(ErrorCode.UNLOGIN, "Î´µÇÂ¼"));
        this.type = type;
    }
}

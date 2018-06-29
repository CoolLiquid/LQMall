package com.simplexx.wnp.bean;

import com.google.gson.annotations.Expose;
import com.simplexx.wnp.exception.ErrorCode;

/**
 * Created by wnp on 2018/6/29.
 */

public class BaseBean<T> extends EmptyBean {

    @Expose
    T data;

    public BaseBean() {
        super();
    }

    public BaseBean(ErrorCode code) {
        super(code);
    }

    public BaseBean(T data) {
        super(ErrorCode.NONE);
        this.data = data;
    }

}

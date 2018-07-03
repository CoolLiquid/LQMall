package com.simplexx.wnp.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.simplexx.wnp.baselib.util.StringUtil;
import com.simplexx.wnp.baselib.exception.ErrorCode;

/**
 * Created by wnp on 2018/6/29.
 */

public class EmptyBean {
    @Expose
    @SerializedName(value = "staus")
    public String state_code;

    @Expose
    @SerializedName(value = "msg")
    public String message;

    public boolean ok() {
        if (StringUtil.equal(state_code, "0")) {
            return false;
        }
        return true;
    }

    public EmptyBean() {
    }

    public EmptyBean(ErrorCode code) {
        state_code = code.toString();
        message = code.getClass().getName();
    }


}

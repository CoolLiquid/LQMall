package com.simplexx.wnp.presenter.resource.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wnp on 2018/7/2.
 */

public class UserInfoBean {
/*
    {
        "status": 1,
            "msg": "密码错误"
    }
    success

    {
        "status": 0,
            "data": {
        "id": 12,
                "username": "aaa",
                "email": "aaa@163.com",
                "phone": null,
                "role": 0,
                "createTime": 1479048325000,
                "updateTime": 1479048325000
    }
    }*/
    @SerializedName(value = "id")
    public long uid;
    @SerializedName("username")
    public String userName;
    @SerializedName("email")
    public String userEmail;
    public String phone;
    public int role;
    public String createTime;
    public String updateTime;
}

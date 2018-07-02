package com.simplexx.wnp.presenter.resource.api;

import com.simplexx.wnp.bean.EmptyBean;
import com.simplexx.wnp.presenter.resource.bean.UserInfoBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wnp on 2018/7/2.
 */

public interface ILoginService {

    @FormUrlEncoded
    @POST("/user/login.do")
    Call<UserInfoBean> login(@Field("username") String name,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("/user/register.do")
    Call<EmptyBean> register(@Field("username") String name,
                             @Field("password") String password,
                             @Field("email") String email,
                             @Field("phone") String phone,
                             @Field("question") String question,
                             @Field("answer response") String answer);

    @GET
    Call<EmptyBean> checkUserVail(@Query("str") String userName,
                                  @Query("type") String emial);


}

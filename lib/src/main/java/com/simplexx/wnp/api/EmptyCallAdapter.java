package com.simplexx.wnp.api;

import com.simplexx.wnp.bean.EmptyBean;

import retrofit2.Call;

/**
 * Created by wnp on 2018/7/2.
 */

public class EmptyCallAdapter extends DataCallAdapter<EmptyBean> {

    public EmptyCallAdapter(Call<EmptyBean> call) {
        super(call);
    }

    public EmptyCallAdapter(Call<EmptyBean> call, HandleResponseHeaderCallBack callBack) {
        super(call, callBack);
    }
}

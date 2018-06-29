package com.simplexx.wnp.api;

import com.simplexx.wnp.bean.EmptyBean;
import com.simplexx.wnp.exception.LQException;
import com.simplexx.wnp.exception.NetWorkException;
import com.simplexx.wnp.exception.UnLoginException;

import retrofit2.Call;

/**
 * Created by wnp on 2018/6/29.
 */

public class DataCallAdapter<T extends EmptyBean> extends CallAdapter<T> {

    public DataCallAdapter(Call<T> call) {
        super(call);
    }

    //需要处理response的Header的时候，可以使用这个方法产生回掉
    public DataCallAdapter(Call<T> call, HandleResponseHeaderCallBack callBack) {
        super(call, callBack);
    }

    public T getData() throws LQException, UnLoginException, NetWorkException {
        ok();
        return body;
    }
}

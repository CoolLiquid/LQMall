package com.simplexx.wnp.api;

import com.simplexx.wnp.bean.EmptyBean;
import com.simplexx.wnp.baselib.exception.ErrorCode;
import com.simplexx.wnp.baselib.exception.LQException;
import com.simplexx.wnp.baselib.exception.NetWorkException;
import com.simplexx.wnp.baselib.exception.UnLoginException;

import java.io.IOException;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by wnp on 2018/6/29.
 */

public class CallAdapter<T extends EmptyBean> {
    private NetWorkException ne;
    private UnLoginException ue;
    private LQException le;

    protected T body = null;
    HandleResponseHeaderCallBack handleResponseCallBack;

    private Call<T> call;

    public interface HandleResponseHeaderCallBack {
        void onHandle(Headers response);
    }

    public CallAdapter(Call<T> call) {
        this(call, null);
    }

    public CallAdapter(Call<T> call, HandleResponseHeaderCallBack handleResponseCallBack) {
        this.call = call;
        this.handleResponseCallBack = handleResponseCallBack;
    }

    public void ok() throws NetWorkException, LQException, UnLoginException {
        Response<T> response = null;
        if (!call.isExecuted()) {
            try {
                response = call.execute();
            } catch (IOException e) {
                ne = new NetWorkException("网络异常，请稍后重试", e);
            }
            if (response == null) {
                if (ne == null) {
                    ne = new NetWorkException("response==null服务器错误，请稍后再试");
                }
            } else {
                T body = response.body();
                //给到外部使用response
                if (handleResponseCallBack != null) {
                    handleResponseCallBack.onHandle(response.headers());
                }
                if (body == null) {
                    le = new LQException(ErrorCode.SERVER, "服务不可用，请稍后再试.错误码：" + response.code());
                } else if (!body.ok()) {
                    ErrorCode errorCode = ErrorCode.parse(body.state_code);
                    if (errorCode == ErrorCode.UNLOGIN)
                        ue = new UnLoginException(UnLoginException.TYPE_OTHER_DEVICE);
                    else
                        le = new LQException(errorCode, body.message);
                } else {
                    this.body = body;
                }


            }
        }
        if (ne != null)
            throw ne;
        if (le != null)
            throw le;
        if (ue != null)
            throw ue;
    }
}

package com.simplexx.wnp.presenter;

import com.simplexx.wnp.baselib.basemvp.IView;

/**
 * Created by wnp on 2018/7/2.
 */

public class LoginPresenter extends BasePresenter<LoginPresenter.ILoginView> {
    public LoginPresenter(ILoginView iView) {
        super(iView);
    }

    public interface ILoginView extends IView {

    }

    @Override
    public void onViewCreate() {
        super.onViewCreate();
        System.out.print("onViewCreate");
    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
        System.out.print("onViewDestroy");
    }

    @Override
    public void onViewResume() {
        super.onViewResume();
        System.out.print("onViewResume");
    }

    @Override
    public void onViewPause() {
        super.onViewPause();
        System.out.print("onViewPause");
    }

    @Override
    public void onViewStart() {
        super.onViewStart();
        System.out.print("onViewStart");
    }

    @Override
    public void onViewStop() {
        super.onViewStop();
        System.out.print("onViewStop");
    }
}

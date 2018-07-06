package com.wnp.lqmall.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.simplexx.wnp.presenter.LoginPresenter;
import com.simplexx.wnp.util.ToastUtils;
import com.wnp.lqmall.R;
import com.wnp.lqmall.base.BasePresenterActivity;
import com.wnp.lqmall.ioc.component.PresenterComponent;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by wnp on 2018/7/2.
 */
@RuntimePermissions
public class LoginActivity extends BasePresenterActivity<LoginPresenter, LoginPresenter.ILoginView>
        implements LoginPresenter.ILoginView {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        getPresenter().request();
        LoginActivityPermissionsDispatcher.showCameraWithPermissionCheck(this);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void showCamera() {
        ToastUtils.showShort(LoginActivity.this, "显示出相机");
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleCamera(PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("需要请求相机权限")
                .setPositiveButton("允许", (dialog, button) -> request.proceed())
                .setNegativeButton("拒绝", (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        ToastUtils.showShort(LoginActivity.this, "请求权限失败");
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        ToastUtils.showShort(LoginActivity.this, "请求权限失败,不允许在被请求");
    }

    @Override
    protected void injectPresenter(PresenterComponent component, LoginPresenter preseneter) {
        component.inject(preseneter);
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onForceLogout() {

    }

    @Override
    protected void onTokenOutDate() {

    }
}

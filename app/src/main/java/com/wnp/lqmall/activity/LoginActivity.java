package com.wnp.lqmall.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.simplexx.wnp.presenter.LoginPresenter;
import com.simplexx.wnp.util.ToastUtils;
import com.simplexx.wnp.util.ui.StatusBarUtil;
import com.wnp.lqmall.R;
import com.wnp.lqmall.base.BasePresenterActivity;
import com.wnp.lqmall.ioc.component.PresenterComponent;
import com.wnp.lqmall.loader.ImageLoaderUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ImageLoaderUtil.INSTANT.loadImage(Uri.parse("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1532000455&di=90bc26ca2b75f28115f93cc48425a969&src=http://s1.sinaimg.cn/mw690/006LDoUHzy7auXElZGE40&690"
        ), imageView);
        mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(false);
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

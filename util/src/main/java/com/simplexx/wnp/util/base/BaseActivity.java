package com.simplexx.wnp.util.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.simplexx.wnp.baselib.basemvp.IView;

/**
 * Created by wnp on 2018/6/25.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}

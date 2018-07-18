package com.wnp.lqmall.loader;

import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by wnp on 2018/7/11.
 */

public interface IImageLoader {


    void loadImage(Uri uri, ImageView imageView, ImageLoaderOptions options);

    void loadImage(Uri uri, ImageView imageView, ImageLoaderOptions options, CallBack callBack);

    //获取图片的回调
    interface CallBack {

        Runnable sucess();

        Runnable error();
    }

}

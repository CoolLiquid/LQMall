package com.wnp.lqmall.loader;

import android.app.Application;
import android.net.Uri;
import android.widget.ImageView;

import com.wnp.lqmall.R;
import com.wnp.lqmall.loader.transform.FaceCenterCrop;
import com.wnp.lqmall.loader.transform.core.PicassoFaceDetector;

/**
 * Created by wnp on 2018/7/18.
 */

public class ImageLoaderUtil {

    public static ImageLoaderUtil INSTANT = new ImageLoaderUtil();

    private PicassonImageLoader picassonImageLoader;
    private ImageLoaderOptions.ImageTransform<FaceCenterCrop> faceCenterCropImageTransform;


    public void init(Application application) {
        picassonImageLoader = PicassonImageLoader.getInstant(application);
        PicassoFaceDetector.initialize(application);//初始化人脸裁图
    }


    public void loadUserIcon(Uri url, ImageView imageView) {
        this.loadUserIcon(url, imageView, null);
    }

    public void loadUserIcon(Uri uri, ImageView imageView, IImageLoader.CallBack callBack) {
        picassonImageLoader.loadImage(uri, imageView, createUserOptions(imageView), callBack);
    }

    public void loadImage(Uri uri, ImageView imageView) {
        this.loadImage(uri, imageView, null);
    }

    public void loadImage(Uri uri, ImageView imageView, IImageLoader.CallBack callBack) {
        picassonImageLoader.loadImage(uri, imageView, createImageOptions(imageView), callBack);
    }

    private ImageLoaderOptions createUserOptions(ImageView imageView) {
        ImageLoaderOptions.Builder builder = new ImageLoaderOptions.Builder();
        builder.setSkipMemoryCache(true)
                .setSkipSaveMemoryCache(true)
                .setSkipSaveDiskCache(true)
                .setImageTransform(new ImageLoaderOptions.
                        ImageTransform<FaceCenterCrop>(new FaceCenterCrop(imageView.getWidth(),
                        imageView.getHeight())))
                .setPlaceHolder(R.mipmap.default_portrait)
                .setErroeDrawable(R.mipmap.default_portrait);
        return builder.build();
    }

    private ImageLoaderOptions createImageOptions(ImageView imageView) {
        ImageLoaderOptions.Builder builder = new ImageLoaderOptions.Builder();
        builder.setSkipMemoryCache(false)
                .setSkipSaveDiskCache(false)
                .setSkipSaveMemoryCache(false)
                .setPlaceHolder(R.mipmap.icon_pic_loding)
                .setErroeDrawable(R.mipmap.default_portrait_circle_corner_rect_error);
        return builder.build();
    }
}

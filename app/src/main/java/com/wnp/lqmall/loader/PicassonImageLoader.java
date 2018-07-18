package com.wnp.lqmall.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import com.wnp.lqmall.http.OkHttp3Downloader;

/**
 * Created by wnp on 2018/7/11.
 */

public class PicassonImageLoader implements IImageLoader {
    private static PicassonImageLoader INSTANT;

    public static PicassonImageLoader getInstant(Context context) {
        if (INSTANT == null) {
            INSTANT = new PicassonImageLoader(context);
        }
        return INSTANT;
    }

    private Picasso picasso;
    private OkHttp3Downloader okHttp3Downloader;

    public PicassonImageLoader(Context context) {
        okHttp3Downloader = new OkHttp3Downloader(context, "tgnet-cahce-image");
        picasso = new Picasso
                .Builder(context)
                .downloader(okHttp3Downloader)
                .build();
    }

    @Override
    public void loadImage(Uri uri, ImageView imageView, ImageLoaderOptions options) {
        loadImage(uri, imageView, options, null);
    }

    @Override
    public void loadImage(Uri uri, ImageView imageView, ImageLoaderOptions options, CallBack callBack) {
        if (options != null && options.isClearMemory()) {
            picasso.invalidate(uri);
            picasso.invalidate(uri);
        }
        RequestCreator requestCreator = createRequestCreator(uri);
        parseImageLoaderOptions(requestCreator, options);
        requestCreator.into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (callBack != null) {
                    callBack.sucess().run();
                }
            }

            @Override
            public void onError() {
                if (callBack != null) {
                    callBack.error().run();
                }
            }
        });
    }

    public RequestCreator createRequestCreator(Uri url) {
        return picasso.load(url).fit();
    }

    private PicassonImageLoader parseImageLoaderOptions(RequestCreator requestCreator, ImageLoaderOptions options) {
        if (options.isSkipMemoryCache()) {
            requestCreator
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE);
        }

        if (options.isSkipSaveDiskCache) {
            requestCreator
                    .networkPolicy(NetworkPolicy.NO_STORE);
        }

        if (options.isSkipSaveMemoryCache) {
            requestCreator.memoryPolicy(MemoryPolicy.NO_STORE);
        }

        if (options.getPlaceHolder() != -1) {
            requestCreator.placeholder(options.getPlaceHolder());
        }

        if (options.getErroeDrawable() != -1) {
            requestCreator.error(options.getErroeDrawable());
        }

        if (options.getSize() != null) {
            requestCreator.resize(options.getSize().reWidth, options.getSize().reHeight);
        }

        if (options.getImageTransform() != null) {
            ImageLoaderOptions.ImageTransform imageTransform = options.getImageTransform();
            if (imageTransform.getTransform() instanceof Transformation) {
                requestCreator.transform((Transformation) imageTransform.getTransform());
            }
        }

        if (options.isCrossFade()) {
            //暂时不做任何的处理
        }
        return this;
    }

}

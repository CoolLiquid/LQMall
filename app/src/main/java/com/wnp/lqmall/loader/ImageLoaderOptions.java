package com.wnp.lqmall.loader;

/**
 * Created by wnp on 2018/7/11.
 */

public class ImageLoaderOptions {

    int placeHolder = -1;//当没有成功加载的时候显示的图片
    ImageResize size = null; //重新设定容器宽高
    int erroeDrawable = -1; //加载错误的时候显示的drawable
    boolean isCrossFade = false; //是否渐变平滑的显示图片
    boolean isSkipMemoryCache = false; //请求时是否跳过内存缓存，直接拉去网络图片
    boolean isClearMemory = false;//是否清除缓存（包括内存缓存，还有本地缓存 ）
    ImageTransform imageTransform;
    boolean isSkipSaveMemoryCache;//是否将图片保存到缓存中（内存缓存）
    boolean isSkipSaveDiskCache;//是否将图片保存到缓存中（本地缓存）

    public class ImageResize {
        int reWidth = 0;
        int reHeight = 0;

        public ImageResize(int reWidth, int reHeight) {
            if (reWidth < 0) {
                reWidth = 0;
            }
            if (reHeight < 0) {
                reHeight = 0;
            }
            this.reWidth = reWidth;
            this.reHeight = reHeight;
        }
    }

    public static class ImageTransform<T> {
        T t;

        public ImageTransform(T t) {
            this.t = t;
        }

        public T getTransform() {
            return t;
        }

    }

    public static class Builder {
        int placeHolder = -1;//当没有成功加载的时候显示的图片
        ImageResize size = null; //重新设定容器宽高
        int erroeDrawable = -1; //加载错误的时候显示的drawable
        boolean isCrossFade = false; //是否渐变平滑的显示图片
        boolean isSkipMemoryCache = false; //请求时是否跳过内存缓存，直接拉去网络图片
        boolean isSkipSaveMemoryCache = false; //是否将图片保存到缓存中（内存缓存）
        boolean isSkipSaveDiskCache = false;//是否将图片保存到缓存中（本地缓存）
        boolean isClearMemory = false;//是否清除缓存（包括内存缓存，还有本地缓存 ）
        ImageTransform imageTransform;

        public int getPlaceHolder() {
            return placeHolder;
        }

        public Builder setPlaceHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public ImageResize getSize() {
            return size;
        }

        public Builder setSize(ImageResize size) {
            this.size = size;
            return this;
        }

        public int getErroeDrawable() {
            return erroeDrawable;
        }

        public Builder setErroeDrawable(int erroeDrawable) {
            this.erroeDrawable = erroeDrawable;
            return this;
        }

        public boolean isCrossFade() {
            return isCrossFade;
        }

        public Builder setCrossFade(boolean crossFade) {
            isCrossFade = crossFade;
            return this;
        }

        public boolean isSkipMemoryCache() {
            return isSkipMemoryCache;
        }

        public Builder setSkipMemoryCache(boolean skipMemoryCache) {
            isSkipMemoryCache = skipMemoryCache;
            return this;
        }

        public boolean isClearMemory() {
            return isClearMemory;
        }

        public Builder setClearMemory(boolean clearMemory) {
            isClearMemory = clearMemory;
            return this;
        }

        public <T> Builder setImageTransform(ImageTransform<T> imageTransform) {
            this.imageTransform = imageTransform;
            return this;
        }

        public Builder setSkipSaveMemoryCache(boolean skip) {
            this.isSkipSaveMemoryCache = skip;
            return this;
        }

        public Builder setSkipSaveDiskCache(boolean skip) {
            this.isSkipSaveDiskCache = skip;
            return this;
        }

        public ImageLoaderOptions build() {
            ImageLoaderOptions options = new ImageLoaderOptions();
            options.size = this.size;
            options.erroeDrawable = this.erroeDrawable;
            options.isClearMemory = this.isClearMemory;
            options.isCrossFade = this.isCrossFade;
            options.isSkipMemoryCache = this.isSkipMemoryCache;
            options.placeHolder = this.placeHolder;
            options.imageTransform = this.imageTransform;
            options.isSkipSaveMemoryCache = this.isSkipSaveMemoryCache;
            options.isSkipSaveDiskCache = this.isSkipSaveDiskCache;
            return options;
        }
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageResize getSize() {
        return size;
    }

    public int getErroeDrawable() {
        return erroeDrawable;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public ImageTransform getImageTransform() {
        return imageTransform;
    }
}

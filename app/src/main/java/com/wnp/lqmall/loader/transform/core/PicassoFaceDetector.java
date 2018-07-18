package com.wnp.lqmall.loader.transform.core;

import android.content.Context;

import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by wnp on 2018/7/18.
 */

public class PicassoFaceDetector {
    private static volatile FaceDetector faceDetector;
    private static Context mContext;

    public static Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("Initialize PicassoFaceDetector by calling PicassoFaceDetector.initialize(context).");
        }
        return mContext;
    }

    public static void initialize(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        mContext = context.getApplicationContext(); // To make it independent of activity lifecycle
    }

    private static void initDetector() {
        if (faceDetector == null) {
            synchronized (PicassoFaceDetector.class) {
                if (faceDetector == null) {
                    faceDetector = new
                            FaceDetector.Builder(getContext())
                            .setTrackingEnabled(false)
                            .build();
                }
            }
        }
    }

    public static FaceDetector getFaceDetector() {
        initDetector();
        return faceDetector;
    }

    /**
     * Release the detector when you no longer need it.
     * Remember to call PicassoFaceDetector.initialize(context) if you have to re-use.
     */
    public static void releaseDetector() {
        if (faceDetector != null) {
            faceDetector.release();
            faceDetector = null;
        }
        mContext = null;
    }
}

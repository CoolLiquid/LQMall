package com.simplexx.wnp.util;

import android.Manifest;
import android.app.Application;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.simplexx.wnp.util.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by fan-gk on 2017/4/24.
 */


public class FileHelper {
    private final static String basePath = "lqmall";
    private final static String recordPath = basePath + "/records";
    private final static String recordCacheFilename = "_cache.mp3";
    private static Application application;

    public static void init(Application application) {
        FileHelper.application = application;
    }

    public static boolean isSdCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File getCacheDir() {
        if (isSdCardMounted())
            return application.getExternalCacheDir();
        else
            return application.getCacheDir();
    }

    public static File getRecordFile(String filename) {
        File file = new File(getCacheDir(), recordPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, filename);
    }

    public static String saveRecordFile(String filename, byte[] stream) {
        File file = getRecordFile(filename);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(stream);
            return file.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileOutputStream != null)
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

/*    public static String saveImage(BaseActivity activity, String filename, Bitmap bitmap, boolean isToast) throws IOException {
        if (!activity.getPermissionHelper().isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            activity.getPermissionHelper().request(false, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return null;
        }

        assert bitmap != null;
        assert filename != null;

        String extension = FileUtil.getExtension(filename);
        String fn = String.valueOf(System.currentTimeMillis());

        long date = System.currentTimeMillis() / 1000;

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_ADDED, date);
        values.put(MediaStore.Images.Media.DATE_MODIFIED, date);
        values.put(MediaStore.Images.Media.TITLE, fn);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fn + extension);
        values.put(MediaStore.Images.Media.MIME_TYPE, FileUtil.getMimeType(extension));

        Uri url = null;
        try {
            url = application.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            OutputStream imageOut = application.getContentResolver().openOutputStream(url);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOut);
            } finally {
                imageOut.close();
            }
            application.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, url));
            if(isToast)
            ToastUtils.showShort(activity, "保存成功");
            return url.getEncodedPath();
        } catch (Exception e) {
            if (url != null) {
                application.getContentResolver().delete(url, null, null);
                url = null;
            }
            throw new IOException("图片保存失败", e);
        }

    }*/

    public static Bitmap.CompressFormat parseCompressFormatFromExtension(String extension) {
        if (extension == null)
            return Bitmap.CompressFormat.JPEG;
        switch (extension.toLowerCase()) {
            case ".png":
                return Bitmap.CompressFormat.PNG;
            default:
                return Bitmap.CompressFormat.JPEG;
        }
    }

    /**
     * 获取临时目录
     *
     * @param isSdcard 是否只取sd卡上的目录
     * @return
     */
    public static File getTmpDir(boolean isSdcard) {
        File tmpDir = null;
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (isSdcard && !sdCardExist) {
            if (!sdCardExist) {
                return null;
            }
        }

        if (sdCardExist || isSdcard) {
            tmpDir = new File(Environment.getExternalStorageDirectory(),
                    "TGInformation");
        } else {
            tmpDir = new File(getCacheDir(), "TGInformation");
        }

        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        return tmpDir;
    }
    /**
     * 获取SD卡上的裁剪图片临时目录
     *
     * @return
     */
    public static File getCropImageTmpDirWithSdCard() {
        if (getTmpDir(true) == null) {
            return null;
        }

        File dir = new File(getTmpDir(true), "image_cache");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}


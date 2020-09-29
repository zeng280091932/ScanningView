/*
 *
 */
package com.beauney.scanning.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.view.View;

/**
 * 截屏工具类
 *
 * @author zengjiantao
 * @date 2017-1-24
 */
public class ScreenCapture {

    /**
     * 截取全屏截图
     *
     * @param activity
     * @return
     */
    public static Bitmap captureFullScreen(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();

        Bitmap screenBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        view.destroyDrawingCache();
        return screenBitmap;
    }

    /**
     * 截取指定控件截图
     *
     * @param view
     * @return
     */
    public static Bitmap captureWidget(View view) {
        if (view == null) {
            return null;
        }
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();

        if (width <= 0 || height <= 0 || bitmap == null) {
            return null;
        }
        Bitmap screenBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        view.destroyDrawingCache();
        return screenBitmap;
    }

    /**
     * 截取自定区域的屏幕内容
     *
     * @param activity
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static Bitmap captureSpecifiedScreen(Activity activity, int x,
                                                int y, int width, int height) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Bitmap screenBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
        view.destroyDrawingCache();
        return screenBitmap;
    }

    /**
     * 生成缩略图
     *
     * @param source
     * @param width
     * @param height
     * @return
     */
    public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
        return ThumbnailUtils.extractThumbnail(source, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }
}

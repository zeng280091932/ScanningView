package com.beauney.scanning.library.utils;

import android.content.Context;

/**
 * 屏幕显示工具类
 *
 * @author zengjiantao
 * @since 2020-09-28
 */
public class DisplayUtil {
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param context  上下文
     * @param dipValue （DisplayMetrics类中属性density）
     * @return px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context 上下文
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}

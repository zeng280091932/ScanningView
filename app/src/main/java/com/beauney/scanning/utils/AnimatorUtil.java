package com.beauney.scanning.utils;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;

/**
 * @author zengjiantao
 * @since 2020-09-28
 */
public class AnimatorUtil {

    private static final long SCALE_DURATION = 400L;

    private static final float DEFAULT_SCALE = 1.05f;

    /**
     * 放大效果动画
     *
     * @param view 目标控件
     */
    public static void scaleBigAnimator(View view) {
        scaleBigAnimator(view, DEFAULT_SCALE);
    }

    /**
     * 放大效果动画
     *
     * @param view  目标控件
     * @param scale 放大比例
     */
    public static void scaleBigAnimator(View view, float scale) {
        view.animate().cancel();
        view.animate()
                .scaleX(scale)
                .scaleY(scale)
                .setInterpolator(new AnticipateOvershootInterpolator())
                .setDuration(SCALE_DURATION)
                .start();
    }

    /**
     * 缩小效果动画
     *
     * @param view 目标控件
     */
    public static void scaleSmallAnimator(View view) {
        view.animate().cancel();
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(SCALE_DURATION)
                .start();
    }
}

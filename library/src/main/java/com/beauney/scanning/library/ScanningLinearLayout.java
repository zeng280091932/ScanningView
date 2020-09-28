package com.beauney.scanning.library;

import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.beauney.scanning.library.utils.DisplayUtil;

import androidx.annotation.Nullable;

/**
 * 带扫描发光效果的线性布局
 *
 * @author zengjiantao
 * @since 2018/9/17
 */
public class ScanningLinearLayout extends LinearLayout {
    private static final long ANIMATOR_DURATION = 2400L;
    private static final int DEFAULT_ROUND_DP = 7;

    private Bitmap mBitmap;

    private Bitmap mRounderBitmap;

    private Paint mScanPaint;

    private Paint mRounderPaint;

    private float mLeft;

    private float mStart;

    private float mEnd;

    private ValueAnimator mAnimator;

    private PorterDuffXfermode mPorterDuffXfermode;

    public ScanningLinearLayout(Context context) {
        super(context);
        init();
    }

    public ScanningLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScanningLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void createRounderBitmap() {
        mRounderBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mRounderBitmap);
        //绘制圆角矩形
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), DisplayUtil.dip2px(getContext(), DEFAULT_ROUND_DP), DisplayUtil.dip2px(getContext(), DEFAULT_ROUND_DP), mRounderPaint);
    }

    private void init() {
        //初始化光图片
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.layount_light);
        mLeft = mStart = -mBitmap.getWidth();

        //初始化画笔 设置抗锯齿和防抖动
        mScanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScanPaint.setDither(true);
        mScanPaint.setFilterBitmap(true);//加快显示速度，本设置项依赖于dither和xfermode的设置


        mRounderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRounderPaint.setDither(true);
        mRounderPaint.setStyle(Paint.Style.FILL);
        mRounderPaint.setColor(Color.WHITE);
        mRounderPaint.setFilterBitmap(true);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createRounderBitmap();
        mEnd = w;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        int sc=canvas.saveLayer(0,0,getWidth(),getHeight(),mScanPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmap, mLeft, 0, mScanPaint);
        mScanPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(mRounderBitmap, 0, 0, mScanPaint);
        mScanPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    private void initAnimator() {
        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                Keyframe.ofFloat(0f, mStart),
                Keyframe.ofFloat(.83f, mStart),
                Keyframe.ofFloat(1f, mEnd)
        );
        //初始化动画
        mAnimator = ValueAnimator.ofPropertyValuesHolder(pvhLeft);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(ANIMATOR_DURATION);
//        mAnimator.setStartDelay(ANIMATOR_DELAY);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLeft = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    public void start() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mAnimator == null) {
                    initAnimator();
                } else if (mAnimator.isRunning()) {
                    mAnimator.cancel();
                }
                mAnimator.start();
            }
        });
    }

    public void stop() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        mLeft = mStart;
        postInvalidate();
    }
}

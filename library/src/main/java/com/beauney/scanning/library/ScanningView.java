package com.beauney.scanning.library;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 扫光控件，从左上角向右下角扫光
 *
 * @author zengjiantao
 * @since 2018/9/14
 */
public class ScanningView extends View {
    private static final long ANIMATOR_DURATION = 400L;

    private Paint mScanPaint;

    private Bitmap mBitmap;

    private Rect mSrcRect;

    private RectF mDestRest;

    private float mDistance;

    private double mDegrees;

    private ValueAnimator mAnimator;

    private float mStartY;

    private float mTranslateY;

    private int mLightImage;

    public ScanningView(@NonNull Context context) {
        super(context);
        init();
    }

    public ScanningView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanningView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScanningView);
        mLightImage = typedArray.getResourceId(R.styleable.ScanningView_lightImage, R.drawable.scanning_view_light);
        typedArray.recycle();
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算出控件的对角线夹角角度
        double radians = Math.atan((double) h / (double) w);
        mDegrees = Math.toDegrees(radians);
        //计算出光要移动的距离
        mDistance = (float) (Math.sin(radians) * w * 2);

        //设置光初始绘画位置
        int lightWidth = w > h ? w : h;
        mDestRest.set(-lightWidth, 0, lightWidth * 2, mBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.rotate((float) -mDegrees);
        canvas.translate(-getWidth() / 2, mTranslateY);
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRest, mScanPaint);
        canvas.restore();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    private void init() {
        //初始化光图片
        mBitmap = BitmapFactory.decodeResource(getResources(), mLightImage);
        mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mDestRest = new RectF();
        mTranslateY = mStartY = -mBitmap.getHeight();

        //初始化画笔 设置抗锯齿和防抖动
        mScanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScanPaint.setDither(true);
    }

    private void initAnimator() {
        //初始化动画
        mAnimator = ValueAnimator.ofFloat(mStartY, mDistance);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(ANIMATOR_DURATION);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTranslateY = (float) animation.getAnimatedValue();
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
        mTranslateY = mStartY;
        postInvalidate();
    }
}

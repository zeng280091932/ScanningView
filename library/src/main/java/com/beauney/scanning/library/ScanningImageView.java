package com.beauney.scanning.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;


/**
 * 扫光ImageView
 *
 * @author zengjiantao
 * @since 2018/10/9
 */
public class ScanningImageView extends View {

    private static final long ANIMATOR_DURATION = 1000L;

    private int mLightColor = Color.WHITE;

    private Bitmap mSrcBitmap;

    private Bitmap mLightBitmap;

    private Paint mScanPaint;

    private Paint mLightPaint;

    private PorterDuffXfermode mPorterDuffXfermode;

    private float mLeft;

    private float mStart;

    private float mEnd;

    private ValueAnimator mAnimator;

    private Animator.AnimatorListener mAnimatorListener;

    public ScanningImageView(Context context) {
        super(context);
        init();
    }

    public ScanningImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanningImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScanningImageView);
        mLightColor = typedArray.getColor(R.styleable.ScanningImageView_lightColor, Color.WHITE);
        typedArray.recycle();
    }

    private void createLightBitmap() {
        LinearGradient linearGradient = new LinearGradient(0, 0, getWidth(), getHeight(), new int[]{Color.TRANSPARENT, mLightColor, Color.TRANSPARENT}, new float[]{0.3f, 0.5f, 0.7f}, LinearGradient.TileMode.CLAMP);
        mLightPaint.setShader(linearGradient);

        mLightBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mLightBitmap);
        //绘制圆角矩形
        canvas.drawRect(0, 0, getWidth(), getHeight(), mLightPaint);
    }

    private void init() {
        //初始化画笔 设置抗锯齿和防抖动
        mScanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScanPaint.setDither(true);
        mScanPaint.setFilterBitmap(true);//加快显示速度，本设置项依赖于dither和xfermode的设置

        mLightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLightPaint.setDither(true);
        mLightPaint.setStyle(Paint.Style.FILL);
        mLightPaint.setColor(Color.WHITE);
        mLightPaint.setFilterBitmap(true);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createLightBitmap();
        mLeft = mStart = -w;
        mEnd = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), mScanPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mLightBitmap, mLeft, 0, mScanPaint);
        if (mSrcBitmap != null) {
            mScanPaint.setXfermode(mPorterDuffXfermode);
            canvas.drawBitmap(mSrcBitmap, 0, 0, mScanPaint);
            mScanPaint.setXfermode(null);
        }
        canvas.restoreToCount(sc);
    }

    private void initAnimator() {
        //初始化动画
        mAnimator = ValueAnimator.ofFloat(mStart, mEnd);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(ANIMATOR_DURATION);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLeft = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
                if (mAnimatorListener != null) {
                    mAnimatorListener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                setVisibility(VISIBLE);
                if (mAnimatorListener != null) {
                    mAnimatorListener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                setVisibility(GONE);
                if (mAnimatorListener != null) {
                    mAnimatorListener.onAnimationCancel(animation);
                }
            }
        });
    }

    public void start() {
        if (mSrcBitmap == null){
            return;
        }
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

    public void setSrcBitmap(Bitmap srcBitmap) {
        this.mSrcBitmap = srcBitmap;
    }

    public Bitmap getSrcBitmap() {
        return mSrcBitmap;
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.mAnimatorListener = animatorListener;
    }
}

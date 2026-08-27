package com.taobao.weex.ui.view.refresh.circlebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class CircleProgressBar extends ImageView {
    public static final int DEFAULT_CIRCLE_BG_LIGHT = -328966;
    public static final int DEFAULT_CIRCLE_COLOR = -1048576;
    private static final int DEFAULT_CIRCLE_DIAMETER = 40;
    private static final int FILL_SHADOW_COLOR = 1023410176;
    private static final int KEY_SHADOW_COLOR = 503316480;
    private static final int SHADOW_ELEVATION = 4;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final int STROKE_WIDTH_LARGE = 3;
    private static final float X_OFFSET = 0.0f;
    private static final float Y_OFFSET = 1.75f;
    private boolean isLayoutFinished;
    private int mArrowHeight;
    private int mArrowWidth;
    private int mBackGroundColor;
    private ShapeDrawable mBgCircle;
    private boolean mCircleBackgroundEnabled;
    private int[] mColors;
    private int mDiameter;
    private Handler mHandler;
    private int mInnerRadius;
    private Animation.AnimationListener mListener;
    private int mMax;
    private int mProgress;
    private int mProgressColor;
    public MaterialProgressDrawable mProgressDrawable;
    private int mProgressStokeWidth;
    private int mShadowRadius;
    private boolean mShowArrow;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private class OvalShadow extends OvalShape {
        private int mCircleDiameter;
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint = new Paint();
        private int mShadowRadius;

        public OvalShadow(int i, int i2) {
            this.mShadowRadius = i;
            this.mCircleDiameter = i2;
            float f = this.mCircleDiameter / 2;
            RadialGradient radialGradient = new RadialGradient(f, f, this.mShadowRadius, new int[]{CircleProgressBar.FILL_SHADOW_COLOR, 0}, (float[]) null, Shader.TileMode.CLAMP);
            this.mRadialGradient = radialGradient;
            this.mShadowPaint.setShader(radialGradient);
        }

        @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void draw(Canvas canvas, Paint paint) {
            float width = CircleProgressBar.this.getWidth() / 2;
            float height = CircleProgressBar.this.getHeight() / 2;
            canvas.drawCircle(width, height, (this.mCircleDiameter / 2) + this.mShadowRadius, this.mShadowPaint);
            canvas.drawCircle(width, height, this.mCircleDiameter / 2, paint);
        }
    }

    public CircleProgressBar(Context context) {
        super(context);
        this.mColors = new int[]{-16777216};
        this.isLayoutFinished = false;
        this.mHandler = new Handler() { // from class: com.taobao.weex.ui.view.refresh.circlebar.CircleProgressBar.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                MaterialProgressDrawable materialProgressDrawable;
                super.handleMessage(message);
                if (message.what == 10010) {
                    if (!CircleProgressBar.this.isLayoutFinished || (materialProgressDrawable = CircleProgressBar.this.mProgressDrawable) == null) {
                        postDelayed(new Runnable() { // from class: com.taobao.weex.ui.view.refresh.circlebar.CircleProgressBar.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CircleProgressBar.this.mHandler.sendEmptyMessage(10010);
                            }
                        }, 100L);
                    } else {
                        materialProgressDrawable.start();
                    }
                }
            }
        };
        init(context, null, 0);
    }

    private boolean elevationSupported() {
        return true;
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.mBackGroundColor = DEFAULT_CIRCLE_BG_LIGHT;
        this.mProgressColor = DEFAULT_CIRCLE_COLOR;
        this.mColors = new int[]{DEFAULT_CIRCLE_COLOR};
        this.mInnerRadius = -1;
        this.mProgressStokeWidth = (int) (f * 3.0f);
        this.mArrowWidth = -1;
        this.mArrowHeight = -1;
        this.mShowArrow = true;
        this.mCircleBackgroundEnabled = true;
        this.mProgress = 0;
        this.mMax = 100;
        MaterialProgressDrawable materialProgressDrawable = new MaterialProgressDrawable(getContext(), this);
        this.mProgressDrawable = materialProgressDrawable;
        super.setImageDrawable(materialProgressDrawable);
    }

    public boolean circleBackgroundEnabled() {
        return this.mCircleBackgroundEnabled;
    }

    public void destory() {
        this.mProgressDrawable.stop();
        this.mProgressDrawable = null;
    }

    public int getMax() {
        return this.mMax;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public int getProgressStokeWidth() {
        return this.mProgressStokeWidth;
    }

    public boolean isShowArrow() {
        return this.mShowArrow;
    }

    @Override // android.view.View
    public void onAnimationEnd() {
        super.onAnimationEnd();
        Animation.AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationEnd(getAnimation());
        }
    }

    @Override // android.view.View
    public void onAnimationStart() {
        super.onAnimationStart();
        Animation.AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationStart(getAnimation());
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialProgressDrawable materialProgressDrawable = this.mProgressDrawable;
        if (materialProgressDrawable != null) {
            materialProgressDrawable.stop();
            this.mProgressDrawable.setVisible(getVisibility() == 0, false);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MaterialProgressDrawable materialProgressDrawable = this.mProgressDrawable;
        if (materialProgressDrawable != null) {
            materialProgressDrawable.stop();
            this.mProgressDrawable.setVisible(false, false);
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        float f = getContext().getResources().getDisplayMetrics().density;
        int iMin = Math.min(getMeasuredWidth(), getMeasuredHeight());
        this.mDiameter = iMin;
        if (iMin <= 0) {
            this.mDiameter = ((int) f) * 40;
        }
        if (getBackground() == null && this.mCircleBackgroundEnabled) {
            int i5 = (int) (Y_OFFSET * f);
            int i6 = (int) (f * 0.0f);
            this.mShadowRadius = (int) (SHADOW_RADIUS * f);
            if (elevationSupported()) {
                this.mBgCircle = new ShapeDrawable(new OvalShape());
                ViewCompat.setElevation(this, f * 4.0f);
            } else {
                int i7 = this.mShadowRadius;
                ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShadow(i7, this.mDiameter - (i7 * 2)));
                this.mBgCircle = shapeDrawable;
                ViewCompat.setLayerType(this, 1, shapeDrawable.getPaint());
                this.mBgCircle.getPaint().setShadowLayer(this.mShadowRadius, i6, i5, KEY_SHADOW_COLOR);
                int i8 = this.mShadowRadius;
                setPadding(i8, i8, i8, i8);
            }
            this.mBgCircle.getPaint().setColor(this.mBackGroundColor);
            setBackgroundDrawable(this.mBgCircle);
        }
        MaterialProgressDrawable materialProgressDrawable = this.mProgressDrawable;
        if (materialProgressDrawable == null) {
            return;
        }
        materialProgressDrawable.setBackgroundColor(this.mBackGroundColor);
        this.mProgressDrawable.setColorSchemeColors(this.mColors);
        if (isShowArrow()) {
            this.mProgressDrawable.setArrowScale(1.0f);
            this.mProgressDrawable.showArrow(true);
        }
        super.setImageDrawable(null);
        super.setImageDrawable(this.mProgressDrawable);
        this.mProgressDrawable.setAlpha(255);
        if (getVisibility() == 0) {
            this.mProgressDrawable.setStartEndTrim(0.0f, 0.8f);
        }
        this.isLayoutFinished = true;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (elevationSupported()) {
            return;
        }
        setMeasuredDimension(getMeasuredWidth() + (this.mShadowRadius * 2), getMeasuredHeight() + (this.mShadowRadius * 2));
    }

    public void setAnimationListener(Animation.AnimationListener animationListener) {
        this.mListener = animationListener;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(i);
        }
    }

    public void setBackgroundColorResource(int i) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(getResources().getColor(i));
        }
    }

    public void setCircleBackgroundEnabled(boolean z) {
        this.mCircleBackgroundEnabled = z;
    }

    public void setColorSchemeColors(int... iArr) {
        this.mColors = iArr;
        MaterialProgressDrawable materialProgressDrawable = this.mProgressDrawable;
        if (materialProgressDrawable != null) {
            materialProgressDrawable.setColorSchemeColors(iArr);
        }
    }

    public void setMax(int i) {
        this.mMax = i;
    }

    public void setProgress(int i) {
        if (getMax() > 0) {
            this.mProgress = i;
        }
        invalidate();
    }

    public void setProgressBackGroundColor(int i) {
        this.mBackGroundColor = i;
    }

    public void setProgressRotation(float f) {
        MaterialProgressDrawable materialProgressDrawable = this.mProgressDrawable;
        if (materialProgressDrawable != null) {
            materialProgressDrawable.setProgressRotation(f);
        }
    }

    public void setProgressStokeWidth(int i) {
        this.mProgressStokeWidth = (int) (i * getContext().getResources().getDisplayMetrics().density);
    }

    public void setShowArrow(boolean z) {
        this.mShowArrow = z;
    }

    public void setStartEndTrim(float f, float f2) {
        MaterialProgressDrawable materialProgressDrawable = this.mProgressDrawable;
        if (materialProgressDrawable != null) {
            materialProgressDrawable.setStartEndTrim(f, f2);
        }
    }

    public void start() {
        this.mHandler.sendEmptyMessage(10010);
    }

    public void stop() {
        MaterialProgressDrawable materialProgressDrawable = this.mProgressDrawable;
        if (materialProgressDrawable != null) {
            materialProgressDrawable.stop();
        }
        this.mHandler.removeMessages(10010);
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColors = new int[]{-16777216};
        this.isLayoutFinished = false;
        this.mHandler = new Handler() { // from class: com.taobao.weex.ui.view.refresh.circlebar.CircleProgressBar.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                MaterialProgressDrawable materialProgressDrawable;
                super.handleMessage(message);
                if (message.what == 10010) {
                    if (!CircleProgressBar.this.isLayoutFinished || (materialProgressDrawable = CircleProgressBar.this.mProgressDrawable) == null) {
                        postDelayed(new Runnable() { // from class: com.taobao.weex.ui.view.refresh.circlebar.CircleProgressBar.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CircleProgressBar.this.mHandler.sendEmptyMessage(10010);
                            }
                        }, 100L);
                    } else {
                        materialProgressDrawable.start();
                    }
                }
            }
        };
        init(context, attributeSet, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColors = new int[]{-16777216};
        this.isLayoutFinished = false;
        this.mHandler = new Handler() { // from class: com.taobao.weex.ui.view.refresh.circlebar.CircleProgressBar.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                MaterialProgressDrawable materialProgressDrawable;
                super.handleMessage(message);
                if (message.what == 10010) {
                    if (!CircleProgressBar.this.isLayoutFinished || (materialProgressDrawable = CircleProgressBar.this.mProgressDrawable) == null) {
                        postDelayed(new Runnable() { // from class: com.taobao.weex.ui.view.refresh.circlebar.CircleProgressBar.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CircleProgressBar.this.mHandler.sendEmptyMessage(10010);
                            }
                        }, 100L);
                    } else {
                        materialProgressDrawable.start();
                    }
                }
            }
        };
        init(context, attributeSet, i);
    }
}

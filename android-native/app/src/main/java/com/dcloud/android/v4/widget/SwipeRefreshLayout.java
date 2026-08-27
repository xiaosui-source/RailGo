package com.dcloud.android.v4.widget;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import com.dcloud.android.v4.view.NestedScrollingChild;
import com.dcloud.android.v4.view.NestedScrollingChildHelper;
import com.dcloud.android.v4.view.NestedScrollingParent;
import com.dcloud.android.v4.view.NestedScrollingParentHelper;
import com.dcloud.android.v4.view.ViewCompat;
import com.dcloud.android.v4.widget.IRefreshAble;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.PdrUtil;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class SwipeRefreshLayout extends ViewGroup implements NestedScrollingParent, NestedScrollingChild, IRefreshAble {
    private static final int ALPHA_ANIMATION_DURATION = 300;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
    private static final int CIRCLE_BG_LIGHT = -328966;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    public static final int DEFAULT = 1;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    public static final int LARGE = 0;
    private static final int[] LAYOUT_ATTRS = {R.attr.enabled};
    private static final String LOG_TAG = "SwipeRefreshLayout";
    private static final int MAX_ALPHA = 255;
    private static final float MAX_PROGRESS_ANGLE = 0.8f;
    static final int PULL_BOTTOM = -1;
    static final int PULL_DEGREE_GAP = 40;
    private static final int SCALE_DOWN_DURATION = 150;
    private static final int STARTING_PROGRESS_ALPHA = 76;
    private int F_OriginalOffsetTop;
    private float F_SpinnerFinalOffset;
    private float F_TotalDragDistance;
    private boolean isSetOffset;
    private Animation mAlphaMaxAnimation;
    private Animation mAlphaStartAnimation;
    private final Animation mAnimateToCorrectPosition;
    private final Animation mAnimateToStartPosition;
    boolean mBeginRefresh;
    private int mCircleHeight;
    private CircleImageView mCircleView;
    private int mCircleViewIndex;
    private int mCircleWidth;
    private int mCurrentTargetOffsetTop;
    private final DecelerateInterpolator mDecelerateInterpolator;
    View mDrawParentView;
    protected int mFrom;
    boolean mHandledDown;
    private float mInitialDownX;
    private float mInitialDownY;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    JSONObject mJsonData;
    private IRefreshAble.OnRefreshListener mListener;
    private int mMediumAnimationDuration;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private boolean mNotify;
    private boolean mOriginalOffsetCalculated;
    protected int mOriginalOffsetTop;
    private final int[] mParentScrollConsumed;
    View mParentView;
    private final Animation mPeek;
    private boolean mPlusRefreshing;
    private MaterialProgressDrawable mProgress;
    int mPullDirect;
    private boolean mRefreshEnable;
    private Animation.AnimationListener mRefreshListener;
    private boolean mRefreshing;
    private boolean mReturningToStart;
    private boolean mScale;
    private Animation mScaleAnimation;
    private Animation mScaleDownAnimation;
    private Animation mScaleDownToStartAnimation;
    private float mSpinnerFinalOffset;
    private float mStartingScale;
    private View mTarget;
    private float mTotalDragDistance;
    private float mTotalUnconsumed;
    private int mTouchSlop;
    boolean mUseSys;
    private boolean mUsingCustomStart;

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet, boolean z) {
        super(context);
        this.mRefreshing = false;
        this.mTotalDragDistance = -1.0f;
        this.mParentScrollConsumed = new int[2];
        this.mOriginalOffsetCalculated = false;
        this.mIsBeingDragged = false;
        this.mCircleViewIndex = -1;
        this.mPlusRefreshing = false;
        this.mRefreshListener = new Animation.AnimationListener() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (SwipeRefreshLayout.this.mRefreshing) {
                    SwipeRefreshLayout.this.mProgress.setAlpha(255);
                    SwipeRefreshLayout.this.mProgress.start();
                    SwipeRefreshLayout.this.mPlusRefreshing = true;
                    if (SwipeRefreshLayout.this.mNotify && SwipeRefreshLayout.this.mListener != null) {
                        SwipeRefreshLayout.this.mListener.onRefresh(3);
                    }
                } else {
                    SwipeRefreshLayout.this.mProgress.stop();
                    SwipeRefreshLayout.this.mPlusRefreshing = false;
                    SwipeRefreshLayout.this.mCircleView.setVisibility(8);
                    SwipeRefreshLayout.this.setColorViewAlpha(255);
                    if (SwipeRefreshLayout.this.mScale) {
                        SwipeRefreshLayout.this.setAnimationProgress(0.0f);
                    } else {
                        SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
                        swipeRefreshLayout.setTargetOffsetTopAndBottom(swipeRefreshLayout.mOriginalOffsetTop - swipeRefreshLayout.mCurrentTargetOffsetTop, true);
                    }
                }
                SwipeRefreshLayout swipeRefreshLayout2 = SwipeRefreshLayout.this;
                swipeRefreshLayout2.mCurrentTargetOffsetTop = swipeRefreshLayout2.mCircleView.getTop();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
                SwipeRefreshLayout.this.parentInvalidate();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        };
        this.mUseSys = false;
        this.mBeginRefresh = false;
        this.isSetOffset = false;
        this.mJsonData = null;
        this.mDrawParentView = null;
        this.mParentView = null;
        this.mPullDirect = -1;
        this.mRefreshEnable = true;
        this.mHandledDown = false;
        this.mAnimateToCorrectPosition = new Animation() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.7
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                float fAbs = !SwipeRefreshLayout.this.mUsingCustomStart ? SwipeRefreshLayout.this.mSpinnerFinalOffset - Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop) : SwipeRefreshLayout.this.mSpinnerFinalOffset;
                SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
                SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((swipeRefreshLayout.mFrom + ((int) ((((int) fAbs) - r1) * f))) - swipeRefreshLayout.mCircleView.getTop(), false);
                SwipeRefreshLayout.this.mProgress.setArrowScale(1.0f - f);
            }
        };
        this.mPeek = new Animation() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.8
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                float fAbs = !SwipeRefreshLayout.this.mUsingCustomStart ? SwipeRefreshLayout.this.mSpinnerFinalOffset - Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop) : SwipeRefreshLayout.this.mSpinnerFinalOffset;
                SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
                SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((swipeRefreshLayout.mFrom + ((int) ((((int) fAbs) - r1) * f))) - swipeRefreshLayout.mCircleView.getTop(), false);
                SwipeRefreshLayout.this.mProgress.setArrowScale(1.0f - f);
            }
        };
        this.mAnimateToStartPosition = new Animation() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.9
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.moveToStart(f);
            }
        };
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mUseSys = z;
        this.mMediumAnimationDuration = getResources().getInteger(R.integer.config_mediumAnimTime);
        setWillNotDraw(false);
        this.mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            setEnabled(typedArrayObtainStyledAttributes.getBoolean(0, true));
            typedArrayObtainStyledAttributes.recycle();
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int i = (int) (displayMetrics.density * 40.0f);
        this.mCircleWidth = i;
        this.mCircleHeight = i;
        createProgressView();
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        float f = displayMetrics.density * 64.0f;
        this.mSpinnerFinalOffset = f;
        this.mTotalDragDistance = f;
        this.F_SpinnerFinalOffset = f;
        this.F_TotalDragDistance = f;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    private void animateOffsetToCorrectPosition(int i, Animation.AnimationListener animationListener) {
        this.mFrom = i;
        this.mAnimateToCorrectPosition.reset();
        this.mAnimateToCorrectPosition.setDuration(200L);
        this.mAnimateToCorrectPosition.setInterpolator(this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
    }

    private void animateOffsetToStartPosition(int i, Animation.AnimationListener animationListener) {
        if (this.mScale) {
            startScaleDownReturnToStartAnimation(i, animationListener);
            return;
        }
        this.mFrom = i;
        this.mAnimateToStartPosition.reset();
        this.mAnimateToStartPosition.setDuration(200L);
        this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToStartPosition);
    }

    private void cancelRefresh() {
        this.mRefreshing = false;
        this.mProgress.setStartEndTrim(0.0f, 0.0f);
        animateOffsetToStartPosition(this.mCurrentTargetOffsetTop, !this.mScale ? new Animation.AnimationListener() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.6
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (SwipeRefreshLayout.this.mCircleView != null) {
                    SwipeRefreshLayout.this.mCircleView.setVisibility(8);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        } : null);
        this.mProgress.showArrow(false);
    }

    private void createProgressView() {
        this.mCircleView = new CircleImageView(getContext(), -328966, 20.0f, false);
        MaterialProgressDrawable materialProgressDrawable = new MaterialProgressDrawable(getContext(), this);
        this.mProgress = materialProgressDrawable;
        materialProgressDrawable.setBackgroundColor(-328966);
        this.mCircleView.setImageDrawable(this.mProgress);
        this.mCircleView.setVisibility(8);
        addView(this.mCircleView);
    }

    private void ensureTarget() {
        if (this.mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (!childAt.equals(this.mCircleView)) {
                    this.mTarget = childAt;
                    return;
                }
            }
        }
    }

    private void finishSpinner(float f) {
        if (f > this.mTotalDragDistance) {
            setRefreshing(true, true);
        } else {
            cancelRefresh();
        }
    }

    private boolean handleTouchEvent(MotionEvent motionEvent) {
        if (isRefreshing()) {
            return false;
        }
        return motionEvent.getAction() == 0 || this.mHandledDown;
    }

    private boolean isAlphaUsedForScale() {
        return false;
    }

    private boolean isAnimationRunning(Animation animation) {
        return (animation == null || !animation.hasStarted() || animation.hasEnded()) ? false : true;
    }

    private boolean isDrawAble() {
        if (this.mCircleView.getVisibility() != 0 || this.mCircleView.getTop() <= this.mOriginalOffsetTop - this.mCircleView.getMeasuredHeight()) {
            return false;
        }
        return this.mParentView.getScrollY() <= 0 || this.mPlusRefreshing;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveSpinner(float f) {
        this.mProgress.showArrow(true);
        float fMin = Math.min(1.0f, Math.abs(f / this.mTotalDragDistance));
        float fMax = (((float) Math.max(fMin - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float fAbs = Math.abs(f) - this.mTotalDragDistance;
        float f2 = this.mUsingCustomStart ? this.mSpinnerFinalOffset - this.mOriginalOffsetTop : this.mSpinnerFinalOffset;
        double dMax = Math.max(0.0f, Math.min(fAbs, f2 * DECELERATE_INTERPOLATION_FACTOR) / f2) / 4.0f;
        float fPow = ((float) (dMax - Math.pow(dMax, 2.0d))) * DECELERATE_INTERPOLATION_FACTOR;
        int i = this.mOriginalOffsetTop + ((int) ((f2 * fMin) + (f2 * fPow * DECELERATE_INTERPOLATION_FACTOR)));
        if (this.mCircleView.getVisibility() != 0) {
            this.mCircleView.setVisibility(0);
        }
        if (!this.mScale) {
            ViewCompat.setScaleX(this.mCircleView, 1.0f);
            ViewCompat.setScaleY(this.mCircleView, 1.0f);
        }
        float f3 = this.mTotalDragDistance;
        if (f < f3) {
            if (this.mScale) {
                setAnimationProgress(f / f3);
            }
            if (this.mProgress.getAlpha() > 76 && !isAnimationRunning(this.mAlphaStartAnimation)) {
                startProgressAlphaStartAnimation();
            }
            this.mProgress.setStartEndTrim(0.0f, Math.min(MAX_PROGRESS_ANGLE, fMax * MAX_PROGRESS_ANGLE));
            this.mProgress.setArrowScale(Math.min(1.0f, fMax));
        } else if (this.mProgress.getAlpha() < 255 && !isAnimationRunning(this.mAlphaMaxAnimation)) {
            startProgressAlphaMaxAnimation();
            this.mProgress.setStartEndTrim(0.0f, MAX_PROGRESS_ANGLE);
            this.mProgress.setArrowScale(1.0f);
        }
        this.mProgress.setProgressRotation((((fMax * 0.4f) - 0.25f) + (fPow * DECELERATE_INTERPOLATION_FACTOR)) * 0.5f);
        setTargetOffsetTopAndBottom(i - this.mCurrentTargetOffsetTop, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveToStart(float f) {
        setTargetOffsetTopAndBottom((this.mFrom + ((int) ((this.mOriginalOffsetTop - r0) * f))) - this.mCircleView.getTop(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parentInvalidate() {
        if (!isDrawAble() || this.mParentView == null) {
            return;
        }
        Log.d("parentInvalidate", "parentInvalidate");
        int width = ((this.mParentView.getWidth() - this.mCircleWidth) / 2) + this.mParentView.getScrollX();
        int scrollY = this.mOriginalOffsetTop + this.mCircleHeight + this.mParentView.getScrollY();
        this.mDrawParentView.invalidate(width, scrollY, this.mCircleWidth + width, this.mCircleView.getTop() + scrollY + this.mCircleHeight);
    }

    private void peek(int i, Animation.AnimationListener animationListener) {
        this.mFrom = i;
        this.mPeek.reset();
        this.mPeek.setDuration(500L);
        this.mPeek.setInterpolator(this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mPeek);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAnimationProgress(float f) {
        if (isAlphaUsedForScale()) {
            setColorViewAlpha((int) (f * 255.0f));
        } else {
            ViewCompat.setScaleX(this.mCircleView, f);
            ViewCompat.setScaleY(this.mCircleView, f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setColorViewAlpha(int i) {
        this.mCircleView.getBackground().setAlpha(i);
        this.mProgress.setAlpha(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTargetOffsetTopAndBottom(int i, boolean z) {
        this.mCircleView.bringToFront();
        this.mCircleView.offsetTopAndBottom(i);
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
    }

    private Animation startAlphaAnimation(final int i, final int i2) {
        if (this.mScale && isAlphaUsedForScale()) {
            return null;
        }
        Animation animation = new Animation() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.4
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.mProgress.setAlpha((int) (i + ((i2 - r0) * f)));
            }
        };
        animation.setDuration(300L);
        this.mCircleView.setAnimationListener(null);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(animation);
        return animation;
    }

    private void startProgressAlphaMaxAnimation() {
        this.mAlphaMaxAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 255);
    }

    private void startProgressAlphaStartAnimation() {
        this.mAlphaStartAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 76);
    }

    private void startScaleDownAnimation(Animation.AnimationListener animationListener) {
        Animation animation = new Animation() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.3
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(1.0f - f);
            }
        };
        this.mScaleDownAnimation = animation;
        animation.setDuration(150L);
        this.mCircleView.setAnimationListener(animationListener);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownAnimation);
    }

    private void startScaleDownReturnToStartAnimation(int i, Animation.AnimationListener animationListener) {
        this.mFrom = i;
        if (isAlphaUsedForScale()) {
            this.mStartingScale = this.mProgress.getAlpha();
        } else {
            this.mStartingScale = ViewCompat.getScaleX(this.mCircleView);
        }
        Animation animation = new Animation() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.10
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(SwipeRefreshLayout.this.mStartingScale + ((-SwipeRefreshLayout.this.mStartingScale) * f));
                SwipeRefreshLayout.this.moveToStart(f);
            }
        };
        this.mScaleDownToStartAnimation = animation;
        animation.setDuration(150L);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownToStartAnimation);
    }

    private void startScaleUpAnimation(Animation.AnimationListener animationListener) {
        this.mCircleView.setVisibility(0);
        this.mProgress.setAlpha(255);
        Animation animation = new Animation() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.2
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(f);
            }
        };
        this.mScaleAnimation = animation;
        animation.setDuration(this.mMediumAnimationDuration);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleAnimation);
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public void beginRefresh() {
        if (this.mBeginRefresh || this.mCircleView.getVisibility() == 0) {
            return;
        }
        post(new Runnable() { // from class: com.dcloud.android.v4.widget.SwipeRefreshLayout.5
            int offset = 0;

            @Override // java.lang.Runnable
            public void run() {
                if (this.offset >= SwipeRefreshLayout.this.mTotalDragDistance) {
                    SwipeRefreshLayout.this.setRefreshing(true, true);
                    SwipeRefreshLayout.this.mBeginRefresh = false;
                } else {
                    SwipeRefreshLayout.this.moveSpinner(this.offset);
                    SwipeRefreshLayout.this.postDelayed(this, 1L);
                    this.offset += 15;
                }
            }
        });
        this.mBeginRefresh = true;
    }

    public boolean canChildScrollUp() {
        View view = this.mTarget;
        if (view != null) {
            return ViewCompat.canScrollVertically(view, -1);
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mUseSys) {
            super.dispatchDraw(canvas);
        } else {
            parentInvalidate();
        }
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.mNestedScrollingChildHelper.dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public void endRefresh() {
        setRefreshing(false);
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        int i3 = this.mCircleViewIndex;
        return i3 < 0 ? i2 : i2 == i + (-1) ? i3 : i2 >= i3 ? i2 + 1 : i2;
    }

    @Override // android.view.ViewGroup, com.dcloud.android.v4.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public int getProgressCircleDiameter() {
        CircleImageView circleImageView = this.mCircleView;
        if (circleImageView != null) {
            return circleImageView.getMeasuredHeight();
        }
        return 0;
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public boolean hasRefreshOperator() {
        return this.mIsBeingDragged || isRefreshing();
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public boolean isRefreshEnable() {
        return this.mRefreshEnable;
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public boolean isRefreshing() {
        return this.mRefreshing;
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public void onInit(ViewGroup viewGroup, View view, IRefreshAble.OnRefreshListener onRefreshListener) {
        this.mParentView = view;
        this.mDrawParentView = view;
        setOnRefreshListener(onRefreshListener);
        viewGroup.addView(this, -1, -1);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (this.mTarget == null) {
            ensureTarget();
        }
        int measuredWidth2 = this.mCircleView.getMeasuredWidth();
        int measuredHeight = this.mCircleView.getMeasuredHeight();
        int i5 = measuredWidth / 2;
        int i6 = measuredWidth2 / 2;
        int i7 = this.mCurrentTargetOffsetTop;
        this.mCircleView.layout(i5 - i6, i7, i5 + i6, measuredHeight + i7);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mTarget == null) {
            ensureTarget();
        }
        this.mCircleView.measure(View.MeasureSpec.makeMeasureSpec(this.mCircleWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCircleHeight, 1073741824));
        if (!this.mUsingCustomStart && !this.mOriginalOffsetCalculated) {
            this.mOriginalOffsetCalculated = true;
            int i3 = -this.mCircleView.getMeasuredHeight();
            this.mOriginalOffsetTop = i3;
            this.mCurrentTargetOffsetTop = i3;
            this.F_OriginalOffsetTop = i3;
        }
        this.mCircleViewIndex = -1;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            if (getChildAt(i4) == this.mCircleView) {
                this.mCircleViewIndex = i4;
                return;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, com.dcloud.android.v4.view.NestedScrollingParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, com.dcloud.android.v4.view.NestedScrollingParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, com.dcloud.android.v4.view.NestedScrollingParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        if (i2 > 0) {
            float f = this.mTotalUnconsumed;
            if (f > 0.0f) {
                float f2 = i2;
                if (f2 > f) {
                    iArr[1] = i2 - ((int) f);
                    this.mTotalUnconsumed = 0.0f;
                } else {
                    this.mTotalUnconsumed = f - f2;
                    iArr[1] = i2;
                }
                moveSpinner(this.mTotalUnconsumed);
            }
        }
        int[] iArr2 = this.mParentScrollConsumed;
        if (dispatchNestedPreScroll(i - iArr[0], i2 - iArr[1], iArr2, null)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, com.dcloud.android.v4.view.NestedScrollingParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        if (i4 < 0) {
            float fAbs = this.mTotalUnconsumed + Math.abs(i4);
            this.mTotalUnconsumed = fAbs;
            moveSpinner(fAbs);
        }
        dispatchNestedScroll(i, i2, i3, i, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, com.dcloud.android.v4.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, i);
        this.mTotalUnconsumed = 0.0f;
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public void onResize(int i, int i2, float f) {
        parseData(this.mJsonData, i, i2, f);
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public void onSelfDraw(Canvas canvas) {
        if (isDrawAble()) {
            canvas.save();
            int measuredWidth = this.mCircleView.getMeasuredWidth();
            int measuredHeight = this.mCircleView.getMeasuredHeight();
            int width = ((this.mParentView.getWidth() - measuredWidth) / 2) + this.mParentView.getScrollX();
            int iMax = Math.max((this.mParentView.getScrollY() - measuredHeight) + this.mCircleView.getTop(), this.mOriginalOffsetTop);
            canvas.clipRect(width, iMax, measuredWidth + width, iMax + measuredHeight);
            canvas.translate(this.mParentView.getScrollX(), this.mParentView.getScrollY() - measuredHeight);
            super.dispatchDraw(canvas);
            canvas.restore();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0098  */
    @Override // com.dcloud.android.v4.widget.IRefreshAble
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onSelfTouchEvent(android.view.MotionEvent r10) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.dcloud.android.v4.widget.SwipeRefreshLayout.onSelfTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, com.dcloud.android.v4.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        int i2;
        if (!isEnabled() || (i2 = i & 2) == 0) {
            return false;
        }
        startNestedScroll(i2);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, com.dcloud.android.v4.view.NestedScrollingParent
    public void onStopNestedScroll(View view) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view);
        float f = this.mTotalUnconsumed;
        if (f > 0.0f) {
            finishSpinner(f);
            this.mTotalUnconsumed = 0.0f;
        }
        stopNestedScroll();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mUseSys) {
            return onSelfTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public void parseData(JSONObject jSONObject, int i, int i2, float f) {
        if (f == 0.0f || f == 1.0f) {
            try {
                f = this.mParentView.getContext().getResources().getDisplayMetrics().density;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.mOriginalOffsetTop = this.F_OriginalOffsetTop;
        this.mSpinnerFinalOffset = this.F_SpinnerFinalOffset;
        this.mTotalDragDistance = this.F_TotalDragDistance;
        this.mJsonData = jSONObject;
        String strOptString = jSONObject.optString("offset");
        int iConvertToScreenInt = this.mOriginalOffsetTop;
        if (!TextUtils.isEmpty(strOptString)) {
            iConvertToScreenInt = PdrUtil.convertToScreenInt(strOptString, i2, iConvertToScreenInt, f);
        }
        String strOptString2 = jSONObject.optString("height");
        int iConvertToScreenInt2 = (int) this.mTotalDragDistance;
        if (!TextUtils.isEmpty(strOptString2)) {
            iConvertToScreenInt2 = PdrUtil.convertToScreenInt(strOptString2, i2, iConvertToScreenInt2, f);
        }
        String strOptString3 = jSONObject.optString(AbsoluteConst.PULL_REFRESH_RANGE);
        int iConvertToScreenInt3 = (int) this.mSpinnerFinalOffset;
        if (!TextUtils.isEmpty(strOptString3)) {
            iConvertToScreenInt3 = PdrUtil.convertToScreenInt(strOptString3, i2, iConvertToScreenInt3, f);
        }
        int i3 = iConvertToScreenInt3 + iConvertToScreenInt;
        String strOptString4 = jSONObject.optString("color");
        int color = Color.parseColor("#2BD009");
        if (!TextUtils.isEmpty(strOptString4) && strOptString4.startsWith("#")) {
            try {
                color = Color.parseColor(strOptString4);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        setColorSchemeColors(color);
        if (this.mOriginalOffsetTop != iConvertToScreenInt) {
            this.isSetOffset = false;
        }
        if (this.isSetOffset) {
            return;
        }
        this.isSetOffset = true;
        setProgressViewOffset(false, iConvertToScreenInt, i3, iConvertToScreenInt2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        View view = this.mTarget;
        if (view == null || ViewCompat.isNestedScrollingEnabled(view)) {
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    @Deprecated
    public void setColorScheme(int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeColors(int... iArr) {
        ensureTarget();
        this.mProgress.setColorSchemeColors(iArr);
    }

    public void setColorSchemeResources(int... iArr) {
        Resources resources = getResources();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = resources.getColor(iArr[i]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setDistanceToTriggerSync(int i) {
        this.mTotalDragDistance = i;
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(z);
    }

    public void setOnRefreshListener(IRefreshAble.OnRefreshListener onRefreshListener) {
        this.mListener = onRefreshListener;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i) {
        setProgressBackgroundColorSchemeResource(i);
    }

    public void setProgressBackgroundColorSchemeColor(int i) {
        this.mCircleView.setBackgroundColor(i);
        this.mProgress.setBackgroundColor(i);
    }

    public void setProgressBackgroundColorSchemeResource(int i) {
        setProgressBackgroundColorSchemeColor(getResources().getColor(i));
    }

    public void setProgressViewEndTarget(boolean z, int i) {
        this.mSpinnerFinalOffset = i;
        this.mScale = z;
        this.mCircleView.invalidate();
    }

    public void setProgressViewOffset(boolean z, int i, int i2, int i3) {
        this.mScale = z;
        this.mCircleView.setVisibility(8);
        this.mCurrentTargetOffsetTop = i;
        this.mOriginalOffsetTop = i;
        this.mSpinnerFinalOffset = i2;
        this.mTotalDragDistance = i3;
        this.mUsingCustomStart = true;
        this.mCircleView.invalidate();
    }

    @Override // com.dcloud.android.v4.widget.IRefreshAble
    public void setRefreshEnable(boolean z) {
        this.mRefreshEnable = z;
    }

    public void setRefreshing(boolean z) {
        if (!z || this.mRefreshing == z) {
            setRefreshing(z, true);
            return;
        }
        this.mRefreshing = z;
        setTargetOffsetTopAndBottom(((int) (!this.mUsingCustomStart ? this.mSpinnerFinalOffset + this.mOriginalOffsetTop : this.mSpinnerFinalOffset)) - this.mCurrentTargetOffsetTop, true);
        this.mNotify = false;
        startScaleUpAnimation(this.mRefreshListener);
    }

    public void setSize(int i) {
        if (i == 0 || i == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (i == 0) {
                int i2 = (int) (displayMetrics.density * 56.0f);
                this.mCircleWidth = i2;
                this.mCircleHeight = i2;
            } else {
                int i3 = (int) (displayMetrics.density * 40.0f);
                this.mCircleWidth = i3;
                this.mCircleHeight = i3;
            }
            this.mCircleView.setImageDrawable(null);
            this.mProgress.updateSizes(i);
            this.mCircleView.setImageDrawable(this.mProgress);
        }
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public boolean startNestedScroll(int i) {
        return this.mNestedScrollingChildHelper.startNestedScroll(i);
    }

    @Override // android.view.View, com.dcloud.android.v4.view.NestedScrollingChild
    public void stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRefreshing(boolean z, boolean z2) {
        if (this.mRefreshing != z) {
            this.mNotify = z2;
            ensureTarget();
            this.mRefreshing = z;
            if (z) {
                animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop, this.mRefreshListener);
            } else {
                startScaleDownAnimation(this.mRefreshListener);
            }
        }
    }

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, true);
    }
}

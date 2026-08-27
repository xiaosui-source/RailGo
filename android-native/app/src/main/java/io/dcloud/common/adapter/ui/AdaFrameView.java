package io.dcloud.common.adapter.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dcloud.android.v4.widget.IRefreshAble;
import com.dcloud.android.widget.AbsoluteLayout;
import com.dcloud.android.widget.StatusBarView;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.INativeBitmap;
import io.dcloud.common.DHInterface.INativeView;
import io.dcloud.common.DHInterface.IWaiter;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.util.AnimOptions;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.adapter.util.ViewRect;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.core.ui.k;
import io.dcloud.common.core.ui.l;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.SubNViewsUtil;
import io.dcloud.nineoldandroids.animation.Animator;
import io.dcloud.nineoldandroids.view.ViewHelper;
import java.util.ArrayList;
import java.util.Locale;
import org.mozilla.universalchardet.prober.HebrewProber;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class AdaFrameView extends AdaContainerFrameItem implements IFrameView {
    private static final int ERROR = 0;
    private static final int SUCCESS = 1;
    public boolean inStack;
    public boolean interceptTouchEvent;
    public boolean isChildOfFrameView;
    private boolean isTabItem;
    public boolean isTouchEvent;
    public String mAccelerationType;
    public boolean mAnimationCapture;
    Animation.AnimationListener mAnimationListener;
    protected boolean mAnimationStarted;
    BounceView mBounceView;
    private Handler mCaptureHandler;
    private IRefreshAble mCircleRefreshView;
    private Context mContext;
    private ICallBack mErrCallBack;
    protected byte mFrameStatus;
    private int mFrameType;
    private int mLastScreenHeight;
    private ArrayList<IEventCallback> mListeners;
    public Bitmap mLoadingSnapshot;
    public String mNativeViewAction;
    private DHImageView mPageCImageView;
    RefreshView mRefreshView;
    public Bitmap mSnapshot;
    private ICallBack mSucCallBack;
    public l mWindowMgr;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface OnAnimationEnd {
        void onDone();
    }

    protected AdaFrameView(Context context, int i, Object obj) {
        super(context);
        this.isChildOfFrameView = false;
        this.mLoadingSnapshot = null;
        this.mSnapshot = null;
        this.mNativeViewAction = "none";
        this.mAnimationStarted = false;
        this.mRefreshView = null;
        this.mBounceView = null;
        this.mCircleRefreshView = null;
        this.mListeners = null;
        this.mFrameType = 0;
        this.mAccelerationType = "auto";
        this.inStack = true;
        this.interceptTouchEvent = true;
        this.isTouchEvent = true;
        this.mPageCImageView = null;
        this.mLastScreenHeight = 0;
        this.mAnimationCapture = false;
        this.mSucCallBack = null;
        this.mErrCallBack = null;
        this.mCaptureHandler = new Handler() { // from class: io.dcloud.common.adapter.ui.AdaFrameView.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    if (AdaFrameView.this.mErrCallBack != null) {
                        AdaFrameView.this.mErrCallBack.onCallBack(message.arg1, message.obj);
                    }
                } else if (AdaFrameView.this.mSucCallBack != null) {
                    AdaFrameView.this.mSucCallBack.onCallBack(0, null);
                }
                super.handleMessage(message);
            }
        };
        this.mAnimationListener = new Animation.AnimationListener() { // from class: io.dcloud.common.adapter.ui.AdaFrameView.7
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                if (animatorListener != null) {
                    animatorListener.onAnimationEnd(null);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
                Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                if (animatorListener != null) {
                    animatorListener.onAnimationRepeat(null);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                if (animatorListener != null) {
                    animatorListener.onAnimationStart(null);
                }
            }
        };
        this.isTabItem = false;
        this.mFrameType = i;
        initMainView(context, i, obj);
        this.mContext = context;
        this.mNeedOrientationUpdate = true;
        this.mLastScreenHeight = PlatformUtil.SCREEN_HEIGHT(context);
    }

    private void addCaptureImageView(ViewGroup viewGroup, DHImageView dHImageView, Bitmap bitmap) {
        if (dHImageView.getParent() != viewGroup) {
            if (dHImageView.getParent() != null) {
                ((ViewGroup) dHImageView.getParent()).removeView(dHImageView);
            }
            viewGroup.addView(dHImageView);
        }
        dHImageView.bringToFront();
        dHImageView.setImageBitmap(bitmap);
        dHImageView.removeNativeView();
        dHImageView.setVisibility(AdaFrameItem.VISIBLE);
    }

    private boolean captureAnimation(final Animator animator, final int i) {
        boolean z;
        if (obtainFrameOptions().isAnimationOptimization && SubNViewsUtil.startAnimation(this, animator, i)) {
            return true;
        }
        if (!this.mAnimationCapture || !BaseInfo.sAnimationCaptureC) {
            return false;
        }
        final ViewGroup viewGroup = (ViewGroup) obtainMainView();
        if (checkITypeofAble()) {
            return false;
        }
        final k kVar = (k) obtainWebAppRootView().obtainMainView();
        Bitmap bitmapCaptureView = null;
        if (this.mPageCImageView != null && kVar.getHeight() != this.mPageCImageView.getHeight()) {
            this.mPageCImageView.clear();
            this.mPageCImageView = null;
            return false;
        }
        if (this.mPageCImageView == null) {
            DHImageView rightImageView = kVar.getRightImageView();
            this.mPageCImageView = rightImageView;
            rightImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        if (this.mPageCImageView.isSlipping()) {
            return false;
        }
        StringBuilder sb = new StringBuilder("C页面是否启用截图动画方案:");
        sb.append(this.mAnimationCapture && BaseInfo.sAnimationCaptureC);
        sb.append(" | ");
        sb.append(this.mAnimOptions.mAnimType);
        Logger.e("mabo", sb.toString());
        long jCurrentTimeMillis = System.currentTimeMillis();
        INativeView iNativeView = this.mNativeView;
        if (iNativeView != null) {
            if (iNativeView.isAnimate()) {
                this.mPageCImageView.removeNativeView();
                this.mNativeView = null;
            } else {
                this.mPageCImageView.setImageBitmap(null);
                this.mPageCImageView.addNativeView(this, this.mNativeView);
            }
        }
        if (this.mNativeView != null) {
            z = false;
        } else if (i == 0) {
            bitmapCaptureView = this.mLoadingSnapshot;
            if (bitmapCaptureView == null && (bitmapCaptureView = this.mSnapshot) == null) {
                bitmapCaptureView = (Build.VERSION.SDK_INT >= 22 || !this.isChildOfFrameView || viewGroup.getHeight() <= obtainFrameOptions().height) ? PlatformUtil.captureView(viewGroup) : PlatformUtil.captureView(viewGroup, true, true, new Rect(0, 0, viewGroup.getWidth(), obtainFrameOptions().height), "ARGB");
                z = true;
            }
            z = false;
        } else {
            bitmapCaptureView = this.mSnapshot;
            if (bitmapCaptureView == null) {
                bitmapCaptureView = PlatformUtil.captureView(viewGroup);
                z = true;
            }
            z = false;
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        StringBuilder sb2 = new StringBuilder("==============C截图耗时=");
        long j = jCurrentTimeMillis2 - jCurrentTimeMillis;
        sb2.append(j);
        Logger.i("mabo", sb2.toString());
        if (j >= BaseInfo.sTimeoutCapture) {
            int i2 = BaseInfo.sTimeOutCount + 1;
            BaseInfo.sTimeOutCount = i2;
            if (i2 > BaseInfo.sTimeOutMax) {
                BaseInfo.sAnimationCaptureC = false;
            }
        } else if (z) {
            BaseInfo.sTimeOutCount = 0;
        }
        this.mPageCImageView.refreshImagerView();
        if (this.mNativeView == null && (bitmapCaptureView == null || PlatformUtil.isWhiteBitmap(bitmapCaptureView))) {
            return false;
        }
        if (viewGroup.getMeasuredWidth() != 0 && viewGroup.getMeasuredHeight() != 0) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(viewGroup.getMeasuredWidth(), viewGroup.getMeasuredHeight());
            if (Build.VERSION.SDK_INT < 22 && this.isChildOfFrameView) {
                layoutParams.height = (viewGroup.getMeasuredHeight() + obtainFrameOptions().top) - (getParentFrameItem().obtainFrameOptions().isStatusbar ? DeviceInfo.sStatusBarHeight : 0);
                this.mPageCImageView.setPadding(0, obtainFrameOptions().top, 0, 0);
            }
            this.mPageCImageView.setLayoutParams(layoutParams);
        }
        if (this.mNativeView == null) {
            addCaptureImageView(kVar, this.mPageCImageView, bitmapCaptureView);
        } else {
            this.mPageCImageView.bringToFront();
            this.mPageCImageView.setVisibility(0);
        }
        viewGroup.setVisibility(4);
        ViewHelper.setX(this.mPageCImageView, this.mViewOptions.left);
        int i3 = this.mViewOptions.top;
        if (this.isChildOfFrameView) {
            i3 += getParentFrameItem().obtainFrameOptions().isStatusbar ? DeviceInfo.sStatusBarHeight : 0;
        }
        ViewHelper.setY(this.mPageCImageView, i3);
        Animation animation = this.mAnimOptions.mAnimator;
        if (animation == null) {
            animator.setTarget(this.mPageCImageView);
            animator.addListener(new Animator.AnimatorListener() { // from class: io.dcloud.common.adapter.ui.AdaFrameView.1
                @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator2) {
                    BaseInfo.sDoingAnimation = false;
                    if (BaseInfo.sOpenedCount == 0) {
                        BaseInfo.sFullScreenChanged = false;
                    }
                    Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                    if (animatorListener != null) {
                        animatorListener.onAnimationCancel(animator2);
                    }
                }

                @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    if (AdaFrameView.this.mPageCImageView != null) {
                        AdaFrameView.this.mPageCImageView.setIntercept(false);
                        if (i != 0) {
                            AdaFrameView.this.mPageCImageView.setNativeAnimationRuning(false);
                        }
                    }
                    Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                    if (animatorListener != null) {
                        animatorListener.onAnimationEnd(animator2);
                    }
                    byte b = AdaFrameView.this.mAnimOptions.mOption;
                    if (b == 3 || b == 1) {
                        viewGroup.setVisibility(4);
                    } else {
                        viewGroup.setVisibility(0);
                    }
                    kVar.postDelayed(new Runnable() { // from class: io.dcloud.common.adapter.ui.AdaFrameView.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (AdaFrameView.this.mPageCImageView != null) {
                                if (AdaFrameView.this.mPageCImageView.isNativeView()) {
                                    AdaFrameView adaFrameView = AdaFrameView.this;
                                    adaFrameView.handleNativeViewByAction(adaFrameView.mPageCImageView, i);
                                } else {
                                    AdaFrameView.this.mPageCImageView.setVisibility(4);
                                    AdaFrameView.this.mPageCImageView.setImageBitmap(null);
                                    AdaFrameView.this.mPageCImageView.removeNativeView();
                                }
                            }
                            BaseInfo.sDoingAnimation = false;
                            if (BaseInfo.sOpenedCount == 0) {
                                BaseInfo.sFullScreenChanged = false;
                            }
                        }
                    }, (AdaFrameView.this.mPageCImageView == null || !AdaFrameView.this.mPageCImageView.isNativeView()) ? HebrewProber.NORMAL_NUN : 0);
                    animator.removeListener(this);
                }

                @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator2) {
                    Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                    if (animatorListener != null) {
                        animatorListener.onAnimationRepeat(animator2);
                    }
                }

                @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    if (AdaFrameView.this.mPageCImageView != null) {
                        AdaFrameView.this.mPageCImageView.setIntercept(true);
                        if (i != 0) {
                            AdaFrameView.this.mPageCImageView.setNativeAnimationRuning(true);
                        }
                    }
                    BaseInfo.sDoingAnimation = true;
                    Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                    if (animatorListener != null) {
                        animatorListener.onAnimationStart(animator2);
                    }
                }
            });
            animator.start();
        } else {
            animation.setAnimationListener(new Animation.AnimationListener() { // from class: io.dcloud.common.adapter.ui.AdaFrameView.2
                /* JADX WARN: Removed duplicated region for block: B:20:0x0055  */
                @Override // android.view.animation.Animation.AnimationListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public void onAnimationEnd(android.view.animation.Animation r5) {
                    /*
                        r4 = this;
                        io.dcloud.common.adapter.ui.AdaFrameView r5 = io.dcloud.common.adapter.ui.AdaFrameView.this
                        io.dcloud.common.adapter.ui.DHImageView r5 = io.dcloud.common.adapter.ui.AdaFrameView.access$000(r5)
                        r0 = 0
                        if (r5 == 0) goto L12
                        io.dcloud.common.adapter.ui.AdaFrameView r5 = io.dcloud.common.adapter.ui.AdaFrameView.this
                        io.dcloud.common.adapter.ui.DHImageView r5 = io.dcloud.common.adapter.ui.AdaFrameView.access$000(r5)
                        r5.setIntercept(r0)
                    L12:
                        io.dcloud.common.util.BaseInfo.sDoingAnimation = r0
                        io.dcloud.common.adapter.ui.AdaFrameView r5 = io.dcloud.common.adapter.ui.AdaFrameView.this
                        io.dcloud.nineoldandroids.animation.Animator$AnimatorListener r5 = r5.mAnimatorListener
                        if (r5 == 0) goto L1e
                        r1 = 0
                        r5.onAnimationEnd(r1)
                    L1e:
                        io.dcloud.common.adapter.ui.AdaFrameView r5 = io.dcloud.common.adapter.ui.AdaFrameView.this
                        io.dcloud.common.adapter.util.AnimOptions r5 = r5.mAnimOptions
                        byte r5 = r5.mOption
                        r1 = 3
                        r2 = 4
                        if (r5 == r1) goto L32
                        r1 = 1
                        if (r5 != r1) goto L2c
                        goto L32
                    L2c:
                        android.view.ViewGroup r5 = r2
                        r5.setVisibility(r0)
                        goto L37
                    L32:
                        android.view.ViewGroup r5 = r2
                        r5.setVisibility(r2)
                    L37:
                        io.dcloud.common.adapter.ui.AdaFrameView r5 = io.dcloud.common.adapter.ui.AdaFrameView.this
                        io.dcloud.common.adapter.ui.DHImageView r5 = io.dcloud.common.adapter.ui.AdaFrameView.access$000(r5)
                        if (r5 == 0) goto L55
                        io.dcloud.common.adapter.ui.AdaFrameView r5 = io.dcloud.common.adapter.ui.AdaFrameView.this
                        io.dcloud.common.adapter.ui.DHImageView r5 = io.dcloud.common.adapter.ui.AdaFrameView.access$000(r5)
                        r5.setVisibility(r2)
                        io.dcloud.common.adapter.ui.AdaFrameView r5 = io.dcloud.common.adapter.ui.AdaFrameView.this
                        io.dcloud.common.adapter.ui.DHImageView r5 = io.dcloud.common.adapter.ui.AdaFrameView.access$000(r5)
                        boolean r5 = r5.isNativeView()
                        if (r5 == 0) goto L55
                        goto L57
                    L55:
                        r0 = 240(0xf0, float:3.36E-43)
                    L57:
                        android.view.ViewGroup r5 = r2
                        io.dcloud.common.adapter.ui.AdaFrameView$2$1 r1 = new io.dcloud.common.adapter.ui.AdaFrameView$2$1
                        r1.<init>()
                        long r2 = (long) r0
                        r5.postDelayed(r1, r2)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.ui.AdaFrameView.AnonymousClass2.onAnimationEnd(android.view.animation.Animation):void");
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation2) {
                    Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                    if (animatorListener != null) {
                        animatorListener.onAnimationRepeat(null);
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation2) {
                    if (AdaFrameView.this.mPageCImageView != null) {
                        AdaFrameView.this.mPageCImageView.setIntercept(true);
                    }
                    BaseInfo.sDoingAnimation = true;
                    Animator.AnimatorListener animatorListener = AdaFrameView.this.mAnimatorListener;
                    if (animatorListener != null) {
                        animatorListener.onAnimationStart(null);
                    }
                }
            });
            DHImageView dHImageView = this.mPageCImageView;
            if (dHImageView != null) {
                dHImageView.startAnimation(this.mAnimOptions.mAnimator);
            }
        }
        return true;
    }

    private int getFrameHeight() {
        return obtainMainView().getParent() != null ? obtainFrameOptions().height == -1 ? obtainWebAppRootView().obtainMainView().getHeight() : obtainFrameOptions().isStatusbarDodifyHeight ? obtainFrameOptions().height + DeviceInfo.sStatusBarHeight : obtainFrameOptions().height : obtainMainView().getHeight();
    }

    private int indexOfViewInParent(View view, ViewGroup viewGroup) {
        int i = 0;
        while (i < viewGroup.getChildCount() && viewGroup.getChildAt(i) != view) {
            i++;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorMessage(int i, String str) {
        Message message = new Message();
        message.what = 0;
        message.arg1 = i;
        message.obj = str;
        this.mCaptureHandler.sendMessage(message);
    }

    @Override // io.dcloud.common.DHInterface.IFrameViewStatus
    public final void addFrameViewListener(IEventCallback iEventCallback) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        if (iEventCallback == null || this.mListeners.contains(iEventCallback)) {
            return;
        }
        this.mListeners.add(iEventCallback);
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void animate(IWebview iWebview, String str, String str2) {
        if (obtainMainView() instanceof AbsoluteLayout) {
            ((AbsoluteLayout) obtainMainView()).animate(iWebview, str, str2);
        }
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void captureSnapshot(final String str, final ICallBack iCallBack, final ICallBack iCallBack2) {
        new Thread(new Runnable() { // from class: io.dcloud.common.adapter.ui.AdaFrameView.5
            @Override // java.lang.Runnable
            public void run() {
                if (AdaFrameView.this.getContext() == null) {
                    return;
                }
                if (TextUtils.isEmpty(str) || !"loading".equals(str.toLowerCase(Locale.ENGLISH))) {
                    try {
                        AdaFrameView adaFrameView = AdaFrameView.this;
                        adaFrameView.mSnapshot = PlatformUtil.captureView(adaFrameView.obtainMainView());
                        AdaFrameView adaFrameView2 = AdaFrameView.this;
                        if (adaFrameView2.mSnapshot != null) {
                            adaFrameView2.mSucCallBack = iCallBack;
                            AdaFrameView.this.mCaptureHandler.sendEmptyMessage(1);
                            return;
                        } else {
                            adaFrameView2.mErrCallBack = iCallBack2;
                            AdaFrameView adaFrameView3 = AdaFrameView.this;
                            adaFrameView3.sendErrorMessage(-100, adaFrameView3.getContext().getString(R.string.dcloud_common_screenshot_fail));
                            return;
                        }
                    } catch (Exception unused) {
                        AdaFrameView.this.mErrCallBack = iCallBack2;
                        AdaFrameView adaFrameView4 = AdaFrameView.this;
                        adaFrameView4.sendErrorMessage(-100, adaFrameView4.getContext().getString(R.string.dcloud_common_screenshot_fail));
                        return;
                    }
                }
                try {
                    AdaFrameView adaFrameView5 = AdaFrameView.this;
                    adaFrameView5.mLoadingSnapshot = PlatformUtil.captureView(adaFrameView5.obtainMainView());
                    AdaFrameView adaFrameView6 = AdaFrameView.this;
                    if (adaFrameView6.mLoadingSnapshot != null) {
                        adaFrameView6.mSucCallBack = iCallBack;
                        AdaFrameView.this.mCaptureHandler.sendEmptyMessage(1);
                    } else {
                        adaFrameView6.mErrCallBack = iCallBack2;
                        AdaFrameView adaFrameView7 = AdaFrameView.this;
                        adaFrameView7.sendErrorMessage(-100, adaFrameView7.getContext().getString(R.string.dcloud_common_screenshot_fail));
                    }
                } catch (Exception unused2) {
                    AdaFrameView.this.mErrCallBack = iCallBack2;
                    AdaFrameView adaFrameView8 = AdaFrameView.this;
                    adaFrameView8.sendErrorMessage(-100, adaFrameView8.getContext().getString(R.string.dcloud_common_screenshot_fail));
                }
            }
        }).start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void changeWebParentViewRect() {
        int i;
        ViewGroup viewGroup = (ViewGroup) obtainMainView();
        if (obtainWebviewParent() == null) {
            return;
        }
        View viewObtainMainView = obtainWebviewParent().obtainMainView();
        if (viewGroup == null || viewObtainMainView == null) {
            return;
        }
        try {
            if (viewGroup.getHeight() == viewObtainMainView.getHeight()) {
                if (viewGroup.getHeight() != viewObtainMainView.getHeight()) {
                    return;
                }
                ViewOptions viewOptions = this.mViewOptions;
                if (!viewOptions.isStatusbar && (viewOptions.titleNView == null || !obtainApp().obtainStatusBarMgr().isImmersive)) {
                    return;
                }
            }
            if (obtainFrameOptions().hasBackground() && viewObtainMainView.getHeight() == obtainWebviewParent().obtainFrameOptions().height && !this.mViewOptions.isStatusbar && obtainFrameOptions().titleNView == null) {
                return;
            }
            int frameHeight = getFrameHeight();
            int i2 = 0;
            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                View childAt = viewGroup.getChildAt(i3);
                if (childAt != viewObtainMainView) {
                    int height = childAt.getHeight();
                    if (childAt instanceof AbsoluteLayout) {
                        if (((AbsoluteLayout) childAt).getFrameView().mPosition != ViewRect.DOCK_TOP) {
                        }
                    } else if (!(childAt instanceof INativeView)) {
                        if (!(childAt instanceof StatusBarView)) {
                        }
                    } else if (((INativeView) childAt).isDock()) {
                        height = (childAt.getTag() == null || !((childAt.getTag().equals("NavigationBar") || childAt.getTag().equals("titleNView")) && ((INativeView) childAt).isStatusBar())) ? ((INativeView) childAt).getInnerHeight() : ((INativeView) childAt).getInnerHeight() + DeviceInfo.sStatusBarHeight;
                        i = ((INativeView) childAt).isDockTop() ? height : 0;
                    }
                    frameHeight -= height;
                    i2 += i;
                }
            }
            viewObtainMainView.getLayoutParams().height = frameHeight;
            ViewHelper.setY(viewObtainMainView, i2);
            viewObtainMainView.requestLayout();
            viewObtainMainView.invalidate();
            if (this.inStack) {
                return;
            }
            changeWebviewRect();
        } catch (Exception e) {
            Log.e("AdaFrameItem", e.getMessage());
        }
    }

    protected void changeWebviewRect() {
        ViewGroup viewGroupObtainWindowView = obtainWebView().obtainWindowView();
        if (viewGroupObtainWindowView.getParent() instanceof FrameLayout) {
            viewGroupObtainWindowView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        } else if (viewGroupObtainWindowView.getParent() instanceof LinearLayout) {
            viewGroupObtainWindowView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        }
        viewGroupObtainWindowView.requestLayout();
    }

    public void chkUseCaptureAnimation(boolean z, int i, boolean z2) {
        StringBuilder sb = z ? new StringBuilder("B页面") : new StringBuilder("C页面");
        sb.append(i);
        String string = sb.toString();
        if (this.mAccelerationType.equals("none") && !z2) {
            this.mAnimationCapture = false;
            return;
        }
        if (!this.mAccelerationType.equals(AbsoluteConst.ACCELERATION) && this.mSnapshot == null && this.mNativeView == null && Build.VERSION.SDK_INT >= 23) {
            this.mAnimationCapture = false;
            Logger.e("mabo", string + "1是否启用截图动画方案:" + this.mAnimationCapture);
            return;
        }
        if (this.mViewOptions.mUniNViewJson != null) {
            this.mAnimationCapture = false;
            return;
        }
        byte b = this.mAnimOptions.mOption;
        boolean z3 = true;
        boolean z4 = b == 3 || b == 1;
        if (this.mSnapshot == null && this.mNativeView == null && (!obtainWebView().isLoaded() || DeviceInfo.sDeviceSdkVer < 11)) {
            this.mAnimationCapture = false;
            Logger.e("mabo", string + "1是否启用截图动画方案:" + this.mAnimationCapture);
            return;
        }
        if (this.isChildOfFrameView) {
            this.mAnimationCapture = false;
            Logger.e("mabo", string + "2是否启用截图动画方案:" + this.mAnimationCapture);
            return;
        }
        PlatformUtil.MESURE_SCREEN_STATUSBAR_HEIGHT(obtainWebView().getActivity());
        if (this.mLastScreenHeight != PlatformUtil.SCREEN_HEIGHT(this.mContext)) {
            this.mLastScreenHeight = PlatformUtil.SCREEN_HEIGHT(this.mContext);
            this.mAnimationCapture = false;
            Logger.e("mabo", string + "3是否启用截图动画方案:" + this.mAnimationCapture);
            return;
        }
        boolean z5 = (this.mAccelerationType.equals("auto") && !PdrUtil.isEquals(this.mAnimOptions.mAnimType, AnimOptions.ANIM_FADE_IN) && z4 && !PdrUtil.isContains(this.mAnimOptions.mAnimType, "slide")) || this.mAccelerationType.equals(AbsoluteConst.CAPTURE);
        if (!z4) {
            AnimOptions animOptions = this.mAnimOptions;
            byte b2 = animOptions.mOption;
            z = (z5 || PdrUtil.isEquals(animOptions.mAnimType, AnimOptions.ANIM_POP_IN) || PdrUtil.isEquals(this.mAnimOptions.mAnimType, AnimOptions.ANIM_ZOOM_FADE_OUT)) & (b2 == 4 || b2 == 0);
        } else if (z5 || PdrUtil.isEquals(this.mAnimOptions.mAnimType_close, AnimOptions.ANIM_POP_OUT) || PdrUtil.isEquals(this.mAnimOptions.mAnimType, AnimOptions.ANIM_ZOOM_FADE_IN)) {
            z = true;
        }
        if (this.isChildOfFrameView && PdrUtil.isEquals(this.mAnimOptions.mAnimType, AnimOptions.ANIM_FADE_IN)) {
            z = true;
        }
        if (this.mSnapshot == null && this.mNativeView == null) {
            z3 = z;
        }
        this.mAnimationCapture = z3;
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void clearSnapshot(String str) {
        DHImageView dHImageView = this.mPageCImageView;
        if (dHImageView != null) {
            dHImageView.setImageBitmap(null);
        }
        if (TextUtils.isEmpty(str) || !"loading".equals(str.toLowerCase(Locale.ENGLISH))) {
            Bitmap bitmap = this.mSnapshot;
            if (bitmap != null) {
                try {
                    if (!bitmap.isRecycled()) {
                        this.mSnapshot.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.mSnapshot = null;
            return;
        }
        Bitmap bitmap2 = this.mLoadingSnapshot;
        if (bitmap2 != null) {
            try {
                if (!bitmap2.isRecycled()) {
                    this.mLoadingSnapshot.recycle();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.mLoadingSnapshot = null;
    }

    public final void dispatchFrameViewEvents(String str, Object obj) {
        if (this.mListeners != null) {
            Logger.d("AdaFrameView.dispatchFrameViewEvents type=" + str + ";args=" + obj);
            for (int size = this.mListeners.size() + (-1); size >= 0; size--) {
                IEventCallback iEventCallback = this.mListeners.get(size);
                if (PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE)) {
                    this.mListeners.remove(size);
                }
                iEventCallback.onCallBack(str, obj);
            }
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public void dispose() {
        super.dispose();
        if (this.mRefreshView != null) {
            this.mRefreshView = null;
        }
        if (this.mBounceView != null) {
            this.mBounceView = null;
        }
        if (this.mCircleRefreshView != null) {
            this.mCircleRefreshView = null;
        }
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public synchronized void draw(final View view, final INativeBitmap iNativeBitmap, final boolean z, final boolean z2, final boolean z3, final Rect rect, final String str, final ICallBack iCallBack, final ICallBack iCallBack2) {
        this.mCaptureHandler.post(new Runnable() { // from class: io.dcloud.common.adapter.ui.AdaFrameView.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (AdaFrameView.this.getContext() == null) {
                        return;
                    }
                    Bitmap bitmapCaptureWebViewContent = z3 ? PlatformUtil.captureWebViewContent(view, z, z2, rect, str) : PlatformUtil.captureView(view, z, z2, rect, str);
                    if (bitmapCaptureWebViewContent != null) {
                        iNativeBitmap.setBitmap(bitmapCaptureWebViewContent);
                        AdaFrameView.this.mSucCallBack = iCallBack;
                        AdaFrameView.this.mCaptureHandler.sendEmptyMessage(1);
                    } else {
                        AdaFrameView.this.mErrCallBack = iCallBack2;
                        AdaFrameView adaFrameView = AdaFrameView.this;
                        adaFrameView.sendErrorMessage(-101, adaFrameView.getContext().getString(R.string.dcloud_common_screenshot_blank));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    AdaFrameView.this.mErrCallBack = iCallBack2;
                    AdaFrameView adaFrameView2 = AdaFrameView.this;
                    adaFrameView2.sendErrorMessage(-100, adaFrameView2.getContext().getString(R.string.dcloud_common_screenshot_fail));
                }
            }
        });
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public IFrameView findPageB() {
        return obtainWebAppRootView().findFrameViewB(this);
    }

    public IRefreshAble getCircleRefreshView() {
        return this.mCircleRefreshView;
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public int getFrameType() {
        return this.mFrameType;
    }

    public void handleNativeViewByAction(DHImageView dHImageView, int i) {
        if ("none".equals(this.mNativeViewAction) || !("hide".equals(this.mNativeViewAction) || AbsoluteConst.EVENTS_CLOSE.equals(this.mNativeViewAction))) {
            if (i != 1 || dHImageView == null) {
                return;
            }
            dHImageView.clearAnimation();
            dHImageView.setVisibility(4);
            dHImageView.setImageBitmap(null);
            dHImageView.removeNativeView();
            return;
        }
        String viewUUId = this.mNativeView.getViewUUId();
        String str = this.mNativeViewAction;
        if (str.equalsIgnoreCase("hide")) {
            if (dHImageView != null) {
                dHImageView.setVisibility(4);
            }
        } else if (this.mNativeViewAction.equalsIgnoreCase(AbsoluteConst.EVENTS_CLOSE)) {
            if (dHImageView != null) {
                dHImageView.setVisibility(4);
                dHImageView.setImageBitmap(null);
                dHImageView.removeNativeView();
            }
            str = "view_close";
        }
        this.mNativeView = null;
        this.mNativeViewAction = "none";
        if (obtainWindowMgr() != null) {
            obtainWindowMgr().processEvent(IMgr.MgrType.FeatureMgr, 1, new Object[]{obtainWebView(), "nativeobj", str, JSONUtil.createJSONArray("['" + viewUUId + "','" + viewUUId + "']")});
        }
    }

    protected abstract void initMainView(Context context, int i, Object obj);

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void interceptTouchEvent(boolean z) {
        this.interceptTouchEvent = z;
    }

    public boolean isSupportLongTouch() {
        return false;
    }

    public boolean isTabItem() {
        return this.isTabItem || obtainFrameOptions().isTabItem.booleanValue();
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public boolean isWebviewCovered() {
        ViewGroup viewGroupObtainWindowView;
        IWebview iWebviewObtainWebView = obtainWebView();
        if (iWebviewObtainWebView == null || (viewGroupObtainWindowView = iWebviewObtainWebView.obtainWindowView()) == null || viewGroupObtainWindowView.getVisibility() != 0 || viewGroupObtainWindowView.getParent() == null) {
            return true;
        }
        Rect rect = new Rect(0, 0, PlatformUtil.SCREEN_WIDTH(viewGroupObtainWindowView.getContext()), PlatformUtil.SCREEN_HEIGHT(viewGroupObtainWindowView.getContext()));
        Rect rect2 = new Rect();
        viewGroupObtainWindowView.getGlobalVisibleRect(rect2);
        if (!rect.contains(rect2)) {
            return true;
        }
        while (viewGroupObtainWindowView.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) viewGroupObtainWindowView.getParent();
            if (viewGroup.getVisibility() != 0) {
                return true;
            }
            for (int iIndexOfViewInParent = indexOfViewInParent(viewGroupObtainWindowView, viewGroup) + 1; iIndexOfViewInParent < viewGroup.getChildCount(); iIndexOfViewInParent++) {
                View childAt = viewGroup.getChildAt(iIndexOfViewInParent);
                if (childAt.getVisibility() == 0 && !(childAt instanceof IWaiter)) {
                    Rect rect3 = new Rect();
                    childAt.getGlobalVisibleRect(rect3);
                    if (rect3.contains(rect2)) {
                        return true;
                    }
                }
            }
            viewGroupObtainWindowView = viewGroup;
        }
        return viewGroupObtainWindowView.getParent() == null;
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public abstract IApp obtainApp();

    public abstract String obtainPrePlusreadyJs();

    @Override // io.dcloud.common.DHInterface.IFrameViewStatus
    public byte obtainStatus() {
        return this.mFrameStatus;
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public abstract IWebview obtainWebView();

    @Override // io.dcloud.common.DHInterface.IFrameView
    public abstract AbsMgr obtainWindowMgr();

    public void onConfigurationChanged() {
    }

    @Override // io.dcloud.common.DHInterface.IFrameViewStatus
    public void onDestroy() {
        this.mFrameStatus = (byte) 4;
        transition((byte) 4);
        dispose();
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public boolean onDispose() {
        boolean zOnDispose = super.onDispose();
        dispatchFrameViewEvents(AbsoluteConst.EVENTS_CLOSE, obtainWebView());
        DHImageView dHImageView = this.mPageCImageView;
        if (dHImageView != null) {
            dHImageView.setImageBitmap(null);
        }
        return zOnDispose;
    }

    public void onDrawAfter(Canvas canvas) {
        IRefreshAble iRefreshAble;
        if (obtainMainView() == null || obtainMainView().getVisibility() != 0 || (iRefreshAble = this.mCircleRefreshView) == null || !iRefreshAble.isRefreshEnable()) {
            return;
        }
        canvas.save();
        int left = obtainWebviewParent().obtainMainView().getLeft();
        int y = (int) ViewHelper.getY(obtainWebviewParent().obtainMainView());
        ViewOptions viewOptions = this.mViewOptions;
        if (viewOptions != null && viewOptions.isStatusbar && viewOptions.titleNView == null) {
            y += DeviceInfo.sStatusBarHeight;
        }
        canvas.translate(left, y);
        this.mCircleRefreshView.onSelfDraw(canvas);
        canvas.restore();
    }

    @Override // io.dcloud.common.DHInterface.IFrameViewStatus
    public void onInit() {
        this.mFrameStatus = (byte) 0;
    }

    @Override // io.dcloud.common.DHInterface.IFrameViewStatus
    public void onLoading() {
        this.mFrameStatus = (byte) 2;
    }

    @Override // io.dcloud.common.DHInterface.IFrameViewStatus
    public void onPreLoading() {
        this.mFrameStatus = (byte) 1;
    }

    @Override // io.dcloud.common.DHInterface.IFrameViewStatus
    public void onPreShow(IFrameView iFrameView) {
        this.mFrameStatus = (byte) 3;
        transition((byte) 3);
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    protected void onResize() {
        if (obtainApp() == null || !obtainApp().manifestBeParsed()) {
            return;
        }
        super.onResize();
        RefreshView refreshView = this.mRefreshView;
        if (refreshView != null) {
            refreshView.onResize();
        }
        BounceView bounceView = this.mBounceView;
        if (bounceView != null) {
            bounceView.onResize();
        }
        dispatchFrameViewEvents(AbsoluteConst.EVENTS_FRAME_ONRESIZE, null);
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    public void paint(Canvas canvas) {
        super.paint(canvas);
        if (obtainMainView() == null || obtainMainView().getVisibility() != 0 || this.mRefreshView == null || this.isSlipping) {
            return;
        }
        Logger.d(Logger.VIEW_VISIBLE_TAG, "AdaFrameView.paint mRefreshView paint" + this);
        ViewOptions viewOptions = obtainWebviewParent().mViewOptions_birth;
        ViewOptions viewOptions2 = obtainWebviewParent().mViewOptions;
        if (viewOptions == null) {
            viewOptions = this.mViewOptions_birth;
        }
        if (viewOptions2 == null) {
            viewOptions2 = this.mViewOptions;
        }
        int i = viewOptions.left;
        int i2 = viewOptions2.left;
        if (i == i2) {
            i2 = 0;
        }
        this.mRefreshView.paint(canvas, i2 + obtainWebviewParent().obtainMainView().getLeft(), (int) ((viewOptions.top != viewOptions2.top ? r1 : 0) + ViewHelper.getY(obtainWebviewParent().obtainMainView())));
    }

    @Override // io.dcloud.common.DHInterface.IFrameViewStatus
    public final void removeFrameViewListener(IEventCallback iEventCallback) {
        ArrayList<IEventCallback> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.remove(iEventCallback);
        }
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void restore() {
        if (obtainMainView() instanceof AbsoluteLayout) {
            ((AbsoluteLayout) obtainMainView()).restore();
        }
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void setAccelerationType(String str) {
        this.mAccelerationType = str;
    }

    public void setCircleRefreshView(IRefreshAble iRefreshAble) {
        this.mCircleRefreshView = iRefreshAble;
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void setSnapshot(Bitmap bitmap) {
        Bitmap bitmap2 = this.mSnapshot;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mSnapshot.recycle();
        }
        this.mSnapshot = bitmap;
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void setSnapshotView(INativeView iNativeView, String str) {
        this.mNativeView = iNativeView;
        this.mNativeViewAction = str;
    }

    public void setTabItem(boolean z) {
        this.isTabItem = z;
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void setVisible(boolean z, boolean z2) {
        Logger.d(Logger.VIEW_VISIBLE_TAG, "AdaFrameView.setVisible pVisible" + z + "       " + this);
        setVisibility(z ? AdaFrameItem.VISIBLE : AdaFrameItem.INVISIBLE);
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    public void startAnimator(int i) {
        startAnimator(null, i);
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void transition(byte b) {
    }

    public void updateFrameRelViewRect(ViewRect viewRect) {
        ViewRect parentViewRect;
        ViewGroup viewGroup = (ViewGroup) obtainMainView();
        if (!this.mViewOptions.hasBackground()) {
            this.mViewOptions.updateViewData(viewRect);
            if (viewGroup != null && viewGroup.getVisibility() == 0 && this.mRefreshView != null) {
                obtainWebviewParent().reInit();
            }
        } else if (obtainWebviewParent().obtainFrameOptions().allowUpdate) {
            obtainWebviewParent().obtainFrameOptions().updateViewData(viewRect);
        }
        ViewOptions viewOptions = this.mViewOptions;
        int i = viewOptions.top;
        int i2 = viewOptions.height;
        if (this.isChildOfFrameView && (parentViewRect = viewOptions.getParentViewRect()) != null && parentViewRect.isStatusbar && !this.mViewOptions.isStatusbar) {
            if (obtainFrameOptions().isBottomAbsolute()) {
                i2 -= DeviceInfo.sStatusBarHeight;
            }
            i += DeviceInfo.sStatusBarHeight;
        }
        View view = this.mViewImpl;
        ViewOptions viewOptions2 = this.mViewOptions;
        AdaFrameItem.LayoutParamsUtil.setViewLayoutParams(view, viewOptions2.left, i, viewOptions2.width, i2);
        changeWebParentViewRect();
        if (viewGroup != null) {
            obtainMainView().invalidate();
        }
    }

    public void startAnimator(final OnAnimationEnd onAnimationEnd, int i) {
        AnimOptions animOptions = this.mAnimOptions;
        if (animOptions != null) {
            animOptions.mUserFrameItem = this;
            animOptions.sScreenWidth = obtainApp().getInt(0);
            this.mAnimOptions.sScreenHeight = obtainApp().getInt(1);
            Animator animatorCreateAnimation = this.mAnimOptions.createAnimation();
            if (obtainFrameOptions().hasBackground() && this.mAnimOptions.mOption == 2) {
                AdaWebViewParent adaWebViewParentObtainWebviewParent = obtainWebviewParent();
                this.mAnimOptions.mUserFrameItem = adaWebViewParentObtainWebviewParent;
                animatorCreateAnimation.setTarget(adaWebViewParentObtainWebviewParent.obtainMainView());
                animatorCreateAnimation.addListener(this.mAnimatorListener);
                animatorCreateAnimation.start();
            } else if (!captureAnimation(animatorCreateAnimation, i)) {
                DHImageView dHImageView = this.mPageCImageView;
                if (dHImageView != null && !dHImageView.isSlipping()) {
                    this.mPageCImageView.clearAnimation();
                    this.mPageCImageView.setVisibility(4);
                    this.mPageCImageView.setImageBitmap(null);
                }
                this.mViewImpl.bringToFront();
                Animation animation = this.mAnimOptions.mAnimator;
                if (animation == null) {
                    animatorCreateAnimation.setTarget(this.mViewImpl);
                    animatorCreateAnimation.addListener(this.mAnimatorListener);
                    animatorCreateAnimation.start();
                } else {
                    animation.setAnimationListener(this.mAnimationListener);
                    this.mViewImpl.startAnimation(this.mAnimOptions.mAnimator);
                }
            }
            animatorCreateAnimation.setInterpolator(new DecelerateInterpolator());
            AnimOptions animOptions2 = this.mAnimOptions;
            MessageHandler.sendMessage(new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaFrameView.3
                @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
                public void execute(Object obj) {
                    Animator.AnimatorListener animatorListener;
                    AdaFrameView adaFrameView = AdaFrameView.this;
                    if (!adaFrameView.mAnimationStarted && (animatorListener = adaFrameView.mAnimatorListener) != null) {
                        animatorListener.onAnimationEnd(null);
                    }
                    OnAnimationEnd onAnimationEnd2 = onAnimationEnd;
                    if (onAnimationEnd2 != null) {
                        onAnimationEnd2.onDone();
                    }
                    if (BaseInfo.sOpenedCount == 0) {
                        BaseInfo.sFullScreenChanged = false;
                    }
                }
            }, Math.max(animOptions2.duration_show, Math.max(animOptions2.duration_close, animOptions2.duration)), null);
        }
    }
}

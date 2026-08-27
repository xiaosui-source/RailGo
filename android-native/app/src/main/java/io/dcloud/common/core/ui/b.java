package io.dcloud.common.core.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.dcloud.android.widget.AbsoluteLayout;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebAppRootView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.ui.AdaWebViewParent;
import io.dcloud.common.adapter.ui.AdaWebview;
import io.dcloud.common.adapter.util.AnimOptions;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.nineoldandroids.animation.Animator;
import io.dcloud.nineoldandroids.view.ViewHelper;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
class b extends AdaFrameView {
    public static int A = 2;
    public static int B = 3;
    public static int x = 0;
    public static int y = 0;
    public static int z = 1;
    boolean a;
    ArrayList b;
    ArrayList c;
    boolean d;
    private boolean e;
    private boolean f;
    boolean g;
    boolean h;
    boolean i;
    IApp j;
    io.dcloud.common.core.ui.a k;
    AdaWebview l;
    AdaWebViewParent m;
    byte n;
    private boolean o;
    ViewOptions p;
    private Boolean q;
    private int r;
    boolean s;
    Animator.AnimatorListener t;
    boolean u;
    boolean v;
    private boolean w;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Animator.AnimatorListener {

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.common.core.ui.b$a$a, reason: collision with other inner class name */
        class RunnableC0032a implements Runnable {
            RunnableC0032a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                a aVar = a.this;
                if (b.this.mWindowMgr != null) {
                    aVar.a();
                }
            }
        }

        a() {
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            BaseInfo.sDoingAnimation = false;
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            Logger.e("DHFrameView", "---------------------onAnimationEnd");
            AnimOptions animOptions = b.this.getAnimOptions();
            b.this.q = Boolean.FALSE;
            byte b = animOptions.mOption;
            if (b == 1) {
                b.this.setVisibility(AdaFrameItem.GONE);
            } else if (b == 3) {
                b.this.l();
            }
            BaseInfo.sDoingAnimation = false;
            if (b.this.obtainMainView() == null) {
                return;
            }
            b.this.obtainMainView().post(new RunnableC0032a());
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            Logger.e("DHFrameView", "---------------------onAnimationStart");
            BaseInfo.sDoingAnimation = true;
            ((AdaFrameView) b.this).mAnimationStarted = true;
            if (b.this.getAnimOptions().mOption == 2) {
                b.a(b.this.obtainMainView(), ((AdaFrameItem) b.this).mViewOptions.left, ((AdaFrameItem) b.this).mViewOptions.top, "onAnimationStart");
            } else if (BaseInfo.isUniAppAppid(b.this.obtainApp())) {
                b.this.obtainWebView().setIWebViewFocusable(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            b bVar = b.this;
            if (bVar.mWindowMgr == null) {
                return;
            }
            byte b = bVar.getAnimOptions().mOption;
            if (b == 0) {
                View view = i.a;
                if (view != null) {
                    view.clearAnimation();
                }
                b.this.n();
            } else if (b == 1) {
                b.this.i();
            } else if (b == 2) {
                b.this.o();
            } else if (b == 3) {
                b.this.k();
            } else if (b == 4) {
                View view2 = i.a;
                if (view2 != null) {
                    view2.clearAnimation();
                }
                b.this.m();
            }
            b.this.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 70, null);
        }
    }

    b(Context context, l lVar, IApp iApp, io.dcloud.common.core.ui.a aVar, int i, Object obj) {
        super(context, i, obj);
        this.a = false;
        this.d = false;
        this.e = true;
        this.f = false;
        this.g = false;
        this.h = false;
        this.i = false;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = (byte) 2;
        this.o = true;
        this.p = null;
        this.q = Boolean.FALSE;
        this.r = y;
        this.s = false;
        this.t = new a();
        this.u = false;
        this.v = true;
        this.w = false;
        this.lastShowTime = System.currentTimeMillis();
        x++;
        Logger.i("dhframeview", "construction Count=" + x);
        this.mWindowMgr = lVar;
        this.j = iApp;
        this.k = aVar;
        aVar.c().add(this);
        this.e = iApp.isVerticalScreen();
        this.f = iApp.isFullScreen();
    }

    public int c() {
        return this.r;
    }

    boolean d() {
        ArrayList arrayList = this.c;
        return arrayList != null && arrayList.size() > 0;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public void dispose() {
        Logger.e("DHFrameView", "dispose");
        super.dispose();
        if (this.k != null) {
            int frameType = getFrameType();
            if (frameType == 2) {
                this.k.f = null;
            } else if (frameType == 4) {
                this.k.g = null;
            } else if (frameType == 5) {
                this.k.e = null;
            }
            this.k.d().remove(this);
            r();
        }
        this.mWindowMgr = null;
        this.j = null;
        this.mParentFrameItem = null;
        this.k = null;
        this.l = null;
        this.t = null;
    }

    boolean e() {
        ArrayList arrayList = this.b;
        return arrayList != null && arrayList.size() > 0;
    }

    public boolean f() {
        return this.w;
    }

    boolean g() {
        AnimOptions animOptions = getAnimOptions();
        return (animOptions == null || animOptions.mOption == 1) ? false : true;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    public AdaFrameItem getParent() {
        return this.k;
    }

    void h() {
        if (this.e == this.j.isVerticalScreen() && this.f == this.j.isFullScreen()) {
            return;
        }
        StringBuilder sb = new StringBuilder("onPushToStack frame ");
        sb.append(this.e ? "调整为横屏状态" : "调整为竖屏状态");
        sb.append(this);
        Logger.d(Logger.ANIMATION_TAG, sb.toString());
        resize();
        this.e = this.j.isVerticalScreen();
        this.f = this.j.isFullScreen();
    }

    void i() {
        setVisibility(AdaFrameItem.GONE);
        if (BaseInfo.isUniAppAppid(obtainApp())) {
            obtainWebView().setIWebViewFocusable(false);
        }
        j();
        q();
        a();
        clearAnimInfo();
        Logger.d(Logger.ANIMATION_TAG, "onCloseAnimationEnd;" + this);
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView
    protected void initMainView(Context context, int i, Object obj) {
        if (i != 1) {
            setMainView(new AbsoluteLayout(context, this, this.j));
        }
    }

    public void j() {
        b(true);
    }

    void k() {
        if (BaseInfo.isUniAppAppid(obtainApp())) {
            obtainWebView().setIWebViewFocusable(false);
        }
        dispatchFrameViewEvents("hide", null);
        if (!this.mViewOptions.hasBackground() && !this.isChildOfFrameView && obtainMainView() != null) {
            ViewHelper.setX(obtainMainView(), this.mViewOptions.left);
            ViewHelper.setY(obtainMainView(), this.mViewOptions.top);
            ViewHelper.setScaleX(obtainMainView(), 1.0f);
            ViewHelper.setScaleY(obtainMainView(), 1.0f);
            if (!this.mViewOptions.hasTransparentValue()) {
                ViewHelper.setAlpha(obtainMainView(), 1.0f);
            }
        }
        ViewOptions viewOptions = this.mViewOptions_animate;
        if (viewOptions != null) {
            updateFrameRelViewRect(viewOptions);
            this.mViewOptions_animate = null;
        }
        j();
        this.h = false;
        a();
        clearAnimInfo();
        Logger.d(Logger.ANIMATION_TAG, "onHideAnimationEnd;" + toString());
    }

    void l() {
        setVisible(false, true);
    }

    void m() {
        if (BaseInfo.isUniAppAppid(obtainApp())) {
            obtainWebView().setIWebViewFocusable(true);
        }
        if (BaseInfo.isUniAppAppid(obtainApp()) && !obtainWebView().isUniWebView()) {
            obtainWebView().obtainWindowView().requestFocus();
        }
        dispatchFrameViewEvents(AbsoluteConst.EVENTS_SHOW_ANIMATION_END, null);
        ViewOptions viewOptions = this.mViewOptions_animate;
        if (viewOptions != null) {
            updateFrameRelViewRect(viewOptions);
            this.mViewOptions_animate = null;
        }
        View viewObtainMainView = obtainMainView();
        ViewOptions viewOptions2 = this.mViewOptions;
        a(viewObtainMainView, viewOptions2.left, viewOptions2.top, "onHideShowAnimationEnd");
        j();
        this.inStack = true;
        a();
        clearAnimInfo();
        Logger.d(Logger.ANIMATION_TAG, "onHideShowAnimationEnd;" + toString());
    }

    void n() {
        if (BaseInfo.isUniAppAppid(obtainApp())) {
            obtainWebView().setIWebViewFocusable(true);
        }
        if (BaseInfo.isUniAppAppid(obtainApp()) && !obtainWebView().isUniWebView()) {
            obtainWebView().obtainWindowView().requestFocus();
        }
        dispatchFrameViewEvents(AbsoluteConst.EVENTS_SHOW_ANIMATION_END, null);
        ViewOptions viewOptions = this.mViewOptions_animate;
        if (viewOptions != null) {
            updateFrameRelViewRect(viewOptions);
            this.mViewOptions_animate = null;
        }
        j();
        this.inStack = true;
        a();
        clearAnimInfo();
        Logger.d(Logger.ANIMATION_TAG, "onShowAnimationEnd;" + this);
        if (SDK.isUniMPSDK() || BaseInfo.isUniAppAppid(this.j)) {
            return;
        }
        io.dcloud.p.b.g().a(this.j.getActivity());
    }

    void o() {
        AdaWebViewParent adaWebViewParentObtainWebviewParent = obtainFrameOptions().hasBackground() ? obtainWebviewParent() : this;
        ViewOptions viewOptionsObtainFrameOptions_Animate = adaWebViewParentObtainWebviewParent.obtainFrameOptions_Animate();
        if (viewOptionsObtainFrameOptions_Animate != null) {
            updateFrameRelViewRect(viewOptionsObtainFrameOptions_Animate);
            adaWebViewParentObtainWebviewParent.setFrameOptions_Animate(null);
        }
        View viewObtainMainView = obtainMainView();
        ViewOptions viewOptions = this.mViewOptions;
        a(viewObtainMainView, viewOptions.left, viewOptions.top, "onStyleChangedAnimationEnd");
        j();
        a();
        clearAnimInfo();
        Logger.d(Logger.ANIMATION_TAG, "onStyleChangedAnimationEnd;" + adaWebViewParentObtainWebviewParent.toString());
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameView
    public IApp obtainApp() {
        return this.j;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView
    public String obtainPrePlusreadyJs() {
        l lVar = this.mWindowMgr;
        return lVar != null ? (String) lVar.processEvent(IMgr.MgrType.FeatureMgr, 2, new Object[]{this.j, this}) : "";
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public IWebAppRootView obtainWebAppRootView() {
        return this.k;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameView
    public IWebview obtainWebView() {
        return this.l;
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public AdaWebViewParent obtainWebviewParent() {
        return this.m;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameView
    public AbsMgr obtainWindowMgr() {
        return this.mWindowMgr;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView
    public void onConfigurationChanged() throws NumberFormatException {
        super.onConfigurationChanged();
        resize();
        this.e = this.j.isVerticalScreen();
        this.f = this.j.isFullScreen();
        Logger.d(Logger.Android_System_TAG, "onConfigurationChanged", this);
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameViewStatus
    public void onDestroy() {
        super.onDestroy();
        x--;
        Logger.i("dhframeview", "onDestroy Count=" + x);
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public boolean onDispose() {
        if (getParentFrameItem() != null && (getParentFrameItem() instanceof c)) {
            ((c) getParentFrameItem()).removeFrameView(this);
        }
        return super.onDispose();
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameViewStatus
    public void onInit() {
        super.onInit();
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameViewStatus
    public void onLoading() {
        super.onLoading();
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public void onPopFromStack(boolean z2) {
        super.onPopFromStack(z2);
        IApp iApp = this.j;
        if (iApp == null) {
            StringBuilder sb = new StringBuilder("已经提前出栈了 ");
            sb.append(this.e ? "竖屏出栈" : "横屏出栈");
            sb.append(this);
            Logger.d(Logger.ANIMATION_TAG, sb.toString());
            return;
        }
        this.e = iApp.isVerticalScreen();
        this.f = this.j.isFullScreen();
        StringBuilder sb2 = new StringBuilder("onPopFromStack ");
        sb2.append(this.e ? "竖屏出栈" : "横屏出栈");
        sb2.append(this);
        Logger.d(Logger.ANIMATION_TAG, sb2.toString());
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameViewStatus
    public void onPreLoading() {
        super.onPreLoading();
        if (this.n == 0) {
            t();
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameViewStatus
    public void onPreShow(IFrameView iFrameView) {
        super.onPreShow(iFrameView);
    }

    public void p() {
        this.u = true;
        Logger.d(Logger.ANIMATION_TAG, "onWillDoAnimation " + this);
        io.dcloud.common.core.ui.a aVar = this.k;
        if (aVar != null) {
            aVar.n.a(this);
        }
        if (this.d) {
            ViewOptions viewOptionsObtainFrameOptions_Animate = obtainFrameOptions_Animate();
            ViewOptions viewOptions = this.mViewOptions;
            viewOptions.opacity = viewOptionsObtainFrameOptions_Animate.opacity;
            viewOptions.background = viewOptionsObtainFrameOptions_Animate.background;
            viewOptions.strBackground = viewOptionsObtainFrameOptions_Animate.strBackground;
            b(viewOptions, this, obtainWebviewParent(), (AdaFrameItem) obtainWebView());
        }
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void popFromViewStack() {
        if (this.isChildOfFrameView || !this.inStack) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(this);
        this.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 27, arrayList);
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void pushToViewStack() {
        if (this.isChildOfFrameView || this.inStack) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(this);
        this.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 28, arrayList);
    }

    void q() {
        io.dcloud.common.core.ui.a aVar = this.k;
        if (aVar != null) {
            aVar.g(this);
        }
    }

    public void r() {
        io.dcloud.common.core.ui.a aVar = this.k;
        if (aVar != null) {
            aVar.c().remove(this);
        }
    }

    void s() {
        setAnimatorLinstener(this.t);
    }

    @Override // io.dcloud.common.DHInterface.IFrameView
    public void setNeedRender(boolean z2) {
        this.a = z2;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameView
    public void setVisible(boolean z2, boolean z3) {
        super.setVisible(z2, z3);
        if (z2 && (getParentFrameItem() instanceof c)) {
            ((c) getParentFrameItem()).d(this);
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.adapter.ui.AdaFrameItem
    public void startAnimator(int i) {
        chkUseCaptureAnimation(false, hashCode(), this.mSnapshot != null);
        super.startAnimator(i);
    }

    void t() {
        s();
    }

    public String toString() {
        AdaWebview adaWebview = this.l;
        return adaWebview != null ? adaWebview.toString() : super.toString();
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.DHInterface.IFrameView
    public void transition(byte b) {
        if (this.n == b && b == 2) {
            t();
        }
    }

    void b(ViewOptions viewOptions, AdaFrameItem adaFrameItem, AdaFrameItem adaFrameItem2, AdaFrameItem adaFrameItem3) {
        if (DeviceInfo.sDeviceSdkVer >= 11 && viewOptions.opacity != -1.0f) {
            adaFrameItem.obtainMainView().setAlpha(viewOptions.opacity);
        }
        if (viewOptions.webviewBGTransparent) {
            adaFrameItem3.setBgcolor(0);
        }
        if (viewOptions.isTabItem.booleanValue() && !PdrUtil.isEmpty(viewOptions.strTabBG)) {
            adaFrameItem.setBgcolor(PdrUtil.stringToColor(viewOptions.strTabBG));
            return;
        }
        if (viewOptions.hasBackground()) {
            adaFrameItem.setBgcolor(viewOptions.background);
        } else if (viewOptions.isTransparent()) {
            adaFrameItem2.setBgcolor(0);
            adaFrameItem3.setBgcolor(0);
            adaFrameItem.setBgcolor(0);
        }
    }

    public void c(boolean z2) {
        this.k.k();
        if (!this.isChildOfFrameView) {
            this.k.b(this);
            onPushToStack(isAutoPop());
            io.dcloud.common.core.ui.a aVar = this.k;
            if (aVar != null) {
                if (aVar.d().contains(this)) {
                    aVar.l();
                } else {
                    aVar.e(this);
                }
            }
        } else if (getParentFrameItem() != null) {
            this.k.h(this);
        }
        a(z2);
    }

    public void d(boolean z2) {
        this.w = z2;
    }

    public void a(int i) {
        this.r = i;
    }

    public void a(boolean z2) {
        boolean z3 = true;
        boolean z4 = obtainMainView().getVisibility() == 0;
        setVisible(true, false);
        p();
        this.q = Boolean.TRUE;
        this.s = false;
        int i = obtainApp().getInt(0);
        int i2 = obtainApp().getInt(1);
        if ((i != obtainFrameOptions().width || obtainFrameOptions().height + 1 < i2) && (obtainFrameOptions().width != -1 || obtainFrameOptions().height != -1)) {
            z3 = false;
        }
        if ((z2 || BaseInfo.isDefaultAim) && !this.isChildOfFrameView && !z4) {
            if (z3 && PdrUtil.isEquals(getAnimOptions().mAnimType, AnimOptions.ANIM_POP_IN)) {
                i.a(this, 0);
            }
            if (z2) {
                s();
                startAnimator(0);
            } else {
                n();
            }
        } else if (z2 && PdrUtil.isEquals(getAnimOptions().mAnimType, AnimOptions.ANIM_FADE_IN)) {
            s();
            startAnimator(0);
        } else {
            n();
        }
        this.k.i(this);
    }

    public void b(boolean z2) {
        this.u = false;
        this.mAnimationStarted = true;
        Logger.d(Logger.ANIMATION_TAG, "onDoneAnimation " + this);
        io.dcloud.common.core.ui.a aVar = this.k;
        if (aVar != null) {
            this.b = null;
            if (aVar.n.a() >= 1) {
                if (this.v) {
                    this.k.b(this);
                }
                this.v = true;
                if (!this.isChildOfFrameView) {
                    if (e()) {
                        Logger.d(Logger.ANIMATION_TAG, "on_Done_Animation 动画完后存在窗口入栈；" + this);
                        l lVar = this.mWindowMgr;
                        if (lVar != null) {
                            lVar.processEvent(IMgr.MgrType.WindowMgr, 28, this.b);
                        }
                    }
                    if (d()) {
                        Logger.d(Logger.ANIMATION_TAG, "on_Done_Animation 动画完后存在窗口出栈；" + this);
                        a(this.c);
                    } else {
                        ICallBack iCallBack = this.k.a;
                        if (iCallBack != null) {
                            iCallBack.onCallBack(-1, null);
                        }
                    }
                }
            }
            this.k.n.b(this);
            this.c = null;
            if (z2) {
                this.k.i();
            }
        }
        if (obtainApp() == null || !obtainApp().needRefreshApp()) {
            return;
        }
        obtainApp().obtainMgrData(IMgr.MgrType.FeatureMgr, 1, new Object[]{obtainWebView(), "UI", "updateAppFrameViews", null});
    }

    /* JADX WARN: Multi-variable type inference failed */
    void a(ViewOptions viewOptions, AdaFrameItem adaFrameItem, AdaFrameItem adaFrameItem2, AdaFrameItem adaFrameItem3) {
        this.p = viewOptions;
        ((IWebview) adaFrameItem3).setScrollIndicator(viewOptions.getScrollIndicator());
        b(viewOptions, adaFrameItem, adaFrameItem2, adaFrameItem3);
    }

    static void a(View view, int i, int i2, String str) {
        if (DeviceInfo.sDeviceSdkVer <= 10) {
            view.layout(i, i2, view.getRight() + i, view.getBottom() + i2);
        }
    }

    private void a() {
        this.b = null;
        this.c = null;
        this.d = false;
    }

    private void a(ArrayList arrayList) {
        Logger.d(Logger.ANIMATION_TAG, "removeFrameViewFromViewStack DoAnimation Frame=" + this + ";Will PopFrames=" + arrayList);
        this.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 27, arrayList);
        ICallBack iCallBack = this.k.a;
        if (iCallBack != null) {
            iCallBack.onCallBack(-1, null);
        }
    }

    public void a(int i, int i2) {
        ViewGroup.LayoutParams layoutParams = obtainMainView().getLayoutParams();
        if (layoutParams == null) {
            obtainMainView().setLayoutParams(new ViewGroup.LayoutParams(i, i2));
        } else {
            layoutParams.width = i;
            layoutParams.height = i2;
        }
    }

    protected void b() {
        View viewA;
        try {
            viewA = a(obtainMainView());
        } catch (Exception e) {
            e.printStackTrace();
            viewA = null;
        }
        if (viewA != null) {
            if (viewA instanceof RecyclerView) {
                ((RecyclerView) viewA).scrollToPosition(0);
            } else {
                viewA.scrollTo(0, 0);
            }
        }
    }

    private View a(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view.canScrollVertically(-1)) {
            return view;
        }
        int i = 0;
        while (true) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (i >= viewGroup.getChildCount()) {
                return null;
            }
            View viewA = a(viewGroup.getChildAt(i));
            if (viewA != null) {
                return viewA;
            }
            i++;
        }
    }
}

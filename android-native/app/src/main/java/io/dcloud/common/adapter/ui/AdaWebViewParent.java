package io.dcloud.common.adapter.ui;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dcloud.android.v4.widget.IRefreshAble;
import com.dcloud.android.v4.widget.SwipeRefreshLayout;
import com.dcloud.android.widget.AbsoluteLayout;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.ui.fresh.PullToRefreshBase;
import io.dcloud.common.adapter.ui.fresh.PullToRefreshWebView;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.util.JSONUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AdaWebViewParent extends AdaContainerFrameItem {
    boolean isSetPull2Refresh;
    AdaWebview mAdaWebview;
    PullToRefreshWebViewExt mPullReFreshViewImpl;
    WebParentView webParentRootView;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class PullToRefreshWebViewExt extends PullToRefreshWebView {
        public PullToRefreshWebViewExt(Context context) {
            super(context);
        }

        private boolean directPageIsLaunchPage(IApp iApp) {
            return (TextUtils.isEmpty(iApp.getOriginalDirectPage()) || iApp.obtainWebAppIntent().hasExtra(IntentConst.DIRECT_PAGE)) ? false : true;
        }

        @Override // io.dcloud.common.adapter.ui.fresh.PullToRefreshBase
        protected void onAddRefreshableView(LinearLayout.LayoutParams layoutParams) {
            ViewOptions viewOptionsObtainFrameOptions;
            AdaWebview adaWebview = AdaWebViewParent.this.mAdaWebview;
            if (adaWebview != null) {
                boolean zIsFullScreenOrImmersive = adaWebview.obtainApp().obtainStatusBarMgr().isFullScreenOrImmersive();
                if (Build.VERSION.SDK_INT > 21 && zIsFullScreenOrImmersive && (viewOptionsObtainFrameOptions = AdaWebViewParent.this.mAdaWebview.mFrameView.obtainFrameOptions()) != null && viewOptionsObtainFrameOptions.titleNView == null && !viewOptionsObtainFrameOptions.isStatusbar) {
                    layoutParams.rightMargin = -1;
                }
            }
            super.onAddRefreshableView(layoutParams);
        }

        @Override // io.dcloud.common.adapter.ui.fresh.PullToRefreshBase, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return (AdaWebViewParent.this.mAdaWebview.obtainMainView() != null && AdaWebViewParent.this.mAdaWebview.obtainMainView().getVisibility() == AdaFrameItem.GONE) || super.onTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            super.setLayoutParams(layoutParams);
        }
    }

    AdaWebViewParent(Context context) {
        super(context);
        this.mPullReFreshViewImpl = null;
        this.mAdaWebview = null;
        this.isSetPull2Refresh = false;
        initRootView(context, true);
    }

    private void initPullView(PullToRefreshBase.OnStateChangeListener onStateChangeListener, int i, int i2) throws JSONException {
        if (this.mPullReFreshViewImpl == null) {
            return;
        }
        Logger.d(Logger.VIEW_VISIBLE_TAG, "AdaWebViewParent.initPullView changeStateHeight=" + i2);
        this.mPullReFreshViewImpl.setInterceptTouchEventEnabled(true);
        this.mPullReFreshViewImpl.setOnStateChangeListener(onStateChangeListener);
        this.mPullReFreshViewImpl.init(getContext());
        this.mPullReFreshViewImpl.setHeaderHeight(i2 > i ? i : i2);
        PullToRefreshWebViewExt pullToRefreshWebViewExt = this.mPullReFreshViewImpl;
        if (i <= i2) {
            i = i2;
        }
        pullToRefreshWebViewExt.setHeaderPullDownMaxHeight(i);
        this.mAdaWebview.setWebviewProperty("bounce", "none");
    }

    private void initRootView(Context context, boolean z) {
        this.webParentRootView = new WebParentView(context);
        if (z) {
            PullToRefreshWebViewExt pullToRefreshWebViewExt = new PullToRefreshWebViewExt(context);
            this.mPullReFreshViewImpl = pullToRefreshWebViewExt;
            pullToRefreshWebViewExt.setPullLoadEnabled(false);
            this.mPullReFreshViewImpl.setInterceptTouchEventEnabled(false);
            this.webParentRootView.addView(this.mPullReFreshViewImpl);
        }
        setMainView(this.webParentRootView);
    }

    void beginPullRefresh() {
        if (((AdaFrameView) this.mAdaWebview.obtainFrameView()).getCircleRefreshView() != null && ((AdaFrameView) this.mAdaWebview.obtainFrameView()).getCircleRefreshView().isRefreshEnable()) {
            ((AdaFrameView) this.mAdaWebview.obtainFrameView()).getCircleRefreshView().beginRefresh();
            return;
        }
        PullToRefreshWebViewExt pullToRefreshWebViewExt = this.mPullReFreshViewImpl;
        if (pullToRefreshWebViewExt != null) {
            pullToRefreshWebViewExt.beginPullRefresh();
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public void dispose() {
        super.dispose();
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview != null) {
            adaWebview.dispose();
            this.mAdaWebview = null;
        }
        this.webParentRootView = null;
        setMainView(null);
        if (this.mPullReFreshViewImpl != null) {
            this.mPullReFreshViewImpl = null;
        }
    }

    void endPullRefresh() {
        if (((AdaFrameView) this.mAdaWebview.obtainFrameView()).getCircleRefreshView() != null && ((AdaFrameView) this.mAdaWebview.obtainFrameView()).getCircleRefreshView().isRefreshEnable()) {
            ((AdaFrameView) this.mAdaWebview.obtainFrameView()).getCircleRefreshView().endRefresh();
            return;
        }
        PullToRefreshWebViewExt pullToRefreshWebViewExt = this.mPullReFreshViewImpl;
        if (pullToRefreshWebViewExt != null) {
            pullToRefreshWebViewExt.onPullDownRefreshComplete();
        }
    }

    void fillsWithWebview(AdaWebview adaWebview) {
        this.webParentRootView.setWebView(adaWebview);
        this.mAdaWebview = adaWebview;
        PullToRefreshWebViewExt pullToRefreshWebViewExt = this.mPullReFreshViewImpl;
        if (pullToRefreshWebViewExt != null) {
            pullToRefreshWebViewExt.setRefreshableView(adaWebview.obtainMainView());
            this.mPullReFreshViewImpl.addRefreshableView(this.mAdaWebview.obtainMainView());
            this.mPullReFreshViewImpl.setAppId(this.mAdaWebview.obtainApp().obtainAppId());
            getChilds().add(adaWebview);
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    protected void onResize() {
        int i;
        int i2;
        PullToRefreshWebViewExt pullToRefreshWebViewExt;
        super.onResize();
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview != null) {
            AdaFrameView adaFrameView = (AdaFrameView) adaWebview.obtainFrameView();
            endPullRefresh();
            RefreshView refreshView = adaFrameView.mRefreshView;
            if (refreshView != null) {
                i = refreshView.maxPullHeight;
                i2 = refreshView.changeStateHeight;
            } else {
                BounceView bounceView = adaFrameView.mBounceView;
                if (bounceView != null) {
                    i = bounceView.maxPullHeights[0];
                    i2 = bounceView.changeStateHeights[0];
                } else {
                    if (adaFrameView.getCircleRefreshView() != null && adaFrameView.getCircleRefreshView().isRefreshEnable()) {
                        IRefreshAble circleRefreshView = adaFrameView.getCircleRefreshView();
                        ViewOptions viewOptions = adaFrameView.mViewOptions;
                        circleRefreshView.onResize(viewOptions.width, viewOptions.height, this.mAdaWebview.getScale());
                    }
                    i = 0;
                    i2 = 0;
                }
            }
            if (i2 == 0 || i == 0 || (pullToRefreshWebViewExt = this.mPullReFreshViewImpl) == null) {
                return;
            }
            pullToRefreshWebViewExt.setHeaderHeight(i2 > i ? i : i2);
            PullToRefreshWebViewExt pullToRefreshWebViewExt2 = this.mPullReFreshViewImpl;
            if (i <= i2) {
                i = i2;
            }
            pullToRefreshWebViewExt2.setHeaderPullDownMaxHeight(i);
            this.mPullReFreshViewImpl.refreshLoadingViewsSize();
        }
    }

    void parseBounce(JSONObject jSONObject) {
        int i;
        int i2;
        AdaFrameView adaFrameView = (AdaFrameView) this.mAdaWebview.obtainFrameView();
        if (adaFrameView.mRefreshView != null) {
            return;
        }
        if (adaFrameView.mBounceView == null) {
            adaFrameView.mBounceView = new BounceView(adaFrameView, this.mAdaWebview);
        }
        int i3 = this.mAdaWebview.mViewOptions.height;
        if (jSONObject == null) {
            BounceView bounceView = adaFrameView.mBounceView;
            bounceView.mSupports[0] = true;
            int i4 = i3 / 3;
            bounceView.maxPullHeights[0] = i4;
            i = i4 / 2;
            bounceView.changeStateHeights[0] = i;
            i2 = i4;
        } else {
            adaFrameView.mBounceView.parseJsonOption(jSONObject);
            BounceView bounceView2 = adaFrameView.mBounceView;
            int i5 = bounceView2.maxPullHeights[0];
            i = bounceView2.changeStateHeights[0];
            i2 = i5;
        }
        int i6 = i;
        BounceView bounceView3 = adaFrameView.mBounceView;
        if (bounceView3.mSupports[0]) {
            initPullView(bounceView3, i2, i6);
        } else {
            this.mPullReFreshViewImpl.setInterceptTouchEventEnabled(false);
        }
        adaFrameView.mBounceView.checkOffset(adaFrameView, this.mPullReFreshViewImpl, jSONObject, i6, i2);
        if (!(adaFrameView.obtainMainView() instanceof AbsoluteLayout) || jSONObject.isNull(AbsoluteConst.BOUNCE_SLIDEO_OFFSET)) {
            return;
        }
        ((AbsoluteLayout) adaFrameView.obtainMainView()).initSlideInfo(jSONObject, this.mAdaWebview.getScale(), this.mAdaWebview.obtainWindowView().getWidth());
    }

    void parsePullToReFresh(JSONObject jSONObject) {
        if (this.mPullReFreshViewImpl == null) {
            return;
        }
        AdaFrameView adaFrameView = (AdaFrameView) this.mAdaWebview.obtainFrameView();
        if (adaFrameView.mBounceView != null) {
            return;
        }
        boolean z = Boolean.parseBoolean(JSONUtil.getString(jSONObject, AbsoluteConst.PULL_REFRESH_SUPPORT));
        String strOptString = jSONObject != null ? jSONObject.optString("style", "default") : "default";
        if (!z) {
            this.isSetPull2Refresh = false;
            if (adaFrameView.getCircleRefreshView() != null) {
                adaFrameView.getCircleRefreshView().setRefreshEnable(false);
            }
            this.mPullReFreshViewImpl.setInterceptTouchEventEnabled(false);
            return;
        }
        this.isSetPull2Refresh = true;
        try {
            if (!"circle".equals(strOptString)) {
                if (adaFrameView.mRefreshView == null) {
                    adaFrameView.mRefreshView = new RefreshView(adaFrameView, this.mAdaWebview);
                }
                if (adaFrameView.getCircleRefreshView() != null) {
                    adaFrameView.getCircleRefreshView().setRefreshEnable(false);
                }
                adaFrameView.mRefreshView.parseJsonOption(jSONObject);
                RefreshView refreshView = adaFrameView.mRefreshView;
                initPullView(refreshView, refreshView.maxPullHeight, refreshView.changeStateHeight);
                return;
            }
            if (adaFrameView.getCircleRefreshView() == null) {
                adaFrameView.setCircleRefreshView(new SwipeRefreshLayout(this.mAdaWebview.getContext(), null, false));
                adaFrameView.getCircleRefreshView().onInit(adaFrameView.obtainWebView().obtainWindowView(), adaFrameView.obtainMainViewGroup(), this.mAdaWebview.mWebViewImpl.getRefreshListener());
            }
            IRefreshAble circleRefreshView = adaFrameView.getCircleRefreshView();
            ViewOptions viewOptions = adaFrameView.mViewOptions;
            circleRefreshView.parseData(jSONObject, viewOptions.width, viewOptions.height, this.mAdaWebview.getScale());
            adaFrameView.getCircleRefreshView().setRefreshEnable(true);
            if (adaFrameView.mRefreshView != null) {
                this.mPullReFreshViewImpl.setInterceptTouchEventEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reInit() {
        PullToRefreshWebViewExt pullToRefreshWebViewExt = this.mPullReFreshViewImpl;
        if (pullToRefreshWebViewExt != null) {
            pullToRefreshWebViewExt.refreshLoadingViewsSize();
        }
    }

    void resetBounce() {
        endPullRefresh();
        AdaFrameView adaFrameView = (AdaFrameView) this.mAdaWebview.obtainFrameView();
        if (adaFrameView.obtainMainView() instanceof AbsoluteLayout) {
            ((AbsoluteLayout) adaFrameView.obtainMainView()).reset();
        }
    }

    public String toString() {
        return this.mAdaWebview.toString();
    }

    AdaWebViewParent(Context context, boolean z) {
        super(context);
        this.mPullReFreshViewImpl = null;
        this.mAdaWebview = null;
        this.isSetPull2Refresh = false;
        initRootView(context, z);
    }
}

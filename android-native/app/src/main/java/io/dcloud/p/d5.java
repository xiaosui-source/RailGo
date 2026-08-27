package io.dcloud.p;

import android.R;
import android.app.Activity;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.RelativeLayout;
import io.dcloud.common.DHInterface.IAppInfo;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.IWebAppRootView;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.adapter.util.ViewRect;
import io.dcloud.common.util.AppStatusBarManager;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class d5 implements IAppInfo {
    AppStatusBarManager m;
    protected Activity a = null;
    protected IWebAppRootView b = null;
    private IOnCreateSplashView c = null;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 0;
    public int h = 0;
    protected boolean i = false;
    private boolean j = false;
    private int k = 0;
    ViewRect l = new ViewRect();
    public String n = "none";
    public String o = null;
    protected boolean p = false;
    protected boolean q = false;
    private RelativeLayout r = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements MessageHandler.IMessages {
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
        public void execute(Object obj) {
            if ("landscape".equals(this.a)) {
                d5.this.a.setRequestedOrientation(6);
                return;
            }
            if ("landscape-primary".equals(this.a)) {
                d5.this.a.setRequestedOrientation(0);
                return;
            }
            if ("landscape-secondary".equals(this.a)) {
                d5.this.a.setRequestedOrientation(8);
                return;
            }
            if ("portrait".equals(this.a)) {
                d5.this.a.setRequestedOrientation(7);
                return;
            }
            if ("portrait-primary".equals(this.a)) {
                d5.this.a.setRequestedOrientation(1);
            } else if ("portrait-secondary".equals(this.a)) {
                d5.this.a.setRequestedOrientation(9);
            } else {
                d5.this.a.setRequestedOrientation(4);
            }
        }
    }

    void a(Activity activity) {
        if (this.m == null) {
            this.m = new AppStatusBarManager(activity, this);
        }
        this.a = activity;
    }

    public int checkSelfPermission(String str, String str2) {
        return PermissionUtil.checkSelfPermission(this.a, str, str2);
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public void clearMaskLayerCount() {
        this.k = 0;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public Activity getActivity() {
        return this.a;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public ViewRect getAppViewRect() {
        return this.l;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo, io.dcloud.common.DHInterface.IType_IntValue
    public int getInt(int i) {
        if (i == 0) {
            return this.d;
        }
        if (i == 1) {
            return this.h;
        }
        if (i != 2) {
            return -1;
        }
        return this.e;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public int getMaskLayerCount() {
        return this.k;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public IOnCreateSplashView getOnCreateSplashView() {
        return this.c;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public int getRequestedOrientation() {
        return this.a.getRequestedOrientation();
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public boolean isFullScreen() {
        return this.i;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public boolean isVerticalScreen() {
        return this.a.getResources().getConfiguration().orientation == 1;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public IWebAppRootView obtainWebAppRootView() {
        return this.b;
    }

    public void requestPermissions(String[] strArr, int i) {
        PermissionUtil.requestPermissions(this.a, strArr, i);
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public void setFullScreen(boolean z) {
        if (BaseInfo.sGlobalFullScreen != z) {
            this.i = z;
            AppStatusBarManager appStatusBarManager = this.m;
            if (appStatusBarManager != null) {
                appStatusBarManager.setFullScreen(getActivity(), z);
            }
            updateScreenInfo(this.i ? 2 : 3);
        }
        BaseInfo.sGlobalFullScreen = z;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public void setMaskLayer(boolean z) {
        this.j = z;
        if (z) {
            this.k++;
            return;
        }
        int i = this.k - 1;
        this.k = i;
        if (i < 0) {
            this.k = 0;
        }
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public void setOnCreateSplashView(IOnCreateSplashView iOnCreateSplashView) {
        this.c = iOnCreateSplashView;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public void setRequestedOrientation(String str) {
        try {
            MessageHandler.sendMessage(new a(str), 48L, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public void setWebAppRootView(IWebAppRootView iWebAppRootView) {
        this.b = iWebAppRootView;
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public void updateScreenInfo(int i) {
        if (Build.VERSION.SDK_INT >= 36 && AndroidResources.sAppTargetSdkVersion >= 36) {
            View viewFindViewById = this.a.findViewById(R.id.content);
            if (viewFindViewById != null && (viewFindViewById.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewFindViewById.getLayoutParams();
                WindowInsets rootWindowInsets = this.a.getWindow().getDecorView().getRootWindowInsets();
                if (rootWindowInsets != null) {
                    Insets insets = rootWindowInsets.getInsets(WindowInsets.Type.navigationBars());
                    marginLayoutParams.leftMargin = insets.left;
                    marginLayoutParams.rightMargin = insets.right;
                    marginLayoutParams.bottomMargin = insets.bottom;
                }
            }
            View viewFindViewById2 = this.a.findViewById(R.id.navigationBarBackground);
            if (viewFindViewById2 != null) {
                if (this.r == null) {
                    this.r = new RelativeLayout(this.a);
                }
                if (this.r.getBackground() == null) {
                    this.r.setBackgroundColor(-16777216);
                }
                if (this.r.getParent() == null) {
                    ((ViewGroup) viewFindViewById2.getParent()).addView(this.r, viewFindViewById2.getLayoutParams());
                } else {
                    this.r.setLayoutParams(viewFindViewById2.getLayoutParams());
                }
            }
        }
        if (!this.i && this.f == 0) {
            Rect rect = new Rect();
            this.a.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int i2 = rect.top;
            this.f = i2;
            if (i2 > 0) {
                SP.setBundleData(getActivity(), BaseInfo.PDR, "StatusBarHeight", String.valueOf(this.f));
            }
        }
        DisplayMetrics displayMetrics = this.a.getResources().getDisplayMetrics();
        int i3 = displayMetrics.widthPixels;
        int i4 = displayMetrics.heightPixels;
        boolean z = i3 < i4;
        boolean zIsAllScreenDevice = PdrUtil.isAllScreenDevice(this.a);
        if (zIsAllScreenDevice) {
            this.a.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
            int i5 = displayMetrics.widthPixels;
            int i6 = displayMetrics.heightPixels;
            if (i5 <= i6 || !z) {
                AppStatusBarManager appStatusBarManager = this.m;
                if (appStatusBarManager != null && !appStatusBarManager.isFullScreenOrImmersive()) {
                    i6 -= this.f;
                }
                i4 = i6;
                boolean z2 = PdrUtil.isNavigationBarExist(this.a) && !this.p;
                int navigationBarHeight = PdrUtil.getNavigationBarHeight(this.a);
                if (this.a.getResources().getConfiguration().orientation == 1) {
                    if (z2) {
                        i4 -= navigationBarHeight;
                    }
                } else if (z2) {
                    i3 = i5 - navigationBarHeight;
                }
                i3 = i5;
            }
        }
        this.e = i4;
        if (i == 2) {
            if (this.q) {
                View viewFindViewById3 = this.a.findViewById(R.id.content);
                this.d = viewFindViewById3.getMeasuredWidth();
                this.h = viewFindViewById3.getMeasuredHeight();
            } else {
                this.d = i3;
                this.h = i4;
            }
        } else if (i == 1) {
            this.d = i3;
            if (zIsAllScreenDevice) {
                this.h = i4;
            } else {
                this.h = i4 - (this.m.isFullScreenOrImmersive() ? 0 : this.f);
            }
        } else {
            IWebAppRootView iWebAppRootView = this.b;
            if (iWebAppRootView != null) {
                this.d = iWebAppRootView.obtainMainView().getWidth();
                this.h = this.b.obtainMainView().getHeight();
            } else {
                this.d = i3;
                this.h = i4;
            }
        }
        int i7 = this.e;
        int i8 = this.h;
        if (i7 < i8) {
            this.e = i8;
        }
        this.l.onScreenChanged(this.d, i8);
    }

    public void a(int i) {
        if (Build.VERSION.SDK_INT >= 36 && AndroidResources.sAppTargetSdkVersion >= 36) {
            try {
                if (this.r == null) {
                    this.r = new RelativeLayout(this.a);
                }
                RelativeLayout relativeLayout = this.r;
                if (relativeLayout != null) {
                    relativeLayout.setBackgroundColor(i);
                    return;
                }
                return;
            } catch (Throwable unused) {
                return;
            }
        }
        this.a.getWindow().setNavigationBarColor(i);
    }

    @Override // io.dcloud.common.DHInterface.IAppInfo
    public void setRequestedOrientation(int i) {
        this.a.setRequestedOrientation(i);
    }
}

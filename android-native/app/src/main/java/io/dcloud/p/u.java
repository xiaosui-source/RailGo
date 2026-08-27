package io.dcloud.p;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.facebook.common.callercontext.ContextChain;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.gg.AolSplashUtil;
import io.dcloud.feature.gg.dcloud.ADHandler;
import io.dcloud.feature.gg.dcloud.GGSplashView;
import io.dcloud.feature.ui.navigator.QueryNotchTool;
import io.dcloud.p.j4;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.entry.SplashAOLConfig;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.util.Const;
import io.dcloud.sdk.core.util.MainHandlerUtil;
import io.dcloud.sdk.core.v3.inters.DCIntAOL;
import io.dcloud.sdk.core.v3.inters.DCIntAOLListener;
import io.dcloud.sdk.core.v3.inters.DCIntAOLLoadListener;
import io.dcloud.sdk.core.v3.sp.DCSplashAOLLoadListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class u extends j4 {
    private boolean E;
    private int F;
    private String G;
    private boolean H;
    i4 I;
    private String J;
    private String K;
    private boolean L;
    private boolean M;
    GGSplashView N;
    long O;
    private long P;
    DCIntAOL Q;
    private boolean R;
    private boolean S;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements AOLLoader.VAOLInteractionListener {
        b() {
        }

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
        public void onClick() {
        }

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
        public void onClose() {
            u.this.u();
        }

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
        public void onPaidGet(long j, String str, int i) {
        }

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
        public void onShow() {
        }

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
        public void onShowError(int i, String str) {
            u.this.u();
        }

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
        public void onSkip() {
            u.this.u();
        }

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
        public void onVideoPlayEnd() {
            u.this.u();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements DCIntAOLListener {
        c() {
        }

        @Override // io.dcloud.sdk.core.v3.inters.DCIntAOLListener
        public void onClick() {
        }

        @Override // io.dcloud.sdk.core.v3.inters.DCIntAOLListener
        public void onClose() {
            AolSplashUtil.setShowInterstitialAd(false);
        }

        @Override // io.dcloud.sdk.core.v3.inters.DCIntAOLListener
        public void onShow() {
            AolSplashUtil.setShowInterstitialAd(true);
        }

        @Override // io.dcloud.sdk.core.v3.inters.DCIntAOLListener
        public void onShowError(int i, String str) {
            AolSplashUtil.setShowInterstitialAd(false);
        }

        @Override // io.dcloud.sdk.core.v3.inters.DCIntAOLListener
        public void onSkip() {
        }

        @Override // io.dcloud.sdk.core.v3.inters.DCIntAOLListener
        public void onVideoPlayEnd() {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements DCIntAOLLoadListener {
        d() {
        }

        @Override // io.dcloud.sdk.core.v3.base.DCBaseAOLLoadListener
        public void onError(int i, String str, JSONArray jSONArray) {
            u.this.R = true;
        }

        @Override // io.dcloud.sdk.core.v3.inters.DCIntAOLLoadListener
        public void onInterstitialAOLLoad() {
            u.this.R = true;
            if (!u.this.S || u.this.P <= 0 || u.this.P <= SystemClock.elapsedRealtime()) {
                return;
            }
            u uVar = u.this;
            uVar.Q.show(uVar.a());
        }
    }

    public u(Activity activity) {
        super(activity, 1);
        this.E = false;
        this.F = 0;
        this.G = "";
        this.H = false;
        this.J = "";
        this.K = "";
        this.L = false;
        this.M = false;
        this.O = 0L;
        this.P = 0L;
        this.R = false;
        this.S = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void u() {
        GGSplashView gGSplashView = this.N;
        if (gGSplashView != null) {
            gGSplashView.onFinishShow();
        }
    }

    public void v() {
        this.R = false;
        if (TextUtils.isEmpty(this.G) || AolSplashUtil.isShowingInterstitialAd()) {
            return;
        }
        DCloudAOLSlot dCloudAOLSlotBuild = new DCloudAOLSlot.Builder().adpid(this.G).build();
        DCIntAOL dCIntAOL = new DCIntAOL(a());
        this.Q = dCIntAOL;
        dCIntAOL.setInterstitialAOLListener(new c());
        this.Q.load(dCloudAOLSlotBuild, new d());
    }

    public void w() {
        if (this.R) {
            DCIntAOL dCIntAOL = this.Q;
            if (dCIntAOL == null || !dCIntAOL.isValid()) {
                return;
            }
            this.Q.show(a());
            return;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int i = this.F;
        if (i <= 0) {
            i = 2500;
        }
        this.P = jElapsedRealtime + i;
        this.S = true;
    }

    @Override // io.dcloud.p.j4
    protected void b(final RelativeLayout relativeLayout, final FrameLayout.LayoutParams layoutParams) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.u$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.c(relativeLayout, layoutParams);
            }
        });
    }

    @Override // io.dcloud.p.x4, io.dcloud.p.a5, io.dcloud.p.w
    protected void c(List list) {
        super.c(list);
    }

    @Override // io.dcloud.p.j4, io.dcloud.p.h4
    protected int c() {
        if (this.L) {
            return 3;
        }
        return super.c();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends j4.b {
        final /* synthetic */ boolean d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(boolean z) {
            super();
            this.d = z;
        }

        @Override // io.dcloud.p.j4.b, io.dcloud.p.b0.b
        public void a(JSONArray jSONArray) {
            if (jSONArray == null || jSONArray.length() <= 0) {
                return;
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.optString("action", "").equals("redPackage")) {
                    u.this.b(jSONObjectOptJSONObject);
                }
            }
        }

        @Override // io.dcloud.p.j4.b, io.dcloud.p.b0.b, io.dcloud.p.b0.a
        public void a(JSONObject jSONObject) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (jSONObject == null || jSONObject.length() == 0) {
                u.this.a(-5001, AOLErrorUtil.getErrorMsg(-5001));
                return;
            }
            SplashAOLConfig splashAOLConfigT = u.this.t();
            i4 i4Var = u.this.I;
            if (i4Var != null) {
                try {
                    i4Var.a(jSONObject);
                    splashAOLConfigT = u.this.I.b();
                    if (!u.this.I.a()) {
                        super.a(-5000, "");
                        return;
                    }
                } catch (Exception unused) {
                }
                if (!m.d(u.this.a())) {
                    super.a(-5000, "");
                    return;
                }
            }
            if (splashAOLConfigT != null) {
                u.this.a(new DCloudAOLSlot.Builder().height(splashAOLConfigT.getHeight()).width(splashAOLConfigT.getWidth()).build());
            }
            if (this.d && io.dcloud.p.e.b().c().contains(Const.TYPE_HW)) {
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("cfgs");
                ArrayList arrayList = new ArrayList();
                if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                    int i = 0;
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                        if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.optString(ContextChain.TAG_PRODUCT).equals(Const.TYPE_HW)) {
                            arrayList.add(Integer.valueOf(i2));
                        }
                    }
                    if (arrayList.size() > 0) {
                        int size = arrayList.size();
                        while (i < size) {
                            Object obj = arrayList.get(i);
                            i++;
                            jSONArrayOptJSONArray.remove(((Integer) obj).intValue());
                        }
                        try {
                            jSONObject.put("cfgs", jSONArrayOptJSONArray);
                        } catch (JSONException unused2) {
                        }
                    }
                }
            }
            super.a(jSONObject);
        }

        @Override // io.dcloud.p.j4.b, io.dcloud.p.b0.b, io.dcloud.p.b0.a
        public void a(int i, String str) {
            super.a(i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(RelativeLayout relativeLayout, FrameLayout.LayoutParams layoutParams) {
        v2 v2Var = this.s;
        if (v2Var instanceof i4) {
            ((i4) v2Var).redBag(relativeLayout, layoutParams);
        }
    }

    @Override // io.dcloud.p.x4, io.dcloud.p.a5, io.dcloud.p.w
    protected void a(int i, String str, JSONArray jSONArray) {
        super.a(i, str, jSONArray);
    }

    public void a(SplashAOLConfig splashAOLConfig, DCSplashAOLLoadListener dCSplashAOLLoadListener, boolean z) {
        this.N = null;
        this.L = z;
        a(new a(z));
        a(dCSplashAOLLoadListener, z);
        this.O = SystemClock.elapsedRealtime();
        this.E = false;
        this.M = false;
        super.a(splashAOLConfig, this.I);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements i4 {
        final /* synthetic */ DCSplashAOLLoadListener a;
        final /* synthetic */ boolean b;

        e(DCSplashAOLLoadListener dCSplashAOLLoadListener, boolean z) {
            this.a = dCSplashAOLLoadListener;
            this.b = z;
        }

        @Override // io.dcloud.p.i4
        public void a(JSONObject jSONObject) {
            if (jSONObject != null) {
                try {
                    u.this.H = jSONObject.optInt("fs", 0) == 1;
                    u.this.J = jSONObject.optString("fr");
                    u.this.K = jSONObject.optString("frt");
                    if (jSONObject.has("cpadpid")) {
                        u.this.G = jSONObject.optString("cpadpid");
                        u.this.F = jSONObject.optInt("fwt");
                        u uVar = u.this;
                        uVar.F = uVar.F <= 0 ? 2500 : u.this.F;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // io.dcloud.p.i4
        public SplashAOLConfig b() {
            return new SplashAOLConfig.Builder().width(a((Context) u.this.c)).height(u.this.H ? a(u.this.c) : (a(u.this.c) / 20) * 17).build();
        }

        @Override // io.dcloud.p.v2
        public void onError(int i, String str, JSONArray jSONArray) {
            u.this.E = true;
            u.this.M = false;
            u.this.a(false);
            DCSplashAOLLoadListener dCSplashAOLLoadListener = this.a;
            if (dCSplashAOLLoadListener != null) {
                dCSplashAOLLoadListener.onError(i, str, jSONArray);
            }
        }

        @Override // io.dcloud.p.v2
        public void onLoaded() {
            u.this.E = true;
            u.this.M = true;
            u.this.a(false);
            DCSplashAOLLoadListener dCSplashAOLLoadListener = this.a;
            if (dCSplashAOLLoadListener != null) {
                dCSplashAOLLoadListener.onSplashAOLLoad();
            }
        }

        @Override // io.dcloud.p.i4
        public void redBag(View view, FrameLayout.LayoutParams layoutParams) {
            DCSplashAOLLoadListener dCSplashAOLLoadListener = this.a;
            if (dCSplashAOLLoadListener != null) {
                dCSplashAOLLoadListener.redBag(view, layoutParams);
            }
        }

        private int a(Activity activity) {
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getRealMetrics(displayMetrics);
            int i = displayMetrics.heightPixels;
            return (PdrUtil.hasNavBar(activity) && PdrUtil.isNavigationBarShowing(activity)) ? i - PdrUtil.getNavigationBarHeight(activity) : i;
        }

        private int a(Context context) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }

        @Override // io.dcloud.p.i4
        public boolean a() {
            if (!ADHandler.SplashAdIsEnable(u.this.a()).booleanValue()) {
                return false;
            }
            boolean z = this.b;
            if (z) {
                return z && "1".equals(u.this.J);
            }
            return true;
        }
    }

    @Override // io.dcloud.p.j4
    public void a(ViewGroup viewGroup) {
        a(new b());
        super.a(viewGroup);
    }

    public void a(View view) {
        GGSplashView gGSplashView;
        if ((this.t == null || !io.dcloud.p.e.b().c(this.t.getType()).equals(Const.PROVIDER_TYPE_GLOBAL)) && (gGSplashView = this.N) != null) {
            gGSplashView.onWillCloseSplash();
        }
    }

    public View a(Activity activity, String str, ICallBack iCallBack) {
        if (!this.E || !this.M) {
            return null;
        }
        if (this.N == null) {
            GGSplashView gGSplashView = new GGSplashView(activity);
            this.N = gGSplashView;
            gGSplashView.showAd(this);
        }
        if (this.H) {
            this.N.getBottomIcon().setVisibility(8);
        }
        this.N.setPullTime(this.O);
        this.N.setAppid(str);
        this.N.setCallBack(iCallBack);
        return this.N;
    }

    public void a(final Activity activity, String str, final ViewGroup viewGroup) {
        v();
        if (this.E && this.M) {
            a(activity, str, new ICallBack() { // from class: io.dcloud.p.u$$ExternalSyntheticLambda0
                @Override // io.dcloud.common.DHInterface.ICallBack
                public final Object onCallBack(int i, Object obj) {
                    return this.f$0.a(viewGroup, activity, i, obj);
                }
            });
            viewGroup.addView(this.N);
            if (BaseInfo.sGlobalFullScreen) {
                return;
            }
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags |= 1024;
            if (QueryNotchTool.hasNotchInScreen(activity) && Build.VERSION.SDK_INT >= 28) {
                attributes.layoutInDisplayCutoutMode = 1;
            }
            window.setAttributes(attributes);
            return;
        }
        w();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object a(ViewGroup viewGroup, Activity activity, int i, Object obj) {
        viewGroup.removeView(this.N);
        if (!BaseInfo.sGlobalFullScreen) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags &= -1025;
            if (QueryNotchTool.hasNotchInScreen(activity) && Build.VERSION.SDK_INT >= 28) {
                attributes.layoutInDisplayCutoutMode = 0;
            }
            window.setAttributes(attributes);
        }
        w();
        return null;
    }

    public boolean a(long j) throws NumberFormatException {
        long j2;
        try {
            j2 = Long.parseLong(this.K);
        } catch (Exception unused) {
            j2 = 0;
        }
        if (j2 <= 0) {
            j2 = 180000;
        }
        return j + j2 < SystemClock.elapsedRealtime() && ("1".equals(this.J) || !TextUtils.isEmpty(this.G));
    }

    private void a(DCSplashAOLLoadListener dCSplashAOLLoadListener, boolean z) {
        this.I = new e(dCSplashAOLLoadListener, z);
    }
}

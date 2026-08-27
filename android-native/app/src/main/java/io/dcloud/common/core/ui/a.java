package io.dcloud.common.core.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.dcloud.android.graphics.Region;
import com.dcloud.android.widget.AbsoluteLayout;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.FeatureMessageDispatcher;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebAppRootView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaContainerFrameItem;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.ui.AdaWebview;
import io.dcloud.common.adapter.ui.DHImageView;
import io.dcloud.common.adapter.ui.RecordView;
import io.dcloud.common.adapter.ui.WebParentView;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.AnimOptions;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.core.permission.PermissionControler;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TestUtil;
import io.dcloud.p.s4;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Stack;
import java.util.Vector;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
class a extends AdaContainerFrameItem implements ISysEventListener, IWebAppRootView {
    protected String A;
    private DHImageView B;
    ICallBack a;
    private Stack b;
    private ArrayList c;
    io.dcloud.common.core.ui.b d;
    io.dcloud.common.core.ui.b e;
    io.dcloud.common.core.ui.b f;
    io.dcloud.common.core.ui.b g;
    boolean h;
    String i;
    IApp j;
    public boolean k;
    private final int l;
    IActivityHandler m;
    k n;
    ICallBack o;
    long p;
    boolean q;
    boolean r;
    private boolean s;
    int t;
    private boolean u;
    private ArrayList v;
    private m w;
    private n x;
    protected byte y;
    protected String z;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.common.core.ui.a$a, reason: collision with other inner class name */
    static /* synthetic */ class C0030a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ISysEventListener.SysEventType.values().length];
            a = iArr;
            try {
                iArr[ISysEventListener.SysEventType.onPause.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ISysEventListener.SysEventType.onResume.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[ISysEventListener.SysEventType.onSimStateChanged.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[ISysEventListener.SysEventType.onDeviceNetChanged.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[ISysEventListener.SysEventType.onNewIntent.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[ISysEventListener.SysEventType.onConfigurationChanged.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[ISysEventListener.SysEventType.onKeyboardShow.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[ISysEventListener.SysEventType.onKeyboardHide.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[ISysEventListener.SysEventType.onWebAppBackground.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[ISysEventListener.SysEventType.onWebAppForeground.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[ISysEventListener.SysEventType.onWebAppTrimMemory.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[ISysEventListener.SysEventType.onSplashclosed.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements ICallBack {
        b() {
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            io.dcloud.common.core.ui.b bVarH;
            String strValueOf = String.valueOf(obj);
            if (TextUtils.isEmpty(strValueOf) || !strValueOf.equals("com.huawei.intent.action.CLICK_STATUSBAR") || a.this.obtainMainView().getParent() == null || (bVarH = a.this.h()) == null) {
                return null;
            }
            bVarH.b();
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements MessageHandler.IMessages {
        final /* synthetic */ io.dcloud.common.core.ui.b a;

        c(io.dcloud.common.core.ui.b bVar) {
            this.a = bVar;
        }

        @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
        public void execute(Object obj) {
            if (a.this.b == null || this.a == null) {
                return;
            }
            Logger.d("DHAppRootView.popFrameView frame" + this.a);
            a.this.b.remove(this.a);
            a.this.a(this.a);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements MessageHandler.IMessages {
        final /* synthetic */ ArrayList a;

        d(ArrayList arrayList) {
            this.a = arrayList;
        }

        @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
        public void execute(Object obj) {
            try {
                if (a.this.b != null) {
                    for (int size = a.this.b.size() - 1; size >= 0; size--) {
                        io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) a.this.b.get(size);
                        if (!this.a.contains(bVar)) {
                            io.dcloud.common.core.ui.l lVar = bVar.mWindowMgr;
                            if (lVar != null) {
                                lVar.processEvent(IMgr.MgrType.WindowMgr, 22, bVar);
                            }
                            bVar.h = true;
                        }
                    }
                }
            } catch (Exception e) {
                Logger.w("DHAppRootView onConfigurationChanged", e);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements Runnable {
        e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            IActivityHandler iActivityHandler = a.this.m;
            if (iActivityHandler != null) {
                iActivityHandler.showSplashWaiting();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class f implements MessageHandler.IMessages {
        final /* synthetic */ boolean a;
        final /* synthetic */ a b;
        final /* synthetic */ int c;

        f(boolean z, a aVar, int i) {
            this.a = z;
            this.b = aVar;
            this.c = i;
        }

        @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
        public void execute(Object obj) {
            Logger.d("approotview", "closeSplashScreen1;autoClose=" + this.a + ";mAppid" + a.this.i);
            a.this.a(this.b, this.c);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class g implements Runnable {
        final /* synthetic */ a a;
        final /* synthetic */ int b;

        g(a aVar, int i) {
            this.a = aVar;
            this.b = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.s = false;
            a.this.a(this.a, this.b);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class h implements ICallBack {
        h() {
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            IApp iApp;
            a.this.j.onSplashClosed();
            a aVar = a.this;
            if (!aVar.q || (iApp = aVar.j) == null) {
                aVar.r = true;
                return null;
            }
            iApp.callSysEventListener(ISysEventListener.SysEventType.onSplashclosed, this);
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class i implements ViewTreeObserver.OnGlobalLayoutListener {
        i() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            View viewObtainMainView = a.this.obtainMainView();
            a.this.onRootViewGlobalLayout(viewObtainMainView);
            if (viewObtainMainView == null || DeviceInfo.sDeviceSdkVer < 16 || viewObtainMainView.getViewTreeObserver() == null) {
                return;
            }
            viewObtainMainView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            viewObtainMainView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class j implements MessageHandler.IMessages {
        j() {
        }

        @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
        public void execute(Object obj) {
            ArrayList arrayList = a.this.v;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                ((ICallBack) obj2).onCallBack(-1, null);
            }
            a.this.v.clear();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class l extends io.dcloud.common.core.ui.k {
        Paint t;
        int u;
        int v;
        int w;
        int x;
        String y;
        c z;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.common.core.ui.a$l$a, reason: collision with other inner class name */
        class C0031a implements ICallBack {
            C0031a() {
            }

            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                l.this.l.b();
                a.this.a = null;
                return null;
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class b implements Runnable {
            b() {
            }

            @Override // java.lang.Runnable
            public void run() throws NumberFormatException {
                if (a.this.c != null) {
                    ArrayList arrayList = a.this.c;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) obj;
                        if (!bVar.isChildOfFrameView) {
                            bVar.resize();
                        }
                    }
                }
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class c implements Runnable {
            int a = 0;
            boolean b = false;

            c() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (!this.b) {
                    this.a = 0;
                    return;
                }
                this.a++;
                l.this.invalidate();
                this.a %= 4;
                l.this.postDelayed(this, 500L);
            }
        }

        public l(Context context, a aVar) {
            super(context, aVar);
            this.t = new Paint();
            this.y = "";
            this.z = new c();
            this.t.setColor(-13421773);
            this.t.setTextSize((int) (DeviceInfo.DEFAULT_FONT_SIZE * DeviceInfo.sDensity * 1.2d));
            setTag("AppRootView");
            String string = context.getString(R.string.dcloud_common_in_the_buffer);
            this.y = string;
            this.u = (int) this.t.measureText(string);
            this.v = (int) this.t.measureText("...");
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchConfigurationChanged(Configuration configuration) {
            super.dispatchConfigurationChanged(configuration);
            if (!BaseInfo.sDoingAnimation) {
                this.l.b();
            } else {
                a.this.a = new C0031a();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (a.this.j.getMaskLayerCount() <= 0) {
                this.z.b = false;
                return;
            }
            c cVar = this.z;
            if (!cVar.b) {
                cVar.b = true;
                cVar.run();
            }
            canvas.drawColor(-2013265920);
            canvas.drawText(this.y, this.w, this.x, this.t);
            int i = this.z.a;
            if (i == 1) {
                canvas.drawText(Operators.DOT_STR, this.w + this.u, this.x, this.t);
            } else if (i == 2) {
                canvas.drawText(PdrUtil.FILE_PATH_ENTRY_BACK, this.w + this.u, this.x, this.t);
            } else if (i == 3) {
                canvas.drawText("...", this.w + this.u, this.x, this.t);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (a.this.j.getMaskLayerCount() > 0) {
                return true;
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            ((AdaFrameItem) a.this).mViewOptions.onScreenChanged();
            PlatformUtil.RESET_H_W();
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            this.w = ((a.this.j.getInt(0) - this.u) - this.v) / 2;
            this.x = (int) (a.this.j.getInt(2) * 0.8d);
        }

        @Override // android.view.View
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            if (AdaWebview.sCustomeizedInputConnection != null && i2 != 0 && i4 != 0) {
                int i5 = getResources().getDisplayMetrics().heightPixels;
                int i6 = getResources().getDisplayMetrics().heightPixels / 4;
                if (Math.abs(i2 - i5) > i6 || Math.abs(i2 - i4) > i6) {
                    if (i2 <= i4 || Math.abs(i2 - i4) <= i6) {
                        AdaWebview.sCustomeizedInputConnection.showRecordView(i2, true);
                    } else {
                        AdaWebview.sCustomeizedInputConnection.closeRecordView();
                    }
                }
            }
            a.this.j.updateScreenInfo(6);
            a.this.j.callSysEventListener(ISysEventListener.SysEventType.onSizeChanged, new int[]{i, i2, i3, i4});
            ((AdaFrameItem) a.this).mViewOptions.onScreenChanged(i, i2);
            post(new b());
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class m implements Comparator {
        m() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(io.dcloud.common.core.ui.b bVar, io.dcloud.common.core.ui.b bVar2) {
            if (bVar.getFrameType() == 3) {
                return 1;
            }
            if (bVar2.getFrameType() == 3) {
                return -1;
            }
            int i = bVar.mZIndex - bVar2.mZIndex;
            return i == 0 ? bVar.lastShowTime > bVar2.lastShowTime ? 1 : -1 : i;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class n implements Comparator {
        n() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(AdaFrameItem adaFrameItem, AdaFrameItem adaFrameItem2) {
            boolean z = adaFrameItem instanceof IFrameView;
            if (z && ((IFrameView) adaFrameItem).getFrameType() == 3) {
                return 1;
            }
            if (z && ((IFrameView) adaFrameItem).getFrameType() == 3) {
                return -1;
            }
            int i = adaFrameItem.mZIndex - adaFrameItem2.mZIndex;
            return i == 0 ? adaFrameItem.lastShowTime > adaFrameItem2.lastShowTime ? 1 : -1 : i;
        }
    }

    public a(Context context, IApp iApp, io.dcloud.common.core.ui.b bVar) {
        super(context);
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = true;
        this.i = null;
        this.j = null;
        this.k = false;
        this.l = 2;
        this.n = new k();
        this.o = new b();
        this.p = System.currentTimeMillis();
        this.q = false;
        this.r = false;
        this.s = false;
        this.t = 0;
        this.u = true;
        this.v = new ArrayList();
        this.w = new m();
        this.x = new n();
        this.B = null;
        this.q = BaseInfo.sRuntimeMode != null;
        this.j = iApp;
        this.m = DCloudAdapterUtil.getIActivityHandler(iApp.getActivity());
        this.i = iApp.obtainAppId();
        setMainView(new l(context, this));
        this.b = new Stack();
        this.c = new ArrayList();
        iApp.setWebAppRootView(this);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onPause);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onResume);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onDeviceNetChanged);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onNewIntent);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onConfigurationChanged);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onSimStateChanged);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onKeyboardShow);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onWebAppBackground);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onWebAppForeground);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onKeyboardHide);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onWebAppTrimMemory);
        iApp.registerSysEventListener(this, ISysEventListener.SysEventType.onSplashclosed);
        if (PermissionControler.checkPermission(this.i, IFeature.F_DEVICE.toLowerCase(Locale.ENGLISH))) {
            String bundleData = SP.getBundleData(context, BaseInfo.PDR, "last_notify_net_type");
            String netWorkType = DeviceInfo.getNetWorkType(context);
            if (!PdrUtil.isEquals(bundleData, netWorkType)) {
                Logger.d("NetCheckReceiver", "netchange last_net_type:" + bundleData + ";cur_net_type:" + netWorkType);
                SP.setBundleData(context, BaseInfo.PDR, "last_notify_net_type", netWorkType);
            }
        }
        this.m.addClickStatusbarCallBack(this.o);
    }

    private void a(View view) {
    }

    @Override // io.dcloud.common.DHInterface.IWebAppRootView
    public boolean didCloseSplash() {
        return this.q;
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public synchronized void dispose() {
        a();
        this.b = null;
        this.c = null;
        DHImageView dHImageView = this.B;
        if (dHImageView != null) {
            dHImageView.setImageBitmap(null);
            this.B = null;
        }
        super.dispose();
        this.m.removeClickStatusbarCallBack(this.o);
        RecordView recordView = AdaWebview.mRecordView;
        if (recordView != null) {
            recordView.dispose();
        }
        AdaWebview.mRecordView = null;
    }

    io.dcloud.common.core.ui.b f() {
        return a(4);
    }

    @Override // io.dcloud.common.DHInterface.IWebAppRootView
    public IFrameView findFrameViewB(IFrameView iFrameView) {
        if (!this.c.contains(iFrameView)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        a(iFrameView, arrayList);
        a(arrayList);
        if (arrayList.size() <= 1 && arrayList.size() == 1) {
            return (IFrameView) arrayList.get(0);
        }
        return null;
    }

    void g(io.dcloud.common.core.ui.b bVar) {
        MessageHandler.sendMessage(new c(bVar), null);
    }

    @Override // io.dcloud.common.DHInterface.IWebAppRootView
    public void goHome(IFrameView iFrameView) {
        if (iFrameView instanceof io.dcloud.common.core.ui.b) {
            io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) iFrameView;
            bVar.getAnimOptions().mOption = (byte) 1;
            bVar.getAnimOptions().mAnimType = "none";
            bVar.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 2, iFrameView);
        }
    }

    public io.dcloud.common.core.ui.b h() {
        Stack stack = this.b;
        io.dcloud.common.core.ui.b bVar = null;
        if (stack != null && !stack.isEmpty()) {
            for (int size = this.b.size() - 1; size >= 0; size--) {
                bVar = (io.dcloud.common.core.ui.b) this.b.get(size);
                if (bVar.obtainMainView().getVisibility() == 0 && !bVar.isChildOfFrameView) {
                    return bVar;
                }
            }
        }
        return bVar;
    }

    void i() {
        Stack stack = this.b;
        if (stack == null || stack.isEmpty()) {
            return;
        }
        Boolean bool = Boolean.FALSE;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) this.b.get(size);
            View viewObtainMainView = bVar.obtainMainView();
            if (!bVar.isChildOfFrameView) {
                if (bool.booleanValue() || viewObtainMainView.getVisibility() != 0) {
                    viewObtainMainView.setImportantForAccessibility(4);
                } else {
                    viewObtainMainView.setImportantForAccessibility(0);
                    bool = Boolean.TRUE;
                }
            }
        }
    }

    public void j() {
        if (obtainMainView() instanceof io.dcloud.common.core.ui.k) {
            ((io.dcloud.common.core.ui.k) obtainMainView()).c();
        }
    }

    void k() {
        Collections.sort(this.c, this.w);
    }

    void l() {
        Collections.sort(this.b, this.w);
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((io.dcloud.common.core.ui.b) this.b.get(i2)).obtainMainView().bringToFront();
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebAppRootView
    public void onAppActive(IApp iApp) {
        b(iApp);
        BaseInfo.sCurrentAppOriginalAppid = iApp.obtainOriginalAppId();
        iApp.getActivity();
        if (this.m != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            if (iApp.obtainStatusBarMgr().isTemporaryFullScreen && !iApp.obtainStatusBarMgr().isImmersive && !iApp.isFullScreen() && !this.q) {
                layoutParams.topMargin = DeviceInfo.sStatusBarHeight;
            }
            this.m.setViewAsContentView(obtainMainView(), layoutParams);
        }
        Logger.d(Logger.MAIN_TAG, iApp.obtainAppId() + " onAppActive setContentView");
        a(obtainMainView());
        FeatureMessageDispatcher.dispatchMessage("app_open", 1);
    }

    @Override // io.dcloud.common.DHInterface.IWebAppRootView
    public void onAppStart(IApp iApp) {
        this.q = false;
        this.r = false;
        if (iApp != null) {
            a(iApp);
        }
        obtainMainView().getViewTreeObserver().addOnGlobalLayoutListener(new i());
        obtainMainView().setBackgroundColor(-1);
        onAppActive(iApp);
    }

    @Override // io.dcloud.common.DHInterface.IWebAppRootView
    public void onAppStop(IApp iApp) {
        onAppUnActive(iApp);
    }

    @Override // io.dcloud.common.DHInterface.IWebAppRootView
    public void onAppUnActive(IApp iApp) {
    }

    @Override // io.dcloud.common.DHInterface.IWebAppRootView
    public void onRootViewGlobalLayout(View view) {
        if (isDisposed()) {
            return;
        }
        if (AdaWebview.ScreemOrientationChangedNeedLayout) {
            AdaWebview.ScreemOrientationChangedNeedLayout = false;
            this.j.updateScreenInfo(3);
        }
        int width = view.getWidth();
        int height = view.getHeight();
        if (Math.abs(width - this.j.getInt(0)) > 100) {
            return;
        }
        int i2 = height - this.j.getInt(1);
        if (!this.j.isVerticalScreen()) {
            i2 = width - this.j.getInt(0);
        }
        if (i2 != 0) {
            this.j.updateScreenInfo(3);
        }
        if (view.getHeight() != this.t && view.getHeight() == this.j.getInt(1)) {
            PlatformUtil.RESET_H_W();
            if (!this.u) {
                BaseInfo.sFullScreenChanged = true;
            }
            this.u = false;
        }
        this.t = view.getHeight();
    }

    void reload(boolean z) {
        for (int size = this.c.size() - 1; size >= 0; size--) {
            IWebview iWebviewObtainWebView = ((io.dcloud.common.core.ui.b) this.c.get(size)).obtainWebView();
            if (iWebviewObtainWebView != null) {
                iWebviewObtainWebView.reload();
            }
            if (!z) {
                return;
            }
        }
    }

    public void b() {
        Logger.d(Logger.ANIMATION_TAG, "AppRootView dispatchConfigurationChanged(横竖屏切换、全屏非全屏切换、虚拟返回键栏隐藏显示) 引发调整栈窗口");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        a(arrayList2, arrayList);
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) obj;
            boolean zContains = this.b.contains(bVar);
            io.dcloud.common.core.ui.l lVar = bVar.mWindowMgr;
            if (lVar != null) {
                lVar.processEvent(IMgr.MgrType.WindowMgr, 8, bVar);
            }
            bVar.i = !zContains;
        }
        MessageHandler.sendMessage(new d(arrayList2), null);
    }

    int c(io.dcloud.common.core.ui.b bVar) {
        return this.b.indexOf(bVar);
    }

    Stack d() {
        return this.b;
    }

    int e(io.dcloud.common.core.ui.b bVar) {
        int i2;
        int i3;
        int i4;
        int iIndexOf = this.c.indexOf(bVar);
        int size = this.b.size() - 1;
        while (true) {
            if (size < 0) {
                i3 = 0;
                break;
            }
            io.dcloud.common.core.ui.b bVar2 = (io.dcloud.common.core.ui.b) this.b.get(size);
            int iIndexOf2 = this.c.indexOf(bVar2);
            if (iIndexOf2 >= 0 && iIndexOf > iIndexOf2 && bVar2.getFrameType() != 3 && !bVar2.isTabItem()) {
                i3 = size + 1;
                break;
            }
            size--;
        }
        if (i3 != 0) {
            ViewGroup viewGroupObtainMainViewGroup = obtainMainViewGroup();
            int childCount = viewGroupObtainMainViewGroup.getChildCount();
            i4 = i3;
            int i5 = 0;
            for (i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroupObtainMainViewGroup.getChildAt(i2);
                if ((childAt instanceof AbsoluteLayout) || (childAt instanceof s4)) {
                    i5++;
                } else {
                    i4++;
                }
                if (i5 >= i3) {
                    break;
                }
            }
        } else {
            i4 = i3;
        }
        a(bVar, i3, i4);
        if (BaseInfo.isUniAppAppid(bVar.obtainApp())) {
            if (bVar.obtainWebView() != null) {
                bVar.obtainWebView().setIWebViewFocusable(true);
            }
            bVar.changeWebParentViewRect();
        }
        return i3;
    }

    public void f(io.dcloud.common.core.ui.b bVar) {
        AnimOptions animOptions = bVar.getAnimOptions();
        animOptions.mOption = this.y;
        animOptions.mAnimType = this.z;
        animOptions.mAnimType_close = this.A;
    }

    public void g() {
        if (!AndroidResources.sIMEAlive || this.v.size() <= 0) {
            return;
        }
        MessageHandler.sendMessage(new j(), BaseInfo.isUniAppAppid(this.j) ? 50 : 500, null);
    }

    ArrayList c() {
        return this.c;
    }

    boolean d(io.dcloud.common.core.ui.b bVar) {
        ArrayList arrayList = this.c;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            io.dcloud.common.core.ui.b bVar2 = (io.dcloud.common.core.ui.b) obj;
            if (bVar != bVar2 && !bVar2.isChildOfFrameView && bVar2.obtainMainView().getVisibility() == 0) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:59:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x03b4  */
    @Override // io.dcloud.common.DHInterface.ISysEventListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onExecute(io.dcloud.common.DHInterface.ISysEventListener.SysEventType r13, java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 1010
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.core.ui.a.onExecute(io.dcloud.common.DHInterface.ISysEventListener$SysEventType, java.lang.Object):boolean");
    }

    private void a(io.dcloud.common.core.ui.b bVar, int i2, int i3) {
        Logger.d("DHAppRootView.pushFrameView" + bVar);
        this.b.insertElementAt(bVar, i2);
        addFrameItem(bVar, i3);
    }

    void h(io.dcloud.common.core.ui.b bVar) {
        ArrayList<AdaFrameItem> arrayList = bVar.getParentFrameItem().mChildArrayList;
        if (arrayList.size() > 1) {
            Collections.sort(arrayList, this.x);
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                AdaFrameItem adaFrameItem = arrayList.get(i2);
                i2++;
                AdaFrameItem adaFrameItem2 = adaFrameItem;
                if (adaFrameItem2.obtainMainView() != null) {
                    if (!(adaFrameItem2.obtainMainView() instanceof WebParentView)) {
                        adaFrameItem2.obtainMainView().bringToFront();
                    }
                } else {
                    arrayList2.add(adaFrameItem2);
                }
            }
            if (arrayList2.size() > 0) {
                arrayList.removeAll(arrayList2);
            }
        }
        i(bVar);
    }

    void reload(String str) {
        String[] strArrSplit = str.split("\\|");
        if (strArrSplit.length > 0 && BaseInfo.isUniNViewBackgroud() && this.c.size() > 0) {
            io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) this.c.get(0);
            bVar.mWindowMgr.processEvent(IMgr.MgrType.FeatureMgr, 10, new Object[]{bVar.obtainApp(), "weex,io.dcloud.feature.weex.WeexFeature", "updateServiceReload", null});
        }
        for (int size = this.c.size() - 1; size >= 0; size--) {
            io.dcloud.common.core.ui.b bVar2 = (io.dcloud.common.core.ui.b) this.c.get(size);
            IWebview iWebviewObtainWebView = bVar2.obtainWebView();
            boolean z = bVar2.obtainFrameOptions().mUniNViewJson != null;
            if (iWebviewObtainWebView != null) {
                for (String str2 : strArrSplit) {
                    if (z) {
                        if (str2.endsWith(".js")) {
                            bVar2.mWindowMgr.processEvent(IMgr.MgrType.FeatureMgr, 10, new Object[]{bVar2.obtainApp(), "weex,io.dcloud.feature.weex.WeexFeature", "updateReload", new Object[]{str2}});
                        }
                    } else if (iWebviewObtainWebView.obtainUrl().startsWith(str2)) {
                        iWebviewObtainWebView.reload();
                        break;
                    } else if (!(iWebviewObtainWebView instanceof TabBarWebview) || !((TabBarWebview) iWebviewObtainWebView).checkUrlToReload(str2)) {
                    }
                }
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class k {
        int a = 0;
        Vector b = new Vector();
        private boolean c = false;

        k() {
        }

        void a(io.dcloud.common.core.ui.b bVar) {
            this.b.add(bVar);
            int i = this.a + 1;
            this.a = i;
            if (i > 1) {
                this.c = true;
            } else {
                this.a = 1;
                this.c = false;
            }
        }

        void b(io.dcloud.common.core.ui.b bVar) {
            this.b.remove(bVar);
            this.a--;
        }

        int a() {
            return this.a;
        }
    }

    private void a(ArrayList arrayList, ArrayList arrayList2) {
        Region region;
        Region region2 = new Region(2);
        for (int size = this.c.size() - 1; size >= 0; size--) {
            io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) this.c.get(size);
            if (bVar.obtainMainView().getVisibility() == 0) {
                ViewOptions viewOptionsObtainFrameOptions = bVar.obtainFrameOptions();
                if (!bVar.isChildOfFrameView) {
                    if (bVar.a) {
                        a(region2, viewOptionsObtainFrameOptions.left, viewOptionsObtainFrameOptions.top, viewOptionsObtainFrameOptions.width, viewOptionsObtainFrameOptions.height);
                        region = region2;
                        b(arrayList, bVar);
                    } else {
                        region = region2;
                        bVar.h();
                        if (a(region)) {
                            if (a(arrayList2, bVar)) {
                                return;
                            }
                        } else {
                            if (viewOptionsObtainFrameOptions.hasTransparentValue()) {
                                region2 = region;
                            } else {
                                region2 = region;
                                if (a(region2, viewOptionsObtainFrameOptions.left, viewOptionsObtainFrameOptions.top, viewOptionsObtainFrameOptions.width, viewOptionsObtainFrameOptions.height)) {
                                    if (a(arrayList2, bVar)) {
                                        return;
                                    }
                                }
                            }
                            b(arrayList, bVar);
                        }
                    }
                    region2 = region;
                }
            } else if (a(arrayList2, bVar)) {
                return;
            }
        }
    }

    void b(io.dcloud.common.core.ui.b bVar) {
        byte b2 = bVar.getAnimOptions().mOption;
        if ((!bVar.d && !bVar.inStack && bVar.obtainFrameOptions().hasTransparentValue()) || bVar.obtainMainView().getVisibility() != 0) {
            if (b2 == 3 || b2 == 1) {
                ArrayList arrayList = new ArrayList();
                a(arrayList, bVar);
                bVar.c = arrayList;
                if (b2 != 1) {
                    return;
                }
            } else {
                if (b2 == 2) {
                    return;
                }
                if (b2 == 4 || b2 == 0) {
                    ArrayList arrayList2 = new ArrayList();
                    b(arrayList2, bVar);
                    bVar.b = arrayList2;
                    return;
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        Region region = new Region(2);
        for (int size = this.c.size() - 1; size >= 0; size--) {
            io.dcloud.common.core.ui.b bVar2 = (io.dcloud.common.core.ui.b) this.c.get(size);
            if (bVar2.obtainMainView().getVisibility() == 0) {
                if (bVar2.isChildOfFrameView) {
                    continue;
                } else {
                    bVar2.h();
                    ViewOptions viewOptionsObtainFrameOptions = bVar2.obtainFrameOptions();
                    if (b2 == 4 || b2 == 0) {
                        if ((a(region) || region.getFillScreenCounter() > 2) && bVar != bVar2) {
                            if (a(arrayList3, bVar2)) {
                                break;
                            }
                        } else if (!viewOptionsObtainFrameOptions.hasTransparentValue() && a(region, viewOptionsObtainFrameOptions.left, viewOptionsObtainFrameOptions.top, viewOptionsObtainFrameOptions.width, viewOptionsObtainFrameOptions.height)) {
                            if (a(arrayList3, bVar2)) {
                                break;
                            }
                        } else {
                            b(arrayList4, bVar2);
                        }
                    } else if (b2 == 2) {
                        ViewOptions viewOptionsObtainFrameOptions_Animate = bVar2.obtainFrameOptions_Animate();
                        if (bVar2 == bVar && viewOptionsObtainFrameOptions_Animate != null) {
                            viewOptionsObtainFrameOptions = viewOptionsObtainFrameOptions_Animate;
                        }
                        if (a(region)) {
                            if (a(arrayList3, bVar2)) {
                                break;
                            }
                        } else if (!viewOptionsObtainFrameOptions.hasTransparentValue() && a(region, viewOptionsObtainFrameOptions.left, viewOptionsObtainFrameOptions.top, viewOptionsObtainFrameOptions.width, viewOptionsObtainFrameOptions.height)) {
                            if (a(arrayList3, bVar2)) {
                                break;
                            }
                        } else {
                            b(arrayList4, bVar2);
                        }
                    } else if (b2 == 3 || b2 == 1) {
                        if (bVar2 == bVar) {
                            a(arrayList3, bVar2);
                        } else if (a(region)) {
                            a(arrayList3, bVar2);
                        } else if (!viewOptionsObtainFrameOptions.hasTransparentValue() && a(region, viewOptionsObtainFrameOptions.left, viewOptionsObtainFrameOptions.top, viewOptionsObtainFrameOptions.width, viewOptionsObtainFrameOptions.height)) {
                            if (a(arrayList3, bVar2)) {
                                break;
                            }
                        } else {
                            b(arrayList4, bVar2);
                        }
                    }
                }
            } else {
                if (a(arrayList3, bVar2)) {
                    break;
                }
            }
        }
        bVar.c = arrayList3;
        bVar.b = arrayList4;
    }

    void i(io.dcloud.common.core.ui.b bVar) {
        if (bVar.getParentFrameItem() == null) {
            return;
        }
        bVar.getParentFrameItem().sortNativeViewBringToFront();
    }

    io.dcloud.common.core.ui.b e() {
        return a(2);
    }

    boolean a(Region region) {
        boolean zQuickContains = region.quickContains(0, 0, this.j.getInt(0), this.j.getInt(1));
        if (region.fillWholeScreen() || !zQuickContains) {
            return zQuickContains;
        }
        region.setEmpty();
        region.count();
        return false;
    }

    boolean a(ArrayList arrayList, io.dcloud.common.core.ui.b bVar) {
        arrayList.add(bVar);
        return false;
    }

    boolean a(Region region, int i2, int i3, int i4, int i5) {
        int i6 = i2 + i4;
        int i7 = i3 + i5;
        boolean zQuickContains = region.quickContains(i2, i3, i6, i7);
        if (!zQuickContains) {
            region.op(i2, i3, i6, i7, Region.Op.UNION);
        }
        return zQuickContains;
    }

    void a(io.dcloud.common.core.ui.b bVar) {
        Logger.d("DHAppRootView.closeFrameView pFrameView=" + bVar);
        bVar.onDestroy();
        removeFrameItem(bVar);
        System.gc();
    }

    public void a() {
        Logger.d(this.i + " clearFrameView");
        ArrayList arrayList = this.c;
        if (arrayList != null) {
            int size = arrayList.size();
            io.dcloud.common.core.ui.b[] bVarArr = new io.dcloud.common.core.ui.b[size];
            this.c.toArray(bVarArr);
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    bVarArr[i2].onDestroy();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.c.clear();
        }
        clearView();
        Stack stack = this.b;
        if (stack != null) {
            stack.clear();
        }
    }

    void a(IApp iApp) {
        if (Boolean.parseBoolean(iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_WAITING))) {
            obtainMainView().postDelayed(new e(), 100L);
        }
    }

    io.dcloud.common.core.ui.b a(int i2) {
        ArrayList arrayList = this.c;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) obj;
            if (bVar.getFrameType() == i2) {
                return bVar;
            }
        }
        return null;
    }

    void a(a aVar, io.dcloud.common.core.ui.b bVar, int i2, boolean z, int i3) {
        if (bVar != null) {
            Logger.d("approotview", "closeSplashScreen0 delay=" + i2 + ";autoClose=" + z + ";mAppid" + this.i);
            if (bVar.obtainMainView() != null) {
                MessageHandler.sendMessage(new f(z, aVar, i3), Math.max(i2, 150), bVar);
                return;
            }
            Logger.d("approotview", "closeSplashScreen2;autoClose;mAppid" + this.i);
            a(aVar, i3);
        }
    }

    void a(a aVar, int i2) {
        Log.e("Html5Plus-SplashClosed", System.currentTimeMillis() + "");
        Log.e(Logger.MAIN_TAG, "closeSplashScreen0 appid=" + this.i + ";" + (i2 > 10) + ";closeSplashDid=" + this.q);
        if (aVar != null && !this.q) {
            if (this.s) {
                return;
            }
            boolean z = this.m != null ? !r0.hasAdService() : true;
            IActivityHandler iActivityHandler = this.m;
            if (iActivityHandler != null) {
                if (!iActivityHandler.hasAdService()) {
                    Object objInvokeMethod = PlatformUtil.invokeMethod("io.dcloud.feature.gg.dcloud.ADHandler", "SplashAdIsEnable", null, new Class[]{Context.class}, new Object[]{aVar.getContext()});
                    if ((objInvokeMethod instanceof Boolean) && ((Boolean) objInvokeMethod).booleanValue()) {
                        long jAbs = Math.abs(System.currentTimeMillis() - BaseInfo.splashCreateTime);
                        if (jAbs < 2500) {
                            this.s = true;
                            MessageHandler.postDelayed(new g(aVar, i2), 2500 - jAbs);
                            return;
                        }
                    }
                }
                if (z) {
                    this.m.closeAppStreamSplash(this.i);
                }
                BaseInfo.setLoadingLaunchePage(false, "closeSplashScreen0");
                try {
                    ((FrameLayout.LayoutParams) obtainMainView().getLayoutParams()).topMargin = 0;
                } catch (Exception e2) {
                    Logger.e("Exception", "e.getMessage()==" + e2.getMessage());
                }
                if (z) {
                    this.j.onSplashClosed();
                } else {
                    this.m.setSplashCloseListener(this.i, new h());
                }
            } else {
                IApp iApp = this.j;
                if (iApp != null) {
                    iApp.diyStatusBarState();
                }
            }
            BaseInfo.run5appEndTime = TestUtil.getUseTime(AbsoluteConst.RUN_5AP_TIME_KEY, "");
            TestUtil.delete(AbsoluteConst.RUN_5AP_TIME_KEY);
            this.j.setConfigProperty("commit", String.valueOf(this.p));
            IOnCreateSplashView onCreateSplashView = this.j.getOnCreateSplashView();
            if (onCreateSplashView != null) {
                onCreateSplashView.onCloseSplash();
            }
            DCKeyboardManager.getInstance().dhAppRootIsReady(this);
            BaseInfo.splashCloseTime = System.currentTimeMillis();
            IApp iApp2 = this.j;
            if ((iApp2 != null && z) || this.r) {
                iApp2.callSysEventListener(ISysEventListener.SysEventType.onSplashclosed, this);
            }
        }
        this.q = true;
    }

    void b(ArrayList arrayList, io.dcloud.common.core.ui.b bVar) {
        arrayList.add(bVar);
    }

    private void b(IApp iApp) {
        iApp.setFullScreen(PdrUtil.parseBoolean(iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_FULLSCREEN), false, false));
    }

    public Object a(View view, ICallBack iCallBack) {
        if (!AndroidResources.sIMEAlive) {
            return iCallBack.onCallBack(-1, null);
        }
        DeviceInfo.hideIME(obtainMainView());
        this.v.add(iCallBack);
        return null;
    }

    private void a(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int i2 = 0;
        int i3 = this.j.getInt(0);
        int size = arrayList.size();
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) obj;
            ViewOptions viewOptionsObtainFrameOptions = bVar.obtainFrameOptions();
            int i4 = viewOptionsObtainFrameOptions.width;
            if (i4 == -1) {
                i4 = i3;
            }
            int i5 = viewOptionsObtainFrameOptions.left;
            if (i5 + i4 <= 0 || i5 >= i3 || viewOptionsObtainFrameOptions.right + i4 <= 0) {
                arrayList2.add(bVar);
            }
        }
        arrayList.removeAll(arrayList2);
    }

    public void a(IFrameView iFrameView, ArrayList arrayList) {
        if (this.c.contains(iFrameView)) {
            int iIndexOf = this.c.indexOf(iFrameView);
            if (this.c != null) {
                com.dcloud.android.graphics.Region region = new com.dcloud.android.graphics.Region();
                for (int i2 = iIndexOf - 1; i2 >= 0; i2--) {
                    io.dcloud.common.core.ui.b bVar = (io.dcloud.common.core.ui.b) this.c.get(i2);
                    if (!bVar.isChildOfFrameView && bVar.obtainMainView().getVisibility() == 0) {
                        arrayList.add(bVar);
                        ViewOptions viewOptionsObtainFrameOptions = bVar.obtainFrameOptions();
                        a(region, viewOptionsObtainFrameOptions.left, viewOptionsObtainFrameOptions.top, viewOptionsObtainFrameOptions.width, viewOptionsObtainFrameOptions.height);
                    }
                    if (a(region)) {
                        return;
                    }
                }
            }
        }
    }

    public void a(io.dcloud.common.core.ui.b bVar, io.dcloud.common.core.ui.b bVar2) {
        AnimOptions animOptions = bVar.getAnimOptions();
        this.y = animOptions.mOption;
        animOptions.mOption = bVar2.getAnimOptions().mOption;
        this.z = animOptions.mAnimType;
        animOptions.mAnimType = bVar2.getAnimOptions().mAnimType;
        this.A = animOptions.mAnimType_close;
        animOptions.mAnimType_close = bVar2.getAnimOptions().mAnimType_close;
    }

    public DHImageView a(io.dcloud.common.core.ui.b bVar, int i2, boolean z) {
        boolean z2;
        DHImageView dHImageView;
        long jCurrentTimeMillis = System.currentTimeMillis();
        l lVar = (l) obtainMainView();
        if (bVar.mNativeView != null) {
            if (this.B == null) {
                this.B = lVar.getLeftImageView();
            }
            if (bVar.mNativeView.isAnimate()) {
                this.B.removeNativeView();
                bVar.mNativeView = null;
            } else {
                if (this.B.getParent() != lVar) {
                    if (this.B.getParent() != null) {
                        ((ViewGroup) this.B.getParent()).removeView(this.B);
                    }
                    lVar.addView(this.B);
                }
                this.B.addNativeView(bVar, bVar.mNativeView);
                this.B.setImageBitmap(null);
                this.B.bringToFront();
                this.B.setVisibility(0);
                return this.B;
            }
        }
        if (a((ViewGroup) lVar)) {
            DHImageView dHImageView2 = this.B;
            if (dHImageView2 != null) {
                dHImageView2.clear();
                this.B = null;
            }
            return null;
        }
        Bitmap bitmapCaptureView = bVar.mSnapshot;
        if (bitmapCaptureView != null) {
            z2 = false;
        } else {
            if (1 == i2 && (dHImageView = this.B) != null && dHImageView.getBitmap() != null && this.B.getTag() != null && bVar.hashCode() == ((Integer) this.B.getTag()).intValue()) {
                if (this.B.getParent() != lVar) {
                    if (this.B.getParent() != null) {
                        ((ViewGroup) this.B.getParent()).removeView(this.B);
                    }
                    lVar.addView(this.B);
                }
                this.B.removeNativeView();
                this.B.bringToFront();
                this.B.setVisibility(0);
                return this.B;
            }
            bitmapCaptureView = PlatformUtil.captureView(bVar.obtainMainView());
            z2 = true;
        }
        if (bitmapCaptureView != null && !PlatformUtil.isWhiteBitmap(bitmapCaptureView)) {
            if (this.B == null) {
                this.B = lVar.getLeftImageView();
            }
            if (this.B.getParent() != lVar) {
                if (this.B.getParent() != null) {
                    ((ViewGroup) this.B.getParent()).removeView(this.B);
                }
                lVar.addView(this.B);
            }
            this.B.bringToFront();
            this.B.setImageBitmap(bitmapCaptureView);
            this.B.removeNativeView();
            this.B.setVisibility(0);
        } else {
            DHImageView dHImageView3 = this.B;
            if (dHImageView3 != null) {
                dHImageView3.clear();
                this.B = null;
            }
        }
        DHImageView dHImageView4 = this.B;
        if (dHImageView4 != null) {
            if (dHImageView4.isSlipping()) {
                return null;
            }
            this.B.refreshImagerView();
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("==============B截图耗时=");
        long j2 = jCurrentTimeMillis2 - jCurrentTimeMillis;
        sb.append(j2);
        Logger.i("mabo", sb.toString());
        if (j2 >= BaseInfo.sTimeoutCapture) {
            int i3 = BaseInfo.sTimeOutCount + 1;
            BaseInfo.sTimeOutCount = i3;
            if (i3 > BaseInfo.sTimeOutMax) {
                BaseInfo.sAnimationCaptureB = false;
            }
        } else if (z2) {
            BaseInfo.sTimeOutCount = 0;
        }
        return this.B;
    }

    public boolean a(ViewGroup viewGroup) {
        DHImageView dHImageView = this.B;
        return (dHImageView == null || dHImageView.mBitmapHeight <= 0 || ((long) viewGroup.getHeight()) == this.B.mBitmapHeight) ? false : true;
    }
}

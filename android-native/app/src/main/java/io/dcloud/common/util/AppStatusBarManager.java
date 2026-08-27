package io.dcloud.common.util;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import io.dcloud.common.DHInterface.IActivityDelegate;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.core.ui.DCKeyboardManager;
import io.dcloud.common.ui.blur.DCBlurDraweeView;
import io.dcloud.p.d5;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AppStatusBarManager {
    private IApp mIApp;
    private d5 mWebAppInfo;
    private int mStatusBarDefaultColor = 0;
    public boolean isImmersive = false;
    public boolean isFullScreen = false;
    public boolean isHandledWhiteScreen = false;
    public boolean isTemporaryFullScreen = true;

    /* JADX WARN: Multi-variable type inference failed */
    public AppStatusBarManager(Activity activity, d5 d5Var) {
        initStatusBarDefaultColor(activity);
        this.mWebAppInfo = d5Var;
        this.mIApp = (IApp) d5Var;
        initDirectImmersive();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void diyContentFullScreenBug(Activity activity) {
        if ((activity instanceof IActivityDelegate ? ((IActivityDelegate) activity).obtainActivityContentView() : null) == null) {
            return;
        }
        if (!this.isFullScreen && !this.isImmersive) {
            this.isTemporaryFullScreen = false;
            DCKeyboardManager.getInstance().setAdjust(false);
            return;
        }
        String metaValue = AndroidResources.getMetaValue("DCLOUD_INPUT_MODE");
        if (TextUtils.isEmpty(metaValue) || !metaValue.contains("adjustPan")) {
            DCKeyboardManager.getInstance().setAdjust(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getRootView(Activity activity) {
        return activity.findViewById(R.id.content);
    }

    private void initDirectImmersive() {
        if (this.mWebAppInfo.getActivity().getIntent().hasExtra(IntentConst.DIRECT_PAGE) && BaseInfo.isWap2AppAppid(this.mWebAppInfo.o)) {
            JSONObject directStatusJson = getDirectStatusJson(this.mIApp);
            if (directStatusJson == null || !directStatusJson.has(AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED)) {
                this.isImmersive = true;
            } else {
                this.isImmersive = directStatusJson.optBoolean(AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED);
            }
            BaseInfo.isImmersive = this.isImmersive;
        }
    }

    private void setMeizuStatusBarDarkIcon(Activity activity, boolean z) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (activity != null) {
            try {
                WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
                Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                int i = declaredField.getInt(null);
                int i2 = declaredField2.getInt(attributes);
                declaredField2.setInt(attributes, z ? i2 | i : (~i) & i2);
                activity.getWindow().setAttributes(attributes);
            } catch (Exception unused) {
            }
        }
    }

    private void setMiuiStatusBarDarkMode(Activity activity, boolean z) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = activity.getWindow().getClass();
        try {
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            Class<?> cls3 = Integer.TYPE;
            cls.getMethod("setExtraFlags", cls3, cls3).invoke(activity.getWindow(), Integer.valueOf(z ? i : 0), Integer.valueOf(i));
        } catch (Exception unused) {
        }
    }

    private void setTranslucentStatus(final Activity activity, boolean z) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (z) {
            attributes.flags |= AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL;
        } else {
            attributes.flags &= -67108865;
        }
        window.setAttributes(attributes);
        window.getDecorView().post(new Runnable() { // from class: io.dcloud.common.util.AppStatusBarManager.2
            @Override // java.lang.Runnable
            public void run() {
                View rootView = AppStatusBarManager.this.getRootView(activity);
                if (rootView.getParent() instanceof LinearLayout) {
                    rootView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                } else if (rootView.getParent() instanceof FrameLayout) {
                    rootView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                }
            }
        });
    }

    public boolean checkImmersedStatusBar(Context context, boolean z) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        int i = Build.VERSION.SDK_INT;
        boolean z2 = false;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        boolean z3 = (applicationInfo == null || (bundle = applicationInfo.metaData) == null) ? false : bundle.getBoolean("immersed.status.bar", false);
        if (z3) {
            z = z3;
        }
        if (!this.mWebAppInfo.n.equals("suggestedDevice") || !z) {
            z2 = z;
        } else if (DeviceInfo.sBrand.equalsIgnoreCase(MobilePhoneModel.XIAOMI) || DeviceInfo.sBrand.contentEquals(MobilePhoneModel.MEIZU) || i >= 23) {
            z2 = true;
        }
        this.isImmersive = z2;
        if (!z2 && this.mWebAppInfo.getActivity().getIntent().hasExtra(IntentConst.DIRECT_PAGE) && BaseInfo.isWap2AppAppid(this.mWebAppInfo.o) && !this.mIApp.manifestBeParsed()) {
            JSONObject directStatusJson = getDirectStatusJson(this.mIApp);
            if (directStatusJson == null || !directStatusJson.has(AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED)) {
                this.isImmersive = true;
            } else {
                this.isImmersive = directStatusJson.optBoolean(AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED);
            }
            z2 = this.isImmersive;
        }
        BaseInfo.isImmersive = this.isImmersive;
        return z2;
    }

    public JSONObject getDirectStatusJson(IApp iApp) {
        JSONObject jSONObjectObtainThridInfo = iApp.obtainThridInfo(IApp.ConfigProperty.ThridInfo.DirectPageJsonData);
        if (jSONObjectObtainThridInfo != null && jSONObjectObtainThridInfo.has(AbsoluteConst.JSONKEY_STATUSBAR)) {
            JSONObject jSONObjectOptJSONObject = jSONObjectObtainThridInfo.optJSONObject(AbsoluteConst.JSONKEY_STATUSBAR);
            if (jSONObjectOptJSONObject != null) {
                return jSONObjectOptJSONObject;
            }
            return null;
        }
        JSONObject jSONObjectObtainThridInfo2 = iApp.obtainThridInfo(IApp.ConfigProperty.ThridInfo.SitemapJsonData);
        if (jSONObjectObtainThridInfo2 == null || !jSONObjectObtainThridInfo2.has(AbsoluteConst.JSONKEY_STATUSBAR)) {
            return null;
        }
        return jSONObjectObtainThridInfo2.optJSONObject(AbsoluteConst.JSONKEY_STATUSBAR);
    }

    public int getStatusBarDefaultColor() {
        return this.mStatusBarDefaultColor;
    }

    public int getStatusbarColorIndex() {
        try {
            return Integer.parseInt(PlatformUtil.invokeFieldValue(null, "Window_statusBarColor", PlatformUtil.newInstance(Base64.decode2String("Y29tLmFuZHJvaWQuaW50ZXJuYWwuUiRzdHlsZWFibGU="), null, null)).toString());
        } catch (Exception unused) {
            return -1;
        }
    }

    public boolean isFullScreenOrImmersive() {
        return this.isImmersive || this.isFullScreen;
    }

    public void setFullScreen(Activity activity, boolean z) {
        Window window = activity.getWindow();
        this.isFullScreen = z;
        if (z) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags |= 1024;
            if (Build.VERSION.SDK_INT >= 28) {
                attributes.layoutInDisplayCutoutMode = 1;
            }
            window.setAttributes(attributes);
        } else {
            WindowManager.LayoutParams attributes2 = window.getAttributes();
            attributes2.flags &= -1025;
            if (Build.VERSION.SDK_INT >= 28) {
                attributes2.layoutInDisplayCutoutMode = 0;
            }
            window.setAttributes(attributes2);
        }
        diyContentFullScreenBug(activity);
    }

    public void setImmersive(final Activity activity, boolean z) {
        if (activity != null) {
            if (DeviceInfo.sBrand.equalsIgnoreCase(MobilePhoneModel.SONY) || DeviceInfo.sBrand.equalsIgnoreCase(MobilePhoneModel.QiKU)) {
                this.isImmersive = z;
                setTranslucentStatus(activity, z);
                diyContentFullScreenBug(activity);
                return;
            }
            this.isImmersive = z;
            Window window = activity.getWindow();
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            window.clearFlags(1024);
            if (z) {
                window.clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
                window.addFlags(Integer.MIN_VALUE);
                window.getDecorView().setSystemUiVisibility(systemUiVisibility | 1280);
                window.setStatusBarColor(0);
            } else {
                window.getDecorView().setSystemUiVisibility(systemUiVisibility & (-1281));
                window.setStatusBarColor(this.mStatusBarDefaultColor);
            }
            window.getDecorView().post(new Runnable() { // from class: io.dcloud.common.util.AppStatusBarManager.1
                @Override // java.lang.Runnable
                public void run() {
                    View rootView = AppStatusBarManager.this.getRootView(activity);
                    if (rootView.getParent() instanceof LinearLayout) {
                        rootView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                    } else if (rootView.getParent() instanceof FrameLayout) {
                        rootView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                    }
                }
            });
            diyContentFullScreenBug(activity);
        }
    }

    public void setStatusBarColor(Activity activity, int i) {
        if (!PdrUtil.checkStatusbarColor(i) || activity == null || this.isImmersive) {
            return;
        }
        Window window = activity.getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(i);
    }

    public void setStatusBarMode(Activity activity, String str) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int i;
        int i2 = Build.VERSION.SDK_INT;
        if (activity != null) {
            if (PdrUtil.isEmpty(str)) {
                str = "nono";
            }
            boolean zEqualsIgnoreCase = str.equalsIgnoreCase(DCBlurDraweeView.DARK);
            Window window = activity.getWindow();
            String str2 = Build.BRAND;
            if (str2.equalsIgnoreCase(MobilePhoneModel.GOOGLE)) {
                str2 = Build.MANUFACTURER;
            }
            if (str2.equals(MobilePhoneModel.XIAOMI)) {
                setMiuiStatusBarDarkMode(activity, zEqualsIgnoreCase);
            } else if (str2.equals(MobilePhoneModel.MEIZU)) {
                setMeizuStatusBarDarkIcon(activity, zEqualsIgnoreCase);
            }
            if (i2 >= 23) {
                int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                try {
                    Class<?> cls = Class.forName("android.view.View");
                    i = cls.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR").getInt(cls);
                } catch (Exception e) {
                    e.printStackTrace();
                    i = 8192;
                }
                window.getDecorView().setSystemUiVisibility(zEqualsIgnoreCase ? systemUiVisibility | i : systemUiVisibility & (~i));
            }
        }
    }

    private void initStatusBarDefaultColor(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            int statusbarColorIndex = getStatusbarColorIndex();
            if (statusbarColorIndex > 0) {
                this.mStatusBarDefaultColor = window.getWindowStyle().getColor(statusbarColorIndex, 0);
            }
            if (this.mStatusBarDefaultColor == 0) {
                this.mStatusBarDefaultColor = Color.parseColor("#D4D4D4");
            }
        }
    }
}

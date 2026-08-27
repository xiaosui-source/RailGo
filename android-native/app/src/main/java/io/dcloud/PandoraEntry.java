package io.dcloud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.ReflectUtils;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class PandoraEntry extends Activity {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PandoraEntry.this.finish();
        }
    }

    private void a(Intent intent) {
        InputStream inputStream;
        try {
            boolean zIsUniMPSDK = SDK.isUniMPSDK();
            String str = BaseInfo.sDefaultBootApp + "/www/manifest.json";
            if (zIsUniMPSDK) {
                File file = new File(BaseInfo.sCacheFsAppsPath + str);
                inputStream = file.exists() ? DHFile.getInputStream(file) : getResources().getAssets().open(str);
            } else {
                File file2 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath().replace("files/Pictures", "apps/") + str);
                inputStream = file2.exists() ? DHFile.getInputStream(file2) : null;
            }
            if (inputStream == null) {
                return;
            }
            String string = IOUtil.toString(inputStream);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            intent.putExtra(IntentConst.INTENT_ORIENTATION, PdrUtil.getConfigOrientation(new JSONObject(string)));
        } catch (Exception unused) {
        }
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            super.attachBaseContext(context);
        } else {
            super.attachBaseContext(LanguageUtil.updateContextLanguageAfterO(context, false));
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT == 26 && a()) {
            a(this);
        }
        super.onCreate(bundle);
        Intent intent = getIntent();
        ReflectUtils.invokeMethod("io.dcloud.feature.aps.NotificationReceiver", "sOnReceiver", null, new Class[]{Context.class, Intent.class}, new Object[]{this, intent});
        AndroidResources.initAndroidResources(getApplication());
        BaseInfo.parseControl();
        if (SDK.isUniMPSDK() && intent.hasExtra("appid")) {
            BaseInfo.sDefaultBootApp = intent.getStringExtra("appid");
        }
        if (SDK.isUniMPSDK() || BaseInfo.SyncDebug) {
            a(intent);
        }
        if (intent.hasExtra(IntentConst.START_FROM_TO_CLASS) && SDK.isUniMPSDK()) {
            String stringExtra = intent.getStringExtra(IntentConst.START_FROM_TO_CLASS);
            if (!TextUtils.isEmpty(stringExtra) && stringExtra.startsWith("io.dcloud.feature.sdk.multi")) {
                intent.setClassName(getPackageName(), stringExtra);
                intent.removeExtra(IntentConst.START_FROM_TO_CLASS);
            }
        } else {
            intent.putExtra(IntentConst.WEBAPP_SHORT_CUT_CLASS_NAME, PandoraEntry.class.getName());
            intent.setClass(this, PandoraEntryActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(0, 0);
        if (SDK.isUniMPSDK()) {
            finish();
        } else {
            new Handler().postDelayed(new a(), 20L);
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    private static void a(Activity activity) throws NoSuchFieldException, SecurityException {
        try {
            Field declaredField = Activity.class.getDeclaredField("mActivityInfo");
            declaredField.setAccessible(true);
            ((ActivityInfo) declaredField.get(activity)).screenOrientation = -1;
        } catch (Exception unused) {
        }
    }

    private boolean a() throws NoSuchMethodException, SecurityException {
        try {
            TypedArray typedArrayObtainStyledAttributes = obtainStyledAttributes((int[]) Class.forName("com.android.internal.R$styleable").getField(AbsoluteConst.FEATURE_WINDOW).get(null));
            Method method = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            method.setAccessible(true);
            boolean zBooleanValue = ((Boolean) method.invoke(null, typedArrayObtainStyledAttributes)).booleanValue();
            try {
                method.setAccessible(false);
                return zBooleanValue;
            } catch (Exception unused) {
                return zBooleanValue;
            }
        } catch (Exception unused2) {
            return false;
        }
    }
}

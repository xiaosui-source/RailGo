package io.dcloud.common.util.language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class LanguageUtil {
    public static final String LanguageBroadCastIntent = "language_uni_broad_cast_intent";
    public static final String LanguageConfigKey = "language_uni_current_key";
    public static final String LanguageConfigSPFile = "language_uni_sp_file";
    private static String deviceDefCountry = "";
    private static String deviceDefLocalLanguage = "";
    private static String sCurrentLocalLanguage = "";

    public static Locale getCurrentLocal(Context context, boolean z) {
        if (context == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 24) {
            if (!z) {
                return context.getResources().getConfiguration().locale;
            }
            context.getResources();
            return Resources.getSystem().getConfiguration().locale;
        }
        LocaleList locales = context.getResources().getConfiguration().getLocales();
        if (z) {
            context.getResources();
            locales = Resources.getSystem().getConfiguration().getLocales();
        }
        return (locales == null || locales.size() <= 0) ? context.getResources().getConfiguration().locale : locales.get(0);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getCurrentLocaleLanguage(android.content.Context r7) {
        /*
            java.lang.String r0 = io.dcloud.common.util.language.LanguageUtil.sCurrentLocalLanguage
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto La5
            r0 = 1
            java.util.Locale r7 = getCurrentLocal(r7, r0)
            if (r7 != 0) goto L12
            java.lang.String r7 = io.dcloud.common.util.language.LanguageUtil.deviceDefLocalLanguage
            return r7
        L12:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r7.getLanguage()
            r1.append(r2)
            java.lang.String r2 = "-"
            r1.append(r2)
            java.lang.String r2 = r7.getCountry()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 21
            if (r2 <= r3) goto L38
            java.lang.String r1 = r7.toLanguageTag()
        L38:
            java.lang.String r7 = r7.getLanguage()
            r7.getClass()
            r7.hashCode()
            int r2 = r7.hashCode()
            java.lang.String r3 = "fr"
            java.lang.String r4 = "es"
            java.lang.String r5 = "en"
            r6 = -1
            switch(r2) {
                case 3241: goto L6e;
                case 3246: goto L67;
                case 3276: goto L5e;
                case 3886: goto L52;
                default: goto L50;
            }
        L50:
            r0 = -1
            goto L76
        L52:
            java.lang.String r0 = "zh"
            boolean r7 = r7.equals(r0)
            if (r7 != 0) goto L5c
            goto L50
        L5c:
            r0 = 3
            goto L76
        L5e:
            boolean r7 = r7.equals(r3)
            if (r7 != 0) goto L65
            goto L50
        L65:
            r0 = 2
            goto L76
        L67:
            boolean r7 = r7.equals(r4)
            if (r7 != 0) goto L76
            goto L50
        L6e:
            boolean r7 = r7.equals(r5)
            if (r7 != 0) goto L75
            goto L50
        L75:
            r0 = 0
        L76:
            switch(r0) {
                case 0: goto La4;
                case 1: goto La3;
                case 2: goto La2;
                case 3: goto L7a;
                default: goto L79;
            }
        L79:
            goto La1
        L7a:
            java.lang.String r7 = "zh-CN"
            boolean r7 = r1.equalsIgnoreCase(r7)
            if (r7 == 0) goto L87
            java.lang.String r7 = "zh-Hans"
            return r7
        L87:
            java.lang.String r7 = "zh-HK"
            boolean r7 = r1.equalsIgnoreCase(r7)
            if (r7 == 0) goto L94
            java.lang.String r7 = "zh-Hant-HK"
            return r7
        L94:
            java.lang.String r7 = "zh-TW"
            boolean r7 = r1.equalsIgnoreCase(r7)
            if (r7 == 0) goto La1
            java.lang.String r7 = "zh-Hant-TW"
            return r7
        La1:
            return r1
        La2:
            return r3
        La3:
            return r4
        La4:
            return r5
        La5:
            java.lang.String r7 = io.dcloud.common.util.language.LanguageUtil.sCurrentLocalLanguage
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.language.LanguageUtil.getCurrentLocaleLanguage(android.content.Context):java.lang.String");
    }

    public static String getDeviceDefCountry() {
        return deviceDefCountry;
    }

    public static String getDeviceDefLocalLanguage() {
        return deviceDefLocalLanguage;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getString(com.alibaba.fastjson.JSONObject r7, java.lang.String r8) {
        /*
            if (r7 == 0) goto Lae
            android.content.Context r0 = io.dcloud.common.adapter.util.DeviceInfo.sApplicationContext
            java.lang.String r0 = getCurrentLocaleLanguage(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto Lae
            java.lang.String r1 = getString(r0, r7)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto Laf
            java.lang.String r2 = "zh-CN"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L29
            java.lang.String r1 = "zh-Hans"
            java.lang.String r1 = getString(r1, r7)
            goto L4a
        L29:
            java.lang.String r2 = "zh-HK"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L3a
            java.lang.String r1 = "zh-Hant-HK"
            java.lang.String r1 = getString(r1, r7)
            goto L4a
        L3a:
            java.lang.String r2 = "zh-TW"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L4a
            java.lang.String r1 = "zh-Hant-TW"
            java.lang.String r1 = getString(r1, r7)
        L4a:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto Laf
            java.lang.String r2 = "-"
            java.lang.String[] r0 = r0.split(r2)
            int r3 = r0.length
            r4 = 2
            r5 = 0
            if (r3 == r4) goto La7
            r6 = 3
            if (r3 == r6) goto L5f
            goto Laf
        L5f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r3 = r0[r5]
            r1.append(r3)
            r1.append(r2)
            r3 = r0[r4]
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = getString(r1, r7)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 == 0) goto L9a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r3 = r0[r5]
            r1.append(r3)
            r1.append(r2)
            r2 = 1
            r2 = r0[r2]
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = getString(r1, r7)
        L9a:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto Laf
            r0 = r0[r5]
            java.lang.String r1 = getString(r0, r7)
            goto Laf
        La7:
            r0 = r0[r5]
            java.lang.String r1 = getString(r0, r7)
            goto Laf
        Lae:
            r1 = r8
        Laf:
            boolean r7 = io.dcloud.common.util.PdrUtil.isEmpty(r1)
            if (r7 == 0) goto Lb6
            return r8
        Lb6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.language.LanguageUtil.getString(com.alibaba.fastjson.JSONObject, java.lang.String):java.lang.String");
    }

    public static void initAppLanguageForAppBeforeO(Context context) {
        updateAppBootLanguage(context);
        updateSystemLanguage(context);
    }

    private static void updateAppBootLanguage(Context context) {
        String string;
        String string2 = context.getSharedPreferences(LanguageConfigSPFile, 0).getString(LanguageConfigKey, "");
        try {
            string = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("DCLOUD_APP_DEFAULT_LANGUAGE");
        } catch (Exception e) {
            e.printStackTrace();
            string = "";
        }
        if (TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string)) {
            string2 = string;
        }
        sCurrentLocalLanguage = "auto".equalsIgnoreCase(string2) ? "" : string2;
        updateDeviceDefLocalLanguage(context);
    }

    public static Context updateContextLanguageAfterO(Context context, boolean z) {
        return updateContextLanguageAfterO(context, z, true);
    }

    public static void updateDeviceDefLocalLanguage(Locale locale) {
        if (locale != null) {
            deviceDefLocalLanguage = locale.getLanguage() + Operators.SUB + locale.getCountry();
            deviceDefCountry = locale.getCountry();
            DeviceInfo.sLanguage = deviceDefLocalLanguage;
        }
    }

    public static void updateLanguage(Context context, String str) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(LanguageConfigSPFile, 0).edit();
        editorEdit.putString(LanguageConfigKey, str);
        editorEdit.commit();
        sCurrentLocalLanguage = str;
        updateSystemLanguage(context);
    }

    public static void updateSystemLanguage(Context context) {
        if (context == null) {
            return;
        }
        String str = sCurrentLocalLanguage;
        if ("zh-Hant-TW".equals(str) || "zh-Hant".equals(str)) {
            str = "zh-TW";
        } else if ("zh-Hant-HK".equals(str)) {
            str = "zh-HK";
        }
        if (Build.VERSION.SDK_INT < 24) {
            Resources resources = context.getResources();
            if (TextUtils.isEmpty(str)) {
                str = deviceDefLocalLanguage;
            }
            Locale localeForLanguageTag = Locale.forLanguageTag(str);
            Locale.setDefault(localeForLanguageTag);
            Configuration configuration = resources.getConfiguration();
            configuration.setLocale(localeForLanguageTag);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            return;
        }
        Resources resources2 = context.getResources();
        DisplayMetrics displayMetrics = resources2.getDisplayMetrics();
        Configuration configuration2 = resources2.getConfiguration();
        if (TextUtils.isEmpty(str)) {
            str = deviceDefLocalLanguage;
        }
        f4$$ExternalSyntheticApiModelOutline0.m464m();
        LocaleList localeListM = f4$$ExternalSyntheticApiModelOutline0.m(new Locale[]{Locale.forLanguageTag(str)});
        LocaleList.setDefault(localeListM);
        configuration2.setLocales(localeListM);
        resources2.updateConfiguration(configuration2, displayMetrics);
    }

    public static Context wrapContextConfigurationAfterO(Context context, String str) {
        return wrapContextConfigurationAfterO(context, str, true);
    }

    public static Context updateContextLanguageAfterO(Context context, boolean z, boolean z2) {
        if (z) {
            updateAppBootLanguage(context);
        }
        return TextUtils.isEmpty(sCurrentLocalLanguage) ? context : wrapContextConfigurationAfterO(context, sCurrentLocalLanguage, z2);
    }

    public static Context wrapContextConfigurationAfterO(Context context, String str, boolean z) {
        if (Build.VERSION.SDK_INT < 24) {
            return context;
        }
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        f4$$ExternalSyntheticApiModelOutline0.m464m();
        LocaleList localeListM = f4$$ExternalSyntheticApiModelOutline0.m(new Locale[]{Locale.forLanguageTag(str)});
        LocaleList.setDefault(localeListM);
        configuration.setLocales(localeListM);
        resources.updateConfiguration(configuration, displayMetrics);
        return z ? context.createConfigurationContext(configuration) : context;
    }

    public static void updateDeviceDefLocalLanguage(Context context) {
        updateDeviceDefLocalLanguage(getCurrentLocal(context, true));
    }

    private static String getString(String str, JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.containsKey(str)) {
            return null;
        }
        return jSONObject.getString(str);
    }
}

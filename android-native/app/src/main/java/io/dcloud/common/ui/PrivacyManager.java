package io.dcloud.common.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.base.R;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.ui.Info.AndroidPrivacyResponse;
import io.dcloud.common.ui.a;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class PrivacyManager {
    private static PrivacyManager f;
    private List a = new ArrayList();
    private AndroidPrivacyResponse b = new AndroidPrivacyResponse();
    private boolean c = false;
    public boolean d = false;
    private io.dcloud.common.ui.a e;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    @FunctionalInterface
    public interface PrivacyAgreeCallback {
        void call(Boolean bool);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface b {
        void a();

        void a(AndroidPrivacyResponse androidPrivacyResponse);

        void a(String str);

        void b(AndroidPrivacyResponse androidPrivacyResponse);
    }

    private PrivacyManager() {
    }

    public static PrivacyManager getInstance() {
        if (f == null) {
            f = new PrivacyManager();
        }
        return f;
    }

    public static boolean isDebugMode() throws ClassNotFoundException {
        Class<?> cls;
        try {
            cls = Class.forName("io.dcloud.common.util.net.http.LocalServer2");
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        return cls != null;
    }

    public boolean getIsAppAsset() {
        return this.d;
    }

    public void init(Context context) {
        init(context, null);
    }

    public boolean isAgreePrivacy(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("privacy_config_uni_sp_file", 0);
        return "1".equals(SP.getBundleData(context, "pdr", "scok")) && sharedPreferences.getString("privacy_config_version_uni_current_key", "emptyPrivacyVersion").equals(this.b.version) && sharedPreferences.getInt("privacy_config_choose_disagree_uni_current_key", 0) == 0;
    }

    public boolean isNotPrivacyAllRight(Context context) {
        if (!this.c) {
            init(context);
        }
        String str = this.b.prompt;
        String bundleData = SP.getBundleData(context, "pdr", "scok");
        return (PdrUtil.isEmpty(bundleData) || !bundleData.equals("1")) && !PdrUtil.isEmpty(str) && str.equalsIgnoreCase("template");
    }

    public boolean isPrivacyVersionChange(Context context) {
        this.c = false;
        init(context);
        return (SDK.isUniMPSDK() || !"template".equals(this.b.prompt) || context.getSharedPreferences("privacy_config_uni_sp_file", 0).getString("privacy_config_version_uni_current_key", "").equals(this.b.version)) ? false : true;
    }

    public void reInit(Context context, JSONObject jSONObject) throws Throwable {
        if (jSONObject != null) {
            this.c = false;
            init(context, jSONObject);
        } else {
            this.c = false;
            init(context, null);
        }
    }

    public void registerPrivacyAgreeListener(String str, PrivacyAgreeCallback privacyAgreeCallback) {
        this.a.add(new Pair(str, privacyAgreeCallback));
    }

    public void resetPrivacyLocalConfig(Context context) {
        context.getSharedPreferences("privacy_config_uni_sp_file", 0).edit().putString("privacy_config_version_uni_current_key", "").putInt("privacy_config_choose_disagree_uni_current_key", 0).apply();
        SP.setBundleData(context, "pdr", "scok", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        this.a.forEach(new Consumer() { // from class: io.dcloud.common.ui.PrivacyManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PrivacyManager.a((Pair) obj);
            }
        });
    }

    public void restartPrivacyDialogIfNeed(Activity activity) {
        io.dcloud.common.ui.a aVar = this.e;
        if (aVar == null || !aVar.d()) {
            return;
        }
        b bVarB = this.e.b();
        boolean zC = this.e.c();
        this.e.a();
        this.e = null;
        showPrivacyDialog(activity, bVarB, zC, true);
    }

    public void setAgreePrivacy(Context context, final boolean z) {
        SP.setBundleData(context, "pdr", "scok", z ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        context.getSharedPreferences("privacy_config_uni_sp_file", 0).edit().putInt("privacy_config_choose_disagree_uni_current_key", !z ? 1 : 0).putString("privacy_config_version_uni_current_key", this.b.version).apply();
        this.a.forEach(new Consumer() { // from class: io.dcloud.common.ui.PrivacyManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PrivacyManager.a(z, (Pair) obj);
            }
        });
    }

    public void showPrivacyDialog(Activity activity, b bVar, boolean z, boolean z2) {
        if (!a(activity)) {
            bVar.a(this.b.prompt);
            return;
        }
        a aVar = new a(activity, bVar);
        io.dcloud.common.ui.a aVar2 = this.e;
        if (aVar2 != null) {
            aVar2.a();
            this.e = null;
        }
        if (z) {
            io.dcloud.common.ui.a aVar3 = new io.dcloud.common.ui.a(activity);
            this.e = aVar3;
            aVar3.a(bVar);
            this.e.a(true);
            this.e.a(this.b, true, (a.d) aVar);
            this.e.a(R.layout.dcloud_custom_privacy_second_dialog_layout);
            this.e.e();
            return;
        }
        io.dcloud.common.ui.a aVar4 = new io.dcloud.common.ui.a(activity);
        this.e = aVar4;
        aVar4.a(bVar);
        this.e.a(false);
        this.e.a(R.layout.dcloud_custom_privacy_dialog_layout);
        this.e.a(this.b, false, (a.d) aVar);
        this.e.e();
    }

    public void unRegisterPrivacyAgreeAllListener() {
        this.a.clear();
    }

    public void unRegisterPrivacyAgreeListener(String str) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            if (((Pair) it.next()).first.equals(str)) {
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Pair pair) {
        ((PrivacyAgreeCallback) pair.second).call(Boolean.FALSE);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void init(android.content.Context r9, com.alibaba.fastjson.JSONObject r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 941
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.ui.PrivacyManager.init(android.content.Context, com.alibaba.fastjson.JSONObject):void");
    }

    private static String a(String str) throws Throwable {
        BufferedReader bufferedReader;
        File file = new File(str);
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            stringBuffer.append(line);
                        } else {
                            bufferedReader.close();
                            String string = stringBuffer.toString();
                            try {
                                bufferedReader.close();
                                return string;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return string;
                            }
                        }
                    } catch (IOException e2) {
                        e = e2;
                        bufferedReader2 = bufferedReader;
                        e.printStackTrace();
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        return stringBuffer.toString();
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
            }
        } catch (IOException e5) {
            e = e5;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements a.d {
        final /* synthetic */ Activity a;
        final /* synthetic */ b b;

        a(Activity activity, b bVar) {
            this.a = activity;
            this.b = bVar;
        }

        @Override // io.dcloud.common.ui.a.d
        public void a(String str) {
            this.a.getSharedPreferences("privacy_config_uni_sp_file", 0).edit().putString("privacy_config_version_uni_current_key", str).apply();
            SP.setBundleData(this.a, "pdr", "scok", "1");
            this.b.a();
        }

        @Override // io.dcloud.common.ui.a.d
        public void onCancel() {
            SP.setBundleData(this.a, "pdr", "scok", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
            this.b.a(PrivacyManager.this.b);
            this.a.getSharedPreferences("privacy_config_uni_sp_file", 0).edit().putInt("privacy_config_choose_disagree_uni_current_key", 1).apply();
        }

        @Override // io.dcloud.common.ui.a.d
        public void a() {
            SP.setBundleData(this.a, "pdr", "scok", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
            this.b.b(PrivacyManager.this.b);
        }
    }

    private static String a(Context context, String str) throws IOException {
        try {
            InputStream inputStreamOpen = context.getResources().getAssets().open(str);
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            return new String(bArr);
        } catch (Exception unused) {
            return "";
        }
    }

    private boolean a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("privacy_config_uni_sp_file", 0);
        String string = sharedPreferences.getString("privacy_config_version_uni_current_key", "emptyPrivacyVersion");
        int i = sharedPreferences.getInt("privacy_config_choose_disagree_uni_current_key", 0);
        if (!string.equals(this.b.version) && !"emptyPrivacyVersion".equals(string)) {
            sharedPreferences.edit().putInt("privacy_config_choose_disagree_uni_current_key", 0).apply();
            SP.setBundleData(context, "pdr", "scok", "");
            if (!this.b.prompt.equals("template")) {
                return false;
            }
            SP.setBundleData(context, "pdr", "scok", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
            return true;
        }
        if (this.b.prompt.equals("template")) {
            AndroidPrivacyResponse.disagreeModeDTO disagreemodedto = this.b.disagreeMode;
            if (((!disagreemodedto.support && !disagreemodedto.visitorEntry) || disagreemodedto.showAlways || i != 1) && !"1".equals(SP.getBundleData(context, "pdr", "scok"))) {
                SP.setBundleData(context, "pdr", "scok", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(boolean z, Pair pair) {
        ((PrivacyAgreeCallback) pair.second).call(Boolean.valueOf(z));
    }
}

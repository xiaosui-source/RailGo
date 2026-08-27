package io.dcloud.sdk.base.dcloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.p.c0;
import io.dcloud.p.k3;
import io.dcloud.p.l;
import io.dcloud.p.m4;
import io.dcloud.p.r0;
import io.dcloud.p.r1;
import io.dcloud.p.v0;
import io.dcloud.p.w4;
import io.dcloud.p.y0;
import io.dcloud.p.z0;
import io.dcloud.sdk.core.util.ReflectUtil;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class ADHandler {
    private static LinkedList a;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class InstallReceiver extends BroadcastReceiver {
        e a;
        String b;

        public InstallReceiver(e eVar, String str) {
            this.a = eVar;
            this.b = str;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (this.b.equals(intent.getData().getSchemeSpecificPart())) {
                String strOptString = this.a.b().optString("tid");
                String strA = ADHandler.a(context, "adid");
                e eVar = this.a;
                ADHandler.b(31, context, strOptString, strA, eVar, eVar.h);
                context.unregisterReceiver(this);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        final /* synthetic */ h a;

        a(h hVar) {
            this.a = hVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.execute();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        final /* synthetic */ e a;
        final /* synthetic */ Context b;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        b(e eVar, Context context, String str, String str2) {
            this.a = eVar;
            this.b = context;
            this.c = str;
            this.d = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            JSONObject jSONObjectC = this.a.c();
            if (jSONObjectC != null && jSONObjectC.has("ua")) {
                jSONObjectC.optString("ua");
            }
            ADHandler.b(50, this.b, this.c, this.d, this.a, r0.d().b().getAppId());
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements f {
        final /* synthetic */ e a;
        final /* synthetic */ Context b;

        c(e eVar, Context context) {
            this.a = eVar;
            this.b = context;
        }

        @Override // io.dcloud.sdk.base.dcloud.ADHandler.f
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void operate(File file) throws IOException {
            ADHandler.a.add(file);
            ADHandler.b(this.b, file, this.a);
        }

        @Override // io.dcloud.sdk.base.dcloud.ADHandler.f
        public boolean find() {
            return this.a.a();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements h {
        final /* synthetic */ JSONObject a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;
        final /* synthetic */ g d;

        d(JSONObject jSONObject, String str, String str2, g gVar) {
            this.a = jSONObject;
            this.b = str;
            this.c = str2;
            this.d = gVar;
        }

        @Override // io.dcloud.sdk.base.dcloud.ADHandler.h
        public void execute() throws IOException {
            HashMap map;
            byte[] bArrA = null;
            if (this.a.has("ua") && this.a.optString("ua").equalsIgnoreCase("webview")) {
                map = new HashMap();
                map.put(IWebview.USER_AGENT, ADHandler.a("ua-webview"));
            } else {
                map = null;
            }
            try {
                bArrA = k3.a(this.b, map, true);
            } catch (Exception unused) {
            }
            StringBuilder sb = new StringBuilder("download file is nulll");
            sb.append(bArrA == null);
            sb.append("src=");
            sb.append(this.b);
            ADHandler.a("adh", sb.toString());
            if (bArrA == null) {
                this.d.b();
            } else {
                v0.a(bArrA, 0, this.c);
                this.d.a();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class e {
        MotionEvent a;
        MotionEvent b;
        String c;
        JSONObject d;
        public Object e;
        String f;
        String g;
        String h;
        String k;
        int i = 0;
        int j = 0;
        public String l = "";

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a extends BroadcastReceiver {
            final /* synthetic */ r1 a;

            a(r1 r1Var) {
                this.a = r1Var;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                try {
                    long longExtra = intent.getLongExtra("end", 0L) - intent.getLongExtra("begin", 0L);
                    ADHandler.a("ADReceive", "useTime=" + longExtra);
                    if (longExtra <= 3000) {
                        this.a.onReceiver(null);
                    }
                    ADHandler.a("ADReceive", "unregisterReceiver");
                    context.unregisterReceiver(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class b implements y0.a {
            final /* synthetic */ Context a;
            final /* synthetic */ String b;
            final /* synthetic */ String c;
            final /* synthetic */ String d;

            b(Context context, String str, String str2, String str3) {
                this.a = context;
                this.b = str;
                this.c = str2;
                this.d = str3;
            }

            @Override // io.dcloud.p.y0.a
            public void a(y0 y0Var) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                Context context = this.a;
                String str = this.b;
                String str2 = this.c;
                e eVar = e.this;
                ADHandler.b(30, context, str, str2, eVar, eVar.h);
                PackageInfo packageInfoF = io.dcloud.p.c.f(this.a, this.d);
                if (packageInfoF != null) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                    intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter.addDataScheme("package");
                    this.a.registerReceiver(new InstallReceiver(e.this, packageInfoF.packageName), intentFilter);
                }
                try {
                    Class.forName("io.dcloud.feature.pack.FileUtils").getDeclaredMethod("addFileToSystem", Context.class, String.class, String.class).invoke(null, this.a, this.a.getPackageName() + ".dc.fileprovider", this.d);
                } catch (Exception unused) {
                }
            }

            @Override // io.dcloud.p.y0.a
            public void b(y0 y0Var) {
                Context context = this.a;
                String str = this.b;
                String str2 = this.c;
                e eVar = e.this;
                ADHandler.b(32, context, str, str2, eVar, eVar.h);
            }
        }

        public void a(String str) {
            this.k = str;
        }

        JSONObject b() {
            return this.d.optJSONObject("data");
        }

        JSONObject c() {
            return this.d;
        }

        boolean d() {
            return this.j == 1;
        }

        JSONObject e() {
            return this.d.optJSONObject("report");
        }

        public boolean a() {
            return (this.d == null || this.e == null) ? false : true;
        }

        void a(Context context, r1 r1Var) {
            if (r1Var != null) {
                a aVar = new a(r1Var);
                try {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("ad_receive");
                    LocalBroadcastManager.getInstance(context).registerReceiver(aVar, intentFilter);
                    ADHandler.a("ADReceive", "registerReceiver");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(Context context, String str, String str2) {
            String str3;
            io.dcloud.p.c.a(context);
            z0 z0VarA = z0.a(context);
            List listA = z0VarA.a();
            JSONObject jSONObjectOptJSONObject = this.d.optJSONObject("data");
            if (jSONObjectOptJSONObject == null) {
                return;
            }
            String strOptString = jSONObjectOptJSONObject.optString("url");
            String strOptString2 = jSONObjectOptJSONObject.optString("downloadAppName");
            Iterator it = listA.iterator();
            while (it.hasNext()) {
                if (((y0) it.next()).c().equals(strOptString)) {
                    return;
                }
            }
            String str4 = io.dcloud.p.f.a;
            if (TextUtils.isEmpty(strOptString2)) {
                str3 = null;
            } else {
                str3 = strOptString2 + str4;
            }
            String strA = l.a(strOptString, str4, str3);
            String absolutePath = context.getExternalFilesDir(null).getAbsolutePath();
            StringBuilder sb = new StringBuilder();
            sb.append(absolutePath);
            sb.append(absolutePath.endsWith("/") ? "" : "/");
            String str5 = sb.toString() + "Download/" + strA;
            ADHandler.b(29, context, str, str2, this, this.h);
            y0 y0Var = new y0();
            y0Var.a(context, strOptString, str5);
            y0Var.a(new b(context, str, str2, str5));
            z0VarA.a(y0Var);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    interface f {
        boolean find();

        void operate(Object obj);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface g {
        void a();

        void b();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    interface h {
        void execute();
    }

    static void a(String str, String str2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final int i, final Context context, final String str, final String str2, final e eVar, final String str3) {
        w4.a().a(new Runnable() { // from class: io.dcloud.sdk.base.dcloud.ADHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ADHandler.a(context, str3, str, str2, i, eVar);
            }
        });
    }

    static void c(Context context, e eVar, String str) {
        if ("wanka".equals(eVar.c)) {
            io.dcloud.sdk.base.dcloud.a.f(context, eVar, str);
        } else if ("youdao".equals(eVar.c)) {
            io.dcloud.sdk.base.dcloud.b.f(context, eVar, str);
        } else if ("common".equals(eVar.c)) {
            io.dcloud.sdk.base.dcloud.e.a(context, eVar, str, "imptracker");
        }
    }

    static void b(Context context, e eVar, String str) {
        JSONObject jSONObjectB = eVar.b();
        String strOptString = eVar.b().optString("tid");
        if (jSONObjectB.has("dplk") && io.dcloud.p.c.d(context, jSONObjectB.optString("dplk"))) {
            if (eVar.d()) {
                return;
            }
            w4.a().a(new b(eVar, context, strOptString, str));
            if ("wanka".equals(eVar.c)) {
                io.dcloud.sdk.base.dcloud.a.e(context, eVar, str);
                return;
            } else if ("youdao".equals(eVar.c)) {
                io.dcloud.sdk.base.dcloud.b.e(context, eVar, str);
                return;
            } else {
                if ("common".equals(eVar.c)) {
                    io.dcloud.sdk.base.dcloud.e.a(context, eVar, str, "dptracker");
                    return;
                }
                return;
            }
        }
        String strOptString2 = jSONObjectB.optString("action");
        if (TextUtils.equals("url", strOptString2)) {
            if (eVar.d()) {
                io.dcloud.sdk.base.dcloud.c.a(context, jSONObjectB.optString("url"));
                return;
            } else {
                io.dcloud.p.c.e(context, jSONObjectB.optString("url"));
                return;
            }
        }
        if (!TextUtils.equals(AbsoluteConst.SPNAME_DOWNLOAD, strOptString2)) {
            if (!TextUtils.equals("browser", strOptString2) || eVar.d()) {
                return;
            }
            io.dcloud.p.c.c(context, jSONObjectB.optString("url"));
            return;
        }
        if (jSONObjectB.has("expires")) {
            try {
                new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(jSONObjectB.optString("expires")).getTime();
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
        String strOptString3 = eVar.c() != null ? eVar.c().optString("ua") : "";
        if (eVar.d()) {
            io.dcloud.sdk.base.dcloud.c.a(context, eVar.h, strOptString, str, jSONObjectB.optString("url"), jSONObjectB.optString(AbsURIAdapter.BUNDLE), null, strOptString3, eVar.k);
        } else {
            eVar.a(context, strOptString, str);
        }
    }

    public static String a(String str) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "adid":
                return r0.d().b().getAdId();
            case "appid":
                return r0.d().b().getAppId();
            case "ua-webview":
                return m4.e(r0.d().c());
            default:
                return null;
        }
    }

    public static String a(Context context, String str) {
        return a(str);
    }

    private static void a(h hVar) {
        if (hVar != null) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                w4.a().a(new a(hVar));
            } else {
                hVar.execute();
            }
        }
    }

    static String a(String str, JSONObject jSONObject) {
        try {
            return str.replace("${User-Agent}", URLEncoder.encode(jSONObject.optString("u-a"), "utf-8")).replace("${click_id}", jSONObject.optString("click_id")).replace("${down_x}", String.valueOf(jSONObject.optInt("down_x", DCloudAlertDialog.DARK_THEME))).replace("${down_y}", String.valueOf(jSONObject.optInt("down_y", DCloudAlertDialog.DARK_THEME))).replace("${up_x}", String.valueOf(jSONObject.optInt("up_x", DCloudAlertDialog.DARK_THEME))).replace("${up_y}", String.valueOf(jSONObject.optInt("up_y", DCloudAlertDialog.DARK_THEME))).replace("${relative_down_x}", String.valueOf(jSONObject.optInt("relative_down_x", DCloudAlertDialog.DARK_THEME))).replace("${relative_down_y}", String.valueOf(jSONObject.optInt("relative_down_y", DCloudAlertDialog.DARK_THEME))).replace("${relative_up_x}", String.valueOf(jSONObject.optInt("relative_up_x", DCloudAlertDialog.DARK_THEME))).replace("${relative_up_y}", String.valueOf(jSONObject.optInt("relative_up_y", DCloudAlertDialog.DARK_THEME)));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, String str, String str2, String str3, int i, e eVar) {
        c0.a(context, str, str2, str3, i, eVar.k);
    }

    static void a(Context context, e eVar, String str) {
        if ("wanka".equals(eVar.c)) {
            io.dcloud.sdk.base.dcloud.a.d(context, eVar, str);
            return;
        }
        if ("youdao".equals(eVar.c)) {
            io.dcloud.sdk.base.dcloud.b.d(context, eVar, str);
        } else if ("common".equals(eVar.c)) {
            io.dcloud.sdk.base.dcloud.e.d(context, eVar, str);
        } else {
            b(context, eVar, str);
        }
    }

    private static void a(File[] fileArr) {
        if (fileArr == null) {
            return;
        }
        for (int i = 0; i < fileArr.length - 1; i++) {
            int i2 = 0;
            while (i2 < (fileArr.length - 1) - i) {
                int i3 = i2 + 1;
                if (Long.parseLong(fileArr[i2].getName()) < Long.parseLong(fileArr[i3].getName())) {
                    File file = fileArr[i2];
                    fileArr[i2] = fileArr[i3];
                    fileArr[i3] = file;
                }
                i2 = i3;
            }
        }
    }

    private static void a(Context context, f fVar) throws InterruptedException {
        File file = new File(a(context));
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        a(fileArrListFiles);
        for (File file2 : fileArrListFiles) {
            if (!fVar.find()) {
                for (File file3 : file2.listFiles()) {
                    if (Long.parseLong(file3.getName()) > System.currentTimeMillis()) {
                        if (!fVar.find()) {
                            for (File file4 : file3.listFiles()) {
                                fVar.operate(file4);
                                if (fVar.find()) {
                                    break;
                                }
                            }
                        }
                    } else {
                        v0.a(file3);
                    }
                }
            } else {
                v0.a(file2);
            }
        }
    }

    public static e b(Context context, String str) {
        return a(context, str, new e());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, File file, e eVar) throws IOException {
        JSONObject jSONObject;
        JSONObject jSONObjectOptJSONObject;
        try {
            String str = new String(v0.e(file.getAbsolutePath() + "/data.json"));
            if (!TextUtils.isEmpty(str) && (jSONObjectOptJSONObject = (jSONObject = new JSONObject(str)).optJSONObject("data")) != null) {
                eVar.c = jSONObject.optString("provider");
                eVar.d = jSONObject;
                eVar.i = jSONObject.optInt("es", 0);
                eVar.j = jSONObject.optInt("ec", 0);
                String strOptString = jSONObjectOptJSONObject.optString("src");
                eVar.g = strOptString;
                eVar.l = jSONObjectOptJSONObject.optString("tid", "");
                boolean zEndsWith = strOptString.toLowerCase(Locale.ENGLISH).endsWith(".gif");
                StringBuilder sb = new StringBuilder();
                sb.append(file.getAbsolutePath());
                sb.append("/");
                sb.append(zEndsWith ? "img.gif" : "img.png");
                String string = sb.toString();
                String str2 = file.getAbsolutePath() + "/s.txt";
                if (!new File(string).exists() || new File(str2).exists()) {
                    return;
                }
                if (zEndsWith) {
                    eVar.e = ReflectUtil.newInstance("pl.droidsonroids.gif.GifDrawable", new Class[]{String.class}, new Object[]{jSONObject.optString("srcPath")});
                } else {
                    Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(string);
                    if (bitmapDecodeFile != null) {
                        eVar.e = bitmapDecodeFile;
                    }
                }
                eVar.f = string;
                new File(str2).createNewFile();
                v0.a((Object) file.getAbsolutePath());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static e a(Context context, String str, e eVar) throws InterruptedException, IOException {
        eVar.h = str;
        a = new LinkedList();
        a(context, new c(eVar, context));
        if (!eVar.a() && a.size() != 0) {
            for (int i = 0; i < a.size(); i++) {
                new File(((File) a.get(i)).getAbsolutePath() + "/s.txt").delete();
                if (i == 0) {
                    b(context, (File) a.get(i), eVar);
                }
            }
        }
        return eVar;
    }

    private static String a(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null) {
            return "/sdcard/Android/data/" + context.getPackageName() + "/cache/ad/";
        }
        return externalCacheDir.getAbsolutePath() + "/ad/";
    }

    static void b(Context context, JSONObject jSONObject, long j, g gVar) throws JSONException, IOException, ParseException {
        String strA = a(context);
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
        Date date = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(jSONObjectOptJSONObject.optString("expires"));
        if (date.getTime() > System.currentTimeMillis()) {
            String strOptString = jSONObjectOptJSONObject.optString("src");
            a(context, jSONObject, jSONObjectOptJSONObject.optString("tid"), strOptString, strA + j + "/" + date.getTime() + "/" + URLEncoder.encode(strOptString, "utf-8").hashCode() + "/", gVar);
            return;
        }
        gVar.b();
    }

    private static void a(Context context, JSONObject jSONObject, String str, String str2, String str3, g gVar) throws JSONException, IOException {
        if (jSONObject != null && jSONObject.has("es") && jSONObject.getInt("es") == 1) {
            new io.dcloud.sdk.base.dcloud.c(context, jSONObject).d();
            return;
        }
        System.currentTimeMillis();
        v0.a(str.getBytes(), 0, str3 + "tid.txt");
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        sb.append(str2.endsWith(".gif") ? "img.gif" : "img.png");
        String string = sb.toString();
        jSONObject.put("srcPath", string);
        v0.a(jSONObject.toString().getBytes(), 0, str3 + "data.json");
        a(new d(jSONObject, str2, string, gVar));
    }

    public static void a(Context context, JSONObject jSONObject, long j, g gVar) {
        try {
            b(context, jSONObject, j, gVar);
        } catch (Exception unused) {
            gVar.b();
        }
    }
}

package io.dcloud.sdk.base.service;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import androidx.core.app.JobIntentService;
import dc.squareup.okhttp3.Call;
import dc.squareup.okhttp3.Callback;
import dc.squareup.okhttp3.OkHttpClient;
import dc.squareup.okhttp3.Request;
import dc.squareup.okhttp3.Response;
import dc.squareup.okhttp3.ResponseBody;
import io.dcloud.p.f;
import io.dcloud.p.f0;
import io.dcloud.p.l1;
import io.dcloud.p.w4;
import io.dcloud.sdk.base.entry.AdData;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DownloadService extends JobIntentService {
    private OkHttpClient a = new OkHttpClient.Builder().build();
    private List b = new ArrayList();
    private final Object c = new Object();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ AdData b;
        final /* synthetic */ int c;

        a(Context context, AdData adData, int i) {
            this.a = context;
            this.b = adData;
            this.c = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            f0.a(this.a, this.b.e(), this.b.j(), "", this.c, this.b.c(), this.b.b(), this.b.d(), null);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private class b implements Callback {
        private AdData a;
        private boolean b = false;

        public b(AdData adData) {
            this.a = adData;
        }

        public String a() {
            AdData adData = this.a;
            return adData != null ? adData.k() : "";
        }

        public Request b() {
            return new Request.Builder().url(this.a.l()).build();
        }

        public boolean c() {
            return this.b;
        }

        @Override // dc.squareup.okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            this.b = true;
            DownloadService.a(DownloadService.this.getApplicationContext(), this.a, 32);
        }

        @Override // dc.squareup.okhttp3.Callback
        public void onResponse(Call call, Response response) throws IllegalAccessException, NoSuchMethodException, IOException, SecurityException, IllegalArgumentException, InvocationTargetException {
            this.b = true;
            String str = DownloadService.this.getApplication().getExternalCacheDir() + f.b + System.currentTimeMillis() + f.a;
            ResponseBody responseBodyBody = response.body();
            if (responseBodyBody != null) {
                l1.a(responseBodyBody.bytes(), 0, str);
                DownloadService.this.a(str, this.a);
            }
            DownloadService.a(DownloadService.this.getApplicationContext(), this.a, 30);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private class c extends BroadcastReceiver {
        private AdData a;
        private Application b;
        private long c = System.currentTimeMillis();

        public c(AdData adData, Application application) {
            this.a = adData;
            this.b = application;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), "android.intent.action.PACKAGE_ADDED")) {
                if (System.currentTimeMillis() - this.c < 60000) {
                    DownloadService.a(DownloadService.this.getApplicationContext(), this.a, 31);
                }
                this.b.unregisterReceiver(this);
            }
        }
    }

    @Override // androidx.core.app.JobIntentService, android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) {
        synchronized (this.c) {
            AdData adData = (AdData) intent.getParcelableExtra("data");
            for (b bVar : this.b) {
                if (bVar.a().equals(adData.k()) && !bVar.c()) {
                    return;
                }
            }
            b bVar2 = new b(adData);
            this.b.add(bVar2);
            a(getApplicationContext(), adData, 29);
            this.a.newCall(bVar2.b()).enqueue(bVar2);
            a();
        }
    }

    private void a() {
        File[] fileArrListFiles;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis() - 604800000;
            File file = new File(getApplication().getExternalCacheDir() + f.b);
            if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null || fileArrListFiles.length <= 0) {
                return;
            }
            for (File file2 : fileArrListFiles) {
                if (file2.lastModified() < jCurrentTimeMillis) {
                    file2.delete();
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, AdData adData) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        getApplication().registerReceiver(new c(adData, getApplication()), intentFilter);
        try {
            Class.forName("io.dcloud.feature.pack.FileUtils").getDeclaredMethod("addFileToSystem", Context.class, String.class, String.class).invoke(null, getApplication(), getApplication().getPackageName() + ".dc.fileprovider", str);
        } catch (Exception unused) {
        }
    }

    public static void a(Context context, AdData adData, int i) {
        w4.a().a(new a(context, adData, i));
    }
}

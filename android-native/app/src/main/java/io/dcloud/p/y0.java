package io.dcloud.p;

import android.content.Context;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class y0 implements Runnable {
    private a a;
    private Context b;
    private String c;
    private String d;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface a {
        void a(y0 y0Var);

        void b(y0 y0Var);
    }

    public void a(Context context, String str, String str2) {
        this.b = context;
        this.c = str;
        this.d = str2;
    }

    public String b() {
        return this.d;
    }

    public String c() {
        return this.c;
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        InputStream inputStreamA = k3.a(this.c, HttpUrlConnectionNetworkFetcher.HTTP_DEFAULT_TIMEOUT, true, new String[1]);
        a aVar = this.a;
        if (aVar != null) {
            if (inputStreamA == null) {
                aVar.b(this);
            } else {
                v0.a(inputStreamA, this.d);
                this.a.a(this);
            }
        }
    }

    public void a(a aVar) {
        this.a = aVar;
    }

    public Context a() {
        return this.b;
    }
}

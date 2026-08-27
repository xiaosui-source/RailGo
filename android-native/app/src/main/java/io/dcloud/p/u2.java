package io.dcloud.p;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import io.dcloud.sdk.base.entry.AdData;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class u2 {
    protected c a;
    private Context b;
    protected String c;
    protected AdData d;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ String b;

        a(int i, String str) {
            this.a = i;
            this.b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            u2.this.a.onError(this.a, this.b);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            u2.this.a.m();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface c {
        void a(int i, String str);

        void k();

        void l();

        void m();

        void onAdClicked();

        void onAdShow();

        void onError(int i, String str);
    }

    public u2(c cVar, Context context, String str) {
        this.a = cVar;
        this.b = context;
        this.c = str;
    }

    public void a(ViewGroup viewGroup) {
        if (viewGroup == null) {
            a(60010, "广告容器不可见");
        } else if (this.d == null) {
            a(60005, "数据解析失败");
        } else {
            new n4(viewGroup.getContext(), this.a, this.d).a(viewGroup);
        }
    }

    public Context b() {
        return this.b;
    }

    protected void a(int i, String str) {
        if (this.a != null) {
            new Handler(Looper.getMainLooper()).post(new a(i, str));
        }
    }

    protected void a() {
        if (this.a != null) {
            new Handler(Looper.getMainLooper()).post(new b());
        }
    }
}

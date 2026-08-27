package com.bun.miitmdid.b;

import android.content.Context;
import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class b {
    private static boolean h;
    private String a = null;
    private InterfaceC0006b b = null;
    private Object c = null;
    private Map<String, String> d = new HashMap();
    private Map<String, String> e = new HashMap();
    private Map<String, String> f = new HashMap();
    private String g = "GET";

    class a extends AsyncTask<Void, Void, c> {
        b a;

        a() {
            this.a = b.this;
        }

        protected native c a(Void... voidArr);

        protected native void a(c cVar);

        @Override // android.os.AsyncTask
        protected native /* bridge */ /* synthetic */ c doInBackground(Void[] voidArr);

        @Override // android.os.AsyncTask
        protected native /* bridge */ /* synthetic */ void onPostExecute(c cVar);
    }

    /* renamed from: com.bun.miitmdid.b.b$b, reason: collision with other inner class name */
    public interface InterfaceC0006b {
        void a(Exception exc, int i, String str);
    }

    private class c {
        private String a;
        private int b;
        private Exception c;

        public c(b bVar, String str, Exception exc, int i) {
            this.a = str;
            this.c = exc;
            this.b = i;
        }

        static native /* synthetic */ Exception a(c cVar);

        static native /* synthetic */ int b(c cVar);

        static native /* synthetic */ String c(c cVar);
    }

    private b(Context context) {
    }

    public static native b a(Context context);

    static native /* synthetic */ String a(b bVar);

    private native void a(HttpURLConnection httpURLConnection);

    private native c b();

    static native /* synthetic */ c b(b bVar);

    private static native void b(String str);

    private native c c();

    static native /* synthetic */ c c(b bVar);

    static native /* synthetic */ InterfaceC0006b d(b bVar);

    private native String d();

    public native b a();

    public native b a(InterfaceC0006b interfaceC0006b);

    public native b a(Object obj);

    public native b a(String str);

    public native b a(String str, String str2);

    public native b a(Map<String, String> map);
}

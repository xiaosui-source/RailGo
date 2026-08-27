package io.dcloud.js.geolocation.system;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class a {
    public static int p = 2;
    public static int q = 0;
    public static int r = 1;
    public static int s = 0;
    public static int t = 1;
    public static int u = 2;
    private Timer b;
    private b c;
    private TimerTask d;
    String e;
    String h;
    IWebview i;
    private Context j;
    LocationManager k;
    public int a = Integer.MAX_VALUE;
    String l = null;
    String m = null;
    IWebview n = null;
    int o = 0;
    io.dcloud.js.geolocation.system.b f = null;
    c g = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.js.geolocation.system.a$a, reason: collision with other inner class name */
    class C0064a extends TimerTask {
        C0064a() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            a aVar = a.this;
            if (aVar.i == null || aVar.h == null || PdrUtil.isEmpty(aVar.l)) {
                return;
            }
            a aVar2 = a.this;
            Deprecated_JSUtil.excCallbackSuccess(aVar2.i, aVar2.h, aVar2.l, true, true);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b extends TimerTask {
        b() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            a aVar = a.this;
            if (aVar.f == null && aVar.g == null) {
                return;
            }
            aVar.a(a.p, "get location fail.", a.u);
        }
    }

    a(Context context, String str) {
        this.e = str;
        this.j = context;
        this.k = (LocationManager) context.getSystemService("location");
        if (this.b == null) {
            this.b = new Timer();
        }
    }

    public void a() {
        c(u);
    }

    boolean b(IWebview iWebview, int i, String str, int i2) {
        this.i = iWebview;
        this.h = str;
        this.a = i2;
        return a(i, r);
    }

    void c(int i) {
        a(-1);
        if (this.o <= 0) {
            if (i == s) {
                io.dcloud.js.geolocation.system.b bVar = this.f;
                if (bVar != null) {
                    bVar.b();
                    this.f = null;
                }
            } else if (i == t) {
                c cVar = this.g;
                if (cVar != null) {
                    cVar.a();
                    this.g = null;
                }
            } else {
                io.dcloud.js.geolocation.system.b bVar2 = this.f;
                if (bVar2 != null) {
                    bVar2.b();
                    this.f = null;
                }
                c cVar2 = this.g;
                if (cVar2 != null) {
                    cVar2.a();
                    this.g = null;
                }
            }
            if (this.b != null) {
                b bVar3 = this.c;
                if (bVar3 != null) {
                    bVar3.cancel();
                    this.c = null;
                }
                TimerTask timerTask = this.d;
                if (timerTask != null) {
                    timerTask.cancel();
                    this.d = null;
                }
            }
            this.l = null;
            this.o = 0;
        }
        Logger.d("GeoListener", "mUseCount=" + this.o);
    }

    private String a(Location location, String str) {
        return StringUtil.format("{latitude:%f,longitude:%f,altitude:%f,accuracy:%f,heading:%f,velocity:%f,altitudeAccuracy:%d,timestamp:new Date('%s'),coordsType:'%s'}", Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()), Double.valueOf(location.getAltitude()), Float.valueOf(location.getAccuracy()), Float.valueOf(location.getBearing()), Float.valueOf(location.getSpeed()), 0, Long.valueOf(location.getTime()), str);
    }

    private void b(int i) {
        if (this.b != null) {
            b bVar = this.c;
            if (bVar != null) {
                bVar.cancel();
            }
            b bVar2 = new b();
            this.c = bVar2;
            this.b.schedule(bVar2, i);
        }
    }

    void a(Location location, int i) {
        String str;
        IWebview iWebview;
        Log.i("geoListener", "successType==" + i);
        String strA = a(location, "wgs84");
        String str2 = this.m;
        if (str2 != null && (iWebview = this.n) != null) {
            Deprecated_JSUtil.excCallbackSuccess(iWebview, str2, strA, true, false);
            c(u);
            this.m = null;
            this.n = null;
        }
        IWebview iWebview2 = this.i;
        if (iWebview2 == null || (str = this.h) == null) {
            return;
        }
        if (this.l == null) {
            Deprecated_JSUtil.excCallbackSuccess(iWebview2, str, strA, true, true);
        }
        this.l = strA;
    }

    void a(int i, String str, int i2) {
        String str2;
        IWebview iWebview;
        Log.i("geoListener", "failType==" + i2);
        c(i2);
        String str3 = this.m;
        if (str3 != null && (iWebview = this.n) != null && this.f == null && this.g == null) {
            Deprecated_JSUtil.excCallbackError(iWebview, str3, DOMException.toJSON(i, str), true);
        }
        IWebview iWebview2 = this.i;
        if (iWebview2 == null || (str2 = this.h) == null || this.f != null || this.g != null) {
            return;
        }
        this.l = null;
        Deprecated_JSUtil.excCallbackError(iWebview2, str2, DOMException.toJSON(i, str), true);
    }

    void a(IWebview iWebview, int i, String str, int i2) {
        this.n = iWebview;
        this.m = str;
        this.a = i2;
        a(i, q);
    }

    private void a(int i) {
        this.o += i;
        Logger.d("GeoListener", "mUseCount=" + this.o);
    }

    private boolean a(int i, int i2) {
        b bVar;
        if (this.o == 0) {
            if (this.f == null && this.k.isProviderEnabled("gps")) {
                this.f = new io.dcloud.js.geolocation.system.b(this.j, this);
            }
            if (this.g == null && this.k.isProviderEnabled("network")) {
                this.g = new c(this.j, this);
            }
            io.dcloud.js.geolocation.system.b bVar2 = this.f;
            if (bVar2 != null) {
                bVar2.a(i);
            }
            c cVar = this.g;
            if (cVar != null) {
                cVar.a(i);
            }
            if (i2 == q) {
                b(this.a);
            }
        }
        if (i2 == r) {
            if (this.b != null && (bVar = this.c) != null) {
                bVar.cancel();
            }
            C0064a c0064a = new C0064a();
            this.d = c0064a;
            long j = i;
            this.b.schedule(c0064a, j, j);
        }
        a(1);
        if (this.g != null || this.f != null) {
            return true;
        }
        a(p, "get location fail.", u);
        return false;
    }
}

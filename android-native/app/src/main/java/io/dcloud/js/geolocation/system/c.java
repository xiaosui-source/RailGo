package io.dcloud.js.geolocation.system;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import io.dcloud.common.adapter.util.Logger;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class c implements LocationListener {
    private Context a;
    private LocationManager b;
    private a c;
    private boolean d = false;
    private Location e;
    private boolean f;

    public c(Context context, a aVar) {
        this.f = false;
        this.c = aVar;
        this.a = context;
        this.b = (LocationManager) context.getSystemService("location");
        this.f = false;
    }

    public void a(int i) {
        if (this.f) {
            return;
        }
        this.f = true;
        this.b.requestLocationUpdates("network", i, 0.0f, this);
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        Logger.d("NetworkListener: The location has been updated!");
        this.d = true;
        this.e = location;
        this.c.a(location, a.t);
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String str) {
        b bVar;
        this.f = false;
        if (!this.d && ((bVar = this.c.f) == null || !bVar.a())) {
            this.c.a(a.p, "The provider " + str + " is disabled", a.t);
        }
        Logger.d("NetworkListener: The provider " + str + " is disabled");
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String str) {
        Logger.d("NetworkListener: The provider " + str + " is enabled");
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
        Logger.d("NetworkListener: The status of the provider " + str + " has changed");
        if (i == 0) {
            Logger.d("NetworkListener: " + str + " is OUT OF SERVICE");
            return;
        }
        if (i == 1) {
            Logger.d("NetworkListener: " + str + " is TEMPORARILY_UNAVAILABLE");
            return;
        }
        Logger.d("NetworkListener: " + str + " is Available");
    }

    public void a() {
        if (this.f) {
            this.b.removeUpdates(this);
        }
        this.f = false;
    }
}

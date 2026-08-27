package io.dcloud.p;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import io.dcloud.sdk.poly.base.utils.PrivacyManager;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class a3 {
    private static JSONObject a = new JSONObject();
    private static boolean b = false;
    private static Map c = new HashMap();
    private static boolean d = false;

    public static JSONObject a(Context context) throws JSONException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        if (context == null) {
            return a;
        }
        if (ContextCompat.checkSelfPermission(context, Permission.ACCESS_FINE_LOCATION) != 0 && ContextCompat.checkSelfPermission(context, Permission.ACCESS_COARSE_LOCATION) != 0) {
            return a;
        }
        if (!PrivacyManager.getInstance().d()) {
            return a;
        }
        if (!b0.a().a(context)) {
            return a;
        }
        if (b) {
            return a;
        }
        JSONObject jSONObject = new JSONObject();
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager != null) {
            try {
                Method declaredMethod = locationManager.getClass().getDeclaredMethod("getLastKnownLocation", String.class);
                declaredMethod.setAccessible(true);
                Location location = (Location) declaredMethod.invoke(locationManager, "gps");
                if (location == null && (location = (Location) declaredMethod.invoke(locationManager, "network")) == null) {
                    location = (Location) declaredMethod.invoke(locationManager, "passive");
                }
                if (location != null) {
                    Class<?> cls = Class.forName("android.location.Location");
                    Method method = cls.getMethod("getLongitude", null);
                    Method method2 = cls.getMethod("getLatitude", null);
                    Method method3 = cls.getMethod("getAccuracy", null);
                    Method method4 = cls.getMethod("getTime", null);
                    method.setAccessible(true);
                    jSONObject.put("lon", String.valueOf(method.invoke(location, null)));
                    method2.setAccessible(true);
                    jSONObject.put("lat", String.valueOf(method2.invoke(location, null)));
                    method3.setAccessible(true);
                    jSONObject.put("accuracy", String.valueOf(method3.invoke(location, null)));
                    method4.setAccessible(true);
                    jSONObject.put("ts", String.valueOf(method4.invoke(location, null)));
                }
            } catch (Exception unused) {
            }
        }
        a = jSONObject;
        b = true;
        return jSONObject;
    }

    public static Map a(Context context, String str) {
        int i;
        int baseStationId;
        int networkId;
        if (context == null) {
            return c;
        }
        if (ContextCompat.checkSelfPermission(context, Permission.ACCESS_FINE_LOCATION) != 0) {
            return c;
        }
        if (!PrivacyManager.getInstance().d()) {
            return c;
        }
        if (!b0.a().a(context)) {
            return c;
        }
        if (d) {
            return c;
        }
        if (TextUtils.isEmpty(str) || str.equals("10")) {
            i = 0;
            baseStationId = 0;
            networkId = 0;
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (!"03".equals(str) && !"05".equals(str)) {
                if ("01".equals(str) || "06".equals(str)) {
                    i = 3;
                } else {
                    i = ("00".equals(str) || "02".equals(str) || "07".equals(str)) ? 1 : 4;
                }
                GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
                networkId = gsmCellLocation.getLac();
                baseStationId = gsmCellLocation.getCid();
            } else {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
                networkId = cdmaCellLocation.getNetworkId();
                baseStationId = cdmaCellLocation.getBaseStationId() / 16;
                i = 2;
            }
        }
        HashMap map = new HashMap();
        map.put("carrier", Integer.valueOf(i));
        map.put("cid", Integer.valueOf(baseStationId));
        map.put("lac", Integer.valueOf(networkId));
        c = map;
        d = true;
        return map;
    }
}

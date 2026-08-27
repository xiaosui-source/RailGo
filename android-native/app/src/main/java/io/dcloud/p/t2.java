package io.dcloud.p;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.sdk.poly.base.utils.PrivacyManager;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class t2 {
    private static String a = "";
    private static boolean b = false;

    public static String a(Context context) {
        if (context == null) {
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
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        String strA = connectivityManager.getNetworkInfo(0).isConnected() ? a() : connectivityManager.getNetworkInfo(1).isConnected() ? a(((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getIpAddress()) : null;
        a = strA;
        b = true;
        return strA;
    }

    public static String a() {
        try {
            ArrayList list = Collections.list(NetworkInterface.getNetworkInterfaces());
            int size = list.size();
            int i = 0;
            while (i < size) {
                Object obj = list.get(i);
                i++;
                ArrayList list2 = Collections.list(((NetworkInterface) obj).getInetAddresses());
                int size2 = list2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = list2.get(i2);
                    i2++;
                    InetAddress inetAddress = (InetAddress) obj2;
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
            return null;
        } catch (SocketException unused) {
            return null;
        }
    }

    public static String a(int i) {
        return (i & 255) + Operators.DOT_STR + ((i >> 8) & 255) + Operators.DOT_STR + ((i >> 16) & 255) + Operators.DOT_STR + ((i >> 24) & 255);
    }
}

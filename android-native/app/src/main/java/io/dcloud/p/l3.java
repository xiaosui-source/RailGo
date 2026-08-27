package io.dcloud.p;

import android.content.Context;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class l3 {
    private static l3 b;
    private String a = "";

    private l3() {
    }

    public static l3 a() {
        if (b == null) {
            synchronized (l3.class) {
                if (b == null) {
                    b = new l3();
                }
            }
        }
        return b;
    }

    public String a(Context context) {
        return this.a;
    }
}

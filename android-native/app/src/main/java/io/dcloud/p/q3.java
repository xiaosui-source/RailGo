package io.dcloud.p;

import io.dcloud.common.DHInterface.IPdrModule;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class q3 {
    private static q3 b;
    private volatile ConcurrentMap a = new ConcurrentHashMap();

    private q3() {
    }

    public static q3 a() {
        if (b == null) {
            b = new q3();
        }
        return b;
    }

    public void b() {
        Iterator it = this.a.keySet().iterator();
        while (it.hasNext()) {
            IPdrModule iPdrModule = (IPdrModule) this.a.remove((String) it.next());
            if (iPdrModule != null) {
                iPdrModule.onDestroy();
            }
        }
    }

    public void a(Map map) {
        if (map != null) {
            for (String str : map.keySet()) {
                a(str, (Class) map.get(str));
            }
        }
    }

    public void a(String str, Class cls) {
        if (cls != null) {
            try {
                this.a.put(str, (IPdrModule) cls.newInstance());
            } catch (IllegalAccessException | InstantiationException unused) {
            }
        }
    }

    public IPdrModule a(String str) {
        if (this.a.containsKey(str)) {
            return (IPdrModule) this.a.get(str);
        }
        return null;
    }
}

package io.dcloud.p;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.taobao.weex.el.parse.Operators;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class p1 {
    private static p1 c;
    private final String a = "UNIAPP_HostPicker_0817";
    private final String b = "SP_LAST_SUIT_HOST_NAME_0817";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class a implements Comparable, Cloneable {
        String a;
        EnumC0068a b;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.p.p1$a$a, reason: collision with other inner class name */
        public enum EnumC0068a {
            NORMAL(0),
            FIRST(1),
            BACKUP(-1);

            int a;

            EnumC0068a(int i) {
                this.a = i;
            }
        }

        public a(String str, EnumC0068a enumC0068a) {
            EnumC0068a enumC0068a2 = EnumC0068a.NORMAL;
            this.a = str;
            this.b = enumC0068a;
        }

        public String a() {
            String str = "";
            if (TextUtils.isEmpty(this.a)) {
                return "";
            }
            try {
                str = new String(Base64.decode(this.a.getBytes("UTF-8"), 2), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return r4.d(str);
        }

        public boolean b() {
            return !TextUtils.isEmpty(this.a);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (TextUtils.isEmpty(aVar.a)) {
                return false;
            }
            return aVar.a.equals(this.a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            return "Host{hostUrl='" + this.a + "', priority=" + this.b + Operators.BLOCK_END;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public a m488clone() {
            return new a(this.a, this.b);
        }

        @Override // java.lang.Comparable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compareTo(a aVar) {
            if (aVar == null) {
                return 1;
            }
            return aVar.b.a - this.b.a;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface b {
        void a(a aVar);

        boolean b(a aVar);

        void onNoOnePicked();
    }

    private p1() {
    }

    private void a(Context context, List list, String str) {
        String str2 = "SP_LAST_SUIT_HOST_NAME_0817" + str;
        SharedPreferences sharedPreferences = context.getSharedPreferences("UNIAPP_HostPicker_0817", 0);
        String string = sharedPreferences.getString(str2, "");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (!aVar.b()) {
                throw new RuntimeException("error format host");
            }
            if (!TextUtils.isEmpty(string)) {
                if (string.equals(aVar.a)) {
                    aVar.b = a.EnumC0068a.FIRST;
                } else {
                    aVar.b = a.EnumC0068a.NORMAL;
                }
            }
        }
        sharedPreferences.edit().remove(str2).apply();
    }

    public static p1 a() {
        if (c == null) {
            synchronized (p1.class) {
                if (c == null) {
                    c = new p1();
                }
            }
        }
        return c;
    }

    public void a(Context context, List list, String str, b bVar) {
        if (list != null && !list.isEmpty()) {
            a(context, list, str);
            Collections.sort(list);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                if (bVar.b(aVar)) {
                    if (aVar.b != a.EnumC0068a.BACKUP) {
                        context.getSharedPreferences("UNIAPP_HostPicker_0817", 0).edit().putString("SP_LAST_SUIT_HOST_NAME_0817" + str, aVar.a).apply();
                    }
                    bVar.a(aVar);
                    return;
                }
            }
            bVar.onNoOnePicked();
            return;
        }
        throw new RuntimeException("call initHosts first");
    }
}

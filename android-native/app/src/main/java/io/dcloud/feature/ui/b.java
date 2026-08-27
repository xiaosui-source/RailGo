package io.dcloud.feature.ui;

import android.content.Context;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.ViewRect;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.JsEventUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class b {
    public static byte n = ViewRect.POSITION_STATIC;
    public static byte o = ViewRect.POSITION_ABSOLUTE;
    public static byte p = ViewRect.POSITION_DOCK;
    public static byte q = ViewRect.DOCK_LEFT;
    public static byte r = ViewRect.DOCK_RIGHT;
    public static byte s = ViewRect.DOCK_TOP;
    public static byte t = ViewRect.DOCK_BOTTOM;
    protected HashMap b;
    protected String d;
    private Context l;
    protected c a = null;
    protected a c = null;
    protected String e = null;
    protected String f = null;
    protected String g = null;
    protected JSONObject h = null;
    protected IWebview i = null;
    private byte j = o;
    private byte k = s;
    protected HashMap m = null;

    public b(String str) {
        this.b = null;
        this.d = str;
        this.b = new HashMap();
    }

    public abstract String a(IWebview iWebview, String str, JSONArray jSONArray);

    public abstract void a(int i, int i2, int i3, int i4, int i5, int i6);

    protected void a(Context context, a aVar, IWebview iWebview, String str, JSONObject jSONObject) {
        this.c = aVar;
        this.l = context;
        this.i = iWebview;
        this.e = str;
        if (jSONObject == null) {
            jSONObject = JSONUtil.createJSONObject("{}");
        }
        this.h = jSONObject;
        f();
    }

    public final byte b() {
        return this.k;
    }

    public final byte c() {
        return this.j;
    }

    public abstract AdaFrameItem d();

    protected abstract void e();

    protected void f() {
        JSONObject jSONObject = this.h;
        if (!JSONUtil.isNull(jSONObject, "id") && PdrUtil.isEmpty(this.f)) {
            this.f = JSONUtil.getString(jSONObject, "id");
        }
        this.g = jSONObject.optString("tid");
        String string = JSONUtil.getString(jSONObject, "position");
        if (!PdrUtil.isEmpty(string)) {
            if (AbsoluteConst.JSON_VALUE_POSITION_ABSOLUTE.equals(string)) {
                this.j = o;
            } else if ("dock".equals(string)) {
                this.j = p;
            } else if ("static".equals(string)) {
                this.j = n;
            }
        }
        String string2 = JSONUtil.getString(jSONObject, "dock");
        if (PdrUtil.isEmpty(string2)) {
            return;
        }
        if ("bottom".equals(string2)) {
            this.k = t;
            return;
        }
        if ("top".equals(string2)) {
            this.k = s;
        } else if ("left".equals(string2)) {
            this.k = q;
        } else if ("right".equals(string2)) {
            this.k = r;
        }
    }

    protected void g() {
        Iterator it = this.c.c.iterator();
        while (it.hasNext()) {
            a(this, ((c) it.next()).m);
        }
    }

    public String h() {
        return StringUtil.format("(function(){return {uuid:'%s',identity:'%s',option:%s}})()", this.e, this.d, this.h);
    }

    protected void b(String str, String str2) {
        ArrayList arrayList;
        HashMap map = this.m;
        if (map == null || (arrayList = (ArrayList) map.get(str2)) == null) {
            return;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            String[] strArr = (String[]) obj;
            if (strArr[0].equals(str)) {
                arrayList.remove(strArr);
            }
        }
        if (arrayList.size() == 0) {
            this.m.remove(str2);
        }
    }

    public final Context a() {
        return this.l;
    }

    protected void a(String str, String str2, String str3) {
        if (this.m == null) {
            this.m = new HashMap(2);
        }
        ArrayList arrayList = (ArrayList) this.m.get(str2);
        if (arrayList == null) {
            arrayList = new ArrayList(2);
            this.m.put(str2, arrayList);
        }
        arrayList.add(new String[]{str, str3});
    }

    private static void a(b bVar, HashMap map) {
        if (map != null) {
            for (ArrayList arrayList : map.values()) {
                if (arrayList != null) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        String str = ((String[]) arrayList.get(size))[0];
                        if (bVar.c.a(str, str, (String) null) == bVar) {
                            arrayList.remove(size);
                        }
                    }
                }
            }
        }
    }

    protected boolean a(String str) {
        ArrayList arrayList;
        HashMap map = this.m;
        if (map == null || (arrayList = (ArrayList) map.get(str)) == null) {
            return false;
        }
        return !arrayList.isEmpty();
    }

    public final boolean a(String str, String str2) {
        return a(str, str2, true);
    }

    public boolean a(String str, String str2, boolean z) {
        ArrayList arrayList;
        IWebview iWebviewObtainWebView;
        Logger.d("execCallback pEventType=" + str + ";");
        HashMap map = this.m;
        if (map == null || (arrayList = (ArrayList) map.get(str)) == null) {
            return false;
        }
        int size = arrayList.size();
        String strEventListener_format = JsEventUtil.eventListener_format(str, str2, z);
        boolean z2 = false;
        for (int i = size - 1; i >= 0; i--) {
            String[] strArr = (String[]) arrayList.get(i);
            String str3 = strArr[0];
            String str4 = strArr[1];
            c cVarA = this.c.a(str3, str3, (String) null);
            if (cVarA != null && !cVarA.J && (iWebviewObtainWebView = cVarA.y.obtainWebView()) != null) {
                Deprecated_JSUtil.execCallback(iWebviewObtainWebView, str4, strEventListener_format, JSUtil.OK, true, true);
                z2 = true;
            }
        }
        return z2;
    }
}

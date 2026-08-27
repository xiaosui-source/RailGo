package io.dcloud.p;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.component.WXComponent;
import io.dcloud.p.s1;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.util.Const;
import io.dcloud.sdk.poly.api.Platform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class b0 implements s1.a {
    private static volatile b0 l;
    private JSONObject d;
    private Context g;
    private final AtomicBoolean a = new AtomicBoolean(false);
    private final Queue b = new ConcurrentLinkedQueue();
    private final Map c = new HashMap();
    private final Map e = new HashMap();
    private String f = "";
    private final String h = "uniad_config";
    private final String i = "S_C";
    private final String j = "pap";
    private final String k = "dpap";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static abstract class a {
        String a;

        public a(String str) {
            this.a = str;
        }

        public abstract void a(int i, String str);

        public abstract void a(JSONObject jSONObject);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static abstract class b extends a {
        private boolean b;

        public b(String str) {
            super(str);
            this.b = false;
        }

        public abstract void a(JSONArray jSONArray);

        public abstract void a(JSONArray jSONArray, boolean z);

        @Override // io.dcloud.p.b0.a
        public void a(JSONObject jSONObject) {
            this.b = true;
        }

        @Override // io.dcloud.p.b0.a
        public void a(int i, String str) {
            this.b = true;
        }

        public boolean a() {
            return this.b;
        }
    }

    private b0() {
    }

    public static b0 a() {
        if (l == null) {
            synchronized (b0.class) {
                if (l == null) {
                    l = new b0();
                    return l;
                }
            }
        }
        return l;
    }

    private JSONObject b(Context context) throws JSONException {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            Set<String> setKeySet = bundle.keySet();
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            for (String str : setKeySet) {
                if (str.startsWith("UNIAD")) {
                    if (str.endsWith("APPID")) {
                        a(bundle, str, jSONObject);
                    } else if (str.endsWith("SPLASH")) {
                        a(bundle, str, jSONObject3);
                    } else if (str.endsWith("APPKEY")) {
                        a(bundle, str, jSONObject2);
                    } else if (str.endsWith("ADPID")) {
                        a(bundle, str, jSONObject3);
                    }
                }
            }
            if (bundle.containsKey("com.google.android.gms.ads.APPLICATION_ID")) {
                jSONObject.put(Const.TYPE_GG, bundle.getString("com.google.android.gms.ads.APPLICATION_ID"));
            }
            if (c(context)) {
                jSONObject3 = new JSONObject();
            }
            if (jSONObject3.length() > 0) {
                Iterator<String> itKeys = jSONObject3.keys();
                StringBuilder sb = new StringBuilder();
                sb.append(itKeys.next());
                while (itKeys.hasNext()) {
                    sb.append(",");
                    sb.append(itKeys.next());
                }
                jSONObject3.put("_psp_", sb.toString());
                jSONObject3.put("_adpid_", jSONObject3.optString("adpid"));
                jSONObject3.put("_fs_", bundle.getString("UNIAD_FULL_SPLASH", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT));
            }
            p0 p0Var = new p0();
            p0Var.put("697878616C", jSONObject);
            p0Var.put("697878436D71", jSONObject2);
            p0Var.put("7B7864697B60", jSONObject3);
            return b(p0Var);
        } catch (Exception unused) {
            return null;
        }
    }

    private boolean c(Context context) {
        String str = r0.d().b().getAppId() + "/www/androidPrivacy.json";
        if (a(context, "apps/" + str)) {
            return true;
        }
        context.getFilesDir();
        String str2 = Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + context.getPackageName() + File.separatorChar;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str);
        return new File(sb.toString()).exists() || new File(context.getFilesDir(), str).exists() || new File(context.getFilesDir().getParentFile(), str).exists();
    }

    public void a(Context context, int i, a aVar) throws JSONException {
        JSONObject jSONObject;
        p0 p0Var;
        if (TextUtils.isEmpty(aVar.a) && i != 1 && i != 3) {
            aVar.a(-5001, AOLErrorUtil.getErrorMsg(-5001));
            return;
        }
        if (i == 3) {
            JSONObject jSONObject2 = this.d;
            if (jSONObject2 != null && jSONObject2.has("frsplash")) {
                aVar.a(this.d.optJSONObject("frsplash"));
                return;
            } else {
                aVar.a(-5015, AOLErrorUtil.getErrorMsg(-5015));
                return;
            }
        }
        if (i == 1) {
            JSONObject jSONObject3 = this.d;
            if (jSONObject3 != null) {
                if (!jSONObject3.has("splash")) {
                    aVar.a(-5019, AOLErrorUtil.getErrorMsg(-5019));
                    return;
                } else if (m.d(context)) {
                    aVar.a(this.d.optJSONObject("splash"));
                    this.b.add(aVar);
                    return;
                } else {
                    aVar.a(this.d.optJSONObject("splash"));
                    this.b.add(aVar);
                    return;
                }
            }
            String strA = d4.a(context, "uniad_config", "S_C");
            if (TextUtils.isEmpty(strA)) {
                JSONObject jSONObjectB = b(context);
                if (jSONObjectB == null) {
                    aVar.a(-5000, "");
                } else {
                    aVar.a(jSONObjectB);
                }
            } else {
                try {
                    p0Var = new p0(r4.c(strA));
                } catch (JSONException unused) {
                    p0Var = null;
                }
                if (p0Var != null && p0Var.length() > 0) {
                    if (!p0Var.has("7B78") && !p0Var.has("697878436D71")) {
                        a(p0Var.b("697878616C"));
                        if (m.d(context)) {
                            JSONObject jSONObjectB2 = p0Var.b("7B7864697B60");
                            if (jSONObjectB2 != null) {
                                aVar.a(jSONObjectB2);
                            }
                        } else {
                            JSONObject jSONObjectB3 = p0Var.b("7B7864697B60");
                            if (jSONObjectB3 != null) {
                                aVar.a(jSONObjectB3);
                            }
                        }
                    } else {
                        JSONObject jSONObjectB4 = b(p0Var);
                        if (jSONObjectB4 != null) {
                            aVar.a(jSONObjectB4);
                        }
                    }
                }
            }
            this.b.add(aVar);
            if (this.a.get()) {
                return;
            }
            a(context, i);
            return;
        }
        if (this.e.size() > 0 && (jSONObject = this.d) != null && jSONObject.length() > 0) {
            if (!TextUtils.isEmpty(aVar.a)) {
                if (this.d.has(aVar.a)) {
                    aVar.a(this.d.optJSONObject(aVar.a));
                    return;
                }
                aVar.a(-5002, AOLErrorUtil.getErrorMsg(-5002) + aVar.a);
                return;
            }
            aVar.a(-5001, AOLErrorUtil.getErrorMsg(-5001));
            return;
        }
        this.b.add(aVar);
        if (this.a.get()) {
            return;
        }
        a(context, i);
    }

    private JSONObject b(p0 p0Var) throws JSONException {
        a0 a0Var;
        JSONObject jSONObjectA = p0Var.a("appid");
        JSONObject jSONObjectA2 = p0Var.a("appKey");
        JSONObject jSONObjectA3 = p0Var.a("splash");
        JSONObject jSONObjectA4 = p0Var.a("sp");
        HashMap map = new HashMap();
        if (jSONObjectA != null) {
            Iterator<String> itKeys = jSONObjectA.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                a0 a0Var2 = new a0();
                a0Var2.c(next);
                a0Var2.a(jSONObjectA.optString(next));
                if (jSONObjectA2 != null && jSONObjectA2.has(next)) {
                    a0Var2.b(jSONObjectA.optString(next));
                }
                map.put(next, a0Var2);
            }
        }
        if (jSONObjectA4 != null) {
            Iterator<String> itKeys2 = jSONObjectA4.keys();
            while (itKeys2.hasNext()) {
                String next2 = itKeys2.next();
                JSONObject jSONObjectOptJSONObject = jSONObjectA4.optJSONObject(next2);
                if (jSONObjectOptJSONObject != null) {
                    if (map.containsKey(next2)) {
                        a0Var = (a0) map.get(next2);
                    } else {
                        a0Var = new a0();
                        a0Var.c(next2);
                    }
                    Platform platform = new Platform();
                    platform.setType(next2);
                    platform.setPlatJson(jSONObjectOptJSONObject);
                    a0Var.a(platform);
                    map.put(next2, a0Var);
                }
            }
        }
        this.e.putAll(map);
        JSONObject jSONObject = null;
        if (jSONObjectA3 != null) {
            String strOptString = jSONObjectA3.optString("_psp_");
            if (TextUtils.isEmpty(strOptString)) {
                return null;
            }
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("adpid", jSONObjectA3.optString("_adpid_"));
                jSONObject2.put("type", 1);
                jSONObject2.put("cpadpid", jSONObjectA3.optString("cp_adp_id"));
                jSONObject2.put("fs", jSONObjectA3.optString("_fs_"));
                jSONObject2.put("fwt", jSONObjectA3.optString("_fwt_"));
                jSONObject2.put("ord", jSONObjectA3.optString("_ord_"));
                jSONObject2.put("sr", jSONObjectA3.optString("_sr_"));
                JSONObject jSONObjectOptJSONObject2 = jSONObjectA3.optJSONObject("_w_");
                if (jSONObjectOptJSONObject2 == null) {
                    jSONObjectOptJSONObject2 = new JSONObject();
                }
                JSONObject jSONObjectOptJSONObject3 = jSONObjectA3.optJSONObject("_m_");
                if (jSONObjectOptJSONObject3 == null) {
                    jSONObjectOptJSONObject3 = new JSONObject();
                }
                String[] strArrSplit = TextUtils.split(strOptString, ",");
                JSONArray jSONArray = new JSONArray();
                for (String str : strArrSplit) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put(ContextChain.TAG_PRODUCT, str);
                    jSONObject3.put("sid", jSONObjectA3.optString(str));
                    if (jSONObjectOptJSONObject2.has(str)) {
                        jSONObject3.put(WXComponent.PROP_FS_WRAP_CONTENT, jSONObjectOptJSONObject2.optString(str));
                    }
                    if (jSONObjectOptJSONObject3.has(str)) {
                        jSONObject3.put(WXComponent.PROP_FS_MATCH_PARENT, jSONObjectOptJSONObject3.optString(str));
                    }
                    jSONArray.put(jSONObject3);
                }
                jSONObject2.put("cfgs", jSONArray);
                return jSONObject2;
            } catch (JSONException e) {
                e.printStackTrace();
                jSONObject = jSONObject2;
            }
        }
        return jSONObject;
    }

    private void a(Context context, int i) {
        this.g = context.getApplicationContext();
        this.a.set(true);
        r0.d().a().a(context, i, this);
    }

    @Override // io.dcloud.p.s1.a
    public void a(p0 p0Var) {
        int i = 0;
        this.a.set(false);
        if (p0Var.has("786978")) {
            m.b(this.g, p0Var.optString("786978", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT));
        }
        if (p0Var.has("6F65")) {
            m.c(this.g, p0Var.optString("6F65", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT));
        }
        if (p0Var.has("6C786978")) {
            String strOptString = p0Var.optString("6C786978", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
            this.f = strOptString;
            d4.a(this.g, "uniad_config", "dpap", strOptString);
        }
        p0 p0VarOptJSONObject = p0Var.optJSONObject("6C697C69");
        if (p0VarOptJSONObject != null && p0VarOptJSONObject.has("7D6661696C")) {
            p0 p0VarOptJSONObject2 = p0VarOptJSONObject.optJSONObject("7D6661696C");
            if (p0VarOptJSONObject2 != null && p0VarOptJSONObject2.length() > 0) {
                JSONObject jSONObjectB = p0VarOptJSONObject2.has("6B606966666D64") ? p0VarOptJSONObject2.b("6B606966666D64") : new JSONObject();
                this.e.clear();
                a(jSONObjectB);
                JSONArray jSONArrayOptJSONArray = p0VarOptJSONObject2.has("787A") ? p0VarOptJSONObject2.optJSONArray("787A") : null;
                JSONObject jSONObjectB2 = p0VarOptJSONObject2.b("696C78616C7B");
                this.d = jSONObjectB2;
                JSONObject jSONObjectOptJSONObject = jSONObjectB2 != null ? jSONObjectB2.optJSONObject("splash") : null;
                p0 p0Var2 = new p0();
                try {
                    p0Var2.put("697878616C", jSONObjectB);
                    p0Var2.put("7B7864697B60", jSONObjectOptJSONObject);
                } catch (JSONException unused) {
                }
                a(p0Var2.toString(), this.g);
                JSONArray jSONArrayOptJSONArray2 = p0Var.optJSONArray("787B697B");
                ArrayList arrayList = new ArrayList();
                JSONObject jSONObject = this.d;
                if (jSONObject != null && jSONObject.length() > 0) {
                    Iterator it = this.b.iterator();
                    while (it.hasNext()) {
                        a aVar = (a) it.next();
                        if (aVar instanceof b) {
                            arrayList.add((b) aVar);
                        } else if (this.d.has(aVar.a)) {
                            aVar.a(this.d.optJSONObject(aVar.a));
                        } else {
                            aVar.a(-5002, AOLErrorUtil.getErrorMsg(-5002) + aVar.a);
                        }
                        it.remove();
                    }
                } else {
                    Iterator it2 = this.b.iterator();
                    while (it2.hasNext()) {
                        a aVar2 = (a) it2.next();
                        if (aVar2 instanceof b) {
                            arrayList.add((b) aVar2);
                        } else {
                            aVar2.a(-5007, AOLErrorUtil.getErrorMsg(-5007));
                        }
                        it2.remove();
                    }
                }
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    b bVar = (b) obj;
                    if (!bVar.b) {
                        if (m.d(this.g)) {
                            if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.length() > 0) {
                                bVar.a(jSONObjectOptJSONObject);
                            } else {
                                bVar.a(-5007, AOLErrorUtil.getErrorMsg(-5007));
                            }
                        } else {
                            bVar.a(jSONObjectOptJSONObject);
                        }
                    }
                    bVar.a(jSONArrayOptJSONArray2, true);
                    bVar.a(jSONArrayOptJSONArray);
                }
                return;
            }
            Iterator it3 = this.b.iterator();
            while (it3.hasNext()) {
                a aVar3 = (a) it3.next();
                if (aVar3 instanceof b) {
                    b bVar2 = (b) aVar3;
                    if (!bVar2.a()) {
                        aVar3.a(-5007, AOLErrorUtil.getErrorMsg(-5007));
                    }
                    bVar2.a((JSONArray) null, false);
                } else {
                    aVar3.a(-5007, AOLErrorUtil.getErrorMsg(-5007));
                }
                it3.remove();
            }
            return;
        }
        Iterator it4 = this.b.iterator();
        while (it4.hasNext()) {
            a aVar4 = (a) it4.next();
            if (aVar4 instanceof b) {
                b bVar3 = (b) aVar4;
                if (!bVar3.a()) {
                    aVar4.a(-5007, AOLErrorUtil.getErrorMsg(-5007));
                }
                bVar3.a((JSONArray) null, false);
            } else {
                aVar4.a(-5007, AOLErrorUtil.getErrorMsg(-5007));
            }
            it4.remove();
        }
    }

    private void a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(next);
            a0 a0Var = new a0();
            a0Var.c(next);
            if (a0Var.a(jSONObjectOptJSONObject)) {
                this.e.put(next, a0Var);
            } else {
                this.e.remove(next);
            }
        }
    }

    @Override // io.dcloud.p.s1.a
    public void a(int i, String str) throws NumberFormatException {
        int i2;
        String str2;
        String str3;
        this.d = new JSONObject();
        d4.a(this.g, "uniad_config", "S_C", "");
        this.a.set(false);
        try {
            i2 = Integer.parseInt(str);
        } catch (Exception unused) {
            i2 = -1;
        }
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (aVar instanceof b) {
                b bVar = (b) aVar;
                if (!bVar.a()) {
                    if (i2 != -1) {
                        str3 = "http:" + str;
                    } else {
                        str3 = str;
                    }
                    aVar.a(-5007, str3);
                }
                bVar.a((JSONArray) null, false);
            } else {
                if (i2 != -1) {
                    str2 = "http:" + str;
                } else {
                    str2 = str;
                }
                aVar.a(-5007, str2);
            }
            it.remove();
        }
    }

    private void a(String str, Context context) {
        if (context != null) {
            d4.a(context, "uniad_config", "S_C", r4.e(str));
        }
    }

    private boolean a(Context context, String str) throws IOException {
        try {
            context.getAssets().open(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private void a(Bundle bundle, String str, JSONObject jSONObject) throws JSONException {
        String string = bundle.getString(str);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        String[] strArrSplit = string.split("_");
        if (strArrSplit.length <= 1 || TextUtils.isEmpty(strArrSplit[0]) || TextUtils.isEmpty(strArrSplit[1])) {
            return;
        }
        try {
            jSONObject.put(strArrSplit[0], strArrSplit[1]);
        } catch (JSONException unused) {
        }
    }

    public a0 a(String str) {
        if (Const.TYPE_GM.equalsIgnoreCase(str) && !this.e.containsKey(str)) {
            str = Const.TYPE_CSJ;
        }
        if (this.e.size() == 0 || TextUtils.isEmpty(str) || !this.e.containsKey(str)) {
            return null;
        }
        return (a0) this.e.get(str);
    }

    public boolean a(Context context) {
        if (TextUtils.isEmpty(this.f)) {
            this.f = d4.a(context, "uniad_config", "dpap");
        }
        return "1".equals(this.f);
    }
}

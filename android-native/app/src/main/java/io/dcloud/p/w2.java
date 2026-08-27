package io.dcloud.p;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.message.action.PermissionRequestAction;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.ui.AdaUniWebView;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.core.permission.PermissionControler;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class w2 implements IEventCallback {
    static HashMap b = new HashMap(2);
    static w2 c = null;
    ArrayList a = new ArrayList();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends PermissionUtil.Request {
        final /* synthetic */ JSONArray a;
        final /* synthetic */ IWebview b;
        final /* synthetic */ String[] c;
        final /* synthetic */ JSONArray d;
        final /* synthetic */ JSONArray e;
        final /* synthetic */ JSONArray f;

        a(JSONArray jSONArray, IWebview iWebview, String[] strArr, JSONArray jSONArray2, JSONArray jSONArray3, JSONArray jSONArray4) {
            this.a = jSONArray;
            this.b = iWebview;
            this.c = strArr;
            this.d = jSONArray2;
            this.e = jSONArray3;
            this.f = jSONArray4;
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) throws JSONException {
            String strConvertNativePermission = PermissionUtil.convertNativePermission(str);
            try {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.b.getActivity(), strConvertNativePermission)) {
                    this.e.put(strConvertNativePermission);
                } else {
                    this.f.put(strConvertNativePermission);
                }
            } catch (RuntimeException unused) {
            }
            w2.this.a(this.b, this.c, this.d, this.a, this.e, this.f);
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) throws JSONException {
            this.a.put(PermissionUtil.convertNativePermission(str));
            w2.this.a(this.b, this.c, this.d, this.a, this.e, this.f);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements ISysEventListener {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String b;

        b(IWebview iWebview, String str) {
            this.a = iWebview;
            this.b = str;
        }

        @Override // io.dcloud.common.DHInterface.ISysEventListener
        public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
            if (sysEventType != ISysEventListener.SysEventType.onActivityResult) {
                return false;
            }
            Object[] objArr = (Object[]) obj;
            int iIntValue = ((Integer) objArr[0]).intValue();
            int iIntValue2 = ((Integer) objArr[1]).intValue();
            Intent intent = (Intent) objArr[2];
            StringBuffer stringBuffer = new StringBuffer(Operators.ARRAY_START_STR);
            stringBuffer.append(iIntValue);
            stringBuffer.append(",").append(iIntValue2);
            if (intent != null) {
                stringBuffer.append(",").append(w2.a(this.a, intent));
            }
            stringBuffer.append(Operators.ARRAY_END_STR);
            Deprecated_JSUtil.execCallback(this.a, this.b, stringBuffer.toString(), JSUtil.OK, true, true);
            return true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements IEventCallback {
        final /* synthetic */ IWebview a;
        final /* synthetic */ ISysEventListener b;

        c(IWebview iWebview, ISysEventListener iSysEventListener) {
            this.a = iWebview;
            this.b = iSysEventListener;
        }

        @Override // io.dcloud.common.DHInterface.IEventCallback
        public Object onCallBack(String str, Object obj) {
            if (!PdrUtil.isEquals(str, AbsoluteConst.EVENTS_WINDOW_CLOSE) && !PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE)) {
                return null;
            }
            this.a.obtainApp().unregisterSysEventListener(this.b, ISysEventListener.SysEventType.onActivityResult);
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ISysEventListener.SysEventType.values().length];
            a = iArr;
            try {
                iArr[ISysEventListener.SysEventType.onActivityResult.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public w2(AbsMgr absMgr) {
        c = this;
    }

    static void b(Class cls, JSONStringer jSONStringer, ArrayList arrayList) throws JSONException {
        for (Class superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            String name = superclass.getName();
            if (!arrayList.contains(name)) {
                jSONStringer.value(name);
                arrayList.add(name);
                a(superclass, jSONStringer, arrayList);
            }
            if (superclass == Object.class) {
                return;
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IEventCallback
    public Object onCallBack(String str, Object obj) {
        if (!PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE) || !(obj instanceof IWebview)) {
            return null;
        }
        try {
            ((AdaFrameView) ((IWebview) obj).obtainFrameView()).removeFrameViewListener(this);
            this.a.remove(Integer.valueOf(((IWebview) obj).hashCode()));
            HashMap map = (HashMap) b.remove(Integer.valueOf(((IWebview) obj).hashCode()));
            if (map == null) {
                return null;
            }
            for (Map.Entry entry : map.entrySet()) {
                b.remove(entry.getKey());
                ((i3) entry.getValue()).a();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String a(IWebview iWebview, String str, String[] strArr) throws JSONException, IOException {
        i3 i3Var;
        JSONArray jSONArray;
        IWebview iWebview2;
        Class cls;
        String str2;
        Class cls2;
        Object objA;
        JSONArray jSONArray2;
        w2 w2Var = this;
        String[] strArr2 = strArr;
        if (!w2Var.a.contains(Integer.valueOf(iWebview.hashCode()))) {
            w2Var.a.add(Integer.valueOf(iWebview.hashCode()));
            ((AdaFrameView) iWebview.obtainFrameView()).addFrameViewListener(w2Var);
        }
        int i = 0;
        if ("__Instance".equals(str)) {
            String str3 = strArr2[0];
            String str4 = strArr2[1];
            if (strArr2.length <= 2 || PdrUtil.isEmpty(strArr2[2])) {
                jSONArray2 = null;
            } else {
                JSONArray jSONArrayCreateJSONArray = JSONUtil.createJSONArray(strArr2[2]);
                JSONObject jSONObject = JSONUtil.getJSONObject(jSONArrayCreateJSONArray, 0);
                zOptBoolean = jSONObject != null ? "boolean".equals(jSONObject.optString("type")) ? jSONObject.optBoolean("value") : true ^ PdrUtil.isEquals("__super__constructor__", JSONUtil.getString(jSONObject, "value")) : true;
                jSONArray2 = jSONArrayCreateJSONArray;
            }
            if (zOptBoolean) {
                try {
                } catch (Exception e) {
                    e = e;
                }
                try {
                    a(iWebview, str3, new i3(iWebview, w2Var, j3.a(str4), str3, jSONArray2));
                    return null;
                } catch (Exception e2) {
                    e = e2;
                    String strA = a(e, "new " + str4);
                    Log.e("InvProxy", "NativeObject.execMethod __Instance " + str4 + " method ; params=" + jSONArray2 + e);
                    return strA;
                }
            }
        } else {
            IWebview iWebview3 = iWebview;
            if ("release".equals(str) || "__autoCollection".equals(str)) {
                String str5 = strArr[0];
                HashMap mapA = a(iWebview);
                if (mapA != null && (i3Var = (i3) mapA.remove(str5)) != null) {
                    i3Var.a();
                }
            } else {
                if ("getWebviewById".equals(str)) {
                    return b(iWebview3, SDK.obtainWebview(iWebview3.obtainFrameView().obtainApp().obtainAppId(), strArr2[0]).obtainWebview());
                }
                if ("currentWebview".equals(str)) {
                    return iWebview3 instanceof AdaUniWebView ? "" : b(iWebview3, iWebview3.obtainWebview());
                }
                if ("getContext".equals(str)) {
                    String str6 = strArr2[0];
                    String strB = b(iWebview3, iWebview3.getActivity());
                    w2Var.a(iWebview3, "onActivityResult", str6);
                    return strB;
                }
                if ("importFields".equals(str)) {
                    i3 i3VarA = w2Var.a(iWebview3, strArr2[0]);
                    if (i3VarA != null) {
                        return JSUtil.wrapJsVar(i3VarA.a(iWebview3, i3VarA.a));
                    }
                } else {
                    if ("import".equals(str)) {
                        return Deprecated_JSUtil.wrapJsVar(j3.a(iWebview3, w2Var, strArr2[0]), false);
                    }
                    if ("__plusGetAttribute".equals(str)) {
                        String str7 = strArr2[0];
                        String str8 = strArr2[1];
                        i3 i3VarA2 = w2Var.a(iWebview3, str7);
                        if (i3VarA2 != null && (objA = i3.a(i3VarA2.b, i3VarA2.c, str8)) != null) {
                            return b(iWebview3, objA);
                        }
                    } else if ("__plusSetAttribute".equals(str)) {
                        String str9 = strArr2[0];
                        String str10 = strArr2[1];
                        JSONArray jSONArrayCreateJSONArray2 = JSONUtil.createJSONArray(strArr2[2]);
                        i3 i3VarA3 = w2Var.a(iWebview3, str9);
                        if (i3VarA3 != null) {
                            i3.b(iWebview, w2Var, i3VarA3.b, i3VarA3.c, str10, jSONArrayCreateJSONArray2);
                            return null;
                        }
                    } else {
                        if ("implements".equals(str)) {
                            String str11 = strArr2[0];
                            x2 x2Var = new x2(iWebview3, strArr2[1], JSONUtil.createJSONArray(strArr2[2]), strArr2[3]);
                            x2Var.a = str11;
                            return b(iWebview3, x2Var.a(null));
                        }
                        if (!"__loadDylib".equals(str) && !"__release".equals(str)) {
                            if ("__inheritList".equals(str)) {
                                String str12 = strArr2[0];
                                try {
                                    String str13 = strArr2[1];
                                    if (TextUtils.isEmpty(str13)) {
                                        return j3.b(str12);
                                    }
                                    i3 i3VarA4 = w2Var.a(iWebview3, str13);
                                    return i3VarA4 != null ? j3.c(i3VarA4.b) : j3.b(str12);
                                } catch (Exception e3) {
                                    return a(e3, "importClass " + str12);
                                }
                            }
                            if (!"__execCFunction".equals(str)) {
                                if ("__newObject".equals(str)) {
                                    String str14 = strArr2[0];
                                    JSONArray jSONArrayCreateJSONArray3 = JSONUtil.createJSONArray(strArr2[1]);
                                    try {
                                        return b(iWebview3, i3.a(iWebview3, w2Var, j3.a(str14), jSONArrayCreateJSONArray3));
                                    } catch (Exception e4) {
                                        String strA2 = a(e4, "newObject " + str14);
                                        Log.e("InvProxy", "NativeObject.execMethod __newObject " + str14 + " method ; params=" + jSONArrayCreateJSONArray3 + e4);
                                        return strA2;
                                    }
                                }
                                if ("__execStatic".equals(str)) {
                                    String str15 = strArr2[0];
                                    String str16 = strArr2[1];
                                    if (!w2Var.a(str15, str16, iWebview3)) {
                                        JSONArray jSONArrayCreateJSONArray4 = (strArr2.length <= 2 || PdrUtil.isEmpty(strArr2[2])) ? null : JSONUtil.createJSONArray(strArr2[2]);
                                        Class clsA = j3.a(str15);
                                        if (clsA == null) {
                                            str2 = str15;
                                            iWebview2 = iWebview3;
                                            cls = String.class;
                                        } else {
                                            iWebview2 = iWebview3;
                                            cls = clsA;
                                            str2 = null;
                                        }
                                        try {
                                            Object objA2 = i3.a(iWebview2, w2Var, cls, str2, str16, jSONArrayCreateJSONArray4);
                                            cls2 = cls;
                                            IWebview iWebview4 = iWebview2;
                                            if (objA2 != null) {
                                                try {
                                                    return b(iWebview4, objA2);
                                                } catch (Exception e5) {
                                                    e = e5;
                                                    String strA3 = a(e, "static " + cls2.getName() + Operators.DOT_STR + str16);
                                                    Log.e("InvProxy", "NativeObject.execMethod " + str16 + " method ; params=" + cls2 + e);
                                                    return strA3;
                                                }
                                            }
                                        } catch (Exception e6) {
                                            e = e6;
                                            cls2 = cls;
                                        }
                                    }
                                } else if ("__exec".equals(str)) {
                                    String str17 = strArr2[0];
                                    String str18 = strArr2[1];
                                    if (!w2Var.a("", str18, iWebview3)) {
                                        JSONArray jSONArrayCreateJSONArray5 = JSONUtil.createJSONArray(strArr2[2]);
                                        i3 i3VarA5 = w2Var.a(iWebview3, str17);
                                        if (i3VarA5 != null) {
                                            try {
                                                Object objA3 = i3VarA5.a(iWebview3, str18, jSONArrayCreateJSONArray5);
                                                if (objA3 != null) {
                                                    return b(iWebview3, objA3);
                                                }
                                            } catch (Exception e7) {
                                                String strA4 = a(e7, i3VarA5.b.getName() + Operators.DOT_STR + str18);
                                                Log.e("InvProxy", "NativeObject.execMethod " + str18 + " method ; params=" + jSONArrayCreateJSONArray5 + e7);
                                                return strA4;
                                            }
                                        }
                                    }
                                } else {
                                    if ("__saveContent".equals(str)) {
                                        DHFile.writeFile(strArr2[1].toString().getBytes(), 0, iWebview3.obtainFrameView().obtainApp().convert2AbsFullPath(iWebview3.obtainFullUrl(), strArr2[0]));
                                        return null;
                                    }
                                    if ("requestPermissions".equals(str)) {
                                        JSONArray jSONArrayCreateJSONArray6 = JSONUtil.createJSONArray(strArr2[1]);
                                        ArrayList arrayList = new ArrayList();
                                        JSONArray jSONArray3 = new JSONArray();
                                        JSONArray jSONArray4 = new JSONArray();
                                        JSONArray jSONArray5 = new JSONArray();
                                        ArrayList arrayList2 = new ArrayList();
                                        int i2 = 0;
                                        while (i2 < jSONArrayCreateJSONArray6.length()) {
                                            String strOptString = jSONArrayCreateJSONArray6.optString(i2);
                                            int iCheckSelfPermission = PermissionChecker.checkSelfPermission(iWebview3.getActivity(), strOptString);
                                            arrayList2.add(strOptString);
                                            if (iCheckSelfPermission != 0) {
                                                if (iWebview3.getActivity().getApplicationInfo().targetSdkVersion < 23 || Build.VERSION.SDK_INT < 23) {
                                                    jSONArray5.put(strOptString);
                                                    w2Var.a(iWebview3, strArr2, jSONArrayCreateJSONArray6, jSONArray3, jSONArray4, jSONArray4);
                                                } else {
                                                    arrayList.add(strOptString);
                                                }
                                                jSONArray = jSONArray5;
                                            } else {
                                                jSONArray3.put(strOptString);
                                                jSONArray = jSONArray5;
                                                a(iWebview, strArr, jSONArrayCreateJSONArray6, jSONArray3, jSONArray4, jSONArray);
                                            }
                                            i2++;
                                            w2Var = this;
                                            iWebview3 = iWebview;
                                            strArr2 = strArr;
                                            jSONArray5 = jSONArray;
                                        }
                                        JSONArray jSONArray6 = jSONArray5;
                                        PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_REQUEST, (String[]) arrayList2.toArray(new String[0]));
                                        if (arrayList.size() == 0) {
                                            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, (String[]) arrayList2.toArray(new String[0]));
                                            return null;
                                        }
                                        if (jSONArray3.length() > 0 || jSONArray6.length() > 0) {
                                            ArrayList arrayList3 = new ArrayList();
                                            for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                                                arrayList3.add(jSONArray3.optString(i3));
                                            }
                                            for (int i4 = 0; i4 < jSONArray6.length(); i4++) {
                                                arrayList3.add(jSONArray6.optString(i4));
                                            }
                                            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, (String[]) arrayList3.toArray(new String[0]));
                                        }
                                        String[] strArr3 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                                        a aVar = new a(jSONArray3, iWebview, strArr, jSONArrayCreateJSONArray6, jSONArray4, jSONArray6);
                                        aVar.setTriggerRequestEvent(true);
                                        PermissionUtil.requestSystemPermissions(iWebview.getActivity(), strArr3, PermissionUtil.getRequestCode(), aVar, false);
                                        return null;
                                    }
                                    if ("checkPermission".equals(str)) {
                                        String str19 = strArr[0];
                                        int iCheckSelfPermission2 = PermissionChecker.checkSelfPermission(iWebview.getActivity(), strArr[1]);
                                        if (iCheckSelfPermission2 == -2) {
                                            i = -2;
                                        } else if (iCheckSelfPermission2 == -1) {
                                            i = -1;
                                        } else if (iCheckSelfPermission2 != 0) {
                                            i = iCheckSelfPermission2;
                                        }
                                        Deprecated_JSUtil.execCallback(iWebview, str19, "{checkResult:+" + i + Operators.BLOCK_END_STR, JSUtil.OK, true, false);
                                        return null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    static String b(IWebview iWebview, Object obj) {
        String str;
        Class<?> cls = obj.getClass();
        String strB = j3.b(cls);
        if (!j3.a((Class) cls) && cls != String.class && cls != CharSequence.class && !cls.isArray()) {
            str = "object";
        } else {
            str = "basic";
        }
        StringBuffer stringBuffer = new StringBuffer();
        a(iWebview, obj, cls, stringBuffer);
        return Deprecated_JSUtil.wrapJsVar(StringUtil.format("{\"type\":\"%s\", \"value\":%s, \"className\":\"%s\",\"superClassNames\":%s}", str, stringBuffer.toString(), strB, a((Class) cls)), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(IWebview iWebview, String[] strArr, JSONArray jSONArray, JSONArray jSONArray2, JSONArray jSONArray3, JSONArray jSONArray4) throws JSONException {
        if (jSONArray.length() == jSONArray2.length() + jSONArray3.length() + jSONArray4.length()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("granted", jSONArray2);
                jSONObject.put("deniedPresent", jSONArray3);
                jSONObject.put("deniedAlways", jSONArray4);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Deprecated_JSUtil.execCallback(iWebview, strArr[0], jSONObject.toString(), JSUtil.OK, true, false);
        }
    }

    private static String a(Exception exc, String str) {
        String message;
        StringBuilder sb = new StringBuilder();
        if (exc.getCause() != null) {
            message = exc.getCause();
        } else {
            boolean zIsEmpty = TextUtils.isEmpty(exc.getMessage());
            message = exc;
            if (!zIsEmpty) {
                message = exc.getMessage();
            }
        }
        sb.append((Object) message);
        sb.append(";at ");
        sb.append(str);
        return StringUtil.format("throw '%s';", sb.toString());
    }

    private void a(IWebview iWebview, String str, String str2) {
        if (d.a[ISysEventListener.SysEventType.valueOf(str).ordinal()] != 1) {
            return;
        }
        b bVar = new b(iWebview, str2);
        iWebview.obtainApp().registerSysEventListener(bVar, ISysEventListener.SysEventType.onActivityResult);
        iWebview.obtainFrameView().addFrameViewListener(new c(iWebview, bVar));
    }

    static String a(IWebview iWebview, Object obj) {
        String strValueOf;
        Class<?> cls = obj.getClass();
        String strB = j3.b(cls);
        if (cls != String.class && cls != CharSequence.class) {
            if (j3.a((Class) cls)) {
                strValueOf = String.valueOf(obj);
            } else {
                String strA = a(obj);
                a(iWebview, strA, obj);
                return StringUtil.format("plus.ios.__Tool.New(%s, true)", Deprecated_JSUtil.wrapJsVar(StringUtil.format("{\"type\":\"%s\", \"value\":%s, \"className\":\"%s\",\"superClassNames\":%s}", "object", JSUtil.QUOTE + strA + JSUtil.QUOTE, strB, a((Class) cls)), false));
            }
        } else {
            strValueOf = JSUtil.QUOTE + String.valueOf(obj) + JSUtil.QUOTE;
        }
        return Deprecated_JSUtil.wrapJsVar(StringUtil.format("{\"type\":\"%s\", \"value\":%s, \"className\":\"%s\",\"superClassNames\":%s}", "basic", strValueOf, strB, a((Class) cls)), false);
    }

    static String a(Class cls) throws JSONException {
        JSONStringer jSONStringer = new JSONStringer();
        ArrayList arrayList = new ArrayList();
        try {
            jSONStringer.array();
            b(cls, jSONStringer, arrayList);
            jSONStringer.endArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String string = jSONStringer.toString();
        return string == null ? "[]" : string;
    }

    static void a(Class cls, JSONStringer jSONStringer, ArrayList arrayList) throws JSONException {
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces != null) {
            for (Class<?> cls2 : interfaces) {
                String name = cls2.getName();
                if (!arrayList.contains(name)) {
                    jSONStringer.value(name);
                    arrayList.add(name);
                    a(cls2, jSONStringer, arrayList);
                }
            }
        }
    }

    static void a(IWebview iWebview, Object obj, Class cls, StringBuffer stringBuffer) {
        if (cls != String.class && cls != CharSequence.class) {
            if (j3.a(cls)) {
                stringBuffer.append(String.valueOf(obj));
                return;
            }
            if (cls.isArray()) {
                int length = Array.getLength(obj);
                stringBuffer.append(Operators.ARRAY_START_STR);
                for (int i = 0; i < length; i++) {
                    stringBuffer.append(b(iWebview, j3.a(Array.get(obj, i), cls)));
                    if (i != length - 1) {
                        stringBuffer.append(",");
                    }
                }
                stringBuffer.append(Operators.ARRAY_END_STR);
                return;
            }
            String strA = a(obj);
            a(iWebview, strA, obj);
            stringBuffer.append(JSUtil.QUOTE).append(strA).append(JSUtil.QUOTE);
            return;
        }
        stringBuffer.append(JSONObject.quote(String.valueOf(obj)));
    }

    private static HashMap a(IWebview iWebview) {
        HashMap map = (HashMap) b.get(Integer.valueOf(iWebview.hashCode()));
        if (map != null) {
            return map;
        }
        HashMap map2 = new HashMap(2);
        b.put(Integer.valueOf(iWebview.hashCode()), map2);
        return map2;
    }

    i3 a(HashMap map, String str) {
        return (i3) map.get(str);
    }

    i3 a(IWebview iWebview, String str) {
        return a(a(iWebview), str);
    }

    private static void a(IWebview iWebview, String str, i3 i3Var) {
        a(iWebview).put(str, i3Var);
    }

    private static i3 a(IWebview iWebview, String str, Object obj) {
        i3 i3Var = new i3(c, obj.getClass(), str, obj);
        a(iWebview, str, i3Var);
        return i3Var;
    }

    static String a(Object obj) {
        return IFeature.F_INVOCATION + obj.hashCode();
    }

    private boolean a(String str, String str2, IWebview iWebview) {
        return iWebview != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && Boolean.parseBoolean(iWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_USE_ENCRYPTION)) && "setWebContentsDebuggingEnabled".equalsIgnoreCase(str2) && (TextUtils.isEmpty(str) || "WebView".equalsIgnoreCase(str) || "android.webkit.WebView".equalsIgnoreCase(str));
    }

    public void a(String str) {
        TextUtils.isEmpty(str);
    }
}

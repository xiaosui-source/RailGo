package io.dcloud.p;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.feature.gg.AolSplashUtil;
import io.dcloud.sdk.core.adapter.IAdAdapter;
import io.dcloud.sdk.core.module.DCBaseAOL;
import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class m {
    private static Boolean a;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class a {
        public boolean a = true;
        public int b;
        public int c;
        public List d;
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static class b {
        private double a;
        private double b;
        private String c;

        public String a() {
            return this.c;
        }

        public void a(double d, double d2, String str) {
            this.a = d;
            this.b = d2;
            this.c = str;
        }

        public boolean a(double d) {
            return d >= this.a && d < this.b;
        }
    }

    private static void a(Context context, Boolean bool) {
        try {
            v0.a((bool.booleanValue() ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT).getBytes(), 0, e(context).replaceAll("/ad/", "/") + "AdEnable.dat");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void b(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        a(context, Boolean.valueOf(Integer.parseInt(str) == 1));
    }

    public static void c(Context context, String str) {
        a = Boolean.valueOf("1".equals(str));
        if (TextUtils.isEmpty(str)) {
            return;
        }
        d4.a(context, AolSplashUtil.showCountADReward, "_is_gm_channel", str);
    }

    public static boolean d(Context context) {
        try {
            String strG = g(context);
            return strG == null ? b(context).booleanValue() : "1".equals(strG.replaceAll("\n", ""));
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private static String e(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null) {
            return "/sdcard/Android/data/" + context.getPackageName() + "/cache/ad/";
        }
        return externalCacheDir.getAbsolutePath() + "/ad/";
    }

    public static boolean f(Context context) {
        if (a == null) {
            a = Boolean.valueOf("1".equals(d4.a(context, AolSplashUtil.showCountADReward, "_is_gm_channel")));
        }
        return a.booleanValue();
    }

    public static String g(Context context) {
        String str = e(context).replaceAll("/ad/", "/") + "AdEnable.dat";
        try {
            if (v0.a(str)) {
                return new String(v0.e(str));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Boolean b(Context context) {
        try {
            return Boolean.valueOf(context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getBoolean("DCLOUD_AD_SPLASH", false));
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public static String c(Context context) {
        return d4.a(context, AolSplashUtil.showCountADReward, "servertime");
    }

    public static void a(Context context, String str, String str2) throws JSONException {
        JSONObject jSONObject;
        String strA = d4.a(context, AolSplashUtil.showCountADReward, str);
        try {
            if (TextUtils.isEmpty(strA)) {
                jSONObject = new JSONObject();
            } else {
                jSONObject = new JSONObject(strA);
            }
            if (jSONObject.has(str2)) {
                jSONObject.put(str2, jSONObject.optInt(str2) + 1);
            } else {
                jSONObject.put(str2, 1);
            }
            d4.a(context, AolSplashUtil.showCountADReward, str, jSONObject.toString());
        } catch (JSONException unused) {
        }
    }

    public static String b(DCBaseAOL dCBaseAOL) {
        IAdAdapter iAdAdapterB;
        if (!dCBaseAOL.s() || (iAdAdapterB = e.b().b(dCBaseAOL.getType())) == null) {
            return null;
        }
        return iAdAdapterB.getSDKVersion();
    }

    public static void a(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(AolSplashUtil.showCountADReward, 0).edit();
        editorEdit.clear();
        editorEdit.apply();
    }

    public static void a(Context context, String str) {
        d4.a(context, AolSplashUtil.showCountADReward, "servertime", str);
    }

    public static boolean a(long j, long j2) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(j);
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.setTimeInMillis(j2);
        return gregorianCalendar.get(1) == gregorianCalendar2.get(1) && gregorianCalendar.get(2) == gregorianCalendar2.get(2) && gregorianCalendar.get(5) == gregorianCalendar2.get(5);
    }

    public static List a(Context context, List list, String str, JSONObject jSONObject, JSONObject jSONObject2) {
        JSONObject jSONObject3;
        String strOptString;
        int iOptInt;
        b3.a(String.valueOf(str) + "---STA---" + list.toString());
        if (jSONObject != null && jSONObject.length() > 0 && list.size() > 1) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (!list.contains(next)) {
                    itKeys.remove();
                } else if (jSONObject.optInt(next) <= 0) {
                    itKeys.remove();
                }
            }
            ArrayList arrayList = new ArrayList(a(jSONObject));
            if (list.size() != arrayList.size()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (!arrayList.contains(str2)) {
                        arrayList.add(str2);
                    }
                }
            }
            list = arrayList;
        }
        b3.a(String.valueOf(str) + "---PRE---" + list.toString());
        if (jSONObject2 != null && jSONObject2.length() > 0 && list.size() > 1) {
            try {
                jSONObject3 = new JSONObject(d4.a(context, AolSplashUtil.showCountADReward, str));
            } catch (Exception unused) {
                jSONObject3 = null;
            }
            boolean z = true;
            int i = 0;
            while (jSONObject3 != null && z && i < list.size()) {
                i++;
                JSONArray jSONArrayOptJSONArray = jSONObject2.optJSONArray((String) list.get(0));
                if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 1) {
                    strOptString = jSONArrayOptJSONArray.optString(0);
                    iOptInt = jSONArrayOptJSONArray.optInt(1);
                } else {
                    strOptString = "";
                    iOptInt = 0;
                }
                if (iOptInt > jSONObject3.optInt(strOptString) || iOptInt == 0) {
                    z = false;
                } else {
                    list.add((String) list.remove(0));
                }
            }
        }
        b3.a(String.valueOf(str) + "---AFT---" + list.toString());
        return list;
    }

    private static List a(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        if (jSONObject != null && jSONObject.length() != 0) {
            if (jSONObject.length() == 1) {
                arrayList.add(jSONObject.keys().next());
                return arrayList;
            }
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < jSONObject.length(); i++) {
                arrayList2.add(new b());
            }
            while (arrayList2.size() > 1) {
                b bVarA = a(arrayList2, a(jSONObject, arrayList2));
                if (bVarA != null) {
                    jSONObject.remove(bVarA.a());
                    arrayList2.remove(bVarA);
                    arrayList.add(bVarA.a());
                }
            }
            arrayList.add(((b) arrayList2.get(0)).a());
        }
        return arrayList;
    }

    private static int a(JSONObject jSONObject, ArrayList arrayList) {
        Iterator<String> itKeys = jSONObject.keys();
        int iOptInt = 0;
        int i = 0;
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            double d = iOptInt;
            iOptInt += jSONObject.optInt(next);
            ((b) arrayList.get(i)).a(d, iOptInt, next);
            i++;
        }
        return iOptInt;
    }

    private static b a(ArrayList arrayList, int i) {
        double dRandom = Math.random() * i;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            b bVar = (b) obj;
            if (bVar.a(dRandom)) {
                return bVar;
            }
        }
        return null;
    }

    public static void a(List list) {
        if (list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) {
                int i2 = 0;
                while (i2 < (list.size() - i) - 1) {
                    int i3 = i2 + 1;
                    if (((DCBaseAOL) list.get(i3)).getBiddingECPM() > ((DCBaseAOL) list.get(i2)).getBiddingECPM()) {
                        DCBaseAOL dCBaseAOL = (DCBaseAOL) list.get(i2);
                        list.set(i2, (DCBaseAOL) list.get(i3));
                        list.set(i3, dCBaseAOL);
                    }
                    i2 = i3;
                }
            }
        }
    }

    public static a a(int i, List... listArr) {
        int biddingECPM;
        int biddingECPM2;
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (List list : listArr) {
            arrayList.addAll(list);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        a(arrayList);
        ArrayList arrayList2 = new ArrayList();
        if (arrayList.size() > i) {
            arrayList2.addAll(arrayList.subList(0, i));
            List<DCBaseAOL> listSubList = arrayList.subList(i, arrayList.size());
            biddingECPM = ((DCBaseAOL) arrayList2.get(0)).getBiddingECPM();
            biddingECPM2 = ((DCBaseAOL) listSubList.get(0)).getBiddingECPM();
            for (DCBaseAOL dCBaseAOL : listSubList) {
                if (dCBaseAOL.isSlotSupportBidding()) {
                    dCBaseAOL.biddingFail(biddingECPM, biddingECPM2, 2);
                }
            }
        } else {
            arrayList2.addAll(arrayList);
            biddingECPM = ((DCBaseAOL) arrayList2.get(0)).getBiddingECPM();
            biddingECPM2 = 0;
        }
        int size = arrayList2.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                z = true;
                break;
            }
            Object obj = arrayList2.get(i2);
            i2++;
            if (!((DCBaseAOL) obj).isSlotSupportBidding()) {
                break;
            }
        }
        a aVar = new a();
        aVar.a = z;
        aVar.b = biddingECPM;
        aVar.c = biddingECPM2;
        aVar.d = arrayList2;
        return aVar;
    }

    public static String a(DCBaseAOL dCBaseAOL) {
        IAdAdapter iAdAdapterB;
        if (!dCBaseAOL.s() || (iAdAdapterB = e.b().b(dCBaseAOL.getType())) == null) {
            return null;
        }
        return iAdAdapterB.getAdapterSDKVersion();
    }
}

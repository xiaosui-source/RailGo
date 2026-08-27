package io.dcloud.sdk.base.entry;

import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.MotionEvent;
import androidx.core.app.JobIntentService;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import com.nostra13.dcloudimageloader.core.download.BaseImageDownloader;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.p.f0;
import io.dcloud.p.k3;
import io.dcloud.p.k4;
import io.dcloud.p.l1;
import io.dcloud.p.m4;
import io.dcloud.p.w4;
import io.dcloud.sdk.activity.WebViewActivity;
import io.dcloud.sdk.base.service.DownloadService;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class AdData implements Parcelable {
    public static final Parcelable.Creator<AdData> CREATOR = new d();
    private int a;
    private String action;
    private String b;
    private String bundle;
    private byte[] c;
    private String d;
    private String downloadAppName;
    private String dplk;
    private String e;
    private String expires;
    private String f;
    private String g;
    private String h;
    private List i;
    private List j;
    private List k;
    private MotionEvent l;
    private MotionEvent m;
    private boolean n;
    private RectF o;
    protected Context p;
    private String price;
    private String provider;
    private String src;
    private String tid;
    private String ua;
    private String url;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class ExtBean implements Parcelable {
        public static final Parcelable.Creator<ExtBean> CREATOR = new a();
        private String a;
        private int b;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements Parcelable.Creator {
            a() {
            }

            @Override // android.os.Parcelable.Creator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ExtBean createFromParcel(Parcel parcel) {
                return new ExtBean(parcel);
            }

            @Override // android.os.Parcelable.Creator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ExtBean[] newArray(int i) {
                return new ExtBean[i];
            }
        }

        protected ExtBean(Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeInt(this.b);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ e b;

        a(String str, e eVar) {
            this.a = str;
            this.b = eVar;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            byte[] bArrA = k3.a(AdData.this.src, (HashMap) null, true, new String[1]);
            if (bArrA == null) {
                e eVar = this.b;
                if (eVar != null) {
                    eVar.a(60009, "图片下载失败");
                    return;
                }
                return;
            }
            AdData.this.c = bArrA;
            l1.a(AdData.this.c, 0, this.a);
            AdData.this.b = this.a;
            e eVar2 = this.b;
            if (eVar2 != null) {
                eVar2.a();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        final /* synthetic */ Context a;

        b(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            Context context = this.a;
            if (context != null) {
                f0.a(context, AdData.this.f, AdData.this.tid, "", 50, AdData.this.e, AdData.this.h, AdData.this.g, null);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements Runnable {
        final /* synthetic */ TrackerBean a;

        c(TrackerBean trackerBean) {
            this.a = trackerBean;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                String strA = AdData.this.a(this.a.a);
                HashMap map = new HashMap();
                map.put(IWebview.USER_AGENT, m4.e(AdData.this.p));
                k3.a(strA, map, true);
            } catch (Exception unused) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Parcelable.Creator {
        d() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public AdData createFromParcel(Parcel parcel) {
            return new AdData(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public AdData[] newArray(int i) {
            return new AdData[i];
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface e {
        void a();

        void a(int i, String str);
    }

    public AdData() {
        this.a = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT;
        this.e = "";
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.k = new ArrayList();
        this.n = false;
        this.o = new RectF();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String h() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("expires", this.expires);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    public int i() {
        return this.a;
    }

    public String j() {
        return this.tid;
    }

    public String k() {
        return this.d;
    }

    public String l() {
        return this.url;
    }

    public boolean m() {
        if (this.p == null) {
            return false;
        }
        return new File(this.p.getCacheDir().getAbsolutePath() + "/dcloud_ad/" + this.src.hashCode()).exists();
    }

    public boolean n() {
        return (TextUtils.isEmpty(this.src) || TextUtils.isEmpty(this.action) || TextUtils.isEmpty(this.url)) ? false : true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.provider);
        parcel.writeString(this.ua);
        parcel.writeString(this.src);
        parcel.writeString(this.action);
        parcel.writeString(this.url);
        parcel.writeString(this.bundle);
        parcel.writeString(this.downloadAppName);
        parcel.writeString(this.dplk);
        parcel.writeString(this.price);
        parcel.writeString(this.tid);
        parcel.writeString(this.expires);
        parcel.writeString(this.b);
        parcel.writeByteArray(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeTypedList(this.i);
        parcel.writeTypedList(this.j);
        parcel.writeTypedList(this.k);
        parcel.writeParcelable(this.l, i);
        parcel.writeParcelable(this.m, i);
        parcel.writeByte(this.n ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.o, i);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class TrackerBean implements Parcelable {
        public static final Parcelable.Creator<TrackerBean> CREATOR = new a();
        private String a;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements Parcelable.Creator {
            a() {
            }

            @Override // android.os.Parcelable.Creator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public TrackerBean createFromParcel(Parcel parcel) {
                return new TrackerBean(parcel);
            }

            @Override // android.os.Parcelable.Creator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public TrackerBean[] newArray(int i) {
                return new TrackerBean[i];
            }
        }

        public TrackerBean(String str) {
            this.a = str;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
        }

        protected TrackerBean(Parcel parcel) {
            this.a = parcel.readString();
        }
    }

    void b(JSONObject jSONObject) throws IOException {
        k4.a().a(this.p, this, jSONObject.toString());
    }

    public void c(Context context) {
        this.p = context;
    }

    public String d() {
        return this.g;
    }

    public String e() {
        return this.f;
    }

    public String f() {
        return this.b;
    }

    public byte[] g() {
        return this.c;
    }

    public void b(MotionEvent motionEvent) {
        this.m = motionEvent;
    }

    public String c() {
        return this.e;
    }

    private void a(JSONObject jSONObject, e eVar) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
        if (jSONObjectOptJSONObject == null) {
            if (eVar != null) {
                eVar.a(60007, "无广告填充");
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(getClass().getDeclaredFields()));
        if (getClass().getSuperclass() != null) {
            Field[] declaredFields = getClass().getSuperclass().getDeclaredFields();
            if (declaredFields.length > 0) {
                arrayList.addAll(Arrays.asList(declaredFields));
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Field field = (Field) obj;
            try {
                if (field.getType() == String.class && jSONObjectOptJSONObject.has(field.getName())) {
                    String strOptString = jSONObjectOptJSONObject.optString(field.getName());
                    field.setAccessible(true);
                    field.set(this, strOptString);
                }
            } catch (IllegalAccessException | Exception unused) {
            }
        }
        if (TextUtils.isEmpty(this.src)) {
            if (eVar != null) {
                eVar.a(60008, "图片资源路径异常");
                return;
            }
            return;
        }
        String str = this.p.getCacheDir().getAbsolutePath() + "/dcloud_ad/img/" + this.src.hashCode();
        if (new File(str).exists()) {
            this.b = str;
            if (eVar != null) {
                eVar.a();
                return;
            }
            return;
        }
        w4.a().a(new a(str, eVar));
    }

    private void b(Context context) {
        a(this.j);
        if (!TextUtils.isEmpty(this.dplk) && io.dcloud.p.c.d(context, this.dplk)) {
            a(this.k);
            w4.a().a(new b(context));
        }
        String str = this.action;
        str.getClass();
        str.hashCode();
        switch (str) {
            case "url":
                try {
                    Intent intent = new Intent();
                    int i = WebViewActivity.o;
                    intent.setClass(context, WebViewActivity.class);
                    intent.putExtra("url", this.url);
                    intent.setData(Uri.parse(this.url));
                    intent.setAction("android.intent.action.VIEW");
                    intent.setFlags(268435456);
                    context.startActivity(intent);
                    break;
                } catch (Exception unused) {
                    return;
                }
            case "browser":
                io.dcloud.p.c.c(context, this.url);
                break;
            case "download":
                Intent intent2 = new Intent();
                intent2.putExtra("url", this.url);
                intent2.putExtra("data", this);
                JobIntentService.enqueueWork(context, (Class<?>) DownloadService.class, 10010, intent2);
                break;
        }
    }

    protected AdData(Parcel parcel) {
        this.a = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT;
        this.e = "";
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.k = new ArrayList();
        this.n = false;
        this.o = new RectF();
        this.provider = parcel.readString();
        this.ua = parcel.readString();
        this.src = parcel.readString();
        this.action = parcel.readString();
        this.url = parcel.readString();
        this.bundle = parcel.readString();
        this.downloadAppName = parcel.readString();
        this.dplk = parcel.readString();
        this.price = parcel.readString();
        this.tid = parcel.readString();
        this.expires = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.createByteArray();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        Parcelable.Creator<TrackerBean> creator = TrackerBean.CREATOR;
        this.i = parcel.createTypedArrayList(creator);
        this.j = parcel.createTypedArrayList(creator);
        this.k = parcel.createTypedArrayList(creator);
        this.l = (MotionEvent) parcel.readParcelable(MotionEvent.class.getClassLoader());
        this.m = (MotionEvent) parcel.readParcelable(MotionEvent.class.getClassLoader());
        this.n = parcel.readByte() != 0;
        this.o = (RectF) parcel.readParcelable(RectF.class.getClassLoader());
    }

    public String b() {
        return this.h;
    }

    private void a(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("report");
        if (jSONObjectOptJSONObject != null) {
            a(jSONObjectOptJSONObject.optJSONArray("imptracker"), this.i);
            a(jSONObjectOptJSONObject.optJSONArray("clktracker"), this.j);
            a(jSONObjectOptJSONObject.optJSONArray("dptracker"), this.k);
        }
    }

    private void a(JSONArray jSONArray, List list) {
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject != null) {
                    list.add(new TrackerBean(jSONObjectOptJSONObject.optString("url")));
                }
            }
        }
    }

    public void a(JSONObject jSONObject, e eVar, boolean z) throws IllegalAccessException, SecurityException, IOException, IllegalArgumentException {
        this.d = String.valueOf(jSONObject.hashCode());
        a(jSONObject);
        a(jSONObject, eVar);
        int iOptInt = jSONObject.optInt("skip", BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT);
        this.a = iOptInt;
        if (iOptInt <= 0) {
            this.a = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT;
        }
        this.e = jSONObject.optString("appid", "");
        this.h = jSONObject.optString("adpid", "");
        this.tid = jSONObject.optString("tid", "");
        this.g = jSONObject.optString("adid", "");
        this.f = jSONObject.optString("did", "");
        if (n() && z && !TextUtils.isEmpty(this.expires)) {
            b(jSONObject);
        }
    }

    public void a(MotionEvent motionEvent) {
        this.l = motionEvent;
    }

    public void a(Context context) {
        b(context);
    }

    String a(String str) {
        try {
            String strReplace = str.replace("${User-Agent}", URLEncoder.encode(m4.e(this.p), "utf-8")).replace("${click_id}", "");
            MotionEvent motionEvent = this.l;
            int iRound = DCloudAlertDialog.DARK_THEME;
            String strReplace2 = strReplace.replace("${down_x}", String.valueOf(motionEvent != null ? Math.round(motionEvent.getX()) : DCloudAlertDialog.DARK_THEME));
            MotionEvent motionEvent2 = this.l;
            String strReplace3 = strReplace2.replace("${down_y}", String.valueOf(motionEvent2 != null ? Math.round(motionEvent2.getY()) : DCloudAlertDialog.DARK_THEME));
            MotionEvent motionEvent3 = this.m;
            String strReplace4 = strReplace3.replace("${up_x}", String.valueOf(motionEvent3 != null ? Math.round(motionEvent3.getX()) : DCloudAlertDialog.DARK_THEME));
            MotionEvent motionEvent4 = this.m;
            String strReplace5 = strReplace4.replace("${up_y}", String.valueOf(motionEvent4 != null ? Math.round(motionEvent4.getY()) : DCloudAlertDialog.DARK_THEME));
            MotionEvent motionEvent5 = this.l;
            String strReplace6 = strReplace5.replace("${relative_down_x}", String.valueOf(motionEvent5 != null ? Math.round(motionEvent5.getX() - this.o.left) : DCloudAlertDialog.DARK_THEME));
            MotionEvent motionEvent6 = this.l;
            String strReplace7 = strReplace6.replace("${relative_down_y}", String.valueOf(motionEvent6 != null ? Math.round(motionEvent6.getY() - this.o.top) : DCloudAlertDialog.DARK_THEME));
            MotionEvent motionEvent7 = this.m;
            String strReplace8 = strReplace7.replace("${relative_up_x}", String.valueOf(motionEvent7 != null ? Math.round(motionEvent7.getX() - this.o.left) : DCloudAlertDialog.DARK_THEME));
            MotionEvent motionEvent8 = this.m;
            if (motionEvent8 != null) {
                iRound = Math.round(motionEvent8.getY() - this.o.top);
            }
            return strReplace8.replace("${relative_up_y}", String.valueOf(iRound)).replace("${ts}", String.valueOf(System.currentTimeMillis()));
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    private void a(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            w4.a().a(new c((TrackerBean) it.next()));
        }
    }

    public void a() {
        a(this.i);
    }

    public void a(RectF rectF) {
        this.o = rectF;
    }
}

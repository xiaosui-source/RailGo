package io.dcloud.sdk.core.entry;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
@Deprecated
/* loaded from: classes2.dex */
public class DCloudAOLSlot {
    private String a;
    private final String b;
    private final String c;
    private JSONObject d;
    private final int e;
    private final boolean f;
    private int g;
    private final String h;
    private String i;
    private final int j;
    private final int k;
    private int l;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static final class Builder {
        private String a;
        private String b;
        private String c;
        private int e;
        private int f;
        private int d = 3;
        private boolean g = false;
        private final int h = 1;
        private String i = "";

        public Builder adpid(String str) {
            this.a = str;
            return this;
        }

        public DCloudAOLSlot build() {
            return new DCloudAOLSlot(this);
        }

        public Builder count(int i) {
            this.d = i;
            return this;
        }

        public Builder extra(String str) {
            this.c = str;
            return this;
        }

        public Builder height(int i) {
            this.f = i;
            return this;
        }

        public Builder setEI(String str) {
            this.i = str;
            return this;
        }

        public Builder setVideoSoundEnable(boolean z) {
            this.g = z;
            return this;
        }

        public Builder userId(String str) {
            this.b = str;
            return this;
        }

        public Builder width(int i) {
            this.e = i;
            return this;
        }
    }

    public String getAdpid() {
        return this.a;
    }

    public JSONObject getConfig() {
        return this.d;
    }

    public int getCount() {
        return this.e;
    }

    public String getEI() {
        return this.h;
    }

    public String getExt() {
        return this.c;
    }

    public String getExtra() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("extra", this.c);
            jSONObject.put("ruu", this.i);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    public int getHeight() {
        return this.k;
    }

    public int getOrientation() {
        return this.g;
    }

    public int getType() {
        return this.l;
    }

    public String getUserId() {
        return this.b;
    }

    public int getWidth() {
        return this.j;
    }

    public boolean isVideoSoundEnable() {
        return this.f;
    }

    public void setAdpid(String str) {
        this.a = str;
    }

    public void setConfig(JSONObject jSONObject) {
        this.d = jSONObject;
    }

    public void setRID(String str) {
        this.i = str;
    }

    public void setType(int i) {
        this.l = i;
    }

    private DCloudAOLSlot(Builder builder) {
        this.g = 1;
        this.l = -1;
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.e = builder.d > 0 ? Math.min(builder.d, 3) : 3;
        this.j = builder.e;
        this.k = builder.f;
        this.g = 1;
        this.f = builder.g;
        this.h = builder.i;
    }
}

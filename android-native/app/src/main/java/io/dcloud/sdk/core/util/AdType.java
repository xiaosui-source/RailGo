package io.dcloud.sdk.core.util;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public enum AdType {
    AD_FULLSCREEN("full_screen_video"),
    AD_REWARD("rewarded"),
    AD_NONE(""),
    AD_SPLASH("splash"),
    AD_INTERSTITIAL("interstitial"),
    AD_DRAW("draw_flow"),
    AD_CONTENT_PAGE("content_page"),
    AD_TEMPLATE("template");

    private String a;

    AdType(String str) {
        this.a = str;
    }

    public static AdType getAdType(String str) {
        for (AdType adType : values()) {
            if (adType.a.equals(str)) {
                return adType;
            }
        }
        return AD_NONE;
    }

    public String getType() {
        return this.a;
    }
}

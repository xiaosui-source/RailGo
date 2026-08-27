package io.dcloud.feature.weex_scroller.helper;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCScrollStartEndHelper {
    public static boolean isScrollEvent(String str) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "scroll":
            case "scrollend":
            case "scrolltolower":
            case "scrolltoupper":
            case "scrollstart":
                return true;
            default:
                return false;
        }
    }
}

package io.dcloud.feature.uniapp.adapter;

import android.net.Uri;
import com.taobao.weex.WXSDKInstance;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface AbsURIAdapter {
    public static final String BUNDLE = "bundle";
    public static final String FILE = "file";
    public static final String FONT = "font";
    public static final String IMAGE = "image";
    public static final String LINK = "link";
    public static final String OTHERS = "others";
    public static final String REQUEST = "request";
    public static final String VIDEO = "video";
    public static final String WEB = "web";

    Uri rewrite(WXSDKInstance wXSDKInstance, String str, Uri uri);

    Uri rewrite(String str, String str2, Uri uri);
}

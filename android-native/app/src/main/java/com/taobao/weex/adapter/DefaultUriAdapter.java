package com.taobao.weex.adapter;

import android.net.Uri;
import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class DefaultUriAdapter implements URIAdapter {
    private Uri.Builder buildRelativeURI(Uri.Builder builder, Uri uri, Uri uri2) {
        if (uri2.getAuthority() != null) {
            return builder.scheme(uri.getScheme());
        }
        builder.encodedAuthority(uri.getEncodedAuthority()).scheme(uri.getScheme()).path(null);
        if (uri2.getPath().startsWith("/")) {
            builder.appendEncodedPath(uri2.getEncodedPath().substring(1));
            return builder;
        }
        List<String> pathSegments = uri.getPathSegments();
        int size = pathSegments.size() - (!uri.getPath().endsWith("/") ? 1 : 0);
        for (int i = 0; i < size; i++) {
            builder.appendEncodedPath(pathSegments.get(i));
        }
        builder.appendEncodedPath(uri2.getEncodedPath());
        return builder;
    }

    @Override // io.dcloud.feature.uniapp.adapter.AbsURIAdapter
    public Uri rewrite(WXSDKInstance wXSDKInstance, String str, Uri uri) {
        return rewrite(wXSDKInstance.getBundleUrl(), str, uri);
    }

    @Override // io.dcloud.feature.uniapp.adapter.AbsURIAdapter
    public Uri rewrite(String str, String str2, Uri uri) {
        if (!TextUtils.isEmpty(str)) {
            Uri uri2 = Uri.parse(str);
            Uri.Builder builderBuildUpon = uri.buildUpon();
            if (uri.isRelative()) {
                if (uri.getEncodedPath().length() != 0) {
                    return buildRelativeURI(builderBuildUpon, uri2, uri).build();
                }
                if (!"image".equals(str2) || !TextUtils.isEmpty(uri.toString())) {
                    return uri2;
                }
            }
        }
        return uri;
    }
}

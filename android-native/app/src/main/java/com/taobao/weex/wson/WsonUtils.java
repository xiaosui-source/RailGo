package com.taobao.weex.wson;

import com.taobao.weex.utils.WXLogUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WsonUtils {
    public static final Object parseWson(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return Wson.parse(bArr);
        } catch (Exception e) {
            WXLogUtils.e("weex wson parse error ", e);
            return null;
        }
    }

    public static final byte[] toWson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return Wson.toWson(obj);
        } catch (Exception e) {
            WXLogUtils.e("weex wson to wson error ", e);
            return null;
        }
    }
}

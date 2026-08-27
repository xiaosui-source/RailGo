package com.taobao.weex.bridge;

import com.taobao.weex.utils.WXJsonUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXJSObject {
    public static final int JSON = 3;
    public static final int NUMBER = 1;
    public static final int String = 2;
    public static final int WSON = 4;
    public Object data;
    public String key;
    public int type;

    public WXJSObject(int i, Object obj) {
        this.type = i;
        this.data = obj;
    }

    public WXJSObject(int i, Object obj, String str) {
        this.type = i;
        this.data = obj;
        this.key = str;
    }

    public WXJSObject(Object obj) {
        if (obj == null) {
            this.type = 2;
            this.data = "";
            return;
        }
        this.data = obj;
        if (obj instanceof Integer) {
            this.type = 1;
            this.data = new Double(((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Double) {
            this.type = 1;
            return;
        }
        if (obj instanceof Float) {
            this.type = 1;
            this.data = new Double(((Float) obj).intValue());
        } else if (obj instanceof String) {
            this.type = 2;
        } else if (obj instanceof Object) {
            this.type = 3;
            this.data = WXJsonUtils.fromObjectToJSONString(obj, true);
        }
    }
}

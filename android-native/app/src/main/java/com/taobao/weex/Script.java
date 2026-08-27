package com.taobao.weex;

import android.text.TextUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class Script {
    private byte[] mBinary;
    private String mContent;

    public Script(String str) {
        this.mContent = str;
    }

    public byte[] getBinary() {
        return this.mBinary;
    }

    public String getContent() {
        return this.mContent;
    }

    public boolean isEmpty() {
        if (!TextUtils.isEmpty(this.mContent)) {
            return false;
        }
        byte[] bArr = this.mBinary;
        return bArr == null || bArr.length == 0;
    }

    public int length() {
        String str = this.mContent;
        if (str != null) {
            return str.length();
        }
        byte[] bArr = this.mBinary;
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    public Script(byte[] bArr) {
        this.mBinary = bArr;
    }
}

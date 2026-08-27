package io.dcloud.p;

import android.text.TextUtils;
import com.taobao.weex.el.parse.Operators;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class q2 {
    private String a;
    private int b;
    private int c;

    public q2(String str, int i, int i2) {
        this.a = str;
        this.b = i;
        this.c = i2;
    }

    public int a() {
        return this.c;
    }

    public int b() {
        return this.b;
    }

    public String c() {
        return this.a;
    }

    public boolean d() {
        return TextUtils.isEmpty(this.a);
    }

    public String toString() {
        return "IMGText{text='" + this.a + "', color=" + this.b + Operators.BLOCK_END;
    }
}

package com.meizu.flyme.openidsdk;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class OpenId {
    public long a;
    public String b;
    public String c;
    public int d;

    public OpenId(String str) {
        this.c = str;
    }

    public void a(int i) {
        this.d = i;
    }

    public void a(long j) {
        this.a = j;
    }

    public void a(String str) {
        this.b = str;
    }

    public boolean a() {
        return this.a > System.currentTimeMillis();
    }

    public void b() {
        this.a = 0L;
    }
}

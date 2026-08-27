package com.meizu.flyme.openidsdk;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class ValueData {
    public String a;
    public int b;
    public long c = System.currentTimeMillis() + 86400000;

    public ValueData(String str, int i) {
        this.a = str;
        this.b = i;
    }

    public String toString() {
        return "ValueData{value='" + this.a + "', code=" + this.b + ", expired=" + this.c + '}';
    }
}

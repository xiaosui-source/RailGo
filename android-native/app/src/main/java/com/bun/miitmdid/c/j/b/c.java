package com.bun.miitmdid.c.j.b;

import android.database.ContentObserver;

/* loaded from: classes.dex */
public class c extends ContentObserver {
    private String a;
    private int b;
    private b c;

    public c(b bVar, int i, String str) {
        super(null);
        this.c = bVar;
        this.b = i;
        this.a = str;
    }

    @Override // android.database.ContentObserver
    public native void onChange(boolean z);
}

package com.bun.miitmdid.c.j.b;

import android.database.ContentObserver;

/* loaded from: /workspace/39285EFA.decrypted.dex */
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
    public void onChange(boolean z) {
        b bVar = this.c;
        if (bVar != null) {
            bVar.a(this.b, this.a);
        } else {
            com.bun.lib.a.a("VMS_IDLG_SDK_Observer", "mIdentifierIdClient is null");
        }
    }
}

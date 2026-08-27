package com.huawei.android.hms.pps.a;

import android.os.IBinder;

/* loaded from: classes.dex */
public final class b implements c {
    private IBinder a;

    public b(IBinder iBinder) {
        this.a = iBinder;
    }

    @Override // android.os.IInterface
    public final native IBinder asBinder();

    @Override // com.huawei.android.hms.pps.a.c
    public final native boolean d();

    @Override // com.huawei.android.hms.pps.a.c
    public final native String e();
}

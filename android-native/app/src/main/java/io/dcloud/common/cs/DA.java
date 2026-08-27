package io.dcloud.common.cs;

import io.dcloud.common.DHInterface.DAI;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class DA implements DAI {
    private static DAI mInstance;

    private native void arn(String str, Object obj);

    private native void atcn(String str, Object obj);

    public static DAI getInstance() {
        if (mInstance == null) {
            mInstance = new DA();
        }
        return mInstance;
    }

    private native void scn();

    @Override // io.dcloud.common.DHInterface.DAI
    public void act(String str, Object obj) {
        atcn(str, obj);
    }

    @Override // io.dcloud.common.DHInterface.DAI
    public void ar(String str, Object obj) {
        arn(str, obj);
    }

    @Override // io.dcloud.common.DHInterface.DAI
    public void sc() {
        scn();
    }
}

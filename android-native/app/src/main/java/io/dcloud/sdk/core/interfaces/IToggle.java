package io.dcloud.sdk.core.interfaces;

import io.dcloud.sdk.core.module.DCBaseAOL;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface IToggle {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface CADLoadListener {
        void onLoadFail(int i, String str);

        void onLoadSuccess(List<? extends DCBaseAOL> list);
    }

    List<DCBaseAOL> getSuccessAds();

    boolean isLoading();

    void setCADLoadListener(CADLoadListener cADLoadListener);
}

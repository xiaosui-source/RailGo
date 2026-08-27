package io.dcloud.sdk.core.v3.cp;

import io.dcloud.sdk.core.v3.cp.DCContentPage;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface DCContentPageVideoListener {
    void onComplete(DCContentPage.ContentPageItem contentPageItem);

    void onError(DCContentPage.ContentPageItem contentPageItem);

    void onPause(DCContentPage.ContentPageItem contentPageItem);

    void onResume(DCContentPage.ContentPageItem contentPageItem);

    void onStart(DCContentPage.ContentPageItem contentPageItem);
}

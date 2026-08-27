package io.dcloud.feature.nativeObj.richtext.span;

import android.view.View;
import io.dcloud.common.DHInterface.IWebview;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface ClickSpanAble {
    String getHref();

    String getOnClickEvent();

    boolean hasClickEvent();

    void onClick(View view, IWebview iWebview);
}

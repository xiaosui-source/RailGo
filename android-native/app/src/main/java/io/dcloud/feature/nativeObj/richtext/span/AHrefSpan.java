package io.dcloud.feature.nativeObj.richtext.span;

import android.text.TextUtils;
import android.view.View;
import io.dcloud.common.DHInterface.IWebview;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AHrefSpan extends FontSpan implements ClickSpanAble {
    String mHref;
    String strOnClickEvent;

    public AHrefSpan(float f, int i, int i2, int i3, int i4, String str, String str2) {
        super(f, i, i2, i3, i4);
        this.strOnClickEvent = str;
        this.mHref = str2;
    }

    @Override // io.dcloud.feature.nativeObj.richtext.span.ClickSpanAble
    public String getHref() {
        return this.mHref;
    }

    @Override // io.dcloud.feature.nativeObj.richtext.span.ClickSpanAble
    public String getOnClickEvent() {
        return this.strOnClickEvent;
    }

    @Override // io.dcloud.feature.nativeObj.richtext.span.ClickSpanAble
    public boolean hasClickEvent() {
        return !TextUtils.isEmpty(getOnClickEvent());
    }

    @Override // io.dcloud.feature.nativeObj.richtext.span.ClickSpanAble
    public void onClick(View view, IWebview iWebview) {
        String onClickEvent = getOnClickEvent();
        if (TextUtils.isEmpty(onClickEvent)) {
            return;
        }
        iWebview.executeScript(onClickEvent);
    }
}

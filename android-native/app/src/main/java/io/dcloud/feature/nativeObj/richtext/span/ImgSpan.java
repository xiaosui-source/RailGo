package io.dcloud.feature.nativeObj.richtext.span;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import io.dcloud.common.DHInterface.IWebview;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ImgSpan extends ImageSpan implements ClickSpanAble {
    String mHref;
    String mOnClickEvent;
    String mSrc;

    public ImgSpan(Drawable drawable, String str, int i, String str2, String str3) {
        super(drawable, str, i);
        this.mOnClickEvent = str2;
        this.mHref = str3;
        this.mSrc = str;
    }

    @Override // io.dcloud.feature.nativeObj.richtext.span.ClickSpanAble
    public String getHref() {
        return this.mHref;
    }

    @Override // io.dcloud.feature.nativeObj.richtext.span.ClickSpanAble
    public String getOnClickEvent() {
        return this.mOnClickEvent;
    }

    public String getSrc() {
        return this.mSrc;
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

package com.taobao.weex.ui.component.richtext.span;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.taobao.weex.utils.ATagUtil;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ASpan extends ClickableSpan {
    private String mInstanceId;
    private String mURL;

    public ASpan(String str, String str2) {
        this.mInstanceId = str;
        this.mURL = str2;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View view) {
        ATagUtil.onClick(view, this.mInstanceId, this.mURL);
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
    }
}

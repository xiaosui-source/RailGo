package com.taobao.weex.dom;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import com.taobao.weex.ui.component.WXTextDecoration;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TextDecorationSpan extends CharacterStyle implements UpdateAppearance {
    private final WXTextDecoration mTextDecoration;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    /* renamed from: com.taobao.weex.dom.TextDecorationSpan$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$taobao$weex$ui$component$WXTextDecoration;

        static {
            int[] iArr = new int[WXTextDecoration.values().length];
            $SwitchMap$com$taobao$weex$ui$component$WXTextDecoration = iArr;
            try {
                iArr[WXTextDecoration.LINETHROUGH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$taobao$weex$ui$component$WXTextDecoration[WXTextDecoration.UNDERLINE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$taobao$weex$ui$component$WXTextDecoration[WXTextDecoration.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public TextDecorationSpan(WXTextDecoration wXTextDecoration) {
        this.mTextDecoration = wXTextDecoration;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        int i = AnonymousClass1.$SwitchMap$com$taobao$weex$ui$component$WXTextDecoration[this.mTextDecoration.ordinal()];
        if (i == 1) {
            textPaint.setUnderlineText(false);
            textPaint.setStrikeThruText(true);
        } else if (i == 2) {
            textPaint.setUnderlineText(true);
            textPaint.setStrikeThruText(false);
        } else {
            if (i != 3) {
                return;
            }
            textPaint.setUnderlineText(false);
            textPaint.setStrikeThruText(false);
        }
    }
}

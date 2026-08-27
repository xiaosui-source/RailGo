package io.dcloud.feature.weex_text;

import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import com.taobao.weex.dom.TextDecorationSpan;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXCustomStyleSpan;
import com.taobao.weex.dom.WXLineHeightSpan;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXTextDecoration;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.constant.AbsoluteConst;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCTextContentBoxMeasurement extends TextContentBoxMeasurement {
    private boolean decode;
    private String space;

    public DCTextContentBoxMeasurement(WXComponent wXComponent) {
        super(wXComponent);
        this.space = "";
        this.decode = false;
    }

    @Override // com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement
    protected Spanned createSpanned(String str) {
        String str2 = this.space;
        if (str2 != null) {
            if (str2.equals("ensp")) {
                str = Html.fromHtml(str.replaceAll(Operators.SPACE_STR, "&ensp;")).toString();
            } else if (this.space.equals("emsp")) {
                str = Html.fromHtml(str.replaceAll(Operators.SPACE_STR, "&emsp;")).toString();
            }
        }
        return super.createSpanned(str);
    }

    @Override // com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement, com.taobao.weex.layout.ContentBoxMeasurement
    public void layoutBefore() {
        WXAttr attrs = this.mComponent.getAttrs();
        this.space = (String) attrs.get("space");
        this.decode = Boolean.valueOf(attrs.containsKey("decode") ? attrs.get("decode").toString() : AbsoluteConst.FALSE).booleanValue();
        super.layoutBefore();
    }

    @Override // com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement
    protected void updateSpannable(Spannable spannable, int i) {
        DCTextContentBoxMeasurement dCTextContentBoxMeasurement;
        Spannable spannable2;
        int i2;
        WXComponent wXComponent = this.mComponent;
        if (wXComponent == null || wXComponent.getInstance() == null) {
            return;
        }
        int length = spannable.length();
        int i3 = this.mFontSize;
        if (i3 == -1) {
            this.mTextPaint.setTextSize(WXViewUtils.getRealPxByWidth(this.mComponent.getInstance().getDefaultFontSize(), this.mComponent.getInstance().getInstanceViewPortWidthWithFloat()));
        } else {
            this.mTextPaint.setTextSize(i3);
        }
        int i4 = this.mLineHeight;
        if (i4 != -1) {
            dCTextContentBoxMeasurement = this;
            spannable2 = spannable;
            i2 = i;
            dCTextContentBoxMeasurement.setSpan(spannable2, new WXLineHeightSpan(i4), 0, length, i2);
        } else {
            dCTextContentBoxMeasurement = this;
            spannable2 = spannable;
            i2 = i;
        }
        dCTextContentBoxMeasurement.setSpan(spannable2, new AlignmentSpan.Standard(dCTextContentBoxMeasurement.mAlignment), 0, length, i2);
        if (dCTextContentBoxMeasurement.mFontStyle != -1 || dCTextContentBoxMeasurement.mFontWeight != -1 || dCTextContentBoxMeasurement.mFontFamily != null) {
            dCTextContentBoxMeasurement.setSpan(spannable2, new WXCustomStyleSpan(dCTextContentBoxMeasurement.mFontStyle, dCTextContentBoxMeasurement.mFontWeight, dCTextContentBoxMeasurement.mFontFamily), 0, length, i2);
        }
        if (dCTextContentBoxMeasurement.mIsColorSet) {
            dCTextContentBoxMeasurement.mTextPaint.setColor(dCTextContentBoxMeasurement.mColor);
        }
        WXTextDecoration wXTextDecoration = dCTextContentBoxMeasurement.mTextDecoration;
        if (wXTextDecoration == WXTextDecoration.UNDERLINE || wXTextDecoration == WXTextDecoration.LINETHROUGH) {
            dCTextContentBoxMeasurement.setSpan(spannable2, new TextDecorationSpan(dCTextContentBoxMeasurement.mTextDecoration), 0, length, i2);
        }
    }
}

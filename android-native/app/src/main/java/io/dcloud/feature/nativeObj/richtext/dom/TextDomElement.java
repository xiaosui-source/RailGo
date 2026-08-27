package io.dcloud.feature.nativeObj.richtext.dom;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.TextView;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.common.Constants;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.nativeObj.richtext.IAssets;
import io.dcloud.feature.nativeObj.richtext.span.AHrefSpan;
import io.dcloud.feature.nativeObj.richtext.span.FontSpan;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class TextDomElement extends DomElement {
    public String color;
    public String href;
    public String text;

    private int getFontStyleInt() {
        return Constants.Value.ITALIC.equalsIgnoreCase(getFontStyle()) ? 1 : 0;
    }

    private int getFontWeightInt() {
        return Constants.Value.BOLD.equalsIgnoreCase(getFontWeight()) ? 1 : 0;
    }

    String getColor() {
        HashMap<String, String> map = this.style;
        if (map != null && map.containsKey("color")) {
            return this.style.get("color");
        }
        if (!TextUtils.isEmpty(this.color) && !"a".equalsIgnoreCase(this.tagName)) {
            return this.color;
        }
        DomElement domElement = this.parentDomElement;
        if (domElement == null || !(domElement instanceof TextDomElement)) {
            return null;
        }
        return ((TextDomElement) domElement).getColor();
    }

    String getFontSize() {
        HashMap<String, String> map = this.style;
        if (map != null && map.containsKey("font-size")) {
            return this.style.get("font-size");
        }
        DomElement domElement = this.parentDomElement;
        if (domElement == null || !(domElement instanceof TextDomElement)) {
            return null;
        }
        return ((TextDomElement) domElement).getFontSize();
    }

    String getFontStyle() {
        HashMap<String, String> map = this.style;
        if (map != null && map.containsKey("font-style")) {
            return this.style.get("font-style");
        }
        DomElement domElement = this.parentDomElement;
        if (domElement == null || !(domElement instanceof TextDomElement)) {
            return null;
        }
        return ((TextDomElement) domElement).getFontStyle();
    }

    String getFontWeight() {
        HashMap<String, String> map = this.style;
        if (map != null && map.containsKey("font-weight")) {
            return this.style.get("font-weight");
        }
        DomElement domElement = this.parentDomElement;
        if (domElement == null || !(domElement instanceof TextDomElement)) {
            return null;
        }
        return ((TextDomElement) domElement).getFontWeight();
    }

    String getTextDecoration() {
        HashMap<String, String> map = this.style;
        if (map != null && map.containsKey("text-decoration")) {
            return this.style.get("text-decoration");
        }
        DomElement domElement = this.parentDomElement;
        if (domElement == null || !(domElement instanceof TextDomElement)) {
            return null;
        }
        return ((TextDomElement) domElement).getTextDecoration();
    }

    int getTextDecorationInt() {
        String textDecoration = getTextDecoration();
        if ("underline".equalsIgnoreCase(textDecoration)) {
            return 1;
        }
        if ("line-through".equalsIgnoreCase(textDecoration)) {
            return 2;
        }
        return "a".equalsIgnoreCase(this.tagName) ? 1 : 0;
    }

    @Override // io.dcloud.feature.nativeObj.richtext.dom.DomElement
    public void makeSpan(IAssets iAssets, TextView textView, SpannableStringBuilder spannableStringBuilder) {
        if (TextUtils.isEmpty(this.text)) {
            return;
        }
        boolean zEqualsIgnoreCase = "a".equalsIgnoreCase(this.tagName);
        boolean zEqualsIgnoreCase2 = ContextChain.TAG_PRODUCT.equalsIgnoreCase(this.tagName);
        if (zEqualsIgnoreCase2) {
            spannableStringBuilder.append("\n");
        }
        spannableStringBuilder.append((CharSequence) this.text);
        float f = PdrUtil.parseFloat(getFontSize(), 100.0f, FontSpan.DEF_FONT_SIZE, 1.0f);
        int defaultColor = iAssets.getDefaultColor(zEqualsIgnoreCase);
        int fontWeightInt = getFontWeightInt();
        int fontStyleInt = getFontStyleInt();
        int textDecorationInt = getTextDecorationInt();
        String color = getColor();
        if (!TextUtils.isEmpty(color)) {
            defaultColor = iAssets.stringToColor(color);
        }
        int i = defaultColor;
        if (zEqualsIgnoreCase) {
            spannableStringBuilder.setSpan(new AHrefSpan(f, i, fontWeightInt, fontStyleInt, textDecorationInt, this.onClickEvent, this.href), spannableStringBuilder.length() - this.text.length(), spannableStringBuilder.length(), 17);
        } else {
            spannableStringBuilder.setSpan(new FontSpan(f, i, fontWeightInt, fontStyleInt, textDecorationInt), spannableStringBuilder.length() - this.text.length(), spannableStringBuilder.length(), 17);
        }
        if (zEqualsIgnoreCase2) {
            spannableStringBuilder.append("\n");
        }
    }

    @Override // io.dcloud.feature.nativeObj.richtext.dom.DomElement
    public void parseDomElement(XmlPullParser xmlPullParser) {
        super.parseDomElement(xmlPullParser);
        this.color = xmlPullParser.getAttributeValue(xmlPullParser.getNamespace(), "color");
        this.href = xmlPullParser.getAttributeValue(xmlPullParser.getNamespace(), "href");
    }
}

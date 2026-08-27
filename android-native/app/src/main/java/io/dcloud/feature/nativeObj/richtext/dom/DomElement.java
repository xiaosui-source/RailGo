package io.dcloud.feature.nativeObj.richtext.dom;

import android.text.SpannableStringBuilder;
import android.widget.TextView;
import io.dcloud.feature.nativeObj.richtext.IAssets;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DomElement {
    public String id;
    public String onClickEvent;
    public DomElement parentDomElement;
    public HashMap<String, String> style;
    public String tagName;

    public void makeSpan(IAssets iAssets, TextView textView, SpannableStringBuilder spannableStringBuilder) {
        spannableStringBuilder.append("\n");
    }

    public void parseDomElement(XmlPullParser xmlPullParser) {
        this.id = xmlPullParser.getAttributeValue(xmlPullParser.getNamespace(), "id");
        this.tagName = xmlPullParser.getName();
        parseStyle(xmlPullParser.getAttributeValue(xmlPullParser.getNamespace(), "style"));
        this.onClickEvent = xmlPullParser.getAttributeValue(xmlPullParser.getNamespace(), "onclick");
    }

    public void parseStyle(String str) {
        String[] strArrSplit;
        if (str == null || (strArrSplit = str.split(";")) == null || strArrSplit.length <= 0) {
            return;
        }
        this.style = new HashMap<>(2);
        for (String str2 : strArrSplit) {
            try {
                String[] strArrSplit2 = str2.split(":");
                this.style.put(strArrSplit2[0].trim(), strArrSplit2[1].trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

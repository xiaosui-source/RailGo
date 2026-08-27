package io.dcloud.feature.nativeObj.richtext;

import io.dcloud.feature.nativeObj.richtext.dom.ImgDomElement;
import java.io.InputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IAssets {
    InputStream convert2InputStream(String str);

    float convertHeight(String str, float f);

    float convertWidth(String str, float f);

    int getDefaultColor(boolean z);

    String getOnClickCallBackId();

    float getScale();

    boolean isClick();

    void loadResource(ImgDomElement.AsycLoader asycLoader);

    void setClick(boolean z);

    void setOnClickCallBackId(String str);

    int stringToColor(String str);
}

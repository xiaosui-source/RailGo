package io.dcloud.feature.uniapp.ui.shadow;

import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UniBoxShadowData {
    int contentHeight;
    int contentWidth;
    List<UniBoxShadowOptions> insetShadows;
    String mStyle;
    int normalLeft;
    int normalMaxHeight;
    int normalMaxWidth;
    List<UniBoxShadowOptions> normalShadows;
    int normalTop;
    float quality;
    float[] radii;

    public UniBoxShadowData(List<UniBoxShadowOptions> list, List<UniBoxShadowOptions> list2, float[] fArr, float f) {
        this.normalShadows = list;
        this.insetShadows = list2;
        this.quality = f;
        this.radii = fArr;
    }

    public boolean equalsUniBoxShadowData(String str, int i, int i2, float[] fArr) {
        return getStyle().equals(str) && getContentWidth() == i && getContentHeight() == i2 && getRadii()[0] == fArr[0] && getRadii()[2] == fArr[2] && getRadii()[4] == fArr[4] && getRadii()[6] == fArr[6];
    }

    public int getCanvasHeight() {
        return (int) (getNormalMaxHeight() * this.quality);
    }

    public int getCanvasWidth() {
        return (int) (getNormalMaxWidth() * this.quality);
    }

    public int getContentHeight() {
        return this.contentHeight;
    }

    public int getContentWidth() {
        return this.contentWidth;
    }

    public List<UniBoxShadowOptions> getInsetShadows() {
        return this.insetShadows;
    }

    public int getNormalLeft() {
        return this.normalLeft;
    }

    public int getNormalMaxHeight() {
        return this.normalMaxHeight;
    }

    public int getNormalMaxWidth() {
        return this.normalMaxWidth;
    }

    public List<UniBoxShadowOptions> getNormalShadows() {
        return this.normalShadows;
    }

    public int getNormalTop() {
        return this.normalTop;
    }

    public float getQuality() {
        return this.quality;
    }

    public float[] getRadii() {
        return this.radii;
    }

    public String getStyle() {
        return this.mStyle;
    }

    public void setContentHeight(int i) {
        this.contentHeight = i;
    }

    public void setContentWidth(int i) {
        this.contentWidth = i;
    }

    public void setNormalLeft(int i) {
        this.normalLeft = i;
    }

    public void setNormalMaxHeight(int i) {
        this.normalMaxHeight = i;
    }

    public void setNormalMaxWidth(int i) {
        this.normalMaxWidth = i;
    }

    public void setNormalTop(int i) {
        this.normalTop = i;
    }

    public void setStyle(String str) {
        this.mStyle = str;
    }
}

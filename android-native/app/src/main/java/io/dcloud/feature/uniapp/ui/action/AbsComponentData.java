package io.dcloud.feature.uniapp.ui.action;

import android.view.View;
import com.taobao.weex.dom.CSSShorthand;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.dom.WXStyle;
import com.taobao.weex.utils.WXUtils;
import io.dcloud.feature.uniapp.dom.AbsAttr;
import io.dcloud.feature.uniapp.dom.AbsCSSShorthand;
import io.dcloud.feature.uniapp.dom.AbsEvent;
import io.dcloud.feature.uniapp.dom.AbsStyle;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public abstract class AbsComponentData<T extends View> {
    protected AbsAttr mAttributes;
    private AbsCSSShorthand mBorders;
    public String mComponentType;
    protected AbsEvent mEvents;
    private AbsCSSShorthand mMargins;
    private AbsCSSShorthand mPaddings;
    public String mParentRef;
    public String mRef;
    protected AbsStyle mStyles;
    protected long renderObjectPr = 0;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    /* renamed from: io.dcloud.feature.uniapp.ui.action.AbsComponentData$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$dcloud$feature$uniapp$dom$AbsCSSShorthand$TYPE;

        static {
            int[] iArr = new int[AbsCSSShorthand.TYPE.values().length];
            $SwitchMap$io$dcloud$feature$uniapp$dom$AbsCSSShorthand$TYPE = iArr;
            try {
                iArr[AbsCSSShorthand.TYPE.MARGIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$dcloud$feature$uniapp$dom$AbsCSSShorthand$TYPE[AbsCSSShorthand.TYPE.PADDING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$dcloud$feature$uniapp$dom$AbsCSSShorthand$TYPE[AbsCSSShorthand.TYPE.BORDER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public AbsComponentData(String str, String str2, String str3) {
        this.mRef = str;
        this.mComponentType = str2;
        this.mParentRef = str3;
    }

    private void addBorder(CSSShorthand.EDGE edge, float f) {
        if (this.mBorders == null) {
            this.mBorders = new CSSShorthand();
        }
        this.mBorders.set(edge, f);
    }

    private void addMargin(CSSShorthand.EDGE edge, float f) {
        if (this.mMargins == null) {
            this.mMargins = new CSSShorthand();
        }
        this.mMargins.set(edge, f);
    }

    private void addPadding(CSSShorthand.EDGE edge, float f) {
        if (this.mPaddings == null) {
            this.mPaddings = new CSSShorthand();
        }
        this.mPaddings.set(edge, f);
    }

    public final void addAttr(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        AbsAttr absAttr = this.mAttributes;
        if (absAttr == null) {
            this.mAttributes = new WXAttr(map, 0);
        } else {
            absAttr.putAll(map);
        }
    }

    public final void addEvent(Set<String> set) {
        if (set == null || set.isEmpty()) {
            return;
        }
        if (this.mEvents == null) {
            this.mEvents = new WXEvent();
        }
        this.mEvents.addAll(set);
    }

    public final void addShorthand(float[] fArr, AbsCSSShorthand.TYPE type) {
        if (fArr == null) {
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        }
        if (fArr.length == 4) {
            int i = AnonymousClass1.$SwitchMap$io$dcloud$feature$uniapp$dom$AbsCSSShorthand$TYPE[type.ordinal()];
            if (i == 1) {
                AbsCSSShorthand absCSSShorthand = this.mMargins;
                if (absCSSShorthand == null) {
                    this.mMargins = new CSSShorthand(fArr);
                    return;
                } else {
                    absCSSShorthand.replace(fArr);
                    return;
                }
            }
            if (i == 2) {
                AbsCSSShorthand absCSSShorthand2 = this.mPaddings;
                if (absCSSShorthand2 == null) {
                    this.mPaddings = new CSSShorthand(fArr);
                    return;
                } else {
                    absCSSShorthand2.replace(fArr);
                    return;
                }
            }
            if (i != 3) {
                return;
            }
            AbsCSSShorthand absCSSShorthand3 = this.mBorders;
            if (absCSSShorthand3 == null) {
                this.mBorders = new CSSShorthand(fArr);
            } else {
                absCSSShorthand3.replace(fArr);
            }
        }
    }

    public void addStyle(Map<String, Object> map) {
        addStyle(map, false);
    }

    @Override // 
    /* renamed from: clone */
    public abstract AbsComponentData<T> mo389clone();

    public AbsAttr getAttrs() {
        if (this.mAttributes == null) {
            this.mAttributes = new WXAttr();
        }
        return this.mAttributes;
    }

    public AbsCSSShorthand getBorder() {
        if (this.mBorders == null) {
            this.mBorders = new CSSShorthand();
        }
        return this.mBorders;
    }

    public AbsEvent getEvents() {
        if (this.mEvents == null) {
            this.mEvents = new WXEvent();
        }
        return this.mEvents;
    }

    public AbsCSSShorthand getMargin() {
        if (this.mMargins == null) {
            this.mMargins = new CSSShorthand();
        }
        return this.mMargins;
    }

    public AbsCSSShorthand getPadding() {
        if (this.mPaddings == null) {
            this.mPaddings = new CSSShorthand();
        }
        return this.mPaddings;
    }

    public long getRenderObjectPr() {
        return this.renderObjectPr;
    }

    public AbsStyle getStyles() {
        if (this.mStyles == null) {
            this.mStyles = new WXStyle();
        }
        return this.mStyles;
    }

    public boolean isRenderPtrEmpty() {
        return this.renderObjectPr == 0;
    }

    public final void setBorders(CSSShorthand cSSShorthand) {
        this.mBorders = cSSShorthand;
    }

    public final void setMargins(CSSShorthand cSSShorthand) {
        this.mMargins = cSSShorthand;
    }

    public final void setPaddings(CSSShorthand cSSShorthand) {
        this.mPaddings = cSSShorthand;
    }

    public synchronized void setRenderObjectPr(long j) {
        long j2 = this.renderObjectPr;
        if (j2 != j) {
            if (j2 == 0) {
                this.renderObjectPr = j;
                return;
            }
            throw new RuntimeException("RenderObjectPr has " + j + " old renderObjectPtr " + this.renderObjectPr);
        }
    }

    public final void addStyle(Map<String, Object> map, boolean z) {
        if (map == null || map.isEmpty()) {
            return;
        }
        AbsStyle absStyle = this.mStyles;
        if (absStyle == null) {
            this.mStyles = new WXStyle(map);
        } else {
            absStyle.putAll(map, z);
        }
    }

    public final void addShorthand(Map<String, String> map) {
        String key;
        if (map == null || map.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            key = it.next().getKey();
            key.getClass();
            key.hashCode();
            switch (key) {
                case "borderRightWidth":
                    addBorder(CSSShorthand.EDGE.RIGHT, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "paddingLeft":
                    addPadding(CSSShorthand.EDGE.LEFT, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "borderTopWidth":
                    addBorder(CSSShorthand.EDGE.TOP, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "borderBottomWidth":
                    addBorder(CSSShorthand.EDGE.BOTTOM, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "margin":
                    addMargin(CSSShorthand.EDGE.ALL, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "marginTop":
                    addMargin(CSSShorthand.EDGE.TOP, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "padding":
                    addPadding(CSSShorthand.EDGE.ALL, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "marginBottom":
                    addMargin(CSSShorthand.EDGE.BOTTOM, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "borderLeftWidth":
                    addBorder(CSSShorthand.EDGE.LEFT, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "paddingTop":
                    addPadding(CSSShorthand.EDGE.TOP, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "paddingBottom":
                    addPadding(CSSShorthand.EDGE.BOTTOM, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "paddingRight":
                    addPadding(CSSShorthand.EDGE.RIGHT, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "borderWidth":
                    addBorder(CSSShorthand.EDGE.ALL, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "marginRight":
                    addMargin(CSSShorthand.EDGE.RIGHT, WXUtils.fastGetFloat(map.get(key)));
                    break;
                case "marginLeft":
                    addMargin(CSSShorthand.EDGE.LEFT, WXUtils.fastGetFloat(map.get(key)));
                    break;
            }
        }
    }
}

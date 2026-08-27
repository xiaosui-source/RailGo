package io.dcloud.uts.component;

import com.taobao.weex.el.parse.Operators;
import kotlin.Metadata;

/* compiled from: UTSSize.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\r\u001a\u00020\u000eH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\n¨\u0006\u000f"}, d2 = {"Lio/dcloud/uts/component/UTSSize;", "", "width", "", "height", "<init>", "(FF)V", "getWidth", "()F", "setWidth", "(F)V", "getHeight", "setHeight", "toString", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSSize {
    private float height;
    private float width;

    public final float getWidth() {
        return this.width;
    }

    public final void setWidth(float f) {
        this.width = f;
    }

    public final float getHeight() {
        return this.height;
    }

    public final void setHeight(float f) {
        this.height = f;
    }

    public UTSSize(float f, float f2) {
        this.width = f;
        this.height = f2;
    }

    public String toString() {
        return "UTSSize(width=" + this.width + ", height=" + this.height + Operators.BRACKET_END;
    }
}

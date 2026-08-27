package kotlin.reflect.jvm.internal.impl.km;

import com.taobao.weex.el.parse.Operators;

/* compiled from: Nodes.kt */
/* loaded from: classes2.dex */
public final class KmVersion {
    private final int major;
    private final int minor;
    private final int patch;

    public final int component1() {
        return this.major;
    }

    public final int component2() {
        return this.minor;
    }

    public final int component3() {
        return this.patch;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KmVersion)) {
            return false;
        }
        KmVersion kmVersion = (KmVersion) obj;
        return this.major == kmVersion.major && this.minor == kmVersion.minor && this.patch == kmVersion.patch;
    }

    public int hashCode() {
        return (((this.major * 31) + this.minor) * 31) + this.patch;
    }

    public KmVersion(int i, int i2, int i3) {
        this.major = i;
        this.minor = i2;
        this.patch = i3;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.major);
        sb.append(Operators.DOT);
        sb.append(this.minor);
        sb.append(Operators.DOT);
        sb.append(this.patch);
        return sb.toString();
    }
}

package io.dcloud.uts;

import com.taobao.weex.el.parse.Operators;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IUTSSourceMap.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u001a"}, d2 = {"Lio/dcloud/uts/UTSSourceMapPosition;", "", "name", "", "file", "line", "", "column", "<init>", "(Ljava/lang/String;Ljava/lang/String;II)V", "getName", "()Ljava/lang/String;", "getFile", "getLine", "()I", "getColumn", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class UTSSourceMapPosition {
    private final int column;
    private final String file;
    private final int line;
    private final String name;

    public static /* synthetic */ UTSSourceMapPosition copy$default(UTSSourceMapPosition uTSSourceMapPosition, String str, String str2, int i, int i2, int i3, java.lang.Object obj) {
        if ((i3 & 1) != 0) {
            str = uTSSourceMapPosition.name;
        }
        if ((i3 & 2) != 0) {
            str2 = uTSSourceMapPosition.file;
        }
        if ((i3 & 4) != 0) {
            i = uTSSourceMapPosition.line;
        }
        if ((i3 & 8) != 0) {
            i2 = uTSSourceMapPosition.column;
        }
        return uTSSourceMapPosition.copy(str, str2, i, i2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final String getFile() {
        return this.file;
    }

    /* renamed from: component3, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    /* renamed from: component4, reason: from getter */
    public final int getColumn() {
        return this.column;
    }

    public final UTSSourceMapPosition copy(String name, String file, int line, int column) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(file, "file");
        return new UTSSourceMapPosition(name, file, line, column);
    }

    public boolean equals(java.lang.Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UTSSourceMapPosition)) {
            return false;
        }
        UTSSourceMapPosition uTSSourceMapPosition = (UTSSourceMapPosition) other;
        return Intrinsics.areEqual(this.name, uTSSourceMapPosition.name) && Intrinsics.areEqual(this.file, uTSSourceMapPosition.file) && this.line == uTSSourceMapPosition.line && this.column == uTSSourceMapPosition.column;
    }

    public int hashCode() {
        return (((((this.name.hashCode() * 31) + this.file.hashCode()) * 31) + this.line) * 31) + this.column;
    }

    public String toString() {
        return "UTSSourceMapPosition(name=" + this.name + ", file=" + this.file + ", line=" + this.line + ", column=" + this.column + Operators.BRACKET_END;
    }

    public UTSSourceMapPosition(String name, String file, int i, int i2) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(file, "file");
        this.name = name;
        this.file = file;
        this.line = i;
        this.column = i2;
    }

    public /* synthetic */ UTSSourceMapPosition(String str, String str2, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? 1 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public final String getName() {
        return this.name;
    }

    public final String getFile() {
        return this.file;
    }

    public final int getLine() {
        return this.line;
    }

    public final int getColumn() {
        return this.column;
    }
}

package io.dcloud.uts;

import com.taobao.weex.adapter.IWXUserTrackAdapter;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: ArrayBuffer.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0015\u001a\u00020\u0010J\u001e\u0010\u0016\u001a\u00020\u00002\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0007R$\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001a"}, d2 = {"Lio/dcloud/uts/ArrayBuffer;", "", "byteLength", "", "<init>", "(I)V", "value", "", "getByteLength", "()Ljava/lang/Number;", "setByteLength", "(Ljava/lang/Number;)V", "byteOffset", "getByteOffset", "setByteOffset", "byteBuffer", "Ljava/nio/ByteBuffer;", "getByteBuffer", "()Ljava/nio/ByteBuffer;", "setByteBuffer", "(Ljava/nio/ByteBuffer;)V", "toByteBuffer", "slice", "begin", "end", "Companion", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ArrayBuffer {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private ByteBuffer byteBuffer;
    private Number byteLength = (Number) 0;
    private Number byteOffset = (Number) 0;

    public final Number getByteLength() {
        return this.byteLength;
    }

    public final void setByteLength(Number value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.byteLength = value;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(value.intValue());
        Intrinsics.checkNotNullExpressionValue(byteBufferAllocate, "allocate(...)");
        this.byteBuffer = byteBufferAllocate;
    }

    public final Number getByteOffset() {
        return this.byteOffset;
    }

    public final void setByteOffset(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.byteOffset = number;
    }

    public final ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    public final void setByteBuffer(ByteBuffer byteBuffer) {
        Intrinsics.checkNotNullParameter(byteBuffer, "<set-?>");
        this.byteBuffer = byteBuffer;
    }

    public ArrayBuffer(int i) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.byteLength.intValue());
        Intrinsics.checkNotNullExpressionValue(byteBufferAllocate, "allocate(...)");
        this.byteBuffer = byteBufferAllocate;
        setByteLength(Integer.valueOf(i));
    }

    public final ByteBuffer toByteBuffer() {
        return this.byteBuffer;
    }

    public static /* synthetic */ ArrayBuffer slice$default(ArrayBuffer arrayBuffer, Number number, Number number2, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            number = null;
        }
        if ((i & 2) != 0) {
            number2 = null;
        }
        return arrayBuffer.slice(number, number2);
    }

    public final ArrayBuffer slice(Number begin, Number end) {
        Number number = this.byteLength;
        if (begin == null) {
            begin = (Number) 0;
        }
        if (end == null) {
            end = number;
        }
        if (NumberKt.compareTo(begin, (Number) 0) < 0) {
            begin = NumberKt.plus(number, begin);
        }
        if (NumberKt.compareTo(end, (Number) 0) < 0) {
            end = NumberKt.plus(number, end);
        }
        int iCoerceIn = RangesKt.coerceIn(begin.intValue(), 0, number.intValue());
        int iCoerceIn2 = RangesKt.coerceIn(end.intValue(), iCoerceIn, number.intValue());
        ArrayBuffer arrayBuffer = new ArrayBuffer(iCoerceIn2 - iCoerceIn);
        while (iCoerceIn < iCoerceIn2) {
            arrayBuffer.byteBuffer.put(this.byteBuffer.get(iCoerceIn));
            iCoerceIn++;
        }
        return arrayBuffer;
    }

    /* compiled from: ArrayBuffer.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0001J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lio/dcloud/uts/ArrayBuffer$Companion;", "", "<init>", "()V", "isView", "", IWXUserTrackAdapter.MONITOR_ARG, "fromByteBuffer", "Lio/dcloud/uts/ArrayBuffer;", "byteBuffer", "Ljava/nio/ByteBuffer;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isView(java.lang.Object arg) {
            Intrinsics.checkNotNullParameter(arg, "arg");
            return (arg instanceof TypedArray) || (arg instanceof DataView);
        }

        public final ArrayBuffer fromByteBuffer(ByteBuffer byteBuffer) {
            Intrinsics.checkNotNullParameter(byteBuffer, "byteBuffer");
            ArrayBuffer arrayBuffer = new ArrayBuffer(byteBuffer.capacity());
            arrayBuffer.setByteBuffer(byteBuffer);
            return arrayBuffer;
        }
    }
}

package io.dcloud.uts;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.UShort;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.lingala.zip4j.util.InternalZipConstants;

/* compiled from: DataView.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0006\u0018\u00002\u00020\u0001B)\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0018\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u001e2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u001e2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010 \u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005J\u000e\u0010 \u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u001eJ\u0018\u0010!\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010!\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u001e2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010\"\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010\"\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u001e2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010#\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005J\u000e\u0010#\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u001eJ\u0018\u0010$\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010$\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u001e2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010%\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010%\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u001e2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u0010&\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u0010&\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020(2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u0010)\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u0010)\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020*2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0016\u0010+\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005J\u0016\u0010+\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020,J\u0010\u0010-\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001eH\u0002J \u0010.\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u0010.\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020/2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0012\u00100\u001a\u00020'2\b\b\u0002\u0010\u001c\u001a\u00020\u001dH\u0002J \u00101\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u00101\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020\u001e2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ\u0016\u00102\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005J\u0016\u00102\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020\u0005J \u00103\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u00103\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020/2\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u00104\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dJ \u00104\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001dR$\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\"\u0010\u0014\u001a\n \u0016*\u0004\u0018\u00010\u00150\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u00065"}, d2 = {"Lio/dcloud/uts/DataView;", "", "buffer", "Lio/dcloud/uts/ArrayBuffer;", "byteOffset", "", "byteLength", "<init>", "(Lio/dcloud/uts/ArrayBuffer;Ljava/lang/Number;Ljava/lang/Number;)V", "value", "getBuffer", "()Lio/dcloud/uts/ArrayBuffer;", "setBuffer", "(Lio/dcloud/uts/ArrayBuffer;)V", "getByteLength", "()Ljava/lang/Number;", "setByteLength", "(Ljava/lang/Number;)V", "getByteOffset", "setByteOffset", "byteBuffer", "Ljava/nio/ByteBuffer;", "kotlin.jvm.PlatformType", "getByteBuffer", "()Ljava/nio/ByteBuffer;", "setByteBuffer", "(Ljava/nio/ByteBuffer;)V", "getFloat32", "littleEndian", "", "", "getFloat64", "getInt8", "getInt16", "getInt32", "getUint8", "getUint16", "getUint32", "setFloat32", "", "", "setFloat64", "", "setInt8", "", "checkRange", "setInt16", "", "setOrder", "setInt32", "setUint8", "setUint16", "setUint32", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class DataView {
    private ArrayBuffer buffer;
    private ByteBuffer byteBuffer;
    private Number byteLength;
    private Number byteOffset;

    public final ArrayBuffer getBuffer() {
        return this.buffer;
    }

    public final void setBuffer(ArrayBuffer value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.buffer = value;
        this.byteBuffer = value.getByteBuffer();
    }

    public final Number getByteLength() {
        return this.byteLength;
    }

    public final void setByteLength(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.byteLength = number;
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
        this.byteBuffer = byteBuffer;
    }

    public /* synthetic */ DataView(ArrayBuffer arrayBuffer, Number number, Number number2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(arrayBuffer, (i & 2) != 0 ? null : number, (i & 4) != 0 ? null : number2);
    }

    public DataView(ArrayBuffer buffer, Number number, Number number2) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.buffer = new ArrayBuffer(0);
        this.byteLength = (Number) 0;
        this.byteOffset = (Number) 0;
        this.byteBuffer = ByteBuffer.allocate(this.byteLength.intValue());
        setBuffer(buffer);
        this.byteOffset = number == null ? (Number) 0 : number;
        this.byteLength = number2 == null ? buffer.getByteLength() : number2;
    }

    public static /* synthetic */ Number getFloat32$default(DataView dataView, Number number, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return dataView.getFloat32(number, z);
    }

    public final Number getFloat32(Number byteOffset, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        return NumberKt.UTSNumber(Float.valueOf(this.byteBuffer.getFloat()));
    }

    public static /* synthetic */ Number getFloat32$default(DataView dataView, int i, boolean z, int i2, java.lang.Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return dataView.getFloat32(i, z);
    }

    public final Number getFloat32(int byteOffset, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        return NumberKt.UTSNumber(Float.valueOf(this.byteBuffer.getFloat()));
    }

    public static /* synthetic */ Number getFloat64$default(DataView dataView, Number number, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return dataView.getFloat64(number, z);
    }

    public final Number getFloat64(Number byteOffset, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        double d = this.byteBuffer.getDouble();
        this.byteBuffer.clear();
        return NumberKt.UTSNumber(Double.valueOf(d));
    }

    public static /* synthetic */ Number getFloat64$default(DataView dataView, int i, boolean z, int i2, java.lang.Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return dataView.getFloat64(i, z);
    }

    public final Number getFloat64(int byteOffset, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        double d = this.byteBuffer.getDouble();
        this.byteBuffer.clear();
        return NumberKt.UTSNumber(Double.valueOf(d));
    }

    public final Number getInt8(Number byteOffset) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        checkRange(byteOffset.intValue());
        return NumberKt.UTSNumber(Byte.valueOf(this.byteBuffer.get()));
    }

    public final Number getInt8(int byteOffset) throws RangeError {
        checkRange(byteOffset);
        return Byte.valueOf(this.byteBuffer.get());
    }

    public static /* synthetic */ Number getInt16$default(DataView dataView, Number number, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return dataView.getInt16(number, z);
    }

    public final Number getInt16(Number byteOffset, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        short s = this.byteBuffer.getShort();
        this.byteBuffer.clear();
        return NumberKt.UTSNumber(Short.valueOf(s));
    }

    public static /* synthetic */ Number getInt16$default(DataView dataView, int i, boolean z, int i2, java.lang.Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return dataView.getInt16(i, z);
    }

    public final Number getInt16(int byteOffset, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        short s = this.byteBuffer.getShort();
        this.byteBuffer.clear();
        return Short.valueOf(s);
    }

    public static /* synthetic */ Number getInt32$default(DataView dataView, Number number, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return dataView.getInt32(number, z);
    }

    public final Number getInt32(Number byteOffset, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        int i = this.byteBuffer.getInt();
        this.byteBuffer.clear();
        return NumberKt.UTSNumber(Integer.valueOf(i));
    }

    public static /* synthetic */ Number getInt32$default(DataView dataView, int i, boolean z, int i2, java.lang.Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return dataView.getInt32(i, z);
    }

    public final Number getInt32(int byteOffset, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        int i = this.byteBuffer.getInt();
        this.byteBuffer.clear();
        return Integer.valueOf(i);
    }

    public final Number getUint8(Number byteOffset) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        checkRange(byteOffset.intValue());
        byte bM552constructorimpl = UByte.m552constructorimpl(this.byteBuffer.get());
        this.byteBuffer.clear();
        return NumberKt.UTSNumber(Integer.valueOf(bM552constructorimpl & 255));
    }

    public final Number getUint8(int byteOffset) throws RangeError {
        checkRange(byteOffset);
        byte bM552constructorimpl = UByte.m552constructorimpl(this.byteBuffer.get());
        this.byteBuffer.clear();
        return Integer.valueOf(bM552constructorimpl & 255);
    }

    public static /* synthetic */ Number getUint16$default(DataView dataView, Number number, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return dataView.getUint16(number, z);
    }

    public final Number getUint16(Number byteOffset, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        short sM817constructorimpl = UShort.m817constructorimpl(this.byteBuffer.getShort());
        this.byteBuffer.clear();
        return NumberKt.UTSNumber(Integer.valueOf(sM817constructorimpl & UShort.MAX_VALUE));
    }

    public static /* synthetic */ Number getUint16$default(DataView dataView, int i, boolean z, int i2, java.lang.Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return dataView.getUint16(i, z);
    }

    public final Number getUint16(int byteOffset, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        short sM817constructorimpl = UShort.m817constructorimpl(this.byteBuffer.getShort());
        this.byteBuffer.clear();
        return Integer.valueOf(sM817constructorimpl & UShort.MAX_VALUE);
    }

    public static /* synthetic */ Number getUint32$default(DataView dataView, Number number, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return dataView.getUint32(number, z);
    }

    public final Number getUint32(Number byteOffset, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        int iM631constructorimpl = UInt.m631constructorimpl(this.byteBuffer.getInt());
        this.byteBuffer.clear();
        return NumberKt.UTSNumber(Long.valueOf(iM631constructorimpl & InternalZipConstants.ZIP_64_SIZE_LIMIT));
    }

    public static /* synthetic */ Number getUint32$default(DataView dataView, int i, boolean z, int i2, java.lang.Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return dataView.getUint32(i, z);
    }

    public final Number getUint32(int byteOffset, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        int iM631constructorimpl = UInt.m631constructorimpl(this.byteBuffer.getInt());
        this.byteBuffer.clear();
        return Long.valueOf(iM631constructorimpl & InternalZipConstants.ZIP_64_SIZE_LIMIT);
    }

    public static /* synthetic */ void setFloat32$default(DataView dataView, Number number, Number number2, boolean z, int i, java.lang.Object obj) throws RangeError {
        if ((i & 4) != 0) {
            z = false;
        }
        dataView.setFloat32(number, number2, z);
    }

    public final void setFloat32(Number byteOffset, Number value, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        this.byteBuffer.putFloat(value.floatValue());
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setFloat32$default(DataView dataView, int i, float f, boolean z, int i2, java.lang.Object obj) throws RangeError {
        if ((i2 & 4) != 0) {
            z = false;
        }
        dataView.setFloat32(i, f, z);
    }

    public final void setFloat32(int byteOffset, float value, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        this.byteBuffer.putFloat(value);
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setFloat64$default(DataView dataView, Number number, Number number2, boolean z, int i, java.lang.Object obj) throws RangeError {
        if ((i & 4) != 0) {
            z = false;
        }
        dataView.setFloat64(number, number2, z);
    }

    public final void setFloat64(Number byteOffset, Number value, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        this.byteBuffer.putDouble(value.doubleValue());
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setFloat64$default(DataView dataView, int i, double d, boolean z, int i2, java.lang.Object obj) throws RangeError {
        if ((i2 & 4) != 0) {
            z = false;
        }
        dataView.setFloat64(i, d, z);
    }

    public final void setFloat64(int byteOffset, double value, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        this.byteBuffer.putDouble(value);
        this.byteBuffer.clear();
    }

    public final void setInt8(Number byteOffset, Number value) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset.intValue());
        this.byteBuffer.put(value.byteValue());
    }

    public final void setInt8(int byteOffset, byte value) throws RangeError {
        checkRange(byteOffset);
        this.byteBuffer.put(value);
    }

    private final void checkRange(int byteOffset) throws RangeError {
        try {
            this.byteBuffer.position(byteOffset);
        } catch (Exception unused) {
            throw new RangeError("Offset is outside the bounds of the DataView");
        }
    }

    public static /* synthetic */ void setInt16$default(DataView dataView, Number number, Number number2, boolean z, int i, java.lang.Object obj) throws RangeError {
        if ((i & 4) != 0) {
            z = false;
        }
        dataView.setInt16(number, number2, z);
    }

    public final void setInt16(Number byteOffset, Number value, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        this.byteBuffer.putShort(value.shortValue());
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setInt16$default(DataView dataView, int i, short s, boolean z, int i2, java.lang.Object obj) throws RangeError {
        if ((i2 & 4) != 0) {
            z = false;
        }
        dataView.setInt16(i, s, z);
    }

    public final void setInt16(int byteOffset, short value, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        this.byteBuffer.putShort(value);
        this.byteBuffer.clear();
    }

    static /* synthetic */ void setOrder$default(DataView dataView, boolean z, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        dataView.setOrder(z);
    }

    private final void setOrder(boolean littleEndian) {
        this.byteBuffer.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
    }

    public static /* synthetic */ void setInt32$default(DataView dataView, Number number, Number number2, boolean z, int i, java.lang.Object obj) throws RangeError {
        if ((i & 4) != 0) {
            z = false;
        }
        dataView.setInt32(number, number2, z);
    }

    public final void setInt32(Number byteOffset, Number value, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        this.byteBuffer.putInt(value.intValue());
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setInt32$default(DataView dataView, int i, int i2, boolean z, int i3, java.lang.Object obj) throws RangeError {
        if ((i3 & 4) != 0) {
            z = false;
        }
        dataView.setInt32(i, i2, z);
    }

    public final void setInt32(int byteOffset, int value, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        this.byteBuffer.putInt(value);
        this.byteBuffer.clear();
    }

    public final void setUint8(Number byteOffset, Number value) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset.intValue());
        this.byteBuffer.put(value.byteValue());
        this.byteBuffer.clear();
    }

    public final void setUint8(int byteOffset, Number value) throws RangeError {
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset);
        this.byteBuffer.put(value.byteValue());
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setUint16$default(DataView dataView, Number number, Number number2, boolean z, int i, java.lang.Object obj) throws RangeError {
        if ((i & 4) != 0) {
            z = false;
        }
        dataView.setUint16(number, number2, z);
    }

    public final void setUint16(Number byteOffset, Number value, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        this.byteBuffer.putShort(value.shortValue());
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setUint16$default(DataView dataView, int i, short s, boolean z, int i2, java.lang.Object obj) throws RangeError {
        if ((i2 & 4) != 0) {
            z = false;
        }
        dataView.setUint16(i, s, z);
    }

    public final void setUint16(int byteOffset, short value, boolean littleEndian) throws RangeError {
        checkRange(byteOffset);
        setOrder(littleEndian);
        this.byteBuffer.putShort(value);
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setUint32$default(DataView dataView, Number number, Number number2, boolean z, int i, java.lang.Object obj) throws RangeError {
        if ((i & 4) != 0) {
            z = false;
        }
        dataView.setUint32(number, number2, z);
    }

    public final void setUint32(Number byteOffset, Number value, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(byteOffset, "byteOffset");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset.intValue());
        setOrder(littleEndian);
        this.byteBuffer.putInt(value.intValue());
        this.byteBuffer.clear();
    }

    public static /* synthetic */ void setUint32$default(DataView dataView, int i, Number number, boolean z, int i2, java.lang.Object obj) throws RangeError {
        if ((i2 & 4) != 0) {
            z = false;
        }
        dataView.setUint32(i, number, z);
    }

    public final void setUint32(int byteOffset, Number value, boolean littleEndian) throws RangeError {
        Intrinsics.checkNotNullParameter(value, "value");
        checkRange(byteOffset);
        setOrder(littleEndian);
        this.byteBuffer.putInt(value.intValue());
        this.byteBuffer.clear();
    }
}

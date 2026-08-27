package io.dcloud.uts;

import com.taobao.weex.common.Constants;
import io.dcloud.uts.utils.IndexKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UShort;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: Uint16Array.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010(\n\u0002\b\u0002\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u0017\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0004\b\u0004\u0010\bB\u0011\b\u0016\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u0004\u0010\u000bB)\b\u0016\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\rJ\b\u0010\u0013\u001a\u00020\u0003H\u0016J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0003H\u0016J_\u0010\u0016\u001a\u00020\u0000\"\b\b\u0000\u0010\u0017*\u00020\u00012K\u0010\u0018\u001aG\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0013\u0012\u0011H\u0017¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u001d0\u0019H\u0016J\u001f\u0010\u001e\u001a\u00020\u001f2\b\u0010\u001c\u001a\u0004\u0018\u00010 2\u0006\u0010\u0015\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010!J\u0010\u0010\"\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020 H\u0016J_\u0010#\u001a\u00020\u0000\"\b\b\u0000\u0010\u0017*\u00020\u00012K\u0010$\u001aG\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0013\u0012\u0011H\u0017¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00030\u0019H\u0016J\u001c\u0010%\u001a\u00020\u00002\b\u0010&\u001a\u0004\u0018\u00010\u00032\b\u0010'\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010(\u001a\u00020\u00002\b\u0010)\u001a\u0004\u0018\u00010\u00032\b\u0010'\u001a\u0004\u0018\u00010\u0003H\u0016J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00030+H\u0096\u0002R\u001c\u0010\u000e\u001a\u00020\u0003X\u0096\u000e¢\u0006\u0010\n\u0002\b\u0012\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0005¨\u0006-"}, d2 = {"Lio/dcloud/uts/Uint16Array;", "Lio/dcloud/uts/TypedArray;", "length", "", "<init>", "(Ljava/lang/Number;)V", "array", "", "(Ljava/util/Collection;)V", "buffer", "Lio/dcloud/uts/ArrayBuffer;", "(Lio/dcloud/uts/ArrayBuffer;)V", "byteOffset", "(Lio/dcloud/uts/ArrayBuffer;Ljava/lang/Number;Ljava/lang/Number;)V", "BYTES_PER_ELEMENT", "getBYTES_PER_ELEMENT", "()Ljava/lang/Number;", "setBYTES_PER_ELEMENT", "BYTES_PER_ELEMENT$1", "getBytesPerElement", "convertValue", "value", Constants.Name.FILTER, "T", "predicate", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "index", "", "putAuto", "", "", "(Ljava/lang/Integer;Ljava/lang/Number;)V", "getAuto", "map", "callbackfn", "slice", "start", "end", "subarray", "begin", "iterator", "", "Companion", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class Uint16Array extends TypedArray {
    public static final int BYTES_PER_ELEMENT = 2;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* renamed from: BYTES_PER_ELEMENT$1, reason: from kotlin metadata */
    private Number BYTES_PER_ELEMENT;

    @Override // io.dcloud.uts.TypedArray
    public Number convertValue(Number value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return value;
    }

    @Override // io.dcloud.uts.TypedArray
    public Number getBYTES_PER_ELEMENT() {
        return this.BYTES_PER_ELEMENT;
    }

    @Override // io.dcloud.uts.TypedArray
    public void setBYTES_PER_ELEMENT(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.BYTES_PER_ELEMENT = number;
    }

    @Override // io.dcloud.uts.TypedArray
    public Number getBytesPerElement() {
        return (Number) 2;
    }

    /* compiled from: Uint16Array.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0004\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001f\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\"\u00020\n¢\u0006\u0002\u0010\u000bJ2\u0010\f\u001a\u00020\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000e2\u001c\b\u0002\u0010\u000f\u001a\u0016\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lio/dcloud/uts/Uint16Array$Companion;", "", "<init>", "()V", "BYTES_PER_ELEMENT", "", "of", "Lio/dcloud/uts/Uint16Array;", "items", "", "", "([Ljava/lang/Number;)Lio/dcloud/uts/Uint16Array;", "from", "arrayLike", "", "mapFn", "Lkotlin/Function2;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Uint16Array of(Number... items) {
            Intrinsics.checkNotNullParameter(items, "items");
            Uint16Array uint16Array = new Uint16Array(Integer.valueOf(items.length));
            ArrayList arrayList = new ArrayList(items.length);
            for (Number number : items) {
                TypedArray.putAuto$default(uint16Array, null, Short.valueOf(number.shortValue()), 1, null);
                arrayList.add(Unit.INSTANCE);
            }
            return uint16Array;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Uint16Array from$default(Companion companion, Collection collection, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.from(collection, function2);
        }

        public final Uint16Array from(Collection<? extends Number> arrayLike, Function2<? super Number, ? super Number, ? extends Number> mapFn) {
            Intrinsics.checkNotNullParameter(arrayLike, "arrayLike");
            Uint16Array uint16Array = new Uint16Array(Integer.valueOf(arrayLike.size()));
            int i = 0;
            for (Number number : arrayLike) {
                if (mapFn != null) {
                    TypedArray.putAuto$default(uint16Array, null, Short.valueOf(mapFn.invoke(number, Integer.valueOf(i)).shortValue()), 1, null);
                } else {
                    TypedArray.putAuto$default(uint16Array, null, Short.valueOf(number.shortValue()), 1, null);
                }
                i++;
            }
            return uint16Array;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Uint16Array(Number length) {
        super(length);
        Intrinsics.checkNotNullParameter(length, "length");
        this.BYTES_PER_ELEMENT = (Number) 2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Uint16Array(Collection<? extends Number> array) {
        super(array);
        Intrinsics.checkNotNullParameter(array, "array");
        this.BYTES_PER_ELEMENT = (Number) 2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Uint16Array(ArrayBuffer buffer) {
        super(buffer);
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.BYTES_PER_ELEMENT = (Number) 2;
    }

    public /* synthetic */ Uint16Array(ArrayBuffer arrayBuffer, Number number, Number number2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(arrayBuffer, (i & 2) != 0 ? null : number, (i & 4) != 0 ? null : number2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Uint16Array(ArrayBuffer buffer, Number number, Number number2) {
        super(buffer, number, number2);
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.BYTES_PER_ELEMENT = (Number) 2;
    }

    @Override // io.dcloud.uts.TypedArray
    public <T extends TypedArray> Uint16Array filter(Function3<? super Number, ? super Number, ? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        UTSArray uTSArray = new UTSArray();
        int size = size();
        for (int i = 0; i < size; i++) {
            short sShortValue = getAuto(i).shortValue();
            Short shValueOf = Short.valueOf(sShortValue);
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.Uint16Array.filter");
            if (predicate.invoke(shValueOf, numValueOf, this).booleanValue()) {
                uTSArray.add(Short.valueOf(sShortValue));
            }
        }
        return new Uint16Array(uTSArray);
    }

    @Override // io.dcloud.uts.TypedArray
    public void putAuto(Integer index, Number value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (index != null) {
            getByteBuffer().putShort(index.intValue() * getBYTES_PER_ELEMENT().intValue(), value.shortValue());
        } else {
            getByteBuffer().putShort(value.shortValue());
        }
    }

    @Override // io.dcloud.uts.TypedArray
    public Number getAuto(int index) {
        return Integer.valueOf(UShort.m817constructorimpl(getByteBuffer().getShort(index * getBYTES_PER_ELEMENT().intValue())) & UShort.MAX_VALUE);
    }

    @Override // io.dcloud.uts.TypedArray
    public <T extends TypedArray> Uint16Array map(Function3<? super Number, ? super Number, ? super T, ? extends Number> callbackfn) {
        Intrinsics.checkNotNullParameter(callbackfn, "callbackfn");
        int size = size();
        UTSArray uTSArray = new UTSArray();
        for (int i = 0; i < size; i++) {
            Number auto = getAuto(i);
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.Uint16Array.map");
            uTSArray.add(Short.valueOf(callbackfn.invoke(auto, numValueOf, this).shortValue()));
        }
        return new Uint16Array(uTSArray);
    }

    @Override // io.dcloud.uts.TypedArray
    public Uint16Array slice(Number start, Number end) {
        if (start == null) {
            start = (Number) 0;
        }
        if (end == null) {
            end = getLength();
        }
        UTSArray uTSArray = new UTSArray();
        int sliceIndex = IndexKt.toSliceIndex(end, getLength().intValue());
        for (int sliceIndex2 = IndexKt.toSliceIndex(start, getLength().intValue()); sliceIndex2 < sliceIndex; sliceIndex2++) {
            uTSArray.add(Short.valueOf(getAuto(sliceIndex2).shortValue()));
        }
        return new Uint16Array(uTSArray);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
    @Override // io.dcloud.uts.TypedArray
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public io.dcloud.uts.Uint16Array subarray(java.lang.Number r5, java.lang.Number r6) {
        /*
            r4 = this;
            r0 = 0
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            if (r5 == 0) goto L1a
            r2 = r1
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = io.dcloud.uts.NumberKt.compareTo(r5, r2)
            if (r2 >= 0) goto L18
            java.lang.Number r2 = r4.getLength()
            java.lang.Number r5 = io.dcloud.uts.NumberKt.plus(r2, r5)
        L18:
            if (r5 != 0) goto L1d
        L1a:
            r5 = r1
            java.lang.Number r5 = (java.lang.Number) r5
        L1d:
            if (r6 == 0) goto L31
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = io.dcloud.uts.NumberKt.compareTo(r6, r1)
            if (r1 >= 0) goto L2f
            java.lang.Number r1 = r4.getLength()
            java.lang.Number r6 = io.dcloud.uts.NumberKt.plus(r1, r6)
        L2f:
            if (r6 != 0) goto L35
        L31:
            java.lang.Number r6 = r4.getLength()
        L35:
            int r5 = r5.intValue()
            java.lang.Number r1 = r4.getLength()
            int r1 = r1.intValue()
            int r5 = kotlin.ranges.RangesKt.coerceIn(r5, r0, r1)
            int r6 = r6.intValue()
            java.lang.Number r1 = r4.getLength()
            int r1 = r1.intValue()
            int r6 = kotlin.ranges.RangesKt.coerceIn(r6, r5, r1)
            io.dcloud.uts.UTSArray r1 = new io.dcloud.uts.UTSArray
            r1.<init>()
        L5a:
            if (r5 >= r6) goto L73
            java.lang.Number r2 = r4.getAuto(r5)
            short r2 = r2.shortValue()
            java.lang.Short r2 = java.lang.Short.valueOf(r2)
            r3 = 1
            java.lang.Short[] r3 = new java.lang.Short[r3]
            r3[r0] = r2
            r1.push(r3)
            int r5 = r5 + 1
            goto L5a
        L73:
            io.dcloud.uts.Uint16Array r5 = new io.dcloud.uts.Uint16Array
            java.util.Collection r1 = (java.util.Collection) r1
            r5.<init>(r1)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.uts.Uint16Array.subarray(java.lang.Number, java.lang.Number):io.dcloud.uts.Uint16Array");
    }

    @Override // io.dcloud.uts.TypedArray, java.util.Collection, java.lang.Iterable
    public Iterator<Number> iterator() {
        getByteBuffer().rewind();
        return new AnonymousClass1();
    }

    /* compiled from: Uint16Array.kt */
    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0010(\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0003\u001a\u00020\u0004H\u0096\u0002J\t\u0010\u0005\u001a\u00020\u0002H\u0096\u0002¨\u0006\u0006"}, d2 = {"io/dcloud/uts/Uint16Array$iterator$1", "", "", "hasNext", "", "next", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    /* renamed from: io.dcloud.uts.Uint16Array$iterator$1, reason: invalid class name */
    public static final class AnonymousClass1 implements Iterator<Number>, KMappedMarker {
        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        AnonymousClass1() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return Uint16Array.this.getByteBuffer().remaining() > 0;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public Number next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return Integer.valueOf(UShort.m817constructorimpl(Uint16Array.this.getByteBuffer().getShort()) & UShort.MAX_VALUE);
        }
    }
}

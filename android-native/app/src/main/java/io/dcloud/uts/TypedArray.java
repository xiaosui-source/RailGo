package io.dcloud.uts;

import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.taobao.weex.common.Constants;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.RangesKt;

/* compiled from: TypedArray.kt */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u0004\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u0017\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\u0004\b\u0004\u0010\u0007B\u0011\b\u0016\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\nB)\b\u0016\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0002\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\fJ\b\u0010\"\u001a\u00020\u0002H&J\u0010\u0010#\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002H&J\b\u0010(\u001a\u00020)H\u0016J\u0011\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010,\u001a\u00020)2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016J\b\u0010.\u001a\u00020/H\u0002J$\u00100\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u00020\u00022\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u00104\u001a\u00020\u00022\u0006\u00105\u001a\u00020%H&J!\u00106\u001a\u00020/2\n\b\u0002\u00105\u001a\u0004\u0018\u00010%2\u0006\u0010\u0015\u001a\u00020\u0002H&¢\u0006\u0002\u00107J_\u00108\u001a\u00020)\"\b\b\u0000\u00109*\u00020\u00002K\u0010:\u001aG\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(5\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020)0;H\u0016J&\u0010>\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u00022\n\b\u0002\u00102\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u00103\u001a\u00020\u0002H\u0016J_\u0010?\u001a\u00020\u0000\"\b\b\u0000\u00109*\u00020\u00002K\u0010:\u001aG\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(5\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020)0;H&Ja\u0010@\u001a\u0004\u0018\u00010\u0002\"\b\b\u0000\u00109*\u00020\u00002K\u0010:\u001aG\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(5\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020)0;H\u0016J_\u0010A\u001a\u00020%\"\b\b\u0000\u00109*\u00020\u00002K\u0010:\u001aG\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(5\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020)0;H\u0016J_\u0010B\u001a\u00020/\"\b\b\u0000\u00109*\u00020\u00002K\u0010C\u001aG\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110%¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(5\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020/0;H\u0016J\u0014\u0010D\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020F0EH\u0016J\u001a\u0010G\u001a\u00020)2\u0006\u0010H\u001a\u00020\u00022\b\b\u0002\u0010I\u001a\u00020\u0002H\u0016J\u001a\u0010J\u001a\u00020\u00022\u0006\u0010H\u001a\u00020\u00022\b\b\u0002\u0010I\u001a\u00020\u0002H\u0016J\u0012\u0010K\u001a\u00020L2\b\b\u0002\u0010M\u001a\u00020LH\u0016J\u000e\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00020EH\u0016J_\u0010O\u001a\u00020\u0000\"\b\b\u0000\u00109*\u00020\u00002K\u0010C\u001aG\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(5\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00020;H&J~\u0010P\u001a\u00020\u0002\"\b\b\u0000\u00109*\u00020\u00002`\u0010C\u001a\\\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(R\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(S\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(T\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00020Q2\n\b\u0002\u0010U\u001a\u0004\u0018\u00010\u0002J~\u0010V\u001a\u00020\u0002\"\b\b\u0000\u00109*\u00020\u00002`\u0010C\u001a\\\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(R\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(S\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(T\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00020Q2\n\b\u0002\u0010U\u001a\u0004\u0018\u00010\u0002J\u0006\u0010W\u001a\u00020\u0000J\u001e\u0010X\u001a\u00020/2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\b\b\u0002\u0010Y\u001a\u00020%J \u0010Z\u001a\u00020\u00002\n\b\u0002\u00102\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u0002H&J]\u0010[\u001a\u00020)\"\b\b\u0000\u00109*\u00020\u00002K\u0010\\\u001aG\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(5\u0012\u0013\u0012\u0011H9¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020)0;JB\u0010]\u001a\u00020\u00002:\b\u0002\u0010^\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(`\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b<\u0012\b\b=\u0012\u0004\b\b(a\u0012\u0004\u0012\u00020\u0002\u0018\u00010_J \u0010b\u001a\u00020\u00002\n\b\u0002\u0010c\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u0002H&J\f\u0010d\u001a\b\u0012\u0004\u0012\u00020\u00020EJ\u0019\u0010X\u001a\u00020/2\u0006\u00105\u001a\u00020\u00022\u0006\u0010+\u001a\u00020\u0002H\u0086\u0002J\u0011\u0010e\u001a\u00020\u00022\u0006\u00105\u001a\u00020\u0002H\u0086\u0002J\b\u0010f\u001a\u00020LH\u0016R\u001a\u0010\u0003\u001a\u00020\u0002X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0005R\u001a\u0010\u0010\u001a\u00020\u0002X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0005R\u001a\u0010\u000b\u001a\u00020\u0002X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0005R(\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\u0015\u001a\u0004\u0018\u00010\t@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\nR\u001a\u0010\u0019\u001a\u00020\u001aX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\u00020\u0002X¦\u000e¢\u0006\f\u001a\u0004\b \u0010\u000e\"\u0004\b!\u0010\u0005R\u0014\u0010$\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'¨\u0006g"}, d2 = {"Lio/dcloud/uts/TypedArray;", "", "", "length", "<init>", "(Ljava/lang/Number;)V", "array", "(Ljava/util/Collection;)V", "buffer", "Lio/dcloud/uts/ArrayBuffer;", "(Lio/dcloud/uts/ArrayBuffer;)V", "byteOffset", "(Lio/dcloud/uts/ArrayBuffer;Ljava/lang/Number;Ljava/lang/Number;)V", "getLength", "()Ljava/lang/Number;", "setLength", "byteLength", "getByteLength", "setByteLength", "getByteOffset", "setByteOffset", "value", "getBuffer", "()Lio/dcloud/uts/ArrayBuffer;", "setBuffer", "byteBuffer", "Ljava/nio/ByteBuffer;", "getByteBuffer", "()Ljava/nio/ByteBuffer;", "setByteBuffer", "(Ljava/nio/ByteBuffer;)V", "BYTES_PER_ELEMENT", "getBYTES_PER_ELEMENT", "setBYTES_PER_ELEMENT", "getBytesPerElement", "convertValue", AbsoluteConst.JSON_KEY_SIZE, "", "getSize", "()I", "isEmpty", "", "contains", BindingXConstants.KEY_ELEMENT, "containsAll", "elements", "reset", "", "copyWithin", IApp.ConfigProperty.CONFIG_TARGET, "start", "end", "getAuto", "index", "putAuto", "(Ljava/lang/Integer;Ljava/lang/Number;)V", "every", "T", "predicate", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "fill", Constants.Name.FILTER, "find", "findIndex", "forEach", "callbackfn", "entries", "Lio/dcloud/uts/IterableIterator;", "", "includes", "searchElement", "fromIndex", "indexOf", "join", "", "separator", "keys", "map", "reduce", "Lkotlin/Function4;", "previousValue", "currentValue", "currentIndex", "initialValue", "reduceRight", "reverse", "set", "offset", "slice", "some", "callbackFn", "sort", "compareFn", "Lkotlin/Function2;", "a", "b", "subarray", "begin", "values", "get", "toString", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class TypedArray implements Collection<Number>, KMappedMarker {
    private ArrayBuffer buffer;
    private ByteBuffer byteBuffer;
    private Number byteLength;
    private Number byteOffset;
    private Number length;

    /* renamed from: add, reason: avoid collision after fix types in other method */
    public boolean add2(Number number) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public /* bridge */ /* synthetic */ boolean add(Number number) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean addAll(Collection<? extends Number> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean contains(Number element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return false;
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<?> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return false;
    }

    public abstract Number convertValue(Number value);

    public abstract <T extends TypedArray> TypedArray filter(Function3<? super Number, ? super Number, ? super T, Boolean> predicate);

    public abstract Number getAuto(int index);

    public abstract Number getBYTES_PER_ELEMENT();

    public abstract Number getBytesPerElement();

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator<Number> iterator() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public abstract <T extends TypedArray> TypedArray map(Function3<? super Number, ? super Number, ? super T, ? extends Number> callbackfn);

    public abstract void putAuto(Integer index, Number value);

    @Override // java.util.Collection
    public boolean remove(java.lang.Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean removeIf(Predicate<? super Number> predicate) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public abstract void setBYTES_PER_ELEMENT(Number number);

    public abstract TypedArray slice(Number start, Number end);

    public abstract TypedArray subarray(Number begin, Number end);

    @Override // java.util.Collection
    public java.lang.Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Collection
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return (T[]) CollectionToArray.toArray(this, array);
    }

    @Override // java.util.Collection
    public final /* bridge */ boolean contains(java.lang.Object obj) {
        if (obj instanceof Number) {
            return contains((Number) obj);
        }
        return false;
    }

    @Override // java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    public final Number getLength() {
        return this.length;
    }

    public final void setLength(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.length = number;
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

    public final ArrayBuffer getBuffer() {
        return this.buffer;
    }

    public final void setBuffer(ArrayBuffer arrayBuffer) {
        ByteBuffer byteBufferAllocate;
        this.buffer = arrayBuffer;
        if (arrayBuffer == null || (byteBufferAllocate = arrayBuffer.getByteBuffer()) == null) {
            byteBufferAllocate = ByteBuffer.allocate(this.byteLength.intValue());
            Intrinsics.checkNotNullExpressionValue(byteBufferAllocate, "allocate(...)");
        }
        this.byteBuffer = byteBufferAllocate;
    }

    protected final ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    protected final void setByteBuffer(ByteBuffer byteBuffer) {
        Intrinsics.checkNotNullParameter(byteBuffer, "<set-?>");
        this.byteBuffer = byteBuffer;
    }

    public int getSize() {
        return this.length.intValue();
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return Intrinsics.areEqual((java.lang.Object) this.length, (java.lang.Object) 0);
    }

    public TypedArray(Number length) {
        Intrinsics.checkNotNullParameter(length, "length");
        this.length = (Number) 0;
        this.byteLength = (Number) 0;
        this.byteOffset = (Number) 0;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(0);
        Intrinsics.checkNotNullExpressionValue(byteBufferAllocate, "allocate(...)");
        this.byteBuffer = byteBufferAllocate;
        Integer numValueOf = Integer.valueOf(length.intValue());
        this.length = numValueOf;
        this.byteLength = NumberKt.times(numValueOf, getBytesPerElement());
        setBuffer(new ArrayBuffer(this.byteLength.intValue()));
    }

    public TypedArray(Collection<? extends Number> array) {
        Intrinsics.checkNotNullParameter(array, "array");
        this.length = (Number) 0;
        this.byteLength = (Number) 0;
        this.byteOffset = (Number) 0;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(0);
        Intrinsics.checkNotNullExpressionValue(byteBufferAllocate, "allocate(...)");
        this.byteBuffer = byteBufferAllocate;
        if (array instanceof TypedArray) {
            ((TypedArray) array).reset();
        }
        Integer numValueOf = Integer.valueOf(array.size());
        this.length = numValueOf;
        this.byteLength = NumberKt.times(numValueOf, getBytesPerElement());
        setBuffer(new ArrayBuffer(this.byteLength.intValue()));
        Iterator<T> it = array.iterator();
        while (it.hasNext()) {
            putAuto$default(this, null, (Number) it.next(), 1, null);
        }
    }

    private final void reset() {
        this.byteBuffer.flip();
    }

    public TypedArray(ArrayBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.length = (Number) 0;
        this.byteLength = (Number) 0;
        this.byteOffset = (Number) 0;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(0);
        Intrinsics.checkNotNullExpressionValue(byteBufferAllocate, "allocate(...)");
        this.byteBuffer = byteBufferAllocate;
        this.byteLength = buffer.getByteLength();
        setBuffer(buffer);
        this.length = NumberKt.div(buffer.getByteLength(), getBytesPerElement());
    }

    public /* synthetic */ TypedArray(ArrayBuffer arrayBuffer, Number number, Number number2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(arrayBuffer, (i & 2) != 0 ? null : number, (i & 4) != 0 ? null : number2);
    }

    public TypedArray(ArrayBuffer buffer, Number number, Number number2) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.length = (Number) 0;
        this.byteLength = (Number) 0;
        this.byteOffset = (Number) 0;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(0);
        Intrinsics.checkNotNullExpressionValue(byteBufferAllocate, "allocate(...)");
        this.byteBuffer = byteBufferAllocate;
        Number byteLength = buffer.getByteLength();
        if (number != null) {
            this.byteOffset = Integer.valueOf(number.intValue());
        }
        if (NumberKt.compareTo(this.byteOffset, (Number) 0) < 0 || NumberKt.compareTo(this.byteOffset, byteLength) > 0) {
            throw new IllegalArgumentException("byteOffset is out of bounds.");
        }
        this.length = number2 == null ? NumberKt.div(NumberKt.minus(byteLength, this.byteOffset), Integer.valueOf(getBytesPerElement().intValue())) : number2;
        setBuffer(buffer);
    }

    public static /* synthetic */ TypedArray copyWithin$default(TypedArray typedArray, Number number, Number number2, Number number3, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: copyWithin");
        }
        if ((i & 4) != 0) {
            number3 = typedArray.byteLength;
        }
        return typedArray.copyWithin(number, number2, number3);
    }

    public TypedArray copyWithin(Number target, Number start, Number end) {
        int iMax;
        int iMax2;
        int iMax3;
        int i;
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(start, "start");
        Number number = this.length;
        if (Intrinsics.areEqual((java.lang.Object) target, (java.lang.Object) Integer.MIN_VALUE)) {
            iMax = 0;
        } else {
            iMax = NumberKt.compareTo(target, (Number) 0) < 0 ? java.lang.Math.max(number.intValue() + target.intValue(), 0) : java.lang.Math.min(target.intValue(), number.intValue());
        }
        if (Intrinsics.areEqual((java.lang.Object) start, (java.lang.Object) Integer.MIN_VALUE)) {
            iMax2 = 0;
        } else {
            iMax2 = NumberKt.compareTo(start, (Number) 0) < 0 ? java.lang.Math.max(number.intValue() + start.intValue(), 0) : java.lang.Math.min(start.intValue(), number.intValue());
        }
        if (Intrinsics.areEqual((java.lang.Object) end, (java.lang.Object) Integer.MIN_VALUE)) {
            iMax3 = 0;
        } else {
            Intrinsics.checkNotNull(end);
            iMax3 = NumberKt.compareTo(end, (Number) 0) < 0 ? java.lang.Math.max(number.intValue() + end.intValue(), 0) : java.lang.Math.min(end.intValue(), number.intValue());
        }
        int iMin = java.lang.Math.min(iMax3 - iMax2, number.intValue() - iMax);
        if (iMin > 0) {
            int iIntValue = number.intValue();
            if (iMax2 >= iMax || iMax >= iMax2 + iMin) {
                i = 1;
            } else {
                int i2 = iMin - 1;
                iMax2 += i2;
                iMax += i2;
                i = -1;
            }
            while (iMin > 0) {
                if (iMax2 >= iIntValue || iMax >= iIntValue) {
                    iMin = 0;
                } else {
                    putAuto(Integer.valueOf(iMax), getAuto(iMax2));
                    iMax2 += i;
                    iMax += i;
                    iMin--;
                }
            }
        }
        return this;
    }

    public static /* synthetic */ void putAuto$default(TypedArray typedArray, Integer num, Number number, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: putAuto");
        }
        if ((i & 1) != 0) {
            num = null;
        }
        typedArray.putAuto(num, number);
    }

    public <T extends TypedArray> boolean every(Function3<? super Number, ? super Number, ? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Number number = this.length;
        for (int i = 0; i < number.intValue(); i++) {
            if (i < number.intValue()) {
                Number auto = getAuto(i);
                Integer numValueOf = Integer.valueOf(i);
                Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.TypedArray.every");
                if (!predicate.invoke(auto, numValueOf, this).booleanValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static /* synthetic */ TypedArray fill$default(TypedArray typedArray, Number number, Number number2, Number number3, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: fill");
        }
        if ((i & 2) != 0) {
            number2 = (Number) 0;
        }
        if ((i & 4) != 0) {
            number3 = typedArray.length;
        }
        return typedArray.fill(number, number2, number3);
    }

    public TypedArray fill(Number value, Number start, Number end) {
        int iMin;
        int iMin2;
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(end, "end");
        Number number = this.length;
        int iIntValue = start != null ? start.intValue() : 0;
        if (iIntValue < 0) {
            iMin = java.lang.Math.max(number.intValue() + iIntValue, 0);
        } else {
            iMin = java.lang.Math.min(iIntValue, number.intValue());
        }
        int iIntValue2 = end.intValue();
        if (iIntValue2 < 0) {
            iMin2 = java.lang.Math.max(number.intValue() + iIntValue2, 0);
        } else {
            iMin2 = java.lang.Math.min(iIntValue2, number.intValue());
        }
        while (iMin < iMin2) {
            putAuto(Integer.valueOf(iMin), value);
            iMin++;
        }
        return this;
    }

    public <T extends TypedArray> Number find(Function3<? super Number, ? super Number, ? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int size = size();
        for (int i = 0; i < size; i++) {
            Number auto = getAuto(i);
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.TypedArray.find");
            if (predicate.invoke(auto, numValueOf, this).booleanValue()) {
                return NumberKt.UTSNumber(auto);
            }
        }
        return null;
    }

    public <T extends TypedArray> int findIndex(Function3<? super Number, ? super Number, ? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int size = size();
        for (int i = 0; i < size; i++) {
            Number auto = getAuto(i);
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.TypedArray.findIndex");
            if (predicate.invoke(auto, numValueOf, this).booleanValue()) {
                return i;
            }
        }
        return -1;
    }

    public <T extends TypedArray> void forEach(Function3<? super Number, ? super Integer, ? super T, Unit> callbackfn) {
        Intrinsics.checkNotNullParameter(callbackfn, "callbackfn");
        for (int i = 0; NumberKt.compareTo(Integer.valueOf(i), this.length) < 0; i++) {
            Number auto = getAuto(i);
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.TypedArray.forEach");
            callbackfn.invoke(auto, numValueOf, this);
        }
    }

    public IterableIterator<List<Number>> entries() {
        return (IterableIterator) new IterableIterator<List<? extends Number>>() { // from class: io.dcloud.uts.TypedArray.entries.1
            private int index;

            private final boolean hasNext() {
                return NumberKt.compareTo(Integer.valueOf(this.index), TypedArray.this.getLength()) < 0;
            }

            @Override // io.dcloud.uts.IterableIterator
            public IteratorReturnResult<List<? extends Number>> next() {
                ArrayList arrayList = new ArrayList();
                if (!hasNext()) {
                    return new IteratorReturnResult<>(true, arrayList);
                }
                arrayList.add(NumberKt.UTSNumber(Integer.valueOf(this.index)));
                TypedArray typedArray = TypedArray.this;
                int i = this.index;
                this.index = i + 1;
                arrayList.add(NumberKt.UTSNumber(typedArray.getAuto(i)));
                return new IteratorReturnResult<>(true, arrayList);
            }
        };
    }

    public static /* synthetic */ boolean includes$default(TypedArray typedArray, Number number, Number number2, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: includes");
        }
        if ((i & 2) != 0) {
            number2 = (Number) 0;
        }
        return typedArray.includes(number, number2);
    }

    public boolean includes(Number searchElement, Number fromIndex) {
        Intrinsics.checkNotNullParameter(searchElement, "searchElement");
        Intrinsics.checkNotNullParameter(fromIndex, "fromIndex");
        if (NumberKt.compareTo(fromIndex, (Number) 0) < 0) {
            fromIndex = Integer.valueOf(RangesKt.coerceAtLeast(NumberKt.plus(this.length, fromIndex).intValue(), 0));
        }
        if (NumberKt.compareTo(fromIndex, this.length) >= 0) {
            return false;
        }
        int iIntValue = this.length.intValue();
        for (int iIntValue2 = fromIndex.intValue(); iIntValue2 < iIntValue; iIntValue2++) {
            if (Intrinsics.areEqual(getAuto(iIntValue2), convertValue(searchElement))) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ Number indexOf$default(TypedArray typedArray, Number number, Number number2, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
        }
        if ((i & 2) != 0) {
            number2 = (Number) 0;
        }
        return typedArray.indexOf(number, number2);
    }

    public Number indexOf(Number searchElement, Number fromIndex) {
        Intrinsics.checkNotNullParameter(searchElement, "searchElement");
        Intrinsics.checkNotNullParameter(fromIndex, "fromIndex");
        if (NumberKt.compareTo(fromIndex, (Number) 0) < 0) {
            fromIndex = Integer.valueOf(RangesKt.coerceAtLeast(NumberKt.plus(this.length, fromIndex).intValue(), 0));
        }
        if (NumberKt.compareTo(fromIndex, this.length) >= 0) {
            return NumberKt.UTSNumber((Number) (-1));
        }
        int iIntValue = this.length.intValue();
        for (int iIntValue2 = fromIndex.intValue(); iIntValue2 < iIntValue; iIntValue2++) {
            if (Intrinsics.areEqual(getAuto(iIntValue2), convertValue(searchElement))) {
                return NumberKt.UTSNumber(Integer.valueOf(iIntValue2));
            }
        }
        return NumberKt.UTSNumber((Number) (-1));
    }

    public static /* synthetic */ String join$default(TypedArray typedArray, String str, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: join");
        }
        if ((i & 1) != 0) {
            str = ",";
        }
        return typedArray.join(str);
    }

    public String join(String separator) {
        Intrinsics.checkNotNullParameter(separator, "separator");
        StringBuffer stringBuffer = new StringBuffer();
        int iIntValue = this.length.intValue();
        for (int i = 0; i < iIntValue; i++) {
            stringBuffer.append(NumberKt.toStringAsJS(getAuto(i)));
            if (NumberKt.compareTo(Integer.valueOf(i), NumberKt.minus(this.length, (Number) 1)) < 0) {
                stringBuffer.append(separator);
            }
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public IterableIterator<Number> keys() {
        return new IterableIterator<Number>() { // from class: io.dcloud.uts.TypedArray.keys.1
            private int index;

            private final boolean hasNext() {
                return NumberKt.compareTo(Integer.valueOf(this.index), TypedArray.this.getLength()) < 0;
            }

            @Override // io.dcloud.uts.IterableIterator
            public IteratorReturnResult<Number> next() {
                if (!hasNext()) {
                    return new IteratorReturnResult<>(true, -1);
                }
                this.index++;
                return new IteratorReturnResult<>(false, NumberKt.UTSNumber(Integer.valueOf(this.index - 1)), 1, null);
            }
        };
    }

    public static /* synthetic */ Number reduce$default(TypedArray typedArray, Function4 function4, Number number, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: reduce");
        }
        if ((i & 2) != 0) {
            number = null;
        }
        return typedArray.reduce(function4, number);
    }

    public final <T extends TypedArray> Number reduce(Function4<? super Number, ? super Number, ? super Number, ? super T, ? extends Number> callbackfn, Number initialValue) {
        Intrinsics.checkNotNullParameter(callbackfn, "callbackfn");
        int size = size();
        if (initialValue == null) {
            initialValue = getAuto(0);
        }
        for (int i = 0; i < size; i++) {
            Number auto = getAuto(i);
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.TypedArray.reduce");
            initialValue = callbackfn.invoke(initialValue, auto, numValueOf, this);
        }
        return NumberKt.UTSNumber(initialValue);
    }

    public static /* synthetic */ Number reduceRight$default(TypedArray typedArray, Function4 function4, Number number, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: reduceRight");
        }
        if ((i & 2) != 0) {
            number = null;
        }
        return typedArray.reduceRight(function4, number);
    }

    public final <T extends TypedArray> Number reduceRight(Function4<? super Number, ? super Number, ? super Number, ? super T, ? extends Number> callbackfn, Number initialValue) {
        Intrinsics.checkNotNullParameter(callbackfn, "callbackfn");
        int iIntValue = this.length.intValue();
        int i = iIntValue - 1;
        Number auto = initialValue == null ? getAuto(this.length.intValue() - 1) : initialValue;
        if (initialValue == null) {
            i = iIntValue - 2;
        }
        while (i >= 0) {
            Number auto2 = getAuto(i);
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.TypedArray.reduceRight");
            auto = callbackfn.invoke(auto, auto2, numValueOf, this);
            i--;
        }
        return NumberKt.UTSNumber(auto);
    }

    public final TypedArray reverse() {
        int iIntValue = this.length.intValue() / 2;
        for (int i = 0; i < iIntValue; i++) {
            int iIntValue2 = (this.length.intValue() - i) - 1;
            Number auto = getAuto(i);
            putAuto(Integer.valueOf(i), getAuto(iIntValue2));
            putAuto(Integer.valueOf(iIntValue2), auto);
        }
        return this;
    }

    public static /* synthetic */ void set$default(TypedArray typedArray, Collection collection, int i, int i2, java.lang.Object obj) throws RangeError {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: set");
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        typedArray.set((Collection<? extends Number>) collection, i);
    }

    public final void set(Collection<? extends Number> array, int offset) throws RangeError {
        Intrinsics.checkNotNullParameter(array, "array");
        if (offset < 0 || NumberKt.compareTo(Integer.valueOf(offset), this.length) > 0) {
            throw new RangeError("Offset is out of bounds.");
        }
        if (NumberKt.compareTo(Integer.valueOf(array.size() + offset), this.length) > 0) {
            throw new RangeError("Source array is too large to fit in the target array at the given offset.");
        }
        int i = 0;
        if (array instanceof UTSArray) {
            Iterator<E> it = ((UTSArray) array).iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                Integer numValueOf = Integer.valueOf(offset + i);
                java.lang.Object next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                putAuto(numValueOf, (Number) next);
                i++;
            }
            return;
        }
        if (array instanceof TypedArray) {
            int size = array.size();
            while (i < size) {
                putAuto(Integer.valueOf(offset + i), ((TypedArray) array).getAuto(i));
                i++;
            }
        }
    }

    public static /* synthetic */ TypedArray slice$default(TypedArray typedArray, Number number, Number number2, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: slice");
        }
        if ((i & 1) != 0) {
            number = (Number) 0;
        }
        if ((i & 2) != 0) {
            number2 = typedArray.length;
        }
        return typedArray.slice(number, number2);
    }

    public final <T extends TypedArray> boolean some(Function3<? super Number, ? super Number, ? super T, Boolean> callbackFn) {
        Intrinsics.checkNotNullParameter(callbackFn, "callbackFn");
        Number number = this.length;
        for (int i = 0; i < number.intValue(); i++) {
            if (i < size()) {
                Number auto = getAuto(i);
                Integer numValueOf = Integer.valueOf(i);
                Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of io.dcloud.uts.TypedArray.some");
                if (callbackFn.invoke(auto, numValueOf, this).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ TypedArray sort$default(TypedArray typedArray, Function2 function2, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sort");
        }
        if ((i & 1) != 0) {
            function2 = null;
        }
        return typedArray.sort(function2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final TypedArray sort(Function2<? super Number, ? super Number, ? extends Number> compareFn) {
        UTSArray uTSArray = new UTSArray();
        int iIntValue = this.length.intValue();
        for (int i = 0; i < iIntValue; i++) {
            uTSArray.add(Short.valueOf(getAuto(i).shortValue()));
        }
        UTSArray uTSArray2 = uTSArray;
        Collections.sort(uTSArray2);
        if (compareFn != null) {
            uTSArray.sort(compareFn);
        } else {
            Collections.sort(uTSArray2);
        }
        int iIntValue2 = this.length.intValue();
        for (int i2 = 0; i2 < iIntValue2; i2++) {
            Integer numValueOf = Integer.valueOf(i2);
            E e = uTSArray.get(i2);
            Intrinsics.checkNotNullExpressionValue(e, "get(...)");
            putAuto(numValueOf, (Number) e);
            Integer numValueOf2 = Integer.valueOf(i2);
            E e2 = uTSArray.get(i2);
            Intrinsics.checkNotNullExpressionValue(e2, "get(...)");
            putAuto(numValueOf2, (Number) e2);
        }
        return this;
    }

    public static /* synthetic */ TypedArray subarray$default(TypedArray typedArray, Number number, Number number2, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: subarray");
        }
        if ((i & 1) != 0) {
            number = (Number) 0;
        }
        if ((i & 2) != 0) {
            number2 = typedArray.length;
        }
        return typedArray.subarray(number, number2);
    }

    public final IterableIterator<Number> values() {
        return new IterableIterator<Number>() { // from class: io.dcloud.uts.TypedArray.values.1
            private int index;

            private final boolean hasNext() {
                return NumberKt.compareTo(Integer.valueOf(this.index), TypedArray.this.getLength()) < 0;
            }

            @Override // io.dcloud.uts.IterableIterator
            public IteratorReturnResult<Number> next() {
                if (!hasNext()) {
                    return new IteratorReturnResult<>(true, NumberKt.UTSNumber((Number) (-1)));
                }
                TypedArray typedArray = TypedArray.this;
                int i = this.index;
                this.index = i + 1;
                return new IteratorReturnResult<>(false, NumberKt.UTSNumber(typedArray.getAuto(i)), 1, null);
            }
        };
    }

    public final void set(Number index, Number element) {
        Intrinsics.checkNotNullParameter(index, "index");
        Intrinsics.checkNotNullParameter(element, "element");
        if (NumberKt.compareTo(index, this.length) >= 0) {
            throw new IndexOutOfBoundsException();
        }
        putAuto(Integer.valueOf(index.intValue()), element);
    }

    public final Number get(Number index) {
        Intrinsics.checkNotNullParameter(index, "index");
        if (NumberKt.compareTo(index, this.length) >= 0) {
            throw new IndexOutOfBoundsException();
        }
        return NumberKt.UTSNumber(getAuto(index.intValue()));
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int iIntValue = this.length.intValue();
        for (int i = 0; i < iIntValue; i++) {
            stringBuffer.append(NumberKt.toStringAsJS(getAuto((this.byteOffset.intValue() / getBytesPerElement().intValue()) + i)));
            if (NumberKt.compareTo(Integer.valueOf(i), NumberKt.minus(this.length, (Number) 1)) < 0) {
                stringBuffer.append(",");
            }
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}

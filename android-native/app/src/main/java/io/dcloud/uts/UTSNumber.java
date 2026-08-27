package io.dcloud.uts;

import com.taobao.weex.el.parse.Operators;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Number.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005J\u0010\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001J\u0010\u0010\u001d\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001J\u000e\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u0019\u001a\u00020\u0001J\u0010\u0010\u001f\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001J\u001a\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00182\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0005J\u001a\u0010#\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00182\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0005R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0011\u0010\u0012\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R\u0011\u0010\u0014\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0007¨\u0006$"}, d2 = {"Lio/dcloud/uts/UTSNumber;", "", "<init>", "()V", "MAX_SAFE_INTEGER", "", "getMAX_SAFE_INTEGER", "()Ljava/lang/Number;", "MIN_SAFE_INTEGER", "getMIN_SAFE_INTEGER", "EPSILON", "getEPSILON", "MAX_VALUE", "getMAX_VALUE", "MIN_VALUE", "getMIN_VALUE", "NaN", "getNaN", "NEGATIVE_INFINITY", "getNEGATIVE_INFINITY", "POSITIVE_INFINITY", "getPOSITIVE_INFINITY", "from", "str", "", "inputNum", "isNaN", "", "number", "isFinite", "isInteger", "isSafeInteger", "parseInt", "string", "radix", "parseFloat", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSNumber {
    public static final UTSNumber INSTANCE = new UTSNumber();
    private static final Number MAX_SAFE_INTEGER = (Number) 9007199254740991L;
    private static final Number MIN_SAFE_INTEGER = (Number) (-9007199254740991L);
    private static final Number EPSILON = Double.valueOf(2.220446049250313E-16d);
    private static final Number MAX_VALUE = Double.valueOf(Double.MAX_VALUE);
    private static final Number MIN_VALUE = Double.valueOf(Double.MIN_VALUE);
    private static final Number NaN = Double.valueOf(Double.NaN);
    private static final Number NEGATIVE_INFINITY = Double.valueOf(Double.NEGATIVE_INFINITY);
    private static final Number POSITIVE_INFINITY = Double.valueOf(Double.POSITIVE_INFINITY);

    public final Number from(Number inputNum) {
        Intrinsics.checkNotNullParameter(inputNum, "inputNum");
        return inputNum;
    }

    private UTSNumber() {
    }

    public final Number getMAX_SAFE_INTEGER() {
        return MAX_SAFE_INTEGER;
    }

    public final Number getMIN_SAFE_INTEGER() {
        return MIN_SAFE_INTEGER;
    }

    public final Number getEPSILON() {
        return EPSILON;
    }

    public final Number getMAX_VALUE() {
        return MAX_VALUE;
    }

    public final Number getMIN_VALUE() {
        return MIN_VALUE;
    }

    public final Number getNaN() {
        return NaN;
    }

    public final Number getNEGATIVE_INFINITY() {
        return NEGATIVE_INFINITY;
    }

    public final Number getPOSITIVE_INFINITY() {
        return POSITIVE_INFINITY;
    }

    public final Number from(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        if (StringKt.includes$default(str, Operators.DOT_STR, null, 2, null) || StringKt.includes$default(str, "e", null, 2, null) || StringKt.includes$default(str, "E", null, 2, null)) {
            return Double.valueOf(Double.parseDouble(str));
        }
        try {
            try {
                try {
                    return Integer.valueOf(Integer.parseInt(str));
                } catch (Exception unused) {
                    return Double.valueOf(Double.NaN);
                }
            } catch (NumberFormatException unused2) {
                return Double.valueOf(Double.parseDouble(str));
            }
        } catch (Exception unused3) {
            return Long.valueOf(Long.parseLong(str));
        }
    }

    public final boolean isNaN(java.lang.Object number) {
        if (!(number instanceof Number)) {
            return false;
        }
        Number number2 = (Number) number;
        if (number2 instanceof Double) {
            return Double.isNaN(number2.doubleValue());
        }
        if (number2 instanceof Float) {
            return Float.isNaN(number2.floatValue());
        }
        return false;
    }

    public final boolean isFinite(java.lang.Object number) {
        if (!(number instanceof Number)) {
            return false;
        }
        Number number2 = (Number) number;
        return number2 instanceof Double ? java.lang.Math.abs(number2.doubleValue()) <= Double.MAX_VALUE : !(number2 instanceof Float) || java.lang.Math.abs(number2.floatValue()) <= Float.MAX_VALUE;
    }

    public final boolean isInteger(java.lang.Object inputNum) {
        Intrinsics.checkNotNullParameter(inputNum, "inputNum");
        if (!(inputNum instanceof Number) || !isFinite(inputNum)) {
            return false;
        }
        if ((inputNum instanceof Integer) || (inputNum instanceof Long) || (inputNum instanceof Short) || (inputNum instanceof Byte)) {
            return true;
        }
        return Intrinsics.areEqual(NumberKt.times(Math.floor((Number) inputNum), Double.valueOf(1.0d)), inputNum);
    }

    public final boolean isSafeInteger(java.lang.Object number) {
        if (!(number instanceof Number)) {
            return false;
        }
        Number number2 = (Number) number;
        return NumberKt.compareTo(number2, MAX_SAFE_INTEGER) <= 0 && NumberKt.compareTo(number2, MIN_SAFE_INTEGER) >= 0;
    }

    public static /* synthetic */ Number parseInt$default(UTSNumber uTSNumber, String str, Number number, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            number = (Number) 0;
        }
        return uTSNumber.parseInt(str, number);
    }

    public final Number parseInt(String string, Number radix) {
        Intrinsics.checkNotNullParameter(string, "string");
        return NumberKt.parseInt(string, radix);
    }

    public static /* synthetic */ Number parseFloat$default(UTSNumber uTSNumber, String str, Number number, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            number = (Number) 0;
        }
        return uTSNumber.parseFloat(str, number);
    }

    public final Number parseFloat(String string, Number radix) {
        Intrinsics.checkNotNullParameter(string, "string");
        return NumberKt.parseFloat(string);
    }
}

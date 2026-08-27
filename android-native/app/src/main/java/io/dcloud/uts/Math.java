package io.dcloud.uts;

import com.taobao.weex.common.Constants;
import io.dcloud.common.constant.AbsoluteConst;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.random.Random;
import net.lingala.zip4j.util.InternalZipConstants;

/* compiled from: Math.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b*\n\u0002\u0010\u0011\n\u0002\b\r\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0018\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u0005H\u0007J\u0010\u0010 \u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010!\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\b\u0010\"\u001a\u00020\u0005H\u0007J\u0012\u0010\"\u001a\u00020\u00052\b\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u0007J\u0010\u0010#\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010$\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010%\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010&\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010'\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010(\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0018\u0010)\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u0005H\u0007J\u0010\u0010*\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010+\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010,\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010-\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J!\u0010.\u001a\u00020\u00052\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000500\"\u00020\u0005H\u0007¢\u0006\u0002\u00101J!\u00102\u001a\u00020\u00052\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000500\"\u00020\u0005H\u0007¢\u0006\u0002\u00101J\u0018\u00103\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u0005H\u0007J\b\u00104\u001a\u00020\u0005H\u0007J\u0010\u00105\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u00106\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u00107\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u00108\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u00109\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010:\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010;\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007J\u0010\u0010<\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0011\u0010\u0012\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R\u0011\u0010\u0014\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0007¨\u0006="}, d2 = {"Lio/dcloud/uts/Math;", "", "<init>", "()V", "E", "", "getE", "()Ljava/lang/Number;", "PI", "getPI", "LN10", "getLN10", "LN2", "getLN2", "LOG10E", "getLOG10E", "LOG2E", "getLOG2E", "SQRT1_2", "getSQRT1_2", "SQRT2", "getSQRT2", "abs", Constants.Name.X, "acos", "acosh", "asin", "asinh", "atan", "atanh", "atan2", Constants.Name.Y, "cbrt", "ceil", "clz32", "cos", "cosh", "exp", "expm1", "floor", "fround", "hypot", "log", "log10", "log1p", "log2", "max", "values", "", "([Ljava/lang/Number;)Ljava/lang/Number;", Constants.Name.MIN, "pow", "random", AbsoluteConst.JSON_KEY_ROUND, "sign", "sin", "sinh", "sqrt", "tan", "tanh", "trunc", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class Math {
    public static final Math INSTANCE = new Math();
    private static final Number E = Double.valueOf(2.718281828459045d);
    private static final Number PI = Double.valueOf(3.141592653589793d);
    private static final Number LN10 = Double.valueOf(2.302585092994046d);
    private static final Number LN2 = Double.valueOf(0.6931471805599453d);
    private static final Number LOG10E = Double.valueOf(0.4342944819032518d);
    private static final Number LOG2E = Double.valueOf(1.4426950408889634d);
    private static final Number SQRT1_2 = Double.valueOf(0.7071067811865476d);
    private static final Number SQRT2 = Double.valueOf(1.4142135623730951d);

    private Math() {
    }

    public final Number getE() {
        return E;
    }

    public final Number getPI() {
        return PI;
    }

    public final Number getLN10() {
        return LN10;
    }

    public final Number getLN2() {
        return LN2;
    }

    public final Number getLOG10E() {
        return LOG10E;
    }

    public final Number getLOG2E() {
        return LOG2E;
    }

    public final Number getSQRT1_2() {
        return SQRT1_2;
    }

    public final Number getSQRT2() {
        return SQRT2;
    }

    @JvmStatic
    public static final Number abs(Number x) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Double) {
            numberValueOf = Double.valueOf(java.lang.Math.abs(x.doubleValue()));
        } else if (x instanceof Float) {
            numberValueOf = Float.valueOf(java.lang.Math.abs(x.floatValue()));
        } else if (x instanceof Integer) {
            numberValueOf = Integer.valueOf(java.lang.Math.abs(x.intValue()));
        } else if (x instanceof Long) {
            numberValueOf = Long.valueOf(java.lang.Math.abs(x.longValue()));
        } else if (x instanceof Byte) {
            numberValueOf = Integer.valueOf(java.lang.Math.abs((int) x.byteValue()));
        } else if (x instanceof Short) {
            numberValueOf = Integer.valueOf(java.lang.Math.abs((int) x.shortValue()));
        } else {
            numberValueOf = Double.valueOf(java.lang.Math.abs(x.doubleValue()));
        }
        return numberValueOf;
    }

    @JvmStatic
    public static final Number acos(Number x) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            numberValueOf = Float.valueOf((float) java.lang.Math.acos(x.floatValue()));
        } else {
            numberValueOf = Double.valueOf(java.lang.Math.acos(x.doubleValue()));
        }
        return NumberKt.UTSNumber(numberValueOf);
    }

    @JvmStatic
    public static final Number acosh(Number x) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            numberValueOf = Float.valueOf((float) MathKt.acosh(x.floatValue()));
        } else {
            numberValueOf = Double.valueOf(MathKt.acosh(x.doubleValue()));
        }
        return NumberKt.UTSNumber(numberValueOf);
    }

    @JvmStatic
    public static final Number asin(Number x) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            numberValueOf = Float.valueOf((float) java.lang.Math.asin(x.floatValue()));
        } else {
            numberValueOf = Double.valueOf(java.lang.Math.asin(x.doubleValue()));
        }
        return NumberKt.UTSNumber(numberValueOf);
    }

    @JvmStatic
    public static final Number asinh(Number x) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            numberValueOf = Float.valueOf((float) MathKt.asinh(x.floatValue()));
        } else {
            numberValueOf = Double.valueOf(MathKt.asinh(x.doubleValue()));
        }
        return NumberKt.UTSNumber(numberValueOf);
    }

    @JvmStatic
    public static final Number atan(Number x) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            numberValueOf = Float.valueOf((float) java.lang.Math.atan(x.floatValue()));
        } else {
            numberValueOf = Double.valueOf(java.lang.Math.atan(x.doubleValue()));
        }
        return NumberKt.UTSNumber(numberValueOf);
    }

    @JvmStatic
    public static final Number atanh(Number x) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            numberValueOf = Float.valueOf((float) MathKt.atanh(x.floatValue()));
        } else {
            numberValueOf = Double.valueOf(MathKt.atanh(x.doubleValue()));
        }
        return NumberKt.UTSNumber(numberValueOf);
    }

    @JvmStatic
    public static final Number atan2(Number x, Number y) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(y, "y");
        return NumberKt.UTSNumber(Double.valueOf(java.lang.Math.atan2(x.doubleValue(), y.doubleValue())));
    }

    @JvmStatic
    public static final Number cbrt(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return NumberKt.UTSNumber(Double.valueOf(java.lang.Math.pow(x.doubleValue(), 0.3333333333333333d)));
    }

    @JvmStatic
    public static final Number ceil(Number x) {
        Number numberValueOf;
        Number numberValueOf2;
        Intrinsics.checkNotNullParameter(x, "x");
        if (Intrinsics.areEqual(x, NumberKt.getNaN())) {
            return x;
        }
        if (x instanceof Float) {
            numberValueOf = Float.valueOf((float) java.lang.Math.ceil(x.floatValue()));
        } else {
            numberValueOf = Double.valueOf(java.lang.Math.ceil(x.doubleValue()));
        }
        Number number = numberValueOf;
        if (NumberKt.compareTo(number, (Number) Integer.MAX_VALUE) > 0 || NumberKt.compareTo(number, (Number) Integer.MIN_VALUE) < 0) {
            numberValueOf2 = Long.valueOf(number.longValue());
        } else {
            numberValueOf2 = Integer.valueOf(number.intValue());
        }
        return numberValueOf2;
    }

    @JvmStatic
    public static final Number clz32() {
        return (Number) 32;
    }

    @JvmStatic
    public static final Number clz32(Number x) {
        if (x == null || Intrinsics.areEqual(UTSNumber.INSTANCE.getNaN(), x) || Intrinsics.areEqual(UTSNumber.INSTANCE.getPOSITIVE_INFINITY(), x) || Intrinsics.areEqual(UTSNumber.INSTANCE.getNEGATIVE_INFINITY(), x) || Intrinsics.areEqual(UTSNumber.INSTANCE.getMAX_VALUE(), x) || Intrinsics.areEqual(UTSNumber.INSTANCE.getMIN_VALUE(), x)) {
            return (Number) 32;
        }
        int iM631constructorimpl = UInt.m631constructorimpl(x.intValue());
        if (iM631constructorimpl == UInt.m631constructorimpl(0)) {
            return (Number) 32;
        }
        return Integer.valueOf(32 - NumberKt.toString((Number) Long.valueOf(iM631constructorimpl & InternalZipConstants.ZIP_64_SIZE_LIMIT), (Number) 2).length());
    }

    @JvmStatic
    public static final Number cos(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            return Float.valueOf((float) java.lang.Math.cos(x.floatValue()));
        }
        return Double.valueOf(java.lang.Math.cos(x.doubleValue()));
    }

    @JvmStatic
    public static final Number cosh(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            return Float.valueOf((float) java.lang.Math.cosh(x.floatValue()));
        }
        return Double.valueOf(java.lang.Math.cosh(x.doubleValue()));
    }

    @JvmStatic
    public static final Number exp(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            return Float.valueOf((float) java.lang.Math.exp(x.floatValue()));
        }
        return Double.valueOf(java.lang.Math.exp(x.doubleValue()));
    }

    @JvmStatic
    public static final Number expm1(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            return Float.valueOf((float) java.lang.Math.expm1(x.floatValue()));
        }
        return Double.valueOf(java.lang.Math.expm1(x.doubleValue()));
    }

    @JvmStatic
    public static final Number floor(Number x) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(x, "x");
        if (x instanceof Float) {
            numberValueOf = Float.valueOf((float) java.lang.Math.floor(x.floatValue()));
        } else {
            numberValueOf = Double.valueOf(java.lang.Math.floor(x.doubleValue()));
        }
        Number number = numberValueOf;
        if (NumberKt.compareTo(number, (Number) Integer.MAX_VALUE) > 0 || NumberKt.compareTo(number, (Number) Integer.MIN_VALUE) < 0) {
            return Long.valueOf(number.longValue());
        }
        return Integer.valueOf(number.intValue());
    }

    @JvmStatic
    public static final Number fround(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Float.valueOf(x.floatValue());
    }

    @JvmStatic
    public static final Number hypot(Number x, Number y) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(y, "y");
        if ((x instanceof Float) && (y instanceof Float)) {
            return Float.valueOf((float) java.lang.Math.hypot(x.floatValue(), y.floatValue()));
        }
        return Double.valueOf(java.lang.Math.hypot(x.doubleValue(), y.doubleValue()));
    }

    @JvmStatic
    public static final Number log(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(java.lang.Math.log(x.doubleValue()));
    }

    @JvmStatic
    public static final Number log10(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(java.lang.Math.log10(x.doubleValue()));
    }

    @JvmStatic
    public static final Number log1p(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(java.lang.Math.log(NumberKt.plus(x, (Number) 1).doubleValue()));
    }

    @JvmStatic
    public static final Number log2(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(MathKt.log2(x.doubleValue()));
    }

    @JvmStatic
    public static final Number max(Number... values) {
        Intrinsics.checkNotNullParameter(values, "values");
        if (ArraysKt.contains(values, UTSNumber.INSTANCE.getNaN())) {
            return UTSNumber.INSTANCE.getNaN();
        }
        if (values.length == 0) {
            return (Number) (-1);
        }
        Number number = values[0];
        for (Number number2 : values) {
            if (NumberKt.compareTo(number2, number) > 0) {
                number = number2;
            }
        }
        return number;
    }

    @JvmStatic
    public static final Number min(Number... values) {
        Intrinsics.checkNotNullParameter(values, "values");
        if (ArraysKt.contains(values, UTSNumber.INSTANCE.getNaN())) {
            return UTSNumber.INSTANCE.getNaN();
        }
        if (values.length == 0) {
            return (Number) 0;
        }
        Number number = values[0];
        for (Number number2 : values) {
            if (NumberKt.compareTo(number2, number) < 0) {
                number = number2;
            }
        }
        return number;
    }

    @JvmStatic
    public static final Number pow(Number x, Number y) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(y, "y");
        return Double.valueOf(java.lang.Math.pow(x.doubleValue(), y.doubleValue()));
    }

    @JvmStatic
    public static final Number random() {
        return Double.valueOf(Random.INSTANCE.nextDouble());
    }

    @JvmStatic
    public static final Number round(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (Intrinsics.areEqual(x, UTSNumber.INSTANCE.getNaN()) || Intrinsics.areEqual(x, UTSNumber.INSTANCE.getPOSITIVE_INFINITY()) || Intrinsics.areEqual(x, UTSNumber.INSTANCE.getNEGATIVE_INFINITY()) || Intrinsics.areEqual(x, UTSNumber.INSTANCE.getMAX_VALUE())) {
            return x;
        }
        double dDoubleValue = x.doubleValue();
        if (dDoubleValue > 2.147483647E9d || dDoubleValue < -2.147483648E9d) {
            return Long.valueOf(MathKt.roundToLong(dDoubleValue));
        }
        return Integer.valueOf(MathKt.roundToInt(dDoubleValue));
    }

    @JvmStatic
    public static final Number sign(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (NumberKt.compareTo(x, (Number) 0) < 0) {
            return (Number) (-1);
        }
        if (Intrinsics.areEqual((java.lang.Object) x, (java.lang.Object) 0)) {
            return (Number) 0;
        }
        return (Number) 1;
    }

    @JvmStatic
    public static final Number sin(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(java.lang.Math.sin(x.doubleValue()));
    }

    @JvmStatic
    public static final Number sinh(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(java.lang.Math.sinh(x.doubleValue()));
    }

    @JvmStatic
    public static final Number sqrt(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(java.lang.Math.sqrt(x.doubleValue()));
    }

    @JvmStatic
    public static final Number tan(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(java.lang.Math.tan(x.doubleValue()));
    }

    @JvmStatic
    public static final Number tanh(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Double.valueOf(java.lang.Math.tanh(x.doubleValue()));
    }

    @JvmStatic
    public static final Number trunc(Number x) {
        Intrinsics.checkNotNullParameter(x, "x");
        return Integer.valueOf(x.intValue());
    }
}

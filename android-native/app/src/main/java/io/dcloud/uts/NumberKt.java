package io.dcloud.uts;

import android.os.Build;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.uts.android.ClassLogWrapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.lingala.zip4j.util.InternalZipConstants;

/* compiled from: Number.kt */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u0001H\u0007\u001a\u001a\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u001a\u0016\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u0001\u001a\u000e\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u000e\u0010\u0010\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u000e\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u001a\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0001\u001a\u001a\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0001\u001a\u0014\u0010\u0018\u001a\u00020\u0013*\u00020\u00012\b\b\u0002\u0010\u0019\u001a\u00020\u0001\u001a\n\u0010\u001a\u001a\u00020\u0013*\u00020\u0001\u001a\u0014\u0010\u001b\u001a\u00020\u0013*\u00020\u000b2\b\b\u0002\u0010\u0016\u001a\u00020\u0001\u001a\u001d\u0010\u001b\u001a\u00020\u0013*\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u0016\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u001c\u001a\u0014\u0010\u001b\u001a\u00020\u0013*\u00020\u00012\b\b\u0002\u0010\u0016\u001a\u00020\u0001\u001a\u001d\u0010\u001b\u001a\u00020\u0013*\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0016\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u001c\u001a\u0016\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u001f\u001a\u0016\u0010 \u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020!2\u0006\u0010\u0016\u001a\u00020\u001f\u001a\u0016\u0010\"\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020#2\u0006\u0010\u0016\u001a\u00020\u001f\u001a\u0012\u0010$\u001a\u00020\u0013*\u00020\u00012\u0006\u0010%\u001a\u00020\u0001\u001a\n\u0010&\u001a\u00020\u0001*\u00020\u0001\u001a\u0015\u0010'\u001a\u00020\u001f*\u00020\u00012\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\u001a\u0015\u0010)\u001a\u00020\u0001*\u00020\u00012\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\u001a\u0015\u0010)\u001a\u00020\u0013*\u00020\u00012\u0006\u0010(\u001a\u00020\u0013H\u0086\u0002\u001a\u0015\u0010*\u001a\u00020\u0001*\u00020\u00012\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\u001a\u0015\u0010+\u001a\u00020\u0001*\u00020\u00012\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\u001a\u0015\u0010,\u001a\u00020\u0001*\u00020\u00012\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\u001a\u0015\u0010-\u001a\u00020\u0001*\u00020\u00012\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\u001a\r\u0010.\u001a\u00020\u0001*\u00020\u0001H\u0086\u0002\u001a\r\u0010/\u001a\u00020\u0001*\u00020\u0001H\u0086\u0002\u001a\r\u00100\u001a\u00020\u0001*\u00020\u0001H\u0086\u0002\u001a\r\u00101\u001a\u00020\u0001*\u00020\u0001H\u0086\u0002\u001a\u0015\u00102\u001a\u00020\u0001*\u00020\u00012\u0006\u00103\u001a\u00020\u0001H\u0086\u0004\u001a\u0015\u00104\u001a\u00020\u0001*\u00020\u00012\u0006\u00103\u001a\u00020\u0001H\u0086\u0004\u001a\u0015\u00105\u001a\u00020\u0001*\u00020\u00012\u0006\u00103\u001a\u00020\u0001H\u0086\u0004\u001a\n\u00106\u001a\u00020\u0001*\u00020\u0001\u001a\u0015\u00107\u001a\u00020\u0001*\u00020\u00012\u0006\u00103\u001a\u00020\u0001H\u0086\u0004\u001a\u0015\u00108\u001a\u00020\u0001*\u00020\u00012\u0006\u00103\u001a\u00020\u0001H\u0086\u0004\u001a\u0015\u00109\u001a\u00020\u0001*\u00020\u00012\u0006\u00103\u001a\u00020\u0001H\u0086\u0004\u001a\u0015\u0010:\u001a\u00020;*\u00020\u00012\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\u001a\u0015\u0010:\u001a\u00020;*\u00020\u00012\u0006\u0010(\u001a\u00020\u001fH\u0086\u0002\u001a\u0015\u0010:\u001a\u00020<*\u00020\u00012\u0006\u0010(\u001a\u00020=H\u0086\u0002\u001a\u0015\u0010:\u001a\u00020;*\u00020\u001f2\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\u001a\u0015\u0010:\u001a\u00020<*\u00020=2\u0006\u0010(\u001a\u00020\u0001H\u0086\u0002\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006>"}, d2 = {"NaN", "", "getNaN", "()Ljava/lang/Number;", "Infinity", "getInfinity", "UTSNumber", "input", "numberEquals", "", "a", "", "b", "numberEqualsImpl", "isFinite", "number", "isNaN", "parseFloatInternal", "string", "", "parseFloat", "parseIntInternal", "radix", "parseInt", "toFixed", "fractionDigits", "toStringAsJS", "toString", "toString_number_nullable", "intToString", "value", "", "bigIntToString", "Ljava/math/BigDecimal;", "doubleToString", "", "toPrecision", "precision", "valueOf", "compareTo", "other", IApp.ConfigProperty.CONFIG_PLUS, "minus", "times", WXBasicComponentType.DIV, "rem", "inc", "dec", "unaryPlus", "unaryMinus", "and", "next", "or", "xor", "inv", "shl", "shr", "ushr", "rangeTo", "Lkotlin/ranges/IntRange;", "Lkotlin/ranges/LongRange;", "", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class NumberKt {
    private static final Number NaN = UTSNumber.INSTANCE.getNaN();
    private static final Number Infinity = UTSNumber.INSTANCE.getPOSITIVE_INFINITY();

    @Deprecated(message = "use kotlin.Number instead", replaceWith = @ReplaceWith(expression = "kotlin.Number", imports = {}))
    public static final Number UTSNumber(Number input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return input;
    }

    public static final Number valueOf(Number number) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return number;
    }

    public static final Number getNaN() {
        return NaN;
    }

    public static final Number getInfinity() {
        return Infinity;
    }

    public static final boolean numberEquals(java.lang.Object obj, java.lang.Object obj2) {
        boolean z = obj == null;
        boolean z2 = obj2 == null;
        if (z && z2) {
            return true;
        }
        if (z || z2) {
            return false;
        }
        boolean z3 = obj2 instanceof Number;
        if ((obj instanceof Number) && z3) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Number");
            return numberEqualsImpl((Number) obj, (Number) obj2);
        }
        return Intrinsics.areEqual(obj, obj2);
    }

    public static final boolean numberEqualsImpl(Number a, Number b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        boolean z = a instanceof Double;
        boolean z2 = b instanceof Double;
        if (z && z2) {
            return a.doubleValue() == b.doubleValue();
        }
        boolean z3 = a instanceof Integer;
        boolean z4 = b instanceof Integer;
        if (z3 && z4) {
            return Intrinsics.areEqual(a, b);
        }
        boolean z5 = a instanceof Long;
        boolean z6 = b instanceof Long;
        if (z5 && z6) {
            return Intrinsics.areEqual(a, b);
        }
        boolean z7 = a instanceof Float;
        boolean z8 = b instanceof Float;
        if (z7 && z8) {
            return a.floatValue() == b.floatValue();
        }
        boolean z9 = a instanceof Byte;
        boolean z10 = b instanceof Byte;
        if (z9 && z10) {
            return a.byteValue() == b.byteValue();
        }
        boolean z11 = a instanceof Short;
        boolean z12 = b instanceof Short;
        if (z11 && z12) {
            return a.shortValue() == b.shortValue();
        }
        if (z) {
            return z8 ? ((float) a.doubleValue()) == b.floatValue() : a.doubleValue() == b.doubleValue();
        }
        if (z7) {
            return z2 ? a.floatValue() == ((float) b.doubleValue()) : a.floatValue() == b.floatValue();
        }
        if (z5) {
            if (z2) {
                return ((double) a.longValue()) == b.doubleValue();
            }
            if (z8) {
                return ((float) a.longValue()) == b.floatValue();
            }
            return Intrinsics.areEqual(a, Long.valueOf(b.longValue()));
        }
        if (z3) {
            if (z2) {
                return ((double) a.intValue()) == b.doubleValue();
            }
            if (z8) {
                return ((float) a.intValue()) == b.floatValue();
            }
            if (z6) {
                return z6 && ((long) a.intValue()) == b.longValue();
            }
            return Intrinsics.areEqual(a, Integer.valueOf(b.intValue()));
        }
        if (z11) {
            if (z2) {
                return ((double) a.shortValue()) == b.doubleValue();
            }
            if (z8) {
                return ((float) a.shortValue()) == b.floatValue();
            }
            if (z6) {
                return z6 && ((long) a.shortValue()) == b.longValue();
            }
            return a.shortValue() == b.intValue();
        }
        if (!z9) {
            return a.intValue() == b.intValue();
        }
        if (z2) {
            return ((double) a.byteValue()) == b.doubleValue();
        }
        if (z8) {
            return ((float) a.byteValue()) == b.floatValue();
        }
        if (z6) {
            return z6 && ((long) a.byteValue()) == b.longValue();
        }
        return a.byteValue() == b.intValue();
    }

    public static final boolean isFinite(Number number) {
        Intrinsics.checkNotNullParameter(number, "number");
        return UTSNumber.INSTANCE.isFinite(number);
    }

    public static final boolean isNaN(Number number) {
        Intrinsics.checkNotNullParameter(number, "number");
        return UTSNumber.INSTANCE.isNaN(number);
    }

    private static final Number parseFloatInternal(String str) {
        String string = StringsKt.trim((CharSequence) str).toString();
        MatchResult matchResultFind$default = Regex.find$default(new Regex("[^\\d+-.Ee]"), string, 0, 2, null);
        if (matchResultFind$default != null) {
            string = StringKt.substring(str, (Number) 0, Integer.valueOf(matchResultFind$default.getRange().getFirst()));
        }
        String string2 = StringsKt.trim((CharSequence) string).toString();
        String str2 = string2;
        if (StringsKt.contains$default((CharSequence) str2, (CharSequence) "E", false, 2, (java.lang.Object) null) || StringsKt.contains$default((CharSequence) str2, (CharSequence) "e", false, 2, (java.lang.Object) null)) {
            return Double.valueOf(new BigDecimal(string2).doubleValue());
        }
        if (!StringsKt.contains$default((CharSequence) str2, (CharSequence) Operators.DOT_STR, false, 2, (java.lang.Object) null)) {
            Long longOrNull = StringsKt.toLongOrNull(string2);
            return longOrNull != null ? longOrNull : Double.valueOf(Double.NaN);
        }
        Double doubleOrNull = StringsKt.toDoubleOrNull(string2);
        return doubleOrNull != null ? doubleOrNull : Double.valueOf(Double.NaN);
    }

    public static final Number parseFloat(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return parseFloatInternal(string);
    }

    public static /* synthetic */ Number parseIntInternal$default(String str, Number number, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            number = (Number) 0;
        }
        return parseIntInternal(str, number);
    }

    public static final Number parseIntInternal(String string, Number number) {
        int iIntValue;
        String strSubstring;
        String strSubstring$default;
        Double dValueOf;
        Intrinsics.checkNotNullParameter(string, "string");
        String str = string;
        boolean zIsBlank = StringsKt.isBlank(str);
        Double dValueOf2 = Double.valueOf(Double.NaN);
        if (zIsBlank) {
            return dValueOf2;
        }
        if (number == null || Intrinsics.areEqual((java.lang.Object) number, (java.lang.Object) 0)) {
            iIntValue = 0;
        } else if (CollectionsKt.contains(new IntRange(2, 36), number)) {
            iIntValue = number.intValue();
        } else {
            return dValueOf2;
        }
        String lowerCase = StringKt.toLowerCase(StringsKt.trim((CharSequence) str).toString());
        if (StringsKt.startsWith$default(lowerCase, Operators.PLUS, false, 2, (java.lang.Object) null) || StringsKt.startsWith$default(lowerCase, Operators.SUB, false, 2, (java.lang.Object) null)) {
            strSubstring = StringKt.substring(lowerCase, (Number) 0, (Number) 1);
            strSubstring$default = StringKt.substring$default(lowerCase, (Number) 1, null, 2, null);
        } else {
            strSubstring = "";
            strSubstring$default = lowerCase;
        }
        if (iIntValue == 0) {
            if (!StringsKt.startsWith$default(strSubstring$default, "0x", false, 2, (java.lang.Object) null) || strSubstring$default.length() <= 2) {
                iIntValue = 10;
            } else {
                strSubstring$default = StringKt.substring$default(strSubstring$default, (Number) 2, null, 2, null);
                iIntValue = 16;
            }
        } else if (iIntValue == 16 && StringsKt.startsWith$default(lowerCase, "0x", false, 2, (java.lang.Object) null)) {
            strSubstring$default = StringKt.substring$default(strSubstring$default, (Number) 2, null, 2, null);
        }
        String strSubstring2 = StringKt.substring("0123456789abcdefghijklmnopqrstuvwxyz", (Number) 0, Integer.valueOf(iIntValue));
        Iterator<IndexedValue<Character>> it = StringsKt.withIndex(strSubstring$default).iterator();
        double d = 0.0d;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            IndexedValue<Character> next = it.next();
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) strSubstring2, next.getValue().charValue(), 0, false, 6, (java.lang.Object) null);
            if (iIndexOf$default >= 0) {
                d = (d * iIntValue) + iIndexOf$default;
            } else if (next.getIndex() == 0) {
                return dValueOf2;
            }
        }
        if (d < 2.147483647E9d) {
            dValueOf = Integer.valueOf((int) d);
        } else if (d < 9.223372036854776E18d) {
            dValueOf = Long.valueOf((long) d);
        } else {
            dValueOf = Double.valueOf(d);
        }
        return Intrinsics.areEqual(strSubstring, Operators.SUB) ? unaryMinus(dValueOf) : dValueOf;
    }

    public static /* synthetic */ Number parseInt$default(String str, Number number, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            number = (Number) 0;
        }
        return parseInt(str, number);
    }

    public static final Number parseInt(String string, Number number) {
        Intrinsics.checkNotNullParameter(string, "string");
        return parseIntInternal(string, number);
    }

    public static /* synthetic */ String toFixed$default(Number number, Number number2, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            number2 = (Number) 0;
        }
        return toFixed(number, number2);
    }

    public static final String toFixed(Number number, Number fractionDigits) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(fractionDigits, "fractionDigits");
        if (Build.VERSION.SDK_INT <= 23) {
            if (Intrinsics.areEqual(number, Double.valueOf(Double.NEGATIVE_INFINITY))) {
                return "-Infinity";
            }
            if (Intrinsics.areEqual(number, Double.valueOf(Double.POSITIVE_INFINITY))) {
                return "Infinity";
            }
            if (UTSNumber.INSTANCE.isNaN(number)) {
                return "NaN";
            }
            String plainString = new BigDecimal(number.doubleValue()).setScale(fractionDigits.intValue(), RoundingMode.HALF_EVEN).toPlainString();
            if (number.doubleValue() < 0.0d) {
                Intrinsics.checkNotNull(plainString);
                if (Double.parseDouble(plainString) == 0.0d) {
                    return Operators.SUB + plainString;
                }
            }
            Intrinsics.checkNotNull(plainString);
            return plainString;
        }
        String str = String.format("%." + fractionDigits + 'f', Arrays.copyOf(new java.lang.Object[]{Double.valueOf(number.doubleValue())}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public static final String toStringAsJS(Number number) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return toString(number, (Number) 10);
    }

    public static /* synthetic */ String toString$default(java.lang.Object obj, Number number, int i, java.lang.Object obj2) {
        if ((i & 1) != 0) {
            number = (Number) 10;
        }
        return toString(obj, number);
    }

    public static final String toString(java.lang.Object obj, Number radix) {
        Intrinsics.checkNotNullParameter(obj, "<this>");
        Intrinsics.checkNotNullParameter(radix, "radix");
        if (obj instanceof Number) {
            return toString((Number) obj, radix);
        }
        return obj.toString();
    }

    public static /* synthetic */ String toString_number_nullable$default(java.lang.Object obj, Number number, int i, java.lang.Object obj2) {
        if ((i & 1) != 0) {
            number = (Number) 10;
        }
        return toString_number_nullable(obj, number);
    }

    public static final String toString_number_nullable(java.lang.Object obj, Number radix) {
        Intrinsics.checkNotNullParameter(radix, "radix");
        if (obj == null) {
            return "null";
        }
        return toString(obj, radix);
    }

    public static /* synthetic */ String toString$default(Number number, Number number2, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            number2 = (Number) 10;
        }
        return toString(number, number2);
    }

    public static final String toString(Number number, Number radix) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(radix, "radix");
        if ((number instanceof Double) || (number instanceof Float)) {
            return doubleToString(number.doubleValue(), radix.intValue());
        }
        return intToString(number, radix.intValue());
    }

    public static /* synthetic */ String toString_number_nullable$default(Number number, Number number2, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            number2 = (Number) 10;
        }
        return toString_number_nullable(number, number2);
    }

    public static final String toString_number_nullable(Number number, Number radix) {
        Intrinsics.checkNotNullParameter(radix, "radix");
        if (number == null) {
            return "null";
        }
        return toString(number, radix);
    }

    public static final String intToString(Number value, int i) {
        Intrinsics.checkNotNullParameter(value, "value");
        String str = "";
        if (i >= 2 && i <= 36) {
            if (i == 10) {
                return value.toString();
            }
            long jAbs = java.lang.Math.abs(value.longValue());
            do {
                long j = i;
                str = StringKt.get("0123456789abcdefghijklmnopqrstuvwxyz", Long.valueOf(jAbs % j)) + str;
                jAbs /= j;
            } while (jAbs > 0);
            if (value.doubleValue() < 0.0d) {
                return Operators.SUB + str;
            }
        }
        return str;
    }

    public static final String bigIntToString(BigDecimal number, int i) {
        Intrinsics.checkNotNullParameter(number, "number");
        char[] charArray = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
        BigDecimal bigDecimalAbs = number.abs();
        boolean zAreEqual = Intrinsics.areEqual(number, bigDecimalAbs);
        BigDecimal bigDecimal = new BigDecimal(i);
        String str = "";
        while (true) {
            Intrinsics.checkNotNull(bigDecimalAbs);
            if (compareTo(bigDecimalAbs, (Number) 0) <= 0) {
                break;
            }
            BigDecimal[] bigDecimalArrDivideAndRemainder = bigDecimalAbs.divideAndRemainder(bigDecimal);
            BigDecimal bigDecimal2 = bigDecimalArrDivideAndRemainder[0];
            str = charArray[bigDecimalArrDivideAndRemainder[1].intValue()] + str;
            bigDecimalAbs = bigDecimal2;
        }
        if (zAreEqual) {
            return str;
        }
        return Operators.SUB + str;
    }

    public static final String doubleToString(double d, int i) {
        double dFloor;
        double dAbs;
        String strIntToString;
        String str;
        String str2 = "";
        if (i < 2 || i > 36) {
            return "";
        }
        if (Double.isNaN(d)) {
            return "NaN";
        }
        if (d == Double.POSITIVE_INFINITY) {
            return "Infinity";
        }
        if (d == Double.NEGATIVE_INFINITY) {
            return "-Infinity";
        }
        if (i == 10) {
            return ClassLogWrapper.INSTANCE.wrapNumberText(Double.valueOf(d));
        }
        if (d < 0.0d) {
            dFloor = java.lang.Math.ceil(d);
            dAbs = java.lang.Math.abs(dFloor - d);
        } else {
            dFloor = java.lang.Math.floor(d);
            dAbs = d - dFloor;
        }
        if (dFloor > 9.223372036854776E18d) {
            BigDecimal bigDecimalValueOf = BigDecimal.valueOf(dFloor);
            Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "valueOf(...)");
            strIntToString = bigIntToString(bigDecimalValueOf, i);
        } else {
            strIntToString = intToString(Double.valueOf(d), i);
        }
        while (dAbs > 0.0d && str2.length() < 60) {
            double d2 = dAbs * i;
            double dFloor2 = java.lang.Math.floor(d2);
            if (dFloor2 > 9.223372036854776E18d) {
                str = str2 + bigIntToString(new BigDecimal(dFloor2), i);
            } else {
                str = str2 + intToString(Long.valueOf((long) dFloor2), i);
            }
            str2 = str;
            dAbs = d2 - dFloor2;
        }
        if (str2.length() == 0) {
            return strIntToString;
        }
        return strIntToString + Operators.DOT + str2;
    }

    public static final String toPrecision(Number number, Number precision) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(precision, "precision");
        StringBuilder sb = new StringBuilder("%.");
        if (precision.intValue() <= 0) {
            precision = (Number) 16;
        }
        sb.append(precision);
        sb.append('g');
        String str = String.format(sb.toString(), Arrays.copyOf(new java.lang.Object[]{number}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return StringKt.replace(StringKt.replace(str, "e+0", "e+"), "e-0", "e-");
    }

    public static final int compareTo(Number number, Number other) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (number instanceof Byte) {
            byte bByteValue = number.byteValue();
            return other instanceof Integer ? Intrinsics.compare((int) bByteValue, other.intValue()) : other instanceof Byte ? Intrinsics.compare((int) bByteValue, (int) other.byteValue()) : other instanceof Short ? Intrinsics.compare((int) bByteValue, (int) other.shortValue()) : other instanceof Long ? Intrinsics.compare(bByteValue, other.longValue()) : other instanceof Float ? Float.compare(bByteValue, other.floatValue()) : Double.compare(bByteValue, other.doubleValue());
        }
        if (number instanceof Short) {
            short sShortValue = number.shortValue();
            return other instanceof Integer ? Intrinsics.compare((int) sShortValue, other.intValue()) : other instanceof Byte ? Intrinsics.compare((int) sShortValue, (int) other.byteValue()) : other instanceof Short ? Intrinsics.compare((int) sShortValue, (int) other.shortValue()) : other instanceof Long ? Intrinsics.compare(sShortValue, other.longValue()) : other instanceof Float ? Float.compare(sShortValue, other.floatValue()) : Double.compare(sShortValue, other.doubleValue());
        }
        if (number instanceof Integer) {
            int iIntValue = number.intValue();
            return other instanceof Integer ? Intrinsics.compare(iIntValue, other.intValue()) : other instanceof Byte ? Intrinsics.compare(iIntValue, (int) other.byteValue()) : other instanceof Short ? Intrinsics.compare(iIntValue, (int) other.shortValue()) : other instanceof Long ? Intrinsics.compare(iIntValue, other.longValue()) : other instanceof Float ? Float.compare(iIntValue, other.floatValue()) : Double.compare(iIntValue, other.doubleValue());
        }
        if (number instanceof Long) {
            long jLongValue = number.longValue();
            return other instanceof Integer ? Intrinsics.compare(jLongValue, other.intValue()) : other instanceof Byte ? Intrinsics.compare(jLongValue, other.byteValue()) : other instanceof Short ? Intrinsics.compare(jLongValue, other.shortValue()) : other instanceof Long ? Intrinsics.compare(jLongValue, other.longValue()) : other instanceof Float ? Float.compare(jLongValue, other.floatValue()) : Double.compare(jLongValue, other.doubleValue());
        }
        if (number instanceof Float) {
            float fFloatValue = number.floatValue();
            return other instanceof Integer ? Float.compare(fFloatValue, other.intValue()) : other instanceof Byte ? Float.compare(fFloatValue, other.byteValue()) : other instanceof Short ? Float.compare(fFloatValue, other.shortValue()) : other instanceof Long ? Float.compare(fFloatValue, other.longValue()) : other instanceof Float ? Float.compare(fFloatValue, other.floatValue()) : Double.compare(fFloatValue, other.doubleValue());
        }
        double dDoubleValue = number.doubleValue();
        return other instanceof Integer ? Double.compare(dDoubleValue, other.intValue()) : other instanceof Byte ? Double.compare(dDoubleValue, other.byteValue()) : other instanceof Short ? Double.compare(dDoubleValue, other.shortValue()) : other instanceof Long ? Double.compare(dDoubleValue, other.longValue()) : other instanceof Float ? Double.compare(dDoubleValue, other.floatValue()) : Double.compare(dDoubleValue, other.doubleValue());
    }

    public static final Number plus(Number number, Number other) {
        Number numberValueOf;
        double dLongValue;
        int iShortValue;
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer)) {
            int iIntValue = number.intValue();
            if ((other instanceof Integer) || (other instanceof Byte) || (other instanceof Short)) {
                try {
                    return Integer.valueOf(UByte$$ExternalSyntheticBackport0.m(iIntValue, other.intValue()));
                } catch (ArithmeticException unused) {
                    return Long.valueOf(iIntValue + other.longValue());
                }
            }
            if (other instanceof Long) {
                try {
                    return Long.valueOf(UByte$$ExternalSyntheticBackport0.m603m(iIntValue, other.longValue()));
                } catch (ArithmeticException unused2) {
                    return Double.valueOf(iIntValue + other.longValue());
                }
            }
            numberValueOf = other instanceof Float ? Float.valueOf(iIntValue + other.floatValue()) : Double.valueOf(iIntValue + other.doubleValue());
        } else if (number instanceof Long) {
            long jLongValue = number.longValue();
            if ((other instanceof Integer) || (other instanceof Byte) || (other instanceof Short) || (other instanceof Long)) {
                try {
                    return Long.valueOf(UByte$$ExternalSyntheticBackport0.m603m(jLongValue, other.longValue()));
                } catch (ArithmeticException unused3) {
                    return Double.valueOf(jLongValue + other.doubleValue());
                }
            }
            numberValueOf = other instanceof Float ? Float.valueOf(jLongValue + other.floatValue()) : Double.valueOf(jLongValue + other.doubleValue());
        } else if (number instanceof Float) {
            float fFloatValue = number.floatValue();
            if (other instanceof Integer) {
                numberValueOf = Float.valueOf(fFloatValue + other.intValue());
            } else if (other instanceof Byte) {
                numberValueOf = Float.valueOf(fFloatValue + other.byteValue());
            } else if (other instanceof Short) {
                numberValueOf = Float.valueOf(fFloatValue + other.shortValue());
            } else if (other instanceof Long) {
                numberValueOf = Float.valueOf(fFloatValue + other.longValue());
            } else {
                numberValueOf = other instanceof Float ? Float.valueOf(fFloatValue + other.floatValue()) : Double.valueOf(fFloatValue + other.doubleValue());
            }
        } else {
            double dDoubleValue = number.doubleValue();
            if (other instanceof Integer) {
                iShortValue = other.intValue();
            } else if (other instanceof Byte) {
                iShortValue = other.byteValue();
            } else if (other instanceof Short) {
                iShortValue = other.shortValue();
            } else {
                if (other instanceof Long) {
                    dLongValue = other.longValue();
                } else {
                    dLongValue = other instanceof Float ? other.floatValue() : other.doubleValue();
                }
                numberValueOf = Double.valueOf(dDoubleValue + dLongValue);
            }
            dLongValue = iShortValue;
            numberValueOf = Double.valueOf(dDoubleValue + dLongValue);
        }
        return numberValueOf;
    }

    public static final String plus(Number number, String other) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return number + other;
    }

    public static final Number minus(Number number, Number other) {
        Number numberValueOf;
        double dLongValue;
        int iShortValue;
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer)) {
            int iIntValue = number.intValue();
            if ((other instanceof Integer) || (other instanceof Byte) || (other instanceof Short)) {
                try {
                    return Integer.valueOf(UByte$$ExternalSyntheticBackport0.m$2(iIntValue, other.intValue()));
                } catch (ArithmeticException unused) {
                    return Long.valueOf(iIntValue - other.longValue());
                }
            }
            if (other instanceof Long) {
                try {
                    return Long.valueOf(UByte$$ExternalSyntheticBackport0.m$2(iIntValue, other.longValue()));
                } catch (ArithmeticException unused2) {
                    return Double.valueOf(iIntValue - other.longValue());
                }
            }
            numberValueOf = other instanceof Float ? Float.valueOf(iIntValue - other.floatValue()) : Double.valueOf(iIntValue - other.doubleValue());
        } else if (number instanceof Long) {
            long jLongValue = number.longValue();
            if ((other instanceof Integer) || (other instanceof Byte) || (other instanceof Short) || (other instanceof Long)) {
                try {
                    return Long.valueOf(UByte$$ExternalSyntheticBackport0.m$2(jLongValue, other.longValue()));
                } catch (ArithmeticException unused3) {
                    return Double.valueOf(jLongValue - other.doubleValue());
                }
            }
            numberValueOf = other instanceof Float ? Float.valueOf(jLongValue - other.floatValue()) : Double.valueOf(jLongValue - other.doubleValue());
        } else if (number instanceof Float) {
            float fFloatValue = number.floatValue();
            if (other instanceof Integer) {
                numberValueOf = Float.valueOf(fFloatValue - other.intValue());
            } else if (other instanceof Byte) {
                numberValueOf = Float.valueOf(fFloatValue - other.byteValue());
            } else if (other instanceof Short) {
                numberValueOf = Float.valueOf(fFloatValue - other.shortValue());
            } else if (other instanceof Long) {
                numberValueOf = Float.valueOf(fFloatValue - other.longValue());
            } else {
                numberValueOf = other instanceof Float ? Float.valueOf(fFloatValue - other.floatValue()) : Double.valueOf(fFloatValue - other.doubleValue());
            }
        } else {
            double dDoubleValue = number.doubleValue();
            if (other instanceof Integer) {
                iShortValue = other.intValue();
            } else if (other instanceof Byte) {
                iShortValue = other.byteValue();
            } else if (other instanceof Short) {
                iShortValue = other.shortValue();
            } else {
                if (other instanceof Long) {
                    dLongValue = other.longValue();
                } else {
                    dLongValue = other instanceof Float ? other.floatValue() : other.doubleValue();
                }
                numberValueOf = Double.valueOf(dDoubleValue - dLongValue);
            }
            dLongValue = iShortValue;
            numberValueOf = Double.valueOf(dDoubleValue - dLongValue);
        }
        return numberValueOf;
    }

    public static final Number times(Number number, Number other) {
        Number numberValueOf;
        double dLongValue;
        int iShortValue;
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer)) {
            int iIntValue = number.intValue();
            if ((other instanceof Integer) || (other instanceof Byte) || (other instanceof Short)) {
                try {
                    return Integer.valueOf(UByte$$ExternalSyntheticBackport0.m$1(iIntValue, other.intValue()));
                } catch (ArithmeticException unused) {
                    return Long.valueOf(iIntValue * other.longValue());
                }
            }
            if (other instanceof Long) {
                try {
                    return Long.valueOf(UByte$$ExternalSyntheticBackport0.m$1(iIntValue, other.longValue()));
                } catch (ArithmeticException unused2) {
                    return Double.valueOf(iIntValue * other.longValue());
                }
            }
            numberValueOf = other instanceof Float ? Float.valueOf(iIntValue * other.floatValue()) : Double.valueOf(iIntValue * other.doubleValue());
        } else if (number instanceof Long) {
            long jLongValue = number.longValue();
            if ((other instanceof Integer) || (other instanceof Byte) || (other instanceof Short) || (other instanceof Long)) {
                try {
                    return Long.valueOf(UByte$$ExternalSyntheticBackport0.m$1(jLongValue, other.longValue()));
                } catch (ArithmeticException unused3) {
                    return Double.valueOf(jLongValue * other.doubleValue());
                }
            }
            numberValueOf = other instanceof Float ? Float.valueOf(jLongValue * other.floatValue()) : Double.valueOf(jLongValue * other.doubleValue());
        } else if (number instanceof Float) {
            float fFloatValue = number.floatValue();
            if (other instanceof Integer) {
                numberValueOf = Float.valueOf(fFloatValue * other.intValue());
            } else if (other instanceof Byte) {
                numberValueOf = Float.valueOf(fFloatValue * other.byteValue());
            } else if (other instanceof Short) {
                numberValueOf = Float.valueOf(fFloatValue * other.shortValue());
            } else if (other instanceof Long) {
                numberValueOf = Float.valueOf(fFloatValue * other.longValue());
            } else {
                numberValueOf = other instanceof Float ? Float.valueOf(fFloatValue * other.floatValue()) : Double.valueOf(fFloatValue * other.doubleValue());
            }
        } else {
            double dDoubleValue = number.doubleValue();
            if (other instanceof Integer) {
                iShortValue = other.intValue();
            } else if (other instanceof Byte) {
                iShortValue = other.byteValue();
            } else if (other instanceof Short) {
                iShortValue = other.shortValue();
            } else {
                if (other instanceof Long) {
                    dLongValue = other.longValue();
                } else {
                    dLongValue = other instanceof Float ? other.floatValue() : other.doubleValue();
                }
                numberValueOf = Double.valueOf(dDoubleValue * dLongValue);
            }
            dLongValue = iShortValue;
            numberValueOf = Double.valueOf(dDoubleValue * dLongValue);
        }
        return numberValueOf;
    }

    public static final Number div(Number number, Number other) {
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        boolean z = true;
        boolean z2 = (other instanceof Byte) || (other instanceof Short) || (other instanceof Integer) || (other instanceof Long);
        if (!(number instanceof Byte) && !(number instanceof Short) && !(number instanceof Integer) && !(number instanceof Long)) {
            z = false;
        }
        if (z2) {
            if (other.longValue() == 0) {
                long jLongValue = number.longValue();
                if (jLongValue == 0) {
                    return Double.valueOf(Double.NaN);
                }
                if (jLongValue > 0) {
                    return Double.valueOf(Double.POSITIVE_INFINITY);
                }
                return Double.valueOf(Double.NEGATIVE_INFINITY);
            }
        } else if (other.doubleValue() == 0.0d) {
            double dDoubleValue = number.doubleValue();
            if (dDoubleValue == 0.0d) {
                return Double.valueOf(Double.NaN);
            }
            if (dDoubleValue > 0.0d) {
                return Double.valueOf(Double.POSITIVE_INFINITY);
            }
            return Double.valueOf(Double.NEGATIVE_INFINITY);
        }
        if (z2 && z && Intrinsics.areEqual((java.lang.Object) rem(number, other), (java.lang.Object) 0)) {
            numberValueOf = Long.valueOf(number.longValue() / other.longValue());
        } else {
            numberValueOf = Double.valueOf(number.doubleValue() / other.doubleValue());
        }
        return numberValueOf;
    }

    public static final Number rem(Number number, Number other) {
        double dLongValue;
        int iShortValue;
        Number numberValueOf;
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (number instanceof Byte) {
            byte bByteValue = number.byteValue();
            if (other instanceof Integer) {
                numberValueOf = Integer.valueOf(bByteValue % other.intValue());
            } else if (other instanceof Byte) {
                numberValueOf = Integer.valueOf(bByteValue % other.byteValue());
            } else if (other instanceof Short) {
                numberValueOf = Integer.valueOf(bByteValue % other.shortValue());
            } else if (other instanceof Long) {
                numberValueOf = Long.valueOf(bByteValue % other.longValue());
            } else {
                numberValueOf = other instanceof Float ? Float.valueOf(bByteValue % other.floatValue()) : Double.valueOf(bByteValue % other.doubleValue());
            }
        } else if (number instanceof Short) {
            short sShortValue = number.shortValue();
            if (other instanceof Integer) {
                numberValueOf = Integer.valueOf(sShortValue % other.intValue());
            } else if (other instanceof Byte) {
                numberValueOf = Integer.valueOf(sShortValue % other.byteValue());
            } else if (other instanceof Short) {
                numberValueOf = Integer.valueOf(sShortValue % other.shortValue());
            } else if (other instanceof Long) {
                numberValueOf = Long.valueOf(sShortValue % other.longValue());
            } else {
                numberValueOf = other instanceof Float ? Float.valueOf(sShortValue % other.floatValue()) : Double.valueOf(sShortValue % other.doubleValue());
            }
        } else if (number instanceof Integer) {
            int iIntValue = number.intValue();
            if (other instanceof Integer) {
                numberValueOf = Integer.valueOf(iIntValue % other.intValue());
            } else if (other instanceof Byte) {
                numberValueOf = Integer.valueOf(iIntValue % other.byteValue());
            } else if (other instanceof Short) {
                numberValueOf = Integer.valueOf(iIntValue % other.shortValue());
            } else if (other instanceof Long) {
                numberValueOf = Long.valueOf(iIntValue % other.longValue());
            } else {
                numberValueOf = other instanceof Float ? Float.valueOf(iIntValue % other.floatValue()) : Double.valueOf(iIntValue % other.doubleValue());
            }
        } else if (number instanceof Long) {
            long jLongValue = number.longValue();
            if (other instanceof Integer) {
                numberValueOf = Long.valueOf(jLongValue % other.intValue());
            } else if (other instanceof Byte) {
                numberValueOf = Long.valueOf(jLongValue % other.byteValue());
            } else if (other instanceof Short) {
                numberValueOf = Long.valueOf(jLongValue % other.shortValue());
            } else if (other instanceof Long) {
                numberValueOf = Long.valueOf(jLongValue % other.longValue());
            } else {
                numberValueOf = other instanceof Float ? Float.valueOf(jLongValue % other.floatValue()) : Double.valueOf(jLongValue % other.doubleValue());
            }
        } else if (number instanceof Float) {
            float fFloatValue = number.floatValue();
            if (other instanceof Integer) {
                numberValueOf = Float.valueOf(fFloatValue % other.intValue());
            } else if (other instanceof Byte) {
                numberValueOf = Float.valueOf(fFloatValue % other.byteValue());
            } else if (other instanceof Short) {
                numberValueOf = Float.valueOf(fFloatValue % other.shortValue());
            } else if (other instanceof Long) {
                numberValueOf = Float.valueOf(fFloatValue % other.longValue());
            } else {
                numberValueOf = other instanceof Float ? Float.valueOf(fFloatValue % other.floatValue()) : Double.valueOf(fFloatValue % other.doubleValue());
            }
        } else {
            double dDoubleValue = number.doubleValue();
            if (other instanceof Integer) {
                iShortValue = other.intValue();
            } else if (other instanceof Byte) {
                iShortValue = other.byteValue();
            } else if (other instanceof Short) {
                iShortValue = other.shortValue();
            } else {
                if (other instanceof Long) {
                    dLongValue = other.longValue();
                } else {
                    dLongValue = other instanceof Float ? other.floatValue() : other.doubleValue();
                }
                numberValueOf = Double.valueOf(dDoubleValue % dLongValue);
            }
            dLongValue = iShortValue;
            numberValueOf = Double.valueOf(dDoubleValue % dLongValue);
        }
        return numberValueOf;
    }

    public static final Number inc(Number number) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return number instanceof Integer ? Integer.valueOf(number.intValue() + 1) : number instanceof Byte ? Byte.valueOf((byte) (number.byteValue() + 1)) : number instanceof Short ? Short.valueOf((short) (number.shortValue() + 1)) : number instanceof Long ? Long.valueOf(number.longValue() + 1) : number instanceof Float ? Float.valueOf(number.floatValue() + 1.0f) : Double.valueOf(number.doubleValue() + 1.0d);
    }

    public static final Number dec(Number number) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return number instanceof Integer ? Integer.valueOf(number.intValue() - 1) : number instanceof Byte ? Byte.valueOf((byte) (number.byteValue() - 1)) : number instanceof Short ? Short.valueOf((short) (number.shortValue() - 1)) : number instanceof Long ? Long.valueOf(number.longValue() - 1) : number instanceof Float ? Float.valueOf(number.floatValue() - 1.0f) : Double.valueOf(number.doubleValue() - 1.0d);
    }

    public static final Number unaryPlus(Number number) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return number instanceof Integer ? Integer.valueOf(number.intValue()) : number instanceof Byte ? Integer.valueOf(number.byteValue()) : number instanceof Short ? Integer.valueOf(number.shortValue()) : number instanceof Long ? Long.valueOf(number.longValue()) : number instanceof Float ? Float.valueOf(number.floatValue()) : Double.valueOf(number.doubleValue());
    }

    public static final Number unaryMinus(Number number) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return number instanceof Integer ? Integer.valueOf(-number.intValue()) : number instanceof Byte ? Integer.valueOf(-number.byteValue()) : number instanceof Short ? Integer.valueOf(-number.shortValue()) : number instanceof Long ? Long.valueOf(-number.longValue()) : number instanceof Float ? Float.valueOf(-number.floatValue()) : Double.valueOf(-number.doubleValue());
    }

    public static final Number and(Number number, Number next) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(next, "next");
        return Integer.valueOf(number.intValue() & next.intValue());
    }

    public static final Number or(Number number, Number next) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(next, "next");
        if (!(next instanceof Double) && !(next instanceof Float) && !(number instanceof Double) && !(number instanceof Float)) {
            return Integer.valueOf(number.intValue() | next.intValue());
        }
        return Integer.valueOf(JSNumberUtil.INSTANCE.doubleToInt32(number.doubleValue()) | JSNumberUtil.INSTANCE.doubleToInt32(next.doubleValue()));
    }

    public static final Number xor(Number number, Number next) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(next, "next");
        return Integer.valueOf(number.intValue() ^ next.intValue());
    }

    public static final Number inv(Number number) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return Integer.valueOf(~number.intValue());
    }

    public static final Number shl(Number number, Number next) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(next, "next");
        return Integer.valueOf(number.intValue() << next.intValue());
    }

    public static final Number shr(Number number, Number next) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(next, "next");
        return Integer.valueOf(number.intValue() >> next.intValue());
    }

    public static final Number ushr(Number number, Number next) {
        int iDoubleToInt32;
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(next, "next");
        if ((number instanceof Double) || (number instanceof Float)) {
            iDoubleToInt32 = JSNumberUtil.INSTANCE.doubleToInt32(number.doubleValue());
        } else {
            iDoubleToInt32 = (int) (number.longValue() & InternalZipConstants.ZIP_64_SIZE_LIMIT);
        }
        int iIntValue = iDoubleToInt32 >>> (next.intValue() & 31);
        if (iIntValue < 0) {
            return Long.valueOf(iIntValue & InternalZipConstants.ZIP_64_SIZE_LIMIT);
        }
        return Long.valueOf(iIntValue);
    }

    public static final IntRange rangeTo(Number number, Number other) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return rangeTo(number.intValue(), other);
    }

    public static final IntRange rangeTo(Number number, int i) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return new IntRange(number.intValue(), i);
    }

    public static final LongRange rangeTo(Number number, long j) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        return new LongRange(number.longValue(), j);
    }

    public static final IntRange rangeTo(int i, Number other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new IntRange(i, other.intValue());
    }

    public static final LongRange rangeTo(long j, Number other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new LongRange(j, other.longValue());
    }
}

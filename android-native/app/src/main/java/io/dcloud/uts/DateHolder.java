package io.dcloud.uts;

import com.facebook.common.statfs.StatFsHelper;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXRequest;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.Calendar;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Date2.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0013\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ \u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rJ\u0018\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0007H\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0013H\u0002J\u0016\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\tH\u0002J\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tH\u0002J\u0010\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0013H\u0002J\u0010\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\u0013H\u0002J/\u0010 \u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\"\"\u00020\u0005¢\u0006\u0002\u0010#R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lio/dcloud/uts/DateHolder;", "", "<init>", "()V", "getDateField", "", "dateDouble", "", "magic", "", "getDateFields", "", "isLocal", "", AbsoluteConst.INSTALL_OPTIONS_FORCE, "mathMod", "a", "b", "floorDiv", "", "yearFromDays", "days", "setDateFields", "fields", "monthDays", "", "isLeapYear", Constants.Name.Y, "daysInYear", "daysFromYear", "getTimezoneOffset", "timestamp", "setDateField", "args", "", "(DI[Ljava/lang/Number;)D", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class DateHolder {
    public static final DateHolder INSTANCE = new DateHolder();
    private static final int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private final double mathMod(double a, double b) {
        return ((a % b) + b) % b;
    }

    private DateHolder() {
    }

    public final Number getDateField(double dateDouble, int magic) {
        boolean z = (magic & 15) != 0;
        int i = (magic >> 4) & 15;
        boolean z2 = (magic & 256) != 0;
        double[] dateFields = getDateFields(dateDouble, z, false);
        if (dateFields == null) {
            return Double.valueOf(Double.NaN);
        }
        if (z2 && i == 0) {
            return Double.valueOf(dateFields[0] - 1900);
        }
        return Long.valueOf((long) dateFields[i]);
    }

    public final double[] getDateFields(double dateDouble, boolean isLocal, boolean force) {
        double d;
        double[] dArr;
        double d2;
        char c;
        double d3 = dateDouble;
        double[] dArr2 = new double[9];
        double d4 = 0.0d;
        if (Double.isNaN(d3)) {
            if (!force) {
                return null;
            }
            for (int i = 0; i < 9; i++) {
                dArr2[i] = 0.0d;
            }
            return dArr2;
        }
        if (isLocal) {
            d4 = -getTimezoneOffset((long) d3);
            d3 += 60000.0d * d4;
        }
        double dMathMod = mathMod(d3, 8.64E7d);
        long j = (long) ((d3 - dMathMod) / 8.64E7d);
        double d5 = dMathMod % 1000.0d;
        double d6 = (dMathMod - d5) / 1000.0d;
        double d7 = d6 % 60.0d;
        double d8 = (d6 - d7) / 60.0d;
        double d9 = d8 % 60.0d;
        double d10 = (d8 - d9) / 60.0d;
        double[] dArr3 = dArr2;
        double dMathMod2 = mathMod(j + 4.0d, 7.0d);
        int iYearFromDays = yearFromDays(j);
        long jDaysFromYear = j - daysFromYear(iYearFromDays);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            d = dMathMod2;
            if (i2 >= 11) {
                dArr = dArr3;
                d2 = d4;
                c = 1;
                i2 = i3;
                break;
            }
            dArr = dArr3;
            double dDaysInYear = monthDays[i2];
            if (i2 == 1) {
                c = 1;
                dDaysInYear += daysInYear(iYearFromDays) - 365;
            } else {
                c = 1;
            }
            d2 = d4;
            if (jDaysFromYear < dDaysInYear) {
                break;
            }
            jDaysFromYear -= (long) dDaysInYear;
            i3 = i2 + 1;
            dMathMod2 = d;
            dArr3 = dArr;
            i2 = i3;
            d4 = d2;
        }
        dArr[0] = iYearFromDays;
        dArr[c] = i2;
        dArr[2] = jDaysFromYear + 1;
        dArr[3] = d10;
        dArr[4] = d9;
        dArr[5] = d7;
        dArr[6] = d5;
        dArr[7] = d;
        dArr[8] = d2;
        return dArr;
    }

    private final long floorDiv(long a, long b) {
        long j = a % b;
        return (a - (j + (j < 0 ? b : 0L))) / b;
    }

    private final int yearFromDays(long days) {
        if (days < 0) {
            int i = 1969;
            while (days < daysFromYear(i)) {
                i--;
            }
            return i;
        }
        int i2 = 1970;
        while (true) {
            int i3 = i2 + 1;
            if (days < daysFromYear(i3)) {
                return i2;
            }
            i2 = i3;
        }
    }

    public final double setDateFields(double[] fields, boolean isLocal) {
        Intrinsics.checkNotNullParameter(fields, "fields");
        double d = fields[0];
        double d2 = fields[1];
        double d3 = fields[2];
        double d4 = 12;
        double dFloor = d + java.lang.Math.floor(d2 / d4);
        double d5 = d2 % d4;
        if (d5 < 0.0d) {
            d5 += d4;
        }
        if (dFloor >= -271821.0d && dFloor <= 275760.0d) {
            int i = (int) d5;
            long jDaysFromYear = daysFromYear((int) dFloor);
            for (int i2 = 0; i2 < i; i2++) {
                jDaysFromYear += monthDays[i2];
                if (i2 == 1) {
                    jDaysFromYear += daysInYear(r3) - 365;
                }
            }
            double timezoneOffset = (((jDaysFromYear + d3) - 1) * 86400000) + (fields[3] * 3600000) + (fields[4] * WXRequest.DEFAULT_TIMEOUT_MS) + (fields[5] * 1000) + fields[6];
            if (java.lang.Math.abs(timezoneOffset) <= Double.MAX_VALUE) {
                if (isLocal) {
                    timezoneOffset += getTimezoneOffset(timezoneOffset < -9.223372036854776E18d ? Long.MIN_VALUE : timezoneOffset >= 9.223372036854776E18d ? Long.MAX_VALUE : (long) timezoneOffset) * 60000.0d;
                }
                return DateParser.INSTANCE.timeClip(timezoneOffset);
            }
        }
        return Double.NaN;
    }

    private final boolean isLeapYear(int y) {
        if (y % 4 == 0) {
            return y % 100 != 0 || y % StatFsHelper.DEFAULT_DISK_YELLOW_LEVEL_IN_MB == 0;
        }
        return false;
    }

    private final int daysInYear(int y) {
        return isLeapYear(y) ? 366 : 365;
    }

    private final long daysFromYear(long y) {
        return (((365 * (y - 1970)) + floorDiv(y - 1969, 4L)) - floorDiv(y - 1901, 100L)) + floorDiv(y - 1601, 400L);
    }

    private final int getTimezoneOffset(long timestamp) {
        java.util.Date date = new java.util.Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (-calendar.getTimeZone().getOffset(timestamp)) / WXRequest.DEFAULT_TIMEOUT_MS;
    }

    public final double setDateField(double dateDouble, int magic, Number... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        int i = (magic >> 8) & 15;
        int i2 = (magic >> 4) & 15;
        boolean z = (magic & 15) != 0;
        double[] dateFields = getDateFields(dateDouble, z, i == 0);
        if (dateFields == null) {
            return Double.NaN;
        }
        int iMin = java.lang.Math.min(args.length, i2 - i);
        for (int i3 = 0; i3 < iMin; i3++) {
            double dDoubleValue = args[i3].doubleValue();
            if (java.lang.Math.abs(dDoubleValue) > Double.MAX_VALUE) {
                return Double.NaN;
            }
            dateFields[i + i3] = java.lang.Math.floor(dDoubleValue);
        }
        return !(args.length == 0) ? setDateFields(dateFields, z) : dateDouble;
    }
}

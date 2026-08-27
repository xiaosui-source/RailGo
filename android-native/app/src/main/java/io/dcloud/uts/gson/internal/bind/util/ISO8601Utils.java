package io.dcloud.uts.gson.internal.bind.util;

import com.taobao.weex.common.WXRequest;
import com.taobao.weex.el.parse.Operators;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder(19 + (z ? 4 : 0) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(sb, gregorianCalendar.get(1), 4);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(Operators.CONDITION_IF_MIDDLE);
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(Operators.CONDITION_IF_MIDDLE);
        padInt(sb, gregorianCalendar.get(13), 2);
        if (z) {
            sb.append(Operators.DOT);
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / WXRequest.DEFAULT_TIMEOUT_MS;
            int iAbs = Math.abs(i / 60);
            int iAbs2 = Math.abs(i % 60);
            sb.append(offset >= 0 ? '+' : '-');
            padInt(sb, iAbs, 2);
            sb.append(Operators.CONDITION_IF_MIDDLE);
            padInt(sb, iAbs2, 2);
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00dc A[Catch: IllegalArgumentException -> 0x01be, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, IndexOutOfBoundsException -> 0x01c2, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, blocks: (B:3:0x000a, B:5:0x001d, B:6:0x001f, B:8:0x002b, B:9:0x002d, B:11:0x003c, B:13:0x0042, B:17:0x0056, B:19:0x0066, B:20:0x0068, B:22:0x0074, B:23:0x0077, B:25:0x007d, B:29:0x0087, B:34:0x0097, B:36:0x009f, B:48:0x00d4, B:50:0x00dc, B:52:0x00e2, B:77:0x0184, B:57:0x00ef, B:58:0x0105, B:59:0x0106, B:63:0x0123, B:65:0x0130, B:68:0x0139, B:70:0x0153, B:73:0x0162, B:74:0x017f, B:76:0x0182, B:62:0x0112, B:79:0x01b6, B:80:0x01bd, B:41:0x00b8, B:42:0x00bb), top: B:96:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01b6 A[Catch: IllegalArgumentException -> 0x01be, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, IndexOutOfBoundsException -> 0x01c2, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c0, blocks: (B:3:0x000a, B:5:0x001d, B:6:0x001f, B:8:0x002b, B:9:0x002d, B:11:0x003c, B:13:0x0042, B:17:0x0056, B:19:0x0066, B:20:0x0068, B:22:0x0074, B:23:0x0077, B:25:0x007d, B:29:0x0087, B:34:0x0097, B:36:0x009f, B:48:0x00d4, B:50:0x00dc, B:52:0x00e2, B:77:0x0184, B:57:0x00ef, B:58:0x0105, B:59:0x0106, B:63:0x0123, B:65:0x0130, B:68:0x0139, B:70:0x0153, B:73:0x0162, B:74:0x017f, B:76:0x0182, B:62:0x0112, B:79:0x01b6, B:80:0x01bd, B:41:0x00b8, B:42:0x00bb), top: B:96:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.Date parse(java.lang.String r18, java.text.ParsePosition r19) throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 548
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.uts.gson.internal.bind.util.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int iDigit = Character.digit(str.charAt(i), 10);
            if (iDigit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = -iDigit;
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int iDigit2 = Character.digit(str.charAt(i4), 10);
            if (iDigit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = (i3 * 10) - iDigit2;
            i4 = i5;
        }
        return -i3;
    }

    private static void padInt(StringBuilder sb, int i, int i2) {
        String string = Integer.toString(i);
        for (int length = i2 - string.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(string);
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}

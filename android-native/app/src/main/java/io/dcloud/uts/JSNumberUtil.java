package io.dcloud.uts;

import kotlin.Metadata;

/* compiled from: JSNumberUtil.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lio/dcloud/uts/JSNumberUtil;", "", "<init>", "()V", "doubleToInt32", "", "d", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class JSNumberUtil {
    public static final JSNumberUtil INSTANCE = new JSNumberUtil();

    private JSNumberUtil() {
    }

    public final int doubleToInt32(double d) {
        if (!NumberKt.isNaN(Double.valueOf(d)) && !Double.isInfinite(d)) {
            long jDoubleToRawLongBits = Double.doubleToRawLongBits(d);
            int i = (int) ((jDoubleToRawLongBits >> 52) & 2047);
            if (i <= 1053) {
                return (int) d;
            }
            if (i <= 1106) {
                long j = (((4503599627370495L & jDoubleToRawLongBits) | 4503599627370496L) << (i - 1043)) >>> 32;
                if ((jDoubleToRawLongBits >> 63) != 0 && j != -2147483648L) {
                    j = -j;
                }
                return (int) j;
            }
        }
        return 0;
    }
}

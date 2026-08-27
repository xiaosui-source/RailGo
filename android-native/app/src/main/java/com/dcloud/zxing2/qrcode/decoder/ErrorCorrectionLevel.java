package com.dcloud.zxing2.qrcode.decoder;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 com.dcloud.zxing2.qrcode.decoder.ErrorCorrectionLevel, still in use, count: 1, list:
  (r0v0 com.dcloud.zxing2.qrcode.decoder.ErrorCorrectionLevel) from 0x002f: FILLED_NEW_ARRAY 
  (r1v1 com.dcloud.zxing2.qrcode.decoder.ErrorCorrectionLevel)
  (r0v0 com.dcloud.zxing2.qrcode.decoder.ErrorCorrectionLevel)
  (r5v1 com.dcloud.zxing2.qrcode.decoder.ErrorCorrectionLevel)
  (r4v1 com.dcloud.zxing2.qrcode.decoder.ErrorCorrectionLevel)
 A[WRAPPED] (LINE:19) elemType: com.dcloud.zxing2.qrcode.decoder.ErrorCorrectionLevel
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class ErrorCorrectionLevel {
    L(1),
    M(0),
    Q(3),
    H(2);

    private static final ErrorCorrectionLevel[] FOR_BITS;
    private final int bits;

    static {
        FOR_BITS = new ErrorCorrectionLevel[]{errorCorrectionLevel, new ErrorCorrectionLevel(1), errorCorrectionLevel, new ErrorCorrectionLevel(3)};
    }

    private ErrorCorrectionLevel(int i) {
        this.bits = i;
    }

    public static ErrorCorrectionLevel forBits(int i) {
        if (i >= 0) {
            ErrorCorrectionLevel[] errorCorrectionLevelArr = FOR_BITS;
            if (i < errorCorrectionLevelArr.length) {
                return errorCorrectionLevelArr[i];
            }
        }
        throw new IllegalArgumentException();
    }

    public static ErrorCorrectionLevel valueOf(String str) {
        return (ErrorCorrectionLevel) Enum.valueOf(ErrorCorrectionLevel.class, str);
    }

    public static ErrorCorrectionLevel[] values() {
        return (ErrorCorrectionLevel[]) $VALUES.clone();
    }

    public int getBits() {
        return this.bits;
    }
}

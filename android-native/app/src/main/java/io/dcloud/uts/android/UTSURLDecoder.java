package io.dcloud.uts.android;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSURLDecoder.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\f\n\u0000\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u0007J\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\"\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lio/dcloud/uts/android/UTSURLDecoder;", "", "isComponent", "", "<init>", "(Z)V", "dfltEncName", "Ljava/nio/charset/Charset;", "kotlin.jvm.PlatformType", "getDfltEncName", "()Ljava/nio/charset/Charset;", "setDfltEncName", "(Ljava/nio/charset/Charset;)V", "decode", "", "s", "charset", "isValidHexChar", "c", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSURLDecoder {
    private Charset dfltEncName;
    private boolean isComponent;

    private final boolean isValidHexChar(char c) {
        if ('0' <= c && c < ':') {
            return true;
        }
        if ('a' > c || c >= 'g') {
            return 'A' <= c && c < 'G';
        }
        return true;
    }

    public final Charset getDfltEncName() {
        return this.dfltEncName;
    }

    public final void setDfltEncName(Charset charset) {
        this.dfltEncName = charset;
    }

    public UTSURLDecoder(boolean z) {
        this.dfltEncName = Charset.forName("UTF-8");
        this.isComponent = z;
    }

    public /* synthetic */ UTSURLDecoder(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    public static /* synthetic */ String decode$default(UTSURLDecoder uTSURLDecoder, String str, Charset dfltEncName, int i, Object obj) {
        if ((i & 2) != 0) {
            dfltEncName = uTSURLDecoder.dfltEncName;
            Intrinsics.checkNotNullExpressionValue(dfltEncName, "dfltEncName");
        }
        return uTSURLDecoder.decode(str, dfltEncName);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b6, code lost:
    
        r15 = io.dcloud.uts.ObjectKt.getGlobalError();
        r0 = java.lang.Thread.currentThread().getName();
        r3 = new java.lang.StringBuilder();
        r3.append("URLDecoder: Illegal hex characters in escape (%) pattern : ");
        r14 = r14.substring(r4, r4 + 3);
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r14, "substring(...)");
        r3.append(r14);
        r15.put(r0, new java.lang.IllegalArgumentException(r3.toString()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00e4, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00e5, code lost:
    
        if (r4 >= r0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e7, code lost:
    
        if (r7 != '%') goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e9, code lost:
    
        io.dcloud.uts.ObjectKt.getGlobalError().put(java.lang.Thread.currentThread().getName(), new java.lang.IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ff, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0100, code lost:
    
        r1.append(new java.lang.String(r6, 0, r5, r15));
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0108, code lost:
    
        r5 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String decode(java.lang.String r14, java.nio.charset.Charset r15) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 320
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.uts.android.UTSURLDecoder.decode(java.lang.String, java.nio.charset.Charset):java.lang.String");
    }
}

package io.dcloud.common.adapter.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class UnicodeInputStream extends InputStream {
    private static final int BOM_SIZE = 4;
    String defaultEnc;
    String encoding;
    PushbackInputStream internalIn;
    boolean isInited = false;

    public UnicodeInputStream(InputStream inputStream, String str) {
        this.internalIn = new PushbackInputStream(inputStream, 4);
        this.defaultEnc = str;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.isInited = true;
        this.internalIn.close();
    }

    public String getDefaultEncoding() {
        return this.defaultEnc;
    }

    public String getEncoding() throws Throwable {
        if (!this.isInited) {
            try {
                init();
            } catch (IOException unused) {
                Throwable illegalStateException = new IllegalStateException("Init method failed.");
                illegalStateException.initCause(illegalStateException);
                throw illegalStateException;
            }
        }
        return this.encoding;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void init() throws java.io.IOException {
        /*
            r9 = this;
            boolean r0 = r9.isInited
            if (r0 == 0) goto L5
            return
        L5:
            r0 = 4
            byte[] r1 = new byte[r0]
            java.io.PushbackInputStream r2 = r9.internalIn
            r3 = 0
            int r0 = r2.read(r1, r3, r0)
            r2 = r1[r3]
            r3 = 3
            r4 = -2
            r5 = -1
            r6 = 2
            r7 = 1
            if (r2 != 0) goto L2b
            r8 = r1[r7]
            if (r8 != 0) goto L2b
            r8 = r1[r6]
            if (r8 != r4) goto L2b
            r8 = r1[r3]
            if (r8 != r5) goto L2b
            java.lang.String r2 = "UTF-32BE"
            r9.encoding = r2
        L28:
            int r2 = r0 + (-4)
            goto L72
        L2b:
            if (r2 != r5) goto L3e
            r8 = r1[r7]
            if (r8 != r4) goto L3e
            r8 = r1[r6]
            if (r8 != 0) goto L3e
            r3 = r1[r3]
            if (r3 != 0) goto L3e
            java.lang.String r2 = "UTF-32LE"
            r9.encoding = r2
            goto L28
        L3e:
            r3 = -17
            if (r2 != r3) goto L55
            r3 = r1[r7]
            r8 = -69
            if (r3 != r8) goto L55
            r3 = r1[r6]
            r6 = -65
            if (r3 != r6) goto L55
            java.lang.String r2 = "UTF-8"
            r9.encoding = r2
            int r2 = r0 + (-3)
            goto L72
        L55:
            if (r2 != r4) goto L62
            r3 = r1[r7]
            if (r3 != r5) goto L62
            java.lang.String r2 = "UTF-16BE"
            r9.encoding = r2
        L5f:
            int r2 = r0 + (-2)
            goto L72
        L62:
            if (r2 != r5) goto L6d
            r2 = r1[r7]
            if (r2 != r4) goto L6d
            java.lang.String r2 = "UTF-16LE"
            r9.encoding = r2
            goto L5f
        L6d:
            java.lang.String r2 = r9.defaultEnc
            r9.encoding = r2
            r2 = r0
        L72:
            if (r2 <= 0) goto L7a
            java.io.PushbackInputStream r3 = r9.internalIn
            int r0 = r0 - r2
            r3.unread(r1, r0, r2)
        L7a:
            r9.isInited = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.io.UnicodeInputStream.init():void");
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        init();
        this.isInited = true;
        return this.internalIn.read();
    }
}

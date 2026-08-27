package org.mozilla.universalchardet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class UnicodeBOMInputStream extends InputStream {
    private final BOM bom;
    private final PushbackInputStream in;
    private boolean skipped;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static final class BOM {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final byte[] bytes;
        private final String description;
        public static final BOM NONE = new BOM(new byte[0], "NONE");
        public static final BOM UTF_8 = new BOM(new byte[]{-17, -69, -65}, "UTF-8");
        public static final BOM UTF_16_LE = new BOM(new byte[]{-1, -2}, "UTF-16 little-endian");
        public static final BOM UTF_16_BE = new BOM(new byte[]{-2, -1}, "UTF-16 big-endian");
        public static final BOM UTF_32_LE = new BOM(new byte[]{-1, -2, 0, 0}, "UTF-32 little-endian");
        public static final BOM UTF_32_BE = new BOM(new byte[]{0, 0, -2, -1}, "UTF-32 big-endian");

        private BOM(byte[] bArr, String str) {
            this.bytes = bArr;
            this.description = str;
        }

        public final byte[] getBytes() {
            byte[] bArr = this.bytes;
            int length = bArr.length;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, length);
            return bArr2;
        }

        public final String toString() {
            return this.description;
        }
    }

    public UnicodeBOMInputStream(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    public final BOM getBOM() {
        return this.bom;
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i) {
        this.in.mark(i);
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.in.markSupported();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        this.skipped = true;
        return this.in.read();
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        this.in.reset();
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        this.skipped = true;
        return this.in.skip(j);
    }

    public final synchronized UnicodeBOMInputStream skipBOM() throws IOException {
        if (this.skipped) {
            return this;
        }
        long length = this.bom.bytes.length;
        for (long jSkip = this.in.skip(length); jSkip < length; jSkip++) {
            this.in.read();
        }
        this.skipped = true;
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public UnicodeBOMInputStream(java.io.InputStream r11, boolean r12) throws java.io.IOException {
        /*
            r10 = this;
            r10.<init>()
            r0 = 0
            r10.skipped = r0
            if (r11 == 0) goto L88
            java.io.PushbackInputStream r1 = new java.io.PushbackInputStream
            r2 = 4
            r1.<init>(r11, r2)
            r10.in = r1
            byte[] r11 = new byte[r2]
            int r3 = r1.read(r11)
            r4 = -2
            r5 = -1
            r6 = 2
            r7 = 1
            if (r3 == r6) goto L61
            r8 = 3
            if (r3 == r8) goto L4a
            if (r3 == r2) goto L22
            goto L79
        L22:
            r2 = r11[r0]
            if (r2 != r5) goto L37
            r9 = r11[r7]
            if (r9 != r4) goto L37
            r9 = r11[r6]
            if (r9 != 0) goto L37
            r9 = r11[r8]
            if (r9 != 0) goto L37
            org.mozilla.universalchardet.UnicodeBOMInputStream$BOM r2 = org.mozilla.universalchardet.UnicodeBOMInputStream.BOM.UTF_32_LE
            r10.bom = r2
            goto L7d
        L37:
            if (r2 != 0) goto L4a
            r2 = r11[r7]
            if (r2 != 0) goto L4a
            r2 = r11[r6]
            if (r2 != r4) goto L4a
            r2 = r11[r8]
            if (r2 != r5) goto L4a
            org.mozilla.universalchardet.UnicodeBOMInputStream$BOM r2 = org.mozilla.universalchardet.UnicodeBOMInputStream.BOM.UTF_32_BE
            r10.bom = r2
            goto L7d
        L4a:
            r2 = r11[r0]
            r8 = -17
            if (r2 != r8) goto L61
            r2 = r11[r7]
            r8 = -69
            if (r2 != r8) goto L61
            r2 = r11[r6]
            r6 = -65
            if (r2 != r6) goto L61
            org.mozilla.universalchardet.UnicodeBOMInputStream$BOM r2 = org.mozilla.universalchardet.UnicodeBOMInputStream.BOM.UTF_8
            r10.bom = r2
            goto L7d
        L61:
            r2 = r11[r0]
            if (r2 != r5) goto L6e
            r6 = r11[r7]
            if (r6 != r4) goto L6e
            org.mozilla.universalchardet.UnicodeBOMInputStream$BOM r2 = org.mozilla.universalchardet.UnicodeBOMInputStream.BOM.UTF_16_LE
            r10.bom = r2
            goto L7d
        L6e:
            if (r2 != r4) goto L79
            r2 = r11[r7]
            if (r2 != r5) goto L79
            org.mozilla.universalchardet.UnicodeBOMInputStream$BOM r2 = org.mozilla.universalchardet.UnicodeBOMInputStream.BOM.UTF_16_BE
            r10.bom = r2
            goto L7d
        L79:
            org.mozilla.universalchardet.UnicodeBOMInputStream$BOM r2 = org.mozilla.universalchardet.UnicodeBOMInputStream.BOM.NONE
            r10.bom = r2
        L7d:
            if (r3 <= 0) goto L82
            r1.unread(r11, r0, r3)
        L82:
            if (r12 == 0) goto L87
            r10.skipBOM()
        L87:
            return
        L88:
            java.lang.NullPointerException r11 = new java.lang.NullPointerException
            java.lang.String r12 = "invalid input stream: null is not allowed"
            r11.<init>(r12)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.universalchardet.UnicodeBOMInputStream.<init>(java.io.InputStream, boolean):void");
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        this.skipped = true;
        return this.in.read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.skipped = true;
        return this.in.read(bArr, i, i2);
    }
}

package net.lingala.zip4j.model;

import java.nio.charset.Charset;

/* loaded from: classes2.dex */
public class Zip4jConfig {
    private final int bufferSize;
    private final Charset charset;
    private final boolean useUtf8CharsetForPasswords;

    public Zip4jConfig(Charset charset, int i, boolean z) {
        this.charset = charset;
        this.bufferSize = i;
        this.useUtf8CharsetForPasswords = z;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public boolean isUseUtf8CharsetForPasswords() {
        return this.useUtf8CharsetForPasswords;
    }
}

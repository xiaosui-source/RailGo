package org.mozilla.universalchardet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public final class ReaderFactory {
    private ReaderFactory() {
        throw new AssertionError("No instances allowed");
    }

    public static Reader createReaderFromFile(File file, Charset charset) throws IOException {
        if (charset == null) {
            throw new NullPointerException("defaultCharset must be not null");
        }
        String strDetectCharset = UniversalDetector.detectCharset(file);
        if (strDetectCharset != null) {
            charset = Charset.forName(strDetectCharset);
        }
        return !charset.toString().contains("UTF") ? new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), charset) : new InputStreamReader(new UnicodeBOMInputStream(new BufferedInputStream(new FileInputStream(file))), charset);
    }

    public static Reader createReaderFromFile(File file) throws IOException {
        return createReaderFromFile(file, Charset.defaultCharset());
    }
}

package com.nostra13.dcloudimageloader.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public final class IoUtils {
    private static final int BUFFER_SIZE = 32768;

    private IoUtils() {
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1195];
        while (true) {
            int i = inputStream.read(bArr, 0, 1195);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    public static void closeSilently(Closeable closeable) throws IOException {
        try {
            closeable.close();
        } catch (Exception unused) {
        }
    }
}

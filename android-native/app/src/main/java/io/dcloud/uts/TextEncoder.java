package io.dcloud.uts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* compiled from: TextEncoder.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005R\u0014\u0010\u0004\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lio/dcloud/uts/TextEncoder;", "", "<init>", "()V", "encoding", "", "getEncoding", "()Ljava/lang/String;", "encode", "Lio/dcloud/uts/Uint8Array;", "input", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class TextEncoder {
    private final String encoding = "utf-8";

    public final String getEncoding() {
        return this.encoding;
    }

    public static /* synthetic */ Uint8Array encode$default(TextEncoder textEncoder, String str, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return textEncoder.encode(str);
    }

    public final Uint8Array encode(String input) {
        byte[] bytes;
        if (input != null) {
            bytes = input.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        } else {
            bytes = null;
        }
        Uint8Array uint8Array = new Uint8Array(Integer.valueOf(bytes != null ? bytes.length : 0));
        if (bytes != null) {
            int length = bytes.length;
            for (int i = 0; i < length; i++) {
                uint8Array.set(Integer.valueOf(i), Byte.valueOf(bytes[i]));
            }
        }
        return uint8Array;
    }
}

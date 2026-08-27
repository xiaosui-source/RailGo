package io.dcloud.uts;

import android.util.Base64;
import io.dcloud.uts.android.UTSURLDecoder;
import io.dcloud.uts.android.UTSURLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.WeakHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* compiled from: Object.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u000e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u0001\u001a\u000e\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001\".\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\u0001\u0012\f\u0012\n\u0018\u00010\fj\u0004\u0018\u0001`\r0\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"encodeURI", "", "input", "encodeURIComponent", "decodeURI", "decodeURIComponent", "btoa", "source", "atob", "encode", "globalError", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "getGlobalError", "()Ljava/util/Map;", "setGlobalError", "(Ljava/util/Map;)V", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ObjectKt {
    private static java.util.Map<String, Exception> globalError;

    public static final String encodeURI(String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return UTSURLEncoder.encode$default(new UTSURLEncoder(false, 1, null), input, null, 2, null);
    }

    public static final String encodeURIComponent(String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return UTSURLEncoder.encode$default(new UTSURLEncoder(true), input, null, 2, null);
    }

    public static final String decodeURI(String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return UTSURLDecoder.decode$default(new UTSURLDecoder(false, 1, null), input, null, 2, null);
    }

    public static final String decodeURIComponent(String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return UTSURLDecoder.decode$default(new UTSURLDecoder(false, 1, null), input, null, 2, null);
    }

    public static final String btoa(String source) {
        Intrinsics.checkNotNullParameter(source, "source");
        byte[] bytes = source.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        String strEncodeToString = Base64.encodeToString(bytes, 2);
        Intrinsics.checkNotNullExpressionValue(strEncodeToString, "encodeToString(...)");
        return strEncodeToString;
    }

    public static final String atob(String encode) {
        Intrinsics.checkNotNullParameter(encode, "encode");
        byte[] bArrDecode = Base64.decode(encode, 2);
        Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
        Charset charsetDefaultCharset = Charset.defaultCharset();
        Intrinsics.checkNotNullExpressionValue(charsetDefaultCharset, "defaultCharset(...)");
        return new String(bArrDecode, charsetDefaultCharset);
    }

    static {
        java.util.Map<String, Exception> mapSynchronizedMap = Collections.synchronizedMap(new WeakHashMap());
        Intrinsics.checkNotNullExpressionValue(mapSynchronizedMap, "synchronizedMap(...)");
        globalError = mapSynchronizedMap;
    }

    public static final java.util.Map<String, Exception> getGlobalError() {
        return globalError;
    }

    public static final void setGlobalError(java.util.Map<String, Exception> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        globalError = map;
    }
}

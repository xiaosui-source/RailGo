package io.dcloud.uts;

import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.util.AppConsoleLogUtil;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.uts.android.ClassLogWrapper;
import io.dcloud.uts.gson.JsonArray;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: console.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001#B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0016J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00012\u0006\u0010\t\u001a\u00020\u0001H\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0001H\u0016J-\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001H\u0002¢\u0006\u0002\u0010\u0014J%\u0010\u0015\u001a\u00020\u00102\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\u0016J%\u0010\u0017\u001a\u00020\u00102\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\u0016J%\u0010\u0018\u001a\u00020\u00102\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\u0016J%\u0010\u0019\u001a\u00020\u00102\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\u0016J%\u0010\u001a\u001a\u00020\u00102\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\u0016J#\u0010\u001b\u001a\u00020\u00102\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0016J#\u0010\u001c\u001a\u00020\u00102\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0016J/\u0010\u001d\u001a\u00020\u00052\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u001e\u001a\u00020\u001fH\u0002¢\u0006\u0002\u0010 J#\u0010!\u001a\u00020\u00052\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0013\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\"R\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lio/dcloud/uts/console;", "", "<init>", "()V", "wrapClassLogStr", "", "k", "Ljava/lang/Class;", "getBasicField", "anyInstance", "getObjectJSON", "Lio/dcloud/uts/gson/JsonObject;", "consoleThreadPool", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "asyncV2Log", "", "tag", "data", "", "(Ljava/lang/String;[Ljava/lang/Object;)V", "debug", "([Ljava/lang/Object;)V", "error", "info", "log", "warn", "errorV1", "errorV1WithStack", "getLog", "alwaysNeedStack", "", "([Ljava/lang/Object;Z)Ljava/lang/String;", "getLogV2", "([Ljava/lang/Object;)Ljava/lang/String;", "Companion", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class console {
    public static final console INSTANCE = new console();
    private static final ExecutorService consoleThreadPool = Executors.newFixedThreadPool(1);

    private console() {
    }

    /* compiled from: console.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J%\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\bJ%\u0010\t\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\bJ%\u0010\n\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\bJ%\u0010\u000b\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\bJ%\u0010\f\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0007\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\b¨\u0006\r"}, d2 = {"Lio/dcloud/uts/console$Companion;", "", "<init>", "()V", "debug", "", "data", "", "([Ljava/lang/Object;)V", "error", "info", "log", "warn", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public static final Companion INSTANCE = new Companion();

        private Companion() {
        }

        @Deprecated(message = "use console.debug() instead", replaceWith = @ReplaceWith(expression = "console.debug(*data)", imports = {}))
        @JvmStatic
        public static final void debug(java.lang.Object... data) {
            Intrinsics.checkNotNullParameter(data, "data");
            console.debug(Arrays.copyOf(data, data.length));
        }

        @Deprecated(message = "use console.error() instead", replaceWith = @ReplaceWith(expression = "console.error(*data)", imports = {}))
        @JvmStatic
        public static final void error(java.lang.Object... data) {
            Intrinsics.checkNotNullParameter(data, "data");
            console.error(Arrays.copyOf(data, data.length));
        }

        @Deprecated(message = "use console.info() instead", replaceWith = @ReplaceWith(expression = "console.info(*data)", imports = {}))
        @JvmStatic
        public static final void info(java.lang.Object... data) {
            Intrinsics.checkNotNullParameter(data, "data");
            console.info(Arrays.copyOf(data, data.length));
        }

        @Deprecated(message = "use console.log() instead", replaceWith = @ReplaceWith(expression = "console.log(*data)", imports = {}))
        @JvmStatic
        public static final void log(java.lang.Object... data) {
            Intrinsics.checkNotNullParameter(data, "data");
            console.log(Arrays.copyOf(data, data.length));
        }

        @Deprecated(message = "use console.warn() instead", replaceWith = @ReplaceWith(expression = "console.warn(*data)", imports = {}))
        @JvmStatic
        public static final void warn(java.lang.Object... data) {
            Intrinsics.checkNotNullParameter(data, "data");
            console.warn(Arrays.copyOf(data, data.length));
        }
    }

    public String wrapClassLogStr(Class<?> k) {
        String canonicalName;
        Intrinsics.checkNotNullParameter(k, "k");
        String canonicalName2 = k.getCanonicalName();
        if (canonicalName2 == null || canonicalName2.length() == 0) {
            canonicalName = "";
        } else {
            canonicalName = k.getCanonicalName();
            Intrinsics.checkNotNull(canonicalName);
            if (StringsKt.endsWith$default(canonicalName, ".Companion", false, 2, (java.lang.Object) null)) {
                canonicalName = StringKt.substring(canonicalName, (Number) 0, StringKt.lastIndexOf$default(canonicalName, ".Companion", null, 2, null));
            }
        }
        return "" + canonicalName + Operators.SPACE_STR;
    }

    private final java.lang.Object getBasicField(java.lang.Object anyInstance) {
        if ((anyInstance instanceof String) || (anyInstance instanceof Boolean) || (anyInstance instanceof Number)) {
            return anyInstance;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0116 A[Catch: Exception -> 0x01f9, TryCatch #0 {Exception -> 0x01f9, blocks: (B:7:0x003b, B:10:0x0046, B:12:0x004e, B:14:0x0052, B:42:0x0106, B:44:0x0116, B:15:0x005c, B:17:0x0060, B:18:0x006a, B:20:0x006e, B:22:0x007b, B:24:0x007f, B:25:0x0090, B:27:0x0094, B:29:0x0098, B:30:0x00a3, B:32:0x00a9, B:34:0x00af, B:35:0x00c4, B:37:0x00d4, B:38:0x00e0, B:40:0x00e6, B:41:0x00f7, B:45:0x017a, B:47:0x0197), top: B:59:0x003b }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01f9 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public io.dcloud.uts.gson.JsonObject getObjectJSON(java.lang.Object r19) throws java.lang.IllegalAccessException, java.lang.SecurityException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 574
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.uts.console.getObjectJSON(java.lang.Object):io.dcloud.uts.gson.JsonObject");
    }

    private final void asyncV2Log(final String tag, final java.lang.Object... data) {
        consoleThreadPool.submit(new Runnable() { // from class: io.dcloud.uts.console$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                console.asyncV2Log$lambda$3(data, tag);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void asyncV2Log$lambda$3(java.lang.Object[] objArr, String str) {
        AppConsoleLogUtil.DCLog(INSTANCE.getLogV2(Arrays.copyOf(objArr, objArr.length)), str);
    }

    @JvmStatic
    public static final void debug(java.lang.Object... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        INSTANCE.asyncV2Log("DEBUG", Arrays.copyOf(data, data.length));
    }

    @JvmStatic
    public static final void error(java.lang.Object... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        INSTANCE.asyncV2Log("ERROR", Arrays.copyOf(data, data.length));
    }

    @JvmStatic
    public static final void info(java.lang.Object... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        INSTANCE.asyncV2Log("INFO", Arrays.copyOf(data, data.length));
    }

    @JvmStatic
    public static final void log(java.lang.Object... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        INSTANCE.asyncV2Log("LOG", Arrays.copyOf(data, data.length));
    }

    @JvmStatic
    public static final void warn(java.lang.Object... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        INSTANCE.asyncV2Log("WARN", Arrays.copyOf(data, data.length));
    }

    public final void errorV1(java.lang.Object... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        AppConsoleLogUtil.DCLog(getLog$default(this, Arrays.copyOf(data, data.length), false, 2, null), "ERROR");
    }

    public final void errorV1WithStack(java.lang.Object... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        AppConsoleLogUtil.DCLog(getLog(Arrays.copyOf(data, data.length), true), "ERROR");
    }

    static /* synthetic */ String getLog$default(console consoleVar, java.lang.Object[] objArr, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return consoleVar.getLog(objArr, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.lang.String getLog(java.lang.Object[] r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 1008
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.uts.console.getLog(java.lang.Object[], boolean):java.lang.String");
    }

    public final String getLogV2(java.lang.Object... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (BaseInfo.SyncDebug) {
            if (data.length == 0) {
                return "---BEGIN:CONSOLE------END:CONSOLE---";
            }
            if (data.length == 1) {
                String string = ClassLogWrapper.wrapClass$default(ClassLogWrapper.INSTANCE, data[0], null, new HashSet(), null, 8, null).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return "---BEGIN:CONSOLE---" + string + "---END:CONSOLE---";
            }
            JsonArray jsonArray = new JsonArray();
            for (java.lang.Object obj : data) {
                jsonArray.add(ClassLogWrapper.wrapClass$default(ClassLogWrapper.INSTANCE, obj, null, new HashSet(), null, 8, null));
            }
            String string2 = jsonArray.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            return "---BEGIN:CONSOLE---" + string2 + "---END:CONSOLE---";
        }
        return "";
    }
}

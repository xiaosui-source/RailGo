package io.dcloud.feature.utsplugin;

import io.dcloud.uts.UTSCallback;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: ParamConvertHelper.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001d\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"utsCallbackCache", "", "", "Lio/dcloud/uts/UTSCallback;", "getUtsCallbackCache", "()Ljava/util/Map;", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ParamConvertHelperKt {
    private static final Map<Integer, UTSCallback> utsCallbackCache = new LinkedHashMap();

    public static final Map<Integer, UTSCallback> getUtsCallbackCache() {
        return utsCallbackCache;
    }
}

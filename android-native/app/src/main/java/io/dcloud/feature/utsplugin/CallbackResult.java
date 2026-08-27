package io.dcloud.feature.utsplugin;

import com.alibaba.fastjson.JSONArray;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CallbackResult.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lio/dcloud/feature/utsplugin/CallbackResult;", "", "<init>", "()V", "type", "", "getType", "()Ljava/lang/String;", "id", "", "getId", "()I", "name", "getName", "param", "Lcom/alibaba/fastjson/JSONArray;", "getParam", "()Lcom/alibaba/fastjson/JSONArray;", "setParam", "(Lcom/alibaba/fastjson/JSONArray;)V", "keepAlive", "", "getKeepAlive", "()Z", "setKeepAlive", "(Z)V", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CallbackResult {
    private boolean keepAlive;
    private final String type = "params";
    private final int id = -1;
    private final String name = "complete";
    private JSONArray param = new JSONArray();

    public String getType() {
        return this.type;
    }

    public final int getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final JSONArray getParam() {
        return this.param;
    }

    public final void setParam(JSONArray jSONArray) {
        Intrinsics.checkNotNullParameter(jSONArray, "<set-?>");
        this.param = jSONArray;
    }

    public final boolean getKeepAlive() {
        return this.keepAlive;
    }

    public final void setKeepAlive(boolean z) {
        this.keepAlive = z;
    }
}

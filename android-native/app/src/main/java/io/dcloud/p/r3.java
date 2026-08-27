package io.dcloud.p;

import io.dcloud.common.DHInterface.IPdrModulesInfo;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class r3 implements IPdrModulesInfo {
    @Override // io.dcloud.common.DHInterface.IPdrModulesInfo
    public Map getPdrModuleMap() {
        HashMap map = new HashMap();
        map.put("commit", e0.class);
        return map;
    }
}

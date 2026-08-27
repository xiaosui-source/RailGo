package io.dcloud.sdk.core.v3.pv;

import android.app.Activity;
import io.dcloud.p.j1;
import io.dcloud.sdk.core.v3.fd.DCFeedAOLLoader;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCPreVideoLoader extends DCFeedAOLLoader {
    public DCPreVideoLoader(Activity activity) {
        super(activity);
    }

    @Override // io.dcloud.sdk.core.v3.fd.DCFeedAOLLoader
    public void initLoader() {
        if (this.b == null) {
            this.b = new j1(getContext(), 5);
        }
    }
}

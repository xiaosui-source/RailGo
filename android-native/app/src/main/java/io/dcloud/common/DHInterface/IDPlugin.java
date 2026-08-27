package io.dcloud.common.DHInterface;

import android.app.Activity;
import android.content.Context;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IDPlugin extends IFeature, IBoot {
    Activity getDPluginActivity();

    Context getDPluginContext();

    void initDPlugin(Context context, Activity activity);
}

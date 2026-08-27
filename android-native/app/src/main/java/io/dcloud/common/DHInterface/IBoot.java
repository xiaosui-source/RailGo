package io.dcloud.common.DHInterface;

import android.content.Context;
import android.os.Bundle;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IBoot extends ISysEventListener {
    void onPause();

    void onRestart(Context context);

    void onResume();

    void onStart(Context context, Bundle bundle, String[] strArr);

    void onStop();
}

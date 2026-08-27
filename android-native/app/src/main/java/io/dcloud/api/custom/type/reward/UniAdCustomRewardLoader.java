package io.dcloud.api.custom.type.reward;

import android.app.Activity;
import io.dcloud.api.custom.type.UniAdCustomBaseLoader;
import io.dcloud.p.v1;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class UniAdCustomRewardLoader extends UniAdCustomBaseLoader {
    public void onReward() {
        v1 v1Var = this.a;
        if (v1Var instanceof v1.a) {
            ((v1.a) v1Var).g();
        }
    }

    public abstract void show(Activity activity);

    @Override // io.dcloud.api.custom.type.UniAdCustomBaseLoader
    public final void show(Object obj) {
        show((Activity) obj);
    }
}

package io.dcloud.p;

import android.app.Activity;
import io.dcloud.sdk.core.module.DCBaseAOL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class b1 extends j1 {
    public b1(Activity activity) {
        super(activity, 10);
    }

    @Override // io.dcloud.p.j1
    protected List f(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new a1((DCBaseAOL) it.next(), a()));
            }
        }
        return arrayList;
    }
}

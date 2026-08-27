package io.dcloud.sdk.core.v3.fd;

import android.app.Activity;
import io.dcloud.p.i1;
import io.dcloud.p.j1;
import io.dcloud.p.y1;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.v3.base.DCBaseAOL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCFeedAOLLoader extends DCBaseAOL {
    protected j1 b;

    public DCFeedAOLLoader(Activity activity) {
        super(activity);
    }

    public void initLoader() {
        if (this.b == null) {
            this.b = new j1(getContext(), 4);
        }
    }

    public void load(DCloudAOLSlot dCloudAOLSlot, final DCFeedAOLLoadListener dCFeedAOLLoadListener) {
        if (getContext() != null && dCloudAOLSlot != null) {
            initLoader();
            this.b.a(dCloudAOLSlot, new y1() { // from class: io.dcloud.sdk.core.v3.fd.DCFeedAOLLoader.1
                @Override // io.dcloud.p.y1
                public void onError(int i, String str, JSONArray jSONArray) {
                    DCFeedAOLLoadListener dCFeedAOLLoadListener2 = dCFeedAOLLoadListener;
                    if (dCFeedAOLLoadListener2 != null) {
                        dCFeedAOLLoadListener2.onError(i, str, jSONArray);
                    }
                }

                @Override // io.dcloud.p.y1
                public void onLoaded(List<i1> list) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<i1> it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new DCFeedAOL(it.next()));
                    }
                    DCFeedAOLLoadListener dCFeedAOLLoadListener2 = dCFeedAOLLoadListener;
                    if (dCFeedAOLLoadListener2 != null) {
                        dCFeedAOLLoadListener2.onFeedAOLLoad(arrayList);
                    }
                }
            });
        } else if (dCFeedAOLLoadListener != null) {
            dCFeedAOLLoadListener.onError(-5014, AOLErrorUtil.getErrorMsg(-5014), null);
        }
    }
}

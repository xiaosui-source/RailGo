package io.dcloud.sdk.core.v3.dw;

import android.app.Activity;
import io.dcloud.p.b1;
import io.dcloud.p.i1;
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
public class DCDrawAOLLoader extends DCBaseAOL {
    protected b1 b;

    public DCDrawAOLLoader(Activity activity) {
        super(activity);
        this.b = new b1(activity);
    }

    public void load(DCloudAOLSlot dCloudAOLSlot, final DCDrawAOLLoadListener dCDrawAOLLoadListener) {
        if (getContext() != null && dCloudAOLSlot != null) {
            this.b.a(dCloudAOLSlot, new y1() { // from class: io.dcloud.sdk.core.v3.dw.DCDrawAOLLoader.1
                @Override // io.dcloud.p.y1
                public void onError(int i, String str, JSONArray jSONArray) {
                    DCDrawAOLLoadListener dCDrawAOLLoadListener2 = dCDrawAOLLoadListener;
                    if (dCDrawAOLLoadListener2 != null) {
                        dCDrawAOLLoadListener2.onError(i, str, jSONArray);
                    }
                }

                @Override // io.dcloud.p.y1
                public void onLoaded(List<i1> list) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<i1> it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new DCDrawAOL(it.next()));
                    }
                    DCDrawAOLLoadListener dCDrawAOLLoadListener2 = dCDrawAOLLoadListener;
                    if (dCDrawAOLLoadListener2 != null) {
                        dCDrawAOLLoadListener2.onDrawAOLLoad(arrayList);
                    }
                }
            });
        } else if (dCDrawAOLLoadListener != null) {
            dCDrawAOLLoadListener.onError(-5014, AOLErrorUtil.getErrorMsg(-5014), null);
        }
    }
}

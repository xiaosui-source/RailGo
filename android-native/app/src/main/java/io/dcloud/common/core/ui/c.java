package io.dcloud.common.core.ui;

import android.content.Context;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.p.s4;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
class c extends b {
    TabBarWebview C;
    b D;

    c(Context context, l lVar, IApp iApp, a aVar, int i, JSONObject jSONObject) {
        super(context, lVar, iApp, aVar, i, null);
        TabBarWebview tabBarWebview = new TabBarWebview(context, iApp, lVar, this, jSONObject);
        this.C = tabBarWebview;
        this.l = tabBarWebview;
        tabBarWebview.setFrameId("tab");
    }

    public void c(b bVar) {
        if (this.C.isInsertLauch()) {
            this.C.append(bVar);
            d(bVar);
        }
    }

    public boolean checkPagePathIsTab(String str) {
        TabBarWebview tabBarWebview = this.C;
        if (tabBarWebview != null) {
            return tabBarWebview.checkPagePathIsTab(str);
        }
        return false;
    }

    public void d(b bVar) {
        this.D = bVar;
        TabBarWebview tabBarWebview = this.C;
        if (tabBarWebview != null) {
            tabBarWebview.tabItemActive(bVar);
        }
    }

    @Override // io.dcloud.common.core.ui.b, io.dcloud.common.adapter.ui.AdaFrameView, io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public void dispose() {
        super.dispose();
        this.D = null;
        this.C = null;
    }

    @Override // io.dcloud.common.core.ui.b, io.dcloud.common.adapter.ui.AdaFrameView
    protected void initMainView(Context context, int i, Object obj) {
        setMainView(new s4(context));
    }

    public void removeFrameView(b bVar) {
        TabBarWebview tabBarWebview = this.C;
        if (tabBarWebview != null) {
            tabBarWebview.removeFrameView(bVar);
        }
    }

    public b u() {
        return this.D;
    }
}

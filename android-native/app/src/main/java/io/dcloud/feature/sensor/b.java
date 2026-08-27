package io.dcloud.feature.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import com.taobao.weex.common.Constants;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.PdrUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class b implements IEventCallback {
    private HashMap a = new HashMap();
    Context b;
    private SensorManager c;
    private Sensor d;

    public b(Context context) {
        this.b = context;
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        this.c = sensorManager;
        this.d = sensorManager.getDefaultSensor(1);
    }

    @Override // io.dcloud.common.DHInterface.IEventCallback
    public Object onCallBack(String str, Object obj) {
        if ((!PdrUtil.isEquals(str, AbsoluteConst.EVENTS_WINDOW_CLOSE) && !PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE)) || !(obj instanceof IWebview)) {
            return null;
        }
        IWebview iWebview = (IWebview) obj;
        a(iWebview);
        ((AdaFrameView) iWebview.obtainFrameView()).removeFrameViewListener(this);
        return null;
    }

    public String a(IWebview iWebview, String str, String[] strArr) {
        if (!str.equals("start")) {
            if (str.equals(Constants.Value.STOP)) {
                a(iWebview);
            }
            return "";
        }
        AdaFrameView adaFrameView = (AdaFrameView) iWebview.obtainFrameView();
        Logger.d("AccelerometerManager.execute start listen frameView=" + adaFrameView);
        adaFrameView.addFrameViewListener(this);
        a(iWebview, strArr[0], strArr[1]);
        return "";
    }

    private void a(IWebview iWebview, String str, String str2) {
        a aVar = (a) this.a.get(iWebview);
        if (aVar == null) {
            aVar = new a(this, iWebview, str);
            this.a.put(iWebview, aVar);
        }
        int i = PdrUtil.parseInt(str2, 500);
        this.c.registerListener(aVar, this.d, (i >= 0 ? i : 500) * 1000);
    }

    void a(IWebview iWebview) {
        a aVar = (a) this.a.remove(iWebview);
        if (aVar != null) {
            Logger.d("AccelerometerManager stop pWebViewImpl=" + iWebview);
            this.c.unregisterListener(aVar);
        }
    }

    void a(String str) {
        Collection collectionValues = this.a.values();
        if (!collectionValues.isEmpty()) {
            Iterator it = collectionValues.iterator();
            while (it.hasNext()) {
                this.c.unregisterListener((a) it.next());
            }
        }
        this.a.clear();
    }
}

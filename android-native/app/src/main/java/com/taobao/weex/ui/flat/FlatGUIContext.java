package com.taobao.weex.ui.flat;

import android.text.TextUtils;
import android.view.View;
import androidx.collection.ArrayMap;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.Destroyable;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXStyle;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.flat.widget.AndroidViewWidget;
import com.taobao.weex.ui.flat.widget.Widget;
import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class FlatGUIContext implements Destroyable {
    private Map<WXComponent, WidgetContainer> mWidgetRegistry = new ArrayMap();
    private Map<WXComponent, AndroidViewWidget> mViewWidgetRegistry = new ArrayMap();
    private Map<Widget, WXComponent> widgetToComponent = new ArrayMap();

    private boolean checkComponent(WXComponent wXComponent) {
        if (wXComponent == null) {
            return false;
        }
        WXStyle styles = wXComponent.getStyles();
        WXAttr attrs = wXComponent.getAttrs();
        return styles.containsKey("opacity") || styles.containsKey("transform") || styles.containsKey(Constants.Name.VISIBILITY) || attrs.containsKey(Constants.Name.ELEVATION) || attrs.containsKey(Constants.Name.ARIA_HIDDEN) || attrs.containsKey(Constants.Name.ARIA_LABEL) || attrs.containsKey(WXComponent.PROP_FIXED_SIZE) || attrs.containsKey(Constants.Name.DISABLED) || styles.isFixed() || styles.isSticky() || !styles.getPesudoStyles().isEmpty() || wXComponent.getEvents().size() > 0;
    }

    private WXComponent getComponent(Widget widget) {
        return this.widgetToComponent.get(widget);
    }

    @Override // com.taobao.weex.common.Destroyable
    public void destroy() {
        this.widgetToComponent.clear();
        Iterator<Map.Entry<WXComponent, AndroidViewWidget>> it = this.mViewWidgetRegistry.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().destroy();
        }
        this.mViewWidgetRegistry.clear();
        Iterator<Map.Entry<WXComponent, WidgetContainer>> it2 = this.mWidgetRegistry.entrySet().iterator();
        while (it2.hasNext()) {
            it2.next().getValue().unmountFlatGUI();
        }
        this.mWidgetRegistry.clear();
    }

    public AndroidViewWidget getAndroidViewWidget(WXComponent wXComponent) {
        return this.mViewWidgetRegistry.get(wXComponent);
    }

    public WidgetContainer getFlatComponentAncestor(WXComponent wXComponent) {
        return this.mWidgetRegistry.get(wXComponent);
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [android.view.View] */
    public View getWidgetContainerView(Widget widget) {
        WidgetContainer flatComponentAncestor;
        WXComponent component = getComponent(widget);
        if (component == null || (flatComponentAncestor = getFlatComponentAncestor(component)) == null) {
            return null;
        }
        return flatComponentAncestor.getHostView();
    }

    public boolean isFlatUIEnabled(WXComponent wXComponent) {
        return false;
    }

    public boolean promoteToView(WXComponent wXComponent, boolean z, Class<? extends WXComponent<?>> cls) {
        if (isFlatUIEnabled(wXComponent) && cls.equals(wXComponent.getClass()) && !TextUtils.equals(wXComponent.getRef(), WXComponent.ROOT)) {
            return (z && getFlatComponentAncestor(wXComponent) == null) || checkComponent(wXComponent);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void register(WXComponent wXComponent, WidgetContainer widgetContainer) {
        if (!(widgetContainer instanceof FlatComponent) || ((FlatComponent) widgetContainer).promoteToView(true)) {
            this.mWidgetRegistry.put(wXComponent, widgetContainer);
        }
    }

    public void register(WXComponent wXComponent, AndroidViewWidget androidViewWidget) {
        this.mViewWidgetRegistry.put(wXComponent, androidViewWidget);
    }

    public void register(Widget widget, WXComponent wXComponent) {
        this.widgetToComponent.put(widget, wXComponent);
    }
}

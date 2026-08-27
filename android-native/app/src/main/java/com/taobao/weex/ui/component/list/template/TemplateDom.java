package com.taobao.weex.ui.component.list.template;

import android.view.View;
import androidx.core.view.ViewCompat;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TemplateDom {
    public static final String ATTACH_CELL_SLOT = "_attach_slot";
    public static final String ATTRS_KEY_REF = "ref";
    public static final String DETACH_CELL_SLOT = "_detach_slot";
    public static final String KEY_ATTRS = "attrs";
    public static final String KEY_RESET_ANIMATION = "resetAnimation";
    public static final String KEY_TYPE = "type";
    public static final String KEY_VIRTUAL_DOM_REF = "ref";
    public static final char SEPARATOR = '@';
    public static final String VIRTUAL_DOM_IDENTIFY = "[[VirtualElement]]";

    public static Map<String, Object> findAllComponentRefs(String str, int i, WXComponent wXComponent) {
        HashMap map = new HashMap();
        findAllComponentRefs(str, i, wXComponent, map);
        HashMap map2 = new HashMap();
        map2.put("refs", map);
        map2.put("position", Integer.valueOf(i));
        map2.put("listRef", str);
        return map2;
    }

    private static WXComponent findChildByAttrsRef(WXComponent wXComponent, String str) {
        if (wXComponent.getAttrs() != null && str.equals(wXComponent.getAttrs().get("ref"))) {
            return wXComponent;
        }
        if (!(wXComponent instanceof WXVContainer)) {
            return null;
        }
        WXVContainer wXVContainer = (WXVContainer) wXComponent;
        for (int i = 0; i < wXVContainer.getChildCount(); i++) {
            WXComponent wXComponentFindChildByAttrsRef = findChildByAttrsRef(wXVContainer.getChild(i), str);
            if (wXComponentFindChildByAttrsRef != null) {
                return wXComponentFindChildByAttrsRef;
            }
        }
        return null;
    }

    public static final WXComponent findComponentByViewTreeKey(WXComponent wXComponent, String str) {
        if (wXComponent.getViewTreeKey().equals(str)) {
            return wXComponent;
        }
        if (!(wXComponent instanceof WXVContainer)) {
            return null;
        }
        WXVContainer wXVContainer = (WXVContainer) wXComponent;
        for (int i = 0; i < wXVContainer.getChildCount(); i++) {
            WXComponent child = wXVContainer.getChild(i);
            if (findComponentByViewTreeKey(child, str) != null) {
                return child;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static WXComponent findVirtualComponentByVRef(String str, String str2) {
        String[] strArrSplit;
        WXComponent wXComponent;
        try {
            strArrSplit = str2.split("@");
            wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(str, strArrSplit[0]);
        } catch (Exception unused) {
        }
        if (!(wXComponent instanceof WXRecyclerTemplateList)) {
            return null;
        }
        WXRecyclerTemplateList wXRecyclerTemplateList = (WXRecyclerTemplateList) wXComponent;
        if (wXRecyclerTemplateList.getHostView() != 0 && ((BounceRecyclerView) wXRecyclerTemplateList.getHostView()).getInnerView() != null) {
            TemplateViewHolder templateViewHolder = (TemplateViewHolder) ((BounceRecyclerView) wXRecyclerTemplateList.getHostView()).getInnerView().findViewHolderForAdapterPosition(Integer.parseInt(strArrSplit[1]));
            if (templateViewHolder == null) {
                return null;
            }
            return findComponentByViewTreeKey(templateViewHolder.getTemplate(), strArrSplit[2]);
        }
        return null;
    }

    public static String genKeyVirtualDomRef(String str, int i, String str2) {
        return str + SEPARATOR + i + SEPARATOR + str2;
    }

    public static boolean isVirtualDomRef(String str) {
        return str != null && str.indexOf(64) > 0;
    }

    public static void resetAnimaiton(View view) {
        if (view == null) {
            return;
        }
        if (ViewCompat.getTranslationX(view) != 0.0f) {
            ViewCompat.setTranslationX(view, 0.0f);
        }
        if (ViewCompat.getTranslationY(view) != 0.0f) {
            ViewCompat.setTranslationY(view, 0.0f);
        }
        if (ViewCompat.getTranslationZ(view) != 0.0f) {
            ViewCompat.setTranslationZ(view, 0.0f);
        }
        if (ViewCompat.getScaleX(view) != 1.0f) {
            ViewCompat.setScaleX(view, 1.0f);
        }
        if (ViewCompat.getScaleY(view) != 1.0f) {
            ViewCompat.setScaleY(view, 1.0f);
        }
        if (ViewCompat.getRotationX(view) != 0.0f) {
            ViewCompat.setRotationX(view, 0.0f);
        }
        if (ViewCompat.getRotationY(view) != 0.0f) {
            ViewCompat.setRotationY(view, 0.0f);
        }
        if (ViewCompat.getElevation(view) != 0.0f) {
            ViewCompat.setElevation(view, 0.0f);
        }
    }

    public static Map toMap(String str, int i, WXComponent wXComponent) {
        HashMap map = new HashMap();
        map.put(KEY_ATTRS, wXComponent.getAttrs());
        map.put("type", wXComponent.getComponentType());
        map.put("ref", genKeyVirtualDomRef(str, i, wXComponent.getViewTreeKey()));
        map.put(VIRTUAL_DOM_IDENTIFY, Boolean.TRUE);
        return map;
    }

    private static void findAllComponentRefs(String str, int i, WXComponent wXComponent, Map<String, Object> map) {
        if (wXComponent.isWaste()) {
            return;
        }
        if (wXComponent instanceof WXVContainer) {
            WXVContainer wXVContainer = (WXVContainer) wXComponent;
            for (int i2 = 0; i2 < wXVContainer.getChildCount(); i2++) {
                findAllComponentRefs(str, i, wXVContainer.getChild(i2), map);
            }
        }
        WXAttr attrs = wXComponent.getAttrs();
        if (attrs == null || attrs.get("ref") != null) {
            String string = attrs.get("ref").toString();
            List arrayList = (List) map.get(string);
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(string, arrayList);
            }
            arrayList.add(toMap(str, i, wXComponent));
        }
    }
}

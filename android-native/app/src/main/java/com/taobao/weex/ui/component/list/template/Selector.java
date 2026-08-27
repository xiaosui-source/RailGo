package com.taobao.weex.ui.component.list.template;

import android.text.TextUtils;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.list.WXCell;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class Selector {
    public static void closest(WXComponent wXComponent, String str, List<WXComponent> list) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String[] strArrSplit = str.replaceAll("\\[|]", "").split("=");
        if (strArrSplit.length <= 0) {
            return;
        }
        closestByAttrs(wXComponent, strArrSplit[0], strArrSplit.length > 1 ? strArrSplit[1].trim() : null, list);
    }

    private static void closestByAttrs(WXComponent wXComponent, String str, String str2, List<WXComponent> list) {
        if (matchAttrs(wXComponent, str, str2)) {
            list.add(wXComponent);
        }
        if ((wXComponent instanceof WXCell) || (wXComponent instanceof WXRecyclerTemplateList)) {
            return;
        }
        queryElementAllByAttrs(wXComponent.getParent(), str, str2, list);
    }

    private static boolean matchAttrs(WXComponent wXComponent, String str, String str2) {
        if (wXComponent.isWaste() || !wXComponent.getAttrs().containsKey(str)) {
            return false;
        }
        if (TextUtils.isEmpty(str2)) {
            return true;
        }
        Object obj = wXComponent.getAttrs().get(str);
        if (obj == null) {
            return false;
        }
        return str2.equals(obj.toString());
    }

    public static void queryElementAll(WXComponent wXComponent, String str, List<WXComponent> list) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String[] strArrSplit = str.replaceAll("\\[|]", "").split("=");
        if (strArrSplit.length <= 0) {
            return;
        }
        String str2 = strArrSplit[0];
        String strTrim = strArrSplit.length > 1 ? strArrSplit[1].trim() : null;
        if (wXComponent instanceof WXVContainer) {
            WXVContainer wXVContainer = (WXVContainer) wXComponent;
            for (int i = 0; i < wXVContainer.getChildCount(); i++) {
                queryElementAllByAttrs(wXVContainer.getChild(i), str2, strTrim, list);
            }
        }
    }

    private static void queryElementAllByAttrs(WXComponent wXComponent, String str, String str2, List<WXComponent> list) {
        if (matchAttrs(wXComponent, str, str2)) {
            list.add(wXComponent);
        }
        if (wXComponent instanceof WXVContainer) {
            WXVContainer wXVContainer = (WXVContainer) wXComponent;
            for (int i = 0; i < wXVContainer.getChildCount(); i++) {
                queryElementAllByAttrs(wXVContainer.getChild(i), str, str2, list);
            }
        }
    }
}

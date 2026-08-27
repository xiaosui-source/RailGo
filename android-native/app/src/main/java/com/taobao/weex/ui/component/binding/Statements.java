package com.taobao.weex.ui.component.binding;

import android.os.Looper;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.dom.WXStyle;
import com.taobao.weex.dom.binding.ELUtils;
import com.taobao.weex.el.parse.ArrayStack;
import com.taobao.weex.el.parse.Token;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.action.GraphicPosition;
import com.taobao.weex.ui.action.GraphicSize;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentFactory;
import com.taobao.weex.ui.component.WXImage;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.list.WXCell;
import com.taobao.weex.ui.component.list.template.CellDataManager;
import com.taobao.weex.ui.component.list.template.CellRenderContext;
import com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList;
import com.taobao.weex.ui.component.list.template.jni.NativeRenderObjectUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class Statements {
    private static final ThreadLocal<Map<String, Object>> dynamicLocal = new ThreadLocal<>();

    public static WXComponent copyComponentTree(WXComponent wXComponent) {
        return copyComponentTree(wXComponent, wXComponent.getParent());
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0150  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void doBindingAttrsEventAndRenderChildNode(com.taobao.weex.ui.component.WXComponent r21, com.taobao.weex.ui.component.list.template.CellRenderContext r22, java.util.List<com.taobao.weex.ui.component.WXComponent> r23) throws java.lang.IllegalAccessException, java.lang.InterruptedException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 551
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.component.binding.Statements.doBindingAttrsEventAndRenderChildNode(com.taobao.weex.ui.component.WXComponent, com.taobao.weex.ui.component.list.template.CellRenderContext, java.util.List):void");
    }

    public static final void doInitCompontent(List<WXComponent> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (WXComponent wXComponent : list) {
            if (wXComponent.getParent() == null) {
                throw new IllegalArgumentException("render node parent cann't find");
            }
            WXVContainer parent = wXComponent.getParent();
            int iIndexOf = parent.indexOf(wXComponent);
            if (iIndexOf < 0) {
                throw new IllegalArgumentException("render node cann't find");
            }
            parent.createChildViewAt(iIndexOf);
            wXComponent.applyLayoutAndEvent(wXComponent);
            wXComponent.bindData(wXComponent);
        }
    }

    public static final List<WXComponent> doRender(WXComponent wXComponent, CellRenderContext cellRenderContext) {
        ArrayList arrayList = new ArrayList(4);
        try {
            doRenderComponent(wXComponent, cellRenderContext, arrayList);
            return arrayList;
        } catch (Exception e) {
            WXLogUtils.e("WeexStatementRender", e);
            return arrayList;
        }
    }

    private static void doRenderBindingAttrsAndEvent(WXComponent wXComponent, CellRenderContext cellRenderContext) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayStack arrayStack = cellRenderContext.stack;
        wXComponent.setWaste(false);
        WXAttr attrs = wXComponent.getAttrs();
        if (attrs != null && attrs.getBindingAttrs() != null && attrs.getBindingAttrs().size() > 0) {
            Map<String, Object> mapRenderBindingAttrs = renderBindingAttrs(wXComponent.getAttrs().getBindingAttrs(), arrayStack);
            Iterator<Map.Entry<String, Object>> it = mapRenderBindingAttrs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> next = it.next();
                String key = next.getKey();
                Object value = next.getValue();
                Object obj = attrs.get(key);
                if (value == null) {
                    if (obj == null) {
                        it.remove();
                    }
                } else if (value.equals(obj)) {
                    it.remove();
                }
            }
            if (mapRenderBindingAttrs.size() > 0) {
                if (mapRenderBindingAttrs.size() == 1 && mapRenderBindingAttrs.get("src") != null && (wXComponent instanceof WXImage)) {
                    wXComponent.getAttrs().put("src", mapRenderBindingAttrs.get("src"));
                } else {
                    wXComponent.nativeUpdateAttrs(mapRenderBindingAttrs);
                }
                if (isMainThread()) {
                    wXComponent.updateProperties(mapRenderBindingAttrs);
                }
                mapRenderBindingAttrs.clear();
            }
        }
        WXStyle styles = wXComponent.getStyles();
        if (styles != null && styles.getBindingStyle() != null) {
            Map<String, Object> mapRenderBindingAttrs2 = renderBindingAttrs(styles.getBindingStyle(), arrayStack);
            Iterator<Map.Entry<String, Object>> it2 = mapRenderBindingAttrs2.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<String, Object> next2 = it2.next();
                String key2 = next2.getKey();
                Object value2 = next2.getValue();
                Object obj2 = styles.get(key2);
                if (value2 == null) {
                    if (obj2 == null) {
                        it2.remove();
                    }
                } else if (value2.equals(obj2)) {
                    it2.remove();
                }
            }
            if (mapRenderBindingAttrs2.size() > 0) {
                wXComponent.updateNativeStyles(mapRenderBindingAttrs2);
                if (isMainThread()) {
                    wXComponent.updateProperties(mapRenderBindingAttrs2);
                }
            }
        }
        WXEvent events = wXComponent.getEvents();
        if (events == null || events.getEventBindingArgs() == null) {
            return;
        }
        for (Map.Entry entry : events.getEventBindingArgs().entrySet()) {
            List<Object> bindingEventArgs = getBindingEventArgs(arrayStack, entry.getValue());
            if (bindingEventArgs != null) {
                events.putEventBindingArgsValue((String) entry.getKey(), bindingEventArgs);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x01ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final int doRenderComponent(com.taobao.weex.ui.component.WXComponent r25, com.taobao.weex.ui.component.list.template.CellRenderContext r26, java.util.List<com.taobao.weex.ui.component.WXComponent> r27) throws java.lang.IllegalAccessException, java.lang.InterruptedException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 497
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.component.binding.Statements.doRenderComponent(com.taobao.weex.ui.component.WXComponent, com.taobao.weex.ui.component.list.template.CellRenderContext, java.util.List):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.Object> getBindingEventArgs(com.taobao.weex.el.parse.ArrayStack r6, java.lang.Object r7) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 4
            r0.<init>(r1)
            boolean r1 = r7 instanceof com.alibaba.fastjson.JSONArray
            java.lang.String r2 = "@binding"
            if (r1 == 0) goto L3d
            com.alibaba.fastjson.JSONArray r7 = (com.alibaba.fastjson.JSONArray) r7
            r1 = 0
        Lf:
            int r3 = r7.size()
            if (r1 >= r3) goto L3c
            java.lang.Object r3 = r7.get(r1)
            boolean r4 = r3 instanceof com.alibaba.fastjson.JSONObject
            if (r4 == 0) goto L36
            r4 = r3
            com.alibaba.fastjson.JSONObject r4 = (com.alibaba.fastjson.JSONObject) r4
            java.lang.Object r5 = r4.get(r2)
            boolean r5 = r5 instanceof com.taobao.weex.el.parse.Token
            if (r5 == 0) goto L36
            java.lang.Object r3 = r4.get(r2)
            com.taobao.weex.el.parse.Token r3 = (com.taobao.weex.el.parse.Token) r3
            java.lang.Object r3 = r3.execute(r6)
            r0.add(r3)
            goto L39
        L36:
            r0.add(r3)
        L39:
            int r1 = r1 + 1
            goto Lf
        L3c:
            return r0
        L3d:
            boolean r1 = r7 instanceof com.alibaba.fastjson.JSONObject
            if (r1 == 0) goto L62
            r1 = r7
            com.alibaba.fastjson.JSONObject r1 = (com.alibaba.fastjson.JSONObject) r1
            java.lang.Object r3 = r1.get(r2)
            boolean r3 = r3 instanceof com.taobao.weex.el.parse.Token
            if (r3 == 0) goto L5a
            java.lang.Object r7 = r1.get(r2)
            com.taobao.weex.el.parse.Token r7 = (com.taobao.weex.el.parse.Token) r7
            java.lang.Object r6 = r7.execute(r6)
            r0.add(r6)
            return r0
        L5a:
            java.lang.String r6 = r7.toString()
            r0.add(r6)
            return r0
        L62:
            java.lang.String r6 = r7.toString()
            r0.add(r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.component.binding.Statements.getBindingEventArgs(com.taobao.weex.el.parse.ArrayStack, java.lang.Object):java.util.List");
    }

    public static String getComponentId(WXComponent wXComponent) {
        if ((wXComponent instanceof WXCell) || wXComponent == null) {
            return null;
        }
        WXAttr attrs = wXComponent.getAttrs();
        if (attrs.get(ELUtils.IS_COMPONENT_ROOT) == null || !WXUtils.getBoolean(attrs.get(ELUtils.IS_COMPONENT_ROOT), Boolean.FALSE).booleanValue() || attrs.get(ELUtils.COMPONENT_PROPS) == null || !(attrs.get(ELUtils.COMPONENT_PROPS) instanceof JSONObject)) {
            return getComponentId(wXComponent.getParent());
        }
        Object obj = attrs.get(CellDataManager.VIRTUAL_COMPONENT_ID);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static void initLazyComponent(WXComponent wXComponent, WXVContainer wXVContainer) {
        if (wXComponent.isLazy() || wXComponent.getHostView() == null) {
            wXComponent.lazy(false);
            if (wXVContainer != null) {
                wXVContainer.createChildViewAt(wXVContainer.indexOf(wXComponent));
            } else {
                wXComponent.createView();
            }
            wXComponent.applyLayoutAndEvent(wXComponent);
            wXComponent.bindData(wXComponent);
        }
    }

    private static boolean isCreateFromNodeStatement(WXComponent wXComponent, WXComponent wXComponent2) {
        return wXComponent.getRef() != null && wXComponent.getRef().equals(wXComponent2.getRef());
    }

    private static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static void parseStatementsToken(WXComponent wXComponent) {
        if (wXComponent.getBasicComponentData().isRenderPtrEmpty()) {
            wXComponent.getBasicComponentData().setRenderObjectPr(wXComponent.getRenderObjectPtr());
        }
        if (wXComponent.getBasicComponentData() != null) {
            BasicComponentData basicComponentData = wXComponent.getBasicComponentData();
            basicComponentData.getAttrs().parseStatements();
            basicComponentData.getStyles().parseStatements();
            basicComponentData.getEvents().parseStatements();
        }
        if (wXComponent instanceof WXVContainer) {
            WXVContainer wXVContainer = (WXVContainer) wXComponent;
            int childCount = wXVContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                parseStatementsToken(wXVContainer.getChild(i));
            }
        }
    }

    public static Map<String, Object> renderBindingAttrs(ArrayMap arrayMap, ArrayStack arrayStack) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Set<Map.Entry> setEntrySet = arrayMap.entrySet();
        ThreadLocal<Map<String, Object>> threadLocal = dynamicLocal;
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        if (map.size() > 0) {
            map.clear();
        }
        for (Map.Entry entry : setEntrySet) {
            Object value = entry.getValue();
            String str = (String) entry.getKey();
            if (value instanceof JSONObject) {
                JSONObject jSONObject = (JSONObject) value;
                if (jSONObject.get(ELUtils.BINDING) instanceof Token) {
                    map.put(str, ((Token) jSONObject.get(ELUtils.BINDING)).execute(arrayStack));
                }
            }
            if (value instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) value;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < jSONArray.size(); i++) {
                    Object obj = jSONArray.get(i);
                    if (obj instanceof CharSequence) {
                        sb.append(obj);
                    } else if (obj instanceof JSONObject) {
                        JSONObject jSONObject2 = (JSONObject) obj;
                        if (jSONObject2.get(ELUtils.BINDING) instanceof Token) {
                            Object objExecute = ((Token) jSONObject2.get(ELUtils.BINDING)).execute(arrayStack);
                            if (objExecute == null) {
                                objExecute = "";
                            }
                            sb.append(objExecute);
                        }
                    }
                }
                String string = sb.toString();
                if (string.length() > 256 && WXEnvironment.isApkDebugable()) {
                    WXLogUtils.w(WXRecyclerTemplateList.TAG, " warn too big string " + string);
                }
                map.put(str, string);
            }
        }
        return map;
    }

    public static Map<String, Object> renderProps(JSONObject jSONObject, ArrayStack arrayStack) {
        Set<Map.Entry<String, Object>> setEntrySet = jSONObject.entrySet();
        ArrayMap arrayMap = new ArrayMap(4);
        for (Map.Entry<String, Object> entry : setEntrySet) {
            Object value = entry.getValue();
            String key = entry.getKey();
            if (value instanceof JSONObject) {
                JSONObject jSONObject2 = (JSONObject) value;
                if (jSONObject2.get(ELUtils.BINDING) instanceof Token) {
                    arrayMap.put(key, ((Token) jSONObject2.get(ELUtils.BINDING)).execute(arrayStack));
                }
            }
            arrayMap.put(key, value);
        }
        return arrayMap;
    }

    private static final WXComponent copyComponentTree(WXComponent wXComponent, WXVContainer wXVContainer) {
        BasicComponentData basicComponentDataMo389clone;
        try {
            basicComponentDataMo389clone = wXComponent.getBasicComponentData().mo389clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            basicComponentDataMo389clone = null;
        }
        WXComponent wXComponentNewInstance = WXComponentFactory.newInstance(wXComponent.getInstance(), wXVContainer, basicComponentDataMo389clone);
        GraphicPosition layoutPosition = wXComponent.getLayoutPosition();
        GraphicSize layoutSize = wXComponent.getLayoutSize();
        wXComponentNewInstance.updateDemission(layoutPosition.getTop(), layoutPosition.getBottom(), layoutPosition.getLeft(), layoutPosition.getRight(), layoutSize.getHeight(), layoutSize.getWidth());
        wXComponentNewInstance.updateExtra(wXComponent.getExtra());
        if (wXComponent instanceof WXVContainer) {
            WXVContainer wXVContainer2 = (WXVContainer) wXComponent;
            WXVContainer wXVContainer3 = (WXVContainer) wXComponentNewInstance;
            int childCount = wXVContainer2.getChildCount();
            for (int i = 0; i < childCount; i++) {
                WXComponent child = wXVContainer2.getChild(i);
                if (child != null) {
                    WXComponent wXComponentCopyComponentTree = copyComponentTree(child, wXVContainer3);
                    wXVContainer3.addChild(wXComponentCopyComponentTree);
                    NativeRenderObjectUtils.nativeAddChildRenderObject(wXVContainer3.getRenderObjectPtr(), wXComponentCopyComponentTree.getRenderObjectPtr());
                }
            }
        }
        if (wXComponent.isWaste()) {
            wXComponentNewInstance.setWaste(true);
        }
        return wXComponentNewInstance;
    }
}

package com.taobao.weex.ui.component.list.template;

import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.JSONArray;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.utils.WXUtils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class CellDataManager {
    public static final String SUB_COMPONENT_TEMPLATE_ID = "@templateId";
    public static final String VIRTUAL_COMPONENT_ID = "@virtualComponentId";
    private static final String VIRTUAL_COMPONENT_SEPRATOR = "@";
    JSONArray listData;
    private Map<Integer, CellRenderState> renderStates = new ArrayMap();
    public final WXRecyclerTemplateList templateList;
    private Map<String, CellRenderState> virtualComponentRenderStates;

    public CellDataManager(WXRecyclerTemplateList wXRecyclerTemplateList) {
        this.templateList = wXRecyclerTemplateList;
    }

    private void cleanRenderState(CellRenderState cellRenderState) {
        if (cellRenderState != null && cellRenderState.hasVirtualComponents()) {
            for (String str : cellRenderState.getVirtualComponentIds().values()) {
                Map<String, CellRenderState> map = this.virtualComponentRenderStates;
                if (map != null) {
                    map.remove(str);
                }
                WXBridgeManager.getInstance().asyncCallJSEventVoidResult(WXBridgeManager.METHD_COMPONENT_HOOK_SYNC, this.templateList.getInstanceId(), null, str, VirtualComponentLifecycle.LIFECYCLE, "detach", null);
            }
        }
    }

    public static String createVirtualComponentId(String str, String str2, long j) {
        return str + VIRTUAL_COMPONENT_SEPRATOR + str2 + VIRTUAL_COMPONENT_SEPRATOR + j;
    }

    public static String getListRef(String str) {
        if (str == null) {
            return null;
        }
        return str.split(VIRTUAL_COMPONENT_SEPRATOR)[0];
    }

    public void createVirtualComponentData(int i, String str, Object obj) {
        if (this.virtualComponentRenderStates == null) {
            this.virtualComponentRenderStates = new HashMap(8);
        }
        CellRenderState cellRenderState = this.renderStates.get(Integer.valueOf(i));
        cellRenderState.getVirtualComponentDatas().put(str, obj);
        this.virtualComponentRenderStates.put(str, cellRenderState);
    }

    public CellRenderState getRenderState(int i) {
        CellRenderState cellRenderState = this.renderStates.get(Integer.valueOf(i));
        if (cellRenderState == null) {
            cellRenderState = new CellRenderState();
            cellRenderState.position = i;
            this.renderStates.put(Integer.valueOf(i), cellRenderState);
        }
        if (i != cellRenderState.position) {
            cellRenderState.position = i;
            cellRenderState.hasPositionChange = true;
        }
        return cellRenderState;
    }

    public boolean insertData(int i, Object obj) {
        this.listData.add(i, obj);
        boolean z = false;
        for (int size = this.listData.size(); size >= i; size--) {
            CellRenderState cellRenderStateRemove = this.renderStates.remove(Integer.valueOf(size));
            if (cellRenderStateRemove != null) {
                this.renderStates.put(Integer.valueOf(size + 1), cellRenderStateRemove);
                z = true;
            }
        }
        return z;
    }

    public boolean insertRange(int i, JSONArray jSONArray) {
        this.listData.addAll(i, jSONArray);
        boolean z = false;
        for (int size = this.listData.size() - 1; size >= i; size--) {
            CellRenderState cellRenderStateRemove = this.renderStates.remove(Integer.valueOf(size));
            if (cellRenderStateRemove != null) {
                this.renderStates.put(Integer.valueOf(size + 1), cellRenderStateRemove);
                z = true;
            }
        }
        return z;
    }

    public void removeData(Integer num) {
        this.listData.remove(num.intValue());
        cleanRenderState(this.renderStates.remove(num));
        int size = this.listData.size() + 1;
        int iIntValue = num.intValue();
        while (true) {
            iIntValue++;
            if (iIntValue >= size) {
                return;
            }
            CellRenderState cellRenderStateRemove = this.renderStates.remove(Integer.valueOf(iIntValue));
            if (cellRenderStateRemove != null) {
                this.renderStates.put(Integer.valueOf(iIntValue - 1), cellRenderStateRemove);
            }
        }
    }

    public void setListData(JSONArray jSONArray) {
        JSONArray jSONArray2 = this.listData;
        if (jSONArray2 != jSONArray) {
            if (jSONArray2 != null && WXUtils.getBoolean(this.templateList.getAttrs().get("exitDetach"), Boolean.TRUE).booleanValue()) {
                for (int i = 0; i < this.listData.size(); i++) {
                    cleanRenderState(this.renderStates.remove(Integer.valueOf(i)));
                }
            }
            this.listData = jSONArray;
            this.renderStates.clear();
            Map<String, CellRenderState> map = this.virtualComponentRenderStates;
            if (map != null) {
                map.clear();
            }
        }
    }

    public boolean updateData(Object obj, int i) {
        boolean zEquals = TextUtils.equals(this.templateList.getTemplateKey(i), this.templateList.getTemplateKey(obj));
        this.listData.set(i, obj);
        if (!zEquals) {
            cleanRenderState(this.renderStates.remove(Integer.valueOf(i)));
            return zEquals;
        }
        CellRenderState cellRenderState = this.renderStates.get(Integer.valueOf(i));
        if (cellRenderState != null) {
            cellRenderState.hasDataUpdate = true;
        }
        return zEquals;
    }

    public void updateVirtualComponentData(String str, Object obj) {
        Map<String, CellRenderState> map = this.virtualComponentRenderStates;
        if (map == null) {
            if (WXEnvironment.isApkDebugable()) {
                throw new IllegalArgumentException("virtualComponentDatas illegal state " + str);
            }
            return;
        }
        CellRenderState cellRenderState = map.get(str);
        if (cellRenderState != null) {
            cellRenderState.getVirtualComponentDatas().put(str, obj);
            cellRenderState.hasVirtualCompoentUpdate = true;
        } else if (WXEnvironment.isApkDebugable()) {
            throw new IllegalArgumentException("virtualComponentDatas illegal state empty render state" + str);
        }
    }
}

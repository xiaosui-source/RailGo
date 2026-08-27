package com.taobao.weex.ui.component.pesudo;

import androidx.collection.ArrayMap;
import com.taobao.weex.common.Constants;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class PesudoStatus {
    static final int CLASS_ACTIVE = 0;
    static final int CLASS_DISABLED = 3;
    static final int CLASS_ENABLED = 2;
    static final int CLASS_FOCUS = 1;
    private static final int SET = 1;
    private static final int UNSET = 0;
    private int[] mStatuses = new int[4];

    public PesudoStatus() {
        int i = 0;
        while (true) {
            int[] iArr = this.mStatuses;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = 0;
            i++;
        }
    }

    public String getStatuses() {
        StringBuilder sb = new StringBuilder();
        if (isSet(0)) {
            sb.append(Constants.PSEUDO.ACTIVE);
        }
        if (isSet(3)) {
            sb.append(Constants.PSEUDO.DISABLED);
        }
        if (isSet(1) && !isSet(3)) {
            sb.append(Constants.PSEUDO.FOCUS);
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public boolean isSet(int i) {
        return this.mStatuses[i] == 1;
    }

    public void setStatus(String str, boolean z) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case ":active":
                setStatus(0, z);
                break;
            case ":disabled":
                setStatus(3, z);
                break;
            case ":enabled":
                setStatus(2, z);
                break;
            case ":focus":
                setStatus(1, z);
                break;
        }
    }

    public Map<String, Object> updateStatusAndGetUpdateStyles(String str, boolean z, Map<String, Map<String, Object>> map, Map<String, Object> map2) {
        String statuses = getStatuses();
        setStatus(str, z);
        Map<String, Object> map3 = map.get(getStatuses());
        Map<String, Object> map4 = map.get(statuses);
        ArrayMap arrayMap = new ArrayMap();
        if (map4 != null) {
            arrayMap.putAll(map4);
        }
        for (K k : arrayMap.keySet()) {
            arrayMap.put(k, map2.containsKey(k) ? map2.get(k) : "");
        }
        if (map3 != null) {
            for (Map.Entry<String, Object> entry : map3.entrySet()) {
                arrayMap.put(entry.getKey(), entry.getValue());
            }
        }
        return arrayMap;
    }

    void setStatus(int i, boolean z) {
        this.mStatuses[i] = z ? 1 : 0;
    }
}

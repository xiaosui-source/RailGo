package io.dcloud.feature.weex.adapter;

import com.taobao.weex.adapter.IWXConfigAdapter;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCDefaultConfigAdapter implements IWXConfigAdapter {
    @Override // com.taobao.weex.adapter.IWXConfigAdapter
    public boolean checkMode(String str) {
        return false;
    }

    @Override // com.taobao.weex.adapter.IWXConfigAdapter
    public String getConfig(String str, String str2, String str3) {
        return str3;
    }

    @Override // com.taobao.weex.adapter.IWXConfigAdapter
    public String getConfigWhenInit(String str, String str2, String str3) {
        return str3;
    }
}

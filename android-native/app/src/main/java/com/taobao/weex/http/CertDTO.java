package com.taobao.weex.http;

import io.dcloud.common.DHInterface.IReflectAble;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class CertDTO implements IReflectAble {
    public String client;
    public String clientPassword;
    public String host;
    public String[] server;

    public String getClient() {
        return this.client;
    }

    public String getClientPassword() {
        return this.clientPassword;
    }

    public String getHost() {
        return this.host;
    }

    public String[] getServer() {
        return this.server;
    }

    public void setClient(String str) {
        this.client = str;
    }

    public void setClientPassword(String str) {
        this.clientPassword = str;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setServer(String[] strArr) {
        this.server = strArr;
    }
}

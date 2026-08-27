package com.taobao.weex.common;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXRequest {
    public static final int DEFAULT_TIMEOUT_MS = 60000;
    public String body;
    public String inputType;
    public String instanceId;
    public String method;
    public Map<String, String> paramMap;
    public String url;
    public int timeoutMs = DEFAULT_TIMEOUT_MS;
    public boolean sslVerify = false;
    public boolean isFirstIpv4 = false;
    public JSONObject tls = null;
}

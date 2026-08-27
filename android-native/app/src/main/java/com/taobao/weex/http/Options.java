package com.taobao.weex.http;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.common.WXRequest;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class Options {
    private String body;
    private Map<String, String> headers;
    private String inputType;
    private boolean isFirstIpv4;
    private String method;
    private boolean sslVerify;
    private int timeout;
    private JSONObject tlsConfig;
    private Type type;
    private String url;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum Type {
        json,
        text,
        jsonp,
        base64
    }

    public String getBody() {
        return this.body;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getInputType() {
        return this.inputType;
    }

    public String getMethod() {
        return this.method;
    }

    public boolean getSslVerify() {
        return this.sslVerify;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public JSONObject getTlsConfig() {
        return this.tlsConfig;
    }

    public Type getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isFirstIpv4() {
        return this.isFirstIpv4;
    }

    public void setInputType(String str) {
        this.inputType = str;
    }

    public void setTlsConfig(JSONObject jSONObject) {
        this.tlsConfig = jSONObject;
    }

    private Options(String str, String str2, Map<String, String> map, String str3, Type type, int i) {
        Type type2 = Type.json;
        this.timeout = WXRequest.DEFAULT_TIMEOUT_MS;
        this.sslVerify = false;
        this.isFirstIpv4 = false;
        this.method = str;
        this.url = str2;
        this.headers = map;
        this.body = str3;
        this.type = type;
        this.timeout = i == 0 ? WXRequest.DEFAULT_TIMEOUT_MS : i;
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Builder {
        private JSONObject androidTLSConfig;
        private String body;
        private Map<String, String> headers = new HashMap();
        private String inputType;
        private boolean isFirstIpv4;
        private String method;
        private boolean sslVerify;
        private int timeout;
        private Type type;
        private String url;

        public Options createOptions() {
            Options options = new Options(this.method, this.url, this.headers, this.body, this.type, this.timeout, this.sslVerify, this.isFirstIpv4);
            options.setInputType(this.inputType);
            options.setTlsConfig(this.androidTLSConfig);
            return options;
        }

        public Builder putHeader(String str, String str2) {
            this.headers.put(str, str2);
            return this;
        }

        public Builder setAndroidTlsConfig(JSONObject jSONObject) {
            this.androidTLSConfig = jSONObject;
            return this;
        }

        public Builder setBody(String str) {
            this.body = str;
            return this;
        }

        public Builder setFirstIpv4(boolean z) {
            this.isFirstIpv4 = z;
            return this;
        }

        public Builder setInputTypes(String str) {
            this.inputType = str;
            return this;
        }

        public Builder setMethod(String str) {
            this.method = str;
            return this;
        }

        public Builder setSslVerify(boolean z) {
            this.sslVerify = z;
            return this;
        }

        public Builder setTimeout(int i) {
            this.timeout = i;
            return this;
        }

        public Builder setType(String str) {
            Type type = Type.json;
            if (type.name().equals(str)) {
                this.type = type;
                return this;
            }
            Type type2 = Type.jsonp;
            if (type2.name().equals(str)) {
                this.type = type2;
                return this;
            }
            Type type3 = Type.base64;
            if (type3.name().equals(str)) {
                this.type = type3;
                return this;
            }
            this.type = Type.text;
            return this;
        }

        public Builder setUrl(String str) {
            this.url = str;
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }
    }

    private Options(String str, String str2, Map<String, String> map, String str3, Type type, int i, boolean z, boolean z2) {
        Type type2 = Type.json;
        this.timeout = WXRequest.DEFAULT_TIMEOUT_MS;
        this.sslVerify = false;
        this.isFirstIpv4 = false;
        this.method = str;
        this.url = str2;
        this.headers = map;
        this.body = str3;
        this.type = type;
        this.timeout = i == 0 ? WXRequest.DEFAULT_TIMEOUT_MS : i;
        this.sslVerify = z;
        this.isFirstIpv4 = z2;
    }
}

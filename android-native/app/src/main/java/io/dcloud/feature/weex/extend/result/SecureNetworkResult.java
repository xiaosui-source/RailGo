package io.dcloud.feature.weex.extend.result;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class SecureNetworkResult extends Result {
    public static final Result NOT_SUPPORT_MP_OR_BASE = new SecureNetworkResult(10001, "unimpsdk or playground is not support");
    public static final Result PARAMS_IS_NULL = new SecureNetworkResult(10002, "params is null");
    public static final Result APP_KEY_IS_NULL = new SecureNetworkResult(10003, "app key is null");
    public static final Result ENCRYPT_CLIENT_KEY_PAYLOAD_ERROR = new SecureNetworkResult(10004, "encrypt client key payload fail");
    public static final Result NATIVE_JSON_FORMAT_ERROR = new SecureNetworkResult(10005, "native json format error");
    public static final Result JSON_FORMAT_ERROR = new SecureNetworkResult(10006, "json format error");
    public static final Result CLIENT_KEY_ILLEGAL = new SecureNetworkResult(10007, "client key data is illegal");
    public static final Result CLIENT_KEY_IS_NULL = new SecureNetworkResult(10008, "client key is null");
    public static final Result ENCRYPT_ERROR = new SecureNetworkResult(10009, "encrypt fail");
    public static final Result DECRYPT_ERROR = new SecureNetworkResult(10010, "decrypt fail");

    public SecureNetworkResult(int i, String str) {
        super("uni-secure-network", i, str);
    }
}

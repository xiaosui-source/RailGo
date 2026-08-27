package io.dcloud.common.DHInterface;

import android.content.Context;
import java.io.InputStream;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IConfusionMgr {
    String decodeString(String str);

    String decodeString(String str, boolean z, int i);

    String decryptStr(String str);

    String decryptStr(String str, byte b);

    String encodeString(String str, boolean z, int i);

    String getCSJClassName();

    Map<String, String> getData(String str);

    InputStream getEncryptionInputStream(String str, IApp iApp);

    String getGDTClassName();

    String getKSClassName();

    String getODS();

    String getS5DS();

    String getSIV();

    String getSK();

    String getSPK();

    String getSQK();

    String handleEncryption(Context context, byte[] bArr);

    boolean isV3Encryption();

    void recordEncryptionResources(String str, JSONObject jSONObject);

    boolean recordEncryptionV3Resources(String str, String str2);

    void removeData(String str);
}

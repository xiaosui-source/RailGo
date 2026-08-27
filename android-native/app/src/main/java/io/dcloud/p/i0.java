package io.dcloud.p;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.URLUtil;
import io.dcloud.common.DHInterface.DCI;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IConfusionMgr;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.util.FileUtil;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.PdrUtil;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class i0 implements IConfusionMgr {
    private static String e;
    private static String f;
    private static String g;
    private static String h;
    private static String i;
    private static String j;
    private static IConfusionMgr k;
    private DCI b;
    private int a = 3;
    final String c = "##";
    private boolean d = false;

    private i0() {
        Object objInvokeMethod = PlatformUtil.invokeMethod(decodeString(b(), true, this.a), "getInstance", null);
        if (objInvokeMethod == null || !(objInvokeMethod instanceof DCI)) {
            return;
        }
        this.b = (DCI) objInvokeMethod;
    }

    private String a(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("GBK");
        for (int i2 = 0; i2 < bytes.length; i2++) {
            bytes[i2] = (byte) (bytes[i2] ^ 8);
        }
        return new String(bytes, "GBK");
    }

    private String b() {
        return "amwtZ2BvbHZnLWBsbm5sbS1gcC1HTyo2YTNkODhmYS00YmEwLTQ3OWYtOTQyMi1lNWFhYmUxNTg5N2I2Nw==";
    }

    public static IConfusionMgr c() {
        if (k == null) {
            k = new i0();
        }
        return k;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String decodeString(String str) {
        try {
            return a(new String(Base64.decode(str, 2)));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String decryptStr(String str) {
        try {
            return a(str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String encodeString(String str, boolean z, int i2) throws UnsupportedEncodingException {
        if (!z) {
            return str;
        }
        try {
            if (PdrUtil.isEmpty(str)) {
                return str;
            }
            byte[] bytes = str.getBytes("utf-8");
            for (int i3 = 0; i3 < bytes.length; i3++) {
                bytes[i3] = (byte) (bytes[i3] ^ i2);
            }
            return "##" + Base64.encodeToString((Base64.encodeToString(bytes, 2) + "*6a3d88fa-4ba0-479f-9422-e5aabe15897b" + (i2 + 64)).getBytes(), 2);
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getCSJClassName() {
        if (TextUtils.isEmpty(i)) {
            try {
                i = a("kge&jq|mlifkm&{lc&gxmfil{lc&\\\\Il[lc");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return i;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public Map getData(String str) {
        Object objInvokeMethod = PlatformUtil.invokeMethod(decodeString(a(), true, this.a), "getData", null, new Class[]{String.class}, new Object[]{str});
        if (objInvokeMethod == null || !(objInvokeMethod instanceof Map)) {
            return null;
        }
        return (Map) objInvokeMethod;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public InputStream getEncryptionInputStream(String str, IApp iApp) {
        byte[] bArrLf;
        Object objInvokeMethod;
        File file = null;
        if (TextUtils.isEmpty(str) || iApp == null || URLUtil.isNetworkUrl(str)) {
            return null;
        }
        InputStream fileInputStream = (Boolean.parseBoolean(iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_USE_V3_ENCRYPTION)) || (objInvokeMethod = PlatformUtil.invokeMethod(decodeString(a(), true, this.a), decodeString("b218TWZrenF4fGFnZkFmeH18W3x6bWll"), null, new Class[]{String.class, IApp.class}, new Object[]{str, iApp})) == null || !(objInvokeMethod instanceof InputStream)) ? null : (InputStream) objInvokeMethod;
        if (fileInputStream != null) {
            return fileInputStream;
        }
        boolean z = iApp.obtainRunningAppMode() == 1;
        if (str.startsWith(DeviceInfo.FILE_PROTOCOL)) {
            try {
                Uri uri = Uri.parse(str);
                if (uri != null) {
                    file = new File(uri.getPath());
                }
            } catch (Exception unused) {
            }
        } else {
            file = new File(str);
        }
        if (file == null && !z) {
            return fileInputStream;
        }
        try {
            if (z) {
                fileInputStream = PlatformUtil.getInputStream(str, 0);
            } else if (file != null && file.exists()) {
                fileInputStream = FileUtil.getFileInputStream(iApp.getActivity(), file);
            }
            return (fileInputStream == null || !isV3Encryption() || !this.b.cf(iApp.obtainAppId(), str) || (bArrLf = this.b.lf(iApp.obtainAppId(), str, IOUtil.getBytes(fileInputStream))) == null) ? fileInputStream : IOUtil.byte2InputStream(bArrLf);
        } catch (Exception e2) {
            e2.printStackTrace();
            return fileInputStream;
        }
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getGDTClassName() {
        if (TextUtils.isEmpty(h)) {
            try {
                h = a("kge&yy&m&il{&{xdi{`&[xdi{`IL");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return h;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getKSClassName() {
        if (TextUtils.isEmpty(j)) {
            try {
                j = a("kge&c\u007fil&{lc&ixa&C{Il[LC");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return j;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getODS() {
        return "##V2hOMGMzNUZaM0pYU25kQ1p4Ri9VMTRUZFdkZlFsaGRGR1ZVV0d4d1UwSjhmM1ZCUUVwallYUllVV0p6ZFhaQmR4aGdVWGgyZjF0SGZHc1JVV2hBVXc9PSo2YTNkODhmYS00YmEwLTQ3OWYtOTQyMi1lNWFhYmUxNTg5N2IxMTQ=";
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getS5DS() {
        return "UWV/BnpHVVhMahB0EU1XA15hAEFOAWlGVHBkcgluSF0HFhlQZx15Yhhjb3xCHgRfWxV+cQhPS1ICFxRzdkUfeyo2YTNkODhmYS00YmEwLTQ3OWYtOTQyMi1lNWFhYmUxNTg5N2IxMjQ=";
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getSIV() {
        if (TextUtils.isEmpty(f)) {
            try {
                f = a("@\\ED=XD][Z]F\\AEM");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return f;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getSK() {
        if (TextUtils.isEmpty(e)) {
            try {
                e = a("LKdg}l.:\"8V9+>88");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return e;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getSPK() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw4a5Rcq2ZWsKUogf5rc6Q+RqEZiFS6hq6FmNd5q6ZVwIRedk7HV5B6c7WCvLcEYhEe+dnF4XqhCiZe31nvp4FpmEvIDJrzh20qEwHAGcEUijF+0iXOWOskLpDqtXuk/anskpyJ/KstPbreHKVSE4DHqxgGf0ZUn7Z4BZynIGfAK/zizsIcRQwFccBHIsgi0AT+HBdXnxGWBK9LbeSnCzotqLTPEBrV9LhZsUGcY4B+HB1qTOS1PF2sv+/UDvmgWtM9PX3FrzuB8uy8gR+vf0XYJadaL6x0NlRRcIpE5R84oWuaarSfoNr3prFbh+EkYctODHmoiVIvqdfUspqRdZaQIDAQAB";
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String getSQK() {
        if (TextUtils.isEmpty(g)) {
            try {
                g = a("Y.:]\"8MV9[+>88\\?");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return g;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String handleEncryption(Context context, byte[] bArr) {
        Object objInvokeMethod = PlatformUtil.invokeMethod(decodeString(a(), true, this.a), decodeString("YGlmbGRtTWZrenF4fGFnZg=="), null, new Class[]{Context.class, byte[].class}, new Object[]{context, bArr});
        if (objInvokeMethod != null) {
            return String.valueOf(objInvokeMethod);
        }
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public boolean isV3Encryption() {
        return this.d;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public void recordEncryptionResources(String str, JSONObject jSONObject) {
        PlatformUtil.invokeMethod(decodeString(a(), true, this.a), decodeString("em1rZ3psTWZrenF4fGFnZlpte2d9emttew=="), null, new Class[]{String.class, JSONObject.class}, new Object[]{str, jSONObject});
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public boolean recordEncryptionV3Resources(String str, String str2) {
        if (this.b != null && !PdrUtil.isEmpty(str2)) {
            this.d = this.b.dt(str, str2.getBytes());
        }
        return this.d;
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public void removeData(String str) {
        PlatformUtil.invokeMethod(decodeString(a(), true, this.a), "removeData", null, new Class[]{String.class}, new Object[]{str});
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String decryptStr(String str, byte b) {
        try {
            return a(str, b);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String a(String str, byte b) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("GBK");
        for (int i2 = 0; i2 < bytes.length; i2++) {
            bytes[i2] = (byte) (bytes[i2] ^ b);
        }
        return new String(bytes, "GBK");
    }

    @Override // io.dcloud.common.DHInterface.IConfusionMgr
    public String decodeString(String str, boolean z, int i2) throws UnsupportedEncodingException {
        boolean z2;
        if (PdrUtil.isEmpty(str)) {
            return str;
        }
        if (str.startsWith("##")) {
            str = str.substring(2);
            z2 = true;
        } else {
            z2 = false;
        }
        byte[] bArrDecode = Base64.decode(str, 2);
        if (z) {
            try {
                String str2 = new String(bArrDecode);
                if (str2.contains("*6a3d88fa-4ba0-479f-9422-e5aabe15897b")) {
                    String strSubstring = str2.substring(0, str2.indexOf("*6a3d88fa-4ba0-479f-9422-e5aabe15897b"));
                    if (Integer.valueOf(str2.substring(r0 + 37)).intValue() - 64 == i2) {
                        if (z2) {
                            bArrDecode = Base64.decode(strSubstring, 2);
                        } else {
                            bArrDecode = strSubstring.getBytes("utf-8");
                        }
                        for (int i3 = 0; i3 < bArrDecode.length; i3++) {
                            bArrDecode[i3] = (byte) (bArrDecode[i3] ^ i2);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        return new String(bArrDecode, "utf-8");
    }

    private String a() {
        return "amwtZ2BvbHZnLWVmYnd2cWYtYGUtYEVmYnd2cWZKbnNvKjZhM2Q4OGZhLTRiYTAtNDc5Zi05NDIyLWU1YWFiZTE1ODk3YjY3";
    }
}

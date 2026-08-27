package io.dcloud.js.file;

import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.constant.AbsoluteConst;
import java.io.File;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class a {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.js.file.a$a, reason: collision with other inner class name */
    static class C0063a {
        long a;
        long b;
        int c = 0;
        int d = 0;

        C0063a() {
        }

        public JSONObject a() {
            try {
                return new JSONObject(String.format(Locale.ENGLISH, "{lastModifiedDate : %d,size : %d,directoryCount : %d,fileCount : %d}", Long.valueOf(this.a), Long.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.d)));
            } catch (JSONException e) {
                e.printStackTrace();
                return new JSONObject();
            }
        }
    }

    protected static JSONObject a(String str, long j, long j2, String str2, String str3) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("lastModifiedDate", j);
            jSONObject.put("type", str);
            jSONObject.put(AbsoluteConst.JSON_KEY_SIZE, j2);
            jSONObject.put("name", str2);
            jSONObject.put("fullPath", str3);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    protected static JSONArray b(String str, String str2) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        String str3 = File.separator;
        sb.append(str.endsWith(str3) ? "" : str3);
        String string = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        if (str2.endsWith(str3)) {
            str3 = "";
        }
        sb2.append(str3);
        String string2 = sb2.toString();
        String[] list = new File(string).list();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                String str4 = string + list[i];
                File file = new File(str4);
                JSONObject jSONObject = new JSONObject();
                boolean zIsDirectory = file.isDirectory();
                String str5 = list[i];
                String str6 = string2 + str5;
                try {
                    jSONObject.put("isDirectory", zIsDirectory);
                    jSONObject.put("isFile", !zIsDirectory);
                    jSONObject.put("name", str5);
                    jSONObject.put("remoteURL", str6);
                    jSONObject.put("fullPath", str4);
                    jSONArray.put(jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONArray;
    }

    protected static JSONObject a(String str, int i, String str2, String str3, String str4) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", str);
            jSONObject.put("type", i);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("name", str2);
            jSONObject2.put("fullPath", str3);
            jSONObject2.put("remoteURL", str4);
            jSONObject.put("root", jSONObject2);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    protected static JSONObject a(String str, String str2, String str3, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("isDirectory", z);
            jSONObject.put("isFile", !z);
            jSONObject.put("name", str);
            jSONObject.put("remoteURL", str3);
            jSONObject.put("fullPath", str2);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    protected static JSONObject a(String str, String str2, boolean z, String str3, String str4, int i, JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("isDirectory", z);
            jSONObject2.put("isFile", !z);
            jSONObject2.put("name", str);
            jSONObject2.put("remoteURL", str3);
            jSONObject2.put("fullPath", str2);
            jSONObject2.put("fsName", str4);
            jSONObject2.put("type", i);
            jSONObject2.put("fsRoot", jSONObject);
            return jSONObject2;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject2;
        }
    }

    public static JSONObject a(String str, String str2) {
        File file = new File(str);
        return a(str2, file.lastModified(), DHFile.getFileSize(file), file.getName(), str);
    }

    public static JSONObject a(String str, boolean z) {
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        C0063a c0063a = new C0063a();
        c0063a.a = file.lastModified();
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    a(file2, c0063a, z);
                }
            }
        } else {
            c0063a.b = file.length();
        }
        return c0063a.a();
    }

    public static void a(File file, C0063a c0063a, boolean z) {
        File[] fileArrListFiles;
        if (file.isDirectory()) {
            if (z && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    a(file2, c0063a, z);
                }
            }
            c0063a.c++;
            return;
        }
        c0063a.b += file.length();
        c0063a.d++;
    }
}

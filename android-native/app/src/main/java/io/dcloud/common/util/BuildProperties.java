package io.dcloud.common.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import io.dcloud.common.adapter.util.DeviceInfo;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class BuildProperties {
    private static String BUILD_PROPERTIES_FILE_NAME = ".buildPropertiesMD5.data";
    private static String BUILD_PROPERTIES_FILE_PATH = "";
    private static volatile BuildProperties ourInstance;
    private Properties properties;

    private BuildProperties() throws Exception {
        Properties properties = new Properties();
        this.properties = properties;
        properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
    }

    public static BuildProperties getInstance() throws Exception {
        if (ourInstance == null) {
            synchronized (BuildProperties.class) {
                if (ourInstance == null) {
                    ourInstance = new BuildProperties();
                }
            }
        }
        return ourInstance;
    }

    public boolean containsKey(Object obj) {
        return this.properties.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return this.properties.containsValue(obj);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.properties.entrySet();
    }

    public String getBuildPropertiesLimit(Context context) throws Throwable {
        if (DeviceInfo.isSDcardExists()) {
            BUILD_PROPERTIES_FILE_PATH = DeviceInfo.sBaseFsCachePath;
        } else {
            if (context == null) {
                return "";
            }
            BUILD_PROPERTIES_FILE_PATH = context.getFilesDir().getAbsolutePath();
        }
        try {
            String strMd5 = Md5Utils.md5(new File(Environment.getRootDirectory(), "build.prop"));
            if (strMd5 != null) {
                String str = BUILD_PROPERTIES_FILE_PATH + File.separator + BUILD_PROPERTIES_FILE_NAME;
                File file = new File(str);
                if (file.exists()) {
                    String stringFile = IOUtil.readStringFile(str);
                    if (!TextUtils.isEmpty(stringFile) && !stringFile.equals(strMd5)) {
                    }
                } else {
                    file.createNewFile();
                }
                IOUtil.writeStringFile(str, strMd5);
                JSONObject jSONObject = new JSONObject();
                Properties properties = this.properties;
                if (properties != null) {
                    for (Map.Entry entry : properties.entrySet()) {
                        jSONObject.put(entry.getKey().toString(), entry.getValue());
                    }
                }
                String string = jSONObject.toString();
                if (string != null) {
                    if (!"".equals(string.trim())) {
                        return string;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getProperty(String str) {
        return this.properties.getProperty(str);
    }

    public boolean isEmpty() {
        return this.properties.isEmpty();
    }

    public Set keySet() {
        return this.properties.keySet();
    }

    public Enumeration keys() {
        return this.properties.keys();
    }

    public int size() {
        return this.properties.size();
    }

    public Collection values() {
        return this.properties.values();
    }

    public String getProperty(String str, String str2) {
        return this.properties.getProperty(str, str2);
    }
}

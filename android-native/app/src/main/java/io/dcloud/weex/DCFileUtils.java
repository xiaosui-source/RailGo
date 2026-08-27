package io.dcloud.weex;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.feature.weex.WeexInstanceMgr;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONObject;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCFileUtils {
    public static String getAssetPath(String str) {
        String str2;
        if (TextUtils.isEmpty(str) || !str.equals("weex-main-jsfm.js")) {
            return str;
        }
        String str3 = WeexInstanceMgr.self().getVueVersion() == 3 ? "uni-jsframework-vue3" : "uni-jsframework";
        if (BaseInfo.SyncDebug) {
            str2 = str3 + "-dev.js";
        } else {
            str2 = str3 + ".js";
        }
        if (PlatformUtil.getResInputStream(str2) == null && BaseInfo.SyncDebug) {
            str2 = str3 + ".js";
        }
        Logger.i("DCFileUtils", "getAssetPath---------" + str2);
        return str2;
    }

    public static InputStream loadWeexAsset(String str, Context context) throws IOException {
        InputStream inputStreamOpen;
        InputStream inputStreamLoadWeexAsset = AppRuntime.loadWeexAsset(str, context);
        if (inputStreamLoadWeexAsset != null || !str.startsWith("uni-jsframework") || !str.endsWith(".js")) {
            return inputStreamLoadWeexAsset;
        }
        try {
            try {
                inputStreamOpen = context.getAssets().open(str);
            } catch (FileNotFoundException unused) {
                WeexInstanceMgr.self().setJSFKFileNotFound(true);
                inputStreamOpen = null;
            }
            if (inputStreamOpen == null) {
                WeexInstanceMgr.self().setJSFKFileNotFound(true);
                return inputStreamLoadWeexAsset;
            }
            WeexInstanceMgr.self().setJSFKFileNotFound(false);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpen));
            JSONObject jSONObject = new JSONObject(bufferedReader.readLine().substring(2));
            BaseInfo.setUniVersionV3(jSONObject.optString("version"), context);
            String strOptString = jSONObject.optString("encode");
            if (TextUtils.isEmpty(strOptString) || !strOptString.equals("base64")) {
                return inputStreamLoadWeexAsset;
            }
            String line = bufferedReader.readLine();
            while (true) {
                String line2 = bufferedReader.readLine();
                if (line2 == null) {
                    return new ByteArrayInputStream(Base64.decode(line, 0));
                }
                line = line + line2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return inputStreamLoadWeexAsset;
        }
    }
}

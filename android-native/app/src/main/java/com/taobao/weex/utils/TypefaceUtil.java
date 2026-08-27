package com.taobao.weex.utils;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.taobao.weex.font.FontAdapter;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TypefaceUtil {
    public static final String ACTION_TYPE_FACE_AVAILABLE = "type_face_available";
    public static final String FONT_CACHE_DIR_NAME = "font-family";
    private static final String TAG = "TypefaceUtil";
    private static final Map<String, FontDO> sCacheMap = new HashMap();

    public static void applyFontStyle(Paint paint, int i, int i2, String str) {
        Typeface typeface = paint.getTypeface();
        int style = typeface == null ? 0 : typeface.getStyle();
        int i3 = 1;
        if (i2 != 1 && ((style & 1) == 0 || i2 != -1)) {
            i3 = 0;
        }
        if (i == 2 || ((style & 2) != 0 && i == -1)) {
            i3 |= 2;
        }
        if (str != null) {
            typeface = getOrCreateTypeface(str, i3);
        }
        if (typeface != null) {
            paint.setTypeface(Typeface.create(typeface, i3));
        } else {
            paint.setTypeface(Typeface.defaultFromStyle(i3));
        }
        if (i >= 0) {
            paint.setTextSkewX(((i & (~(paint.getTypeface() != null ? paint.getTypeface().getStyle() : 0))) & 2) != 0 ? -0.2f : 0.0f);
        }
    }

    private static void downloadFontByNetwork(final String str, final String str2, final String str3) {
        IWXHttpAdapter iWXHttpAdapter = WXSDKManager.getInstance().getIWXHttpAdapter();
        if (iWXHttpAdapter == null) {
            WXLogUtils.e(TAG, "downloadFontByNetwork() IWXHttpAdapter == null");
            return;
        }
        WXRequest wXRequest = new WXRequest();
        wXRequest.url = str;
        wXRequest.method = "GET";
        iWXHttpAdapter.sendRequest(wXRequest, new IWXHttpAdapter.OnHttpListener() { // from class: com.taobao.weex.utils.TypefaceUtil.1
            @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
            public void onHeadersReceived(int i, Map<String, List<String>> map) {
            }

            @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
            public void onHttpFinish(WXResponse wXResponse) throws NumberFormatException {
                int i;
                FontDO fontDO;
                byte[] bArr;
                boolean zSaveFile = false;
                if (TextUtils.isEmpty(wXResponse.statusCode)) {
                    i = 0;
                } else {
                    try {
                        i = Integer.parseInt(wXResponse.statusCode);
                    } catch (NumberFormatException unused) {
                        WXLogUtils.e(TypefaceUtil.TAG, "IWXHttpAdapter onHttpFinish statusCode:" + wXResponse.statusCode);
                    }
                }
                if (i >= 200 && i <= 299 && (bArr = wXResponse.originalData) != null) {
                    zSaveFile = WXFileUtils.saveFile(str2, bArr, WXEnvironment.getApplication());
                    if (zSaveFile) {
                        zSaveFile = TypefaceUtil.loadLocalFontFile(str2, str3, true);
                    } else if (WXEnvironment.isApkDebugable()) {
                        WXLogUtils.d(TypefaceUtil.TAG, "downloadFontByNetwork() onHttpFinish success, but save file failed.");
                    }
                }
                if (zSaveFile || (fontDO = (FontDO) TypefaceUtil.sCacheMap.get(str3)) == null) {
                    return;
                }
                fontDO.setState(3);
            }

            @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
            public void onHttpResponseProgress(int i) {
            }

            @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
            public void onHttpStart() {
                if (WXEnvironment.isApkDebugable()) {
                    WXLogUtils.d(TypefaceUtil.TAG, "downloadFontByNetwork begin url:" + str);
                }
            }

            @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
            public void onHttpUploadProgress(int i) {
            }
        });
    }

    private static String getFontCacheDir() {
        return WXEnvironment.getApplication().getCacheDir() + "/font-family";
    }

    public static FontDO getFontDO(String str) {
        return sCacheMap.get(str);
    }

    public static Typeface getOrCreateTypeface(String str, int i) {
        FontDO fontDO = sCacheMap.get(str);
        return (fontDO == null || fontDO.getTypeface() == null) ? Typeface.create(str, i) : fontDO.getTypeface();
    }

    private static void loadFromAsset(FontDO fontDO, String str) {
        try {
            Typeface typefaceCreateFromAsset = Typeface.createFromAsset(WXEnvironment.getApplication().getAssets(), str);
            if (typefaceCreateFromAsset == null) {
                WXLogUtils.e(TAG, "Font asset file not found " + fontDO.getUrl());
            } else {
                if (WXEnvironment.isApkDebugable()) {
                    WXLogUtils.d(TAG, "load asset file success");
                }
                fontDO.setState(2);
                fontDO.setTypeface(typefaceCreateFromAsset);
            }
        } catch (Exception e) {
            WXLogUtils.e(TAG, e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean loadLocalFontFile(String str, String str2, boolean z) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                if (!new File(str).exists()) {
                    return false;
                }
                Typeface typefaceCreateFromFile = Typeface.createFromFile(str);
                if (typefaceCreateFromFile != null) {
                    final FontDO fontDO = sCacheMap.get(str2);
                    if (fontDO != null) {
                        fontDO.setState(2);
                        fontDO.setTypeface(typefaceCreateFromFile);
                        fontDO.setFilePath(str);
                        if (WXEnvironment.isApkDebugable()) {
                            WXLogUtils.d(TAG, "load local font file success");
                        }
                        if (z) {
                            WXSDKManager.getInstance().getWXRenderManager().postOnUiThread(new Runnable() { // from class: com.taobao.weex.utils.TypefaceUtil.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    TypefaceUtil.notifyFontAvailable(true, fontDO);
                                }
                            }, 100L);
                        } else {
                            notifyFontAvailable(true, fontDO);
                        }
                        return true;
                    }
                } else {
                    WXLogUtils.e(TAG, "load local font file failed, can't create font.");
                }
            } catch (Exception e) {
                WXLogUtils.e(TAG, e.toString());
            }
        }
        return false;
    }

    public static void loadTypeface(FontDO fontDO, boolean z) {
        if (fontDO == null || fontDO.getTypeface() != null || (fontDO.getState() != 3 && fontDO.getState() != 0)) {
            if (z) {
                notifyFontAvailable(false, fontDO);
                return;
            }
            return;
        }
        fontDO.setState(1);
        if (fontDO.getType() == 3) {
            loadFromAsset(fontDO, Uri.parse(fontDO.getUrl()).getPath().substring(1));
            return;
        }
        if (fontDO.getType() != 1) {
            if ((fontDO.getType() == 2 || fontDO.getType() == 5) && !loadLocalFontFile(fontDO.getUrl(), fontDO.getFontFamilyName(), false)) {
                fontDO.setState(3);
                return;
            }
            return;
        }
        String url = fontDO.getUrl();
        String fontFamilyName = fontDO.getFontFamilyName();
        String strMd5 = WXFileUtils.md5(url);
        File file = new File(getFontCacheDir());
        if (!file.exists()) {
            file.mkdirs();
        }
        String str = file.getAbsolutePath() + File.separator + strMd5;
        if (loadLocalFontFile(str, fontFamilyName, false)) {
            return;
        }
        downloadFontByNetwork(url, str, fontFamilyName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyFontAvailable(boolean z, FontDO fontDO) {
        if (z) {
            Intent intent = new Intent(ACTION_TYPE_FACE_AVAILABLE);
            intent.putExtra(Constants.Name.FONT_FAMILY, fontDO.getFontFamilyName());
            intent.putExtra("filePath", fontDO.getFilePath());
            intent.putExtra("fontUrl", fontDO.getUrl());
            LocalBroadcastManager.getInstance(WXEnvironment.getApplication()).sendBroadcast(intent);
        }
        FontAdapter fontAdapter = WXSDKManager.getInstance().getFontAdapter();
        if (fontAdapter != null) {
            fontAdapter.onFontLoad(fontDO.getFontFamilyName(), fontDO.getUrl(), fontDO.getFilePath());
        }
    }

    public static void putFontDO(FontDO fontDO) {
        if (fontDO == null || TextUtils.isEmpty(fontDO.getFontFamilyName())) {
            return;
        }
        sCacheMap.put(fontDO.getFontFamilyName(), fontDO);
    }

    public static void registerNativeFont(Map<String, Typeface> map) {
        if (map == null || map.size() <= 0) {
            return;
        }
        for (Map.Entry<String, Typeface> entry : map.entrySet()) {
            putFontDO(new FontDO(entry.getKey(), entry.getValue()));
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d(TAG, "register new typeface: " + entry.getKey());
            }
        }
    }

    public static void removeFontDO(String str) {
        sCacheMap.remove(str);
    }
}

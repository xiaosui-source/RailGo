package com.taobao.weex.utils;

import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.Constants;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import java.io.File;
import java.io.IOException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class FontDO {
    public static final int STATE_FAILED = 3;
    public static final int STATE_INIT = 0;
    public static final int STATE_INVALID = -1;
    public static final int STATE_LOADING = 1;
    public static final int STATE_SUCCESS = 2;
    public static final int TYPE_BASE64 = 5;
    public static final int TYPE_FILE = 2;
    public static final int TYPE_LOCAL = 3;
    public static final int TYPE_NATIVE = 4;
    public static final int TYPE_NETWORK = 1;
    public static final int TYPE_UNKNOWN = 0;
    private String mFilePath;
    private final String mFontFamilyName;
    private Typeface mTypeface;
    private String mUrl = "";
    private int mType = 4;
    private int mState = 2;

    public FontDO(String str, String str2, WXSDKInstance wXSDKInstance) throws IOException {
        this.mFontFamilyName = str;
        parseSrc(str2, wXSDKInstance);
    }

    private void parseSrc(String str, WXSDKInstance wXSDKInstance) throws IOException {
        String strTrim = str != null ? str.trim() : "";
        if (wXSDKInstance != null && wXSDKInstance.getCustomFontNetworkHandler() != null) {
            String strFetchLocal = wXSDKInstance.getCustomFontNetworkHandler().fetchLocal(strTrim);
            if (!TextUtils.isEmpty(strFetchLocal)) {
                strTrim = strFetchLocal;
            }
        }
        if (strTrim.isEmpty()) {
            this.mState = -1;
            WXLogUtils.e("TypefaceUtil", "font src is empty.");
            return;
        }
        if (strTrim.matches("^url\\((('.*')|(\".*\"))\\)$")) {
            Uri uriRewriteUri = Uri.parse(strTrim.substring(5, strTrim.length() - 2));
            if (wXSDKInstance != null) {
                uriRewriteUri = wXSDKInstance.rewriteUri(uriRewriteUri, AbsURIAdapter.FONT);
            }
            this.mUrl = uriRewriteUri.toString();
            try {
                String scheme = uriRewriteUri.getScheme();
                if ("http".equals(scheme) || "https".equals(scheme)) {
                    this.mType = 1;
                } else if ("file".equals(scheme)) {
                    this.mType = 2;
                    this.mUrl = uriRewriteUri.getEncodedSchemeSpecificPart();
                } else if (Constants.Scheme.LOCAL.equals(scheme)) {
                    this.mType = 3;
                } else if ("data".equals(scheme)) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    String[] strArrSplit = this.mUrl.split(",");
                    if (strArrSplit != null && strArrSplit.length == 2) {
                        String str2 = strArrSplit[0];
                        if (!TextUtils.isEmpty(str2) && str2.endsWith("base64")) {
                            String str3 = strArrSplit[1];
                            if (!TextUtils.isEmpty(str3)) {
                                String strMd5 = WXFileUtils.md5(str3);
                                File file = new File(WXEnvironment.getApplication().getCacheDir(), TypefaceUtil.FONT_CACHE_DIR_NAME);
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                File file2 = new File(file, strMd5);
                                if (!file2.exists()) {
                                    file2.createNewFile();
                                    WXFileUtils.saveFile(file2.getPath(), Base64.decode(str3, 0), WXEnvironment.getApplication());
                                }
                                this.mUrl = file2.getPath();
                                this.mType = 5;
                                WXLogUtils.d("TypefaceUtil", "Parse base64 font cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
                            }
                        }
                    }
                } else {
                    WXLogUtils.e("TypefaceUtil", "Unknown scheme for font url: " + this.mUrl);
                    this.mType = 0;
                }
                this.mState = 0;
            } catch (Exception e) {
                this.mType = -1;
                WXLogUtils.e("TypefaceUtil", "URI.create(mUrl) failed mUrl: " + this.mUrl + "\n" + WXLogUtils.getStackTrace(e));
            }
        } else {
            this.mUrl = strTrim;
            this.mState = -1;
        }
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d("TypefaceUtil", "src:" + strTrim + ", mUrl:" + this.mUrl + ", mType:" + this.mType);
        }
    }

    public String getFilePath() {
        return this.mFilePath;
    }

    public String getFontFamilyName() {
        return this.mFontFamilyName;
    }

    public int getState() {
        return this.mState;
    }

    public int getType() {
        return this.mType;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setFilePath(String str) {
        this.mFilePath = str;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
    }

    public FontDO(String str, Typeface typeface) {
        this.mFontFamilyName = str;
        this.mTypeface = typeface;
    }
}

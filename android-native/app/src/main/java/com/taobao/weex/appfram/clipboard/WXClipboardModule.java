package com.taobao.weex.appfram.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXClipboardModule extends WXModule implements IWXClipboard {
    private static final String DATA = "data";
    private static final String RESULT = "result";
    private static final String RESULT_FAILED = "failed";
    private static final String RESULT_OK = "success";
    private final String CLIP_KEY = "WEEX_CLIP_KEY_MAIN";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v13, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.io.FileInputStream] */
    private CharSequence coerceToText(Context context, ClipData.Item item) throws Throwable {
        Throwable th;
        FileInputStream fileInputStream;
        IOException e;
        FileInputStream fileInputStream2;
        InputStreamReader inputStreamReader;
        CharSequence text = item.getText();
        if (text != null) {
            return text;
        }
        Uri uri = item.getUri();
        InputStreamReader inputStreamReader2 = null;
        try {
            try {
                if (uri == null) {
                    Intent intent = item.getIntent();
                    if (intent != null) {
                        return intent.toUri(1);
                    }
                    return null;
                }
                try {
                    context = context.getContentResolver().openTypedAssetFileDescriptor(uri, "text/*", null).createInputStream();
                    try {
                        inputStreamReader = new InputStreamReader((InputStream) context, "UTF-8");
                    } catch (FileNotFoundException unused) {
                        fileInputStream2 = context;
                    } catch (IOException e2) {
                        e = e2;
                    }
                    try {
                        StringBuilder sb = new StringBuilder(128);
                        char[] cArr = new char[8192];
                        while (true) {
                            int i = inputStreamReader.read(cArr);
                            if (i <= 0) {
                                break;
                            }
                            sb.append(cArr, 0, i);
                        }
                        String string = sb.toString();
                        try {
                            inputStreamReader.close();
                        } catch (IOException unused2) {
                        }
                        if (context != 0) {
                            try {
                                context.close();
                            } catch (IOException unused3) {
                            }
                        }
                        return string;
                    } catch (FileNotFoundException unused4) {
                        inputStreamReader2 = inputStreamReader;
                        fileInputStream2 = context;
                        if (inputStreamReader2 != null) {
                            try {
                                inputStreamReader2.close();
                            } catch (IOException unused5) {
                            }
                        }
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        return uri.toString();
                    } catch (IOException e3) {
                        inputStreamReader2 = inputStreamReader;
                        e = e3;
                        WXLogUtils.w("ClippedData Failure loading text.", e);
                        if (inputStreamReader2 != null) {
                            try {
                                inputStreamReader2.close();
                            } catch (IOException unused6) {
                            }
                        }
                        if (context != 0) {
                            context.close();
                        }
                        return uri.toString();
                    } catch (Throwable th2) {
                        th = th2;
                        inputStreamReader2 = inputStreamReader;
                        fileInputStream = context;
                        if (inputStreamReader2 != null) {
                            try {
                                inputStreamReader2.close();
                            } catch (IOException unused7) {
                            }
                        }
                        if (fileInputStream == null) {
                            throw th;
                        }
                        try {
                            fileInputStream.close();
                            throw th;
                        } catch (IOException unused8) {
                            throw th;
                        }
                    }
                } catch (FileNotFoundException unused9) {
                    fileInputStream2 = null;
                } catch (IOException e4) {
                    e = e4;
                    context = 0;
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = null;
                }
            } catch (IOException unused10) {
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = context;
        }
    }

    @Override // com.taobao.weex.appfram.clipboard.IWXClipboard
    @JSMethod
    public void getString(JSCallback jSCallback) throws Throwable {
        Context context = this.mWXSDKInstance.getContext();
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        HashMap map = new HashMap(2);
        ClipData primaryClip = clipboardManager.getPrimaryClip();
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            map.put("result", "failed");
            map.put("data", "");
        } else {
            CharSequence charSequenceCoerceToText = coerceToText(context, primaryClip.getItemAt(0));
            map.put("result", charSequenceCoerceToText != null ? "success" : "failed");
            map.put("data", charSequenceCoerceToText != null ? charSequenceCoerceToText : "");
        }
        if (jSCallback != null) {
            jSCallback.invoke(map);
        }
    }

    @Override // com.taobao.weex.appfram.clipboard.IWXClipboard
    @JSMethod
    public void setString(String str) {
        if (str == null) {
            return;
        }
        ((ClipboardManager) this.mWXSDKInstance.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("WEEX_CLIP_KEY_MAIN", str));
    }
}

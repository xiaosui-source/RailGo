package io.dcloud.js.file;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.taobao.weex.common.Constants;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.AsyncTaskHandler;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Base64;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.FileUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.Md5Utils;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class FileFeatureImpl implements IFeature {
    private static String a;
    private static String b;
    private static String c;
    private static String d;
    private static String e;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements AsyncTaskHandler.IAsyncTaskListener {
        final /* synthetic */ String a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ String d;
        final /* synthetic */ IWebview e;
        final /* synthetic */ String f;

        a(String str, int i, int i2, String str2, IWebview iWebview, String str3) {
            this.a = str;
            this.b = i;
            this.c = i2;
            this.d = str2;
            this.e = iWebview;
            this.f = str3;
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onCancel() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteBegin() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteEnd(Object obj) {
            if (obj != null) {
                Deprecated_JSUtil.execCallback(this.e, this.f, String.valueOf(obj), JSUtil.OK, true, false);
            } else {
                FileFeatureImpl.this.a(10, this.e, this.f);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:47:0x0098 A[Catch: IOException -> 0x0094, TRY_LEAVE, TryCatch #6 {IOException -> 0x0094, blocks: (B:43:0x0090, B:47:0x0098), top: B:71:0x0090 }] */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00ae A[Catch: IOException -> 0x00aa, TRY_LEAVE, TryCatch #2 {IOException -> 0x00aa, blocks: (B:55:0x00a6, B:59:0x00ae), top: B:67:0x00a6 }] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x00a6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0090 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public java.lang.Object onExecuting() throws java.lang.Throwable {
            /*
                r10 = this;
                r0 = 0
                java.lang.String r1 = r10.a     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L85
                java.lang.Object r1 = io.dcloud.common.adapter.io.DHFile.createFileHandler(r1)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L85
                java.io.InputStream r1 = io.dcloud.common.adapter.io.DHFile.getInputStream(r1)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L85
                int r2 = r1.available()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L80
                io.dcloud.common.adapter.io.UnicodeInputStream r3 = new io.dcloud.common.adapter.io.UnicodeInputStream     // Catch: java.lang.Exception -> L1e java.lang.Throwable -> L7c
                java.nio.charset.Charset r4 = java.nio.charset.Charset.defaultCharset()     // Catch: java.lang.Exception -> L1e java.lang.Throwable -> L7c
                java.lang.String r4 = r4.name()     // Catch: java.lang.Exception -> L1e java.lang.Throwable -> L7c
                r3.<init>(r1, r4)     // Catch: java.lang.Exception -> L1e java.lang.Throwable -> L7c
                r1 = r3
                goto L22
            L1e:
                r3 = move-exception
                r3.printStackTrace()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L80
            L22:
                java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L80
                r3.<init>()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L80
                int r4 = r10.b     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                r5 = 0
                if (r4 <= 0) goto L42
                if (r4 < r2) goto L30
                int r4 = r2 + (-1)
            L30:
                int r2 = r10.c     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                int r4 = r4 - r2
                int r4 = r4 + 1
                byte[] r6 = new byte[r4]     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                long r7 = (long) r2     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                r1.skip(r7)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                r1.read(r6, r5, r4)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                r3.write(r6, r5, r4)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                goto L4e
            L42:
                r2 = 204800(0x32000, float:2.86986E-40)
                byte[] r2 = new byte[r2]     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                int r4 = r1.read(r2)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                r6 = -1
                if (r4 != r6) goto L71
            L4e:
                java.lang.String r2 = r10.d     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                boolean r2 = io.dcloud.common.util.PdrUtil.isEmpty(r2)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                if (r2 == 0) goto L5b
                java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                goto L61
            L5b:
                java.lang.String r2 = r10.d     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                java.lang.String r0 = r3.toString(r2)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            L61:
                java.lang.String r0 = io.dcloud.common.util.JSONUtil.toJSONableString(r0)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                r1.close()     // Catch: java.io.IOException -> L6c
                r3.close()     // Catch: java.io.IOException -> L6c
                goto La0
            L6c:
                r1 = move-exception
                r1.printStackTrace()
                goto La0
            L71:
                r3.write(r2, r5, r4)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
                goto L42
            L75:
                r0 = move-exception
                goto La4
            L77:
                r2 = move-exception
                r9 = r1
                r1 = r0
                r0 = r9
                goto L8b
            L7c:
                r2 = move-exception
                r3 = r0
                r0 = r1
                goto La2
            L80:
                r2 = move-exception
                goto L88
            L82:
                r2 = move-exception
                r3 = r0
                goto La2
            L85:
                r1 = move-exception
                r2 = r1
                r1 = r0
            L88:
                r3 = r0
                r0 = r1
                r1 = r3
            L8b:
                r2.printStackTrace()     // Catch: java.lang.Throwable -> La1
                if (r0 == 0) goto L96
                r0.close()     // Catch: java.io.IOException -> L94
                goto L96
            L94:
                r0 = move-exception
                goto L9c
            L96:
                if (r3 == 0) goto L9f
                r3.close()     // Catch: java.io.IOException -> L94
                goto L9f
            L9c:
                r0.printStackTrace()
            L9f:
                r0 = r1
            La0:
                return r0
            La1:
                r2 = move-exception
            La2:
                r1 = r0
                r0 = r2
            La4:
                if (r1 == 0) goto Lac
                r1.close()     // Catch: java.io.IOException -> Laa
                goto Lac
            Laa:
                r1 = move-exception
                goto Lb2
            Lac:
                if (r3 == 0) goto Lb5
                r3.close()     // Catch: java.io.IOException -> Laa
                goto Lb5
            Lb2:
                r1.printStackTrace()
            Lb5:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.js.file.FileFeatureImpl.a.onExecuting():java.lang.Object");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements AsyncTaskHandler.IAsyncTaskListener {
        final /* synthetic */ String a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ IWebview d;
        final /* synthetic */ String e;

        b(String str, int i, int i2, IWebview iWebview, String str2) {
            this.a = str;
            this.b = i;
            this.c = i2;
            this.d = iWebview;
            this.e = str2;
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onCancel() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteBegin() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteEnd(Object obj) {
            if (obj != null) {
                Deprecated_JSUtil.execCallback(this.d, this.e, String.valueOf(obj), JSUtil.OK, true, false);
            } else {
                FileFeatureImpl.this.a(10, this.d, this.e);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:52:0x009e A[Catch: IOException -> 0x009a, TRY_LEAVE, TryCatch #0 {IOException -> 0x009a, blocks: (B:48:0x0096, B:52:0x009e), top: B:67:0x0096 }] */
        /* JADX WARN: Removed duplicated region for block: B:63:0x00b2 A[Catch: IOException -> 0x00ae, TRY_LEAVE, TryCatch #8 {IOException -> 0x00ae, blocks: (B:59:0x00aa, B:63:0x00b2), top: B:78:0x00aa }] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:78:0x00aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public java.lang.Object onExecuting() throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 186
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.js.file.FileFeatureImpl.b.onExecuting():java.lang.Object");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c extends CustomTarget {
        final /* synthetic */ String a;
        final /* synthetic */ IWebview b;
        final /* synthetic */ String c;

        c(String str, IWebview iWebview, String str2) {
            this.a = str;
            this.b = iWebview;
            this.c = str2;
        }

        @Override // com.bumptech.glide.request.target.Target
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResourceReady(File file, Transition transition) throws JSONException, IOException {
            if (PdrUtil.isEmpty(this.a)) {
                FileFeatureImpl.this.a(file.getAbsolutePath(), this.b, this.c);
            } else if (DHFile.copyFile(file.getPath(), this.a) != 1) {
                Deprecated_JSUtil.execCallback(this.b, this.c, DOMException.toJSON(13, "Failed to load resource"), JSUtil.ERROR, true, false);
            } else {
                FileFeatureImpl.this.a(this.a, this.b, this.c);
            }
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadCleared(Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.CustomTarget, com.bumptech.glide.request.target.Target
        public void onLoadFailed(Drawable drawable) {
            Deprecated_JSUtil.execCallback(this.b, this.c, DOMException.toJSON(13, "Failed to load resource"), JSUtil.ERROR, true, false);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Runnable {
        final /* synthetic */ File a;
        final /* synthetic */ String b;
        final /* synthetic */ IWebview c;
        final /* synthetic */ String d;

        d(File file, String str, IWebview iWebview, String str2) {
            this.a = file;
            this.b = str;
            this.c = iWebview;
            this.d = str2;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            String strMd5 = Md5Utils.md5(this.a, this.b);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(AbsoluteConst.JSON_KEY_SIZE, this.a.length());
                if (strMd5 != null) {
                    jSONObject.put(Constants.CodeCache.BANNER_DIGEST, strMd5.toUpperCase(Locale.US));
                }
            } catch (JSONException unused) {
            }
            Deprecated_JSUtil.execCallback(this.c, this.d, jSONObject.toString(), JSUtil.OK, true, false);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements ISysEventListener {
        final /* synthetic */ int a;
        final /* synthetic */ IApp b;
        final /* synthetic */ IWebview c;
        final /* synthetic */ String d;

        e(int i, IApp iApp, IWebview iWebview, String str) {
            this.a = i;
            this.b = iApp;
            this.c = iWebview;
            this.d = str;
        }

        @Override // io.dcloud.common.DHInterface.ISysEventListener
        public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) throws JSONException {
            Object[] objArr = (Object[]) obj;
            int iIntValue = ((Integer) objArr[0]).intValue();
            Intent intent = (Intent) objArr[2];
            ISysEventListener.SysEventType sysEventType2 = ISysEventListener.SysEventType.onActivityResult;
            if (sysEventType == sysEventType2 && iIntValue == this.a) {
                this.b.unregisterSysEventListener(this, sysEventType2);
                if (intent == null || (intent.getData() == null && intent.getClipData() == null)) {
                    Deprecated_JSUtil.execCallback(this.c, this.d, StringUtil.format(DOMException.JSON_ERROR_INFO, -2, DOMException.MSG_USER_CANCEL), JSUtil.ERROR, true, false);
                } else {
                    JSONArray jSONArray = new JSONArray();
                    ClipData clipData = intent.getClipData();
                    if (clipData != null) {
                        int itemCount = clipData.getItemCount();
                        for (int i = 0; i < itemCount; i++) {
                            jSONArray.put(FileUtil.getPathFromUri(this.b.getActivity(), clipData.getItemAt(i).getUri()));
                        }
                    } else {
                        Uri data = intent.getData();
                        String pathFromUri = FileUtil.getPathFromUri(this.b.getActivity(), data);
                        if (PdrUtil.isEmpty(pathFromUri)) {
                            pathFromUri = data.toString();
                        }
                        jSONArray.put(pathFromUri);
                    }
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("files", jSONArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Deprecated_JSUtil.execCallback(this.c, this.d, jSONObject.toString(), JSUtil.OK, true, false);
                }
            }
            return false;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class f implements AsyncTaskHandler.IAsyncTaskListener {
        final /* synthetic */ String[] a;
        final /* synthetic */ IWebview b;
        final /* synthetic */ String c;
        final /* synthetic */ int d;
        final /* synthetic */ String e;

        f(String[] strArr, IWebview iWebview, String str, int i, String str2) {
            this.a = strArr;
            this.b = iWebview;
            this.c = str;
            this.d = i;
            this.e = str2;
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onCancel() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteBegin() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteEnd(Object obj) {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public Object onExecuting() throws IOException {
            byte[] bArrDecode2bytes = Base64.decode2bytes(this.a[1]);
            if (bArrDecode2bytes == null) {
                Deprecated_JSUtil.execCallback(this.b, this.c, StringUtil.format(DOMException.JSON_ERROR_INFO, 16, this.b.getContext().getString(R.string.dcloud_io_write_non_base64)).toString(), JSUtil.ERROR, true, false);
                return null;
            }
            DHFile.writeFile(bArrDecode2bytes, this.d, this.e);
            JSUtil.execCallback(this.b, this.c, bArrDecode2bytes.length, JSUtil.OK, false);
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class g implements AsyncTaskHandler.IAsyncTaskListener {
        final /* synthetic */ String a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ IWebview d;
        final /* synthetic */ String e;

        g(String str, int i, int i2, IWebview iWebview, String str2) {
            this.a = str;
            this.b = i;
            this.c = i2;
            this.d = iWebview;
            this.e = str2;
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onCancel() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteBegin() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteEnd(Object obj) {
            if (obj != null) {
                Deprecated_JSUtil.execCallback(this.d, this.e, String.valueOf(obj), JSUtil.OK, true, false);
            } else {
                FileFeatureImpl.this.a(10, this.d, this.e);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:54:0x00ad A[Catch: IOException -> 0x00a9, TRY_LEAVE, TryCatch #0 {IOException -> 0x00a9, blocks: (B:50:0x00a5, B:54:0x00ad), top: B:58:0x00a5 }] */
        /* JADX WARN: Removed duplicated region for block: B:58:0x00a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:73:? A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r5v0 */
        /* JADX WARN: Type inference failed for: r5v1, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r5v2 */
        /* JADX WARN: Type inference failed for: r5v3 */
        /* JADX WARN: Type inference failed for: r5v4 */
        /* JADX WARN: Type inference failed for: r5v5, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r5v6, types: [java.io.InputStream] */
        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public java.lang.Object onExecuting() throws java.lang.Throwable {
            /*
                r15 = this;
                r1 = 1
                r2 = 0
                r3 = 2
                r4 = 0
                java.lang.String r0 = r15.a     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
                java.lang.Object r0 = io.dcloud.common.adapter.io.DHFile.createFileHandler(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
                java.io.InputStream r5 = io.dcloud.common.adapter.io.DHFile.getInputStream(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
                int r0 = r5.available()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                int r6 = r15.b     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                if (r6 <= 0) goto L2d
                if (r6 < r0) goto L1a
                int r6 = r0 + (-1)
            L1a:
                int r0 = r15.c     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                int r6 = r6 - r0
                int r6 = r6 + r1
                byte[] r7 = new byte[r6]     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                long r8 = (long) r0     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                r5.skip(r8)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                r5.read(r7, r2, r6)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                java.lang.String r0 = android.util.Base64.encodeToString(r7, r3)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                r6 = r4
                goto L46
            L2d:
                java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
                r6.<init>()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            L32:
                r0 = 204800(0x32000, float:2.86986E-40)
                byte[] r0 = new byte[r0]     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L5e
                int r7 = r5.read(r0)     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L5e
                r8 = -1
                if (r7 != r8) goto L58
                byte[] r0 = r6.toByteArray()     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L5e
                java.lang.String r0 = android.util.Base64.encodeToString(r0, r3)     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L5e
            L46:
                java.lang.String r4 = io.dcloud.common.util.JSONUtil.toJSONableString(r0)     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L9f
                r5.close()     // Catch: java.io.IOException -> L53
                if (r6 == 0) goto L9e
                r6.close()     // Catch: java.io.IOException -> L53
                goto L9e
            L53:
                r0 = move-exception
                r0.printStackTrace()
                goto L9e
            L58:
                r6.write(r0, r2, r7)     // Catch: java.lang.Throwable -> L5c java.lang.Exception -> L5e
                goto L32
            L5c:
                r0 = move-exception
                goto La2
            L5e:
                r0 = move-exception
                goto L6c
            L60:
                r0 = move-exception
                r6 = r4
                goto La0
            L63:
                r0 = move-exception
                r6 = r4
                goto L6c
            L66:
                r0 = move-exception
                r6 = r4
                goto La1
            L69:
                r0 = move-exception
                r5 = r4
                r6 = r5
            L6c:
                java.lang.String r7 = "{code:%d,message:'%s'}"
                r8 = 13
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L9f
                java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L9f
                java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L9f
                r3[r2] = r8     // Catch: java.lang.Throwable -> L9f
                r3[r1] = r0     // Catch: java.lang.Throwable -> L9f
                java.lang.String r11 = io.dcloud.common.util.StringUtil.format(r7, r3)     // Catch: java.lang.Throwable -> L9f
                io.dcloud.common.DHInterface.IWebview r9 = r15.d     // Catch: java.lang.Throwable -> L9f
                java.lang.String r10 = r15.e     // Catch: java.lang.Throwable -> L9f
                int r12 = io.dcloud.common.util.JSUtil.ERROR     // Catch: java.lang.Throwable -> L9f
                r13 = 1
                r14 = 0
                io.dcloud.common.util.JSUtil.execCallback(r9, r10, r11, r12, r13, r14)     // Catch: java.lang.Throwable -> L9f
                if (r5 == 0) goto L95
                r5.close()     // Catch: java.io.IOException -> L93
                goto L95
            L93:
                r0 = move-exception
                goto L9b
            L95:
                if (r6 == 0) goto L9e
                r6.close()     // Catch: java.io.IOException -> L93
                goto L9e
            L9b:
                r0.printStackTrace()
            L9e:
                return r4
            L9f:
                r0 = move-exception
            La0:
                r4 = r5
            La1:
                r5 = r4
            La2:
                r1 = r0
                if (r5 == 0) goto Lab
                r5.close()     // Catch: java.io.IOException -> La9
                goto Lab
            La9:
                r0 = move-exception
                goto Lb1
            Lab:
                if (r6 == 0) goto Lb4
                r6.close()     // Catch: java.io.IOException -> La9
                goto Lb4
            Lb1:
                r0.printStackTrace()
            Lb4:
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.js.file.FileFeatureImpl.g.onExecuting():java.lang.Object");
        }
    }

    private void b(IWebview iWebview, String[] strArr, String str) {
        try {
            String str2 = strArr[0];
            if (!FileUtil.checkPathAccord(iWebview.getContext(), str2)) {
                a(15, iWebview, str);
                return;
            }
            boolean zCheckPrivateDir = iWebview.obtainApp().checkPrivateDir(str2);
            int iIntValue = Integer.valueOf(strArr[2]).intValue();
            try {
                if (strArr[1] != null && !zCheckPrivateDir) {
                    AsyncTaskHandler.executeThreadTask(new f(strArr, iWebview, str, iIntValue, str2));
                    return;
                }
                a(4, iWebview, str);
            } catch (Exception unused) {
                a(10, iWebview, str);
            }
        } catch (Exception unused2) {
        }
    }

    private String c(String str) {
        if (str.startsWith(a)) {
            return AbsoluteConst.MINI_SERVER_APP_WWW + str.substring(a.length(), str.length());
        }
        if (str.startsWith(c)) {
            return AbsoluteConst.MINI_SERVER_APP_DOC + str.substring(c.length(), str.length());
        }
        if (str.startsWith(d)) {
            return "_documents/" + str.substring(d.length(), str.length());
        }
        if (!str.startsWith(e)) {
            return null;
        }
        return "_downloads/" + str.substring(e.length(), str.length());
    }

    private String d(String str) {
        boolean z;
        String strB = b(str);
        if (PdrUtil.isEmpty(strB)) {
            strB = String.valueOf(-1);
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (str.startsWith(a)) {
                return String.valueOf(1);
            }
            if (str.startsWith(c)) {
                return String.valueOf(2);
            }
            if (str.startsWith(d)) {
                return String.valueOf(3);
            }
            if (str.startsWith(e)) {
                return String.valueOf(4);
            }
        }
        return strB;
    }

    private boolean e(String str) {
        return str.endsWith(BaseInfo.REL_PRIVATE_WWW_DIR) || str.endsWith(BaseInfo.REL_PUBLIC_DOCUMENTS_DIR) || str.endsWith(BaseInfo.REL_PUBLIC_DOWNLOADS_DIR) || str.endsWith(BaseInfo.REL_PRIVATE_DOC_DIR) || str.endsWith(AbsoluteConst.MINI_SERVER_APP_WWW) || str.endsWith("_documents/") || str.endsWith("_downloads/") || str.endsWith(AbsoluteConst.MINI_SERVER_APP_DOC);
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x089e  */
    /* JADX WARN: Removed duplicated region for block: B:453:0x08a5  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x005b  */
    @Override // io.dcloud.common.DHInterface.IFeature
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String execute(final io.dcloud.common.DHInterface.IWebview r24, java.lang.String r25, java.lang.String[] r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 3514
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.js.file.FileFeatureImpl.execute(io.dcloud.common.DHInterface.IWebview, java.lang.String, java.lang.String[]):java.lang.String");
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(IWebview iWebview, String str, String str2, int i, String str3, String str4) throws IOException {
        IWebview iWebview2;
        InputStream inputStreamOpenInputStream = null;
        try {
            try {
                inputStreamOpenInputStream = iWebview.getActivity().getContentResolver().openInputStream(Uri.parse(str));
                String strMd5 = Md5Utils.md5(inputStreamOpenInputStream, str2);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(AbsoluteConst.JSON_KEY_SIZE, i);
                    if (strMd5 != null) {
                        jSONObject.put(Constants.CodeCache.BANNER_DIGEST, strMd5.toUpperCase(Locale.US));
                    }
                } catch (JSONException unused) {
                }
                iWebview2 = iWebview;
                try {
                    Deprecated_JSUtil.execCallback(iWebview2, str3, jSONObject.toString(), JSUtil.OK, true, false);
                    if (inputStreamOpenInputStream == null) {
                        return;
                    }
                } catch (FileNotFoundException unused2) {
                    a(15, iWebview2, str4);
                    if (inputStreamOpenInputStream == null) {
                        return;
                    }
                    inputStreamOpenInputStream.close();
                }
            } catch (FileNotFoundException unused3) {
                iWebview2 = iWebview;
            }
            try {
                inputStreamOpenInputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                    throw th;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    throw th;
                }
            }
            throw th;
        }
    }

    private void a(IWebview iWebview, IApp iApp, String[] strArr, String str) {
        String str2 = strArr[0];
        if (!FileUtil.checkPathAccord(iWebview.getContext(), str2)) {
            a(15, iWebview, str);
            return;
        }
        boolean zCheckPrivateDir = iApp.checkPrivateDir(str2);
        int i = PdrUtil.parseInt(strArr[1], 0);
        int i2 = PdrUtil.parseInt(strArr[2], -1);
        if (zCheckPrivateDir && iApp.isOnAppRunningMode()) {
            a(10, iWebview, str);
        } else {
            AsyncTaskHandler.executeThreadTask(new g(str2, i2, i, iWebview, str));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x025b A[Catch: Exception -> 0x02a4, TryCatch #9 {Exception -> 0x02a4, blocks: (B:29:0x00bb, B:31:0x00ca, B:38:0x00f1, B:40:0x0118, B:43:0x0120, B:48:0x012f, B:55:0x0165, B:58:0x016d, B:66:0x018d, B:72:0x01a8, B:75:0x01b6, B:77:0x01c5, B:81:0x01db, B:83:0x01e1, B:78:0x01c9, B:80:0x01d3, B:91:0x0218, B:108:0x024a, B:110:0x025b, B:111:0x0260, B:117:0x0294, B:98:0x022b, B:101:0x0234, B:104:0x023f, B:70:0x0196, B:68:0x0192, B:69:0x0195, B:71:0x019b, B:32:0x00ce, B:34:0x00d4, B:35:0x00e0, B:37:0x00ea, B:60:0x017f, B:65:0x018a), top: B:150:0x00bb, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x026c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void a(java.lang.String r18, java.lang.String r19, io.dcloud.common.DHInterface.IWebview r20, java.lang.String r21) throws org.json.JSONException, java.io.IOException, java.lang.SecurityException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 717
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.js.file.FileFeatureImpl.a(java.lang.String, java.lang.String, io.dcloud.common.DHInterface.IWebview, java.lang.String):void");
    }

    public static String b(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (fileExtensionFromUrl != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        return null;
    }

    private JSONObject a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (str.startsWith(a)) {
            jSONObject.put("type", 1);
            jSONObject.put("fsName", "PRIVATE_WWW");
            jSONObject.put("fsRoot", io.dcloud.js.file.a.a("PRIVATE_WWW", a, c(str), true));
            return jSONObject;
        }
        if (str.startsWith(c)) {
            jSONObject.put("type", 2);
            jSONObject.put("fsName", "PRIVATE_DOCUMENTS");
            jSONObject.put("fsRoot", io.dcloud.js.file.a.a("PRIVATE_DOCUMENTS", c, c(str), true));
            return jSONObject;
        }
        if (str.startsWith(d)) {
            jSONObject.put("type", 3);
            jSONObject.put("fsName", "PUBLIC_DOCUMENTS");
            jSONObject.put("fsRoot", io.dcloud.js.file.a.a("PUBLIC_DOCUMENTS", d, c(str), true));
            return jSONObject;
        }
        if (str.startsWith(e)) {
            jSONObject.put("type", 4);
            jSONObject.put("fsName", "PUBLIC_DOWNLOADS");
            jSONObject.put("fsRoot", io.dcloud.js.file.a.a("PUBLIC_DOWNLOADS", e, c(str), true));
            return jSONObject;
        }
        if (str.startsWith(b)) {
            jSONObject.put("type", 1);
            jSONObject.put("fsName", "PRIVATE_WWW");
            jSONObject.put("fsRoot", io.dcloud.js.file.a.a("PRIVATE_WWW", b, c(str), true));
            return jSONObject;
        }
        if (PdrUtil.isDeviceRootDir(str)) {
            jSONObject.put("type", 5);
            jSONObject.put("fsName", "PUBLIC_DEVICE_ROOT");
            jSONObject.put("fsRoot", io.dcloud.js.file.a.a("PUBLIC_DEVICE_ROOT", DeviceInfo.sDeviceRootDir, c(str), true));
        }
        return jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, IWebview iWebview, String str) {
        Deprecated_JSUtil.execCallback(iWebview, str, a(iWebview.getContext(), i), JSUtil.ERROR, true, false);
    }

    private String a(Context context, int i) {
        switch (i) {
            case 1:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_file_not_found));
            case 2:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_without_authorization));
            case 3:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_common_cancel));
            case 4:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_file_not_read));
            case 5:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_coding_error));
            case 6:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_no_modification_allowed));
            case 7:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_invalid_state));
            case 8:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_grammar_mistakes));
            case 9:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_invalid_modification));
            case 10:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_perform_error));
            case 11:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_type_mismatch));
            case 12:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_path_exists));
            case 13:
            default:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_unknown_error));
            case 14:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), context.getString(R.string.dcloud_io_path_not_exist));
            case 15:
                return StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(i), DOMException.MSG_PATH_NOT_PRIVATE_ERROR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0049 A[Catch: IOException -> 0x0116, JSONException -> 0x011a, TRY_ENTER, TryCatch #3 {IOException -> 0x0116, blocks: (B:4:0x000d, B:17:0x0055, B:22:0x006f, B:53:0x00d5, B:57:0x0109, B:56:0x0103, B:16:0x0049), top: B:76:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(java.lang.String r14, io.dcloud.common.DHInterface.IWebview r15, java.lang.String r16) throws org.json.JSONException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 366
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.js.file.FileFeatureImpl.a(java.lang.String, io.dcloud.common.DHInterface.IWebview, java.lang.String):void");
    }

    private String[] a(IWebview iWebview, String[] strArr, String str) {
        if (strArr.length > 1 && !PdrUtil.isEmpty(strArr[1])) {
            try {
                return JSUtil.jsonArrayToStringArr(new JSONArray(strArr[1]));
            } catch (JSONException e2) {
                e2.printStackTrace();
                a(8, iWebview, str);
            }
        }
        return null;
    }
}

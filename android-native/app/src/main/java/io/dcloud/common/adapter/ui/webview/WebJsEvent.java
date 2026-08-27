package io.dcloud.common.adapter.ui.webview;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.core.content.FileProvider;
import com.facebook.common.util.UriUtil;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IActivityDelegate;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.ITitleNView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaWebview;
import io.dcloud.common.adapter.ui.FileChooseDialog;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.CanvasHelper;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.AppConsoleLogUtil;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TitleNViewUtil;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class WebJsEvent extends WebChromeClient {
    static final FrameLayout.LayoutParams COVER_SCREEN_GRAVITY_CENTER = new FrameLayout.LayoutParams(-1, -1, 17);
    public static final int FILECHOOSER_RESULTCODE = 1;
    static final String TAG = "webview";
    FileChooseDialog dialog;
    AdaWebview mAdaWebview;
    View mCustomView;
    WebChromeClient.CustomViewCallback mCustomViewCallback;
    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mUploadMessage21Level;
    private boolean rptJsErr;
    private boolean mScreemOrientationChanged = false;
    private boolean mDefaultTitleBarVisibility = false;
    private int mDefaultScreemOrientation = -2;
    DialogListener mListener = null;
    private Bitmap mDefaultVideoPoster = null;
    private boolean isNeedCloseScreenWakelock = false;
    private int defaultSystemUI = 0;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class DialogListener implements DialogInterface.OnClickListener {
        JsResult mResult = null;

        DialogListener() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            this.mResult.cancel();
        }
    }

    public WebJsEvent(AdaWebview adaWebview) {
        this.rptJsErr = true;
        this.mAdaWebview = adaWebview;
        adaWebview.mProgressIntValue = 0;
        this.rptJsErr = BaseInfo.getCmitInfo(BaseInfo.sLastRunApp).rptJse;
    }

    private void handleConsoleMessage(ConsoleMessage consoleMessage) {
        if (this.mAdaWebview == null) {
            return;
        }
        String strMessage = consoleMessage.message();
        if (isFilterConsoleMessage(strMessage)) {
            return;
        }
        int iLineNumber = consoleMessage.lineNumber();
        String strSourceId = consoleMessage.sourceId();
        String strName = consoleMessage.messageLevel().name();
        if (PdrUtil.isEmpty(strSourceId)) {
            AppConsoleLogUtil.DCLog(strMessage, strName);
            return;
        }
        try {
            strSourceId = this.mAdaWebview.getDCWebView().convertRelPath(strSourceId);
        } catch (Exception unused) {
        }
        AppConsoleLogUtil.DCLog(strMessage + " at " + strSourceId + ":" + iLineNumber, strName);
    }

    private void handleMessage(JsPromptResult jsPromptResult, AdaWebview adaWebview, String str, String str2, String str3, boolean z) {
        jsPromptResult.confirm(this.mAdaWebview.execScript(str, str2, JSONUtil.createJSONArray(str3), z));
    }

    private void initUniLoadUrl() {
        if (this.mAdaWebview.isDisposed() || this.mAdaWebview.obtainApp() == null || !BaseInfo.isUniAppAppid(this.mAdaWebview.obtainApp())) {
            return;
        }
        BaseInfo.isWeexUniJs(this.mAdaWebview.obtainApp());
    }

    private boolean isCallbackId(String str) {
        return str != null && str.startsWith(IApp.ConfigProperty.CONFIG_PLUS);
    }

    private boolean isFilterConsoleMessage(String str) {
        if (PdrUtil.isEmpty(str)) {
            return false;
        }
        return str.contains("viewport-fit") || str.contains("Ignored attempt to cancel a touchend event with cancelable=false");
    }

    private boolean isUrlWhiteListed(String str) {
        return true;
    }

    private void openFileChooserLogic(ValueCallback<Uri> valueCallback, String str, String str2) {
        openFileChooserLogic(valueCallback, null, str, str2, null);
    }

    private void setStatusBarVisibility(Activity activity, boolean z) {
        if (z) {
            activity.getWindow().getDecorView().setSystemUiVisibility(this.defaultSystemUI);
        } else {
            this.defaultSystemUI = activity.getWindow().getDecorView().getSystemUiVisibility();
            activity.getWindow().getDecorView().setSystemUiVisibility(5894);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOpenFileChooser(ValueCallback<Uri> valueCallback, ValueCallback<Uri[]> valueCallback2, String str, String str2, WebChromeClient.FileChooserParams fileChooserParams) {
        this.mUploadMessage = valueCallback;
        this.mUploadMessage21Level = valueCallback2;
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        if (PdrUtil.isEmpty(str)) {
            intent.setType("*/*");
        } else {
            intent.setType(str);
        }
        if (fileChooserParams != null && fileChooserParams.getMode() == 1) {
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        }
        this.dialog = new FileChooseDialog(this.mAdaWebview.getActivity(), this.mAdaWebview.getActivity(), intent);
        this.mAdaWebview.obtainFrameView().obtainApp().registerSysEventListener(new ISysEventListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.10
            /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            private android.net.Uri getUri(android.net.Uri r8) throws java.lang.IllegalArgumentException {
                /*
                    r7 = this;
                    io.dcloud.common.adapter.ui.webview.WebJsEvent r0 = io.dcloud.common.adapter.ui.webview.WebJsEvent.this
                    io.dcloud.common.adapter.ui.AdaWebview r0 = r0.mAdaWebview
                    android.content.Context r0 = r0.getContext()
                    android.content.ContentResolver r1 = r0.getContentResolver()
                    java.lang.String r0 = "_data"
                    java.lang.String[] r3 = new java.lang.String[]{r0}
                    r5 = 0
                    r6 = 0
                    r4 = 0
                    r2 = r8
                    android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)
                    if (r8 == 0) goto L68
                    int r1 = android.os.Build.VERSION.SDK_INT
                    r3 = 29
                    if (r1 >= r3) goto L63
                    boolean r1 = r8.moveToFirst()
                    if (r1 == 0) goto L63
                    int r0 = r8.getColumnIndexOrThrow(r0)     // Catch: java.lang.Exception -> L63
                    r1 = -1
                    if (r0 <= r1) goto L63
                    java.lang.String r0 = r8.getString(r0)     // Catch: java.lang.Exception -> L63
                    android.net.Uri r1 = android.net.Uri.parse(r0)     // Catch: java.lang.Exception -> L63
                    java.lang.String r2 = r1.getScheme()     // Catch: java.lang.Exception -> L64
                    boolean r2 = io.dcloud.common.util.PdrUtil.isEmpty(r2)     // Catch: java.lang.Exception -> L64
                    if (r2 == 0) goto L64
                    java.lang.String r2 = "/"
                    boolean r2 = r0.startsWith(r2)     // Catch: java.lang.Exception -> L64
                    if (r2 == 0) goto L4c
                    java.lang.String r2 = "file://"
                    goto L4e
                L4c:
                    java.lang.String r2 = "file:///"
                L4e:
                    java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L64
                    r3.<init>()     // Catch: java.lang.Exception -> L64
                    r3.append(r2)     // Catch: java.lang.Exception -> L64
                    r3.append(r0)     // Catch: java.lang.Exception -> L64
                    java.lang.String r0 = r3.toString()     // Catch: java.lang.Exception -> L64
                    android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch: java.lang.Exception -> L64
                    r1 = r0
                    goto L64
                L63:
                    r1 = r2
                L64:
                    r8.close()
                    return r1
                L68:
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.ui.webview.WebJsEvent.AnonymousClass10.getUri(android.net.Uri):android.net.Uri");
            }

            @Override // io.dcloud.common.DHInterface.ISysEventListener
            public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) throws IllegalArgumentException {
                List<File> list;
                Object[] objArr = (Object[]) obj;
                int iIntValue = ((Integer) objArr[0]).intValue();
                int iIntValue2 = ((Integer) objArr[1]).intValue();
                FileChooseDialog fileChooseDialog = WebJsEvent.this.dialog;
                if (fileChooseDialog != null) {
                    fileChooseDialog.dismiss();
                }
                IApp iAppObtainApp = WebJsEvent.this.mAdaWebview.obtainFrameView().obtainApp();
                ISysEventListener.SysEventType sysEventType2 = ISysEventListener.SysEventType.onActivityResult;
                iAppObtainApp.unregisterSysEventListener(this, sysEventType2);
                if (iIntValue2 == 0 || sysEventType != sysEventType2) {
                    WebJsEvent webJsEvent = WebJsEvent.this;
                    ValueCallback<Uri[]> valueCallback3 = webJsEvent.mUploadMessage21Level;
                    if (valueCallback3 != null) {
                        valueCallback3.onReceiveValue(null);
                    } else {
                        ValueCallback<Uri> valueCallback4 = webJsEvent.mUploadMessage;
                        if (valueCallback4 != null) {
                            valueCallback4.onReceiveValue(null);
                        }
                    }
                    return false;
                }
                Intent intent2 = (Intent) objArr[2];
                ArrayList arrayList = new ArrayList();
                if (iIntValue == 1) {
                    if (intent2.getClipData() != null) {
                        ClipData clipData = intent2.getClipData();
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            if (uri != null && UriUtil.LOCAL_CONTENT_SCHEME.equals(uri.getScheme())) {
                                arrayList.add(getUri(uri));
                            }
                        }
                    } else {
                        Uri data = intent2.getData();
                        if (data != null && UriUtil.LOCAL_CONTENT_SCHEME.equals(data.getScheme())) {
                            data = getUri(data);
                        }
                        arrayList.add(data);
                        Logger.i(WebJsEvent.TAG, "openFileChooserLogic  OnActivityResult url=" + data);
                    }
                } else if (iIntValue == 2 && (list = WebJsEvent.this.dialog.uris) != null) {
                    Iterator<File> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        File next = it.next();
                        if (next.exists()) {
                            arrayList.add(FileProvider.getUriForFile(WebJsEvent.this.mAdaWebview.getActivity(), WebJsEvent.this.mAdaWebview.getActivity().getPackageName() + ".dc.fileprovider", next));
                            break;
                        }
                    }
                }
                Uri[] uriArr = arrayList.size() > 0 ? (Uri[]) arrayList.toArray(new Uri[arrayList.size()]) : null;
                WebJsEvent webJsEvent2 = WebJsEvent.this;
                ValueCallback<Uri[]> valueCallback5 = webJsEvent2.mUploadMessage21Level;
                if (valueCallback5 != null) {
                    valueCallback5.onReceiveValue(uriArr);
                } else {
                    ValueCallback<Uri> valueCallback6 = webJsEvent2.mUploadMessage;
                    if (valueCallback6 != null) {
                        valueCallback6.onReceiveValue(arrayList.isEmpty() ? null : (Uri) arrayList.get(0));
                    }
                }
                return true;
            }
        }, ISysEventListener.SysEventType.onActivityResult);
        try {
            this.dialog.show();
            this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.11
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    try {
                        WebJsEvent webJsEvent = WebJsEvent.this;
                        ValueCallback<Uri[]> valueCallback3 = webJsEvent.mUploadMessage21Level;
                        if (valueCallback3 != null) {
                            valueCallback3.onReceiveValue(null);
                            return;
                        }
                        ValueCallback<Uri> valueCallback4 = webJsEvent.mUploadMessage;
                        if (valueCallback4 != null) {
                            valueCallback4.onReceiveValue(null);
                        }
                    } catch (Exception unused) {
                    }
                }
            });
        } catch (Exception unused) {
            Logger.e("openFileChooserLogic Exception");
        }
    }

    private void updateTitleNViewTitle(String str) {
        JSONObject jSONObject = this.mAdaWebview.mFrameView.obtainFrameOptions().titleNView;
        if (jSONObject != null) {
            try {
                Object obj = jSONObject.has("titleText") ? jSONObject.get("titleText") : jSONObject.has("titletext") ? jSONObject.get("titletext") : null;
                if (obj == null || !(obj instanceof String)) {
                    String strOptString = jSONObject.optString("titleColor");
                    if (TextUtils.isEmpty(strOptString)) {
                        strOptString = jSONObject.optString("titlecolor");
                    }
                    String strOptString2 = jSONObject.optString("titleSize");
                    if (TextUtils.isEmpty(strOptString2)) {
                        strOptString2 = jSONObject.optString("titlesize");
                    }
                    String str2 = strOptString2;
                    String strOptString3 = jSONObject.optString("titleOverflow");
                    String strOptString4 = jSONObject.optString("titleAlign");
                    String strOptString5 = jSONObject.optString("titleIcon");
                    String strOptString6 = jSONObject.optString("titleIconRadius");
                    String strOptString7 = jSONObject.optString("subtitleText");
                    String strOptString8 = jSONObject.optString("subtitleColor");
                    String strOptString9 = jSONObject.optString("subtitleSize");
                    String strOptString10 = jSONObject.optString("subtitleOverflow");
                    String strOptString11 = jSONObject.optString("titleIconWidth");
                    if (TextUtils.isEmpty(str) || TextUtils.isEmpty(strOptString) || TextUtils.isEmpty(str2)) {
                        return;
                    }
                    AbsMgr absMgrObtainWindowMgr = this.mAdaWebview.mFrameView.obtainWindowMgr();
                    IWebview iWebviewObtainWebView = this.mAdaWebview.mFrameView.obtainWebView();
                    AdaWebview adaWebview = this.mAdaWebview;
                    Object titleNView = TitleNViewUtil.getTitleNView(absMgrObtainWindowMgr, iWebviewObtainWebView, adaWebview.mFrameView, TitleNViewUtil.getTitleNViewId(adaWebview.obtainFrameView()));
                    if (titleNView instanceof ITitleNView) {
                        ITitleNView iTitleNView = (ITitleNView) titleNView;
                        if ("transparent".equals(jSONObject.optString("type")) && !TextUtils.isEmpty(strOptString) && Color.alpha(iTitleNView.getTitleColor()) == 0) {
                            strOptString = TitleNViewUtil.changeColorAlpha(strOptString, 0.0f);
                        }
                        TitleNViewUtil.drawTitle(this.mAdaWebview.mFrameView, iTitleNView, str, strOptString, str2, strOptString3, strOptString4, strOptString5, strOptString6, strOptString7, strOptString8, strOptString9, strOptString10, strOptString11);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy() {
        this.mAdaWebview = null;
    }

    @Override // android.webkit.WebChromeClient
    public Bitmap getDefaultVideoPoster() {
        return super.getDefaultVideoPoster();
    }

    public void hideCustomView() {
        Log.d(TAG, "Hidding Custom View");
        if (this.mCustomView == null) {
            return;
        }
        if (this.mAdaWebview.obtainMainView() != null) {
            this.mAdaWebview.obtainMainView().setVisibility(0);
        }
        this.mCustomView.setVisibility(8);
        ((ViewGroup) this.mCustomView.getParent()).removeView(this.mCustomView);
        this.mCustomView = null;
        this.mCustomViewCallback.onCustomViewHidden();
        Activity activity = this.mAdaWebview.obtainApp() != null ? this.mAdaWebview.obtainApp().getActivity() : null;
        if (activity != null) {
            if (this.isNeedCloseScreenWakelock && this.mAdaWebview.obtainFrameView() != null && this.mAdaWebview.obtainFrameView().obtainWindowMgr() != null) {
                this.mAdaWebview.obtainFrameView().obtainWindowMgr().processEvent(IMgr.MgrType.FeatureMgr, 1, new Object[]{this.mAdaWebview, "device", "setWakelock", new JSONArray().put(false)});
            }
            IApp iAppObtainApp = this.mAdaWebview.obtainApp();
            if (iAppObtainApp != null && iAppObtainApp.obtainStatusBarMgr() != null) {
                if (!iAppObtainApp.obtainStatusBarMgr().isFullScreenOrImmersive() || !iAppObtainApp.obtainStatusBarMgr().isFullScreen) {
                    setStatusBarVisibility(activity, true);
                }
                if (iAppObtainApp.obtainStatusBarMgr().isImmersive) {
                    iAppObtainApp.obtainStatusBarMgr().setImmersive(activity, true);
                }
            }
            if (this.mScreemOrientationChanged) {
                this.mScreemOrientationChanged = false;
                AdaWebview.ScreemOrientationChangedNeedLayout = true;
                activity.setRequestedOrientation(this.mDefaultScreemOrientation);
            }
            IActivityHandler iActivityHandler = DCloudAdapterUtil.getIActivityHandler(activity);
            if (iActivityHandler != null) {
                iActivityHandler.setSideBarVisibility(0);
            }
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        handleConsoleMessage(consoleMessage);
        return true;
    }

    @Override // android.webkit.WebChromeClient
    public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
        Logger.i(TAG, "onExceededDatabaseQuota url=" + str);
        quotaUpdater.updateQuota(j2 * 2);
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsHidePrompt() {
        Logger.i(TAG, "onGeolocationPermissionsHidePrompt");
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsShowPrompt(final String str, final GeolocationPermissions.Callback callback) {
        if (this.mAdaWebview == null) {
            return;
        }
        Logger.i(TAG, "onGeolocationPermissionsShowPrompt origin=" + str);
        IApp iAppObtainApp = this.mAdaWebview.obtainFrameView().obtainApp();
        if (iAppObtainApp != null) {
            PermissionUtil.usePermission(this.mAdaWebview.getActivity(), "base", PermissionUtil.PMS_LOCATION, 2, new PermissionUtil.StreamPermissionRequest(iAppObtainApp) { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.12
                @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
                public void onDenied(String str2) {
                    callback.invoke(str, false, false);
                }

                @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
                public void onGranted(String str2) {
                    callback.invoke(str, true, false);
                }
            });
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        hideCustomView();
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null || PdrUtil.isEmpty(adaWebview.getAppName())) {
            return super.onJsAlert(webView, str, str2, jsResult);
        }
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mAdaWebview.getActivity()).create();
        alertDialogCreate.setTitle(this.mAdaWebview.getAppName());
        alertDialogCreate.setMessage(str2);
        if (this.mListener == null) {
            this.mListener = new DialogListener();
        }
        this.mListener.mResult = jsResult;
        alertDialogCreate.setButton(AndroidResources.getString(R.string.ok), this.mListener);
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.4
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 1 || i != 4) {
                    return false;
                }
                jsResult.cancel();
                alertDialogCreate.dismiss();
                return true;
            }
        });
        alertDialogCreate.show();
        return true;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        return super.onJsBeforeUnload(webView, str, str2, jsResult);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null || PdrUtil.isEmpty(adaWebview.getAppName())) {
            return super.onJsConfirm(webView, str, str2, jsResult);
        }
        try {
            final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mAdaWebview.getActivity()).create();
            alertDialogCreate.setMessage(str2);
            alertDialogCreate.setTitle(this.mAdaWebview.getAppName());
            alertDialogCreate.setButton(AndroidResources.getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    jsResult.confirm();
                }
            });
            alertDialogCreate.setButton2(AndroidResources.getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    jsResult.cancel();
                }
            });
            alertDialogCreate.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.7
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    jsResult.cancel();
                }
            });
            alertDialogCreate.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.8
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (keyEvent.getAction() != 0 || i != 4) {
                        return false;
                    }
                    jsResult.cancel();
                    alertDialogCreate.dismiss();
                    return true;
                }
            });
            alertDialogCreate.show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return super.onJsConfirm(webView, str, str2, jsResult);
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, final JsPromptResult jsPromptResult) throws JSONException {
        String str4;
        CharSequence charSequence;
        boolean z;
        if (this.mAdaWebview == null) {
            return false;
        }
        boolean zIsUrlWhiteListed = isUrlWhiteListed(str);
        if (zIsUrlWhiteListed && str3 != null && str3.length() > 3 && str3.substring(0, 4).equals("pdr:")) {
            try {
                JSONArray jSONArray = new JSONArray(str3.substring(4));
                try {
                    charSequence = "\\\"";
                    z = true;
                    z = true;
                    str4 = str2;
                    try {
                        handleMessage(jsPromptResult, this.mAdaWebview, jSONArray.getString(0), jSONArray.getString(1), str4, jSONArray.getBoolean(2));
                    } catch (JSONException e) {
                        e = e;
                        if (PdrUtil.isEquals(str3, str3.replace(charSequence, JSUtil.QUOTE))) {
                            e.printStackTrace();
                            Logger.e(TAG, "onJsPrompt js->native message=" + str4 + ";defaultValue=" + str3);
                        } else {
                            String strReplace = str4.replace(charSequence, JSUtil.QUOTE);
                            String strSubstring = strReplace.substring(z ? 1 : 0, strReplace.length() - (z ? 1 : 0));
                            String strSubstring2 = str3.replace(charSequence, JSUtil.QUOTE).substring(4);
                            onJsPrompt(webView, str, strSubstring, "pdr:" + strSubstring2.substring(z ? 1 : 0, strSubstring2.length() - (z ? 1 : 0)), jsPromptResult);
                        }
                        return z;
                    }
                } catch (JSONException e2) {
                    e = e2;
                    charSequence = "\\\"";
                    z = true;
                    str4 = str2;
                }
            } catch (JSONException e3) {
                e = e3;
                str4 = str2;
                charSequence = "\\\"";
                z = true;
            }
            return z;
        }
        if (this.mAdaWebview.mReceiveJSValue_android42 == null || !zIsUrlWhiteListed || str3 == null || str3.length() <= 5 || !str3.substring(0, 5).equals("sync:")) {
            final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mAdaWebview.getActivity()).create();
            alertDialogCreate.setMessage(str2);
            alertDialogCreate.setTitle(this.mAdaWebview.getAppName());
            final EditText editText = new EditText(this.mAdaWebview.getActivity());
            if (str3 != null) {
                editText.setText(str3);
                editText.setSelection(0, str3.length());
                DeviceInfo.showIME(editText);
            }
            alertDialogCreate.setView(editText);
            alertDialogCreate.setButton(AndroidResources.getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    jsPromptResult.confirm(editText.getText().toString());
                }
            });
            alertDialogCreate.setButton2(AndroidResources.getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    jsPromptResult.cancel();
                }
            });
            alertDialogCreate.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.3
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (AndroidResources.sIMEAlive || keyEvent.getAction() != 0 || i != 4) {
                        return false;
                    }
                    alertDialogCreate.dismiss();
                    jsPromptResult.cancel();
                    return true;
                }
            });
            alertDialogCreate.show();
            return true;
        }
        try {
            JSONArray jSONArray2 = new JSONArray(str3.substring(5));
            jsPromptResult.confirm(this.mAdaWebview.mReceiveJSValue_android42.__js__call__native__(jSONArray2.getString(0), jSONArray2.getString(1)));
        } catch (JSONException e4) {
            if (PdrUtil.isEquals(str3, str3.replace("\\\"", JSUtil.QUOTE))) {
                e4.printStackTrace();
                Logger.e(TAG, "onJsPrompt js->native message=" + str2 + ";defaultValue=" + str3);
            } else {
                String strReplace2 = str2.replace("\\\"", JSUtil.QUOTE);
                String strSubstring3 = strReplace2.substring(1, strReplace2.length() - 1);
                String strSubstring4 = str3.replace("\\\"", JSUtil.QUOTE).substring(4);
                onJsPrompt(webView, str, strSubstring3, "pdr:" + strSubstring4.substring(1, strSubstring4.length() - 1), jsPromptResult);
            }
        }
        return true;
    }

    @Override // android.webkit.WebChromeClient
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        permissionRequest.grant(permissionRequest.getResources());
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView webView, int i) throws NumberFormatException {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null) {
            return;
        }
        if (i < 20 && !adaWebview.unReceiveTitle) {
            adaWebview.unReceiveTitle = true;
        }
        adaWebview.mProgressIntValue = i;
        adaWebview.dispatchWebviewStateEvent(3, Integer.valueOf(i));
        this.mAdaWebview.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_PROGRESS_CHANGED, Integer.valueOf(i));
        super.onProgressChanged(webView, i);
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(WebView webView, String str) throws NumberFormatException {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null) {
            return;
        }
        adaWebview.unReceiveTitle = false;
        adaWebview.dispatchWebviewStateEvent(4, str);
        this.mAdaWebview.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_TITLE_UPDATE, str);
        this.mAdaWebview.getDCWebView().setPageTitle(str);
        Logger.i(TAG, "onReceivedTitle title=" + str);
        this.mAdaWebview.mLoadCompleted = true;
        updateTitleNViewTitle(str);
        initUniLoadUrl();
        super.onReceivedTitle(webView, str);
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTouchIconUrl(WebView webView, String str, boolean z) {
        Logger.d("super.onReceivedTouchIconUrl(view, url, precomposed");
        super.onReceivedTouchIconUrl(webView, str, z);
    }

    @Override // android.webkit.WebChromeClient
    public void onRequestFocus(WebView webView) {
        Logger.i(TAG, "onRequestFocus");
        super.onRequestFocus(webView);
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        showCustomView(view, customViewCallback);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        openFileChooserLogic(null, valueCallback, fileChooserParams.getAcceptTypes() != null ? fileChooserParams.getAcceptTypes()[0] : null, "", fileChooserParams);
        return true;
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback) {
        openFileChooserLogic(valueCallback, null, null);
    }

    public void releaseDefaultVideoPoster() {
        Bitmap bitmap = this.mDefaultVideoPoster;
        if (bitmap != null) {
            bitmap.recycle();
            this.mDefaultVideoPoster = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void showCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        View childAt;
        Log.d(TAG, "showing Custom View");
        if (this.mCustomView != null) {
            customViewCallback.onCustomViewHidden();
            return;
        }
        view.setBackgroundDrawable(CanvasHelper.getDrawable());
        if (DeviceInfo.sModel.equals("HUAWEI MT1-U06") || DeviceInfo.sModel.equals("SM-T310") || DeviceInfo.sModel.equals("vivo Y51A")) {
            this.mAdaWebview.obtainFrameView().obtainApp().registerSysEventListener(new ISysEventListener() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.13
                @Override // io.dcloud.common.DHInterface.ISysEventListener
                public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                    ISysEventListener.SysEventType sysEventType2 = ISysEventListener.SysEventType.onKeyUp;
                    if (sysEventType != sysEventType2 || ((Integer) ((Object[]) obj)[0]).intValue() != 4) {
                        return false;
                    }
                    WebJsEvent.this.onHideCustomView();
                    WebJsEvent.this.mAdaWebview.obtainFrameView().obtainApp().unregisterSysEventListener(this, sysEventType2);
                    return true;
                }
            }, ISysEventListener.SysEventType.onKeyUp);
        }
        this.mCustomView = view;
        FrameLayout frameLayoutObtainActivityContentView = null;
        int requestedOrientation = 0;
        if (!(view instanceof ViewGroup) || ((ViewGroup) view).getChildCount() <= 0) {
            childAt = null;
        } else {
            childAt = ((ViewGroup) this.mCustomView).getChildAt(0);
            if (childAt instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) childAt;
                if (viewGroup.getChildCount() > 0) {
                    childAt = viewGroup.getChildAt(0);
                }
            }
        }
        if (childAt != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            layoutParams.setMargins(0, 0, 0, 0);
            layoutParams.gravity = 17;
            childAt.setPadding(0, 0, 0, 0);
            childAt.setLayoutParams(layoutParams);
            childAt.invalidate();
        }
        this.mCustomViewCallback = customViewCallback;
        Activity activity = this.mAdaWebview.obtainApp().getActivity();
        if (this.mAdaWebview.obtainFrameView() != null && this.mAdaWebview.obtainFrameView().obtainWindowMgr() != null) {
            AbsMgr absMgrObtainWindowMgr = this.mAdaWebview.obtainFrameView().obtainWindowMgr();
            IMgr.MgrType mgrType = IMgr.MgrType.FeatureMgr;
            Object objProcessEvent = absMgrObtainWindowMgr.processEvent(mgrType, 1, new Object[]{this.mAdaWebview, "device", "__isWakelockNative__", new JSONArray()});
            if (!(objProcessEvent instanceof String ? Boolean.valueOf(String.valueOf(objProcessEvent)).booleanValue() : false)) {
                this.isNeedCloseScreenWakelock = true;
                this.mAdaWebview.obtainFrameView().obtainWindowMgr().processEvent(mgrType, 1, new Object[]{this.mAdaWebview, "device", "setWakelock", new JSONArray().put(true)});
            }
        }
        if (activity != 0) {
            IActivityHandler iActivityHandler = DCloudAdapterUtil.getIActivityHandler(activity);
            if (iActivityHandler != null) {
                frameLayoutObtainActivityContentView = iActivityHandler.obtainActivityContentView();
                iActivityHandler.closeSideBar();
                iActivityHandler.setSideBarVisibility(8);
            } else if (activity instanceof IActivityDelegate) {
                frameLayoutObtainActivityContentView = ((IActivityDelegate) activity).obtainActivityContentView();
            }
        }
        if (frameLayoutObtainActivityContentView != null) {
            frameLayoutObtainActivityContentView.addView(view, COVER_SCREEN_GRAVITY_CENTER);
            this.mAdaWebview.obtainMainView().setVisibility(8);
            setStatusBarVisibility(activity, false);
            String webviewProperty = this.mAdaWebview.getWebviewProperty(AbsoluteConst.JSON_KEY_VIDEO_FULL_SCREEN);
            if ("landscape".equals(webviewProperty)) {
                requestedOrientation = 6;
            } else if (!"landscape-primary".equals(webviewProperty)) {
                requestedOrientation = "landscape-secondary".equals(webviewProperty) ? 8 : "portrait-primary".equals(webviewProperty) ? 1 : "portrait-secondary".equals(webviewProperty) ? 9 : activity.getRequestedOrientation();
            }
            if (activity.getRequestedOrientation() != requestedOrientation) {
                if (-2 == this.mDefaultScreemOrientation) {
                    this.mDefaultScreemOrientation = activity.getRequestedOrientation();
                }
                this.mScreemOrientationChanged = true;
                AdaWebview.ScreemOrientationChangedNeedLayout = true;
                activity.setRequestedOrientation(requestedOrientation);
            }
        }
    }

    private void openFileChooserLogic(final ValueCallback<Uri> valueCallback, final ValueCallback<Uri[]> valueCallback2, final String str, final String str2, final WebChromeClient.FileChooserParams fileChooserParams) {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null) {
            return;
        }
        PermissionUtil.usePermission(adaWebview.getActivity(), "base", PermissionUtil.PMS_STORAGE, 2, new PermissionUtil.Request() { // from class: io.dcloud.common.adapter.ui.webview.WebJsEvent.9
            @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
            public void onDenied(String str3) {
            }

            @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
            public void onGranted(String str3) {
                WebJsEvent.this.showOpenFileChooser(valueCallback, valueCallback2, str, str2, fileChooserParams);
            }
        });
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str) {
        openFileChooserLogic(valueCallback, str, null);
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
        openFileChooserLogic(valueCallback, str, str2);
    }
}

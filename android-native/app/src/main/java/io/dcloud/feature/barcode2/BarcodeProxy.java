package io.dcloud.feature.barcode2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.dcloud.zxing2.Result;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.FileUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.barcode2.decoding.CaptureActivityHandler;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class BarcodeProxy implements ISysEventListener {
    public static Context context = null;
    public static boolean save = false;
    public String mId;
    BarcodeFrameItem mBarcodeView = null;
    boolean mIsRegisetedSysEvent = false;

    public void appendToFrameView(AdaFrameView adaFrameView) {
        BarcodeFrameItem barcodeFrameItem = this.mBarcodeView;
        if (barcodeFrameItem != null) {
            barcodeFrameItem.appendToFrameView(adaFrameView);
        }
    }

    void execute(IWebview iWebview, String str, String[] strArr) {
        BarcodeFrameItem barcodeFrameItem;
        boolean z = false;
        if ("start".equals(str)) {
            if (!PdrUtil.isEmpty(this.mBarcodeView.errorMsg)) {
                this.mBarcodeView.runJsCallBack(StringUtil.format(DOMException.JSON_ERROR_INFO, 8, this.mBarcodeView.errorMsg), JSUtil.ERROR, true, true);
                return;
            }
            JSONObject jSONObjectCreateJSONObject = JSONUtil.createJSONObject(strArr[1]);
            if (jSONObjectCreateJSONObject != null) {
                boolean z2 = PdrUtil.parseBoolean(JSONUtil.getString(jSONObjectCreateJSONObject, "conserve"), false, false);
                if (z2) {
                    this.mBarcodeView.mFilename = iWebview.obtainFrameView().obtainApp().convert2AbsFullPath(iWebview.obtainFullUrl(), PdrUtil.getDefaultPrivateDocPath(JSONUtil.getString(jSONObjectCreateJSONObject, AbsoluteConst.JSON_KEY_FILENAME), "png"));
                    Logger.d("Filename:" + this.mBarcodeView.mFilename);
                }
                this.mBarcodeView.vibrate = PdrUtil.parseBoolean(JSONUtil.getString(jSONObjectCreateJSONObject, "vibrate"), true, false);
                this.mBarcodeView.playBeep = !TextUtils.equals(JSONUtil.getString(jSONObjectCreateJSONObject, "sound"), "none");
                z = z2;
            }
            BarcodeFrameItem barcodeFrameItem2 = this.mBarcodeView;
            barcodeFrameItem2.mConserve = z;
            barcodeFrameItem2.start();
            return;
        }
        if (BindingXConstants.STATE_CANCEL.equals(str)) {
            this.mBarcodeView.cancel_scan();
            return;
        }
        if ("setFlash".equals(str)) {
            this.mBarcodeView.setFlash(Boolean.parseBoolean(strArr[1]));
            return;
        }
        Bitmap bitmapDecodeFile = null;
        if (IFeature.F_BARCODE.equals(str)) {
            AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Barcode-Create");
            String str2 = strArr[0];
            if (!this.mIsRegisetedSysEvent) {
                IApp iAppObtainApp = iWebview.obtainFrameView().obtainApp();
                iAppObtainApp.registerSysEventListener(this, ISysEventListener.SysEventType.onPause);
                iAppObtainApp.registerSysEventListener(this, ISysEventListener.SysEventType.onResume);
                this.mIsRegisetedSysEvent = true;
            }
            JSONArray jSONArrayCreateJSONArray = !PdrUtil.isEmpty(strArr[4]) ? JSONUtil.createJSONArray(strArr[4]) : null;
            JSONObject jSONObjectCreateJSONObject2 = PdrUtil.isEmpty(strArr[5]) ? null : JSONUtil.createJSONObject(strArr[5]);
            JSONArray jSONArrayCreateJSONArray2 = JSONUtil.createJSONArray(strArr[3]);
            this.mId = strArr[2];
            BarcodeFrameItem barcodeFrameItem3 = new BarcodeFrameItem(this, iWebview, str2, jSONArrayCreateJSONArray2, jSONArrayCreateJSONArray, jSONObjectCreateJSONObject2);
            this.mBarcodeView = barcodeFrameItem3;
            barcodeFrameItem3.addCallBackId(strArr[1], iWebview.getWebviewUUID());
            if (strArr.length > 6) {
                this.mBarcodeView.autoDecodeCharset = Boolean.parseBoolean(strArr[6]);
            }
            if (jSONArrayCreateJSONArray2.length() > 3) {
                this.mBarcodeView.toFrameView();
                return;
            }
            return;
        }
        if (!"scan".equals(str)) {
            if (AbsoluteConst.EVENTS_CLOSE.equals(str)) {
                this.mBarcodeView.close_scan();
                return;
            }
            if ("setStyle".equals(str)) {
                JSONObject jSONObjectCreateJSONObject3 = JSONUtil.createJSONObject(strArr[1]);
                if (jSONObjectCreateJSONObject3 != null) {
                    this.mBarcodeView.upateStyles(jSONObjectCreateJSONObject3);
                    return;
                }
                return;
            }
            if (!"addCallBack".equals(str) || (barcodeFrameItem = this.mBarcodeView) == null) {
                return;
            }
            barcodeFrameItem.addCallBackId(strArr[1], iWebview.getWebviewUUID());
            return;
        }
        AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Barcode-" + str);
        String str3 = strArr[0];
        IApp iAppObtainApp2 = iWebview.obtainFrameView().obtainApp();
        String strConvert2AbsFullPath = iAppObtainApp2.convert2AbsFullPath(iWebview.obtainFullUrl(), strArr[1]);
        if (FileUtil.needMediaStoreOpenFile(iAppObtainApp2.getActivity())) {
            InputStream fileInputStream = FileUtil.getFileInputStream(iAppObtainApp2.getActivity(), strConvert2AbsFullPath);
            if (fileInputStream != null) {
                bitmapDecodeFile = BitmapFactory.decodeStream(fileInputStream);
            }
        } else {
            bitmapDecodeFile = BitmapFactory.decodeFile(strConvert2AbsFullPath);
        }
        if (bitmapDecodeFile != null) {
            Result resultDecode = CaptureActivityHandler.decode(bitmapDecodeFile, strArr.length > 3 ? Boolean.parseBoolean(strArr[3]) : false);
            if (resultDecode != null) {
                Deprecated_JSUtil.execCallback(iWebview, str3, StringUtil.format("{type:%d,message:%s,file:'%s',charSet:'%s'}", Integer.valueOf(BarcodeFrameItem.convertTypestrToNum(resultDecode.getBarcodeFormat())), JSONUtil.toJSONableString(resultDecode.getText()), strConvert2AbsFullPath, resultDecode.textCharset), JSUtil.OK, true, false);
                return;
            }
        }
        Deprecated_JSUtil.execCallback(iWebview, str3, StringUtil.format(DOMException.JSON_ERROR_INFO, 8, ""), JSUtil.ERROR, true, false);
    }

    public JSONObject getJsBarcode() {
        BarcodeFrameItem barcodeFrameItem = this.mBarcodeView;
        if (barcodeFrameItem != null) {
            return barcodeFrameItem.getJsBarcode();
        }
        return null;
    }

    protected void onDestroy() {
        BarcodeFrameItem barcodeFrameItem = this.mBarcodeView;
        if (barcodeFrameItem != null) {
            barcodeFrameItem.onDestroy();
            this.mBarcodeView = null;
        }
        this.mIsRegisetedSysEvent = false;
    }

    @Override // io.dcloud.common.DHInterface.ISysEventListener
    public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
        if (sysEventType == ISysEventListener.SysEventType.onResume) {
            onResume();
            return false;
        }
        if (sysEventType != ISysEventListener.SysEventType.onPause) {
            return false;
        }
        onPause();
        return false;
    }

    protected void onPause() {
        BarcodeFrameItem barcodeFrameItem = this.mBarcodeView;
        if (barcodeFrameItem != null) {
            barcodeFrameItem.onPause();
        }
    }

    protected void onResume() {
        BarcodeFrameItem barcodeFrameItem = this.mBarcodeView;
        if (barcodeFrameItem != null) {
            barcodeFrameItem.onResume(true);
        }
    }
}

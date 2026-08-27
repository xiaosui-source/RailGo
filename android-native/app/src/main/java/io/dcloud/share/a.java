package io.dcloud.share;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import androidx.webkit.internal.AssetHelper;
import com.facebook.common.util.UriUtil;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.application.DCloudApplication;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class a {
    private AbsMgr a;
    private HashMap c;
    private HashMap e;
    private HashMap b = new HashMap();
    public ArrayList d = new ArrayList();

    protected a(AbsMgr absMgr, String str) {
        this.a = absMgr;
        this.e = (HashMap) this.a.processEvent(IMgr.MgrType.FeatureMgr, 4, str);
    }

    public String a(IWebview iWebview, String str, String[] strArr) {
        AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Share-" + str);
        str.getClass();
        str.hashCode();
        switch (str) {
            case "launchMiniProgram":
                String str2 = strArr[0];
                IFShareApi iFShareApi = (IFShareApi) this.b.get(strArr[1]);
                if (!(iFShareApi instanceof IWeiXinFShareApi)) {
                    Deprecated_JSUtil.execCallback(iWebview, str2, StringUtil.format(DOMException.JSON_ERROR_INFO, -3, DOMException.MSG_NOT_SUPPORT), JSUtil.ERROR, true, false);
                    break;
                } else {
                    ((IWeiXinFShareApi) iFShareApi).launchMiniProgram(iWebview, strArr[2], str2);
                    break;
                }
            case "create":
                ShareAuthorizeView shareAuthorizeView = new ShareAuthorizeView(iWebview, strArr[1]);
                if (strArr[2] == null || !strArr[1].equals(AbsoluteConst.FALSE)) {
                    float scale = iWebview.getScale();
                    iWebview.addFrameItem(shareAuthorizeView, AdaFrameItem.LayoutParamsUtil.createLayoutParams((int) (Integer.parseInt(strArr[3]) * scale), (int) (Integer.parseInt(strArr[4]) * scale), (int) (Integer.parseInt(strArr[5]) * scale), (int) (Integer.parseInt(strArr[6]) * scale)));
                }
                this.c.put(strArr[0], shareAuthorizeView);
                break;
            case "forbid":
                ((IFShareApi) this.b.get(strArr[0])).forbid(iWebview);
                break;
            case "setVisible":
                ShareAuthorizeView shareAuthorizeView2 = (ShareAuthorizeView) this.c.get(strArr[0]);
                if (!Boolean.parseBoolean(strArr[1])) {
                    shareAuthorizeView2.setVisibility(8);
                    break;
                } else {
                    shareAuthorizeView2.setVisibility(0);
                    break;
                }
            case "getServices":
                Deprecated_JSUtil.execCallback(iWebview, strArr[0], a(iWebview), 1, true, false);
                break;
            case "load":
                ((ShareAuthorizeView) this.c.get(strArr[0])).load(this, strArr[1]);
                break;
            case "send":
                IFShareApi iFShareApi2 = (IFShareApi) this.b.get(strArr[1]);
                Application application = iWebview.getActivity().getApplication();
                if (application instanceof DCloudApplication) {
                    ((DCloudApplication) application).stopB2FOnce();
                }
                iFShareApi2.send(iWebview, strArr[0], strArr[2]);
                break;
            case "sendWithSystem":
                a(iWebview, strArr[0], strArr[1]);
                break;
            case "openCustomerServiceChat":
                String str3 = strArr[0];
                IFShareApi iFShareApi3 = (IFShareApi) this.b.get(strArr[1]);
                if (!(iFShareApi3 instanceof IWeiXinFShareApi)) {
                    Deprecated_JSUtil.execCallback(iWebview, str3, StringUtil.format(DOMException.JSON_ERROR_INFO, -3, DOMException.MSG_NOT_SUPPORT), JSUtil.ERROR, true, false);
                    break;
                } else {
                    ((IWeiXinFShareApi) iFShareApi3).openCustomerServiceChat(iWebview, strArr[2], str3);
                    break;
                }
            case "authorize":
                ((IFShareApi) this.b.get(strArr[1])).authorize(iWebview, strArr[0], strArr[2]);
                break;
        }
        return null;
    }

    public String a(String str) {
        if (PdrUtil.isEquals(str, "sinaweibo")) {
            return (String) this.e.get("sina");
        }
        if (PdrUtil.isEquals(str, "tencentweibo")) {
            return (String) this.e.get("tencent");
        }
        return null;
    }

    private String a(IWebview iWebview) {
        String str;
        Exception e;
        StringBuffer stringBuffer = new StringBuffer(Operators.ARRAY_START_STR);
        HashMap map = this.e;
        if (map != null && !map.isEmpty()) {
            String str2 = null;
            int i = 0;
            for (String str3 : this.e.keySet()) {
                try {
                    IFShareApi iFShareApi = (IFShareApi) this.b.get(str3);
                    if (iFShareApi == null) {
                        str = (String) this.e.get(str3);
                        try {
                            iFShareApi = (IFShareApi) Class.forName(str).newInstance();
                            iFShareApi.initConfig();
                            this.b.put(iFShareApi.getId(), iFShareApi);
                            str2 = str;
                        } catch (Resources.NotFoundException unused) {
                            str2 = str;
                            Logger.e("ShareApiManager getServices " + str2 + " is Not found!");
                            i++;
                        } catch (Exception e2) {
                            e = e2;
                            Logger.e("ShareApiManager getServices " + str + " Exception =" + e);
                            str2 = str;
                            i++;
                        }
                    }
                    stringBuffer.append(iFShareApi.getJsonObject(iWebview));
                    if (i != this.b.size()) {
                        stringBuffer.append(",");
                    }
                } catch (Resources.NotFoundException unused2) {
                } catch (Exception e3) {
                    str = str2;
                    e = e3;
                }
                i++;
            }
        }
        stringBuffer.append(Operators.ARRAY_END_STR);
        return stringBuffer.toString();
    }

    public void a(IWebview iWebview, String str, String str2) {
        Uri uriFromFile;
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String strOptString = jSONObject.optString(UriUtil.LOCAL_CONTENT_SCHEME);
            String strOptString2 = jSONObject.optString(AbsoluteConst.JSON_KEY_TITLE);
            String strOptString3 = jSONObject.optString("href");
            if (!TextUtils.isEmpty(strOptString3)) {
                strOptString = strOptString + "  " + strOptString3;
            }
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("pictures");
            Intent intent = new Intent();
            if (!PdrUtil.isEmpty(jSONArrayOptJSONArray)) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    String strConvert2AbsFullPath = iWebview.obtainApp().convert2AbsFullPath(iWebview.obtainFullUrl(), jSONArrayOptJSONArray.optString(i));
                    if (strConvert2AbsFullPath.startsWith(DeviceInfo.sPrivateDir)) {
                        String str3 = DeviceInfo.sPrivateExternalDir + strConvert2AbsFullPath.substring(DeviceInfo.sPrivateDir.length());
                        if (DHFile.copyFile(strConvert2AbsFullPath, str3, true, false) == 1) {
                            strConvert2AbsFullPath = str3;
                        }
                    }
                    if (Build.VERSION.SDK_INT >= 24) {
                        uriFromFile = a(iWebview.getContext(), new File(strConvert2AbsFullPath), intent);
                    } else {
                        uriFromFile = Uri.fromFile(new File(strConvert2AbsFullPath));
                    }
                    arrayList.add(uriFromFile);
                }
                a(intent, strOptString, strOptString2, arrayList);
            } else {
                a(intent, strOptString, strOptString2, null);
            }
            Intent intentCreateChooser = Intent.createChooser(intent, "");
            intentCreateChooser.addFlags(1);
            iWebview.getActivity().startActivity(intentCreateChooser);
            Deprecated_JSUtil.execCallback(iWebview, str, "", JSUtil.OK, false, false);
        } catch (Exception unused) {
            Deprecated_JSUtil.execCallback(iWebview, str, StringUtil.format(DOMException.JSON_ERROR_INFO, -99, DOMException.MSG_UNKNOWN_ERROR), JSUtil.ERROR, true, false);
        }
    }

    private Intent a(Intent intent, String str, String str2, ArrayList arrayList) {
        if (!PdrUtil.isEmpty(str)) {
            intent.putExtra("android.intent.extra.TEXT", str);
        }
        if (!PdrUtil.isEmpty(str2)) {
            intent.putExtra("android.intent.extra.SUBJECT", str2);
        }
        if (!PdrUtil.isEmpty(arrayList) && arrayList.size() > 0) {
            intent.setType("image/*");
            if (arrayList.size() > 1) {
                intent.setAction("android.intent.action.SEND_MULTIPLE");
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
                return intent;
            }
            intent.setAction("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", (Parcelable) arrayList.get(0));
            return intent;
        }
        intent.setAction("android.intent.action.SEND");
        intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
        return intent;
    }

    public Uri a(Context context, File file, Intent intent) {
        String absolutePath = file.getAbsolutePath();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursorQuery = contentResolver.query(uri, new String[]{"_id"}, "_data=? ", new String[]{absolutePath}, null);
        Uri uriForFile = null;
        if (cursorQuery != null) {
            if (cursorQuery.moveToFirst()) {
                int i = cursorQuery.getInt(cursorQuery.getColumnIndex("_id"));
                uriForFile = Uri.withAppendedPath(Uri.parse("content://media/external/images/media"), "" + i);
            }
            cursorQuery.close();
        }
        if (uriForFile == null) {
            uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".dc.fileprovider", file);
        }
        if (uriForFile != null) {
            return uriForFile;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", absolutePath);
        return context.getContentResolver().insert(uri, contentValues);
    }

    public void a() {
        HashMap map = this.b;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                ((IFShareApi) ((Map.Entry) it.next()).getValue()).dispose();
            }
        }
        HashMap map2 = this.c;
        if (map2 != null) {
            Iterator it2 = map2.entrySet().iterator();
            while (it2.hasNext()) {
                ((ShareAuthorizeView) ((Map.Entry) it2.next()).getValue()).dispose();
            }
        }
        HashMap map3 = this.e;
        if (map3 != null) {
            map3.clear();
        }
        HashMap map4 = this.b;
        if (map4 != null) {
            map4.clear();
        }
        HashMap map5 = this.c;
        if (map5 != null) {
            map5.clear();
        }
    }
}

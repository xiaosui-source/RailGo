package io.dcloud.feature.sqlite;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.taobao.weex.common.Constants;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.StandardFeature;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class DataBaseFeature extends StandardFeature {
    public HashMap<String, SQLiteDatabase> map = new HashMap<>();
    private String resultMessage = "{'code':%d,'message':\"%s\"}";

    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void openDatabase(io.dcloud.common.DHInterface.IWebview r11, org.json.JSONArray r12) throws org.json.JSONException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 341
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.sqlite.DataBaseFeature.openDatabase(io.dcloud.common.DHInterface.IWebview, org.json.JSONArray):void");
    }

    public String isOpenDatabase(IWebview iWebview, JSONArray jSONArray) {
        JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(0);
        if (PdrUtil.isEmpty(jSONObjectOptJSONObject)) {
            return Deprecated_JSUtil.wrapJsVar(Constants.Name.UNDEFINED, false);
        }
        String strOptString = jSONObjectOptJSONObject.optString("name");
        String strOptString2 = jSONObjectOptJSONObject.optString(AbsoluteConst.XML_PATH);
        if (PdrUtil.isEmpty(strOptString) || PdrUtil.isEmpty(strOptString2)) {
            return Deprecated_JSUtil.wrapJsVar(Constants.Name.UNDEFINED, false);
        }
        Iterator<String> it = this.map.keySet().iterator();
        while (it.hasNext()) {
            SQLiteDatabase sQLiteDatabase = this.map.get(it.next());
            String strConvert2AbsFullPath = iWebview.obtainApp().convert2AbsFullPath(iWebview.obtainFullUrl(), strOptString2);
            if (!PdrUtil.isDeviceRootDir(strConvert2AbsFullPath)) {
                strConvert2AbsFullPath = DeviceInfo.sBaseFsRootPath + strConvert2AbsFullPath;
            }
            if (strConvert2AbsFullPath.equalsIgnoreCase(sQLiteDatabase.getPath())) {
                return JSUtil.wrapJsVar(true);
            }
        }
        if (this.map.containsKey(strOptString)) {
            return JSUtil.wrapJsVar(true);
        }
        return JSUtil.wrapJsVar(false);
    }

    public void closeDatabase(IWebview iWebview, JSONArray jSONArray) {
        String strOptString = jSONArray.optString(0);
        String strOptString2 = jSONArray.optString(1);
        SQLiteDatabase sQLiteDatabase = this.map.get(strOptString2);
        if (PdrUtil.isEmpty(strOptString2)) {
            Deprecated_JSUtil.execCallback(iWebview, strOptString, StringUtil.format(this.resultMessage, -1404, DOMException.toString("parameter can't be null")), JSUtil.ERROR, true, false);
        } else {
            if (sQLiteDatabase == null) {
                Deprecated_JSUtil.execCallback(iWebview, strOptString, StringUtil.format(this.resultMessage, Integer.valueOf(DOMException.CODE_STATISTICS_SERVICE_INVALID), DOMException.toString("Not Open")), JSUtil.ERROR, true, false);
                return;
            }
            sQLiteDatabase.close();
            this.map.remove(strOptString2);
            Deprecated_JSUtil.execCallback(iWebview, strOptString, "{}", JSUtil.OK, true, false);
        }
    }

    public void transaction(IWebview iWebview, JSONArray jSONArray) throws JSONException {
        IWebview iWebview2;
        Exception exc;
        String strOptString = jSONArray.optString(0);
        String strOptString2 = jSONArray.optString(1);
        SQLiteDatabase sQLiteDatabase = this.map.get(strOptString2);
        String strOptString3 = jSONArray.optString(2);
        if (PdrUtil.isEmpty(strOptString2) || PdrUtil.isEmpty(strOptString3)) {
            Deprecated_JSUtil.execCallback(iWebview, strOptString, StringUtil.format(this.resultMessage, -1404, DOMException.toString("parameter can't be null")), JSUtil.ERROR, true, false);
            return;
        }
        if (sQLiteDatabase == null) {
            Deprecated_JSUtil.execCallback(iWebview, strOptString, StringUtil.format(this.resultMessage, Integer.valueOf(DOMException.CODE_STATISTICS_SERVICE_INVALID), DOMException.toString("Not Open")), JSUtil.ERROR, true, false);
            return;
        }
        try {
            try {
                if (strOptString3.equals("begin")) {
                    sQLiteDatabase.beginTransaction();
                } else if (strOptString3.equals("commit")) {
                    sQLiteDatabase.setTransactionSuccessful();
                    sQLiteDatabase.endTransaction();
                } else {
                    if (!strOptString3.equals("rollback")) {
                        try {
                            iWebview2 = iWebview;
                            try {
                                Deprecated_JSUtil.execCallback(iWebview2, strOptString, StringUtil.format(this.resultMessage, -1404, DOMException.toString("Operation Error")), JSUtil.ERROR, true, false);
                                Deprecated_JSUtil.execCallback(iWebview2, strOptString, "{}", JSUtil.OK, true, false);
                            } catch (Exception e) {
                                e = e;
                                exc = e;
                                JSONObject jSONObject = new JSONObject();
                                try {
                                    jSONObject.put("code", -1404);
                                    jSONObject.put("message", DOMException.toString(exc.toString()));
                                } catch (JSONException unused) {
                                }
                                JSUtil.execCallback(iWebview2, strOptString, jSONObject, JSUtil.ERROR, false);
                                return;
                            }
                        } catch (Exception e2) {
                            iWebview2 = iWebview;
                            exc = e2;
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("code", -1404);
                            jSONObject2.put("message", DOMException.toString(exc.toString()));
                            JSUtil.execCallback(iWebview2, strOptString, jSONObject2, JSUtil.ERROR, false);
                            return;
                        }
                    }
                    sQLiteDatabase.endTransaction();
                }
                iWebview2 = iWebview;
                Deprecated_JSUtil.execCallback(iWebview2, strOptString, "{}", JSUtil.OK, true, false);
            } catch (Exception e3) {
                exc = e3;
                iWebview2 = iWebview;
            }
        } catch (Exception e4) {
            e = e4;
            iWebview2 = iWebview;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [io.dcloud.common.DHInterface.IWebview] */
    /* JADX WARN: Type inference failed for: r4v6, types: [org.json.JSONArray] */
    /* JADX WARN: Type inference failed for: r4v7, types: [io.dcloud.common.DHInterface.IWebview] */
    public void executeSql(IWebview iWebview, JSONArray jSONArray) throws JSONException, SQLException {
        ?? jSONArray2;
        String strOptString = jSONArray.optString(0);
        String strOptString2 = jSONArray.optString(1);
        SQLiteDatabase sQLiteDatabase = this.map.get(strOptString2);
        String strOptString3 = jSONArray.optString(2);
        if (PdrUtil.isEmpty(strOptString2) || PdrUtil.isEmpty(strOptString3)) {
            Deprecated_JSUtil.execCallback(iWebview, strOptString, StringUtil.format(this.resultMessage, -1404, DOMException.toString("parameter can't be null")), JSUtil.ERROR, true, false);
            return;
        }
        if (sQLiteDatabase == null) {
            Deprecated_JSUtil.execCallback(iWebview, strOptString, StringUtil.format(this.resultMessage, Integer.valueOf(DOMException.CODE_STATISTICS_SERVICE_INVALID), DOMException.toString("Not Open")), JSUtil.ERROR, true, false);
            return;
        }
        try {
            jSONArray2 = new JSONArray(strOptString3);
            try {
                if (jSONArray2.length() > 0) {
                    for (int i = 0; i < jSONArray2.length(); i++) {
                        String strOptString4 = jSONArray2.optString(i);
                        if (!TextUtils.isEmpty(strOptString4.trim())) {
                            sQLiteDatabase.execSQL(strOptString4);
                        }
                    }
                    Deprecated_JSUtil.execCallback(iWebview, strOptString, "{}", JSUtil.OK, true, false);
                    return;
                }
                jSONArray2 = iWebview;
                String str = this.resultMessage;
                String string = DOMException.toString("parameter can't be null");
                try {
                    Object[] objArr = new Object[2];
                    try {
                        objArr[0] = -1404;
                        objArr[1] = string;
                        Deprecated_JSUtil.execCallback(jSONArray2, strOptString, StringUtil.format(str, objArr), JSUtil.ERROR, true, false);
                    } catch (Exception e) {
                        e = e;
                        try {
                            if (e instanceof SQLException) {
                                JSONObject jSONObject = new JSONObject();
                                try {
                                    jSONObject.put("code", -1404);
                                    jSONObject.put("message", DOMException.toString(e.toString()));
                                } catch (JSONException unused) {
                                }
                                JSUtil.execCallback((IWebview) jSONArray2, strOptString, jSONObject, JSUtil.ERROR, false);
                                return;
                            }
                            sQLiteDatabase.execSQL(strOptString3);
                            Deprecated_JSUtil.execCallback(jSONArray2, strOptString, "{}", JSUtil.OK, true, false);
                        } catch (Exception e2) {
                            JSONObject jSONObject2 = new JSONObject();
                            try {
                                jSONObject2.put("code", -1404);
                                jSONObject2.put("message", DOMException.toString(e2.toString()));
                            } catch (JSONException unused2) {
                            }
                            JSUtil.execCallback((IWebview) jSONArray2, strOptString, jSONObject2, JSUtil.ERROR, false);
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Exception e5) {
            e = e5;
            jSONArray2 = iWebview;
        }
    }

    public void selectSql(IWebview iWebview, JSONArray jSONArray) throws JSONException {
        String str;
        String str2 = "code";
        String strOptString = jSONArray.optString(0);
        String strOptString2 = jSONArray.optString(1);
        SQLiteDatabase sQLiteDatabase = this.map.get(strOptString2);
        if (sQLiteDatabase == null) {
            Deprecated_JSUtil.execCallback(iWebview, strOptString, StringUtil.format(this.resultMessage, Integer.valueOf(DOMException.CODE_STATISTICS_SERVICE_INVALID), DOMException.toString("Not Open")), JSUtil.ERROR, true, false);
            return;
        }
        String strOptString3 = jSONArray.optString(2);
        if (PdrUtil.isEmpty(strOptString2) || PdrUtil.isEmpty(strOptString3)) {
            Deprecated_JSUtil.execCallback(iWebview, strOptString, StringUtil.format(this.resultMessage, -1404, DOMException.toString("parameter can't be null")), JSUtil.ERROR, true, false);
            return;
        }
        try {
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery(strOptString3, null);
            JSONArray jSONArray2 = new JSONArray();
            try {
                if (cursorRawQuery.moveToFirst()) {
                    String[] columnNames = cursorRawQuery.getColumnNames();
                    while (true) {
                        JSONObject jSONObject = new JSONObject();
                        int i = 0;
                        while (i < columnNames.length) {
                            int type = cursorRawQuery.getType(i);
                            if (type == 0) {
                                str = str2;
                                jSONObject.put(columnNames[i], JSONObject.NULL);
                            } else if (type == 1 || type == 2) {
                                str = str2;
                                try {
                                    try {
                                        jSONObject.put(columnNames[i], new BigDecimal(String.valueOf(cursorRawQuery.getDouble(i))).doubleValue());
                                    } catch (JSONException unused) {
                                    }
                                } catch (Exception e) {
                                    e = e;
                                    JSONObject jSONObject2 = new JSONObject();
                                    try {
                                        jSONObject2.put(str, -1404);
                                        jSONObject2.put("message", DOMException.toString(e.toString()));
                                    } catch (JSONException unused2) {
                                    }
                                    JSUtil.execCallback(iWebview, strOptString, jSONObject2, JSUtil.ERROR, false);
                                    return;
                                }
                            } else {
                                if (type == 3) {
                                    jSONObject.put(columnNames[i], cursorRawQuery.getString(i));
                                } else if (type == 4) {
                                    try {
                                        jSONObject.put(columnNames[i], Arrays.toString(cursorRawQuery.getBlob(i)));
                                    } catch (JSONException unused3) {
                                    }
                                }
                                str = str2;
                            }
                            i++;
                            str2 = str;
                        }
                        str = str2;
                        jSONArray2.put(jSONObject);
                        if (!cursorRawQuery.moveToNext()) {
                            break;
                        } else {
                            str2 = str;
                        }
                    }
                } else {
                    str = str2;
                }
                cursorRawQuery.close();
                JSUtil.execCallback(iWebview, strOptString, jSONArray2, JSUtil.OK, false);
            } catch (Exception e2) {
                e = e2;
                str = str2;
            }
        } catch (Exception e3) {
            JSONObject jSONObject3 = new JSONObject();
            try {
                jSONObject3.put(str2, -1404);
                jSONObject3.put("message", DOMException.toString(e3.toString()));
            } catch (JSONException unused4) {
            }
            JSUtil.execCallback(iWebview, strOptString, jSONObject3, JSUtil.ERROR, false);
        }
    }
}

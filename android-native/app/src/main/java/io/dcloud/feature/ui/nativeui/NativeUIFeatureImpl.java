package io.dcloud.feature.ui.nativeui;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.module.WXModalUIModule;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.DialogUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.RuningAcitvityUtil;
import io.dcloud.feature.nativeObj.photoview.LongClickEventManager;
import io.dcloud.feature.ui.nativeui.a;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NativeUIFeatureImpl implements IFeature {
    int d;
    int e;
    int f;
    int h;
    int i;
    AbsMgr m;
    HashMap a = null;
    HashMap b = null;
    DatePickerDialog c = null;
    TimePickerDialog g = null;
    final byte j = 0;
    final byte k = 1;
    final byte l = 2;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements DialogInterface.OnClickListener {
        final /* synthetic */ byte a;
        final /* synthetic */ EditText b;
        final /* synthetic */ IWebview c;
        final /* synthetic */ String d;
        final /* synthetic */ int e;
        final /* synthetic */ AlertDialog f;

        a(byte b, EditText editText, IWebview iWebview, String str, int i, AlertDialog alertDialog) {
            this.a = b;
            this.b = editText;
            this.c = iWebview;
            this.d = str;
            this.e = i;
            this.f = alertDialog;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (this.a == 2) {
                String jSONableString = JSONUtil.toJSONableString(this.b.getText().toString());
                Deprecated_JSUtil.execCallback(this.c, this.d, "{index:" + this.e + ",message:" + jSONableString + Operators.BLOCK_END_STR, JSUtil.OK, true, false);
            }
            if (this.a == 1) {
                Deprecated_JSUtil.execCallback(this.c, this.d, String.valueOf(this.e), JSUtil.OK, true, false);
            }
            this.f.dismiss();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements DialogInterface.OnKeyListener {
        final /* synthetic */ byte a;
        final /* synthetic */ EditText b;
        final /* synthetic */ IWebview c;
        final /* synthetic */ String d;
        final /* synthetic */ AlertDialog e;

        b(byte b, EditText editText, IWebview iWebview, String str, AlertDialog alertDialog) {
            this.a = b;
            this.b = editText;
            this.c = iWebview;
            this.d = str;
            this.e = alertDialog;
        }

        @Override // android.content.DialogInterface.OnKeyListener
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() != 1 || i != 4) {
                return false;
            }
            if (this.a == 2) {
                String jSONableString = JSONUtil.toJSONableString(this.b.getText().toString());
                Deprecated_JSUtil.execCallback(this.c, this.d, "{index:-1,message:" + jSONableString + Operators.BLOCK_END_STR, JSUtil.OK, true, false);
            }
            if (this.a == 1) {
                Deprecated_JSUtil.execCallback(this.c, this.d, String.valueOf(-1), JSUtil.OK, true, false);
            }
            this.e.dismiss();
            return true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c extends LongClickEventManager.OnLongClickListener {
        c(IWebview iWebview, String str) {
            super(iWebview, str);
        }

        @Override // io.dcloud.feature.nativeObj.photoview.LongClickEventManager.OnLongClickListener
        public void onLongClickListener(JSONObject jSONObject) {
            Deprecated_JSUtil.execCallback(getPwebview(), getCallbackIds(), jSONObject.toString(), JSUtil.OK, true, true);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements a.b {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        d(IWebview iWebview, String str, String str2) {
            this.a = iWebview;
            this.b = str;
            this.c = str2;
        }

        @Override // io.dcloud.feature.ui.nativeui.a.b
        public void initCancelText(TextView textView) {
        }

        @Override // io.dcloud.feature.ui.nativeui.a.b
        public void initTextItem(int i, TextView textView, String str) {
        }

        @Override // io.dcloud.feature.ui.nativeui.a.b
        public boolean onDismiss(int i) {
            HashMap map = NativeUIFeatureImpl.this.b;
            if (map == null || !map.containsKey(this.c)) {
                return false;
            }
            NativeUIFeatureImpl.this.b.remove(this.c);
            return false;
        }

        @Override // io.dcloud.feature.ui.nativeui.a.b
        public void onItemClick(int i) {
            Deprecated_JSUtil.execCallback(this.a, this.b, "" + i, JSUtil.OK, true, false);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements DatePickerDialog.OnDateSetListener {
        e() {
        }

        @Override // android.app.DatePickerDialog.OnDateSetListener
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            NativeUIFeatureImpl nativeUIFeatureImpl = NativeUIFeatureImpl.this;
            nativeUIFeatureImpl.d = i;
            nativeUIFeatureImpl.e = i2;
            nativeUIFeatureImpl.f = i3;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class f extends DatePickerDialog {
        int a;
        int b;
        int c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;
        final /* synthetic */ int f;
        final /* synthetic */ boolean g;
        final /* synthetic */ IWebview h;
        final /* synthetic */ String i;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        f(Context context, int i, DatePickerDialog.OnDateSetListener onDateSetListener, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, IWebview iWebview, String str) {
            super(context, i, onDateSetListener, i2, i3, i4);
            this.d = i5;
            this.e = i6;
            this.f = i7;
            this.g = z;
            this.h = iWebview;
            this.i = str;
            this.a = i5;
            this.b = i6;
            this.c = i7;
        }

        @Override // android.app.DatePickerDialog, android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            super.onClick(dialogInterface, i);
            if (i == -2) {
                Deprecated_JSUtil.execCallback(this.h, this.i, DOMException.toJSON(-2, DOMException.MSG_USER_CANCEL), JSUtil.ERROR, true, false);
            } else if (i == -1) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
                if (DeviceInfo.sDeviceSdkVer >= 21) {
                    NativeUIFeatureImpl nativeUIFeatureImpl = NativeUIFeatureImpl.this;
                    gregorianCalendar.set(nativeUIFeatureImpl.d, nativeUIFeatureImpl.e, nativeUIFeatureImpl.f, 0, 0, 0);
                } else {
                    gregorianCalendar.set(this.a, this.b, this.c, 0, 0, 0);
                }
                Deprecated_JSUtil.execCallback(this.h, this.i, String.valueOf(gregorianCalendar.getTime().getTime()), JSUtil.OK, true, false);
            }
            NativeUIFeatureImpl.this.a();
        }

        @Override // android.app.AlertDialog, android.app.Dialog
        protected void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            getWindow().setSoftInputMode(2);
        }

        @Override // android.app.DatePickerDialog, android.widget.DatePicker.OnDateChangedListener
        public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            if (this.g || DeviceInfo.sVersion_release.equals("4.0.3") || DeviceInfo.sVersion_release.equals("4.0.4")) {
                return;
            }
            super.onDateChanged(datePicker, i, i2, i3);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class g implements DialogInterface.OnDismissListener {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String b;

        g(IWebview iWebview, String str) {
            this.a = iWebview;
            this.b = str;
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            if (NativeUIFeatureImpl.this.c != null) {
                Deprecated_JSUtil.execCallback(this.a, this.b, DOMException.toJSON(-2, DOMException.MSG_USER_CANCEL), JSUtil.ERROR, true, false);
                NativeUIFeatureImpl.this.a();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class h implements TimePickerDialog.OnTimeSetListener {
        h() {
        }

        @Override // android.app.TimePickerDialog.OnTimeSetListener
        public void onTimeSet(TimePicker timePicker, int i, int i2) {
            NativeUIFeatureImpl nativeUIFeatureImpl = NativeUIFeatureImpl.this;
            nativeUIFeatureImpl.h = i;
            nativeUIFeatureImpl.i = i2;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class i extends TimePickerDialog {
        int a;
        int b;
        final /* synthetic */ int c;
        final /* synthetic */ int d;
        final /* synthetic */ boolean e;
        final /* synthetic */ IWebview f;
        final /* synthetic */ String g;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        i(Context context, int i, TimePickerDialog.OnTimeSetListener onTimeSetListener, int i2, int i3, boolean z, int i4, int i5, boolean z2, IWebview iWebview, String str) {
            super(context, i, onTimeSetListener, i2, i3, z);
            this.c = i4;
            this.d = i5;
            this.e = z2;
            this.f = iWebview;
            this.g = str;
            this.a = i4;
            this.b = i5;
        }

        @Override // android.app.TimePickerDialog, android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            super.onClick(dialogInterface, i);
            if (i == -2) {
                Deprecated_JSUtil.execCallback(this.f, this.g, DOMException.toJSON(-2, DOMException.MSG_USER_CANCEL), JSUtil.ERROR, true, false);
            } else if (i == -1) {
                Date date = new Date();
                if (DeviceInfo.sDeviceSdkVer >= 21) {
                    date.setHours(NativeUIFeatureImpl.this.h);
                    date.setMinutes(NativeUIFeatureImpl.this.i);
                } else {
                    date.setHours(this.a);
                    date.setMinutes(this.b);
                }
                Deprecated_JSUtil.execCallback(this.f, this.g, String.valueOf(date.getTime()), JSUtil.OK, true, false);
            }
            NativeUIFeatureImpl.this.b();
        }

        @Override // android.app.AlertDialog, android.app.Dialog
        protected void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            getWindow().setSoftInputMode(2);
        }

        @Override // android.app.TimePickerDialog, android.widget.TimePicker.OnTimeChangedListener
        public void onTimeChanged(TimePicker timePicker, int i, int i2) {
            this.a = i;
            this.b = i2;
            if (this.e) {
                return;
            }
            setTitle(i + ":" + i2);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class j implements DialogInterface.OnDismissListener {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String b;

        j(IWebview iWebview, String str) {
            this.a = iWebview;
            this.b = str;
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            if (NativeUIFeatureImpl.this.g != null) {
                Deprecated_JSUtil.execCallback(this.a, this.b, DOMException.toJSON(-2, DOMException.MSG_USER_CANCEL), JSUtil.ERROR, true, false);
                NativeUIFeatureImpl.this.b();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class k implements DialogInterface.OnClickListener {
        final /* synthetic */ AlertDialog a;
        final /* synthetic */ IWebview b;
        final /* synthetic */ String c;

        k(AlertDialog alertDialog, IWebview iWebview, String str) {
            this.a = alertDialog;
            this.b = iWebview;
            this.c = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            this.a.dismiss();
            Deprecated_JSUtil.execCallback(this.b, this.c, "{index:0}", JSUtil.OK, true, false);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class l implements DialogInterface.OnKeyListener {
        final /* synthetic */ AlertDialog a;
        final /* synthetic */ IWebview b;
        final /* synthetic */ String c;

        l(AlertDialog alertDialog, IWebview iWebview, String str) {
            this.a = alertDialog;
            this.b = iWebview;
            this.c = str;
        }

        @Override // android.content.DialogInterface.OnKeyListener
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() != 1 || i != 4) {
                return false;
            }
            this.a.dismiss();
            Deprecated_JSUtil.execCallback(this.b, this.c, "{index:-1}", JSUtil.OK, true, false);
            return true;
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
        if (PdrUtil.isEmpty(str)) {
            this.m = null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    @Override // io.dcloud.common.DHInterface.IFeature
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String execute(io.dcloud.common.DHInterface.IWebview r20, java.lang.String r21, java.lang.String[] r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1534
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.ui.nativeui.NativeUIFeatureImpl.execute(io.dcloud.common.DHInterface.IWebview, java.lang.String, java.lang.String[]):java.lang.String");
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        this.m = absMgr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.g = null;
        this.h = 0;
        this.i = 0;
    }

    void a(String str) {
        this.a.remove(str);
    }

    private void b(IWebview iWebview, String str, JSONObject jSONObject) throws NumberFormatException {
        int i2;
        int i3;
        if (this.g != null) {
            Deprecated_JSUtil.execCallback(iWebview, str, DOMException.toJSON(5, ""), JSUtil.ERROR, true, false);
            return;
        }
        Activity topRuningActivity = RuningAcitvityUtil.getTopRuningActivity(iWebview.getActivity());
        boolean z = (jSONObject == null || jSONObject.isNull(AbsoluteConst.JSON_KEY_TITLE)) ? false : true;
        boolean z2 = PdrUtil.parseBoolean(JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_IS24HOUR), true, false);
        String string = JSONUtil.getString(jSONObject, Constants.Value.TIME);
        String string2 = JSONUtil.getString(jSONObject, "__minutes");
        String string3 = JSONUtil.getString(jSONObject, "__hours");
        if (PdrUtil.isEmpty(string3)) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            PdrUtil.isEmpty(string);
            if (z2) {
                i2 = gregorianCalendar.get(11);
            } else {
                i2 = gregorianCalendar.get(10);
            }
            i3 = gregorianCalendar.get(12);
        } else {
            i2 = Integer.parseInt(string3);
            i3 = Integer.parseInt(string2);
        }
        int i4 = i2;
        int i5 = i3;
        i iVar = new i(topRuningActivity, AppRuntime.getAppDarkMode(iWebview.getContext()) ? 4 : 0, new h(), i4, i5, z2, i4, i5, z, iWebview, str);
        if (z) {
            iVar.setTitle(JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_TITLE));
        }
        iVar.setOnDismissListener(new j(iWebview, str));
        iVar.show();
        this.g = iVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x027b  */
    /* JADX WARN: Type inference failed for: r13v4, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout] */
    /* JADX WARN: Type inference failed for: r1v6, types: [io.dcloud.p.g0] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.lang.Object, java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.widget.Toast] */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r5v7, types: [io.dcloud.common.DHInterface.AbsMgr, io.dcloud.common.DHInterface.IMgr] */
    /* JADX WARN: Type inference failed for: r9v3, types: [java.lang.Object[]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void a(io.dcloud.common.DHInterface.IApp r23, io.dcloud.common.DHInterface.IWebview r24, java.lang.String r25, org.json.JSONObject r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 694
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.ui.nativeui.NativeUIFeatureImpl.a(io.dcloud.common.DHInterface.IApp, io.dcloud.common.DHInterface.IWebview, java.lang.String, org.json.JSONObject):void");
    }

    private void a(IWebview iWebview, String str, JSONObject jSONObject) throws JSONException {
        int i2;
        int i3;
        if (this.c != null) {
            Deprecated_JSUtil.execCallback(iWebview, str, DOMException.toJSON(5, ""), JSUtil.ERROR, true, false);
            return;
        }
        Activity topRuningActivity = RuningAcitvityUtil.getTopRuningActivity(iWebview.getActivity());
        int i4 = JSONUtil.getInt(jSONObject, AbsoluteConst.JSON_KEY_STARTYEAR);
        int i5 = JSONUtil.getInt(jSONObject, "startMonth");
        int i6 = JSONUtil.getInt(jSONObject, "startDay");
        int i7 = JSONUtil.getInt(jSONObject, AbsoluteConst.JSON_KEY_ENDYEAR);
        int i8 = JSONUtil.getInt(jSONObject, "endMonth");
        int i9 = JSONUtil.getInt(jSONObject, "endDay");
        int i10 = JSONUtil.getInt(jSONObject, "setYear");
        int i11 = JSONUtil.getInt(jSONObject, "setMonth");
        int i12 = JSONUtil.getInt(jSONObject, "setDay");
        if (i10 == 0) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(new Date());
            int i13 = gregorianCalendar.get(1);
            int i14 = gregorianCalendar.get(2);
            i12 = gregorianCalendar.get(5);
            i10 = i13;
            i11 = i14;
        }
        boolean z = (jSONObject == null || jSONObject.isNull(AbsoluteConst.JSON_KEY_TITLE)) ? false : true;
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        f fVar = new f(topRuningActivity, AppRuntime.getAppDarkMode(iWebview.getContext()) ? 4 : 0, new e(), i10, i11, i12, i10, i11, i12, z, iWebview, str);
        if (DeviceInfo.sDeviceSdkVer >= 11) {
            if (i4 > 1900) {
                gregorianCalendar2.set(1, i4);
                i2 = 2;
                gregorianCalendar2.set(2, i5);
                i3 = 5;
                gregorianCalendar2.set(5, i6);
                fVar.getDatePicker().setMinDate(gregorianCalendar2.getTimeInMillis());
            } else {
                i2 = 2;
                i3 = 5;
            }
            if (i7 > 1900 && i7 >= i4) {
                gregorianCalendar2.set(1, i7);
                gregorianCalendar2.set(i2, i8);
                gregorianCalendar2.set(i3, i9);
                fVar.getDatePicker().setMaxDate(gregorianCalendar2.getTimeInMillis());
            }
        }
        fVar.setOnDismissListener(new g(iWebview, str));
        if (z) {
            fVar.setTitle(JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_TITLE));
        }
        fVar.show();
        this.c = fVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.c = null;
        this.d = 0;
        this.e = 0;
        this.f = 0;
    }

    private io.dcloud.feature.ui.nativeui.a a(String str, JSONObject jSONObject, String str2, IWebview iWebview) throws JSONException, NumberFormatException {
        String strOptString = jSONObject.has(AbsoluteConst.JSON_KEY_TITLE) ? jSONObject.optString(AbsoluteConst.JSON_KEY_TITLE) : null;
        String strOptString2 = jSONObject.has(BindingXConstants.STATE_CANCEL) ? jSONObject.optString(BindingXConstants.STATE_CANCEL) : null;
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("buttons");
        if (jSONArrayOptJSONArray == null || a(jSONArrayOptJSONArray, iWebview.obtainApp(), str2, iWebview)) {
            return null;
        }
        d dVar = new d(iWebview, str2, str);
        Activity topRuningActivity = RuningAcitvityUtil.getTopRuningActivity(iWebview.getActivity());
        topRuningActivity.setTheme(NativeUIR.ACTS_STYLE_ActionSheetStyleIOS7);
        iWebview.obtainApp().isFullScreen();
        io.dcloud.feature.ui.nativeui.a aVar = new io.dcloud.feature.ui.nativeui.a(topRuningActivity);
        aVar.b(strOptString2);
        aVar.a(strOptString);
        aVar.a(jSONArrayOptJSONArray);
        aVar.a(dVar);
        aVar.a(true);
        aVar.j();
        return aVar;
    }

    private void a(byte b2, String str, JSONObject jSONObject, IWebview iWebview, String str2) {
        String[] strArr;
        String strOptString = jSONObject.optString(AbsoluteConst.JSON_KEY_VERTICAL_ALIGN, "center");
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("buttons");
        if (jSONArrayOptJSONArray != null) {
            int length = jSONArrayOptJSONArray.length();
            strArr = new String[length];
            for (int i2 = 0; i2 < length; i2++) {
                strArr[i2] = JSONUtil.getString(jSONArrayOptJSONArray, i2);
            }
        } else {
            strArr = null;
        }
        String[] strArr2 = strArr;
        if (TextUtils.equals(strOptString, "center")) {
            a(b2, jSONObject.optString(AbsoluteConst.JSON_KEY_TITLE), null, str, strArr2, null, iWebview, str2, 17);
        } else if (TextUtils.equals(strOptString, "top")) {
            a(b2, jSONObject.optString(AbsoluteConst.JSON_KEY_TITLE), null, str, strArr2, null, iWebview, str2, 49);
        } else if (TextUtils.equals(strOptString, "bottom")) {
            a(b2, jSONObject.optString(AbsoluteConst.JSON_KEY_TITLE), null, str, strArr2, null, iWebview, str2, 81);
        }
    }

    private void a(byte b2, String str, String str2, String str3, String[] strArr, String str4, IWebview iWebview, String str5) {
        a(b2, str, str2, str3, strArr, str4, iWebview, str5, 17);
    }

    private void a(byte b2, String str, String str2, String str3, String[] strArr, String str4, IWebview iWebview, String str5, int i2) {
        EditText editText;
        Activity topRuningActivity = RuningAcitvityUtil.getTopRuningActivity(iWebview.getActivity());
        if (b2 == 0) {
            if (a(str3, iWebview.obtainApp(), iWebview, str5)) {
                return;
            }
            DCloudAlertDialog dCloudAlertDialogInitDialogTheme = DialogUtil.initDialogTheme(topRuningActivity, true);
            if (strArr != null && PdrUtil.isEmpty(strArr[0])) {
                strArr[0] = AndroidResources.getString(R.string.ok);
            }
            if (!PdrUtil.isEmpty(str)) {
                dCloudAlertDialogInitDialogTheme.setTitle(str);
            }
            dCloudAlertDialogInitDialogTheme.setCanceledOnTouchOutside(false);
            dCloudAlertDialogInitDialogTheme.setMessage(str3);
            dCloudAlertDialogInitDialogTheme.setButton(-1, strArr[0], new k(dCloudAlertDialogInitDialogTheme, iWebview, str5));
            dCloudAlertDialogInitDialogTheme.setOnKeyListener(new l(dCloudAlertDialogInitDialogTheme, iWebview, str5));
            dCloudAlertDialogInitDialogTheme.show();
            return;
        }
        DCloudAlertDialog dCloudAlertDialogInitDialogTheme2 = DialogUtil.initDialogTheme(topRuningActivity, true);
        dCloudAlertDialogInitDialogTheme2.setMessage(str3);
        if (b2 == 2) {
            editText = new EditText(topRuningActivity);
            if (str4 != null) {
                editText.setHint(str4);
            }
            dCloudAlertDialogInitDialogTheme2.setView(editText);
            Editable text = editText.getText();
            if (text instanceof Spannable) {
                Selection.setSelection(text, text.length());
            }
        } else {
            editText = null;
        }
        if (b2 == 1) {
            Window window = dCloudAlertDialogInitDialogTheme2.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = i2;
            window.setAttributes(attributes);
        }
        if (!PdrUtil.isEmpty(str)) {
            dCloudAlertDialogInitDialogTheme2.setTitle(str);
        }
        dCloudAlertDialogInitDialogTheme2.setCanceledOnTouchOutside(false);
        dCloudAlertDialogInitDialogTheme2.setMessage(str3);
        String[] strArr2 = strArr == null ? new String[]{AndroidResources.getString(R.string.ok), AndroidResources.getString(R.string.cancel)} : strArr;
        if (a(b2, str3, strArr2, iWebview.obtainApp(), iWebview, str5)) {
            return;
        }
        int i3 = 0;
        while (i3 < strArr2.length && i3 < 3) {
            DCloudAlertDialog dCloudAlertDialog = dCloudAlertDialogInitDialogTheme2;
            EditText editText2 = editText;
            a aVar = new a(b2, editText2, iWebview, str5, i3, dCloudAlertDialog);
            int i4 = i3;
            if (i4 == 0) {
                dCloudAlertDialog.setButton(-1, strArr2[i4], aVar);
            } else if (i4 == 1) {
                dCloudAlertDialog.setButton(-2, strArr2[i4], aVar);
            } else if (i4 == 2) {
                dCloudAlertDialog.setButton(-3, strArr2[i4], aVar);
            }
            editText = editText2;
            dCloudAlertDialogInitDialogTheme2 = dCloudAlertDialog;
            i3 = i4 + 1;
        }
        DCloudAlertDialog dCloudAlertDialog2 = dCloudAlertDialogInitDialogTheme2;
        EditText editText3 = editText;
        dCloudAlertDialog2.setOnKeyListener(new b(b2, editText3, iWebview, str5, dCloudAlertDialog2));
        dCloudAlertDialog2.show();
        if (b2 != 2 || Build.FINGERPRINT.toLowerCase(Locale.ENGLISH).contains("flyme")) {
            return;
        }
        DeviceInfo.showIME(editText3);
    }

    private boolean a(String str, IApp iApp, IWebview iWebview, String str2) {
        if (TextUtils.isEmpty(str) || !BaseInfo.ISAMU) {
            return false;
        }
        if (!str.contains("更新") && !str.contains("升级") && !str.contains("版本")) {
            return false;
        }
        try {
            Deprecated_JSUtil.execCallback(iWebview, str2, "{index:0}", JSUtil.OK, true, false);
            a(iApp);
            return true;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private boolean a(byte b2, String str, String[] strArr, IApp iApp, IWebview iWebview, String str2) {
        if (!TextUtils.isEmpty(str) && strArr != null && BaseInfo.ISAMU) {
            for (int i2 = 0; i2 < strArr.length && i2 < 3; i2++) {
                String str3 = strArr[i2];
                if (str3.contains("更新") || str3.contains("升级") || ((str.contains("更新") || str.contains("升级") || str.contains("版本")) && (str3.equals(WXModalUIModule.OK) || str3.equals("是") || str3.equals("确定")))) {
                    try {
                        if (b2 == 2) {
                            Deprecated_JSUtil.execCallback(iWebview, str2, "{index:" + i2 + ",message:" + JSONUtil.toJSONableString("") + Operators.BLOCK_END_STR, JSUtil.OK, true, false);
                            a(iApp);
                            return true;
                        }
                        if (b2 == 1) {
                            Deprecated_JSUtil.execCallback(iWebview, str2, String.valueOf(i2), JSUtil.OK, true, false);
                            a(iApp);
                            return true;
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    private boolean a(JSONArray jSONArray, IApp iApp, String str, IWebview iWebview) throws JSONException {
        String str2;
        IWebview iWebview2;
        String string;
        if (jSONArray != null && BaseInfo.ISAMU) {
            int i2 = 0;
            while (i2 < jSONArray.length()) {
                try {
                    string = jSONArray.getJSONObject(i2).getString(AbsoluteConst.JSON_KEY_TITLE);
                } catch (JSONException e2) {
                    e = e2;
                    str2 = str;
                    iWebview2 = iWebview;
                }
                if (!string.contains("更新") && !string.contains("升级") && !string.contains("版本")) {
                    str2 = str;
                    iWebview2 = iWebview;
                    i2++;
                    iWebview = iWebview2;
                    str = str2;
                }
                str2 = str;
                iWebview2 = iWebview;
                try {
                    Deprecated_JSUtil.execCallback(iWebview2, str2, "" + (i2 + 1), JSUtil.OK, true, false);
                    a(iApp);
                    return true;
                } catch (JSONException e3) {
                    e = e3;
                    e.printStackTrace();
                    i2++;
                    iWebview = iWebview2;
                    str = str2;
                }
            }
        }
        return false;
    }

    private void a(IApp iApp) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", "notify");
        jSONObject.put("appid", iApp.obtainOriginalAppId());
        jSONObject.put("version", iApp.obtainAppVersionName());
        Log.i(AbsoluteConst.HBUILDER_TAG, jSONObject.toString());
    }
}

package io.dcloud.common.adapter.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.PdrR;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.FeatureMessageDispatcher;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.util.AssistInputUtil;
import io.dcloud.common.util.Base64;
import io.dcloud.common.util.DialogUtil;
import io.dcloud.common.util.PdrUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class RecordView implements View.OnClickListener, Handler.Callback {
    private static final int ASSIST_ARROW_LONG_CLICK_INTERVAL_TIMES = 100;
    private static final int ASSIST_ARROW_LONG_CLICK_TIMER_DELAYED = 500;
    private static final int HANDLER_WHAT_LONG_CLICK = 1;
    private static final int HANDLER_WHAT_LONG_CLICK_TIMER = 0;
    public static final int TYPE_ADDRESS = 4;
    public static final int TYPE_COMPANY = 5;
    public static final int TYPE_EMAIL = 2;
    public static final int TYPE_ID = 7;
    public static final int TYPE_NICK = 3;
    public static final int TYPE_PHONE = 1;
    public static final int TYPE_TAX = 6;
    public static final int TYPE_UNKNOW = -1;
    private static final int XORNUMBER = 5;
    int Height;
    int mAnchorY;
    String mAppid;
    private Handler mHandler;
    ViewGroup mMainView;
    RecordData mRecordData;
    boolean mShowed = false;
    private boolean isLongClick = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class RecordData {
        RecordItem[] mRecordItems = new RecordItem[2];
        int mRecordType;

        RecordData() {
        }

        void checkType(int i) throws UnsupportedEncodingException {
            if (i == 4) {
                return;
            }
            if ((i != this.mRecordType || this.mRecordItems[1] == null || hasChanged(i)) && i != -1) {
                this.mRecordType = i;
                this.mRecordItems = new RecordItem[2];
                String recordDatas = RecordView.getRecordDatas(DeviceInfo.sApplicationContext, (String) null, i);
                if (TextUtils.isEmpty(recordDatas)) {
                    return;
                }
                RecordView.this.log("AssistantInput", "RecordView checkType load recordType=" + i + ";value=" + recordDatas);
                String[] strArrSplit = recordDatas.split("&");
                for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                    String[] strArrSplit2 = strArrSplit[i2].split(Operators.SUB);
                    if (!TextUtils.isEmpty(strArrSplit2[0])) {
                        try {
                            this.mRecordItems[i2] = RecordView.this.new RecordItem(URLDecoder.decode(strArrSplit2[0], "utf-8"), System.currentTimeMillis());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        boolean hasChanged(int i) {
            return SP.hasChanged(DeviceInfo.sApplicationContext, "assis_input", true);
        }

        void record(String str, int i) throws UnsupportedEncodingException {
            if (i != 4 && Utils.needRecord(i)) {
                RecordItem recordItem = RecordView.this.new RecordItem(str, System.currentTimeMillis());
                checkType(i);
                StringBuffer stringBuffer = new StringBuffer();
                if (i == 7 || i == 5 || i == 6) {
                    this.mRecordItems[0] = recordItem;
                    try {
                        stringBuffer.append(URLEncoder.encode(recordItem.mContent, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    RecordItem recordItem2 = this.mRecordItems[0];
                    if (recordItem2 != null && !TextUtils.equals(str, recordItem2.mContent)) {
                        RecordItem[] recordItemArr = this.mRecordItems;
                        recordItemArr[1] = recordItemArr[0];
                    }
                    this.mRecordItems[0] = recordItem;
                    if (!TextUtils.isEmpty(recordItem.mContent)) {
                        try {
                            stringBuffer.append(URLEncoder.encode(this.mRecordItems[0].mContent, "utf-8"));
                            RecordItem recordItem3 = this.mRecordItems[1];
                            if (recordItem3 != null && !TextUtils.isEmpty(recordItem3.mContent)) {
                                stringBuffer.append("&");
                                stringBuffer.append(URLEncoder.encode(this.mRecordItems[1].mContent, "utf-8"));
                            }
                        } catch (UnsupportedEncodingException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                RecordView.setRcordDatas(DeviceInfo.sApplicationContext, (String) null, i, stringBuffer.toString());
                RecordView.this.log("AssistantInput", "RecordView record recordType=" + i + ";value=" + stringBuffer.toString());
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class RecordItem {
        String mContent;
        long mTime;

        RecordItem(String str, long j) {
            this.mContent = str;
            this.mTime = j;
        }

        public String toString() {
            return this.mContent + Operators.SUB + this.mTime;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static class Utils {
        Utils() {
        }

        static int convertInt(String str) {
            if (TextUtils.equals(str, "nick")) {
                return 3;
            }
            if (TextUtils.equals(str, "address")) {
                return 4;
            }
            if (TextUtils.equals(str, Constants.Value.TEL)) {
                return 1;
            }
            if (TextUtils.equals(str, "email")) {
                return 2;
            }
            if (TextUtils.equals(str, "none")) {
                return -1;
            }
            if ("company".equals(str)) {
                return 5;
            }
            if ("tax".equals(str)) {
                return 6;
            }
            return "id".equals(str) ? 7 : -1;
        }

        static boolean needRecord(int i) {
            if (i == 1) {
                if (AssistInputUtil.useAssistSettingPhone()) {
                    return false;
                }
            } else if (i == 5) {
                if (AssistInputUtil.useAssistSettingCompany()) {
                    return false;
                }
            } else if (i == 6) {
                if (AssistInputUtil.useAssistSettingTax()) {
                    return false;
                }
            } else if (i == 7) {
                if (AssistInputUtil.useAssistSettingId()) {
                    return false;
                }
            } else if (i == 3) {
                if (AssistInputUtil.useAssistSettingName()) {
                    return false;
                }
            } else if (i == 2 && AssistInputUtil.useAssistSettingEmail()) {
                return false;
            }
            return true;
        }
    }

    RecordView(Context context, ViewGroup viewGroup, String str) {
        this.mMainView = null;
        this.mHandler = null;
        this.mRecordData = null;
        this.mHandler = new Handler(this);
        this.mAppid = str;
        this.Height = dp2px(context, 46.0f);
        this.mRecordData = new RecordData();
        FrameLayout frameLayout = new FrameLayout(context);
        this.mMainView = frameLayout;
        frameLayout.addView(initView2(context, frameLayout));
        frameLayout.addView(initView1(context, frameLayout));
        viewGroup.addView(frameLayout, new FrameLayout.LayoutParams(-1, this.Height));
    }

    private boolean checkLocationPermission(Activity activity) {
        return PermissionUtil.checkLocationPermission(activity);
    }

    private boolean checkLocationService(Activity activity) {
        return PermissionUtil.checkLocationService(activity);
    }

    static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static String getAssisBundleData(String str) {
        return getAssisBundleData(DeviceInfo.sApplicationContext, str);
    }

    public static String getRecordDatas(String str, String str2) {
        return getRecordDatas0(DeviceInfo.sApplicationContext, str, str2);
    }

    private static String getRecordDatas0(Context context, String str, String str2) {
        return "address_home".equals(str2) ? AssistInputUtil.getHomeAddress(AdaWebview.sCustomeizedInputConnection.mWebview.getContext()) : "address_work".equals(str2) ? AssistInputUtil.getWorkAddress(AdaWebview.sCustomeizedInputConnection.mWebview.getContext()) : getRecordDatas(context, str, Utils.convertInt(str2));
    }

    private TextView getTextView(View view, String str) {
        View view2 = getView(view, str);
        if (view2 instanceof TextView) {
            return (TextView) view2;
        }
        return null;
    }

    private View getView(View view, String str) {
        return view.findViewById(PdrR.getInt(view.getContext(), "id", str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAssistInputPreviousOrNextButtOnTouch(MotionEvent motionEvent, int i) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isLongClick = false;
            Message messageObtain = Message.obtain();
            messageObtain.what = 0;
            messageObtain.obj = Integer.valueOf(i);
            this.mHandler.sendMessageDelayed(messageObtain, 500L);
            return;
        }
        if (action != 1) {
            return;
        }
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        if (!this.isLongClick) {
            AdaWebview.sCustomeizedInputConnection.update(i);
        }
        this.isLongClick = false;
    }

    private void initArrowView(View view) {
        if (view == null) {
            return;
        }
        View view2 = getView(view, "dcloud_record_arrow_left_layout");
        if (view2 != null) {
            view2.setClickable(true);
            view2.setLongClickable(true);
            view2.setOnTouchListener(new View.OnTouchListener() { // from class: io.dcloud.common.adapter.ui.RecordView.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view3, MotionEvent motionEvent) {
                    RecordView.this.handleAssistInputPreviousOrNextButtOnTouch(motionEvent, -1);
                    return true;
                }
            });
        }
        View view3 = getView(view, "dcloud_record_arrow_right_layout");
        if (view3 != null) {
            view3.setClickable(true);
            view3.setLongClickable(true);
            view3.setOnTouchListener(new View.OnTouchListener() { // from class: io.dcloud.common.adapter.ui.RecordView.2
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view4, MotionEvent motionEvent) {
                    RecordView.this.handleAssistInputPreviousOrNextButtOnTouch(motionEvent, 1);
                    return true;
                }
            });
        }
    }

    private ViewGroup initView1(Context context, ViewGroup viewGroup) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(PdrR.getInt(context, Constants.Name.LAYOUT, "dcloud_record_default"), (ViewGroup) null);
        getView(relativeLayout, "dcloud_record_scroll_view").setHorizontalScrollBarEnabled(false);
        getView(relativeLayout, "dcloud_record_view_1").setOnClickListener(this);
        getView(relativeLayout, "dcloud_record_view_2").setOnClickListener(this);
        initArrowView(relativeLayout);
        return relativeLayout;
    }

    private ViewGroup initView2(Context context, ViewGroup viewGroup) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(PdrR.getInt(context, Constants.Name.LAYOUT, "dcloud_record_address"), (ViewGroup) null);
        getView(relativeLayout, "dcloud_record_scroll_view").setHorizontalScrollBarEnabled(false);
        getView(relativeLayout, "dcloud_record_address_view_1").setOnClickListener(this);
        getView(relativeLayout, "dcloud_record_address_view_2").setOnClickListener(this);
        getView(relativeLayout, "dcloud_record_address_view_3").setOnClickListener(this);
        initArrowView(relativeLayout);
        return relativeLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str, String str2) {
        Logger.e(str, str2 + ";this=" + this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCurrentLocation(final TextView textView) {
        final Activity activity = AdaWebview.sCustomeizedInputConnection.mWebview.getActivity();
        if (!checkLocationPermission(activity)) {
            showConfrim(activity, activity.getString(R.string.dcloud_geo_open_permissions), new String[]{activity.getString(R.string.dcloud_common_set_up), activity.getString(R.string.dcloud_common_cancel)}, new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.ui.RecordView.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -2) {
                        AdaWebview.sCustomeizedInputConnection.mWebview.obtainApp().registerSysEventListener(new ISysEventListener() { // from class: io.dcloud.common.adapter.ui.RecordView.5.1
                            @Override // io.dcloud.common.DHInterface.ISysEventListener
                            public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                                IApp iAppObtainApp = AdaWebview.sCustomeizedInputConnection.mWebview.obtainApp();
                                ISysEventListener.SysEventType sysEventType2 = ISysEventListener.SysEventType.onResume;
                                iAppObtainApp.unregisterSysEventListener(this, sysEventType2);
                                if (sysEventType != sysEventType2) {
                                    return false;
                                }
                                AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                                RecordView.this.requestCurrentLocation(textView);
                                return false;
                            }
                        }, ISysEventListener.SysEventType.onResume);
                        try {
                            PermissionUtil.goSafeCenter(activity);
                        } catch (ActivityNotFoundException e) {
                            Logger.e("AssistantInput", "checkLocationService ActivityNotFoundException =" + e);
                            e.printStackTrace();
                        } catch (Exception e2) {
                            Logger.e("AssistantInput", "Exception =" + e2);
                        }
                    }
                }
            });
        } else if (checkLocationService(activity)) {
            requestCurrentLocation0(textView);
        } else {
            showConfrim(activity, activity.getString(R.string.dcloud_geo_open_service), new String[]{activity.getString(R.string.dcloud_common_set_up), activity.getString(R.string.dcloud_common_cancel)}, new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.ui.RecordView.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -2) {
                        AdaWebview.sCustomeizedInputConnection.mWebview.obtainApp().registerSysEventListener(new ISysEventListener() { // from class: io.dcloud.common.adapter.ui.RecordView.4.1
                            @Override // io.dcloud.common.DHInterface.ISysEventListener
                            public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                                IApp iAppObtainApp = AdaWebview.sCustomeizedInputConnection.mWebview.obtainApp();
                                ISysEventListener.SysEventType sysEventType2 = ISysEventListener.SysEventType.onResume;
                                iAppObtainApp.unregisterSysEventListener(this, sysEventType2);
                                if (sysEventType != sysEventType2) {
                                    return false;
                                }
                                AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                RecordView.this.requestCurrentLocation(textView);
                                return false;
                            }
                        }, ISysEventListener.SysEventType.onResume);
                        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                        try {
                            activity.startActivity(intent);
                            RecordView.this.log("AssistantInput", "checkLocationService successful " + Build.MODEL + "intent=" + intent);
                        } catch (ActivityNotFoundException e) {
                            Logger.e("AssistantInput", "checkLocationService ActivityNotFoundException =" + e);
                            e.printStackTrace();
                        } catch (Exception e2) {
                            Logger.e("Permission", "Exception =" + e2);
                        }
                    }
                }
            });
        }
    }

    private void requestCurrentLocation0(final TextView textView) {
        textView.setText(R.string.dcloud_geo_loading);
        FeatureMessageDispatcher.StrongMessageListener strongMessageListener = new FeatureMessageDispatcher.StrongMessageListener("record_address") { // from class: io.dcloud.common.adapter.ui.RecordView.6
            @Override // io.dcloud.common.DHInterface.FeatureMessageDispatcher.StrongMessageListener, io.dcloud.common.DHInterface.FeatureMessageDispatcher.MessageListener
            public void onReceiver(Object obj) {
                if (!(obj instanceof String)) {
                    textView.setText(R.string.dcloud_geo_current_address);
                } else {
                    AdaWebview.sCustomeizedInputConnection.setText((String) obj);
                    textView.setText(R.string.dcloud_geo_current_address);
                }
            }
        };
        FeatureMessageDispatcher.registerListener(strongMessageListener);
        IWebview iWebview = AdaWebview.sCustomeizedInputConnection.mWebview;
        AbsMgr absMgrObtainWindowMgr = iWebview.obtainFrameView().obtainWindowMgr();
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(AnonymousClass6.class.getName() + "_" + strongMessageListener.hashCode());
        jSONArray.put(strongMessageListener.hashCode());
        jSONArray.put(true);
        jSONArray.put("null");
        jSONArray.put("");
        jSONArray.put("null");
        jSONArray.put("null");
        jSONArray.put("null");
        absMgrObtainWindowMgr.processEvent(IMgr.MgrType.FeatureMgr, 1, new Object[]{iWebview, IFeature.F_GEOLOCATION, "getCurrentPosition", jSONArray});
    }

    public static void setAssisBundleData(Context context, String str, String str2) {
        SP.setBundleData(context, "assis_input", str, str2, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setRcordDatas(Context context, String str, int i, String str2) {
        if (i != -1) {
            String strEncodeString = Base64.encodeString(str2, true, 5);
            if (PdrUtil.isEmpty(strEncodeString)) {
                strEncodeString = "";
            }
            setAssisBundleData(context, "_input_text" + i, strEncodeString);
        }
    }

    private static void setRcordDatas0(Context context, String str, String str2, String str3) {
        setRcordDatas(context, str, Utils.convertInt(str2), str3);
    }

    private void showConfrim(Activity activity, String str, String[] strArr, DialogInterface.OnClickListener onClickListener) {
        DCloudAlertDialog dCloudAlertDialogInitDialogTheme = DialogUtil.initDialogTheme(activity, true);
        dCloudAlertDialogInitDialogTheme.setCanceledOnTouchOutside(false);
        dCloudAlertDialogInitDialogTheme.setMessage(str);
        dCloudAlertDialogInitDialogTheme.setButton(-2, strArr[0], onClickListener);
        dCloudAlertDialogInitDialogTheme.setButton(-1, strArr[1], onClickListener);
        dCloudAlertDialogInitDialogTheme.show();
    }

    void conceal() {
    }

    void display() {
    }

    public synchronized void dispose() {
        this.isLongClick = false;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.mHandler = null;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            this.isLongClick = true;
            Message messageObtain = Message.obtain();
            messageObtain.what = 1;
            messageObtain.obj = message.obj;
            this.mHandler.sendMessageDelayed(messageObtain, 100L);
            return false;
        }
        if (i != 1 || !this.isLongClick) {
            return false;
        }
        Object obj = message.obj;
        if (obj instanceof Integer) {
            AdaWebview.sCustomeizedInputConnection.update(((Integer) obj).intValue());
        }
        Message messageObtain2 = Message.obtain();
        messageObtain2.what = 1;
        messageObtain2.obj = message.obj;
        this.mHandler.sendMessageDelayed(messageObtain2, 100L);
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        Context context = view.getContext();
        if (id == PdrR.getInt(context, "id", "dcloud_record_address_view_1")) {
            requestCurrentLocation((TextView) view);
            return;
        }
        if (id == PdrR.getInt(context, "id", "dcloud_record_address_view_2")) {
            AdaWebview.sCustomeizedInputConnection.setText(getRecordDatas(this.mMainView.getContext(), this.mAppid, "address_home"));
        } else if (id != PdrR.getInt(context, "id", "dcloud_record_address_view_3")) {
            AdaWebview.sCustomeizedInputConnection.setText(((TextView) view).getText().toString());
        } else {
            AdaWebview.sCustomeizedInputConnection.setText(getRecordDatas(this.mMainView.getContext(), this.mAppid, "address_work"));
        }
    }

    void record(String str, int i) {
        if (i != -1) {
            this.mRecordData.record(str, i);
        }
    }

    void update(final int i, final int i2) {
        this.mMainView.post(new Runnable() { // from class: io.dcloud.common.adapter.ui.RecordView.3
            @Override // java.lang.Runnable
            public void run() throws UnsupportedEncodingException {
                RecordView.this.update0(i, i2);
            }
        });
    }

    void update0(int i, int i2) throws UnsupportedEncodingException {
        boolean z;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mMainView.getLayoutParams();
        layoutParams.topMargin = i - this.Height;
        layoutParams.bottomMargin = i;
        this.mMainView.setLayoutParams(layoutParams);
        this.mAnchorY = i;
        boolean z2 = true;
        boolean z3 = i2 != -1;
        this.mMainView.setVisibility(z3 ? 0 : 8);
        this.mMainView.bringToFront();
        this.mRecordData.checkType(i2);
        View childAt = this.mMainView.getChildAt(0);
        View childAt2 = this.mMainView.getChildAt(1);
        if (i2 == 4) {
            childAt.setVisibility(0);
            childAt2.setVisibility(8);
            getView(childAt, "dcloud_record_address_view_1").setVisibility(0);
            getTextView(childAt, "dcloud_record_address_view_1").setText(R.string.dcloud_current_address);
            String recordDatas = getRecordDatas(this.mMainView.getContext(), this.mAppid, "address_home");
            getView(childAt, "dcloud_record_line_1").setVisibility(TextUtils.isEmpty(recordDatas) ? 8 : 0);
            getView(childAt, "dcloud_record_address_view_2").setVisibility(TextUtils.isEmpty(recordDatas) ? 8 : 0);
            String recordDatas2 = getRecordDatas(this.mMainView.getContext(), this.mAppid, "address_work");
            getView(childAt, "dcloud_record_line_2").setVisibility(TextUtils.isEmpty(recordDatas2) ? 8 : 0);
            getView(childAt, "dcloud_record_address_view_3").setVisibility(TextUtils.isEmpty(recordDatas2) ? 8 : 0);
        } else {
            childAt2.setVisibility(0);
            childAt.setVisibility(8);
            if (this.mRecordData.mRecordItems[0] != null) {
                getView(childAt2, "dcloud_record_view_1").setVisibility(z3 ? 0 : 8);
                getTextView(childAt2, "dcloud_record_view_1").setText(this.mRecordData.mRecordItems[0].mContent);
                z = true;
            } else {
                this.mMainView.setVisibility(8);
                z = false;
            }
            if (this.mRecordData.mRecordItems[1] != null) {
                getView(childAt2, "dcloud_record_line_1").setVisibility(z3 ? 0 : 8);
                getView(childAt2, "dcloud_record_view_2").setVisibility(z3 ? 0 : 8);
                getTextView(childAt2, "dcloud_record_view_2").setText(this.mRecordData.mRecordItems[1].mContent);
            } else {
                getView(childAt2, "dcloud_record_line_1").setVisibility(8);
                getView(childAt2, "dcloud_record_view_2").setVisibility(8);
            }
            z2 = z;
        }
        if (z3 && z2) {
            display();
        } else {
            conceal();
        }
    }

    public static String getAssisBundleData(Context context, String str) {
        return SP.getBundleData(context, "assis_input", str, true);
    }

    public static String getRecordDatas(Context context, String str, String str2) {
        return getRecordDatas0(context, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getRecordDatas(Context context, String str, int i) {
        if (i == -1) {
            return null;
        }
        return Base64.decodeString(getAssisBundleData(context, "_input_text" + i), true, 5);
    }

    public static void setRcordDatas(Context context, String str, String str2, String str3) {
        setRcordDatas0(context, str, str2, str3);
    }
}

package io.src.dcloud.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.RuningAcitvityUtil;
import java.io.File;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCloudBaseActivity extends AppCompatActivity implements IReflectAble {
    public static String loadDexDirectInfo;
    private AlertDialog mDebugDialog;
    private DebugSocketStatusReceiver mDebugSocketStatusReceiver;
    private Dialog mLoadingPD;
    private SocketCheckReceiver mSocketCheckReceiver;
    private String preDebuggingInfoForWeexDebugging2;
    private WeexDebugStartReceiver weexDebugStartReceiver;
    public Activity that = this;
    private int loadingSecond = 0;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class DebugSocketStatusReceiver extends BroadcastReceiver {
        public DebugSocketStatusReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            DCloudBaseActivity.this.a(intent.getStringExtra(AbsoluteConst.WEEX_DEBUG_CONNECT_BROADCAST_KEY), true);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class SocketCheckReceiver extends BroadcastReceiver {
        public SocketCheckReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra(AbsoluteConst.WEEX_DEBUG_PING_IP_KEY);
            if (DCloudBaseActivity.this.mLoadingPD != null) {
                DCloudBaseActivity.this.loadingSecond = 0;
                ((TextView) DCloudBaseActivity.this.mLoadingPD.findViewById(R.id.debugTV)).setText(context.getString(R.string.dcloud_debug_connecting) + "\n(" + stringExtra + Operators.BRACKET_END_STR);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class WeexDebugStartReceiver extends BroadcastReceiver {
        public WeexDebugStartReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            DCloudBaseActivity.this.preDebuggingInfoForWeexDebugging2 = intent.getStringExtra("debugging_info");
            DCloudBaseActivity.this.e();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            String str;
            boolean z;
            Object objInvokeMethod = PlatformUtil.invokeMethod("io.dcloud.feature.weex.WeexDevtoolImpl", "getDebugSocketStatus", null, null, null);
            while (true) {
                str = (String) objInvokeMethod;
                if (str != null) {
                    z = false;
                    break;
                } else if (DCloudBaseActivity.this.loadingSecond >= 6) {
                    z = true;
                    break;
                } else {
                    SystemClock.sleep(1000L);
                    DCloudBaseActivity.access$208(DCloudBaseActivity.this);
                    objInvokeMethod = PlatformUtil.invokeMethod("io.dcloud.feature.weex.WeexDevtoolImpl", "getDebugSocketStatus", null, null, null);
                }
            }
            DCloudBaseActivity.this.a(str, false, z);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        final /* synthetic */ String a;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements DialogInterface.OnClickListener {
            a() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Android/data/" + RuningAcitvityUtil.getAppName(DCloudBaseActivity.this) + "/apps/" + BaseInfo.sDefaultBootApp + "/www/__nvue_debug__");
                if (file.exists()) {
                    file.delete();
                }
                DCloudBaseActivity.this.startActivity(Intent.makeRestartActivityTask(DCloudBaseActivity.this.getPackageManager().getLaunchIntentForPackage(DCloudBaseActivity.this.getPackageName()).getComponent()));
                Runtime.getRuntime().exit(0);
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.src.dcloud.adapter.DCloudBaseActivity$b$b, reason: collision with other inner class name */
        class DialogInterfaceOnClickListenerC0073b implements DialogInterface.OnClickListener {
            DialogInterfaceOnClickListenerC0073b() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                int iB = DCloudBaseActivity.this.b();
                if (iB == 1) {
                    DCloudBaseActivity.this.startActivity(Intent.makeRestartActivityTask(DCloudBaseActivity.this.getPackageManager().getLaunchIntentForPackage(DCloudBaseActivity.this.getPackageName()).getComponent()));
                    Runtime.getRuntime().exit(0);
                } else if (iB == 2 && !PdrUtil.isEmpty(DCloudBaseActivity.this.preDebuggingInfoForWeexDebugging2)) {
                    DCloudBaseActivity.this.f();
                    Intent intent = new Intent(AbsoluteConst.WEEX_DEBUG_START_WAITING_CONNECT);
                    intent.putExtra("debugging_info", DCloudBaseActivity.this.preDebuggingInfoForWeexDebugging2);
                    LocalBroadcastManager.getInstance(DCloudBaseActivity.this.getApplication()).sendBroadcast(intent);
                    PlatformUtil.invokeMethod("io.dcloud.feature.weex.WeexDevtoolImpl", "initDebugEnvironment", null, new Class[]{Application.class, String.class}, new Object[]{DCloudBaseActivity.this.getApplication(), DCloudBaseActivity.this.preDebuggingInfoForWeexDebugging2});
                }
            }
        }

        b(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            DCloudBaseActivity.this.a();
            if (DCloudBaseActivity.this.mDebugDialog == null) {
                DCloudBaseActivity.this.mDebugDialog = new AlertDialog.Builder(DCloudBaseActivity.this).setTitle("").setMessage(this.a).setPositiveButton(R.string.dcloud_debug_reconnection_service, new DialogInterfaceOnClickListenerC0073b()).setNegativeButton(R.string.dcloud_common_cancel, new a()).setCancelable(false).show();
            } else {
                if (DCloudBaseActivity.this.mDebugDialog.isShowing()) {
                    return;
                }
                DCloudBaseActivity.this.mDebugDialog.setMessage(this.a);
                DCloudBaseActivity.this.mDebugDialog.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        Dialog dialog = this.mLoadingPD;
        if (dialog != null) {
            dialog.dismiss();
            this.mLoadingPD = null;
        }
    }

    static /* synthetic */ int access$208(DCloudBaseActivity dCloudBaseActivity) {
        int i = dCloudBaseActivity.loadingSecond;
        dCloudBaseActivity.loadingSecond = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int b() {
        return c() ? 1 : 2;
    }

    private boolean c() {
        String path;
        if (Build.VERSION.SDK_INT <= 28) {
            path = Environment.getExternalStorageDirectory() + File.separator + "Android/data/" + RuningAcitvityUtil.getAppName(this);
        } else {
            path = getExternalFilesDir(null).getParentFile().getPath();
        }
        return new File(path + "/apps/" + BaseInfo.sDefaultBootApp + "/www/__nvue_debug__").exists();
    }

    private void d() {
        Dialog dialog = new Dialog(this);
        this.mLoadingPD = dialog;
        dialog.getWindow().setGravity(17);
        this.mLoadingPD.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mLoadingPD.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.mLoadingPD.setCancelable(false);
        this.mLoadingPD.show();
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.dcloud_weex_debug_progress, (ViewGroup) null);
        TextView textView = (TextView) viewGroup.findViewById(R.id.debugTV);
        String str = (String) PlatformUtil.invokeMethod("io.dcloud.feature.weex.WeexDevtoolImpl", "getCurrentPingIP", null, null, null);
        if (str != null) {
            textView.setText(getString(R.string.dcloud_debug_connecting) + "\n(" + str + Operators.BRACKET_END_STR);
        }
        this.mLoadingPD.setContentView(viewGroup);
        new Thread(new a()).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        if (BaseInfo.SyncDebug) {
            this.mDebugSocketStatusReceiver = new DebugSocketStatusReceiver();
            LocalBroadcastManager.getInstance(this).registerReceiver(this.mDebugSocketStatusReceiver, new IntentFilter(AbsoluteConst.WEEX_DEBUG_CONNECT_BROADCAST));
            this.mSocketCheckReceiver = new SocketCheckReceiver();
            LocalBroadcastManager.getInstance(this).registerReceiver(this.mSocketCheckReceiver, new IntentFilter(AbsoluteConst.WEEX_DEBUG_PING_BROADCAST));
            d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        if (BaseInfo.SyncDebug && this.mDebugSocketStatusReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mDebugSocketStatusReceiver);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mSocketCheckReceiver);
            this.mDebugSocketStatusReceiver = null;
            this.mSocketCheckReceiver = null;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (BaseInfo.SyncDebug) {
            this.weexDebugStartReceiver = new WeexDebugStartReceiver();
            LocalBroadcastManager.getInstance(this).registerReceiver(this.weexDebugStartReceiver, new IntentFilter(AbsoluteConst.WEEX_DEBUG_START_WAITING_CONNECT));
            loadDexDirectInfo = getIntent().getStringExtra("load_dex_direct_info");
            if (c()) {
                e();
            }
            AppRuntime.loadDex(getApplication());
            AppRuntime.initWeex(getApplication());
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.mDebugSocketStatusReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mDebugSocketStatusReceiver);
        }
        if (this.mSocketCheckReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mSocketCheckReceiver);
        }
        if (this.weexDebugStartReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.weexDebugStartReceiver);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onNewIntentImpl(intent);
    }

    public void onNewIntentImpl(Intent intent) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, boolean z, boolean z2) {
        if (z2) {
            a();
        }
        a(str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, boolean z) {
        String str2;
        if (AbsoluteConst.WEEX_DEBUG_CONNECT_SUCCESS.equalsIgnoreCase(str)) {
            a();
            AlertDialog alertDialog = this.mDebugDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                return;
            }
            return;
        }
        if (z) {
            str2 = getString(R.string.dcloud_debug_break_off_reason) + "\n";
        } else {
            str2 = getString(R.string.dcloud_debug_cannot_connect) + "\n";
        }
        new Handler(Looper.getMainLooper()).post(new b(str2 + getString(R.string.dcloud_debug_possible_causes)));
    }
}

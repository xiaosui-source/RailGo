package io.dcloud.common.util;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import io.dcloud.PdrR;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.adapter.util.AndroidResources;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DialogUtil {
    public static DCloudAlertDialog initDialogTheme(Activity activity, boolean z) {
        int i;
        boolean zIsNavigationBarExist = PdrUtil.isNavigationBarExist(activity);
        int i2 = PdrR.STREAMAPP_DELETE_THEME;
        if (AppRuntime.getAppDarkMode(activity)) {
            int i3 = PdrR.STREAMAPP_DELETE_DARK_THEME;
            i = DCloudAlertDialog.DARK_THEME;
        } else {
            i = DCloudAlertDialog.LIGHT_THEME;
        }
        return z ? new DCloudAlertDialog(activity, i, zIsNavigationBarExist) : new DCloudAlertDialog(activity, i, zIsNavigationBarExist);
    }

    public static void showAlertDialog(Activity activity, String str, String str2, String str3, final View.OnClickListener onClickListener, final View.OnClickListener onClickListener2, DialogInterface.OnCancelListener onCancelListener, DialogInterface.OnDismissListener onDismissListener, boolean z, int i, int i2, int i3) {
        if (activity == null) {
            return;
        }
        final AlertDialog alertDialogInitDialogTheme = initDialogTheme(activity);
        View viewInflate = LayoutInflater.from(activity).inflate(PdrR.STREAMAPP_CUSTOM_ALERT_DIALOG_LAYOUT, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(PdrR.STREAMAPP_CUSTOM_ALERT_DIALOG_TITLE);
        Button button = (Button) viewInflate.findViewById(PdrR.STREAMAPP_CUSTOM_ALERT_DIALOG_SURE);
        Button button2 = (Button) viewInflate.findViewById(PdrR.STREAMAPP_CUSTOM_ALERT_DIALOG_CANCEL);
        if (!TextUtils.isEmpty(str)) {
            textView.setText(str);
            textView.setGravity(i);
        }
        if (!z) {
            viewInflate.findViewById(PdrR.STREAMAPP_CUSTOM_ALERT_DIALOG_CUSTOM_LAYOUT).setVisibility(8);
        }
        if (!TextUtils.isEmpty(str2)) {
            button.setText(str2);
        }
        if (!TextUtils.isEmpty(str3)) {
            button2.setText(str3);
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: io.dcloud.common.util.DialogUtil.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                alertDialogInitDialogTheme.dismiss();
                View.OnClickListener onClickListener3 = onClickListener;
                if (onClickListener3 != null) {
                    onClickListener3.onClick(view);
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: io.dcloud.common.util.DialogUtil.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                alertDialogInitDialogTheme.dismiss();
                View.OnClickListener onClickListener3 = onClickListener2;
                if (onClickListener3 != null) {
                    onClickListener3.onClick(view);
                }
            }
        });
        if (onCancelListener != null) {
            alertDialogInitDialogTheme.setOnCancelListener(onCancelListener);
        }
        if (onDismissListener != null) {
            alertDialogInitDialogTheme.setOnDismissListener(onDismissListener);
        }
        alertDialogInitDialogTheme.show();
        alertDialogInitDialogTheme.setContentView(viewInflate);
        Window window = alertDialogInitDialogTheme.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        window.setGravity(i2);
        window.setLayout(i3, attributes.height);
    }

    public static void showConfirm(Activity activity, String str, String str2, String[] strArr, final ICallBack iCallBack) {
        final DCloudAlertDialog dCloudAlertDialog = new DCloudAlertDialog(activity, AppRuntime.getAppDarkMode(activity) ? DCloudAlertDialog.DARK_THEME : DCloudAlertDialog.LIGHT_THEME, PdrUtil.isNavigationBarExist(activity));
        dCloudAlertDialog.setTitle(str);
        dCloudAlertDialog.setCanceledOnTouchOutside(false);
        dCloudAlertDialog.setMessage(str2);
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.DialogUtil.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == -2) {
                    dCloudAlertDialog.cancel();
                    dCloudAlertDialog.dismiss();
                } else if (i != -3 && i == -1) {
                    dCloudAlertDialog.dismiss();
                }
                iCallBack.onCallBack(i, null);
            }
        };
        dCloudAlertDialog.setButton(-1, strArr[0], onClickListener);
        dCloudAlertDialog.setButton(-2, strArr[1], onClickListener);
        dCloudAlertDialog.show();
    }

    public static void showDialog(Activity activity, String str, String str2, String[] strArr) {
        final AlertDialog alertDialogInitDialogTheme = initDialogTheme(activity);
        if (strArr != null && PdrUtil.isEmpty(strArr[0])) {
            strArr[0] = AndroidResources.getString(R.string.ok);
        }
        if (!PdrUtil.isEmpty(str)) {
            alertDialogInitDialogTheme.setTitle(str);
        }
        alertDialogInitDialogTheme.setCanceledOnTouchOutside(true);
        alertDialogInitDialogTheme.setMessage(str2);
        alertDialogInitDialogTheme.setButton(-1, strArr[0], new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.DialogUtil.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialogInitDialogTheme.dismiss();
            }
        });
        alertDialogInitDialogTheme.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.common.util.DialogUtil.3
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 1 || i != 4) {
                    return false;
                }
                alertDialogInitDialogTheme.dismiss();
                return true;
            }
        });
        alertDialogInitDialogTheme.show();
    }

    public static AlertDialog initDialogTheme(Activity activity) {
        int i;
        boolean zIsNavigationBarExist = PdrUtil.isNavigationBarExist(activity);
        int i2 = PdrR.STREAMAPP_DELETE_THEME;
        if (AppRuntime.getAppDarkMode(activity)) {
            int i3 = PdrR.STREAMAPP_DELETE_DARK_THEME;
            i = DCloudAlertDialog.DARK_THEME;
        } else {
            i = DCloudAlertDialog.LIGHT_THEME;
        }
        return new DCloudAlertDialog(activity, i, zIsNavigationBarExist);
    }
}

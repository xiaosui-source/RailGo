package io.dcloud.common.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Process;
import android.view.KeyEvent;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import io.dcloud.PdrR;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IWebview;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ErrorDialogUtil {
    private static ArrayList<String> list = new ArrayList<>();

    public static void checkAppKeyErrorTips(Activity activity) {
        String string = activity.getString(R.string.dcloud_offline_fail_tips);
        DCloudAlertDialog dCloudAlertDialog = new DCloudAlertDialog(activity, AppRuntime.getAppDarkMode(activity) ? DCloudAlertDialog.DARK_THEME : DCloudAlertDialog.LIGHT_THEME, PdrUtil.isNavigationBarExist(activity));
        dCloudAlertDialog.setTitle(string);
        dCloudAlertDialog.setButton(-2, activity.getString(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Process.killProcess(Process.myPid());
            }
        });
        dCloudAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.8
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return i == 4;
            }
        });
        dCloudAlertDialog.setCancelable(false);
        dCloudAlertDialog.setCanceledOnTouchOutside(false);
        dCloudAlertDialog.show();
    }

    public static Dialog getLossDialog(final Activity activity, String str, final String str2, final String str3) {
        if (list.contains(str3)) {
            return null;
        }
        list.add(str3);
        DCloudAlertDialog dCloudAlertDialog = new DCloudAlertDialog(activity, PdrR.FEATURE_LOSS_STYLE, PdrUtil.isNavigationBarExist(activity));
        dCloudAlertDialog.setTitle("HTML5+ Runtime");
        dCloudAlertDialog.setIcon(android.R.drawable.ic_dialog_info);
        dCloudAlertDialog.setMessage(str);
        dCloudAlertDialog.setButton(-1, activity.getString(R.string.dcloud_common_view_details), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(str2));
                activity.startActivity(intent);
            }
        });
        dCloudAlertDialog.setButton(-2, activity.getString(R.string.dcloud_common_ignore), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dCloudAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.3
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                ErrorDialogUtil.list.remove(str3);
            }
        });
        return dCloudAlertDialog;
    }

    public static void showErrorTipsAlert(Activity activity, String str, DialogInterface.OnClickListener onClickListener) {
        DCloudAlertDialog dCloudAlertDialog = new DCloudAlertDialog(activity, AppRuntime.getAppDarkMode(activity) ? DCloudAlertDialog.DARK_THEME : DCloudAlertDialog.LIGHT_THEME, PdrUtil.isNavigationBarExist(activity));
        dCloudAlertDialog.setMessage(str);
        dCloudAlertDialog.setButton(-2, activity.getString(android.R.string.ok), onClickListener);
        dCloudAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.9
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return i == 4;
            }
        });
        dCloudAlertDialog.setCancelable(false);
        dCloudAlertDialog.setCanceledOnTouchOutside(false);
        dCloudAlertDialog.show();
    }

    public static Dialog getLossDialog(final IWebview iWebview, String str, final String str2, final String str3) {
        if (list.contains(str3)) {
            return null;
        }
        list.add(str3);
        if (iWebview == null || iWebview.getActivity() == null) {
            return null;
        }
        DCloudAlertDialog dCloudAlertDialog = new DCloudAlertDialog(iWebview.getActivity(), PdrR.FEATURE_LOSS_STYLE, PdrUtil.isNavigationBarExist(iWebview.getActivity()));
        dCloudAlertDialog.setTitle("HTML5+ Runtime");
        dCloudAlertDialog.setIcon(android.R.drawable.ic_dialog_info);
        dCloudAlertDialog.setMessage(str);
        dCloudAlertDialog.setButton(-1, iWebview.getContext().getString(R.string.dcloud_common_view_details), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(str2));
                iWebview.getActivity().startActivity(intent);
            }
        });
        dCloudAlertDialog.setButton(-2, iWebview.getContext().getString(R.string.dcloud_common_ignore), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dCloudAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: io.dcloud.common.util.ErrorDialogUtil.6
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                ErrorDialogUtil.list.remove(str3);
            }
        });
        return dCloudAlertDialog;
    }
}

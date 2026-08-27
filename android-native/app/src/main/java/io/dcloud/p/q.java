package io.dcloud.p;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Process;
import android.view.KeyEvent;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import io.dcloud.base.R;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.Md5Utils;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class q {
    public static String a = "appid";

    /* JADX WARN: Removed duplicated region for block: B:8:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void a(android.app.Activity r5, io.dcloud.common.DHInterface.IApp r6) {
        /*
            java.lang.String r0 = "DCLOUD_STREAMAPP_CHANNEL"
            java.lang.String r0 = io.dcloud.common.adapter.util.AndroidResources.getMetaValue(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L42
            if (r0 == 0) goto L42
            java.lang.String r1 = "\\|"
            java.lang.String[] r0 = r0.split(r1)
            int r1 = r0.length
            r2 = 2
            if (r1 <= r2) goto L42
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r0 = r0[r2]
            r1.append(r0)
            java.lang.String r0 = "|"
            r1.append(r0)
            io.dcloud.common.DHInterface.IConfusionMgr r0 = r6.getConfusionMgr()
            io.dcloud.common.DHInterface.IConfusionMgr r2 = r6.getConfusionMgr()
            java.lang.String r2 = r2.getODS()
            r3 = 1
            r4 = 50
            java.lang.String r0 = r0.decodeString(r2, r3, r4)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L43
        L42:
            r0 = 0
        L43:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L73
            r1.<init>()     // Catch: java.lang.Exception -> L73
            java.lang.String r2 = r5.getPackageName()     // Catch: java.lang.Exception -> L73
            r1.append(r2)     // Catch: java.lang.Exception -> L73
            io.dcloud.common.DHInterface.IConfusionMgr r2 = r6.getConfusionMgr()     // Catch: java.lang.Exception -> L73
            java.lang.String r3 = "&J}adlKgfnao"
            java.lang.String r2 = r2.decryptStr(r3)     // Catch: java.lang.Exception -> L73
            r1.append(r2)     // Catch: java.lang.Exception -> L73
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> L73
            io.dcloud.common.DHInterface.IConfusionMgr r2 = r6.getConfusionMgr()     // Catch: java.lang.Exception -> L73
            java.lang.String r3 = "GIC"
            java.lang.String r2 = r2.decryptStr(r3)     // Catch: java.lang.Exception -> L73
            java.lang.Object r1 = io.dcloud.common.util.ReflectUtils.getStaticObjectField(r1, r2)     // Catch: java.lang.Exception -> L73
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> L73
            goto L75
        L73:
            java.lang.String r1 = ""
        L75:
            boolean r2 = io.dcloud.common.util.BaseInfo.SyncDebug
            if (r2 == 0) goto L7a
            goto L97
        L7a:
            boolean r2 = io.dcloud.feature.internal.sdk.SDK.isUniMP
            if (r2 == 0) goto L7f
            goto L97
        L7f:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L97
            java.lang.String r2 = r6.obtainAppId()
            java.lang.String r3 = io.dcloud.common.util.LoadAppUtils.getAppSignatureSHA1(r5)
            boolean r5 = a(r5, r2, r3, r1, r0)
            if (r5 != 0) goto L97
            r5 = 4
            r6.setStatus(r5)
        L97:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.q.a(android.app.Activity, io.dcloud.common.DHInterface.IApp):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return i == 4;
    }

    private static boolean a(Context context, String str, String str2, String str3, String str4) {
        String packageName = context.getPackageName();
        Locale locale = Locale.ENGLISH;
        String lowerCase = packageName.toLowerCase(locale);
        try {
            StringBuilder sb = new StringBuilder("|");
            int iCharAt = str3.charAt(0) - '0';
            if ((iCharAt & 1) != 1) {
                str = "";
            }
            sb.append(str);
            sb.append("|");
            if ((iCharAt & 2) != 2) {
                lowerCase = "";
            }
            sb.append(lowerCase);
            sb.append("|");
            if ((iCharAt & 4) != 4) {
                str2 = "";
            }
            sb.append(str2);
            sb.append("|");
            sb.append(str4);
            boolean zEquals = str3.equals((iCharAt + Md5Utils.md5(sb.toString())).toLowerCase(locale));
            if (!zEquals) {
                a(iCharAt, context);
            }
            return zEquals;
        } catch (Exception unused) {
            return false;
        }
    }

    private static void a(int i, Context context) {
        boolean z = (i & 1) == 1;
        boolean z2 = (i & 2) == 2;
        boolean z3 = (i & 4) == 4;
        String string = "";
        if (!z && !z2 && !z3) {
            Process.killProcess(Process.myPid());
        } else if (z && z2 && z3) {
            string = context.getString(R.string.dcloud_onlone_fail_tips_all);
        } else {
            String string2 = context.getString(R.string.dcloud_onlone_fail_tips);
            String string3 = context.getString(R.string.dcloud_tips_package_name);
            String string4 = context.getString(R.string.dcloud_tips_certificate);
            if (z) {
                string = "" + a + "、";
            }
            if (z2) {
                string = string + string3 + "、";
            }
            if (z3) {
                string = string + string4 + "、";
            }
            string = String.format(string2, string.substring(0, string.length() - 1));
        }
        a(context, string);
    }

    public static void a(Context context, String str) {
        try {
            DCloudAlertDialog dCloudAlertDialog = new DCloudAlertDialog(context, AppRuntime.getAppDarkMode(context) ? DCloudAlertDialog.DARK_THEME : DCloudAlertDialog.LIGHT_THEME, true);
            dCloudAlertDialog.setTitle(str);
            dCloudAlertDialog.setButton(-2, context.getString(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: io.dcloud.p.q$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    q.a(dialogInterface, i);
                }
            });
            dCloudAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.p.q$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnKeyListener
                public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    return q.a(dialogInterface, i, keyEvent);
                }
            });
            dCloudAlertDialog.setCancelable(false);
            dCloudAlertDialog.setCanceledOnTouchOutside(false);
            dCloudAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(DialogInterface dialogInterface, int i) {
        Process.killProcess(Process.myPid());
    }
}

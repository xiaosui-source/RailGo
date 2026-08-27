package com.dcloud.android.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DCloudAlertDialog extends AlertDialog {
    public static final int DARK_THEME = -999;
    public static final int LIGHT_THEME = -998;
    boolean hasNavigationBar;

    public DCloudAlertDialog(Context context, boolean z) {
        super(context);
        this.hasNavigationBar = z;
    }

    private static int getTheme(Context context, int i) {
        if (i == -998) {
            return 5;
        }
        if (i == -999) {
            return 4;
        }
        return i;
    }

    @Override // android.app.Dialog
    public void show() {
        if (this.hasNavigationBar) {
            super.show();
            return;
        }
        getWindow().addFlags(8);
        super.show();
        getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() | 4866);
        getWindow().clearFlags(8);
    }

    public DCloudAlertDialog(Context context, int i, boolean z) {
        super(context, getTheme(context, i));
        this.hasNavigationBar = z;
    }
}

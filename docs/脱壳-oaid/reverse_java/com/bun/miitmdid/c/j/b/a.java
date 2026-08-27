package com.bun.miitmdid.c.j.b;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a {
    private Context a;

    public a(Context context) {
        this.a = context;
    }

    public String a(int i, String str) {
        Cursor cursorQuery = this.a.getContentResolver().query(i != 0 ? i != 1 ? i != 2 ? null : Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/AAID_" + str) : Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/VAID_" + str) : Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), null, null, null, null);
        if (cursorQuery != null) {
            string = cursorQuery.moveToNext() ? cursorQuery.getString(cursorQuery.getColumnIndex("value")) : null;
            cursorQuery.close();
        } else {
            com.bun.lib.a.a("VMS_IDLG_SDK_DB", "return cursor is null,return");
        }
        return string;
    }
}

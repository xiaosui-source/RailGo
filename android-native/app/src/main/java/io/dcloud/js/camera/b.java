package io.dcloud.js.camera;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import io.dcloud.base.R;
import java.io.IOException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
abstract class b {
    /* JADX WARN: Removed duplicated region for block: B:36:0x0043 A[Catch: Exception -> 0x003f, TRY_LEAVE, TryCatch #0 {Exception -> 0x003f, blocks: (B:32:0x003b, B:36:0x0043), top: B:40:0x003b }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x003b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String a(android.graphics.Bitmap r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L1f
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L1f
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Throwable -> L19 java.lang.Exception -> L1b
            r3 = 100
            r4.compress(r2, r3, r1)     // Catch: java.lang.Throwable -> L19 java.lang.Exception -> L1b
            r1.close()     // Catch: java.lang.Exception -> L14
            r4.recycle()     // Catch: java.lang.Exception -> L14
            return r5
        L14:
            r4 = move-exception
            r4.printStackTrace()
            return r5
        L19:
            r5 = move-exception
            goto L39
        L1b:
            r5 = move-exception
            goto L21
        L1d:
            r5 = move-exception
            goto L38
        L1f:
            r5 = move-exception
            r1 = r0
        L21:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L36
            if (r1 == 0) goto L2c
            r1.close()     // Catch: java.lang.Exception -> L2a
            goto L2c
        L2a:
            r4 = move-exception
            goto L32
        L2c:
            if (r4 == 0) goto L35
            r4.recycle()     // Catch: java.lang.Exception -> L2a
            goto L35
        L32:
            r4.printStackTrace()
        L35:
            return r0
        L36:
            r5 = move-exception
            r0 = r1
        L38:
            r1 = r0
        L39:
            if (r1 == 0) goto L41
            r1.close()     // Catch: java.lang.Exception -> L3f
            goto L41
        L3f:
            r4 = move-exception
            goto L47
        L41:
            if (r4 == 0) goto L4a
            r4.recycle()     // Catch: java.lang.Exception -> L3f
            goto L4a
        L47:
            r4.printStackTrace()
        L4a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.js.camera.b.a(android.graphics.Bitmap, java.lang.String):java.lang.String");
    }

    public static Bitmap b(String str) {
        return BitmapFactory.decodeFile(str, new BitmapFactory.Options());
    }

    public static int c(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String a(String str) {
        int iC = c(str);
        if (iC == 0) {
            return str;
        }
        Bitmap bitmapB = b(str);
        if (bitmapB == null) {
            return null;
        }
        return a(a(iC, bitmapB), str);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001e  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.graphics.Bitmap a(int r7, android.graphics.Bitmap r8) {
        /*
            android.graphics.Matrix r5 = new android.graphics.Matrix
            r5.<init>()
            float r7 = (float) r7
            r5.postRotate(r7)
            int r3 = r8.getWidth()     // Catch: java.lang.OutOfMemoryError -> L1a
            int r4 = r8.getHeight()     // Catch: java.lang.OutOfMemoryError -> L1a
            r6 = 1
            r1 = 0
            r2 = 0
            r0 = r8
            android.graphics.Bitmap r7 = android.graphics.Bitmap.createBitmap(r0, r1, r2, r3, r4, r5, r6)     // Catch: java.lang.OutOfMemoryError -> L1b
            goto L1c
        L1a:
            r0 = r8
        L1b:
            r7 = 0
        L1c:
            if (r7 != 0) goto L1f
            r7 = r0
        L1f:
            if (r0 == r7) goto L24
            r0.recycle()
        L24:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.js.camera.b.a(int, android.graphics.Bitmap):android.graphics.Bitmap");
    }

    public static Dialog a(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog_transparent);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.dcloud_dialog_loading, (ViewGroup) null);
        viewGroup.findViewById(R.id.loading_background).setBackgroundColor(0);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setView(viewGroup, 0, 0, 0, 0);
        return alertDialogCreate;
    }
}

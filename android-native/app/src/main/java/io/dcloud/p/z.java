package io.dcloud.p;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import io.dcloud.common.util.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class z {
    private static int a(int i) {
        if (i == 3) {
            return 180;
        }
        if (i != 6) {
            return i != 8 ? 0 : 270;
        }
        return 90;
    }

    public static Bitmap a(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        int iA = a(i);
        if (iA <= 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(iA);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap a(File file, BitmapFactory.Options options) {
        try {
            int i = Integer.parseInt(new ExifInterface(file).getAttribute("Orientation"));
            byte[] bArrA = a(file);
            if (i > 1) {
                return a(BitmapFactory.decodeByteArray(bArrA, 0, bArrA.length, options), i);
            }
            return BitmapFactory.decodeByteArray(bArrA, 0, bArrA.length, options);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Bitmap a(InputStream inputStream, BitmapFactory.Options options) {
        try {
            byte[] bArrA = a(inputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrA);
            ExifInterface exifInterface = new ExifInterface(byteArrayInputStream);
            try {
                byteArrayInputStream.close();
            } catch (Exception unused) {
            }
            int i = Integer.parseInt(exifInterface.getAttribute("Orientation"));
            if (i > 1) {
                return a(BitmapFactory.decodeByteArray(bArrA, 0, bArrA.length), i);
            }
            return BitmapFactory.decodeByteArray(bArrA, 0, bArrA.length, options);
        } catch (Throwable unused2) {
            return null;
        }
    }

    public static byte[] a(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[10240];
            while (true) {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    inputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }

    private static byte[] a(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            return a(new FileInputStream(file));
        } catch (Exception unused) {
            return null;
        }
    }
}

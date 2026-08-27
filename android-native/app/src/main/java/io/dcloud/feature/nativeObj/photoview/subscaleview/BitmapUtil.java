package io.dcloud.feature.nativeObj.photoview.subscaleview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import io.dcloud.common.util.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class BitmapUtil {
    public static Bitmap getRotatedBitmap(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        switch (i) {
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                break;
            case 3:
                matrix.setRotate(180.0f);
                break;
            case 4:
                matrix.setScale(1.0f, -1.0f);
                break;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 6:
                matrix.setRotate(90.0f);
                break;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 8:
                matrix.setRotate(-90.0f);
                break;
            default:
                return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap getRotatedBitmapFromBitmap(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return getRotatedBitmap(bitmap, new ExifInterface(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).getAttributeInt("Orientation", 1));
    }

    public static Bitmap getRotatedBitmapFromFile(File file) throws IOException {
        return getRotatedBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()), new ExifInterface(file.getAbsolutePath()).getAttributeInt("Orientation", 1));
    }

    public static boolean needRatationWithExif(File file) {
        int attributeInt;
        try {
            attributeInt = new ExifInterface(file).getAttributeInt("Orientation", 1);
        } catch (IOException unused) {
        }
        return (attributeInt == 1 || attributeInt == 0) ? false : true;
    }
}

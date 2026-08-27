package com.nostra13.dcloudimageloader.core.display;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;
import com.nostra13.dcloudimageloader.core.assist.LoadedFrom;
import com.nostra13.dcloudimageloader.core.imageaware.ImageAware;
import com.nostra13.dcloudimageloader.core.imageaware.ImageViewAware;
import com.nostra13.dcloudimageloader.utils.L;

/* loaded from: classes.dex */
public class RoundedBitmapDisplayer implements BitmapDisplayer {
    private final int roundPixels;

    public RoundedBitmapDisplayer(int i) {
        this.roundPixels = i;
    }

    @Override // com.nostra13.dcloudimageloader.core.display.BitmapDisplayer
    public Bitmap display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
        Bitmap bitmapRoundCorners = roundCorners(bitmap, (ImageViewAware) imageAware, this.roundPixels);
        imageAware.setImageBitmap(bitmapRoundCorners);
        return bitmapRoundCorners;
    }

    public static Bitmap roundCorners(Bitmap bitmap, ImageViewAware imageViewAware, int i) {
        int i2;
        int iMin;
        Bitmap bitmap2;
        int i3;
        Rect rect;
        int i4;
        int i5;
        Rect rect2;
        int i6;
        int i7;
        Rect rect3;
        Rect rect4;
        ImageView wrappedView = imageViewAware.getWrappedView();
        if (wrappedView == null) {
            L.w("View is collected probably. Can't round bitmap corners without view properties.", new Object[0]);
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int width2 = imageViewAware.getWidth();
        int height2 = imageViewAware.getHeight();
        if (width2 <= 0) {
            width2 = width;
        }
        if (height2 <= 0) {
            height2 = height;
        }
        ImageView.ScaleType scaleType = wrappedView.getScaleType();
        if (scaleType == null) {
            return bitmap;
        }
        int iOrdinal = scaleType.ordinal();
        if (iOrdinal == 1) {
            float f = width;
            float f2 = height;
            if (width2 / height2 > f / f2) {
                iMin = Math.min(height2, height);
                i2 = (int) (f / (f2 / iMin));
            } else {
                int iMin2 = Math.min(width2, width);
                int i8 = (int) (f2 / (f / iMin2));
                i2 = iMin2;
                iMin = i8;
            }
            int i9 = (width2 - i2) / 2;
            int i10 = (height2 - iMin) / 2;
            Rect rect5 = new Rect(0, 0, width, height);
            bitmap2 = bitmap;
            i3 = i;
            rect = new Rect(i9, i10, i2 + i9, iMin + i10);
            i4 = width2;
            i5 = height2;
            rect2 = rect5;
        } else if (iOrdinal != 5) {
            if (iOrdinal == 6) {
                rect3 = new Rect(0, 0, width, height);
                rect4 = new Rect(0, 0, width2, height2);
            } else if (iOrdinal != 7 && iOrdinal != 8) {
                float f3 = width2;
                float f4 = height2;
                float f5 = width;
                float f6 = height;
                if (f3 / f4 > f5 / f6) {
                    width2 = (int) (f5 / (f6 / f4));
                } else {
                    height2 = (int) (f6 / (f5 / f3));
                }
                rect3 = new Rect(0, 0, width, height);
                rect4 = new Rect(0, 0, width2, height2);
            } else {
                width2 = Math.min(width2, width);
                height2 = Math.min(height2, height);
                int i11 = (width - width2) / 2;
                int i12 = (height - height2) / 2;
                rect3 = new Rect(i11, i12, i11 + width2, i12 + height2);
                rect4 = new Rect(0, 0, width2, height2);
            }
            bitmap2 = bitmap;
            i3 = i;
            rect2 = rect3;
            rect = rect4;
            i4 = width2;
            i5 = height2;
        } else {
            float f7 = width2;
            float f8 = height2;
            float f9 = width;
            float f10 = height;
            if (f7 / f8 > f9 / f10) {
                int i13 = (int) (f8 * (f9 / f7));
                i7 = (height - i13) / 2;
                height = i13;
                i6 = 0;
            } else {
                int i14 = (int) (f7 * (f10 / f8));
                int i15 = (width - i14) / 2;
                width = i14;
                i6 = i15;
                i7 = 0;
            }
            Rect rect6 = new Rect(i6, i7, i6 + width, i7 + height);
            bitmap2 = bitmap;
            i3 = i;
            rect = new Rect(0, 0, width, height);
            i4 = width;
            i5 = height;
            rect2 = rect6;
        }
        try {
            return getRoundedCornerBitmap(bitmap2, i3, rect2, rect, i4, i5);
        } catch (OutOfMemoryError e) {
            L.e(e, "Can't create bitmap with rounded corners. Not enough memory.", new Object[0]);
            return bitmap;
        }
    }

    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int i, Rect rect, Rect rect2, int i2, int i3) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(rect2);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-16777216);
        float f = i;
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return bitmapCreateBitmap;
    }
}

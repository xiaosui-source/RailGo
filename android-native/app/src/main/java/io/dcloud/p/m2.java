package io.dcloud.p;

import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.gallery.imageedit.view.IMGStickerView;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class m2 implements View.OnTouchListener {
    private View a;
    private IMGStickerView b;
    private float c;
    private float d;
    private double e;
    private double f;
    private Matrix g = new Matrix();

    public m2(IMGStickerView iMGStickerView, View view) {
        this.a = view;
        this.b = iMGStickerView;
        view.setOnTouchListener(this);
    }

    private static double a(float f, float f2) {
        return Math.toDegrees(Math.atan2(f, f2));
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.d = 0.0f;
            this.c = 0.0f;
            float x2 = (this.a.getX() + x) - this.b.getPivotX();
            float y2 = (this.a.getY() + y) - this.b.getPivotY();
            Log.d("IMGStickerAdjustHelper", StringUtil.format("X=%f,Y=%f", Float.valueOf(x2), Float.valueOf(y2)));
            this.e = a(0.0f, 0.0f, x2, y2);
            this.f = a(y2, x2);
            this.g.setTranslate(x2 - x, y2 - y);
            Log.d("IMGStickerAdjustHelper", StringUtil.format("degrees=%f", Double.valueOf(a(y2, x2))));
            this.g.postRotate((float) (-a(y2, x2)), this.c, this.d);
            return true;
        }
        if (action != 2) {
            return false;
        }
        float[] fArr = {motionEvent.getX(), motionEvent.getY()};
        float x3 = (this.a.getX() + fArr[0]) - this.b.getPivotX();
        float y3 = (this.a.getY() + fArr[1]) - this.b.getPivotY();
        Log.d("IMGStickerAdjustHelper", StringUtil.format("X=%f,Y=%f", Float.valueOf(x3), Float.valueOf(y3)));
        double dA = a(0.0f, 0.0f, x3, y3);
        double dA2 = a(y3, x3);
        this.b.a((float) (dA / this.e));
        Log.d("IMGStickerAdjustHelper", "    D   = " + (dA2 - this.f));
        IMGStickerView iMGStickerView = this.b;
        iMGStickerView.setRotation((float) ((((double) iMGStickerView.getRotation()) + dA2) - this.f));
        this.e = dA;
        return true;
    }

    private static double a(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return Math.sqrt((f5 * f5) + (f6 * f6));
    }
}

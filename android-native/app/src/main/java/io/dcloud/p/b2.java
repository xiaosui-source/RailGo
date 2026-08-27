package io.dcloud.p;

import android.graphics.RectF;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface b2 {
    public static final float[] a = {0.0f, 1.0f, 0.33f, 0.66f};
    public static final float[] b = {0.0f, 3.0f, -3.0f};
    public static final float[] c = {0.0f, 48.0f, -48.0f};
    public static final byte[] d = {8, 8, 9, 8, 6, 8, 4, 8, 4, 8, 4, 1, 4, 10, 4, 8, 4, 4, 6, 4, 9, 4, 8, 4, 8, 4, 8, 6, 8, 9, 8, 8};

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public enum a {
        LEFT(1),
        RIGHT(2),
        TOP(4),
        BOTTOM(8),
        LEFT_TOP(5),
        RIGHT_TOP(6),
        LEFT_BOTTOM(9),
        RIGHT_BOTTOM(10);

        static final int[] j = {1, -1};
        int a;

        a(int i) {
            this.a = i;
        }

        public void a(RectF rectF, RectF rectF2, float f, float f2, float[] fArr, boolean z) {
            float fAbs;
            float fHeight;
            float fAbs2;
            int i;
            float[] fArrA = a(rectF, 0.0f, 0.0f);
            float[] fArrA2 = a(rectF2, 150.72f, 150.72f);
            float[] fArrA3 = a(rectF2, 0.0f, 0.0f);
            int i2 = this.a;
            if (z) {
                fArrA2 = a(rectF2, 150.72f, (150.72f / fArr[0]) * fArr[1]);
                int[] iArr = {(int) (Math.abs(f) / f), (int) (Math.abs(f2) / f2)};
                int i3 = this.a;
                if (i3 == 4) {
                    rectF2.top += f2;
                    fAbs = iArr[1] * Math.abs(rectF2.width() - ((rectF2.height() / fArr[1]) * fArr[0]));
                    fHeight = f2;
                    i2 = 5;
                } else if (i3 == 8) {
                    rectF2.bottom += f2;
                    fAbs = iArr[1] * Math.abs(rectF2.width() - ((rectF2.height() / fArr[1]) * fArr[0]));
                    fHeight = f2;
                    i2 = 10;
                } else {
                    if (i3 == 1) {
                        i2 = 5;
                    }
                    if (i3 == 2) {
                        i2 = 10;
                    }
                    if ((i2 & 2) != 0) {
                        rectF2.right += f;
                    } else if ((i2 & 1) != 0) {
                        rectF2.left += f;
                    }
                    fHeight = rectF2.height() - ((rectF2.width() / fArr[0]) * fArr[1]);
                    if (i2 == 5 || i2 == 10) {
                        fAbs2 = Math.abs(fHeight);
                        i = iArr[0];
                    } else {
                        if (i2 == 6 || i2 == 9) {
                            fAbs2 = Math.abs(fHeight);
                            i = -iArr[0];
                        }
                        fAbs = f;
                    }
                    fHeight = i * fAbs2;
                    fAbs = f;
                }
            } else {
                fAbs = f;
                fHeight = f2;
            }
            float[] fArr2 = {fAbs, 0.0f, fHeight};
            int i4 = 0;
            for (int i5 = 4; i4 < i5; i5 = 4) {
                if (((1 << i4) & i2) != 0) {
                    int i6 = j[i4 & 1];
                    float f3 = i6;
                    fArrA3[i4] = f3 * a((fArrA3[i4] + fArr2[i4 & 2]) * f3, f3 * fArrA[i4], fArrA2[i6 + i4] * f3);
                }
                i4++;
            }
            rectF2.set(fArrA3[0], fArrA3[2], fArrA3[1], fArrA3[3]);
        }

        public static float a(float f, float f2, float f3) {
            return Math.min(Math.max(f, f2), f3);
        }

        public static float[] a(RectF rectF, float f, float f2) {
            return new float[]{rectF.left + f, rectF.right - f, rectF.top + f2, rectF.bottom - f2};
        }

        public static boolean a(RectF rectF, float f, float f2, float f3) {
            return rectF.left + f < f2 && rectF.right - f > f2 && rectF.top + f < f3 && rectF.bottom - f > f3;
        }

        public static a a(int i) {
            for (a aVar : values()) {
                if (aVar.a == i) {
                    return aVar;
                }
            }
            return null;
        }
    }
}

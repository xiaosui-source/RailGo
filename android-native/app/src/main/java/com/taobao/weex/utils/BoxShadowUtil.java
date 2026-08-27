package com.taobao.weex.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.common.util.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class BoxShadowUtil {
    private static final String TAG = "BoxShadowUtil";
    private static boolean sBoxShadowEnabled = true;
    private static Pattern sColorPattern;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class BoxShadowOptions {
        public float blur;
        public int color;
        public float hShadow;
        public boolean isInset;
        private List<IParser> optionParamParsers;
        public float[] radii;
        public float spread;
        public PointF topLeft;
        public float vShadow;
        public int viewHeight;
        public int viewWidth;
        private float viewport;

        /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
        private interface IParser {
            void parse(String str);
        }

        public Rect getTargetCanvasRect() {
            return new Rect(0, 0, this.viewWidth + (((int) (this.blur + this.spread + Math.abs(this.hShadow))) * 2), this.viewHeight + (((int) (this.blur + this.spread + Math.abs(this.vShadow))) * 2));
        }

        public BoxShadowOptions scale(float f) {
            if (f <= 0.0f || f > 1.0f) {
                return null;
            }
            BoxShadowOptions boxShadowOptions = new BoxShadowOptions(this.viewport);
            boxShadowOptions.hShadow = this.hShadow * f;
            boxShadowOptions.vShadow = this.vShadow * f;
            boxShadowOptions.blur = this.blur * f;
            boxShadowOptions.spread = this.spread * f;
            int i = 0;
            while (true) {
                float[] fArr = this.radii;
                if (i >= fArr.length) {
                    break;
                }
                boxShadowOptions.radii[i] = fArr[i] * f;
                i++;
            }
            boxShadowOptions.viewHeight = (int) (this.viewHeight * f);
            boxShadowOptions.viewWidth = (int) (this.viewWidth * f);
            if (this.topLeft != null) {
                PointF pointF = new PointF();
                boxShadowOptions.topLeft = pointF;
                PointF pointF2 = this.topLeft;
                pointF.x = pointF2.x * f;
                pointF.y = pointF2.y * f;
            }
            boxShadowOptions.color = this.color;
            boxShadowOptions.isInset = this.isInset;
            WXLogUtils.d(BoxShadowUtil.TAG, "Scaled BoxShadowOptions: [" + f + "] " + boxShadowOptions);
            return boxShadowOptions;
        }

        public String toString() {
            String str = Operators.ARRAY_START_STR + this.radii[0] + "," + this.radii[2] + "," + this.radii[4] + "," + this.radii[6] + Operators.ARRAY_END_STR;
            StringBuffer stringBuffer = new StringBuffer("BoxShadowOptions{h-shadow=");
            stringBuffer.append(this.hShadow);
            stringBuffer.append(", v-shadow=").append(this.vShadow);
            stringBuffer.append(", blur=").append(this.blur);
            stringBuffer.append(", spread=").append(this.spread);
            stringBuffer.append(", corner-radius=").append(str);
            stringBuffer.append(", color=#").append(Integer.toHexString(this.color));
            stringBuffer.append(", inset=").append(this.isInset);
            stringBuffer.append(Operators.BLOCK_END);
            return stringBuffer.toString();
        }

        private BoxShadowOptions(float f) {
            this.blur = 0.0f;
            this.spread = 0.0f;
            this.radii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
            this.color = -16777216;
            this.isInset = false;
            this.viewWidth = 0;
            this.viewHeight = 0;
            this.topLeft = null;
            this.viewport = f;
            this.optionParamParsers = new ArrayList();
            IParser iParser = new IParser() { // from class: com.taobao.weex.utils.BoxShadowUtil.BoxShadowOptions.1
                @Override // com.taobao.weex.utils.BoxShadowUtil.BoxShadowOptions.IParser
                public void parse(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    float fFloatValue = WXUtils.getFloat(str, Float.valueOf(0.0f)).floatValue();
                    BoxShadowOptions boxShadowOptions = BoxShadowOptions.this;
                    boxShadowOptions.spread = WXViewUtils.getRealSubPxByWidth(fFloatValue, boxShadowOptions.viewport);
                    WXLogUtils.w(BoxShadowUtil.TAG, "Experimental box-shadow attribute: spread");
                }
            };
            this.optionParamParsers.add(new IParser() { // from class: com.taobao.weex.utils.BoxShadowUtil.BoxShadowOptions.2
                @Override // com.taobao.weex.utils.BoxShadowUtil.BoxShadowOptions.IParser
                public void parse(String str) {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    float fFloatValue = WXUtils.getFloat(str, Float.valueOf(0.0f)).floatValue();
                    BoxShadowOptions boxShadowOptions = BoxShadowOptions.this;
                    boxShadowOptions.blur = WXViewUtils.getRealSubPxByWidth(fFloatValue, boxShadowOptions.viewport);
                }
            });
            this.optionParamParsers.add(iParser);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class InsetShadowDrawable extends Drawable {
        private static final int BOTTOM_TO_TOP = 3;
        private static final int LEFT_TO_RIGHT = 0;
        private static final int RIGHT_TO_LEFT = 2;
        private static final int TOP_TO_BOTTOM = 1;
        private float blurRadius;
        private float height;
        private Paint paint;
        private Path[] paths;
        private float[] radii;
        private Shader[] shades;
        private int shadowColor;
        private float shadowXSize;
        private float shadowYSize;
        private float width;

        private void prepare() {
            PointF pointF = new PointF(0.0f, 0.0f);
            PointF pointF2 = new PointF(this.width, 0.0f);
            PointF pointF3 = new PointF(pointF2.x, this.height);
            PointF pointF4 = new PointF(pointF.x, pointF3.y);
            PointF pointF5 = new PointF(this.shadowXSize, this.shadowYSize);
            PointF pointF6 = new PointF(pointF2.x - this.shadowXSize, pointF5.y);
            PointF pointF7 = new PointF(pointF6.x, pointF3.y - this.shadowYSize);
            PointF pointF8 = new PointF(pointF5.x, pointF7.y);
            float f = pointF5.x;
            float f2 = f - this.blurRadius;
            float f3 = pointF5.y;
            int i = this.shadowColor;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            LinearGradient linearGradient = new LinearGradient(f2, f3, f, f3, i, 0, tileMode);
            float f4 = pointF5.x;
            float f5 = pointF5.y;
            LinearGradient linearGradient2 = new LinearGradient(f4, f5 - this.blurRadius, f4, f5, this.shadowColor, 0, tileMode);
            float f6 = pointF7.x;
            float f7 = f6 + this.blurRadius;
            float f8 = pointF7.y;
            LinearGradient linearGradient3 = new LinearGradient(f7, f8, f6, f8, this.shadowColor, 0, tileMode);
            float f9 = pointF7.x;
            float f10 = pointF7.y;
            LinearGradient linearGradient4 = new LinearGradient(f9, f10 + this.blurRadius, f9, f10, this.shadowColor, 0, tileMode);
            Shader[] shaderArr = this.shades;
            shaderArr[0] = linearGradient;
            shaderArr[1] = linearGradient2;
            shaderArr[2] = linearGradient3;
            shaderArr[3] = linearGradient4;
            Path path = new Path();
            path.moveTo(pointF.x, pointF.y);
            path.lineTo(pointF5.x, pointF5.y);
            path.lineTo(pointF8.x, pointF8.y);
            path.lineTo(pointF4.x, pointF4.y);
            path.close();
            Path path2 = new Path();
            path2.moveTo(pointF.x, pointF.y);
            path2.lineTo(pointF2.x, pointF2.y);
            path2.lineTo(pointF6.x, pointF6.y);
            path2.lineTo(pointF5.x, pointF5.y);
            path2.close();
            Path path3 = new Path();
            path3.moveTo(pointF2.x, pointF2.y);
            path3.lineTo(pointF3.x, pointF3.y);
            path3.lineTo(pointF7.x, pointF7.y);
            path3.lineTo(pointF6.x, pointF6.y);
            path3.close();
            Path path4 = new Path();
            path4.moveTo(pointF4.x, pointF4.y);
            path4.lineTo(pointF3.x, pointF3.y);
            path4.lineTo(pointF7.x, pointF7.y);
            path4.lineTo(pointF8.x, pointF8.y);
            path4.close();
            Path[] pathArr = this.paths;
            pathArr[0] = path;
            pathArr[1] = path2;
            pathArr[2] = path3;
            pathArr[3] = path4;
            Paint paint = new Paint();
            this.paint = paint;
            paint.setAntiAlias(true);
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(this.shadowColor);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Rect clipBounds = canvas.getClipBounds();
            Path path = new Path();
            path.addRoundRect(new RectF(clipBounds), this.radii, Path.Direction.CCW);
            canvas.clipPath(path);
            canvas.translate(clipBounds.left, clipBounds.top);
            for (int i = 0; i < 4; i++) {
                Shader shader = this.shades[i];
                Path path2 = this.paths[i];
                this.paint.setShader(shader);
                canvas.drawPath(path2, this.paint);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -1;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        private InsetShadowDrawable(int i, int i2, float f, float f2, float f3, float f4, int i3, float[] fArr) {
            this.shades = new Shader[4];
            this.paths = new Path[4];
            this.blurRadius = f3;
            this.shadowColor = i3;
            this.width = i + (f * 2.0f);
            this.height = i2 + (2.0f * f2);
            this.shadowXSize = f + f4;
            this.shadowYSize = f2 + f4;
            this.radii = fArr;
            setBounds(0, 0, i, i2);
            prepare();
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class OverflowBitmapDrawable extends BitmapDrawable {
        private int paddingX;
        private int paddingY;
        private float[] radii;
        private Rect viewRect;

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Rect clipBounds = canvas.getClipBounds();
            Rect rect = new Rect(clipBounds);
            rect.inset((-this.paddingX) * 2, (-this.paddingY) * 2);
            try {
                if (WXEnvironment.sApplication.getApplicationInfo().targetSdkVersion > 26) {
                    canvas.clipRect(rect);
                } else {
                    canvas.clipRect(rect, Region.Op.REPLACE);
                }
            } catch (NullPointerException unused) {
                canvas.clipRect(rect);
            }
            Path path = new Path();
            path.addRoundRect(new RectF(clipBounds), this.radii, Path.Direction.CCW);
            canvas.clipPath(path, Region.Op.DIFFERENCE);
            canvas.translate(clipBounds.left, clipBounds.top);
            super.draw(canvas);
        }

        private OverflowBitmapDrawable(Resources resources, Bitmap bitmap, Point point, Rect rect, float[] fArr) {
            super(resources, bitmap);
            int i = point.x;
            this.paddingX = i;
            int i2 = point.y;
            this.paddingY = i2;
            this.viewRect = rect;
            this.radii = fArr;
            setBounds(-i, -i2, rect.width() + this.paddingX, rect.height() + this.paddingY);
        }
    }

    private static void drawShadow(Canvas canvas, BoxShadowOptions boxShadowOptions) {
        float f = boxShadowOptions.viewWidth;
        float f2 = boxShadowOptions.spread * 2.0f;
        RectF rectF = new RectF(0.0f, 0.0f, f + f2, boxShadowOptions.viewHeight + f2);
        PointF pointF = boxShadowOptions.topLeft;
        if (pointF != null) {
            rectF.offset(pointF.x, pointF.y);
        }
        float f3 = boxShadowOptions.blur;
        float f4 = boxShadowOptions.hShadow;
        float f5 = f4 > 0.0f ? (f4 * 2.0f) + f3 : f3;
        float f6 = boxShadowOptions.vShadow;
        if (f6 > 0.0f) {
            f3 += f6 * 2.0f;
        }
        rectF.offset(f5, f3);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(boxShadowOptions.color);
        paint.setStyle(Paint.Style.FILL);
        if (boxShadowOptions.blur > 0.0f) {
            paint.setMaskFilter(new BlurMaskFilter(boxShadowOptions.blur, BlurMaskFilter.Blur.NORMAL));
        }
        Path path = new Path();
        float[] fArr = new float[8];
        int i = 0;
        while (true) {
            float[] fArr2 = boxShadowOptions.radii;
            if (i >= fArr2.length) {
                path.addRoundRect(rectF, fArr, Path.Direction.CCW);
                canvas.drawPath(path, paint);
                return;
            } else {
                float f7 = fArr2[i];
                if (f7 == 0.0f) {
                    fArr[i] = 0.0f;
                } else {
                    fArr[i] = f7 + boxShadowOptions.spread;
                }
                i++;
            }
        }
    }

    public static boolean isBoxShadowEnabled() {
        return sBoxShadowEnabled;
    }

    private static BoxShadowOptions parseBoxShadow(String str, float f) {
        BoxShadowOptions boxShadowOptions = new BoxShadowOptions(f);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String strReplaceAll = str.replaceAll("\\s*,\\s+", ",");
        if (strReplaceAll.contains("inset")) {
            boxShadowOptions.isInset = true;
            strReplaceAll = strReplaceAll.replace("inset", "");
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(strReplaceAll.trim().split("\\s+")));
        String str2 = (String) arrayList.get(arrayList.size() - 1);
        if (!TextUtils.isEmpty(str2) && (str2.startsWith("#") || str2.startsWith("rgb") || WXResourceUtils.isNamedColor(str2))) {
            boxShadowOptions.color = WXResourceUtils.getColor(str2, -16777216);
            arrayList.remove(arrayList.size() - 1);
        }
        try {
            if (arrayList.size() < 2) {
                return null;
            }
            if (!TextUtils.isEmpty((CharSequence) arrayList.get(0))) {
                boxShadowOptions.hShadow = WXViewUtils.getRealSubPxByWidth(WXUtils.getFloat(((String) arrayList.get(0)).trim(), Float.valueOf(0.0f)).floatValue(), f);
            }
            if (!TextUtils.isEmpty((CharSequence) arrayList.get(1))) {
                boxShadowOptions.vShadow = WXViewUtils.getRealPxByWidth(WXUtils.getFloat(((String) arrayList.get(1)).trim(), Float.valueOf(0.0f)).floatValue(), f);
            }
            for (int i = 2; i < arrayList.size(); i++) {
                ((BoxShadowOptions.IParser) boxShadowOptions.optionParamParsers.get(i - 2)).parse((String) arrayList.get(i));
            }
            return boxShadowOptions;
        } catch (Throwable th) {
            th.printStackTrace();
            return boxShadowOptions;
        }
    }

    public static BoxShadowOptions[] parseBoxShadows(String str, float f) {
        int i;
        if (sColorPattern == null) {
            sColorPattern = Pattern.compile("([rR][gG][bB][aA]?)\\((\\d+\\s*),\\s*(\\d+\\s*),\\s*(\\d+\\s*)(?:,\\s*(\\d+(?:\\.\\d+)?))?\\)");
        }
        Matcher matcher = sColorPattern.matcher(str);
        while (true) {
            if (!matcher.find()) {
                break;
            }
            String strGroup = matcher.group();
            str = str.replace(strGroup, "#" + StringUtil.format("%8s", Integer.toHexString(WXResourceUtils.getColor(strGroup, -16777216))).replaceAll("\\s", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT));
        }
        String[] strArrSplit = str.split(",");
        if (strArrSplit == null || strArrSplit.length <= 0) {
            return null;
        }
        BoxShadowOptions[] boxShadowOptionsArr = new BoxShadowOptions[strArrSplit.length];
        for (i = 0; i < strArrSplit.length; i++) {
            boxShadowOptionsArr[i] = parseBoxShadow(strArrSplit[i], f);
        }
        return boxShadowOptionsArr;
    }

    public static void setBoxShadow(final View view, String str, final float[] fArr, float f, final float f2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!sBoxShadowEnabled) {
            WXLogUtils.w(TAG, "box-shadow was disabled by config");
            return;
        }
        if (view == null) {
            WXLogUtils.w(TAG, "Target view is null!");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            view.getOverlay().clear();
            WXLogUtils.d(TAG, "Remove all box-shadow");
            return;
        }
        BoxShadowOptions[] boxShadows = parseBoxShadows(str, f);
        if (boxShadows == null || boxShadows.length == 0) {
            WXLogUtils.w(TAG, "Failed to parse box-shadow: " + str);
            return;
        }
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        for (BoxShadowOptions boxShadowOptions : boxShadows) {
            if (boxShadowOptions != null) {
                if (boxShadowOptions.isInset) {
                    arrayList2.add(0, boxShadowOptions);
                } else {
                    arrayList.add(0, boxShadowOptions);
                }
            }
        }
        if (fArr != null) {
            if (fArr.length != 8) {
                WXLogUtils.w(TAG, "Length of radii must be 8");
            } else {
                for (int i = 0; i < fArr.length; i++) {
                    fArr[i] = WXViewUtils.getRealSubPxByWidth(fArr[i], f);
                }
            }
        }
        view.post(WXThread.secure(new Runnable() { // from class: com.taobao.weex.utils.BoxShadowUtil.1
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                view.getOverlay().clear();
                if (arrayList.size() > 0) {
                    BoxShadowUtil.setNormalBoxShadow(view, arrayList, f2, fArr);
                }
                if (arrayList2.size() > 0) {
                    BoxShadowUtil.setInsetBoxShadow(view, arrayList2, f2, fArr);
                }
            }
        }));
    }

    public static void setBoxShadowEnabled(boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        sBoxShadowEnabled = z;
        WXLogUtils.w(TAG, "Switch box-shadow status: " + z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setInsetBoxShadow(View view, List<BoxShadowOptions> list, float f, float[] fArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (view == null || list == null) {
            WXLogUtils.w(TAG, "Illegal arguments");
            return;
        }
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            WXLogUtils.w(TAG, "Target view is invisible, ignore set shadow.");
            return;
        }
        Drawable[] drawableArr = new Drawable[list.size()];
        for (int i = 0; i < list.size(); i++) {
            BoxShadowOptions boxShadowOptions = list.get(i);
            drawableArr[i] = new InsetShadowDrawable(view.getWidth(), view.getHeight(), boxShadowOptions.hShadow, boxShadowOptions.vShadow, boxShadowOptions.blur, boxShadowOptions.spread, boxShadowOptions.color, fArr);
        }
        view.getOverlay().add(new LayerDrawable(drawableArr));
        view.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setNormalBoxShadow(View view, List<BoxShadowOptions> list, float f, float[] fArr) {
        int height = view.getHeight();
        int width = view.getWidth();
        view.getLayoutParams();
        if (height == 0 || width == 0) {
            Log.w(TAG, "Target view is invisible, ignore set shadow.");
            return;
        }
        int iWidth = 0;
        int iHeight = 0;
        for (BoxShadowOptions boxShadowOptions : list) {
            boxShadowOptions.viewWidth = width;
            boxShadowOptions.viewHeight = height;
            boxShadowOptions.radii = fArr;
            Rect targetCanvasRect = boxShadowOptions.getTargetCanvasRect();
            if (iWidth < targetCanvasRect.width()) {
                iWidth = targetCanvasRect.width();
            }
            if (iHeight < targetCanvasRect.height()) {
                iHeight = targetCanvasRect.height();
            }
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (iWidth * f), (int) (iHeight * f), Bitmap.Config.ARGB_4444);
        WXLogUtils.d(TAG, "Allocation memory for box-shadow: " + (bitmapCreateBitmap.getAllocationByteCount() / 1024) + " KB");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        for (BoxShadowOptions boxShadowOptions2 : list) {
            Rect targetCanvasRect2 = boxShadowOptions2.getTargetCanvasRect();
            boxShadowOptions2.topLeft = new PointF((iWidth - targetCanvasRect2.width()) / 2.0f, (iHeight - targetCanvasRect2.height()) / 2.0f);
            drawShadow(canvas, boxShadowOptions2.scale(f));
        }
        OverflowBitmapDrawable overflowBitmapDrawable = new OverflowBitmapDrawable(view.getResources(), bitmapCreateBitmap, new Point((iWidth - width) / 2, (iHeight - height) / 2), new Rect(0, 0, width, height), fArr);
        view.getOverlay().add(overflowBitmapDrawable);
        ViewParent parent = view.getParent();
        if (parent != null) {
            parent.requestLayout();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).invalidate(overflowBitmapDrawable.getBounds());
            }
        }
    }
}

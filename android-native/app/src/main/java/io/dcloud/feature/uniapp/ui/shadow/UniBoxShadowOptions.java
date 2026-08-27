package io.dcloud.feature.uniapp.ui.shadow;

import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UniBoxShadowOptions {
    private static final String TAG = "UniBoxShadowOptions";
    public float hShadow;
    public float vShadow;
    private float viewport;
    public float blur = 0.0f;
    public float spread = 0.0f;
    public float[] radii = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    public int color = -16777216;
    public boolean isInset = false;
    public int viewWidth = 0;
    public int viewHeight = 0;
    public PointF topLeft = null;
    public List<IParser> optionParamParsers = new ArrayList();

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface IParser {
        void parse(String str);
    }

    public UniBoxShadowOptions(float f) {
        this.viewport = f;
        IParser iParser = new IParser() { // from class: io.dcloud.feature.uniapp.ui.shadow.UniBoxShadowOptions.1
            @Override // io.dcloud.feature.uniapp.ui.shadow.UniBoxShadowOptions.IParser
            public void parse(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                float fFloatValue = WXUtils.getFloat(str, Float.valueOf(0.0f)).floatValue();
                UniBoxShadowOptions uniBoxShadowOptions = UniBoxShadowOptions.this;
                uniBoxShadowOptions.spread = WXViewUtils.getRealSubPxByWidth(fFloatValue, uniBoxShadowOptions.viewport);
                WXLogUtils.w(UniBoxShadowOptions.TAG, "Experimental box-shadow attribute: spread");
            }
        };
        this.optionParamParsers.add(new IParser() { // from class: io.dcloud.feature.uniapp.ui.shadow.UniBoxShadowOptions.2
            @Override // io.dcloud.feature.uniapp.ui.shadow.UniBoxShadowOptions.IParser
            public void parse(String str) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                float fFloatValue = WXUtils.getFloat(str, Float.valueOf(0.0f)).floatValue();
                UniBoxShadowOptions uniBoxShadowOptions = UniBoxShadowOptions.this;
                uniBoxShadowOptions.blur = WXViewUtils.getRealSubPxByWidth(fFloatValue, uniBoxShadowOptions.viewport);
            }
        });
        this.optionParamParsers.add(iParser);
    }

    public Rect getTargetCanvasRect() {
        return new Rect(0, 0, this.viewWidth + (((int) (this.blur + this.spread + Math.abs(this.hShadow))) * 2), this.viewHeight + (((int) (this.blur + this.spread + Math.abs(this.vShadow))) * 2));
    }

    public UniBoxShadowOptions scale(float f) {
        if (f <= 0.0f || f > 1.0f) {
            return null;
        }
        UniBoxShadowOptions uniBoxShadowOptions = new UniBoxShadowOptions(this.viewport);
        uniBoxShadowOptions.hShadow = this.hShadow * f;
        uniBoxShadowOptions.vShadow = this.vShadow * f;
        uniBoxShadowOptions.blur = this.blur * f;
        uniBoxShadowOptions.spread = this.spread * f;
        int i = 0;
        while (true) {
            float[] fArr = this.radii;
            if (i >= fArr.length) {
                break;
            }
            uniBoxShadowOptions.radii[i] = fArr[i] * f;
            i++;
        }
        uniBoxShadowOptions.viewHeight = (int) (this.viewHeight * f);
        uniBoxShadowOptions.viewWidth = (int) (this.viewWidth * f);
        if (this.topLeft != null) {
            PointF pointF = new PointF();
            uniBoxShadowOptions.topLeft = pointF;
            PointF pointF2 = this.topLeft;
            pointF.x = pointF2.x * f;
            pointF.y = pointF2.y * f;
        }
        uniBoxShadowOptions.color = this.color;
        uniBoxShadowOptions.isInset = this.isInset;
        WXLogUtils.d(TAG, "Scaled BoxShadowOptions: [" + f + "] " + uniBoxShadowOptions);
        return uniBoxShadowOptions;
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
}

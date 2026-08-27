package com.taobao.weex.layout.measurefunc;

import android.graphics.Canvas;
import android.os.Looper;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.TextDecorationSpan;
import com.taobao.weex.dom.WXCustomStyleSpan;
import com.taobao.weex.dom.WXLineHeightSpan;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.layout.MeasureMode;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXTextDecoration;
import com.taobao.weex.utils.WXDomUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;
import io.dcloud.feature.uniapp.dom.AbsAttr;
import io.dcloud.feature.uniapp.dom.AbsStyle;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TextContentBoxMeasurement extends ContentBoxMeasurement {
    private static final Canvas DUMMY_CANVAS = new Canvas();
    private static final String ELLIPSIS = "…";
    private AtomicReference<Layout> atomicReference;
    private boolean hasBeenMeasured;
    private Layout layout;
    protected Layout.Alignment mAlignment;
    protected int mColor;
    protected String mFontFamily;
    protected int mFontSize;
    protected int mFontStyle;
    protected int mFontWeight;
    protected boolean mIsColorSet;
    protected int mLineHeight;
    private int mNumberOfLines;
    private String mText;
    protected WXTextDecoration mTextDecoration;
    protected TextPaint mTextPaint;
    private float previousWidth;
    private Spanned spanned;
    private TextUtils.TruncateAt textOverflow;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    class SetSpanOperation {
        protected final int end;
        protected final int flag;
        protected final int start;
        protected final Object what;

        SetSpanOperation(TextContentBoxMeasurement textContentBoxMeasurement, int i, int i2, Object obj) {
            this(i, i2, obj, 17);
        }

        public void execute(Spannable spannable) {
            spannable.setSpan(this.what, this.start, this.end, this.flag);
        }

        SetSpanOperation(int i, int i2, Object obj, int i3) {
            this.start = i;
            this.end = i2;
            this.what = obj;
            this.flag = i3;
        }
    }

    public TextContentBoxMeasurement(WXComponent wXComponent) {
        super(wXComponent);
        this.mIsColorSet = false;
        this.hasBeenMeasured = false;
        this.mFontStyle = -1;
        this.mFontWeight = -1;
        this.mNumberOfLines = -1;
        this.mFontSize = -1;
        this.mLineHeight = -1;
        this.previousWidth = Float.NaN;
        this.mFontFamily = null;
        this.mText = null;
        this.mTextDecoration = WXTextDecoration.NONE;
        this.atomicReference = new AtomicReference<>();
    }

    private void adjustSpansRange(Spanned spanned, Spannable spannable) {
        for (Object obj : spanned.getSpans(0, spanned.length(), Object.class)) {
            int spanStart = spanned.getSpanStart(obj);
            int spanEnd = spanned.getSpanEnd(obj);
            if (spanStart == 0 && spanEnd == spanned.length()) {
                spannable.removeSpan(obj);
                spannable.setSpan(obj, 0, spannable.length(), spanned.getSpanFlags(obj));
            }
        }
    }

    private Layout createLayout(float f, Layout layout) {
        if (this.previousWidth != f || layout == null) {
            layout = new StaticLayout(this.spanned, this.mTextPaint, (int) Math.ceil(f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
        int i = this.mNumberOfLines;
        if (i != -1 && i > 0 && i < layout.getLineCount()) {
            int i2 = this.mNumberOfLines;
            if (i2 <= 1) {
                int iCeil = (int) Math.ceil(f);
                Spanned spanned = this.spanned;
                return StaticLayout.Builder.obtain(spanned, 0, spanned.length(), this.mTextPaint, iCeil).setMaxLines(1).setEllipsize(TextUtils.TruncateAt.END).setEllipsizedWidth(iCeil).build();
            }
            int lineStart = layout.getLineStart(i2 - 1);
            int lineEnd = layout.getLineEnd(this.mNumberOfLines - 1);
            if (lineStart < lineEnd) {
                SpannableStringBuilder spannableStringBuilder = lineStart > 0 ? new SpannableStringBuilder(this.spanned.subSequence(0, lineStart)) : new SpannableStringBuilder();
                double d = f;
                spannableStringBuilder.append((CharSequence) truncate(new SpannableStringBuilder(this.spanned.subSequence(lineStart, lineEnd)), this.mTextPaint, (int) Math.ceil(d), this.textOverflow));
                adjustSpansRange(this.spanned, spannableStringBuilder);
                this.spanned = spannableStringBuilder;
                return new StaticLayout(this.spanned, this.mTextPaint, (int) Math.ceil(d), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
        }
        return layout;
    }

    private float getTextWidth(TextPaint textPaint, float f, boolean z) {
        if (this.mText == null) {
            if (!z) {
                return 0.0f;
            }
        } else if (!z) {
            float desiredWidth = Layout.getDesiredWidth(this.spanned, textPaint);
            if (WXUtils.isUndefined(f) || desiredWidth < f) {
                return desiredWidth;
            }
        }
        return f;
    }

    private void recalculateLayout(float f) {
        float contentWidth = WXDomUtils.getContentWidth(this.mComponent.getPadding(), this.mComponent.getBorder(), f);
        if (contentWidth > 0.0f) {
            Spanned spannedCreateSpanned = createSpanned(this.mText);
            this.spanned = spannedCreateSpanned;
            if (spannedCreateSpanned == null) {
                this.previousWidth = 0.0f;
                return;
            }
            this.layout = createLayout(contentWidth, this.layout);
            this.previousWidth = r3.getWidth();
        }
    }

    private void swap() {
        Layout layout = this.layout;
        if (layout != null) {
            this.atomicReference.set(layout);
            this.layout = null;
        }
        this.hasBeenMeasured = false;
    }

    private Spanned truncate(Editable editable, TextPaint textPaint, int i, TextUtils.TruncateAt truncateAt) {
        SpannedString spannedString = new SpannedString("");
        if (!TextUtils.isEmpty(editable) && editable.length() > 0) {
            if (truncateAt != null) {
                editable.append(ELLIPSIS);
                for (Object obj : editable.getSpans(0, editable.length(), Object.class)) {
                    int spanStart = editable.getSpanStart(obj);
                    int spanEnd = editable.getSpanEnd(obj);
                    if (spanStart == 0 && spanEnd == editable.length() - 1) {
                        editable.removeSpan(obj);
                        editable.setSpan(obj, 0, editable.length(), editable.getSpanFlags(obj));
                    }
                }
            }
            while (editable.length() > 1) {
                int length = editable.length();
                int i2 = length - 1;
                if (truncateAt != null) {
                    i2 = length - 2;
                }
                editable.delete(i2, i2 + 1);
                if (new StaticLayout(editable, textPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false).getLineCount() <= 1) {
                    return editable;
                }
            }
        }
        return spannedString;
    }

    private void updateStyleAndText() {
        updateStyleImp(this.mComponent.getStyles());
        this.mText = AbsAttr.getValue(this.mComponent.getAttrs());
    }

    private void updateStyleImp(Map<String, Object> map) {
        if (map != null) {
            if (map.containsKey(Constants.Name.LINES)) {
                int lines = AbsStyle.getLines(map);
                if (lines <= 0) {
                    lines = -1;
                }
                this.mNumberOfLines = lines;
            }
            if (map.containsKey(Constants.Name.FONT_SIZE)) {
                this.mFontSize = AbsStyle.getFontSize(map, this.mComponent.getInstance().getDefaultFontSize(), this.mComponent.getViewPortWidthForFloat());
            }
            if (map.containsKey(Constants.Name.FONT_WEIGHT)) {
                this.mFontWeight = AbsStyle.getFontWeight(map);
            }
            if (map.containsKey(Constants.Name.FONT_STYLE)) {
                this.mFontStyle = AbsStyle.getFontStyle(map);
            }
            if (map.containsKey("color")) {
                int color = WXResourceUtils.getColor(AbsStyle.getTextColor(map));
                this.mColor = color;
                this.mIsColorSet = color != Integer.MIN_VALUE;
            }
            if (map.containsKey(Constants.Name.TEXT_DECORATION)) {
                this.mTextDecoration = AbsStyle.getTextDecoration(map);
            }
            if (map.containsKey(Constants.Name.FONT_FAMILY)) {
                this.mFontFamily = AbsStyle.getFontFamily(map);
            }
            this.mAlignment = AbsStyle.getTextAlignment(map, this.mComponent.isLayoutRTL());
            this.textOverflow = AbsStyle.getTextOverflow(map);
            int lineHeight = AbsStyle.getLineHeight(map, this.mComponent.getViewPortWidthForFloat());
            if (lineHeight != -1) {
                this.mLineHeight = lineHeight;
            }
        }
    }

    private boolean warmUpTextLayoutCache(Layout layout) {
        try {
            layout.draw(DUMMY_CANVAS);
            return true;
        } catch (Exception e) {
            WXLogUtils.eTag("TextWarmUp", e);
            return false;
        }
    }

    protected Spanned createSpanned(String str) {
        if (TextUtils.isEmpty(str)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(str);
        updateSpannable(spannableString, 17);
        return spannableString;
    }

    public void forceRelayout() {
        layoutBefore();
        measure(this.previousWidth, Float.NaN, MeasureMode.EXACTLY, MeasureMode.UNSPECIFIED);
        layoutAfter(this.previousWidth, Float.NaN);
    }

    @Override // com.taobao.weex.layout.ContentBoxMeasurement
    public void layoutAfter(float f, float f2) {
        WXComponent wXComponent = this.mComponent;
        if (wXComponent != null) {
            if (!this.hasBeenMeasured) {
                updateStyleAndText();
                recalculateLayout(f);
            } else if (this.layout != null && WXDomUtils.getContentWidth(wXComponent.getPadding(), this.mComponent.getBorder(), f) != this.previousWidth) {
                recalculateLayout(f);
            }
            this.hasBeenMeasured = false;
            Layout layout = this.layout;
            if (layout != null && !layout.equals(this.atomicReference.get()) && Thread.currentThread() != Looper.getMainLooper().getThread()) {
                warmUpTextLayoutCache(this.layout);
            }
            swap();
            WXSDKManager.getInstance().getWXRenderManager().postOnUiThread(new Runnable() { // from class: com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement.1
                @Override // java.lang.Runnable
                public void run() {
                    if (((ContentBoxMeasurement) TextContentBoxMeasurement.this).mComponent != null) {
                        ((ContentBoxMeasurement) TextContentBoxMeasurement.this).mComponent.updateExtra(TextContentBoxMeasurement.this.atomicReference.get());
                    }
                }
            }, this.mComponent.getInstanceId());
        }
    }

    @Override // com.taobao.weex.layout.ContentBoxMeasurement
    public void layoutBefore() {
        this.mTextPaint = new TextPaint(1);
        this.hasBeenMeasured = false;
        updateStyleAndText();
        this.spanned = createSpanned(this.mText);
    }

    @Override // com.taobao.weex.layout.ContentBoxMeasurement
    public void measureInternal(float f, float f2, int i, int i2) {
        this.hasBeenMeasured = true;
        float textWidth = getTextWidth(this.mTextPaint, f, i == MeasureMode.EXACTLY);
        if (textWidth <= 0.0f || this.spanned == null) {
            int i3 = MeasureMode.UNSPECIFIED;
            if (i == i3) {
                f = 0.0f;
            }
            if (i2 == i3) {
                f2 = 0.0f;
            }
        } else {
            this.layout = createLayout(textWidth, null);
            this.previousWidth = r6.getWidth();
            f = Float.isNaN(f) ? this.layout.getWidth() : Math.min(this.layout.getWidth(), f);
            if (Float.isNaN(f2)) {
                f2 = this.layout.getHeight();
            }
        }
        this.mMeasureWidth = f;
        this.mMeasureHeight = f2;
    }

    protected void setSpan(Spannable spannable, Object obj, int i, int i2, int i3) {
        spannable.setSpan(obj, i, i2, i3);
    }

    protected void updateSpannable(Spannable spannable, int i) {
        TextContentBoxMeasurement textContentBoxMeasurement;
        Spannable spannable2;
        int i2;
        int length = spannable.length();
        int i3 = this.mFontSize;
        if (i3 == -1) {
            this.mTextPaint.setTextSize(this.mComponent.getInstance().getDefaultFontSize());
        } else {
            this.mTextPaint.setTextSize(i3);
        }
        int i4 = this.mLineHeight;
        if (i4 != -1) {
            WXLineHeightSpan wXLineHeightSpan = new WXLineHeightSpan(i4);
            textContentBoxMeasurement = this;
            spannable2 = spannable;
            i2 = i;
            textContentBoxMeasurement.setSpan(spannable2, wXLineHeightSpan, 0, length, i2);
        } else {
            textContentBoxMeasurement = this;
            spannable2 = spannable;
            i2 = i;
        }
        textContentBoxMeasurement.setSpan(spannable2, new AlignmentSpan.Standard(textContentBoxMeasurement.mAlignment), 0, length, i2);
        if (textContentBoxMeasurement.mFontStyle != -1 || textContentBoxMeasurement.mFontWeight != -1 || textContentBoxMeasurement.mFontFamily != null) {
            textContentBoxMeasurement.setSpan(spannable2, new WXCustomStyleSpan(textContentBoxMeasurement.mFontStyle, textContentBoxMeasurement.mFontWeight, textContentBoxMeasurement.mFontFamily), 0, length, i2);
        }
        if (textContentBoxMeasurement.mIsColorSet) {
            textContentBoxMeasurement.mTextPaint.setColor(textContentBoxMeasurement.mColor);
        }
        WXTextDecoration wXTextDecoration = textContentBoxMeasurement.mTextDecoration;
        if (wXTextDecoration == WXTextDecoration.UNDERLINE || wXTextDecoration == WXTextDecoration.LINETHROUGH) {
            textContentBoxMeasurement.setSpan(spannable2, new TextDecorationSpan(textContentBoxMeasurement.mTextDecoration), 0, length, i2);
        }
    }
}

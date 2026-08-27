package io.dcloud.feature.nativeObj;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.dcloud.android.widget.AbsoluteLayout;
import com.dcloud.android.widget.StatusBarView;
import com.nostra13.dcloudimageloader.core.assist.FailReason;
import com.nostra13.dcloudimageloader.core.assist.ImageLoadingListener;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.INativeView;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.DHInterface.IWaiter;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.ui.FrameBitmapView;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.adapter.util.ViewRect;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TitleNViewUtil;
import io.dcloud.feature.nativeObj.richtext.RichTextLayout;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.nineoldandroids.view.ViewHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NativeView extends FrameLayout implements IWaiter, INativeView, IReflectAble {
    static final String T = "{clientX:%d,clientY:%d,pageX:%d,pageY:%d,screenX:%d,screenY:%d}";
    private boolean isAnimate;
    protected boolean isImmersed;
    public boolean isLayoutAdapt;
    public boolean isStatusBar;
    boolean isWebAnimationRuning;
    IApp mApp;
    public int mAppScreenHeight;
    public int mAppScreenWidth;
    boolean mAttached;
    protected int mBackGroundColor;
    protected String mBackgroundImageSrc;
    public NativeCanvasView mCanvasView;
    HashMap<String, INativeViewChildView> mChildViewMaps;
    public float mCreateScale;
    IFrameView mFrameViewParent;
    private Handler mHandler;
    String mID;
    IEventCallback mIEventCallback;
    protected int mInnerBottom;
    public int mInnerHeight;
    public int mInnerLeft;
    protected int mInnerRight;
    public int mInnerTop;
    public int mInnerWidth;
    boolean mIntercept;
    public int mMarginBottom;
    public int mMarginTop;
    HashMap<String, Integer> mOverlayMaps;
    ArrayList<Overlay> mOverlays;
    Paint mPaint;
    private int mRegionBottom;
    private JSONObject mRegionJson;
    private int mRegionLeft;
    private RectF mRegionRect;
    private int mRegionRight;
    private int mRegionTop;
    boolean mShow;
    public int mStatusColor;
    protected View mStatusbarView;
    public JSONObject mStyle;
    String mTouchRectJson;
    ArrayList<RectF> mTouchRects;
    float mTouchX;
    float mTouchY;
    public String mUUID;
    protected IWebview mWebView;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class NativeCanvasView extends View implements View.OnClickListener {
        private Runnable clickEventRunnable;
        HashMap<String, HashMap<String, IWebview>> doEventListenerMap;
        boolean isAddDoubleClickEvent;
        boolean isTouchDown;
        long mCurClickTime;
        long mLastClickTime;

        public NativeCanvasView(Context context) {
            super(context);
            this.doEventListenerMap = new HashMap<>(2);
            this.mCurClickTime = 0L;
            this.mLastClickTime = 0L;
            this.isAddDoubleClickEvent = false;
            this.clickEventRunnable = null;
            this.isTouchDown = false;
            setOnClickListener(this);
            setClickable(false);
        }

        private void drawRect(Canvas canvas, int i, int i2, int i3, int i4, Overlay overlay) {
            NativeView.this.mPaint.reset();
            NativeView.this.mPaint.setAntiAlias(true);
            RectF rectF = new RectF(i, i2, i3, i4);
            if (overlay.borderWidth <= 0.0f) {
                NativeView.this.mPaint.setColor(overlay.mRectColor);
                canvas.drawRoundRect(rectF, overlay.radius, overlay.radius, NativeView.this.mPaint);
                return;
            }
            int i5 = overlay.mRectColor;
            if (((-16777216) & i5) == 0) {
                NativeView.this.mPaint.setStyle(Paint.Style.STROKE);
                NativeView.this.mPaint.setStrokeWidth(overlay.borderWidth);
                NativeView.this.mPaint.setColor(overlay.borderColor);
                canvas.drawRoundRect(rectF, overlay.radius, overlay.radius, NativeView.this.mPaint);
                return;
            }
            NativeView.this.mPaint.setColor(i5);
            NativeView.this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rectF, overlay.radius, overlay.radius, NativeView.this.mPaint);
            NativeView.this.mPaint.setStyle(Paint.Style.STROKE);
            NativeView.this.mPaint.setStrokeWidth(overlay.borderWidth);
            NativeView.this.mPaint.setColor(overlay.borderColor);
            canvas.drawRoundRect(rectF, overlay.radius, overlay.radius, NativeView.this.mPaint);
        }

        private void initAuto(Overlay overlay) {
            Rect rect = overlay.mDestRect;
            if (rect.left == Integer.MIN_VALUE) {
                NativeView nativeView = NativeView.this;
                int i = nativeView.mInnerLeft;
                int i2 = nativeView.mInnerWidth;
                int i3 = rect.right;
                int i4 = i + ((i2 - i3) / 2);
                rect.left = i4;
                rect.right = i3 + i4;
            }
            if (rect.top == Integer.MIN_VALUE) {
                NativeView nativeView2 = NativeView.this;
                int i5 = nativeView2.mInnerTop;
                int i6 = nativeView2.mInnerHeight;
                int i7 = rect.bottom;
                int i8 = i5 + ((i6 - i7) / 2);
                rect.top = i8;
                rect.bottom = i7 + i8;
            }
        }

        private void postDelayedClickEvent() {
            if (this.clickEventRunnable == null) {
                this.clickEventRunnable = new Runnable() { // from class: io.dcloud.feature.nativeObj.NativeView.NativeCanvasView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        NativeCanvasView.this.doTypeEvent(Constants.Event.CLICK);
                        NativeCanvasView.this.clickEventRunnable = null;
                    }
                };
            }
            postDelayed(this.clickEventRunnable, 300L);
        }

        void addEventListener(String str, IWebview iWebview, String str2) {
            HashMap<String, IWebview> map = this.doEventListenerMap.get(str);
            if (map == null) {
                map = new HashMap<>(2);
                this.doEventListenerMap.put(str, map);
            }
            if (TextUtils.equals(str, Constants.Event.CLICK)) {
                setClickable(true);
            }
            if (TextUtils.equals(str, "doubleclick")) {
                this.isAddDoubleClickEvent = true;
            }
            Iterator<String> it = this.doEventListenerMap.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    iWebview.obtainFrameView().addFrameViewListener(new IEventCallback() { // from class: io.dcloud.feature.nativeObj.NativeView.NativeCanvasView.2
                        @Override // io.dcloud.common.DHInterface.IEventCallback
                        public Object onCallBack(String str3, Object obj) {
                            if (str3 != AbsoluteConst.EVENTS_CLOSE && str3 != AbsoluteConst.EVENTS_WINDOW_CLOSE) {
                                return null;
                            }
                            for (String str4 : NativeCanvasView.this.doEventListenerMap.keySet()) {
                                HashMap<String, IWebview> map2 = NativeCanvasView.this.doEventListenerMap.get(str4);
                                if (!map2.isEmpty()) {
                                    Set<String> setKeySet = map2.keySet();
                                    int size = setKeySet.size();
                                    String[] strArr = new String[size];
                                    setKeySet.toArray(strArr);
                                    for (int i = size - 1; i >= 0; i--) {
                                        String str5 = strArr[i];
                                        if (map2.get(str5) == obj) {
                                            map2.remove(str5);
                                        }
                                    }
                                }
                                if (map2.isEmpty() && TextUtils.equals(str4, Constants.Event.CLICK)) {
                                    NativeCanvasView.this.setClickable(false);
                                }
                            }
                            ((IWebview) obj).obtainFrameView().removeFrameViewListener(this);
                            return null;
                        }
                    });
                    break;
                }
                String next = it.next();
                HashMap<String, IWebview> map2 = this.doEventListenerMap.get(next);
                if (next != null && map2.containsValue(iWebview)) {
                    break;
                }
            }
            map.put(str2, iWebview);
        }

        public boolean doTypeEvent(String str) {
            HashMap<String, IWebview> map = this.doEventListenerMap.get(str);
            boolean z = false;
            if (map != null) {
                for (String str2 : map.keySet()) {
                    Deprecated_JSUtil.execCallback(map.get(str2), str2, NativeView.this.getEventJSON(), JSUtil.OK, true, true);
                    z = true;
                }
            }
            return z;
        }

        public boolean listenClick() {
            HashMap<String, IWebview> map = this.doEventListenerMap.get(Constants.Event.CLICK);
            return (map == null || map.isEmpty()) ? false : true;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            NativeView nativeView = NativeView.this;
            if (nativeView.checkTouchRectsContains(nativeView.mTouchX, nativeView.mTouchY)) {
                this.mLastClickTime = this.mCurClickTime;
                long jCurrentTimeMillis = System.currentTimeMillis();
                this.mCurClickTime = jCurrentTimeMillis;
                boolean z = this.isAddDoubleClickEvent;
                if (!z || jCurrentTimeMillis - this.mLastClickTime >= 300) {
                    if (z) {
                        postDelayedClickEvent();
                        return;
                    } else {
                        doTypeEvent(Constants.Event.CLICK);
                        return;
                    }
                }
                Runnable runnable = this.clickEventRunnable;
                if (runnable != null) {
                    removeCallbacks(runnable);
                }
                doTypeEvent("doubleclick");
            }
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            int i;
            int i2;
            int i3;
            Typeface typefaceCreate;
            NativeCanvasView nativeCanvasView = this;
            Canvas canvas2 = canvas;
            canvas2.save();
            int i4 = 0;
            canvas2.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            NativeView nativeView = NativeView.this;
            canvas2.clipRect(nativeView.mInnerLeft, nativeView.mInnerTop, nativeView.mInnerRight, nativeView.mInnerBottom);
            if (NativeView.this.getViewType().equals(AbsoluteConst.NATIVE_NVIEW)) {
                canvas2.drawColor(NativeView.this.mBackGroundColor);
            }
            if (NativeView.this.mRegionRect != null) {
                canvas2.clipRect(NativeView.this.mRegionRect, Region.Op.DIFFERENCE);
            }
            ArrayList<Overlay> arrayList = NativeView.this.mOverlays;
            int size = arrayList.size();
            int i5 = 0;
            while (i5 < size) {
                Overlay overlay = arrayList.get(i5);
                i5++;
                Overlay overlay2 = overlay;
                if (overlay2.type.equals("clear")) {
                    canvas2.clipRect(overlay2.mDestRect, Region.Op.DIFFERENCE);
                }
            }
            ArrayList<Overlay> arrayList2 = NativeView.this.mOverlays;
            int size2 = arrayList2.size();
            int i6 = 0;
            while (i6 < size2) {
                int i7 = i6 + 1;
                Overlay overlay3 = arrayList2.get(i6);
                NativeView.this.mPaint.reset();
                canvas2.save();
                NativeBitmap nativeBitmap = overlay3.mNativeBitmap;
                if (nativeBitmap != null && nativeBitmap.getBitmap() != null && !overlay3.mNativeBitmap.isRecycled() && !overlay3.mNativeBitmap.isGif()) {
                    Rect rect = overlay3.mDestRect;
                    if (rect.left == Integer.MIN_VALUE || rect.top == Integer.MIN_VALUE) {
                        nativeCanvasView.initAuto(overlay3);
                        canvas2.drawBitmap(overlay3.mNativeBitmap.getBitmap(), overlay3.mSrcRect, overlay3.mDestRect, NativeView.this.mPaint);
                    } else {
                        canvas2.clipRect(rect);
                        canvas2.drawBitmap(overlay3.mNativeBitmap.getBitmap(), overlay3.mSrcRect, overlay3.mDestRect, NativeView.this.mPaint);
                    }
                } else if (overlay3.mText != null) {
                    nativeCanvasView.initAuto(overlay3);
                    canvas2.clipRect(overlay3.mDestRect);
                    NativeView.this.mPaint.reset();
                    NativeView.this.mPaint.setAntiAlias(true);
                    int i8 = overlay3.mFontColor;
                    if (i8 != 0) {
                        NativeView.this.mPaint.setColor(i8);
                    }
                    if (overlay3.mFontColor == 0) {
                        NativeView.this.mPaint.setColor(i4);
                    }
                    float f = overlay3.mFontSize;
                    if (f != 0.0f) {
                        NativeView.this.mPaint.setTextSize(f);
                    }
                    if (!TextUtils.isEmpty(overlay3.textTTFPh)) {
                        Typeface typeface = NativeTypefaceFactory.getTypeface(NativeView.this.mApp, overlay3.textTTFPh);
                        if (typeface != null) {
                            NativeView.this.mPaint.setTypeface(typeface);
                        }
                    } else if (!TextUtils.isEmpty(overlay3.textFamily) && (typefaceCreate = Typeface.create(overlay3.textFamily, i4)) != null) {
                        NativeView.this.mPaint.setTypeface(typefaceCreate);
                    }
                    NativeView.this.mPaint.setFakeBoldText(overlay3.textWeight.equals(FrameBitmapView.BOLD));
                    if (overlay3.textStyle.equals(FrameBitmapView.ITALIC)) {
                        NativeView.this.mPaint.setTextSkewX(-0.5f);
                    }
                    NativeView.this.mPaint.setTextAlign(Paint.Align.CENTER);
                    int iCenterX = overlay3.mDestRect.centerX();
                    if (overlay3.textAlign.equals("right")) {
                        NativeView.this.mPaint.setTextAlign(Paint.Align.RIGHT);
                        iCenterX = overlay3.mDestRect.right;
                    } else if (overlay3.textAlign.equals("left")) {
                        NativeView.this.mPaint.setTextAlign(Paint.Align.LEFT);
                        iCenterX = overlay3.mDestRect.left;
                    }
                    String string = overlay3.mText;
                    if (overlay3.textDecoration.equals("underline")) {
                        NativeView.this.mPaint.setUnderlineText(true);
                    } else if (overlay3.textDecoration.equals("line-through")) {
                        NativeView.this.mPaint.setFlags(16);
                    }
                    TextPaint textPaint = new TextPaint();
                    textPaint.set(NativeView.this.mPaint);
                    if (overlay3.textWhiteSpace.equals("normal")) {
                        int iWidth = overlay3.mDestRect.width();
                        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
                        StaticLayout staticLayout = new StaticLayout(string, textPaint, iWidth, alignment, overlay3.textLineSpacing + 0.9f, 0.0f, true);
                        if (!overlay3.textAdapt && staticLayout.getHeight() > overlay3.mDestRect.height()) {
                            int lineEnd = staticLayout.getLineEnd((overlay3.mDestRect.height() / (staticLayout.getHeight() / staticLayout.getLineCount())) - 1);
                            String str = overlay3.textOverflow.equals(Constants.Name.ELLIPSIS) ? "…" : "";
                            StringBuilder sb = new StringBuilder();
                            if (!TextUtils.isEmpty(str)) {
                                lineEnd--;
                            }
                            sb.append(string.substring(0, lineEnd));
                            sb.append(str);
                            staticLayout = new StaticLayout(sb.toString(), textPaint, overlay3.mDestRect.width(), alignment, overlay3.textLineSpacing + 0.9f, 0.0f, false);
                        }
                        int iHeight = (overlay3.mDestRect.height() - staticLayout.getHeight()) / 2;
                        if (overlay3.textVerticalAligin.equals("top")) {
                            iHeight = 0;
                        } else if (overlay3.textVerticalAligin.equals("bottom")) {
                            iHeight = overlay3.mDestRect.height() - staticLayout.getHeight();
                        }
                        int i9 = iHeight + overlay3.mDestRect.top;
                        canvas2.save();
                        canvas2.translate(iCenterX, i9);
                        staticLayout.draw(canvas2);
                        canvas2.restore();
                    } else {
                        if (overlay3.textOverflow.equals(Constants.Name.ELLIPSIS)) {
                            string = TextUtils.ellipsize(string, textPaint, overlay3.mDestRect.width(), TextUtils.TruncateAt.END).toString();
                        }
                        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                        float f2 = fontMetrics.top;
                        float f3 = fontMetrics.bottom;
                        int iCenterY = (int) ((overlay3.mDestRect.centerY() - (f2 / 2.0f)) - (f3 / 2.0f));
                        if (overlay3.textVerticalAligin.equals("top")) {
                            iCenterY = (int) (overlay3.mDestRect.top - f2);
                        } else if (overlay3.textVerticalAligin.equals("bottom")) {
                            iCenterY = (int) (overlay3.mDestRect.bottom - f3);
                        }
                        canvas2.drawText(string, iCenterX, iCenterY, textPaint);
                    }
                } else if (overlay3.type.equals("rect")) {
                    canvas2.save();
                    Rect rect2 = overlay3.mDestRect;
                    int i10 = rect2.left;
                    if (i10 == Integer.MIN_VALUE || (i3 = rect2.top) == Integer.MIN_VALUE) {
                        int i11 = rect2.top;
                        int i12 = rect2.right;
                        int i13 = rect2.bottom;
                        if (i10 == Integer.MIN_VALUE) {
                            NativeView nativeView2 = NativeView.this;
                            int i14 = (nativeView2.mInnerWidth - (i12 - nativeView2.mInnerLeft)) / 2;
                            i = i12 + i14;
                            i2 = i14;
                        } else {
                            i = i12;
                            i2 = i10;
                        }
                        if (i11 == Integer.MIN_VALUE) {
                            NativeView nativeView3 = NativeView.this;
                            i11 = (nativeView3.mInnerHeight - (i13 - nativeView3.mInnerTop)) / 2;
                            i13 += i11;
                        }
                        nativeCanvasView.drawRect(canvas, i2, i11, i, i13, overlay3);
                    } else {
                        nativeCanvasView.drawRect(canvas2, i10, i3, rect2.right, rect2.bottom, overlay3);
                    }
                    canvas.restore();
                }
                canvas.restore();
                nativeCanvasView = this;
                canvas2 = canvas;
                i6 = i7;
                i4 = 0;
            }
            canvas.restore();
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            NativeView nativeView = NativeView.this;
            if (size != nativeView.mAppScreenWidth || size2 != nativeView.mAppScreenHeight) {
                nativeView.mAppScreenWidth = size;
                nativeView.mAppScreenHeight = size2;
                nativeView.init();
                Logger.e("NativeView", NativeView.this.mAppScreenWidth + ";onMeasure;" + NativeView.this.mAppScreenHeight);
            }
            NativeView nativeView2 = NativeView.this;
            int i3 = nativeView2.mInnerHeight + nativeView2.mInnerTop;
            int i4 = nativeView2.mAppScreenHeight;
            if (i3 <= i4) {
                i3 = i4;
            }
            nativeView2.mAppScreenHeight = i3;
            int i5 = 0;
            nativeView2.measureFitViewParent(false);
            IWebview iWebview = NativeView.this.mWebView;
            if (iWebview != null && iWebview.obtainApp() != null) {
                ArrayList<Overlay> arrayList = NativeView.this.mOverlays;
                int size3 = arrayList.size();
                while (i5 < size3) {
                    Overlay overlay = arrayList.get(i5);
                    i5++;
                    overlay.parseJson(NativeView.this.mWebView);
                }
            }
            NativeView nativeView3 = NativeView.this;
            setMeasuredDimension(nativeView3.mAppScreenWidth, nativeView3.mAppScreenHeight);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            NativeView.this.mTouchX = motionEvent.getX();
            NativeView.this.mTouchY = motionEvent.getY();
            NativeView nativeView = NativeView.this;
            if (!nativeView.mIntercept) {
                return false;
            }
            boolean zCheckTouchRectsContains = nativeView.checkTouchRectsContains(nativeView.mTouchX, nativeView.mTouchY);
            if (actionMasked != 0) {
                if (actionMasked == 1) {
                    this.isTouchDown = false;
                    if (zCheckTouchRectsContains) {
                        doTypeEvent("touchend");
                    }
                } else if (actionMasked != 2) {
                    if (actionMasked == 3) {
                        this.isTouchDown = false;
                        if (zCheckTouchRectsContains) {
                            doTypeEvent("touchcancel");
                        }
                    }
                } else if (this.isTouchDown) {
                    doTypeEvent("touchmove");
                }
            } else if (zCheckTouchRectsContains) {
                this.isTouchDown = true;
                doTypeEvent(AbsoluteConst.EVENTS_WEBVIEW_ONTOUCH_START);
            }
            if (zCheckTouchRectsContains) {
                if (NativeView.this.mIntercept) {
                    return listenClick() ? super.onTouchEvent(motionEvent) : NativeView.this.mIntercept;
                }
                return false;
            }
            if (listenClick()) {
                return false;
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class Overlay {
        private int borderColor;
        private int borderRadius;
        private float borderWidth;
        String callBackId;
        int inputBackgroundColor;
        String inputOnBlurCallBackId;
        String inputOnFocusCallBackId;
        String inputType;
        JSONObject mDestJson;
        Rect mDestRect;
        int mFontColor = -16777216;
        float mFontSize;
        NativeBitmap mNativeBitmap;
        NativeView mNativeView;
        int mRectColor;
        JSONObject mSrcJson;
        Rect mSrcRect;
        JSONObject mStyleJson;
        String mText;
        int margin;
        String placeholder;
        int placeholderColor;
        private float radius;
        boolean textAdapt;
        String textAlign;
        String textDecoration;
        String textFamily;
        float textLineSpacing;
        String textOverflow;
        String textStyle;
        String textTTFPh;
        String textVerticalAligin;
        String textWeight;
        String textWhiteSpace;
        String type;
        IWebview webview;

        Overlay() {
            String str = FrameBitmapView.NORMAL;
            this.textWeight = str;
            this.textStyle = str;
            this.textFamily = "";
            this.textAlign = "center";
            this.textTTFPh = null;
            this.textOverflow = "clip";
            this.textDecoration = "none";
            this.textWhiteSpace = "nowrap";
            this.textVerticalAligin = "middle";
            this.textLineSpacing = 0.2f;
            this.textAdapt = false;
            this.margin = 0;
            this.borderWidth = -1.0f;
            this.borderColor = -1;
            this.borderRadius = 0;
            this.radius = 0.0f;
            this.inputType = "text";
            this.inputBackgroundColor = 0;
            this.placeholder = "";
        }

        void parseJson(IWebview iWebview) {
            int i;
            NativeBitmap nativeBitmap = this.mNativeBitmap;
            if (nativeBitmap != null) {
                this.mSrcRect = NativeView.makeBitmapSrcRect(this.mNativeView, this.mSrcJson, nativeBitmap);
                if (this.mNativeBitmap.isNetWorkBitmap()) {
                    this.mNativeBitmap.initNetworkBitmap(new ImageLoadingListener() { // from class: io.dcloud.feature.nativeObj.NativeView.Overlay.1
                        @Override // com.nostra13.dcloudimageloader.core.assist.ImageLoadingListener
                        public void onLoadingCancelled(String str, View view) {
                        }

                        @Override // com.nostra13.dcloudimageloader.core.assist.ImageLoadingListener
                        public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                            NativeBitmap nativeBitmap2 = Overlay.this.mNativeBitmap;
                            if (nativeBitmap2 != null) {
                                nativeBitmap2.setBitmap(bitmap);
                                Overlay.this.mNativeBitmap.setNetWorkBitmapDownload(true);
                            }
                            NativeView nativeView = Overlay.this.mNativeView;
                            if (nativeView == null || nativeView.getParent() == null) {
                                return;
                            }
                            Overlay overlay = Overlay.this;
                            NativeView nativeView2 = overlay.mNativeView;
                            if (nativeView2.mCanvasView != null) {
                                overlay.mSrcRect = NativeView.makeBitmapSrcRect(nativeView2, overlay.mSrcJson, overlay.mNativeBitmap);
                                Overlay overlay2 = Overlay.this;
                                overlay2.mDestRect = NativeView.this.makeRect(overlay2.mNativeView, overlay2.mDestJson, this);
                                if (Overlay.this.mNativeBitmap.isGif()) {
                                    NativeView.this.addGifImagview(this);
                                } else {
                                    Overlay.this.mNativeView.mCanvasView.invalidate();
                                }
                            }
                        }

                        @Override // com.nostra13.dcloudimageloader.core.assist.ImageLoadingListener
                        public void onLoadingFailed(String str, View view, FailReason failReason) {
                        }

                        @Override // com.nostra13.dcloudimageloader.core.assist.ImageLoadingListener
                        public void onLoadingStarted(String str, View view) {
                        }
                    });
                }
            }
            JSONObject jSONObject = this.mStyleJson;
            if (jSONObject != null) {
                String strOptString = jSONObject.optString("color");
                if (!TextUtils.isEmpty(strOptString) && !strOptString.equals("null")) {
                    try {
                        this.mFontColor = Color.parseColor(strOptString);
                    } catch (Exception unused) {
                        this.mFontColor = PdrUtil.stringToColor(strOptString);
                    }
                }
                String strOptString2 = this.mStyleJson.optString(AbsoluteConst.JSON_KEY_SIZE);
                if (TextUtils.isEmpty(strOptString2)) {
                    strOptString2 = "16px";
                }
                NativeView nativeView = this.mNativeView;
                this.mFontSize = PdrUtil.convertToScreenInt(strOptString2, nativeView.mInnerWidth, 0, nativeView.mCreateScale);
                this.textWeight = this.mStyleJson.optString("weight", this.textWeight);
                this.textStyle = this.mStyleJson.optString("style", this.textStyle);
                this.textFamily = this.mStyleJson.optString("family", this.textFamily);
                this.textAlign = this.mStyleJson.optString(AbsoluteConst.JSON_KEY_ALIGN, this.textAlign);
                this.textOverflow = this.mStyleJson.optString(Constants.Name.OVERFLOW, this.textOverflow);
                this.textDecoration = this.mStyleJson.optString("decoration", this.textDecoration);
                this.textWhiteSpace = this.mStyleJson.optString("whiteSpace", this.textWhiteSpace);
                this.textVerticalAligin = this.mStyleJson.optString(AbsoluteConst.JSON_KEY_VERTICAL_ALIGN, this.textVerticalAligin);
                String str = "0px";
                if (this.mStyleJson.has(Constants.Name.BORDER_WIDTH)) {
                    String strOptString3 = this.mStyleJson.optString(Constants.Name.BORDER_WIDTH);
                    if (TextUtils.isEmpty(strOptString3)) {
                        strOptString3 = "0px";
                    }
                    NativeView nativeView2 = this.mNativeView;
                    this.borderWidth = PdrUtil.convertToScreenInt(strOptString3, nativeView2.mInnerWidth, 0, nativeView2.mCreateScale);
                }
                this.borderColor = this.mRectColor;
                if (this.mStyleJson.has(Constants.Name.BORDER_COLOR)) {
                    this.borderColor = PdrUtil.stringToColor(this.mStyleJson.optString(Constants.Name.BORDER_COLOR));
                }
                if (this.mStyleJson.has("radius")) {
                    String strOptString4 = this.mStyleJson.optString("radius");
                    if (TextUtils.isEmpty(strOptString4)) {
                        strOptString4 = "0px";
                    }
                    NativeView nativeView3 = this.mNativeView;
                    this.radius = PdrUtil.convertToScreenInt(strOptString4, nativeView3.mInnerWidth, 0, nativeView3.mCreateScale);
                }
                if (this.mStyleJson.has("lineSpacing")) {
                    this.textLineSpacing = PdrUtil.convertToScreenInt(this.mStyleJson.optString("lineSpacing"), (int) this.mFontSize, 0, this.mNativeView.mCreateScale) / this.mFontSize;
                }
                if (this.mStyleJson.has("fontSrc")) {
                    String strOptString5 = this.mStyleJson.optString("fontSrc", "");
                    if (strOptString5.contains("__wap2app.ttf")) {
                        String str2 = BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/__wap2app.ttf";
                        if (new File(str2).exists()) {
                            this.textTTFPh = str2;
                        } else {
                            this.textTTFPh = iWebview.obtainApp().convert2AbsFullPath(iWebview.obtainFullUrl(), strOptString5);
                        }
                    } else {
                        this.textTTFPh = iWebview.obtainApp().convert2AbsFullPath(iWebview.obtainFullUrl(), strOptString5);
                    }
                }
                String strOptString6 = this.mStyleJson.optString("margin", "0px");
                NativeView nativeView4 = this.mNativeView;
                this.margin = PdrUtil.convertToScreenInt(strOptString6, nativeView4.mInnerWidth, 0, nativeView4.mCreateScale);
                if ("input".equals(this.type)) {
                    this.inputType = "text";
                    if (this.mStyleJson.has("type")) {
                        String strOptString7 = this.mStyleJson.optString("type");
                        if (!TextUtils.isEmpty(strOptString7)) {
                            this.inputType = strOptString7;
                        }
                    }
                    this.placeholderColor = -7829368;
                    this.placeholder = "";
                    if (this.mStyleJson.has(Constants.Name.PLACEHOLDER)) {
                        this.placeholder = this.mStyleJson.optString(Constants.Name.PLACEHOLDER);
                    }
                    if (this.mStyleJson.has(Constants.Name.FONT_SIZE)) {
                        String strOptString8 = this.mStyleJson.optString(Constants.Name.FONT_SIZE);
                        String str3 = TextUtils.isEmpty(strOptString8) ? "16px" : strOptString8;
                        NativeView nativeView5 = this.mNativeView;
                        this.mFontSize = PdrUtil.convertToScreenInt(str3, nativeView5.mInnerWidth, 0, nativeView5.mCreateScale);
                    }
                    this.mFontColor = -16777216;
                    if (this.mStyleJson.has("fontColor")) {
                        String strOptString9 = this.mStyleJson.optString("fontColor");
                        if (!TextUtils.isEmpty(strOptString9)) {
                            if (Pattern.compile("^#[0-9a-fA-F]{6}$").matcher(strOptString9).matches()) {
                                this.mFontSize = PdrUtil.stringToColor(strOptString9);
                            }
                            if (Pattern.compile("^rgba\\(((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\,){3}([0-1]{1}|0\\.[1-9]{1})\\)$").matcher(strOptString9).matches()) {
                                this.mFontSize = PdrUtil.stringToColor(strOptString9);
                            }
                        }
                    }
                    this.inputBackgroundColor = 0;
                    if (this.mStyleJson.has("backgroundColor")) {
                        String strOptString10 = this.mStyleJson.optString("backgroundColor");
                        if (!TextUtils.isEmpty(strOptString10)) {
                            if (Pattern.compile("^#[0-9a-fA-F]{6}$").matcher(strOptString10).matches()) {
                                this.inputBackgroundColor = PdrUtil.stringToColor(strOptString10);
                            }
                            if (Pattern.compile("^rgba\\(((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\,){3}([0-1]{1}|0\\.[1-9]{1})\\)$").matcher(strOptString10).matches()) {
                                this.inputBackgroundColor = PdrUtil.stringToColor(strOptString10);
                            }
                        }
                    }
                    this.borderColor = -16777216;
                    if (this.mStyleJson.has(Constants.Name.BORDER_COLOR)) {
                        String strOptString11 = this.mStyleJson.optString(Constants.Name.BORDER_COLOR);
                        if (!TextUtils.isEmpty(strOptString11) && Pattern.compile("^#[0-9a-fA-F]{6}$").matcher(strOptString11).matches()) {
                            this.borderColor = PdrUtil.stringToColor(strOptString11);
                        }
                    }
                    NativeView nativeView6 = this.mNativeView;
                    String str4 = "1px";
                    this.borderWidth = PdrUtil.convertToScreenInt("1px", nativeView6.mInnerWidth, 0, nativeView6.mCreateScale);
                    if (this.mStyleJson.has(Constants.Name.BORDER_WIDTH)) {
                        String strOptString12 = this.mStyleJson.optString(Constants.Name.BORDER_WIDTH);
                        if (!TextUtils.isEmpty(strOptString12) && Pattern.compile("^[1-9]\\d*px$").matcher(strOptString12).matches()) {
                            try {
                                if (!TextUtils.isEmpty(strOptString12)) {
                                    str4 = strOptString12;
                                }
                                NativeView nativeView7 = this.mNativeView;
                                this.borderWidth = PdrUtil.convertToScreenInt(str4, nativeView7.mInnerWidth, 0, nativeView7.mCreateScale);
                            } catch (Exception unused2) {
                            }
                        }
                    }
                    this.borderRadius = 0;
                    if (this.mStyleJson.has(Constants.Name.BORDER_RADIUS)) {
                        String strOptString13 = this.mStyleJson.optString(Constants.Name.BORDER_RADIUS);
                        if (!TextUtils.isEmpty(strOptString13) && Pattern.compile("^[1-9]\\d*px$").matcher(strOptString13).matches()) {
                            try {
                                if (!TextUtils.isEmpty(strOptString13)) {
                                    str = strOptString13;
                                }
                                NativeView nativeView8 = this.mNativeView;
                                this.borderRadius = PdrUtil.convertToScreenInt(str, nativeView8.mInnerWidth, 0, nativeView8.mCreateScale);
                            } catch (Exception unused3) {
                            }
                        }
                    }
                    if (this.mStyleJson.has("__onCompleteCallBackId__")) {
                        this.callBackId = this.mStyleJson.optString("__onCompleteCallBackId__");
                        this.mStyleJson.remove("__onCompleteCallBackId__");
                    } else if (this.mStyleJson.has("onComplete")) {
                        String strOptString14 = this.mStyleJson.optString("onComplete");
                        if (strOptString14.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT) || strOptString14.startsWith("javaScript:")) {
                            this.callBackId = strOptString14;
                        }
                    }
                    if (this.mStyleJson.has("__onFocusCallBackId__")) {
                        this.inputOnFocusCallBackId = this.mStyleJson.optString("__onFocusCallBackId__");
                        this.mStyleJson.remove("__onFocusCallBackId__");
                    } else if (this.mStyleJson.has("onFocus")) {
                        String strOptString15 = this.mStyleJson.optString("onFocus");
                        if (strOptString15.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT) || strOptString15.startsWith("javaScript:")) {
                            this.inputOnFocusCallBackId = strOptString15;
                        }
                    }
                    if (this.mStyleJson.has("__onBlurCallBackId__")) {
                        this.inputOnBlurCallBackId = this.mStyleJson.optString("__onBlurCallBackId__");
                        this.mStyleJson.remove("__onBlurCallBackId__");
                    } else if (this.mStyleJson.has("onBlur")) {
                        String strOptString16 = this.mStyleJson.optString("onBlur");
                        if (strOptString16.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT) || strOptString16.startsWith("javaScript:")) {
                            this.inputOnBlurCallBackId = strOptString16;
                        }
                    }
                }
            }
            Rect rectMakeRect = NativeView.this.makeRect(this.mNativeView, this.mDestJson, this);
            this.mDestRect = rectMakeRect;
            if (rectMakeRect == null || (i = this.margin) == 0) {
                return;
            }
            rectMakeRect.left += i;
            rectMakeRect.top += i;
            rectMakeRect.right -= i;
            rectMakeRect.bottom -= i;
        }
    }

    public NativeView(Context context, IWebview iWebview, String str, String str2, JSONObject jSONObject) {
        super(context);
        this.mShow = false;
        this.mID = null;
        this.mUUID = null;
        this.mStyle = null;
        this.mApp = null;
        this.mPaint = new Paint();
        this.mOverlays = new ArrayList<>();
        this.mOverlayMaps = new HashMap<>();
        this.mChildViewMaps = new HashMap<>();
        this.mMarginTop = 0;
        this.mMarginBottom = 0;
        this.mCreateScale = 1.0f;
        this.mIntercept = true;
        this.isAnimate = false;
        this.mTouchRects = new ArrayList<>();
        this.mTouchRectJson = null;
        this.mBackGroundColor = 0;
        this.mBackgroundImageSrc = null;
        this.isWebAnimationRuning = false;
        this.mHandler = new Handler();
        this.isStatusBar = false;
        this.mCanvasView = null;
        this.mStatusbarView = null;
        this.isImmersed = false;
        this.isLayoutAdapt = false;
        this.mAttached = false;
        this.mFrameViewParent = null;
        this.mIEventCallback = null;
        this.mCanvasView = new NativeCanvasView(context);
        setWillNotDraw(false);
        IApp iAppObtainApp = iWebview.obtainApp();
        this.mApp = iAppObtainApp;
        int statusBarDefaultColor = iAppObtainApp.obtainStatusBarMgr().getStatusBarDefaultColor();
        if (statusBarDefaultColor != 0) {
            this.mStatusColor = statusBarDefaultColor;
        }
        this.mWebView = iWebview;
        this.mCreateScale = iWebview.getScale();
        this.mUUID = str;
        this.mID = str2;
        this.mStyle = jSONObject;
        this.isImmersed = this.mApp.obtainStatusBarMgr().checkImmersedStatusBar(this.mApp.getActivity(), Boolean.valueOf(this.mApp.obtainConfigProperty(AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED)).booleanValue());
        setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        attachCanvasView();
        initScreenData();
        try {
            setAlpha((float) jSONObject.optDouble("opacity", 1.0d));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void UpdateRegionData() {
        if (this.mRegionRect != null) {
            askRegionJson();
            RectF rectF = this.mRegionRect;
            rectF.left = this.mRegionLeft;
            rectF.right = this.mAppScreenWidth - this.mRegionRight;
            rectF.top = this.mRegionTop + this.mInnerTop;
            rectF.bottom = this.mInnerBottom - this.mRegionBottom;
        }
    }

    private void askRegionJson() {
        JSONObject jSONObject = this.mRegionJson;
        if (jSONObject != null) {
            this.mRegionLeft = PdrUtil.convertToScreenInt(jSONObject.optString("left"), this.mInnerWidth, 0, this.mCreateScale);
            this.mRegionRight = PdrUtil.convertToScreenInt(this.mRegionJson.optString("right"), this.mInnerWidth, 0, this.mCreateScale);
            this.mRegionTop = PdrUtil.convertToScreenInt(this.mRegionJson.optString("top"), this.mInnerHeight, 0, this.mCreateScale);
            this.mRegionBottom = PdrUtil.convertToScreenInt(this.mRegionJson.optString("bottom"), this.mInnerHeight, 0, this.mCreateScale);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkTouchRectsContains(float f, float f2) {
        ArrayList<RectF> arrayList = this.mTouchRects;
        int i = 0;
        if (arrayList == null || arrayList.size() <= 0) {
            return false;
        }
        ArrayList<RectF> arrayList2 = this.mTouchRects;
        int size = arrayList2.size();
        boolean z = false;
        while (i < size) {
            RectF rectF = arrayList2.get(i);
            i++;
            if (rectF.contains(f, f2)) {
                z = true;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endAnimatecallback(IWebview iWebview, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Deprecated_JSUtil.execCallback(iWebview, str, null, JSUtil.OK, false, false);
    }

    private int getDrawLeft(int i) {
        return this.mInnerLeft + i;
    }

    private int getDrawTop(int i) {
        return this.mInnerTop + i;
    }

    private EditText getInputById(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt != null && (childAt instanceof EditText) && childAt.getTag() != null && childAt.getTag().toString().equals(str)) {
                return (EditText) childAt;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void initScreenData() {
        /*
            r8 = this;
            io.dcloud.common.DHInterface.IApp r0 = r8.mApp
            if (r0 == 0) goto Lb6
            io.dcloud.common.DHInterface.IWebAppRootView r0 = r0.obtainWebAppRootView()
            r1 = 0
            if (r0 == 0) goto L16
            io.dcloud.common.DHInterface.IApp r0 = r8.mApp
            io.dcloud.common.DHInterface.IWebAppRootView r0 = r0.obtainWebAppRootView()
            android.view.View r0 = r0.obtainMainView()
            goto L17
        L16:
            r0 = r1
        L17:
            r2 = 1
            r3 = 0
            if (r0 != 0) goto L95
            r0 = 2
            java.lang.Class[] r4 = new java.lang.Class[r0]
            java.lang.Class<android.app.Activity> r5 = android.app.Activity.class
            r4[r3] = r5
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r2] = r5
            io.dcloud.common.DHInterface.IApp r5 = r8.mApp
            android.app.Activity r5 = r5.getActivity()
            io.dcloud.common.DHInterface.IApp r6 = r8.mApp
            java.lang.String r6 = r6.obtainAppId()
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r3] = r5
            r0[r2] = r6
            java.lang.String r5 = "isTitlebarVisible"
            java.lang.String r6 = "io.dcloud.appstream.actionbar.StreamAppActionBarUtil"
            java.lang.Object r0 = io.dcloud.common.adapter.util.PlatformUtil.invokeMethod(r6, r5, r1, r4, r0)
            boolean r4 = r0 instanceof java.lang.Boolean
            if (r4 == 0) goto L51
            java.lang.String r0 = r0.toString()
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            boolean r0 = r0.booleanValue()
            goto L52
        L51:
            r0 = 0
        L52:
            if (r0 == 0) goto L7b
            java.lang.Class[] r0 = new java.lang.Class[r2]
            java.lang.Class<android.app.Activity> r4 = android.app.Activity.class
            r0[r3] = r4
            io.dcloud.common.DHInterface.IApp r4 = r8.mApp
            android.app.Activity r4 = r4.getActivity()
            java.lang.Object[] r5 = new java.lang.Object[r2]
            r5[r3] = r4
            java.lang.String r4 = "getTitlebarHeightPx"
            java.lang.Object r0 = io.dcloud.common.adapter.util.PlatformUtil.invokeMethod(r6, r4, r1, r0, r5)
            boolean r1 = r0 instanceof java.lang.Integer
            if (r1 == 0) goto L7b
            java.lang.String r0 = r0.toString()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            int r0 = r0.intValue()
            goto L7c
        L7b:
            r0 = 0
        L7c:
            io.dcloud.common.DHInterface.IApp r1 = r8.mApp
            android.app.Activity r1 = r1.getActivity()
            android.view.Window r1 = r1.getWindow()
            android.view.View r1 = r1.getDecorView()
            r4 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r1 = r1.findViewById(r4)
            r7 = r1
            r1 = r0
            r0 = r7
            goto L96
        L95:
            r1 = 0
        L96:
            if (r0 == 0) goto Lb6
            io.dcloud.common.DHInterface.IApp r0 = r8.mApp
            android.app.Activity r0 = r0.getActivity()
            r0.getWindowManager()
            io.dcloud.common.DHInterface.IApp r0 = r8.mApp
            int r0 = r0.getInt(r3)
            io.dcloud.common.DHInterface.IApp r3 = r8.mApp
            int r2 = r3.getInt(r2)
            int r2 = r2 - r1
            r8.mAppScreenHeight = r2
            int r0 = r0 - r1
            r8.mAppScreenWidth = r0
            r8.init()
        Lb6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.nativeObj.NativeView.initScreenData():void");
    }

    static Rect makeBitmapSrcRect(NativeView nativeView, JSONObject jSONObject, NativeBitmap nativeBitmap) {
        Rect rect = new Rect();
        if (nativeBitmap.getBitmap() == null) {
            return rect;
        }
        int width = nativeBitmap.getBitmap().getWidth();
        int height = nativeBitmap.getBitmap().getHeight();
        if (jSONObject == null) {
            rect.left = 0;
            rect.top = 0;
            rect.right = width;
            rect.bottom = height;
            return rect;
        }
        int iConvertToScreenInt = PdrUtil.convertToScreenInt(jSONObject.optString("bottom"), height, 0, 1.0f);
        int iConvertToScreenInt2 = PdrUtil.convertToScreenInt(jSONObject.optString("right"), width, 0, 1.0f);
        int iConvertToScreenInt3 = PdrUtil.convertToScreenInt(jSONObject.optString("left"), width, 0, 1.0f);
        int iConvertToScreenInt4 = PdrUtil.convertToScreenInt(jSONObject.optString("top"), height, 0, 1.0f);
        if (iConvertToScreenInt2 == 0 || (jSONObject.has("width") && jSONObject.has("left"))) {
            rect.left = PdrUtil.convertToScreenInt(jSONObject.optString("left"), width, iConvertToScreenInt3, 1.0f);
            int iConvertToScreenInt5 = PdrUtil.convertToScreenInt(jSONObject.optString("width"), width, width, 1.0f) + rect.left;
            rect.right = iConvertToScreenInt5;
            if (iConvertToScreenInt5 <= width) {
                width = iConvertToScreenInt5;
            }
            rect.right = width;
        } else {
            rect.right = width - iConvertToScreenInt2;
            if (jSONObject.has("width")) {
                rect.left = rect.right - PdrUtil.convertToScreenInt(jSONObject.optString("width"), width, width, 1.0f);
            } else if (jSONObject.has("left")) {
                rect.left = PdrUtil.convertToScreenInt(jSONObject.optString("left"), width, iConvertToScreenInt3, 1.0f);
            } else {
                rect.left = iConvertToScreenInt3;
            }
        }
        if (iConvertToScreenInt == 0 || (jSONObject.has("height") && jSONObject.has("top"))) {
            rect.top = PdrUtil.convertToScreenInt(jSONObject.optString("top"), height, iConvertToScreenInt4, 1.0f);
            int iConvertToScreenInt6 = PdrUtil.convertToScreenInt(jSONObject.optString("height"), height, height, 1.0f) + rect.top;
            rect.bottom = iConvertToScreenInt6;
            if (iConvertToScreenInt6 <= height) {
                height = iConvertToScreenInt6;
            }
            rect.bottom = height;
            return rect;
        }
        rect.bottom = height - iConvertToScreenInt;
        if (jSONObject.has("height")) {
            rect.top = iConvertToScreenInt - PdrUtil.convertToScreenInt(jSONObject.optString("height"), height, height, 1.0f);
            return rect;
        }
        if (jSONObject.has("top")) {
            rect.top = PdrUtil.convertToScreenInt(jSONObject.optString("top"), height, iConvertToScreenInt4, 1.0f);
            return rect;
        }
        rect.top = iConvertToScreenInt4;
        return rect;
    }

    private void measureGifImageview(int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof GifImageView) {
                GifImageView gifImageView = (GifImageView) childAt;
                if (gifImageView.getTag() != null) {
                    Overlay overlay = (Overlay) gifImageView.getTag();
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) gifImageView.getLayoutParams();
                    Rect rect = overlay.mDestRect;
                    int i3 = rect.top;
                    if (i3 != Integer.MIN_VALUE) {
                        layoutParams.topMargin = i3 + i;
                    } else {
                        int i4 = rect.bottom;
                        layoutParams.height = i4;
                        layoutParams.topMargin = ((this.mInnerHeight - i4) + this.mInnerTop) / 2;
                    }
                }
            }
        }
    }

    private int pxFromDp(int i) {
        return (int) TypedValue.applyDimension(1, i, getResources().getDisplayMetrics());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runDrawRectF(final IWebview iWebview, final String str, final int i, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int i8, final int i9) {
        if (!this.isAnimate) {
            endAnimatecallback(iWebview, str);
            return;
        }
        if (this.mRegionRect == null) {
            this.mRegionRect = new RectF();
        }
        RectF rectF = this.mRegionRect;
        rectF.left = i;
        rectF.right = this.mAppScreenWidth - i2;
        float f = i3;
        rectF.top = f;
        if (i9 == i7) {
            rectF.bottom = (i6 * i9) + i3 + i8;
        } else {
            rectF.bottom = (i6 * i9) + i3;
        }
        if (this.isStatusBar) {
            float f2 = DeviceInfo.sStatusBarHeight;
            rectF.top = f + f2;
            rectF.bottom += f2;
        }
        postDelayed(new Runnable() { // from class: io.dcloud.feature.nativeObj.NativeView.4
            @Override // java.lang.Runnable
            public void run() {
                NativeView.this.invalidate();
                int i10 = i9;
                int i11 = i7;
                if (i10 == i11) {
                    NativeView.this.endAnimatecallback(iWebview, str);
                } else {
                    NativeView.this.runDrawRectF(iWebview, str, i, i2, i3, i4, i5, i6, i11, i8, i10 + 1);
                }
            }
        }, i5);
    }

    private void viewPostResize(final View view, final ViewGroup.LayoutParams layoutParams, final int i, final int i2, final int i3, final int i4) {
        view.post(new Runnable() { // from class: io.dcloud.feature.nativeObj.NativeView.3
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.LayoutParams layoutParams2 = layoutParams;
                layoutParams2.height = i2;
                layoutParams2.width = i;
                view.setLayoutParams(layoutParams2);
                ViewHelper.setY(view, i4);
                ViewHelper.setX(view, i3);
            }
        });
    }

    public void StartAnimate(IWebview iWebview, String str, String str2) throws Exception {
        if (this.isWebAnimationRuning || TextUtils.isEmpty(str)) {
            return;
        }
        if (getParent() == null) {
            endAnimatecallback(iWebview, str2);
            return;
        }
        this.isAnimate = true;
        JSONObject jSONObject = new JSONObject(str);
        String strOptString = jSONObject.optString("type");
        int iOptInt = jSONObject.optInt("duration", 200);
        int iOptInt2 = jSONObject.optInt("frames", 12);
        this.mRegionJson = jSONObject.optJSONObject("region");
        askRegionJson();
        int i = iOptInt / iOptInt2;
        int i2 = this.mInnerBottom - ((this.mRegionTop + this.mInnerTop) + this.mRegionBottom);
        int i3 = i2 / iOptInt2;
        int i4 = i2 - (i3 * iOptInt2);
        if (TextUtils.isEmpty(strOptString) || !strOptString.equals("shrink")) {
            return;
        }
        runDrawRectF(iWebview, str2, this.mRegionLeft, this.mRegionRight, this.mRegionTop + this.mInnerTop, this.mInnerBottom - this.mRegionBottom, i, i3, iOptInt2, i4, 1);
    }

    public void addEventListener(String str, IWebview iWebview, String str2) {
        this.mCanvasView.addEventListener(str, iWebview, str2);
    }

    public void addGifImagview(Overlay overlay) {
        GifDrawable gifDrawable = overlay.mNativeBitmap.getGifDrawable();
        if (gifDrawable != null) {
            GifImageView gifImageView = new GifImageView(this.mApp.getActivity());
            gifImageView.setImageDrawable(gifDrawable);
            gifImageView.setTag(overlay);
            int i = this.isStatusBar ? DeviceInfo.sStatusBarHeight / 2 : 0;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(overlay.mDestRect.width(), overlay.mDestRect.height());
            Rect rect = overlay.mDestRect;
            int i2 = rect.left;
            if (i2 != Integer.MIN_VALUE) {
                layoutParams.leftMargin = i2;
            } else {
                int i3 = rect.right;
                layoutParams.width = i3;
                layoutParams.leftMargin = ((this.mInnerWidth - i3) + this.mInnerLeft) / 2;
            }
            int i4 = rect.top;
            if (i4 != Integer.MIN_VALUE) {
                layoutParams.topMargin = i4 + i;
            } else {
                int i5 = rect.bottom;
                layoutParams.height = i5;
                layoutParams.topMargin = ((this.mInnerHeight - i5) + this.mInnerTop) / 2;
            }
            addView(gifImageView, layoutParams);
            requestLayout();
            invalidate();
            NativeCanvasView nativeCanvasView = this.mCanvasView;
            if (nativeCanvasView != null) {
                nativeCanvasView.requestLayout();
                this.mCanvasView.invalidate();
            }
        }
    }

    public void addInput(final Overlay overlay, final String str) {
        EditText inputById = getInputById(str);
        if (inputById == null) {
            inputById = new EditText(this.mApp.getActivity());
            inputById.setTag(str);
            inputById.setGravity(8388627);
            inputById.setSingleLine();
        }
        int i = 1;
        inputById.setImeOptions(1);
        if (!"text".equals(overlay.inputType)) {
            if ("email".equals(overlay.inputType)) {
                i = 33;
            } else if ("number".equals(overlay.inputType)) {
                i = 2;
            } else if ("search".equals(overlay.inputType)) {
                inputById.setImeOptions(3);
            } else if (Constants.Value.TEL.equals(overlay.inputType)) {
                i = 3;
            } else if ("url".equals(overlay.inputType)) {
                inputById.setImeOptions(2);
                i = 17;
            }
        }
        inputById.setInputType(i);
        inputById.getPaint().setTextSize(overlay.mFontSize);
        inputById.setTextColor(overlay.mFontColor);
        inputById.setHint(overlay.placeholder);
        inputById.setHintTextColor(overlay.placeholderColor);
        int i2 = (int) overlay.borderWidth;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(overlay.inputBackgroundColor);
        gradientDrawable.setShape(0);
        gradientDrawable.setStroke(i2, overlay.borderColor);
        gradientDrawable.setCornerRadius(overlay.borderRadius);
        int i3 = overlay.borderRadius + i2;
        inputById.setPadding(i3, i2, i3, i2);
        inputById.setBackground(gradientDrawable);
        inputById.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: io.dcloud.feature.nativeObj.NativeView.6
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i4, KeyEvent keyEvent) {
                String str2;
                IFrameView iFrameView;
                if ((i4 == 4 || i4 == 6 || i4 == 3 || i4 == 2 || (keyEvent != null && 66 == keyEvent.getKeyCode() && keyEvent.getAction() == 0)) && !TextUtils.isEmpty(overlay.callBackId)) {
                    if (overlay.callBackId.toLowerCase(Locale.ENGLISH).startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT)) {
                        String strObtainAppId = NativeView.this.mApp.obtainAppId();
                        IWebview iWebview = NativeView.this.mWebView;
                        AbsMgr absMgrObtainWindowMgr = (iWebview == null || iWebview.obtainFrameView() == null) ? null : NativeView.this.mWebView.obtainFrameView().obtainWindowMgr();
                        if (absMgrObtainWindowMgr == null && (iFrameView = NativeView.this.mFrameViewParent) != null) {
                            absMgrObtainWindowMgr = iFrameView.obtainWindowMgr();
                        }
                        if (absMgrObtainWindowMgr != null) {
                            Object objProcessEvent = absMgrObtainWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 47, strObtainAppId);
                            if (objProcessEvent instanceof IFrameView) {
                                IFrameView iFrameView2 = (IFrameView) objProcessEvent;
                                if (iFrameView2.obtainWebView() != null && iFrameView2.obtainWebView().obtainWindowView() != null) {
                                    iFrameView2.obtainWebView().loadUrl(overlay.callBackId);
                                    return true;
                                }
                            }
                        }
                        IWebview iWebview2 = overlay.webview;
                        if (iWebview2 != null && iWebview2.obtainWindowView() != null) {
                            Overlay overlay2 = overlay;
                            overlay2.webview.loadUrl(overlay2.callBackId);
                            return true;
                        }
                        IWebview iWebview3 = NativeView.this.mWebView;
                        if (iWebview3 != null && iWebview3.obtainWindowView() != null) {
                            NativeView.this.mWebView.loadUrl(overlay.callBackId);
                            return true;
                        }
                    }
                    if (overlay.webview != null) {
                        StringBuilder sb = new StringBuilder("{\"text\":\"");
                        sb.append((Object) textView.getText());
                        sb.append("\",\"id\":");
                        if (TextUtils.isEmpty(str)) {
                            str2 = "\"\"";
                        } else {
                            str2 = JSUtil.QUOTE + str + JSUtil.QUOTE;
                        }
                        sb.append(str2);
                        sb.append(Operators.BLOCK_END_STR);
                        String string = sb.toString();
                        Overlay overlay3 = overlay;
                        Deprecated_JSUtil.execCallback(overlay3.webview, overlay3.callBackId, string, JSUtil.OK, true, true);
                        if (overlay.webview.getOpener() != null) {
                            Deprecated_JSUtil.execCallback(overlay.webview.getOpener(), overlay.callBackId, string, JSUtil.OK, true, true);
                        }
                    }
                }
                return true;
            }
        });
        inputById.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: io.dcloud.feature.nativeObj.NativeView.7
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                String str2;
                IFrameView iFrameView;
                StringBuilder sb = new StringBuilder("{\"id\":");
                if (TextUtils.isEmpty(str)) {
                    str2 = "\"\"";
                } else {
                    str2 = JSUtil.QUOTE + str + JSUtil.QUOTE;
                }
                sb.append(str2);
                sb.append(Operators.BLOCK_END_STR);
                String string = sb.toString();
                String str3 = z ? overlay.inputOnFocusCallBackId : overlay.inputOnBlurCallBackId;
                if (TextUtils.isEmpty(str3)) {
                    return;
                }
                if (str3.toLowerCase(Locale.ENGLISH).startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT)) {
                    String strObtainAppId = NativeView.this.mApp.obtainAppId();
                    IWebview iWebview = NativeView.this.mWebView;
                    AbsMgr absMgrObtainWindowMgr = (iWebview == null || iWebview.obtainFrameView() == null) ? null : NativeView.this.mWebView.obtainFrameView().obtainWindowMgr();
                    if (absMgrObtainWindowMgr == null && (iFrameView = NativeView.this.mFrameViewParent) != null) {
                        absMgrObtainWindowMgr = iFrameView.obtainWindowMgr();
                    }
                    if (absMgrObtainWindowMgr != null) {
                        Object objProcessEvent = absMgrObtainWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 47, strObtainAppId);
                        if (objProcessEvent instanceof IFrameView) {
                            IFrameView iFrameView2 = (IFrameView) objProcessEvent;
                            if (iFrameView2.obtainWebView() != null && iFrameView2.obtainWebView().obtainWindowView() != null) {
                                iFrameView2.obtainWebView().loadUrl(str3);
                                return;
                            }
                        }
                    }
                    IWebview iWebview2 = overlay.webview;
                    if (iWebview2 != null && iWebview2.obtainWindowView() != null) {
                        overlay.webview.loadUrl(str3);
                        return;
                    }
                    IWebview iWebview3 = NativeView.this.mWebView;
                    if (iWebview3 != null && iWebview3.obtainWindowView() != null) {
                        NativeView.this.mWebView.loadUrl(str3);
                        return;
                    }
                }
                IWebview iWebview4 = overlay.webview;
                if (iWebview4 != null) {
                    Deprecated_JSUtil.execCallback(iWebview4, str3, string, JSUtil.OK, true, true);
                    if (overlay.webview.getOpener() != null) {
                        Deprecated_JSUtil.execCallback(overlay.webview.getOpener(), str3, string, JSUtil.OK, true, true);
                    }
                }
            }
        });
        int i4 = this.isStatusBar ? DeviceInfo.sStatusBarHeight : 0;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(overlay.mDestRect.width(), overlay.mDestRect.height());
        Rect rect = overlay.mDestRect;
        int i5 = rect.left;
        if (i5 != Integer.MIN_VALUE) {
            layoutParams.leftMargin = i5;
        } else {
            int i6 = rect.right;
            layoutParams.width = i6;
            layoutParams.leftMargin = ((this.mInnerWidth - i6) + this.mInnerLeft) / 2;
        }
        int i7 = rect.top;
        if (i7 != Integer.MIN_VALUE) {
            layoutParams.topMargin = i7 + i4;
        } else {
            int i8 = rect.bottom;
            layoutParams.height = i8;
            layoutParams.topMargin = ((this.mInnerHeight - i8) + this.mInnerTop) / 2;
        }
        addView(inputById, layoutParams);
        requestLayout();
        invalidate();
        NativeCanvasView nativeCanvasView = this.mCanvasView;
        if (nativeCanvasView != null) {
            nativeCanvasView.requestLayout();
            this.mCanvasView.invalidate();
        }
    }

    protected void attachCanvasView() {
        if (this.mCanvasView.getParent() != null) {
            ((ViewGroup) this.mCanvasView.getParent()).removeView(this.mCanvasView);
        }
        addView(this.mCanvasView, new FrameLayout.LayoutParams(-1, -2));
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public void attachToViewGroup(IFrameView iFrameView) {
        if (this.mAttached) {
            return;
        }
        this.mFrameViewParent = iFrameView;
        if (iFrameView instanceof AdaFrameView) {
            ((AdaFrameView) iFrameView).addNativeViewChild(this);
        }
        final String strObtainAppId = iFrameView.obtainApp().obtainAppId();
        IEventCallback iEventCallback = new IEventCallback() { // from class: io.dcloud.feature.nativeObj.NativeView.2
            @Override // io.dcloud.common.DHInterface.IEventCallback
            public Object onCallBack(String str, Object obj) {
                if (TextUtils.equals(str, AbsoluteConst.EVENTS_CLOSE)) {
                    FeatureImpl.destroyNativeView(strObtainAppId, NativeView.this);
                    PlatformUtil.invokeMethod("io.dcloud.feature.ad.AdFlowFeatureImpl", "destroyNativeView", null, new Class[]{String.class, NativeView.class}, new Object[]{strObtainAppId, NativeView.this});
                } else {
                    TextUtils.equals(str, AbsoluteConst.EVENTS_FRAME_ONRESIZE);
                }
                return null;
            }
        };
        this.mIEventCallback = iEventCallback;
        iFrameView.addFrameViewListener(iEventCallback);
        ViewParent parent = getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(this);
            setVisibility(0);
        }
        measureFitViewParent(true);
        this.mShow = true;
        this.mAttached = true;
    }

    public void clearAnimate() {
        this.isAnimate = false;
        this.mRegionRect = null;
        this.mRegionJson = null;
        invalidate();
        NativeCanvasView nativeCanvasView = this.mCanvasView;
        if (nativeCanvasView != null) {
            nativeCanvasView.requestLayout();
            this.mCanvasView.invalidate();
        }
    }

    public void clearNativeViewData() {
        IFrameView iFrameView = this.mFrameViewParent;
        if (iFrameView != null && (iFrameView instanceof AdaFrameView)) {
            ((AdaFrameView) iFrameView).removeNativeViewChild(this);
        }
        postDelayed(new Runnable() { // from class: io.dcloud.feature.nativeObj.NativeView.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    NativeView.this.setVisibility(8);
                    if (NativeView.this.getParent() != null) {
                        ((ViewGroup) NativeView.this.getParent()).removeView(NativeView.this);
                    }
                    NativeView.this.clearViewData();
                    NativeView.this.mFrameViewParent = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 200);
    }

    public void clearViewData() {
        NativeBitmap nativeBitmap;
        ArrayList<Overlay> arrayList = this.mOverlays;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Overlay overlay = arrayList.get(i);
            i++;
            Overlay overlay2 = overlay;
            if (overlay2.type.equals(WXBasicComponentType.IMG) && (nativeBitmap = overlay2.mNativeBitmap) != null && nativeBitmap.getBitmap() != null && !overlay2.mNativeBitmap.isRecycled()) {
                overlay2.mNativeBitmap.recycle(true);
            }
        }
        this.mOverlays.clear();
        this.mOverlayMaps.clear();
        this.mChildViewMaps.clear();
    }

    protected void configurationCange() {
    }

    RectF createTouchRect(JSONObject jSONObject) {
        RectF rectF = new RectF(makeRect(this, jSONObject, null));
        float f = rectF.left;
        if (f != -2.1474836E9f && rectF.top != -2.1474836E9f) {
            return rectF;
        }
        float f2 = rectF.top;
        float f3 = rectF.right;
        float f4 = rectF.bottom;
        if (f == -2.1474836E9f) {
            float f5 = this.mInnerWidth;
            float f6 = this.mInnerLeft;
            f = ((f5 - (f3 - f6)) / 2.0f) + f6;
            f3 += f;
        }
        if (f2 == -2.1474836E9f) {
            float f7 = this.mInnerHeight;
            float f8 = this.mInnerTop;
            f2 = ((f7 - (f4 - f8)) / 2.0f) + f8;
            f4 += f2;
        }
        return new RectF(f, f2, f3, f4);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        RectF rectF = this.mRegionRect;
        if (rectF != null) {
            canvas.clipRect(rectF, Region.Op.DIFFERENCE);
        }
        ArrayList<Overlay> arrayList = this.mOverlays;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Overlay overlay = arrayList.get(i);
            i++;
            Overlay overlay2 = overlay;
            if (overlay2.type.equals("clear")) {
                canvas.clipRect(overlay2.mDestRect, Region.Op.DIFFERENCE);
            }
        }
        super.dispatchDraw(canvas);
    }

    public Object doForFeature(String str, Object obj) {
        if (str.equals("clearAnimate")) {
            clearAnimate();
            return null;
        }
        if (!str.equals("checkTouch")) {
            return null;
        }
        MotionEvent motionEvent = (MotionEvent) obj;
        return checkTouchRectsContains(motionEvent.getX(), motionEvent.getY()) ? Boolean.valueOf(this.mIntercept) : Boolean.FALSE;
    }

    String getEventJSON() {
        return String.format(Locale.ENGLISH, T, Integer.valueOf((int) ((this.mTouchX - this.mInnerLeft) / this.mCreateScale)), Integer.valueOf((int) ((this.mTouchY - this.mInnerTop) / this.mCreateScale)), Integer.valueOf((int) (this.mTouchX / this.mCreateScale)), Integer.valueOf((int) (this.mTouchY / this.mCreateScale)), Integer.valueOf((int) (this.mTouchX / this.mCreateScale)), Integer.valueOf((int) (this.mTouchY / this.mCreateScale)));
    }

    public int getInnerBottom() {
        return this.mInnerBottom;
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public int getInnerHeight() {
        return this.mInnerHeight;
    }

    public boolean getInputFocusById(String str) {
        EditText inputById = getInputById(str);
        if (inputById != null) {
            return inputById.hasFocus();
        }
        return false;
    }

    public String getInputValueById(String str) {
        EditText inputById;
        if (TextUtils.isEmpty(str) || (inputById = getInputById(str)) == null || inputById.getText() == null) {
            return null;
        }
        return inputById.getText().toString();
    }

    protected int getNViewContentHeight() {
        ArrayList<Overlay> arrayList = this.mOverlays;
        if (arrayList == null) {
            return this.mAppScreenHeight;
        }
        this.mInnerHeight = this.mAppScreenHeight;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            Overlay overlay = arrayList.get(i);
            i++;
            Overlay overlay2 = overlay;
            int i3 = makeRect(this, overlay2.mDestJson, overlay2).bottom;
            if (i3 > i2) {
                i2 = i3;
            }
        }
        return i2;
    }

    public int getRectHeightForBitmap(NativeView nativeView, JSONObject jSONObject, Rect rect, Overlay overlay, int i) {
        if (overlay == null) {
            String strOptString = jSONObject.optString("height");
            int i2 = nativeView.mInnerHeight;
            return PdrUtil.convertToScreenInt(strOptString, i2, i2, nativeView.mCreateScale);
        }
        overlay.textAdapt = false;
        if (jSONObject.has("height") && "auto".equals(jSONObject.optString("height")) && rect != null) {
            if (jSONObject.has("width") && "auto".equals(jSONObject.optString("width"))) {
                return rect.height();
            }
            if (jSONObject.has("width")) {
                String strOptString2 = jSONObject.optString("width");
                int i3 = nativeView.mInnerWidth;
                return (int) (rect.height() * (PdrUtil.convertToScreenInt(strOptString2, i3, i3, nativeView.mCreateScale) / rect.width()));
            }
        } else if (jSONObject.has("height") && "wrap_content".equals(jSONObject.optString("height")) && PdrUtil.isEquals(overlay.type, AbsURIAdapter.FONT)) {
            overlay.textAdapt = true;
            if (!PdrUtil.isEquals(overlay.textWhiteSpace, "normal")) {
                return (int) (overlay.mFontSize + (overlay.margin * 2));
            }
            this.mPaint.reset();
            this.mPaint.setTextSize(overlay.mFontSize);
            return new StaticLayout(overlay.mText, new TextPaint(this.mPaint), i - (overlay.margin * 2), Layout.Alignment.ALIGN_NORMAL, overlay.textLineSpacing + 0.9f, 0.0f, false).getHeight() + (overlay.margin * 2);
        }
        String strOptString3 = jSONObject.optString("height");
        int i4 = nativeView.mInnerHeight;
        return PdrUtil.convertToScreenInt(strOptString3, i4, i4, nativeView.mCreateScale);
    }

    public int getRectWidthForBitmap(NativeView nativeView, JSONObject jSONObject, Rect rect) {
        if (jSONObject.has("width") && "auto".equals(jSONObject.optString("width")) && rect != null) {
            if (jSONObject.has("height") && "auto".equals(jSONObject.optString("height"))) {
                return rect.width();
            }
            if (jSONObject.has("height")) {
                String strOptString = jSONObject.optString("height");
                int i = nativeView.mInnerHeight;
                return (int) (rect.width() * (PdrUtil.convertToScreenInt(strOptString, i, i, nativeView.mCreateScale) / rect.height()));
            }
        }
        String strOptString2 = jSONObject.optString("width");
        int i2 = nativeView.mInnerWidth;
        return PdrUtil.convertToScreenInt(strOptString2, i2, i2, nativeView.mCreateScale);
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public String getStyleBackgroundColor() {
        JSONObject jSONObject = this.mStyle;
        if (jSONObject == null) {
            return null;
        }
        if (jSONObject.has("backgroudColor")) {
            return this.mStyle.optString("backgroudColor");
        }
        if (this.mStyle.has("backgroundColor")) {
            return this.mStyle.optString("backgroundColor");
        }
        return null;
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public int getStyleLeft() {
        return PdrUtil.convertToScreenInt(this.mStyle.optString("left"), this.mAppScreenWidth, 0, this.mCreateScale);
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public int getStyleWidth() {
        return this.mInnerWidth;
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public String getViewId() {
        return this.mID;
    }

    public String getViewType() {
        return AbsoluteConst.NATIVE_NVIEW;
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public String getViewUUId() {
        return this.mUUID;
    }

    protected void init() {
        int i;
        String str;
        JSONObject jSONObject = this.mStyle;
        if (jSONObject != null) {
            try {
                String strOptString = jSONObject.has("backgroudColor") ? this.mStyle.optString("backgroudColor") : this.mStyle.has("backgroundColor") ? this.mStyle.optString("backgroundColor") : null;
                if (!TextUtils.isEmpty(strOptString)) {
                    try {
                        this.mBackGroundColor = Color.parseColor(strOptString);
                    } catch (Exception unused) {
                        this.mBackGroundColor = PdrUtil.stringToColor(strOptString);
                    }
                } else if (TextUtils.equals(AbsoluteConst.NATIVE_IMAGESLIDER, getViewType())) {
                    this.mBackGroundColor = -1118482;
                }
                String strOptString2 = this.mStyle.has(Constants.Name.BACKGROUND_IMAGE) ? this.mStyle.optString(Constants.Name.BACKGROUND_IMAGE) : null;
                if (strOptString2 != null && ((str = this.mBackgroundImageSrc) == null || !str.equalsIgnoreCase(strOptString2))) {
                    this.mBackgroundImageSrc = strOptString2;
                }
                if (this.mStyle.has(AbsoluteConst.JSONKEY_STATUSBAR) && BaseInfo.isImmersive) {
                    this.isStatusBar = true;
                    JSONObject jSONObjectOptJSONObject = this.mStyle.optJSONObject(AbsoluteConst.JSONKEY_STATUSBAR);
                    if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.has("background")) {
                        String strOptString3 = jSONObjectOptJSONObject.optString("background");
                        if (PdrUtil.checkStatusbarColor(PdrUtil.stringToColor(strOptString3))) {
                            this.mStatusColor = PdrUtil.stringToColor(strOptString3);
                        }
                    }
                    if (AbsoluteConst.NATIVE_TITLE_N_VIEW.equals(getViewType()) && (i = this.mBackGroundColor) != 0 && this.mStatusColor != i && PdrUtil.checkStatusbarColor(i)) {
                        this.mStatusColor = this.mBackGroundColor;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        initStatusBarView(DeviceInfo.sStatusBarHeight);
        this.mInnerLeft = PdrUtil.convertToScreenInt(this.mStyle.optString("left"), this.mAppScreenWidth, 0, this.mCreateScale);
        int iConvertToScreenInt = PdrUtil.convertToScreenInt(this.mStyle.optString("top"), this.mAppScreenHeight, 0, this.mCreateScale);
        this.mMarginTop = iConvertToScreenInt;
        this.mInnerTop = iConvertToScreenInt;
        String strOptString4 = this.mStyle.optString("width");
        int i2 = this.mAppScreenWidth;
        this.mInnerRight = PdrUtil.convertToScreenInt(strOptString4, i2, i2, this.mCreateScale) + this.mInnerLeft;
        String strOptString5 = this.mStyle.optString("height");
        if (TextUtils.isEmpty(strOptString5) || !strOptString5.equals("wrap_content")) {
            this.isLayoutAdapt = false;
        } else {
            this.isLayoutAdapt = true;
        }
        if (!this.mStyle.has("bottom") || this.isLayoutAdapt) {
            if (this.isLayoutAdapt) {
                this.mInnerHeight = getNViewContentHeight();
            } else {
                int i3 = this.mAppScreenHeight;
                this.mInnerHeight = PdrUtil.convertToScreenInt(strOptString5, i3, i3, this.mCreateScale);
            }
            int i4 = this.mInnerHeight;
            if (i4 >= this.mAppScreenHeight || this.isLayoutAdapt) {
                this.mInnerBottom = i4;
            } else {
                this.mInnerBottom = i4 + this.mInnerTop;
            }
        } else {
            String strOptString6 = this.mStyle.optString("bottom");
            int i5 = this.mAppScreenHeight;
            int iConvertToScreenInt2 = PdrUtil.convertToScreenInt(strOptString6, i5, i5, this.mCreateScale);
            this.mMarginBottom = iConvertToScreenInt2;
            this.mInnerBottom = this.mAppScreenHeight - iConvertToScreenInt2;
            if (this.mStyle.has("height") && !this.mStyle.has("top")) {
                int i6 = this.mAppScreenHeight;
                int iConvertToScreenInt3 = PdrUtil.convertToScreenInt(strOptString5, i6, i6, this.mCreateScale);
                this.mInnerHeight = iConvertToScreenInt3;
                int i7 = this.mInnerBottom - iConvertToScreenInt3;
                this.mInnerTop = i7;
                if (i7 < 0) {
                    this.mInnerTop = 0;
                }
            } else if (this.mStyle.has("height")) {
                int i8 = this.mAppScreenHeight;
                this.mInnerHeight = PdrUtil.convertToScreenInt(strOptString5, i8, i8, this.mCreateScale);
            } else {
                this.mInnerHeight = this.mInnerBottom - this.mInnerTop;
            }
        }
        this.mInnerWidth = this.mInnerRight - this.mInnerLeft;
        initJsonTouchRect();
    }

    void initJsonTouchRect() {
        this.mTouchRects.clear();
        JSONObject jSONObject = null;
        JSONArray jSONArray = null;
        if (TextUtils.isEmpty(this.mTouchRectJson) || !this.mTouchRectJson.startsWith(Operators.ARRAY_START_STR)) {
            try {
                jSONObject = new JSONObject(this.mTouchRectJson);
            } catch (Exception unused) {
            }
            this.mTouchRects.add(createTouchRect(jSONObject));
            return;
        }
        try {
            jSONArray = new JSONArray(this.mTouchRectJson);
        } catch (Exception unused2) {
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject != null) {
                    this.mTouchRects.add(createTouchRect(jSONObjectOptJSONObject));
                }
            } catch (Exception unused3) {
            }
        }
    }

    void initStatusBarView(int i) {
        if (-1 == indexOfChild(this.mCanvasView)) {
            attachCanvasView();
        }
        if (this.isStatusBar) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (childAt.getTag() != null && childAt.getTag().equals("StatusBar")) {
                    childAt.setBackgroundColor(this.mStatusColor);
                    return;
                }
            }
            View view = new View(getContext());
            this.mStatusbarView = view;
            view.setTag("StatusBar");
            this.mStatusbarView.setBackgroundColor(this.mStatusColor);
            this.mStatusbarView.setVisibility(0);
            this.mStatusbarView.setLayoutParams(new FrameLayout.LayoutParams(-1, i));
            addView(this.mStatusbarView);
            this.mStatusbarView.bringToFront();
            measureFitViewParent(false);
        }
    }

    void interceptTouchEvent(boolean z) {
        this.mIntercept = z;
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public boolean isAnimate() {
        return this.mRegionRect != null;
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public boolean isDock() {
        return TextUtils.equals(this.mStyle.optString("position", AbsoluteConst.JSON_VALUE_POSITION_ABSOLUTE), "dock");
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public boolean isDockTop() {
        return TextUtils.equals(this.mStyle.optString("position", AbsoluteConst.JSON_VALUE_POSITION_ABSOLUTE), "dock") && TextUtils.equals(this.mStyle.optString("dock"), "top");
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public boolean isStatusBar() {
        return this.isStatusBar;
    }

    Overlay makeOverlay(IWebview iWebview, NativeBitmap nativeBitmap, String str, int i, JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, String str2, String str3, boolean z) {
        return makeOverlay(iWebview, nativeBitmap, str, i, jSONObject, jSONObject2, jSONObject3, str2, str3, true, z);
    }

    Rect makeRect(NativeView nativeView, JSONObject jSONObject, Overlay overlay) {
        Rect rect = new Rect();
        Rect rect2 = overlay != null ? overlay.mSrcRect : null;
        if (jSONObject == null) {
            rect.left = nativeView.mInnerLeft;
            rect.top = nativeView.mInnerTop;
            rect.right = nativeView.mInnerRight;
            rect.bottom = nativeView.mInnerBottom;
            return rect;
        }
        if (jSONObject.has("right") && (!jSONObject.has("left") || !jSONObject.has("width"))) {
            rect.right = nativeView.mInnerWidth - PdrUtil.convertToScreenInt(jSONObject.optString("right"), nativeView.mInnerWidth, 0, nativeView.mCreateScale);
            if (jSONObject.has("width")) {
                rect.left = rect.right - getRectWidthForBitmap(nativeView, jSONObject, rect2);
            } else if (!jSONObject.has("left") || "auto".equals(jSONObject.optString("left"))) {
                rect.left = nativeView.getDrawLeft(0);
            } else {
                rect.left = nativeView.getDrawLeft(PdrUtil.convertToScreenInt(jSONObject.optString("left"), nativeView.mInnerWidth, 0, nativeView.mCreateScale));
            }
        } else if ("auto".equals(jSONObject.optString("left"))) {
            rect.left = Integer.MIN_VALUE;
            rect.right = getRectWidthForBitmap(nativeView, jSONObject, rect2);
        } else {
            rect.left = nativeView.getDrawLeft(PdrUtil.convertToScreenInt(jSONObject.optString("left"), nativeView.mInnerWidth, 0, nativeView.mCreateScale));
            rect.right = getRectWidthForBitmap(nativeView, jSONObject, rect2) + rect.left;
        }
        if (!jSONObject.has("bottom") || (jSONObject.has("top") && jSONObject.has("height"))) {
            if ("auto".equals(jSONObject.optString("top"))) {
                rect.top = Integer.MIN_VALUE;
                rect.bottom = getRectHeightForBitmap(nativeView, jSONObject, rect2, overlay, rect.width());
                return rect;
            }
            rect.top = nativeView.getDrawTop(PdrUtil.convertToScreenInt(jSONObject.optString("top"), nativeView.mInnerHeight, 0, nativeView.mCreateScale));
            rect.bottom = getRectHeightForBitmap(nativeView, jSONObject, rect2, overlay, rect.width()) + rect.top;
            return rect;
        }
        rect.bottom = nativeView.mInnerBottom - PdrUtil.convertToScreenInt(jSONObject.optString("bottom"), nativeView.mInnerWidth, 0, nativeView.mCreateScale);
        if (jSONObject.has("height")) {
            rect.top = rect.bottom - getRectHeightForBitmap(nativeView, jSONObject, rect2, overlay, rect.width());
            return rect;
        }
        if (!jSONObject.has("top") || "auto".equals(jSONObject.optString("top"))) {
            rect.top = nativeView.getDrawTop(0);
            return rect;
        }
        rect.top = nativeView.getDrawTop(PdrUtil.convertToScreenInt(jSONObject.optString("top"), nativeView.mInnerHeight, 0, nativeView.mCreateScale));
        return rect;
    }

    public void makeRichText(IWebview iWebview, String str, JSONObject jSONObject, JSONObject jSONObject2, String str2) {
        if (PdrUtil.isEmpty(str2)) {
            str2 = "richtext_" + System.currentTimeMillis();
        }
        String str3 = str2;
        INativeViewChildView iNativeViewChildView = this.mChildViewMaps.get(str3);
        if (iNativeViewChildView != null) {
            RichTextLayout.makeRichText((RichTextLayout.RichTextLayoutHolder) iNativeViewChildView, str, jSONObject, jSONObject2);
            return;
        }
        RichTextLayout.RichTextLayoutHolder richTextLayoutHolderMakeRichText = RichTextLayout.makeRichText(getContext(), iWebview, this, str, jSONObject, jSONObject2, str3);
        addView(richTextLayoutHolderMakeRichText);
        this.mChildViewMaps.put(str3, richTextLayoutHolderMakeRichText);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void makeWeexView(IWebview iWebview, JSONObject jSONObject, String str) {
        if (PdrUtil.isEmpty(str)) {
            str = "weexview_" + System.currentTimeMillis();
        }
        if (this.mChildViewMaps.get(str) == null) {
            Object obj = (INativeViewChildView) iWebview.obtainFrameView().obtainWindowMgr().processEvent(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebview.obtainApp(), "weex,io.dcloud.feature.weex.WeexFeature", "createWeexWindow", new Object[]{iWebview, this, jSONObject, str}});
            addView((View) obj);
            this.mChildViewMaps.put(str, obj);
        }
    }

    protected void measureChildViewToTop(int i) {
        measureGifImageview(i);
    }

    protected void measureFitViewParent(boolean z) {
        View viewObtainMainView;
        ViewGroup viewGroup;
        int i;
        if (this.mFrameViewParent == null) {
            return;
        }
        String strOptString = this.mStyle.optString("position", AbsoluteConst.JSON_VALUE_POSITION_ABSOLUTE);
        if (TextUtils.equals(strOptString, "dock")) {
            ViewGroup viewGroup2 = (ViewGroup) ((AdaFrameItem) this.mFrameViewParent).obtainMainView();
            ViewGroup viewGroup3 = (ViewGroup) this.mFrameViewParent.obtainWebviewParent().obtainMainView();
            ViewGroup.LayoutParams layoutParams = viewGroup3.getLayoutParams();
            ViewOptions viewOptionsObtainFrameOptions = ((AdaFrameItem) this.mFrameViewParent).obtainFrameOptions();
            int left = z ? viewOptionsObtainFrameOptions.left : viewGroup3.getLeft();
            if (getTag() != null && "titleNView".equals(getTag().toString())) {
                left = 0;
            }
            int y = z ? viewOptionsObtainFrameOptions.top : (int) ViewHelper.getY(viewGroup3);
            int i2 = viewOptionsObtainFrameOptions.width;
            int i3 = this.mApp.getInt(1);
            int i4 = this.mAppScreenHeight;
            if (i3 >= i4) {
                i4 = this.mApp.getInt(1);
            }
            int i5 = i4 - viewOptionsObtainFrameOptions.bottom;
            String strOptString2 = this.mStyle.optString("dock", "top");
            ViewGroup viewGroup4 = (ViewGroup) this.mFrameViewParent.obtainMainView();
            if (TextUtils.equals(strOptString2, "left")) {
                left = this.mInnerWidth;
                this.mInnerRight = left;
                this.mInnerLeft = 0;
                i2 -= left;
                viewGroup = viewGroup3;
            } else if (TextUtils.equals(strOptString2, "top")) {
                this.mInnerBottom = this.mInnerHeight;
                this.mInnerTop = 0;
                int iIndexOfChild = viewGroup4.indexOfChild(this);
                int i6 = 0;
                int height = 0;
                int innerBottom = 0;
                int innerHeight = 0;
                while (i6 < viewGroup4.getChildCount()) {
                    View childAt = viewGroup4.getChildAt(i6);
                    ViewGroup viewGroup5 = viewGroup3;
                    if (childAt instanceof StatusBarView) {
                        i = iIndexOfChild;
                    } else {
                        if (childAt != null && (childAt instanceof NativeView) && childAt != this) {
                            boolean z2 = childAt instanceof TitleNView;
                            NativeView nativeView = (NativeView) childAt;
                            if (nativeView.isDock()) {
                                if (z2) {
                                    innerHeight += nativeView.getInnerHeight();
                                }
                                if (nativeView.isDockTop()) {
                                    innerBottom += nativeView.getInnerBottom();
                                    if (!z2 && (i6 < iIndexOfChild || iIndexOfChild == -1)) {
                                        innerHeight += nativeView.getInnerHeight();
                                    }
                                }
                                height += nativeView.getInnerHeight();
                            }
                        } else if (childAt instanceof AbsoluteLayout) {
                            AbsoluteLayout absoluteLayout = (AbsoluteLayout) childAt;
                            if (absoluteLayout.getFrameView() != null) {
                                i = iIndexOfChild;
                                if (absoluteLayout.getFrameView().mPosition == ViewRect.DOCK_BOTTOM) {
                                    height += childAt.getHeight();
                                }
                            }
                        }
                        i = iIndexOfChild;
                    }
                    i6++;
                    viewGroup3 = viewGroup5;
                    iIndexOfChild = i;
                }
                viewGroup = viewGroup3;
                int i7 = this.mInnerHeight;
                int i8 = innerBottom + i7;
                int i9 = height + i7;
                if (viewOptionsObtainFrameOptions.isStatusbar || (this.isImmersed && TitleNViewUtil.isTitleTypeForDef(viewOptionsObtainFrameOptions.titleNView))) {
                    int i10 = DeviceInfo.sStatusBarHeight;
                    int i11 = i9 + i10;
                    innerHeight += i10;
                    y = i8 + i10;
                    i8 = i11;
                } else {
                    if (this.isStatusBar) {
                        i8 += DeviceInfo.sStatusBarHeight;
                    }
                    y = i8;
                }
                int i12 = innerHeight;
                ((FrameLayout.LayoutParams) this.mCanvasView.getLayoutParams()).topMargin = i12;
                measureChildViewToTop(i12);
                i5 -= i8;
            } else {
                viewGroup = viewGroup3;
                if (TextUtils.equals(strOptString2, "right")) {
                    int right = viewGroup2.getRight();
                    this.mInnerRight = right;
                    int i13 = this.mInnerWidth;
                    this.mInnerLeft = right - i13;
                    i2 -= i13;
                } else {
                    int i14 = this.mAppScreenHeight;
                    this.mInnerBottom = i14;
                    if (i14 == 0) {
                        this.mInnerBottom = viewOptionsObtainFrameOptions.top + viewOptionsObtainFrameOptions.height;
                    }
                    int innerHeight2 = 0;
                    int innerBottom2 = 0;
                    for (int i15 = 0; i15 < viewGroup4.getChildCount(); i15++) {
                        View childAt2 = viewGroup4.getChildAt(i15);
                        if (childAt2 != null && (childAt2 instanceof NativeView) && childAt2 != this && (childAt2.getTag() == null || !childAt2.getTag().equals("StatusBar"))) {
                            NativeView nativeView2 = (NativeView) childAt2;
                            if (nativeView2.isDock()) {
                                if ((childAt2 instanceof TitleNView) && TitleNViewUtil.isTitleTypeForDef(nativeView2.mStyle)) {
                                    innerHeight2 = nativeView2.getInnerHeight();
                                }
                                if (nativeView2.isDockTop()) {
                                    innerBottom2 += nativeView2.getInnerBottom();
                                }
                            }
                        }
                    }
                    this.mInnerTop = this.mInnerBottom - this.mInnerHeight;
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mCanvasView.getLayoutParams();
                    if (this.isImmersed && (viewOptionsObtainFrameOptions.isStatusbar || TitleNViewUtil.isTitleTypeForDef(viewOptionsObtainFrameOptions.titleNView))) {
                        innerHeight2 += DeviceInfo.sStatusBarHeight;
                    }
                    layoutParams2.topMargin = innerHeight2;
                    measureChildViewToTop(innerHeight2);
                    if (viewOptionsObtainFrameOptions.isStatusbar || this.isStatusBar) {
                        int i16 = DeviceInfo.sStatusBarHeight;
                        innerBottom2 += i16;
                    }
                    y = innerBottom2;
                    i5 -= this.mInnerHeight + y;
                }
            }
            int i17 = i2;
            int i18 = i5;
            if (z) {
                viewGroup2.addView(this);
                if (viewOptionsObtainFrameOptions.titleNView != null) {
                    int i19 = 0;
                    while (true) {
                        if (i19 >= viewGroup2.getChildCount()) {
                            break;
                        }
                        View childAt3 = viewGroup2.getChildAt(i19);
                        if (childAt3 instanceof TitleNView) {
                            childAt3.bringToFront();
                            break;
                        }
                        i19++;
                    }
                }
            }
            viewPostResize(viewGroup, layoutParams, i17, i18, left, y);
        } else if (TextUtils.equals(strOptString, "static")) {
            if (z) {
                this.mFrameViewParent.obtainWebView().obtainWindowView().addView(this);
            }
        } else if (TextUtils.equals(strOptString, AbsoluteConst.JSON_VALUE_POSITION_ABSOLUTE)) {
            ViewOptions viewOptionsObtainFrameOptions2 = ((AdaFrameItem) this.mFrameViewParent).obtainFrameOptions();
            ViewGroup viewGroup6 = (ViewGroup) ((AdaFrameItem) this.mFrameViewParent).obtainMainView();
            int innerHeight3 = 0;
            for (int i20 = 0; i20 < viewGroup6.getChildCount(); i20++) {
                View childAt4 = viewGroup6.getChildAt(i20);
                if (childAt4 instanceof NativeView) {
                    NativeView nativeView3 = (NativeView) childAt4;
                    if (nativeView3.isDockTop() && nativeView3 != this) {
                        innerHeight3 += nativeView3.getInnerHeight();
                    }
                }
            }
            if (this.isImmersed && (viewOptionsObtainFrameOptions2.isStatusbar || TitleNViewUtil.isTitleTypeForDef(viewOptionsObtainFrameOptions2.titleNView))) {
                innerHeight3 += DeviceInfo.sStatusBarHeight;
            }
            if (this.isImmersed && !viewOptionsObtainFrameOptions2.isStatusbar && (this instanceof TitleNView)) {
                innerHeight3 += DeviceInfo.sStatusBarHeight;
            }
            for (int i21 = 0; i21 < getChildCount(); i21++) {
                KeyEvent.Callback childAt5 = getChildAt(i21);
                NativeCanvasView nativeCanvasView = this.mCanvasView;
                if (childAt5 == nativeCanvasView) {
                    ((FrameLayout.LayoutParams) nativeCanvasView.getLayoutParams()).topMargin = innerHeight3;
                    measureChildViewToTop(innerHeight3);
                } else if ((childAt5 instanceof INativeViewChildView) && (viewObtainMainView = ((INativeViewChildView) childAt5).obtainMainView()) != null) {
                    ViewGroup.LayoutParams layoutParams3 = viewObtainMainView.getLayoutParams();
                    if (layoutParams3 instanceof ViewGroup.MarginLayoutParams) {
                        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = innerHeight3;
                    }
                }
            }
            if (z) {
                viewGroup6.addView(this);
            }
        }
        if (this.mInnerHeight > this.mAppScreenHeight) {
            getLayoutParams().height = this.mInnerHeight;
        } else if (TextUtils.equals(strOptString, "static")) {
            getLayoutParams().height = this.mAppScreenHeight;
        } else {
            getLayoutParams().height = -1;
        }
        initJsonTouchRect();
        Collection<INativeViewChildView> collectionValues = this.mChildViewMaps.values();
        if (collectionValues != null) {
            Iterator<INativeViewChildView> it = collectionValues.iterator();
            while (it.hasNext()) {
                it.next().updateLayout();
            }
        }
    }

    public void nativeInvalidate(boolean z) {
        if (this.mShow) {
            if (z) {
                requestLayout();
            }
            postInvalidate();
            NativeCanvasView nativeCanvasView = this.mCanvasView;
            if (nativeCanvasView != null) {
                if (z) {
                    nativeCanvasView.requestLayout();
                }
                this.mCanvasView.invalidate();
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public View obtanMainView() {
        return this;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.isAnimate) {
            this.isAnimate = false;
        }
        postDelayed(new Runnable() { // from class: io.dcloud.feature.nativeObj.NativeView.1
            @Override // java.lang.Runnable
            public void run() {
                NativeView nativeView = NativeView.this;
                nativeView.mAppScreenWidth = nativeView.mApp.getInt(0);
                NativeView nativeView2 = NativeView.this;
                nativeView2.mAppScreenHeight = nativeView2.mApp.getInt(1);
                IWebview iWebview = NativeView.this.mWebView;
                if (iWebview != null && iWebview.obtainFrameView() != null) {
                    NativeView.this.init();
                    NativeView.this.UpdateRegionData();
                    NativeView.this.configurationCange();
                    NativeView.this.requestLayout();
                    NativeView.this.invalidate();
                }
                NativeCanvasView nativeCanvasView = NativeView.this.mCanvasView;
                if (nativeCanvasView != null) {
                    nativeCanvasView.requestLayout();
                    NativeView.this.mCanvasView.invalidate();
                }
            }
        }, 100L);
    }

    void removeFromViewGroup() {
        if (!this.mAttached) {
            if (getParent() != null) {
                ((ViewGroup) getParent()).removeView(this);
            }
            clearViewData();
            return;
        }
        IFrameView iFrameView = this.mFrameViewParent;
        if (iFrameView == null) {
            return;
        }
        iFrameView.removeFrameViewListener(this.mIEventCallback);
        String strOptString = this.mStyle.optString("position", AbsoluteConst.JSON_VALUE_POSITION_ABSOLUTE);
        ViewGroup viewGroupObtainWindowView = TextUtils.equals(strOptString, "dock") ? (ViewGroup) ((AdaFrameItem) this.mFrameViewParent).obtainMainView() : TextUtils.equals(strOptString, "static") ? this.mFrameViewParent.obtainWebView().obtainWindowView() : TextUtils.equals(strOptString, AbsoluteConst.JSON_VALUE_POSITION_ABSOLUTE) ? (ViewGroup) ((AdaFrameItem) this.mFrameViewParent).obtainMainView() : null;
        if (viewGroupObtainWindowView != null) {
            viewGroupObtainWindowView.removeView(this);
        } else {
            ViewParent parent = getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(this);
            }
        }
        if (this instanceof TitleNView) {
            ((AdaFrameView) this.mFrameViewParent).obtainFrameOptions().titleNView = null;
        }
        ((AdaFrameView) this.mFrameViewParent).changeWebParentViewRect();
        this.mAttached = false;
        this.mFrameViewParent = null;
        clearViewData();
    }

    public void resetNativeView() {
        clearViewData();
        clearAnimate();
    }

    public void setInputFocusById(String str, boolean z) {
        EditText inputById = getInputById(str);
        if (inputById != null) {
            if (z) {
                inputById.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) inputById.getContext().getSystemService("input_method");
                if (inputMethodManager != null) {
                    try {
                        inputMethodManager.showSoftInput(inputById, 0);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                return;
            }
            inputById.clearFocus();
            InputMethodManager inputMethodManager2 = (InputMethodManager) inputById.getContext().getSystemService("input_method");
            if (inputMethodManager2 != null) {
                try {
                    inputMethodManager2.hideSoftInputFromWindow(inputById.getApplicationWindowToken(), 0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public void setNativeShowType(boolean z) {
        this.mShow = z;
    }

    public void setStyle(JSONObject jSONObject, boolean z) {
        if (jSONObject != null) {
            try {
                JSONObject jSONObject2 = this.mStyle;
                if (jSONObject2 == null) {
                    this.mStyle = jSONObject;
                } else {
                    this.mStyle = JSONUtil.combinJSONObject(jSONObject2, jSONObject);
                }
                if (z) {
                    init();
                    requestLayout();
                    invalidate();
                    NativeCanvasView nativeCanvasView = this.mCanvasView;
                    if (nativeCanvasView != null) {
                        nativeCanvasView.requestLayout();
                        this.mCanvasView.invalidate();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public void setStyleBackgroundColor(String str) throws JSONException, NumberFormatException {
        int iStringToColor;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = this.mStyle;
            if (jSONObject != null) {
                if (jSONObject.has("backgroudColor")) {
                    this.mStyle.put("backgroudColor", str);
                } else if (this.mStyle.has("backgroundColor")) {
                    this.mStyle.put("backgroundColor", str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            iStringToColor = Color.parseColor(str);
        } catch (Exception unused) {
            iStringToColor = PdrUtil.stringToColor(str);
        }
        setStyleBackgroundColor(iStringToColor);
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public void setStyleLeft(int i) throws JSONException {
        try {
            this.mStyle.put("left", (Float.valueOf(String.valueOf(i)).floatValue() / this.mCreateScale) + "px");
            requestLayout();
            invalidate();
            NativeCanvasView nativeCanvasView = this.mCanvasView;
            if (nativeCanvasView != null) {
                nativeCanvasView.requestLayout();
                this.mCanvasView.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setTouchEventRect(String str) {
        this.mTouchRectJson = str;
        initJsonTouchRect();
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public void setWebAnimationRuning(boolean z) {
        this.isWebAnimationRuning = z;
    }

    @Override // io.dcloud.common.DHInterface.INativeView
    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", this.mID);
            jSONObject.put("uuid", this.mUUID);
            jSONObject.put("type", getViewType());
            JSONObject jSONObject2 = this.mStyle;
            if (jSONObject2 != null) {
                jSONObject.put("styles", jSONObject2.toString());
            }
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public Overlay makeOverlay(IWebview iWebview, NativeBitmap nativeBitmap, String str, int i, JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, String str2, String str3, boolean z, boolean z2) {
        Overlay overlay = new Overlay();
        this.mWebView = iWebview;
        overlay.mNativeView = this;
        overlay.mSrcJson = jSONObject;
        overlay.mDestJson = jSONObject2;
        overlay.mStyleJson = jSONObject3;
        overlay.mNativeBitmap = nativeBitmap;
        overlay.mText = str;
        overlay.mRectColor = i;
        overlay.type = str3;
        if (PdrUtil.isEmpty(str2)) {
            this.mOverlays.add(overlay);
        } else if (this.mOverlayMaps.containsKey(str2)) {
            this.mOverlays.set(this.mOverlayMaps.get(str2).intValue(), overlay);
        } else {
            this.mOverlays.add(overlay);
            this.mOverlayMaps.put(str2, Integer.valueOf(this.mOverlays.indexOf(overlay)));
        }
        overlay.parseJson(this.mWebView);
        if (str3.equals(WXBasicComponentType.IMG) && nativeBitmap != null && nativeBitmap.isGif()) {
            addGifImagview(overlay);
        }
        if (str3.equals("input")) {
            overlay.webview = iWebview;
            addInput(overlay, str2);
        }
        if (z) {
            nativeInvalidate(z2);
        }
        return overlay;
    }

    public void setStyleBackgroundColor(int i) {
        this.mBackGroundColor = i;
        NativeCanvasView nativeCanvasView = this.mCanvasView;
        if (nativeCanvasView != null) {
            nativeCanvasView.invalidate();
        }
    }
}

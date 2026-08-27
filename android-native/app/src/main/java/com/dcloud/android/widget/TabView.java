package com.dcloud.android.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.ui.blur.DCBlurDraweeView;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TitleNViewUtil;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.p.d5;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class TabView extends FrameLayout {
    private String mBackgroundColor;
    private String mBackgroundImage;
    private DCBlurDraweeView mBlurDraweeView;
    private String mBlurEffect;
    private String mBorderStyle;
    private View mBorderView;
    private JSONArray mCommonList;
    private String mCommonSelectedIndex;
    private Context mContext;
    private String mDefaultBackgroundColor;
    private String mDefaultBorderColor;
    private String mDefaultMaskBackgroundColor;
    private String mDefaultSelectedTextColor;
    private String mDefaultTextColor;
    private ICallBack mIDoubleCallback;
    private ICallBack mIMaskCallback;
    private ICallBack mIMidCallback;
    private ICallBack mISingleCallback;
    private String mIconfontPath;
    private String mImageSize;
    private LinearLayout mMask;
    private JSONObject mMidButton;
    private RelativeLayout mMidButtonView;
    private int mMidIndex;
    private View.OnTouchListener mMidTouchListener;
    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mOnMaskClickListener;
    private float mScale;
    private String mSelectedColor;
    private JSONObject mStyleJson;
    private LinearLayout mTabBar;
    private int mTabHeight;
    private String mTabHeightStr;
    private ArrayList<RelativeLayout> mTabItemViews;
    private String mTextColor;
    private String mTextSize;
    private String mTextTop;
    private IApp mWebApp;
    private int redDotColor;
    private String repeatType;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class BackGroundDrawable extends Drawable {
        private Rect bound;
        private Paint colorPaint;
        private Paint mPaint;
        private Shader mBackgroundBitmap = null;
        private String bitmapPath = null;
        private String repeat = "no-repeat";
        private int mBackgroundColor = 0;

        BackGroundDrawable() {
        }

        private Shader getShader(List<String> list, float f, float f2) {
            float[] gradientDirection = parseGradientDirection(list.get(0).trim(), f, f2);
            if (gradientDirection == null) {
                return null;
            }
            return new LinearGradient(gradientDirection[0], gradientDirection[1], gradientDirection[2], gradientDirection[3], PdrUtil.stringToColor(list.get(1).trim()), PdrUtil.stringToColor(list.get(2).trim()), Shader.TileMode.CLAMP);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0032  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private float[] parseGradientDirection(java.lang.String r9, float r10, float r11) {
            /*
                r8 = this;
                r0 = 4
                float[] r1 = new float[r0]
                r2 = 0
                r3 = 0
                r1[r2] = r3
                r4 = 1
                r1[r4] = r3
                r5 = 2
                r1[r5] = r3
                r6 = 3
                r1[r6] = r3
                boolean r3 = android.text.TextUtils.isEmpty(r9)
                if (r3 != 0) goto L24
                java.lang.String r3 = "\\s*"
                java.lang.String r7 = ""
                java.lang.String r9 = r9.replaceAll(r3, r7)
                java.util.Locale r3 = java.util.Locale.ENGLISH
                java.lang.String r9 = r9.toLowerCase(r3)
            L24:
                r9.getClass()
                r9.hashCode()
                int r3 = r9.hashCode()
                r7 = -1
                switch(r3) {
                    case -1352032154: goto L6e;
                    case -1137407871: goto L62;
                    case -868157182: goto L56;
                    case -172068863: goto L4a;
                    case 110550266: goto L40;
                    case 1176531318: goto L34;
                    default: goto L32;
                }
            L32:
                r0 = -1
                goto L79
            L34:
                java.lang.String r0 = "tobottomright"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L3e
                goto L32
            L3e:
                r0 = 5
                goto L79
            L40:
                java.lang.String r3 = "totop"
                boolean r9 = r9.equals(r3)
                if (r9 != 0) goto L79
                goto L32
            L4a:
                java.lang.String r0 = "totopleft"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L54
                goto L32
            L54:
                r0 = 3
                goto L79
            L56:
                java.lang.String r0 = "toleft"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L60
                goto L32
            L60:
                r0 = 2
                goto L79
            L62:
                java.lang.String r0 = "toright"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L6c
                goto L32
            L6c:
                r0 = 1
                goto L79
            L6e:
                java.lang.String r0 = "tobottom"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L78
                goto L32
            L78:
                r0 = 0
            L79:
                switch(r0) {
                    case 0: goto L91;
                    case 1: goto L8e;
                    case 2: goto L8b;
                    case 3: goto L86;
                    case 4: goto L83;
                    case 5: goto L7e;
                    default: goto L7c;
                }
            L7c:
                r9 = 0
                return r9
            L7e:
                r1[r5] = r10
                r1[r6] = r11
                return r1
            L83:
                r1[r4] = r11
                return r1
            L86:
                r1[r2] = r10
                r1[r4] = r11
                return r1
            L8b:
                r1[r2] = r10
                return r1
            L8e:
                r1[r5] = r10
                return r1
            L91:
                r1[r6] = r11
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.dcloud.android.widget.TabView.BackGroundDrawable.parseGradientDirection(java.lang.String, float, float):float[]");
        }

        private List<String> parseGradientValues(String str) {
            String strNextToken;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            str.getClass();
            try {
                if (str.startsWith("linear-gradient")) {
                    StringTokenizer stringTokenizer = new StringTokenizer(str.substring(str.indexOf(Operators.BRACKET_START_STR) + 1, str.lastIndexOf(Operators.BRACKET_END_STR)), ",");
                    ArrayList arrayList = new ArrayList();
                    while (true) {
                        String str2 = null;
                        while (stringTokenizer.hasMoreTokens()) {
                            strNextToken = stringTokenizer.nextToken();
                            if (strNextToken.contains(Operators.BRACKET_START_STR)) {
                                str2 = strNextToken + ",";
                            } else {
                                if (strNextToken.contains(Operators.BRACKET_END_STR)) {
                                    break;
                                }
                                if (str2 != null) {
                                    str2 = str2 + strNextToken + ",";
                                } else {
                                    arrayList.add(strNextToken);
                                }
                            }
                        }
                        return arrayList;
                        arrayList.add(str2 + strNextToken);
                    }
                }
            } catch (Exception unused) {
            }
            return null;
        }

        private Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
            if (bitmap == null) {
                return null;
            }
            if (this.repeat.equals("repeat")) {
                return bitmap;
            }
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            float f = i / width;
            float f2 = i2 / height;
            Matrix matrix = new Matrix();
            if (this.repeat.equals("repeat-x")) {
                matrix.preScale(1.0f, f2);
            } else if (this.repeat.equals("repeat-y")) {
                matrix.preScale(f, 1.0f);
            } else {
                matrix.preScale(f, f2);
            }
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.bound == null) {
                this.bound = getBounds();
            }
            if (this.mBackgroundBitmap != null) {
                getPaint().setShader(this.mBackgroundBitmap);
                getBackgroundColorPaint().setColor(Color.argb(getBackgroundColorPaint().getAlpha(), 255, 255, 255));
                canvas.drawRect(this.bound, getBackgroundColorPaint());
            } else {
                String str = this.bitmapPath;
                if (str != null) {
                    setBackgroundImage(str);
                    this.bitmapPath = null;
                    getBackgroundColorPaint().setColor(Color.argb(getBackgroundColorPaint().getAlpha(), 255, 255, 255));
                    canvas.drawRect(this.bound, getBackgroundColorPaint());
                    getPaint().setShader(this.mBackgroundBitmap);
                } else {
                    getPaint().setColor(this.mBackgroundColor);
                }
            }
            canvas.drawRect(this.bound, getPaint());
            if (this.mBackgroundBitmap != null) {
                getPaint().setShader(null);
            }
        }

        public Paint getBackgroundColorPaint() {
            if (this.colorPaint == null) {
                this.colorPaint = new Paint();
            }
            return this.colorPaint;
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return 0;
        }

        public Paint getPaint() {
            if (this.mPaint == null) {
                this.mPaint = new Paint(1);
            }
            return this.mPaint;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            if (this.mBackgroundBitmap == null && this.bitmapPath == null) {
                return;
            }
            getPaint().setAlpha(i);
            getBackgroundColorPaint().setAlpha(i);
        }

        public void setBackgroundColor(int i) {
            if (this.mBackgroundBitmap != null) {
                setAlpha(Color.alpha(i));
            } else {
                this.mBackgroundColor = i;
            }
            invalidateSelf();
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x0073  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x007b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void setBackgroundImage(java.lang.String r7) {
            /*
                r6 = this;
                android.graphics.Rect r0 = r6.bound
                if (r0 == 0) goto Lda
                int r0 = r0.width()
                if (r0 != 0) goto Lc
                goto Lda
            Lc:
                if (r7 != 0) goto Lf
                return
            Lf:
                java.util.List r0 = r6.parseGradientValues(r7)
                if (r0 == 0) goto L32
                int r1 = r0.size()
                r2 = 3
                if (r1 != r2) goto L32
                android.graphics.Rect r7 = r6.bound
                int r7 = r7.width()
                float r7 = (float) r7
                android.graphics.Rect r1 = r6.bound
                int r1 = r1.height()
                float r1 = (float) r1
                android.graphics.Shader r7 = r6.getShader(r0, r7, r1)
                r6.mBackgroundBitmap = r7
                goto Ld6
            L32:
                com.dcloud.android.widget.TabView r0 = com.dcloud.android.widget.TabView.this
                java.lang.String r7 = com.dcloud.android.widget.TabView.access$500(r0, r7)
                java.lang.String r0 = "file:///android_asset/"
                boolean r1 = r7.startsWith(r0)
                java.lang.String r2 = ""
                r3 = 0
                if (r1 == 0) goto L5f
                java.lang.String r7 = r7.replace(r0, r2)
                com.dcloud.android.widget.TabView r0 = com.dcloud.android.widget.TabView.this     // Catch: java.lang.Exception -> L5a
                android.content.Context r0 = r0.getContext()     // Catch: java.lang.Exception -> L5a
                android.content.res.AssetManager r0 = r0.getAssets()     // Catch: java.lang.Exception -> L5a
                java.io.InputStream r7 = r0.open(r7)     // Catch: java.lang.Exception -> L5a
                android.graphics.Bitmap r7 = android.graphics.BitmapFactory.decodeStream(r7)     // Catch: java.lang.Exception -> L5a
                goto L71
            L5a:
                r7 = move-exception
                r7.printStackTrace()
                goto L70
            L5f:
                java.lang.String r0 = "file://"
                boolean r1 = r7.startsWith(r0)
                if (r1 == 0) goto L70
                java.lang.String r7 = r7.replace(r0, r2)
                android.graphics.Bitmap r7 = android.graphics.BitmapFactory.decodeFile(r7)
                goto L71
            L70:
                r7 = r3
            L71:
                if (r7 != 0) goto L7b
                r6.mBackgroundBitmap = r3
                r6.bitmapPath = r3
                r6.invalidateSelf()
                return
            L7b:
                byte[] r0 = r7.getNinePatchChunk()
                if (r0 == 0) goto Lb8
                boolean r1 = android.graphics.NinePatch.isNinePatchChunk(r0)
                if (r1 == 0) goto Lb8
                android.graphics.drawable.NinePatchDrawable r1 = new android.graphics.drawable.NinePatchDrawable
                android.graphics.Rect r2 = new android.graphics.Rect
                r2.<init>()
                r1.<init>(r7, r0, r2, r3)
                android.graphics.Rect r7 = r6.bound
                int r7 = r7.width()
                android.graphics.Rect r0 = r6.bound
                int r0 = r0.height()
                android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.ARGB_8888
                android.graphics.Bitmap r7 = android.graphics.Bitmap.createBitmap(r7, r0, r2)
                android.graphics.Canvas r0 = new android.graphics.Canvas
                r0.<init>(r7)
                int r2 = r0.getWidth()
                int r4 = r0.getHeight()
                r5 = 0
                r1.setBounds(r5, r5, r2, r4)
                r1.draw(r0)
                goto Lc8
            Lb8:
                android.graphics.Rect r0 = r6.bound
                int r0 = r0.width()
                android.graphics.Rect r1 = r6.bound
                int r1 = r1.height()
                android.graphics.Bitmap r7 = r6.scaleBitmap(r7, r0, r1)
            Lc8:
                if (r7 != 0) goto Lcd
                r6.mBackgroundBitmap = r3
                goto Ld6
            Lcd:
                android.graphics.BitmapShader r0 = new android.graphics.BitmapShader
                android.graphics.Shader$TileMode r1 = android.graphics.Shader.TileMode.REPEAT
                r0.<init>(r7, r1, r1)
                r6.mBackgroundBitmap = r0
            Ld6:
                r6.invalidateSelf()
                return
            Lda:
                r6.bitmapPath = r7
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.dcloud.android.widget.TabView.BackGroundDrawable.setBackgroundImage(java.lang.String):void");
        }

        public void setBackgroundRepeat(String str, String str2) {
            if (PdrUtil.isEmpty(str) || str.equals(this.repeat) || TextUtils.isEmpty(str2)) {
                return;
            }
            this.repeat = str;
            this.mBackgroundBitmap = null;
            setBackgroundImage(str2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    public TabView(Context context, View view, JSONObject jSONObject, float f, IApp iApp) throws NumberFormatException {
        super(context);
        this.mDefaultTextColor = "#7A7E83";
        this.mDefaultSelectedTextColor = "#3cc51f";
        this.mDefaultBorderColor = "#000000";
        this.mDefaultBackgroundColor = TitleNViewUtil.TRANSPARENT_BUTTON_TEXT_COLOR;
        this.mDefaultMaskBackgroundColor = "#00000000";
        this.mImageSize = "24px";
        this.mTextTop = "3px";
        this.mTextSize = "10px";
        this.redDotColor = -65536;
        this.mBlurEffect = "none";
        this.mMidTouchListener = new View.OnTouchListener() { // from class: com.dcloud.android.widget.TabView.3
            long downTime = 0;
            float X = 0.0f;
            float Y = 0.0f;
            boolean downInMid = false;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (TabView.this.mMidButtonView == null || TabView.this.mMidButtonView.getParent() == null) {
                    this.downInMid = false;
                } else {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        RelativeLayout relativeLayout = TabView.this.mMidButtonView;
                        if (relativeLayout == null || relativeLayout.getVisibility() != 0) {
                            this.downInMid = false;
                        } else {
                            this.downInMid = new Rect(relativeLayout.getLeft(), relativeLayout.getTop() + TabView.this.mTabBar.getTop(), relativeLayout.getRight(), relativeLayout.getBottom() + TabView.this.mTabBar.getTop()).contains((int) motionEvent.getX(), (int) motionEvent.getY());
                        }
                        this.downTime = System.currentTimeMillis();
                        this.X = motionEvent.getX();
                        this.Y = motionEvent.getY();
                    } else if (action == 1 && this.downInMid && System.currentTimeMillis() - this.downTime < 500 && Math.abs(motionEvent.getY() - this.Y) < 70.0f && Math.abs(motionEvent.getX() - this.X) < 70.0f && TabView.this.mIMidCallback != null) {
                        TabView.this.mIMidCallback.onCallBack(0, null);
                    }
                }
                return this.downInMid;
            }
        };
        this.mOnClickListener = new View.OnClickListener() { // from class: com.dcloud.android.widget.TabView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) throws NumberFormatException {
                int iIntValue = ((Integer) view2.getTag()).intValue();
                TabView.this.switchTab(iIntValue);
                if (TabView.this.mISingleCallback != null) {
                    TabView.this.mISingleCallback.onCallBack(iIntValue, null);
                }
            }
        };
        this.mOnMaskClickListener = new View.OnClickListener() { // from class: com.dcloud.android.widget.TabView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (TabView.this.mIMaskCallback != null) {
                    TabView.this.mIMaskCallback.onCallBack(0, null);
                }
            }
        };
        this.mMidIndex = 0;
        this.mContext = context;
        this.mScale = f;
        this.mWebApp = iApp;
        setClipChildren(false);
        this.mStyleJson = jSONObject;
        if (jSONObject == null) {
            this.mStyleJson = new JSONObject();
        }
        if (jSONObject.containsKey("iconfontSrc")) {
            String string = jSONObject.getString("iconfontSrc");
            if (!PdrUtil.isNetPath(string)) {
                this.mIconfontPath = this.mWebApp.convert2AbsFullPath(string);
            }
        }
        this.mTextColor = this.mStyleJson.getString("color");
        this.mSelectedColor = this.mStyleJson.getString("selectedColor");
        this.mBackgroundColor = this.mStyleJson.getString("backgroundColor");
        this.mBackgroundImage = this.mStyleJson.getString(Constants.Name.BACKGROUND_IMAGE);
        this.repeatType = this.mStyleJson.getString("backgroundRepeat");
        if (this.mStyleJson.containsKey("blurEffect")) {
            this.mBlurEffect = this.mStyleJson.getString("blurEffect");
        }
        if (this.mStyleJson.containsKey("redDotColor")) {
            String string2 = this.mStyleJson.getString("redDotColor");
            if (!PdrUtil.isEmpty(string2)) {
                this.redDotColor = PdrUtil.stringToColor(string2);
            }
        }
        this.mBorderStyle = this.mStyleJson.getString(Constants.Name.BORDER_STYLE);
        if (this.mStyleJson.containsKey(Constants.Name.FONT_SIZE)) {
            this.mTextSize = this.mStyleJson.getString(Constants.Name.FONT_SIZE);
        }
        if (this.mStyleJson.containsKey(AbsoluteConst.JSON_KEY_ICON_WIDTH)) {
            this.mImageSize = this.mStyleJson.getString(AbsoluteConst.JSON_KEY_ICON_WIDTH);
        }
        float fApplyDimension = TypedValue.applyDimension(1, 72.0f, getResources().getDisplayMetrics());
        this.mTabHeightStr = !this.mStyleJson.containsKey("height") ? "50px" : this.mStyleJson.getString("height");
        if (this.mStyleJson.containsKey("spacing")) {
            this.mTextTop = this.mStyleJson.getString("spacing");
        }
        this.mTabHeight = (int) PdrUtil.parseFloat(this.mTabHeightStr, 0.0f, fApplyDimension, f);
        this.mCommonSelectedIndex = this.mStyleJson.getString("selected") == null ? WXInstanceApm.VALUE_ERROR_CODE_DEFAULT : this.mStyleJson.getString("selected");
        this.mCommonList = this.mStyleJson.getJSONArray(WXBasicComponentType.LIST);
        this.mMidButton = this.mStyleJson.getJSONObject("midButton");
        this.mTabItemViews = new ArrayList<>();
        this.mBorderView = new View(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, 1);
        layoutParams.bottomMargin = this.mTabHeight;
        layoutParams.gravity = 80;
        addView(this.mBorderView, layoutParams);
        LinearLayout linearLayout = new LinearLayout(context);
        this.mTabBar = linearLayout;
        linearLayout.setOrientation(0);
        this.mTabBar.setGravity(80);
        this.mTabBar.setClipChildren(false);
        if (this.mBlurEffect.equals(DCBlurDraweeView.LIGHT) || this.mBlurEffect.equals(DCBlurDraweeView.DARK) || this.mBlurEffect.equals(DCBlurDraweeView.EXTRALIGHT)) {
            this.mDefaultBackgroundColor = "#00FFFFFF";
            this.mBlurDraweeView = new DCBlurDraweeView(iApp.getActivity(), true, DCBlurDraweeView.SEMI_AUTOMATICALLY);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, this.mTabHeight);
            layoutParams2.gravity = 80;
            this.mBlurDraweeView.setDownscaleFactor(0.3f);
            this.mBlurDraweeView.setBlurRadius(20);
            this.mBlurDraweeView.setBlurEffect(this.mBlurEffect);
            this.mBlurDraweeView.setGravityType(80);
            addView(this.mBlurDraweeView, layoutParams2);
            this.mBlurDraweeView.setBlur(true);
            this.mBlurDraweeView.setRootView(view);
            this.mBlurDraweeView.setBlurLayoutChangeCallBack(new DCBlurDraweeView.BlurLayoutChangeCallBack() { // from class: com.dcloud.android.widget.TabView.1
                @Override // io.dcloud.common.ui.blur.DCBlurDraweeView.BlurLayoutChangeCallBack
                public void setVisibility(int i) {
                    if (TabView.this.mTabBar != null) {
                        TabView.this.mTabBar.setVisibility(i);
                    }
                }
            });
        }
        addView(this.mTabBar, new ViewGroup.LayoutParams(-1, this.mTabHeight));
        initTabStyle();
        initTabItemStyle();
        setSelectedStyle();
        setOnTouchListener(this.mMidTouchListener);
    }

    private void changeNavigationBarColor(final int i) {
        if (Build.VERSION.SDK_INT < 26 || this.mContext == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.dcloud.android.widget.TabView.6
            @Override // java.lang.Runnable
            public void run() {
                Window window = ((Activity) TabView.this.mContext).getWindow();
                if (TabView.this.mWebApp instanceof d5) {
                    ((d5) TabView.this.mWebApp).a(i);
                } else {
                    window.setNavigationBarColor(i);
                }
                int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                window.getDecorView().setSystemUiVisibility(PdrUtil.isLightColor(i) ? systemUiVisibility | 16 : systemUiVisibility & (-17));
            }
        }, 400L);
    }

    private ViewGroup getCommonItemByIndex(int i) {
        if (this.mTabItemViews.size() - 1 >= i) {
            return this.mTabItemViews.get(i);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getIconPath(String str) {
        String strConvert2AbsFullPath = this.mWebApp.convert2AbsFullPath(str);
        if (strConvert2AbsFullPath != null && PdrUtil.isDeviceRootDir(strConvert2AbsFullPath)) {
            return DeviceInfo.FILE_PROTOCOL + strConvert2AbsFullPath;
        }
        if (strConvert2AbsFullPath != null && strConvert2AbsFullPath.startsWith("/") && strConvert2AbsFullPath.length() > 1) {
            strConvert2AbsFullPath = strConvert2AbsFullPath.substring(1);
        }
        if (strConvert2AbsFullPath != null && strConvert2AbsFullPath.startsWith("android_asset/")) {
            strConvert2AbsFullPath = strConvert2AbsFullPath.replace("android_asset/", "");
        }
        return SDK.ANDROID_ASSET + strConvert2AbsFullPath;
    }

    private void initTabItemStyle() throws NumberFormatException {
        for (int i = 0; i < this.mCommonList.size(); i++) {
            setCommonItemStyle(i, (JSONObject) this.mCommonList.get(i));
        }
        updateMidItemStyle();
    }

    private void initTabStyle() {
        BackGroundDrawable backGroundDrawable;
        if (this.mTabBar.getBackground() instanceof BackGroundDrawable) {
            backGroundDrawable = (BackGroundDrawable) this.mTabBar.getBackground();
        } else {
            backGroundDrawable = new BackGroundDrawable();
            this.mTabBar.setBackground(backGroundDrawable);
        }
        backGroundDrawable.setBackgroundRepeat(this.repeatType, this.mBackgroundImage);
        int iOptColor = optColor(this.mBackgroundColor, this.mDefaultBackgroundColor);
        backGroundDrawable.setBackgroundColor(iOptColor);
        backGroundDrawable.setBackgroundImage(this.mBackgroundImage);
        changeNavigationBarColor(iOptColor);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mTabBar.getLayoutParams();
        layoutParams.gravity = 80;
        layoutParams.height = this.mTabHeight;
        this.mTabBar.setLayoutParams(layoutParams);
        this.mBorderView.setBackgroundColor(optColor(this.mBorderStyle, this.mDefaultBorderColor));
    }

    private static int optColor(String str, String str2) {
        try {
            return str == null ? PdrUtil.stringToColor(str2) : PdrUtil.stringToColor(str);
        } catch (Exception unused) {
            return PdrUtil.stringToColor(str2);
        }
    }

    private void placeholder(String str, ImageView imageView) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Glide.with(getContext()).load(getIconPath(str)).dontAnimate().placeholder(imageView.getDrawable()).into(imageView);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void setCommonItemJson(int i, String str, String str2, String str3, JSONObject jSONObject, boolean z) throws NumberFormatException {
        if (i >= this.mCommonList.size()) {
            return;
        }
        JSONObject jSONObject2 = this.mCommonList.getJSONObject(i);
        if (str != null) {
            jSONObject2.put("text", (Object) str);
        }
        if (str2 != null) {
            jSONObject2.put("iconPath", (Object) str2);
        }
        if (str3 != null) {
            jSONObject2.put("selectedIconPath", (Object) str3);
        }
        if (jSONObject != null) {
            if (jSONObject2.containsKey("iconfont")) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject("iconfont");
                if (jSONObject3 != null && (r5 = jSONObject.keySet().iterator()) != null) {
                    for (String str4 : jSONObject.keySet()) {
                        jSONObject3.put(str4, (Object) jSONObject.getString(str4));
                    }
                }
            } else {
                jSONObject2.put("iconfont", (Object) jSONObject);
            }
        }
        jSONObject2.put("visible", (Object) Boolean.valueOf(z));
        setCommonItemStyle(i, jSONObject2);
    }

    private void setCommonItemStyle(int i, JSONObject jSONObject) throws NumberFormatException {
        RelativeLayout relativeLayout;
        int i2;
        int i3;
        jSONObject.getString("pagePath");
        String string = jSONObject.getString("text");
        JSONObject jSONObject2 = jSONObject.getJSONObject("textLocales");
        if (jSONObject2 != null) {
            string = LanguageUtil.getString(jSONObject2, string);
        }
        String string2 = jSONObject.getString("iconPath");
        String string3 = jSONObject.getString("selectedIconPath");
        JSONObject jSONObject3 = jSONObject.getJSONObject("iconfont");
        int i4 = Integer.parseInt(this.mCommonSelectedIndex);
        boolean booleanValue = jSONObject.containsKey("visible") ? jSONObject.getBooleanValue("visible") : true;
        if (this.mTabItemViews.size() - 1 < i) {
            relativeLayout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.dcloud_tabbar_item, (ViewGroup) null);
            relativeLayout.setTag(Integer.valueOf(i));
            relativeLayout.setOnClickListener(this.mOnClickListener);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            layoutParams.weight = 1.0f;
            this.mTabBar.addView(relativeLayout, layoutParams);
            this.mTabItemViews.add(relativeLayout);
        } else {
            relativeLayout = this.mTabItemViews.get(i);
        }
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.tabIV);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.iconfontTV);
        if (jSONObject3 != null) {
            imageView.setVisibility(8);
            updateIconfont(textView, jSONObject3, PdrUtil.parseFloat(this.mImageSize, 0.0f, 0.0f, this.mScale), i4 == i);
        } else if (PdrUtil.isEmpty(string2) && PdrUtil.isEmpty(string3)) {
            imageView.setVisibility(8);
            textView.setVisibility(8);
        } else {
            if (!PdrUtil.isEmpty(string3) && i4 == i) {
                imageView.setVisibility(0);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams2.height = (int) PdrUtil.parseFloat(this.mImageSize, 0.0f, 0.0f, this.mScale);
                layoutParams2.width = (int) PdrUtil.parseFloat(this.mImageSize, 0.0f, 0.0f, this.mScale);
                imageView.setLayoutParams(layoutParams2);
                String iconPath = getIconPath(string3);
                try {
                    GifDrawable gifDrawable = iconPath.startsWith(SDK.ANDROID_ASSET) ? new GifDrawable(this.mContext.getAssets(), iconPath.replace(SDK.ANDROID_ASSET, "")) : new GifDrawable(getContext().getContentResolver(), Uri.parse(iconPath));
                    gifDrawable.setLoopCount(1);
                    imageView.setImageDrawable(gifDrawable);
                } catch (Exception unused) {
                    placeholder(string3, imageView);
                }
            } else if (PdrUtil.isEmpty(string2)) {
                i2 = 8;
                imageView.setVisibility(8);
                textView.setVisibility(i2);
            } else {
                imageView.setVisibility(0);
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams3.height = (int) PdrUtil.parseFloat(this.mImageSize, 0.0f, 0.0f, this.mScale);
                layoutParams3.width = (int) PdrUtil.parseFloat(this.mImageSize, 0.0f, 0.0f, this.mScale);
                imageView.setLayoutParams(layoutParams3);
                placeholder(string2, imageView);
            }
            i2 = 8;
            textView.setVisibility(i2);
        }
        TextView textView2 = (TextView) relativeLayout.findViewById(R.id.tabTV);
        textView2.setTag(jSONObject);
        ((GradientDrawable) ((ImageView) relativeLayout.findViewById(R.id.itemDot)).getDrawable()).setColor(this.redDotColor);
        if (PdrUtil.isEmpty(string)) {
            i3 = 8;
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) textView2.getLayoutParams();
            layoutParams4.topMargin = (int) PdrUtil.parseFloat(this.mTextTop, 0.0f, 0.0f, this.mScale);
            textView2.setLayoutParams(layoutParams4);
            textView2.setTextSize(0, PdrUtil.parseFloat(this.mTextSize, 0.0f, 0.0f, this.mScale));
            if (i4 != i) {
                textView2.setTextColor(optColor(this.mTextColor, this.mDefaultTextColor));
            } else {
                textView2.setTextColor(optColor(this.mSelectedColor, this.mDefaultSelectedTextColor));
            }
            textView2.setText(string);
            i3 = 8;
        }
        if (booleanValue) {
            relativeLayout.setVisibility(0);
        } else {
            relativeLayout.setVisibility(i3);
        }
    }

    private void setDotBadgeMarginTop(ViewGroup viewGroup, View view) {
        float f;
        float f2;
        View viewFindViewById = viewGroup.findViewById(R.id.tabIV);
        float height = viewGroup.getHeight() / this.mScale;
        float height2 = viewGroup.findViewById(R.id.contentWrapper).getHeight() / this.mScale;
        float height3 = view.getHeight() / this.mScale;
        float f3 = (height - height2) / 2.0f;
        if (viewFindViewById.getVisibility() == 0) {
            height3 /= 2.0f;
        }
        float f4 = f3 - height3;
        float f5 = 0.0f;
        if (f4 <= 2.0f) {
            height3 = ((f3 <= height3 || f4 >= 2.0f) && (f3 >= height3 || f3 <= 2.0f)) ? 0.0f : f3 - 2.0f;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = (int) ((-height3) * this.mScale);
        if (viewFindViewById.getVisibility() == 0) {
            if (view.getId() == R.id.itemDot) {
                f = this.mScale;
                f2 = -5.0f;
            } else {
                if (view.getId() == R.id.itemBadge) {
                    f = this.mScale;
                    f2 = -9.0f;
                }
                layoutParams.leftMargin = (int) (f5 - ((viewGroup.findViewById(R.id.contentWrapper).getWidth() - viewFindViewById.getWidth()) / 2));
            }
            f5 = f * f2;
            layoutParams.leftMargin = (int) (f5 - ((viewGroup.findViewById(R.id.contentWrapper).getWidth() - viewFindViewById.getWidth()) / 2));
        }
        view.setLayoutParams(layoutParams);
    }

    private void setSelectedStyle() throws NumberFormatException {
        for (int i = 0; i < this.mTabItemViews.size(); i++) {
            String str = this.mCommonSelectedIndex;
            if (str != null) {
                int i2 = Integer.parseInt(str);
                RelativeLayout relativeLayout = this.mTabItemViews.get(i);
                TextView textView = (TextView) relativeLayout.findViewById(R.id.tabTV);
                final ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.tabIV);
                TextView textView2 = (TextView) relativeLayout.findViewById(R.id.iconfontTV);
                JSONObject jSONObject = (JSONObject) textView.getTag();
                JSONObject jSONObject2 = jSONObject.getJSONObject("iconfont");
                if (i2 == i) {
                    textView.setTextColor(optColor(this.mSelectedColor, this.mDefaultSelectedTextColor));
                    if (jSONObject2 != null) {
                        if (jSONObject2.containsKey("selectedText")) {
                            textView2.setText(jSONObject2.getString("selectedText"));
                        }
                        if (jSONObject2.containsKey("selectedColor")) {
                            textView2.setTextColor(PdrUtil.stringToColor(jSONObject2.getString("selectedColor")));
                        }
                    } else {
                        String string = jSONObject.getString("selectedIconPath");
                        try {
                            String iconPath = getIconPath(string);
                            GifDrawable gifDrawable = iconPath.startsWith(SDK.ANDROID_ASSET) ? new GifDrawable(this.mContext.getAssets(), iconPath.replace(SDK.ANDROID_ASSET, "")) : new GifDrawable(getContext().getContentResolver(), Uri.parse(iconPath));
                            gifDrawable.setLoopCount(1);
                            gifDrawable.addAnimationListener(new AnimationListener() { // from class: com.dcloud.android.widget.TabView.2
                                @Override // pl.droidsonroids.gif.AnimationListener
                                public void onAnimationCompleted(int i3) {
                                    Drawable drawable = imageView.getDrawable();
                                    if (drawable instanceof GifDrawable) {
                                        GifDrawable gifDrawable2 = (GifDrawable) drawable;
                                        gifDrawable2.seekToFrame(gifDrawable2.getNumberOfFrames());
                                        gifDrawable2.removeAnimationListener(this);
                                    }
                                }
                            });
                            imageView.setImageDrawable(gifDrawable);
                        } catch (Exception unused) {
                            placeholder(string, imageView);
                        }
                    }
                } else {
                    textView.setTextColor(optColor(this.mTextColor, this.mDefaultTextColor));
                    if (jSONObject2 != null) {
                        if (jSONObject2.containsKey("text")) {
                            textView2.setText(jSONObject2.getString("text"));
                        }
                        if (jSONObject2.containsKey("color")) {
                            textView2.setTextColor(optColor(jSONObject2.getString("color"), this.mDefaultTextColor));
                        }
                    } else {
                        placeholder(jSONObject.getString("iconPath"), imageView);
                    }
                }
            }
        }
    }

    private void setTabItemStyle() throws NumberFormatException {
        initTabItemStyle();
    }

    private void updateMidItemStyle() {
        JSONObject jSONObject = this.mMidButton;
        boolean booleanValue = (jSONObject == null || !jSONObject.containsKey("visible")) ? true : this.mMidButton.getBooleanValue("visible");
        if (booleanValue) {
            booleanValue = canMidButtonShowDisplayed();
        }
        if (!booleanValue) {
            RelativeLayout relativeLayout = this.mMidButtonView;
            if (relativeLayout == null || relativeLayout.getParent() == null) {
                return;
            }
            this.mTabBar.removeView(this.mMidButtonView);
            return;
        }
        JSONObject jSONObject2 = this.mMidButton;
        if (jSONObject2 == null) {
            return;
        }
        float f = jSONObject2.getString("height") != null ? PdrUtil.parseFloat(this.mMidButton.getString("height"), 0.0f, 0.0f, this.mScale) : -1.0f;
        float f2 = this.mMidButton.getString("width") != null ? PdrUtil.parseFloat(this.mMidButton.getString("width"), 0.0f, 0.0f, this.mScale) : -1.0f;
        String string = this.mMidButton.getString("text");
        JSONObject jSONObject3 = this.mMidButton.getJSONObject("textLocales");
        if (jSONObject3 != null) {
            string = LanguageUtil.getString(jSONObject3, string);
        }
        float f3 = PdrUtil.parseFloat(this.mMidButton.getString(AbsoluteConst.JSON_KEY_ICON_WIDTH) != null ? this.mMidButton.getString(AbsoluteConst.JSON_KEY_ICON_WIDTH) : this.mImageSize, 0.0f, 0.0f, this.mScale);
        String string2 = this.mMidButton.getString("iconPath");
        String string3 = this.mMidButton.getString(Constants.Name.BACKGROUND_IMAGE);
        JSONObject jSONObject4 = this.mMidButton.getJSONObject("iconfont");
        RelativeLayout relativeLayout2 = this.mMidButtonView;
        if (relativeLayout2 == null) {
            relativeLayout2 = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.dcloud_tabbar_mid, (ViewGroup) null);
            this.mMidButtonView = relativeLayout2;
        }
        ((GradientDrawable) ((ImageView) relativeLayout2.findViewById(R.id.itemDot)).getDrawable()).setColor(this.redDotColor);
        float f4 = (int) PdrUtil.parseFloat(this.mImageSize, 0.0f, 0.0f, this.mScale);
        float f5 = (int) PdrUtil.parseFloat(this.mTextTop, 0.0f, 0.0f, this.mScale);
        float f6 = (int) PdrUtil.parseFloat(this.mTextSize, 0.0f, 0.0f, this.mScale);
        ImageView imageView = (ImageView) relativeLayout2.findViewById(R.id.tabIV);
        TextView textView = (TextView) relativeLayout2.findViewById(R.id.tabIconTV);
        if (jSONObject4 != null) {
            imageView.setVisibility(8);
            updateIconfont(textView, jSONObject4, f3, false);
        } else if (TextUtils.isEmpty(string2)) {
            textView.setVisibility(8);
            imageView.setVisibility(8);
        } else {
            textView.setVisibility(8);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            int i = (int) f3;
            layoutParams.height = i;
            layoutParams.width = i;
            imageView.setLayoutParams(layoutParams);
            placeholder(string2, imageView);
            imageView.setVisibility(0);
        }
        TextView textView2 = (TextView) relativeLayout2.findViewById(R.id.tabTV);
        textView2.setTextSize(0, PdrUtil.parseFloat(this.mTextSize, 0.0f, 0.0f, this.mScale));
        textView2.setTextColor(optColor(this.mTextColor, this.mDefaultTextColor));
        textView2.setText(string);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) textView2.getLayoutParams();
        layoutParams2.bottomMargin = (int) ((this.mTabHeight - ((f4 + f5) + f6)) / 2.0f);
        textView2.setLayoutParams(layoutParams2);
        if (TextUtils.isEmpty(string)) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
        }
        placeholder(string3, (ImageView) relativeLayout2.findViewById(R.id.bgImg));
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams((int) f2, (int) f);
        if (f2 == -1.0f) {
            layoutParams3.weight = 1.0f;
        }
        if (this.mMidButtonView.getParent() != null) {
            this.mTabBar.removeView(this.mMidButtonView);
        }
        this.mTabBar.addView(this.mMidButtonView, this.mMidIndex, layoutParams3);
    }

    public void bringMaskToFront() {
        LinearLayout linearLayout = this.mMask;
        if (linearLayout != null) {
            linearLayout.bringToFront();
        }
    }

    public boolean canMidButtonShowDisplayed() {
        int tabItemDisplayedSize = getTabItemDisplayedSize();
        boolean z = tabItemDisplayedSize % 2 == 0;
        if (z) {
            int i = tabItemDisplayedSize / 2;
            int i2 = 0;
            for (int i3 = 0; i3 < this.mCommonList.size(); i3++) {
                JSONObject jSONObject = this.mCommonList.getJSONObject(i3);
                if (jSONObject != null) {
                    if (!jSONObject.containsKey("visible") || jSONObject.getBoolean("visible").booleanValue()) {
                        i2++;
                    }
                    if (i2 == i) {
                        this.mMidIndex = i3 + 1;
                    }
                }
            }
        }
        return z;
    }

    public void dispose() {
        this.mTabItemViews.clear();
        this.mMidButtonView = null;
    }

    public int getMidHeight() {
        JSONObject jSONObject = this.mMidButton;
        if (jSONObject == null) {
            return 0;
        }
        return (int) PdrUtil.parseFloat(jSONObject.getString("height"), 0.0f, 0.0f, this.mScale);
    }

    public int getTabHeight() {
        DCBlurDraweeView dCBlurDraweeView = this.mBlurDraweeView;
        if (dCBlurDraweeView == null || !dCBlurDraweeView.checkBlurEffect(this.mBlurEffect)) {
            return this.mTabHeight;
        }
        return 0;
    }

    public String getTabHeightStr() {
        DCBlurDraweeView dCBlurDraweeView = this.mBlurDraweeView;
        return (dCBlurDraweeView == null || !dCBlurDraweeView.checkBlurEffect(this.mBlurEffect)) ? this.mTabHeightStr : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
    }

    public int getTabItemDisplayedSize() {
        int i = 0;
        for (int i2 = 0; i2 < this.mCommonList.size(); i2++) {
            JSONObject jSONObject = this.mCommonList.getJSONObject(i2);
            if (jSONObject != null && (!jSONObject.containsKey("visible") || jSONObject.getBoolean("visible").booleanValue())) {
                i++;
            }
        }
        return i;
    }

    public void hideTabBarRedDot(JSONObject jSONObject) {
        ViewGroup commonItemByIndex = getCommonItemByIndex(jSONObject.getInteger("index").intValue());
        if (commonItemByIndex != null) {
            ((ImageView) commonItemByIndex.findViewById(R.id.itemDot)).setVisibility(4);
        }
    }

    public void removeTabBarBadge(JSONObject jSONObject) {
        ViewGroup commonItemByIndex = getCommonItemByIndex(jSONObject.getInteger("index").intValue());
        if (commonItemByIndex != null) {
            ((TextView) commonItemByIndex.findViewById(R.id.itemBadge)).setVisibility(4);
        }
    }

    public void setDoubleCallbackListener(ICallBack iCallBack) {
        this.mIDoubleCallback = iCallBack;
    }

    public void setIWebViewFocusable(boolean z) {
        DCBlurDraweeView dCBlurDraweeView = this.mBlurDraweeView;
        if (dCBlurDraweeView == null || !dCBlurDraweeView.checkBlurEffect(this.mBlurEffect)) {
            return;
        }
        this.mBlurDraweeView.setContentFocusable(z);
    }

    public void setMask(JSONObject jSONObject) {
        String string = (jSONObject == null || !jSONObject.containsKey("color")) ? null : jSONObject.getString("color");
        if ("none".equals(string)) {
            if (this.mMask != null) {
                ((FrameLayout) getParent()).removeView(this.mMask);
                this.mMask = null;
                return;
            }
            return;
        }
        if (this.mMask == null) {
            LinearLayout linearLayout = new LinearLayout(this.mContext);
            this.mMask = linearLayout;
            linearLayout.setOnClickListener(this.mOnMaskClickListener);
            this.mMask.setBackgroundColor(optColor(string, this.mDefaultMaskBackgroundColor));
            ((FrameLayout) getParent()).addView(this.mMask, new ViewGroup.LayoutParams(-1, -1));
        }
    }

    public void setMaskCallbackListener(ICallBack iCallBack) {
        this.mIMaskCallback = iCallBack;
    }

    public void setMidCallbackListener(ICallBack iCallBack) {
        this.mIMidCallback = iCallBack;
    }

    public void setSingleCallbackListener(ICallBack iCallBack) {
        this.mISingleCallback = iCallBack;
    }

    public void setTabBarBadge(JSONObject jSONObject) {
        int iIntValue = jSONObject.getInteger("index").intValue();
        String string = jSONObject.getString("text");
        ViewGroup commonItemByIndex = getCommonItemByIndex(iIntValue);
        if (commonItemByIndex != null) {
            ImageView imageView = (ImageView) commonItemByIndex.findViewById(R.id.itemDot);
            TextView textView = (TextView) commonItemByIndex.findViewById(R.id.itemBadge);
            imageView.setVisibility(4);
            textView.setText(string);
            setDotBadgeMarginTop(commonItemByIndex, textView);
            textView.setVisibility(0);
        }
    }

    public void setTabBarItem(JSONObject jSONObject) throws NumberFormatException {
        setCommonItemJson(jSONObject.getInteger("index").intValue(), jSONObject.getString("text"), jSONObject.getString("iconPath"), jSONObject.getString("selectedIconPath"), jSONObject.containsKey("iconfont") ? jSONObject.getJSONObject("iconfont") : null, jSONObject.containsKey("visible") ? jSONObject.getBooleanValue("visible") : true);
    }

    public void setTabBarStyle(JSONObject jSONObject) throws NumberFormatException {
        if (jSONObject.containsKey("color")) {
            this.mTextColor = jSONObject.getString("color");
        }
        if (jSONObject.containsKey("selectedColor")) {
            this.mSelectedColor = jSONObject.getString("selectedColor");
        }
        if (jSONObject.containsKey("backgroundColor")) {
            this.mBackgroundColor = jSONObject.getString("backgroundColor");
        }
        if (jSONObject.containsKey(Constants.Name.BACKGROUND_IMAGE)) {
            this.mBackgroundImage = jSONObject.getString(Constants.Name.BACKGROUND_IMAGE);
        }
        if (jSONObject.containsKey(Constants.Name.BORDER_STYLE)) {
            this.mBorderStyle = jSONObject.getString(Constants.Name.BORDER_STYLE);
        }
        if (jSONObject.containsKey("height")) {
            String string = jSONObject.getString("height");
            this.mTabHeightStr = string;
            this.mTabHeight = (int) PdrUtil.parseFloat(string, 0.0f, 0.0f, this.mScale);
        }
        if (jSONObject.containsKey("midButton")) {
            this.mMidButton = jSONObject.getJSONObject("midButton");
        }
        if (jSONObject.containsKey(Constants.Name.FONT_SIZE)) {
            this.mTextSize = jSONObject.getString(Constants.Name.FONT_SIZE);
        }
        if (jSONObject.containsKey(AbsoluteConst.JSON_KEY_ICON_WIDTH)) {
            this.mImageSize = jSONObject.getString(AbsoluteConst.JSON_KEY_ICON_WIDTH);
        }
        if (jSONObject.containsKey("backgroundRepeat")) {
            this.repeatType = jSONObject.getString("backgroundRepeat");
        }
        initTabStyle();
        setTabItemStyle();
        setSelectedStyle();
        if (jSONObject.containsKey("redDotColor")) {
            String string2 = jSONObject.getString("redDotColor");
            if (PdrUtil.isEmpty(string2)) {
                return;
            }
            if (this.redDotColor != PdrUtil.stringToColor(string2)) {
                this.redDotColor = PdrUtil.stringToColor(string2);
                for (int i = 0; i < this.mTabItemViews.size(); i++) {
                    RelativeLayout relativeLayout = this.mTabItemViews.get(i);
                    if (relativeLayout != null) {
                        ((GradientDrawable) ((ImageView) relativeLayout.findViewById(R.id.itemDot)).getDrawable()).setColor(this.redDotColor);
                    }
                }
            }
        }
    }

    public void showTabBarRedDot(JSONObject jSONObject) {
        ViewGroup commonItemByIndex = getCommonItemByIndex(jSONObject.getInteger("index").intValue());
        if (commonItemByIndex != null) {
            ImageView imageView = (ImageView) commonItemByIndex.findViewById(R.id.itemDot);
            ((TextView) commonItemByIndex.findViewById(R.id.itemBadge)).setVisibility(4);
            setDotBadgeMarginTop(commonItemByIndex, imageView);
            imageView.setVisibility(0);
        }
    }

    public void switchTab(int i) throws NumberFormatException {
        this.mCommonSelectedIndex = String.valueOf(i);
        setSelectedStyle();
        DCBlurDraweeView dCBlurDraweeView = this.mBlurDraweeView;
        if (dCBlurDraweeView != null) {
            dCBlurDraweeView.postInvalidate(1000L);
        }
    }

    public void updateIconfont(TextView textView, JSONObject jSONObject, float f, boolean z) {
        if (this.mWebApp == null || textView == null || jSONObject == null || TextUtils.isEmpty(this.mIconfontPath)) {
            return;
        }
        textView.setVisibility(0);
        byte bObtainRunningAppMode = this.mWebApp.obtainRunningAppMode();
        String string = jSONObject.getString("text");
        if (jSONObject.containsKey(Constants.Name.FONT_SIZE)) {
            textView.setTextSize(0, (int) PdrUtil.parseFloat(jSONObject.getString(Constants.Name.FONT_SIZE), 0.0f, 0.0f, this.mScale));
        }
        if (z && jSONObject.containsKey("selectedColor")) {
            textView.setTextColor(PdrUtil.stringToColor(jSONObject.getString("selectedColor")));
        } else if (jSONObject.containsKey("color")) {
            textView.setTextColor(PdrUtil.stringToColor(jSONObject.getString("color")));
        }
        if (this.mIconfontPath.startsWith("/storage") || bObtainRunningAppMode != 1) {
            File file = new File(this.mIconfontPath);
            if (file.exists()) {
                textView.setTypeface(Typeface.createFromFile(file));
            }
        } else {
            try {
                textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), this.mIconfontPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        int i = (int) f;
        layoutParams.height = i;
        layoutParams.width = i;
        textView.setLayoutParams(layoutParams);
        textView.setText(string);
        textView.setVisibility(0);
    }

    public void updateMidButton(JSONObject jSONObject) {
        if (jSONObject != null) {
            this.mMidButton = jSONObject;
        }
        updateMidItemStyle();
    }
}

package com.taobao.weex.ui.component;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.fastjson.JSONObject;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.dom.CSSConstants;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.action.GraphicSize;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.view.WXEditText;
import com.taobao.weex.utils.FontDO;
import com.taobao.weex.utils.TypefaceUtil;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.core.ui.DCKeyboardManager;
import io.dcloud.common.core.ui.keyboard.DCEditText;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.common.UniConstants;
import io.dcloud.feature.uniapp.dom.AbsStyle;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class DCWXInput extends WXComponent<WXEditText> {
    private static final int MAX_TEXT_FORMAT_REPEAT = 3;
    final String ADJUST_POSITION;
    final String PASSWORD;
    private int cursor;
    boolean isConfirmHold;
    private AtomicBoolean isLayoutFinished;
    public boolean isNeedConfirm;
    private boolean isPassword;
    float keyboardHeight;
    private String mBeforeText;
    private int mEditorAction;
    private List<TextView.OnEditorActionListener> mEditorActionListeners;
    private String mFontFamily;
    private int mFormatRepeatCount;
    private TextFormatter mFormatter;
    private WXSDKInstance.FrameViewEventListener mFrameViewEventListener;
    private boolean mIgnoreNextOnInputEvent;
    private final InputMethodManager mInputMethodManager;
    private int mLineHeight;
    private boolean mListeningConfirm;
    private WXComponent.OnFocusChangeListener mOnFocusChangeListener;
    private TextPaint mPaint;
    private String mReturnKeyType;
    private TextWatcher mTextChangedEventDispatcher;
    private List<TextWatcher> mTextChangedListeners;
    protected String mType;
    private BroadcastReceiver mTypefaceObserver;
    private float measureHeight;
    private float measureWidht;
    private JSONObject placeholderStyle;
    private String placeholderTextAlign;
    private int selectionEnd;
    private int selectionStart;
    private String textAlign;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    /* renamed from: com.taobao.weex.ui.component.DCWXInput$6, reason: invalid class name */
    class AnonymousClass6 implements WXComponent.OnFocusChangeListener {
        int count = 0;

        AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fireEventForFocus(final TextView textView) {
            DCWXInput.this.getHostView().postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXInput.6.1
                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                    if (DCWXInput.this.keyboardHeight != 0.0f) {
                        anonymousClass6.count = 0;
                        HashMap map = new HashMap(1);
                        HashMap map2 = new HashMap(1);
                        map2.put("value", textView.getText().toString());
                        map2.put("height", Float.valueOf(DCWXInput.this.keyboardHeight));
                        map.put("detail", map2);
                        DCWXInput.this.fireEvent(Constants.Event.FOCUS, map);
                        return;
                    }
                    int i = anonymousClass6.count + 1;
                    anonymousClass6.count = i;
                    if (i <= 3) {
                        anonymousClass6.fireEventForFocus(textView);
                        return;
                    }
                    HashMap map3 = new HashMap(1);
                    HashMap map4 = new HashMap(1);
                    map4.put("value", textView.getText().toString());
                    map4.put("height", Float.valueOf(DCWXInput.this.keyboardHeight));
                    map3.put("detail", map4);
                    DCWXInput.this.fireEvent(Constants.Event.FOCUS, map3);
                }
            }, 200L);
        }

        @Override // com.taobao.weex.ui.component.WXComponent.OnFocusChangeListener
        public void onFocusChange(boolean z) {
            WXEditText hostView = DCWXInput.this.getHostView();
            if (hostView == null) {
                return;
            }
            HashMap map = new HashMap(1);
            HashMap map2 = new HashMap(1);
            map2.put("value", hostView.getText().toString());
            if (!z) {
                map.put("detail", map2);
                DCWXInput.this.fireEvent(Constants.Event.BLUR, map);
                return;
            }
            float f = DCWXInput.this.keyboardHeight;
            if (f == 0.0f) {
                fireEventForFocus(hostView);
                return;
            }
            map2.put("height", Float.valueOf(f));
            map2.put("value", hostView.getText().toString());
            map.put("detail", map2);
            DCWXInput.this.fireEvent(Constants.Event.FOCUS, map);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class PatternWrapper {
        private boolean global;
        private Pattern matcher;
        private String replace;

        private PatternWrapper() {
            this.global = false;
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private interface ReturnTypes {
        public static final String DEFAULT = "default";
        public static final String DONE = "done";
        public static final String GO = "go";
        public static final String NEXT = "next";
        public static final String SEARCH = "search";
        public static final String SEND = "send";
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class TextFormatter {
        private PatternWrapper format;
        private PatternWrapper recover;

        String format(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                PatternWrapper patternWrapper = this.format;
                return patternWrapper != null ? patternWrapper.global ? this.format.matcher.matcher(str).replaceAll(this.format.replace) : this.format.matcher.matcher(str).replaceFirst(this.format.replace) : str;
            } catch (Throwable th) {
                WXLogUtils.w("WXInput", "[format] " + th.getMessage());
                return str;
            }
        }

        String recover(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                PatternWrapper patternWrapper = this.recover;
                return patternWrapper != null ? patternWrapper.global ? this.recover.matcher.matcher(str).replaceAll(this.recover.replace) : this.recover.matcher.matcher(str).replaceFirst(this.recover.replace) : str;
            } catch (Throwable th) {
                WXLogUtils.w("WXInput", "[formatted] " + th.getMessage());
                return str;
            }
        }

        private TextFormatter(PatternWrapper patternWrapper, PatternWrapper patternWrapper2) {
            this.format = patternWrapper;
            this.recover = patternWrapper2;
        }
    }

    public DCWXInput(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
        this.mBeforeText = "";
        this.mType = "text";
        this.isPassword = false;
        this.mEditorAction = 6;
        this.mReturnKeyType = null;
        this.mListeningConfirm = false;
        this.mIgnoreNextOnInputEvent = false;
        this.mFormatter = null;
        this.mFormatRepeatCount = 0;
        this.mPaint = new TextPaint();
        this.mLineHeight = -1;
        this.ADJUST_POSITION = "adjustPosition";
        this.PASSWORD = Constants.Value.PASSWORD;
        this.keyboardHeight = 0.0f;
        this.isConfirmHold = false;
        this.isNeedConfirm = true;
        this.cursor = -1;
        this.selectionStart = Integer.MAX_VALUE;
        this.selectionEnd = Integer.MAX_VALUE;
        this.textAlign = "left";
        this.placeholderStyle = new JSONObject();
        this.measureHeight = -1.0f;
        this.measureWidht = -1.0f;
        this.mOnFocusChangeListener = new AnonymousClass6();
        this.isLayoutFinished = new AtomicBoolean(false);
        interceptFocusAndBlurEvent();
        this.mInputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        setContentBoxMeasurement(new ContentBoxMeasurement() { // from class: com.taobao.weex.ui.component.DCWXInput.1
            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutAfter(float f, float f2) {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutBefore() {
                DCWXInput.this.updateStyleAndAttrs();
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void measureInternal(float f, float f2, int i, int i2) {
                if (DCWXInput.this.getBasicComponentData().getStyles() == null || DCWXInput.this.getBasicComponentData().getStyles().size() == 0) {
                    DCWXInput.this.measureWidht = f;
                }
                if (CSSConstants.isUndefined(f2)) {
                    float realPxByWidth = WXViewUtils.getRealPxByWidth(DCWXInput.this.getInstance().getDefaultFontSize() * 1.4f, DCWXInput.this.getInstance().getInstanceViewPortWidthWithFloat());
                    this.mMeasureHeight = realPxByWidth;
                    DCWXInput.this.measureHeight = realPxByWidth;
                }
            }
        });
    }

    private final void addEditorActionListener(TextView.OnEditorActionListener onEditorActionListener) {
        WXEditText hostView;
        if (onEditorActionListener == null || (hostView = getHostView()) == null) {
            return;
        }
        if (this.mEditorActionListeners == null) {
            this.mEditorActionListeners = new ArrayList();
            hostView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.taobao.weex.ui.component.DCWXInput.13
                private boolean handled = true;

                @Override // android.widget.TextView.OnEditorActionListener
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    for (TextView.OnEditorActionListener onEditorActionListener2 : DCWXInput.this.mEditorActionListeners) {
                        if (onEditorActionListener2 != null) {
                            this.handled = onEditorActionListener2.onEditorAction(textView, i, keyEvent) & this.handled;
                        }
                    }
                    return this.handled;
                }
            });
        }
        this.mEditorActionListeners.add(onEditorActionListener);
    }

    private void addEditorChangeListener() {
        addEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.taobao.weex.ui.component.DCWXInput.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!DCWXInput.this.mListeningConfirm || i != DCWXInput.this.mEditorAction) {
                    return DCWXInput.this.isConfirmHold;
                }
                HashMap map = new HashMap(1);
                HashMap map2 = new HashMap(1);
                map2.put("value", textView.getText().toString());
                map.put("detail", map2);
                DCWXInput.this.fireEvent("confirm", map);
                DCWXInput dCWXInput = DCWXInput.this;
                if (!dCWXInput.isConfirmHold) {
                    dCWXInput.blur();
                }
                return true;
            }
        });
    }

    private void addKeyboardListener(final WXEditText wXEditText) {
        if (wXEditText != null && (wXEditText.getContext() instanceof Activity)) {
            getHostView().setkeyBoardHeightChangeListener(new DCEditText.OnKeyboardHeightChangeListener() { // from class: com.taobao.weex.ui.component.DCWXInput.14
                @Override // io.dcloud.common.core.ui.keyboard.DCEditText.OnKeyboardHeightChangeListener
                public void onChange(boolean z, int i) {
                    if (DCWXInput.this.getInstance() == null || DCWXInput.this.getInstance().isDestroy() || wXEditText == null) {
                        return;
                    }
                    DCWXInput.this.keyboardHeight = (int) WXViewUtils.getWebPxByWidth(i, r0.getInstance().getInstanceViewPortWidthWithFloat());
                    HashMap map = new HashMap(2);
                    map.put("height", Float.valueOf(DCWXInput.this.keyboardHeight));
                    map.put("duration", 0);
                    HashMap map2 = new HashMap(1);
                    map2.put("detail", map);
                    DCWXInput.this.fireEvent("keyboardheightchange", map2);
                    if (wXEditText.isFocused()) {
                        String str = DCWXInput.this.isPassword ? Constants.Value.PASSWORD : DCWXInput.this.mType;
                        if (!z && !DCWXInput.this.isConfirmHold && !str.equalsIgnoreCase(Constants.Value.PASSWORD) && !DCKeyboardManager.getInstance().getFrontInputType().equals(Constants.Value.PASSWORD)) {
                            DCWXInput.this.blur();
                        }
                        if (z) {
                            DCKeyboardManager.getInstance().setFrontInputType(str);
                        }
                    }
                    if (z) {
                        DCWXInput dCWXInput = DCWXInput.this;
                        dCWXInput.keyboardHeight = dCWXInput.keyboardHeight;
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decideSoftKeyboard() {
        WXEditText hostView = getHostView();
        if (hostView != null) {
            final Context context = getContext();
            if (context instanceof Activity) {
                hostView.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXInput.12
                    @Override // java.lang.Runnable
                    public void run() {
                        View currentFocus = ((Activity) context).getCurrentFocus();
                        if (currentFocus == null || (currentFocus instanceof EditText) || currentFocus.isFocused()) {
                            return;
                        }
                        DCWXInput.this.mInputMethodManager.hideSoftInputFromWindow(DCWXInput.this.getHostView().getWindowToken(), 0);
                    }
                }), 16L);
            }
        }
    }

    private int getInputType(String str) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "number":
                return 2;
            case "tel":
                return 3;
            case "url":
                return 17;
            case "date":
                getHostView().setFocusable(false);
                return 0;
            case "time":
                if (getHostView() != null) {
                    getHostView().setFocusable(false);
                }
                return 0;
            case "digit":
                return 8194;
            case "email":
                return 33;
            case "password":
                if (getHostView() != null) {
                    getHostView().setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return 129;
            case "datetime":
                return 4;
            default:
                return 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTextAlign(String str) {
        int i = isLayoutRTL() ? GravityCompat.END : GravityCompat.START;
        if (!TextUtils.isEmpty(str)) {
            str.getClass();
            str.hashCode();
            switch (str) {
                case "center":
                    return 17;
                case "left":
                    return GravityCompat.START;
                case "right":
                    return GravityCompat.END;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hostViewFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setCursorVisible(true);
        editText.requestFocus();
        showSoftKeyboard();
    }

    private PatternWrapper parseToPattern(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Pattern patternCompile;
        if (str == null || str2 == null) {
            return null;
        }
        if (!Pattern.compile("/[\\S]+/[i]?[m]?[g]?").matcher(str).matches()) {
            WXLogUtils.w("WXInput", "Illegal js pattern syntax: " + str);
            return null;
        }
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        String strSubstring2 = str.substring(str.indexOf("/") + 1, str.lastIndexOf("/"));
        int i = strSubstring.contains(ContextChain.TAG_INFRA) ? 2 : 0;
        if (strSubstring.contains(WXComponent.PROP_FS_MATCH_PARENT)) {
            i |= 32;
        }
        boolean zContains = strSubstring.contains("g");
        try {
            patternCompile = Pattern.compile(strSubstring2, i);
        } catch (PatternSyntaxException unused) {
            WXLogUtils.w("WXInput", "Pattern syntax error: " + strSubstring2);
            patternCompile = null;
        }
        if (patternCompile == null) {
            return null;
        }
        PatternWrapper patternWrapper = new PatternWrapper();
        patternWrapper.global = zContains;
        patternWrapper.matcher = patternCompile;
        patternWrapper.replace = str2;
        return patternWrapper;
    }

    private void registerTypefaceObserver(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (WXEnvironment.getApplication() == null) {
            WXLogUtils.w("WXText", "ApplicationContent is null on register typeface observer");
            return;
        }
        this.mFontFamily = str;
        if (this.mTypefaceObserver != null) {
            return;
        }
        this.mTypefaceObserver = new BroadcastReceiver() { // from class: com.taobao.weex.ui.component.DCWXInput.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                FontDO fontDO;
                String stringExtra = intent.getStringExtra(Constants.Name.FONT_FAMILY);
                if (!DCWXInput.this.mFontFamily.equals(stringExtra) || (fontDO = TypefaceUtil.getFontDO(stringExtra)) == null || fontDO.getTypeface() == null || DCWXInput.this.getHostView() == null) {
                    return;
                }
                DCWXInput.this.getHostView().setTypeface(fontDO.getTypeface());
            }
        };
        LocalBroadcastManager.getInstance(WXEnvironment.getApplication()).registerReceiver(this.mTypefaceObserver, new IntentFilter(TypefaceUtil.ACTION_TYPE_FACE_AVAILABLE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSelectionRange(int i, int i2) {
        WXEditText hostView;
        if (i2 == Integer.MAX_VALUE || i == Integer.MAX_VALUE || (hostView = getHostView()) == null) {
            return;
        }
        int length = getHostView().length();
        if (i > i2) {
            return;
        }
        if (i < 0) {
            i = 0;
        }
        if (i2 > length) {
            i2 = length;
        }
        hostView.setSelection(i, i2 >= 0 ? i2 : 0);
    }

    private void showSoftKeyboard() {
        if (getHostView() == null) {
            return;
        }
        getHostView().postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXInput.10
            @Override // java.lang.Runnable
            public void run() {
                DCWXInput.this.mInputMethodManager.showSoftInput(DCWXInput.this.getHostView(), 1);
            }
        }), 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStyleAndAttrs() {
        if (getStyles().size() > 0) {
            int fontSize = getStyles().containsKey(Constants.Name.FONT_SIZE) ? AbsStyle.getFontSize(getStyles(), getInstance().getDefaultFontSize(), getViewPortWidth()) : -1;
            String fontFamily = getStyles().containsKey(Constants.Name.FONT_FAMILY) ? AbsStyle.getFontFamily(getStyles()) : null;
            int fontStyle = getStyles().containsKey(Constants.Name.FONT_STYLE) ? AbsStyle.getFontStyle(getStyles()) : -1;
            int fontWeight = getStyles().containsKey(Constants.Name.FONT_WEIGHT) ? AbsStyle.getFontWeight(getStyles()) : -1;
            int lineHeight = AbsStyle.getLineHeight(getStyles(), getViewPortWidth());
            if (lineHeight != -1) {
                this.mLineHeight = lineHeight;
            }
            if (fontSize != -1) {
                this.mPaint.setTextSize(fontSize);
            }
            if (fontFamily != null) {
                TypefaceUtil.applyFontStyle(this.mPaint, fontStyle, fontWeight, fontFamily);
            }
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void addEvent(String str) {
        if (getHostView() == null || TextUtils.isEmpty(str)) {
            return;
        }
        if (str.equals("input")) {
            addTextChangedListener(new TextWatcher() { // from class: com.taobao.weex.ui.component.DCWXInput.5
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    if (DCWXInput.this.mIgnoreNextOnInputEvent) {
                        DCWXInput.this.mIgnoreNextOnInputEvent = false;
                        DCWXInput.this.mBeforeText = charSequence.toString();
                        return;
                    }
                    HashMap map = new HashMap(1);
                    HashMap map2 = new HashMap(3);
                    map2.put("value", charSequence.toString());
                    map2.put("cursor", Integer.valueOf(DCWXInput.this.getHostView().getSelectionEnd()));
                    try {
                    } catch (Exception unused) {
                        map2.put("keyCode", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
                    }
                    if (i2 != 0 || i3 == 0) {
                        if (i2 != 0 && i3 == 0) {
                            String string = DCWXInput.this.mBeforeText.subSequence(i, i2 + i).toString();
                            map2.put("keyCode", Integer.valueOf(Character.codePointAt(string, string.length() - 1)));
                        }
                        DCWXInput.this.mBeforeText = charSequence.toString();
                        map.put("detail", map2);
                        DCWXInput.this.fireEvent("input", map);
                    }
                    String string2 = charSequence.subSequence(i, i3 + i).toString();
                    map2.put("keyCode", Integer.valueOf(Character.codePointAt(string2, string2.length() - 1)));
                    DCWXInput.this.mBeforeText = charSequence.toString();
                    map.put("detail", map2);
                    DCWXInput.this.fireEvent("input", map);
                }
            });
        }
        if ("confirm".equals(str)) {
            this.mListeningConfirm = true;
        }
        if (Constants.Event.FOCUS.equals(str) || Constants.Event.BLUR.equals(str)) {
            setFocusAndBlur();
        }
        super.addEvent(str);
    }

    void addTextChangedListener(TextWatcher textWatcher) {
        if (this.mTextChangedListeners == null) {
            this.mTextChangedListeners = new ArrayList();
        }
        this.mTextChangedListeners.add(textWatcher);
    }

    protected void appleStyleAfterCreated(final WXEditText wXEditText) {
        int textAlign = getTextAlign((String) getStyles().get(Constants.Name.TEXT_ALIGN));
        if (textAlign <= 0) {
            textAlign = GravityCompat.START;
        }
        wXEditText.setGravity(textAlign | getVerticalGravity());
        int color = WXResourceUtils.getColor("#999999");
        if (color != Integer.MIN_VALUE) {
            wXEditText.setHintTextColor(color);
        }
        TextWatcher textWatcher = new TextWatcher() { // from class: com.taobao.weex.ui.component.DCWXInput.4
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (DCWXInput.this.mTextChangedListeners != null) {
                    Iterator it = DCWXInput.this.mTextChangedListeners.iterator();
                    while (it.hasNext()) {
                        ((TextWatcher) it.next()).afterTextChanged(editable);
                    }
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (DCWXInput.this.mTextChangedListeners != null) {
                    Iterator it = DCWXInput.this.mTextChangedListeners.iterator();
                    while (it.hasNext()) {
                        ((TextWatcher) it.next()).beforeTextChanged(charSequence, i, i2, i3);
                    }
                }
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (DCWXInput.this.mFormatter != null) {
                    String str = DCWXInput.this.mFormatter.format(DCWXInput.this.mFormatter.recover(charSequence.toString()));
                    if (!str.equals(charSequence.toString()) && DCWXInput.this.mFormatRepeatCount < 3) {
                        DCWXInput.this.mFormatRepeatCount++;
                        int length = DCWXInput.this.mFormatter.format(DCWXInput.this.mFormatter.recover(charSequence.subSequence(0, wXEditText.getSelectionStart()).toString())).length();
                        wXEditText.setText(str);
                        wXEditText.setSelection(length);
                        return;
                    }
                    DCWXInput.this.mFormatRepeatCount = 0;
                }
                if (DCWXInput.this.mTextChangedListeners != null) {
                    Iterator it = DCWXInput.this.mTextChangedListeners.iterator();
                    while (it.hasNext()) {
                        ((TextWatcher) it.next()).onTextChanged(charSequence, i, i2, i3);
                    }
                }
            }
        };
        this.mTextChangedEventDispatcher = textWatcher;
        wXEditText.addTextChangedListener(textWatcher);
        wXEditText.setTextSize(0, AbsStyle.getFontSize(getStyles(), getInstance().getDefaultFontSize(), getInstance().getInstanceViewPortWidthWithFloat()));
        wXEditText.setSingleLine(true);
    }

    @JSMethod
    public void blur() {
        WXEditText hostView = getHostView();
        if (hostView == null || !hostView.hasFocus()) {
            return;
        }
        if (getParent() != null) {
            getParent().interceptFocus();
        }
        hostView.clearFocus();
        hideSoftKeyboard();
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected Object convertEmptyProperty(String str, Object obj) {
        str.getClass();
        return !str.equals("color") ? !str.equals(Constants.Name.FONT_SIZE) ? super.convertEmptyProperty(str, obj) : Integer.valueOf(getInstance().getDefaultFontSize()) : "black";
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.destroy();
        List<TextView.OnEditorActionListener> list = this.mEditorActionListeners;
        if (list != null) {
            list.clear();
        }
        List<TextWatcher> list2 = this.mTextChangedListeners;
        if (list2 != null) {
            list2.clear();
        }
        if (this.mTextChangedEventDispatcher != null) {
            this.mTextChangedEventDispatcher = null;
        }
        if (getHostView() != null) {
            getHostView().destroy();
        }
    }

    @JSMethod
    public void focus() {
        WXEditText hostView = getHostView();
        if (hostView == null || hostView.hasFocus()) {
            return;
        }
        if (getParent() != null) {
            getParent().ignoreFocus();
        }
        hostView.requestFocus();
        hostView.setFocusable(true);
        hostView.setFocusableInTouchMode(true);
        showSoftKeyboard();
    }

    @UniJSMethod
    public void getCursor(JSCallback jSCallback) {
        HashMap map = new HashMap(1);
        if (getHostView() == null || !getHostView().isFocused()) {
            map.put("cursor", 0);
        } else {
            map.put("cursor", Integer.valueOf(getHostView().getSelectionEnd()));
        }
        jSCallback.invoke(map);
    }

    protected float getMeasureHeight() {
        return WXViewUtils.getRealPxByWidth(getMeasuredLineHeight() >= 50.0f ? getMeasureHeight() : 50.0f, getInstance().getInstanceViewPortWidthWithFloat());
    }

    final float getMeasuredLineHeight() {
        int i = this.mLineHeight;
        return (i == -1 || i <= 0) ? this.mPaint.getFontMetrics(null) : i;
    }

    @UniJSMethod
    public void getSelectionRange(String str) {
        HashMap map = new HashMap(2);
        WXEditText hostView = getHostView();
        if (hostView != null) {
            int selectionStart = hostView.getSelectionStart();
            int selectionEnd = hostView.getSelectionEnd();
            if (!hostView.hasFocus()) {
                selectionStart = 0;
                selectionEnd = 0;
            }
            map.put(Constants.Name.SELECTION_START, Integer.valueOf(selectionStart));
            map.put(Constants.Name.SELECTION_END, Integer.valueOf(selectionEnd));
        }
        WXBridgeManager.getInstance().callback(getInstanceId(), str, map, false);
    }

    public String getValue() {
        return getHostView().getText().toString();
    }

    protected int getVerticalGravity() {
        return 16;
    }

    void hideSoftKeyboard() {
        if (getHostView() != null) {
            getHostView().postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXInput.11
                @Override // java.lang.Runnable
                public void run() {
                    DCWXInput.this.mInputMethodManager.hideSoftInputFromWindow(DCWXInput.this.getHostView().getWindowToken(), 0);
                }
            }), 16L);
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean isConsumeTouch() {
        return !isDisabled();
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected void layoutDirectionDidChanged(boolean z) {
        int textAlign = getTextAlign((String) getStyles().get(Constants.Name.TEXT_ALIGN));
        if (textAlign <= 0) {
            textAlign = GravityCompat.START;
        }
        if (getHostView() != null) {
            getHostView().setGravity(textAlign | getVerticalGravity());
        }
    }

    @WXComponentProp(name = "adjustPosition")
    public void setAdjustPosition(Object obj) {
        if (getHostView() != null) {
            getHostView().setInputSoftMode(WXUtils.getBoolean(obj, Boolean.TRUE).booleanValue() ? DCKeyboardManager.SOFT_INPUT_MODE_ADJUST_PAN : DCKeyboardManager.SOFT_INPUT_MODE_ADJUST_NOTHING);
        }
    }

    @WXComponentProp(name = Constants.Name.AUTOFOCUS)
    public void setAutofocus(final boolean z) {
        if (getHostView() == null) {
            return;
        }
        final WXEditText hostView = getHostView();
        if (this.isLayoutFinished.get()) {
            if (z) {
                hostViewFocus(hostView);
            } else {
                hideSoftKeyboard();
            }
            hostView.clearFocus();
            return;
        }
        if (getInstance().isFrameViewShow()) {
            getHostView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.taobao.weex.ui.component.DCWXInput.8
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (DCWXInput.this.getInstance() == null) {
                        return;
                    }
                    if (z) {
                        DCWXInput.this.isLayoutFinished.set(true);
                        DCWXInput.this.hostViewFocus(hostView);
                        DCWXInput dCWXInput = DCWXInput.this;
                        dCWXInput.setSelectionRange(dCWXInput.selectionStart, DCWXInput.this.selectionEnd);
                    } else {
                        hostView.clearFocus();
                    }
                    DCWXInput.this.getHostView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
            return;
        }
        if (this.mFrameViewEventListener != null) {
            getInstance().removeFrameViewEventListener(this.mFrameViewEventListener);
        }
        this.mFrameViewEventListener = new WXSDKInstance.FrameViewEventListener() { // from class: com.taobao.weex.ui.component.DCWXInput.9
            @Override // com.taobao.weex.WXSDKInstance.FrameViewEventListener
            public void onShowAnimationEnd() {
                if (DCWXInput.this.getInstance() == null) {
                    return;
                }
                if (z) {
                    DCWXInput.this.isLayoutFinished.set(true);
                    DCWXInput.this.hostViewFocus(hostView);
                    DCWXInput dCWXInput = DCWXInput.this;
                    dCWXInput.setSelectionRange(dCWXInput.selectionStart, DCWXInput.this.selectionEnd);
                } else {
                    hostView.clearFocus();
                }
                DCWXInput.this.getInstance().removeFrameViewEventListener(this);
                DCWXInput.this.mFrameViewEventListener = null;
            }
        };
        getInstance().addFrameViewEventListener(this.mFrameViewEventListener);
    }

    @WXComponentProp(name = "color")
    public void setColor(String str) {
        int color;
        if (getHostView() == null || TextUtils.isEmpty(str) || (color = WXResourceUtils.getColor(str)) == Integer.MIN_VALUE) {
            return;
        }
        getHostView().setTextColor(color);
    }

    @WXComponentProp(name = UniConstants.Name.CURSOR_SPACING)
    public void setCursorSpacing(String str) {
        if (getHostView() != null) {
            getHostView().setCursorSpacing(WXViewUtils.getRealPxByWidth(WXUtils.getFloat(str), getInstance().getInstanceViewPortWidthWithFloat()));
        }
    }

    protected void setFocusAndBlur() {
        if (ismHasFocusChangeListener(this.mOnFocusChangeListener)) {
            return;
        }
        addFocusChangeListener(this.mOnFocusChangeListener);
    }

    @WXComponentProp(name = Constants.Name.FONT_SIZE)
    public void setFontSize(String str) {
        if (getHostView() == null || str == null) {
            return;
        }
        new HashMap(1).put(Constants.Name.FONT_SIZE, str);
        getHostView().setTextSize(0, AbsStyle.getFontSize(r0, getInstance().getDefaultFontSize(), getInstance().getInstanceViewPortWidthWithFloat()));
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    protected void setLayoutSize(GraphicSize graphicSize) {
        super.setLayoutSize(graphicSize);
    }

    @WXComponentProp(name = Constants.Name.LINES)
    public void setLines(int i) {
        if (getHostView() == null) {
            return;
        }
        getHostView().setLines(i);
    }

    @WXComponentProp(name = Constants.Name.MAX_LENGTH)
    public void setMaxLength(int i) {
        if (getHostView() == null) {
            return;
        }
        if (i == -1) {
            i = Integer.MAX_VALUE;
        }
        getHostView().setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
    }

    @WXComponentProp(name = Constants.Name.MAXLENGTH)
    @Deprecated
    public void setMaxlength(int i) {
        setMaxLength(i);
    }

    public void setPlaceholder(String str) {
        if (str == null || getHostView() == null) {
            return;
        }
        getHostView().setHint(str);
        setPlaceholderStyle(this.placeholderStyle);
    }

    @WXComponentProp(name = "placeholderClass")
    public void setPlaceholderClass(JSONObject jSONObject) {
        setPlaceholderStyle(jSONObject);
    }

    @WXComponentProp(name = Constants.Name.PLACEHOLDER_COLOR)
    public void setPlaceholderColor(String str) {
        int color;
        if (getHostView() == null || TextUtils.isEmpty(str) || (color = WXResourceUtils.getColor(str)) == Integer.MIN_VALUE) {
            return;
        }
        getHostView().setHintTextColor(color);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008b  */
    @com.taobao.weex.ui.component.WXComponentProp(name = "placeholderStyle")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setPlaceholderStyle(com.alibaba.fastjson.JSONObject r18) {
        /*
            Method dump skipped, instructions count: 658
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.component.DCWXInput.setPlaceholderStyle(com.alibaba.fastjson.JSONObject):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) throws Throwable {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "selectionStart":
                int iIntValue = WXUtils.getInteger(obj, Integer.MAX_VALUE).intValue();
                this.selectionStart = iIntValue;
                setSelectionRange(iIntValue, this.selectionEnd);
                break;
            case "selectionEnd":
                int iIntValue2 = WXUtils.getInteger(obj, Integer.MAX_VALUE).intValue();
                this.selectionEnd = iIntValue2;
                setSelectionRange(this.selectionStart, iIntValue2);
                break;
            case "placeholderColor":
                String string = WXUtils.getString(obj, null);
                if (string != null) {
                    setPlaceholderColor(string);
                    break;
                }
                break;
            case "cursor":
                int i = WXUtils.getInt(obj);
                if (i > 0 && i <= getHostView().getText().length()) {
                    getHostView().setSelection(i);
                    break;
                } else if (i > getHostView().getText().length()) {
                    getHostView().setSelection(getHostView().getText().length());
                    break;
                }
                break;
            case "fontFamily":
                if (obj != null) {
                    try {
                        FontDO fontDO = TypefaceUtil.getFontDO(obj.toString());
                        if (fontDO == null || fontDO.getTypeface() == null || getHostView() == null) {
                            registerTypefaceObserver(obj.toString());
                        } else {
                            getHostView().setTypeface(fontDO.getTypeface());
                        }
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
                break;
            case "textAlign":
                String string2 = WXUtils.getString(obj, null);
                if (string2 != null) {
                    setTextAlign(string2);
                    break;
                }
                break;
            case "maxLength":
                Integer integer = WXUtils.getInteger(obj, null);
                if (integer != null) {
                    setMaxLength(integer.intValue());
                    break;
                }
                break;
            case "color":
                String string3 = WXUtils.getString(obj, null);
                if (string3 != null) {
                    setColor(string3);
                    break;
                }
                break;
            case "focus":
            case "autoFocus":
                setAutofocus(WXUtils.getBoolean(obj, Boolean.FALSE).booleanValue());
                break;
            case "lines":
                Integer integer2 = WXUtils.getInteger(obj, null);
                if (integer2 != null) {
                    setLines(integer2.intValue());
                    break;
                }
                break;
            case "maxlength":
                Integer integer3 = WXUtils.getInteger(obj, null);
                if (integer3 != null) {
                    setMaxLength(integer3.intValue());
                    break;
                }
                break;
            case "disabled":
                Boolean bool = WXUtils.getBoolean(obj, Boolean.FALSE);
                getHostView().setFocusable(!bool.booleanValue());
                getHostView().setFocusableInTouchMode(!bool.booleanValue());
                getHostView().setCursorVisible(!bool.booleanValue());
                break;
            case "confirmHold":
                this.isConfirmHold = WXUtils.getBoolean(obj, Boolean.FALSE).booleanValue();
                break;
            case "fontSize":
                String string4 = WXUtils.getString(obj, null);
                if (string4 != null) {
                    setFontSize(string4);
                    break;
                }
                break;
            case "singleline":
                Boolean bool2 = WXUtils.getBoolean(obj, null);
                if (bool2 != null) {
                    setSingleLine(bool2.booleanValue());
                    break;
                }
                break;
            case "returnKeyType":
                setReturnKeyType(String.valueOf(obj));
                break;
            case "allowCopyPaste":
                boolean zBooleanValue = WXUtils.getBoolean(obj, Boolean.TRUE).booleanValue();
                if (getHostView() != null) {
                    getHostView().setAllowCopyPaste(zBooleanValue);
                    break;
                }
                break;
        }
        return true;
        return true;
    }

    @WXComponentProp(name = Constants.Name.RETURN_KEY_TYPE)
    public void setReturnKeyType(String str) {
        if (getHostView() == null || str.equals(this.mReturnKeyType)) {
            return;
        }
        this.mReturnKeyType = str;
        str.hashCode();
        switch (str) {
            case "search":
                this.mEditorAction = 3;
                break;
            case "go":
                this.mEditorAction = 2;
                break;
            case "done":
                this.mEditorAction = 6;
                break;
            case "next":
                this.mEditorAction = 5;
                break;
            case "send":
                this.mEditorAction = 4;
                break;
            case "default":
                this.mEditorAction = 0;
                break;
        }
        blur();
        getHostView().setImeOptions(this.mEditorAction);
    }

    @WXComponentProp(name = Constants.Name.SINGLELINE)
    public void setSingleLine(boolean z) {
        if (getHostView() == null) {
            return;
        }
        getHostView().setSingleLine(z);
    }

    @WXComponentProp(name = "value")
    public void setText(String str) {
        WXEditText hostView = getHostView();
        if (hostView == null || TextUtils.equals(hostView.getText(), str)) {
            return;
        }
        this.mIgnoreNextOnInputEvent = true;
        hostView.setText(str);
        int length = this.cursor;
        if (length <= 0) {
            length = str.length();
        }
        if (str == null) {
            length = 0;
        }
        hostView.setSelection(length);
    }

    @WXComponentProp(name = Constants.Name.TEXT_ALIGN)
    public void setTextAlign(String str) {
        this.textAlign = str;
        int textAlign = getTextAlign(str);
        if (textAlign > 0) {
            getHostView().setGravity(textAlign | getVerticalGravity());
        }
    }

    @JSMethod
    public void setTextFormatter(JSONObject jSONObject) {
        try {
            String string = jSONObject.getString("formatRule");
            String string2 = jSONObject.getString("formatReplace");
            String string3 = jSONObject.getString("recoverRule");
            String string4 = jSONObject.getString("recoverReplace");
            PatternWrapper toPattern = parseToPattern(string, string2);
            PatternWrapper toPattern2 = parseToPattern(string3, string4);
            if (toPattern == null || toPattern2 == null) {
                return;
            }
            this.mFormatter = new TextFormatter(toPattern, toPattern2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setType(String str) {
        Log.e("weex", "setType=" + str);
        if (str == null || getHostView() == null || this.mType.equals(str)) {
            return;
        }
        this.mType = str;
        getHostView().setInputType(getInputType(this.mType));
    }

    @JSMethod
    public void setValue(String str) {
        getHostView().setText(str);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void updateProperties(Map<String, Object> map) {
        String strValueOf;
        if (map != null && map.size() > 0) {
            setType(map.containsKey("type") ? String.valueOf(map.get("type")) : this.mType);
            String str = this.mType;
            if (str != null && str.equals("text") && map.containsKey("confirmType")) {
                if (map.containsKey("confirmType")) {
                    strValueOf = String.valueOf(map.get("confirmType"));
                } else {
                    strValueOf = this.mReturnKeyType;
                    if (strValueOf == null) {
                        strValueOf = "done";
                    }
                }
                setReturnKeyType(strValueOf);
            }
            WXAttr attrs = getAttrs();
            String str2 = Constants.Value.PASSWORD;
            if (attrs.containsKey(Constants.Value.PASSWORD)) {
                boolean zBooleanValue = WXUtils.getBoolean(getAttrs().get(Constants.Value.PASSWORD), Boolean.FALSE).booleanValue();
                this.isPassword = zBooleanValue;
                if (!zBooleanValue) {
                    str2 = this.mType;
                }
                if (str2 == null || getHostView() == null) {
                    return;
                } else {
                    getHostView().setInputType(getInputType(str2));
                }
            }
            if (map.containsKey("cursor")) {
                this.cursor = WXUtils.getInteger(map.get("cursor"), 0).intValue();
            }
            if (map.containsKey(Constants.Name.SELECTION_START)) {
                this.selectionStart = WXUtils.getInteger(map.get(Constants.Name.SELECTION_START), Integer.MAX_VALUE).intValue();
            }
            if (map.containsKey(Constants.Name.SELECTION_END)) {
                this.selectionEnd = WXUtils.getInteger(map.get(Constants.Name.SELECTION_END), Integer.MAX_VALUE).intValue();
            }
            if (map.containsKey(Constants.Name.PLACEHOLDER)) {
                setPlaceholder(WXUtils.getString(map.get(Constants.Name.PLACEHOLDER), ""));
            }
            if (map.containsKey("placeholderClass")) {
                this.placeholderStyle.putAll((JSONObject) JSONObject.parse(WXUtils.getString(map.get("placeholderClass"), "{}")));
            }
            if (map.containsKey("placeholderStyle")) {
                this.placeholderStyle.putAll((JSONObject) JSONObject.parse(WXUtils.getString(map.get("placeholderStyle"), "{}")));
            }
            if (map.containsKey("adjustPosition")) {
                setAdjustPosition(map.get("adjustPosition"));
            }
        }
        super.updateProperties(map);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public WXEditText initComponentHostView(Context context) {
        WXEditText wXEditText = new WXEditText(context, getInstanceId());
        appleStyleAfterCreated(wXEditText);
        wXEditText.setImeOptions(6);
        return wXEditText;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void onHostViewInitialized(WXEditText wXEditText) {
        super.onHostViewInitialized((DCWXInput) wXEditText);
        if (this.measureWidht > 0.0f) {
            WXBridgeManager.getInstance().setStyleWidth(getInstanceId(), getRef(), this.measureWidht);
        }
        if (this.measureHeight > 0.0f) {
            WXBridgeManager.getInstance().setStyleHeight(getInstanceId(), getRef(), this.measureHeight);
        }
        addFocusChangeListener(new WXComponent.OnFocusChangeListener() { // from class: com.taobao.weex.ui.component.DCWXInput.2
            @Override // com.taobao.weex.ui.component.WXComponent.OnFocusChangeListener
            public void onFocusChange(boolean z) {
                if (!z) {
                    DCWXInput.this.decideSoftKeyboard();
                }
                DCWXInput.this.setPseudoClassStatus(Constants.PSEUDO.FOCUS, z);
                if (z) {
                    DCWXInput dCWXInput = DCWXInput.this;
                    dCWXInput.setTextAlign(dCWXInput.textAlign);
                } else {
                    if (DCWXInput.this.getHostView() == null || !PdrUtil.isEmpty(DCWXInput.this.getHostView().getText().toString())) {
                        return;
                    }
                    DCWXInput dCWXInput2 = DCWXInput.this;
                    int textAlign = dCWXInput2.getTextAlign(dCWXInput2.placeholderTextAlign == null ? DCWXInput.this.textAlign : DCWXInput.this.placeholderTextAlign);
                    if (textAlign > 0) {
                        DCWXInput.this.getHostView().setGravity(textAlign | DCWXInput.this.getVerticalGravity());
                    }
                }
            }
        });
        addKeyboardListener(wXEditText);
        if (this.isNeedConfirm) {
            addEditorChangeListener();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void setHostLayoutParams(WXEditText wXEditText, int i, int i2, int i3, int i4, int i5, int i6) {
        super.setHostLayoutParams((DCWXInput) wXEditText, i, i2, i3, i4, i5, i6);
    }
}

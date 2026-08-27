package io.dcloud.feature.nativeObj;

import android.graphics.Color;
import android.text.TextUtils;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.INativeBitmap;
import io.dcloud.common.DHInterface.INativeView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.TitleNViewUtil;
import io.dcloud.feature.nativeObj.richtext.RichTextLayout;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NativeBitmapMgr {
    private HashMap<String, INativeBitmap> mSnaps = new HashMap<>();
    private HashMap<String, String> mIds = new HashMap<>();
    protected LinkedHashMap<String, NativeView> mNativeViews = new LinkedHashMap<>();
    public final String SUCCESS_INFO = "{path:'file://%s', w:%d, h:%d, size:%d}";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.feature.nativeObj.NativeBitmapMgr$7, reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action;

        static {
            int[] iArr = new int[Action.values().length];
            $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action = iArr;
            try {
                iArr[Action.View.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.setStyle.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.addEventListener.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.interceptTouchEvent.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.setTouchEventRect.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.getViewById.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.evalWeexJS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.drawRichText.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.drawBitmap.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.drawText.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.drawInput.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.getInputValueById.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.getInputFocusById.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.setInputFocusById.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.show.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.hide.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.view_close.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.view_animate.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.view_reset.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.view_restore.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.view_drawRect.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.isVisible.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.Bitmap.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.getItems.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.getBitmapById.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.clear.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.bitmapRecycle.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.load.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.loadBase64Data.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.save.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.toBase64Data.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.startAnimation.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.clearAnimation.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.view_clearRect.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.view_draw.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.setImages.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.addImages.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$io$dcloud$feature$nativeObj$NativeBitmapMgr$Action[Action.currentImageIndex.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    protected enum Action {
        Bitmap,
        getItems,
        getBitmapById,
        clear,
        load,
        loadBase64Data,
        save,
        toBase64Data,
        View,
        startAnimation,
        clearAnimation,
        getViewById,
        drawBitmap,
        drawText,
        evalWeexJS,
        drawRichText,
        show,
        hide,
        setImages,
        addImages,
        view_animate,
        view_reset,
        view_restore,
        view_drawRect,
        isVisible,
        addEventListener,
        interceptTouchEvent,
        setTouchEventRect,
        bitmapRecycle,
        setStyle,
        view_clearRect,
        view_draw,
        view_close,
        currentImageIndex,
        drawInput,
        getInputValueById,
        getInputFocusById,
        setInputFocusById
    }

    private void createBitmap(IApp iApp, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2)) {
            str2 = "" + System.currentTimeMillis();
        }
        if (this.mSnaps.containsKey(str)) {
            return;
        }
        this.mSnaps.put(str, new NativeBitmap(iApp, str2, str, str3));
        this.mIds.put(str2, str);
    }

    private NativeView getNativeView(String str, String str2) {
        NativeView nativeView = this.mNativeViews.get(str2);
        if (nativeView == null && !TextUtils.isEmpty(str)) {
            for (NativeView nativeView2 : this.mNativeViews.values()) {
                if (TextUtils.equals(nativeView2.mID, str)) {
                    return nativeView2;
                }
            }
        }
        return nativeView;
    }

    private void load(final IWebview iWebview, NativeBitmap nativeBitmap, String str, final String str2) {
        nativeBitmap.load(iWebview, iWebview.obtainFrameView().obtainMainView().getContext(), str, TextUtils.isEmpty(str2) ? null : new ICallBack() { // from class: io.dcloud.feature.nativeObj.NativeBitmapMgr.1
            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                Deprecated_JSUtil.execCallback(iWebview, str2, null, JSUtil.OK, false, false);
                return null;
            }
        }, TextUtils.isEmpty(str2) ? null : new ICallBack() { // from class: io.dcloud.feature.nativeObj.NativeBitmapMgr.2
            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                String string = obj == null ? iWebview.getContext().getString(R.string.dcloud_native_obj_load_failed) : obj.toString();
                Deprecated_JSUtil.execCallback(iWebview, str2, "{\"code\":-100,\"message\":\"" + string + "\"}", JSUtil.ERROR, true, false);
                return null;
            }
        });
    }

    private void loadBase64Data(final IWebview iWebview, NativeBitmap nativeBitmap, String str, final String str2) {
        nativeBitmap.loadBase64Data(str, TextUtils.isEmpty(str2) ? null : new ICallBack() { // from class: io.dcloud.feature.nativeObj.NativeBitmapMgr.3
            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                Deprecated_JSUtil.execCallback(iWebview, str2, null, JSUtil.OK, false, false);
                return null;
            }
        }, TextUtils.isEmpty(str2) ? null : new ICallBack() { // from class: io.dcloud.feature.nativeObj.NativeBitmapMgr.4
            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                Deprecated_JSUtil.execCallback(iWebview, str2, "{\"code\":-100,\"message\":\"" + iWebview.getContext().getString(R.string.dcloud_native_obj_load_failed) + "\"}", JSUtil.ERROR, true, false);
                return null;
            }
        });
    }

    private void save(final IWebview iWebview, NativeBitmap nativeBitmap, String str, JSONObject jSONObject, final String str2) {
        nativeBitmap.save(iWebview.obtainFrameView().obtainApp(), str, new NativeBitmapSaveOptions(jSONObject.toString()), iWebview.getScale(), TextUtils.isEmpty(str2) ? null : new ICallBack() { // from class: io.dcloud.feature.nativeObj.NativeBitmapMgr.5
            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                String str3;
                if (obj == null || !(obj instanceof NativeBitmapSaveOptions)) {
                    str3 = null;
                } else {
                    NativeBitmapSaveOptions nativeBitmapSaveOptions = (NativeBitmapSaveOptions) obj;
                    str3 = StringUtil.format("{path:'file://%s', w:%d, h:%d, size:%d}", nativeBitmapSaveOptions.path, Integer.valueOf(nativeBitmapSaveOptions.width), Integer.valueOf(nativeBitmapSaveOptions.height), Long.valueOf(nativeBitmapSaveOptions.size));
                }
                Deprecated_JSUtil.execCallback(iWebview, str2, str3, JSUtil.OK, true, false);
                return null;
            }
        }, TextUtils.isEmpty(str2) ? null : new ICallBack() { // from class: io.dcloud.feature.nativeObj.NativeBitmapMgr.6
            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                Deprecated_JSUtil.execCallback(iWebview, str2, "{\"code\":-100,\"message\":\"\"+webview.getContext().getString(R.string.dcloud_native_obj_load_failed)+\"\"}", JSUtil.ERROR, true, false);
                return null;
            }
        });
    }

    public void destroy() {
        try {
            Iterator<INativeBitmap> it = this.mSnaps.values().iterator();
            while (it.hasNext()) {
                try {
                    it.next().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.mIds.clear();
            this.mSnaps.clear();
            for (NativeView nativeView : this.mNativeViews.values()) {
                if (nativeView != null) {
                    nativeView.clearNativeViewData();
                }
            }
            this.mNativeViews.clear();
            NativeTypefaceFactory.clearCache();
            System.gc();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void destroyNativeView(NativeView nativeView) {
        NativeView nativeView2 = this.mNativeViews.get(nativeView.mUUID);
        nativeView.clearNativeViewData();
        if (nativeView2 == nativeView) {
            this.mNativeViews.remove(nativeView.mUUID);
        } else {
            this.mNativeViews.remove(nativeView.mID);
        }
    }

    public Object doForFeature(String str, Object obj) throws JSONException {
        NativeView nativeView;
        if ("addNativeView".equals(str)) {
            Object[] objArr = (Object[]) obj;
            IFrameView iFrameView = (IFrameView) objArr[0];
            String str2 = (String) objArr[1];
            NativeView nativeView2 = getNativeView(str2, str2);
            Logger.d("adadad", "addNativeView outter" + nativeView2);
            if (nativeView2 != null) {
                Logger.d("adadad", "addNativeView inner" + iFrameView);
                nativeView2.attachToViewGroup(iFrameView);
                return null;
            }
        } else if ("removeNativeView".equals(str)) {
            String str3 = (String) ((Object[]) obj)[1];
            NativeView nativeView3 = getNativeView(str3, str3);
            if (nativeView3 != null) {
                nativeView3.removeFromViewGroup();
                return null;
            }
        } else if ("getNativeView".equals(str)) {
            try {
                Object[] objArr2 = (Object[]) obj;
                String str4 = (String) objArr2[1];
                return getNativeView(str4, str4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if ("makeRichText".equals(str)) {
                return RichTextLayout.makeRichText((Object[]) obj);
            }
            if ("View".equals(str)) {
                try {
                    Object[] objArr3 = (Object[]) obj;
                    IWebview iWebview = (IWebview) objArr3[1];
                    String str5 = (String) objArr3[2];
                    String str6 = (String) objArr3[3];
                    JSONObject jSONObject = (JSONObject) objArr3[4];
                    if (jSONObject == null) {
                        jSONObject = new JSONObject();
                    }
                    JSONObject jSONObject2 = jSONObject;
                    JSONArray jSONArray = objArr3.length > 5 ? (JSONArray) objArr3[5] : null;
                    String str7 = AbsoluteConst.NATIVE_NVIEW;
                    if (objArr3.length > 6) {
                        str7 = (String) objArr3[6];
                    }
                    if (!this.mNativeViews.containsKey(str6)) {
                        NativeView nativeImageSlider = str7.equals(AbsoluteConst.NATIVE_IMAGESLIDER) ? new NativeImageSlider(iWebview.getContext(), iWebview, str6, str5, jSONObject2) : AbsoluteConst.NATIVE_TITLE_N_VIEW.equals(str7) ? new TitleNView(iWebview.getContext(), iWebview, str6, str5, jSONObject2) : new NativeView(iWebview.getContext(), iWebview, str6, str5, jSONObject2);
                        this.mNativeViews.put(nativeImageSlider.mUUID, nativeImageSlider);
                        initViewDrawItme(iWebview, nativeImageSlider, jSONArray);
                        return nativeImageSlider;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else if ("updateSubNViews".equals(str)) {
                Object[] objArr4 = (Object[]) obj;
                IWebview iWebview2 = (IWebview) objArr4[1];
                JSONArray jSONArray2 = (JSONArray) objArr4[2];
                AdaFrameView adaFrameView = (AdaFrameView) iWebview2.obtainFrameView();
                for (int i = 0; i < jSONArray2.length(); i++) {
                    try {
                        JSONObject jSONObject3 = jSONArray2.getJSONObject(i);
                        String strOptString = jSONObject3.optString("id");
                        if (!PdrUtil.isEmpty(strOptString)) {
                            ArrayList<INativeView> arrayList = adaFrameView.mChildNativeViewList;
                            int size = arrayList.size();
                            int i2 = 0;
                            while (true) {
                                if (i2 >= size) {
                                    nativeView = null;
                                    break;
                                }
                                INativeView iNativeView = arrayList.get(i2);
                                i2++;
                                INativeView iNativeView2 = iNativeView;
                                if (strOptString.equals(iNativeView2.getViewId())) {
                                    nativeView = (NativeView) iNativeView2;
                                    break;
                                }
                            }
                            if (nativeView != null) {
                                JSONArray jSONArrayOptJSONArray = jSONObject3.optJSONArray("tags");
                                nativeView.setStyle(jSONObject3.optJSONObject("styles"), jSONArrayOptJSONArray == null);
                                initViewDrawItme(iWebview2, nativeView, jSONArrayOptJSONArray);
                            }
                        }
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0307  */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r4v6, types: [io.dcloud.common.DHInterface.AbsMgr, io.dcloud.common.DHInterface.IMgr] */
    /* JADX WARN: Type inference failed for: r6v45, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String execute(io.dcloud.common.DHInterface.IWebview r17, java.lang.String r18, java.lang.String[] r19) {
        /*
            Method dump skipped, instructions count: 1692
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.nativeObj.NativeBitmapMgr.execute(io.dcloud.common.DHInterface.IWebview, java.lang.String, java.lang.String[]):java.lang.String");
    }

    public INativeBitmap getBitmapById(String str) {
        return getBitmapByUuid(this.mIds.get(str));
    }

    public INativeBitmap getBitmapByUuid(String str) {
        return this.mSnaps.get(str);
    }

    public JSONArray getItems() {
        JSONArray jSONArray = new JSONArray();
        Iterator<Map.Entry<String, INativeBitmap>> it = this.mSnaps.entrySet().iterator();
        while (it.hasNext()) {
            try {
                jSONArray.put(new JSONObject(((NativeBitmap) it.next().getValue()).toJsString()));
            } catch (Exception unused) {
            }
        }
        return jSONArray;
    }

    public NativeBitmap getSrcNativeBitmap(IWebview iWebview, IApp iApp, String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException unused) {
            jSONObject = null;
        }
        if (jSONObject != null) {
            return (NativeBitmap) getBitmapById(jSONObject.optString("id"));
        }
        if (!PdrUtil.isNetPath(str)) {
            str = (TextUtils.isEmpty(str) || str.equals("null")) ? null : iApp.convert2AbsFullPath(iWebview.obtainFullUrl(), str);
        }
        if (PdrUtil.isEmpty(str)) {
            return null;
        }
        String str2 = iApp.obtainAppId() + str.hashCode();
        return new NativeBitmap(iApp, str2, str2, str);
    }

    protected void initViewDrawItme(IWebview iWebview, NativeView nativeView, JSONArray jSONArray) throws JSONException {
        String str;
        int i;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        int iStringToColor;
        JSONObject jSONObject3;
        JSONObject jSONObject4;
        JSONObject jSONObject5;
        JSONObject jSONObject6;
        IWebview iWebview2 = iWebview;
        JSONArray jSONArray2 = jSONArray;
        String str2 = "color";
        if (jSONArray2 == null || nativeView == null) {
            return;
        }
        int i2 = 0;
        while (i2 < jSONArray2.length()) {
            try {
                JSONObject jSONObject7 = jSONArray2.getJSONObject(i2);
                String strOptString = jSONObject7.has("id") ? jSONObject7.optString("id") : null;
                String strOptString2 = jSONObject7.optString("tag");
                if (strOptString2.equals(WXBasicComponentType.IMG)) {
                    NativeBitmap srcNativeBitmap = getSrcNativeBitmap(iWebview2, iWebview2.obtainApp(), jSONObject7.optString("src"));
                    try {
                        jSONObject5 = new JSONObject(jSONObject7.optString("sprite"));
                    } catch (JSONException unused) {
                        jSONObject5 = new JSONObject();
                    }
                    try {
                        jSONObject6 = new JSONObject(jSONObject7.optString("position"));
                    } catch (JSONException unused2) {
                        jSONObject6 = new JSONObject();
                    }
                    i = i2;
                    str = str2;
                    nativeView.makeOverlay(iWebview2, srcNativeBitmap, null, -1, jSONObject5, jSONObject6, null, strOptString, WXBasicComponentType.IMG, false, true);
                } else {
                    str = str2;
                    i = i2;
                    if (strOptString2.equals(AbsURIAdapter.FONT)) {
                        String strOptString3 = jSONObject7.optString("text");
                        if (strOptString3 != null) {
                            try {
                                jSONObject3 = new JSONObject(jSONObject7.optString("position"));
                            } catch (JSONException unused3) {
                                jSONObject3 = new JSONObject();
                            }
                            JSONObject jSONObject8 = jSONObject3;
                            try {
                                jSONObject4 = new JSONObject(jSONObject7.optString("textStyles"));
                            } catch (JSONException unused4) {
                                jSONObject4 = new JSONObject();
                            }
                            nativeView.makeOverlay(iWebview, null, strOptString3, -1, null, jSONObject8, jSONObject4, strOptString, AbsURIAdapter.FONT, false, true);
                        }
                    } else if (strOptString2.equals("rect")) {
                        String strOptString4 = TitleNViewUtil.TRANSPARENT_BUTTON_TEXT_COLOR;
                        if (jSONObject7.has(str)) {
                            strOptString4 = jSONObject7.optString(str);
                        }
                        String strOptString5 = jSONObject7.optString("position");
                        JSONObject jSONObject9 = (TextUtils.isEmpty(strOptString5) || strOptString5.equals("null")) ? null : new JSONObject(strOptString5);
                        if (jSONObject7.has("rectStyles")) {
                            jSONObjectOptJSONObject = jSONObject7.optJSONObject("rectStyles");
                            if (jSONObjectOptJSONObject.has(str)) {
                                strOptString4 = jSONObjectOptJSONObject.optString(str);
                            }
                        }
                        JSONObject jSONObject10 = jSONObjectOptJSONObject;
                        try {
                            iStringToColor = Color.parseColor(strOptString4);
                        } catch (Exception unused5) {
                            iStringToColor = PdrUtil.stringToColor(strOptString4);
                        }
                        nativeView.makeOverlay(iWebview, null, null, iStringToColor, null, jSONObject9, jSONObject10, strOptString, "rect", false, true);
                    } else if (strOptString2.equals(WXBasicComponentType.RICHTEXT)) {
                        String strOptString6 = jSONObject7.optString("text");
                        String strOptString7 = jSONObject7.optString("position");
                        nativeView.makeRichText(iWebview, strOptString6, (TextUtils.isEmpty(strOptString7) || strOptString7.equals("null")) ? null : new JSONObject(strOptString7), jSONObject7.has("richTextStyles") ? jSONObject7.optJSONObject("richTextStyles") : null, strOptString);
                    } else {
                        if (strOptString2.equals("input")) {
                            try {
                                jSONObject = new JSONObject(jSONObject7.optString("position"));
                            } catch (JSONException unused6) {
                                jSONObject = new JSONObject();
                            }
                            JSONObject jSONObject11 = jSONObject;
                            try {
                                jSONObject2 = new JSONObject(jSONObject7.optString("inputStyles"));
                            } catch (JSONException unused7) {
                                jSONObject2 = new JSONObject();
                            }
                            iWebview2 = iWebview;
                            nativeView.makeOverlay(iWebview2, null, null, -1, null, jSONObject11, jSONObject2, strOptString, "input", false, true);
                        } else {
                            iWebview2 = iWebview;
                            if (strOptString2.equals("weex")) {
                                nativeView.makeWeexView(iWebview2, jSONObject7, strOptString);
                            }
                        }
                        i2 = i + 1;
                        str2 = str;
                        jSONArray2 = jSONArray;
                    }
                }
                iWebview2 = iWebview;
                i2 = i + 1;
                str2 = str;
                jSONArray2 = jSONArray;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        nativeView.nativeInvalidate(true);
    }
}

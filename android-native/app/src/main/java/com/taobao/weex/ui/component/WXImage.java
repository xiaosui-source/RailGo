package com.taobao.weex.ui.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXImageSharpen;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.common.WXRuntimeException;
import com.taobao.weex.dom.WXImageQuality;
import com.taobao.weex.performance.WXAnalyzerDataTransfer;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.view.WXImageView;
import com.taobao.weex.ui.view.border.BorderDrawable;
import com.taobao.weex.utils.ImageDrawable;
import com.taobao.weex.utils.ImgURIUtil;
import com.taobao.weex.utils.SingleFunctionParser;
import com.taobao.weex.utils.WXDomUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewToImageUtil;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXImage extends WXComponent<ImageView> {
    private static SingleFunctionParser.FlatMapper<Integer> BLUR_RADIUS_MAPPER = new SingleFunctionParser.FlatMapper<Integer>() { // from class: com.taobao.weex.ui.component.WXImage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.taobao.weex.utils.SingleFunctionParser.FlatMapper
        public Integer map(String str) {
            return WXUtils.getInteger(str, 0);
        }
    };
    public static final String ERRORDESC = "errorDesc";
    public static final String SUCCEED = "success";
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    private boolean mAutoRecycle;
    private int mBlurRadius;
    private WXSDKInstance.FrameViewEventListener mFrameViewEventListener;
    protected boolean mIsUni;
    private String mSrc;
    private String preImgUrlStr;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new WXImage(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface Measurable {
        int getNaturalHeight();

        int getNaturalWidth();
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public class MyImageListener implements WXImageStrategy.ImageListener {
        private String rewritedStr;
        private WeakReference<WXImage> wxImageWeakReference;

        MyImageListener(WXImage wXImage, String str) {
            this.wxImageWeakReference = new WeakReference<>(wXImage);
            this.rewritedStr = str;
        }

        @Override // com.taobao.weex.common.WXImageStrategy.ImageListener
        public void onImageFinish(String str, ImageView imageView, boolean z, Map map) {
            WXImage wXImage = this.wxImageWeakReference.get();
            if (wXImage == null) {
                return;
            }
            if (map == null) {
                HashMap map2 = new HashMap();
                map2.put(WXImage.SUCCEED, Boolean.FALSE);
                wXImage.fireEvent("error", map2);
                return;
            }
            wXImage.onImageFinish(z, map);
            HashMap map3 = new HashMap();
            HashMap map4 = new HashMap(2);
            map4.put("width", map.get("width"));
            map4.put("height", map.get("height"));
            if (WXImage.this.mIsUni) {
                if (!z && wXImage.containsEvent("error")) {
                    map3.put(WXImage.SUCCEED, Boolean.valueOf(z));
                    map3.put("detail", map4);
                    wXImage.fireEvent("error", map3);
                }
                if (z && wXImage.containsEvent("load")) {
                    map3.put(WXImage.SUCCEED, Boolean.valueOf(z));
                    map3.put("detail", map4);
                    wXImage.fireEvent("load", map3);
                }
            } else if (wXImage.containsEvent("load")) {
                map3.put(WXImage.SUCCEED, Boolean.valueOf(z));
                map3.put("detail", map4);
                wXImage.fireEvent("load", map3);
            }
            wXImage.monitorImgSize(imageView, this.rewritedStr);
        }
    }

    @Deprecated
    public WXImage(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, basicComponentData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void monitorImgSize(ImageView imageView, String str) {
        WXSDKInstance wXComponent;
        if (imageView == null || (wXComponent = getInstance()) == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Drawable drawable = imageView.getDrawable();
        if (layoutParams == null || drawable == null) {
            return;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (this.preImgUrlStr.equals(str)) {
            return;
        }
        this.preImgUrlStr = str;
        if (intrinsicHeight > 1081 && intrinsicWidth > 721) {
            wXComponent.getApmForInstance().updateDiffStats(WXInstanceApm.KEY_PAGE_STATS_LARGE_IMG_COUNT, 1.0d);
            if (WXAnalyzerDataTransfer.isOpenPerformance) {
                WXAnalyzerDataTransfer.transferPerformance(getInstanceId(), "details", WXInstanceApm.KEY_PAGE_STATS_LARGE_IMG_COUNT, intrinsicWidth + "*" + intrinsicHeight + "," + str);
            }
        }
        long j = intrinsicHeight * intrinsicWidth;
        long measuredHeight = imageView.getMeasuredHeight() * imageView.getMeasuredWidth();
        if (measuredHeight != 0 && j / measuredHeight > 1.2d && j - measuredHeight > 1600) {
            wXComponent.getWXPerformance().wrongImgSizeCount += 1.0d;
            wXComponent.getApmForInstance().updateDiffStats(WXInstanceApm.KEY_PAGE_STATS_WRONG_IMG_SIZE_COUNT, 1.0d);
            if (WXAnalyzerDataTransfer.isOpenPerformance) {
                WXAnalyzerDataTransfer.transferPerformance(getInstanceId(), "details", WXInstanceApm.KEY_PAGE_STATS_WRONG_IMG_SIZE_COUNT, StringUtil.format("imgSize:[%d,%d],viewSize:[%d,%d],urL:%s", Integer.valueOf(intrinsicWidth), Integer.valueOf(intrinsicHeight), Integer.valueOf(imageView.getMeasuredWidth()), Integer.valueOf(imageView.getMeasuredHeight()), str));
            }
        }
    }

    private int parseBlurRadius(String str) {
        if (str == null) {
            return 0;
        }
        try {
            List list = new SingleFunctionParser(str, BLUR_RADIUS_MAPPER).parse(Constants.Event.BLUR);
            if (list != null && !list.isEmpty()) {
                return ((Integer) list.get(0)).intValue();
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    private void runSrc(String str) {
        if (getHostView() == null || getInstance() == null) {
            return;
        }
        if (getInstance().getImageNetworkHandler() != null) {
            String strFetchLocal = getInstance().getImageNetworkHandler().fetchLocal(str);
            if (!TextUtils.isEmpty(strFetchLocal)) {
                str = strFetchLocal;
            }
        }
        if (PdrUtil.isEmpty(str)) {
            return;
        }
        ImageView hostView = getHostView();
        if (hostView != null && hostView.getDrawable() != null && !TextUtils.equals(this.mSrc, str)) {
            hostView.setImageDrawable(null);
        }
        this.mSrc = str;
        Uri uriRewriteUri = getInstance().rewriteUri(Uri.parse(str), "image");
        if (Constants.Scheme.LOCAL.equals(uriRewriteUri.getScheme())) {
            setLocalSrc(uriRewriteUri);
        } else {
            setRemoteSrc(uriRewriteUri, parseBlurRadius(getStyles().getBlur()));
        }
    }

    private void setBlurRadius(String str, int i) {
        if (getInstance() == null || i == this.mBlurRadius) {
            return;
        }
        Uri uriRewriteUri = getInstance().rewriteUri(Uri.parse(str), "image");
        if (Constants.Scheme.LOCAL.equals(uriRewriteUri.getScheme())) {
            return;
        }
        setRemoteSrc(uriRewriteUri, i);
    }

    private void setLocalSrc(Uri uri) {
        ImageView hostView;
        Drawable drawableFromLoaclSrc = ImgURIUtil.getDrawableFromLoaclSrc(getContext(), uri);
        if (drawableFromLoaclSrc == null || (hostView = getHostView()) == null) {
            return;
        }
        hostView.setImageDrawable(drawableFromLoaclSrc);
    }

    private void setRemoteSrc(Uri uri, int i) {
        WXImageStrategy wXImageStrategy = new WXImageStrategy(getInstanceId());
        wXImageStrategy.isClipping = true;
        wXImageStrategy.isSharpen = getAttrs().getImageSharpen() == WXImageSharpen.SHARPEN;
        wXImageStrategy.blurRadius = Math.max(0, i);
        this.mBlurRadius = i;
        String string = uri.toString();
        wXImageStrategy.setImageListener(new MyImageListener(this, string));
        if (getAttrs().containsKey("autoCompression")) {
            wXImageStrategy.setAutoCompression(WXUtils.getBoolean(getAttrs().get("autoCompression"), Boolean.TRUE).booleanValue());
        }
        String str = getAttrs().containsKey(Constants.Name.PLACEHOLDER) ? (String) getAttrs().get(Constants.Name.PLACEHOLDER) : getAttrs().containsKey(Constants.Name.PLACE_HOLDER) ? (String) getAttrs().get(Constants.Name.PLACE_HOLDER) : null;
        if (str != null) {
            wXImageStrategy.placeHolder = getInstance().rewriteUri(Uri.parse(str), "image").toString();
        }
        wXImageStrategy.instanceId = getInstanceId();
        setImage(string, wXImageStrategy);
    }

    private void updateBorderRadius() {
        if (getHostView() instanceof WXImageView) {
            WXImageView wXImageView = (WXImageView) getHostView();
            BorderDrawable borderDrawable = WXViewUtils.getBorderDrawable(getHostView());
            float[] borderInnerRadius = borderDrawable != null ? borderDrawable.getBorderInnerRadius(new RectF(0.0f, 0.0f, WXDomUtils.getContentWidth(this), WXDomUtils.getContentHeight(this))) : new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
            wXImageView.setBorderRadius(borderInnerRadius);
            if (wXImageView.getDrawable() instanceof ImageDrawable) {
                ImageDrawable imageDrawable = (ImageDrawable) wXImageView.getDrawable();
                if (Arrays.equals(imageDrawable.getCornerRadii(), borderInnerRadius)) {
                    return;
                }
                imageDrawable.setCornerRadii(borderInnerRadius);
            }
        }
    }

    public void autoRecoverImage() {
        if (this.mAutoRecycle) {
            setSrc(this.mSrc);
        }
    }

    public void autoReleaseImage() {
        if (!this.mAutoRecycle || getHostView() == null || getInstance() == null || getInstance().getImgLoaderAdapter() == null) {
            return;
        }
        getInstance().getImgLoaderAdapter().setImage(null, (ImageView) this.mHost, null, null);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (getHostView() != null && getInstance() != null && (getHostView() instanceof WXImageView) && getInstance().getImgLoaderAdapter() != null) {
            getInstance().getImgLoaderAdapter().setImage(null, (ImageView) this.mHost, null, null);
        }
        super.destroy();
    }

    protected WXImageQuality getImageQuality() {
        return getAttrs().getImageQuality();
    }

    protected ImageView.ScaleType getResizeMode(String str) {
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
        if (!TextUtils.isEmpty(str)) {
            str.getClass();
            if (str.equals(IApp.ConfigProperty.CONFIG_COVER)) {
                return ImageView.ScaleType.CENTER_CROP;
            }
            if (str.equals("contain")) {
                return ImageView.ScaleType.FIT_CENTER;
            }
        }
        return scaleType;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected void onFinishLayout() {
        super.onFinishLayout();
        updateBorderRadius();
    }

    public void onImageFinish(boolean z, Map map) {
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void recycled() {
        super.recycled();
        if (getHostView() != null && getInstance() != null && getInstance().getImgLoaderAdapter() != null) {
            getInstance().getImgLoaderAdapter().setImage(null, (ImageView) this.mHost, null, null);
        } else {
            if (WXEnvironment.isApkDebugable()) {
                throw new WXRuntimeException("getImgLoaderAdapter() == null");
            }
            WXLogUtils.e("Error getImgLoaderAdapter() == null");
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void refreshData(WXComponent wXComponent) {
        super.refreshData(wXComponent);
        if (wXComponent instanceof WXImage) {
            setSrc(wXComponent.getAttrs().getImageSrc());
        }
    }

    @JSMethod(uiThread = false)
    public void save(final JSCallback jSCallback) {
        if (ContextCompat.checkSelfPermission(getContext(), Permission.WRITE_EXTERNAL_STORAGE) != 0 && (getContext() instanceof Activity)) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            if (jSCallback != null) {
                HashMap map = new HashMap();
                map.put(SUCCEED, Boolean.FALSE);
                map.put(ERRORDESC, "Permission denied: android.permission.WRITE_EXTERNAL_STORAGE");
                jSCallback.invoke(map);
                return;
            }
            return;
        }
        if (this.mHost == 0) {
            if (jSCallback != null) {
                HashMap map2 = new HashMap();
                map2.put(SUCCEED, Boolean.FALSE);
                map2.put(ERRORDESC, "Image component not initialized");
                jSCallback.invoke(map2);
                return;
            }
            return;
        }
        String str = this.mSrc;
        if (str != null && !str.equals("")) {
            WXViewToImageUtil.generateImage(this.mHost, 0, -460552, new WXViewToImageUtil.OnImageSavedCallback() { // from class: com.taobao.weex.ui.component.WXImage.2
                @Override // com.taobao.weex.utils.WXViewToImageUtil.OnImageSavedCallback
                public void onSaveFailed(String str2) {
                    if (jSCallback != null) {
                        HashMap map3 = new HashMap();
                        map3.put(WXImage.SUCCEED, Boolean.FALSE);
                        map3.put(WXImage.ERRORDESC, str2);
                        jSCallback.invoke(map3);
                    }
                }

                @Override // com.taobao.weex.utils.WXViewToImageUtil.OnImageSavedCallback
                public void onSaveSucceed(String str2) {
                    if (jSCallback != null) {
                        HashMap map3 = new HashMap();
                        map3.put(WXImage.SUCCEED, Boolean.TRUE);
                        jSCallback.invoke(map3);
                    }
                }
            });
        } else if (jSCallback != null) {
            HashMap map3 = new HashMap();
            map3.put(SUCCEED, Boolean.FALSE);
            map3.put(ERRORDESC, "Image does not have the correct src");
            jSCallback.invoke(map3);
        }
    }

    protected void setImage(String str, WXImageStrategy wXImageStrategy) {
        IWXImgLoaderAdapter imgLoaderAdapter = getInstance().getImgLoaderAdapter();
        if (imgLoaderAdapter != null) {
            imgLoaderAdapter.setImage(str, getHostView(), getImageQuality(), wXImageStrategy);
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) {
        int blurRadius;
        str.getClass();
        str.hashCode();
        blurRadius = 0;
        switch (str) {
            case "autoBitmapRecycle":
                boolean zBooleanValue = WXUtils.getBoolean(obj, Boolean.valueOf(this.mAutoRecycle)).booleanValue();
                this.mAutoRecycle = zBooleanValue;
                if (!zBooleanValue && getInstance() != null) {
                    getInstance().getApmForInstance().updateDiffStats(WXInstanceApm.KEY_PAGE_STATS_IMG_UN_RECYCLE_NUM, 1.0d);
                }
                return true;
            case "filter":
                if (obj != null && (obj instanceof String)) {
                    blurRadius = parseBlurRadius((String) obj);
                }
                if (!TextUtils.isEmpty(this.mSrc)) {
                    setBlurRadius(this.mSrc, blurRadius);
                }
                return true;
            case "resize":
                String string = WXUtils.getString(obj, null);
                if (string != null) {
                    setResize(string);
                }
                return true;
            case "src":
                String string2 = WXUtils.getString(obj, null);
                if (string2 != null) {
                    setSrc(string2);
                }
                return true;
            case "resizeMode":
                String string3 = WXUtils.getString(obj, null);
                if (string3 != null) {
                    setResizeMode(string3);
                }
            case "imageQuality":
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    @WXComponentProp(name = "resize")
    public void setResize(String str) {
        setResizeMode(str);
    }

    @WXComponentProp(name = Constants.Name.RESIZE_MODE)
    public void setResizeMode(String str) {
        getHostView().setScaleType(getResizeMode(str));
        getHostView().setImageDrawable(getHostView().getDrawable());
    }

    @WXComponentProp(name = "src")
    public void setSrc(String str) {
        runSrc(str);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void updateProperties(Map<String, Object> map) {
        super.updateProperties(map);
        updateBorderRadius();
    }

    public WXImage(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.mAutoRecycle = true;
        this.mIsUni = false;
        this.preImgUrlStr = "";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public ImageView initComponentHostView(Context context) {
        WXImageView wXImageView = new WXImageView(context);
        wXImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        wXImageView.setCropToPadding(true);
        wXImageView.holdComponent(this);
        return wXImageView;
    }
}

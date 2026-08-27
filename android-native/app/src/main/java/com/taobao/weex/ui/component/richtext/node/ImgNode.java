package com.taobao.weex.ui.component.richtext.node;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.DrawableStrategy;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.richtext.span.ImgSpan;
import com.taobao.weex.ui.component.richtext.span.ItemClickSpan;
import com.taobao.weex.utils.ImgURIUtil;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class ImgNode extends RichTextNode {
    public static final String NODE_TYPE = "image";

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    static class ImgNodeCreator implements RichTextNodeCreator<ImgNode> {
        ImgNodeCreator() {
        }

        @Override // com.taobao.weex.ui.component.richtext.node.RichTextNodeCreator
        public /* bridge */ /* synthetic */ RichTextNode createRichTextNode(Context context, String str, String str2, String str3, Map map, Map map2) {
            return createRichTextNode(context, str, str2, str3, (Map<String, Object>) map, (Map<String, Object>) map2);
        }

        @Override // com.taobao.weex.ui.component.richtext.node.RichTextNodeCreator
        public ImgNode createRichTextNode(Context context, String str, String str2) {
            return new ImgNode(context, str, str2);
        }

        @Override // com.taobao.weex.ui.component.richtext.node.RichTextNodeCreator
        public ImgNode createRichTextNode(Context context, String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2) {
            return new ImgNode(context, str, str2, str3, map, map2);
        }
    }

    private ImgSpan createImgSpan(WXSDKInstance wXSDKInstance) {
        Object obj = (this.style.containsKey("width") ? this.style : this.attr).get("width");
        Object obj2 = (this.style.containsKey("height") ? this.style : this.attr).get("height");
        int realPxByWidth = (int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(obj), wXSDKInstance.getInstanceViewPortWidthWithFloat());
        int realPxByWidth2 = (int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(obj2), wXSDKInstance.getInstanceViewPortWidthWithFloat());
        ImgSpan imgSpan = new ImgSpan(realPxByWidth, realPxByWidth2, this.mInstanceId, this.mComponentRef);
        Uri uriRewriteUri = wXSDKInstance.rewriteUri(Uri.parse(this.attr.get("src").toString()), "image");
        if (Constants.Scheme.LOCAL.equals(uriRewriteUri.getScheme())) {
            imgSpan.setDrawable(ImgURIUtil.getDrawableFromLoaclSrc(this.mContext, uriRewriteUri), false);
            return imgSpan;
        }
        DrawableStrategy drawableStrategy = new DrawableStrategy();
        drawableStrategy.width = realPxByWidth;
        drawableStrategy.height = realPxByWidth2;
        WXSDKEngine.getDrawableLoader().setDrawable(uriRewriteUri.toString(), imgSpan, drawableStrategy);
        return imgSpan;
    }

    @Override // com.taobao.weex.ui.component.richtext.node.RichTextNode
    protected boolean isInternalNode() {
        return false;
    }

    @Override // com.taobao.weex.ui.component.richtext.node.RichTextNode
    public String toString() {
        return "\ufeff";
    }

    @Override // com.taobao.weex.ui.component.richtext.node.RichTextNode
    protected void updateSpans(SpannableStringBuilder spannableStringBuilder, int i) {
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(this.mInstanceId);
        if (WXSDKEngine.getDrawableLoader() != null) {
            if (((this.style.containsKey("width") && this.style.containsKey("height")) || (this.attr.containsKey("width") && this.attr.containsKey("height"))) && this.attr.containsKey("src") && sDKInstance != null) {
                LinkedList linkedList = new LinkedList();
                linkedList.add(createImgSpan(sDKInstance));
                if (this.attr.containsKey(RichTextNode.PSEUDO_REF)) {
                    linkedList.add(new ItemClickSpan(this.mInstanceId, this.mComponentRef, this.attr.get(RichTextNode.PSEUDO_REF).toString()));
                }
                Iterator it = linkedList.iterator();
                while (it.hasNext()) {
                    spannableStringBuilder.setSpan(it.next(), 0, spannableStringBuilder.length(), RichTextNode.createSpanFlag(i));
                }
            }
        }
    }

    private ImgNode(Context context, String str, String str2) {
        super(context, str, str2);
    }

    private ImgNode(Context context, String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2) {
        super(context, str, str2, str3, map, map2);
    }
}

package com.taobao.weex.ui.component.richtext;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXText;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.richtext.node.RichTextNode;
import com.taobao.weex.ui.component.richtext.node.RichTextNodeManager;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXRichText extends WXText {
    private List<RichTextNode> nodes;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new WXRichText(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    static class RichTextContentBoxMeasurement extends TextContentBoxMeasurement {
        public RichTextContentBoxMeasurement(WXComponent wXComponent) {
            super(wXComponent);
        }

        @Override // com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement
        protected Spanned createSpanned(String str) {
            if (TextUtils.isEmpty(str)) {
                Spannable span = ((WXRichText) this.mComponent).toSpan();
                updateSpannable(span, RichTextNode.createSpanFlag(0));
                return span;
            }
            if ((!(this.mComponent.getInstance() != null) || !(this.mComponent.getInstance().getUIContext() != null)) || TextUtils.isEmpty(this.mComponent.getInstanceId())) {
                return new SpannedString("");
            }
            Spannable spannable = RichTextNode.parse(this.mComponent.getInstance().getUIContext(), this.mComponent.getInstanceId(), this.mComponent.getRef(), str);
            updateSpannable(spannable, RichTextNode.createSpanFlag(0));
            return spannable;
        }
    }

    public WXRichText(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.nodes = new LinkedList();
        setContentBoxMeasurement(new RichTextContentBoxMeasurement(this));
    }

    private RichTextNode findRichNode(String str) {
        List<RichTextNode> list;
        if (TextUtils.isEmpty(str) || (list = this.nodes) == null || list.isEmpty()) {
            return null;
        }
        Iterator<RichTextNode> it = this.nodes.iterator();
        while (it.hasNext()) {
            RichTextNode richTextNodeFindRichNode = it.next().findRichNode(str);
            if (richTextNodeFindRichNode != null) {
                return richTextNodeFindRichNode;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Spannable toSpan() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        List<RichTextNode> list = this.nodes;
        if (list != null && !list.isEmpty()) {
            Iterator<RichTextNode> it = this.nodes.iterator();
            while (it.hasNext()) {
                spannableStringBuilder.append((CharSequence) it.next().toSpan(1));
            }
        }
        return spannableStringBuilder;
    }

    public void AddChildNode(String str, String str2, String str3, Map<String, String> map, Map<String, String> map2) {
        if (getInstance() == null || getInstance().getUIContext() == null || TextUtils.isEmpty(getInstanceId()) || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        RichTextNode richTextNodeCreateRichTextNode = RichTextNodeManager.createRichTextNode(getInstance().getUIContext(), getInstanceId(), getRef(), str, str2, map, map2);
        if (TextUtils.isEmpty(str3)) {
            this.nodes.add(richTextNodeCreateRichTextNode);
            return;
        }
        RichTextNode richTextNodeFindRichNode = findRichNode(str3);
        if (richTextNodeFindRichNode != null) {
            richTextNodeFindRichNode.addChildNode(richTextNodeCreateRichTextNode);
        }
    }

    public void removeChildNode(String str, String str2) throws Throwable {
        List<RichTextNode> list = this.nodes;
        if (list == null || list.isEmpty()) {
            return;
        }
        if (!str.equals("")) {
            RichTextNode richTextNodeFindRichNode = findRichNode(str);
            if (richTextNodeFindRichNode != null) {
                richTextNodeFindRichNode.removeChildNode(str2);
                return;
            }
            return;
        }
        for (RichTextNode richTextNode : this.nodes) {
            if (TextUtils.equals(richTextNode.getRef(), str2)) {
                this.nodes.remove(richTextNode);
            }
        }
    }

    public void updateChildNodeAttrs(String str, Map<String, Object> map) {
        RichTextNode richTextNodeFindRichNode = findRichNode(str);
        if (richTextNodeFindRichNode != null) {
            richTextNodeFindRichNode.updateAttrs(map);
        }
    }

    public void updateChildNodeStyles(String str, Map<String, Object> map) {
        RichTextNode richTextNodeFindRichNode = findRichNode(str);
        if (richTextNodeFindRichNode != null) {
            richTextNodeFindRichNode.updateStyles(map);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXText, com.taobao.weex.ui.component.WXComponent
    public WXRichTextView initComponentHostView(Context context) {
        return new WXRichTextView(context);
    }
}

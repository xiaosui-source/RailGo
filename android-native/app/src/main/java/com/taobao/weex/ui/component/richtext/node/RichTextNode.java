package com.taobao.weex.ui.component.richtext.node;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.WXCustomStyleSpan;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXResourceUtils;
import io.dcloud.feature.uniapp.dom.AbsStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public abstract class RichTextNode {
    public static final String ATTR = "attr";
    public static final String CHILDREN = "children";
    public static final String ITEM_CLICK = "itemclick";
    private static final int MAX_LEVEL = 255;
    public static final String PSEUDO_REF = "pseudoRef";
    public static final String STYLE = "style";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
    protected Map<String, Object> attr;
    protected List<RichTextNode> children;
    protected final String mComponentRef;
    protected final Context mContext;
    protected final String mInstanceId;
    protected final String mRef;
    protected Map<String, Object> style;

    protected RichTextNode(Context context, String str, String str2) {
        this.mContext = context;
        this.mInstanceId = str;
        this.mComponentRef = str2;
        this.mRef = null;
    }

    private WXCustomStyleSpan createCustomStyleSpan() {
        int fontWeight = this.style.containsKey(Constants.Name.FONT_WEIGHT) ? AbsStyle.getFontWeight(this.style) : -1;
        int fontStyle = this.style.containsKey(Constants.Name.FONT_STYLE) ? AbsStyle.getFontStyle(this.style) : -1;
        String fontFamily = this.style.containsKey(Constants.Name.FONT_FAMILY) ? AbsStyle.getFontFamily(this.style) : null;
        if (fontWeight == -1 && fontStyle == -1 && fontFamily == null) {
            return null;
        }
        return new WXCustomStyleSpan(fontStyle, fontWeight, fontFamily);
    }

    private static int createPriorityFlag(int i) {
        if (i <= 255) {
            return (255 - i) << 16;
        }
        return 16711680;
    }

    public static int createSpanFlag(int i) {
        return createPriorityFlag(i) | 17;
    }

    public static Spannable parse(Context context, String str, String str2, String str3) {
        RichTextNode richTextNodeCreateRichTextNode;
        JSONArray array = JSON.parseArray(str3);
        if (array == null || array.isEmpty()) {
            return new SpannableString("");
        }
        ArrayList arrayList = new ArrayList(array.size());
        for (int i = 0; i < array.size(); i++) {
            JSONObject jSONObject = array.getJSONObject(i);
            if (jSONObject != null && (richTextNodeCreateRichTextNode = RichTextNodeManager.createRichTextNode(context, str, str2, jSONObject)) != null) {
                arrayList.add(richTextNodeCreateRichTextNode);
            }
        }
        return parse(arrayList);
    }

    public void addChildNode(RichTextNode richTextNode) {
        if (this.children == null) {
            this.children = new LinkedList();
        }
        if (richTextNode == null || !isInternalNode()) {
            return;
        }
        this.children.add(richTextNode);
    }

    public RichTextNode findRichNode(String str) {
        String str2 = this.mRef;
        if (str2 != null && TextUtils.equals(str2, str)) {
            return this;
        }
        List<RichTextNode> list = this.children;
        if (list == null || list.isEmpty()) {
            return null;
        }
        Iterator<RichTextNode> it = this.children.iterator();
        while (it.hasNext()) {
            RichTextNode richTextNodeFindRichNode = it.next().findRichNode(str);
            if (richTextNodeFindRichNode != null) {
                return richTextNodeFindRichNode;
            }
        }
        return null;
    }

    public String getRef() {
        return this.mRef;
    }

    protected abstract boolean isInternalNode();

    public void removeChildNode(String str) throws Throwable {
        List<RichTextNode> list = this.children;
        if (list == null || list.isEmpty() || TextUtils.isEmpty(str)) {
            return;
        }
        try {
            for (RichTextNode richTextNode : this.children) {
                if (TextUtils.equals(richTextNode.mRef, str)) {
                    this.children.remove(richTextNode);
                }
            }
        } catch (Exception e) {
            WXLogUtils.getStackTrace(e);
        }
    }

    public Spannable toSpan(int i) {
        List<RichTextNode> list;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) toString());
        if (isInternalNode() && (list = this.children) != null) {
            Iterator<RichTextNode> it = list.iterator();
            while (it.hasNext()) {
                spannableStringBuilder.append((CharSequence) it.next().toSpan(i + 1));
            }
        }
        updateSpans(spannableStringBuilder, i);
        return spannableStringBuilder;
    }

    public abstract String toString();

    public void updateAttrs(Map<String, Object> map) {
        if (this.attr == null || map.isEmpty()) {
            return;
        }
        this.attr.putAll(map);
    }

    protected void updateSpans(SpannableStringBuilder spannableStringBuilder, int i) {
        int color;
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(this.mInstanceId);
        if (this.style == null || sDKInstance == null) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        WXCustomStyleSpan wXCustomStyleSpanCreateCustomStyleSpan = createCustomStyleSpan();
        if (wXCustomStyleSpanCreateCustomStyleSpan != null) {
            linkedList.add(wXCustomStyleSpanCreateCustomStyleSpan);
        }
        if (this.style.containsKey(Constants.Name.FONT_SIZE)) {
            linkedList.add(new AbsoluteSizeSpan(AbsStyle.getFontSize(this.style, sDKInstance.getDefaultFontSize(), sDKInstance.getInstanceViewPortWidthWithFloat())));
        }
        if (this.style.containsKey("backgroundColor") && (color = WXResourceUtils.getColor(this.style.get("backgroundColor").toString(), 0)) != 0) {
            linkedList.add(new BackgroundColorSpan(color));
        }
        if (this.style.containsKey("color")) {
            linkedList.add(new ForegroundColorSpan(WXResourceUtils.getColor(AbsStyle.getTextColor(this.style))));
        }
        int iCreateSpanFlag = createSpanFlag(i);
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            spannableStringBuilder.setSpan(it.next(), 0, spannableStringBuilder.length(), iCreateSpanFlag);
        }
    }

    public void updateStyles(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        this.style.putAll(map);
    }

    protected RichTextNode(Context context, String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2) {
        this.mContext = context;
        this.mInstanceId = str;
        this.mComponentRef = str2;
        this.mRef = str3;
        if (map != null) {
            this.style = map;
        } else {
            this.style = new ArrayMap(0);
        }
        if (map2 != null) {
            this.attr = map2;
        } else {
            this.attr = new ArrayMap(0);
        }
        this.children = new LinkedList();
    }

    final void parse(Context context, String str, String str2, JSONObject jSONObject) {
        JSONObject jSONObject2 = jSONObject.getJSONObject("style");
        if (jSONObject2 != null) {
            ArrayMap arrayMap = new ArrayMap();
            this.style = arrayMap;
            arrayMap.putAll(jSONObject2);
        } else {
            this.style = new ArrayMap(0);
        }
        JSONObject jSONObject3 = jSONObject.getJSONObject(ATTR);
        if (jSONObject3 != null) {
            ArrayMap arrayMap2 = new ArrayMap(jSONObject3.size());
            this.attr = arrayMap2;
            arrayMap2.putAll(jSONObject3);
        } else {
            this.attr = new ArrayMap(0);
        }
        JSONArray jSONArray = jSONObject.getJSONArray(CHILDREN);
        if (jSONArray != null) {
            this.children = new ArrayList(jSONArray.size());
            for (int i = 0; i < jSONArray.size(); i++) {
                RichTextNode richTextNodeCreateRichTextNode = RichTextNodeManager.createRichTextNode(context, str, str2, jSONArray.getJSONObject(i));
                if (richTextNodeCreateRichTextNode != null) {
                    this.children.add(richTextNodeCreateRichTextNode);
                }
            }
            return;
        }
        this.children = new ArrayList(0);
    }

    private static Spannable parse(List<RichTextNode> list) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        Iterator<RichTextNode> it = list.iterator();
        while (it.hasNext()) {
            spannableStringBuilder.append((CharSequence) it.next().toSpan(1));
        }
        return spannableStringBuilder;
    }
}

package io.dcloud.feature.uniapp.dom;

import android.text.Layout;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.binding.ELUtils;
import com.taobao.weex.ui.component.WXTextDecoration;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public abstract class AbsStyle implements Map<String, Object>, Cloneable {
    public static final int UNSET = -1;
    private static final long serialVersionUID = 611132641365274134L;
    protected ArrayMap<String, Object> mBindingStyle;
    protected Map<String, Object> mPesudoResetStyleMap;
    protected Map<String, Map<String, Object>> mPesudoStyleMap;
    protected Map<String, Object> mStyles;

    public AbsStyle() {
        this.mStyles = new ArrayMap();
    }

    private boolean addBindingStyleIfStatement(String str, Object obj) {
        if (!ELUtils.isBinding(obj)) {
            return false;
        }
        if (this.mBindingStyle == null) {
            this.mBindingStyle = new ArrayMap<>();
        }
        this.mBindingStyle.put(str, ELUtils.bindingBlock(obj));
        return true;
    }

    public static String getFontFamily(Map<String, Object> map) {
        Object obj;
        if (map == null || (obj = map.get(Constants.Name.FONT_FAMILY)) == null) {
            return null;
        }
        return obj.toString();
    }

    public static int getFontSize(Map<String, Object> map, int i, float f) throws Throwable {
        float realPxByWidth;
        if (map == null) {
            realPxByWidth = WXViewUtils.getRealPxByWidth(i, f);
        } else {
            int i2 = WXUtils.getInt(map.get(Constants.Name.FONT_SIZE));
            if (i2 > 0) {
                i = i2;
            }
            realPxByWidth = WXViewUtils.getRealPxByWidth(i, f);
        }
        return (int) realPxByWidth;
    }

    public static int getFontStyle(Map<String, Object> map) {
        Object obj;
        return (map == null || (obj = map.get(Constants.Name.FONT_STYLE)) == null || !obj.toString().equals(Constants.Value.ITALIC)) ? 0 : 2;
    }

    public static int getFontWeight(Map<String, Object> map) {
        Object obj;
        if (map != null && (obj = map.get(Constants.Name.FONT_WEIGHT)) != null) {
            String string = obj.toString();
            string.getClass();
            string.hashCode();
            switch (string) {
                case "600":
                case "700":
                case "800":
                case "900":
                case "bold":
                    return 1;
            }
        }
        return 0;
    }

    public static int getLineHeight(Map<String, Object> map, float f) {
        int i;
        if (map != null && (i = WXUtils.getInt(map.get(Constants.Name.LINE_HEIGHT))) > 0) {
            return (int) WXViewUtils.getRealPxByWidth(i, f);
        }
        return -1;
    }

    public static int getLines(Map<String, Object> map) {
        return WXUtils.getInt(map.get(Constants.Name.LINES));
    }

    public static Layout.Alignment getTextAlignment(Map<String, Object> map) {
        return getTextAlignment(map, false);
    }

    public static WXTextDecoration getTextDecoration(Map<String, Object> map) {
        Object obj;
        if (map == null || (obj = map.get(Constants.Name.TEXT_DECORATION)) == null) {
            return WXTextDecoration.NONE;
        }
        String string = obj.toString();
        string.getClass();
        string.hashCode();
        switch (string) {
            case "line-through":
                return WXTextDecoration.LINETHROUGH;
            case "underline":
                return WXTextDecoration.UNDERLINE;
            case "none":
                return WXTextDecoration.NONE;
            default:
                return WXTextDecoration.INVALID;
        }
    }

    public static TextUtils.TruncateAt getTextOverflow(Map<String, Object> map) {
        if (TextUtils.equals(Constants.Name.ELLIPSIS, (String) map.get(Constants.Name.TEXT_OVERFLOW))) {
            return TextUtils.TruncateAt.END;
        }
        return null;
    }

    private void initPesudoMapsIfNeed(Map<? extends String, ?> map) {
        if (this.mPesudoStyleMap == null) {
            this.mPesudoStyleMap = new ArrayMap();
        }
        if (this.mPesudoResetStyleMap == null) {
            this.mPesudoResetStyleMap = new ArrayMap();
        }
        if (this.mPesudoResetStyleMap.isEmpty()) {
            this.mPesudoResetStyleMap.putAll(map);
        }
    }

    private Map<String, Object> parseBindingStylesStatements(Map map) {
        if (map != null && map.size() != 0) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (addBindingStyleIfStatement((String) entry.getKey(), entry.getValue())) {
                    Map<String, Map<String, Object>> map2 = this.mPesudoStyleMap;
                    if (map2 != null) {
                        map2.remove(entry.getKey());
                    }
                    Map<String, Object> map3 = this.mPesudoResetStyleMap;
                    if (map3 != null) {
                        map3.remove(entry.getKey());
                    }
                    it.remove();
                }
            }
        }
        return map;
    }

    @Override // java.util.Map
    public void clear() {
        this.mStyles.clear();
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract AbsStyle mo387clone();

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.mStyles.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.mStyles.containsValue(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.mStyles.entrySet();
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        return this.mStyles.equals(obj);
    }

    @Override // java.util.Map
    public Object get(Object obj) {
        return this.mStyles.get(obj);
    }

    public String getBackgroundColor() {
        Object obj = get("backgroundColor");
        return obj == null ? "" : obj.toString();
    }

    public ArrayMap<String, Object> getBindingStyle() {
        return this.mBindingStyle;
    }

    public String getBlur() {
        if (get(Constants.Name.FILTER) == null) {
            return null;
        }
        return get(Constants.Name.FILTER).toString().trim();
    }

    public String getBorderColor() {
        Object obj = get(Constants.Name.BORDER_COLOR);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public float getBorderRadius() {
        float f = WXUtils.getFloat(get(Constants.Name.BORDER_RADIUS));
        if (WXUtils.isUndefined(f)) {
            return Float.NaN;
        }
        return f;
    }

    public String getBorderStyle() {
        Object obj = get(Constants.Name.BORDER_STYLE);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public float getBottom() {
        float f = WXUtils.getFloat(get("bottom"));
        if (WXUtils.isUndefined(f)) {
            return Float.NaN;
        }
        return f;
    }

    public float getLeft() {
        float f = WXUtils.getFloat(get("left"));
        if (WXUtils.isUndefined(f)) {
            return Float.NaN;
        }
        return f;
    }

    public float getOpacity() {
        Object obj = get("opacity");
        if (obj == null) {
            return 1.0f;
        }
        return WXUtils.getFloat(obj);
    }

    public String getOverflow() {
        Object obj = get(Constants.Name.OVERFLOW);
        return obj == null ? "visible" : obj.toString();
    }

    public Map<String, Object> getPesudoResetStyles() {
        if (this.mPesudoResetStyleMap == null) {
            this.mPesudoResetStyleMap = new ArrayMap();
        }
        return this.mPesudoResetStyleMap;
    }

    public Map<String, Map<String, Object>> getPesudoStyles() {
        if (this.mPesudoStyleMap == null) {
            this.mPesudoStyleMap = new ArrayMap();
        }
        return this.mPesudoStyleMap;
    }

    public float getRight() {
        float f = WXUtils.getFloat(get("right"));
        if (WXUtils.isUndefined(f)) {
            return Float.NaN;
        }
        return f;
    }

    public int getTimeFontSize(int i) throws Throwable {
        int i2 = WXUtils.getInt(get("timeFontSize"));
        return i2 <= 0 ? i : i2;
    }

    public float getTop() {
        float f = WXUtils.getFloat(get("top"));
        if (WXUtils.isUndefined(f)) {
            return Float.NaN;
        }
        return f;
    }

    @Override // java.util.Map
    public int hashCode() {
        return this.mStyles.hashCode();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mStyles.isEmpty();
    }

    public boolean isFixed() {
        Object obj = get("position");
        if (obj == null) {
            return false;
        }
        return obj.toString().equals(Constants.Value.FIXED);
    }

    public boolean isSticky() {
        Object obj = get("position");
        if (obj == null) {
            return false;
        }
        return obj.toString().equals("sticky");
    }

    @Override // java.util.Map
    public Set<String> keySet() {
        return this.mStyles.keySet();
    }

    public void parseStatements() {
        Map<String, Object> map = this.mStyles;
        if (map != null) {
            this.mStyles = parseBindingStylesStatements(map);
        }
    }

    <T extends String, V> void processPesudoClasses(Map<T, V> map) {
        ArrayMap arrayMap = null;
        for (Map.Entry<T, V> entry : map.entrySet()) {
            T key = entry.getKey();
            int iIndexOf = key.indexOf(":");
            if (iIndexOf > 0) {
                initPesudoMapsIfNeed(map);
                String strSubstring = key.substring(iIndexOf);
                if (strSubstring.equals(Constants.PSEUDO.ENABLED)) {
                    String strSubstring2 = key.substring(0, iIndexOf);
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                    }
                    arrayMap.put(strSubstring2, entry.getValue());
                    this.mPesudoResetStyleMap.put(strSubstring2, entry.getValue());
                } else {
                    String strReplace = strSubstring.replace(Constants.PSEUDO.ENABLED, "");
                    Map<String, Object> arrayMap2 = this.mPesudoStyleMap.get(strReplace);
                    if (arrayMap2 == null) {
                        arrayMap2 = new ArrayMap<>();
                        this.mPesudoStyleMap.put(strReplace, arrayMap2);
                    }
                    arrayMap2.put(key.substring(0, iIndexOf), entry.getValue());
                }
            }
        }
        if (arrayMap == null || arrayMap.isEmpty()) {
            return;
        }
        this.mStyles.putAll(arrayMap);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends String, ? extends Object> map) {
        this.mStyles.putAll(map);
    }

    @Override // java.util.Map
    public Object remove(Object obj) {
        return this.mStyles.remove(obj);
    }

    @Override // java.util.Map
    public int size() {
        return this.mStyles.size();
    }

    public String toString() {
        return this.mStyles.toString();
    }

    public void updateStyle(Map<? extends String, ?> map, boolean z) {
        parseBindingStylesStatements(map);
        putAll(map, z);
    }

    @Override // java.util.Map
    public Collection<Object> values() {
        return this.mStyles.values();
    }

    public static Layout.Alignment getTextAlignment(Map<String, Object> map, boolean z) {
        Layout.Alignment alignment = z ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL;
        String str = (String) map.get(Constants.Name.TEXT_ALIGN);
        return TextUtils.equals("left", str) ? Layout.Alignment.ALIGN_NORMAL : TextUtils.equals("center", str) ? Layout.Alignment.ALIGN_CENTER : TextUtils.equals("right", str) ? Layout.Alignment.ALIGN_OPPOSITE : alignment;
    }

    @Override // java.util.Map
    public Object put(String str, Object obj) {
        if (addBindingStyleIfStatement(str, obj)) {
            return null;
        }
        return this.mStyles.put(str, obj);
    }

    public void putAll(Map<? extends String, ?> map, boolean z) {
        this.mStyles.putAll(map);
        if (z) {
            return;
        }
        processPesudoClasses(map);
    }

    public AbsStyle(Map<String, Object> map) {
        this.mStyles = map;
        processPesudoClasses(map);
    }

    public static String getTextColor(Map<String, Object> map) {
        Object obj;
        return (map == null || (obj = map.get("color")) == null) ? "" : obj.toString();
    }

    public AbsStyle(Map<String, Object> map, boolean z) {
        this();
        putAll(map, z);
    }
}

package com.taobao.weex.utils;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.Pair;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.utils.SingleFunctionParser;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXResourceUtils {
    private static final int COLOR_RANGE = 255;
    private static final SingleFunctionParser.NonUniformMapper<Number> FUNCTIONAL_RGBA_MAPPER;
    private static final SingleFunctionParser.FlatMapper<Integer> FUNCTIONAL_RGB_MAPPER;
    private static final int HEX = 16;
    private static final String RGB = "rgb";
    private static final String RGBA = "rgba";
    private static final int RGBA_SIZE = 4;
    private static final int RGB_SIZE = 3;
    private static final Map<String, Integer> colorMap;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    enum ColorConvertHandler {
        NAMED_COLOR_HANDLER { // from class: com.taobao.weex.utils.WXResourceUtils.ColorConvertHandler.1
            @Override // com.taobao.weex.utils.WXResourceUtils.ColorConvertHandler
            Pair<Boolean, Integer> handle(String str) {
                return WXResourceUtils.colorMap.containsKey(str) ? new Pair<>(Boolean.TRUE, (Integer) WXResourceUtils.colorMap.get(str)) : new Pair<>(Boolean.FALSE, 0);
            }
        },
        RGB_HANDLER { // from class: com.taobao.weex.utils.WXResourceUtils.ColorConvertHandler.2
            @Override // com.taobao.weex.utils.WXResourceUtils.ColorConvertHandler
            Pair<Boolean, Integer> handle(String str) throws NumberFormatException {
                if (str.length() != 4) {
                    return (str.length() == 7 || str.length() == 9) ? new Pair<>(Boolean.TRUE, Integer.valueOf(Color.parseColor(str))) : new Pair<>(Boolean.FALSE, 0);
                }
                int i = Integer.parseInt(str.substring(1, 2), 16);
                int i2 = Integer.parseInt(str.substring(2, 3), 16);
                int i3 = Integer.parseInt(str.substring(3, 4), 16);
                return new Pair<>(Boolean.TRUE, Integer.valueOf(Color.rgb(i + (i << 4), i2 + (i2 << 4), i3 + (i3 << 4))));
            }
        },
        FUNCTIONAL_RGB_HANDLER { // from class: com.taobao.weex.utils.WXResourceUtils.ColorConvertHandler.3
            @Override // com.taobao.weex.utils.WXResourceUtils.ColorConvertHandler
            Pair<Boolean, Integer> handle(String str) {
                List list = new SingleFunctionParser(str, WXResourceUtils.FUNCTIONAL_RGB_MAPPER).parse(WXResourceUtils.RGB);
                return (list == null || list.size() != 3) ? new Pair<>(Boolean.FALSE, 0) : new Pair<>(Boolean.TRUE, Integer.valueOf(Color.rgb(((Integer) list.get(0)).intValue(), ((Integer) list.get(1)).intValue(), ((Integer) list.get(2)).intValue())));
            }
        },
        FUNCTIONAL_RGBA_HANDLER { // from class: com.taobao.weex.utils.WXResourceUtils.ColorConvertHandler.4
            @Override // com.taobao.weex.utils.WXResourceUtils.ColorConvertHandler
            Pair<Boolean, Integer> handle(String str) {
                List list = new SingleFunctionParser(str, WXResourceUtils.FUNCTIONAL_RGBA_MAPPER).parse(WXResourceUtils.RGBA);
                return list.size() == 4 ? new Pair<>(Boolean.TRUE, Integer.valueOf(Color.argb(ColorConvertHandler.parseAlpha(((Number) list.get(3)).floatValue()), ((Number) list.get(0)).intValue(), ((Number) list.get(1)).intValue(), ((Number) list.get(2)).intValue()))) : new Pair<>(Boolean.FALSE, 0);
            }
        };

        /* JADX INFO: Access modifiers changed from: private */
        public static int parseAlpha(float f) {
            return (int) (f * 255.0f);
        }

        abstract Pair<Boolean, Integer> handle(String str);
    }

    static {
        HashMap map = new HashMap();
        colorMap = map;
        FUNCTIONAL_RGB_MAPPER = new SingleFunctionParser.FlatMapper<Integer>() { // from class: com.taobao.weex.utils.WXResourceUtils.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.taobao.weex.utils.SingleFunctionParser.FlatMapper
            public Integer map(String str) {
                int i = 255;
                int unitOrPercent = WXUtils.parseUnitOrPercent(str, 255);
                if (unitOrPercent < 0) {
                    i = 0;
                } else if (unitOrPercent <= 255) {
                    i = unitOrPercent;
                }
                return Integer.valueOf(i);
            }
        };
        FUNCTIONAL_RGBA_MAPPER = new SingleFunctionParser.NonUniformMapper<Number>() { // from class: com.taobao.weex.utils.WXResourceUtils.2
            @Override // com.taobao.weex.utils.SingleFunctionParser.NonUniformMapper
            public List<Number> map(List<String> list) {
                ArrayList arrayList = new ArrayList(4);
                int i = 0;
                while (i < 3) {
                    int i2 = 255;
                    int unitOrPercent = WXUtils.parseUnitOrPercent(list.get(i), 255);
                    if (unitOrPercent < 0) {
                        i2 = 0;
                    } else if (unitOrPercent <= 255) {
                        i2 = unitOrPercent;
                    }
                    arrayList.add(Integer.valueOf(i2));
                    i++;
                }
                arrayList.add(Float.valueOf(list.get(i)));
                return arrayList;
            }
        };
        map.put("aliceblue", -984833);
        map.put("antiquewhite", -332841);
        map.put("aqua", -16711681);
        map.put("aquamarine", -8388652);
        map.put("azure", -983041);
        map.put("beige", -657956);
        map.put("bisque", -6972);
        map.put("black", -16777216);
        map.put("blanchedalmond", -5171);
        map.put("blue", -16776961);
        map.put("blueviolet", -7722014);
        map.put("brown", -5952982);
        map.put("burlywood", -2180985);
        map.put("cadetblue", -10510688);
        map.put("chartreuse", -8388864);
        map.put("chocolate", -2987746);
        map.put("coral", -32944);
        map.put("cornflowerblue", -10185235);
        map.put("cornsilk", -1828);
        map.put("crimson", -2354116);
        map.put("cyan", -16711681);
        map.put("darkblue", -16777077);
        map.put("darkcyan", -16741493);
        map.put("darkgoldenrod", -4684277);
        map.put("darkgray", -5658199);
        map.put("darkgreen", -16751616);
        map.put("darkkhaki", -4343957);
        map.put("darkmagenta", -7667573);
        map.put("darkolivegreen", -11179217);
        map.put("darkorange", -29696);
        map.put("darkorchid", -6737204);
        map.put("darkred", -7667712);
        map.put("darksalmon", -1468806);
        map.put("darkseagreen", -7357297);
        map.put("darkslateblue", -12042869);
        map.put("darkslategray", -13676721);
        map.put("darkslategrey", -13676721);
        map.put("darkturquoise", -16724271);
        map.put("darkviolet", -7077677);
        map.put("deeppink", -60269);
        map.put("deepskyblue", -16728065);
        map.put("dimgray", -9868951);
        map.put("dimgrey", -9868951);
        map.put("dodgerblue", -14774017);
        map.put("firebrick", -5103070);
        map.put("floralwhite", -1296);
        map.put("forestgreen", -14513374);
        map.put("fuchsia", -65281);
        map.put("gainsboro", -2302756);
        map.put("ghostwhite", -460545);
        map.put("gold", -10496);
        map.put("goldenrod", -2448096);
        map.put("gray", -8355712);
        map.put("grey", -8355712);
        map.put("green", -16744448);
        map.put("greenyellow", -5374161);
        map.put("honeydew", -983056);
        map.put("hotpink", -38476);
        map.put("indianred", -3318692);
        map.put("indigo", -11861886);
        map.put("ivory", -16);
        map.put("khaki", -989556);
        map.put("lavender", -1644806);
        map.put("lavenderblush", -3851);
        map.put("lawngreen", -8586240);
        map.put("lemonchiffon", -1331);
        map.put("lightblue", -5383962);
        map.put("lightcoral", -1015680);
        map.put("lightcyan", -2031617);
        map.put("lightgoldenrodyellow", -329006);
        map.put("lightgray", -2894893);
        map.put("lightgrey", -2894893);
        map.put("lightgreen", -7278960);
        map.put("lightpink", -18751);
        map.put("lightsalmon", -24454);
        map.put("lightseagreen", -14634326);
        map.put("lightskyblue", -7876870);
        map.put("lightslategray", -8943463);
        map.put("lightslategrey", -8943463);
        map.put("lightsteelblue", -5192482);
        map.put("lightyellow", -32);
        map.put("lime", -16711936);
        map.put("limegreen", -13447886);
        map.put("linen", -331546);
        map.put("magenta", -65281);
        map.put("maroon", -8388608);
        map.put("mediumaquamarine", -10039894);
        map.put("mediumblue", -16777011);
        map.put("mediumorchid", -4565549);
        map.put("mediumpurple", -7114533);
        map.put("mediumseagreen", -12799119);
        map.put("mediumslateblue", -8689426);
        map.put("mediumspringgreen", -16713062);
        map.put("mediumturquoise", -12004916);
        map.put("mediumvioletred", -3730043);
        map.put("midnightblue", -15132304);
        map.put("mintcream", -655366);
        map.put("mistyrose", -6943);
        map.put("moccasin", -6987);
        map.put("navajowhite", -8531);
        map.put("navy", -16777088);
        map.put("oldlace", -133658);
        map.put("olive", -8355840);
        map.put("olivedrab", -9728477);
        map.put("orange", -23296);
        map.put("orangered", -47872);
        map.put("orchid", -2461482);
        map.put("palegoldenrod", -1120086);
        map.put("palegreen", -6751336);
        map.put("paleturquoise", -5247250);
        map.put("palevioletred", -2396013);
        map.put("papayawhip", -4139);
        map.put("peachpuff", -9543);
        map.put("peru", -3308225);
        map.put("pink", -16181);
        map.put("plum", -2252579);
        map.put("powderblue", -5185306);
        map.put("purple", -8388480);
        map.put("rebeccapurple", -10079335);
        map.put("red", -65536);
        map.put("rosybrown", -4419697);
        map.put("royalblue", -12490271);
        map.put("saddlebrown", -7650029);
        map.put("salmon", -360334);
        map.put("sandybrown", -744352);
        map.put("seagreen", -13726889);
        map.put("seashell", -2578);
        map.put("sienna", -6270419);
        map.put("silver", -4144960);
        map.put("skyblue", -7876885);
        map.put("slateblue", -9807155);
        map.put("slategray", -9404272);
        map.put("slategrey", -9404272);
        map.put("snow", -1286);
        map.put("springgreen", -16711809);
        map.put("steelblue", -12156236);
        map.put("tan", -2968436);
        map.put("teal", -16744320);
        map.put("thistle", -2572328);
        map.put("tomato", -40121);
        map.put("turquoise", -12525360);
        map.put("violet", -1146130);
        map.put("wheat", -663885);
        map.put("white", -1);
        map.put("whitesmoke", -657931);
        map.put("yellow", -256);
        map.put("yellowgreen", -6632142);
        map.put("transparent", 0);
    }

    public static int getColor(String str) {
        return getColor(str, Integer.MIN_VALUE);
    }

    public static Shader getShader(String str, float f, float f2) {
        List<String> gradientValues = parseGradientValues(str);
        if (gradientValues == null || gradientValues.size() != 3) {
            return null;
        }
        float[] gradientDirection = parseGradientDirection(gradientValues.get(0), f, f2);
        return new LinearGradient(gradientDirection[0], gradientDirection[1], gradientDirection[2], gradientDirection[3], getColor(gradientValues.get(1), -1), getColor(gradientValues.get(2), -1), Shader.TileMode.CLAMP);
    }

    public static boolean isNamedColor(String str) {
        return colorMap.containsKey(str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static float[] parseGradientDirection(java.lang.String r8, float r9, float r10) {
        /*
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
            boolean r3 = android.text.TextUtils.isEmpty(r8)
            if (r3 != 0) goto L24
            java.lang.String r3 = "\\s*"
            java.lang.String r7 = ""
            java.lang.String r8 = r8.replaceAll(r3, r7)
            java.util.Locale r3 = java.util.Locale.ENGLISH
            java.lang.String r8 = r8.toLowerCase(r3)
        L24:
            r8.getClass()
            r8.hashCode()
            int r3 = r8.hashCode()
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
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L3e
            goto L32
        L3e:
            r0 = 5
            goto L79
        L40:
            java.lang.String r3 = "totop"
            boolean r8 = r8.equals(r3)
            if (r8 != 0) goto L79
            goto L32
        L4a:
            java.lang.String r0 = "totopleft"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L54
            goto L32
        L54:
            r0 = 3
            goto L79
        L56:
            java.lang.String r0 = "toleft"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L60
            goto L32
        L60:
            r0 = 2
            goto L79
        L62:
            java.lang.String r0 = "toright"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L6c
            goto L32
        L6c:
            r0 = 1
            goto L79
        L6e:
            java.lang.String r0 = "tobottom"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L78
            goto L32
        L78:
            r0 = 0
        L79:
            switch(r0) {
                case 0: goto L90;
                case 1: goto L8d;
                case 2: goto L8a;
                case 3: goto L85;
                case 4: goto L82;
                case 5: goto L7d;
                default: goto L7c;
            }
        L7c:
            return r1
        L7d:
            r1[r5] = r9
            r1[r6] = r10
            return r1
        L82:
            r1[r4] = r10
            return r1
        L85:
            r1[r2] = r9
            r1[r4] = r10
            return r1
        L8a:
            r1[r2] = r9
            return r1
        L8d:
            r1[r5] = r9
            return r1
        L90:
            r1[r6] = r10
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.utils.WXResourceUtils.parseGradientDirection(java.lang.String, float, float):float[]");
    }

    private static List<String> parseGradientValues(String str) {
        String strNextToken;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        str.getClass();
        if (!str.startsWith("linear-gradient")) {
            return null;
        }
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

    public static int getColor(String str, int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Pair<Boolean, Integer> pairHandle;
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        String strTrim = str.trim();
        Integer num = WXUtils.sCache.get(strTrim);
        if (num != null) {
            return num.intValue();
        }
        for (ColorConvertHandler colorConvertHandler : ColorConvertHandler.values()) {
            try {
                pairHandle = colorConvertHandler.handle(strTrim);
            } catch (RuntimeException e) {
                WXLogUtils.v("Color_Parser", WXLogUtils.getStackTrace(e));
            }
            if (((Boolean) pairHandle.first).booleanValue()) {
                Integer num2 = (Integer) pairHandle.second;
                i = num2.intValue();
                WXUtils.sCache.put(strTrim, num2);
                break;
            }
            continue;
        }
        return i;
    }
}

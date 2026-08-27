package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import com.taobao.weex.el.parse.Operators;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses;
    private String[] autoTypeAccept;
    private boolean autoTypeEnable;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    protected transient BeanContext lastBeanContext;
    public final JSONLexer lexer;
    private int objectKeyLevel;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    static {
        HashSet hashSet = new HashSet();
        primitiveClasses = hashSet;
        hashSet.addAll(Arrays.asList(Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class));
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat = simpleDateFormat;
            simpleDateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        setDateFormat(dateFormat);
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DefaultJSONParser(String str) {
        this(str, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig) {
        this(str, new JSONScanner(str, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig, int i) {
        this(str, new JSONScanner(str, i), parserConfig);
    }

    public DefaultJSONParser(char[] cArr, int i, ParserConfig parserConfig, int i2) {
        this(cArr, new JSONScanner(cArr, i, i2), parserConfig);
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this((Object) null, jSONLexer, parserConfig);
    }

    public DefaultJSONParser(Object obj, JSONLexer jSONLexer, ParserConfig parserConfig) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.objectKeyLevel = 0;
        this.autoTypeAccept = null;
        this.lexer = jSONLexer;
        this.input = obj;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char current = jSONLexer.getCurrent();
        if (current == '{') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 12;
        } else if (current == '[') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 14;
        } else {
            jSONLexer.nextToken();
        }
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public String getInput() {
        Object obj = this.input;
        if (obj instanceof char[]) {
            return new String((char[]) this.input);
        }
        return obj.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:147:0x0299, code lost:
    
        r4.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02a4, code lost:
    
        if (r4.token() != 13) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02a6, code lost:
    
        r4.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02b1, code lost:
    
        if ((r17.config.getDeserializer(r7) instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02b3, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r18, (java.lang.Class<java.lang.Object>) r7, r17.config);
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02ba, code lost:
    
        r0 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02bb, code lost:
    
        if (r0 != null) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02bf, code lost:
    
        if (r7 != java.lang.Cloneable.class) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02c1, code lost:
    
        r0 = new java.util.HashMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02cd, code lost:
    
        if ("java.util.Collections$EmptyMap".equals(r6) == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02cf, code lost:
    
        r0 = java.util.Collections.EMPTY_MAP;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02d8, code lost:
    
        if ("java.util.Collections$UnmodifiableMap".equals(r6) == false) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x02da, code lost:
    
        r0 = java.util.Collections.unmodifiableMap(new java.util.HashMap());
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x02e4, code lost:
    
        r0 = r7.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x02eb, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x02ec, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x02f4, code lost:
    
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x02f5, code lost:
    
        setResolveStatus(2);
        r3 = r17.context;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x02fb, code lost:
    
        if (r3 == null) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x02fd, code lost:
    
        if (r19 == null) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0301, code lost:
    
        if ((r19 instanceof java.lang.Integer) != false) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0307, code lost:
    
        if ((r3.fieldName instanceof java.lang.Integer) != false) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0309, code lost:
    
        popContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0310, code lost:
    
        if (r18.size() <= 0) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0312, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r18, (java.lang.Class<java.lang.Object>) r7, r17.config);
        setResolveStatus(0);
        parseObject(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0322, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0323, code lost:
    
        r0 = r17.config.getDeserializer(r7);
        r3 = r0.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0333, code lost:
    
        if (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class.isAssignableFrom(r3) == false) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0337, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x033b, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x033d, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x0344, code lost:
    
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer) == false) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0346, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0351, code lost:
    
        return r0.deserialze(r17, r7, r19);
     */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0214 A[Catch: all -> 0x06a3, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0293 A[Catch: all -> 0x06a3, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x045f A[Catch: all -> 0x06a3, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0488 A[Catch: all -> 0x06a3, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x04e4 A[Catch: all -> 0x06a3, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:347:0x05e5  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x05e9 A[Catch: all -> 0x06a3, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:354:0x05f5 A[Catch: all -> 0x06a3, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0601 A[Catch: all -> 0x06a3, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:363:0x0616 A[Catch: all -> 0x06a3, TRY_ENTER, TryCatch #0 {all -> 0x06a3, blocks: (B:24:0x0070, B:26:0x0074, B:29:0x007e, B:32:0x0091, B:36:0x00aa, B:115:0x0214, B:116:0x021a, B:118:0x0225, B:120:0x022d, B:124:0x0242, B:126:0x0250, B:146:0x0293, B:147:0x0299, B:149:0x02a6, B:150:0x02a9, B:152:0x02b3, B:157:0x02c1, B:158:0x02c7, B:160:0x02cf, B:161:0x02d2, B:163:0x02da, B:164:0x02e4, B:168:0x02ed, B:169:0x02f4, B:170:0x02f5, B:173:0x02ff, B:175:0x0303, B:177:0x0309, B:178:0x030c, B:180:0x0312, B:183:0x0323, B:189:0x033d, B:193:0x034a, B:190:0x0342, B:192:0x0346, B:127:0x0255, B:131:0x0261, B:135:0x026d, B:137:0x0273, B:142:0x0280, B:143:0x0283, B:200:0x035b, B:202:0x0361, B:204:0x0369, B:206:0x0373, B:208:0x0384, B:210:0x038f, B:212:0x0397, B:214:0x039b, B:216:0x03a1, B:219:0x03a6, B:221:0x03aa, B:244:0x0410, B:246:0x0418, B:249:0x0421, B:250:0x043c, B:223:0x03b1, B:225:0x03b9, B:227:0x03bd, B:228:0x03c0, B:229:0x03cc, B:232:0x03d5, B:234:0x03d9, B:235:0x03dc, B:237:0x03e0, B:238:0x03e4, B:239:0x03f0, B:241:0x03fa, B:243:0x0407, B:251:0x043d, B:252:0x045b, B:255:0x045f, B:257:0x0463, B:259:0x0467, B:261:0x046d, B:262:0x0470, B:266:0x0478, B:272:0x0488, B:274:0x0497, B:276:0x04a2, B:277:0x04aa, B:278:0x04ad, B:290:0x04d9, B:292:0x04e4, B:296:0x04f1, B:299:0x0501, B:300:0x0522, B:285:0x04bd, B:287:0x04c7, B:289:0x04d6, B:288:0x04cc, B:303:0x0527, B:305:0x0531, B:307:0x0539, B:308:0x053c, B:310:0x0547, B:311:0x054b, B:313:0x0556, B:316:0x055d, B:319:0x0566, B:320:0x056b, B:323:0x0570, B:325:0x0575, B:329:0x0580, B:331:0x0588, B:333:0x059d, B:337:0x05bc, B:339:0x05c4, B:342:0x05ca, B:344:0x05d0, B:346:0x05d8, B:349:0x05e9, B:352:0x05f1, B:354:0x05f5, B:355:0x05fc, B:357:0x0601, B:358:0x0604, B:360:0x060c, B:363:0x0616, B:366:0x0620, B:367:0x0625, B:368:0x062a, B:369:0x0645, B:334:0x05a8, B:335:0x05af, B:370:0x0646, B:372:0x0658, B:375:0x065f, B:378:0x066d, B:379:0x068e, B:39:0x00bc, B:40:0x00da, B:43:0x00df, B:45:0x00ea, B:47:0x00ee, B:49:0x00f2, B:51:0x00f8, B:52:0x00fb, B:59:0x010a, B:61:0x0112, B:64:0x0122, B:65:0x013a, B:66:0x013b, B:67:0x0140, B:78:0x0155, B:79:0x015b, B:81:0x0162, B:83:0x016b, B:90:0x017d, B:93:0x0185, B:94:0x019d, B:88:0x0178, B:82:0x0167, B:95:0x019e, B:96:0x01b6, B:102:0x01c0, B:104:0x01c8, B:107:0x01d9, B:108:0x01f9, B:109:0x01fa, B:110:0x01ff, B:111:0x0200, B:113:0x020a, B:380:0x068f, B:381:0x0696, B:382:0x0697, B:383:0x069c, B:384:0x069d, B:385:0x06a2), top: B:389:0x0070, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:403:0x0299 A[EDGE_INSN: B:403:0x0299->B:147:0x0299 BREAK  A[LOOP:0: B:29:0x007e->B:419:0x007e], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:410:0x04ed A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x060c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object parseObject(java.util.Map r18, java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 1704
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public void setConfig(ParserConfig parserConfig) {
        this.config = parserConfig;
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T parseObject(Type type, Object obj) {
        int i = this.lexer.token();
        if (i == 8) {
            this.lexer.nextToken();
            return (T) TypeUtils.optionalEmpty(type);
        }
        if (i == 4) {
            if (type == byte[].class) {
                T t = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t;
            }
            if (type == char[].class) {
                String strStringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) strStringVal.toCharArray();
            }
        }
        ObjectDeserializer deserializer = this.config.getDeserializer(type);
        try {
            if (deserializer.getClass() == JavaBeanDeserializer.class) {
                if (this.lexer.token() != 12 && this.lexer.token() != 14) {
                    throw new JSONException("syntax error,expect start with { or [,but actually start with " + this.lexer.tokenName());
                }
                return (T) ((JavaBeanDeserializer) deserializer).deserialze(this, type, obj, 0);
            }
            return (T) deserializer.deserialze(this, type, obj);
        } catch (JSONException e) {
            throw e;
        } catch (Throwable th) {
            throw new JSONException(th.getMessage(), th);
        }
    }

    public <T> List<T> parseArray(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        parseArray((Class<?>) cls, (Collection) arrayList);
        return arrayList;
    }

    public void parseArray(Class<?> cls, Collection collection) {
        parseArray((Type) cls, collection);
    }

    public void parseArray(Type type, Collection collection) {
        parseArray(type, collection, null);
    }

    public void parseArray(Type type, Collection collection, Object obj) {
        ObjectDeserializer deserializer;
        int i = this.lexer.token();
        if (i == 21 || i == 22) {
            this.lexer.nextToken();
            i = this.lexer.token();
        }
        if (i != 14) {
            throw new JSONException("field " + obj + " expect '[', but " + JSONToken.name(i) + ", " + this.lexer.info());
        }
        if (Integer.TYPE == type) {
            deserializer = IntegerCodec.instance;
            this.lexer.nextToken(2);
        } else if (String.class == type) {
            deserializer = StringCodec.instance;
            this.lexer.nextToken(4);
        } else {
            deserializer = this.config.getDeserializer(type);
            this.lexer.nextToken(deserializer.getFastMatchToken());
        }
        ParseContext parseContext = this.context;
        setContext(collection, obj);
        int i2 = 0;
        while (true) {
            try {
                if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (this.lexer.token() == 16) {
                        this.lexer.nextToken();
                    }
                }
                if (this.lexer.token() != 15) {
                    Object objDeserialze = null;
                    if (Integer.TYPE == type) {
                        collection.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            objDeserialze = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj2 = parse();
                            if (obj2 != null) {
                                objDeserialze = obj2.toString();
                            }
                        }
                        collection.add(objDeserialze);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                        } else {
                            objDeserialze = deserializer.deserialze(this, type, Integer.valueOf(i2));
                        }
                        collection.add(objDeserialze);
                        checkListResolve(collection);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i2++;
                } else {
                    setContext(parseContext);
                    this.lexer.nextToken(16);
                    return;
                }
            } catch (Throwable th) {
                setContext(parseContext);
                throw th;
            }
        }
    }

    public Object[] parseArray(Type[] typeArr) {
        Object objCast;
        Class<?> componentType;
        boolean zIsArray;
        Class cls;
        int i = 8;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        int i2 = 14;
        if (this.lexer.token() != 14) {
            throw new JSONException("syntax error : " + this.lexer.tokenName());
        }
        Object[] objArr = new Object[typeArr.length];
        if (typeArr.length == 0) {
            this.lexer.nextToken(15);
            if (this.lexer.token() != 15) {
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(16);
            return new Object[0];
        }
        this.lexer.nextToken(2);
        int i3 = 0;
        while (i3 < typeArr.length) {
            if (this.lexer.token() == i) {
                this.lexer.nextToken(16);
                objCast = null;
            } else {
                Type type = typeArr[i3];
                if (type == Integer.TYPE || type == Integer.class) {
                    if (this.lexer.token() == 2) {
                        objCast = Integer.valueOf(this.lexer.intValue());
                        this.lexer.nextToken(16);
                    } else {
                        objCast = TypeUtils.cast(parse(), type, this.config);
                    }
                } else if (type == String.class) {
                    if (this.lexer.token() == 4) {
                        objCast = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                    } else {
                        objCast = TypeUtils.cast(parse(), type, this.config);
                    }
                } else {
                    if (i3 == typeArr.length - 1 && (type instanceof Class) && (((cls = (Class) type) != byte[].class && cls != char[].class) || this.lexer.token() != 4)) {
                        zIsArray = cls.isArray();
                        componentType = cls.getComponentType();
                    } else {
                        componentType = null;
                        zIsArray = false;
                    }
                    if (zIsArray && this.lexer.token() != i2) {
                        ArrayList arrayList = new ArrayList();
                        ObjectDeserializer deserializer = this.config.getDeserializer(componentType);
                        int fastMatchToken = deserializer.getFastMatchToken();
                        if (this.lexer.token() != 15) {
                            while (true) {
                                arrayList.add(deserializer.deserialze(this, type, null));
                                if (this.lexer.token() != 16) {
                                    break;
                                }
                                this.lexer.nextToken(fastMatchToken);
                            }
                            if (this.lexer.token() != 15) {
                                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                            }
                        }
                        objCast = TypeUtils.cast(arrayList, type, this.config);
                    } else {
                        objCast = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i3));
                    }
                }
            }
            objArr[i3] = objCast;
            if (this.lexer.token() == 15) {
                break;
            }
            if (this.lexer.token() != 16) {
                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
            }
            if (i3 == typeArr.length - 1) {
                this.lexer.nextToken(15);
            } else {
                this.lexer.nextToken(2);
            }
            i3++;
            i = 8;
            i2 = 14;
        }
        if (this.lexer.token() != 15) {
            throw new JSONException("syntax error");
        }
        this.lexer.nextToken(16);
        return objArr;
    }

    public void parseObject(Object obj) {
        Object objDeserialze;
        Class<?> cls = obj.getClass();
        ObjectDeserializer deserializer = this.config.getDeserializer(cls);
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (this.lexer.token() != 12 && this.lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
        while (true) {
            String strScanSymbol = this.lexer.scanSymbol(this.symbolTable);
            if (strScanSymbol == null) {
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                } else if (this.lexer.token() != 16 || !this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            FieldDeserializer fieldDeserializer = javaBeanDeserializer != null ? javaBeanDeserializer.getFieldDeserializer(strScanSymbol) : null;
            if (fieldDeserializer == null) {
                if (!this.lexer.isEnabled(Feature.IgnoreNotMatch)) {
                    throw new JSONException("setter not found, class " + cls.getName() + ", property " + strScanSymbol);
                }
                this.lexer.nextTokenWithColon();
                parse();
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken();
                    return;
                }
            } else {
                Class<?> cls2 = fieldDeserializer.fieldInfo.fieldClass;
                Type type = fieldDeserializer.fieldInfo.fieldType;
                if (cls2 == Integer.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = IntegerCodec.instance.deserialze(this, type, null);
                } else if (cls2 == String.class) {
                    this.lexer.nextTokenWithColon(4);
                    objDeserialze = StringCodec.deserialze(this);
                } else if (cls2 == Long.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = LongCodec.instance.deserialze(this, type, null);
                } else {
                    ObjectDeserializer deserializer2 = this.config.getDeserializer(cls2, type);
                    this.lexer.nextTokenWithColon(deserializer2.getFastMatchToken());
                    objDeserialze = deserializer2.deserialze(this, type, null);
                }
                fieldDeserializer.setValue(obj, objDeserialze);
                if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                }
            }
        }
    }

    public Object parseArrayWithType(Type type) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new JSONException("not support type " + type);
        }
        Type type2 = actualTypeArguments[0];
        if (type2 instanceof Class) {
            ArrayList arrayList = new ArrayList();
            parseArray((Class<?>) type2, (Collection) arrayList);
            return arrayList;
        }
        if (type2 instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type2;
            Type type3 = wildcardType.getUpperBounds()[0];
            if (Object.class.equals(type3)) {
                if (wildcardType.getLowerBounds().length == 0) {
                    return parse();
                }
                throw new JSONException("not support type : " + type);
            }
            ArrayList arrayList2 = new ArrayList();
            parseArray((Class<?>) type3, (Collection) arrayList2);
            return arrayList2;
        }
        if (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length != 1) {
                throw new JSONException("not support : " + typeVariable);
            }
            Type type4 = bounds[0];
            if (type4 instanceof Class) {
                ArrayList arrayList3 = new ArrayList();
                parseArray((Class<?>) type4, (Collection) arrayList3);
                return arrayList3;
            }
        }
        if (type2 instanceof ParameterizedType) {
            ArrayList arrayList4 = new ArrayList();
            parseArray((ParameterizedType) type2, arrayList4);
            return arrayList4;
        }
        throw new JSONException("TODO : " + type);
    }

    public void acceptType(String str) {
        JSONLexer jSONLexer = this.lexer;
        jSONLexer.nextTokenWithColon();
        if (jSONLexer.token() != 4) {
            throw new JSONException("type not match error");
        }
        if (str.equals(jSONLexer.stringVal())) {
            jSONLexer.nextToken();
            if (jSONLexer.token() == 16) {
                jSONLexer.nextToken();
                return;
            }
            return;
        }
        throw new JSONException("type not match error");
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public void setResolveStatus(int i) {
        this.resolveStatus = i;
    }

    public Object getObject(String str) {
        for (int i = 0; i < this.contextArrayIndex; i++) {
            if (str.equals(this.contextArray[i].toString())) {
                return this.contextArray[i].object;
            }
        }
        return null;
    }

    public void checkListResolve(Collection collection) {
        if (this.resolveStatus == 1) {
            if (collection instanceof List) {
                int size = collection.size() - 1;
                ResolveTask lastResolveTask = getLastResolveTask();
                lastResolveTask.fieldDeserializer = new ResolveFieldDeserializer(this, (List) collection, size);
                lastResolveTask.ownerContext = this.context;
                setResolveStatus(0);
                return;
            }
            ResolveTask lastResolveTask2 = getLastResolveTask();
            lastResolveTask2.fieldDeserializer = new ResolveFieldDeserializer(collection);
            lastResolveTask2.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public void checkMapResolve(Map map, Object obj) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer resolveFieldDeserializer = new ResolveFieldDeserializer(map, obj);
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = resolveFieldDeserializer;
            lastResolveTask.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public Object parseObject(Map map) {
        return parseObject(map, (Object) null);
    }

    public JSONObject parseObject() {
        Object object = parseObject((Map) new JSONObject(this.lexer.isEnabled(Feature.OrderedField)));
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        if (object == null) {
            return null;
        }
        return new JSONObject((Map<String, Object>) object);
    }

    public final void parseArray(Collection collection) {
        parseArray(collection, (Object) null);
    }

    public final void parseArray(Collection collection, Object obj) {
        Object object;
        Number numberDecimalValue;
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == 21 || jSONLexer.token() == 22) {
            jSONLexer.nextToken();
        }
        if (jSONLexer.token() != 14) {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(jSONLexer.token()) + ", pos " + jSONLexer.pos() + ", fieldName " + obj);
        }
        jSONLexer.nextToken(4);
        ParseContext parseContext = this.context;
        if (parseContext != null && parseContext.level > 512) {
            throw new JSONException("array level > 512");
        }
        ParseContext parseContext2 = this.context;
        setContext(collection, obj);
        int i = 0;
        while (true) {
            try {
                try {
                    if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (jSONLexer.token() == 16) {
                            jSONLexer.nextToken();
                        }
                    }
                    int i2 = jSONLexer.token();
                    if (i2 == 2) {
                        Number numberIntegerValue = jSONLexer.integerValue();
                        jSONLexer.nextToken(16);
                        object = numberIntegerValue;
                    } else if (i2 == 3) {
                        if (jSONLexer.isEnabled(Feature.UseBigDecimal)) {
                            numberDecimalValue = jSONLexer.decimalValue(true);
                        } else {
                            numberDecimalValue = jSONLexer.decimalValue(false);
                        }
                        object = numberDecimalValue;
                        jSONLexer.nextToken(16);
                    } else if (i2 == 4) {
                        String strStringVal = jSONLexer.stringVal();
                        jSONLexer.nextToken(16);
                        object = strStringVal;
                        if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                            JSONScanner jSONScanner = new JSONScanner(strStringVal);
                            Object time = strStringVal;
                            if (jSONScanner.scanISO8601DateIfMatch()) {
                                time = jSONScanner.getCalendar().getTime();
                            }
                            jSONScanner.close();
                            object = time;
                        }
                    } else if (i2 == 6) {
                        Boolean bool = Boolean.TRUE;
                        jSONLexer.nextToken(16);
                        object = bool;
                    } else if (i2 != 7) {
                        object = null;
                        object = null;
                        if (i2 == 8) {
                            jSONLexer.nextToken(4);
                        } else if (i2 == 12) {
                            object = parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), Integer.valueOf(i));
                        } else {
                            if (i2 == 20) {
                                throw new JSONException("unclosed jsonArray");
                            }
                            if (i2 == 23) {
                                jSONLexer.nextToken(4);
                            } else if (i2 == 14) {
                                JSONArray jSONArray = new JSONArray();
                                parseArray(jSONArray, Integer.valueOf(i));
                                object = jSONArray;
                                if (jSONLexer.isEnabled(Feature.UseObjectArray)) {
                                    object = jSONArray.toArray();
                                }
                            } else {
                                if (i2 == 15) {
                                    jSONLexer.nextToken(16);
                                    return;
                                }
                                object = parse();
                            }
                        }
                    } else {
                        Boolean bool2 = Boolean.FALSE;
                        jSONLexer.nextToken(16);
                        object = bool2;
                    }
                    collection.add(object);
                    checkListResolve(collection);
                    if (jSONLexer.token() == 16) {
                        jSONLexer.nextToken(4);
                    }
                    i++;
                } catch (ClassCastException e) {
                    throw new JSONException("unkown error", e);
                }
            } finally {
                setContext(parseContext2);
            }
        }
    }

    public ParseContext getContext() {
        return this.context;
    }

    public ParseContext getOwnerContext() {
        return this.context.parent;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(r0.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setContext(ParseContext parseContext) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = parseContext;
    }

    public void popContext() {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = this.context.parent;
        int i = this.contextArrayIndex;
        if (i <= 0) {
            return;
        }
        int i2 = i - 1;
        this.contextArrayIndex = i2;
        this.contextArray[i2] = null;
    }

    public ParseContext setContext(Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, obj, obj2);
    }

    public ParseContext setContext(ParseContext parseContext, Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        ParseContext parseContext2 = new ParseContext(parseContext, obj, obj2);
        this.context = parseContext2;
        addContext(parseContext2);
        return this.context;
    }

    private void addContext(ParseContext parseContext) {
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        ParseContext[] parseContextArr = this.contextArray;
        if (parseContextArr == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= parseContextArr.length) {
            ParseContext[] parseContextArr2 = new ParseContext[(parseContextArr.length * 3) / 2];
            System.arraycopy(parseContextArr, 0, parseContextArr2, 0, parseContextArr.length);
            this.contextArray = parseContextArr2;
        }
        this.contextArray[i] = parseContext;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parseKey() {
        if (this.lexer.token() == 18) {
            String strStringVal = this.lexer.stringVal();
            this.lexer.nextToken(16);
            return strStringVal;
        }
        return parse(null);
    }

    public Object parse(Object obj) {
        Map jSONObject;
        JSONLexer jSONLexer = this.lexer;
        int i = jSONLexer.token();
        if (i == 2) {
            Number numberIntegerValue = jSONLexer.integerValue();
            jSONLexer.nextToken();
            return numberIntegerValue;
        }
        if (i == 3) {
            Number numberDecimalValue = jSONLexer.decimalValue(jSONLexer.isEnabled(Feature.UseBigDecimal));
            jSONLexer.nextToken();
            return numberDecimalValue;
        }
        if (i == 4) {
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            if (!jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                return strStringVal;
            }
            JSONScanner jSONScanner = new JSONScanner(strStringVal);
            try {
                return jSONScanner.scanISO8601DateIfMatch() ? jSONScanner.getCalendar().getTime() : strStringVal;
            } finally {
                jSONScanner.close();
            }
        }
        if (i == 12) {
            if (isEnabled(Feature.UseNativeJavaObject)) {
                jSONObject = jSONLexer.isEnabled(Feature.OrderedField) ? new HashMap() : new LinkedHashMap();
            } else {
                jSONObject = new JSONObject(jSONLexer.isEnabled(Feature.OrderedField));
            }
            return parseObject(jSONObject, obj);
        }
        if (i == 14) {
            Collection arrayList = isEnabled(Feature.UseNativeJavaObject) ? new ArrayList() : new JSONArray();
            parseArray(arrayList, obj);
            return jSONLexer.isEnabled(Feature.UseObjectArray) ? arrayList.toArray() : arrayList;
        }
        if (i == 18) {
            if ("NaN".equals(jSONLexer.stringVal())) {
                jSONLexer.nextToken();
                return null;
            }
            throw new JSONException("syntax error, " + jSONLexer.info());
        }
        if (i != 26) {
            switch (i) {
                case 6:
                    jSONLexer.nextToken();
                    return Boolean.TRUE;
                case 7:
                    jSONLexer.nextToken();
                    return Boolean.FALSE;
                case 8:
                    jSONLexer.nextToken();
                    return null;
                case 9:
                    jSONLexer.nextToken(18);
                    if (jSONLexer.token() != 18) {
                        throw new JSONException("syntax error");
                    }
                    jSONLexer.nextToken(10);
                    accept(10);
                    long jLongValue = jSONLexer.integerValue().longValue();
                    accept(2);
                    accept(11);
                    return new Date(jLongValue);
                default:
                    switch (i) {
                        case 20:
                            if (jSONLexer.isBlankInput()) {
                                return null;
                            }
                            throw new JSONException("unterminated json string, " + jSONLexer.info());
                        case 21:
                            jSONLexer.nextToken();
                            HashSet hashSet = new HashSet();
                            parseArray(hashSet, obj);
                            return hashSet;
                        case 22:
                            jSONLexer.nextToken();
                            TreeSet treeSet = new TreeSet();
                            parseArray(treeSet, obj);
                            return treeSet;
                        case 23:
                            jSONLexer.nextToken();
                            return null;
                        default:
                            throw new JSONException("syntax error, " + jSONLexer.info());
                    }
            }
        }
        byte[] bArrBytesValue = jSONLexer.bytesValue();
        jSONLexer.nextToken();
        return bArrBytesValue;
    }

    public void config(Feature feature, boolean z) {
        this.lexer.config(feature, z);
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public final void accept(int i) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i) {
            jSONLexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(jSONLexer.token()));
    }

    public final void accept(int i, int i2) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i) {
            jSONLexer.nextToken(i2);
        } else {
            throwException(i);
        }
    }

    public void throwException(int i) {
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(this.lexer.token()));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer jSONLexer = this.lexer;
        try {
            if (jSONLexer.isEnabled(Feature.AutoCloseSource) && jSONLexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(jSONLexer.token()));
            }
        } finally {
            jSONLexer.close();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object resolveReference(java.lang.String r5) {
        /*
            r4 = this;
            com.alibaba.fastjson.parser.ParseContext[] r0 = r4.contextArray
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            r0 = 0
        L7:
            com.alibaba.fastjson.parser.ParseContext[] r2 = r4.contextArray
            int r3 = r2.length
            if (r0 >= r3) goto L22
            int r3 = r4.contextArrayIndex
            if (r0 >= r3) goto L22
            r2 = r2[r0]
            java.lang.String r3 = r2.toString()
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L1f
            java.lang.Object r5 = r2.object
            return r5
        L1f:
            int r0 = r0 + 1
            goto L7
        L22:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.resolveReference(java.lang.String):java.lang.Object");
    }

    public void handleResovleTask(Object obj) {
        Object objEval;
        List<ResolveTask> list = this.resolveTaskList;
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ResolveTask resolveTask = this.resolveTaskList.get(i);
            String str = resolveTask.referenceValue;
            Object obj2 = resolveTask.ownerContext != null ? resolveTask.ownerContext.object : null;
            if (str.startsWith(Operators.DOLLAR_STR)) {
                objEval = getObject(str);
                if (objEval == null) {
                    try {
                        JSONPath jSONPath = new JSONPath(str, SerializeConfig.getGlobalInstance(), this.config, true);
                        if (jSONPath.isRef()) {
                            objEval = jSONPath.eval(obj);
                        }
                    } catch (JSONPathException unused) {
                    }
                }
            } else {
                objEval = resolveTask.context.object;
            }
            FieldDeserializer fieldDeserializer = resolveTask.fieldDeserializer;
            if (fieldDeserializer != null) {
                if (objEval != null && objEval.getClass() == JSONObject.class && fieldDeserializer.fieldInfo != null && !Map.class.isAssignableFrom(fieldDeserializer.fieldInfo.fieldClass)) {
                    Object obj3 = this.contextArray[0].object;
                    JSONPath jSONPathCompile = JSONPath.compile(str);
                    if (jSONPathCompile.isRef()) {
                        objEval = jSONPathCompile.eval(obj3);
                    }
                }
                if (fieldDeserializer.getOwnerClass() != null && !fieldDeserializer.getOwnerClass().isInstance(obj2) && resolveTask.ownerContext.parent != null) {
                    ParseContext parseContext = resolveTask.ownerContext;
                    while (true) {
                        parseContext = parseContext.parent;
                        if (parseContext != null) {
                            if (fieldDeserializer.getOwnerClass().isInstance(parseContext.object)) {
                                obj2 = parseContext.object;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                fieldDeserializer.setValue(obj2, objEval);
            }
        }
    }

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext parseContext, String str) {
            this.context = parseContext;
            this.referenceValue = str;
        }
    }

    public void parseExtra(Object obj, String str) {
        Object object;
        this.lexer.nextTokenWithColon();
        List<ExtraTypeProvider> list = this.extraTypeProviders;
        Type extraType = null;
        if (list != null) {
            Iterator<ExtraTypeProvider> it = list.iterator();
            while (it.hasNext()) {
                extraType = it.next().getExtraType(obj, str);
            }
        }
        if (extraType == null) {
            object = parse();
        } else {
            object = parseObject(extraType);
        }
        if (obj instanceof ExtraProcessable) {
            ((ExtraProcessable) obj).processExtra(str, object);
            return;
        }
        List<ExtraProcessor> list2 = this.extraProcessors;
        if (list2 != null) {
            Iterator<ExtraProcessor> it2 = list2.iterator();
            while (it2.hasNext()) {
                it2.next().processExtra(obj, str, object);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x0235, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable r11, java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 612
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable, java.lang.Object):java.lang.Object");
    }
}

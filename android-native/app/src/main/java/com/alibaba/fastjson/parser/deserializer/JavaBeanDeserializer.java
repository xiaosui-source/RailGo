package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.taobao.weex.el.parse.Operators;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    private final ParserConfig.AutoTypeCheckHandler autoTypeCheckHandler;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private Map<String, FieldDeserializer> fieldDeserializerMap;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] hashArray;
    private transient short[] hashArrayMapping;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls) {
        this(parserConfig, cls, cls);
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type) {
        this(parserConfig, JavaBeanInfo.build(cls, type, parserConfig.propertyNamingStrategy, parserConfig.fieldBased, parserConfig.compatibleWithJavaBean, parserConfig.isJacksonCompatible()));
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) throws IllegalAccessException, InstantiationException {
        ParserConfig.AutoTypeCheckHandler autoTypeCheckHandlerNewInstance;
        this.clazz = javaBeanInfo.clazz;
        this.beanInfo = javaBeanInfo;
        HashMap map = null;
        if (javaBeanInfo.jsonType == null || javaBeanInfo.jsonType.autoTypeCheckHandler() == ParserConfig.AutoTypeCheckHandler.class) {
            autoTypeCheckHandlerNewInstance = null;
        } else {
            try {
                autoTypeCheckHandlerNewInstance = javaBeanInfo.jsonType.autoTypeCheckHandler().newInstance();
            } catch (Exception unused) {
            }
        }
        this.autoTypeCheckHandler = autoTypeCheckHandlerNewInstance;
        this.sortedFieldDeserializers = new FieldDeserializer[javaBeanInfo.sortedFields.length];
        int length = javaBeanInfo.sortedFields.length;
        for (int i = 0; i < length; i++) {
            FieldInfo fieldInfo = javaBeanInfo.sortedFields[i];
            FieldDeserializer fieldDeserializerCreateFieldDeserializer = parserConfig.createFieldDeserializer(parserConfig, javaBeanInfo, fieldInfo);
            this.sortedFieldDeserializers[i] = fieldDeserializerCreateFieldDeserializer;
            if (length > 128) {
                if (this.fieldDeserializerMap == null) {
                    this.fieldDeserializerMap = new HashMap();
                }
                this.fieldDeserializerMap.put(fieldInfo.name, fieldDeserializerCreateFieldDeserializer);
            }
            for (String str : fieldInfo.alternateNames) {
                if (map == null) {
                    map = new HashMap();
                }
                map.put(str, fieldDeserializerCreateFieldDeserializer);
            }
        }
        this.alterNameFieldDeserializers = map;
        this.fieldDeserializers = new FieldDeserializer[javaBeanInfo.fields.length];
        int length2 = javaBeanInfo.fields.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(javaBeanInfo.fields[i2].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(String str) {
        return getFieldDeserializer(str, null);
    }

    public FieldDeserializer getFieldDeserializer(String str, int[] iArr) {
        FieldDeserializer fieldDeserializer;
        if (str == null) {
            return null;
        }
        Map<String, FieldDeserializer> map = this.fieldDeserializerMap;
        if (map != null && (fieldDeserializer = map.get(str)) != null) {
            return fieldDeserializer;
        }
        int length = this.sortedFieldDeserializers.length - 1;
        int i = 0;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            int iCompareTo = this.sortedFieldDeserializers[i2].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i = i2 + 1;
            } else {
                if (iCompareTo <= 0) {
                    if (isSetFlag(i2, iArr)) {
                        return null;
                    }
                    return this.sortedFieldDeserializers[i2];
                }
                length = i2 - 1;
            }
        }
        Map<String, FieldDeserializer> map2 = this.alterNameFieldDeserializers;
        if (map2 != null) {
            return map2.get(str);
        }
        return null;
    }

    public FieldDeserializer getFieldDeserializer(long j) {
        int i = 0;
        if (this.hashArray == null) {
            long[] jArr = new long[this.sortedFieldDeserializers.length];
            int i2 = 0;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                if (i2 >= fieldDeserializerArr.length) {
                    break;
                }
                jArr[i2] = TypeUtils.fnv1a_64(fieldDeserializerArr[i2].fieldInfo.name);
                i2++;
            }
            Arrays.sort(jArr);
            this.hashArray = jArr;
        }
        int iBinarySearch = Arrays.binarySearch(this.hashArray, j);
        if (iBinarySearch < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            while (true) {
                FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                if (i >= fieldDeserializerArr2.length) {
                    break;
                }
                int iBinarySearch2 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(fieldDeserializerArr2[i].fieldInfo.name));
                if (iBinarySearch2 >= 0) {
                    sArr[iBinarySearch2] = (short) i;
                }
                i++;
            }
            this.hashArrayMapping = sArr;
        }
        short s = this.hashArrayMapping[iBinarySearch];
        if (s != -1) {
            return this.sortedFieldDeserializers[s];
        }
        return null;
    }

    static boolean isSetFlag(int i, int[] iArr) {
        int i2;
        if (iArr != null && (i2 = i / 32) < iArr.length) {
            if (((1 << (i % 32)) & iArr[i2]) != 0) {
                return true;
            }
        }
        return false;
    }

    public Object createInstance(DefaultJSONParser defaultJSONParser, Type type) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject());
        }
        Object obj = null;
        if (this.beanInfo.defaultConstructor == null && this.beanInfo.factoryMethod == null) {
            return null;
        }
        if (this.beanInfo.factoryMethod != null && this.beanInfo.defaultConstructorParameterSize > 0) {
            return null;
        }
        try {
            Constructor<?> constructor = this.beanInfo.defaultConstructor;
            if (this.beanInfo.defaultConstructorParameterSize != 0) {
                ParseContext context = defaultJSONParser.getContext();
                if (context == null || context.object == null) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                if (type instanceof Class) {
                    String name = ((Class) type).getName();
                    String strSubstring = name.substring(0, name.lastIndexOf(36));
                    Object obj2 = context.object;
                    String name2 = obj2.getClass().getName();
                    if (!name2.equals(strSubstring)) {
                        ParseContext parseContext = context.parent;
                        if (parseContext == null || parseContext.object == null || !("java.util.ArrayList".equals(name2) || "java.util.List".equals(name2) || "java.util.Collection".equals(name2) || "java.util.Map".equals(name2) || "java.util.HashMap".equals(name2))) {
                            obj = obj2;
                        } else if (parseContext.object.getClass().getName().equals(strSubstring)) {
                            obj = parseContext.object;
                        }
                        obj2 = obj;
                    }
                    if (obj2 == null || ((obj2 instanceof Collection) && ((Collection) obj2).isEmpty())) {
                        throw new JSONException("can't create non-static inner class instance.");
                    }
                    objNewInstance = constructor.newInstance(obj2);
                } else {
                    throw new JSONException("can't create non-static inner class instance.");
                }
            } else if (constructor != null) {
                objNewInstance = constructor.newInstance(null);
            } else {
                objNewInstance = this.beanInfo.factoryMethod.invoke(null, null);
            }
            if (defaultJSONParser != null && defaultJSONParser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                for (FieldInfo fieldInfo : this.beanInfo.fields) {
                    if (fieldInfo.fieldClass == String.class) {
                        try {
                            fieldInfo.set(objNewInstance, "");
                        } catch (Exception e) {
                            throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                        }
                    }
                }
            }
            return objNewInstance;
        } catch (JSONException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e3);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, 0);
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, int i) {
        return (T) deserialze(defaultJSONParser, type, obj, null, i, null);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        Enum<?> enumScanEnum;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 14) {
            throw new JSONException("error");
        }
        String strScanTypeName = jSONLexer.scanTypeName(defaultJSONParser.symbolTable);
        if (strScanTypeName != null) {
            ObjectDeserializer seeAlso = getSeeAlso(defaultJSONParser.getConfig(), this.beanInfo, strScanTypeName);
            if (seeAlso == null) {
                seeAlso = defaultJSONParser.getConfig().getDeserializer(defaultJSONParser.getConfig().checkAutoType(strScanTypeName, TypeUtils.getClass(type), jSONLexer.getFeatures()));
            }
            if (seeAlso instanceof JavaBeanDeserializer) {
                return (T) ((JavaBeanDeserializer) seeAlso).deserialzeArrayMapping(defaultJSONParser, type, obj, obj2);
            }
        }
        T t = (T) createInstance(defaultJSONParser, type);
        int length = this.sortedFieldDeserializers.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            char c = i == length + (-1) ? Operators.ARRAY_END : Operators.ARRAY_SEPRATOR;
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
            Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
            if (cls == Integer.TYPE) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanInt(c));
            } else if (cls == String.class) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanString(c));
            } else if (cls == Long.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanLong(c));
            } else if (cls.isEnum()) {
                char current = jSONLexer.getCurrent();
                if (current == '\"' || current == 'n') {
                    enumScanEnum = jSONLexer.scanEnum(cls, defaultJSONParser.getSymbolTable(), c);
                } else if (current >= '0' && current <= '9') {
                    enumScanEnum = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(jSONLexer.scanInt(c));
                } else {
                    enumScanEnum = scanEnum(jSONLexer, c);
                }
                fieldDeserializer.setValue(t, enumScanEnum);
            } else if (cls == Boolean.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanBoolean(c));
            } else if (cls == Float.TYPE) {
                fieldDeserializer.setValue(t, Float.valueOf(jSONLexer.scanFloat(c)));
            } else if (cls == Double.TYPE) {
                fieldDeserializer.setValue(t, Double.valueOf(jSONLexer.scanDouble(c)));
            } else if (cls == Date.class && jSONLexer.getCurrent() == '1') {
                fieldDeserializer.setValue(t, new Date(jSONLexer.scanLong(c)));
            } else if (cls == BigDecimal.class) {
                fieldDeserializer.setValue(t, jSONLexer.scanDecimal(c));
            } else {
                jSONLexer.nextToken(14);
                fieldDeserializer.setValue(t, defaultJSONParser.parseObject(fieldDeserializer.fieldInfo.fieldType, fieldDeserializer.fieldInfo.name));
                if (jSONLexer.token() == 15) {
                    break;
                }
                check(jSONLexer, c == ']' ? 15 : 16);
            }
            i++;
        }
        jSONLexer.nextToken(16);
        return t;
    }

    protected void check(JSONLexer jSONLexer, int i) {
        if (jSONLexer.token() != i) {
            throw new JSONException("syntax error");
        }
    }

    protected Enum<?> scanEnum(JSONLexer jSONLexer, char c) {
        throw new JSONException("illegal enum. " + jSONLexer.info());
    }

    /* JADX WARN: Code restructure failed: missing block: B:277:0x036d, code lost:
    
        if (r8.matchStat == (-2)) goto L278;
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x04fe, code lost:
    
        if (r10 != null) goto L375;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x0500, code lost:
    
        r11 = r9.checkAutoType(r4, r6, r8.getFeatures());
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x050a, code lost:
    
        r11 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x050b, code lost:
    
        r6 = r2.getConfig().getDeserializer(r11);
        r11 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:0x0516, code lost:
    
        r0 = (T) r6.deserialze(r2, r11, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:379:0x051c, code lost:
    
        if ((r6 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L385;
     */
    /* JADX WARN: Code restructure failed: missing block: B:380:0x051e, code lost:
    
        r6 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:381:0x0520, code lost:
    
        if (r14 == null) goto L385;
     */
    /* JADX WARN: Code restructure failed: missing block: B:382:0x0522, code lost:
    
        r5 = r6.getFieldDeserializer(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x0526, code lost:
    
        if (r5 == null) goto L385;
     */
    /* JADX WARN: Code restructure failed: missing block: B:384:0x0528, code lost:
    
        r5.setValue((java.lang.Object) r0, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:385:0x052b, code lost:
    
        if (r7 == null) goto L387;
     */
    /* JADX WARN: Code restructure failed: missing block: B:386:0x052d, code lost:
    
        r7.object = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x052f, code lost:
    
        r2.setContext(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:388:0x0532, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:392:0x053e, code lost:
    
        r15 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:638:0x08ff, code lost:
    
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r8.token()));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x01b9 A[Catch: all -> 0x0377, TryCatch #13 {all -> 0x0377, blocks: (B:104:0x018d, B:106:0x0199, B:108:0x019f, B:114:0x01b9, B:116:0x01bf, B:286:0x0384, B:288:0x038e, B:290:0x039a, B:475:0x0669, B:477:0x066f, B:482:0x067b, B:485:0x0683, B:486:0x0687, B:488:0x068a, B:490:0x0692, B:492:0x06a2, B:531:0x072d, B:493:0x06aa, B:495:0x06b0, B:496:0x06b6, B:498:0x06ba, B:499:0x06c0, B:501:0x06c4, B:502:0x06c9, B:504:0x06cd, B:505:0x06d2, B:507:0x06d6, B:508:0x06db, B:510:0x06df, B:513:0x06e6, B:516:0x06f1, B:518:0x06f7, B:520:0x06fe, B:522:0x0708, B:524:0x0710, B:526:0x0714, B:528:0x071e, B:530:0x0729, B:565:0x07a1, B:567:0x07a9, B:570:0x07b0, B:572:0x07b3, B:574:0x07b7, B:576:0x07bd, B:578:0x07c4, B:580:0x07d0, B:582:0x07d6, B:583:0x07e1, B:585:0x07e4, B:587:0x07e8, B:589:0x07ee, B:591:0x07f5, B:592:0x07fe, B:597:0x0811, B:598:0x0819, B:600:0x081f, B:602:0x0831, B:614:0x0894, B:604:0x083a, B:605:0x0860, B:593:0x0801, B:594:0x0806, B:606:0x0861, B:608:0x0867, B:611:0x0873, B:612:0x0891, B:532:0x0733, B:534:0x073d, B:536:0x0747, B:538:0x074d, B:563:0x079b, B:539:0x0754, B:541:0x075a, B:542:0x075f, B:544:0x0763, B:545:0x0768, B:547:0x076c, B:548:0x0771, B:550:0x0775, B:551:0x077a, B:553:0x077e, B:554:0x0783, B:556:0x0787, B:559:0x078e, B:615:0x0896, B:622:0x08a5, B:628:0x08b2, B:629:0x08b9, B:293:0x03a5, B:300:0x03b9, B:302:0x03c3, B:304:0x03cf, B:336:0x0456, B:338:0x045f, B:343:0x046f, B:344:0x0476, B:306:0x03d5, B:308:0x03dd, B:310:0x03e3, B:311:0x03e6, B:312:0x03f2, B:315:0x03fb, B:317:0x03ff, B:318:0x0402, B:320:0x0406, B:321:0x0409, B:322:0x0415, B:324:0x041d, B:325:0x0423, B:327:0x0429, B:329:0x042f, B:330:0x0435, B:331:0x043d, B:332:0x0441, B:335:0x0449, B:345:0x0477, B:346:0x0491, B:348:0x0494, B:352:0x049e, B:354:0x04a8, B:356:0x04bb, B:359:0x04c5, B:361:0x04cd, B:363:0x04d5, B:366:0x04e2, B:368:0x04ea, B:370:0x04f2, B:372:0x04fa, B:374:0x0500, B:376:0x050b, B:378:0x0516, B:380:0x051e, B:382:0x0522, B:384:0x0528, B:389:0x0533, B:391:0x053b, B:395:0x054e, B:396:0x0556, B:350:0x049a, B:120:0x01ce, B:125:0x01dd, B:132:0x01eb, B:135:0x01f7, B:278:0x036f, B:140:0x0201, B:142:0x0205, B:145:0x020e, B:150:0x0218, B:153:0x0221, B:158:0x022b, B:161:0x0234, B:164:0x023a, B:169:0x0244, B:174:0x024e, B:179:0x0258, B:181:0x025e, B:184:0x026c, B:186:0x0274, B:188:0x0278, B:191:0x0287, B:196:0x0292, B:199:0x029c, B:204:0x02a7, B:207:0x02b1, B:212:0x02bc, B:215:0x02c6, B:218:0x02cd, B:221:0x02d5, B:223:0x02dd, B:227:0x02e9, B:230:0x02ef, B:226:0x02e5, B:233:0x02f6, B:235:0x02fe, B:239:0x030a, B:242:0x0310, B:238:0x0306, B:245:0x0316, B:249:0x0326, B:252:0x032c, B:248:0x0322, B:255:0x0332, B:257:0x033a, B:261:0x0346, B:264:0x034c, B:260:0x0342, B:267:0x0352, B:269:0x0358, B:273:0x0364, B:276:0x036a, B:272:0x0360), top: B:676:0x018d, inners: #6, #7, #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:281:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0384 A[Catch: all -> 0x0377, TryCatch #13 {all -> 0x0377, blocks: (B:104:0x018d, B:106:0x0199, B:108:0x019f, B:114:0x01b9, B:116:0x01bf, B:286:0x0384, B:288:0x038e, B:290:0x039a, B:475:0x0669, B:477:0x066f, B:482:0x067b, B:485:0x0683, B:486:0x0687, B:488:0x068a, B:490:0x0692, B:492:0x06a2, B:531:0x072d, B:493:0x06aa, B:495:0x06b0, B:496:0x06b6, B:498:0x06ba, B:499:0x06c0, B:501:0x06c4, B:502:0x06c9, B:504:0x06cd, B:505:0x06d2, B:507:0x06d6, B:508:0x06db, B:510:0x06df, B:513:0x06e6, B:516:0x06f1, B:518:0x06f7, B:520:0x06fe, B:522:0x0708, B:524:0x0710, B:526:0x0714, B:528:0x071e, B:530:0x0729, B:565:0x07a1, B:567:0x07a9, B:570:0x07b0, B:572:0x07b3, B:574:0x07b7, B:576:0x07bd, B:578:0x07c4, B:580:0x07d0, B:582:0x07d6, B:583:0x07e1, B:585:0x07e4, B:587:0x07e8, B:589:0x07ee, B:591:0x07f5, B:592:0x07fe, B:597:0x0811, B:598:0x0819, B:600:0x081f, B:602:0x0831, B:614:0x0894, B:604:0x083a, B:605:0x0860, B:593:0x0801, B:594:0x0806, B:606:0x0861, B:608:0x0867, B:611:0x0873, B:612:0x0891, B:532:0x0733, B:534:0x073d, B:536:0x0747, B:538:0x074d, B:563:0x079b, B:539:0x0754, B:541:0x075a, B:542:0x075f, B:544:0x0763, B:545:0x0768, B:547:0x076c, B:548:0x0771, B:550:0x0775, B:551:0x077a, B:553:0x077e, B:554:0x0783, B:556:0x0787, B:559:0x078e, B:615:0x0896, B:622:0x08a5, B:628:0x08b2, B:629:0x08b9, B:293:0x03a5, B:300:0x03b9, B:302:0x03c3, B:304:0x03cf, B:336:0x0456, B:338:0x045f, B:343:0x046f, B:344:0x0476, B:306:0x03d5, B:308:0x03dd, B:310:0x03e3, B:311:0x03e6, B:312:0x03f2, B:315:0x03fb, B:317:0x03ff, B:318:0x0402, B:320:0x0406, B:321:0x0409, B:322:0x0415, B:324:0x041d, B:325:0x0423, B:327:0x0429, B:329:0x042f, B:330:0x0435, B:331:0x043d, B:332:0x0441, B:335:0x0449, B:345:0x0477, B:346:0x0491, B:348:0x0494, B:352:0x049e, B:354:0x04a8, B:356:0x04bb, B:359:0x04c5, B:361:0x04cd, B:363:0x04d5, B:366:0x04e2, B:368:0x04ea, B:370:0x04f2, B:372:0x04fa, B:374:0x0500, B:376:0x050b, B:378:0x0516, B:380:0x051e, B:382:0x0522, B:384:0x0528, B:389:0x0533, B:391:0x053b, B:395:0x054e, B:396:0x0556, B:350:0x049a, B:120:0x01ce, B:125:0x01dd, B:132:0x01eb, B:135:0x01f7, B:278:0x036f, B:140:0x0201, B:142:0x0205, B:145:0x020e, B:150:0x0218, B:153:0x0221, B:158:0x022b, B:161:0x0234, B:164:0x023a, B:169:0x0244, B:174:0x024e, B:179:0x0258, B:181:0x025e, B:184:0x026c, B:186:0x0274, B:188:0x0278, B:191:0x0287, B:196:0x0292, B:199:0x029c, B:204:0x02a7, B:207:0x02b1, B:212:0x02bc, B:215:0x02c6, B:218:0x02cd, B:221:0x02d5, B:223:0x02dd, B:227:0x02e9, B:230:0x02ef, B:226:0x02e5, B:233:0x02f6, B:235:0x02fe, B:239:0x030a, B:242:0x0310, B:238:0x0306, B:245:0x0316, B:249:0x0326, B:252:0x032c, B:248:0x0322, B:255:0x0332, B:257:0x033a, B:261:0x0346, B:264:0x034c, B:260:0x0342, B:267:0x0352, B:269:0x0358, B:273:0x0364, B:276:0x036a, B:272:0x0360), top: B:676:0x018d, inners: #6, #7, #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:398:0x055d  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x0568 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:410:0x0595 A[PHI: r6 r7 r18
      0x0595: PHI (r6v10 java.lang.Object) = (r6v9 java.lang.Object), (r6v9 java.lang.Object), (r6v18 java.lang.Object) binds: [B:399:0x0566, B:400:0x0568, B:405:0x057e] A[DONT_GENERATE, DONT_INLINE]
      0x0595: PHI (r7v6 com.alibaba.fastjson.parser.ParseContext) = 
      (r7v2 com.alibaba.fastjson.parser.ParseContext)
      (r7v2 com.alibaba.fastjson.parser.ParseContext)
      (r7v11 com.alibaba.fastjson.parser.ParseContext)
     binds: [B:399:0x0566, B:400:0x0568, B:405:0x057e] A[DONT_GENERATE, DONT_INLINE]
      0x0595: PHI (r18v3 java.util.HashMap) = (r18v1 java.util.HashMap), (r18v1 java.util.HashMap), (r18v5 java.util.HashMap) binds: [B:399:0x0566, B:400:0x0568, B:405:0x057e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:412:0x059c  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0613  */
    /* JADX WARN: Removed duplicated region for block: B:468:0x0657  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x0658 A[Catch: all -> 0x0909, TryCatch #3 {all -> 0x0909, blocks: (B:636:0x08d2, B:466:0x064f, B:469:0x0658, B:471:0x0660, B:631:0x08be, B:633:0x08c6, B:637:0x08e0, B:638:0x08ff, B:458:0x062c, B:460:0x0632, B:462:0x063a, B:464:0x0647, B:639:0x0900, B:640:0x0908), top: B:660:0x08d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:597:0x0811 A[Catch: all -> 0x0377, TRY_ENTER, TryCatch #13 {all -> 0x0377, blocks: (B:104:0x018d, B:106:0x0199, B:108:0x019f, B:114:0x01b9, B:116:0x01bf, B:286:0x0384, B:288:0x038e, B:290:0x039a, B:475:0x0669, B:477:0x066f, B:482:0x067b, B:485:0x0683, B:486:0x0687, B:488:0x068a, B:490:0x0692, B:492:0x06a2, B:531:0x072d, B:493:0x06aa, B:495:0x06b0, B:496:0x06b6, B:498:0x06ba, B:499:0x06c0, B:501:0x06c4, B:502:0x06c9, B:504:0x06cd, B:505:0x06d2, B:507:0x06d6, B:508:0x06db, B:510:0x06df, B:513:0x06e6, B:516:0x06f1, B:518:0x06f7, B:520:0x06fe, B:522:0x0708, B:524:0x0710, B:526:0x0714, B:528:0x071e, B:530:0x0729, B:565:0x07a1, B:567:0x07a9, B:570:0x07b0, B:572:0x07b3, B:574:0x07b7, B:576:0x07bd, B:578:0x07c4, B:580:0x07d0, B:582:0x07d6, B:583:0x07e1, B:585:0x07e4, B:587:0x07e8, B:589:0x07ee, B:591:0x07f5, B:592:0x07fe, B:597:0x0811, B:598:0x0819, B:600:0x081f, B:602:0x0831, B:614:0x0894, B:604:0x083a, B:605:0x0860, B:593:0x0801, B:594:0x0806, B:606:0x0861, B:608:0x0867, B:611:0x0873, B:612:0x0891, B:532:0x0733, B:534:0x073d, B:536:0x0747, B:538:0x074d, B:563:0x079b, B:539:0x0754, B:541:0x075a, B:542:0x075f, B:544:0x0763, B:545:0x0768, B:547:0x076c, B:548:0x0771, B:550:0x0775, B:551:0x077a, B:553:0x077e, B:554:0x0783, B:556:0x0787, B:559:0x078e, B:615:0x0896, B:622:0x08a5, B:628:0x08b2, B:629:0x08b9, B:293:0x03a5, B:300:0x03b9, B:302:0x03c3, B:304:0x03cf, B:336:0x0456, B:338:0x045f, B:343:0x046f, B:344:0x0476, B:306:0x03d5, B:308:0x03dd, B:310:0x03e3, B:311:0x03e6, B:312:0x03f2, B:315:0x03fb, B:317:0x03ff, B:318:0x0402, B:320:0x0406, B:321:0x0409, B:322:0x0415, B:324:0x041d, B:325:0x0423, B:327:0x0429, B:329:0x042f, B:330:0x0435, B:331:0x043d, B:332:0x0441, B:335:0x0449, B:345:0x0477, B:346:0x0491, B:348:0x0494, B:352:0x049e, B:354:0x04a8, B:356:0x04bb, B:359:0x04c5, B:361:0x04cd, B:363:0x04d5, B:366:0x04e2, B:368:0x04ea, B:370:0x04f2, B:372:0x04fa, B:374:0x0500, B:376:0x050b, B:378:0x0516, B:380:0x051e, B:382:0x0522, B:384:0x0528, B:389:0x0533, B:391:0x053b, B:395:0x054e, B:396:0x0556, B:350:0x049a, B:120:0x01ce, B:125:0x01dd, B:132:0x01eb, B:135:0x01f7, B:278:0x036f, B:140:0x0201, B:142:0x0205, B:145:0x020e, B:150:0x0218, B:153:0x0221, B:158:0x022b, B:161:0x0234, B:164:0x023a, B:169:0x0244, B:174:0x024e, B:179:0x0258, B:181:0x025e, B:184:0x026c, B:186:0x0274, B:188:0x0278, B:191:0x0287, B:196:0x0292, B:199:0x029c, B:204:0x02a7, B:207:0x02b1, B:212:0x02bc, B:215:0x02c6, B:218:0x02cd, B:221:0x02d5, B:223:0x02dd, B:227:0x02e9, B:230:0x02ef, B:226:0x02e5, B:233:0x02f6, B:235:0x02fe, B:239:0x030a, B:242:0x0310, B:238:0x0306, B:245:0x0316, B:249:0x0326, B:252:0x032c, B:248:0x0322, B:255:0x0332, B:257:0x033a, B:261:0x0346, B:264:0x034c, B:260:0x0342, B:267:0x0352, B:269:0x0358, B:273:0x0364, B:276:0x036a, B:272:0x0360), top: B:676:0x018d, inners: #6, #7, #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:649:0x0919  */
    /* JADX WARN: Type inference failed for: r10v36 */
    /* JADX WARN: Type inference failed for: r10v38 */
    /* JADX WARN: Type inference failed for: r10v39 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.lang.Class, java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r10v40 */
    /* JADX WARN: Type inference failed for: r10v41, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r10v53 */
    /* JADX WARN: Type inference failed for: r10v54 */
    /* JADX WARN: Type inference failed for: r10v55 */
    /* JADX WARN: Type inference failed for: r10v56 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r11v36 */
    /* JADX WARN: Type inference failed for: r11v37, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r11v46, types: [com.alibaba.fastjson.parser.deserializer.FieldDeserializer] */
    /* JADX WARN: Type inference failed for: r11v47 */
    /* JADX WARN: Type inference failed for: r11v48 */
    /* JADX WARN: Type inference failed for: r11v49 */
    /* JADX WARN: Type inference failed for: r11v6, types: [com.alibaba.fastjson.parser.deserializer.FieldDeserializer[]] */
    /* JADX WARN: Type inference failed for: r11v9, types: [com.alibaba.fastjson.parser.deserializer.FieldDeserializer] */
    /* JADX WARN: Type inference failed for: r13v19, types: [com.alibaba.fastjson.util.FieldInfo] */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [com.alibaba.fastjson.util.FieldInfo] */
    /* JADX WARN: Type inference failed for: r31v0, types: [com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer] */
    /* JADX WARN: Type inference failed for: r6v106, types: [com.alibaba.fastjson.parser.ParserConfig] */
    /* JADX WARN: Type inference failed for: r6v136 */
    /* JADX WARN: Type inference failed for: r6v137 */
    /* JADX WARN: Type inference failed for: r6v36, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r6v52, types: [com.alibaba.fastjson.parser.deserializer.ObjectDeserializer] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r32, java.lang.reflect.Type r33, java.lang.Object r34, java.lang.Object r35, int r36, int[] r37) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 2340
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object, int, int[]):java.lang.Object");
    }

    protected Enum scanEnum(JSONLexerBase jSONLexerBase, char[] cArr, ObjectDeserializer objectDeserializer) {
        EnumDeserializer enumDeserializer = objectDeserializer instanceof EnumDeserializer ? (EnumDeserializer) objectDeserializer : null;
        if (enumDeserializer == null) {
            jSONLexerBase.matchStat = -1;
            return null;
        }
        long jScanEnumSymbol = jSONLexerBase.scanEnumSymbol(cArr);
        if (jSONLexerBase.matchStat <= 0) {
            return null;
        }
        Enum enumByHashCode = enumDeserializer.getEnumByHashCode(jScanEnumSymbol);
        if (enumByHashCode == null) {
            if (jScanEnumSymbol == TypeUtils.fnv1a_64_magic_hashcode) {
                return null;
            }
            if (jSONLexerBase.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                throw new JSONException("not match enum value, " + enumDeserializer.enumClass);
            }
        }
        return enumByHashCode;
    }

    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map) {
        return parseField(defaultJSONParser, str, obj, type, map, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0110  */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean parseField(com.alibaba.fastjson.parser.DefaultJSONParser r21, java.lang.String r22, java.lang.Object r23, java.lang.reflect.Type r24, java.util.Map<java.lang.String, java.lang.Object> r25, int[] r26) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 567
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.parseField(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.String, java.lang.Object, java.lang.reflect.Type, java.util.Map, int[]):boolean");
    }

    public FieldDeserializer smartMatch(String str) {
        return smartMatch(str, null);
    }

    public FieldDeserializer smartMatch(String str, int[] iArr) {
        boolean zStartsWith;
        if (str == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer = getFieldDeserializer(str, iArr);
        if (fieldDeserializer == null) {
            int i = 0;
            if (this.smartMatchHashArray == null) {
                long[] jArr = new long[this.sortedFieldDeserializers.length];
                int i2 = 0;
                while (true) {
                    FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                    if (i2 >= fieldDeserializerArr.length) {
                        break;
                    }
                    jArr[i2] = fieldDeserializerArr[i2].fieldInfo.nameHashCode;
                    i2++;
                }
                Arrays.sort(jArr);
                this.smartMatchHashArray = jArr;
            }
            int iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(str));
            if (iBinarySearch < 0) {
                iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_extract(str));
            }
            if (iBinarySearch < 0) {
                zStartsWith = str.startsWith("is");
                if (zStartsWith) {
                    iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_extract(str.substring(2)));
                }
            } else {
                zStartsWith = false;
            }
            if (iBinarySearch >= 0) {
                if (this.smartMatchHashArrayMapping == null) {
                    short[] sArr = new short[this.smartMatchHashArray.length];
                    Arrays.fill(sArr, (short) -1);
                    while (true) {
                        FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                        if (i >= fieldDeserializerArr2.length) {
                            break;
                        }
                        int iBinarySearch2 = Arrays.binarySearch(this.smartMatchHashArray, fieldDeserializerArr2[i].fieldInfo.nameHashCode);
                        if (iBinarySearch2 >= 0) {
                            sArr[iBinarySearch2] = (short) i;
                        }
                        i++;
                    }
                    this.smartMatchHashArrayMapping = sArr;
                }
                short s = this.smartMatchHashArrayMapping[iBinarySearch];
                if (s != -1 && !isSetFlag(s, iArr)) {
                    fieldDeserializer = this.sortedFieldDeserializers[s];
                }
            }
            if (fieldDeserializer != null) {
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                if ((fieldInfo.parserFeatures & Feature.DisableFieldSmartMatch.mask) != 0) {
                    return null;
                }
                Class<?> cls = fieldInfo.fieldClass;
                if (zStartsWith && cls != Boolean.TYPE && cls != Boolean.class) {
                    return null;
                }
            }
        }
        return fieldDeserializer;
    }

    private Object createFactoryInstance(ParserConfig parserConfig, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.beanInfo.factoryMethod.invoke(null, obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object createInstance(java.util.Map<java.lang.String, java.lang.Object> r13, com.alibaba.fastjson.parser.ParserConfig r14) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.ArrayIndexOutOfBoundsException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 796
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.createInstance(java.util.Map, com.alibaba.fastjson.parser.ParserConfig):java.lang.Object");
    }

    public Type getFieldType(int i) {
        return this.sortedFieldDeserializers[i].fieldInfo.fieldType;
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i) {
        return parseRest(defaultJSONParser, type, obj, obj2, i, new int[0]);
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i, int[] iArr) {
        return deserialze(defaultJSONParser, type, obj, obj2, i, iArr);
    }

    protected static JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, String str) {
        if (javaBeanInfo.jsonType == null) {
            return null;
        }
        for (Class<?> cls : javaBeanInfo.jsonType.seeAlso()) {
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                JavaBeanInfo javaBeanInfo2 = javaBeanDeserializer.beanInfo;
                if (javaBeanInfo2.typeName.equals(str)) {
                    return javaBeanDeserializer;
                }
                JavaBeanDeserializer seeAlso = getSeeAlso(parserConfig, javaBeanInfo2, str);
                if (seeAlso != null) {
                    return seeAlso;
                }
            }
        }
        return null;
    }

    protected static void parseArray(Collection collection, ObjectDeserializer objectDeserializer, DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
        int i = jSONLexerBase.token();
        if (i == 8) {
            jSONLexerBase.nextToken(16);
            jSONLexerBase.token();
            return;
        }
        if (i != 14) {
            defaultJSONParser.throwException(i);
        }
        if (jSONLexerBase.getCurrent() == '[') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(14);
        } else {
            jSONLexerBase.nextToken(14);
        }
        if (jSONLexerBase.token() == 15) {
            jSONLexerBase.nextToken();
            return;
        }
        int i2 = 0;
        while (true) {
            collection.add(objectDeserializer.deserialze(defaultJSONParser, type, Integer.valueOf(i2)));
            i2++;
            if (jSONLexerBase.token() != 16) {
                break;
            }
            if (jSONLexerBase.getCurrent() == '[') {
                jSONLexerBase.next();
                jSONLexerBase.setToken(14);
            } else {
                jSONLexerBase.nextToken(14);
            }
        }
        int i3 = jSONLexerBase.token();
        if (i3 != 15) {
            defaultJSONParser.throwException(i3);
        }
        if (jSONLexerBase.getCurrent() == ',') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(16);
        } else {
            jSONLexerBase.nextToken(16);
        }
    }
}
